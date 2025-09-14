package com.novatech.cybertech.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    private String to;
    private String from;
    private String subject;
    private Object payload;
}
