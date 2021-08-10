package com.example.demo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {

    @Modifying
    @Query(
        value = ""
            + "UPDATE simple "
            + "SET field = 'updated' "
            + "WHERE id IS NOT NULL "
            + "RETURNING id",
        nativeQuery = true
    )
    List<Long> updateAll();

    @Query(
        value = ""
            + "UPDATE simple "
            + "SET field = 'updated' "
            + "WHERE id IS NOT NULL "
            + "RETURNING id",
        nativeQuery = true
    )
    List<Integer> updateAllWithoutModifying();

}
