package com.fpoly.duantotnghiep.API.ClientRest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.MucLuc;
import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;
import com.fpoly.duantotnghiep.service.KhoaHocService;
import com.fpoly.duantotnghiep.service.MucLucService;
import com.fpoly.duantotnghiep.service.VideoKhoaHocService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping("rest/loadVideo")
public class VideoRestController {
	@Autowired
	KhoaHocService daoHocService;

	@Autowired
	MucLucService mucLucService;

	@Autowired
	VideoKhoaHocService daoVideoKhoaHocService;

	@PostMapping("/store-id-in-session")
	public ResponseEntity<String> storeIdInSession(@RequestBody String idJson, HttpServletRequest request) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(idJson);
			String id = jsonNode.get("id").asText();

			HttpSession session = request.getSession(true);
			session.setAttribute("videoId", id);

			return ResponseEntity.ok("{\"message\": \"ID stored in session\"}");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error storing ID in session: " + e.getMessage());
		}
	}

	@GetMapping("/get-video-id/{id}")
	public ResponseEntity<List<VideoKhoaHoc>> findByKhoaHocId(@PathVariable("id") Integer id) {

		List<VideoKhoaHoc> videoKhoaHocs = daoVideoKhoaHocService.findByKhoaHocId(id);

		if (!videoKhoaHocs.isEmpty()) {
			return ResponseEntity.ok(videoKhoaHocs);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
