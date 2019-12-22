package com.example.convert.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Reference {
	@XmlAttribute(name = "color")
	private String color;
	@XmlAttribute(name = "price")
	private float price;
	@XmlAttribute(name = "size")
	private int size;
	@XmlAttribute(name = "numReference")
	private long numReference;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = Float.parseFloat(price);
	}

	public int getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = Integer.parseInt(size);
		;
	}

	public long getNumReference() {
		return numReference;
	}

	public void setNumReference(String numReference) {
		this.numReference = Long.parseLong(numReference);
		;
	}
}
