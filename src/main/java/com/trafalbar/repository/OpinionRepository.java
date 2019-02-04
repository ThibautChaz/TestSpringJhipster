package com.trafalbar.repository;

import com.trafalbar.domain.Opinion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Opinion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

}
