package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.DanhGia;
import com.fpoly.duantotnghiep.jparepository.DanhGiaRepository;
import com.fpoly.duantotnghiep.service.DanhGiaService;

@Service
public class DanhGiaServiceImpl implements DanhGiaService {

    @Autowired
    DanhGiaRepository danhGiaRepository;

    @Override
    public List<DanhGia> findAll() {
        return danhGiaRepository.findAll();
    }

    @Override
    public DanhGia save(DanhGia danhGia) {
        return danhGiaRepository.save(danhGia);
    }

}
