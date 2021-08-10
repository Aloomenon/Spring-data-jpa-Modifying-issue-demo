package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@DataJpaTest
@AutoConfigureEmbeddedDatabase
class ModifyingWithNativeQueryWithReturningTests {

    private final static long ID = 1L;

    @Autowired
    SimpleRepository repo;

    @BeforeEach
    void before() {
        SimpleEntity entity = new SimpleEntity();
        entity.setField("some field");
        entity.setId(ID);
        repo.save(entity).getId();
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.NEVER)
    @Test
    void modifyingIsIgnored() {
        assertDoesNotThrow(() -> repo.updateAll());
        assertTrue("updated".equals(repo.getById(ID).getField()));
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.NEVER)
    @Test
    void withoutModifying() {
        assertDoesNotThrow(() -> repo.updateAllWithoutModifying());
        assertTrue("updated".equals(repo.getById(ID).getField()));
    }

    @Test
    void withoutModifyingInTransaction() {
        assertDoesNotThrow(() -> repo.updateAllWithoutModifying());
        assertFalse("updated".equals(repo.getById(ID).getField()));
    }

    @Test
    void modifyingIsIgnoredInTransaction() {
        assertDoesNotThrow(() -> repo.updateAll());
        assertFalse("updated".equals(repo.getById(ID).getField()));
    }

}
