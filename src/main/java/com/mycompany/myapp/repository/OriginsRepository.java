package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Origins;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Origins entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OriginsRepository extends JpaRepository<Origins, Long>, JpaSpecificationExecutor<Origins> {}
