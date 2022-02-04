package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Files;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Files entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilesRepository extends JpaRepository<Files, Long>, JpaSpecificationExecutor<Files> {
    @Query("select files from Files files where files.createdBy.login = ?#{principal.username}")
    List<Files> findByCreatedByIsCurrentUser();
}
