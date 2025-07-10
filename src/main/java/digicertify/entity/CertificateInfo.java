package digicertify.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "certificate_info")
public class CertificateInfo {

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "guide_name", nullable = false)
    private String guideName;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "supervisor_name", nullable = false)
    private String supervisorName;

    @Column(name = "supervisor_designation", nullable = false)
    private String supervisorDesignation;
//    private String issuedDate;
}


//Certificate data = new Certificate(
//        "Md Noornawaz Rahman",
//        "2022CSB085",
//        "Document Authentication with Digital Signature",
//        "AKL Sir",
//        "2025-05-19",
//        "2025-06-19",
//        "AKL Sir",
//        "Profesor",
//        "2025-06-15"
//);