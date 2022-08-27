package com.poc.jooqlock.domain.mileage.interfaces;

import com.poc.jooqlock.domain.mileage.domain.service.MileageService;
import com.poc.jooqlock.domain.mileage.interfaces.dto.MileageAccumulateRequest;
import com.poc.jooqlock.domain.mileage.interfaces.dto.MileageDeductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class MileageApi {

    private final MileageService mileageService;

    public MileageApi(final MileageService mileageService) {
        this.mileageService = mileageService;
    }

    @PostMapping("/mileage/{id}")
    public ResponseEntity<URI> accumulate(@RequestBody final MileageAccumulateRequest request,
                                          @PathVariable final Long id) {
        mileageService.accumulate(id, request.getPoint());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(URI.create("/mileage/%s".formatted(id)));
    }

    @PutMapping("/mileage/{id}")
    public ResponseEntity<Void> deduct(@RequestBody final MileageDeductRequest request,
                                       @PathVariable final Long id) {
        mileageService.deduct(id, request.getPoint());
        return ResponseEntity.noContent().build();
    }
}
