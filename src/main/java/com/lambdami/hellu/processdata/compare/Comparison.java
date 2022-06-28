package com.lambdami.hellu.processdata.compare;

import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.util.Collections;

/**
 * Text similarity - For author comparison
 *
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */
public class Comparison {

    private static final int ZERO = 0;
    private static final int LETTER_TO_LETTER = 1;
    private static final int LETTER_TO_WORD = 2;
    private static final int WORD_TO_WORD = 4;

    private Comparison(){
        //util class
    }


    // TODO : continue from here
    public static int isTheSameAuthor(Author a1, Author a2) {

        int aSize = a1.getName().size();
        int bSize = a2.getName().size();

        int[][] index = new int[aSize][bSize];

        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                index[i][j] = ZERO;
            }
        }

        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                String author1Name = a1.getName().get(i);
                String author2Name = a2.getName().get(j);
                if (author2Name.length() <= 2 && author1Name.startsWith(author2Name)) {
                    if (author1Name.length() <= 2) {
                        index[i][j] = LETTER_TO_LETTER;
                    } else {
                        index[i][j] = LETTER_TO_WORD;
                    }
                } else if (author1Name.length() <= 2 && author2Name.startsWith(author1Name)) {
                    if (author2Name.length() <= 2) {
                        index[i][j] = LETTER_TO_LETTER;
                    } else {
                        index[i][j] = LETTER_TO_WORD;
                    }
                } else if (author1Name.length() >= 2 && author2Name.length() >= 2 && author1Name.equalsIgnoreCase(author2Name))
                    index[i][j] = WORD_TO_WORD;
            }
        }
        int isSame = 0;

        for (int i = 0; i < aSize; i++) {
            int largerNum = 0;
            for (int j = 0; j < bSize; j++) {
                if (largerNum < index[i][j])
                    largerNum = index[i][j];
                else index[i][j] = ZERO;
            }
        }

        for (int j = 0; j < bSize; j++) {
            int largerNum = 0;
            for (int i = 0; i < aSize; i++) {
                if (largerNum < index[i][j])
                    largerNum = index[i][j];
                else index[i][j] = ZERO;
            }
        }

        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                isSame += index[i][j];
            }
        }

        if (isSame >= 5) {
            return 0;
        }
        //return -1?
        return 1;
    }
}
