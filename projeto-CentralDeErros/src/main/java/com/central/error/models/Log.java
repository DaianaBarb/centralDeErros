package com.central.error.models;

import java.io.Serializable;
import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_LOG")
public class Log implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@NotBlank(message = "Error is can not be blank")
	@NotNull(message = "Error level is a required parameter (INFO, WARNING, ERROR)")
    @Enumerated(EnumType.STRING)
    @Column
    private ErrorLevelsEnum errorLevel;
	//@NotBlank(message = "Description is can not be blank")
	@NotNull(message = "Description is a required parameter")
    @Column
    private String description;
	//@NotBlank(message = "Log is can not be blank")
    @NotNull(message = "Log of Event is a required parameter")
    @Column
    private String logDoEvento;
    
    @Column
   // @NotBlank(message = "Origin is can not be blank")
    @NotNull(message = "Origin of Event is a required parameter")
    private String origin;
   // @NotBlank(message = "Date is can not be blank")
    @NotNull(message = "Date is a required parameter")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
	
}
