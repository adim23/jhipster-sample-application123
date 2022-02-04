package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Citizens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Citizens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitizensRepository extends JpaRepository<Citizens, Long>, JpaSpecificationExecutor<Citizens> {}
