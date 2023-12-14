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
@Table(name = "dang_ky_khoa_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DangKyKhoaHoc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_nguoi_dung")
	private NguoiDung nguoiDung;

	@ManyToOne
	@JoinColumn(name = "id_khoa_hoc")
	private KhoaHoc khoaHoc;

	@Column(name = "ngay_dang_ky")
	private Date ngayDangKy;

	@Column(name = "tien_do")
    private String tienDo;
	
    @Column(name = "trang_thai")
    private String trangThai;
	// Getters and Setters
}
