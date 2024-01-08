package com.example.VegroKart.OrderForLater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VegroKart.Dto.BookingDetailsResponse;
import com.example.VegroKart.Helper.ResponseBody;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orderForLater")

public class OrderForLaterController {
	
	@Autowired
    private OderForLaterService orderForLaterService;
	
	@PostMapping("/placeOrder/{userId}")
	public ResponseEntity<ResponseBody<?>> placeOrder(
	        @PathVariable Long userId,
	        @Valid @RequestBody OrderRequest orderRequest) {
	    orderForLaterService.placeOrder(userId, orderRequest.getOrderCategories());
	    // Your existing response logic
	    ResponseBody<String> body = new ResponseBody<>();
	    body.setStatusCode(HttpStatus.OK.value());
	    body.setStatus("SUCCESS");
	    body.setData("Order placed successfully");
	    return new ResponseEntity<>(body, HttpStatus.OK);
	}


    @GetMapping("/all")
    public ResponseEntity<ResponseBody<List<OrderForLater>>> getAllOderForLater() {
        List<OrderForLater> instantDeliveries = orderForLaterService.getAllOderForLater();
        ResponseBody<List<OrderForLater>> body=new ResponseBody<List<OrderForLater>>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(instantDeliveries);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseBody<List<BookingDetailsResponse>>> getOderForLaterByUser(@PathVariable Long userId) {
        List<BookingDetailsResponse> responses = orderForLaterService.getBookingDetailsByUserId(userId);
        ResponseBody<List<BookingDetailsResponse>> body=new ResponseBody<List<BookingDetailsResponse>>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(responses);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PutMapping("/delivered/{id}")
    public ResponseEntity<ResponseBody<String>> approveorderForLater(@PathVariable Long id) {
        String result = orderForLaterService.approveorderForLater(id);

        ResponseBody<String> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(result);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ResponseBody<String>> rejectorderForLater(@PathVariable Long id) {
    	String result = orderForLaterService.cancelledorderForLater(id);
        ResponseBody<String> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(result);
        return ResponseEntity.ok(body);
    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<ResponseBody<BookingDetailsResponse>> getBookingDetails(@PathVariable Long bookingId) {
        BookingDetailsResponse bookingDetails = orderForLaterService.getBookingDetails(bookingId);
        ResponseBody<BookingDetailsResponse> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(bookingDetails);
        return ResponseEntity.ok(body);
    }
    
    @GetMapping("/all-bookings")
    public ResponseEntity<ResponseBody<List<BookingDetailsResponse>>> getAllBookingDetails() {
        List<BookingDetailsResponse> bookingDetailsList = orderForLaterService.getAllBookingDetails();
        ResponseBody<List<BookingDetailsResponse>> body=new ResponseBody<List<BookingDetailsResponse>>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(bookingDetailsList);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

 
    

}



