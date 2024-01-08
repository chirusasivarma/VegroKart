package com.example.VegroKart.OrderForLater;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsRequest {
	
	private Long itemId;
    private int quantity;
    
    
    private LocalDateTime requestedDeliveryDateTime;
}
