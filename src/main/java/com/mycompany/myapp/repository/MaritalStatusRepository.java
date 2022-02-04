package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaritalStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MaritalStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long>, JpaSpecificationExecutor<MaritalStatus> {}
