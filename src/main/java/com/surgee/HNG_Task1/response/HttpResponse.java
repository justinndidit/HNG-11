package com.surgee.HNG_Task1.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpResponse {
    private String client_ip;
    private String Location;
    private String greeting;
}
