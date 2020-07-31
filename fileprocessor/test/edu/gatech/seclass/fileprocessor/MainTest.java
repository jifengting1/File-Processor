package edu.gatech.seclass.fileprocessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/*
DO NOT ALTER THIS CLASS.  Use it as an example for MyMainTest.java
 */

public class MainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
    *  TEST UTILITIES
    */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                     new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }

    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
	
	/*
	* TEST FILE CONTENT
	*/
	private static final String FILE1 =
            "Hello" + System.lineSeparator() +
            "Beatrice" + System.lineSeparator() +
            "albert" + System.lineSeparator() +
            "@#$%" + System.lineSeparator() +
            "#%Albert" + System.lineSeparator() +
            "--’’--911" + System.lineSeparator() +
            "hello" + System.lineSeparator();
	// You can add more files here using the same approach used for FILE1

    // test cases

    /*
    *   TEST CASES
    */

    // Frame #: Instructor example 1 from assignment directions
    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-s", "-l", inputFile1.getPath()};
        Main.main(args);

        String expected1 =
                "1 --’’--911" + System.lineSeparator() +
                "2 #%Albert" + System.lineSeparator() +
                "3 Beatrice" + System.lineSeparator() +
                "4 Hello" + System.lineSeparator() +
                "5 albert" + System.lineSeparator() +
                "6 hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: Instructor example 2 from assignment directions
    @Test
    public void mainTest2() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-s", "-l", "-r", "Hell", inputFile1.getPath()};
        Main.main(args);

        String expected1 =
                "1 --’’--911" + System.lineSeparator() +
                "2 #%Albert" + System.lineSeparator() +
                "3 Beatrice" + System.lineSeparator() +
                "4 albert" + System.lineSeparator() +
                "5 hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: Instructor example 3 from assignment directions
    @Test
    public void mainTest3() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-s", "-l", "-k", "ello", inputFile1.getPath()};
        Main.main(args);

        String expected1 =
                "1 Hello" + System.lineSeparator() +
                "2 hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: Instructor example 4 from assignment directions
    @Test
    public void mainTest4() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-t", "2", "-s", "-k", "l", inputFile1.getPath()};
        Main.main(args);

        String expected1 =
                "#%" + System.lineSeparator() +
                "He" + System.lineSeparator() +
                "al" + System.lineSeparator() +
                "he" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: Extra example
    @Test
    public void mainTest5() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-t", "-s", "-k", "l", "-l", inputFile1.getPath()};
        Main.main(args);

        String expected1 =
                "1 #" + System.lineSeparator() +
                "2 H" + System.lineSeparator() +
                "3 a" + System.lineSeparator() +
                "4 h" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: Instructor error example
    @Test
    public void mainTest7() throws Exception {
        //no arguments on the command line will pass an array of length 0 to the application (not a null).
        String args[]  = new String[0];
        Main.main(args);
        assertEquals("Usage: filesummary [-s] [-r string | -k string] [-t [integer]] [-l] <filename>", errStream.toString().trim());
    }

}