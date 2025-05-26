package hse.repository;

import hse.domains.FileEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * File repository.
 */
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}