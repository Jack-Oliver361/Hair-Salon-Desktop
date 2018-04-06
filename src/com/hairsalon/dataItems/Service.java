/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.dataItems;

import com.google.gson.annotations.Expose;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jacko
 */
public class Service extends RecursiveTreeObject<Service> {
    @Expose
    public final  IntegerProperty serviceID;
    @Expose
    public final  StringProperty name;
    @Expose
    public final  StringProperty duration;
    @Expose
    public final  StringProperty price;

    
    public Service(int serviceID, String name, String duration,String price){
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.name = new SimpleStringProperty(name);
        this.duration = new SimpleStringProperty(duration);
        this.price = new SimpleStringProperty(price);

    }
    public final Integer getID(){
        return serviceID.getValue();
    }
    public IntegerProperty propertyID(){
        return serviceID;
    }
    public final void setID(int serviceID){
        this.serviceID.setValue(serviceID);
    }
    public final String getName(){
        return name.getValue();
    }
    public StringProperty propertyName(){
        return name;
    }
    public final void setName(String name){
        this.name.setValue(name);
    }
    public final String getDuration(){
        return duration.getValue();
    }
    public StringProperty propertyDuration(){
        return duration;
    }
    public final void setDuration(String duration){
        this.duration.setValue(duration);
    }
    public final String getPrice(){
        return price.getValue();
    }
    public StringProperty propertyPrice(){
        return price;
    }
    public final void setPrice(String price){
        this.price.setValue(price);
    }
}
