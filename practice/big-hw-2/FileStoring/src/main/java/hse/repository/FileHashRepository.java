package hse.repository;

import hse.domains.FileHash;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * File hashes repository.
 */
@Repository
public interface FileHashRepository extends JpaRepository<FileHash, UUID> {
    /**
     * Find file by id.
     *
     * @param fileId File id
     * @return File hash entity
     */
    FileHash findByFileId(UUID fileId);

    /**
     * Find first file by sentence hash.
     *
     * @param sentenceHash Sentence hash
     * @return File hash entity
     */
    FileHash findFirstBySentenceHash(List<Integer> sentenceHash);
}