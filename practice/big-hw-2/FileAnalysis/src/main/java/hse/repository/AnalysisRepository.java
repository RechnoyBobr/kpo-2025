package hse.repository;

import hse.domains.AnalysisResult;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository forn analysis entity.
 */
@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisResult, UUID> {
    /**
     * Find analysis entity by id.
     *
     * @param fileId File id.
     * @return Analysis entity.
     */
    AnalysisResult findByFileId(UUID fileId);
} 