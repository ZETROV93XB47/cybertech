package com.novatech.cybertech.utils;

import com.github.javafaker.Faker;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.entities.enums.Sex;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.novatech.cybertech.entities.enums.Role.USER;
import static com.novatech.cybertech.entities.enums.Sex.M;

@Slf4j
//@RequiredArgsConstructor
public class DataGenerator {

    private static final Faker FAKER = new Faker();
    //private final UserRepositoryUseCaseImpl userRepository;


    public static void main(String[] args) {

        //userRepository.saveAll(generateUsers(10));


        log.info("Start");
        log.info(generateUsersCustom(3, M, Role.USER, 10).toString());
        log.info("End");
    }



    public static Collection<UserEntity> generateUsers(final int numberOfUsers) {
        if (numberOfUsers <= 0) {
            log.info("Number of users is equal 0, only one user is going to be generated");
            UserEntity user = UserEntity.builder()
                    .id(0L)
                    .email(FAKER.internet().emailAddress())
                    .firstName(FAKER.name().firstName())
                    .lastName(FAKER.name().lastName())
                    .sex(M)
                    .address(FAKER.address().streetAddress())
                    .birthDate(FAKER.date().birthday())
                    .password(FAKER.internet().password())
                    .role(USER)
                    .orderEntities(new ArrayList<>())
                    .cartEntities(new ArrayList<>())
                    .reviewEntities(new ArrayList<>())
                    .build();

            return List.of(user);

        } else {
            List<UserEntity> users = new ArrayList<>();

            for (long i = 1; i < numberOfUsers + 1; i++) {
                users.add(
                        UserEntity.builder()
                                .uuid(UUID.randomUUID())
                                .email(FAKER.internet().emailAddress())
                                .firstName(FAKER.name().firstName())
                                .lastName(FAKER.name().lastName())
                                .sex(i % 2 == 0 ? Sex.F : M)
                                .address(FAKER.address().streetAddress())
                                .birthDate(FAKER.date().birthday())
                                .password(FAKER.internet().password())
                                .role(USER)
                                .orderEntities(new ArrayList<>())
                                .cartEntities(new ArrayList<>())
                                .reviewEntities(new ArrayList<>())
                                .build()

                );
            }
            return users;
        }
    }

    public static Collection<UserEntity> generateUsersCustom(final int numberOfUsers, final Sex sex, final Role role, long firstId) {
        if (numberOfUsers <= 0 || sex == null || role == null || firstId < 0) {
            return generateUsers(0);
        }
        List<UserEntity> users = new ArrayList<>();
        for (long i = 0; i < numberOfUsers; i++) {
            users.add(
                    UserEntity.builder()
                            .id(firstId++)

                            .email(FAKER.internet().emailAddress())
                            .firstName(FAKER.name().firstName())
                            .lastName(FAKER.name().lastName())
                            .sex(i % 2 == 0 ? Sex.F : M)
                            .address(FAKER.address().streetAddress())
                            .birthDate(FAKER.date().birthday())
                            .password(FAKER.internet().password())
                            .role(USER)
                            .orderEntities(new ArrayList<>())
                            .cartEntities(new ArrayList<>())
                            .build()
            );
        }
        return users;

    }

}