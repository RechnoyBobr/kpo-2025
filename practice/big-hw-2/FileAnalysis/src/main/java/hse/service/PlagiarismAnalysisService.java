package hse.service;

import hse.domains.AnalysisResult;
import hse.domains.FileHash;
import hse.repository.AnalysisRepository;
import hse.repository.FileHashRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Plagiarism service.
 */
@Service
public class PlagiarismAnalysisService {
    /**
     * Analysis repository.
     */
    @Autowired
    private AnalysisRepository analysisRepository;

    /**
     * File hash repository.
     */
    @Autowired
    private FileHashRepository fileHashRepository;

    /**
     * Analysing file.
     *
     * @param fileId         File id
     * @param sentenceHashes Hashes of file
     */
    public void analyzeFile(UUID fileId, List<Integer> sentenceHashes) {
        AnalysisResult result = new AnalysisResult();
        result.setFileId(fileId);
        try {

            List<FileHash> existingResults = fileHashRepository.findAll();

            Map<String, Double> similarities = new HashMap<>();
            for (FileHash existingResult : existingResults) {
                if (existingResult.getFileId().equals(fileId)) {
                    continue;
                }

                List<Integer> otherHashes = existingResult.getSentenceHash();
                if (!otherHashes.isEmpty()) {
                    double similarity = calculateSimilarity(sentenceHashes, otherHashes);
                    if (similarity > 0.1) {
                        similarities.put(existingResult.getFileId().toString(), similarity);
                    }
                }
            }

            if (similarities.isEmpty()) {
                result.setSimilarityScore(0.0);
                result.setMatchedFiles("");
            } else {
                result.setSimilarityScore(calculateOverallSimilarity(similarities));
                result.setMatchedFiles(convertMatchesToString(similarities));
            }

            result.setStatus(AnalysisResult.AnalysisStatus.COMPLETED);
        } catch (Exception e) {
            result.setStatus(AnalysisResult.AnalysisStatus.FAILED);
            throw new RuntimeException("Analysis failed: " + e.getMessage(), e);
        }
        analysisRepository.save(result);
    }

    /**
     * Get analysis report.
     *
     * @param fileId File id to get report
     * @return Report
     */
    public AnalysisResult getAnalysisResult(UUID fileId) {
        return analysisRepository.findByFileId(fileId);
    }

    /**
     * Calculate similarity between to files.
     *
     * @param hashes1 Hash
     * @param hashes2 Hash
     * @return Similarity score
     */
    private double calculateSimilarity(List<Integer> hashes1, List<Integer> hashes2) {
        if (hashes1.isEmpty() || hashes2.isEmpty()) {
            return 0.0;
        }

        Set<Integer> set1 = new HashSet<>(hashes1);
        Set<Integer> set2 = new HashSet<>(hashes2);

        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    /**
     * Get maximal similarity between all files.
     *
     * @param similarities Similarities
     * @return Double
     */
    private Double calculateOverallSimilarity(Map<String, Double> similarities) {
        return similarities.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
    }

    /**
     * Convert similarities to readable report.
     *
     * @param similarities Similarities
     * @return String
     */
    private String convertMatchesToString(Map<String, Double> similarities) {
        StringBuilder sb = new StringBuilder();
        similarities.forEach((file, score) ->
                sb.append(String.format("%s: %.2f%%\n", file, score * 100))
        );
        return sb.toString();
    }
} 