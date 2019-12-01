package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Hand {
    private ArrayList<District> districts=new ArrayList<>();

    Hand(){
        this.districts=new ArrayList<>();
    }
    public Hand(List<District> liste){
        this.districts=new ArrayList<>(liste);
    }

	District lowCostDistrict() {
		if (districts.isEmpty()) {
			return null;
		} else {
			/*District lowCost = districts.get(0);
			for (District d : districts) {
				if (d.getCost() < lowCost.getCost()) {
					lowCost = d;
				}
            }*/
            return districts.stream().min((a,b)->Integer.compare(a.getCost(),b.getCost())).get();
		}
	}

    District lowCostDistrictForNextTurn(int golds) {
        District ld= lowCostDistrict();
        District optimized=ld;
        for(District d : districts){
            if(d.getCost()+ld.getCost()<=golds+2&&(!d.equals(ld))&&d.getCost()>optimized.getCost()){
                optimized=d;
            }
        }
        return optimized;
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

	public void addAll(List<District> list) {
        districts.addAll(list);
	}

	public void add(District d) {
        districts.add(d);
	}

	public boolean remove(District theDistrict) {
        return districts.remove(theDistrict);
    }

	public List<District> toList() {
		return districts;
	}

	public boolean isEmpty() {
		return districts.isEmpty();
	}

    /**
     * Compte le nombre de district dont le cout est plus eleve que le nombre de piece d'or posseder par le joueur
     * @return int : nombre de district
     */
    public int nbTooExpensiveDistricts(int gold){
        int n=0;
        for(District d : districts) {
            if (gold < d.getCost()) {
                n++;
            }
        }
        return n;
    }

	public int size() {
		return districts.size();
	}

    public int nbCardsCostingLessThan(int limit){
        return (int)districts.stream().filter(d->d.getCost()<=limit).count();
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

	public ArrayList<District> badCards(int gold) {
		ArrayList<District> badCards = new ArrayList<>();
        districts.forEach(d -> {
            if(d.getCost()+3<gold||d.getCost()>gold){
                badCards.add(d);
            }
        });
        return badCards;
    }
    

    
    public ArrayList<District> cardsAboveGold(int gold){
    	ArrayList<District> res = new ArrayList<>();
    	districts.forEach(d -> {
    		if(d.getCost() - gold == 1) res.add(d);
    	});
    	return res;
    }

    public HashMap<DistrictColor,Integer> countColors(){
        HashMap<DistrictColor,Integer> countColors=new HashMap<>();
        countColors.put(DistrictColor.Religion,0);
        countColors.put(DistrictColor.Commerce,0);
        countColors.put(DistrictColor.Warlord,0);
        countColors.put(DistrictColor.Noble,0);
        countColors.put(DistrictColor.Wonder,0);
        for (DistrictColor color :countColors.keySet()){
            districts.forEach(d->{
                if(d.hasColor(color)){
                    countColors.computeIfPresent(color,(k,v)->v+1);
                }
            });
        }
        return countColors;
    }

    public String bestColorDistrict(){
        HashMap<DistrictColor,Integer> countColors=countColors();
        Entry<DistrictColor,Integer> resultat= Collections.max(countColors.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue());

        if(resultat.getValue()<=0){
            return DistrictColor.Commerce.toString();
        }
        else{
            return resultat.getKey().toString();
        }
    }
    
    public List<District> discardDistrictsForMultiColors(){//TODO Test
    	List<District> res = new ArrayList<>();
    	HashMap<DistrictColor,Integer> countColors = countColors();
    	List<DistrictColor> severalDistrictsColor = new ArrayList<>();
    	countColors.forEach((k, v) -> {
    		if(v > 1) {
    			severalDistrictsColor.add(k);
    		}
    	});
    	districts.forEach(d -> {
    		if(severalDistrictsColor.contains(d.primaryColor())) {
    			res.add(d);
    		};
    	});
    	res.sort((a,b) -> a.getValue() - b.getValue());
    	return res;
    }
}