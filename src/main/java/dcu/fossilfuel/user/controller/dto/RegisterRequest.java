package dcu.fossilfuel.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String nickname;
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;
    @Size(min = 1, message = "비밀번호는 1자 이상이어야 합니다.")
    private String password;
    // 여기도 뭔가 설정을 하긴 해야하는데?
    private String grade;  // "1학년", "2학년", "3학년", "4학년" 등을 저장



    // Role & ImageURL 은 백엔드 only => 서비스단 처리



}
