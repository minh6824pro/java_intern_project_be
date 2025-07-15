package com.go.assignment.seeder;

import com.go.assignment.entity.DiemThi;
import com.go.assignment.repository.DiemThiRepository;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("flywayInitializer")
public class Seeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Seeder.class);
    private final DiemThiRepository repository;
    private static final int BATCH_SIZE = 5000;

    @PersistenceContext
    private EntityManager entityManager;

    public Seeder(DiemThiRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // ❗ Kiểm tra nếu DB đã có dữ liệu thì không seed nữa
        if (repository.count() > 0) {
            log.info("➡️ Đã có dữ liệu, bỏ qua seeding.");
            return;
        }

        InputStream inputStream = getClass().getResourceAsStream("/seed/diem_thi_thpt_2024.csv");
        if (inputStream == null) {
            log.error("❌ Không tìm thấy file CSV.");
            return;
        }

        log.info("🚀 Bắt đầu import dữ liệu...");
        long startTime = System.currentTimeMillis();
        int totalRecords = 0;
        int batchCount = 0;

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            // Skip header
            reader.readNext();
            List<DiemThi> batch = new ArrayList<>(BATCH_SIZE);
            String[] row;
            
            while ((row = reader.readNext()) != null) {
                DiemThi diem = new DiemThi();
                diem.setSbd(row[0]);
                diem.setToan(parse(row[1]));
                diem.setVan(parse(row[2]));
                diem.setAnh(parse(row[3]));
                diem.setLy(parse(row[4]));
                diem.setHoa(parse(row[5]));
                diem.setSinh(parse(row[6]));
                diem.setSu(parse(row[7]));
                diem.setDia(parse(row[8]));
                diem.setGdcd(parse(row[9]));
                diem.setMaNgoaiNgu(row[10]);
                
                batch.add(diem);
                totalRecords++;

                // Khi đủ kích thước batch
                if (batch.size() >= BATCH_SIZE) {
                    processBatch(batch, totalRecords, batchCount++);
                    batch = new ArrayList<>(BATCH_SIZE);
            }

                // In tiến độ mỗi 50,000 records
                if (totalRecords % 50000 == 0) {
                    log.info("📊 Đã xử lý: {} records - Batch #{}", 
                        totalRecords, batchCount);
                }
            }

            // Xử lý batch cuối cùng nếu còn
            if (!batch.isEmpty()) {
                processBatch(batch, totalRecords, batchCount++);
            }

            long endTime = System.currentTimeMillis();
            double timeInSeconds = (endTime - startTime) / 1000.0;
            double recordsPerSecond = totalRecords / timeInSeconds;

            log.info("\n✅ Import hoàn tất!");
            log.info("📈 Tổng số records: {}", totalRecords);
            log.info("⏱️ Thời gian thực hiện: {:.2f} giây", timeInSeconds);
            log.info("🚄 Tốc độ: {:.2f} records/giây", recordsPerSecond);
        }
    }

    private void processBatch(List<DiemThi> batch, int totalRecords, int batchCount) {
        for (DiemThi record : batch) {
            entityManager.persist(record);
        }
        entityManager.flush();
        entityManager.clear();
    }

    private Double parse(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}