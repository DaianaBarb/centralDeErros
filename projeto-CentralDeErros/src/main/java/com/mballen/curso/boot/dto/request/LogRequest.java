package com.mballen.curso.boot.dto.request;





import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;


import java.time.LocalDate;


public class LogRequest {

    private Long id;

    
    private ErrorLevelsEnum errorLevel;
    private String description;
    private String logDoEvento;
    private String origin;
    private LocalDate date;

  
public Log convertToDto() {
	return new Log(id,errorLevel,description,logDoEvento,origin,date);
}


public LogRequest() {

}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public ErrorLevelsEnum getErrorLevel() {
	return errorLevel;
}


public void setErrorLevel(ErrorLevelsEnum errorLevel) {
	this.errorLevel = errorLevel;
}


public String getDescription() {
	return description;
}


public void setDescription(String description) {
	this.description = description;
}


public String getLogDoEvento() {
	return logDoEvento;
}


public void setLogDoEvento(String logDoEvento) {
	this.logDoEvento = logDoEvento;
}


public String getOrigin() {
	return origin;
}


public void setOrigin(String origin) {
	this.origin = origin;
}


public LocalDate getDate() {
	return date;
}


public void setDate(LocalDate date) {
	this.date = date;
}
  

}
