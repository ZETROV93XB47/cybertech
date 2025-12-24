package com.novatech.cybertech.batch.task;

import com.novatech.cybertech.batch.base.BaseTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendFailedOrdersReportTasklet extends BaseTasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, StepArguments stepArguments) throws Exception {
        return null;
    }
}
