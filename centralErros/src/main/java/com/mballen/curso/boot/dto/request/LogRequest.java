package com.mballen.curso.boot.dto.request;

import java.time.LocalDate;

import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;
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
	
	 public Log transformaParaObjeto(){
	        return new Log(this.errorLevel,this.description,this.origin,this.date,this.quantity);
	    }

}
