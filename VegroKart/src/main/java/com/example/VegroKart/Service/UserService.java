package com.example.VegroKart.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Dto.ChangePassword;
import com.example.VegroKart.Dto.CombinedAuthResponse;
import com.example.VegroKart.Dto.Login;
import com.example.VegroKart.Dto.LoginDto;
import com.example.VegroKart.Dto.OtpResponseDto;
import com.example.VegroKart.Dto.Registration;
import com.example.VegroKart.Dto.ResetPassword;
import com.example.VegroKart.Dto.UserDto;
import com.example.VegroKart.Entity.MyAddress;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Helper.EncryptionService;
import com.example.VegroKart.Repository.UserRepository;
import com.example.VegroKart.SecurityConfiguration.JwtService;
import com.example.VegroKart.SecurityConfiguration.LocalUserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	public SmsService smsService;
	
	@Autowired
	private JwtService jwtService;
	

	
	//save user details
	public User saveUser(MultipartFile file,String name,String mobileNumber,List<MyAddress> myAddress, String emailAddress, String password) throws SerialException, SQLException, IOException {
		Optional<User> emailOptional=userRepository.findByEmailAddress(emailAddress);
		if (emailOptional.isPresent()) {
			throw new UserIsNotFoundException("user is already existed with this email addres .");
		}
		Optional<User> mobileOptional=userRepository.findByMobileNumber(mobileNumber);
		if (mobileOptional.isPresent()) {
			throw new UserIsNotFoundException("Mobile number is already registered");
		}
		User user= new User();
		byte[] image=file.getBytes();
		Blob blob= new SerialBlob(image);
		user.setImage(blob);
		user.setName(name);
		user.setEmailAddress(emailAddress);
		user.setMobileNumber(mobileNumber);
		user.setMyAddress(myAddress);
		user.setPassword(encryptionService.encryptPassword(password));
		return userRepository.save(user);
	}
	
	//user registration
    public User userRegistration(Registration registration) {
        
            Optional<User> emailOptional = userRepository.findByEmailAddress(registration.getEmailAddress());
            if (emailOptional.isPresent()) {
                throw new UserIsNotFoundException("User is already existed with this email address.");
            }

            Optional<User> mobileOptional = userRepository.findByMobileNumber(registration.getMobileNumber());
            if (mobileOptional.isPresent()) {
                throw new UserIsNotFoundException("User is already existed with this mobile number.");
            }
            smsService.sendRegistrationOtp(registration);
            User user = new User();
            user.setName(registration.getName());
            user.setEmailAddress(registration.getEmailAddress());
            user.setMobileNumber(registration.getMobileNumber());
            user.setPassword(encryptionService.encryptPassword(registration.getPassword()));
            userRepository.save(user);
            return user;
       
    }
	
    
//	// user login
//	public User loginUser(String mobileNumber , String password) {
//		Optional<User> userOptional=userRepository.findByMobileNumber(mobileNumber);
//		if (userOptional.isPresent()) {
//			User user=userOptional.get();
//			if (encryptionService.verifyPassword(password, user.getPassword())) {
//				return user;
//			}else {
//				throw new UserIsNotFoundException("user password invalide.");
//			}
//		}else {
//			throw new UserIsNotFoundException("this mobile number is not registred.");
//		}
//		
//	}
//	
//	
//// user login and otp
//	 public OtpResponseDto loginUserAndSendOtp(Login login) {
//	        Optional<User> userOptional = userRepository.findByMobileNumber(login.getMobileNumber());
//	        
//	        if (userOptional.isPresent()) {
//	            User user = userOptional.get();
//	            if (encryptionService.verifyPassword(login.getPassword(), user.getPassword())) {
//	                // User login successful, now send OTP
//	                return smsService.sendSMS(login);
//	            } else {
//	                throw new UserIsNotFoundException("User password is invalid.");
//	            }
//	        } else {
//	            throw new UserIsNotFoundException("This mobile number is not registered.");
//	        }
//	    }
//	 
	 

	 public Object authenticateUser(Login login) {
	        Optional<User> userOptional = userRepository.findByMobileNumber(login.getMobileNumber());

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            if (encryptionService.verifyPassword(login.getPassword(), user.getPassword())) {
	                // User login successful

	                // Generate JWT token
	                LocalUserService userDetails = new LocalUserService(user);
	                String jwt = jwtService.generateToken(userDetails);
	                // Send OTP
	                OtpResponseDto otpResponse = smsService.sendSMS(login);
	                CombinedAuthResponse combinedResponse = new CombinedAuthResponse();
	                combinedResponse.setJwtToken(jwt);
	                combinedResponse.setOtpResponse(otpResponse);

	                return combinedResponse;
	            } else {
	                throw new UserIsNotFoundException("Invalid password");
	            }
	        } else {
	            throw new UserIsNotFoundException("This mobile number is not registered.");
	        }
	    }



	 
	 
	
	
	//user resetPassword By userId
		 public User resetPasswordById(long userId, ResetPassword resetPassword) {
		        Optional<User> optionalUser = userRepository.findById(userId);
		        if (optionalUser.isPresent()) {
		            User user = optionalUser.get();

		            if (!resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) {
		                throw new UserIsNotFoundException("Password and confirm password do not match");
		            }

		            user.setPassword(encryptionService.encryptPassword(resetPassword.getPassword()));
		            return userRepository.save(user);
		        } else {
		            throw new UserIsNotFoundException("User not found for user Id: " + userId);
		        }
		    }
	 
	//user resetPassword by user mobile number
	 public User resetPassword(String mobileNumber, ResetPassword resetPassword) {
	        Optional<User> optionalUser = userRepository.findByMobileNumber(mobileNumber);
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();

	            if (!resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) {
	                throw new UserIsNotFoundException("Password and confirm password do not match");
	            }

	            user.setPassword(encryptionService.encryptPassword(resetPassword.getPassword()));
	            return userRepository.save(user);
	        } else {
	            throw new UserIsNotFoundException("User not found for mobile number: " + mobileNumber);
	        }
	    }
	 
	 
	 
	 //user change password
	 public String changePassword(String mobileNumber, ChangePassword changePassword) {
		    Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);
		    if (userOptional.isPresent()) {
		        User user = userOptional.get();

		        String oldPassword = changePassword.getOldPassword();
		        String newPassword = changePassword.getNewPassword();
		        String confirmPassword = changePassword.getConfirmPassword();

		        if (oldPassword == null || !confirmPassword.equals(newPassword) || 
		            !encryptionService.verifyPassword(oldPassword, user.getPassword())) {
		            return "Failed to change password. Please check your input.";
		        }

		        String encryptedPassword = encryptionService.encryptPassword(confirmPassword);
		        user.setPassword(encryptedPassword);
		        userRepository.save(user);

		        return "Password changed successfully!";
		    } else {
		        return "Failed to change password. User not found.";
		    }
		}
	 
//	 public List<UserDto> getAllUser() {
//		 List<User> users=userRepository.findAll();
//		 List<UserDto> userDtos= new ArrayList<UserDto>();
//		 
//		 users.forEach(user ->{
//			 UserDto userDto=new UserDto();
//			 userDto.setName(user.getName());
//			 userDto.setEmailAddress(user.getEmailAddress());
//			 userDto.setMobileNumber(user.getMobileNumber());
//			 userDto.setMyAddress(user.getMyAddress());
//			 userDto.setPassword(user.getPassword());
//			  try {
//				byte[] image=user.getImage().getBytes(1,(int) user.getImage(). length());
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			  String imageUrl="/user/getall/image?id"+user.getId();
//			  userDto.setImage(imageUrl);
//			  userDtos.add(userDto);
//		 });
//		 return userDtos; 
//	 }

	 
	 
	 // getall user details
	 @Transactional
	 public List<UserDto> getAllUsers() {
		    List<User> users = userRepository.findAll();
		    List<UserDto> userDtos = new ArrayList<>();

		    users.forEach(user -> {
		        UserDto userDto = new UserDto();
		        userDto.setId(user.getId());
		        userDto.setName(user.getName());
		        userDto.setEmailAddress(user.getEmailAddress());
		        userDto.setMobileNumber(user.getMobileNumber());
		        userDto.setMyAddress(user.getMyAddress());
		        userDto.setInstantDelivery(user.getInstantDelivery());
				userDto.setOrderForLater(user.getOrderForLater());
				userDto.setMorningDeliveries(user.getMorningDeliveries());
		        userDto.setPassword(user.getPassword());
		        if (user.getImage() != null) {
		            try {
		                Blob imageBlob = user.getImage();
		                if (imageBlob != null) {
		                    byte[] image = imageBlob.getBytes(1, (int) imageBlob.length());
		                    userDto.setImage(Base64.getEncoder().encodeToString(image));
		                } else {
		                    userDto.setImage(null);
		                }
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        } else {
		            userDto.setImage(null);
		        }
		        String imageUrl = "/user/getall/image?id=" + user.getId();
		        userDto.setImage(imageUrl);

		        userDtos.add(userDto);
		    });

		    return userDtos;
		}

	 
	 
	 //get user by id
	 @Transactional
	 public UserDto getUserById(long id) throws SQLException {
		 Optional<User> userOptional=userRepository.findById(id);
		 if (userOptional.isEmpty()) {
			throw new UserIsNotFoundException("user not found with this id :" + id);
		}
		 User user=userOptional.get();
		 UserDto userDto=new UserDto();
		 userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmailAddress(user.getEmailAddress());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setInstantDelivery(user.getInstantDelivery());
		userDto.setOrderForLater(user.getOrderForLater());
		userDto.setMorningDeliveries(user.getMorningDeliveries());
		userDto.setMyAddress(user.getMyAddress());
		userDto.setPassword(encryptionService.encryptPassword(user.getPassword()));
		  if (user.getImage() != null) {
	            try {
	                Blob imageBlob = user.getImage();
	                if (imageBlob != null) {
	                    byte[] image = imageBlob.getBytes(1, (int) imageBlob.length());
	                    userDto.setImage(Base64.getEncoder().encodeToString(image));
	                } else {
	                    userDto.setImage(null);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } else {
	            userDto.setImage(null);
	        }
	        String imageUrl = "/user/getall/image?id=" + user.getId();
	        userDto.setImage(imageUrl);
		return userDto;
	 }
	 
	 @Transactional
	 public UserDto getUserByMobileNumber(String mobileNumber) throws SQLException {
		 Optional<User> userOptional=userRepository.findByMobileNumber(mobileNumber);
		 if (userOptional.isEmpty()) {
			throw new UserIsNotFoundException("user not found with this mobileNumber :" + mobileNumber);
		}
		 User user=userOptional.get();
		 UserDto userDto=new UserDto();
		 userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmailAddress(user.getEmailAddress());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setInstantDelivery(user.getInstantDelivery());
		userDto.setOrderForLater(user.getOrderForLater());
		userDto.setMorningDeliveries(user.getMorningDeliveries());
		userDto.setMyAddress(user.getMyAddress());
		userDto.setPassword(encryptionService.encryptPassword(user.getPassword()));
		  if (user.getImage() != null) {
	            try {
	                Blob imageBlob = user.getImage();
	                if (imageBlob != null) {
	                    byte[] image = imageBlob.getBytes(1, (int) imageBlob.length());
	                    userDto.setImage(Base64.getEncoder().encodeToString(image));
	                } else {
	                    userDto.setImage(null);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } else {
	            userDto.setImage(null);
	        }
	        String imageUrl = "/user/getall/image?id=" + user.getId();
	        userDto.setImage(imageUrl);
		return userDto;
	 }
	 
	  @Transactional
	    public User updateUserById(long id, String name, String emailAddress, String mobileNumber, String password,
	            MultipartFile file) throws IOException, SerialException, SQLException {
	        Optional<User> optionalExistingUser = userRepository.findById(id);

	        if (optionalExistingUser.isEmpty()) {
	            throw new UserIsNotFoundException("user not found with id :" + id);
	        }

	        User existingUser = optionalExistingUser.get();
	        existingUser.setName(name);
	        existingUser.setEmailAddress(emailAddress);
	        existingUser.setMobileNumber(mobileNumber);
	        existingUser.setPassword(encryptionService.encryptPassword(password));

	        if (file != null && !file.isEmpty()) {
	            byte[] bytes = file.getBytes();
	            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
	            existingUser.setImage(blob);
	        }

	        userRepository.save(existingUser);

	        return existingUser;
	    }
	  
	  @Transactional
	    public User updateUser(long id,List<MyAddress> myAddress) throws IOException, SerialException, SQLException {
	        Optional<User> optionalExistingUser = userRepository.findById(id);
	        if (optionalExistingUser.isEmpty()) {
	            throw new UserIsNotFoundException("user not found with id :" + id);
	        }
	        User existingUser = optionalExistingUser.get();
	        existingUser.setMyAddress(myAddress);
	        userRepository.save(existingUser);
	        return existingUser;
	    }

	 
	  public byte[] getImageById(long id) {
	        Optional<User> optionalUser = userRepository.findById(id);
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            try {
	                if (user.getImage() != null) {
	                    return user.getImage().getBytes(1, (int) user.getImage().length());
	                }
	            } catch (SQLException e) { 
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }
	  
	  public void deleteUserById(long id) {
		  Optional<User> userOptional=userRepository.findById(id);
		  if (userOptional.isPresent()) {
			   userRepository.deleteById(id);
		}else {
			 throw new UserIsNotFoundException("user not found with id :" + id);
		}
	  }
	  
	  
	

	    public Optional<User> getUserWithInstantDelivery(Long userId) {
	        return userRepository.findByIdWithInstantDelivery(userId);
	    }


}