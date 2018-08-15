package com.abugrov.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class UrlController {


    @MessageMapping("/getinfo")
    @SendTo("/info/urldata")
    public UrlInfo getInfo(UrlMessage message) throws Exception {
        String url = message.getUrl();
		
		long start = System.currentTimeMillis();
		String code = getUrlContents(url);
		long finish = System.currentTimeMillis();
		
		return new UrlInfo(url, code.isEmpty() ? 0 : finish - start, message.getWord().isEmpty() ? false : code.contains(message.getWord()), code.length());
    }
	
	private String getUrlContents(String theUrl) {
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(theUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
			bufferedReader.close();
		} catch(IOException e) {
		}

		return content.toString();
	}
}
