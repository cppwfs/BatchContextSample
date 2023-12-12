package io.spring.bcsample;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BcContextConfiguration {
    @Autowired
    public JobRepository jobRepository;

    @Autowired
    public PlatformTransactionManager transactionManager;

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .flow(new StepBuilder("jobStep1", this.jobRepository)
                        .tasklet(new Tasklet() {
                            @Override
                            public RepeatStatus execute(StepContribution contribution,
                                                        ChunkContext chunkContext) throws Exception {
                                contribution.getStepExecution().getExecutionContext().put("akshay", "step context");
                                System.out.println("Job was run");
                                return RepeatStatus.FINISHED;
                            }
                        }, this.transactionManager).listener(new StepExecutionListener() {

                            @Override
                            public void beforeStep(StepExecution stepExecution) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public ExitStatus afterStep(StepExecution stepExecution) {
                                stepExecution.getExecutionContext().put("akshay2", "exec listener");
                                return stepExecution.getExitStatus();
                            }
                        }).build()).build().build();
    }
}
