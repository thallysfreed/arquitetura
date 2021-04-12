package com.poc.repository;

import com.poc.domain.NormaTecnica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NormaTecnica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaTecnicaRepository extends JpaRepository<NormaTecnica, Long> {

}
