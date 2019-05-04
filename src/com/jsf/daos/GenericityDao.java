package com.jsf.daos;

import java.util.Collection;
import java.util.List;

import com.jsf.classes.DrinkBean;

public interface GenericityDao <T extends Object>{
	
	T getObjectById(String id);
	
	Collection<T> getObjects();

}
