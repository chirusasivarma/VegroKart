package com.example.VegroKart.OrderForLater;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    
    @FutureOrPresent(message = "The requested delivery date and time must be in the present or future.")
    private LocalDateTime requestedDeliveryDateTime;
    private List<OrderCategoryRequest> orderCategories;

}
