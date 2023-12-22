package com.example.VegroKart.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.Banner;
import com.example.VegroKart.Entity.BannerResponse;
import com.example.VegroKart.Repository.BannerRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BannerService {
	
	@Autowired
	private BannerRepository bannerRepository;

	public Banner saveBanner(HttpServletRequest request, MultipartFile banner1, MultipartFile banner2,
			MultipartFile banner3,MultipartFile banner4,MultipartFile banner5) throws IOException, SerialException, SQLException {
		byte[] bytes1 = banner1.getBytes();
		byte[] bytes2 = banner2.getBytes();
		byte[] bytes3 = banner3.getBytes();
		byte[] bytes4 = banner4.getBytes();
		byte[] bytes5 = banner5.getBytes();
		
        SerialBlob blob1 = new javax.sql.rowset.serial.SerialBlob(bytes1);
        SerialBlob blob2 = new javax.sql.rowset.serial.SerialBlob(bytes2);
        SerialBlob blob3 = new javax.sql.rowset.serial.SerialBlob(bytes3);
        SerialBlob blob4 = new javax.sql.rowset.serial.SerialBlob(bytes4);
        SerialBlob blob5 = new javax.sql.rowset.serial.SerialBlob(bytes5);
        
		Banner banner = new Banner();
		
		banner.setBanner1(blob1);
		banner.setBanner2(blob2);
		banner.setBanner3(blob3);
		banner.setBanner4(blob4);
		banner.setBanner5(blob5);
		
		log.info(banner1.getSize()+"  "+banner1.getBytes());
		log.info(banner2.getSize()+"  "+banner2.getBytes());
		log.info(banner3.getSize()+"  "+banner3.getBytes());
		log.info(banner4.getSize()+"  "+banner4.getBytes());
		log.info(banner5.getSize()+"  "+banner5.getBytes());
		
		return bannerRepository.save(banner);
	}

	public List<BannerResponse> getAllBanner() {
		List<Banner> banner = bannerRepository.findAll();
		
		List<BannerResponse> bannerResponses = new ArrayList<>();

		banner.forEach(e -> {
			BannerResponse bannerResponse = new BannerResponse();
			bannerResponse.setId(e.getId());
			
			try {
				byte[] Banner1Bytes = e.getBanner1().getBytes(1, (int) e.getBanner1().length());
				byte[] Banner2Bytes = e.getBanner2().getBytes(1, (int) e.getBanner2().length());
				byte[] Banner3Bytes = e.getBanner3().getBytes(1, (int) e.getBanner3().length());
				byte[] Banner4Bytes = e.getBanner4().getBytes(1, (int) e.getBanner4().length());
				byte[] Banner5Bytes = e.getBanner5().getBytes(1, (int) e.getBanner5().length());
				String customImageUrl = "/banner/get all?id=" + e.getId(); // Custom URL

				bannerResponse.setBanner1(customImageUrl);
				bannerResponse.setBanner2(customImageUrl);
				bannerResponse.setBanner3(customImageUrl);
				bannerResponse.setBanner4(customImageUrl);
				bannerResponse.setBanner5(customImageUrl);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bannerResponses.add(bannerResponse);
		});
		return bannerResponses;

	}

	public List<Banner> getAllBanners() {
		return bannerRepository.findAll();
	}

	}
		
	