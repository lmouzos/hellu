package com.lambdami.hellu.searchsata.googleresults;

import com.lambdami.hellu.connection.HttpConnection;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 */

public class GoogleRequestJS {

    private String Code;

    public GoogleRequestJS(String code) {

        this.Code = code;
    }

    public String getContent() {

        delay();
        String content = "";
        String URL = "http://scholar.google.com//scholar?q=info:" + Code + ":scholar.google.com/&output=cite&hl=en&as_sdt=0,5";
        HttpConnection http = new HttpConnection(URL, "Google Scholar / JSPopUpWindow");
        http.connect();
        content = http.getContent();
        http.closeConnection();
        return content;

    }

    private void delay() {
        double count = 0.0;
        //-0.50
        while (count < 5000000000.0) {
            count++;
        }
    }

}
