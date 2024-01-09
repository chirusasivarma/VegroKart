package com.example.VegroKart.OrderForLater;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.OrderForLater.OrderForLater;
import com.example.VegroKart.OrderForLater.OrderForLaterRepository;

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
public class OderForLaterService {
	
	@Autowired
    private OrderForLaterRepository orderForLaterRepository;

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

    public List<OrderForLater> getAllOderForLater() {
        return orderForLaterRepository.findAll();
    }

    public List<OrderForLater> getOderForLaterByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIsNotFoundException("Invalid user ID"));

        return orderForLaterRepository.findByUser(user);
    }
    public void placeOrder(Long userId, List<OrderCategoryRequest> orderCategories, LocalDateTime requestedDeliveryDateTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIsNotFoundException("Invalid user ID"));
        double totalOrderPrice = 0.0;
        
        for (OrderCategoryRequest orderCategory : orderCategories) {
            String categoryName = orderCategory.getCategoryName();
            List<OrderItemsRequest> items = orderCategory.getItems();

            for (OrderItemsRequest orderItem : items) {
                Long entityId = orderItem.getItemId();
                int quantity = orderItem.getQuantity();

                OrderForLater orderForLater = new OrderForLater();
                orderForLater.setUser(user);
                orderForLater.setQuantity(quantity);

                orderForLater.setStatus(Status.PendingApproval);

                orderForLater.setOrderDateTime(LocalDateTime.now());

                orderForLater.setRequestedDeliveryDateTime(requestedDeliveryDateTime); // Set the requestedDeliveryDateTime here

                orderForLater.setRequestedDeliveryDateTime(requestedDeliveryDateTime);
                double itemTotalPrice = 0.0;
                switch (categoryName) {
                case "Fruit":
                    Fruits fruit = fruitRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Fruit ID"));
                    orderForLater.setFruit(fruit);
                    itemTotalPrice = fruit.getPrice() * quantity;
                    break;
                case "Snack":
                    Snacks snack = snackRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Snack ID"));
                    orderForLater.setSnack(snack);
                    itemTotalPrice = snack.getPrice() * quantity;
                    break;
                case "Vegetable":
                    Vegetables vegetable = vegetableRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Vegetable ID"));
                    orderForLater.setVegetable(vegetable);
                    itemTotalPrice =vegetable.getPrice() * quantity;
                    break;

                case "Meat":
                    Meat meat = meatRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Meat ID"));
                    orderForLater.setMeat(meat);
                    itemTotalPrice = meat.getPrice() * quantity;
                    break;
                case "Beverage":
                    Beverages beverage = beverageRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Beverage ID"));
                    orderForLater.setBeverage(beverage);
                    itemTotalPrice = beverage.getPrice() * quantity;
                    break;
                case "DairyProduct":
                    DairyProducts dairyProduct = dairyProductRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid DairyProduct ID"));
                    orderForLater.setDairyProduct(dairyProduct);
                    orderForLater.setTotalPrice(dairyProduct.getPrice() * quantity);
                    break;
                case "CannedGoods":
                    CannedGoods cannedGoods = cannedGoodsRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid CannedGoods ID"));
                    orderForLater.setCannedGood(cannedGoods);
                    orderForLater.setTotalPrice(cannedGoods.getPrice() * quantity);
                    break;
                case "FrozenFoods":
                    FrozenFoods frozenFoods = frozenFoodsRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid FrozenFoods ID"));
                    orderForLater.setFrozenFood(frozenFoods);
                    orderForLater.setTotalPrice(frozenFoods.getPrice() * quantity);
                    break;
                case "PersonalCare":
                    PersonalCare personalCare = personalCareRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid PersonalCare ID"));
                    orderForLater.setPersonalCare(personalCare);
                    orderForLater.setTotalPrice(personalCare.getPrice() * quantity);
                    break;
                case "SaucesAndOil":
                    SaucesAndOil saucesAndOil = saucesAndOilRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid SaucesAndOil ID"));
                    orderForLater.setSaucesAndOils(saucesAndOil);
                    orderForLater.setTotalPrice(saucesAndOil.getPrice() * quantity);
                    break;
                case "babyItem":
                    BabyItems babyItem = babyItemsRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid babyitems ID"));
                    orderForLater.setBabyItem(babyItem);
                    orderForLater.setTotalPrice(babyItem.getPrice() * quantity);
                    break;
                case "petFood":
                    PetFood petFood = petFoodRepository.findById(entityId)
                            .orElseThrow(() -> new ProductsIsNotFoundException("Invalid petFood ID"));
                    orderForLater.setPetFood(petFood);
                    orderForLater.setTotalPrice(petFood.getPrice() * quantity);
                    break;
            }
                    totalOrderPrice += itemTotalPrice; 

                    orderForLater.setTotalPrice(itemTotalPrice);
                    orderForLaterRepository.save(orderForLater);
                }
            }

           
            System.out.println("Total Order Price: " + totalOrderPrice);
        

}
    
    
    
    public String approveorderForLater(Long id) {
		OrderForLater orderForLater = orderForLaterRepository.findById(id).get();
		orderForLater.setStatus(Status.orderplacedsuccessfully);
		orderForLaterRepository.save(orderForLater);
		return "Order Deliverd";
    }
    
    
    
    public String cancelledorderForLater(Long id) {
		OrderForLater orderForLater = orderForLaterRepository.findById(id).get();
		orderForLater.setStatus(Status.Cancelled);
		orderForLaterRepository.save(orderForLater);
		return "Order cancelled";
    }
    
    
    public BookingDetailsResponse getBookingDetails(Long bookingId) {
        OrderForLater orderForLater = orderForLaterRepository.findById(bookingId)
                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid Booking ID"));

        BookingDetailsResponse response = new BookingDetailsResponse();
        response.setId(orderForLater.getId());
        response.setName(orderForLater.getUser().getName());
        response.setMobileNumber(orderForLater.getUser().getMobileNumber());
        response.setMyAddress(orderForLater.getUser().getMyAddress());
        response.setQuantity(orderForLater.getQuantity());
        response.setStatus(orderForLater.getStatus());
        response.setTotalPrice(orderForLater.getTotalPrice());
        response.setOrderDateTime(orderForLater.getOrderDateTime());

        setBookedItemAndCategory(orderForLater, response);

        return response;
    }

    private void setBookedItemAndCategory(OrderForLater orderForLater, BookingDetailsResponse response) {
        if (orderForLater.getFruit() != null) {
            response.setBookedItem(orderForLater.getFruit());
            response.setCategory("Fruit");
        } else if (orderForLater.getSnack() != null) {
            response.setBookedItem(orderForLater.getSnack());
            response.setCategory("Snack");
        } else if (orderForLater.getVegetable() != null) {
            response.setBookedItem(orderForLater.getVegetable());
            response.setCategory("Vegetable");
        } else if (orderForLater.getMeat() != null) {
            response.setBookedItem(orderForLater.getMeat());
            response.setCategory("Meat");
        } else if (orderForLater.getBeverage() != null) {
            response.setBookedItem(orderForLater.getBeverage());
            response.setCategory("Beverage");
        } else if (orderForLater.getDairyProduct() != null) {
            response.setBookedItem(orderForLater.getDairyProduct());
            response.setCategory("Dairy Product");
        } else if (orderForLater.getCannedGood() != null) {
            response.setBookedItem(orderForLater.getCannedGood());
            response.setCategory("Canned Good");
        } else if (orderForLater.getFrozenFood() != null) {
            response.setBookedItem(orderForLater.getFrozenFood());
            response.setCategory("Frozen Food");
        } else if (orderForLater.getPersonalCare() != null) {
            response.setBookedItem(orderForLater.getPersonalCare());
            response.setCategory("Personal Care");
        } else if (orderForLater.getSaucesAndOils() != null) {
            response.setBookedItem(orderForLater.getSaucesAndOils());
            response.setCategory("Sauces and Oils");
        } else if (orderForLater.getBabyItem() != null) {
        	response.setBookedItem(orderForLater.getBabyItem());
        	response.setCategory("babyItem");
        }else if (orderForLater.getPersonalCare() !=null) {
        	response.setBookedItem(orderForLater.getPetFood());
			response.setCategory("petFood");
		}
        
    }
    
    
    public List<BookingDetailsResponse> getAllBookingDetails() {
        List<OrderForLater> allInstantDeliveries = orderForLaterRepository.findAll();

        List<BookingDetailsResponse> responses = new ArrayList<>();
        for (OrderForLater orderForLater : allInstantDeliveries) {
            BookingDetailsResponse response = new BookingDetailsResponse();
            response.setId(orderForLater.getId());
            response.setName(orderForLater.getUser().getName());
            response.setMobileNumber(orderForLater.getUser().getMobileNumber());
            response.setMyAddress(orderForLater.getUser().getMyAddress());
            response.setQuantity(orderForLater.getQuantity());
            response.setStatus(orderForLater.getStatus());
            response.setTotalPrice(orderForLater.getTotalPrice());
            response.setOrderDateTime(orderForLater.getOrderDateTime());

            setBookedItemAndCategory(orderForLater, response);

            responses.add(response);
        }

        return responses;
    }
    
    
 
    public List<BookingDetailsResponse> getBookingDetailsByUserId(Long userId) {
        List<OrderForLater> instantDeliveries = orderForLaterRepository.findByUserId(userId);

        if (instantDeliveries.isEmpty()) {
            throw new ProductsIsNotFoundException("No bookings found for the given User ID");
        }

        List<BookingDetailsResponse> responses = new ArrayList<>();

        for (OrderForLater orderForLater : instantDeliveries) {
            BookingDetailsResponse response = new BookingDetailsResponse();
            response.setId(orderForLater.getId());
            response.setName(orderForLater.getUser().getName());
            response.setMobileNumber(orderForLater.getUser().getMobileNumber());
            response.setMyAddress(orderForLater.getUser().getMyAddress());
            response.setQuantity(orderForLater.getQuantity());
            response.setStatus(orderForLater.getStatus());
            response.setTotalPrice(orderForLater.getTotalPrice());
            response.setOrderDateTime(orderForLater.getOrderDateTime());
            

            setBookedItemAndCategory(orderForLater, response);

            responses.add(response);
        }

        return responses;
    }
  
    public ResponseEntity<ResponseBody<?>> respondToOrder(Long orderId, boolean accept) {
        OrderForLater order = orderForLaterRepository.findById(orderId)
                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid order ID"));

        if (accept) {
            order.setStatus(Status.Ontheway);
        } else {
            order.setStatus(Status.Cancelled);
        }

        orderForLaterRepository.save(order);

        ResponseBody<String> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData("Order response processed successfully");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
    public ResponseEntity<ResponseBody<?>> respondToOrder2(Long orderId, boolean reject) {
        OrderForLater order = orderForLaterRepository.findById(orderId)
                .orElseThrow(() -> new ProductsIsNotFoundException("Invalid order ID"));

        if (reject) {
            order.setStatus(Status.Ontheway);
        } else {
            order.setStatus(Status.Cancelled);
        }

        orderForLaterRepository.save(order);

        ResponseBody<String> body = new ResponseBody<>();
        body.setStatusCode(HttpStatus.OK.value());
        body.setStatus("SUCCESS");
        body.setData("Order response processed successfully");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }





    
    
}

