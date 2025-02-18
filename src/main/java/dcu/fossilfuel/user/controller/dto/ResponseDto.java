package dcu.fossilfuel.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private boolean success;
    private String message;
    private Object data;

    public static ResponseDto success(String message, Object data) {
        return new ResponseDto(true, message, data);
    }

    public static ResponseDto fail(String message) {
        return new ResponseDto(false, message, null);
    }
}
