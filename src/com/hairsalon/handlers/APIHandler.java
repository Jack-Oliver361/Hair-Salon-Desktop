/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.handlers;

import com.hairsalon.dataItems.Customer;
import com.hairsalon.dataItems.Login;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class APIHandler {

   private String url;
   private String dataBeingPulled;
   private ArrayList<Object> dataFromAPI = new ArrayList<>();
   private Login LoginData;
   
   public APIHandler(String url, String dataBeingPulled) {
        this.url = url;
        this.dataBeingPulled = dataBeingPulled;
    }
   
   public void setDataBeingPulled(String dataBeingPulled) {
        this.dataBeingPulled = dataBeingPulled;
    }
   
   public void setUrl(String url) {
        this.url = url;
    }
   public ArrayList<Object> getDataFromAPI() {
        return dataFromAPI;
    }
   
   public void MakeAPICall() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet apirequest = new HttpGet(url);
            apirequest.addHeader("content-type", "application/json");
            apirequest.addHeader("Authorization", LoginData.getToken_type() + " " + LoginData.getAccess_token());
            HttpResponse apiresult = httpClient.execute(apirequest);
            String json = EntityUtils.toString(apiresult.getEntity(), "UTF-8");
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
            gsonBuilder.registerTypeHierarchyAdapter(IntegerProperty.class, new IntegerPropertyAdapter());
            final Gson gson = gsonBuilder.create();
            Object[] response = new Object[0];

            switch (dataBeingPulled) {
                case "customer":
                    System.out.print(json);
                    response = gson.fromJson(json, Customer[].class);
                    break;
            }

            dataFromAPI.addAll(Arrays.asList(response));

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
   public void loginAPI() throws ClientProtocolException, IOException {
       try (CloseableHttpClient client = HttpClients.createDefault()) {
           HttpPost httpPost = new HttpPost(url);
           
           List<NameValuePair> params = new ArrayList<>();
           params.add(new BasicNameValuePair("username", "administrator"));
           params.add(new BasicNameValuePair("password", "administrator123"));
           params.add(new BasicNameValuePair("grant_type", "password"));
           httpPost.setEntity(new UrlEncodedFormEntity(params));
           
           CloseableHttpResponse response = client.execute(httpPost);
           com.google.gson.Gson gson = new com.google.gson.Gson();
           String json = EntityUtils.toString(response.getEntity(), "UTF-8");
           LoginData = gson.fromJson(json, Login.class);
           System.out.print(LoginData.getAccess_token());
       }
    }
}