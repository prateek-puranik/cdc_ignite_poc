package com.example.IgniteTrial.repository;

import com.example.IgniteTrial.model.company_3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface OracleRepository extends JpaRepository<company_3, Integer> {

}
