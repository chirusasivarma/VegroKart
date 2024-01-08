package com.example.VegroKart.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;
import com.example.VegroKart.Entity.SaucesAndOilResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.SaucesAndOil;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.SaucesAndOilRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SaucesAndOilService {
	@Autowired
	private SaucesAndOilRepository saucesAndOilRepository;

	public SaucesAndOil saveSaucesAndOil(HttpServletRequest request, MultipartFile file, String name,
			String quantity, Double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		SaucesAndOil saucesAndOil = new SaucesAndOil();
		saucesAndOil.setImage(blob);
		saucesAndOil.setName(name);
		saucesAndOil.setQuantity(quantity);
		saucesAndOil.setPrice(price);
		
		return saucesAndOilRepository.save(saucesAndOil);
	}

	@Transactional
	public List<SaucesAndOilResponse> getAllSaucesAndOil() {
		List<SaucesAndOil> sauceAndoil = saucesAndOilRepository.findAll();

		List<SaucesAndOilResponse> sauceAndoilResponses = new ArrayList<>();

		sauceAndoil.forEach(e -> {

			SaucesAndOilResponse sauceAndoilResponse = new SaucesAndOilResponse();
			sauceAndoilResponse.setId(e.getId());
			sauceAndoilResponse.setName(e.getName());
			sauceAndoilResponse.setQuantity(e.getQuantity());
			sauceAndoilResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/sauceAndoilResponse/displayImage?id=" + e.getId(); // Custom URL

				sauceAndoilResponse.setImage(customImageUrl);
				sauceAndoilResponses.add(sauceAndoilResponse);
			
		});
		return sauceAndoilResponses;

	}

	@Transactional
	public SaucesAndOilResponse getSaucesAndOilById(long id) {
	
		Optional<SaucesAndOil> optionalsauceAndoil = saucesAndOilRepository.findById(id);
		if (optionalsauceAndoil.isEmpty()) {
			throw new ProductsIsNotFoundException("SaucesAndOil with ID " + id + " not found");
		}

		SaucesAndOil sauceAndoil = optionalsauceAndoil.get();
		SaucesAndOilResponse sauceAndoilResponse = SaucesAndOilResponse(sauceAndoil);
		return sauceAndoilResponse;
	}

	private SaucesAndOilResponse SaucesAndOilResponse(SaucesAndOil sauceAndoil) {
		SaucesAndOilResponse sauceAndoilResponse = new SaucesAndOilResponse();
		sauceAndoilResponse.setId(sauceAndoil.getId());
		sauceAndoilResponse.setName(sauceAndoil.getName());
		sauceAndoilResponse.setQuantity(sauceAndoil.getQuantity());
		sauceAndoilResponse.setPrice(sauceAndoil.getPrice());
		try {
			byte[] imageBytes = sauceAndoil.getImage().getBytes(1, (int) sauceAndoil.getImage().length());
			String sauceAndoilImageUrl = "/sauceAndoil/GetById?id=" + sauceAndoil.getId();
			sauceAndoilResponse.setImage(sauceAndoilImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for sauceAndoil id: " + sauceAndoil.getId(), e);

		}

		return sauceAndoilResponse;

	}

	public SaucesAndOil getImageViewById(Integer id) {
		Optional<SaucesAndOil> optionalSaucesAndOil = saucesAndOilRepository.findById(id);
		if (optionalSaucesAndOil.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalSaucesAndOil.get();
		}
	}
	@Transactional
	public List<SaucesAndOilResponse> getSaucesAndOilByName(String name) {
		List<SaucesAndOil> sauceAndoil = saucesAndOilRepository.findByName(name);
		List<SaucesAndOilResponse> sauceAndoilResponses = new ArrayList<>();

		sauceAndoil.forEach(e -> {
			SaucesAndOilResponse sauceAndoilResponse = new SaucesAndOilResponse();
			sauceAndoilResponse.setId(e.getId());
			sauceAndoilResponse.setName(e.getName());
			sauceAndoilResponse.setQuantity(e.getQuantity());
			sauceAndoilResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/sauceAndoilResponse/GetByName?id=" + e.getId(); // Custom URL

				sauceAndoilResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sauceAndoilResponses.add(sauceAndoilResponse);
		});
		return sauceAndoilResponses;
	}
	
	public String updatesaucesAndOil(long id, String name, String quantity, Double price, MultipartFile file) throws IOException, SerialException, SQLException {
	
		Optional<SaucesAndOil> optionalExistingSaucesAndOil = saucesAndOilRepository.findById(id);

		if (optionalExistingSaucesAndOil.isEmpty()) {

			throw new ProductsIsNotFoundException("SaucesAndOil with ID " + id + " not found");

		}

		SaucesAndOil existingSaucesAndOil = optionalExistingSaucesAndOil.get();

		existingSaucesAndOil.setName(name);		
		existingSaucesAndOil.setQuantity(quantity);
		existingSaucesAndOil.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingSaucesAndOil.setImage(blob);
		}
		saucesAndOilRepository.save(existingSaucesAndOil);
		return "SaucesAndOil with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteSaucesAndOil(long id) {
        Optional<SaucesAndOil> optionalSaucesAndOil = saucesAndOilRepository.findById(id);

        if (optionalSaucesAndOil.isPresent()) {
        	saucesAndOilRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("SaucesAndOil not found with this id " + id); 
        }
    }

}




