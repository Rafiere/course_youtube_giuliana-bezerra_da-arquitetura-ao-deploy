package dev.piccodev.daarquiteturaaodeploy.web.handlers;

import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    //This exception handler will be called when the "JobInstanceAlreadyCompleteException" is thrown.
    //This exception is thrown when we try to run a job that has already been completed.
    @ExceptionHandler(JobInstanceAlreadyCompleteException.class)
    private ResponseEntity<Object> handleFileAlreadyImported(JobInstanceAlreadyCompleteException exception){

        //We cannot import the same file twice.

        //At the database, the "PARAMETER_VALUE" column, from the "BATCH_JOB_EXECUTION_PARAMS" table, will store the file name and
        //this data will be used as a key to avoid duplicate executions of this job.
        return ResponseEntity.status(HttpStatus.CONFLICT).body("File already imported");
    }
}