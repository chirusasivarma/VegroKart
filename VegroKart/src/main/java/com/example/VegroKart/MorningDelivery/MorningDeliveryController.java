package com.example.VegroKart.MorningDelivery;

import java.util.List;
import java.util.concurrent.Delayed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VegroKart.Dto.BookingDetailsResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.InstantDelivery.OrderCategoryRequest;

@RestController
@RequestMapping("/morning_Delivery")
public class MorningDeliveryController {
	
	@Autowired
	private MorningDeliveryService morningDeliveryService;
	
	    @PostMapping("/place-order/{userId}")
	    public ResponseEntity<ResponseBody<?>> placeMorningOrder(
	            @PathVariable("userId") Long userId,
	            @RequestBody MorningOrderRequest morningOrderRequest) {
	        morningDeliveryService.placeMorningOrder(userId, morningOrderRequest);
	        ResponseBody<String> body=new ResponseBody<String>();
	        body.setStatusCode(HttpStatus.OK.value());
	        body.setStatus("SUCCESS");
	        body.setData("Morning order placed successfully");
	        return new ResponseEntity<>(body,HttpStatus.OK); 
	    }
	    
	    
	    @GetMapping("/{morningDeliveryId}")
	    public ResponseEntity<BookingDetailsResponse> getMorningDeliveryDetails(@PathVariable Long morningDeliveryId) {
	        BookingDetailsResponse response = morningDeliveryService.getMorningDeliveryDetails(morningDeliveryId);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	   

	    @GetMapping("/all")
	    public ResponseEntity<ResponseBody<List<BookingDetailsResponse>>> getAllMorningDeliveryDetails() {
	        List<BookingDetailsResponse> responses = morningDeliveryService.getAllBookingDetails();
	        ResponseBody<List<BookingDetailsResponse>> body=new ResponseBody<List<BookingDetailsResponse>>();
	    	body.setStatusCode(HttpStatus.OK.value());
	    	body.setStatus("SUCCESS");
	    	body.setData(responses);
	    	return ResponseEntity.ok(body);
	    }

//	    @GetMapping("/user/{userId}")
//	    public ResponseEntity<List<BookingDetailsResponse>> getMorningDeliveryDetailsByUserId(@PathVariable Long userId) {
//	        List<BookingDetailsResponse> responses = morningDeliveryService.getMorningDeliveriesByUser(userId);
//	        return new ResponseEntity<>(responses, HttpStatus.OK);
//	    }

}
