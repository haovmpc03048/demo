var app = angular.module("myApp", ['ui.bootstrap']);
app.controller("Video-ctrl", function ($scope, $http, $window) {
    $scope.itemsVideoWithTen = [];
    $scope.itemsVideo = [];
    $scope.formTaiLieu = {};
    $scope.order = null;
    $scope.itemsKhoaHoc = [];
    $scope.formKhoaHoc = {};
    $scope.inputString = [];
    $scope.itemsNguoiDung = [];
    $scope.formVideo = {};
    $scope.MucLuc = [];
    $scope.MucLuc1 = [];

    $scope.videoTitles = [];


    $scope.currentPage = 1;
    $scope.itemsPerPage = 5;
    $scope.totalItems = 0;
    $scope.selectedMucLuc = ""; // Khởi tạo selectedMucLuc trong $scope

    const accountTokenInput = document.getElementById("accessToken");
    const authorizationTokenGroup = document.getElementById('authorizationTokenGroup');
    const accessTokenGroup = document.getElementById('accessTokenGroup');
    const titleGroup = document.getElementById('form-title');
    const privacyStatusGroup = document.getElementById('form-privacyStatus');
    const descriptionGroup = document.getElementById('form-description');
    const fileGroup = document.getElementById('form-file');

    function getCookieValue(cookieName) {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.startsWith(cookieName + '=')) {
                console.log(cookie.substring(cookieName.length + 1));
                return cookie.substring(cookieName.length + 1);
            }
        }
        return null;
    }

    function deleteCookie(cookieName) {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.startsWith(cookieName + '=')) {
                const cookieParts = cookie.split('=');
                const name = cookieParts[0];
                // Đặt thời gian hết hạn của cookie thành ngày quá khứ
                document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
                return;
            }
        }
    }



    $scope.saveChanges = function () {
        // Logic để lưu các thay đổi, có thể là gọi API hoặc thực hiện các thao tác khác
        // Sau khi lưu thành công, có thể ẩn form chỉnh sửa
        $scope.closeForm();
    };

    $scope.closeForm = function () {
        // Đưa form chỉnh sửa về trạng thái ẩn
        var editForm = document.querySelector('.edit-form');
        if (editForm) {
            editForm.style.display = 'none';
            $scope.formIsVisible = false; // Ban đầu form ẩn đi
            console.log($scope.formIsVisible);

        }
    };


    $scope.delete = function (id) {
        if (confirm("Bạn có chắc chắn muốn xóa không?")) {
            $http.delete(`/rest/admin/Videos/${id}`).then(resp => {
                showNotification(3);
                $scope.initialize();
            }).catch(error => {
                showNotification(7);
                console.error("Error", error);
            });
        }
    };


    $scope.initialize = function () {
        $http.get("/rest/admin/Videos").then(resp => {
            $scope.itemsVideo = resp.data;
            console.log($scope.itemsVideo);
            $scope.totalItems = $scope.itemsVideo.length;
            $scope.pageChanged(); // Hiển thị trang đầu tiên
        }).catch(error => {
            console.error("An error occurred:", error);
        });

        $http.get("/rest/admin/KhoaHoc").then(resp => {
            $scope.itemsKhoaHoc = resp.data;
        });
    };



    $scope.loadDocuments = function (course) {
        $scope.selectedCourse = course;

        $http.get("/rest/admin/MucLuc/KhoaHoc/" + $scope.selectedCourse).then(resp => {
            $scope.MucLuc = resp.data;
            console.log($scope.MucLuc);
        });


        if ($scope.selectedCourse) {
            $http.get("/rest/admin/Videos/" + $scope.selectedCourse).then(resp => {
                $scope.itemsVideo = resp.data;
                $scope.totalItems = $scope.itemsVideo.length;

                $scope.pageChanged(); // Hiển thị trang đầu tiên

                console.log($scope.order);
            });
        } else {
            $http.get("/rest/admin/Videos").then(resp => {
                $scope.itemsVideo = resp.data;
                $scope.totalItems = $scope.itemsVideo.length;
                $scope.pageChanged(); // Hiển thị trang đầu tiên
                $scope.order = null;
            });
        }

    };

    $scope.getSelectedMucLuc = function (selectedMucLuc) {
        console.log(selectedMucLuc);
        $http.get("/rest/admin/MucLuc/" + selectedMucLuc).then(resp => {
            console.log(resp.data);
            $scope.selectedMucLuc = selectedMucLuc;
        });
        $http.get("/rest/admin/Videos/mucLuc/" + selectedMucLuc).then(resp => {
            console.log(resp.data.length);
            $scope.order = resp.data.length;
        });
    };



    // Hàm xử lý phân trang
    $scope.pageChanged = function () {
        var startIndex = ($scope.currentPage - 1) * $scope.itemsPerPage;
        var endIndex = startIndex + $scope.itemsPerPage;
        $scope.displayedItems = $scope.itemsVideo.slice(startIndex, endIndex);
    };

    $scope.goToFirstPage = function () {
        $scope.currentPage = 1;
        $scope.pageChanged();
    };

    $scope.goToPreviousPage = function () {
        if ($scope.currentPage > 1) {
            $scope.currentPage--;
            $scope.pageChanged();
        }
    };

    $scope.goToNextPage = function () {
        if ($scope.currentPage < $scope.totalItems / $scope.itemsPerPage) {
            $scope.currentPage++;
            $scope.pageChanged();
        }
    };

    $scope.goToLastPage = function () {
        $scope.currentPage = Math.ceil($scope.totalItems / $scope.itemsPerPage);
        $scope.pageChanged();
    };

    console.log($scope.itemsVideoWithTen);
    console.log($scope.itemsVideo);

    $scope.updateUI = function () {
        if (accountTokenInput === null || accountTokenInput.value === '' || accountTokenInput.value === null) {
            authorizationTokenGroup.style.display = 'block';
            accessTokenGroup.style.display = 'block';
            titleGroup.style.display = 'none';
            privacyStatusGroup.style.display = 'none';
            descriptionGroup.style.display = 'none';
            fileGroup.style.display = 'none';
        } else {
            authorizationTokenGroup.style.display = 'none';
            accessTokenGroup.style.display = 'none';
            titleGroup.style.display = 'block';
            privacyStatusGroup.style.display = 'block';
            descriptionGroup.style.display = 'block';
            fileGroup.style.display = 'block';
        }
    };
    function getYoutubeVideoId(youtubeLink) {
        const regExp = /^.*(?:youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
        const match = youtubeLink.match(regExp);

        if (match && match[1]) {
            return match[1];
        } else {
            return null;
        }
    }

    function getYoutubeVideoTitle(videoId, callback) {
        // Sử dụng API của YouTube để lấy thông tin video
        const apiUrl = `https://www.googleapis.com/youtube/v3/videos?part=snippet&id=${videoId}&key=AIzaSyAMbEBUWSt19z0-kaQ-yUEV84r-YONdGh0`;

        $http.get(apiUrl)
            .then(resp => {
                if (resp.data.items && resp.data.items.length > 0) {
                    const videoTitle = resp.data.items[0].snippet.title;
                    callback(videoTitle);
                } else {
                    callback(null);
                }
            })
            .catch(error => {
                console.log("Error", error);
                callback(null);
            });
    }

    $scope.create = function () {
        const selectedCourseId = $scope.selectedCourse;
        const youtubeLink = document.getElementById("youtubeLink").value;

        if (!selectedCourseId) {
            showNotification(12)
            return;
        }

        if (!youtubeLink.trim()) {
            showNotification(13);
            return;
        }

        const videoId = getYoutubeVideoId(youtubeLink);

        if (!videoId) {
            showNotification(14);
            return;
        }
        if (!$scope.selectedMucLuc) {
            showNotification(23);
            return;
        } else {
            $http.get("/rest/admin/MucLuc/" + $scope.selectedMucLuc)
                .then(resp => {
                    $scope.formVideo.mucLuc = resp.data;
                    $http.get("/rest/admin/NguoiDung/" + 1)
                        .then(resp => {
                            $scope.formVideo.nguoiTao = resp.data;
                            $scope.formVideo.linkVideo = videoId;
                            $scope.formVideo.thuTu = $scope.order + 1;

                            // Lấy ngày và giờ hiện tại
                            var currentDate = new Date();
                            $scope.formVideo.ngayTao = currentDate.toISOString(); // Lưu dưới dạng ISO string

                            // Lấy tiêu đề của video
                            getYoutubeVideoTitle(videoId, function (videoTitle) {
                                if (videoTitle) {
                                    $scope.formVideo.tenVideo = videoTitle;
                                } else {
                                    showNotification(15);
                                    return;
                                }

                                // Gửi yêu cầu POST để thêm video mới
                                return $http.post(`/rest/admin/Videos`, $scope.formVideo);
                            });
                        })

                })
                .then(resp => {
                    console.log("Success", resp);
                    showNotification(2);
                    $scope.loadDocuments();
                })
                .catch(error => {
                    console.log("Error", error);
                    showNotification(6);
                });
        }
    };




    $scope.createNewVideo = function () {
        const selectedCourseId = $scope.selectedCourse;
        if (!selectedCourseId) {
            showNotification(12);
            return;
        }

        const usernameCookie = getCookieValue('videoId');
        // Lấy đối tượng input theo id "title"
        var inputElement = document.getElementById("title");
        var videoTitle;
        // Lấy giá trị từ input khi người dùng nhấn nút "Lấy giá trị"
        videoTitle = inputElement.value;

        console.log(usernameCookie);
        console.log(videoTitle);

        $http.get("/rest/admin/MucLuc/" + $scope.selectedMucLuc)
            .then(resp => {
                $scope.formVideo.mucLuc = resp.data;

                $http.get("/rest/admin/NguoiDung/" + 1)
                    .then(resp => {
                        $scope.formVideo.nguoiTao = resp.data;
                        $scope.formVideo.linkVideo = usernameCookie;
                        $scope.formVideo.tenVideo = videoTitle;
                        $scope.formVideo.thuTu = $scope.order + 1;

                        // Lấy ngày và giờ hiện tại
                        var currentDate = new Date();
                        $scope.formVideo.ngayTao = currentDate.toISOString(); // Lưu dưới dạng ISO string

                        deleteCookie('videoId');
                        deleteCookie('videoTitle');

                        showNotification(2);
                        $scope.loadDocuments();

                        // Gửi yêu cầu POST để thêm video mới
                        console.log($scope.formVideo);

                        $http.post(`/rest/admin/Videos`, $scope.formVideo)
                            .then(resp => {
                                // Xử lý kết quả thành công nếu cần thiết
                            })
                            .catch(error => {
                                console.log("Error", error);
                                showNotification(6);
                            });

                    })
                    .catch(error => {
                        console.log("Error", error);
                        showNotification(6);
                    });
            })
            .catch(error => {
                console.log("Error", error);
                showNotification(6);
            });
    };


    $scope.updateUI();
    $scope.initialize();


    $scope.itemsKhoaHoc = []; // Danh sách khóa học của bạn
    $scope.KhoaHocChon = null; // Khởi tạo biến selectedCourse
    $scope.showAllCourses = function () {
        $scope.KhoaHocChon = null; // Đặt selectedCourse thành null
        // Hiển thị tất cả các khóa học bằng cách áp dụng bộ lọc
        $scope.searchCourse = "";
        console.log("selectedCourse is null");
        $scope.loadDocuments();
    };
    $scope.selectCourse = function (course) {
        $scope.KhoaHocChon = course;
        $scope.loadDocuments(course.id);

    };



});



document.addEventListener("DOMContentLoaded", function () {
    const uploadNewVideoSection = document.getElementById("uploadNewVideoSection");
    const addButton = document.getElementById("addButton");

    if (!uploadNewVideoSection || !addButton) {
        console.error("Không thể tìm thấy các phần tử cần thiết.");
        return;
    }

    addButton.addEventListener("click", function () {
        if (uploadNewVideoSection.style.display !== "none") {
            const titleInput = document.getElementById("title");
            const descriptionInput = document.getElementById("description");
            const privacyStatusInput = document.getElementById("privacyStatus");
            const fileInput = document.getElementById("file");

            if (!titleInput.value) {
                showNotification(16);
                return;
            }

            if (!descriptionInput.value) {
                showNotification(17);
                return;
            }

            if (!privacyStatusInput.value) {
                showNotification(18);
                return;
            }

            if (!fileInput.files[0]) {
                showNotification(19);
                return;
            }

            const form = new FormData();
            form.append("title", titleInput.value);
            form.append("description", descriptionInput.value);
            form.append("privacyStatus", privacyStatusInput.value);
            form.append("file", fileInput.files[0]);
            fetch("/youtube/uploadVideo", {
                method: "POST",
                body: form
            })
                .then(response => {
                    console.log(response);
                    if (response.ok) {

                        const scope = angular.element(document.querySelector('[ng-controller="Video-ctrl"]')).scope();
                        scope.$apply(function () {
                            scope.createNewVideo();
                            showNotification(10);
                        });

                    } else {
                        showNotification(11);
                    }
                })
                .catch(error => {
                    console.error("Lỗi: " + error);
                });
        } else {
            const scope = angular.element(document.querySelector('[ng-controller="Video-ctrl"]')).scope();
            scope.$apply(function () {

                scope.create();
            });
        }
    });


});

document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById("file");
    const youtubeLinkInput = document.getElementById("youtubeLink");
    const showYouTubeVideoButton = document.getElementById("showYouTubeVideo");
    const youtubeVideoContainer = document.getElementById("youtubeVideoContainer");

    fileInput.addEventListener("change", function () {
        const selectedFile = fileInput.files[0];

        if (selectedFile) {
            if (selectedFile.type === "video/mp4") {
                const objectURL = URL.createObjectURL(selectedFile);
                videoPlayer.src = objectURL;
                youtubeVideoContainer.style.display = "none";
            } else {
                showNotification(20);
                fileInput.value = "";
            }
        } else {
            videoPlayer.src = "";
        }
    });

    showYouTubeVideoButton.addEventListener("click", function () {
        const youtubeLink = youtubeLinkInput.value;

        if (youtubeLink.includes("youtube.com")) {
            const videoId = getYoutubeVideoId(youtubeLink);

            if (videoId) {
                const youtubeEmbedCode = `https://www.youtube.com/embed/${videoId}`;
                videoPlayer.src = youtubeEmbedCode;
                youtubeVideoContainer.style.display = "block";
            } else {
                showNotification(21);
            }
        } else {
            showNotification(22);
        }
    });

    function getYoutubeVideoId(url) {
        const regex = /[?&]v=([^&#]+)/;
        const match = url.match(regex);
        return match && match[1];
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const uploadNewVideoRadio = document.getElementById("uploadNewVideo");
    const useExistingVideoRadio = document.getElementById("useExistingVideo");
    const uploadNewVideoSection = document.getElementById("uploadNewVideoSection");
    const useExistingVideoSection = document.getElementById("useExistingVideoSection");
    const titleInput = document.getElementById("title");
    const descriptionInput = document.getElementById("description");
    const privacyStatusSelect = document.getElementById("privacyStatus");
    const fileInput = document.getElementById("file");
    const youtubeLinkInput = document.getElementById("youtubeLink");

    useExistingVideoSection.style.display = "none";

    uploadNewVideoRadio.addEventListener("change", function () {
        uploadNewVideoSection.style.display = "block";
        useExistingVideoSection.style.display = "none";
        privacyStatusSelect.selectedIndex = 0;
        youtubeLinkInput.value = "";
        titleInput.value = "";
        descriptionInput.value = "";
        youtubeLinkInput.value = "";
    });

    useExistingVideoRadio.addEventListener("change", function () {
        uploadNewVideoSection.style.display = "none";
        useExistingVideoSection.style.display = "block";
        youtubeLinkInput.value = "";
        titleInput.value = "";
        descriptionInput.value = "";
        privacyStatusSelect.selectedIndex = 0;
    });


});

const selected = document.querySelector(".selected");
const optionsContainer = document.querySelector(".options-container");
const searchBox = document.querySelector(".search-box input");

const optionsList = document.querySelectorAll(".option");

selected.addEventListener("click", () => {
    optionsContainer.classList.toggle("active");
    searchBox.value = "";
    filterList("");

    if (optionsContainer.classList.contains("active")) {
        searchBox.focus();
    }
});

optionsList.forEach(o => {
    o.addEventListener("click", () => {
        selected.innerHTML = o.querySelector("label").innerHTML;
        optionsContainer.classList.remove("active");
    });
});

searchBox.addEventListener("input", function () {
    if (searchBox.value.length = 0) {
        filterList("");
    } else {
        filterList(searchBox.value);
    }

});

const filterList = searchTerm => {
    searchTerm = searchTerm.toLowerCase();
    optionsList.forEach(option => {
        let label = option.firstElementChild.nextElementSibling.innerText.toLowerCase();
        if (label.indexOf(searchTerm) !== -1) {
            option.style.display = "block";
        } else {
            option.style.display = "none";
        }
    });
};


const notifications = [
    { text: "Đã khóa", duration: 3000, type: "success" },//---0---
    { text: "Đã mở khóa", duration: 3000, type: "success" },//---1---
    { text: "Thêm thành công", duration: 3000, type: "success" },//---2---
    { text: "Xóa thành công", duration: 3000, type: "success" },//---3---
    { text: "Sửa thành công", duration: 3000, type: "success" },//---4---
    { text: "lỗi", duration: 3000, type: "error" },//---5---
    { text: "Lỗi thêm mới", duration: 3000, type: "error" },//---6---
    { text: "Lỗi xóa", duration: 3000, type: "error" },//---7---
    { text: "Lỗi cập nhật", duration: 3000, type: "error" },//---8---D
    { text: "Duyệt thành công", duration: 3000, type: "success" },//---9---
    { text: "Đã gửi form thành công", duration: 3000, type: "success" },//---10---
    { text: "Lỗi gửi form", duration: 3000, type: "error" },//---11---
    { text: "Vui lòng chọn một khóa học", duration: 3000, type: "warning" },//---12---
    { text: "Vui lòng nhập liên kết video từ YouTube", duration: 3000, type: "warning" },//---13---
    { text: "Không thể trích xuất ID video từ liên kết YouTube", duration: 3000, type: "warning" },//---14---
    { text: "Không thể lấy được tiêu đề của video từ YouTube", duration: 3000, type: "warning" },//---15---
    { text: "Vui lòng nhập tiêu đề", duration: 3000, type: "warning" },//---16---
    { text: "Vui lòng nhập mô tả", duration: 3000, type: "warning" },//---17---
    { text: "Vui lòng chọn quyền riêng tư", duration: 3000, type: "warning" },//---18---
    { text: "Vui lòng chọn tệp video", duration: 3000, type: "warning" },//---19---
    { text: "Chỉ hỗ trợ tệp .mp4", duration: 3000, type: "warning" },//---20---
    { text: "Không thể trích xuất mã video từ liên kết", duration: 3000, type: "warning" },//---21---
    { text: "Liên kết YouTube không hợp lệ", duration: 3000, type: "warning" },//---22---
    { text: "Vui lòng chọn mục lục", duration: 3000, type: "warning" },//---23---
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
            onClose: function () {
                // Hiển thị thông báo tiếp theo khi thông báo hiện tại được đóng
                showNotification(index + 1);
            }
        }).showToast();
    }
}

