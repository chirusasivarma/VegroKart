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

import com.example.VegroKart.Entity.Beverages;
import com.example.VegroKart.Entity.BeveragesResponse;
import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.FruitsResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.BeveragesService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/beverages")
public class BeveragesController {
	
	@Autowired
	public BeveragesService beveragesService;

	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<Beverages>> saveBeverages(
			@RequestParam("file") MultipartFile file, @RequestParam("beveragesName") String beveragesName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price,HttpServletRequest request)
			throws IOException, SerialException, SQLException {

		Beverages beverages = beveragesService.saveBeverages(request, file, beveragesName,quantity, price);
				
		ResponseBody<Beverages> beveragesbody = new ResponseBody<Beverages>();
		beveragesbody.setStatusCode(HttpStatus.OK.value());
		beveragesbody.setStatus("SUCCESS");
		beveragesbody.setData(beverages);
		return ResponseEntity.ok(beveragesbody);

	}
	@GetMapping("/allBeverages")

	public ResponseEntity<?> getAllBeverages() {
		List<BeveragesResponse> list = beveragesService.getAllBeverages();
		
		ResponseBody<List<BeveragesResponse>> beveragesBody = new ResponseBody<>();
		beveragesBody.setStatusCode(HttpStatus.OK.value());
		beveragesBody.setStatus("SUCCESS");
		beveragesBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(beveragesBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<BeveragesResponse>> getBeveragesById(@PathVariable("id") long id) {
		BeveragesResponse beveragesResponse = beveragesService.getBeveragesById(id);
        ResponseBody<BeveragesResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(beveragesResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayBeveragesImage(@RequestParam("id") Integer id) throws SQLException {
		Beverages beverages = beveragesService.getImageViewById(id);
		if (beverages != null) {
			byte[] imageBytes = beverages.getImage().getBytes(1, (int) beverages.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{beveragesName}")
	public ResponseEntity<ResponseBody<List<BeveragesResponse>>> getbeveragesByBeveragesName(
			@PathVariable String beveragesName) {
		List<BeveragesResponse> beveragesResponses = beveragesService.getBeveragesByBeveragesName(beveragesName);
				
		ResponseBody<List<BeveragesResponse>> beveragesBody = new ResponseBody<>();

		if (beveragesResponses.isEmpty()) {
			beveragesBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			beveragesBody.setStatus("No Beverages available with this given name.");

		} else {

			beveragesBody.setStatusCode(HttpStatus.OK.value());
			beveragesBody.setStatus("SUCCESS");
			beveragesBody.setData(beveragesResponses);
		}
		return ResponseEntity.ok(beveragesBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateBeverages(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("beveragesName") String beveragesName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price) throws IOException, SerialException, SQLException {
		String message = beveragesService.updatebeverages(id, beveragesName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteBevereagesById(@PathVariable long id) {
		beveragesService.deleteBeverages(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("Bevereages with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}

