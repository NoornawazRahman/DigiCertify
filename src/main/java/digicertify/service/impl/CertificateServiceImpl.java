package digicertify.service.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import digicertify.entity.Certificate;
import digicertify.service.CertificateService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.OutputStream;

@Service
public class CertificateServiceImpl implements CertificateService {
    @Override
    public void generateCertificate(Certificate data, OutputStream out) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);



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

        Paragraph dated = new Paragraph()
                .add("\n\n\n")
                .add("\nDated: \n\n")
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(12);
        document.add(dated);

        Paragraph signature = new Paragraph()
                .add("\n\n\n")
                .add(signerName)
                .add("\n" + designation)
                .add("\nDepartment of Computer Science and Technology")
                .add("\nIIEST Shibpur, Howrah – 711103.")
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(12);
        document.add(signature);

        document.close();
    }
}
