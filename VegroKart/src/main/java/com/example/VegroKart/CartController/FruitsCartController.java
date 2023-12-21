package com.example.VegroKart.CartController;

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

import com.example.VegroKart.CartService.FruitsCartService;
import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.User;

@RestController
@RequestMapping("/api/cart")
public class FruitsCartController {

    @Autowired
    private FruitsCartService fruitsCartService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<User> addToCart(@PathVariable long userId, @RequestBody Fruits fruit) {
        User user = fruitsCartService.addToCart(userId, fruit);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Fruits>> getCartItems(@PathVariable long userId) {
        List<Fruits> cartItems = fruitsCartService.getCartItems(userId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
}
