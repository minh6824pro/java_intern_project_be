package com.go.assignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "diem_thi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiemThi implements Serializable {
    
    @Id
    private String sbd;
    private Double toan;
    private Double van;
    private Double anh;
    private Double ly;
    private Double hoa;
    private Double sinh;
    private Double su;
    private Double dia;
    private Double gdcd;
    private String maNgoaiNgu;
}