package com.novatech.cybertech.config.batch;

import com.novatech.cybertech.batch.task.GetAllFailedPaymentOrderTasklet;
import com.novatech.cybertech.batch.task.SendFailedOrdersReportTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private static final String REPORT_FAILED_PAYMENT_ORDERS = "REPORT_FAILED_PAYMENT_ORDERS";
    private static final String SEND_FAILED_ORDER_REPORT_TASKLET = "SendFailedOrdersReportTasklet";
    private static final String REPORT_FAILED_PAYMENT_ORDERS_JOB = "REPORT_FAILED_PAYMENT_ORDERS_JOB";
    private static final String GET_ALL_FAILED_PAYMENTS_ORDER_TASKLET = "GetAllFailedPaymentOrderTasklet";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final SendFailedOrdersReportTasklet sendFailedOrdersReportTasklet;
    private final GetAllFailedPaymentOrderTasklet getAllFailedPaymentOrderTasklet;


    @Bean(REPORT_FAILED_PAYMENT_ORDERS)
    public Job reportFailedOrders() {
        final Step getAllFailedPaymentOrderStep = getAllFailedPaymentOrder();
        final Step sendFailedOrdersReportStep = sendFailedOrdersReportTasklet();

        return new JobBuilder(REPORT_FAILED_PAYMENT_ORDERS_JOB, jobRepository)
                .start(getAllFailedPaymentOrderStep).on("REPORT_NOT_NEEDED").end()
                .from(getAllFailedPaymentOrderStep).on(ExitStatus.COMPLETED.getExitCode())
                .to(sendFailedOrdersReportStep).end()
                .build();
    }


    @Bean(GET_ALL_FAILED_PAYMENTS_ORDER_TASKLET)
    public Step getAllFailedPaymentOrder() {
        return new StepBuilder(GET_ALL_FAILED_PAYMENTS_ORDER_TASKLET, jobRepository)
                .tasklet(getAllFailedPaymentOrderTasklet, platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean(SEND_FAILED_ORDER_REPORT_TASKLET)
    public Step sendFailedOrdersReportTasklet() {
        return new StepBuilder(SEND_FAILED_ORDER_REPORT_TASKLET, jobRepository)
                .tasklet(sendFailedOrdersReportTasklet, platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }
}
