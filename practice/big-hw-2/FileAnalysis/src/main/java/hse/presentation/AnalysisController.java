package hse.presentation;

import hse.domains.AnalysisResult;
import hse.dto.AnalysisRequest;
import hse.service.FileUploadListener;
import hse.service.PlagiarismAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Analysis controller.
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    /**
     * Analysis service.
     */
    @Autowired
    private PlagiarismAnalysisService analysisService;
    /**
     * Listener.
     */
    @Autowired
    private FileUploadListener fileUploadListener;

    /**
     * Notify analysis service for file upload.
     *
     * @param analysisRequest Dto
     * @return Void response
     */
    @PostMapping("/notify")
    @Operation(summary = "Notify about new file upload",
            description = "Triggers plagiarism analysis for a newly uploaded file")
    public ResponseEntity<Void> notifyFileUpload(@RequestBody AnalysisRequest analysisRequest) {
        System.out.println("Got request: " + analysisRequest);
        fileUploadListener.onFileUploaded(analysisRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Get analysis report.
     *
     * @param fileId File id to get report
     * @return Report
     */
    @GetMapping("/{fileId}")
    @Operation(summary = "Get analysis result",
            description = "Returns the analysis result for the specified file")
    public ResponseEntity<AnalysisResult> getAnalysisResult(@PathVariable UUID fileId) {
        AnalysisResult result = analysisService.getAnalysisResult(fileId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

} 