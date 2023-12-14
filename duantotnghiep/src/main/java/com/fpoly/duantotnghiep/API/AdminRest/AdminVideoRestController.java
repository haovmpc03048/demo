package com.fpoly.duantotnghiep.API.AdminRest;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.ChungChi;
import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;
import com.fpoly.duantotnghiep.service.VideoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/admin/Videos")
public class AdminVideoRestController {
    @Autowired
    VideoService videoService;

    @GetMapping()
    public List<VideoKhoaHoc> getAll() {
        return videoService.findAll();
    }

    @GetMapping("/{idKhoaHoc}")
    public List<VideoKhoaHoc> getByKhoaHoc(@PathVariable int idKhoaHoc) {
        return videoService.findByKhoaHocId(idKhoaHoc);
    }

    @PostMapping()
    public VideoKhoaHoc add(@RequestBody VideoKhoaHoc videoKhoaHoc) {
        return videoService.save(videoKhoaHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        videoService.deleteById(id);
    }

    @GetMapping("/mucLuc/{idMucLuc}")
    public ArrayList<VideoKhoaHoc> getByMucLuc(@PathVariable int idMucLuc) {
        return videoService.findByMucHocId(idMucLuc);
    }

}
