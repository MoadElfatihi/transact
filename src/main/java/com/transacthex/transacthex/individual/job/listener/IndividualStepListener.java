package com.transacthex.transacthex.individual.job.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class IndividualStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Step Execution Started: "+ stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Step Execution Completed: "+ stepExecution.getStepName());
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
