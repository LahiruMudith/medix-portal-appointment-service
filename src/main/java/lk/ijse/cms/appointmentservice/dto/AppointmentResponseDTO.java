package lk.ijse.cms.appointmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDTO {
    private Long id;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String status;
    private String notes;
}
