package hse.service;

import hse.domains.FileHash;
import hse.repository.FileHashRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * File hash service.
 */
@Service
public class FileHashService {
    /**
     * File hash repository.
     */
    @Autowired
    private FileHashRepository fileHashRepository;
    /**
     * Minimal sentence length (char).
     */
    private static final int MIN_SENTENCE_LENGTH = 10;

    /**
     * Create file hash entity.
     *
     * @param fileId File id.
     * @param file   File to read contents from.
     * @return File hash
     * @throws IOException If file is unreadable
     */
    public FileHash createFileHash(UUID fileId, MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        List<String> sentences = splitIntoSentences(content);
        FileHash fileHash = new FileHash();
        fileHash.setFileId(fileId);
        List<Integer> hashes = new ArrayList<>();
        for (String sentence : sentences) {
            hashes.add(calculateHash(sentence));
        }
        fileHash.setSentenceHash(hashes);
        fileHash.setFileText(content);
        return fileHash;
    }

    /**
     * Calculate hashes.
     *
     * @param file File to get hashes from
     * @return Hashes
     * @throws IOException If file is unreadable
     */
    public List<Integer> calculateHashes(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        List<String> sentences = splitIntoSentences(content);
        ;
        List<Integer> hashes = new ArrayList<>();
        for (String sentence : sentences) {
            hashes.add(calculateHash(sentence));
        }
        return hashes;
    }

    /**
     * Find file id by hashes.
     *
     * @param hashes Hashes
     * @return File id
     */
    public UUID findByHashes(List<Integer> hashes) {
        return fileHashRepository.findFirstBySentenceHash(hashes).getFileId();
    }

    /**
     * Save entity to file hash repository.
     *
     * @param fileHash Entity to save
     * @throws IOException If entity is incorrect
     */
    public void saveHashes(FileHash fileHash) throws IOException {
        fileHashRepository.save(fileHash);
    }

    /**
     * Get all hashes from database.
     *
     * @return All hashes.
     */
    public List<List<Integer>> getAllHashes() {
        List<FileHash> all = fileHashRepository.findAll();
        return all.stream()
                .map(FileHash::getSentenceHash)
                .collect(Collectors.toList());

    }

    /**
     * Split file content into sentences.
     *
     * @param content Content to split.
     * @return List of sentences.
     */
    private List<String> splitIntoSentences(String content) {
        return Arrays.stream(content.split("[.!?]+"))
                .map(String::trim)
                .filter(s -> s.length() >= MIN_SENTENCE_LENGTH)
                .collect(Collectors.toList());
    }

    /**
     * Calculate hash of sentence.
     *
     * @param text Sentence
     * @return Hash
     */
    private int calculateHash(String text) {
        return text.toLowerCase()
                .replaceAll("[^a-z0-9]", "")
                .hashCode();
    }

    public String getFileContentById(UUID fileId) {
        return fileHashRepository.findByFileId(fileId).getFileText();
    }
} 