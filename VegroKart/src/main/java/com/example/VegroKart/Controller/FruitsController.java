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

import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.FruitsResponse;
import com.example.VegroKart.Exception.FruitsIsNotFoundException;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.FruitsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fruits")
public class FruitsController {
	
	@Autowired
	public FruitsService fruitsService;

	@PostMapping("/save")
    public ResponseEntity<ResponseBody<FruitsResponse>> saveFruits(
            @RequestParam("file") @Valid MultipartFile file,
            @RequestParam("fruitName") String fruitName,
            @RequestParam("quantity") String quantity,
            @RequestParam("price") String price,
            HttpServletRequest request) throws IOException, SerialException, SQLException {

        if (file == null || file.isEmpty()) {
            throw new FruitsIsNotFoundException("Image file is required");
        }

        Fruits fruits = fruitsService.saveFruits(request, file, fruitName, quantity, price);

        FruitsResponse fruitsResponse = new FruitsResponse();
        fruitsResponse.setId(fruits.getId());
        fruitsResponse.setFruitName(fruits.getFruitName());
        fruitsResponse.setQuantity(fruits.getQuantity());

        if (file != null && !file.isEmpty()) {
            fruitsResponse.setImage("Image uploaded");
        } else {
            fruitsResponse.setImage("");
        }

        fruitsResponse.setPrice(fruits.getPrice());

        ResponseBody<FruitsResponse> fruitsbody = new ResponseBody<>();
        fruitsbody.setStatusCode(HttpStatus.OK.value());
        fruitsbody.setStatus("SUCCESS");
        fruitsbody.setData(fruitsResponse);

        return ResponseEntity.ok(fruitsbody);
    }
	
	@GetMapping("/allFruits")

	public ResponseEntity<?> getAllFruits() {
		List<FruitsResponse> list = fruitsService.getAllFruits();

		ResponseBody<List<FruitsResponse>> fruitseBody = new ResponseBody<>();
		fruitseBody.setStatusCode(HttpStatus.OK.value());
		fruitseBody.setStatus("SUCCESS");
		fruitseBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(fruitseBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<FruitsResponse>> getFruitsById(@PathVariable("id") long id) {
		FruitsResponse fruitsResponse = fruitsService.getFruitsById(id);
        ResponseBody<FruitsResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(fruitsResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayFruitImage(@RequestParam("id") Integer id) throws SQLException {
		Fruits fruits = fruitsService.getImageViewById(id);
		if (fruits != null) {
			byte[] imageBytes = fruits.getImage().getBytes(1, (int) fruits.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{fruitsName}")
	public ResponseEntity<ResponseBody<List<FruitsResponse>>> getFruitsByFruitName(
			@PathVariable String fruitsName) {
		List<FruitsResponse> fruitsResponses = fruitsService
				.getFruitsByFruitName(fruitsName);
		ResponseBody<List<FruitsResponse>> fruitsBody = new ResponseBody<>();

		if (fruitsResponses.isEmpty()) {
			fruitsBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			fruitsBody.setStatus("No Fruits available with this given name.");

		} else {

			fruitsBody.setStatusCode(HttpStatus.OK.value());
			fruitsBody.setStatus("SUCCESS");
			fruitsBody.setData(fruitsResponses);
		}
		return ResponseEntity.ok(fruitsBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateFruits(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("fruitName") String fruitName,
			@RequestParam("quantity") String quantity, @RequestParam("price") String price) throws IOException, SerialException, SQLException {
		String message = fruitsService.updatefruits(id, fruitName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteFruitsById(@PathVariable long id) {
		fruitsService.deleteFruits(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("Fruits with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	            
	        } 
	}


