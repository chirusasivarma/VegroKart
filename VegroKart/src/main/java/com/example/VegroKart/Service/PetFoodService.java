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

import com.example.VegroKart.Entity.PetFood;
import com.example.VegroKart.Entity.PetFoodResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.PetFoodRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PetFoodService {
	
	@Autowired
	private PetFoodRepository petFoodRepository;

	public PetFood savePetFood(HttpServletRequest request, MultipartFile file, String foodName,
			int quantity, double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		PetFood petFood = new PetFood();
		petFood.setImage(blob);
		petFood.setFoodName(foodName);	
		petFood.setQuantity(quantity);
		petFood.setPrice(price);
		
		return petFoodRepository.save(petFood);
	}

	@Transactional
	public List<PetFoodResponse> getAllPetFood() {
		List<PetFood> petFood = petFoodRepository.findAll();

		List<PetFoodResponse> petFoodResponses = new ArrayList<>();

		petFood.forEach(e -> {

			PetFoodResponse petFoodResponse = new PetFoodResponse();
			petFoodResponse.setId(e.getId());
			petFoodResponse.setFoodName(e.getFoodName());
			petFoodResponse.setQuantity(e.getQuantity());
			petFoodResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/petFood/displayImage?id=" + e.getId(); // Custom URL

				petFoodResponse.setImage(customImageUrl);
				petFoodResponses.add(petFoodResponse);
			
		});
		return petFoodResponses;

	}

	@Transactional
	public PetFoodResponse getPetFoodById(long id) {
	
		Optional<PetFood> optionalpetFood = petFoodRepository.findById(id);
		if (optionalpetFood.isEmpty()) {
			throw new ProductsIsNotFoundException("petFood with ID " + id + " not found");
		}

		PetFood petFood = optionalpetFood.get();
		PetFoodResponse petFoodResponse = PetFoodResponse(petFood);
		return petFoodResponse;
	}

	private PetFoodResponse PetFoodResponse(PetFood petFood) {
		PetFoodResponse petFoodResponse = new PetFoodResponse();
		petFoodResponse.setId(petFood.getId());
		petFoodResponse.setFoodName(petFood.getFoodName());
		petFoodResponse.setQuantity(petFood.getQuantity());
		petFoodResponse.setPrice(petFood.getPrice());
		try {
			byte[] imageBytes = petFood.getImage().getBytes(1, (int) petFood.getImage().length());
			String petFoodImageUrl = "/petFood/GetById?id=" + petFood.getId();
			petFoodResponse.setImage(petFoodImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for petFood id: " + petFood.getId(), e);

		}

		return petFoodResponse;

	}

	public PetFood getImageViewById(Integer id) {
		Optional<PetFood> optionalPetFood = petFoodRepository.findById(id);
		if (optionalPetFood.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalPetFood.get();
		}
	}
	@Transactional
	public List<PetFoodResponse> getPetFoodByFoodName(String foodname) {
		List<PetFood> petFood = petFoodRepository.findByFoodName(foodname);
		List<PetFoodResponse> petFoodResponses = new ArrayList<>();

		petFood.forEach(e -> {
			PetFoodResponse petFoodResponse = new PetFoodResponse();
			petFoodResponse.setId(e.getId());
			petFoodResponse.setFoodName(e.getFoodName());
			petFoodResponse.setQuantity(e.getQuantity());
			petFoodResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/petFood/GetByName?id=" + e.getId(); // Custom URL

				petFoodResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			petFoodResponses.add(petFoodResponse);
		});
		return petFoodResponses;
	}
	
	public String updatepetFood(long id, String foodName, int quantity, double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<PetFood> optionalExistingPetFood = petFoodRepository.findById(id);

		if (optionalExistingPetFood.isEmpty()) {

			throw new ProductsIsNotFoundException("PetFood with ID " + id + " not found");

		}

		PetFood existingPetFood = optionalExistingPetFood.get();

		existingPetFood.setFoodName(foodName);	
		existingPetFood.setQuantity(quantity);
		existingPetFood.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingPetFood.setImage(blob);
		}
		petFoodRepository.save(existingPetFood);
		return "PetFood with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deletePetFood(long id) {
        Optional<PetFood> optionalPetFood = petFoodRepository.findById(id);

        if (optionalPetFood.isPresent()) {
        	petFoodRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("petFood not found with this id " + id); 
        }
    }

}



