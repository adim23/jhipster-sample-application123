package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CitizensMeetings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CitizensMeetings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitizensMeetingsRepository extends JpaRepository<CitizensMeetings, Long>, JpaSpecificationExecutor<CitizensMeetings> {}
