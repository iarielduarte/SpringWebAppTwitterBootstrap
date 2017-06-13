package ar.com.api.ibera.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCrypt {
	
	public static void main(String[] args){
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("user"));
	}

}
