package com.uef.group6.Methods.IdGenerator;

import java.util.UUID;

public class BookIDGenerator {
    /**
     * Tạo Book ID với format "bookxxxxxxxxxxx"
     * với xxxxxxxxxxxx là 12 chữ số từ UUID.randomUUID()
     */
    public static String generateBookId() {
        String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        return "book" + uniquePart;
    }

}
