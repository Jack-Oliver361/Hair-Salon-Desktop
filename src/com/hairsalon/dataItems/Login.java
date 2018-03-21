/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.dataItems;

/**
 *
 * @author Jacko
 */
public class Login {
    private String access_token;
    private String token_type;
    
    public String getAccess_token(){
        return access_token;
    }
    public void setAccess_token(String access_token){
        this.access_token = access_token;
    }
    public String getToken_type(){
        return token_type;
    }
    public void setToken_type(String token_type){
        this.token_type = token_type;
    }
}
