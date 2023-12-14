var app = angular.module("myApp", []);
app.controller("TracNghiem-ctrl", function ($scope, $http, $window) {
    $scope.itemsCauHoi = [];

    $scope.initialize = function () {
        // Gọi API để tải dữ liệu câu hỏi từ đường dẫn "/rest/TracNghiem"
        $http.get("/rest/tracnghiem").then(resp => {
            $scope.itemsCauHoi = resp.data;
            //console.log("API Response:", resp.data)
        }
        );
        $scope.chonKhoaHoc();
    }

    $scope.setid = function (id) {
        $window.sessionStorage.setItem('MucLuc2', id);
    }
    // Lấy phần tử input theo id (giả sử id của phần tử là "hiddenInputId")
    var hiddenInput = document.getElementById("hiddenInputId");

    // Lấy giá trị của phần tử input ẩn
    var hiddenValue = hiddenInput.value;

    // Sử dụng hiddenValue theo nhu cầu của bạn trong mã JavaScript của bạn
    // Ví dụ:
    console.log("Giá trị của input ẩn là: " + hiddenValue);

    // Lấy giá trị từ sessionStorage
    var idFromSessionStorage = $window.sessionStorage.getItem('MucLuc');

    // Sử dụng giá trị lấy được từ sessionStorage theo nhu cầu của bạn trong mã JavaScript của AngularJS
    // Ví dụ:
    console.log("Giá trị từ sessionStorage là: " + idFromSessionStorage);



    $scope.chonKhoaHoc = function () {
        $http.get(`/rest/tracnghiem/${hiddenValue}`)
            .then(function (resp) {
                $scope.itemsCauHoi = resp.data;
                console.log("API Response:", resp.data)
                // Tạo mảng mới chứa thông tin câu hỏi, câu trả lời và mảng đáp án
                const questionAnswerArray = [];
                for (const item of $scope.itemsCauHoi) {
                    console.log("item.mucLuc: " + item.mucLuc.id);
                    if (item.mucLuc.id == idFromSessionStorage) { // Kiểm tra nếu có mucLuc=idFromSessionStorage
                        if (item.cauTraLoi) {
                            const splitAnswers = item.cauTraLoi.split(',').map(answer => answer.trim());

                            // Tạo đối tượng chứa thông tin và đưa vào mảng mới
                            const questionObject = {
                                cauHoi: item.cauHoi,
                                cauTraLoi: splitAnswers,
                                dapAn: item.dapAn
                            };
                            questionAnswerArray.push(questionObject);
                            //console.log("Question and answer object:", questionObject);
                        } else {
                            console.log("'cauTraLoi' does not exist or has no value for an item.");
                        }
                    }
                }
                // console.log("Question and answer array:", questionAnswerArray);
                $scope.questionAnswerArray = questionAnswerArray;
            })
            .catch(function (error) {
                console.error("An error occurred:", error);
            });

    };
    $scope.danhSachKhoaHoc = [];
    $scope.questionAnswerArray = [];
    $scope.selectedKhoaHoc = null; // Giá trị mặc định
    $scope.selectedAnswers = []
    $scope.selectedKhoaHoc = ''; // Giá trị khoá học được chọn
    $scope.chungChi = {};
    // Để ẩn nút "Tiếp tục"
    $scope.showContinueButton = false;

    // Hàm tính điểm
    $scope.checkAnswers = function () {
        $scope.soCauDung = 0;
        $scope.soCauSai = 0;
        $scope.msg = '';
        $scope.phanTramCauDung = 0;

        // Thêm một thuộc tính 'isCorrect' vào mỗi câu hỏi để xác định câu hỏi có đúng hay sai
        for (const question of $scope.questionAnswerArray) {
            if (question.selectedAnswer === undefined) {
                $scope.soCauSai++;
                $scope.msg = 'Bạn chưa chọn đáp án cho tất cả các câu hỏi.';
                return;
            } else {
                if (question.selectedAnswer === question.dapAn) {
                    $scope.soCauDung++;
                    question.isCorrect = true; // Đánh dấu câu hỏi này là đúng
                    // console.log("Câu đúng: ", $scope.soCauDung / $scope.questionAnswerArray.length * 100);
                    $scope.phanTramCauDung = ($scope.soCauDung / $scope.questionAnswerArray.length) * 100;
                    console.log("Phần trăm câu đúng: " + $scope.phanTramCauDung);
                    if ($scope.soCauDung / $scope.questionAnswerArray.length * 100 >= 80) {
                        $scope.msg = 'Bạn đã hoàn thành bài thi';
                        console.log("Bạn đã hoàn thành bài thi");
                        // Hàm xử lý khi thay đổi khoá học
                        $scope.showContinueButton = true; // Nếu muốn hiển thị ban đầu
                    } else {
                        $scope.msg = 'Bạn đã không hoàn thành bài thi';
                        console.log("Bạn đã không hoàn thành bài thi");
                    }
                } else {
                    $scope.soCauSai++;
                    question.isCorrect = false; // Đánh dấu câu hỏi này là sai
                }
            }
            console.log("A: " + question.selectedAnswer);
            console.log("B: " + question.dapAn);
        }


    };
    const nextCauHoiButton = document.getElementById('next-cauhoi');

    var inputElement = document.getElementById('idLogin');
    var value;
    let currentVideoIndex = 0;
    if (inputElement == null || inputElement == "") {
        value = 0;
    }
    else {
        // Lấy giá trị của input
        var value = inputElement.value;
        value = inputElement.value;
    }
    nextCauHoiButton.addEventListener('click', function () {
        $http.get('/api/courseOnline/detail/' + hiddenValue).then(function (response) {
            $scope.hoc = response.data;
            idKhoaHoc = $scope.hoc.courseOnline.id;
            console.log("idKhoaHoc: " + $scope.hoc.mucLuc.id);
            // Tìm vị trí của idFromSessionStorage trong mảng $scope.hoc.mucLuc
            const index = $scope.hoc.mucLuc.findIndex(item => item.id == idFromSessionStorage);

            if (index !== -1) {
                // Nếu tìm thấy idFromSessionStorage trong mảng mucLuc
                const nextIndex = index + 1;

                if (nextIndex < $scope.hoc.mucLuc.length) {
                    // Lấy giá trị tiếp theo của id từ mucLuc nếu có
                    const nextId = $scope.hoc.mucLuc[nextIndex].id;
                    console.log("Giá trị tiếp theo của id là: " + nextId);

                    // Đặt giá trị này cho idFromSessionStorage để sử dụng tiếp
                    idFromSessionStorage = nextId;
                    $scope.setid(idFromSessionStorage);
                    // Thực hiện các hành động khác với giá trị id mới
                    // ... (Điều kiện, xử lý dữ liệu, hoặc gọi hàm khác tại đây)
                    window.location.href = '/courseOnline/video/' + idKhoaHoc; // Ví dụ: chuyển đến trang trắc nghiệm
                } else {
                    console.log("Không có giá trị tiếp theo trong mảng.");
                    // Xử lý khi không có giá trị tiếp theo
                    $scope.msg = 'Bạn đã hoàn thành khóa học';

                    $scope.trangThai = "Đã hoàn thành";

                    $http({
                        method: 'PUT',
                        url: '/api/tiendokhoahoc/upload/' + value + '/' + idKhoaHoc + '/' + $scope.trangThai
                    }).then(function (response) {
                        console.log("Cập nhật thành công");
                    });


                    $http({
                        method: 'POST',
                        url: '/rest/ChungChi',
                        data: {
                            id: null,
                            nguoiDung: {
                                id: value
                            },
                            khoaHoc: {
                                id: idKhoaHoc
                            },
                            ngayCap: new Date(),
                        }
                    }).then(function (response) {
                        console.log("Thêm thành công");
                        window.location.href = '/courseOnline/detail/' + idKhoaHoc; // Ví dụ: chuyển đến trang trắc nghiệm
                    });
                    window.location.href = '/courseOnline/detail/' + idKhoaHoc; // Ví dụ: chuyển đến trang trắc nghiệm
                }
            } else {
                console.log("Không tìm thấy idFromSessionStorage trong mảng.");
                // Xử lý khi không tìm thấy id trong mảng
            }

            console.log($scope.hoc);


        }, function (response) {
            console.log(response);
        });
    });

    $scope.hasUnansweredQuestions = function () {
        for (const question of $scope.questionAnswerArray) {
            if (question.selectedAnswer === undefined) {
                return true; // Còn câu hỏi chưa được trả lời
            }
        }
        return false; // Tất cả câu hỏi đã được trả lời
    };
    // Gọi hàm initialize để khởi tạo dữ liệu khi trang được tải
    $scope.selectAnswer = function (questionIndex, answer) {
        $scope.selectedAnswers[questionIndex] = answer;
        console.log(questionIndex);
    }
    $scope.initialize();
});
