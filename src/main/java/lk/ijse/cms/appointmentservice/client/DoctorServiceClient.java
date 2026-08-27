package lk.ijse.cms.appointmentservice.client;

import lk.ijse.cms.appointmentservice.exception.DoctorServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class DoctorServiceClient {

    private final RestClient restClient;

    public DoctorServiceClient(@LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://DOCTOR-SERVICE")
                .build();
    }

    public void validateDoctor(String doctorId) {
        log.debug("Validating doctor in DOCTOR-SERVICE for ID: {}", doctorId);
        try {
            restClient.get()
                    .uri("/api/v1/doctors/{id}", doctorId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            log.error("Failed to validate doctor: {}", doctorId, e);
            throw new DoctorServiceException("Unable to validate doctor: " + doctorId, e);
        }
    }
}
