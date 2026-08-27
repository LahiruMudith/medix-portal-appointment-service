package lk.ijse.cms.appointmentservice.client;

import lk.ijse.cms.appointmentservice.dto.PatientDTO;
import lk.ijse.cms.appointmentservice.exception.PatientServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class PatientServiceClient {

    private final RestClient restClient;

    public PatientServiceClient(@LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://PATIENT-SERVICE")
                .build();
    }

    public PatientDTO getPatient(String patientId) {
        log.debug("Fetching patient from PATIENT-SERVICE for ID: {}", patientId);
        try {
            return restClient.get()
                    .uri("/api/v1/patients/{id}", patientId)
                    .retrieve()
                    .body(PatientDTO.class);
        } catch (RestClientException e) {
            log.error("Failed to fetch patient: {}", patientId, e);
            throw new PatientServiceException("Unable to retrieve patient: " + patientId, e);
        }
    }
}
