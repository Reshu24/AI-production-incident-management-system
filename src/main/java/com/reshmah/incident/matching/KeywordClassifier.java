package com.reshmah.incident.matching;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KeywordClassifier {

    // category → keywords that signal this category
    private static final Map<String, List<String>> CATEGORY_KEYWORDS = Map.of(
            "Payments",       List.of("payment", "checkout", "transaction", "billing", "invoice", "refund", "gateway"),
            "Auth",           List.of("login", "auth", "token", "session", "password", "oauth", "jwt", "unauthorized"),
            "Cart",           List.of("cart", "basket", "item", "product", "quantity", "order", "add to cart"),
            "Database",       List.of("database", "db", "query", "connection", "timeout", "sql", "deadlock", "migration"),
            "Infrastructure", List.of("server", "memory", "cpu", "disk", "deployment", "pod", "kubernetes", "crash", "down")
    );

    // confidence threshold — above this = auto assign, below = suggest only
    public static final double AUTO_ASSIGN_THRESHOLD = 0.7;

    public ClassificationResult classify(String text) {
        String lowerText = text.toLowerCase();

        // count keyword hits per category
        Map<String, Integer> hitCount = new HashMap<>();
        Map<String, List<String>> matchedKeywordsPerCategory = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : CATEGORY_KEYWORDS.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();

            List<String> matched = new ArrayList<>();
            for (String keyword : keywords) {
                if (lowerText.contains(keyword)) {
                    matched.add(keyword);
                }
            }

            if (!matched.isEmpty()) {
                hitCount.put(category, matched.size());
                matchedKeywordsPerCategory.put(category, matched);
            }
        }

        // no keywords matched at all
        if (hitCount.isEmpty()) {
            return ClassificationResult.unknown();
        }

        // find the category with the most hits
        String bestCategory = Collections.max(hitCount.entrySet(),
                Map.Entry.comparingByValue()).getKey();

        List<String> bestMatchedKeywords = matchedKeywordsPerCategory.get(bestCategory);
        int bestHits = hitCount.get(bestCategory);
        int totalKeywordsInCategory = CATEGORY_KEYWORDS.get(bestCategory).size();

        // confidence = how many keywords from this category we found
        // out of total keywords in that category
        double confidence = (double) bestHits / totalKeywordsInCategory;

        return new ClassificationResult(
                bestCategory,
                confidence,
                String.join(", ", bestMatchedKeywords),
                confidence >= AUTO_ASSIGN_THRESHOLD
        );
    }

    // inner result class — clean way to return multiple values
    public static class ClassificationResult {
        private final String category;
        private final double confidenceScore;
        private final String matchedKeywords;
        private final boolean autoAssign;

        public ClassificationResult(String category, double confidenceScore,
                                    String matchedKeywords, boolean autoAssign) {
            this.category = category;
            this.confidenceScore = confidenceScore;
            this.matchedKeywords = matchedKeywords;
            this.autoAssign = autoAssign;
        }

        // when nothing matched
        public static ClassificationResult unknown() {
            return new ClassificationResult("Unknown", 0.0, "", false);
        }

        public String getCategory() { return category; }
        public double getConfidenceScore() { return confidenceScore; }
        public String getMatchedKeywords() { return matchedKeywords; }
        public boolean isAutoAssign() { return autoAssign; }
    }
}