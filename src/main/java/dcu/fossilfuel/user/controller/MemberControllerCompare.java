package dcu.fossilfuel.user.controller;

import dcu.fossilfuel.user.controller.dto.*;
import dcu.fossilfuel.user.service.MailService;
import dcu.fossilfuel.user.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberControllerCompare {

    private final MemberService memberService;
    private final MailService registerMail;

    // 인증 코드 전송
    @PostMapping("/api/send-verification-code")
    public ResponseEntity<ResponseDto> sendVerificationCode(HttpSession session, @RequestBody EmailCheckRequest request) throws Exception {
        String verificationCode = registerMail.sendSimpleMessage(request.getEmail());
        session.setAttribute("verificationCode", verificationCode);
        return ResponseEntity.ok(ResponseDto.success("인증 코드가 이메일로 발송되었습니다.", null));
    }

    // 인증 코드 확인
    @PostMapping("/api/verify-code")
    public ResponseEntity<ResponseDto> verifyCode(HttpSession session, @RequestBody VerifyCodeRequest request) {
        String verificationCode = (String) session.getAttribute("verificationCode");

        if (verificationCode != null && verificationCode.equals(request.getCode())) {
            session.setAttribute("emailVerified", true);
            return ResponseEntity.ok(ResponseDto.success("이메일 인증 성공!", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.fail("인증 코드가 일치하지 않습니다."));
        }
    }

    // 이메일 중복 확인
    @PostMapping("/api/check-email")
    public ResponseEntity<ResponseDto> checkEmailDuplicate(@RequestBody EmailCheckRequest request) {
        if (memberService.isEmailDuplicate(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail("이미 가입된 이메일입니다."));
        }
        return ResponseEntity.ok(ResponseDto.success("사용 가능한 이메일입니다.", null));
    }

    // 이메일 인증 여부 확인
    @GetMapping("/api/email-last-verified")
    public ResponseEntity<ResponseDto> checkEmailVerified(HttpSession session) {
        Boolean emailVerified = (Boolean) session.getAttribute("emailVerified");
        if (Boolean.TRUE.equals(emailVerified)) {
            return ResponseEntity.ok(ResponseDto.success("이메일 인증 완료", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.fail("이메일 인증이 필요합니다."));
        }
    }

    // 회원가입
    @PostMapping("/api/members/register")
    public ResponseEntity<ResponseDto> saveMember(@RequestBody RegisterRequest registerRequest) {
        memberService.saveMember(registerRequest);
        return ResponseEntity.ok(ResponseDto.success("회원가입 성공!", null));
    }

    // ID 찾기
    @PostMapping("/api/auth/find-id")
    public ResponseEntity<ResponseDto> findId(@RequestBody FindIdRequest request) {
        String email = memberService.findEmailByNickname(request.getNickname());
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail("닉네임이 잘못되었거나 회원이 아닙니다."));
        }
        return ResponseEntity.ok(ResponseDto.success("이메일을 찾았습니다.", email));
    }
}

