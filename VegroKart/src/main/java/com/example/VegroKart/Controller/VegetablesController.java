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

import com.example.VegroKart.Entity.Vegetables;
import com.example.VegroKart.Entity.VegetablesResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.VegetablesService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vegetables")
public class VegetablesController {
	@Autowired
	public VegetablesService vegetablesService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<Vegetables>> saveFruits(
			@RequestParam("file") MultipartFile file, @RequestParam("vegetablesName") String vegetablesName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price,HttpServletRequest request)
			throws IOException, SerialException, SQLException {

		Vegetables vegetables = vegetablesService.saveVegetables(request, file, vegetablesName,
				quantity, price);
		ResponseBody<Vegetables> vegetablesbody = new ResponseBody<Vegetables>();
		vegetablesbody.setStatusCode(HttpStatus.OK.value());
		vegetablesbody.setStatus("SUCCESS");
		vegetablesbody.setData(vegetables);
		return ResponseEntity.ok(vegetablesbody);

	}
	@GetMapping("/allVegetables")

	public ResponseEntity<?> getAllVegetables() {
		List<VegetablesResponse> list = vegetablesService.getAllVegetables();

		ResponseBody<List<VegetablesResponse>> vegetablesBody = new ResponseBody<>();
		vegetablesBody.setStatusCode(HttpStatus.OK.value());
		vegetablesBody.setStatus("SUCCESS");
		vegetablesBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(vegetablesBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<VegetablesResponse>> getVegetablesById(@PathVariable("id") long id) {
		VegetablesResponse vegetablesResponse = vegetablesService.getVegetablesById(id);
        ResponseBody<VegetablesResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(vegetablesResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayVegetablesImage(@RequestParam("id") Integer id) throws SQLException {
		Vegetables vegetables = vegetablesService.getImageViewById(id);
		if (vegetables != null) {
			byte[] imageBytes = vegetables.getImage().getBytes(1, (int) vegetables.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{vegetablesName}")
	public ResponseEntity<ResponseBody<List<VegetablesResponse>>> getVegetablesByVegetablesName(
			@PathVariable String vegetablesName) {
		List<VegetablesResponse> vegetablesResponses = vegetablesService
				.getVegetablesByVegetablesName(vegetablesName);
		ResponseBody<List<VegetablesResponse>> vegetablesBody = new ResponseBody<>();

		if (vegetablesResponses.isEmpty()) {
			vegetablesBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			vegetablesBody.setStatus("No vegetables available with this given name.");

		} else {

			vegetablesBody.setStatusCode(HttpStatus.OK.value());
			vegetablesBody.setStatus("SUCCESS");
			vegetablesBody.setData(vegetablesResponses);
		}
		return ResponseEntity.ok(vegetablesBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateFruits(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("vegetablesName") String vegetablesName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price) throws IOException, SerialException, SQLException {
		String message = vegetablesService.updatevegetables(id, vegetablesName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteVegetablesById(@PathVariable long id) {
		vegetablesService.deleteVegetables(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("vegetables with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}



