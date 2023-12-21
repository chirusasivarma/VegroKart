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

import com.example.VegroKart.Entity.Snacks;
import com.example.VegroKart.Entity.SnacksResponse;
import com.example.VegroKart.Exception.SnacksIsNotFoundException;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.SnacksService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/snacks")
public class SnacksController {
	
	@Autowired
	public SnacksService snacksService;

	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<Snacks>> saveFruits(
			@RequestParam("file") MultipartFile file, @RequestParam("snacksName") String snacksName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price,HttpServletRequest request)
			throws IOException, SerialException, SQLException {

		Snacks snacks = snacksService.saveFruits(request, file, snacksName,quantity, price);
				
		ResponseBody<Snacks> snacksbody = new ResponseBody<Snacks>();
		snacksbody.setStatusCode(HttpStatus.OK.value());
		snacksbody.setStatus("SUCCESS");
		snacksbody.setData(snacks);
		return ResponseEntity.ok(snacksbody);

	}
	
	@GetMapping("/allSnacks")
	public ResponseEntity<?> getAllSnacks() {
		List<SnacksResponse> list = snacksService.getAllSnacks();

		ResponseBody<List<SnacksResponse>> snacksBody = new ResponseBody<>();
		snacksBody.setStatusCode(HttpStatus.OK.value());
		snacksBody.setStatus("SUCCESS");
		snacksBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(snacksBody);

	}
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<SnacksResponse>> getSnacksById(@PathVariable("id") long id) {
		SnacksResponse snacksResponse = snacksService.getSnacksById(id);
        ResponseBody<SnacksResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(snacksResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayFruitImage(@RequestParam("id") Integer id) throws SQLException {
		Snacks snacks = snacksService.getImageViewById(id);
		if (snacks != null) {
			byte[] imageBytes = snacks.getImage().getBytes(1, (int) snacks.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	
	@GetMapping("/search/{snacksName}")
	public ResponseEntity<ResponseBody<List<SnacksResponse>>> getSnacksBySnacksName(
			@PathVariable String snacksName) {
		List<SnacksResponse> snacksResponse = snacksService
				.getSnacksBySnacksName(snacksName);
		ResponseBody<List<SnacksResponse>> snacksBody = new ResponseBody<>();

		if (snacksResponse.isEmpty()) {
			snacksBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			snacksBody.setStatus("No Snacks available with this given name.");

		} else {

			snacksBody.setStatusCode(HttpStatus.OK.value());
			snacksBody.setStatus("SUCCESS");
			snacksBody.setData(snacksResponse);
		}
		return ResponseEntity.ok(snacksBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateSnacks(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("snacksName") String snacksName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price) throws IOException, SerialException, SQLException {
		String message = snacksService.updatesnacks(id, snacksName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	
	public ResponseEntity<ResponseBody<String>> deleteSnacksById(@PathVariable long id) {
		snacksService.deleteSnacks(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("Snacks with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}





