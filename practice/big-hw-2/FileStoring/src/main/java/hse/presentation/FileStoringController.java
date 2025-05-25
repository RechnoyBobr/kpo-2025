package hse.presentation;

import hse.domains.FileEntity;
import hse.repository.FileRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/storing")
public class FileStoringController {
    @Autowired
    FileRepository fileRepository;

    @PostMapping("/upload")
    @Operation(summary = "Добавить файл в базу данных",
            description = "Получает Multipart file и добавляет его в базу данных")
    public ResponseEntity<UUID> uploadFile(@Valid @RequestParam("file") MultipartFile file, BindingResult bindingResult) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getOriginalFilename());
        FileEntity savedFile = fileRepository.save(fileEntity);
        try {
            fileEntity.setFile(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedFile.getId());
    }

}
