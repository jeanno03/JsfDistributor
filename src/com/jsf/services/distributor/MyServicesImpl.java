package com.jsf.services.distributor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jsf.enums.Coins;
import com.jsf.tools.FloatConverterInterfaceImpl;

public class MyServicesImpl implements MyServices, FloatConverterInterfaceImpl{

	private List<Coins> coinsList ;

	public MyServicesImpl() {
		super();
		coinsList = new ArrayList();
	}

	@Override
	public List<Coins> getMonnaie(float amount) {
		
		coinsList = new ArrayList();
		
		float monnaieRestantApres2Euros = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterTwoEuros(amount));
		float monnaieRestantApres1Euro = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres2Euros, Coins.UN_EUROS));	
		float monnaieRestantApres50Cents = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres1Euro, Coins.CINQUANTE_CENTIMES));	
		float monnaieRestantApres20Cents = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres50Cents, Coins.VINGT_CENTIMES));	
		float monnaieRestantApres10Cents = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres20Cents, Coins.DIX_CENTIMES));	
		float monnaieRestantApres5Cents = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres10Cents, Coins.CINQ_CENTIMES));	
		float monnaieRestantApres2Cents = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres5Cents, Coins.DEUX_CENTIMES));	
		float monnaieRestantApres1Cents = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(monnaieAfterGeneriqueCents(monnaieRestantApres2Cents, Coins.UN_CENTIME));
		
		System.out.println("il reste : " + monnaieRestantApres1Cents);
		
		return coinsList;
	}

	private float monnaieAfterTwoEuros(float restant) {

		float retour=0f;

		//avant tout je rend que des pieces de 2€
		float coinsOfToToGiveBackFloat = restant/2f;

		if(coinsOfToToGiveBackFloat==1f) {
			//on rend 1 piece de 2 et on arrete
			coinsList.add(Coins.DEUX_EUROS);
		}
		else if(coinsOfToToGiveBackFloat>=1) {

			int coinsOfToToGiveBackInteger = (int) (restant/2);

			System.out.println("on rend en piece de 2€ coinsOfToToGiveBackInteger : " + coinsOfToToGiveBackInteger);

			for(int i=0;i<coinsOfToToGiveBackInteger;i++) {
				System.out.println(i+")on rend en piece de 2€");
				coinsList.add(Coins.DEUX_EUROS);
			}

			//on regarde si le result de %2 est != 0 si ==0 on arrete sinon on continue			
			float testModul2 = coinsOfToToGiveBackFloat%2;			
			if(testModul2 ==1 || testModul2 ==0) {
				System.out.println("testModul2 : " + testModul2);
				System.out.println("le result de %2 est == 1 || == 0 on arrete");
			}
			else {
				System.out.println("testModul2 : " + testModul2 + " -  on continue ");
				float monnaieRestantApresRenduDe2 = coinsOfToToGiveBackFloat - coinsOfToToGiveBackInteger;
				retour =  monnaieRestantApresRenduDe2 * 2;
			}

		}else {
			//moin de 2euros
			retour = restant;
		}
		retour = FloatConverterInterfaceImpl.getFloatWithTwoDecimal(retour);
		System.out.println("retour : " + retour);
		return retour;
	}
	
	private float monnaieAfterGeneriqueCents(float restant, Coins coins) {
		float retour=0f;
		
		//on cherche coinValue
		float coinsToToGiveBackFloat = restant/coins.getValeur();
		if(coinsToToGiveBackFloat==1) {
			System.out.println("On rend " + coins.getValeur() + " - puis on arrete coinsToToGiveBackFloat : " + coinsToToGiveBackFloat);
			coinsList.add(coins);
		}
		else if(coinsToToGiveBackFloat>1) {
			int coinsToToGiveBackInteger = (int) (restant/coins.getValeur());
			retour =  FloatConverterInterfaceImpl.getFloatWithTwoDecimal((coinsToToGiveBackFloat- coinsToToGiveBackInteger)*coins.getValeur());
			System.out.println("On rend " + coins.getValeur() + " - puis il reste retour : " + retour);	
			
			for(int i=0;i<coinsToToGiveBackInteger;i++) {
				System.out.println(i+")on rend en piece de " + coins.getValeur());
				coinsList.add(coins);
			}

		}else {
			retour = restant;
		}
		return FloatConverterInterfaceImpl.getFloatWithTwoDecimal(retour);
	}
	


}