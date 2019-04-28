package com.jsf.beans.student;
 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jsf.services.student.StudentService;

 
@ManagedBean @RequestScoped
public class StudentBean {
 
    private int id;  
    private String name;  
    private String email;  
    private String password;  
    private String gender;  
    private String address;
 
    private List<StudentBean>studentsListFromDB;
 
    public int getId() {
        return id;  
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }  
     
    @PostConstruct
    public void init() {
        studentsListFromDB = StudentService.getStudentsListFromDB();
    }
 
    public List<StudentBean> studentsList() {
        return studentsListFromDB;
    }
     
    public String saveStudentDetails(StudentBean newStudentObj) {
        return StudentService.saveStudentDetailsInDB(newStudentObj);
    }
     
    public String editStudentRecord(int studentId) {
        return StudentService.editStudentRecordInDB(studentId);
    }
     
    public String updateStudentDetails(StudentBean updateStudentObj) {
        return StudentService.updateStudentDetailsInDB(updateStudentObj);
    }
     
    public String deleteStudentRecord(int studentId) {
        return StudentService.deleteStudentRecordInDB(studentId);
    }
}