package dcu.fossilfuel.user.controller;

import dcu.fossilfuel.user.controller.dto.*;
import dcu.fossilfuel.user.service.MailService;
import dcu.fossilfuel.user.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

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
    public ResponseEntity<ResponseDto> checkEmailDuplicate(@Valid @RequestBody EmailCheckRequest request) {
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
    public ResponseEntity<ResponseDto> saveMember(@Valid @RequestBody RegisterRequest registerRequest) {
        memberService.saveMember(registerRequest);
        return ResponseEntity.ok(ResponseDto.success("회원가입 성공!", null));
    }

    // ID 찾기
    @PostMapping("/api/auth/find-id")
    public ResponseEntity<ResponseDto> findId(@Valid @RequestBody FindIdRequest request) {
        String email = memberService.findEmailByNickname(request.getNickname());
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail("닉네임이 잘못되었거나 회원이 아닙니다."));
        }
        return ResponseEntity.ok(ResponseDto.success("이메일을 찾았습니다.", email));
    }

    // 추가 (비밀번호 찾기)

    // 비밀번호 찾기 - 이메일 확인
    @PostMapping("/api/auth/find-password")
    public ResponseEntity<ResponseDto> findPassword(@Valid @RequestBody EmailCheckRequest request, HttpSession session) throws Exception {
        if (!memberService.isEmailDuplicate(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail("존재하지 않는 사용자입니다."));
        }

        // 이메일이 존재하면 인증 코드 전송
        String verificationCode = registerMail.sendSimpleMessage(request.getEmail());
        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("resetEmail", request.getEmail()); // 이후 비밀번호 변경 시 사용
        return ResponseEntity.ok(ResponseDto.success("인증 코드가 이메일로 발송되었습니다.", null));
    }

    // 비밀번호 찾기 - 인증 코드 확인
    @PostMapping("/api/auth/verify-password-code")
    public ResponseEntity<ResponseDto> verifyPasswordCode(@RequestBody VerifyCodeRequest request, HttpSession session) {
        String verificationCode = (String) session.getAttribute("verificationCode");

        if (verificationCode != null && verificationCode.equals(request.getCode())) {
            session.setAttribute("passwordResetVerified", true);
            return ResponseEntity.ok(ResponseDto.success("이메일 인증 성공!", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.fail("인증 코드가 일치하지 않습니다."));
        }
    }

    // 비밀번호 재설정
    @PostMapping("/api/auth/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@Valid @RequestBody ResetPasswordRequest request, HttpSession session) {
        Boolean isVerified = (Boolean) session.getAttribute("passwordResetVerified");
        String email = (String) session.getAttribute("resetEmail");

        if (Boolean.TRUE.equals(isVerified) && email != null) {
            memberService.updatePassword(email, request.getNewPassword()); // 비밀번호 변경
            session.removeAttribute("passwordResetVerified"); // 인증 상태 제거
            session.removeAttribute("resetEmail"); // 이메일 정보 제거
            return ResponseEntity.ok(ResponseDto.success("비밀번호가 성공적으로 변경되었습니다.", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.fail("이메일 인증이 필요합니다."));
        }
    }
}