package lk.ijse.cms.appointmentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentRequestDTO {

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    @NotBlank(message = "Appointment time is required")
    @Pattern(regexp = "^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$", message = "Invalid time format (e.g. 09:00 AM)")
    private String appointmentTime;

    @NotBlank(message = "Patient ID is required")
    @Pattern(regexp = "^\\d{9}[vV]$", message = "Invalid patient NIC format")
    private String patientId;

    @NotBlank(message = "Doctor ID is required")
    private String doctorId;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(SCHEDULED|COMPLETED|CANCELLED)$", message = "Status must be SCHEDULED, COMPLETED, or CANCELLED")
    private String status;

    private String notes;
}
