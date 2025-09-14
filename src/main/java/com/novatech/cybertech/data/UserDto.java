package com.novatech.cybertech.data;

import com.novatech.cybertech.entities.enums.CommunicationType;
import lombok.*;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UserDto {
    private CommunicationType favoriteCommunicationChanel;
    private String email;
}
