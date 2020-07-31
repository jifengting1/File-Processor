package edu.gatech.seclass.fileprocessor;

/**
 *  Interface created for use in Georgia Tech CS6300.
 *
 *  IMPORTANT: This interface should NOT be altered in any way.
 */
public interface FileProcessorInterface {
    /**
     * Sets the path of the input file. This method has to be called before invoking
     * the {@link #process() process} method.
     *
     * @param filepath The value to be set
     */
    void setFilepath(String filepath);

    /**
     * Enables/disables sorting.
     *
     * @param s Pass true to enable and false to disable (default)
     */
    void setS(boolean s);

    /**
     * Enables/disables line numbering.
     *
     * @param l Pass true to enable and false to disable (default)
     */
    void setL(boolean l);

    /**
     * Enables/disables the "remove" option, passing the string to be matched
     * (i.e., lines in the file that contain rString will be removed).
     *
     * @param rString The string to be matched or null to disable (default)
     */
    void setRString(String rString);

    /**
     * Enables/disables the "keep"" option, passing the string to be matched
     * (i.e., only lines in the file that contain kString will be kept).
     *
     * @param kString The string to be matched or null to disable (default)
     */
    void setKString(String kString);

    /**
     * Enables/disables trimming, passing the number of characters to be kept on each line.
     * It throws a {@link ProcessingException ProcessingException} if the parameter passed is 0 or negative.
     * The message in the exception provides information about the error.
     *
     * @param tInt Number of characters to be kept on each line
     * @throws ProcessingException
     */
    void setTInt(int tInt) throws ProcessingException;

    /**
     * Processes the file according to the current configuration (set through calls to the other methods in the class).
     * It throws a {@link ProcessingException ProcessingException} if an error condition occurs (e.g., when opening or
     * reading the input file). The message in the exception provides information about the error.
     *
     * @throws ProcessingException
     */
    void process() throws ProcessingException;
}
