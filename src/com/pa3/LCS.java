/*
    Programming Assignment 3
    Title: Longest Common Subsequence
    Team Members: Justin Toler, Rahul Bairathi, Shubhra Mishra
 */

package com.pa3;

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
        System.out.println(getLCS("ABCBDAB", "BDCABA"));
    }
}
