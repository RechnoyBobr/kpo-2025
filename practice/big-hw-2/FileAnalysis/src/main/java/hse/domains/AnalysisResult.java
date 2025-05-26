package hse.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Analysis entity.
 */
@Entity
@Getter
@Setter
@Table(name = "analysis_results")
public class AnalysisResult {
    /**
     * Id.
     */
    @Id
    @GeneratedValue(generator = "gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * File id.
     */
    @Column(name = "file_id", nullable = false)
    private UUID fileId;

    /**
     * Score.
     */
    @Column(name = "similarity_score")
    private Double similarityScore;

    /**
     * Files that matched.
     */
    @Column(name = "matched_files", columnDefinition = "TEXT")
    private String matchedFiles;

    /**
     * Current status.
     */
    @Column(name = "analysis_status")
    @Enumerated(EnumType.STRING)
    private AnalysisStatus status;

    /**
     * Enum.
     */
    public enum AnalysisStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }
} 