/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.dataItems;

import com.google.gson.annotations.Expose;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jacko
 */
public class Appointment extends RecursiveTreeObject<Appointment> {
    @Expose
    public final  IntegerProperty appointmentID;
    @Expose
    public final  IntegerProperty customerID;
    @Expose
    public final  StringProperty date;
    @Expose
    public final  StringProperty time;
    @Expose
    public final  StringProperty cFirstName;
    @Expose
    public final  StringProperty cLastName;
   

    
    public Appointment(int appointmentID,int customerID,String date,String time, String cFirstName, String cLastName){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.cFirstName = new SimpleStringProperty(cFirstName);
        this.cLastName = new SimpleStringProperty(cLastName);

    }
    public final Integer getAppointmentID(){
        return appointmentID.getValue();
    }
    public IntegerProperty propertyAppointmentID(){
        return appointmentID;
    }
    public final void setAppointmentID(int appointmentID){
        this.appointmentID.setValue(appointmentID);
    }
    public final Integer getCustomerID(){
        return customerID.getValue();
    }
    public IntegerProperty propertyCustomerID(){
        return customerID;
    }
    public final void setCustomerID(int customerID){
        this.customerID.setValue(customerID);
    }
    public final String getDate(){
        return date.getValue();
    }
    public StringProperty propertyDate(){
        return date;
    }
    public final void setDate(String date){
        this.date.setValue(date);
    }
    public final String getTime(){
        return time.getValue();
    }
    public StringProperty propertyTime(){
        return time;
    }
    public final void setTime(String time){
        this.time.setValue(time);
    }
    public final String getCFirstName(){
        return cFirstName.getValue();
    }
    public StringProperty propertyCFirstName(){
        return cFirstName;
    }
    public final void setCFirstName(String firstName){
        this.cFirstName.setValue(firstName);
    }
    public final String getCLastName(){
        return cLastName.getValue();
    }
    public StringProperty propertyCLastName(){
        return cLastName;
    }
    public final void setCLastName(String lastName){
        this.cLastName.setValue(lastName);
    }
   
}
