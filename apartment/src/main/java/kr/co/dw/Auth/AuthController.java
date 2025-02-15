package kr.co.dw.Auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
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
    	
    	if(adminUserId.equals(request.getUserId()) && adminPW.equals(request.getUserPassword())) {
    		String token = jwtTokenProvider.createToken(request.getUserId());
            return ResponseEntity.ok().body(new TokenResponse(token, "Bearer"));
    	}
    	return ResponseEntity.badRequest().body("로그인 실패");
    }
    
    @GetMapping("/auth/admin/dashboard")
    public ResponseEntity<?> authDashBoard(HttpServletRequest request) {
    	String bearerToken = request.getHeader("Authorization");
    	if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
    		boolean isToken = jwtTokenProvider.validateToken(bearerToken.substring(7)); 
    		if(isToken == true) {
    			return ResponseEntity.ok("성공");
    		};
        }
    	return ResponseEntity.badRequest().body("실패");
    }
    
}
