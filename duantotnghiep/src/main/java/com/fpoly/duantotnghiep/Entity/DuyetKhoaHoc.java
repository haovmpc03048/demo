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
@Table(name = "duyet_khoa_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuyetKhoaHoc {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_khoa_hoc")
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_duyet")
    private NguoiDung nguoiDuyet;

    @Column(name = "trang_thai")
    private String trangThai;

    @Column(name = "ngay_duyet")
    private Date ngayDuyet;

    // Getters and Setters
}
