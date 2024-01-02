//package com.example.VegroKart.CartService;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.VegroKart.Entity.Fruits;
//import com.example.VegroKart.Entity.User;
//import com.example.VegroKart.Exception.ProductsIsNotFoundException;
//import com.example.VegroKart.Repository.FruitsRepository;
//import com.example.VegroKart.Repository.UserRepository;
//
//import jakarta.transaction.Transactional;
//
//@Service
//@Transactional
//public class FruitsCartService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private FruitsRepository fruitsRepository;
//
//    public User addToCart(long userId, long fruitsId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ProductsIsNotFoundException("User not found"));
//
//        Fruits fruit = fruitsRepository.findById(fruitsId)
//                .orElseThrow(() -> new ProductsIsNotFoundException("Fruits not found"));
//
//        // Check if the fruit is already in the cart; if yes, update the quantity
//        Optional<Fruits> existingFruit = user.getFruitsList().stream()
//                .filter(f -> f.getId().equals(fruitsId))
//                .findFirst();
//
//        if (existingFruit.isPresent()) {
//            existingFruit.get().setQuantity(existingFruit.get().getQuantity() + 1); // Increment quantity by 1
//        } else {
//            fruit.setQuantity(1); // Set quantity to 1 for a new fruit
//            fruit.setUser(user);
//            fruitsRepository.save(fruit);
//            user.getFruitsList().add(fruit);
//        }
//
//        return userRepository.save(user);
//    }
//    @Transactional
//    public List<Fruits> getCartItems(long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ProductsIsNotFoundException("User not found"));
//
//        // Initialize the collection to ensure that it's loaded outside of the transaction
//        user.getFruitsList().size();
//
//        return user.getFruitsList();
//    }
//}