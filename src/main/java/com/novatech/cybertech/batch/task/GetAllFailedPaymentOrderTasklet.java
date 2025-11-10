package com.novatech.cybertech.batch.task;

import com.novatech.cybertech.batch.base.BaseTasklet;
import com.novatech.cybertech.entities.BaseEntity;
import com.novatech.cybertech.entities.PaymentEntity;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import com.novatech.cybertech.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.batch.core.ExitStatus.COMPLETED;


@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllFailedPaymentOrderTasklet extends BaseTasklet {

    private static final String REPORT_NOT_NEEDED = "REPORT_NOT_NEEDED";

    private final PaymentRepository paymentRepository;


    @Override
    public RepeatStatus execute(final StepContribution stepContribution, final BaseTasklet.StepArguments stepArguments) {

        Map<String, Long> failedPaymentsUsersEmail = paymentRepository.findByPaymentStatus(PaymentStatus.FAILED).stream()
                .map(PaymentEntity::getOrderEntity)
                .collect(Collectors.toMap(o -> o.getUserEntity().getEmail(), BaseEntity::getId));

        if (failedPaymentsUsersEmail.isEmpty()) {
            stepContribution.setExitStatus(new ExitStatus(REPORT_NOT_NEEDED));
        }

        else {
            stepContribution.getStepExecution().getJobExecution().getExecutionContext().put("FAILED_PAYMENT_ORDERS_MAP_BY_USERS", failedPaymentsUsersEmail);
            stepContribution.setExitStatus(COMPLETED);
        }

        return RepeatStatus.FINISHED;
    }
}
