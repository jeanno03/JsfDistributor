package com.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.jsf.enums.Coins;

@ManagedBean
@SessionScoped
public class CoinsSession {
	
	private float insert = 0f;
	private List<Coins> coinsList = new ArrayList();

	public CoinsSession() {
		super();
		insert = 0f;
		coinsList = new ArrayList();
	}
	
	public void setInsert(Coins coins){
		this.coinsList.add(coins);
	}

	public float getInsert() {
		Stream <Coins> coinsStream = this.coinsList.stream();
		insert = coinsStream.map(x->x.getValeur()).reduce(0f, Float ::sum);
		return insert;
	}

	public List<Coins> getCoinsList() {
		return coinsList;
	}

	public void setCoinsList(List<Coins> coinsList) {
		this.coinsList = coinsList;
	}
	
	

}
