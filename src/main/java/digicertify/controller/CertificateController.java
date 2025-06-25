package digicertify.controller;

import digicertify.entity.CertificateInfo;
import digicertify.service.CertificateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/generate")
//    public void generateCertificate(@ModelAttribute CertificateInfo data, HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=certificate.pdf");
//
//        try (OutputStream out = response.getOutputStream()) {
//            certificateService.generateCertificate(data, out); // write PDF directly to response
//            out.flush();
//        }
//
//    }

    @PostMapping("/generate")
    public String makeNewCertificate(@ModelAttribute CertificateInfo data) {
        certificateService.makeNewCertificate(data);
        return "redirect:/certificate";
    }

    @GetMapping("/view")
    public String showViewCertificateForm() {
        return "viewCertificateForm";
    }
    @PostMapping("/view")
    public String redirectToViewCertificate(@RequestParam String studentId) {
        return "redirect:/certificate/view/" + studentId;
    }

    @GetMapping("/download")
    public String showDownloadCertificateForm() {
        return "downloadCertificateForm";
    }

    @PostMapping("/download")
    public String redirectToDownloadCertificate(@RequestParam String studentId) {
        return "redirect:/certificate/download/" + studentId;
    }

    @GetMapping("/view/{studentId}")
    public void showViewCertificateForm(@PathVariable String studentId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=certificate.pdf");

        try (OutputStream out = response.getOutputStream()) {
            certificateService.viewCertificateByStudentId(studentId, out); // write PDF directly to response
            out.flush();
        }

    }

    @GetMapping("/download/{studentId}")
    public void showDownloadCertificateForm(@PathVariable String studentId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=certificate.pdf");

        try (OutputStream out = response.getOutputStream()) {
            certificateService.viewCertificateByStudentId(studentId, out); // write PDF directly to response
            out.flush();
        }

    }


}
