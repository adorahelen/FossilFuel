package dcu.fossilfuel.gate.controller.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiChatRequest {

    @NotBlank(message = "Cant not NULL - K")
    private String message;

}
