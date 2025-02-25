package dcu.fossilfuel.user.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckRequest {

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;
}