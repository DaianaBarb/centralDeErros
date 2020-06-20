package com.codenation.curso.central.error.dto.request;

import java.time.LocalDate;

import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogRequest {
	
	private ErrorLevelsEnum errorLevel;
	
	private LocalDate date;
	
	private String origin;
	
	private String description;
	
	private int quantity;
	
	private String logDoEvento;
	
	
	
	 public Log transformaParaObjeto(){
	        return new Log(this.errorLevel,this.description,this.origin,this.date,this.quantity,this.logDoEvento);
	    }

	
	 
}
