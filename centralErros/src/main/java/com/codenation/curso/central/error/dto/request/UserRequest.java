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
    
    

    
    
    
    public User transformaParaObjeto(){
        return new User(this.name,this.userEmail,this.password);
    }




}