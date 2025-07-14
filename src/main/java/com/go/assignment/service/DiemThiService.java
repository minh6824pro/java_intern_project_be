package com.go.assignment.service;

import com.go.assignment.entity.DiemThi;
import java.util.List;
import java.util.Map;

public interface DiemThiService {
    DiemThi findBySbd(String sbd);
    Map<String, Map<String, Integer>> statistics();
    List<Object[]> top10A();
}