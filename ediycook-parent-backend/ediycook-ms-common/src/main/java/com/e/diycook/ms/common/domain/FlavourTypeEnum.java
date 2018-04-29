package com.e.diycook.ms.common.domain;

public enum FlavourTypeEnum {
	/**
	 * key:A
	 */
	ANANAS,
	/**
	 * key:B
	 */
	/**
	 * key:C
	 */
	CITRON,
	
	/**
	 * key:END
	 */
	UNKNOWN;
	
	private FlavourTypeEnum(){
		
	}
	
	  public static FlavourTypeEnum fromString(String text) {
		    for (FlavourTypeEnum b : FlavourTypeEnum.values()) {
		      if (b.name().equalsIgnoreCase(text)) {
		        return b;
		      }
		    }
		    return null;
		  }
}
