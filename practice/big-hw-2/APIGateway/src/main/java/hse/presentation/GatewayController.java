package hse.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;


/**
 * Gateway controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GatewayController {

    /**
     * Storing rest client.
     */
    final RestClient storingClient = RestClient.builder().baseUrl("http://file-storing:8080/").build();

    /**
     * Analysis rest client.
     */
    final RestClient analysisClient = RestClient.builder().baseUrl("http://file-analysis:8080/").build();

    /**
     * Upload file.
     *
     * @param file File to upload
     * @return File id
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка файла",
            description = "Загружает файл в базу антиплагиата. Возвращает id файла в базе",
            parameters = {@Parameter(name = "file", description = "Файл для загрузки"),
            }
    )
    public ResponseEntity<UUID> upload(@RequestParam("file") MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());

        UUID id = storingClient.post()
                .uri("/storing/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(builder.build())
                .retrieve()
                .body(UUID.class);
        return ResponseEntity.ok(id);
    }

    /**
     * Get file analysis.
     *
     * @param id File id
     * @return Anti plagiarism report.
     */
    @GetMapping("/analyse/{id}")
    @Operation(
            summary = "Анализ файла на плагиат по id",
            description = "Возвращает результат антиплагиата по id файла"
    )
    public ResponseEntity<String> analyse(@RequestParam("id") UUID id) {
        var res = analysisClient.get().uri("/analysis/" + id).retrieve().body(String.class);
        return ResponseEntity.ok(res);
    }

    /**
     * Get file text.
     *
     * @param id File id
     * @return File content
     */
    @GetMapping("/storing/get/{id}")
    @Operation(
            summary = "Получить текст файла по его id"
    )
    public ResponseEntity<String> getStoring(@PathVariable("id") UUID id) {
        var res = storingClient.get().uri("/storing/get/" + id).retrieve().body(String.class);
        return ResponseEntity.ok(res);
    }
}
