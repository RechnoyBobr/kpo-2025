package hse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

/**
 * AnalysisRequest DTO.
 *
 * @param fileId         File id
 * @param sentenceHashes Sentence hashes
 */
@Builder
public record AnalysisRequest(
        @Schema(description = "file ID (UUID)")
        UUID fileId,
        @Schema(description = "Hashes of file sentences")
        List<Integer> sentenceHashes
) {
}
