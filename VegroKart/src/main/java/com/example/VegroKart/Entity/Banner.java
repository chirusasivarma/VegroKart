package com.example.VegroKart.Entity;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="banner")
public class Banner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Lob
    private Blob banner1;
    
    @Lob
    private Blob banner2;
    
    @Lob
    private Blob banner3;
    
    @Lob
    private Blob banner4;
    
    @Lob
    private Blob banner5;
    
    
	

}
