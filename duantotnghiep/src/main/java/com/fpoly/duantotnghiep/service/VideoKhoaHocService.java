package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;

public interface VideoKhoaHocService {

	VideoKhoaHoc findByVideoKhoaHocId(Integer id);

    List<VideoKhoaHoc> findByKhoaHocId(Integer khoaHocId);

}
