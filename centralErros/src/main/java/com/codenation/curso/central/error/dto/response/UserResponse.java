package com.codenation.curso.central.error.dto.response;
import com.codenation.curso.central.error.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse  {

    private Long id;
    private String name;
    private String userEmail;
    private String role;

    public static UserResponse transformaEmDTO(User user) {
    	
        return new UserResponse(user.getId(),user.getName(),user.getUserEmail(),user.getRole());
    }

	

	

}

