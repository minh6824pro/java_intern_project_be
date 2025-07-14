package com.go.assignment.service.impl;

import com.go.assignment.entity.DiemThi;
import com.go.assignment.exception.SbdNotFoundException;
import com.go.assignment.repository.DiemThiRepository;
import com.go.assignment.service.DiemThiService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class DiemThiServiceImpl implements DiemThiService {

    private final DiemThiRepository repository;

    public DiemThiServiceImpl(DiemThiRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "diemthi", key = "#sbd")
    public DiemThi findBySbd(String sbd) {
        if (sbd == null || !sbd.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("SBD phải có đúng 8 chữ số");
        }
        return repository.findById(sbd)
                .orElseThrow(() -> new SbdNotFoundException(sbd));
    }

    @Override
    @Cacheable(value = "statistics", key = "'all'", unless = "#result == null")
    public Map<String, Map<String, Integer>> statistics() {
        Map<String, Integer> toanMap = initEmptyLevelMap();
        Map<String, Integer> vanMap = initEmptyLevelMap();
        Map<String, Integer> anhMap = initEmptyLevelMap();
        Map<String, Integer> lyMap = initEmptyLevelMap();
        Map<String, Integer> hoaMap = initEmptyLevelMap();
        Map<String, Integer> sinhMap = initEmptyLevelMap();
        Map<String, Integer> suMap = initEmptyLevelMap();
        Map<String, Integer> diaMap = initEmptyLevelMap();
        Map<String, Integer> gdcdMap = initEmptyLevelMap();

        try (Stream<DiemThi> stream = repository.streamAll()) {
            stream.forEach(diemThi -> {
                if (diemThi.getToan() != null) updateScore(toanMap, diemThi.getToan());
                if (diemThi.getVan() != null) updateScore(vanMap, diemThi.getVan());
                if (diemThi.getAnh() != null) updateScore(anhMap, diemThi.getAnh());
                if (diemThi.getLy() != null) updateScore(lyMap, diemThi.getLy());
                if (diemThi.getHoa() != null) updateScore(hoaMap, diemThi.getHoa());
                if (diemThi.getSinh() != null) updateScore(sinhMap, diemThi.getSinh());
                if (diemThi.getSu() != null) updateScore(suMap, diemThi.getSu());
                if (diemThi.getDia() != null) updateScore(diaMap, diemThi.getDia());
                if (diemThi.getGdcd() != null) updateScore(gdcdMap, diemThi.getGdcd());
            });
        }

        Map<String, Map<String, Integer>> map = new HashMap<>();
        map.put("toan", toanMap);
        map.put("van", vanMap);
        map.put("anh", anhMap);
        map.put("ly", lyMap);
        map.put("hoa", hoaMap);
        map.put("sinh", sinhMap);
        map.put("su", suMap);
        map.put("dia", diaMap);
        map.put("gdcd", gdcdMap);

        return map;
    }

    private void updateScore(Map<String, Integer> map, double score) {
        if (score >= 8) {
            map.put("greaterThan8", map.get("greaterThan8") + 1);
        } else if (score >= 6) {
            map.put("greaterThan6", map.get("greaterThan6") + 1);
        } else if (score >= 4) {
            map.put("greaterThan4", map.get("greaterThan4") + 1);
        } else {
            map.put("lessThan4", map.get("lessThan4") + 1);
        }
    }

    private Map<String, Integer> initEmptyLevelMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("greaterThan8", 0);
        map.put("greaterThan6", 0);
        map.put("greaterThan4", 0);
        map.put("lessThan4", 0);
        return map;
    }

    @Override
    @Cacheable(value = "top10A", key = "'all'", unless = "#result == null")
    public List<Object[]> top10A() {
        return repository.findTop10A(PageRequest.of(0,10));
    }
} 