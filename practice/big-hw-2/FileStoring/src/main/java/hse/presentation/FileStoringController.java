package hse.presentation;

import hse.domains.FileEntity;
import hse.domains.FileHash;
import hse.dto.AnalysisRequest;
import hse.repository.FileRepository;
import hse.service.FileHashService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

/**
 * File storing controller.
 */
@RestController
@RequestMapping("/storing")
public class FileStoringController {

    /**
     * File repository.
     */
    @Autowired
    FileRepository fileRepository;

    /**
     * File hash service.
     */
    @Autowired
    FileHashService fileHashService;

    /**
     * Rest client for analysis service.
     */
    RestClient restClient = RestClient.builder().baseUrl("http://file-analysis:8080/analysis").build();

    /**
     * Upload file to database.
     *
     * @param file File to upload
     * @return File id
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавить файл в базу данных",
            description = "Получает Multipart file и добавляет его в базу данных")
    public ResponseEntity<UUID> uploadFile(@RequestParam("file") MultipartFile file) {
        String ext = Optional.ofNullable(file.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf("."))).orElse("");

        String filename = UUID.randomUUID().toString() + ext;
        Path storageDir = Paths.get("/data/files");
        Path fullPath = storageDir.resolve(filename);
        FileEntity entity = new FileEntity();
        entity.setName(file.getOriginalFilename());
        entity.setFilePath("/data/files/" + filename);
        try {
            List<List<Integer>> allHashes = fileHashService.getAllHashes();
            List<Integer> hash = fileHashService.calculateHashes(file);
            if (allHashes.contains(hash)) {
                return ResponseEntity.ok(fileHashService.findByHashes(hash));

            }
            System.out.println("Saving file with path: " + entity.getFilePath());
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, fullPath);
            } catch (IOException e) {
                return ResponseEntity.status(500).build();
            }
            entity = fileRepository.save(entity);
            FileHash fileHash = fileHashService.createFileHash(entity.getId(), file);
            AnalysisRequest analysisRequest = new AnalysisRequest(entity.getId(), fileHash.getSentenceHash());
            restClient.post()
                    .uri("/notify")
                    .body(analysisRequest)
                    .retrieve()
                    .toBodilessEntity();
            fileHashService.saveHashes(fileHash);
            System.out.println("Saved entity with ID: " + entity.getId() + " and path: " + entity.getFilePath());
            return ResponseEntity.ok(entity.getId());
        } catch (IOException e) {
            System.err.println("Failed to process file for analysis: " + e.getMessage());
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * Get file content from file id.
     *
     * @param fileId File id
     * @return File content
     */
    @GetMapping("/get/{fileId}")
    @Operation(summary = "Get file content")
    public ResponseEntity<String> getFileContent(@PathVariable UUID fileId) {
        try {
            String content = fileHashService.getFileContentById(fileId);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            System.err.println("Failed to get file content: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
