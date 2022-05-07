package com.lambdami.hellu.processdata.compare;

import com.lambdami.hellu.model.Author;

/** Text similarity - For author comparison
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */
public class Comparison {

    private final int ZERO = 0;
    private final int LETTER_TO_LETTER = 1;
    private final int LETTER_TO_WORD = 2;
    private final int WORD_TO_WORD = 4;

    private Author author1;
    private Author author2;

    public Comparison(Author author1, Author author2) {

        this.author1 = author1;
        this.author2 = author2;

    }

    // TODO : continue from here
    public boolean isTheSameAuthor() {

        int aSize = author1.getName().length;
        int bSize = author2.getName().length;

        int[][] index = new int[aSize][bSize];

        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                index[i][j] = ZERO;
            }
        }

        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                if (author2.getName()[j].length() <= 2 && author1.getName()[i].startsWith(author2.getName()[j])) {
                    if (author1.getName()[i].length() <= 2) {
                        index[i][j] = LETTER_TO_LETTER;
                    } else {
                        index[i][j] = LETTER_TO_WORD;
                    }
                } else if (author1.getName()[i].length() <= 2 && author2.getName()[j].startsWith(author1.getName()[i])) {
                    if (author2.getName()[j].length() <= 2) {
                        index[i][j] = LETTER_TO_LETTER;
                    } else {
                        index[i][j] = LETTER_TO_WORD;
                    }
                } else if (author1.getName()[i].equalsIgnoreCase(author2.getName()[j]) && author1.getName()[i].length() >= 2
                        && author2.getName()[j].length() >= 2)
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

        return isSame >= 5;
    }
}
