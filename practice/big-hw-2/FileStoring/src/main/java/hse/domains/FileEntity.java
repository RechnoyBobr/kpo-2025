package hse.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * File entity.
 */
@Entity
@Getter
@Setter
@Table(name = "files")
public class FileEntity {
    /**
     * Id.
     */
    @Id
    @GeneratedValue(generator = "gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;
    /**
     * File path.
     */
    @Column(name = "filepath", length = 255)
    private String filePath;
    /**
     * File original name.
     */
    @Size(max = 255)
    @Column(name = "name")
    private String name;
}
