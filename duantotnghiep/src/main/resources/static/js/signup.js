// Kiểm tra trùng tên tài khoản trước khi đăng ký
function checkIfUsernameExists(username) {
  return fetch(`/api/nguoidung/checkUsername/${username}`)
    .then(response => response.json())
    .then(usernameExists => {
      return usernameExists;
    });
}
//End Kiểm tra trùng tên tài khoản trước khi đăng ký

//Kiểm tra trùng tài khoản 
function checkIfEmailExists(email) {
  return fetch(`/api/nguoidung/checkEmail/${email}`)
    .then(response => response.json())
    .then(emailExists => {
      return emailExists;
    });
}
// End Kiểm tra trùng tài khoản 
//Bắt hiển thị với ẩn mật khẩu
const pass_field = document.querySelector('.pass-key');
const showBtn = document.querySelector('.show');

showBtn.addEventListener('click', function () {
  if (pass_field.type === 'password') {
    pass_field.type = 'text';
    showBtn.innerHTML = '<i class="fas fa-eye-slash"></i>';
    showBtn.style.color = '#ff0000';
  } else {
    pass_field.type = 'password';
    showBtn.innerHTML = '<i class="fas fa-eye"></i>';
    showBtn.style.color = '#222';
  }
});
// End Hiển thị mẩu khẩu

const signupForm = document.getElementById('signupForm');
signupForm.addEventListener('submit', function (event) {
  event.preventDefault();

  // Lấy dữ liệu từ các trường nhập liệu
  const fullName = document.getElementById('fullName').value;
  const username = document.getElementById('username').value;
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  const phone = document.getElementById('phone').value;

  // Kiểm tra xem số điện thoại chỉ chứa chữ số
  if (!isValidPhoneNumber(phone)) {
    alert('Sai định dang số điện thoại');
    return;
  }
  toggleSpinner()
  // Kiểm tra trùng tên tài khoản trước khi đăng ký
  checkIfUsernameExists(username)
    .then(usernameExists => {
      if (usernameExists) {
        alert('Tài khoản đã tồn tại. Vui lòng chọn tài khoản khác.');
      } else {

        checkIfEmailExists(email)
          .then(emailExists => {
            if (emailExists) {
              alert('Email đã tồn tại. Vui lòng sử dụng email khác.');
            } else {
              // Tiếp tục với đăng ký nếu tên tài khoản không tồn tại
              // Gửi dữ liệu đăng ký đến máy chủ hoặc xử lý chúng theo cách bạn muốn.
              fetch('/api/nguoidung', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                  hoTen: fullName,
                  taiKhoan: username,
                  email: email,
                  soDienThoai: phone,
                  matKhau: password,
                  thongBao: false,
                  thoiGianTao: new Date().toISOString() // Get the current date and time in ISO format
                })
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Network response was not ok');
                  }
                  return response.json();
                })
                .then(data => {
                  if (data) {
                    alert('Đăng ký thành công');
                    // Redirect người dùng đến trang đăng nhập
                    location.href = 'http://localhost:8080/courseOnline/dangnhap';
                  } else {
                    alert('Đăng ký không thành công. Vui lòng thử lại sau.');

                  }
                });
            }
          });
      }
    });
});
// Hàm kiểm tra số điện thoại
function isValidPhoneNumber(phone) {
  // Kiểm tra xem chuỗi chỉ chứa các chữ số và có ít nhất 10 ký tự
  return /^[0-9]{10,}$/.test(phone);
}

function toggleSpinner() {
  var spinnerContainer = document.querySelector('.spinner-container');

    spinnerContainer.style.display = 'block';

  }

