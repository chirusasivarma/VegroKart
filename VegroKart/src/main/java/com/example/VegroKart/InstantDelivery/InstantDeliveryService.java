package com.example.VegroKart.InstantDelivery;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VegroKart.Dto.BookingDetailsResponse;
import com.example.VegroKart.Dto.Status;
import com.example.VegroKart.Entity.BabyItems;
import com.example.VegroKart.Entity.Beverages;
import com.example.VegroKart.Entity.CannedGoods;
import com.example.VegroKart.Entity.DairyProducts;
import com.example.VegroKart.Entity.FrozenFoods;
import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.Meat;
import com.example.VegroKart.Entity.PersonalCare;
import com.example.VegroKart.Entity.PetFood;
import com.example.VegroKart.Entity.SaucesAndOil;
import com.example.VegroKart.Entity.Snacks;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Entity.Vegetables;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Repository.BabyItemsRepository;
import com.example.VegroKart.Repository.BeveragesRepository;
import com.example.VegroKart.Repository.CannedGoodsRepository;
import com.example.VegroKart.Repository.DairyProductsRepository;
import com.example.VegroKart.Repository.FrozenFoodsRepository;
import com.example.VegroKart.Repository.FruitsRepository;
import com.example.VegroKart.Repository.MeatRepository;
import com.example.VegroKart.Repository.PersonalCareRepository;
import com.example.VegroKart.Repository.PetFoodRepository;
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
	
	@Autowired
	private BabyItemsRepository babyItemsRepository;
	
	@Autowired
	private PetFoodRepository petFoodRepository;

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
                instantDelivery.setStatus(Status.Ontheway);
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
                    case "babyItem":
                        BabyItems babyItem = babyItemsRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid babyitems ID"));
                        instantDelivery.setBabyItem(babyItem);
                        break;
                    case "petFood":
                        PetFood petFood = petFoodRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid petFood ID"));
                        instantDelivery.setPetFood(petFood);
                        break;
   
                    
                }

                instantDeliveryRepository.save(instantDelivery);
            }
        }
    }
    
    
    
    public String approveinstantDelivery(Long id) {
		InstantDelivery instantDelivery = instantDeliveryRepository.findById(id).get();
		instantDelivery.setStatus(Status.Delivered);
		instantDeliveryRepository.save(instantDelivery);
		return "Order Deliverd";
    }
    
    
    
    public String cancelledinstantDelivery(Long id) {
		InstantDelivery instantDelivery = instantDeliveryRepository.findById(id).get();
		instantDelivery.setStatus(Status.Cancelled);
		instantDeliveryRepository.save(instantDelivery);
		return "Order cancelled";
    }
    
    
    public BookingDetailsResponse getBookingDetails(Long bookingId) {
        InstantDelivery instantDelivery = instantDeliveryRepository.findById(bookingId)
                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Booking ID"));

        BookingDetailsResponse response = new BookingDetailsResponse();
        response.setId(instantDelivery.getId());
        response.setUser(instantDelivery.getUser());
        response.setQuantity(instantDelivery.getQuantity());
        response.setStatus(instantDelivery.getStatus());
        response.setDeliveryTime(instantDelivery.getDeliveryTime());

        setBookedItemAndCategory(instantDelivery, response);

        return response;
    }

    private void setBookedItemAndCategory(InstantDelivery instantDelivery, BookingDetailsResponse response) {
        if (instantDelivery.getFruit() != null) {
            response.setBookedItem(instantDelivery.getFruit());
            response.setCategory("Fruit");
        } else if (instantDelivery.getSnack() != null) {
            response.setBookedItem(instantDelivery.getSnack());
            response.setCategory("Snack");
        } else if (instantDelivery.getVegetable() != null) {
            response.setBookedItem(instantDelivery.getVegetable());
            response.setCategory("Vegetable");
        } else if (instantDelivery.getMeat() != null) {
            response.setBookedItem(instantDelivery.getMeat());
            response.setCategory("Meat");
        } else if (instantDelivery.getBeverage() != null) {
            response.setBookedItem(instantDelivery.getBeverage());
            response.setCategory("Beverage");
        } else if (instantDelivery.getDairyProduct() != null) {
            response.setBookedItem(instantDelivery.getDairyProduct());
            response.setCategory("Dairy Product");
        } else if (instantDelivery.getCannedGood() != null) {
            response.setBookedItem(instantDelivery.getCannedGood());
            response.setCategory("Canned Good");
        } else if (instantDelivery.getFrozenFood() != null) {
            response.setBookedItem(instantDelivery.getFrozenFood());
            response.setCategory("Frozen Food");
        } else if (instantDelivery.getPersonalCare() != null) {
            response.setBookedItem(instantDelivery.getPersonalCare());
            response.setCategory("Personal Care");
        } else if (instantDelivery.getSaucesAndOils() != null) {
            response.setBookedItem(instantDelivery.getSaucesAndOils());
            response.setCategory("Sauces and Oils");
        } else if (instantDelivery.getBabyItem() != null) {
        	response.setBookedItem(instantDelivery.getBabyItem());
        	response.setCategory("babyItem");
        }else if (instantDelivery.getPersonalCare() !=null) {
        	response.setBookedItem(instantDelivery.getPetFood());
			response.setCategory("petFood");
		}
        
    }
    
    
    public List<BookingDetailsResponse> getAllBookingDetails() {
        List<InstantDelivery> allInstantDeliveries = instantDeliveryRepository.findAll();

        List<BookingDetailsResponse> responses = new ArrayList<>();
        for (InstantDelivery instantDelivery : allInstantDeliveries) {
            BookingDetailsResponse response = new BookingDetailsResponse();
            response.setId(instantDelivery.getId());
            response.setUser(instantDelivery.getUser());
            response.setQuantity(instantDelivery.getQuantity());
            response.setStatus(instantDelivery.getStatus());
            response.setDeliveryTime(instantDelivery.getDeliveryTime());

            setBookedItemAndCategory(instantDelivery, response);

            responses.add(response);
        }

        return responses;
    }
    
    
    
    
    
    
}
