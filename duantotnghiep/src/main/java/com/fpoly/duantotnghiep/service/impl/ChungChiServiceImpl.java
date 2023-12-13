package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.ChungChi;
import com.fpoly.duantotnghiep.jparepository.ChungChiRepository;
import com.fpoly.duantotnghiep.service.ChungChiService;

@Service
public class ChungChiServiceImpl implements ChungChiService {
    @Autowired
    ChungChiRepository chungChiRepository;

    @Override
    public List<ChungChi> findAll() {
        return chungChiRepository.findAll();
    }

    @Override
    public List<ChungChi> findByKhoaHocId(int idKhoaHoc) {
        return chungChiRepository.findByKhoaHocId(idKhoaHoc);
    }

    @Override
    public List<ChungChi> findByNguoiDungId(int idNguoiDung) {
        return chungChiRepository.findByNguoiDungId(idNguoiDung);
    }

    @Override
    public List<ChungChi> findByKhoaHocIdAndNguoiDungId(int idKhoaHoc, int idNguoiDung) {
        return chungChiRepository.findByKhoaHocIdAndNguoiDungId(idKhoaHoc, idNguoiDung);
    }

    @Override
    public ChungChi save(ChungChi chungChi) {
        return chungChiRepository.save(chungChi);
    }

}
