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

import com.example.VegroKart.Entity.DairyProducts;
import com.example.VegroKart.Entity.DairyProductsResponse;
import com.example.VegroKart.Entity.Snacks;
import com.example.VegroKart.Entity.SnacksResponse;
import com.example.VegroKart.Exception.DairyProductsIsNotFoundException;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Exception.SnacksIsNotFoundException;
import com.example.VegroKart.Repository.DairyProductsRepository;
import com.example.VegroKart.Repository.SnacksRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DairyProductsService {
	@Autowired
	private DairyProductsRepository dairyProductsRepository;

	public DairyProducts saveDairyProducts(HttpServletRequest request, MultipartFile file, String productName, int quantity,
			double price)throws IOException, SerialException, SQLException {
			
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		DairyProducts dairyProducts = new DairyProducts();
		dairyProducts.setImage(blob);
		dairyProducts.setProductName(productName);
		dairyProducts.setQuantity(quantity);
		dairyProducts.setPrice(price);
		
		return dairyProductsRepository.save(dairyProducts);
	}

	@Transactional
	public DairyProductsResponse getDairyProductsById(long id) {
		Optional<DairyProducts> optionaldairyProducts = dairyProductsRepository.findById(id);
		if (optionaldairyProducts.isEmpty()) {
			throw new DairyProductsIsNotFoundException("DairyProducts with ID " + id + " not found");
		}

		DairyProducts dairyProducts = optionaldairyProducts.get();
		DairyProductsResponse dairyProductsResponse = DairyProductsResponse(dairyProducts);
		return dairyProductsResponse;
	}

	private DairyProductsResponse DairyProductsResponse(DairyProducts dairyProducts) {
		DairyProductsResponse dairyProductsResponse = new DairyProductsResponse();
		dairyProductsResponse.setId(dairyProducts.getId());
		dairyProductsResponse.setProductName(dairyProducts.getProductName());
		dairyProductsResponse.setQuantity(dairyProducts.getQuantity());
		dairyProductsResponse.setPrice(dairyProducts.getPrice());
		try {
			byte[] imageBytes = dairyProducts.getImage().getBytes(1, (int) dairyProducts.getImage().length());
			String dairyProductsImageUrl = "/dairyProducts/GetById?id=" + dairyProducts.getId();
			dairyProductsResponse.setImage(dairyProductsImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for fruits id: " + dairyProducts.getId(), e);

		}

		return dairyProductsResponse;

	}
	@Transactional
	public List<DairyProductsResponse> getAllDairyProducts() {
			List<DairyProducts> dairyProducts = dairyProductsRepository.findAll();
			List<DairyProductsResponse> dairyProductsResponses = new ArrayList<>();

			dairyProducts.forEach(e -> {

				DairyProductsResponse dairyProductsResponse = new DairyProductsResponse();
				dairyProductsResponse.setId(e.getId());
				dairyProductsResponse.setProductName(e.getProductName());
				dairyProductsResponse.setQuantity(e.getQuantity());
				dairyProductsResponse.setPrice(e.getPrice());
					
					try {

						byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String customImageUrl = "/dairyProducts/displayImage?id=" + e.getId(); // Custom URL

					dairyProductsResponse.setImage(customImageUrl);
					dairyProductsResponses.add(dairyProductsResponse);
				
			});
			return dairyProductsResponses;
	}

	public DairyProducts getImageViewById(Integer id) {
		Optional<DairyProducts> optionalDairyProducts = dairyProductsRepository.findById(id);
		if (optionalDairyProducts.isEmpty()) {
			throw new DairyProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalDairyProducts.get();
		}
	}

	public String updatedairyProducts(long id, String productsName, int quantity, double price, MultipartFile file)  throws IOException, SerialException, SQLException{
		
		Optional<DairyProducts> optionalExistingDairyProducts = dairyProductsRepository.findById(id);

		if (optionalExistingDairyProducts.isEmpty()) {
			throw new ProductsIsNotFoundException("DairyProducts with ID " + id + " not found");
		}
		DairyProducts existingDairyProducts = optionalExistingDairyProducts.get();

		existingDairyProducts.setProductName(productsName);		
		existingDairyProducts.setQuantity(quantity);
		existingDairyProducts.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingDairyProducts.setImage(blob);
		}
		dairyProductsRepository.save(existingDairyProducts);
		return "DairyProducts with ID " + id + " updated successfully.";
	}

	@Transactional
	public void deleteDairyProducts(long id) {
	        Optional<DairyProducts> optionalDairyProducts = dairyProductsRepository.findById(id);

	        if (optionalDairyProducts.isPresent()) {
	        	dairyProductsRepository.deleteById(id);
	           // Deletion successful
	        } else {
	           throw new ProductsIsNotFoundException("DairyProducts not found with this id " + id); // Snacks with the given ID not found
	        }
	    }

}