package com.codenation.curso.central.error.dto.request;
import com.codenation.curso.central.error.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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