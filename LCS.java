/*
    Programming Assignment 3
    Title: Longest Common Subsequence
    Team Members: Justin Toler, Rahul Bairathi, Shubhra Mishra

    README file for LCS implementation for Programming Assignment 3
Team Members: Justin Toler, Rahul Bairathi, Shubhra Mishra

Running time of this algorithm is O(mn) in the worst case.

1) Compiler used is Java development toolkit (JDK)

2) IDE used: IntelliJ

3) Array is used to store the various data types. No other datastructure is used.

4) The program is divided into three parts:
    i) reading the contents of input file
    ii) determining the subsequence of the strings through LCS algorithm
    iii) Calculating the performance of the code for the given input file
    iv) Writing the generated subsequences and the computed performance analysis to the output file array
    
5) Key functions used:
    i) We first initialize arrays to store sub-sequence lengths
    ii)Secondly, we determine the index for sub-sequence array
    iii)Then, we initialize the sub-sequence array.
    iv) Finally, we generate the sub-sequences for the given input file
    
6) What works in this implementation:
    i) The program successfully reads the input file in the format mentioned in the assignment.
    ii) The program successfully generates sub-sequences and computes performance for any input file.
    iii) The program successfully writes the contents of the final output to the output file that is specified.
    iv) Program is capable of handling any dirty data by exiting gracefully and not throwing any error.
    
7) What might not work:
    i)The program might run for extremely long time or may hang if the input is extremely large because the running time of LCS algorithm is O(mn). Although, for such case the program handles and exits efficiently and also is able to continue with other inputs elegantly.
 */

import java.io.*;

public class LCS {
    private static final int DIAGONAL = 0;
    private static final int UP = 1;
    private static final int LEFT = 2;

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

    public static void main(String[] args) {
        // 1. Check if 2 command line parameters are provided
        if(args.length < 2) {
            System.out.println("Please specify input and output file names.\n" + 
                "For example: java LCS input.txt output.txt");
            System.exit(1);
        }

        // 2. Check if the input file exists and is readable
        FileInputStream iFileStream = null;
        try {
            iFileStream = new FileInputStream(args[0]);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The input file " + args[0] + " cannot be read. " + 
                "Please provide a valid filename");
            System.exit(1);
        }

        // 3. Check if output file is writable
        FileOutputStream oFileStream = null;
        try {
            oFileStream = new FileOutputStream(args[1]);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The output file" + args[1] + ", is not writable. " +
                "Please verify that you have the correct permission, or there is no " +
                "folder with the same name");
            System.exit(1);
        }

        // 4. Initialize stream reader and writer
        DataInputStream iStream = new DataInputStream(iFileStream);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
        DataOutputStream oStream = new DataOutputStream(oFileStream);
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(oStream));

        // 5. Read input line from the file and perform insertion sort
        long beginTime = System.currentTimeMillis();// Begin time of execution
        String cLine = null;                        // Current line
        String pLine = null;                        // Previous Line
        String output = null;                       // Output
        boolean emptyInput = true;                  // Think input file is empty

        try {
            while((cLine = bReader.readLine()) != null) {
                // Skip empty lines
                if(cLine.trim().length() == 0) {
                    continue;
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

            if(emptyInput) {
                // If there were no valid lines in the file
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