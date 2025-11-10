package com.novatech.cybertech.data;

import com.novatech.cybertech.entities.enums.CommunicationChanel;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserDto {
    private CommunicationChanel defaultCommunicationChanel;
    private String email;
}
