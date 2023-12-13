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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tai_lieu_khoa_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaiLieuKhoaHoc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_khoa_hoc")
	private KhoaHoc khoaHoc;

	@Column(name = "ten_slide")
	private String tenSlide;

	@Column(name = "thu_tu")
	private int thuTu;

	@Column(name = "ten_file")
	private String tenFile;

	@Column(name = "ngay_tao")
	private Date ngayTao;
}
