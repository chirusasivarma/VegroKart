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

import com.example.VegroKart.Entity.BabyItems;
import com.example.VegroKart.Entity.BabyItemsResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.BabyItemsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/babyItems")
public class BabyItemsController {
	
	@Autowired
	public BabyItemsService babyItemsService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<BabyItemsResponse>> saveBabyItems(
	        @RequestParam("file") @Valid MultipartFile file,
	        @RequestParam("name") String name,
	        @RequestParam("quantity") String quantity,
	        @RequestParam("price") Double price,
	        HttpServletRequest request) throws IOException, SerialException, SQLException {

	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Image file is required");
	    }

	    BabyItems babyItems = babyItemsService.saveBabyItems(request, file, name, quantity, price);

	    BabyItemsResponse babyItemsResponse = new BabyItemsResponse();
	    babyItemsResponse.setId(babyItems.getId());
	    babyItemsResponse.setName(babyItems.getName());
	    babyItemsResponse.setQuantity(babyItems.getQuantity());

	    if (file != null && !file.isEmpty()) {
	        babyItemsResponse.setImage("Image is present");
	    } else {
	        babyItemsResponse.setImage("");
	    }

	    babyItemsResponse.setPrice(babyItems.getPrice());

	    ResponseBody<BabyItemsResponse> babyItemsBody = new ResponseBody<>();
	    babyItemsBody.setStatusCode(HttpStatus.OK.value());
	    babyItemsBody.setStatus("SUCCESS");
	    babyItemsBody.setData(babyItemsResponse);

	    return ResponseEntity.ok(babyItemsBody);
	}

	@GetMapping("/allBabyItems")

	public ResponseEntity<?> getAllBabyItems() {
		List<BabyItemsResponse> list = babyItemsService.getAllBabyItems();

		ResponseBody<List<BabyItemsResponse>> babyItemsBody = new ResponseBody<>();
		babyItemsBody.setStatusCode(HttpStatus.OK.value());
		babyItemsBody.setStatus("SUCCESS");
		babyItemsBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(babyItemsBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<BabyItemsResponse>> getBabyItemsById(@PathVariable("id") long id) {
		BabyItemsResponse babyItemsResponse = babyItemsService.getBabyItemsById(id);
        ResponseBody<BabyItemsResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(babyItemsResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayBabyItemsImage(@RequestParam("id") Integer id) throws SQLException {
		BabyItems babyItems = babyItemsService.getImageViewById(id);
		if (babyItems != null) {
			byte[] imageBytes = babyItems.getImage().getBytes(1, (int) babyItems.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{name}")
	public ResponseEntity<ResponseBody<List<BabyItemsResponse>>> getBabyItemsByName(
			@PathVariable String name) {
		List<BabyItemsResponse> babyItemsResponses = babyItemsService
				.getBabyItemsByName(name);
		ResponseBody<List<BabyItemsResponse>> babyItemsBody = new ResponseBody<>();

		if (babyItemsResponses.isEmpty()) {
			babyItemsBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			babyItemsBody.setStatus("No BabyItems available with this given name.");

		} else {

			babyItemsBody.setStatusCode(HttpStatus.OK.value());
			babyItemsBody.setStatus("SUCCESS");
			babyItemsBody.setData(babyItemsResponses);
		}
		return ResponseEntity.ok(babyItemsBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateFruits(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") String quantity, @RequestParam("price") Double price) throws IOException, SerialException, SQLException {
		String message = babyItemsService.updatebabyItems(id, name, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteBabyItemsById(@PathVariable long id) {
		babyItemsService.deleteBabyItems(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("BabyItems with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}








