package com.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.jsf.enums.Coins;
import com.jsf.tools.FloatConverterInterfaceImpl;

public class CoinsSession{
	
	private float amount = 0f;
	private List<Coins> coinsList = new ArrayList();

	public CoinsSession() {
		super();
		amount = 0f;
		coinsList = new ArrayList();
	}
	
	public void setInsert(Coins coins){
		this.coinsList.add(coins);
	}

	public float getAmount() {
		Stream <Coins> coinsStream = this.coinsList.stream();
		amount = coinsStream.map(x->FloatConverterInterfaceImpl.getFloatWithTwoDecimal(x.getValeur())).reduce(0f, Float ::sum);
		return FloatConverterInterfaceImpl.getFloatWithTwoDecimal(amount);
	}

	public List<Coins> getCoinsList() {
		return coinsList;
	}

	public void setCoinsList(List<Coins> coinsList) {
		this.coinsList = coinsList;
	}
	
	

}
