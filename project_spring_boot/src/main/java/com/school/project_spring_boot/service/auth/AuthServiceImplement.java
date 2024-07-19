package com.school.project_spring_boot.service.auth;

import com.school.project_spring_boot.common.CertificationNumber;
import com.school.project_spring_boot.dto.requset.auth.*;
import com.school.project_spring_boot.dto.response.ResponseDto;
import com.school.project_spring_boot.dto.response.auth.*;
import com.school.project_spring_boot.entity.auth.Member;
import com.school.project_spring_boot.entity.auth.Certification;
import com.school.project_spring_boot.provider.EmailProvider;
import com.school.project_spring_boot.provider.JwtProvider;
import com.school.project_spring_boot.repository.auth.CertificationRepository;
import com.school.project_spring_boot.repository.auth.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final MemberRepository memberRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto emailCheckRequestDto) {
        try{
            String email = emailCheckRequestDto.getEmail();
            Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);
            if(memberOptional.isPresent()){
                return EmailCheckResponseDto.duplicateEmail();
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return EmailCheckResponseDto.databaseError();
        }
        return EmailCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto emailCertificationRequestDto) {
        try {
            String email = emailCertificationRequestDto.getEmail();
            Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);
            if(memberOptional.isPresent()){
                EmailCheckResponseDto.duplicateEmail();
            }

            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean certificationEmailSend = emailProvider.sendCertificationEmail(email, certificationNumber);
            if(!certificationEmailSend) {
                return EmailCertificationResponseDto.mailSendFail();
            }

            Certification certification = new Certification(email, certificationNumber);
            certificationRepository.save(certification);

        } catch (Exception exception){
            exception.printStackTrace();
            return EmailCertificationResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CertificationCheckResponseDto> certificationCheck(CertificationCheckRequestDto certificationCheckRequestDto) {
        try {
            String email = certificationCheckRequestDto.getEmail();
            String certificationNumber = certificationCheckRequestDto.getCertificationNumber();
            Certification certification = null;

            Optional<Certification> certificationOptional = certificationRepository.findByMemberEmail(email);
            if(certificationOptional.isPresent()) {
                certification = certificationOptional.get();
            }

            boolean certificated = certification.getCertificationNumber().equals(certificationNumber) && certification.getMemberEmail().equals(email);
            if (!certificated) {
                return CertificationCheckResponseDto.certificationFail();
            }

        } catch (Exception exception){
            exception.printStackTrace();
            return CertificationCheckResponseDto.databaseError();
        }
        return CertificationCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto) {
        try{
            String email = signUpRequestDto.getEmail();
            Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);
            if(memberOptional.isPresent()) {
                return SignUpResponseDto.duplicateEmail();
            }

            String certificationNumber = signUpRequestDto.getCertificationNumber();
            Optional<Certification> certificationOptional = certificationRepository.findByMemberEmail(email);
            if(certificationOptional.isPresent()) {
                boolean certificated =certificationOptional.get().getCertificationNumber().equals(certificationNumber) && certificationOptional.get().getMemberEmail().equals(email);
                if(!certificated) {
                    return SignUpResponseDto.certificationFail();
                }
            }
            else{
                return SignUpResponseDto.certificationFail();
            }

            String password = signUpRequestDto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            signUpRequestDto.setPassword(encodedPassword);

            Member member = new Member(signUpRequestDto);

            memberRepository.save(member);
            certificationRepository.deleteByMemberEmail(email);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto signInRequestDto) {
        String accessToken = null;

        try{
            String email = signInRequestDto.getEmail();
            String password = signInRequestDto.getPassword();

            Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);
            if(memberOptional.isPresent()) {
                Member member = memberOptional.get();
                boolean isMatchedPassword = passwordEncoder.matches(password, member.getPassword());
                if(!isMatchedPassword) {
                    return SignInResponseDto.signInFail();
                }
            }
            else {
                return SignInResponseDto.signInFail();
            }

            accessToken = jwtProvider.create(email);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken);
    }
}
