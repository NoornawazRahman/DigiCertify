package digicertify.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Certificate {

    private String studentName;
    private String studentId;
    private String topic;
    private String guideName;
    private String startDate;
    private String endDate;
    private String supervisorName;
    private String supervisorDesignation;
    private String issuedDate;
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