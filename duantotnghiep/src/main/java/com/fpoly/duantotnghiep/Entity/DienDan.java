package com.fpoly.duantotnghiep.Entity;

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
@Table(name = "dien_dan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DienDan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_khoa_hoc")
	private KhoaHoc khoaHoc;

	@Column(name = "noi_dung", columnDefinition = "nvarchar(max)")
	private String noiDung;

	@ManyToOne
	@JoinColumn(name = "nguoi_dang")
	private NguoiDung nguoiDang;

	@Column(name = "ngay_dang")
	private Date ngayDang;

	// Getters and Setters
}
