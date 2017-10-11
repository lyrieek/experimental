package com.versionsystem.${package}.${module}.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import com.versionsystem.persistence.model.${model};

public interface ${^module}Repository extends JpaRepository<${model}, String>,QueryDslPredicateExecutor<${model}> {
    
}