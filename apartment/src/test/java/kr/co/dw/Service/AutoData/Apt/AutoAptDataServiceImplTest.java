package kr.co.dw.Service.AutoData.Apt;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(properties = "spring.profiles.active=dev")
@TestPropertySource("classpath:application-dev.properties")
public class AutoAptDataServiceImplTest {

}
