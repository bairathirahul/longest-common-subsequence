import java.io.*;
import java.util.regex.Pattern;

public class LCS {
    /*
     * Direction constants for LCS in direction matrix
     */
    private static final int DIAGONAL = 0;
    private static final int UP = 1;
    private static final int LEFT = 2;

    /*
     * Regular expression to check validity of DNA strand
     */
    private static final String REGEX_STRAND = "^[A|C|G|T]+$";

    /**
     * Returns longest common subsequence of input strings
     *
     * @param seq1 Input String Sequence 1
     * @param seq2 Input String Sequence 2
     * @return Longest Common Subsequence
     */
    private static String getLCS(String seq1, String seq2) {
        int seq1Length = seq1.length();
        int seq2Length = seq2.length();

        // Array to store sub-sequence lengths
        int[][] length = new int[seq1Length + 1][seq2Length + 1];
        // Array to store directions
        int[][] direction = new int[seq1Length + 1][seq2Length + 1];
        // Loop indexes
        int i = 0;
        int j = 0;

        // Index for sub-sequence array
        for (i = 0; i <= seq1Length; i++) {
            for (j = 0; j <= seq2Length; j++) {
                if (i == 0 || j == 0) {
                    // Initialize first row and column with zero
                    length[i][j] = 0;
                } else if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                    // Characters at are same at indexes i - 1 & j - 1
                    length[i][j] = length[i - 1][j - 1] + 1;
                    direction[i][j] = DIAGONAL;
                } else if (length[i - 1][j] >= length[i][j - 1]) {
                    // Characters are different and LCS is up
                    length[i][j] = length[i - 1][j];
                    direction[i][j] = UP;
                } else {
                    // Characters are different and LCS is left
                    length[i][j] = length[i][j - 1];
                    direction[i][j] = LEFT;
                }
            }
        }

        if (length[seq1Length][seq2Length] == 0) {
            // No LCS found, return empty string
            return "";
        } else {
            // Generate LCS. Loop from the end of the matrix
            i = seq1Length;
            j = seq2Length;

            // Initialize sub-sequence array
            char[] subSequence = new char[length[seq1Length][seq2Length]];
            // sIndex is the length of LCS
            int sIndex = length[seq1Length][seq2Length] - 1;
            // Find sub-sequence characters
            while(sIndex >= 0) {
                switch (direction[i][j]) {
                    case DIAGONAL:
                        subSequence[sIndex] = seq1.charAt(i - 1);
                        sIndex--;
                        i--;
                        j--;
                        break;
                    case UP:
                        i--;
                        break;
                    case LEFT:
                        j--;
                        break;
                }
            }
            return String.valueOf(subSequence);
        }
    }

    /**
     * Entry point of the program that reads the input file, calls getLCS function
     * and writes output to the output file
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Validate command line parameters
        if(args.length < 2) {
            System.out.println("Please specify input and output file names.\n" + 
                "For example: java LCS input.txt output.txt");
            System.exit(1);
        }

        // Validate the availability of input file
        FileInputStream iFileStream = null;
        try {
            iFileStream = new FileInputStream(args[0]);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The input file " + args[0] + " cannot be read. " + 
                "Please provide a valid filename");
            System.exit(1);
        }

        // Validate writeability of output file
        FileOutputStream oFileStream = null;
        try {
            oFileStream = new FileOutputStream(args[1]);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The output file" + args[1] + ", is not writable. " +
                "Please verify that you have the correct permission, or there is no " +
                "folder with the same name");
            System.exit(1);
        }

        // Initialize stream readers and writers
        DataInputStream iStream = new DataInputStream(iFileStream);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
        DataOutputStream oStream = new DataOutputStream(oFileStream);
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(oStream));

        // Initialize other auxillary variables and objects
        long beginTime = System.currentTimeMillis();        // Begin time of execution
        String cLine = null;                                // Current line
        String pLine = null;                                // Previous Line
        String output = null;                               // Output
        boolean emptyInput = true;                          // Flag for empty input file
        boolean invalidInput = false;                       // Flag for invalid input
        Pattern validator = Pattern.compile(REGEX_STRAND);  // Validator for input strings
        int lineNo = 0;                                     // Line number in file

        try {
            while((cLine = bReader.readLine()) != null) {
                // Increment line number
                lineNo++;

                // Skip empty lines
                if(cLine.trim().length() == 0) {
                    continue;
                }

                // Validate input line
                if(!validator.matcher(cLine).matches()) {
                    invalidInput = true;
                    break;
                }

                if(pLine == null) {
                    // Odd number line has been read, assign it to pLine
                    pLine = cLine;
                } else {
                    // Even number line is read, generate LCS
                    String lcs = getLCS(pLine, cLine);
                    // Conclusion, input was not empty
                    emptyInput = false;
                    output = "-----------------------------------\n" +
                             "The DNA Strands:\n\t" + pLine + "\n\t" + cLine + "\n" +
                             "LCS is " + lcs  + "\n" + 
                             "LCS length is " + lcs.length();
                    bWriter.write(output);
                    pLine = null;
                }
                bWriter.newLine();
            }

            if(invalidInput) {
                // Input was invalid
                System.out.println("Error: The string at line " + lineNo + " is invalid. Please fix the input and try again");
            } else if(emptyInput) {
                // There was only one line or no lines in input file
                System.out.println("Error: The input file was empty or contained only one line.");
            } else {
                long executionTime = (System.currentTimeMillis() - beginTime) / 1000;
                output = "-----------------------------------\n" +
                         "Running time: " + executionTime + " seconds\n" + 
                         "-----------------------------------";
                bWriter.write(output);
            }
        } catch(IOException ex) {
            System.out.println("Error: Unexpected IO error has occurred.");
        } finally {
            // 6. Release resources
            try {
                bWriter.close();
                oStream.close();
                oFileStream.close();
                bReader.close();
                iStream.close();
                iFileStream.close();
            } catch(IOException ex) {
                System.out.println("Error: Unexpected IO error has occurred.");
            }
        }
    }
}