package com.example.VegroKart.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_Address")
public class MyAddress {
	
	@Id
	private int houseNumber;
	
	private String street;
	
	private String city;
	
	private int pinCode;
	
	@Enumerated(EnumType.STRING)
	private Place place;


}
