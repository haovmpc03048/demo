package com.fpoly.duantotnghiep.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;
import com.fpoly.duantotnghiep.service.NguoiDungService;

@Service
@Transactional
public class NguoiDungServiceImpl implements NguoiDungService {

	 @Autowired
	    private NguoiDungRepository nguoiDungRepository;

    private static final String IMAGE_DIRECTORY = "Admin/img/User/";  // 


    @Override
    public List<NguoiDung> findAll() {
        Sort sort = Sort.by(Direction.ASC, "hoTen");
        return nguoiDungRepository.findAll(sort);
    }

    @Override
    public Optional<NguoiDung> findById(int id) {
        return nguoiDungRepository.findById(id);
    }

    @Override
    public NguoiDung save(NguoiDung nguoiDung) {
        return nguoiDungRepository.save(nguoiDung);
    }

    @Override
    public List<NguoiDung> findByHoTen(String hoTen) {
        return (List<NguoiDung>) nguoiDungRepository.findByHoTen(hoTen);
    }

    @Override
    public boolean existsById(int id) {
        return nguoiDungRepository.existsById(id);
    }

    	
   

    @Override
    public NguoiDung findNguoiDungById(int id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }

    @Override
    public NguoiDung getNguoiDungById(int id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }
    @Override
    public NguoiDung addNguoiDung(NguoiDung nguoiDung, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + Objects.requireNonNull(file.getOriginalFilename())
                                                         .substring(file.getOriginalFilename().lastIndexOf("."));
        Path path = Paths.get(IMAGE_DIRECTORY, fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        nguoiDung.setHinhAnh("uploads/images/" + fileName);
        return nguoiDungRepository.save(nguoiDung);
    }
       @Override
    public void deleteNguoiDung(int id) {
        nguoiDungRepository.deleteById(id);
    }

        


       @Override
       public void capNhatNguoiDung(int id, NguoiDung nguoiDung, MultipartFile file) throws IOException {
           NguoiDung existingUser = nguoiDungRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("User not found"));
           // Cập nhật thông tin người dùng
           existingUser.setTaiKhoan(nguoiDung.getTaiKhoan());
           existingUser.setMatKhau(nguoiDung.getMatKhau());
           existingUser.setHoTen(nguoiDung.getHoTen());
           existingUser.setEmail(nguoiDung.getEmail());
           // Kiểm tra và cập nhật hình ảnh nếu có
           if (file != null && !file.isEmpty()) {
               // Lưu hình ảnh trong project, tên file giữ nguyên
               String fileName = file.getOriginalFilename();
               String filePath = "Admin/img/User/" + fileName;
               // Lưu hình ảnh
               Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
               // Cập nhật tên hình ảnh trong đối tượng NguoiDung
               existingUser.setHinhAnh(fileName);
           }

           nguoiDungRepository.save(existingUser);
       }


       @Override
       public NguoiDung findByTaiKhoan(String taiKhoan) {
           return nguoiDungRepository.findByTaiKhoan(taiKhoan);
       }
}
