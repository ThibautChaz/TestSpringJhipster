package com.trafalbar.repository;

import com.trafalbar.domain.Bottle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bottle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BottleRepository extends JpaRepository<Bottle, Long> {

}
