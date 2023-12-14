package com.fpoly.duantotnghiep.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.NguoiDung;

public interface NguoiDungService {

	List<NguoiDung> findAll();

	NguoiDung findNguoiDungById(int id);

	NguoiDung save(NguoiDung nguoiDung);

	List<NguoiDung> findByHoTen(String kw);

	boolean existsById(int id);

	Optional<NguoiDung> findById(int id);

	NguoiDung getNguoiDungById(int id);

	NguoiDung addNguoiDung(NguoiDung nguoiDung, MultipartFile file) throws IOException;

	void deleteNguoiDung(int id);

	void capNhatNguoiDung(int id, NguoiDung nguoiDung, MultipartFile file) throws IOException;

	NguoiDung findByTaiKhoan(String currentUsername);

}