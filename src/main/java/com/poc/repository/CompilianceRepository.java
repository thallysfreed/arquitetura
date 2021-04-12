package com.poc.repository;

import com.poc.domain.Compiliance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Compiliance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompilianceRepository extends JpaRepository<Compiliance, Long> {

}
