package com.jsf.controllers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.jsf.beans.CoinsSession;
import com.jsf.beans.MoneyReturn;
import com.jsf.classes.ContainerBean;
import com.jsf.classes.DistributorBean;
import com.jsf.classes.DrinkBean;
import com.jsf.constants.MyConstant;
import com.jsf.daos.ContainerDao;
import com.jsf.daos.DistributorDao;
import com.jsf.daos.DrinkDao;
import com.jsf.daos.GenericityDao;
import com.jsf.daos.SpecificDao;
import com.jsf.enums.Coins;
import com.jsf.services.MyServices;
import com.jsf.services.MyServicesImpl;
import com.jsf.services.student.StudentService;

@ManagedBean
@SessionScoped
public class MyController extends MySuperController implements Serializable{

	public static SpecificDao SpecificDao = new SpecificDao();
	public static MyServices myServices = new MyServicesImpl();

	private static DistributorBean distributorBean;
	private CoinsSession coinsSession = null;
	private float difference;

	public MyController() {
		super();
	}

	public String goToDistributor() {	
		initDistributor();
			getMoney();
			difference = 0f;
			displayDifference();
		return "/distributor.xhtml?faces-redirect=true";
	}

	private void initDistributor() {
		distributorBean=new DistributorBean();
		distributorBean=getDistributor();
		insert100CoinsToDistributorBean(Coins.UN_CENTIME);
		insert100CoinsToDistributorBean(Coins.DEUX_CENTIMES);
		insert100CoinsToDistributorBean(Coins.CINQ_CENTIMES);
		insert100CoinsToDistributorBean(Coins.DIX_CENTIMES);
		insert100CoinsToDistributorBean(Coins.VINGT_CENTIMES);
		insert100CoinsToDistributorBean(Coins.CINQUANTE_CENTIMES);
		insert100CoinsToDistributorBean(Coins.UN_EUROS);
		insert100CoinsToDistributorBean(Coins.DEUX_EUROS);
	}

	private void insert100CoinsToDistributorBean(Coins coins) {
		for(int i=0;i<100;i++) {
			distributorBean.setCoinsHash(coins);
		}
	}

	public void startInsert() {
		coinsSession = new CoinsSession();
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
		distributorBean.setCoinsHash(Coins.UN_CENTIME);
	}

	public void insertDeuxCents() {   	
		distributorBean.setCoinsHash(Coins.DEUX_CENTIMES);
	}	

	public void insertCinqCents() {   	
		distributorBean.setCoinsHash(Coins.CINQ_CENTIMES);
	}

	public void insertDixCents() {   	
		distributorBean.setCoinsHash(Coins.DIX_CENTIMES);
	}	

	public void insertVingtCents() {   	
		distributorBean.setCoinsHash(Coins.VINGT_CENTIMES);
	}	

	public void insertCinquanteCents() {   	
		distributorBean.setCoinsHash(Coins.CINQUANTE_CENTIMES);
	}

	public void insertUnEuro() {   	
		distributorBean.setCoinsHash(Coins.UN_EUROS);
	}	

	public void insertDeuxEuros() {   	
		distributorBean.setCoinsHash(Coins.DEUX_EUROS);
	}	

	public boolean distributorAmountEnought() {

		boolean more150 = false;

		if(distributorBean.getAmount()>=150) {			
			more150 = true;
		}
		else {
			more150 = false;
		}
		return more150;
	}
	
	private void addDifference(Coins coins) {
		difference +=coins.getValeur();
		displayDifference();
	}

	public void insertUnCent2() {   
		this.coinsSession.setInsert(Coins.UN_CENTIME);
		addDifference(Coins.UN_CENTIME);
	}

	public void insertDeuxCents2() {   	
		coinsSession.setInsert(Coins.DEUX_CENTIMES);
		addDifference(Coins.DEUX_CENTIMES);
	}	

	public void insertCinqCents2() {   
		coinsSession.setInsert(Coins.CINQ_CENTIMES);
		addDifference(Coins.CINQ_CENTIMES);
	}

	public void insertDixCents2() { 
		coinsSession.setInsert(Coins.DIX_CENTIMES);
		addDifference(Coins.DIX_CENTIMES);
	}	

	public void insertVingtCents2() {   
		coinsSession.setInsert(Coins.VINGT_CENTIMES);
		addDifference(Coins.VINGT_CENTIMES);
	}	

	public void insertCinquanteCents2() {   	
		coinsSession.setInsert(Coins.CINQUANTE_CENTIMES);
		addDifference(Coins.CINQUANTE_CENTIMES);
	}

	public void insertUnEuro2() {   
		coinsSession.setInsert(Coins.UN_EUROS);
		addDifference(Coins.UN_EUROS);
	}	

	public void insertDeuxEuros2() { 
		coinsSession.setInsert(Coins.DEUX_EUROS);
		addDifference(Coins.DEUX_EUROS);
	}	

	public void getDrink(String drinkName) {

		//on rajoute l'argent à la caisse
		List<Coins> coins = coinsSession.getCoinsList();
		for(int i=0;i<coins.size();i++) {
			distributorBean.setCoinsHash(coins.get(i));
		}

		difference = getDifference(drinkName);
		
		//list de coins a rendre
		List<Coins> myCoins = myServices.getMonnaie(difference);
		coinsSession = new CoinsSession();
		coinsSession.setCoinsList(myCoins);
		getMoney();
		displayDifference();
		//on retire la monnaie rendu du distributeur
		distributorBean.removeCoinsHash(myCoins);

	}

	public void recupArgent() {
		coinsSession = null;
		difference = 0f;
		displayDifference();
	}

	public float getDifference(String drinkName) {
		
		float difference = 0f;
		
			try {
				List<ContainerBean> containersBean = distributorBean.getContainers();

				float drinkPrice =0f;
				for(int i=0;i<containersBean.size();i++) {
					if(containersBean.get(i).getDrink().getName().equals(drinkName)) {
						drinkPrice=containersBean.get(i).getDrink().getPrice();
						break;
					}
				}
				float insertMonnai = coinsSession.getInsert();
				difference = insertMonnai - drinkPrice ;
				}catch(Exception ex) {
					ex.printStackTrace();
				}
		
		return difference;
	}
	
	public float displayDifference() {
		return difference;
	}
	
	public List<MoneyReturn> getMoney(){
		List<MoneyReturn>  moneyReturn = new ArrayList();
		try {
		for(int i=0;i<coinsSession.getCoinsList().size();i++) {
			if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.UN_CENTIME)){
				MoneyReturn m = new MoneyReturn("unCent.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.DEUX_CENTIMES)){
				MoneyReturn m = new MoneyReturn("deuxCents.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.CINQ_CENTIMES)){
				MoneyReturn m = new MoneyReturn("cinqCents.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.DIX_CENTIMES)){
				MoneyReturn m = new MoneyReturn("dixCents.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.VINGT_CENTIMES)){
				MoneyReturn m = new MoneyReturn("vingtCents.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.CINQUANTE_CENTIMES)){
				MoneyReturn m = new MoneyReturn("cinquanteCents.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.UN_EUROS)){
				MoneyReturn m = new MoneyReturn("unEuro.jpeg");
				moneyReturn.add(m);
			}
			else if(testCoinsList(coinsSession.getCoinsList().get(i), Coins.DEUX_EUROS)){
				MoneyReturn m = new MoneyReturn("deuxEuros.jpeg");
				moneyReturn.add(m);
			}
		
		}	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return moneyReturn;
		
	}

}
