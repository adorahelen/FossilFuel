package dcu.fossilfuel.user.service;

import dcu.fossilfuel.user.controller.dto.RegisterRequest;
import dcu.fossilfuel.user.domain.Member;
import dcu.fossilfuel.user.domain.Role;
import dcu.fossilfuel.user.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

// 회원가입시 이메일 중복체크
    public boolean isEmailDuplicate(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

// 회원가입
    public void saveMember(RegisterRequest dto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 이메일 중복체크
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .grade(dto.getGrade())
                .role(Role.ROLE_USER)

                .build();

        memberRepository.save(member);
    }

    // [아이디 찾기]
    // 닉네임으로 이메일 조회
    public String findEmailByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .map(Member::getEmail) // 이메일 가져오기
                .orElse(null);
    }

    // 추가
    // 비밀번호 찾기
    // 이메일 존재 여부 확인 (가존 코드 재활용)
    @Transactional // @Transactional을 추가하면 save() 호출 없이도 JPA가 자동으로 DB 반영!
    public void updatePassword(String email, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        member.setPassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화 후 저장
    }


}
