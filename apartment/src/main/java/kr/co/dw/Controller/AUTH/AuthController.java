package kr.co.dw.Controller.AUTH;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dw.Domain.Auth.LoginRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${spring.admin.userid}")
    private String adminUserId;
    
    @Value("${spring.admin.userpw}")
    private String adminPW;
	
    @PostMapping("/api/admin/login")
    public ResponseEntity<?> adminlogin(@RequestBody LoginRequest request) {
    	
    	System.out.println(adminUserId);
    	System.out.println(adminPW);
    	
    	
    	System.out.println(request.getUserId());
    	System.out.println(request.getUserPw());
    	
    	
    	
    	return null;
    }
    
}
