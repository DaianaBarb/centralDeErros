package com.central.error.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.central.error.models.Log;

import java.util.UUID;

import javax.transaction.Transactional;
@Repository
@Transactional
//JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> para filtro
public interface LogRepository extends JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> {

}
