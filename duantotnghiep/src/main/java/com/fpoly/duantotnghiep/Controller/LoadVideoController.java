package com.fpoly.duantotnghiep.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
class LoadVideoController {
	@GetMapping("/courseOnline/video/{id}")
    public String loadVideo(Model model, @PathVariable("id") Long id) {
        return "loadVideo";
    }
}
