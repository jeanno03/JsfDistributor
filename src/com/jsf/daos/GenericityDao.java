package com.jsf.daos;

import java.util.Collection;
import java.util.List;

import com.jsf.beans.DrinkBean;

public interface GenericityDao <T extends Object>{
	
	T getObjectById(String id);
	
	Collection<T> getObjects();

}
