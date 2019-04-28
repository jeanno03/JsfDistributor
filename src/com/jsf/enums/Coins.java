package com.jsf.enums;

public enum Coins {

	UN_CENTIME(0.01f),
	DEUX_CENTIMES(0.02f),
	CINQ_CENTIMES(0.05f),
	DIX_CENTIMES(0.10f),
	VINGT_CENTIMES(0.20f),
	CINQUANTE_CENTIMES(0.50f),
	UN_EUROS(1.0f),
	DEUX_EUROS(2.0f);	

	private float valeur;

	private Coins(float valeur) {
		this.valeur = valeur;
	}

	public float getValeur() {
		return valeur;
	}

}
