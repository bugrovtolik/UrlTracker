package com.abugrov.configtest;

import com.abugrov.config.UrlController;
import com.abugrov.config.UrlInfo;
import com.abugrov.config.UrlMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlControllerTest extends Mockito {


    private void getInfo(String url) throws Exception {
        UrlMessage urlMessage = mock(UrlMessage.class);

        when(urlMessage.getUrl()).thenReturn(url);
        when(urlMessage.getWord()).thenReturn("Google");

        UrlController urlController = new UrlController();
        UrlInfo urlInfo = urlController.getInfo(urlMessage);

        assertEquals(url, urlInfo.getUrl());
        assertTrue(urlInfo.getTime() != 0);
        assertTrue(urlInfo.getCodeLength() != 0);
    }

    @Test
    void manyUrls() throws Exception {
        String[] urls = {"https://junit.org/junit5/", "https://en.wikipedia.org/wiki/Wikipedia",
                         "https://www.youtube.com/", "https://www.facebook.com/", "https://www.nytimes.com/",
                         "https://habr.com/", "https://twitter.com/", "https://stackoverflow.com/"};

        for (int i = 0; i < 20; i++) {
            for (String url : urls) {
                getInfo(url);
            }
        }
    }
}