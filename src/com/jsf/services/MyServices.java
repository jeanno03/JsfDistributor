package com.jsf.services;

import java.util.List;

import com.jsf.enums.Coins;

public interface MyServices {
	
	List<Coins> getMonnaie(float amount);

}
