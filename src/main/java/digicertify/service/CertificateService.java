package digicertify.service;

import digicertify.entity.Certificate;

import java.io.FileNotFoundException;
import java.io.OutputStream;

public interface CertificateService {
    void generateCertificate(Certificate data, OutputStream out) throws FileNotFoundException;
}
