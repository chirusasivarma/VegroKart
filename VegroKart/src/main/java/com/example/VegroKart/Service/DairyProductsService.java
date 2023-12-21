package com.example.VegroKart.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
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

}
		
			