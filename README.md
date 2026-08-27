# Appointment Service

## Student Information
* **Name:** Lahiru Mudith
* **Student Number:** CMS-ST-2026
* **GCP Project ID:** `cms-cloud-architecture`

---

## Service Overview
The **Appointment Service** coordinates patient bookings. When saving a booking, it validates:
1. Patient registration by querying `Patient-Service`.
2. Doctor availability schedules by querying `Doctor-Service`.

These queries are executed using a load-balanced `RestClient` over dynamic Eureka service IDs (`http://PATIENT-SERVICE` and `http://DOCTOR-SERVICE`).

* **Default Port:** `8002`
* **Data Store:** MySQL (Database: `cms_appointment`)
* **Target Environment:** OpenJDK 25, Spring Boot 4.1.1, Spring Cloud 2025.1.3.

---

## RestClient Configuration (Eureka Isolation)
Configured with a custom [`RestClientConfig.java`](file:///C:/Users/Lahiru%20Mudith/Documents/Study/IJSE/4th%20Sem/Enterprise%20Cloud%20Architectur/Projects/Clinic-Management-System/cms-services/service-appointment/src/main/java/lk/ijse/cms/appointmentservice/config/RestClientConfig.java):
* A `@Primary` builder (without `@LoadBalanced`) is used by Eureka's registry service.
* A qualified `@LoadBalanced` builder is used for inter-service client queries to resolve dynamic service locations.

---

## Validation Constraints
* **`appointmentTime` pattern:** `^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$` (e.g., `09:30 AM` or `04:15 PM`).

---

## REST Endpoints (Routed via Gateway Port `7000`)

| Method | Endpoint | Description | Request Format |
|---|---|---|---|
| **POST** | `/api/v1/appointments` | Book new appointment session | JSON |
| **GET** | `/api/v1/appointments` | List all bookings / filter by patientId | Query params (optional) |
| **PUT** | `/api/v1/appointments/{id}` | Reschedule an appointment slot | JSON |
| **DELETE** | `/api/v1/appointments/{id}` | Cancel and delete booking | None |

### Sample Appointment Request Payload:
```json
{
  "appointmentDate": "2026-09-12",
  "appointmentTime": "10:30 AM",
  "patientId": "716500500V",
  "doctorId": "doc-uuid-here",
  "status": "SCHEDULED",
  "notes": "Regular checkup"
}
```

---

## Build and Run

### Build Command:
```bash
mvn clean package -DskipTests
```

### Execution Command:
```bash
java -jar target/Appointment-Service-1.0.0.jar
```

---

## 🧪 Testing with Postman
A dedicated testing collection is available locally:
👉 **[Appointment-Service-Postman-Collection.json](file:///C:/Users/Lahiru%20Mudith/Documents/Study/IJSE/4th%20Sem/Enterprise%20Cloud%20Architectur/Projects/Clinic-Management-System/cms-services/service-appointment/Appointment-Service-Postman-Collection.json)**

* **Usage:** Import the collection file into Postman.
* **Environment Configuration:** Adjust the collection-scoped `baseUrl` variable to point to `http://localhost:8002` (for direct testing) or `http://localhost:7000` (when testing via the gateway).

It incorporates test script hooks to save created appointment IDs dynamically into `{{last_appointment_id}}` variables for easy update/delete sequence testing.
