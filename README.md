# DigiCertify - Document Authentication With Digital Signature

**DigiCertify** is a web-based application designed to generate and digitally sign PDF certificates using user-specific data. It enables secure, tamper-proof certificate generation by embedding a digital signature, ensuring the authenticity of each certificate issued.

---

## 🔑 Key Features

- 🎓 **PDF Certificate Generation** using [iTextPDF](https://itextpdf.com/)
- 🧾 **Digital Signature Integration** for authenticity and tamper-proofing
- 📋 **User Input Support** (Name, Registration Number, Duration)
- 🗄️ **Data Persistence** using PostgreSQL via Spring Data JPA
- 🌐 **Frontend** built with **Thymeleaf CSR** supporting:
    - Home Page
    - Certificate Application Form
    - View Certificate
    - Download Certificate
- ⚙️ **Backend** using Java Spring Boot

---

## 🧰 Tech Stack

| Layer        | Technology                   |
|--------------|-------------------------------|
| Backend      | Spring Boot (Java 21), Spring Data JPA |
| Frontend     | Thymeleaf (Client-Side Rendering) |
| PDF Engine   | iTextPDF 7.2.5                |
| Database     | PostgreSQL                   |
| Digital Signing | iText Sign, BouncyCastle   |
| Build Tool   | Maven                        |

---

## 📦 Dependencies Overview

Major libraries and tools used:
- `com.itextpdf:sign` – for applying digital signatures
- `org.bouncycastle:bcprov-jdk15on` – for cryptographic operations
- `org.springframework.boot` – application framework
- `org.postgresql:postgresql` – PostgreSQL driver
- `org.projectlombok:lombok` – boilerplate reduction

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL (running and configured)
- PDF certificate template (if applicable)
- Keystore (PFX or JKS) for digital signing

---

### 🛠️ Installation

1. **Clone the repository**

```bash
git clone https://github.com/NoornawazRahman/DigiCertify.git
cd DigiCertify
