package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fpoly.duantotnghiep.jparepository.MucLucReponsitory;
import com.fpoly.duantotnghiep.Entity.MucLuc;
import com.fpoly.duantotnghiep.service.MucLucService;

@Service
public class MucLucServiceImpl implements MucLucService {
    @Autowired
    MucLucReponsitory mucLucReponsitory;

    @Override
    public List<MucLuc> findAll() {
        return mucLucReponsitory.findAll();
    }

    @Override
    public List<MucLuc> findByKhoaHoc(Integer idKhoaHoc) {
        return mucLucReponsitory.findByKhoaHoc(idKhoaHoc);
    }

    @Override
    public MucLuc findById(int id) {
        return mucLucReponsitory.findById(id);
    }

    @Override
    public MucLuc save(MucLuc mucLuc) {
        return mucLucReponsitory.save(mucLuc);
    }

    @Override
    public void deleteById(int id) {
        mucLucReponsitory.deleteById(id);
    }
}
