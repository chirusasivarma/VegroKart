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

import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.FruitsResponse;
import com.example.VegroKart.Entity.Meat;
import com.example.VegroKart.Entity.MeatResponse;
import com.example.VegroKart.Exception.FruitsIsNotFoundException;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.MeatRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MeatService {
	@Autowired
	private MeatRepository meatRepository;

	public Meat saveMeat(HttpServletRequest request, MultipartFile file, String meatName,
			String quantity, Double price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		Meat meat = new Meat();
		meat.setImage(blob);
		meat.setMeatName(meatName);
		meat.setQuantity(quantity);
		meat.setPrice(price);
		
		return meatRepository.save(meat);
	}
	
	@Transactional
	public List<MeatResponse> getAllMeat() {
		List<Meat> meat = meatRepository.findAll();

		List<MeatResponse> meatResponses = new ArrayList<>();

		meat.forEach(e -> {

			MeatResponse meatResponse = new MeatResponse();
			meatResponse.setId(e.getId());
			meatResponse.setMeatName(e.getMeatName());
			meatResponse.setQuantity(e.getQuantity());
			meatResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/meat/displayImage?id=" + e.getId(); // Custom URL

				meatResponse.setImage(customImageUrl);
				meatResponses.add(meatResponse);
			
		});
		return meatResponses;

	}
	@Transactional
	public MeatResponse getMeatById(long id) {
	
		Optional<Meat> optionalmeat = meatRepository.findById(id);
		if (optionalmeat.isEmpty()) {
			throw new ProductsIsNotFoundException("Meat with ID " + id + " not found");
		}

		Meat meat = optionalmeat.get();
		MeatResponse meatResponse = MeatResponse(meat);
		return meatResponse;
	}

	private MeatResponse MeatResponse(Meat meat) {
		MeatResponse meatResponse = new MeatResponse();
		meatResponse.setId(meat.getId());
		meatResponse.setMeatName(meat.getMeatName());
		meatResponse.setQuantity(meat.getQuantity());
		meatResponse.setPrice(meat.getPrice());
		try {
			byte[] imageBytes = meat.getImage().getBytes(1, (int) meat.getImage().length());
			String meatImageUrl = "/meat/displayImage?id=" + meat.getId();
			meatResponse.setImage(meatImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for meat id: " + meat.getId(), e);

		}

		return meatResponse;

	}
	public Meat getImageViewById(Integer id) {
		Optional<Meat> optionalMeat = meatRepository.findById(id);
		if (optionalMeat.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalMeat.get();
		}
	}
	
	@Transactional
	public List<MeatResponse> getMeatByMeatName(String meatName) {
		List<Meat> meat = meatRepository.findByMeatName(meatName);
		List<MeatResponse> meatResponses = new ArrayList<>();

		meat.forEach(e -> {
			MeatResponse meatResponse = new MeatResponse();
			meatResponse.setId(e.getId());
			meatResponse.setMeatName(meatName);
			meatResponse.setQuantity(e.getQuantity());
			meatResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/meat/GetByName?id=" + e.getId(); // Custom URL

				meatResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			meatResponses.add(meatResponse);
		});
		return meatResponses;
	}
public String updatemeat(long id, String meatName, String quantity, Double price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<Meat> optionalExistingMeat = meatRepository.findById(id);

		if (optionalExistingMeat.isEmpty()) {

			throw new ProductsIsNotFoundException("Meat with ID " + id + " not found");

		}

		Meat existingMeat = optionalExistingMeat.get();

		existingMeat.setMeatName(meatName);		
		existingMeat.setQuantity(quantity);
		existingMeat.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingMeat.setImage(blob);
		}
		meatRepository.save(existingMeat);
		return "Meat with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deleteMeat(long id) {
        Optional<Meat> optionalMeat = meatRepository.findById(id);

        if (optionalMeat.isPresent()) {
            meatRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("Meat not found with this id " + id); 
        }
    }

}








