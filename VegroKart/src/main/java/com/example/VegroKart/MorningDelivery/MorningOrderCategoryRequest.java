package com.example.VegroKart.MorningDelivery;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MorningOrderCategoryRequest {

    private String categoryName;

    private List<MorningOrderItemRequest> items;
}