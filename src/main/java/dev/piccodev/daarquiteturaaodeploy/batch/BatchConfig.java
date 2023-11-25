package dev.piccodev.daarquiteturaaodeploy.batch;

import dev.piccodev.daarquiteturaaodeploy.domain.Transacao;
import dev.piccodev.daarquiteturaaodeploy.domain.TransacaoCNAB;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    //In this class, we will configure the batch processing components.

    //This dependency will help us to manage the transactions in the batch process.
    private PlatformTransactionManager platformTransactionManager;

    public BatchConfig(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    //A job is a collection of steps that can be executed sequentially or in parallel to do a task.

    //A step is a domain object that encapsulates an independent, sequential phase of a batch job.

    //A job repository is a component used to save the state and the metadata of the batch job.
    @Bean
    public Job job(Step step, JobRepository jobRepository){ //We could inject the JobRepository at the class level.

        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer()) //The RunIdIncrementer is used to generate a unique job instance id. It allows us to rerun the job with the same parameters.
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     ItemReader<TransacaoCNAB> reader, //The reader will read a "TransacaoCNAB" type.
                     ItemProcessor<TransacaoCNAB, Transacao> processor, //The processor will receive a "TransacaoCNAB" file and will return a "Transacao" type.
                     ItemWriter<Transacao> writer){ //The ItemWriter will write a "Transacao" object type.

        return new StepBuilder("step", jobRepository)
                .<TransacaoCNAB, Transacao>chunk(100, platformTransactionManager) //The chunk size is the number of records that will be read, processed, and written in each transaction. The job will use the injected TransactionManager for manage the database transactions. The chunk enter type will be "TransacaoCNAB" and the exit type will be "Transacao".
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    //By default, Spring Batch will run the jobs synchronously. This means that the job will run in the same thread as the caller.
    //If we want to run the job asynchronously, we need to create a JobLauncher bean and set a TaskExecutor to it.
    //The TaskExecutor will be responsible for running the job in a different thread.

    //Basically, we are configuring the job launcher that will be injected by Spring Boot.
    @Bean
    public JobLauncher jobLauncherAsync(JobRepository jobRepository) throws Exception {

        var jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();

        return jobLauncher;
    }
}