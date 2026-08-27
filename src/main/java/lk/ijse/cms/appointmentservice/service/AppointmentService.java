package lk.ijse.cms.appointmentservice.service;

import lk.ijse.cms.appointmentservice.dto.AppointmentRequestDTO;
import lk.ijse.cms.appointmentservice.dto.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);
    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto);
    void deleteAppointment(Long id);
    AppointmentResponseDTO getAppointment(Long id);
    List<AppointmentResponseDTO> getAllAppointments();
    List<AppointmentResponseDTO> getAppointmentsByPatientId(String patientId);
}
