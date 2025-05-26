package hse.service;

import hse.dto.AnalysisRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * File upload listener.
 */
@Service
public class FileUploadListener {
    /**
     * Analysis service.
     */
    @Autowired
    private PlagiarismAnalysisService analysisService;

    /**
     * Method to create analysis for file in new thread.
     *
     * @param request Request entity.
     */
    public void onFileUploaded(AnalysisRequest request) {
        new Thread(() -> {
            try {
                analysisService.analyzeFile(request.fileId(), request.sentenceHashes());
            } catch (Exception e) {
                System.err.println("Failed to analyze file " + request.fileId() + ": " + e.getMessage());
            }
        }).start();
    }
} 