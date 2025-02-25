package dcu.fossilfuel.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindIdRequest {

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
}