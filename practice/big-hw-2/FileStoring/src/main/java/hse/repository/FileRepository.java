package hse.repository;

import hse.domains.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}
