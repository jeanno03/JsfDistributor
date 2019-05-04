package com.jsf.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
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

	private Map<Coins, Integer> coinsHashMap = new HashMap();

	private List<ContainerBean> containers;
	private Set<MyMessage> myMessages;

	public DistributorBean() {
		super();
		coinsHashMap = new HashMap();
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

	public float getAmount() {		
		return this.amount+ regularizeAmount();	 
	}

	public void setCoinsHash(Coins coins) {
		int oldAmount = 0;		
		try {
			oldAmount = coinsHashMap.get(coins);
			coinsHashMap.remove(coins);				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		coinsHashMap.put(coins, oldAmount+1);
	}

	public void removeCoinsHash(List<Coins> coinsList) {	
	for(int i=0;i<coinsList.size();i++) {
		int oldAmount = coinsHashMap.get(coinsList.get(i));
		coinsHashMap.put(coinsList.get(i), oldAmount-1);
	}
	}

	private float regularizeAmount() {
		float sum = 0f;
		try {
		for(Map.Entry<Coins,Integer> entry : coinsHashMap.entrySet()) {
			sum += (entry.getKey().getValeur() * entry.getValue());

		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return sum;
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
