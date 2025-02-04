package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseRecordDto;
import com.ead.authuser.dtos.ResponsePageDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class CourseClient
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 13/12/2024
 */
@Component
public class CourseClient {

    @Value("${ead.api.url.course}")
    String baseUrlCourse;

    final RestClient restClient;

    public CourseClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

//    @Retry(name = "retryInstance",fallbackMethod = "retryfallback")
    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseRecordDto> getAllCoursesByUser(UUID userId, Pageable pageable){
        String url = baseUrlCourse + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                       + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
        try{
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<CourseRecordDto>>() {});

        } catch (RestClientException e) {
            throw new RuntimeException("Error Request RestClient", e);
        }
    }

    public Page<CourseRecordDto> retryfallback(UUID userId, Pageable pageable, Throwable t) {
        List<CourseRecordDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }

    public Page<CourseRecordDto> circuitbreakerfallback(UUID userId, Pageable pageable, Throwable t) {
        List<CourseRecordDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }
}
