package com.jsf.controllers;

import java.util.List;

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

public class MySuperController {
	
	public static GenericityDao drinkDao = new DrinkDao();
	public static GenericityDao containerDao = new ContainerDao();
	public static GenericityDao distributorDao = new DistributorDao();
	public static SpecificDao SpecificDao = new SpecificDao();
	
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
		SpecificDao.generateDistributor(containersBean, MyConstant.DISTRIBUTOR_NAME);
	}

	//inutile ==> non utilisé
	public List<ContainerBean> getContainersBeanFromDistributor(){
		List<ContainerBean> containersBean = getDistributor().getContainers();
		return containersBean;
	}
	
	public DistributorBean getDistributor() {
		DistributorBean distributorBean = (DistributorBean) distributorDao.getObjectById(MyConstant.DISTRIBUTOR_NAME);		
		//contains ContainerBean + DrinkBean
		List<ContainerBean> containersBean = SpecificDao.getContainersByDistributor(distributorBean.getId());		
		distributorBean.setContainers(containersBean);
		return distributorBean;
	}
	
	public boolean testCoinsList(Coins coin1, Coins coin2) {
		boolean test = true;
		if(coin1 != coin2) {
			test = false;
		}
		return test;
	}

}
