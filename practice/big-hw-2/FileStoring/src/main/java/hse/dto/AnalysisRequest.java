package hse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

/**
 * Analysis request dto.
 *
 * @param fileId         File id.
 * @param sentenceHashes Hashes of file contents.
 */
@Builder
public record AnalysisRequest(
        @Schema(description = "fileId", example = "UUID")
        UUID fileId,
        @Schema(description = "Hashes of file sentences")
        List<Integer> sentenceHashes
) {
}