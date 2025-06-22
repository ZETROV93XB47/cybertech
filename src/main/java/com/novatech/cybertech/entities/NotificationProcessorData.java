package com.novatech.cybertech.entities;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class NotificationProcessorData {
    private final Object processorData;
}

