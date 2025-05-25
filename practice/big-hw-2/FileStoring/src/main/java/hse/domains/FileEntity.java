package hse.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(generator = "gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Size(max = 255)
    @Column(name = "name")
    private String name;
}
