package com.fpoly.duantotnghiep.service;

import java.util.ArrayList;
import java.util.List;

import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;

public interface VideoService {
    List<VideoKhoaHoc> findAll();

    List<VideoKhoaHoc> findByKhoaHocId(int idKhoaHoc);

    VideoKhoaHoc save(VideoKhoaHoc videoKhoaHoc);

    ArrayList<VideoKhoaHoc> findByMucHocId(int idMucLuc);

    void deleteById(int id);

}
