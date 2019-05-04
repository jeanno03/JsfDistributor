package com.jsf.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jsf.classes.ContainerBean;
import com.jsf.classes.DrinkBean;
import com.jsf.tools.MyConnection;

public class DrinkDao implements GenericityDao{
	
	public static MyConnection myConnection = new MyConnection();
	
    public static Statement statement;
    public static ResultSet resultSet;
    public static PreparedStatement preparedStatement;

	@Override
	public DrinkBean getObjectById(String drink_name) {
		DrinkBean drinkBean = new DrinkBean();
    	try {
    		preparedStatement = MyConnection.getConnection().prepareStatement(
    				"select * from drink where drink_name = ?");
    		preparedStatement.setString(1,drink_name);
    		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {				
				
				drinkBean.setName(resultSet.getString("drink_name"));
				drinkBean.setPrice(resultSet.getFloat("drink_price"));
			}
            System.out.println("DrinkDao.getObjectById() - Total Records Fetched: 1" );
            myConnection.connection.close();   	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	return drinkBean;
	}

	@Override
	public Collection<DrinkBean> getObjects() {
    	List<DrinkBean> drinksList = new ArrayList();
    	
    	try {
    		statement = MyConnection.getConnection().createStatement();
    		resultSet = statement.executeQuery("select * from drink");
			while(resultSet.next()) {
				DrinkBean drinkObj = new DrinkBean();
				drinkObj.setName(resultSet.getString("drink_name"));
				drinkObj.setPrice(resultSet.getFloat("drink_price"));
				drinksList.add(drinkObj);			
			}
			
            System.out.println("DrinkDao.getObjects() Total Records Fetched: " + drinksList.size());
            myConnection.connection.close();   	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
    	return drinksList;
    }

}
