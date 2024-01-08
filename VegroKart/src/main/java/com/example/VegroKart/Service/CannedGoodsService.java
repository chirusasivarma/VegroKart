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

import com.example.VegroKart.Entity.CannedGoods;
import com.example.VegroKart.Entity.CannedGoodsResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.CannedGoodsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CannedGoodsService {
	
	@Autowired
	private CannedGoodsRepository cannedGoodsRepository;

	public CannedGoods saveCannedGoods(HttpServletRequest request, MultipartFile file, String name,
			String quantity, Double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		CannedGoods cannedGoods = new CannedGoods();
		cannedGoods.setImage(blob);
		cannedGoods.setName(name);		
		cannedGoods.setQuantity(quantity);
		cannedGoods.setPrice(price);
		
		return cannedGoodsRepository.save(cannedGoods);
	}

	@Transactional
	public List<CannedGoodsResponse> getAllCannedGoods() {
		List<CannedGoods> cannedGoods = cannedGoodsRepository.findAll();

		List<CannedGoodsResponse> cannedGoodsResponses = new ArrayList<>();

		cannedGoods.forEach(e -> {

			CannedGoodsResponse cannedGoodsResponse = new CannedGoodsResponse();
			cannedGoodsResponse.setId(e.getId());
			cannedGoodsResponse.setName(e.getName());
			cannedGoodsResponse.setQuantity(e.getQuantity());
			cannedGoodsResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/cannedGoods/displayImage?id=" + e.getId(); // Custom URL

				cannedGoodsResponse.setImage(customImageUrl);
				cannedGoodsResponses.add(cannedGoodsResponse);
			
		});
		return cannedGoodsResponses;

	}

	@Transactional
	public CannedGoodsResponse getCannedGoodsById(long id) {
	
		Optional<CannedGoods> optionalcannedGoods = cannedGoodsRepository.findById(id);
		if (optionalcannedGoods.isEmpty()) {
			throw new ProductsIsNotFoundException("cannedGoods with ID " + id + " not found");
		}

		CannedGoods cannedGoods = optionalcannedGoods.get();
		CannedGoodsResponse cannedGoodsResponse = CannedGoodsResponse(cannedGoods);
		return cannedGoodsResponse;
	}

	private CannedGoodsResponse CannedGoodsResponse(CannedGoods cannedGoods) {
		CannedGoodsResponse cannedGoodsResponse = new CannedGoodsResponse();
		cannedGoodsResponse.setId(cannedGoods.getId());
		cannedGoodsResponse.setName(cannedGoods.getName());
		cannedGoodsResponse.setQuantity(cannedGoods.getQuantity());
		cannedGoodsResponse.setPrice(cannedGoods.getPrice());
		try {
			byte[] imageBytes = cannedGoods.getImage().getBytes(1, (int) cannedGoods.getImage().length());
			String cannedGoodsImageUrl = "/cannedGoods/GetById?id=" + cannedGoods.getId();
			cannedGoodsResponse.setImage(cannedGoodsImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for cannedGoods id: " + cannedGoods.getId(), e);

		}

		return cannedGoodsResponse;

	}

	public CannedGoods getImageViewById(Integer id) {
		Optional<CannedGoods> optionalCannedGoods = cannedGoodsRepository.findById(id);
		if (optionalCannedGoods.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalCannedGoods.get();
		}
	}
	@Transactional
	public List<CannedGoodsResponse> getCannedGoodsByName(String name) {
		List<CannedGoods> cannedGoods = cannedGoodsRepository.findByName(name);
		List<CannedGoodsResponse> cannedGoodsResponses = new ArrayList<>();

		cannedGoods.forEach(e -> {
			CannedGoodsResponse cannedGoodsResponse = new CannedGoodsResponse();
			cannedGoodsResponse.setId(e.getId());
			cannedGoodsResponse.setName(e.getName());
			cannedGoodsResponse.setQuantity(e.getQuantity());
			cannedGoodsResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/cannedGoods/GetByName?id=" + e.getId(); // Custom URL

				cannedGoodsResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cannedGoodsResponses.add(cannedGoodsResponse);
		});
		return cannedGoodsResponses;
	}
	
	public String updatecannedGoods(long id, String name, String quantity, Double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<CannedGoods> optionalExistingCannedGoods = cannedGoodsRepository.findById(id);

		if (optionalExistingCannedGoods.isEmpty()) {

			throw new ProductsIsNotFoundException("CannedGoods with ID " + id + " not found");

		}

		CannedGoods existingCannedGoods = optionalExistingCannedGoods.get();

		existingCannedGoods.setName(name);		
		existingCannedGoods.setQuantity(quantity);
		existingCannedGoods.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingCannedGoods.setImage(blob);
		}
		cannedGoodsRepository.save(existingCannedGoods);
		return "CannedGoods with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteCannedGoods(long id) {
        Optional<CannedGoods> optionalCannedGoods = cannedGoodsRepository.findById(id);

        if (optionalCannedGoods.isPresent()) {
        	cannedGoodsRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("cannedGoods not found with this id " + id); 
        }
    }

}


