package com.jsf.services.distributor;

import java.util.List;

import com.jsf.enums.Coins;

public interface MyServices {
	
	List<Coins> getMonnaie(float amount);

}
