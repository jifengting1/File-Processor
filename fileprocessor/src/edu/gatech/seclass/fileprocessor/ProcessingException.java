package edu.gatech.seclass.fileprocessor;

public class ProcessingException extends Exception {
    ProcessingException() {
        super("Something went wrong during processing");
    }

    ProcessingException(String str) {
        super(str);
    }
}
