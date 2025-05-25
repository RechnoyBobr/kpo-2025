package hse.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GatewayController {
    final RestClient storingClient = RestClient.builder().baseUrl("http://file-storing:8081/").build();
    final RestClient analysisClient = RestClient.builder().baseUrl("http://file-analysis:8081/").build();
    @PostMapping("/upload")
    @Operation(
            summary = "Загрузка файла",
            description = "Загружает файл в базу антиплагиата. Возвращает id файла в базе"
    )
    public ResponseEntity<UUID> upload(@Valid @RequestParam("file") MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var res = storingClient.post().uri("/storing/upload").body(file).retrieve().body(UUID.class);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/analyse")
    @Operation(
            summary = "Анализ файла на плагиат по id",
            description = "Возвращает результат антиплагиата по id файла"
    )
    public ResponseEntity<String> analyse(@RequestParam("id") int id) {
        var res = analysisClient.get().uri("/analyse/" + id).retrieve().body(String.class);
        return ResponseEntity.ok(res);
    }

}
