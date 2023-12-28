package com.example.VegroKart.InstantDelivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VegroKart.Helper.ResponseBody;


@RestController
@RequestMapping("/api/instant-delivery")
public class InstantDeliveryController {

    @Autowired
    private InstantDeliveryService instantDeliveryService;

    @GetMapping("/all")
    public ResponseEntity<ResponseBody<List<InstantDelivery>>> getAllInstantDeliveries() {
        List<InstantDelivery> instantDeliveries = instantDeliveryService.getAllInstantDeliveries();
        ResponseBody<List<InstantDelivery>> body=new ResponseBody<List<InstantDelivery>>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(instantDeliveries);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseBody<List<InstantDelivery>>> getInstantDeliveriesByUser(@PathVariable Long userId) {
        List<InstantDelivery> instantDeliveries = instantDeliveryService.getInstantDeliveriesByUser(userId);
        ResponseBody<List<InstantDelivery>> body=new ResponseBody<List<InstantDelivery>>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData(instantDeliveries);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping("/place-order/{userId}")
    public ResponseEntity<ResponseBody<String>> placeOrder(@PathVariable Long userId,
                                            @RequestBody List<OrderCategoryRequest> orderCategories) {
        instantDeliveryService.placeOrder(userId, orderCategories);
        ResponseBody<String> body=new ResponseBody<String>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData("Order placed successfully");
        return new ResponseEntity<>(body,HttpStatus.OK); 
    }

}
