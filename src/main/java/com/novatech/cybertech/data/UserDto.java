package com.novatech.cybertech.data;

import com.novatech.cybertech.entities.enums.CommunicationType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserDto {
    private CommunicationType defaultCommunicationChanel;
    private String email;
}
