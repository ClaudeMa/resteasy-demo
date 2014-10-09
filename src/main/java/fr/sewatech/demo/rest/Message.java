package fr.sewatech.demo.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
	private Long id;
	private String text;
	
	public Message() {
	}
	public Message(Long l, String text) {
		this.id = l;
		this.text = text;
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
