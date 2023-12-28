package com.example.VegroKart.Home;

import java.util.List;

import com.example.VegroKart.Entity.Fruits;
import com.example.VegroKart.Entity.Snacks;
import com.example.VegroKart.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="instantDeliery")

public class InstantDelivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instantDeliveryId;
	
	 @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    @OneToMany(mappedBy = "instantDelivery", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore  
	    private List<Fruits> fruits;

	    @OneToMany(mappedBy = "instantDelivery", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore  
	    private List<Snacks> snacks;
}
