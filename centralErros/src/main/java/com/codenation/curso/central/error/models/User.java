package com.codenation.curso.central.error.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User   implements Serializable {


	public User(String name, String userEmail, String password) {
		
		this.userEmail = userEmail;
		this.name = name;
		this.password = password;
		this.role="ADMIN";
	this.enabled=(short)1;
		
	}

	

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Email
   // @NotBlank(message= "Email can not be  blank ")
    @NotNull(message = "Email is a required parameter")
    private String userEmail;
    
    @Column
   // @NotBlank(message= "Name can not be  blank ")
    @NotNull(message = "Name is a required parameter")
    @Size(min = 3)
    private String name;
    
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  //  @NotBlank(message= "password can not be  blank ")
    @NotNull(message = "Password is a required parameter")
    @Size(min = 3)
    private String password;
   // @NotBlank(message= "Role can not be  blank ")
    @NotNull(message = "Role is not informed (USER or ADMIN)")
    @Column
    private String role;

    //@NotBlank(message= "Enabled can not be  blank ")
    @NotNull(message = "enabled is a required parameter")
    @Column
    private short enabled;
   
    
    

 

	

}
