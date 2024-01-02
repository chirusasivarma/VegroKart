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

import com.example.VegroKart.Entity.FrozenFoods;
import com.example.VegroKart.Entity.FrozenFoodsReponse;
import com.example.VegroKart.Entity.PersonalCare;
import com.example.VegroKart.Entity.PersonalCareResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.FrozenFoodsRepository;
import com.example.VegroKart.Repository.PersonalCareRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonalCareService {
	
	@Autowired
	private PersonalCareRepository personalCareRepository;

	public PersonalCare savePersonalCare(HttpServletRequest request, MultipartFile file, String name,
			String quantity, String price)
			throws IOException, SerialException, SQLException {
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

		PersonalCare personalCare = new PersonalCare();
		personalCare.setImage(blob);
		personalCare.setName(name);		
		personalCare.setQuantity(quantity);
		personalCare.setPrice(price);
		
		return personalCareRepository.save(personalCare);
	}

	@Transactional
	public List<PersonalCareResponse> getAllPersonalCare() {
		List<PersonalCare> personalCare = personalCareRepository.findAll();

		List<PersonalCareResponse> personalCareResponses = new ArrayList<>();

		personalCare.forEach(e -> {

			PersonalCareResponse personalCareResponse = new PersonalCareResponse();
			personalCareResponse.setId(e.getId());
			personalCareResponse.setName(e.getName());
			personalCareResponse.setQuantity(e.getQuantity());
			personalCareResponse.setPrice(e.getPrice());
				
				try {

					byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String customImageUrl = "/personalCare/displayImage?id=" + e.getId(); // Custom URL

				personalCareResponse.setImage(customImageUrl);
				personalCareResponses.add(personalCareResponse);
			
		});
		return personalCareResponses;

	}

	@Transactional
	public PersonalCareResponse getPersonalCareById(long id) {
		Optional<PersonalCare> optionalpersonalCare = personalCareRepository.findById(id);
		if (optionalpersonalCare.isEmpty()) {
			throw new ProductsIsNotFoundException("personalCare with ID " + id + " not found");
		}

		PersonalCare personalCare = optionalpersonalCare.get();
		PersonalCareResponse personalCareResponse = PersonalCareResponse(personalCare);
		return personalCareResponse;
	}

	private PersonalCareResponse PersonalCareResponse(PersonalCare personalCare) {
		PersonalCareResponse personalCareResponse = new PersonalCareResponse();
		personalCareResponse.setId(personalCare.getId());
		personalCareResponse.setName(personalCare.getName());
		personalCareResponse.setQuantity(personalCare.getQuantity());
		personalCareResponse.setPrice(personalCare.getPrice());
		try {
			byte[] imageBytes = personalCare.getImage().getBytes(1, (int) personalCare.getImage().length());
			String personalCareImageUrl = "/personalCare/GetById?id=" + personalCare.getId();
			personalCareResponse.setImage(personalCareImageUrl);
		} catch (SQLException e) {
			log.error("Error while processing image for personalCare id: " + personalCare.getId(), e);

		}

		return personalCareResponse;

	}

	public PersonalCare getImageViewById(Integer id) {
		Optional<PersonalCare> optionalPersonalCare = personalCareRepository.findById(id);
		if (optionalPersonalCare.isEmpty()) {
			throw new ProductsIsNotFoundException("image is not uploaded...");
		} else {
			return optionalPersonalCare.get();
		}
	}
	@Transactional
	public List<PersonalCareResponse> getFrozenFoodsByName(String name) {
		List<PersonalCare> personalCare = personalCareRepository.findByName(name);
		List<PersonalCareResponse> personalCareResponses = new ArrayList<>();

		personalCare.forEach(e -> {
			PersonalCareResponse personalCareResponse = new PersonalCareResponse();
			personalCareResponse.setId(e.getId());
			personalCareResponse.setName(e.getName());
			personalCareResponse.setQuantity(e.getQuantity());
			personalCareResponse.setPrice(e.getPrice());
			try {
				byte[] imageBytes = e.getImage().getBytes(1, (int) e.getImage().length());
				String customImageUrl = "/personalCare/GetByName?id=" + e.getId(); // Custom URL

				personalCareResponse.setImage(customImageUrl);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			personalCareResponses.add(personalCareResponse);
		});
		return personalCareResponses;
	}
	
	public String updatepersonalCare(long id, String name, String quantity, String price, MultipartFile file) throws IOException, SerialException, SQLException {
		
		Optional<PersonalCare> optionalExistingPersonalCare = personalCareRepository.findById(id);

		if (optionalExistingPersonalCare.isEmpty()) {

			throw new ProductsIsNotFoundException("PersonalCare with ID " + id + " not found");

		}

		PersonalCare existingPersonalCare = optionalExistingPersonalCare.get();

		existingPersonalCare.setName(name);		
		existingPersonalCare.setQuantity(quantity);
		existingPersonalCare.setPrice(price);
		if (file != null && !file.isEmpty()) {

			byte[] bytes = file.getBytes();

			Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

			existingPersonalCare.setImage(blob);
		}
		personalCareRepository.save(existingPersonalCare);
		return "PersonalCare with ID " + id + " updated successfully.";
	}

	@Transactional
    public void deletePersonalCare(long id) {
        Optional<PersonalCare> optionalPersonalCare = personalCareRepository.findById(id);

        if (optionalPersonalCare.isPresent()) {
        	personalCareRepository.deleteById(id);
           // Deletion successful
        } else {
           throw new ProductsIsNotFoundException("PersonalCare not found with this id " + id); 
        }
    }

}