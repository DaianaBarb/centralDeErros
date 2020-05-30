package com.mballen.curso.boot.specification;

import org.springframework.data.jpa.domain.Specification;
import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogSpecs {
  //  private static final Logger LOG = Logger.getLogger(LogService.class);

    public static Specification<Log> getLogsByFilters(Map<String, String> params){
        return new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) // criando o criterio e a raiz do log
            {
                List<Predicate> predicatesList = getListOfParameters(root, criteriaBuilder, params);

                Predicate predicateForFilters = criteriaBuilder.and(
                        predicatesList.toArray(new Predicate[predicatesList.size()])
                );

                return predicateForFilters;
            }
        };
    }
    public static List<Predicate> getListOfParameters(Root<Log> root, CriteriaBuilder criteriaBuilder,
                                                      Map<String, String> params)
    {
        List<Predicate> predicates = new ArrayList<>();
        if (params.get("id") != null && !params.get("id").isEmpty()) {
        	// se o value do map for igual aos valores  que 
        	//estao nos atributos do banco vai ser feito um findAll
        	//e vao ser adicionados a lista todos os atributos que contem o valor passado..
            predicates.add(criteriaBuilder.equal(root.get("id"), params.get("id")));
            //o criterio praentrar na lista e: que o valor da raiz  seja igual ao parametro.
        }

        if (params.get("origin") != null && !params.get("origin").isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("origin"), params.get("origin")));
        }
        if (params.get("date") != null && !params.get("date").isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("date"), params.get("date")));
        }
        if (params.get("description") != null && !params.get("description").isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("description"), params.get("description")));
        }
        if (params.get("logDoEvento") != null && !params.get("logDoEvento").isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("logDoEvento"), params.get("logDoEvento")));
        }
        if (params.get("errorLevel") != null && !params.get("errorLevel").isEmpty()) {
        	
        	 predicates.add(criteriaBuilder.equal(root.get("errorLevel"),
              ErrorLevelsEnum.valueOf(params.get("errorLevel"))));
        }
        

        return predicates;
    }
}