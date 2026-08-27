package lk.ijse.cms.appointmentservice.mapper;

import javax.annotation.processing.Generated;
import lk.ijse.cms.appointmentservice.dto.AppointmentRequestDTO;
import lk.ijse.cms.appointmentservice.dto.AppointmentResponseDTO;
import lk.ijse.cms.appointmentservice.dto.PatientDTO;
import lk.ijse.cms.appointmentservice.entity.Appointment;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-26T13:44:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4 (Microsoft)"
)
@Component
public class AppointmentMapperImpl extends AppointmentMapper {

    @Override
    public Appointment toEntity(AppointmentRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate( dto.getAppointmentDate() );
        appointment.setAppointmentTime( dto.getAppointmentTime() );
        appointment.setPatientId( dto.getPatientId() );
        appointment.setDoctorId( dto.getDoctorId() );
        appointment.setStatus( dto.getStatus() );
        appointment.setNotes( dto.getNotes() );

        return appointment;
    }

    @Override
    public AppointmentResponseDTO toResponseDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentResponseDTO.AppointmentResponseDTOBuilder appointmentResponseDTO = AppointmentResponseDTO.builder();

        appointmentResponseDTO.id( appointment.getId() );
        appointmentResponseDTO.appointmentDate( appointment.getAppointmentDate() );
        appointmentResponseDTO.appointmentTime( appointment.getAppointmentTime() );
        appointmentResponseDTO.patientId( appointment.getPatientId() );
        appointmentResponseDTO.doctorId( appointment.getDoctorId() );
        appointmentResponseDTO.status( appointment.getStatus() );
        appointmentResponseDTO.notes( appointment.getNotes() );

        return appointmentResponseDTO.build();
    }

    @Override
    public AppointmentResponseDTO toDto(Appointment appointment, PatientDTO patient) {
        if ( appointment == null && patient == null ) {
            return null;
        }

        AppointmentResponseDTO.AppointmentResponseDTOBuilder appointmentResponseDTO = AppointmentResponseDTO.builder();

        if ( appointment != null ) {
            appointmentResponseDTO.id( appointment.getId() );
            appointmentResponseDTO.patientId( appointment.getPatientId() );
            appointmentResponseDTO.doctorId( appointment.getDoctorId() );
            appointmentResponseDTO.appointmentDate( appointment.getAppointmentDate() );
            appointmentResponseDTO.appointmentTime( appointment.getAppointmentTime() );
            appointmentResponseDTO.status( appointment.getStatus() );
            appointmentResponseDTO.notes( appointment.getNotes() );
        }
        if ( patient != null ) {
            appointmentResponseDTO.patientName( patient.getName() );
        }

        return appointmentResponseDTO.build();
    }

    @Override
    public void updateEntity(AppointmentRequestDTO dto, Appointment appointment) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getAppointmentDate() != null ) {
            appointment.setAppointmentDate( dto.getAppointmentDate() );
        }
        if ( dto.getAppointmentTime() != null ) {
            appointment.setAppointmentTime( dto.getAppointmentTime() );
        }
        if ( dto.getPatientId() != null ) {
            appointment.setPatientId( dto.getPatientId() );
        }
        if ( dto.getDoctorId() != null ) {
            appointment.setDoctorId( dto.getDoctorId() );
        }
        if ( dto.getStatus() != null ) {
            appointment.setStatus( dto.getStatus() );
        }
        if ( dto.getNotes() != null ) {
            appointment.setNotes( dto.getNotes() );
        }
    }
}
