package com.example.VegroKart.CartController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.VegroKart.CartService.FruitsCartService;
import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.User;

@RestController
@RequestMapping("/cart")
public class FruitsCartController {

    @Autowired
    private FruitsCartService fruitsCartService;

    @PostMapping("/addCart")
    public ResponseEntity<User> addToCart(
            @RequestParam long userId,
            @RequestParam long fruitsId
    ) {
        User user = fruitsCartService.addToCart(userId, fruitsId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getCart/{userId}")
    public ResponseEntity<List<Fruits>> getCartItems(@PathVariable long userId) {
        List<Fruits> cartItems = fruitsCartService.getCartItems(userId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
}
