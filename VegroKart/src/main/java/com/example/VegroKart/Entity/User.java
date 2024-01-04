package com.example.VegroKart.Entity;

import java.sql.Blob;
import java.util.List;

import com.example.VegroKart.InstantDelivery.InstantDelivery;
import com.example.VegroKart.MorningDelivery.MorningDelivery;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "User")

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@NotBlank(message = "Name cannot be blank")
	private String name;

	@NotBlank(message = "Email must not be empty")
	private String emailAddress;

	@Pattern(regexp = "^\\+91[0-9]{10}$", message = "Invalid mobile number. It should start with +91 and be followed by 10 digits.")
	private String mobileNumber;

	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;
    
    @Lob
    @JsonIgnore
    private Blob image;
    
 //   @NotBlank(message = "Address cannot be blank")
    @JsonIgnore
    private String myAddress;
    
//    
//    
//    @OneToMany(targetEntity = MyAddress.class , cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName = "id" , name = "user-Id")
//    private List<MyAddress> myAddress;
//	

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InstantDelivery> instantDelivery;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MorningDelivery> morningDelivery;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MorningDelivery> morningDeliveries; 

}