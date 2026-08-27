package lk.ijse.cms.appointmentservice.mapper;

import lk.ijse.cms.appointmentservice.dto.AppointmentRequestDTO;
import lk.ijse.cms.appointmentservice.dto.AppointmentResponseDTO;
import lk.ijse.cms.appointmentservice.dto.PatientDTO;
import lk.ijse.cms.appointmentservice.entity.Appointment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    public abstract Appointment toEntity(AppointmentRequestDTO dto);

    @Mapping(target = "patientName", ignore = true)
    public abstract AppointmentResponseDTO toResponseDto(Appointment appointment);

    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "appointment.id", target = "id")
    @Mapping(source = "appointment.patientId", target = "patientId")
    @Mapping(source = "appointment.doctorId", target = "doctorId")
    @Mapping(source = "appointment.appointmentDate", target = "appointmentDate")
    @Mapping(source = "appointment.appointmentTime", target = "appointmentTime")
    @Mapping(source = "appointment.status", target = "status")
    @Mapping(source = "appointment.notes", target = "notes")
    public abstract AppointmentResponseDTO toDto(Appointment appointment, PatientDTO patient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(AppointmentRequestDTO dto, @MappingTarget Appointment appointment);
}
