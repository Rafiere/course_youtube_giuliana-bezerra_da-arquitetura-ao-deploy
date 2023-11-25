package dev.piccodev.daarquiteturaaodeploy.web.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CnabService {

    private final Path fileStorageLocation;
    private final JobLauncher jobLauncher;
    private final Job job;


    public CnabService(@Value("${file.upload-dir}") String fileUploadDir,
                       @Qualifier("jobLauncherAsync") JobLauncher jobLauncher, //We are setting the bean using a qualifier because the Spring Batch will create a JobLauncher bean by default and we want to use our custom JobLauncher.
                       Job job){
        this.fileStorageLocation = Paths.get(fileUploadDir);
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void uploadCnabFile(MultipartFile file) throws Exception {

        //The "tmp" director will be used to store the uploaded file temporarily.

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = fileStorageLocation.resolve(fileName);

        //Here, the file is saved in the "tmp" directory.
        file.transferTo(targetLocation);

        //By default, if the Spring Boot finds a job bean, it will execute it.
        //To disable this behavior, we can set the property "spring.batch.job.enabled" to "false".


        //Below, we are creating the job parameters.
        var jobParameters = new JobParametersBuilder()
                .addJobParameter("cnab", file.getOriginalFilename(), String.class, true) //The "true" flag means that the parameter is used as a key to avoid duplicate executions of this job.
                .addJobParameter("cnabFile", "file:" + targetLocation.toString(), String.class)
                .toJobParameters();

        //Below, we are executing the job asynchronously.
        jobLauncher.run(job, jobParameters);
    }
}
