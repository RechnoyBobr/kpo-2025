package hse.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * File hash entity.
 */
@Entity
@Getter
@Setter
@Table(name = "file_hashes")
public class FileHash {
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
     * List of sentence hashes.
     */
    @Column(name = "sentence_hash", nullable = false, columnDefinition = "integer[]")
    private List<Integer> sentenceHash;
    /**
     * File content.
     */
    @Column(name = "file_text")
    private String fileText;
} 