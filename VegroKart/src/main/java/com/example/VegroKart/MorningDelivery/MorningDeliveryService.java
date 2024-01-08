package com.example.VegroKart.MorningDelivery;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.example.VegroKart.Exception.FruitsIsNotFoundException;
import com.example.VegroKart.Exception.ProductsIsNotFoundException;
import com.example.VegroKart.Exception.SnacksIsNotFoundException;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.InstantDelivery.InstantDelivery;
import com.example.VegroKart.InstantDelivery.OrderCategoryRequest;
import com.example.VegroKart.InstantDelivery.OrderItemRequest;
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

import jakarta.transaction.Transactional;

@Service
public class MorningDeliveryService {

    @Autowired
    private MorningDeliveryRepository morningDeliveryRepository;

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

    public List<MorningDelivery> getAllMorningDeliveries() {
        return morningDeliveryRepository.findAll();
    }

    public List<MorningDelivery> getMorningDeliveriesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIsNotFoundException("Invalid user ID"));

        return morningDeliveryRepository.findByUser(user);
    }

    @Transactional
	public void placeMorningOrder(Long userId, MorningOrderRequest morningOrderRequest) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new UserIsNotFoundException("Invalid user ID"));

	    double totalOrderPrice = 0.0; 
	    List<LocalDate> deliveryDates = morningOrderRequest.getDeliveryDates();

	    for (LocalDate deliveryDate : deliveryDates) {
	        Instant deliveryInstant = deliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

	        List<OrderCategoryRequest> orderCategories = morningOrderRequest.getOrderCategories();

	        for (OrderCategoryRequest orderCategory : orderCategories) {
	            String categoryName = orderCategory.getCategoryName();
	            List<OrderItemRequest> items = orderCategory.getItems();

	            MorningDelivery morningDelivery = new MorningDelivery();
	            morningDelivery.setUser(user);
	            morningDelivery.setDeliveryDates(deliveryDates);
	            morningDelivery.setOrderDateTime(LocalDateTime.now());

	            for (OrderItemRequest orderItem : items) {
	                Long entityId = orderItem.getItemId();
	                int quantity = orderItem.getQuantity();
	                morningDelivery.setQuantity(quantity);
	                morningDelivery.setStatus(Status.approvalPending);

	                double itemTotalPrice = 0.0;
	                switch (categoryName) {
                    case "Fruit":
                        Fruits fruit = fruitRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Fruit ID"));
                        morningDelivery.setFruit(fruit);
                        itemTotalPrice = fruit.getPrice() * quantity;
                        break;
                    case "Snack":
                        Snacks snack = snackRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Snack ID"));
                        morningDelivery.setSnack(snack);
                        itemTotalPrice = snack.getPrice() * quantity;
                        break;
                    case "Vegetable":
                        Vegetables vegetable = vegetableRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Vegetable ID"));
                        morningDelivery.setVegetable(vegetable);
                        itemTotalPrice =vegetable.getPrice() * quantity;
                        break;
                    case "Meat":
                        Meat meat = meatRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Meat ID"));
                        morningDelivery.setMeat(meat);
                        itemTotalPrice = meat.getPrice() * quantity;
                        break;
                    case "Beverage":
                        Beverages beverage = beverageRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Beverage ID"));
                        morningDelivery.setBeverage(beverage);
                        itemTotalPrice = beverage.getPrice() * quantity;
                        break;
                    case "DairyProduct":
                        DairyProducts dairyProduct = dairyProductRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid DairyProduct ID"));
                        morningDelivery.setDairyProduct(dairyProduct);
                        morningDelivery.setTotalPrice(dairyProduct.getPrice() * quantity);
                        break;
                    case "CannedGoods":
                        CannedGoods cannedGoods = cannedGoodsRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid CannedGoods ID"));
                        morningDelivery.setCannedGood(cannedGoods);
                        morningDelivery.setTotalPrice(cannedGoods.getPrice() * quantity);
                        break;
                    case "FrozenFoods":
                        FrozenFoods frozenFoods = frozenFoodsRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid FrozenFoods ID"));
                        morningDelivery.setFrozenFood(frozenFoods);
                        morningDelivery.setTotalPrice(frozenFoods.getPrice() * quantity);
                        break;
                    case "PersonalCare":
                        PersonalCare personalCare = personalCareRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid PersonalCare ID"));
                        morningDelivery.setPersonalCare(personalCare);
                        morningDelivery.setTotalPrice(personalCare.getPrice() * quantity);
                        break;
                    case "SaucesAndOil":
                        SaucesAndOil saucesAndOil = saucesAndOilRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid SaucesAndOil ID"));
                        morningDelivery.setSaucesAndOils(saucesAndOil);
                        morningDelivery.setTotalPrice(saucesAndOil.getPrice() * quantity);
                        break;
                    case "babyItem":
                        BabyItems babyItem = babyItemsRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid babyitems ID"));
                        morningDelivery.setBabyItem(babyItem);
                        morningDelivery.setTotalPrice(babyItem.getPrice() * quantity);
                        break;
                    case "petFood":
                        PetFood petFood = petFoodRepository.findById(entityId)
                                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid petFood ID"));
                        morningDelivery.setPetFood(petFood);
                        morningDelivery.setTotalPrice(petFood.getPrice() * quantity);
                        break;
                }
	                totalOrderPrice += itemTotalPrice; 

	                morningDelivery.setTotalPrice(itemTotalPrice);
                    morningDeliveryRepository.save(morningDelivery);
                }
            }

           
            System.out.println("Total Order Price: " + totalOrderPrice);
        
	    }
}


    public List<BookingDetailsResponse> getAllBookingDetails() {
        List<MorningDelivery> allMorningDeliveries = morningDeliveryRepository.findAll();

        List<BookingDetailsResponse> responses = new ArrayList<>();
        for (MorningDelivery morningDelivery : allMorningDeliveries) {
            BookingDetailsResponse response = new BookingDetailsResponse();
            response.setId(morningDelivery.getId());
            response.setName(morningDelivery.getUser().getName());
            response.setMobileNumber(morningDelivery.getUser().getMobileNumber());
            response.setMyAddress(morningDelivery.getUser().getMyAddress());
            response.setQuantity(morningDelivery.getQuantity());
            response.setStatus(morningDelivery.getStatus());
            response.setTotalPrice(morningDelivery.getTotalPrice());
            response.setDeliveryDates(morningDelivery.getDeliveryDates());
            response.setOrderDateTime(morningDelivery.getOrderDateTime());
            setMorningDeliveryItemAndCategory(morningDelivery, response);
            responses.add(response);
        }

        return responses;
    }

	    private void setMorningDeliveryItemAndCategory(MorningDelivery morningDelivery, BookingDetailsResponse response) {
	        if (morningDelivery.getFruit() != null) {
	            response.setBookedItem(morningDelivery.getFruit());
	            response.setCategory("Fruit");
	        } else if (morningDelivery.getSnack() != null) {
	            response.setBookedItem(morningDelivery.getSnack());
	            response.setCategory("Snack");
	        } else if (morningDelivery.getVegetable() != null) {
	            response.setBookedItem(morningDelivery.getVegetable());
	            response.setCategory("Vegetable");
	        } else if (morningDelivery.getMeat() != null) {
	            response.setBookedItem(morningDelivery.getMeat());
	            response.setCategory("Meat");
	        } else if (morningDelivery.getBeverage() != null) {
	            response.setBookedItem(morningDelivery.getBeverage());
	            response.setCategory("Beverage");
	        } else if (morningDelivery.getDairyProduct() != null) {
	            response.setBookedItem(morningDelivery.getDairyProduct());
	            response.setCategory("Dairy Product");
	        } else if (morningDelivery.getCannedGood() != null) {
	            response.setBookedItem(morningDelivery.getCannedGood());
	            response.setCategory("Canned Good");
	        } else if (morningDelivery.getFrozenFood() != null) {
	            response.setBookedItem(morningDelivery.getFrozenFood());
	            response.setCategory("Frozen Food");
	        } else if (morningDelivery.getPersonalCare() != null) {
	            response.setBookedItem(morningDelivery.getPersonalCare());
	            response.setCategory("Personal Care");
	        } else if (morningDelivery.getSaucesAndOils() != null) {
	            response.setBookedItem(morningDelivery.getSaucesAndOils());
	            response.setCategory("Sauces and Oils");
	        } else if (morningDelivery.getBabyItem() != null) {
	            response.setBookedItem(morningDelivery.getBabyItem());
	            response.setCategory("Baby Item");
	        } else if (morningDelivery.getPetFood() != null) {
	            response.setBookedItem(morningDelivery.getPetFood());
	            response.setCategory("Pet Food");
	        }
	        response.setDeliveryDates(morningDelivery.getDeliveryDates());
	        
	    }
	    
	    public BookingDetailsResponse getMorningDeliveryDetails(Long morningDeliveryId) {
	        MorningDelivery morningDelivery = morningDeliveryRepository.findById(morningDeliveryId)
	                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Morning Delivery ID"));

	        System.out.println("Delivery Dates: " + morningDelivery.getDeliveryDates());

	        BookingDetailsResponse response = new BookingDetailsResponse();
	        response.setId(morningDelivery.getId());
	        response.setName(morningDelivery.getUser().getName());
	        response.setMobileNumber(morningDelivery.getUser().getMobileNumber());
	        response.setMyAddress(morningDelivery.getUser().getMyAddress());
	        response.setQuantity(morningDelivery.getQuantity());
	        response.setStatus(morningDelivery.getStatus());
	        response.setTotalPrice(morningDelivery.getTotalPrice());
	        response.setOrderDateTime(morningDelivery.getOrderDateTime());
	        response.setDeliveryDates(morningDelivery.getDeliveryDates());
	        setMorningDeliveryItemAndCategory(morningDelivery, response);
	        return response;
	    }

	    public String approveDelivery(Long id) {
			MorningDelivery moring = morningDeliveryRepository.findById(id).get();
			moring.setStatus(Status.orderplacedsuccessfully);
			morningDeliveryRepository.save(moring);
			return "Order placed successfully";
	    }
	    
	    
	    
	    public String cancelledDelivery(Long id) {
	    	MorningDelivery moring = morningDeliveryRepository.findById(id).get();
			moring.setStatus(Status.Cancelled);
			morningDeliveryRepository.save(moring);
			return "Order cancelled";
	    }


	    
}
