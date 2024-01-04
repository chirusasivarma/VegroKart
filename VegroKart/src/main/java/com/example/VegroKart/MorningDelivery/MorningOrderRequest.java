package com.example.VegroKart.MorningDelivery;

import java.time.LocalDate;
import java.util.List;

import com.example.VegroKart.InstantDelivery.OrderCategoryRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MorningOrderRequest {

	private List<LocalDate> deliveryDates;
    private String orderTime;
//    private LocalTime deliveryStartTime;
//    private LocalTime deliveryEndTime;
    private List<OrderCategoryRequest> orderCategories;
}
