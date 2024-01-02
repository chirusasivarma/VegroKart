package com.example.VegroKart.InstantDelivery;

import java.time.Instant;

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

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "InstantDelivery")
public class InstantDelivery {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "fruit_id")
	    private Fruits fruit;

	    @ManyToOne
	    @JoinColumn(name = "snack_id")
	    private Snacks snack;

	    @ManyToOne
	    @JoinColumn(name = "vegetable_id")
	    private Vegetables vegetable;

	    @ManyToOne
	    @JoinColumn(name = "meat_id")
	    private Meat meat;

	    @ManyToOne
	    @JoinColumn(name = "baby_item_id")
	    private BabyItems babyItem;
	    
	    @ManyToOne
	    @JoinColumn(name = "pet_food_id")
	    private PetFood petFood;

	    @ManyToOne
	    @JoinColumn(name = "personal_care_id")
	    private PersonalCare personalCare;

	

	    @ManyToOne
	    @JoinColumn(name = "dairy_product_id")
	    private DairyProducts dairyProduct;

	    @ManyToOne
	    @JoinColumn(name = "beverage_id")
	    private Beverages beverage;

	    @ManyToOne
	    @JoinColumn(name = "canned_good_id")
	    private CannedGoods cannedGood;

	    @ManyToOne
	    @JoinColumn(name = "frozen_food_id")
	    private FrozenFoods frozenFood;

	    @ManyToOne
	    @JoinColumn(name = "sauces_and_oils_id")
	    private SaucesAndOil saucesAndOils;

	    private int quantity;
	    
	    @Enumerated(EnumType.STRING)
	    private Status status;
	    
	    private Instant deliveryTime;


}
