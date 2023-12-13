package com.fpoly.duantotnghiep.Entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "danh_gia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_khoa_hoc")
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung")
    private NguoiDung nguoiDung;

    @Column(name = "so_diem_danh_gia")
    private int soDiemDanhGia;

    @Column(name = "ngay_danh_gia")
    private Date ngayDanhGia;

    @Column(name = "noi_dung")
    private String noiDung;

    // Constructors, getters, and setters

    // Getters and Setters
}
