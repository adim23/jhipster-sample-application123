package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jobs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jobs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long>, JpaSpecificationExecutor<Jobs> {}
