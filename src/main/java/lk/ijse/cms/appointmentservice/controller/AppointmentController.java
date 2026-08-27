package lk.ijse.cms.appointmentservice.controller;

import jakarta.validation.Valid;
import lk.ijse.cms.appointmentservice.dto.AppointmentRequestDTO;
import lk.ijse.cms.appointmentservice.dto.AppointmentResponseDTO;
import lk.ijse.cms.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        log.info("POST /api/v1/appointments - Patient: {}, Doctor: {}", dto.getPatientId(), dto.getDoctorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(dto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO dto) {
        log.info("PUT /api/v1/appointments/{} - Updating appointment", id);
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        log.info("DELETE /api/v1/appointments/{} - Deleting appointment", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable Long id) {
        log.info("GET /api/v1/appointments/{} - Fetching appointment", id);
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(
            @RequestParam(required = false) String patientId) {
        if (patientId != null && !patientId.isBlank()) {
            log.info("GET /api/v1/appointments?patientId={} - Fetching appointments by patient", patientId);
            return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
        }
        log.info("GET /api/v1/appointments - Fetching all appointments");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}
