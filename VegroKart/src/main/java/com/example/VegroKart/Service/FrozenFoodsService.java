package com.example.VegroKart.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.FrozenFoods;
import com.example.VegroKart.Entity.FrozenFoodsReponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.FrozenFoodsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FrozenFoodsService {
	
	@Autowired
	private FrozenFoodsRepository frozenFoodsRepository;

	public FrozenFoods saveFrozenFoods(HttpServletRequest request, MultipartFile file, String name,
			String quantity, String price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		FrozenFoods frozenFoods = new FrozenFoods();
		frozenFoods.setImage(blob);
		frozenFoods.setName(name);		
		frozenFoods.setQuantity(quantity);
		frozenFoods.setPrice(price);
		
		return frozenFoodsRepository.save(frozenFoods);
	}

	@Transactional
	public List<FrozenFoodsReponse> getAllFrozenFoods() {
		List<FrozenFoods> frozenFoods = frozenFoodsRepository.findAll();

		List<FrozenFoodsReponse> frozenFoodsResponses = new ArrayList<>();

		frozenFoods.forEach(e -> {

			FrozenFoodsReponse frozenFoodsResponse = new FrozenFoodsReponse();
			frozenFoodsResponse.setId(e.getId());
			frozenFoodsResponse.setName(e.getName());
			frozenFoodsResponse.setQuantity(e.getQuantity());
			frozenFoodsResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/frozenFoodsResponse/displayImage?id=" + e.getId(); // Custom URL

				frozenFoodsResponse.setImage(customImageUrl);
				frozenFoodsResponses.add(frozenFoodsResponse);
			
		});
		return frozenFoodsResponses;

	}

	@Transactional
	public FrozenFoodsReponse getFrozenFoodsById(long id) {
	
		Optional<FrozenFoods> optionalfrozenFoods = frozenFoodsRepository.findById(id);
		if (optionalfrozenFoods.isEmpty()) {
			throw new ProductsIsNotFoundException("FrozenFoods with ID " + id + " not found");
		}

		FrozenFoods frozenFoods = optionalfrozenFoods.get();
		FrozenFoodsReponse frozenFoodsResponse = FrozenFoodsResponse(frozenFoods);
		return frozenFoodsResponse;
	}

	private FrozenFoodsReponse FrozenFoodsResponse(FrozenFoods frozenFoods) {
		FrozenFoodsReponse frozenFoodsResponse = new FrozenFoodsReponse();
		frozenFoodsResponse.setId(frozenFoods.getId());
		frozenFoodsResponse.setName(frozenFoods.getName());
		frozenFoodsResponse.setQuantity(frozenFoods.getQuantity());
		frozenFoodsResponse.setPrice(frozenFoods.getPrice());
		try {
			byte[] imageBytes = frozenFoods.getImage().getBytes(1, (int) frozenFoods.getImage().length());
			String frozenFoodsImageUrl = "/frozenFoods/GetById?id=" + frozenFoods.getId();
			frozenFoodsResponse.setImage(frozenFoodsImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for frozenFoods id: " + frozenFoods.getId(), e);

		}

		return frozenFoodsResponse;

	}

	public FrozenFoods getImageViewById(Integer id) {
		Optional<FrozenFoods> optionalFrozenFoods = frozenFoodsRepository.findById(id);
		if (optionalFrozenFoods.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalFrozenFoods.get();
		}
	}
	@Transactional
	public List<FrozenFoodsReponse> getFrozenFoodsByName(String name) {
		List<FrozenFoods> frozenFoods = frozenFoodsRepository.findByName(name);
		List<FrozenFoodsReponse> frozenFoodsResponses = new ArrayList<>();

		frozenFoods.forEach(e -> {
			FrozenFoodsReponse frozenFoodsResponse = new FrozenFoodsReponse();
			frozenFoodsResponse.setId(e.getId());
			frozenFoodsResponse.setName(e.getName());
			frozenFoodsResponse.setQuantity(e.getQuantity());
			frozenFoodsResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/frozenFoodsResponse/GetByName?id=" + e.getId(); // Custom URL

				frozenFoodsResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frozenFoodsResponses.add(frozenFoodsResponse);
		});
		return frozenFoodsResponses;
	}
	
	public String updatefrozenFoods(long id, String name, String quantity, String price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<FrozenFoods> optionalExistingFrozenFoods = frozenFoodsRepository.findById(id);

		if (optionalExistingFrozenFoods.isEmpty()) {

			throw new ProductsIsNotFoundException("FrozenFoods with ID " + id + " not found");

		}

		FrozenFoods existingFrozenFoods = optionalExistingFrozenFoods.get();

		existingFrozenFoods.setName(name);		
		existingFrozenFoods.setQuantity(quantity);
		existingFrozenFoods.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingFrozenFoods.setImage(blob);
		}
		frozenFoodsRepository.save(existingFrozenFoods);
		return "FrozenFoods with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteFrozenFoods(long id) {
        Optional<FrozenFoods> optionalFrozenFoods = frozenFoodsRepository.findById(id);

        if (optionalFrozenFoods.isPresent()) {
        	frozenFoodsRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("FrozenFoods not found with this id " + id); 
        }
    }

}

