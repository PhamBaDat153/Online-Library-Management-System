package com.uef.LibraryManagementWeb.UsefulMethod.IDgenerator;

/*
 * Tự động tạo ra mã sách
 * mã sách có đinh dạng bookxxxxxxxxxxxxx với x là UUID
 */

public class BookIdGenerator {
    private static final String BOOK_ID_PREFIX = "book";

    public static String generateNextId() {
        return BOOK_ID_PREFIX + java.util.UUID.randomUUID().toString();
    }
}