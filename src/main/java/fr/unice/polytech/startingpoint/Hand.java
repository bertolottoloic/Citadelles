package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
public class Hand{
	private ArrayList<District> myDistricts;
	
	Hand(){
		myDistricts = new ArrayList<>();
	}
	
	ArrayList<District> getMyDistricts(){
		return myDistricts;
	}
	
	void addDistrict(District newDis){
		
		myDistricts.add(newDis);
	}
	void addDistricts(ArrayList<District> districts){
		myDistricts.addAll(districts);
	}
	
	void removeDistrict(District formerDis){
		myDistricts.remove(formerDis);
		/*Remettre le district dans le deck
				*/
		Assets.TheDeck.putbackOne(formerDis);
	}

	
	
}
