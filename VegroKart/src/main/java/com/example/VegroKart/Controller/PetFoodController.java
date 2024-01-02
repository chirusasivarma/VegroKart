package com.example.VegroKart.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.PetFood;
import com.example.VegroKart.Entity.PetFoodResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.PetFoodService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/petFood")
public class PetFoodController {
	
	@Autowired
	public PetFoodService petFoodService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<PetFoodResponse>> savePetFood(
	        @RequestParam("file") @Valid MultipartFile file,
	        @RequestParam("foodName") String foodName,
	        @RequestParam("quantity") String quantity,
	        @RequestParam("price") String price,
	        HttpServletRequest request) throws IOException, SerialException, SQLException {

	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Image file is required");
	    }

	    PetFood petFood = petFoodService.savePetFood(request, file, foodName, quantity, price);

	    PetFoodResponse petFoodResponse = new PetFoodResponse();
	    petFoodResponse.setId(petFood.getId());
	    petFoodResponse.setFoodName(petFood.getFoodName());
	    petFoodResponse.setQuantity(petFood.getQuantity());

	    if (file != null && !file.isEmpty()) {
	        petFoodResponse.setImage("Image is present");
	    } else {
	        petFoodResponse.setImage("");
	    }

	    petFoodResponse.setPrice(petFood.getPrice());

	    ResponseBody<PetFoodResponse> petFoodBody = new ResponseBody<>();
	    petFoodBody.setStatusCode(HttpStatus.OK.value());
	    petFoodBody.setStatus("SUCCESS");
	    petFoodBody.setData(petFoodResponse);

	    return ResponseEntity.ok(petFoodBody);
	}

	@GetMapping("/allPetFood")

	public ResponseEntity<?> getAllPetFood() {
		List<PetFoodResponse> list = petFoodService.getAllPetFood();

		ResponseBody<List<PetFoodResponse>> petFoodBody = new ResponseBody<>();
		petFoodBody.setStatusCode(HttpStatus.OK.value());
		petFoodBody.setStatus("SUCCESS");
		petFoodBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(petFoodBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<PetFoodResponse>> getPetFoodById(@PathVariable("id") long id) {
		PetFoodResponse petFoodResponse = petFoodService.getPetFoodById(id);
        ResponseBody<PetFoodResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(petFoodResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayPetFoodImage(@RequestParam("id") Integer id) throws SQLException {
		PetFood petFood = petFoodService.getImageViewById(id);
		if (petFood != null) {
			byte[] imageBytes = petFood.getImage().getBytes(1, (int) petFood.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{foodName}")
	public ResponseEntity<ResponseBody<List<PetFoodResponse>>> getPetFoodByFoodName(
			@PathVariable String foodName) {
		List<PetFoodResponse> petFoodResponses = petFoodService
				.getPetFoodByFoodName(foodName);
		ResponseBody<List<PetFoodResponse>> petFoodBody = new ResponseBody<>();

		if (petFoodResponses.isEmpty()) {
			petFoodBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			petFoodBody.setStatus("No PetFood available with this given name.");

		} else {

			petFoodBody.setStatusCode(HttpStatus.OK.value());
			petFoodBody.setStatus("SUCCESS");
			petFoodBody.setData(petFoodResponses);
		}
		return ResponseEntity.ok(petFoodBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateFruits(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("foodName") String foodName,
			@RequestParam("quantity") String quantity, @RequestParam("price") String price) throws IOException, SerialException, SQLException {
		String message = petFoodService.updatepetFood(id, foodName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deletePetFoodById(@PathVariable long id) {
		petFoodService.deletePetFood(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("PetFood with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}


