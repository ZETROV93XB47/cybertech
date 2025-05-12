package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.request.UserCreateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.mappers.entity.UserMapper;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Collection<UserResponseDto> getAll() {
        return userMapper.mapFromEntityToDto(userRepository.findAll());
    }

    @Override
    public UserResponseDto getByUUID(UUID uuid) {
        return userMapper.mapFromEntityToDto(userRepository.findByUuid(uuid).orElse(null));//TODO: check i fit's relevant to return null or throw an exception
    }

    @Override
    public Collection<UserResponseDto> getByUUIDs(Collection<UUID> uuids) {
        return userMapper.mapFromEntityToDto(userRepository.findAllByUuidIn(uuids));
    }

    @Override
    public UserResponseDto create(UserCreateRequestDto userCreateRequestDto) {
        return userMapper.mapFromEntityToDto(userRepository.save(userMapper.mapFromRequestToEntity(userCreateRequestDto)));
    }

    @Transactional
    public Collection<UserResponseDto> createAutomatically(Collection<UserEntity> users) {
        return new ArrayList<>(userMapper.mapFromEntityToDto(users.stream().map(userRepository::save).toList()));
    }

    //TODO: refactorer cette methode ou la retirer
    @Override
    public UserResponseDto update(UserCreateRequestDto userCreateRequestDto) {
        return userMapper.mapFromEntityToDto(userRepository.save(userMapper.mapFromRequestToEntity(userCreateRequestDto)));
    }

    @Override
    public void deleteByUUID(UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }

    @Override
    public void deleteByUUIDs(Collection<UUID> uuids) {
        userRepository.deleteAllByUuidIn(uuids);
    }


}
