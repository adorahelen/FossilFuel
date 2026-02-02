async function updateProfileImage() {
    const fileInput = document.getElementById("profileUpload");
    const file = fileInput.files[0];
    if (!file) return alert("이미지를 선택하세요!");

    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch("/projects/fossilfuel/api/user/profile-image", {
        method: "POST",
        body: formData,
        credentials: "include"
    });

    if (response.ok) {
        alert("프로필 이미지 변경 완료!");
        location.reload();
    } else {
        alert("변경 실패!");
    }
}

async function updateNickname() {
    const newNickname = document.getElementById("newNickname").value;
    if (!newNickname) return alert("새 닉네임을 입력하세요!");

    const response = await fetch("/projects/fossilfuel/api/user/nickname", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nickname: newNickname }),
        credentials: "include"
    });

    if (response.ok) {
        alert("닉네임 변경 완료!");
        location.reload();
    } else {
        alert("변경 실패!");
    }
}

async function updatePassword() {
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (!currentPassword || !newPassword || !confirmPassword) {
        return alert("모든 필드를 입력하세요!");
    }
    if (newPassword !== confirmPassword) {
        return alert("새 비밀번호가 일치하지 않습니다!");
    }

    const response = await fetch("/projects/fossilfuel/api/user/password", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ currentPassword, newPassword }),
        credentials: "include"
    });

    if (response.ok) {
        alert("비밀번호 변경 완료!");
    } else {
        alert("변경 실패!");
    }
}

function logout() {
    fetch("/projects/fossilfuel/api/logout", { method: "POST", credentials: "include" }).then(() => {
        alert("로그아웃 되었습니다.");
        window.location.href = "/login";
    });
}
