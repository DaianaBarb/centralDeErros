package com.codenation.curso.central.error.dto.response;

import java.time.LocalDate;
import java.util.Date;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class LogResponse   {

    private Long id;

    
    private ErrorLevelsEnum errorLevel;
    
    private String description;
    
    private String origin;
    
    private LocalDate date;
    
    private int quantity;
    
    private String createdBy;

    private String lastModifiedBy;

    private LocalDate lastModifiedDate;

  
public Log convertToDto() {
	return new Log(this.id,this.errorLevel,this.description,this.origin,this.date,this.quantity,this.createdBy,this.lastModifiedBy,this.lastModifiedDate);
}


public LogResponse() {

}




}
