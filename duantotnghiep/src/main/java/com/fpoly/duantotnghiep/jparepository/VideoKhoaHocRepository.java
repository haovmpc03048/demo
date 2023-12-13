package com.fpoly.duantotnghiep.jparepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.duantotnghiep.Entity.ChungChi;
import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;

public interface VideoKhoaHocRepository extends JpaRepository<VideoKhoaHoc, Integer> {

	@Query("SELECT vkh FROM VideoKhoaHoc vkh INNER JOIN vkh.mucLuc ml INNER JOIN ml.khoaHoc kh WHERE kh.id = :KhoaHocId ORDER BY vkh.id ASC")
	ArrayList<VideoKhoaHoc> findByKhoaHocIdOrderByAsc(@Param("KhoaHocId") Integer khoaHocId);

	@Query("SELECT vkh FROM VideoKhoaHoc vkh INNER JOIN vkh.mucLuc ml ON ml.id = vkh.mucLuc.id WHERE ml.id = :khoaHocId ORDER BY vkh.id ASC")
	ArrayList<VideoKhoaHoc> findByMucLucIdOrderByAsc(@Param("khoaHocId") Integer khoaHocId);

	VideoKhoaHoc findByMucLucId(int idMucLuc);

	VideoKhoaHoc findBylinkVideo(String linkVideo);

}
