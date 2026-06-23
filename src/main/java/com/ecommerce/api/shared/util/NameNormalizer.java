package com.ecommerce.api.shared.util;

import java.text.Normalizer;

public class NameNormalizer {

    private NameNormalizer() {}

    public static String normalize(String input) {

        if (input == null) {
            return null;
        }

        String result = input.trim().toLowerCase();

        result = Normalizer.normalize(result, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        result = result.replaceAll("[^a-z0-9\\s]", "");

        result = result.replaceAll("\\s+", " ");

        return result.trim();

    }
}
