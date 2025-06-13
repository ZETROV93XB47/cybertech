package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.dto.request.user.UserUpdateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.exceptions.UserNotFoundException;
import com.novatech.cybertech.mappers.entity.UserMapper;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public Collection<UserResponseDto> getAll() {
        return userMapper.mapFromEntityToDto(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getByUUID(UUID uuid) {
        return userMapper.mapFromEntityToDto(userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException("No user with the UUID : {} found", uuid)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserResponseDto> getByUUIDs(Collection<UUID> uuids) {
        return userMapper.mapFromEntityToDto(userRepository.findAllByUuidIn(uuids));
    }

    @Override
    @Transactional
    public UserResponseDto create(UserCreateRequestDto userCreateRequestDto) {
        return userMapper.mapFromEntityToDto(userRepository.save(userMapper.mapFromCreationRequestToEntity(userCreateRequestDto)));
    }

    @Transactional
    public Collection<UserResponseDto> createAutomatically(Collection<UserEntity> users) {
        return new ArrayList<>(userMapper.mapFromEntityToDto(users.stream().map(userRepository::save).toList()));
    }

    //TODO: refactorer cette methode ou la retirer
    @Override
    @Transactional
    public UserResponseDto update(final UserUpdateRequestDto userCreateRequestDto) {
        return userMapper.mapFromEntityToDto(userRepository.save(userMapper.mapFromUpdateRequestToEntity(userCreateRequestDto)));
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteByUUIDs(Collection<UUID> uuids) {
        userRepository.deleteAllByUuidIn(uuids);
    }

}
