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


    private String name;


    private String emailAddress;

  
    private String mobileNumber;

    private String password;
    
    @Lob
    @JsonIgnore
    private Blob image;
    
    @JsonIgnore
    private String myAddress;
    
//    
//    
//    @OneToMany(targetEntity = MyAddress.class , cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName = "id" , name = "user-Id")
//    private List<MyAddress> myAddress;
//	

}
