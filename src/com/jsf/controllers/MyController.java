package com.jsf.controllers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.jsf.beans.ContainerBean;
import com.jsf.beans.DistributorBean;
import com.jsf.beans.DrinkBean;
import com.jsf.daos.ContainerDao;
import com.jsf.daos.DistributorDao;
import com.jsf.daos.DrinkDao;
import com.jsf.daos.GenericityDao;
import com.jsf.daos.SpecificDao;
import com.jsf.enums.Coins;
import com.jsf.services.student.StudentService;
import com.jsf.sessions.CoinsSession;

@ManagedBean
@SessionScoped
public class MyController implements Serializable{

	public static GenericityDao drinkDao = new DrinkDao();
	public static GenericityDao containerDao = new ContainerDao();
	public static GenericityDao distributorDao = new DistributorDao();
	public static SpecificDao SpecificDao = new SpecificDao();
	public static String distributorName = "Mon distributeur";

	private static DistributorBean distributorBean;

	private CoinsSession coinsSession = null;

	public MyController() {
		super();
	}

	public String goToStudents() {
		return "/studentsList.xhtml?faces-redirect=true";
	}

	public String goToMyApplication() {
		return "/application.xhtml?faces-redirect=true";
	}

	public static List<DrinkBean> getDrinksBean(){

		List<DrinkBean> drinksBean = (List<DrinkBean>) drinkDao.getObjects();
		return drinksBean;
	}

	public void generateContainer() {
		List<DrinkBean> drinksBean = getDrinksBean();
		SpecificDao.generateContainer(drinksBean);
	}

	public void generateDistributor() {

		List<ContainerBean> containersBean = (List<ContainerBean>) containerDao.getObjects();
		SpecificDao.generateDistributor(containersBean, distributorName);
	}


	public String goToDistributor() {	
		initDistributor();
		return "/distributor.xhtml?faces-redirect=true";
	}

	//inutile
	public List<ContainerBean> getContainersBeanFromDistributor(){
		List<ContainerBean> containersBean = getDistributor().getContainers();
		return containersBean;
	}

	private DistributorBean getDistributor() {
		DistributorBean distributorBean = (DistributorBean) distributorDao.getObjectById(distributorName);		
		//contains ContainerBean + DrinkBean
		List<ContainerBean> containersBean = SpecificDao.getContainersByDistributor(distributorBean.getId());		
		distributorBean.setContainers(containersBean);
		return distributorBean;
	}

	private void initDistributor() {
		distributorBean=new DistributorBean();
		distributorBean=getDistributor();

	}

	public void startInsert() {
		System.out.println("startInsert()");
		CoinsSession coinsSession = new CoinsSession();
		this.coinsSession = coinsSession;
	}

	//	@PostConstruct
	//	public void init() {
	//		this.distributorBean = getDistributor() ;
	//	}

	public DistributorBean myDistributor() {
		return this.distributorBean;
	}

	public CoinsSession myCoinSession() {
		return this.coinsSession;
	}

	public void insertUnCent() {   	
		distributorBean.setInsert(Coins.UN_CENTIME);
		System.out.println("on insert 1 cent");
	}

	public void insertDeuxCents() {   	
		distributorBean.setInsert(Coins.DEUX_CENTIMES);
		System.out.println("on insert 2 cents");
	}	

	public void insertCinqCents() {   	
		distributorBean.setInsert(Coins.CINQ_CENTIMES);
		System.out.println("on insert 5 cent");
	}

	public void insertDixCents() {   	
		distributorBean.setInsert(Coins.DIX_CENTIMES);
		System.out.println("on insert 10 cent");
	}	

	public void insertVingtCents() {   	
		distributorBean.setInsert(Coins.VINGT_CENTIMES);
		System.out.println("on insert 20 cent");
	}	

	public void insertCinquanteCents() {   	
		distributorBean.setInsert(Coins.CINQUANTE_CENTIMES);
		System.out.println("on insert 50 cent");
	}

	public void insertUnEuro() {   	
		distributorBean.setInsert(Coins.UN_EUROS);
		System.out.println("on insert 1 Euro");
	}	

	public void insertDeuxEuros() {   	
		distributorBean.setInsert(Coins.DEUX_EUROS);
		System.out.println("on insert 2 Euros");
	}	

	public boolean distributorAmountEnought() {

		boolean more150 = false;

		if(distributorBean.getAmount()>=150) {			
			more150 = true;
			System.out.println("monnai is enought");
		}
		else {
			more150 = false;
		}
		return more150;
	}

	public void insertUnCent2() {   

		this.coinsSession.setInsert(Coins.UN_CENTIME);

		System.out.println("on insert 1 cent v2");
		System.out.println("total : " + coinsSession.getInsert());
	}

	public void insertDeuxCents2() {   	

		coinsSession.setInsert(Coins.DEUX_CENTIMES);

		System.out.println("on insert 2 cents v2");
		System.out.println("total : " + coinsSession.getInsert());
	}	

	public void insertCinqCents2() {   

		coinsSession.setInsert(Coins.CINQ_CENTIMES);

		System.out.println("on insert 5 cent v2");
		System.out.println("total : " + coinsSession.getInsert());
	}

	public void insertDixCents2() { 

		coinsSession.setInsert(Coins.DIX_CENTIMES);

		System.out.println("on insert 10 cent v2");
		System.out.println("total : " + coinsSession.getInsert());
	}	

	public void insertVingtCents2() {   

		coinsSession.setInsert(Coins.VINGT_CENTIMES);

		System.out.println("on insert 20 cent v2");
		System.out.println("total : " + coinsSession.getInsert());
	}	

	public void insertCinquanteCents2() {   	

		coinsSession.setInsert(Coins.CINQUANTE_CENTIMES);

		System.out.println("on insert 50 cent v2");
		System.out.println("total : " + coinsSession.getInsert());
	}

	public void insertUnEuro2() {   

		coinsSession.setInsert(Coins.UN_EUROS);

		System.out.println("on insert 1 Euro v2");
		System.out.println("total : " + coinsSession.getInsert());
	}	

	public void insertDeuxEuros2() { 

		coinsSession.setInsert(Coins.DEUX_EUROS);

		System.out.println("on insert 2 Euros v2");
		System.out.println("total : " + coinsSession.getInsert());
	}	

	public void getDrink(String drinkName) {

		System.out.println("Vous avez choisi : " + drinkName);
		//on rajoute l'argent à la caisse
		List<Coins> coins = coinsSession.getCoinsList();
		distributorBean.setListInsert(coins);

		//on rend la monnaie en pièce de 1 cent :)
		float difference = getDifference(drinkName);
		System.out.println("On rend : " + difference);
		coinsSession = null;
		coinsSession = new CoinsSession();
		List<Coins> returnCoins = returnCoins(difference);	
//		distributorBean.removeCoins(returnCoins);

	}
	
	public void recupArgent() {
		coinsSession = null;
	}

	private float getDifference(String drinkName) {
		List<ContainerBean> containersBean = distributorBean.getContainers();

		float drinkPrice =0f;
		for(int i=0;i<containersBean.size();i++) {
			if(containersBean.get(i).getDrink().getName().equals(drinkName)) {
				drinkPrice=containersBean.get(i).getDrink().getPrice();
				break;
			}
		}

		float insertMonnai = coinsSession.getInsert();
		float difference = insertMonnai - drinkPrice ;
		return difference;
	}
	
	private List<Coins> returnCoins(float difference){

		float nb = difference*100;
		List<Coins> coins = new ArrayList();
		for(int i=0;i<nb;i++) {
			System.out.println(i+")on rend 1 cent");
			coinsSession.setInsert(Coins.UN_CENTIME);
			coins.add(Coins.UN_CENTIME);

		}
		return coins;
	}
}
