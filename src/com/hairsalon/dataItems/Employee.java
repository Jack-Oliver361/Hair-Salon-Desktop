/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.dataItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jacko
 */
public class Employee extends RecursiveTreeObject<Employee> {
    
    public final  IntegerProperty employeeID;
    public final  StringProperty firstName;
    public final  StringProperty lastName;

    
    public Employee(int employeeID, String firstName, String lastName){
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

    }
    public final Integer getID(){
        return employeeID.getValue();
    }
    public IntegerProperty propertyID(){
        return employeeID;
    }
    public final void setID(int employeeID){
        this.employeeID.setValue(employeeID);
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
   
}
