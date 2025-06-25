package digicertify.service.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.signatures.*;
import digicertify.entity.CertificateInfo;
import digicertify.repository.CertificateRepository;
import digicertify.service.CertificateService;
import lombok.AllArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Properties;


@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private CertificateRepository certificateRepository;

    @Override
    public void generateCertificate(CertificateInfo data, OutputStream out) {
        try {
            // Step 1: Generate PDF into memory
            ByteArrayOutputStream unsignedOut = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(unsignedOut);
            PdfDocument pdfDoc = new PdfDocument(writer);


            Document document = new Document(pdfDoc);



            Paragraph title = new Paragraph("CERTIFICATE")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(24)
                    .setBold()
                    .setUnderline()
                    .setMarginBottom(40);
            document.add(title);

            String name = data.getStudentName();
            String id = data.getStudentId();
            String topic = data.getTopic();
            String guide = data.getGuideName();
            String startDate = data.getStartDate();
            String endDate = data.getEndDate();
            String signerName = data.getSupervisorName();
            String designation = data.getSupervisorDesignation();

            String content = String.format(
                    "This is to certify that %s (%s), a 6th semester undergraduate student in the Department of " +
                            "Computer Science and Technology, Indian Institute of Engineering Science and Technology, " +
                            "Shibpur, Howrah, West Bengal, India has successfully completed the Summer Internship Program on " +
                            "“%s”, under the guidance of %s in the Department of Computer Science and Technology, IIEST Shibpur " +
                            "during the period from %s to %s.",
                    name, id, topic, guide, startDate, endDate
            );

            Paragraph body = new Paragraph(content)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFontSize(12)
                    .setMarginBottom(50);
            document.add(body);

//            Paragraph dated = new Paragraph()
//                    .add("Dated: \n\n")
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setFontSize(12);
//            document.add(dated);

            Paragraph signsss = new Paragraph()
                    .add("\n\n\n")
                    .add(signerName)
                    .add("\n" + designation)
                    .add("\nDepartment of Computer Science and Technology")
                    .add("\nIIEST Shibpur, Howrah – 711103.")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12);
            document.add(signsss);

            document.close();



            // Step 2: Load keystore
            Properties props = new Properties();
            props.load(new FileInputStream("G:\\SignWithCAcert\\local\\key.properties"));
            String keystorePath = props.getProperty("PRIVATE");
            char[] password = props.getProperty("PASSWORD").toCharArray();


            Security.addProvider(new BouncyCastleProvider());
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(keystorePath), password);
            String alias = ks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) ks.getKey(alias, password);
            Certificate[] chain = ks.getCertificateChain(alias);

            // Step 3: Sign using iText 7 PdfSigner
            ByteArrayInputStream inputForSigning = new ByteArrayInputStream(unsignedOut.toByteArray());

            PdfSigner signer = new PdfSigner(
                    new PdfReader(inputForSigning),
                    out,
                    new StampingProperties().useAppendMode()
            );

            // Visible signature rectangle and appearance
            Rectangle rect = new Rectangle(250, 220, 100, 100);
            PdfSignatureAppearance appearance = signer.getSignatureAppearance()
                    .setReason("Certified Internship")
                    .setLocation("IIEST Shibpur")
                    .setReuseAppearance(false)
                    .setPageNumber(1)
                    .setPageRect(rect);
            signer.setFieldName("sig");

            // Signature config
            IExternalDigest digest = new BouncyCastleDigest();
            IExternalSignature signature = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, "BC");

            signer.signDetached(
                    digest,
                    signature,
                    chain,
                    null,
                    null,
                    null,
                    0,
                    PdfSigner.CryptoStandard.CMS
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate and sign PDF", e);
        }
    }

    @Override
    public void makeNewCertificate(CertificateInfo data) {
//        Employee savedEmployee =  employeeRepository.save(employee);
        CertificateInfo savedData = certificateRepository.save(data);

    }

    @Override
    public void viewCertificateByStudentId(String studentId, OutputStream out) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with the given Id" + employeeId));

        CertificateInfo data = certificateRepository.findById(studentId)
                .orElseThrow(()->new RuntimeException("Invalid Id"));
        generateCertificate(data, out);
    }


//    @Override
//    public void generateCertificate(Certificate data, OutputStream out) throws FileNotFoundException {
//        PdfWriter writer = new PdfWriter(out);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//
//
//        Paragraph title = new Paragraph("CERTIFICATE")
//                .setTextAlignment(TextAlignment.CENTER)
//                .setFontSize(24)
//                .setBold()
//                .setUnderline()
//                .setMarginBottom(40);
//        document.add(title);
//
//        String name = data.getStudentName();
//        String id = data.getStudentId();
//        String topic = data.getTopic();
//        String guide = data.getGuideName();
//        String startDate = data.getStartDate();
//        String endDate = data.getEndDate();
//        String signerName = data.getSupervisorName();
//        String designation = data.getSupervisorDesignation();
//
//        String content = String.format(
//                "This is to certify that %s (%s), a 6th semester undergraduate student in the Department of " +
//                        "Computer Science and Technology, Indian Institute of Engineering Science and Technology, " +
//                        "Shibpur, Howrah, West Bengal, India has successfully completed the Summer Internship Program on " +
//                        "“%s”, under the guidance of %s in the Department of Computer Science and Technology, IIEST Shibpur " +
//                        "during the period from %s to %s.",
//                name, id, topic, guide, startDate, endDate
//        );
//
//        Paragraph body = new Paragraph(content)
//                .setTextAlignment(TextAlignment.JUSTIFIED)
//                .setFontSize(12)
//                .setMarginBottom(50);
//        document.add(body);
//
//        Paragraph dated = new Paragraph()
//                .add("\n\n\n")
//                .add("\nDated: \n\n")
//                .setTextAlignment(TextAlignment.RIGHT)
//                .setFontSize(12);
//        document.add(dated);
//
//        Paragraph signature = new Paragraph()
//                .add("\n\n\n")
//                .add(signerName)
//                .add("\n" + designation)
//                .add("\nDepartment of Computer Science and Technology")
//                .add("\nIIEST Shibpur, Howrah – 711103.")
//                .setTextAlignment(TextAlignment.LEFT)
//                .setFontSize(12);
//        document.add(signature);
//
//        document.close();
//    }
}


//
//Document doc = new Document(pdfDoc);
//
//            doc.add(new Paragraph("CERTIFICATE")
//                    .setTextAlignment(TextAlignment.CENTER)
//                    .setFontSize(24)
//                    .setBold()
//                    .setUnderline()
//                    .setMarginBottom(40));
//
//String content = String.format(
//        "This is to certify that %s (%s), ... on “%s”, under the guidance of %s ... from %s to %s.",
//        data.getStudentName(), data.getStudentId(), data.getTopic(),
//        data.getGuideName(), data.getStartDate(), data.getEndDate()
//);
//
//            doc.add(new Paragraph(content)
//                    .setTextAlignment(TextAlignment.JUSTIFIED)
//                    .setFontSize(12)
//                    .setMarginBottom(50));
//
//        doc.add(new Paragraph("\nDated: \n\n")
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setFontSize(12));
//
//        doc.add(new Paragraph(data.getSupervisorName())
//        .add("\n" + data.getSupervisorDesignation())
//        .add("\nDepartment of Computer Science and Technology")
//                    .add("\nIIEST Shibpur, Howrah – 711103.")
//                    .setTextAlignment(TextAlignment.LEFT)
//                    .setFontSize(12));
//
//        doc.close();
