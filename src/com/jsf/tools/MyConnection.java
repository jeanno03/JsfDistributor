package com.jsf.tools;
//https://examples.javacodegeeks.com/enterprise-java/jsf/jsf-crud-application-example/
//pour bootstrap ==> créer répertoire resources dans WebContent puis télécharger bootstrap-3.3.7 (ou bootstrap 4)
//dézipper puis dans répertoire dist prendre les dossiers css, fonts et js puis les copier dans resources (ou css et js)
//https://examples.javacodegeeks.com/enterprise-java/jsf/jsf-bootstrap-example/
//http://localhost:8080/JSFHello/faces/application.xhtml
//http://localhost:8080/JSFHello/faces/studentsList.xhtml
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	
	public static Connection connection;
	
	public static Connection getConnection() {
		
	      String url = "jdbc:postgresql://localhost/students";
	      String user = "postgres";
//	      String password ="Afpa03!!";
	      String password ="adm";
	      
	      try {
	    	  Class.forName("org.postgresql.Driver");
	    	  connection = DriverManager.getConnection(url, user, password);
	         System.out.println("Connection completed.");
	      } catch (SQLException ex) {
	         System.out.println(ex.getMessage());
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      finally {
	      }
	      return connection;
	   }

}
