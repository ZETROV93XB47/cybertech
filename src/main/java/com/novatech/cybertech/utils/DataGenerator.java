package com.novatech.cybertech.utils;

import com.github.javafaker.Faker;
import com.novatech.cybertech.entities.*;
import com.novatech.cybertech.entities.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.novatech.cybertech.entities.enums.Role.ADMIN;
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

    public static ReviewEntity generateReviewEntity() {

        UserEntity userEntity = generateUser();
        userEntity.setId((long) (Math.random() * 1000));

        return ReviewEntity.builder()
                //.id(new Random().nextLong())
                .rating(5)
                .comment("Fuck you nigga, i hope your family die from cancer")
                .isHateful(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .uuid(UUID.randomUUID())
                .userEntity(userEntity)
                .productEntity(generateProduct())
                .build();
    }

    public static ProductEntity generateProduct() {
        return ProductEntity.builder()
                //.id(new Random().nextLong())
                .uuid(UUID.randomUUID())
                .name(FAKER.commerce().productName())
                .description("Gaming PC Fireeeee")
                .price(new BigDecimal(2300))
                .cpu("core ultra 7")
                .gpu("RTX 5090 Ti")
                .ram(Ram.GO_128)
                .category(Category.GAMING)
                .brand(Brand.ASUS)
                .connectivity("WIFI 7")
                .displaySize(DisplaySize._15_INCHES)
                .displayType(DisplayType.AMVA)
                .stock(1000)
                .os(Os.WINDOWS)
                .ssd(SSD.GO_8192)
                .photo(FAKER.internet().image())
                .orderItemEntities(List.of())
                //.reviewEntities(List.of())
                .build();
    }

    public static UserEntity generateUser() {
        return UserEntity.builder()
                //.id(1875L)
                .email(FAKER.internet().emailAddress())
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .sex(M)
                .address(FAKER.address().streetAddress())
                .birthDate(FAKER.date().birthday())
                .password(FAKER.internet().password())
                .role(USER)
                .numberOfHatefulComments(0)
                .orderEntities(new ArrayList<>())
                .reviewEntities(new ArrayList<>())
                .build();
    }

    public static Collection<UserEntity> generateUsers(final int numberOfUsers) {

        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (numberOfUsers <= 0) {
            log.info("Number of users is equal 0, only one user is going to be generated");
            UserEntity user = UserEntity.builder()
                    .email(FAKER.internet().emailAddress())
                    .firstName(FAKER.name().firstName())
                    .lastName(FAKER.name().lastName())
                    .sex(M)
                    .address(FAKER.address().streetAddress())
                    .birthDate(FAKER.date().birthday())
                    .password(passwordEncoder.encode("password"))
                    .role(ADMIN)
                    .isActive(true)
                    .numberOfHatefulComments(0)
                    .orderEntities(new ArrayList<>())
                    //.cartEntities(new ArrayList<>())
                    .reviewEntities(new ArrayList<>())
                    .build();

            return List.of(user);

        } else {
            List<UserEntity> users = new ArrayList<>();

            for (long i = 1; i < numberOfUsers + 1; i++) {
                UserEntity userEntity = UserEntity.builder()
                        .uuid(UUID.randomUUID())
                        .email(FAKER.internet().emailAddress())
                        .firstName(FAKER.name().firstName())
                        .lastName(FAKER.name().lastName())
                        .sex(i % 2 == 0 ? Sex.F : M)
                        .address(FAKER.address().streetAddress())
                        .birthDate(FAKER.date().birthday())
                        .password(passwordEncoder.encode("password"))
                        .role(ADMIN)
                        .isActive(true)
                        .numberOfHatefulComments(0)
                        .orderEntities(new ArrayList<>())
                        .reviewEntities(new ArrayList<>())
                        .bankCardEntities(new ArrayList<>())
                        //.cartEntities(new ArrayList<>())
                        .build();

                //ReviewEntity reviewEntity = generateReviewEntity(userEntity);
                //userEntity.getReviewEntities().add(reviewEntity);

                users.add(userEntity);

            }
            return users;
        }
    }

    public static OrderEntity generateOrder() {
        return OrderEntity.builder()
                .uuid(UUID.randomUUID())
                .orderDate(LocalDateTime.now())
                .orderItemEntities(new ArrayList<>())
                .shippingAddress(FAKER.address().fullAddress())
                .status(OrderStatus.PROCESSING)
                .totalAmount(new BigDecimal(0))
                .paymentEntity(generatePayment())
                .build();
    }

    public static PaymentEntity generatePayment() {
        return PaymentEntity.builder()
                .uuid(UUID.randomUUID())
                .amount(new BigDecimal(0))
                .paymentDate(LocalDateTime.now())
                .paymentStatus(PaymentStatus.SUCCESS)
                .paymentType(PaymentType.VISA)
                .build();
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
                            //.cartEntities(new ArrayList<>())
                            .build()
            );
        }
        return users;

    }

}