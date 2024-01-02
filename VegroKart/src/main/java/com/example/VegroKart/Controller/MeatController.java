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
import com.example.VegroKart.Entity.Meat;
import com.example.VegroKart.Entity.MeatResponse;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.MeatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/meat")
public class MeatController {
	
	@Autowired
	public MeatService meatService;

	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<MeatResponse>> saveMeat(
	        @RequestParam("file") @Valid MultipartFile file,
	        @RequestParam("meatName") String meatName,
	        @RequestParam("quantity") String quantity,
	        @RequestParam("price") String price,
	        HttpServletRequest request) throws IOException, SerialException, SQLException {

	    if (file == null || file.isEmpty()) {
	        throw new ProductsIsNotFoundException("Image file is required");
	    }

	    Meat meat = meatService.saveMeat(request, file, meatName, quantity, price);

	    MeatResponse meatResponse = new MeatResponse();
	    meatResponse.setId(meat.getId());
	    meatResponse.setMeatName(meat.getMeatName());
	    meatResponse.setQuantity(meat.getQuantity());

	    if (file != null && !file.isEmpty()) {
	        meatResponse.setImage("Image is Uploaded");
	    } else {
	        meatResponse.setImage("");
	    }

	    meatResponse.setPrice(meat.getPrice());

	    ResponseBody<MeatResponse> meatBody = new ResponseBody<>();
	    meatBody.setStatusCode(HttpStatus.OK.value());
	    meatBody.setStatus("SUCCESS");
	    meatBody.setData(meatResponse);

	    return ResponseEntity.ok(meatBody);
	}

	@GetMapping("/allMeats")

	public ResponseEntity<?> getAllMeat() {
		List<MeatResponse> list = meatService.getAllMeat();

		ResponseBody<List<MeatResponse>> meatBody = new ResponseBody<>();
		meatBody.setStatusCode(HttpStatus.OK.value());
		meatBody.setStatus("SUCCESS");
		meatBody.setData(list);

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(meatBody);

	}
	@GetMapping("/getById/{id}")
    public ResponseEntity<ResponseBody<MeatResponse>> getMeatById(@PathVariable("id") long id) {
		MeatResponse meatResponse = meatService.getMeatById(id);
        ResponseBody<MeatResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(meatResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
       
    }
	@GetMapping("/displayImage")
	@Transactional(readOnly = true)
	public ResponseEntity<byte[]> displayMeatImage(@RequestParam("id") Integer id) throws SQLException {
		Meat meat = meatService.getImageViewById(id);
		if (meat != null) {
			byte[] imageBytes = meat.getImage().getBytes(1, (int) meat.getImage().length());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		} else {
			return null;
		}
	}
	@GetMapping("/search/{meatName}")
	public ResponseEntity<ResponseBody<List<MeatResponse>>> getMeatByMeatName(
			@PathVariable String meatName) {
		List<MeatResponse> meatResponses = meatService
				.getMeatByMeatName(meatName);
		ResponseBody<List<MeatResponse>> meatBody = new ResponseBody<>();
		if (meatResponses.isEmpty()) {
			meatBody.setStatusCode(HttpStatus.NO_CONTENT.value());
			meatBody.setStatus("No Meat available with this given name.");
		} else {
			meatBody.setStatusCode(HttpStatus.OK.value());
			meatBody.setStatus("SUCCESS");
			meatBody.setData(meatResponses);
		}
		return ResponseEntity.ok(meatBody);
	}
	@PutMapping("/updateBy/{id}")
	public ResponseEntity<ResponseBody<String>> updateMeat(

			@PathVariable("id") long id, @RequestParam("file") MultipartFile file, @RequestParam("meatName") String meatName,
			@RequestParam("quantity") String quantity, @RequestParam("price") String price) throws IOException, SerialException, SQLException {
		String message = meatService.updatemeat(id, meatName, quantity, price,file);

		ResponseBody<String> responseBody = new ResponseBody<>();
		responseBody.setStatusCode(HttpStatus.OK.value());
		responseBody.setStatus("SUCCESS");
		responseBody.setData(message);
		return ResponseEntity.ok(responseBody);

	}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseBody<String>> deleteMeatById(@PathVariable long id) {
		meatService.deleteMeat(id);
	    ResponseBody<String> responsebody = new ResponseBody<>();
	            responsebody.setStatusCode(HttpStatus.OK.value());
	            responsebody.setStatus("SUCCESS");
	            responsebody.setData("Meat with ID " + id + " deleted successfully.");
	            return ResponseEntity.ok(responsebody);
	        } 
	}
