package com.jsf.classes;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

public class ContainerBean {
	
	private int id;
	private DrinkBean drink;
	private int amount;
	
	public ContainerBean() {
		super();
	}
	
	public ContainerBean(int id, int amount) {
		super();
		this.id = id;
		this.amount = amount;
	}

	public ContainerBean(DrinkBean drink, int amount) {
		super();
		this.drink = drink;
		this.amount = amount;
	}

	public ContainerBean(int id, DrinkBean drink, int amount) {
		super();
		this.id = id;
		this.drink = drink;
		this.amount = amount;
	}

	public ContainerBean(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DrinkBean getDrink() {
		return drink;
	}

	public void setDrink(DrinkBean drink) {
		this.drink = drink;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
