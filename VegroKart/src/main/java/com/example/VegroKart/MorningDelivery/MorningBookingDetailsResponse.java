package com.example.VegroKart.MorningDelivery;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.example.VegroKart.Dto.Status;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MorningBookingDetailsResponse {

	private Long id;

    private String name;

    private String mobileNumber;

    private String myAddress;

    private Object bookedItem;

    private String category;

    private int quantity;

    private Status status;

    private Instant orderedTime;

    private List<LocalDate> deliveryDates;
}
