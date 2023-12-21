package com.example.VegroKart.Entity;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotEmpty(message = "email address must not be empty")
   	@NotNull(message = "email address must not be null")
   	@Email(message = "please provide valide email")
    private String emailAddress;

    @NotNull(message = "Mobile number must not be null")
    @Pattern(regexp = "\\d{10}", message = "Please provide a valid 10-digit mobile number")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Size(min = 10, max = 10, message = "Please provide a valid 10-digit mobile number")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    private String password;
    
    @Lob
    @JsonIgnore
    private Blob image;
    
    @NotBlank(message = "address must not be blank")
    private String myAddress;
    
//    
//    
//    @OneToMany(targetEntity = MyAddress.class , cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName = "id" , name = "user-Id")
//    private List<MyAddress> myAddress;
//	

}
