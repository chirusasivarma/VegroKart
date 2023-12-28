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

import com.example.VegroKart.Entity.BabyItems;
import com.example.VegroKart.Entity.BabyItemsResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.BabyItemsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BabyItemsService {
	
	@Autowired
	private BabyItemsRepository babyItemsRepository;

	public BabyItems saveBabyItems(HttpServletRequest request, MultipartFile file, String name,
			int quantity, double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		BabyItems babyItems = new BabyItems();
		babyItems.setImage(blob);
		babyItems.setName(name);		
		babyItems.setQuantity(quantity);
		babyItems.setPrice(price);
		
		return babyItemsRepository.save(babyItems);
	}

	@Transactional
	public List<BabyItemsResponse> getAllBabyItems() {
		List<BabyItems> babyItems = babyItemsRepository.findAll();

		List<BabyItemsResponse> babyItemsResponses = new ArrayList<>();

		babyItems.forEach(e -> {

			BabyItemsResponse babyItemsResponse = new BabyItemsResponse();
			babyItemsResponse.setId(e.getId());
			babyItemsResponse.setName(e.getName());
			babyItemsResponse.setQuantity(e.getQuantity());
			babyItemsResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/babyItems/displayImage?id=" + e.getId(); // Custom URL

				babyItemsResponse.setImage(customImageUrl);
				babyItemsResponses.add(babyItemsResponse);
			
		});
		return babyItemsResponses;

	}

	@Transactional
	public BabyItemsResponse getBabyItemsById(long id) {
	
		Optional<BabyItems> optionalbabyItems = babyItemsRepository.findById(id);
		if (optionalbabyItems.isEmpty()) {
			throw new ProductsIsNotFoundException("babyItems with ID " + id + " not found");
		}

		BabyItems babyItems = optionalbabyItems.get();
		BabyItemsResponse babyItemsResponse = BabyItemsResponse(babyItems);
		return babyItemsResponse;
	}

	private BabyItemsResponse BabyItemsResponse(BabyItems babyItems) {
		BabyItemsResponse babyItemsResponse = new BabyItemsResponse();
		babyItemsResponse.setId(babyItems.getId());
		babyItemsResponse.setName(babyItems.getName());
		babyItemsResponse.setQuantity(babyItems.getQuantity());
		babyItemsResponse.setPrice(babyItems.getPrice());
		try {
			byte[] imageBytes = babyItems.getImage().getBytes(1, (int) babyItems.getImage().length());
			String babyItemsImageUrl = "/babyItems/GetById?id=" + babyItems.getId();
			babyItemsResponse.setImage(babyItemsImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for babyItems id: " + babyItems.getId(), e);

		}

		return babyItemsResponse;

	}

	public BabyItems getImageViewById(Integer id) {
		Optional<BabyItems> optionalBabyItems = babyItemsRepository.findById(id);
		if (optionalBabyItems.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalBabyItems.get();
		}
	}
	@Transactional
	public List<BabyItemsResponse> getBabyItemsByName(String name) {
		List<BabyItems> babyItems = babyItemsRepository.findByName(name);
		List<BabyItemsResponse> babyItemsResponses = new ArrayList<>();

		babyItems.forEach(e -> {
			BabyItemsResponse babyItemsResponse = new BabyItemsResponse();
			babyItemsResponse.setId(e.getId());
			babyItemsResponse.setName(e.getName());
			babyItemsResponse.setQuantity(e.getQuantity());
			babyItemsResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/babyItems/GetByName?id=" + e.getId(); // Custom URL

				babyItemsResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			babyItemsResponses.add(babyItemsResponse);
		});
		return babyItemsResponses;
	}
	
	public String updatebabyItems(long id, String name, int quantity, double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<BabyItems> optionalExistingBabyItems = babyItemsRepository.findById(id);

		if (optionalExistingBabyItems.isEmpty()) {

			throw new ProductsIsNotFoundException("BabyItems with ID " + id + " not found");

		}

		BabyItems existingBabyItems = optionalExistingBabyItems.get();

		existingBabyItems.setName(name);		
		existingBabyItems.setQuantity(quantity);
		existingBabyItems.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingBabyItems.setImage(blob);
		}
		babyItemsRepository.save(existingBabyItems);
		return "BabyItems with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteBabyItems(long id) {
        Optional<BabyItems> optionalBabyItems = babyItemsRepository.findById(id);

        if (optionalBabyItems.isPresent()) {
        	babyItemsRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("babyItems not found with this id " + id); 
        }
    }

}
