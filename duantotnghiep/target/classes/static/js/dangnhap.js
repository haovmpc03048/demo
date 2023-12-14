if(location.href == location.origin + "/auth/blocked"){
    alert("Thông báo: Tài khoản chưa xác minh")
}
if(location.href == location.origin + "/auth/login_error"){
    alert("Sai tài khoản hoặc mật khẩu")
}

if(location.href == location.origin + "/courseOnline/EmailError"){
    alert("Email không tồn tại")
}
if(location.href == location.origin + "/courseOnline/OTPError"){
    alert("Vui lòng nhập OTP trước rồi mới nhập mật khẩu")
}
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