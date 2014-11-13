package com.epam.model;

public class Message {

	private String mailFrom;
	private String subject;
	private String text;

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Message [mailFrom=" + mailFrom + ", subject=" + subject
				+ ", text=" + text + "]";
	}	
}
