package com.example.convert.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Errors {
	@XmlAttribute(name = "line")
	private int line;
	@XmlAttribute(name = "message")
	private String message;
	private String value;

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
