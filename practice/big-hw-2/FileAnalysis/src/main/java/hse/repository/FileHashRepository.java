package hse.repository;

import hse.domains.FileHash;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for file hash entity.
 */
@Repository
public interface FileHashRepository extends JpaRepository<FileHash, UUID> {

}