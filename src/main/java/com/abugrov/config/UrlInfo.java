package com.abugrov.config;

public class UrlInfo {

	private String url;
	private long time;
	private boolean foundWord;
	private int codeLength;
	
	public UrlInfo(String url, long time, boolean foundWord, int codeLength) {
		this.url = url;
		this.time = time;
		this.codeLength = codeLength;
		this.foundWord = foundWord;
	}
	

	public UrlInfo() {
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public boolean isFoundWord() {
		return foundWord;
	}

	public void setFoundWord(boolean foundWord) {
		this.foundWord = foundWord;
	}
}
