package com.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.jsf.enums.Coins;
import com.jsf.enums.MyMessage;

public class DistributorBean {
	
	private int id;
	private String name;
	private float amount;
	private float toRemove;
	//on insere des coins et on obtient la caisse en float ==> amount
	private List<Coins> coinsList = new ArrayList();
	
	private List<ContainerBean> containers;
	private Set<MyMessage> myMessages;
	
	public DistributorBean() {
		super();
	}

	public DistributorBean(String name, float amount) {
		super();
		this.name = name;
		this.amount = amount;
	}

	public DistributorBean(int id, String name, float amount) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setInsert(Coins coins){
		this.coinsList.add(coins);
	}
	
	public void setListInsert(List<Coins> coins) {
		coins.parallelStream().collect(Collectors.toCollection(()->coinsList));
		//Equivalent
//		ListIterator iteratorCoins = coins.listIterator();
//		while(iteratorCoins.hasNext()) {
//			coinsList.add((Coins) iteratorCoins.next());
//		}
	}
	
	public void removeCoins(List<Coins> coins) {

		Stream <Coins> coinsStream = this.coinsList.stream();
		float cumul = coinsStream.map(x->x.getValeur()).reduce(0f, Float ::sum);
		this.toRemove=cumul;

	}

	public float getAmount() {
		float cumul = getInsertAmount();
		return this.amount + cumul - this.toRemove;
	}
	
	private float getInsertAmount() {
		Stream <Coins> coinsStream = this.coinsList.stream();
		float cumul = coinsStream.map(x->x.getValeur()).reduce(0f, Float ::sum);
		return cumul;
	}

	public List<ContainerBean> getContainers() {
		return containers;
	}

	public void setContainers(List<ContainerBean> containers) {
		this.containers = containers;
	}

	public Set<MyMessage> getMyMessages() {
		return myMessages;
	}
	
	public void setMyMessages(Set<MyMessage> myMessages) {
		this.myMessages = myMessages;
	}

}
