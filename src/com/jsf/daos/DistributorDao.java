package com.jsf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jsf.beans.ContainerBean;
import com.jsf.beans.DistributorBean;
import com.jsf.tools.MyConnection;

public class DistributorDao implements GenericityDao{


	public static MyConnection myConnection = new MyConnection();

	public static Statement statement;
	public static ResultSet resultSet;
	public static PreparedStatement preparedStatement;
	public static Connection connection;

	@Override
	public DistributorBean getObjectById(String name) {

		int distributor_id = 0;
		String distributor_name = null;
		float distributor_amount = 0f;

		try {
			
			connection = myConnection.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM distributor WHERE distributor_name = ?");
			preparedStatement.setString(1,name);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				distributor_id = resultSet.getInt("distributor_id");
				distributor_name = resultSet.getString("distributor_name");
				distributor_amount = resultSet.getFloat("distributor_amount");

			}

            System.out.println("DistributorDao.getObjectById() - Total Records Fetched: 1" );
            myConnection.connection.close();  
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		DistributorBean distributorBean = new DistributorBean(distributor_id, distributor_name, distributor_amount);
		return distributorBean;

	}


	@Override
	public List<DistributorBean> getObjects() {
		List<DistributorBean>  distributors = new ArrayList();
		try {					
			statement = myConnection.getConnection().createStatement();
			resultSet = statement.executeQuery("select * from distributor");
			while(resultSet.next()) {
				DistributorBean distributorBean = new DistributorBean(
						resultSet.getInt("distributor_id"),
						resultSet.getString("distributor_name"),
						resultSet.getFloat("distributor_amount")
						);
				distributors.add(distributorBean);
			}
			
            System.out.println("DistributorDao.getObjects() Total Records Fetched: " + distributors.size());
            myConnection.connection.close(); 
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		return distributors;
	}

}
