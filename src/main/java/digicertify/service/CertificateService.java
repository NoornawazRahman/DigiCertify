package digicertify.service;

import digicertify.entity.CertificateInfo;

import java.io.FileNotFoundException;
import java.io.OutputStream;

public interface CertificateService {
    void generateCertificate(CertificateInfo data, OutputStream out) throws FileNotFoundException;

    void makeNewCertificate(CertificateInfo data);

    void viewCertificateByStudentId(String studentId, OutputStream out);
}
