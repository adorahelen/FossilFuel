package dcu.fossilfuel.user.controller.dto;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @Size(min = 1, message = "비밀번호는 1자 이상이어야 합니다.")
    private String newPassword;
}
