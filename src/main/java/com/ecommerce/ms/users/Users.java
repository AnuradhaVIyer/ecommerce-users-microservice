//User Entity

package com.ecommerce.ms.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;

@Entity
public class Users {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long userId;
 private String name;
 private String email;
 
 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Allow in request payloads
 private String password;
 private String address;
 private String phoneNumber;
 
 @Enumerated(EnumType.STRING)
 private Role role = Role.USER; // Default to USER

 public enum Role {
     ADMIN, USER
 }

 // Constructors
 public Users() {}

 public Users(String name, String email, String password, String address, String phoneNumber, String role) {
     this.name = name;
     this.email = email;
     this.password = password;
     this.address = address;
     this.phoneNumber = phoneNumber;
     this.role = Role.valueOf(role);
 }
 
 
 // Getters and Setters
 public Long getUserId() { return userId; }
 public void setUserId(Long userId) { this.userId = userId; }

 public String getName() { return name; }
 public void setName(String name) { this.name = name; }

 public String getEmail() { return email; }
 public void setEmail(String email) { this.email = email; }

 public String getPassword() { return password; }
 public void setPassword(String password) { this.password = password; }

 public String getAddress() { return address; }
 public void setAddress(String address) { this.address = address; }

 public String getPhoneNumber() { return phoneNumber; }
 public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
 
 public Role getRole() { return role; }
 public void setRole(Role role) { this.role = role; }
 
 @Override
 public String toString() {
     try {
         return new ObjectMapper().writeValueAsString(this);
     } catch (JsonProcessingException e) {
         return super.toString(); // Fallback if serialization fails
     }
 }
 
}