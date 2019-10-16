package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Iterator;

public class Hand {
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
	
	void removeDistrict(District formerDis){
		myDistricts.remove(formerDis);
	}

	public void deleteDistrict(District toDelete){
		Iterator<District> it =myDistricts.iterator();

		while(it.hasNext()){
			if(toDelete.equals(it.next())){
				it.remove();
			}
		}
	}
	
}
