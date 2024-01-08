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

import com.example.VegroKart.Entity.DairyProducts;
import com.example.VegroKart.Entity.DairyProductsResponse;
import com.example.VegroKart.Entity.SnacksResponse;
import com.example.VegroKart.Exception.DairyProductsIsNotFoundException;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.DairyProductsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dairy")
public class DairyProductsController {
	@Autowired
	public DairyProductsService dairyProductsService;

	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<DairyProductsResponse>> saveDairyProducts(
	        @RequestParam("file") @Valid MultipartFile file,
	        @RequestParam("productName") String productName,
	        @RequestParam("quantity") String quantity,
	        @RequestParam("price") Double price,
	        HttpServletRequest request) throws IOException, SerialException, SQLException {

	    if (file == null || file.isEmpty()) {
	        throw new ProductsIsNotFoundException("Image file is required");
	    }

	    DairyProducts dairyProducts = dairyProductsService.saveDairyProducts(request, file, productName, quantity, price);

	    DairyProductsResponse dairyProductsResponse = new DairyProductsResponse();
	    dairyProductsResponse.setId(dairyProducts.getId());
	    dairyProductsResponse.setProductName(dairyProducts.getProductName());
	    dairyProductsResponse.setQuantity(dairyProducts.getQuantity());

	    if (file != null && !file.isEmpty()) {
	        dairyProductsResponse.setImage("Image is Uploaded");
	    } else {
	        dairyProductsResponse.setImage("");
	    }

	    dairyProductsResponse.setPrice(dairyProducts.getPrice());

	    ResponseBody<DairyProductsResponse> dairyProductsBody = new ResponseBody<>();
	    dairyProductsBody.setStatusCode(HttpStatus.OK.value());
	    dairyProductsBody.setStatus("SUCCESS");
	    dairyProductsBody.setData(dairyProductsResponse);

	    return ResponseEntity.ok(dairyProductsBody);
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
	@GetMapping("/allDairyProducts")
	public ResponseEntity<?> getAllDairyProducts() {
		List<DairyProductsResponse> list = dairyProductsService.getAllDairyProducts();

		ResponseBody<List<DairyProductsResponse>> dairyProductsBody = new ResponseBody<>();
		dairyProductsBody.setStatusCode(HttpStatus.OK.value());
		dairyProductsBody.setStatus("SUCCESS");
		dairyProductsBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(dairyProductsBody);

	}
	
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayDairyProductsImage(@RequestParam("id") Integer id) throws SQLException {
		DairyProducts dairyProducts = dairyProductsService.getImageViewById(id);
		if (dairyProducts != null) {
			byte[] imageBytes = dairyProducts.getImage().getBytes(1, (int) dairyProducts.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateDairyProducts(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("productsName") String productsName,
			@RequestParam("quantity") String quantity, @RequestParam("price") Double price) throws IOException, SerialException, SQLException {
		String message = dairyProductsService.updatedairyProducts(id, productsName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	
	public ResponseEntity<ResponseBody<String>> deleteproductsNameById(@PathVariable long id) {
		dairyProductsService.deleteDairyProducts(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("DairyProducts with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	
}