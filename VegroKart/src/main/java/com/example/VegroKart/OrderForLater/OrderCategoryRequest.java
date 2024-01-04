package com.example.VegroKart.OrderForLater;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderCategoryRequest {
	
private String categoryName;
	
    private List<OrderItemsRequest> items;
}


