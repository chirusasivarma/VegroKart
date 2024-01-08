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

import com.example.VegroKart.Entity.PersonalCare;
import com.example.VegroKart.Entity.PersonalCareResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.PersonalCareService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/personalCare")
public class PersonalCareController {
	
	@Autowired
	public PersonalCareService personalCareService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<PersonalCareResponse>> savePersonalCare(
	        @RequestParam("file") @Valid MultipartFile file,
	        @RequestParam("name") String name,
	        @RequestParam("quantity") String quantity,
	        @RequestParam("price") Double price,
	        HttpServletRequest request) throws IOException, SerialException, SQLException {

	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Image file is required");
	    }

	    PersonalCare personalCare = personalCareService.savePersonalCare(request, file, name, quantity, price);

	    PersonalCareResponse personalCareResponse = new PersonalCareResponse();
	    personalCareResponse.setId(personalCare.getId());
	    personalCareResponse.setName(personalCare.getName());
	    personalCareResponse.setQuantity(personalCare.getQuantity());

	    if (file != null && !file.isEmpty()) {
	        personalCareResponse.setImage("Image is present");
	    } else {
	        personalCareResponse.setImage("");
	    }

	    personalCareResponse.setPrice(personalCare.getPrice());

	    ResponseBody<PersonalCareResponse> personalCareBody = new ResponseBody<>();
	    personalCareBody.setStatusCode(HttpStatus.OK.value());
	    personalCareBody.setStatus("SUCCESS");
	    personalCareBody.setData(personalCareResponse);

	    return ResponseEntity.ok(personalCareBody);
	}

	@GetMapping("/getAll")

	public ResponseEntity<?> getAllPersonalCarebody() {
		List<PersonalCareResponse> list = personalCareService.getAllPersonalCare();

		ResponseBody<List<PersonalCareResponse>> personalCareBody = new ResponseBody<>();
		personalCareBody.setStatusCode(HttpStatus.OK.value());
		personalCareBody.setStatus("SUCCESS");
		personalCareBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(personalCareBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<PersonalCareResponse>> getPersonalCareResponseById(@PathVariable("id") long id) {
		PersonalCareResponse personalCareResponse = personalCareService.getPersonalCareById(id);
        ResponseBody<PersonalCareResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(personalCareResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayPersonalCareImage(@RequestParam("id") Integer id) throws SQLException {
		PersonalCare personalCare = personalCareService.getImageViewById(id);
		if (personalCare != null) {
			byte[] imageBytes = personalCare.getImage().getBytes(1, (int) personalCare.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{name}")
	public ResponseEntity<ResponseBody<List<PersonalCareResponse>>> getPersonalCareByName(
			@PathVariable String name) {
		List<PersonalCareResponse> personalCareResponses = personalCareService.getFrozenFoodsByName(name);
			
		ResponseBody<List<PersonalCareResponse>> personalCareBody = new ResponseBody<>();

		if (personalCareResponses.isEmpty()) {
			personalCareBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			personalCareBody.setStatus("No personalCare available with this given name.");

		} else {

			personalCareBody.setStatusCode(HttpStatus.OK.value());
			personalCareBody.setStatus("SUCCESS");
			personalCareBody.setData(personalCareResponses);
		}
		return ResponseEntity.ok(personalCareBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updatePersonalCare(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") String quantity, @RequestParam("price") Double price) throws IOException, SerialException, SQLException {
		String message = personalCareService.updatepersonalCare(id, name, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deletePersonalCareById(@PathVariable long id) {
		personalCareService.deletePersonalCare(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("PersonalCare with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}
