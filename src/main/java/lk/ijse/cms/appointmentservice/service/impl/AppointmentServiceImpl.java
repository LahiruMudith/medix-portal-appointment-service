package lk.ijse.cms.appointmentservice.service.impl;

import lk.ijse.cms.appointmentservice.client.DoctorServiceClient;
import lk.ijse.cms.appointmentservice.client.PatientServiceClient;
import lk.ijse.cms.appointmentservice.dto.AppointmentRequestDTO;
import lk.ijse.cms.appointmentservice.dto.AppointmentResponseDTO;
import lk.ijse.cms.appointmentservice.dto.PatientDTO;
import lk.ijse.cms.appointmentservice.entity.Appointment;
import lk.ijse.cms.appointmentservice.exception.AppointmentNotFoundException;
import lk.ijse.cms.appointmentservice.mapper.AppointmentMapper;
import lk.ijse.cms.appointmentservice.repository.AppointmentRepository;
import lk.ijse.cms.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientServiceClient patientServiceClient;
    private final DoctorServiceClient doctorServiceClient;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        log.debug("Creating appointment for patient ID: {} and doctor ID: {}", dto.getPatientId(), dto.getDoctorId());

        // Validate both references before any DB write
        PatientDTO patient = patientServiceClient.getPatient(dto.getPatientId());
        doctorServiceClient.validateDoctor(dto.getDoctorId());

        Appointment appointment = appointmentMapper.toEntity(dto);
        Appointment saved = appointmentRepository.save(appointment);

        log.info("Appointment created: {}", saved.getId());
        return appointmentMapper.toDto(saved, patient);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        log.debug("Updating appointment with ID: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        // Validate both references before updating DB
        PatientDTO patient = patientServiceClient.getPatient(dto.getPatientId());
        doctorServiceClient.validateDoctor(dto.getDoctorId());

        appointmentMapper.updateEntity(dto, appointment);
        Appointment updated = appointmentRepository.save(appointment);

        log.info("Appointment updated: {}", id);
        return appointmentMapper.toDto(updated, patient);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        log.debug("Deleting appointment with ID: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        appointmentRepository.delete(appointment);
        log.info("Appointment deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointment(Long id) {
        log.debug("Fetching appointment with ID: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        PatientDTO patient = null;
        try {
            patient = patientServiceClient.getPatient(appointment.getPatientId());
        } catch (Exception e) {
            log.warn("Could not retrieve patient details for patientId: {}", appointment.getPatientId(), e);
        }

        return patient != null
                ? appointmentMapper.toDto(appointment, patient)
                : appointmentMapper.toResponseDto(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAllAppointments() {
        log.debug("Fetching all appointments");
        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> {
                    PatientDTO patient = null;
                    try {
                        patient = patientServiceClient.getPatient(appointment.getPatientId());
                    } catch (Exception e) {
                        log.debug("Could not retrieve patient name for appointment {}", appointment.getId());
                    }
                    return patient != null
                            ? appointmentMapper.toDto(appointment, patient)
                            : appointmentMapper.toResponseDto(appointment);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAppointmentsByPatientId(String patientId) {
        log.debug("Fetching appointments for patient ID: {}", patientId);
        PatientDTO patient = null;
        try {
            patient = patientServiceClient.getPatient(patientId);
        } catch (Exception e) {
            log.warn("Could not retrieve patient details for patientId: {}", patientId, e);
        }

        PatientDTO finalPatient = patient;
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointment -> finalPatient != null
                        ? appointmentMapper.toDto(appointment, finalPatient)
                        : appointmentMapper.toResponseDto(appointment))
                .collect(Collectors.toList());
    }
}
