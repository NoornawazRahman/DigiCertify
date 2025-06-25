package digicertify.controller;

import digicertify.entity.CertificateInfo;
import digicertify.service.CertificateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/certificate")
@AllArgsConstructor
public class CertificateController {
    private CertificateService certificateService;

    @GetMapping
    public String renderHomePage() {
        return "home";
    }

    @GetMapping("/generate")
    public String showCertificateGenerationForm() {

        return "certificateGenerationForm";
    }

    @PostMapping("/generate")
    public void generateCertificate(@ModelAttribute CertificateInfo data, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=certificate.pdf");

        try (OutputStream out = response.getOutputStream()) {
            certificateService.generateCertificate(data, out); // write PDF directly to response
            out.flush();
        }

    }

}
