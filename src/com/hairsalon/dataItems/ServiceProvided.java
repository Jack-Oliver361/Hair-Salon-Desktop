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
public class ServiceProvided extends RecursiveTreeObject<ServiceProvided> {
    @Expose
    public final  IntegerProperty appointmentID;
    @Expose
    public final  IntegerProperty employeeID;
    @Expose
    public final  IntegerProperty numberOfService;
    @Expose
    public final  IntegerProperty serviceID;
    @Expose
    public  Appointment appointment;
    @Expose
    public  Service service;
    @Expose
    public  Employee employee;

    
    public ServiceProvided(int appointmentID,int employeeID,int numberOfService,int serviceID,Appointment appointment,Service service, Employee employee){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.numberOfService = new SimpleIntegerProperty(numberOfService);
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.appointment = appointment;
        this.service = service;
        this.employee = employee;

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
    public final Integer getEmployeeID(){
        return employeeID.getValue();
    }
    public IntegerProperty propertyEmployeeID(){
        return employeeID;
    }
    public final void setEmployeeID(int employeeID){
        this.employeeID.setValue(employeeID);
    }
    public final Integer getNumberOfService(){
        return numberOfService.getValue();
    }
    public IntegerProperty propertyNumberOfService(){
        return numberOfService;
    }
    public final void setNumberOfService(int numberOfService){
        this.numberOfService.setValue(numberOfService);
    }
    public final Integer getServiceID(){
        return serviceID.getValue();
    }
    public IntegerProperty propertyServiceID(){
        return serviceID;
    }
    public final void setServiceID(int serviceID){
        this.serviceID.setValue(serviceID);
    }
    public Appointment getAppointment(){
        return appointment;
    }
    public final void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }
    public Service getService(){
        return service;
    }
    public final void setService(Service service){
        this.service = service;
    }
    public Employee getEmployee(){
        return employee;
    }
    public final void setEmployee(Employee employee){
        this.employee = employee;
    }
    
}
