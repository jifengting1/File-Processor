package edu.gatech.seclass.fileprocessor;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FileProcessor implements FileProcessorInterface {
    private String filepath;
    private Boolean sMethod;
    private Boolean lMethod;
    private String rString;
    private String kString;
    private int tInt = 1;
    private Queue<String> queue = new LinkedList<>();
    private Charset charset = StandardCharsets.UTF_8;

    public String getFilepath() {
        return filepath;
    }

    public Boolean getsMethod() {
        return sMethod;
    }

    public Boolean getlMethod() {
        return lMethod;
    }

    public String getrString() {
        return rString;
    }

    public String getkString() {
        return kString;
    }

    public int gettInt() {
        return tInt;
    }

    public Queue<String> getQueue() {
        return queue;
    }

    public Charset getCharset() {
        return charset;
    }

    //get file content
    private String getFileContent(String filepath) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private File createInputFile(String input) throws IOException {
        File file = new File(filepath);
        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);
        fileWriter.close();
        return file;
    }

    @Override
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void setS(boolean s) {
        this.sMethod = s;
        queue.offer("s");
    }

    //-s: if specified, fileprocessor will sort the lines in the file alphanumerically, (1) ignoring (but keeping)
    // non-alphanumeric characters, (2) with numbers preceding letters, and (3) with capital letters preceding lowercase
    // letters (i.e., 0-9, A-Z, a-z). Any line containing only non-alphanumeric characters is removed.
    public String[] executeS(String[] content) {

        // remove lines without alphanumerical values
        int lineIdx;
        String regex = ".*[a-zA-Z0-9].*";
        List<String> newContent = new ArrayList<>();
        for (lineIdx = 0; lineIdx < content.length; lineIdx++) {
            if (content[lineIdx].matches(regex)) newContent.add(content[lineIdx]);
        }
        content = newContent.toArray(new String[newContent.size()]);//content to be sort and output

        // create string which is used for sorting (remove special character)
        List<String> sortContent = new ArrayList<>(Arrays.asList(content));//array contain only alphanumerical characters

        for (int sortContentIdx = 0; sortContentIdx < sortContent.size(); sortContentIdx++) {
            sortContent.set(sortContentIdx, sortContent.get(sortContentIdx).replaceAll("[^a-zA-Z0-9]", ""));
        }

        //convert sortContent to ascii value and sort.
        List<int[]> asciiContent = new ArrayList<>();
        for (String s : sortContent) {
            byte[] ascii = s.getBytes(StandardCharsets.US_ASCII);
            //convert byte array to integer array:
            // reference: https://stackoverflow.com/questions/11437203/how-to-convert-a-byte-array-to-an-int-array
            int[] ret = new int[ascii.length];
            for (int i = 0; i < ascii.length; i++) {
                ret[i] = ascii[i] & 0xff; // Range 0 to 255, not -128 to 127 }
            }
            asciiContent.add(ret);
        }

        List<int[]> asciiSortContent = new ArrayList<>(asciiContent);
        //sort the list based on ascii value
        Collections.sort(asciiSortContent, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                int idx = 0;
                int result = a[idx] - b[idx];
                idx = idx + 1;
                while (result == 0 && idx <= a.length - 1 && idx <= b.length - 1) {
                    result = a[idx] - b[idx];
                    idx = idx + 1;
                    //if two strings are the same, but one is longer than the other
                    if (result == 0 && idx > a.length -1 && idx <= b.length-1){
                        result = -2;
                    }
                    if (result == 0 && idx <= a.length -1 && idx > b.length-1){
                        result = 2;
                    }
                }
                return result;
            }
        });

        //get index of sorted list
        List<Integer> sortIndex = new ArrayList<>();
        for (int[] item : asciiSortContent) {
            int index = asciiContent.indexOf(item);
            sortIndex.add(index);
        }

        //rebuild content based on sortIndex
        List<String> sOutput = new ArrayList<>();
        for (int sortIdx = 0; sortIdx < content.length; sortIdx++) {
            sOutput.add(content[sortIndex.get(sortIdx)]);
        }
        String[] arrayOutput = sOutput.toArray(new String[0]);
        return arrayOutput;
    }

    @Override
    public void setL(boolean l) {
        this.lMethod = l;
        queue.offer("l");
    }

    //-l: if specified, fileprocessor will add the line number at the beginning of each line, followed by a space
    // and with the first line having line number 1.
    public String[] executeL(String[] content) {
        for (int lineIdx = 0; lineIdx < content.length; lineIdx++) {
            content[lineIdx] = Integer.toString(lineIdx + 1) + " " + content[lineIdx];
        }
        return content;
    }

    @Override
    public void setRString(String rString) {
        this.rString = rString;
        queue.offer("r");
    }

    //(-r|-k) <string s>: if specified, fileprocessor will remove (-r) or keep (-k) only lines in the file which contain
    // string s (case sensitive). Options -r and -k are mutually exclusive.
    public String[] executeR(String[] content) {
        List<String> output = new ArrayList<String>();
        for (String s : content) {
            if (!s.contains(rString)) {
                output.add(s);
            }
        }
        String[] arrayOutput = output.toArray(new String[0]);
        return arrayOutput;
    }

    @Override
    public void setKString(String kString) {
        this.kString = kString;
        queue.offer("k");
    }

    public String[] executeK(String[] content) {
        List<String> output = new ArrayList<String>();
        for (String s : content) {
            if (s.contains(kString)) {
                output.add(s);
            }
        }
        String[] arrayOutput = output.toArray(new String[0]);
        return arrayOutput;
    }

    @Override
    public void setTInt(int tInt) throws ProcessingException {
        this.tInt = tInt;
        if (tInt <= 0){
            throw new ProcessingException("The argument of t must be greater than 0");
        }
        queue.offer("t");
    }

    public String[] executeT(String[] content) {
        //-t [integer n]: if specified, fileprocessor will keep only the first n characters of each line.
        // Value n must be a positive integer. If n is omitted, its default value is 1.
        for (int lineIdx = 0; lineIdx < content.length; lineIdx++) {
            if (tInt < content[lineIdx].length()) {
                content[lineIdx] = content[lineIdx].substring(0, tInt);
            } else {
                content[lineIdx] = content[lineIdx];
            }
        }
        return content;
    }

    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
        @Override
        public void process () throws ProcessingException {

            if (filepath == null) {
                throw new ProcessingException("No filename provided");
            }

            try {
                Paths.get(filepath);
            } catch (InvalidPathException | NullPointerException ex) {
                throw new ProcessingException(("Invalid filename"));
            }

            if (!new File(filepath).isFile()){
                    throw new ProcessingException("Cannot read file: No_such_file.txt");
                }


            if(queue.contains("r") && queue.contains("k")){
                throw new ProcessingException("Options r and k are mutually exclusive");
            }

            //read content of file
            String lines = getFileContent(filepath);

            //String[] content = lines.split(System.getProperty("line.separator"));
            String[] content  = lines.split(System.getProperty("line.separator"), -1);
            content = Arrays.copyOfRange(content, 0, content.length-1 );


            /*
            //execute in sequence
            String currentExe = queue.poll();
            while (currentExe != null) {
                switch (currentExe) {
                    case "s":
                        content = executeS(content);
                        break;
                    case "l":
                        content = executeL(content);
                        break;
                    case "r":
                        content = executeR(content);
                        break;
                    case "k":
                        content = executeK(content);
                        break;
                    case "t":
                        content = executeT(content);
                        break;
                }
                currentExe = queue.poll();
            }
            */

            // execute without sequence
            if (queue.contains("s")){
                content = executeS(content);
            }
            if (queue.contains("r")){
                content = executeR(content);
            }
            if (queue.contains("k")){
                content = executeK(content);
            }
            if (queue.contains("t")){
                content = executeT(content);
            }
            if (queue.contains("l")){
                content = executeL(content);
            }

            // create final output string
            StringBuilder finalsb = new StringBuilder();
            String finalString;
                if (content == null){
                    finalString = "";
                }else{
                for (String s : content) {
                    finalsb.append(s).append(System.getProperty("line.separator"));
                }
                finalString = finalsb.toString();}
                // to file
                try {
                    createInputFile(finalString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }


