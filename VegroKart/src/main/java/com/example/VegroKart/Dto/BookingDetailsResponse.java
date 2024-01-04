package com.example.VegroKart.Dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import com.example.VegroKart.Entity.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDetailsResponse {
	private Long id;
    private String name;
    private String mobileNumber;
    private String myAddress;
    private Object bookedItem;
    private String category;
    private int quantity;
    private Status status;
    private Instant deliveryTime;
	private LocalDateTime orderDateTime;
	private Date deliveryDate;
		
}
