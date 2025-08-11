package com.coderscampus.Assignment15.dto;

import java.time.LocalDate;

import com.coderscampus.Assignment15.validation.BirthDateConstraint;
import com.coderscampus.Assignment15.validation.PasswordMatches;
import com.coderscampus.Assignment15.validation.UniqueEmail;
import com.coderscampus.Assignment15.validation.UniqueUsername;
import com.coderscampus.Assignment15.validation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordMatches
public class UserRegistrationDto {
	
	@UniqueUsername
	private String username;
	
	@UniqueEmail
	private String email;
	
	@ValidPassword
	private String password;
	
	@NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
	
	@BirthDateConstraint
	private LocalDate dateOfBirth;


	@Pattern(
		    regexp = "^\\+?1?\\s*\\(?[2-9][0-9]{2}\\)?[-.\\s]?[0-9]{3}[-.\\s]?[0-9]{4}$",
		    message = "Please enter a valid US phone number"
		)
	private String phoneNumber;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "UserRegistrationDto [username=" + username + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", dateOfBirth=" + dateOfBirth + ", phoneNumber="
				+ phoneNumber + "]";
	}
}

