package org.cqrs.response;

import lombok.*;

import java.util.Map;

/**
 * @author Mukesh Verma
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> header;
    private String status;
    private T payload;
    private String error;
    private String message;
}
