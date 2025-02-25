package dcu.fossilfuel.main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitDTO {

    @NotBlank(message = "아 이거 불안한데, 없는 유저면?")
    private String username;

    //    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "아 이거 불안한데 커밋 0이면?")
    private int commitCount;

}