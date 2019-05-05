package com.jsf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.jsf.beans.ContainerBean;
import com.jsf.beans.DistributorBean;
import com.jsf.beans.DrinkBean;
import com.jsf.constants.MyConstant;
import com.jsf.controllers.MyController;
import com.jsf.tools.MyConnection;

public class SpecificDao {

	public static MyConnection myConnection = new MyConnection();

	public static Statement statement;
	public static ResultSet resultSet;
	public static PreparedStatement preparedStatement;
	public static Connection connection;

	public static GenericityDao drinkDao = new DrinkDao();
	public static GenericityDao distributorDao = new DistributorDao();
	public static GenericityDao containerDao = new ContainerDao();

	public void generateContainer(Collection<DrinkBean> drinksBean) {

		truncateTable("container");

		drinksBean.forEach(d->{
			System.out.println("nom de la cannette : " + d.getName());	

			try {

				preparedStatement = myConnection.getConnection().prepareStatement(
						"insert into container(drink_name, container_amount) values (?,?)");
				preparedStatement.setString(1, d.getName());
				preparedStatement.setInt(2, 20);
				int result = preparedStatement.executeUpdate();
				System.out.println("sauvegarde de container : " + result);
				myConnection.connection.close();

			}catch(SQLException ex) {
				ex.printStackTrace();
			}

		});
	}

	public void generateDistributor(List<ContainerBean> containersBean, String distributorName) {

		//		truncateTable("distributor");


		try {
			preparedStatement = myConnection.getConnection().prepareStatement(
					"DELETE FROM distributor WHERE distributor_name =?");
			preparedStatement.setString(1, distributorName);
			int result = preparedStatement.executeUpdate();
			System.out.println("delete distributor : " + result);
			myConnection.connection.close();

		}catch(SQLException ex) {
			ex.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		//création du distrubuteur
		try {
			preparedStatement = myConnection.getConnection().prepareStatement(
					"insert into distributor(distributor_name, distributor_amount) values (?,?)");
			preparedStatement.setString(1, "Mon distributeur");
			preparedStatement.setFloat(2, 0f);
			int result = preparedStatement.executeUpdate();
			System.out.println("sauvegarde de distributor : " + result);
			myConnection.connection.close();

		}catch(SQLException ex) {
			ex.printStackTrace();
		}

		//récupération du distributeur
		DistributorBean distributorBean = (DistributorBean) distributorDao.getObjectById(MyConstant.DISTRIBUTOR_NAME);

		//set du distributeur dans les containers
		containersBean.forEach(c->{
			try {
				preparedStatement = myConnection.getConnection().prepareStatement(
						"UPDATE container set distributor_id = ? where container_id = ?");
				preparedStatement.setInt(1, distributorBean.getId());
				preparedStatement.setInt(2, c.getId());
				int result = preparedStatement.executeUpdate();
				System.out.println("sauvegarde de container : " + result);
				myConnection.connection.close();

			}catch(SQLException ex) {
				ex.printStackTrace();
			}


		});
	}

	public List<ContainerBean> getContainersByDistributor(int distributor_id) {

		List<ContainerBean> containersBean = new ArrayList();

		try {
			preparedStatement = MyConnection.getConnection().prepareStatement(
					"select * from container where distributor_id = ?");
			preparedStatement.setInt(1,distributor_id);

			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {				

				ContainerBean containerBean = new ContainerBean();
				containerBean.setId(resultSet.getInt("container_id"));
				containerBean.setAmount(resultSet.getInt("container_amount"));

				DrinkBean drinkBean = (DrinkBean) drinkDao.getObjectById(resultSet.getString("drink_name"));
				containerBean.setDrink(drinkBean);

				containersBean.add(containerBean);

			}
			System.out.println("SpecificDao.getContainersByDistributor() - Total Records Fetched: 1" );
			myConnection.connection.close();   	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return containersBean;
	}

	// a chaque achat on update le distributor
	public DistributorBean getUpdateDistributor(String drinkName, DistributorBean distributorBeanB) {

		//récupération de tous les containers 
		List<ContainerBean> containersBean = (List<ContainerBean>) containerDao.getObjects();

		//récupération du distributeur
		DistributorBean distributorBeanReturn = (DistributorBean) distributorDao.getObjectById(MyConstant.DISTRIBUTOR_NAME);

		//modification du container
		for(int i=0;i<containersBean.size();i++) {
			try {
				if(containersBean.get(i).getDrink().getName().equals(drinkName)) {
					int newAmount = containersBean.get(i).getAmount() - 1;
					containersBean.get(i).setAmount(newAmount);

					//set du container
					try {
						preparedStatement = myConnection.getConnection().prepareStatement(
								"UPDATE container set container_amount = ? where container_id = ?");
						preparedStatement.setInt(1, newAmount);
						preparedStatement.setInt(2, containersBean.get(i).getId());
						int result = preparedStatement.executeUpdate();
						System.out.println("sauvegarde de container : " + result);
						myConnection.connection.close();

					}catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}

		}

		//reconstruction du distributor
		distributorBeanReturn.setContainers(containersBean);
		
		//Maj de la coinList
		distributorBeanReturn.setCoinsHashMap(distributorBeanB.getCoinsHashMap());
		return distributorBeanReturn;

	}

	private void truncateTable(String table) {
		try {
			connection = myConnection.getConnection();
			statement = connection.createStatement();
			int result = statement.executeUpdate("TRUNCATE " + table + "");
			System.out.println("SpecificDao.truncateTable() - " + table + " : " + result);	
			myConnection.connection.close();

		}catch(SQLException ex) {
			ex.printStackTrace();
		}

	}

}
