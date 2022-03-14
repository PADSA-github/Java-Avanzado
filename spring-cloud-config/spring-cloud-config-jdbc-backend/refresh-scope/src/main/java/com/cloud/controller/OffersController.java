package com.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class OffersController {
	
	@Value("${offers.discount:35%}")
	private String discount;
	
	@Value("${mi.string: Hola Mundo}")
	private String hello;
	
	
	@GetMapping("/")
	public String viewDiscounts() {
		return "Descuento: " + discount + " " + hello;
	}
}
