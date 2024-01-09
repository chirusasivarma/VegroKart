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

import com.example.VegroKart.Entity.Beverages;
import com.example.VegroKart.Entity.BeveragesResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.BeveragesRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeveragesService {
	@Autowired
	private BeveragesRepository beveragesRepository;

	public Beverages saveBeverages(HttpServletRequest request, MultipartFile file, String beveragesName,String quantity, Double price)
			
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		Beverages beverages = new Beverages();
		beverages.setImage(blob);
		beverages.setBeveragesName(beveragesName);
		beverages.setQuantity(quantity);
		beverages.setPrice(price);
		
		return beveragesRepository.save(beverages);
	}
	
	@Transactional
	public List<BeveragesResponse> getAllBeverages() {
		List<Beverages> beverages = beveragesRepository.findAll();

		List<BeveragesResponse> beveragesResponses = new ArrayList<>();

		beverages.forEach(e -> {

				BeveragesResponse beveragesResponse = new BeveragesResponse();
				beveragesResponse.setId(e.getId());
				beveragesResponse.setBeveragesName(e.getBeveragesName());
				beveragesResponse.setQuantity(e.getQuantity());
				beveragesResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/beverages/displayImage?id=" + e.getId(); // Custom URL

				beveragesResponse.setImage(customImageUrl);
				beveragesResponses.add(beveragesResponse);
			
		});
		return beveragesResponses;

	}

	@Transactional
	public BeveragesResponse getBeveragesById(long id) {
	
		Optional<Beverages> optionalbeverages = beveragesRepository.findById(id);
		if (optionalbeverages.isEmpty()) {
			throw new ProductsIsNotFoundException("Beverages with ID " + id + " not found");
		}

		Beverages beverages = optionalbeverages.get();
		BeveragesResponse beveragesResponse = BeveragesResponse(beverages);
		return beveragesResponse;
	}

	private BeveragesResponse BeveragesResponse(Beverages beverages) {
		BeveragesResponse beveragesResponse = new BeveragesResponse();
		beveragesResponse.setId(beverages.getId());
		beveragesResponse.setBeveragesName(beverages.getBeveragesName());
		beveragesResponse.setQuantity(beverages.getQuantity());
		beveragesResponse.setPrice(beverages.getPrice());
		try {
			byte[] imageBytes = beverages.getImage().getBytes(1, (int) beverages.getImage().length());
			String beveragesImageUrl = "/beverages/GetById?id=" + beverages.getId();
			beveragesResponse.setImage(beveragesImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for fruits id: " + beverages.getId(), e);

		}

		return beveragesResponse;

	}
	public Beverages getImageViewById(Integer id) {
		Optional<Beverages> optionalBeverages = beveragesRepository.findById(id);
		if (optionalBeverages.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalBeverages.get();
		}
	}
	@Transactional
	public List<BeveragesResponse> getBeveragesByBeveragesName(String BeveragesName) {
		List<Beverages> beverages = beveragesRepository.findByBeveragesName(BeveragesName);
		List<BeveragesResponse> beveragesResponses = new ArrayList<>();

		beverages.forEach(e -> {
			BeveragesResponse beveragesResponse = new BeveragesResponse();
			beveragesResponse.setId(e.getId());
			beveragesResponse.setBeveragesName(e.getBeveragesName());
			beveragesResponse.setQuantity(e.getQuantity());
			beveragesResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/beverages/GetByName?id=" + e.getId(); // Custom URL

				beveragesResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			beveragesResponses.add(beveragesResponse);
		});
		return beveragesResponses;
	}
public String updatebeverages(long id, String beveragesName, String quantity, Double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<Beverages> optionalExistingBeverages = beveragesRepository.findById(id);

		if (optionalExistingBeverages.isEmpty()) {

			throw new ProductsIsNotFoundException("Beverages with ID " + id + " not found");

		}

		Beverages existingBeverages = optionalExistingBeverages.get();

		existingBeverages.setBeveragesName(beveragesName);		
		existingBeverages.setQuantity(quantity);
		existingBeverages.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingBeverages.setImage(blob);
		}
		beveragesRepository.save(existingBeverages);
		return "Beverages with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteBeverages(long id) {
        Optional<Beverages> optionalBeverages = beveragesRepository.findById(id);

        if (optionalBeverages.isPresent()) {
            beveragesRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("Beverages not found with this id " + id); 
        }
    }



}
