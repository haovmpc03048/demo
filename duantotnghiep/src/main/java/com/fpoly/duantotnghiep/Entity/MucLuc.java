package com.fpoly.duantotnghiep.Entity;

import java.util.Date;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "muc_luc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MucLuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "ten_muc_luc")
    private String tenMucLuc;

    @ManyToOne
    @JoinColumn(name = "id_khoa_hoc")
    private KhoaHoc khoaHoc;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @ManyToOne
    @JoinColumn(name = "nguoi_tao")
    private NguoiDung nguoiTao;


}
