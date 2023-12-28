package com.example.VegroKart.InstantDelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

	private Long itemId;
    private int quantity;
}
