package com.go.assignment.controller;

import com.go.assignment.entity.DiemThi;
import com.go.assignment.repository.DiemThiRepository;
import com.go.assignment.service.DiemThiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diem-thi")
public class DiemThiController {

    private final DiemThiService service;
    public DiemThiController(DiemThiService service) {
        this.service = service;
    }

    @GetMapping("/{sbd}")
    public ResponseEntity<DiemThi> getDiemThi(@PathVariable String sbd) {
        return ResponseEntity.ok(service.findBySbd(sbd));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Map<String, Integer>>> getStatistics() {
        return ResponseEntity.ok(service.statistics());
    }

    @GetMapping("/top10A")
    public ResponseEntity<List<Object[]>> getTop10A() {
        return ResponseEntity.ok(service.top10A());
    }
}
