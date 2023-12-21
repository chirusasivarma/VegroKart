package com.example.VegroKart.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.Banner;
import com.example.VegroKart.Entity.BannerResponse;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.BannerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/banner")
public class BannerController {
	
	@Autowired
    public BannerService bannerService;

    @PostMapping("/save")
    public ResponseEntity<ResponseBody<Banner>> saveBanner(@RequestParam("banner1") MultipartFile banner1, @RequestParam("banner2") MultipartFile banner2,
			@RequestParam("banner3") MultipartFile banner3,@RequestParam("banner4") MultipartFile banner4,@RequestParam("banner5") MultipartFile banner5, HttpServletRequest request)
			throws IOException, SerialException, SQLException {
    	
    	Banner banner = bannerService.saveBanner(request, banner1, banner2, banner3,banner4,banner5);
        ResponseBody<Banner> bannerbody = new ResponseBody<Banner>();
        bannerbody.setStatusCode(HttpStatus.OK.value());
        bannerbody.setStatus("SUCCESS");
        bannerbody.setData(banner);
        return ResponseEntity.ok(bannerbody);   
        
    }
    
   


}
