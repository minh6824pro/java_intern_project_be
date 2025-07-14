package com.go.assignment.repository;

import com.go.assignment.entity.DiemThi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;
import java.util.List;

@Repository
public interface DiemThiRepository extends JpaRepository<DiemThi, String> {
    @Modifying(clearAutomatically = true)
    @Transactional(readOnly = true)
    @Query("SELECT d FROM DiemThi d")
    Stream<DiemThi> streamAll();

    @Query("""
    SELECT d.sbd, d.toan, d.ly, d.hoa FROM DiemThi d
    WHERE d.toan IS NOT NULL AND d.ly IS NOT NULL AND d.hoa IS NOT NULL
    ORDER BY (d.toan + d.ly + d.hoa) DESC
    """)
    List<Object[]> findTop10A(Pageable pageable);
}