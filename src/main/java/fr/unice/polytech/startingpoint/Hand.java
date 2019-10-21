package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
public class Hand extends ArrayList<District>{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 5231238297256256884L;

	Hand() {
	}
	
	/**
	 * Le district est retiré de la main et retourne dans le deck
	 * 
	 * @param District en question
	 */
	void remove(District formerDis){
		this.remove(formerDis);
		/*Remettre le district dans le deck
				*/
		Assets.TheDeck.putbackOne(formerDis);
	}

	
	
}
