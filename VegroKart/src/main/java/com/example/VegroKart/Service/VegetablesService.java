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

import com.example.VegroKart.Entity.Vegetables;
import com.example.VegroKart.Entity.VegetablesResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.VegetablesRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VegetablesService {
	@Autowired
	private VegetablesRepository vegetablesRepository;

	public Vegetables saveVegetables(HttpServletRequest request, MultipartFile file, String vegetablesName,
			int quantity, double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		Vegetables vegetables = new Vegetables();
		vegetables.setImage(blob);
		vegetables.setVegetablesName(vegetablesName);
		vegetables.setQuantity(quantity);
		vegetables.setPrice(price);
		
		return vegetablesRepository.save(vegetables);
	}

	@Transactional
	public List<VegetablesResponse> getAllVegetables() {
		List<Vegetables> vegetables = vegetablesRepository.findAll();

		List<VegetablesResponse> vegetablesResponses = new ArrayList<>();

		vegetables.forEach(e -> {

			VegetablesResponse vegetablesResponse = new VegetablesResponse();
			vegetablesResponse.setId(e.getId());
			vegetablesResponse.setVegetablesName(e.getVegetablesName());
				vegetablesResponse.setQuantity(e.getQuantity());
				vegetablesResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/vegetables/displayImage?id=" + e.getId(); // Custom URL

				vegetablesResponse.setImage(customImageUrl);
				vegetablesResponses.add(vegetablesResponse);
			
		});
		return vegetablesResponses;

	}

	@Transactional
	public VegetablesResponse getVegetablesById(long id) {
	
		Optional<Vegetables> optionalvegetables = vegetablesRepository.findById(id);
		if (optionalvegetables.isEmpty()) {
			throw new ProductsIsNotFoundException("vegetables with ID " + id + " not found");
		}

		Vegetables vegetables = optionalvegetables.get();
		VegetablesResponse vegetablesResponse = VegetablesResponse(vegetables);
		return vegetablesResponse;
	}

	private VegetablesResponse VegetablesResponse(Vegetables vegetables) {
		VegetablesResponse vegetablesResponse = new VegetablesResponse();
		vegetablesResponse.setId(vegetables.getId());
		vegetablesResponse.setVegetablesName(vegetables.getVegetablesName());
		vegetablesResponse.setQuantity(vegetables.getQuantity());
		vegetablesResponse.setPrice(vegetables.getPrice());
		try {
			byte[] imageBytes = vegetables.getImage().getBytes(1, (int) vegetables.getImage().length());
			String vegetablesImageUrl = "/vegetables/GetById?id=" + vegetables.getId();
			vegetablesResponse.setImage(vegetablesImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for vegetables id: " + vegetables.getId(), e);

		}

		return vegetablesResponse;

	}

	public Vegetables getImageViewById(Integer id) {
		Optional<Vegetables> optionalVegetables = vegetablesRepository.findById(id);
		if (optionalVegetables.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalVegetables.get();
		}
	}
	@Transactional
	public List<VegetablesResponse> getVegetablesByVegetablesName(String vegetablesName) {
		List<Vegetables> vegetables = vegetablesRepository.findByVegetablesName(vegetablesName);
		List<VegetablesResponse> vegetablesResponses = new ArrayList<>();

		vegetables.forEach(e -> {
			VegetablesResponse vegetablesResponse = new VegetablesResponse();
			vegetablesResponse.setId(e.getId());
			vegetablesResponse.setVegetablesName(e.getVegetablesName());
			vegetablesResponse.setQuantity(e.getQuantity());
			vegetablesResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/vegetables/GetByName?id=" + e.getId(); // Custom URL

				vegetablesResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			vegetablesResponses.add(vegetablesResponse);
		});
		return vegetablesResponses;
	}
	
	public String updatevegetables(long id, String vegetablesName, int quantity, double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<Vegetables> optionalExistingVegetables = vegetablesRepository.findById(id);

		if (optionalExistingVegetables.isEmpty()) {

			throw new ProductsIsNotFoundException("Vegetables with ID " + id + " not found");

		}

		Vegetables existingVegetables = optionalExistingVegetables.get();

		existingVegetables.setVegetablesName(vegetablesName);		
		existingVegetables.setQuantity(quantity);
		existingVegetables.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingVegetables.setImage(blob);
		}
		vegetablesRepository.save(existingVegetables);
		return "Vegetables with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteVegetables(long id) {
        Optional<Vegetables> optionalVegetables = vegetablesRepository.findById(id);

        if (optionalVegetables.isPresent()) {
            vegetablesRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("Vegetables not found with this id " + id); 
        }
    }

}
    