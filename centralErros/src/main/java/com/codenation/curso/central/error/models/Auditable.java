package com.codenation.curso.central.error.models;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
  @CreatedBy
  @Column(name = "created_by")
  private U createdBy;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  
  private U lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  
  private LocalDate lastModifiedDate;
}