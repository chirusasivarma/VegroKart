package com.example.VegroKart.InstantDelivery;



import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OrderCategoryRequest {

	 private String categoryName;
	
    private List<OrderItemRequest> items;
}
