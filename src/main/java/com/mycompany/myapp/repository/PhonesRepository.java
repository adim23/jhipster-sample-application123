package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Phones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Phones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhonesRepository extends JpaRepository<Phones, Long>, JpaSpecificationExecutor<Phones> {}
