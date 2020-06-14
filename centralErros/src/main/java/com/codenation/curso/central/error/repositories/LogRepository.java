package com.codenation.curso.central.error.repositories;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.models.QLog;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import javax.transaction.Transactional;

@Repository
@Transactional
//JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> para filtro
public interface LogRepository extends JpaRepository<Log, Long>, QuerydslPredicateExecutor<Log>
,QuerydslBinderCustomizer<QLog> 
{
    
	Page<Log> findAllDistinctLogByIdOrErrorLevelOrOriginOrDateOrLogDoEventoOrDescription
	(Long id,ErrorLevelsEnum errorLevel,String origin,LocalDate date,String logDoEvento,String description, Pageable paging);
    
	 Page<Log> findByErrorLevel(ErrorLevelsEnum errorLevel, Pageable paging);
	 
	 Page<Log> findByDate(LocalDate date, Pageable paging);
	 
	 Page<Log> findByOriginIgnoreCase(String origin, Pageable paging);
	 
	 Page<Log> findByDescriptionIgnoreCase(String description, Pageable paging);
	 
	 Page<Log> findByLogDoEventoIgnoreCase(String logDoEvento, Pageable paging);
	 
	 Page<Log> findByQuantity(int quantity, Pageable paging);
	 @SuppressWarnings("NullableProblems")
	   @Override
	    default void customize(QuerydslBindings bindings, QLog log) {

	      //nao fazer a busca pelo parametro Id
	        bindings.excluding(log.id);

	       //quando for string ignorar case
	        bindings.bind(String.class)
	                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

	        // a data nao e string e Date
	        bindings.bind(log.date).first((path, value) -> {
	            return path.eq(value);
	        });

	        // a quantidade nao e string e Integer
	        bindings.bind(log.quantity).first((path, value) -> {
	            return path.eq(value);
	        });
	    } 
}
