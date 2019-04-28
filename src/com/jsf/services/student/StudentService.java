package com.jsf.services.student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.jsf.beans.student.StudentBean;
import com.jsf.tools.MyConnection;
//http://localhost:8080/JSFHello/faces/studentsList.xhtml
//http://localhost:8080/JSFHello/faces/application.xhtml
public class StudentService {
	
	public static MyConnection myConnection = new MyConnection();
	
    public static Statement stmtObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;


	public static ArrayList getStudentsListFromDB() {
        ArrayList studentsList = new ArrayList();  
        try {
            stmtObj = myConnection.getConnection().createStatement();      
            resultSetObj = stmtObj.executeQuery("select * from student_record");    
            while(resultSetObj.next()) {  
                StudentBean stuObj = new StudentBean(); 
                stuObj.setId(resultSetObj.getInt("student_id"));  
                stuObj.setName(resultSetObj.getString("student_name"));  
                stuObj.setEmail(resultSetObj.getString("student_email"));  
                stuObj.setPassword(resultSetObj.getString("student_password"));  
                stuObj.setGender(resultSetObj.getString("student_gender"));  
                stuObj.setAddress(resultSetObj.getString("student_address"));  
                studentsList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + studentsList.size());
            myConnection.connection.close();   
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return studentsList;
    }
 
    public static String saveStudentDetailsInDB(StudentBean newStudentObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = myConnection.getConnection().prepareStatement("insert into student_record (student_name, student_email, student_password, student_gender, student_address) values (?, ?, ?, ?, ?)");
        	pstmt.setString(1, newStudentObj.getName());
            pstmt.setString(2, newStudentObj.getEmail());
            pstmt.setString(3, newStudentObj.getPassword());
            pstmt.setString(4, newStudentObj.getGender());
            pstmt.setString(5, newStudentObj.getAddress());
            saveResult = pstmt.executeUpdate();
            myConnection.connection.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "studentsList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createStudent.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editStudentRecordInDB(int studentId) {
        StudentBean editRecord = null;
        System.out.println("editStudentRecordInDB() : Student Id: " + studentId);
 
        /* Setting The Particular Student Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = myConnection.getConnection().createStatement();       
            resultSetObj = stmtObj.executeQuery("select * from student_record where student_id = "+studentId);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new StudentBean(); 
                editRecord.setId(resultSetObj.getInt("student_id"));
                editRecord.setName(resultSetObj.getString("student_name"));
                editRecord.setEmail(resultSetObj.getString("student_email"));
                editRecord.setGender(resultSetObj.getString("student_gender"));
                editRecord.setAddress(resultSetObj.getString("student_address"));
                editRecord.setPassword(resultSetObj.getString("student_password")); 
            }
            sessionMapObj.put("editRecordObj", editRecord);
            myConnection.connection.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editStudent.xhtml?faces-redirect=true";
    }
 
    public static String updateStudentDetailsInDB(StudentBean updateStudentObj) {
        try {
            pstmt = myConnection.getConnection().prepareStatement("update student_record set student_name=?, student_email=?, student_password=?, student_gender=?, student_address=? where student_id=?");      
            pstmt.setString(1,updateStudentObj.getName());  
            pstmt.setString(2,updateStudentObj.getEmail());  
            pstmt.setString(3,updateStudentObj.getPassword());  
            pstmt.setString(4,updateStudentObj.getGender());  
            pstmt.setString(5,updateStudentObj.getAddress());  
            pstmt.setInt(6,updateStudentObj.getId());  
            pstmt.executeUpdate();
            myConnection.connection.close();    
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/studentsList.xhtml?faces-redirect=true";
    }
 
    public static String deleteStudentRecordInDB(int studentId){
        System.out.println("deleteStudentRecordInDB() : Student Id: " + studentId);
        try {
            pstmt = myConnection.getConnection().prepareStatement("delete from student_record where student_id = "+studentId);
            pstmt.executeUpdate();  
            myConnection.connection.close();

        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/studentsList.xhtml?faces-redirect=true";
    }
}
