package com.uef.LibManager.Models.Book.IDgenerator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BookIDGenerator implements IdentifierGenerator
{

    private static final AtomicLong counter = new AtomicLong(1);


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Generate a unique ID: "BOOK-" prefixed UUID
        return "BOOK-" + UUID.randomUUID().toString();

    }

}
