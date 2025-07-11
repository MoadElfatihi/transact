package com.transacthex.transacthex.individual.job;

import com.transacthex.transacthex.individual.job.listener.IndividualStepListener;
import com.transacthex.transacthex.individual.job.mapper.IndividualFieldSetMapper;
import com.transacthex.transacthex.individual.job.model.IndividualDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class IndividualJobConfiguration {


    @Bean(name="IndividualJob")
    public Job individualJob(@Qualifier("individualStep") Step individualStep,
                             JobRepository jobRepository
                             ) {
        return new JobBuilder("individualJob", jobRepository).incrementer(new RunIdIncrementer())
                .start(individualStep)
                .build();
    }

    @Bean("individualStep")
    public Step getIndividualStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                  @Qualifier("individualReader") FlatFileItemReader<IndividualDto> individualReader,
                                  @Qualifier("individualWriter") ItemWriter<IndividualDto> individualWriter) {
        return new StepBuilder("individualStep", jobRepository)
                .<IndividualDto,IndividualDto> chunk(10, transactionManager)
                .reader(individualReader)
                .writer(individualWriter)
                .allowStartIfComplete(true)
                .listener(new IndividualStepListener())
                .build();

    }

    //@Value("#{jobParameters['filePath']}")String filePath
    @Bean("individualReader")
    @StepScope
    public  FlatFileItemReader<IndividualDto> getIndividualReader() {
        var itemReader = new FlatFileItemReader<IndividualDto>();
        itemReader.setLinesToSkip(1);
        //"files/individuals.txt"
        itemReader.setResource(new FileSystemResource("files/individuals.txt"));
        var lineMapper = new DefaultLineMapper<IndividualDto>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new IndividualFieldSetMapper());
        itemReader.setLineMapper(lineMapper);
        return itemReader;
    }

    @Bean("individualWriter")
    public ItemWriter<IndividualDto> getIndividualWriter() {
        return items -> items.forEach(System.out::println);
    }
}
