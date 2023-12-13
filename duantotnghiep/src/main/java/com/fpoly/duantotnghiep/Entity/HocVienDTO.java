package com.fpoly.duantotnghiep.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "hoc_vien")
@Getter
@Setter
public class HocVienDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idNguoiDung;
    private String hoTen;
    private String soDienThoai;
    private String chucVu;
    private String trangThai;
    private String khoaHoc;
    private String tenKhoaHoc;
    private Double chiPhi;
  

    // Constructors, getters, and setters
}
