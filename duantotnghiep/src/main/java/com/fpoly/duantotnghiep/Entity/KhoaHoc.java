package com.fpoly.duantotnghiep.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "khoa_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KhoaHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ten_khoa_hoc")
    private String tenKhoaHoc;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "loai")
    private LoaiKhoaHoc loaiKhoaHoc;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @ManyToOne
    @JoinColumn(name = "nguoi_tao")
    private NguoiDung nguoiTao;

    @Column(name = "chi_phi")
    private double chiPhi;

    @Column(name = "duyet")
    private boolean duyet;

    @Column(name = "trang_thai")
    private String trangThai;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @OneToMany(mappedBy = "khoaHoc")
    @JsonIgnore
    private java.util.List<DangKyKhoaHoc> dangKyKhoaHocs;

    // Constructors, getters, and setters
}
