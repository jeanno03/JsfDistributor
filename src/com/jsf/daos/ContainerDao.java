package com.jsf.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.jsf.beans.ContainerBean;
import com.jsf.beans.DrinkBean;
import com.jsf.tools.MyConnection;

public class ContainerDao implements GenericityDao{
	
	public static MyConnection myConnection = new MyConnection();
	public static GenericityDao drinkDao = new DrinkDao();
	
    public static Statement statement;
    public static ResultSet resultSet;
    public static PreparedStatement preparedStatement;

	@Override
	public Object getObjectById(String drinkName) {
		ContainerBean containerBean = new ContainerBean();
    	try {
    		preparedStatement = MyConnection.getConnection().prepareStatement(
    				"select * from container where drink_name = ?");
    		preparedStatement.setString(1,drinkName);
    		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {	
				
				containerBean.setId(resultSet.getInt("container_id"));
				DrinkBean drinkBean =  (DrinkBean) drinkDao.getObjectById(drinkName);
				containerBean.setDrink(drinkBean);
				containerBean.setAmount(resultSet.getInt("container_amount"));
			}
            System.out.println("ContainerDao.getObjectById() - Total Records Fetched: 1" );
            myConnection.connection.close();   	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	return containerBean;
	}

	@Override
	public List<ContainerBean> getObjects() {
		List<ContainerBean> containersBean = new ArrayList();
    	
    	try {
    		statement = MyConnection.getConnection().createStatement();
    		resultSet = statement.executeQuery("select * from container");
			while(resultSet.next()) {
				
				int id  = resultSet.getInt("container_id");
				int amount = resultSet.getInt("container_amount");
				String drinkName = resultSet.getString("drink_name");
				DrinkBean drinkBean =  (DrinkBean) drinkDao.getObjectById(drinkName);
	
				ContainerBean containerBean = new ContainerBean(id, amount);	
				containerBean.setDrink(drinkBean);
				containersBean.add(containerBean);			
			}
			
            System.out.println("ContainerDao.getObjects() - Total Records Fetched: " + containersBean.size());
            myConnection.connection.close();   	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
    	return containersBean;
    }
	

}
