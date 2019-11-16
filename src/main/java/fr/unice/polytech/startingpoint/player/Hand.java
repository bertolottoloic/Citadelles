package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.startingpoint.board.District;

public class Hand {
    private ArrayList<District> districts=new ArrayList<>();

    Hand(){
        this.districts=new ArrayList<>();
    }
    public Hand(List<District> liste){
        this.districts=new ArrayList<>(liste);
    }

    District lowCostDistrict() {
        District lowCost=districts.get(0);
        for(District d : districts){
            if(d.getCost() < lowCost.getCost()) {
                lowCost=d;
            }
        }
        return lowCost;
    }

    /**
     * Cherche un district qui vaut plus que le threeshold
     * @return true s'il y en a
     */
    boolean highValuedDistrict(int threeshold) {
    	for(District d : districts){
    		if(d.getValue() > threeshold) {
    			return true;
    		}
    	}
    	return false;
    }

	public void addAll(ArrayList<District> withdrawMany) {
        districts.addAll(withdrawMany);
	}

	public void add(District d) {
        districts.add(d);
	}

	public void remove(District theDistrict) {
        districts.remove(theDistrict);
    }

	public List<? extends District> toList() {
		return districts;
	}

	public boolean isEmpty() {
		return districts.isEmpty();
	}

    /**
     * Compte le nombre de district dont le cout est plus eleve que le nombre de piece d'or posseder par le joueur
     * @return int : nombre de district
     */
    public int nbTooExpensivesDistricts(int golds){
        int n=0;
        for(District d : districts) {
            if (golds < d.getCost()) {
                n++;
            }
        }
        return n;
    }

	public int size() {
		return districts.size();
	}

	public District highCostDistrict(int gold) {
		District highCost=districts.get(0);
        for(District d : districts){
            if(d.getCost() > highCost.getCost()&&(d.getCost()<=gold)) {
                highCost=d;
            }
        }
        return highCost;
	}

	public int nbBadCards(int gold) {
		int count=0;
        for(District d : districts){
            if(d.getCost()+3<gold||d.getCost()>gold){
                count++;
            }
        }
        return count;
    }
    
    
    


}