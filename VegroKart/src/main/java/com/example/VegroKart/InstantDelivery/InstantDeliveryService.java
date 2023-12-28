package com.example.VegroKart.InstantDelivery;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VegroKart.Entity.Beverages;
import com.example.VegroKart.Entity.CannedGoods;
import com.example.VegroKart.Entity.DairyProducts;
import com.example.VegroKart.Entity.FrozenFoods;
import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.Meat;
import com.example.VegroKart.Entity.PersonalCare;
import com.example.VegroKart.Entity.SaucesAndOil;
import com.example.VegroKart.Entity.Snacks;
import com.example.VegroKart.Entity.Status;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Entity.Vegetables;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Repository.BeveragesRepository;
import com.example.VegroKart.Repository.CannedGoodsRepository;
import com.example.VegroKart.Repository.DairyProductsRepository;
import com.example.VegroKart.Repository.FrozenFoodsRepository;
import com.example.VegroKart.Repository.FruitsRepository;
import com.example.VegroKart.Repository.MeatRepository;
import com.example.VegroKart.Repository.PersonalCareRepository;
import com.example.VegroKart.Repository.SaucesAndOilRepository;
import com.example.VegroKart.Repository.SnacksRepository;
import com.example.VegroKart.Repository.UserRepository;
import com.example.VegroKart.Repository.VegetablesRepository;


@Service
public class InstantDeliveryService {

    @Autowired
    private InstantDeliveryRepository instantDeliveryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FruitsRepository fruitRepository;

    @Autowired
    private SnacksRepository snackRepository;

    @Autowired
    private VegetablesRepository vegetableRepository;

    @Autowired
    private MeatRepository meatRepository;

    @Autowired
    private BeveragesRepository beverageRepository;

    @Autowired
    private DairyProductsRepository dairyProductRepository;
    
    @Autowired
	private CannedGoodsRepository cannedGoodsRepository;
    
	@Autowired
	private FrozenFoodsRepository frozenFoodsRepository;
	
	@Autowired
	private PersonalCareRepository personalCareRepository;
	@Autowired
	private SaucesAndOilRepository saucesAndOilRepository;

    public List<InstantDelivery> getAllInstantDeliveries() {
        return instantDeliveryRepository.findAll();
    }

    public List<InstantDelivery> getInstantDeliveriesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIsNotFoundException("Invalid user ID"));

        return instantDeliveryRepository.findByUser(user);
    }

    public void placeOrder(Long userId, List<OrderCategoryRequest> orderCategories) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIsNotFoundException("Invalid user ID"));

        for (OrderCategoryRequest orderCategory : orderCategories) {
            String categoryName = orderCategory.getCategoryName();
            List<OrderItemRequest> items = orderCategory.getItems();

            for (OrderItemRequest orderItem : items) {
                Long entityId = orderItem.getItemId();
                int quantity = orderItem.getQuantity();

                InstantDelivery instantDelivery = new InstantDelivery();
                instantDelivery.setUser(user);
                instantDelivery.setQuantity(quantity);
                instantDelivery.setStatus(Status.WAITING);
                instantDelivery.setDeliveryTime(Instant.now());

                switch (categoryName) {
                    case "Fruit":
                        Fruits fruit = fruitRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Fruit ID"));
                        instantDelivery.setFruit(fruit);
                        break;
                    case "Snack":
                        Snacks snack = snackRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Snack ID"));
                        instantDelivery.setSnack(snack);
                        break;
                    case "Vegetable":
                        Vegetables vegetable = vegetableRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Vegetable ID"));
                        instantDelivery.setVegetable(vegetable);
                        break;
                    case "Meat":
                        Meat meat = meatRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Meat ID"));
                        instantDelivery.setMeat(meat);
                        break;
                    case "Beverage":
                        Beverages beverage = beverageRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Beverage ID"));
                        instantDelivery.setBeverage(beverage);
                        break;
                    case "DairyProduct":
                        DairyProducts dairyProduct = dairyProductRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid DairyProduct ID"));
                        instantDelivery.setDairyProduct(dairyProduct);
                        break;
                    case "CannedGoods":
                        CannedGoods cannedGoods = cannedGoodsRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid CannedGoods ID"));
                        instantDelivery.setCannedGood(cannedGoods);
                        break;
                    case "FrozenFoods":
                        FrozenFoods frozenFoods = frozenFoodsRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid FrozenFoods ID"));
                        instantDelivery.setFrozenFood(frozenFoods);
                        break;
                    case "PersonalCare":
                        PersonalCare personalCare = personalCareRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid PersonalCare ID"));
                        instantDelivery.setPersonalCare(personalCare);
                        break;
                    case "SaucesAndOil":
                        SaucesAndOil saucesAndOil = saucesAndOilRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid SaucesAndOil ID"));
                        instantDelivery.setSaucesAndOils(saucesAndOil);
                        break;
                    
                }

                instantDeliveryRepository.save(instantDelivery);
            }
        }
    }
}
