package com.fpoly.duantotnghiep.service;

import java.util.List;
import com.fpoly.duantotnghiep.Entity.MucLuc;

public interface MucLucService {
    List<MucLuc> findAll();

    List<MucLuc> findByKhoaHoc(Integer idKhoaHoc);

    MucLuc findById(int id);

    MucLuc save(MucLuc mucLuc);

    void deleteById(int id);
}
