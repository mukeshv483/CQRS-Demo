package org.cqrs.controller;


import org.cqrs.model.InventoryAvailability;
import org.cqrs.response.ServiceResponse;
import org.cqrs.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping(value = "/availability")
    public Mono<ResponseEntity<ServiceResponse>> getAllOrders() {
        return Mono.just(queryService.getAllOrders()).map(res->{
            return ResponseEntity.ok(buildResponse((res)));
        }).onErrorResume(th->Mono.just(handleError(th)));
    }
    @GetMapping(value = "/availability/{itemNumber}")

    public Mono<ResponseEntity<ServiceResponse>> getOrders(@PathVariable("itemNumber") String itemNumber) {
        return Mono.just(queryService.getOrders(itemNumber)).map(res->{
          return ResponseEntity.ok(buildResponse(res));
        }).onErrorResume(th->Mono.just(handleError(th)));




    }

    private ResponseEntity<ServiceResponse> handleError(Throwable th){
        return ResponseEntity.ok(buildErrorResponse(th));
    }

    private ServiceResponse buildResponse(List<InventoryAvailability> inventoryAvailability){
        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setStatus(HttpStatus.OK.name());
        serviceResponse.setPayload(inventoryAvailability);
        return serviceResponse;
    }
    private ServiceResponse buildResponse(InventoryAvailability inventoryAvailability){
        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setStatus(HttpStatus.OK.name());
        serviceResponse.setPayload(inventoryAvailability);
        return serviceResponse;
    }
    private ServiceResponse buildErrorResponse(Throwable throwable){
        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        serviceResponse.setError(throwable.getLocalizedMessage());
        return serviceResponse;
    }
}
