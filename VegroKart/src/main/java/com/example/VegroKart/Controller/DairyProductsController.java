package com.example.VegroKart.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.DairyProducts;
import com.example.VegroKart.Entity.DairyProductsResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.DairyProductsService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dairy")
public class DairyProductsController {
	@Autowired
	public DairyProductsService dairyProductsService;

	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<DairyProducts>> saveFruits(
			@RequestParam("file") MultipartFile file, @RequestParam("productName") String productName,
			@RequestParam("quantity") int quantity, @RequestParam("price") double price,HttpServletRequest request)
			throws IOException, SerialException, SQLException {

		DairyProducts dairyProducts = dairyProductsService.saveDairyProducts(request, file, productName, quantity, price);
				
		ResponseBody<DairyProducts> dairyProductsbody = new ResponseBody<DairyProducts>();
		dairyProductsbody.setStatusCode(HttpStatus.OK.value());
		dairyProductsbody.setStatus("SUCCESS");
		dairyProductsbody.setData(dairyProducts);
		return ResponseEntity.ok(dairyProductsbody);

	}
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<DairyProductsResponse>> getDairyProductsById(@PathVariable("id") long id) {
		DairyProductsResponse dairyProductsResponse = dairyProductsService.getDairyProductsById(id);
        ResponseBody<DairyProductsResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(dairyProductsResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	

}
