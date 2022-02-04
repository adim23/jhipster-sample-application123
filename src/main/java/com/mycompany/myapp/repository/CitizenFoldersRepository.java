package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CitizenFolders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CitizenFolders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitizenFoldersRepository extends JpaRepository<CitizenFolders, Long>, JpaSpecificationExecutor<CitizenFolders> {}
