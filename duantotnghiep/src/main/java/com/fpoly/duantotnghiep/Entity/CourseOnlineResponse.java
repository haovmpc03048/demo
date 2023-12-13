package com.fpoly.duantotnghiep.Entity;

import java.util.List;

public class CourseOnlineResponse {
    private List<VideoKhoaHoc> videoKhoaHoc;
    private KhoaHoc courseOnline;
    private List<MucLuc> mucLuc;
    private List<CauHoi> cauHois;

    // Constructors, getters, and setters

    public List<VideoKhoaHoc> getVideoKhoaHoc() {
    	return videoKhoaHoc;
    }
    
    public void setVideoKhoaHoc(List<VideoKhoaHoc> videoKhoaHoc) {
        this.videoKhoaHoc = videoKhoaHoc;
    }

    public KhoaHoc getCourseOnline() {
        return courseOnline;
    }

    public void setCourseOnline(KhoaHoc courseOnline) {
        this.courseOnline = courseOnline;
    }

    public List<MucLuc> getMucLuc() {
        return mucLuc;
    }

    public void setMucLuc(List<MucLuc> mucLuc) {
        this.mucLuc = mucLuc;
    }

    public List<CauHoi> getCauHois() {
        return cauHois;
    }

    public void setCauHois(List<CauHoi> cauHois) {
        this.cauHois = cauHois;
    }
}
