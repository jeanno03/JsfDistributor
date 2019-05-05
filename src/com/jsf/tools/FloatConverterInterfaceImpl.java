package com.jsf.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface FloatConverterInterfaceImpl {
	
	static float getFloatWithTwoDecimal(float entry) {

		        BigDecimal bd = new BigDecimal(entry).setScale(2, RoundingMode.HALF_UP);
		        float result = (float) bd.doubleValue();
				return result;
				
	}

}
