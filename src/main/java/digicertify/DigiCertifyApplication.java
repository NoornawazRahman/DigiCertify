package digicertify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class DigiCertifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigiCertifyApplication.class, args);
	}

	@GetMapping("/")
	public String renderHomePage() {
		return "home";
	}
}
