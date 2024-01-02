package com.example.VegroKart.Dto;

import java.time.Instant;

import com.example.VegroKart.Entity.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDetailsResponse {
	private Long id;
    private User user;
    private Object bookedItem;
    private String category;
    private int quantity;
    private Status status;
    private Instant deliveryTime;
		
}
