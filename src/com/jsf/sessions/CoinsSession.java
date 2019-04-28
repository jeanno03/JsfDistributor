package com.jsf.sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.jsf.enums.Coins;

@ManagedBean
@SessionScoped
public class CoinsSession {
	
	private float insert;
	private List<Coins> coinsList = new ArrayList();

	public CoinsSession() {
		super();
	}
	
	public void setInsert(Coins coins){
		this.coinsList.add(coins);
	}

	public float getInsert() {
		Stream <Coins> coinsStream = this.coinsList.stream();
		float cumul = coinsStream.map(x->x.getValeur()).reduce(0f, Float ::sum);
		this.insert = cumul;
		return insert;
	}

	public List<Coins> getCoinsList() {
		return coinsList;
	}

	public void setCoinsList(List<Coins> coinsList) {
		this.coinsList = coinsList;
	}
	
	

}
