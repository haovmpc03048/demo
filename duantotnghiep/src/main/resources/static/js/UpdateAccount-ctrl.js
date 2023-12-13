    var taiKhoan = document.getElementById("taiKhoan").value;
document.getElementById("updateAccountForm").addEventListener("submit", function (event) {
    event.preventDefault();



    var formData = {
        taiKhoan: taiKhoan,
        email: document.getElementById("email").value,
                hoTen: document.getElementById("text-input").value,

        matKhau: document.getElementById("password-input").value,
        soDienThoai: document.getElementById("phone-input").value,
        // Các trường thông tin khác
    };

    fetch("/courseOnline/updateAccount", {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
        },
        body: JSON.stringify(formData),
    })
    .then(response => response.json())
    .then(data => {
        console.log("Cập nhật thành công:", data);
        // Cập nhật giao diện người dùng nếu cần
        showSuccessMessage();
    })
    .catch(error => {
        console.error("Lỗi khi cập nhật:", error);
        // Xử lý lỗi nếu cần
    });
});
      
function previewImages(input) {
    var preview = document.getElementById('image-preview');
    preview.innerHTML = '';

    if (input.files) {
        for (var i = 0; i < input.files.length; i++) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var image = document.createElement('img');
                image.src = e.target.result;
                image.style.maxWidth = '100%';
                image.style.marginTop = '10px';
                preview.appendChild(image);
            };

            reader.readAsDataURL(input.files[i]);
        }
    }
}

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("updateAccountButton").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent default form submission

        // Perform form validation
        if (validateForm()) {
            // If validation passes, show success message
            showSuccessMessage();
        }
    });

    function validateForm() {
        const usernameInput = document.getElementById("username");
        const emailInput = document.getElementById("email");
        const passwordInput = document.getElementById("password-input");
        const phoneInput = document.getElementById("phone-input");

        let valid = true;

        // Reset error messages
        clearErrors();

        // Validate username
        if (usernameInput && usernameInput.value.trim() === "") {
            valid = false;
            displayError(usernameInput, "Vui lòng nhập tên đăng nhập");
        }

        // Validate email
        if (emailInput && emailInput.value.trim() === "") {
            valid = false;
            displayError(emailInput, "Vui lòng nhập email");
        } else if (emailInput && !isValidEmail(emailInput.value.trim())) {
            valid = false;
            displayError(emailInput, "Vui lòng nhập đúng địa chỉ email");
        }

        // Validate password
        if (passwordInput && passwordInput.value.trim() === "") {
            valid = false;
            displayError(passwordInput, "Vui lòng nhập mật khẩu");
        }

        // Validate phone number
        if (phoneInput && phoneInput.value.trim() === "") {
            valid = false;
            displayError(phoneInput, "Vui lòng nhập số điện thoại");
        }

        return valid; 
    }

    function displayError(input, message) {
        const helpBlock = input.nextElementSibling;
        helpBlock.innerText = message;
    }

    function clearErrors() {
        const errorMessages = document.querySelectorAll(".help-block.text-danger");
        errorMessages.forEach(function (errorMessage) {
            errorMessage.innerText = "";
        });
    }

    function isValidEmail(email) {
        // A simple email validation regex
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function showSuccessMessage() {
        Swal.fire({
            icon: 'success',
            title: 'Cập nhật thành công!',
            text: 'Thông tin tài khoản đã được cập nhật.',
            confirmButtonText: 'Đóng',
        }).then(() => {
            // Sau khi người dùng đóng thông báo thành công
            // Chuyển hướng đến trang đăng xuất
            window.location.href = "/logoff";
        });
    }
});
