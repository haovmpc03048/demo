package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.BinhLuan;
import com.fpoly.duantotnghiep.jparepository.BinhLuanRepository;
import com.fpoly.duantotnghiep.service.BinhLuanService;

@Service
public class BinhLuanServiceImpl implements BinhLuanService {
    @Autowired
    BinhLuanRepository binhLuanRepository;

    @Override
    public List<BinhLuan> findAll() {
        return binhLuanRepository.findAll();
    }

    @Override
    public List<BinhLuan> findByBaiDangId(int id) {
        return binhLuanRepository.findByBaiDangId(id);
    }

    @Override
    public BinhLuan deleteById(int id) {
        BinhLuan binhLuan = binhLuanRepository.findById(id).get();
        binhLuanRepository.deleteById(id);
        return binhLuan;
    }

    @Override
    public BinhLuan save(BinhLuan binhLuan) {
        return binhLuanRepository.save(binhLuan);
    }
}
