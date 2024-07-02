package com.surgee.HNG_Task1.Data;

import java.util.Optional;

import lombok.Data;

@Data
public class HttpResponseData {
    private Optional<MinutelyData> timelines;
}
