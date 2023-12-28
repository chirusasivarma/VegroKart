package com.example.VegroKart.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.FruitsResponse;
import com.example.VegroKart.Entity.Meat;
import com.example.VegroKart.Exception.FruitsIsNotFoundException;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.FruitsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FruitsService {
	@Autowired
	private FruitsRepository fruitsRepository;

	public Fruits saveFruits(HttpServletRequest request, MultipartFile file, String fruitName,
			int quantity, double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		Fruits fruits = new Fruits();
		fruits.setImage(blob);
		fruits.setFruitName(fruitName);
		fruits.setQuantity(quantity);
		fruits.setPrice(price);
		
		return fruitsRepository.save(fruits);
	}

	@Transactional
	public List<FruitsResponse> getAllFruits() {
	    List<Fruits> fruits = fruitsRepository.findAll(Sort.by(Sort.Order.asc("id")));

	    List<FruitsResponse> fruitsResponses = new ArrayList<>();

	    fruits.forEach(e -> {
	        FruitsResponse fruitsResponse = new FruitsResponse();
	        fruitsResponse.setId(e.getId());
	        fruitsResponse.setFruitName(e.getFruitName());
	        fruitsResponse.setQuantity(e.getQuantity());
	        fruitsResponse.setPrice(e.getPrice());
	        
	        try {
	            byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
	            
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	        String customImageUrl = "/fruits/displayImage?id=" + e.getId();
	        fruitsResponse.setImage(customImageUrl);
	        fruitsResponses.add(fruitsResponse);
	    });
	    return fruitsResponses;
	}

	@Transactional
	public FruitsResponse getFruitsById(long id) {
	
		Optional<Fruits> optionalfruits = fruitsRepository.findById(id);
		if (optionalfruits.isEmpty()) {
			throw new FruitsIsNotFoundException("fruits with ID " + id + " not found");
		}

		Fruits fruits = optionalfruits.get();
		FruitsResponse fruitsResponse = FruitsResponse(fruits);
		return fruitsResponse;
	}

	private FruitsResponse FruitsResponse(Fruits fruits) {
		FruitsResponse fruitsResponse = new FruitsResponse();
		fruitsResponse.setId(fruits.getId());
		fruitsResponse.setFruitName(fruits.getFruitName());
		fruitsResponse.setQuantity(fruits.getQuantity());
		fruitsResponse.setPrice(fruits.getPrice());
		try {
			byte[] imageBytes = fruits.getImage().getBytes(1, (int) fruits.getImage().length());
			String fruitsImageUrl = "/fruits/GetById?id=" + fruits.getId();
			fruitsResponse.setImage(fruitsImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for fruits id: " + fruits.getId(), e);

		}

		return fruitsResponse;

	}

	public Fruits getImageViewById(Integer id) {
		Optional<Fruits> optionalFruits = fruitsRepository.findById(id);
		if (optionalFruits.isEmpty()) {
			throw new FruitsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalFruits.get();
		}
	}
	@Transactional
	public List<FruitsResponse> getFruitsByFruitName(String fruitName) {
		List<Fruits> fruits = fruitsRepository.findByFruitName(fruitName);
		List<FruitsResponse> fruitsResponses = new ArrayList<>();

		fruits.forEach(e -> {
			FruitsResponse fruitsResponse = new FruitsResponse();
			fruitsResponse.setId(e.getId());
			fruitsResponse.setFruitName(e.getFruitName());
			fruitsResponse.setQuantity(e.getQuantity());
			fruitsResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/fruits/GetByName?id=" + e.getId(); // Custom URL

				fruitsResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fruitsResponses.add(fruitsResponse);
		});
		return fruitsResponses;
	}
	
	public String updatefruits(long id, String fruitName, int quantity, double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<Fruits> optionalExistingFruits = fruitsRepository.findById(id);

		if (optionalExistingFruits.isEmpty()) {

			throw new FruitsIsNotFoundException("fruits with ID " + id + " not found");

		}

		Fruits existingFruits = optionalExistingFruits.get();

		existingFruits.setFruitName(fruitName);		
		existingFruits.setQuantity(quantity);
		existingFruits.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingFruits.setImage(blob);
		}
		fruitsRepository.save(existingFruits);
		return "Fruits with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteFruits(long id) {
        Optional<Fruits> optionalFruits = fruitsRepository.findById(id);

        if (optionalFruits.isPresent()) {
            fruitsRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("Fruits not found with this id " + id); 
        }
    }
}