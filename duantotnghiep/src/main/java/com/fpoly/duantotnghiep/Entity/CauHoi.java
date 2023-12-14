package com.fpoly.duantotnghiep.Entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
@Table(name = "cau_hoi")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CauHoi {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_muc_luc")
    private MucLuc mucLuc;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "cau_hoi", columnDefinition = "nvarchar(max)")
    private String cauHoi;

    @Column(name = "cau_tra_loi", columnDefinition = "nvarchar(max)")
    private String cauTraLoi;

    @Column(name = "dap_an")
    private String dapAn;

}
