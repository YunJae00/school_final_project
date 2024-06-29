package com.school.project_spring_boot.jwt;

import com.school.project_spring_boot.entity.Member;
import com.school.project_spring_boot.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if(memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if (member != null) {
                return new CustomUserDetails(member);
            }
            else{
                throw new UsernameNotFoundException(email + " not found");
            }
        }
        else return null;
    }
}
