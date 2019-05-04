package com.jsf.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.jsf.classes.ContainerBean;
import com.jsf.classes.DrinkBean;
import com.jsf.tools.MyConnection;

public class ContainerDao implements GenericityDao{
	
	public static MyConnection myConnection = new MyConnection();
	
    public static Statement statement;
    public static ResultSet resultSet;
    public static PreparedStatement preparedStatement;

	@Override
	public Object getObjectById(String id) {
		// TODO Auto-generated method stub
		return null;
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

				ContainerBean containerBean = new ContainerBean(id, amount);			
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
