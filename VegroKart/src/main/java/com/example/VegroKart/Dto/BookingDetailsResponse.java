package com.example.VegroKart.Dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.example.VegroKart.Entity.MyAddress;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDetailsResponse {
	
	private Long id;
    private String name;
    private String mobileNumber;
    private List<MyAddress> myAddress;
//    private String myAddress;
    private Object bookedItem;
    private String category;
    private int quantity;
    private Status status;
    private List<LocalDate> deliveryDates;
	private LocalDateTime orderDateTime;
	
		
}
