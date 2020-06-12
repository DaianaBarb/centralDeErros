package com.mballen.curso.boot.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;
import java.time.LocalDate;


import javax.transaction.Transactional;
@Repository
@Transactional
//JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> para filtro
public interface LogRepository extends JpaRepository<Log, Long> {
    
	Page<Log> findAllDistinctLogByIdOrErrorLevelOrOriginOrDateOrLogDoEventoOrDescription
	(Long id,ErrorLevelsEnum errorLevel,String origin,LocalDate date,String logDoEvento,String description, Pageable paging);
    
	 Page<Log> findByErrorLevel(ErrorLevelsEnum errorLevel, Pageable paging);
	 
	 Page<Log> findByDate(LocalDate date, Pageable paging);
	 
	 Page<Log> findByOriginIgnoreCase(String origin, Pageable paging);
	 
	 Page<Log> findByDescriptionIgnoreCase(String description, Pageable paging);
	 
	 Page<Log> findByLogDoEventoIgnoreCase(String logDoEvento, Pageable paging);
	 
	 Page<Log> findByQuantity(int quantity, Pageable paging);
}
