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

import com.example.VegroKart.Entity.Snacks;
import com.example.VegroKart.Entity.SnacksResponse;
import com.example.VegroKart.Exception.SnacksIsNotFoundException;
import com.example.VegroKart.Repository.SnacksRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SnacksService {
	
	@Autowired
	private SnacksRepository snacksRepository;

	public Snacks saveFruits(HttpServletRequest request, MultipartFile file, String snacksName, int quantity,
			double price)throws IOException, SerialException, SQLException {
			
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		Snacks snacks = new Snacks();
		snacks.setImage(blob);
		snacks.setSnacksName(snacksName);
		snacks.setQuantity(quantity);
		snacks.setPrice(price);
		
		return snacksRepository.save(snacks);
	}

	@Transactional
	public List<SnacksResponse> getAllSnacks() {
			List<Snacks> snacks = snacksRepository.findAll();

			List<SnacksResponse> snacksResponses = new ArrayList<>();

			snacks.forEach(e -> {

				SnacksResponse snacksResponse = new SnacksResponse();
					snacksResponse.setId(e.getId());
					snacksResponse.setSnacksName(e.getSnacksName());
					snacksResponse.setQuantity(e.getQuantity());
					snacksResponse.setPrice(e.getPrice());
					
					try {

						byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String customImageUrl = "/snacks/displayImage?id=" + e.getId(); // Custom URL

					snacksResponse.setImage(customImageUrl);
					snacksResponses.add(snacksResponse);
				
			});
			return snacksResponses;

		}
	public Snacks getImageViewById(Integer id) {
		Optional<Snacks> optionalSnacks = snacksRepository.findById(id);
		if (optionalSnacks.isEmpty()) {
			throw new SnacksIsNotFoundException("image is not uploaded...");
		} else {
			return optionalSnacks.get();
		}
	}

	@Transactional
	public List<SnacksResponse> getSnacksBySnacksName(String snacksName) {
		List<Snacks> snacks = snacksRepository.findBySnacksName(snacksName);
		List<SnacksResponse> snacksResponses = new ArrayList<>();

		snacks.forEach(e -> {
			SnacksResponse snacksResponse = new SnacksResponse();
			snacksResponse.setId(e.getId());
			snacksResponse.setSnacksName(e.getSnacksName());
			snacksResponse.setQuantity(e.getQuantity());
			snacksResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/snacks/GetByName?id=" + e.getId(); // Custom URL

				snacksResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			snacksResponses.add(snacksResponse);
		});
		return snacksResponses;
	}

	public String updatesnacks(long id, String snacksName, int quantity, double price, MultipartFile file) throws IOException, SerialException, SQLException{
		
			
			Optional<Snacks> optionalExistingSnacks = snacksRepository.findById(id);

			if (optionalExistingSnacks.isEmpty()) {
				throw new SnacksIsNotFoundException("Snacks with ID " + id + " not found");
			}
			Snacks existingSnacks = optionalExistingSnacks.get();

			existingSnacks.setSnacksName(snacksName);		
			existingSnacks.setQuantity(quantity);
			existingSnacks.setPrice(price);
			if (file != null && !file.isEmpty()) {

				byte[] bytes = file.getBytes();

				Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

				existingSnacks.setImage(blob);
			}
			snacksRepository.save(existingSnacks);
			return "Snacks with ID " + id + " updated successfully.";
		}

		@Transactional
		public SnacksResponse getSnacksById(long id) {
			Optional<Snacks> optionalsnacks = snacksRepository.findById(id);
			if (optionalsnacks.isEmpty()) {
				throw new SnacksIsNotFoundException("snacks with ID " + id + " not found");
			}

			Snacks snacks = optionalsnacks.get();
			SnacksResponse snacksResponse = SnacksResponse(snacks);
			return snacksResponse;
		}

		private SnacksResponse SnacksResponse(Snacks snacks) {
			SnacksResponse snacksResponse = new SnacksResponse();
			snacksResponse.setId(snacks.getId());
			snacksResponse.setSnacksName(snacks.getSnacksName());
			snacksResponse.setQuantity(snacks.getQuantity());
			snacksResponse.setPrice(snacks.getPrice());
			try {
				byte[] imageBytes = snacks.getImage().getBytes(1, (int) snacks.getImage().length());
				String snacksImageUrl = "/snacks/GetById?id=" + snacks.getId();
				snacksResponse.setImage(snacksImageUrl);
			} catch (SQLException e) {
				log.error("Error while processing image for fruits id: " + snacks.getId(), e);

			}

			return snacksResponse;

		}
		@Transactional
	    public void deleteSnacks(long id) {
	        Optional<Snacks> optionalSnacks = snacksRepository.findById(id);

	        if (optionalSnacks.isPresent()) {
	            snacksRepository.deleteById(id);
	           // Deletion successful
	        } else {
	           throw new SnacksIsNotFoundException("Snacks not found with this id " + id); // Snacks with the given ID not found
	        }
	    }

	    // Other methods in your service...
	}




		
			
				
	
	
			