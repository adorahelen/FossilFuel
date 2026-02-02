document.getElementById("sendCode").addEventListener("click", function() {
    const email = document.getElementById("email").value;
    const messageDiv = document.getElementById("message");

    if (!email) {
        messageDiv.textContent = "이메일을 입력하세요.";
        return;
    }

    fetch('/projects/fossilfuel/api/auth/find-password', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email })
    })
        .then(response => response.json())
        .then(data => {
            if (!data.success) {
                messageDiv.textContent = "이메일이 존재하지 않습니다.";
            } else {
                messageDiv.textContent = "인증 코드가 이메일로 전송되었습니다.";
                document.getElementById("step1").classList.add("hidden");
                document.getElementById("step2").classList.remove("hidden");
            }
        })
        .catch(() => messageDiv.textContent = "서버 오류가 발생했습니다.");
});

document.getElementById("verifyCode").addEventListener("click", function() {
    const code = document.getElementById("code").value;
    const messageDiv = document.getElementById("message");

    if (!code) {
        messageDiv.textContent = "인증 코드를 입력하세요.";
        return;
    }

    fetch('/projects/fossilfuel/api/auth/verify-password-code', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ code })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                messageDiv.textContent = "인증이 완료되었습니다.";
                document.getElementById("step2").classList.add("hidden");
                document.getElementById("step3").classList.remove("hidden");
            } else {
                messageDiv.textContent = "인증 코드가 올바르지 않습니다.";
            }
        })
        .catch(() => messageDiv.textContent = "서버 오류가 발생했습니다.");
});

document.getElementById("resetPassword").addEventListener("click", function() {
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const messageDiv = document.getElementById("message");

    if (!newPassword || !confirmPassword) {
        messageDiv.textContent = "새 비밀번호를 입력하세요.";
        return;
    }

    if (newPassword !== confirmPassword) {
        messageDiv.textContent = "비밀번호가 일치하지 않습니다.";
        return;
    }

    fetch('/projects/fossilfuel/api/auth/reset-password', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ newPassword })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                messageDiv.textContent = "비밀번호가 변경되었습니다.";
                document.getElementById("step3").classList.add("hidden");
            } else {
                messageDiv.textContent = "비밀번호 변경 실패.";
            }
        })
        .catch(() => messageDiv.textContent = "서버 오류가 발생했습니다.");
});