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

import com.example.VegroKart.Entity.SaucesAndOil;
import com.example.VegroKart.Entity.SaucesAndOilResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.SaucesAndOilService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sauces")
public class SaucesAndOilController {
	
	@Autowired
	public SaucesAndOilService saucesAndOilService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<SaucesAndOil>> saveSaucesAndOil(
			@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price,HttpServletRequest request)
			throws IOException, SerialException, SQLException {

		SaucesAndOil saucesAndOil = saucesAndOilService.saveSaucesAndOil(request, file, name,
				quantity, price);
		ResponseBody<SaucesAndOil> saucesAndOilbody = new ResponseBody<SaucesAndOil>();
		saucesAndOilbody.setStatusCode(HttpStatus.OK.value());
		saucesAndOilbody.setStatus("SUCCESS");
		saucesAndOilbody.setData(saucesAndOil);
		return ResponseEntity.ok(saucesAndOilbody);

	}
	@GetMapping("/getAll")

	public ResponseEntity<?> getAllSauceAndOil() {
		List<SaucesAndOilResponse> list = saucesAndOilService.getAllSaucesAndOil();

		ResponseBody<List<SaucesAndOilResponse>> sauceAndOilBody = new ResponseBody<>();
		sauceAndOilBody.setStatusCode(HttpStatus.OK.value());
		sauceAndOilBody.setStatus("SUCCESS");
		sauceAndOilBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(sauceAndOilBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<SaucesAndOilResponse>> getSauceAndOilById(@PathVariable("id") long id) {
		SaucesAndOilResponse sauceAndOilResponse = saucesAndOilService.getSaucesAndOilById(id);
        ResponseBody<SaucesAndOilResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(sauceAndOilResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displaySauceAndOilImage(@RequestParam("id") Integer id) throws SQLException {
		SaucesAndOil sauceAndOil = saucesAndOilService.getImageViewById(id);
		if (sauceAndOil != null) {
			byte[] imageBytes = sauceAndOil.getImage().getBytes(1, (int) sauceAndOil.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{name}")
	public ResponseEntity<ResponseBody<List<SaucesAndOilResponse>>> getSauceAndOilByName(
			@PathVariable String name) {
		List<SaucesAndOilResponse> sauceAndOilResponses = saucesAndOilService
				.getSaucesAndOilByName(name);
		ResponseBody<List<SaucesAndOilResponse>> sauceAndOilBody = new ResponseBody<>();

		if (sauceAndOilResponses.isEmpty()) {
			sauceAndOilBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			sauceAndOilBody.setStatus("No sauceAndOil available with this given name.");

		} else {

			sauceAndOilBody.setStatusCode(HttpStatus.OK.value());
			sauceAndOilBody.setStatus("SUCCESS");
			sauceAndOilBody.setData(sauceAndOilResponses);
		}
		return ResponseEntity.ok(sauceAndOilBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateSauceAndOil(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price) throws IOException, SerialException, SQLException {
		String message = saucesAndOilService.updatesaucesAndOil(id, name, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteSauceAndOilById(@PathVariable long id) {
		saucesAndOilService.deleteSaucesAndOil(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("SauceAndOil with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}








