package com.mballen.curso.boot.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.mballen.curso.boot.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
   
    private String name;

    private String userEmail;
   
    private String password;

    private String role;
 
    private short enabled;
    
    
    
    public User transformaParaObjeto(){
        return new User(this.userEmail,this.name,this.role,this.name, this.enabled);
    }




}