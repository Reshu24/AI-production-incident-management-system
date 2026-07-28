package com.reshmah.incident.matching;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class TextSimilarityService {

    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");
    private static final Set<String> STOP_WORDS = Set.of(
            "the", "is", "at", "which", "on", "a", "an", "and", "or", "to", "of",
            "in", "for", "with", "was", "were", "are", "be", "this", "that", "it"
    );

    // Turns raw text into a clean list of meaningful lowercase words
    private List<String> tokenize(String text) {
        if (text == null) return List.of();
        var matcher = WORD_PATTERN.matcher(text.toLowerCase());
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            String word = matcher.group();
            if (!STOP_WORDS.contains(word) && word.length() > 2) {
                tokens.add(word);
            }
        }
        return tokens;
    }

    // Builds a word -> frequency count map (term frequency)
    private Map<String, Integer> termFrequency(List<String> tokens) {
        Map<String, Integer> freq = new HashMap<>();
        for (String token : tokens) {
            freq.merge(token, 1, Integer::sum);
        }
        return freq;
    }

    // Cosine similarity between two texts, returns a score between 0.0 and 1.0
    public double calculateSimilarity(String textA, String textB) {
        Map<String, Integer> freqA = termFrequency(tokenize(textA));
        Map<String, Integer> freqB = termFrequency(tokenize(textB));

        Set<String> allWords = new HashSet<>();
        allWords.addAll(freqA.keySet());
        allWords.addAll(freqB.keySet());

        if (allWords.isEmpty()) return 0.0;

        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;

        for (String word : allWords) {
            int a = freqA.getOrDefault(word, 0);
            int b = freqB.getOrDefault(word, 0);
            dotProduct += a * b;
            magnitudeA += a * a;
            magnitudeB += b * b;
        }

        if (magnitudeA == 0 || magnitudeB == 0) return 0.0;

        return dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
    }
}