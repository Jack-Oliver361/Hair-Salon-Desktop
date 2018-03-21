/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.dataItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jacko
 */
public class Customer extends RecursiveTreeObject<Customer> {
    
    public final  IntegerProperty customerID;
    public final  StringProperty firstName;
    public final  StringProperty lastName;
    public final  StringProperty email;
    private final StringProperty password;
    private final StringProperty confirmPassword;
    public final  StringProperty phone;
    public final  StringProperty dob;
    public final  StringProperty gender;
    
    public Customer(int customerID, String firstName, String lastName, String email, String password, String confirmPassword, String phone, String dob, String gender){
        this.customerID = new SimpleIntegerProperty(customerID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.confirmPassword = new SimpleStringProperty(confirmPassword);
        this.phone = new SimpleStringProperty(phone);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
    }
    public final Integer getID(){
        return customerID.getValue();
    }
    public IntegerProperty propertyID(){
        return customerID;
    }
    public final void setID(int customerID){
        this.customerID.setValue(customerID);
    }
    public final String getFirstName(){
        return firstName.getValue();
    }
    public StringProperty propertyFirstName(){
        return firstName;
    }
    public final void setFirstName(String firstName){
        this.firstName.setValue(firstName);
    }
    public final String getLastName(){
        return lastName.getValue();
    }
    public StringProperty propertyLastName(){
        return lastName;
    }
    public final void setLastName(String lastName){
        this.lastName.setValue(lastName);
    }
    public final String getEmail(){
        return email.getValue();
    }
    public StringProperty propertyEmail(){
        return email;
    }
    public final void setEmail(String email){
        this.email.setValue(email);
    }
    public final String getPassword(){
        return password.getValue();
    }
    public StringProperty propertyPassword(){
        return password;
    }
    public final void setPassword(String password){
        this.password.setValue(password);
    }
    public final String getConfirmPassword(){
        return confirmPassword.getValue();
    }
    public StringProperty propertyConfirmPassword(){
        return confirmPassword;
    }
    public final void setConfirmPassword(String confirmPassword){
        this.confirmPassword.setValue(confirmPassword);
    }
    public final String getPhone(){
        return phone.getValue();
    }
    public StringProperty propertyPhone(){
        return phone;
    }
    public final void setPhone(String phone){
        this.phone.setValue(phone);
    }
    public final String getDOB(){
        return dob.getValue();
    }
    public StringProperty propertyDOB(){
        return dob;
    }
    public final void setDOB(String dob){
        this.dob.setValue(dob);
    }
    public final String getGender(){
        return gender.getValue();
    }
    public StringProperty propertyGender(){
        return gender;
    }
    public final void setGender(String gender){
        this.gender.setValue(gender);
    }

}
