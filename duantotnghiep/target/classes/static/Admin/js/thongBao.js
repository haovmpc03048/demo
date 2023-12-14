app.controller("thongBaoCtrl", function ($scope, $http, $window) {
    $scope.notifications = [];

    $http.get("/rest/admin/NguoiDung").then(resp => {
        $scope.nguoiDung = resp.data;
        $scope.nguoiDung.forEach(user => {
            user.type = 'user';
            $scope.notifications.push(user);
        });
        processNotifications();
    });

    $http.get("/api/tiendokhoahoc").then(resp => {
        $scope.dangKyKhoaHoc = resp.data;
        $scope.dangKyKhoaHoc.forEach(course => {
            course.type = 'course';
            $scope.notifications.push(course);
        });
        processNotifications();
    });

    function processNotifications() {
        // lọc theo thời gian của thông báo
        $scope.notifications.sort(function (a, b) {
            return new Date(b.thoiGianTao || b.ngayDangKy) - new Date(a.thoiGianTao || a.ngayDangKy);
        });

        $scope.notifications.forEach(notification => {
            notification.timeDifference = $scope.calculateTimeDifference(notification.thoiGianTao || notification.ngayDangKy);
        });

    }
    //tính ngày giờ thời gian chênh lệch
    $scope.calculateTimeDifference = function (thoiGianTao) {
        var thoiGianHienTai = new Date();
        var thoiGianChenhLech = thoiGianHienTai - new Date(thoiGianTao);

        var phutChenhLech = Math.floor(thoiGianChenhLech / (1000 * 60));
        var gioChenhLech = Math.floor(thoiGianChenhLech / (1000 * 60 * 60));
        var ngayChenhLech = Math.floor(thoiGianChenhLech / (1000 * 60 * 60 * 24));

        if (ngayChenhLech > 0) {
            return ngayChenhLech + (ngayChenhLech === 1 ? " ngày" : " ngày") + " trước";
        } else if (gioChenhLech > 0) {
            return gioChenhLech + (gioChenhLech === 1 ? " giờ" : " giờ") + " trước";
        } else if (phutChenhLech > 0) {
            return phutChenhLech + (phutChenhLech === 1 ? " phút" : " phút") + " trước";
        } else {
            return "Vừa xong";
        }
    };
});
