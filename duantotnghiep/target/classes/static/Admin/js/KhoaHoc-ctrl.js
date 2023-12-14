var app = angular.module("myApp", []);
app.controller("KhoaHoc-ctrl", function ($scope, $http, $window) {
    $scope.itemsKhoaHoc = [];
    $scope.itemsKenhKhoaHoc = [];
    $scope.formKhoaHoc = {};
    $scope.courses = []; // Mảng để lưu trữ các khóa học
    $scope.nguoiTao = [];
    $scope.btnThemKhoaHoc = true;
    $scope.btnSuaKhoaHoc = true;
    $scope.btnXoaKhoaHoc = true;
    $scope.cboloaiKhoaHoc = [];
    $scope.modalItem = {};
    $scope.itemsMucLuc = [];
    $scope.formMucLuc = {};
    $scope.TimKhoaHoc = {};
    //định dạng tiền
    $scope.formatCurrency = function (amount) {
        // Chuyển đổi số tiền thành chuỗi và loại bỏ số 0 ở cuối
        var formattedAmount = amount.toLocaleString('vi-VN');
        return formattedAmount + '₫';
    };

    $scope.initialize = function () {
        
        // load thông tin người dùng từ session
        $http.get("/rest/admin/KhoaHoc/getUserInfo").then(response => {
            // Dùng thông tin người dùng ở đây

            $scope.nguoiTao =response.data ;
            $scope.formKhoaHoc = {
                nguoiTao:{
                    id: $scope.nguoiTao.id
                }
                
            }
            $scope.formMucLuc = {
                nguoiTao:{
                    id: $scope.nguoiTao.id
                },
                khoaHoc:{
                    id: $scope.modalItem.id
                }
            } 
        });

        // load Khóa học
        $http.get("/rest/admin/KhoaHoc").then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });

            $scope.itemsKhoaHoc = resp.data;
            $scope.btnThemKhoaHoc = true;
            $scope.btnSuaKhoaHoc = false;
            $scope.btnXoaKhoaHoc = false;
            
        });

        // load kênh Khóa học
        $http.get("/rest/admin/KhoaHoc/Duyet").then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });

            $scope.itemsKenhKhoaHoc = resp.data;
            
            
        });

        // load loại khóa học
        $http.get("/rest/admin/KhoaHoc/loaiKhoaHoc").then(resp => {
            $scope.cboloaiKhoaHoc = resp.data;
            
        });


    }

    // Khởi đầu
    $scope.initialize();

    //tìm kiếm khóa học
    $scope.timKiemKhoaHoc = function () {
         
        // load Khóa học
        $http.get("/rest/admin/KhoaHoc/tim-khoa-hoc", {
            params: {
                tenKhoaHoc: $scope.TimKhoaHoc.tenKhoaHoc,
            }
        }).then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });
            $scope.itemsKhoaHoc = resp.data;
           
            $scope.btnThemKhoaHoc = true;
            $scope.btnSuaKhoaHoc = false;
            $scope.btnXoaKhoaHoc = false;
            
        });
    }
    //tìm kiếm kênh khóa học
    $scope.timKiemKenhKhoaHoc = function () {
         
        // load Khóa học
        $http.get("/rest/admin/KhoaHoc/tim-kenh-khoa-hoc", {
            params: {
                tenKhoaHoc: $scope.TimKhoaHoc.tenKhoaHoc,
            }
        }).then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });
           
            $scope.itemsKenhKhoaHoc = resp.data;
            
            
        });
    }

    // Xóa formKhoaHoc
    $scope.resetKhoaHoc = function () {
        $scope.formKhoaHoc = {};
        // Xóa giá trị của input file
        var input = document.getElementById('imageInput');
        input.value = '';
        // Xóa nội dung của phần tử preview
        var preview = document.getElementById('preview');
        preview.innerHTML = '';

        // Hiển thị ảnh mặc định
        var preview = document.getElementById('preview');
        preview.innerHTML = '<img  src="/Admin/img/gallery.png" alt="Default Image" height="100px" width="100px">';

        $scope.btnThemKhoaHoc = true;
        $scope.btnSuaKhoaHoc = false;
        $scope.btnXoaKhoaHoc = false;

        // load thông tin người dùng từ session
        $http.get("/rest/admin/KhoaHoc/getUserInfo").then(response => {
            
            $scope.nguoiTao =response.data ;
            $scope.formKhoaHoc = {
                nguoiTao:{
                    id: $scope.nguoiTao.id
                }
                
            } 
        });
    }

    // Hiển thị lên formKhoaHoc
    $scope.editbr = function (item) {
        $scope.formKhoaHoc = angular.copy(item);
        
        let anh = $scope.formKhoaHoc.hinhAnh;
        var preview = document.getElementById('preview');
        preview.innerHTML = '<img  src="/img/'+ anh +'" alt="Default Image" height="324px" width="576px">';

        $scope.btnThemKhoaHoc = false;
        $scope.btnSuaKhoaHoc = true;
        $scope.btnXoaKhoaHoc = true;

        $(".nav-tabs button:eq(1)").tab('show');
    }    
    //Thêm mới
    $scope.create = function () {
        $scope.hassError = false;
        if ($scope.validate()) {
        //trạng thái hiển thị khoa học
        $scope.formKhoaHoc.trangThai = "false";
        $scope.formKhoaHoc.duyet = true;
        $scope.formKhoaHoc.nguoiTao.id = $scope.nguoiTao.id;
        

        var currentDate = new Date();
        $scope.formKhoaHoc.ngayTao = currentDate.toISOString();

        var formData = new FormData();
        formData.append('hinhAnh', $scope.formKhoaHoc.hinhAnh);
        $scope.formKhoaHoc.hinhAnh = $scope.formKhoaHoc.hinhAnh.name;
        
        formData.append('khoaHoc', new Blob([JSON.stringify($scope.formKhoaHoc)], { type: "application/json" }));

        $http.post('/rest/admin/KhoaHoc', formData, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.itemsKhoaHoc.push(resp.data);
             // load Khóa học
            $http.get("/rest/admin/KhoaHoc").then(resp => {
                // Chuyển đổi ngày giờ sang múi giờ Việt Nam
                resp.data.forEach(item => {
                    item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
                });

                $scope.itemsKhoaHoc = resp.data;
                
            });
            $scope.resetKhoaHoc();
            showNotification(2);
        }).catch(error => {
            showNotification(6);
            console.log("Error", error)
        });
    }
    }
     // Xóa 
     $scope.deleteKhoaHoc = function (item) {
        $http.delete(`/rest/admin/KhoaHoc/${item.id}`).then(resp => {
            var index = $scope.itemsKhoaHoc.findIndex(
                p => p.id == item.id);
            $scope.itemsKhoaHoc.splice(index, 1);
            $scope.resetKhoaHoc();
            showNotification(3);
        })
            .catch(error => {
                showNotification(7);
            })
    }
     // Cập nhật 
     $scope.updateKhoaHoc = function () {

        $scope.hassError = false;
        if ($scope.validate()) {
        var item = angular.copy($scope.formKhoaHoc);
        

        // Chuyển đổi định dạng của chuỗi ngày tháng
        item.ngayTao = moment(item.ngayTao,"DD-MM-YYYY HH:mm:ss");
        $scope.formKhoaHoc.ngayTao = item.ngayTao;
        
        


        var formData = new FormData();

        formData.append('hinhAnh', $scope.formKhoaHoc.hinhAnh);
        $scope.formKhoaHoc.hinhAnh = $scope.formKhoaHoc.hinhAnh.name;

        formData.append('khoaHoc', new Blob([JSON.stringify($scope.formKhoaHoc)], { type: "application/json" }));

        $http.put(`/rest/admin/KhoaHoc/${item.id}`, formData, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            var index = $scope.itemsKhoaHoc.findIndex(
                p => p.id == item.id);
            $scope.itemsKhoaHoc[index] = item;

            // load Khóa học
        $http.get("/rest/admin/KhoaHoc").then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });

            $scope.itemsKhoaHoc = resp.data;
            
        });showNotification(4);
            $scope.resetKhoaHoc();
            
        })
        .catch(error => {
            showNotification(8);
                console.log("Error", error)
            })
           
        
        }
}
    
     // Duyệt khóa học
     $scope.duyet = function (items) {
        
        var item = angular.copy(items);
        item.ngayTao = moment(item.ngayTao,"DD-MM-YYYY HH:mm:ss");
        item.duyet = true;

        var formData = new FormData();

        formData.append('hinhAnh', item.hinhAnh);
        
    
        formData.append('khoaHoc', new Blob([JSON.stringify(item)], { type: "application/json" }));

        $http.put(`/rest/admin/KhoaHoc/${item.id}`, formData, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            var index = $scope.itemsKhoaHoc.findIndex(
                p => p.id == item.id);
            $scope.itemsKhoaHoc[index] = item;
            
             // load kênh Khóa học
            $http.get("/rest/admin/KhoaHoc/Duyet").then(resp => {
                // Chuyển đổi ngày giờ sang múi giờ Việt Nam
                resp.data.forEach(item => {
                    item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
                });

                $scope.itemsKenhKhoaHoc = resp.data;
                
               
            });showNotification(9);
        })
            
        .catch(error => {
            showNotification(5);
                console.log("Error", error)
        })
}

 // Cập nhật trạng thái khóa học
 $scope.trangThaiKhoaHoc = function (items) {
    var item = angular.copy(items);
    
    item.ngayTao = moment(item.ngayTao,"DD-MM-YYYY HH:mm:ss");
    

    if(item.trangThai == "true"){
        item.trangThai = "false";
    }
    else if(item.trangThai == "false"){
        item.trangThai = "true"; 
    }
    var formData = new FormData();

    formData.append('hinhAnh', item.hinhAnh);

    formData.append('khoaHoc', new Blob([JSON.stringify(item)], { type: "application/json" }));

    
    $http.put(`/rest/admin/KhoaHoc/${item.id}`, formData, {
        transformRequest: angular.identity,
        headers: { 'Content-Type': undefined }
    }).then(resp => {
        var index = $scope.itemsKhoaHoc.findIndex(
            p => p.id == item.id);
        $scope.itemsKhoaHoc[index] = item;

        // load Khóa học
        $http.get("/rest/admin/KhoaHoc").then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });

            $scope.itemsKhoaHoc = resp.data;
            $scope.btnThemKhoaHoc = true;
            $scope.btnSuaKhoaHoc = false;
            $scope.btnXoaKhoaHoc = false;
            
        });
        
        if(item.trangThai == "true"){
            
            showNotification(0);
        }
        else if(item.trangThai == "false"){
            showNotification(1);
        }
        
    })
    .catch(error => {
        showNotification(6);
            console.log("Error", error)
    })
}

    $scope.pager = {
        page: 0,
        size: 5,
        get itemsKhoaHoc() {
            var start = this.page * this.size;
            return $scope.itemsKhoaHoc.slice(start, start + this.size);
        },

        get count() {
            return Math.ceil(1.0 * $scope.itemsKhoaHoc.length / this.size);
        },

        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    }

//Chuyển trang kênh khóa học
    $scope.trang = {
        page2: 0,
        size2: 5,
        get itemsKenhKhoaHoc() {
            var start = this.page2 * this.size2;
            return $scope.itemsKenhKhoaHoc.slice(start, start + this.size2);
        },

        get count2() {
            return Math.ceil(1.0 * $scope.itemsKenhKhoaHoc.length / this.size2);
        },

        first() {
            this.page2 = 0;
        },
        prev() {
            this.page2--;
            if (this.page2 < 0) {
                this.last();
            }
        },
        next() {
            this.page2++;
            if (this.page2 >= this.count2) {
                this.first();
            }
        },
        last() {
            this.page2 = this.count2 - 1;
        }
    }

    //validate
    $scope.KhoaHocMessages = {
        id: "ID is required",
        nameNull: "Tên khóa học không được rỗng",
        hocPhiNull: "Học phí khóa học không được rỗng",
        hocPhiiNaN: "Học phí khóa học phải là số",
        hinhAnhNull: "Bạn chưa chọn ảnh cho khóa học",
        loaiKhoaHocNull: "Bạn chưa chọn loại khóa học",
        // ...
    };

    $scope.validate = function () {
        $scope.message1 = "";
        $scope.message2 = "";
        $scope.message3 = "";
        $scope.message4 = "";
        $scope.checkV = false;
        if ($scope.formKhoaHoc.tenKhoaHoc === undefined) {
            $scope.hassError = true;
            $scope.message1 = $scope.KhoaHocMessages.nameNull;
            $scope.checkV = true;
        }

        if ($scope.formKhoaHoc.chiPhi === undefined) {
            $scope.hassError = true;
            $scope.message2 = $scope.KhoaHocMessages.hocPhiNull;
            $scope.checkV = true;
        }
        if ($scope.formKhoaHoc.hinhAnh === undefined) {
            $scope.hassError = true;
            $scope.message3 = $scope.KhoaHocMessages.hinhAnhNull;
            $scope.checkV = true;
        }
        if ($scope.formKhoaHoc.loaiKhoaHoc === undefined) {
            $scope.hassError = true;
            $scope.message4 = $scope.KhoaHocMessages.loaiKhoaHocNull;
            $scope.checkV = true;
        }
        if ($scope.formKhoaHoc.loaiKhoaHoc.id === null) {
            $scope.hassError = true;
            $scope.message4 = $scope.KhoaHocMessages.loaiKhoaHocNull;
            $scope.checkV = true;
        }

        
        
        if ($scope.checkV === true) {
            return false;
        } else {
            return true;
        }

    }

    $scope.itemsVideoKhoaHoc = [];
    $scope.videoKhoaHoc = {};
    $scope.loadVideoKhoaHoc = function (item) {
        $http.get(`/rest/admin/Videos/${item.id}`).then(resp => {
            $scope.itemsVideoKhoaHoc = resp.data;
            let tieuDeVideo = "";
    angular.element(document.getElementById('tieude')).text(tieuDeVideo);
            let videoUrl = "";
            // Đặt thuộc tính src của video-iframe
    let videoIframe = document.getElementById('video-iframe');
    videoIframe.src = "https://www.youtube.com/embed/" + videoUrl;
        })
    }
    
// Hiển thị video
$scope.xem = function (item) {
    $scope.videoKhoaHoc = angular.copy(item);
    
    let videoUrl = $scope.videoKhoaHoc.linkVideo;
    let tieuDeVideo = $scope.videoKhoaHoc.tenVideo;
    angular.element(document.getElementById('tieude')).text(tieuDeVideo);
    // Đặt thuộc tính src của video-iframe
    let videoIframe = document.getElementById('video-iframe');
    videoIframe.src = "https://www.youtube.com/embed/" + videoUrl;
    $(".nav-tabs button:eq(1)").tab('show');
}   
$scope.MucLuc = function (item){
    $scope.modalItem = angular.copy(item);

    $scope.openModal();

    // load Muc luc
    $http.get(`/rest/admin/MucLuc/KhoaHoc/${item.id}`).then(resp => {
        // Chuyển đổi ngày giờ sang múi giờ Việt Nam
        resp.data.forEach(item => {
            item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
        });

        $scope.itemsMucLuc = resp.data;
    });

}
$scope.EditMucLuc = function (item){
    $scope.formMucLuc = angular.copy(item);

}

//Thêm mới
$scope.createMucLuc = function () {
   
    //trạng thái hiển thị khoa học
    $scope.formMucLuc.nguoiTao.id = $scope.nguoiTao.id;
    

    var currentDate = new Date();
    $scope.formMucLuc.ngayTao = currentDate.toISOString();

    $scope.formMucLuc.khoaHoc.id = $scope.modalItem.id;

    $http.post('/rest/admin/MucLuc',$scope.formMucLuc).then(resp => {
        $scope.itemsMucLuc.push(resp.data);
         // load Muc luc
    $http.get(`/rest/admin/MucLuc/KhoaHoc/${$scope.modalItem.id}`).then(resp => {
        // Chuyển đổi ngày giờ sang múi giờ Việt Nam
        resp.data.forEach(item => {
            item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
        });

        $scope.itemsMucLuc = resp.data;
    });
        $scope.resetMucLuc();
        showNotification(2);
    }).catch(error => {
        showNotification(6);
        console.log("Error", error)
    });

}

//Sủa mới
$scope.suaMucLuc = function (item) {
   
    //trạng thái hiển thị khoa học
    $scope.formMucLuc.nguoiTao.id = $scope.nguoiTao.id;
        
    $scope.formMucLuc.ngayTao = moment(item.ngayTao,"DD-MM-YYYY HH:mm:ss");

    $scope.formMucLuc.khoaHoc.id = $scope.modalItem.id;

    $http.put(`/rest/admin/MucLuc/${item.id}`,$scope.formMucLuc).then(resp => {
        $scope.itemsMucLuc.push(resp.data);
         // load Muc luc
         $http.get(`/rest/admin/MucLuc/KhoaHoc/${$scope.modalItem.id}`).then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });
    
            $scope.itemsMucLuc = resp.data;
        });
        $scope.resetMucLuc();
        showNotification(4);
    }).catch(error => {
        showNotification(6);
        console.log("Error", error)
    });

}
// Xóa 
$scope.deleteMucLuc = function (item) {
    $http.delete(`/rest/admin/MucLuc/${item.id}`).then(resp => {
        var index = $scope.itemsMucLuc.findIndex(
            p => p.id == item.id);
        $scope.itemsMucLuc.splice(index, 1);
        $scope.resetMucLuc();
        showNotification(3);
    })
        .catch(error => {
            showNotification(7);
        })
}
$scope.resetMucLuc = function (){
    $scope.formMucLuc ={};

    // load thông tin người dùng từ session
    $http.get("/rest/admin/KhoaHoc/getUserInfo").then(response => {
            
        $scope.nguoiTao =response.data ;
        $scope.formMucLuc = {
            nguoiTao:{
                id: $scope.nguoiTao.id
            },
            khoaHoc:{
                id: $scope.modalItem.id
            }
        } 
    });
}

$scope.openModal = function () {
    document.getElementById('myModal').style.display = 'block';
};

$scope.closeModal = function () {
    document.getElementById('myModal').style.display = 'none';
};


})
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

// Mảng lưu trữ các thông báo
const notifications = [
    { text: "Đã khóa", duration: 3000, type: "success" },//---0---
    { text: "Đã mở khóa", duration: 3000, type: "success" },//---1---
    { text: "Thêm thành công", duration: 3000, type: "success" },//---2---
    { text: "Xóa thành công", duration: 3000, type: "success" },//---3---
    { text: "Sửa thành công", duration: 3000, type: "success" },//---4---
    { text: "lỗi", duration: 3000, type: "error" },//---5---
    { text: "Lỗi thêm mới", duration: 3000, type: "error" },//---6---
    { text: "Lỗi xóa", duration: 3000, type: "error" },//---7---
    { text: "Lỗi cập nhật", duration: 3000, type: "error" },//---8---
    { text: "Duyệt thành công", duration: 3000, type: "success" }//---9---
  ];

  // Hàm hiển thị thông báo
  function showNotification(index) {
    if (index < notifications.length) {
      const notification = notifications[index];

      Toastify({
        text: notification.text,
        duration: notification.duration,
        gravity: "top",
        position: 'center',
        className: notification.type,
        onClose: function() {
          // Hiển thị thông báo tiếp theo khi thông báo hiện tại được đóng
          showNotification(index + 1);
        }
      }).showToast();
    }
  }
 