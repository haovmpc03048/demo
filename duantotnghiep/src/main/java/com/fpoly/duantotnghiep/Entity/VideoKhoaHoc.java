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
@Table(name = "video_khoa_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoKhoaHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_muc_luc")
    private MucLuc mucLuc;

    @Column(name = "link_video")
    private String linkVideo;

    @Column(name = "thu_tu")
    private int thuTu;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ten_video")
    private String tenVideo;

    @ManyToOne
    @JoinColumn(name = "nguoi_tao")
    private NguoiDung nguoiTao;
    // Getters and Setters
}
