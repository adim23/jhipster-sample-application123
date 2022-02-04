package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SocialContacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SocialContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialContactsRepository extends JpaRepository<SocialContacts, Long>, JpaSpecificationExecutor<SocialContacts> {}
