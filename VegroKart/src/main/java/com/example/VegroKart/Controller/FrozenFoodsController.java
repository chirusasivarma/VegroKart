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

import com.example.VegroKart.Entity.FrozenFoods;
import com.example.VegroKart.Entity.FrozenFoodsReponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.FrozenFoodsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/frozen")
public class FrozenFoodsController {
	
	@Autowired
	public FrozenFoodsService frozenFoodsService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<FrozenFoodsReponse>> saveFrozenFoods(
	        @RequestParam("file") @Valid MultipartFile file,
	        @RequestParam("name") String name,
	        @RequestParam("quantity") String quantity,
	        @RequestParam("price") String price,
	        HttpServletRequest request) throws IOException, SerialException, SQLException {

	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Image file is required");
	    }

	    FrozenFoods frozenFoods = frozenFoodsService.saveFrozenFoods(request, file, name, quantity, price);

	    FrozenFoodsReponse frozenFoodsResponse = new FrozenFoodsReponse();
	    frozenFoodsResponse.setId(frozenFoods.getId());
	    frozenFoodsResponse.setName(frozenFoods.getName());
	    frozenFoodsResponse.setQuantity(frozenFoods.getQuantity());

	    if (file != null && !file.isEmpty()) {
	        frozenFoodsResponse.setImage("Image is present");
	    } else {
	        frozenFoodsResponse.setImage("");
	    }

	    frozenFoodsResponse.setPrice(frozenFoods.getPrice());

	    ResponseBody<FrozenFoodsReponse> frozenFoodsBody = new ResponseBody<>();
	    frozenFoodsBody.setStatusCode(HttpStatus.OK.value());
	    frozenFoodsBody.setStatus("SUCCESS");
	    frozenFoodsBody.setData(frozenFoodsResponse);

	    return ResponseEntity.ok(frozenFoodsBody);
	}
	
	@GetMapping("/getAll")

	public ResponseEntity<?> getAllFrozenFoods() {
		List<FrozenFoodsReponse> list = frozenFoodsService.getAllFrozenFoods();

		ResponseBody<List<FrozenFoodsReponse>> frozenFoodsBody = new ResponseBody<>();
		frozenFoodsBody.setStatusCode(HttpStatus.OK.value());
		frozenFoodsBody.setStatus("SUCCESS");
		frozenFoodsBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(frozenFoodsBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<FrozenFoodsReponse>> getFrozenFoodsById(@PathVariable("id") long id) {
		FrozenFoodsReponse frozenFoodsResponse = frozenFoodsService.getFrozenFoodsById(id);
        ResponseBody<FrozenFoodsReponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(frozenFoodsResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayFrozenFoodsImage(@RequestParam("id") Integer id) throws SQLException {
		FrozenFoods frozenFoods = frozenFoodsService.getImageViewById(id);
		if (frozenFoods != null) {
			byte[] imageBytes = frozenFoods.getImage().getBytes(1, (int) frozenFoods.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{name}")
	public ResponseEntity<ResponseBody<List<FrozenFoodsReponse>>> getFrozenFoodsByName(
			@PathVariable String name) {
		List<FrozenFoodsReponse> frozenFoodsResponses = frozenFoodsService
				.getFrozenFoodsByName(name);
		ResponseBody<List<FrozenFoodsReponse>> frozenFoodsBody = new ResponseBody<>();

		if (frozenFoodsResponses.isEmpty()) {
			frozenFoodsBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			frozenFoodsBody.setStatus("No frozenFoods available with this given name.");

		} else {

			frozenFoodsBody.setStatusCode(HttpStatus.OK.value());
			frozenFoodsBody.setStatus("SUCCESS");
			frozenFoodsBody.setData(frozenFoodsResponses);
		}
		return ResponseEntity.ok(frozenFoodsBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateFrozenFoods(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") String quantity, @RequestParam("price") String price) throws IOException, SerialException, SQLException {
		String message = frozenFoodsService.updatefrozenFoods(id, name, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteFrozenFoodsById(@PathVariable long id) {
		frozenFoodsService.deleteFrozenFoods(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("FrozenFoods with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}





