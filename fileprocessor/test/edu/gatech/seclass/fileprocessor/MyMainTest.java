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

import static org.junit.Assert.assertEquals;

public class MyMainTest {
/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/
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
    //
    private static final String manyLineFile =
            "Hello" + System.lineSeparator() +
                    "Beatrice" + System.lineSeparator() +
                    "albert" + System.lineSeparator() +
                    "@#$%" + System.lineSeparator() +
                    "#%Albert" + System.lineSeparator() +
                    "--’’--911" + System.lineSeparator() +
                    "hello" + System.lineSeparator();

    private static final String manyLineFile1 =
            "Hello" + System.lineSeparator() +
                    "Beatrice" + System.lineSeparator() +
                    "albert#%" + System.lineSeparator() +
                    "@#$%" + System.lineSeparator() +
                    "#%Albert" + System.lineSeparator() +
                    "--’’--911" + System.lineSeparator() +
                    "hello" + System.lineSeparator();

    private static final String mixedManyLineFile =
            "Hello" + System.lineSeparator() +
                    "Beatrice" + System.lineSeparator() +
                    "albert" + System.lineSeparator() +
                    "@#$%" + System.lineSeparator() +
                    "#%Albert" + System.lineSeparator() +
                    "42--’’--911" + System.lineSeparator() +
                    "hello" + System.lineSeparator();

    private static final String oneLineFile =
            "Hello123$%@" + System.lineSeparator();

    private static final String emptyFile =
            "" + System.lineSeparator();

    private static final String nonAlphanumericFile =
            "Beatrice" + System.lineSeparator() +
                    "albert" + System.lineSeparator() +
                    "@#$%" + System.lineSeparator();

    private static final String numberLeadingFile =
            "23Hello" + System.lineSeparator() +
                    "45Beatrice" + System.lineSeparator() +
                    "65albert" + System.lineSeparator() +
                    "87235@#$%" + System.lineSeparator() +
                    "37#%Albert" + System.lineSeparator() +
                    "967--’’--911" + System.lineSeparator() +
                    "4367hello" + System.lineSeparator();

    private static final String capitalLetterLeadingFile =
            "DFH23Hello" + System.lineSeparator() +
                    "OHM45Beatrice" + System.lineSeparator() +
                    "BMWW65albert" + System.lineSeparator() +
                    "ADFCC87235@#$%" + System.lineSeparator() +
                    "ZREFC37#%Albert" + System.lineSeparator() +
                    "AAAA967--’’--911" + System.lineSeparator() +
                    "AAA4367hello" + System.lineSeparator();

    private static final String lowercaseLetterLeadingFile =
            "dfh23Hello" + System.lineSeparator() +
                    "ohm45Beatrice" + System.lineSeparator() +
                    "bmww65albert" + System.lineSeparator() +
                    "adfcc87235@#$%" + System.lineSeparator() +
                    "zref37#%Albert" + System.lineSeparator() +
                    "aaaa967--’’--911" + System.lineSeparator() +
                    "aa4367hello" + System.lineSeparator();

    private static final String numberAndCapitalLetterLeadingFile =
            "143DFH23Hello" + System.lineSeparator() +
                    "OHM45Beatrice" + System.lineSeparator() +
                    "BMWW65albert" + System.lineSeparator() +
                    "53ADFCC87235@#$%" + System.lineSeparator() +
                    "ZREFC37#%Albert" + System.lineSeparator() +
                    "35AAAA967--’’--911" + System.lineSeparator() +
                    "AAA4367hello" + System.lineSeparator();

    private static final String capitalAndLowerLetterLeadingFile =
            "DFH23Hello" + System.lineSeparator() +
                    "ohm45Beatrice" + System.lineSeparator() +
                    "BMWW65albert" + System.lineSeparator() +
                    "AdfCC87235@#$%" + System.lineSeparator() +
                    "ZREFC37#%Albert" + System.lineSeparator() +
                    "aaAA967--’’--911" + System.lineSeparator() +
                    "AAA4367hello" + System.lineSeparator();

    private static final String numberAndLowercaseLetterLeadingFile =
            "25dfh23Hello" + System.lineSeparator() +
                    "ohm45Beatrice" + System.lineSeparator() +
                    "bmww65albert" + System.lineSeparator() +
                    "35adfcc87235@#$%" + System.lineSeparator() +
                    "zref37#%Albert" + System.lineSeparator() +
                    "34aaaa967--’’--911" + System.lineSeparator() +
                    "aa4367hello" + System.lineSeparator();

    /*
     *   TEST CASES
     */
    // Frame #: test case 1 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest1() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // not specified OPT
        String args[] = {inputFile1.getPath()};
        Main.main(args);

        String expected1 =
                "Hello" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "--’’--911" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 2 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest2() throws Exception {
        File inputFile1 = createInputFile(emptyFile);
        // input empty file
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);

        assertEquals("", errStream.toString().trim());
    }

    // Frame #: test case 3 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest3() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // input empty string with -r|-k optio
        String args[] = {"-r", "", inputFile1.getPath()};
        Main.main(args);
        assertEquals("", errStream.toString().trim());
    }

    // Frame #: test case 4 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest4() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // remove (-r) or keep (-k) only lines in the file which contain string s (case sensitive).
        String args[] = {"-k", "BeatriceNOT",inputFile1.getPath()};
        Main.main(args);
        String expected1 = "";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 5 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest5() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // remove (-r) or keep (-k) only lines in the file which contain string s (case sensitive).
        String args[] = {"-r", "  ",inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hello" + System.lineSeparator() +
                "Beatrice" + System.lineSeparator() +
                "albert" + System.lineSeparator() +
                "@#$%" + System.lineSeparator() +
                "#%Albert" + System.lineSeparator() +
                "--’’--911" + System.lineSeparator() +
                "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 6 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest6() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // n Value :  <=0
        String args[] = {"-t", "-1", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: filesummary [-s] [-r string | -k string] [-t [integer]] [-l] <filename>", errStream.toString().trim());
    }

    // Frame #: test case 7 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest7() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // n Value : > length of line
        String args[] = {"-t", "100", inputFile1.getPath()};
        Main.main(args);
        assertEquals("", errStream.toString().trim());
    }

    // Frame #: test case 8 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest8() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // remove (-r) or keep (-k) only lines in the file which contain string s (case sensitive).
        // n is omitted, n = 1
        String args[] = {"-t", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "H" + System.lineSeparator() +
                        "B" + System.lineSeparator() +
                        "a" + System.lineSeparator() +
                        "@" + System.lineSeparator() +
                        "#" + System.lineSeparator() +
                        "-" + System.lineSeparator() +
                        "h" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }


    // Frame #: test case 9 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest9() throws Exception {
        File inputFile1 = createInputFile(nonAlphanumericFile);
        // if line only contain non-alphanumerical characters
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 10 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest10() throws Exception {
        File inputFile1 = createInputFile(numberLeadingFile);
        // if line with number leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "23Hello" + System.lineSeparator() +
                        "37#%Albert" + System.lineSeparator() +
                        "4367hello" + System.lineSeparator() +
                        "45Beatrice" + System.lineSeparator() +
                        "65albert" + System.lineSeparator() +
                        "87235@#$%" + System.lineSeparator() +
                        "967--’’--911" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 11 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest11() throws Exception {
        File inputFile1 = createInputFile(capitalLetterLeadingFile);
        // if line capital letter leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "AAA4367hello" + System.lineSeparator()+
                        "AAAA967--’’--911" + System.lineSeparator() +
                        "ADFCC87235@#$%" + System.lineSeparator() +
                        "BMWW65albert" + System.lineSeparator() +
                        "DFH23Hello" + System.lineSeparator() +
                        "OHM45Beatrice" + System.lineSeparator() +
                        "ZREFC37#%Albert" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 12 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest12() throws Exception {
        File inputFile1 = createInputFile(lowercaseLetterLeadingFile);
        // if line lowercase letter leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "aa4367hello" + System.lineSeparator()+
                        "aaaa967--’’--911" + System.lineSeparator() +
                        "adfcc87235@#$%" + System.lineSeparator() +
                        "bmww65albert" + System.lineSeparator() +
                        "dfh23Hello" + System.lineSeparator() +
                        "ohm45Beatrice" + System.lineSeparator() +
                        "zref37#%Albert" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 13 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest13() throws Exception {
        File inputFile1 = createInputFile(numberAndCapitalLetterLeadingFile);
        // if line numbers and captial letters mixed leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "143DFH23Hello" + System.lineSeparator() +
                        "35AAAA967--’’--911" + System.lineSeparator() +
                        "53ADFCC87235@#$%" + System.lineSeparator() +
                        "AAA4367hello" + System.lineSeparator()+
                        "BMWW65albert" + System.lineSeparator() +
                        "OHM45Beatrice" + System.lineSeparator() +
                        "ZREFC37#%Albert" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 14 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest14() throws Exception {
        File inputFile1 = createInputFile(capitalAndLowerLetterLeadingFile);
        // captial letters and lowercase letters mixed leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "AAA4367hello" + System.lineSeparator() +
                        "AdfCC87235@#$%" + System.lineSeparator() +
                        "BMWW65albert" + System.lineSeparator() +
                        "DFH23Hello" + System.lineSeparator() +
                        "ZREFC37#%Albert" + System.lineSeparator() +
                        "aaAA967--’’--911" + System.lineSeparator() +
                        "ohm45Beatrice" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 15 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest15() throws Exception {
        File inputFile1 = createInputFile(numberAndLowercaseLetterLeadingFile);
        // numbers and lowercase letters mixed leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "25dfh23Hello" + System.lineSeparator() +
                        "34aaaa967--’’--911" + System.lineSeparator() +
                        "35adfcc87235@#$%" + System.lineSeparator() +
                        "aa4367hello" + System.lineSeparator() +
                        "bmww65albert" + System.lineSeparator() +
                        "ohm45Beatrice" + System.lineSeparator() +
                        "zref37#%Albert" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 16 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest16() throws Exception {
        File inputFile1 = createInputFile(mixedManyLineFile);
        // mixed numerical, lowercase and captial letter leadings
        String args[] = {"-s", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "42--’’--911" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "Hello" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 17 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest17() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  alphanumeric characters
        // File Content        :  String s occurs more than once in same line
        //   String s Length     :  1
        String args[] = {"-r", "l", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                        "Beatrice" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "--’’--911" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 18 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest18() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  alphanumeric characters
        // File Content        :  String s occurs in multiple lines
        //   String s Length     :  1
        String args[] = {"-k", "a", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() ;

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 19 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest19() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  alphanumeric characters
        //    File Content        :  String s not occurrs
        //   String s Length     :  1
        String args[] = {"-k", "w", inputFile1.getPath()};
        Main.main(args);
        String expected1 = "" ;

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 20 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest20() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  non-alphanumeric
        //  File Content        :  String s occurs more than once in same line
        //   String s Length     :  1
        String args[] = {"-r", "-", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hello" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 21 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest21() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  non-alphanumeric
        //  File Content        :  String s occurs in multiple lines
        //   String s Length     :  1
        String args[] = {"-r", "#", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hello" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "--’’--911" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 22 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest22() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  non-alphanumeric
        //  File Content        :  String s not occurrs
        //   String s Length     :  1
        String args[] = {"-r", "*", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hello" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "--’’--911" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 23 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest23() throws Exception {
        File inputFile1 = createInputFile(capitalLetterLeadingFile);
        // String s pattern    :  alphanumeric characters
        //  File Content        :  String s occurs more than once in same line
        //   String s Length     :  larger than 1
        String args[] = {"-k", "AA", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "AAAA967--’’--911" + System.lineSeparator() +
                        "AAA4367hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 24 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest24() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  alphanumeric characters
        //  File Content        :  String s occurs in multiple lines
        //   String s Length     :  larger than 1
        String args[] = {"-r", "ello", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "--’’--911" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 25 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest25() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  alphanumeric characters
        //  File Content        :  String s not occurrs
        //   String s Length     :  larger than 1
        String args[] = {"-r", "qwert", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hello" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "--’’--911" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 26 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest26() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  non-alphanumeric
        //  File Content        :  String s occurs more than once in same line
        //   String s Length     :  larger than 1
        String args[] = {"-r", "--", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hello" + System.lineSeparator() +
                        "Beatrice" + System.lineSeparator() +
                        "albert" + System.lineSeparator() +
                        "@#$%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator() +
                        "hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 27 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest27() throws Exception {
        File inputFile1 = createInputFile(manyLineFile1);
        // String s pattern    :  non-alphanumeric
        //  File Content        :  String s occurs in multiple lines
        //   String s Length     :  larger than 1
        String args[] = {"-k", "#%", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                        "albert#%" + System.lineSeparator() +
                        "#%Albert" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 28 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest28() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        // String s pattern    :  non-alphanumeric
        //  File Content        :  String s not occurrs
        //   String s Length     :  larger than 1
        String args[] = {"-k", "@%#%", inputFile1.getPath()};
        Main.main(args);
        String expected1 = "";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 29 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest29() throws Exception {
        File inputFile1 = createInputFile(oneLineFile);
        //  n Value             :  >0
        //   File Size           :  One line
        String args[] = {"-t", "4", inputFile1.getPath()};
        Main.main(args);
        String expected1 = "Hell" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 30 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest30() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        //  n Value             :  >0
        //   File Size           :  More than one line
        String args[] = {"-t", "3", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "Hel" + System.lineSeparator() +
                "Bea" + System.lineSeparator() +
                "alb" + System.lineSeparator() +
                "@#$" + System.lineSeparator() +
                "#%A" + System.lineSeparator() +
                "--’" + System.lineSeparator() +
                "hel" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 31 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest31() throws Exception {
        File inputFile1 = createInputFile(oneLineFile);
        //   File Size           :  One line
        String args[] = {"-l", inputFile1.getPath()};
        Main.main(args);
        String expected1 = "1 Hello123$%@" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Frame #: test case 32 in file catpart.txt.tsl
    @Test
    public void fileprocessorTest32() throws Exception {
        File inputFile1 = createInputFile(manyLineFile);
        //   File Size           :  More than one line
        String args[] = {"-l", inputFile1.getPath()};
        Main.main(args);
        String expected1 =
                "1 Hello" + System.lineSeparator() +
                "2 Beatrice" + System.lineSeparator() +
                "3 albert" + System.lineSeparator() +
                "4 @#$%" + System.lineSeparator() +
                "5 #%Albert" + System.lineSeparator() +
                "6 --’’--911" + System.lineSeparator() +
                "7 hello" + System.lineSeparator();

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }
}
