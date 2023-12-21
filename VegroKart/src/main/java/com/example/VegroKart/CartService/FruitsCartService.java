package com.example.VegroKart.CartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Repository.FruitsRepository;
import com.example.VegroKart.Repository.UserRepository;

@Service
public class FruitsCartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FruitsRepository fruitsRepository;

    public User addToCart(long userId, Fruits fruit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProductsIsNotFoundException("User not found"));

        fruit.setUser(user);
        fruitsRepository.save(fruit);

        user.getFruitsList().add(fruit);
        return userRepository.save(user);
    }

    public List<Fruits> getCartItems(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProductsIsNotFoundException("User not found"));

        return user.getFruitsList();
    }
}
