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

import com.example.VegroKart.Entity.CannedGoods;
import com.example.VegroKart.Entity.CannedGoodsResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.CannedGoodsService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/goods")
public class CannedGoodsController {
	
	@Autowired
	public CannedGoodsService cannedGoodsService;

	@PostMapping("/save")
	public ResponseEntity<ResponseBody<CannedGoods>> saveCannedGoods(
			@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price,HttpServletRequest request)
			throws IOException, SerialException, SQLException {

		CannedGoods cannedGoods = cannedGoodsService.saveCannedGoods(request, file, name,
				quantity, price);
		ResponseBody<CannedGoods> cannedGoodsbody = new ResponseBody<CannedGoods>();
		cannedGoodsbody.setStatusCode(HttpStatus.OK.value());
		cannedGoodsbody.setStatus("SUCCESS");
		cannedGoodsbody.setData(cannedGoods);
		return ResponseEntity.ok(cannedGoodsbody);

	}
	@GetMapping("/allGoods")

	public ResponseEntity<?> getAllCannedGoods() {
		List<CannedGoodsResponse> list = cannedGoodsService.getAllCannedGoods();

		ResponseBody<List<CannedGoodsResponse>> cannedGoodsBody = new ResponseBody<>();
		cannedGoodsBody.setStatusCode(HttpStatus.OK.value());
		cannedGoodsBody.setStatus("SUCCESS");
		cannedGoodsBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(cannedGoodsBody);

	}
	
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<CannedGoodsResponse>> getCannedGoodsById(@PathVariable("id") long id) {
		CannedGoodsResponse cannedGoodsResponse = cannedGoodsService.getCannedGoodsById(id);
        ResponseBody<CannedGoodsResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(cannedGoodsResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayCannedGoodsImage(@RequestParam("id") Integer id) throws SQLException {
		CannedGoods cannedGoods = cannedGoodsService.getImageViewById(id);
		if (cannedGoods != null) {
			byte[] imageBytes = cannedGoods.getImage().getBytes(1, (int) cannedGoods.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{name}")
	public ResponseEntity<ResponseBody<List<CannedGoodsResponse>>> getCannedGoodsByName(
			@PathVariable String name) {
		List<CannedGoodsResponse> cannedGoodsResponses = cannedGoodsService
				.getCannedGoodsByName(name);
		ResponseBody<List<CannedGoodsResponse>> cannedGoodsBody = new ResponseBody<>();

		if (cannedGoodsResponses.isEmpty()) {
			cannedGoodsBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			cannedGoodsBody.setStatus("No CannedGoods available with this given name.");

		} else {

			cannedGoodsBody.setStatusCode(HttpStatus.OK.value());
			cannedGoodsBody.setStatus("SUCCESS");
			cannedGoodsBody.setData(cannedGoodsResponses);
		}
		return ResponseEntity.ok(cannedGoodsBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateFruits(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price) throws IOException, SerialException, SQLException {
		String message = cannedGoodsService.updatecannedGoods(id, name, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteCannedGoodsById(@PathVariable long id) {
		cannedGoodsService.deleteCannedGoods(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("CannedGoods with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}





