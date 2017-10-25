/*
    Programming Assignment 3
    Title: Longest Common Subsequence
    Team Members: Justin Toler, Rahul Bairathi, Shubhra Mishra
 */

package com.pa3;

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
        int[][] direction = new int[seq1Length + 1][seq2Length + 1];
        int i = 0;
        int j = 0;

        // Index for sub-sequence array
        for (i = 0; i <= seq1Length; i++) {
            for (j = 0; j <= seq2Length; j++) {
                if (i == 0 || j == 0) {
                    length[i][j] = 0;
                } else if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                    length[i][j] = length[i - 1][j - 1] + 1;
                    direction[i][j] = DIAGONAL;
                } else if (length[i - 1][j] >= length[i][j - 1]) {
                    length[i][j] = length[i - 1][j];
                    direction[i][j] = UP;
                } else {
                    length[i][j] = length[i][j - 1];
                    direction[i][j] = LEFT;
                }
            }
        }

        if (length[seq1Length][seq2Length] == 0) {
            return "";
        } else {
            i = seq1Length;
            j = seq2Length;

            // Initialize Sub-sequence array
            char[] subSequence = new char[length[seq1Length][seq2Length]];
            int sIndex = length[seq1Length][seq2Length] - 1;
            // Generate sub-sequence
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
            System.out.println("Please specify input and output file names.\nFor example: java LCS input.txt output.text");
            System.exit(1);
        }

        // 2. Check if the input file exists and is readable
        FileInputStream iFileStream = null;
        try {
            iFileStream = new FileInputStream(args[0]);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The input file " + args[0] + " cannot be read. Please provide a valid filename");
            System.exit(1);
        }

        // 3. Check if output file is writable
        FileOutputStream oFileStream = null;
        try {
            oFileStream = new FileOutputStream(args[1]);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The output file" + args[1] + ", is not writable. Please verify that you have the correct permission, or there is no folder with the same name");
            System.exit(1);
        }

        // 4. Initialize stream reader and writer
        DataInputStream iStream = new DataInputStream(iFileStream);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
        DataOutputStream oStream = new DataOutputStream(oFileStream);
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(oStream));

        // 5. Read input line from the file and perform insertion sort
        String cLine = null;
        String pLine = null;
        try {
            while((cLine = bReader.readLine()) != null) {
                // Skip empty lines
                if(cLine.trim().length() == 0) {
                    continue;
                }

                if(pLine == null) {
                    pLine = cLine;
                } else {
                    String lcs = getLCS(pLine, cLine);
                    String output = "-----------------------------------\n" +
                            "The DNA Strands:\n\t" + pLine + "\n\t" + cLine +
                            "\nLCS is " + lcs  + "\nLCS length is " + lcs.length();
                    bWriter.write(output);
                    pLine = null;
                }
                bWriter.newLine();
            }
            System.out.println("Success: All done!");
        } catch(IOException ex) {
            System.out.println("Error: Unexpected IO error has occurred. Please try again");
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
                System.out.println("Error: Unexpected IO error has occurred. Please try again");
            }
        }
    }
}
