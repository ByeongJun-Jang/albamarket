package proj.albamarcketV2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register", "/login").permitAll()  // 로그인과 회원가입은 누구나 접근 가능
                        .anyRequest().authenticated()  // 그 외의 모든 요청은 인증이 필요
                )
                .formLogin((form) -> form
                        .loginPage("/login")  // 사용자 정의 로그인 페이지 지정
                        .defaultSuccessUrl("/", true)  // 로그인 성공 시 리다이렉트할 기본 URL
                        .permitAll()  // 로그인 페이지는 인증 없이 접근 가능하게 설정
                )
                .logout(LogoutConfigurer::permitAll);  // 로그아웃도 인증 없이 접근 가능하게 설정
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
    }
}
