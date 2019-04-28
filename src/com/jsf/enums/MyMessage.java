package com.jsf.enums;

public enum MyMessage {

		MESSAGE_1("insérer pièce"),
		MESSAGE_2("choisir boisson"),
		MESSAGE_3("boisson indisponible, faire un autre choix"),
		MESSAGE_4("récupérer votre monnaie");

	private String name="";

	private MyMessage(String name) {
		this.name = name;
	}

	public String toString(){
		return name;
	}

}
