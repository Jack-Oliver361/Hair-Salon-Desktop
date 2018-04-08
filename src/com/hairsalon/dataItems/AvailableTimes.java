/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.dataItems;

import com.google.gson.annotations.Expose;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jacko
 */


public class AvailableTimes extends RecursiveTreeObject<AvailableTimes>{
    
    @Expose
    public final StringProperty AvailableTime;
    
    public AvailableTimes (String availableTime){
        this.AvailableTime = new SimpleStringProperty(availableTime);
    }
    public final String getAvailableTimes(){
        return AvailableTime.getValue();
    }
    public StringProperty propertyAvailableTimes(){
        return AvailableTime;
    }
    public final void setDate(String AvailableTime){
        this.AvailableTime.setValue(AvailableTime);
    }
}
