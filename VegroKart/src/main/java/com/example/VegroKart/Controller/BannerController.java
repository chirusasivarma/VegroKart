package com.example.VegroKart.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Entity.Banner;
import com.example.VegroKart.Entity.BannerResponse;
import com.example.VegroKart.Entity.Fruits;
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

    
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseBody<BannerResponse>> getBannerById(@PathVariable("id") long id) {
        BannerResponse bannerResponse = bannerService.getBannerById(id);
        ResponseBody<BannerResponse> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData(bannerResponse);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseBody<Banner>> updateBannerById(@PathVariable Long id,
            @RequestParam("banner1") MultipartFile banner1, @RequestParam("banner2") MultipartFile banner2,
            @RequestParam("banner3") MultipartFile banner3, @RequestParam("banner4") MultipartFile banner4,
            @RequestParam("banner5") MultipartFile banner5) throws IOException, SQLException {
        Banner updatedBanner = bannerService.updateBannerById(id, banner1, banner2, banner3, banner4, banner5);
        ResponseBody<Banner> bannerbody = new ResponseBody<>();
        bannerbody.setStatusCode(HttpStatus.OK.value());
        bannerbody.setStatus("SUCCESS");
        bannerbody.setData(updatedBanner);
        return ResponseEntity.ok(bannerbody); 
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBody<String>> deleteBannerById(@PathVariable Long id) {
        bannerService.deleteBannerById(id);
        ResponseBody<String> bannerbody = new ResponseBody<>();
        bannerbody.setStatusCode(HttpStatus.OK.value());
        bannerbody.setStatus("SUCCESS");
        bannerbody.setData("Banner with id " + id + " deleted successfully");
        return ResponseEntity.ok(bannerbody); 
        
    }
    @GetMapping("/displayImage")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> displayBannerImage(@RequestParam("id") Integer id,
                                                     @RequestParam("bannerNumber") int bannerNumber) throws SQLException {
        Banner banner = bannerService.getImageViewById(id);
        byte[] imageBytes;

        if (banner != null) {
            switch (bannerNumber) {
            
                case 1:
                    imageBytes = banner.getBanner1().getBytes(1, (int) banner.getBanner1().length());
                break;
                case 2:
                    imageBytes = banner.getBanner2().getBytes(1, (int) banner.getBanner2().length());
                    break;
                case 3:
                    imageBytes = banner.getBanner3().getBytes(1, (int) banner.getBanner3().length());
                    break;
                case 4:
                    imageBytes = banner.getBanner4().getBytes(1, (int) banner.getBanner4().length());
                    break;
                case 5:
                    imageBytes = banner.getBanner5().getBytes(1, (int) banner.getBanner5().length());
                    break;
                default:
             
                    return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
