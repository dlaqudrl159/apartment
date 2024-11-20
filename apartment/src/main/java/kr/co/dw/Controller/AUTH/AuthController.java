package kr.co.dw.Controller.AUTH;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.dw.Domain.Auth.LoginRequest;
import kr.co.dw.Dto.Response.TokenResponse;
import kr.co.dw.Service.Auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${spring.admin.userid}")
    private String adminUserId;
    
    @Value("${spring.admin.userpw}")
    private String adminPW;
	
    private final JwtTokenProvider jwtTokenProvider;
    
    @PostMapping("/auth/admin/login")
    public ResponseEntity<?> adminlogin(@RequestBody LoginRequest request) {
    	
    	if(adminUserId.equals(request.getUserId()) && adminPW.equals(request.getUserPw())) {
    		String token = jwtTokenProvider.createToken(request.getUserId());
    		System.out.println("토큰생성성공");
            return ResponseEntity.ok().body(new TokenResponse(token, "Bearer"));
    	}
    	System.out.println("토큰생성실패ㅇㅇ");
    	return ResponseEntity.status(401).body("로그인 실패");
    }
    
}
