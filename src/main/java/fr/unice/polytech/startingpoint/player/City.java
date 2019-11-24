package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;

public class City {
    private ArrayList<District> districts=new ArrayList<>();
    
    public City(){
        
    }

    /**
     * Cette méthode renverra la couleur qui permet
     * de rapporter le plus d'argent dans la ville
     * actuelle
     * On renverra null dans le cas où il n'y a pas
     * de couleur payante
     * @return la couleur la plus rentable
     */
    public DistrictColor mostPotentiallyPayingColor(){
        if(districts.isEmpty()){
            return null;
        }
        else{
            
            return List.of(DistrictColor.Warlord,DistrictColor.Commerce,DistrictColor.Noble,DistrictColor.Religion).stream().max((a,b)->
                Long.compare(nbOcurrencesOf(a) , nbOcurrencesOf(b))
            ).get();

        }

    }

    public String mostPotentiallyPayingColor(List<String> availableColors){
        if(districts.isEmpty() || availableColors.isEmpty()){
            return null;
        }
        else{
            //les couleurs payantes sont : soldatesque,noblesse,commerce,religion
            return availableColors.stream().max((a,b)->
                Long.compare(nbOcurrencesOf(a) , nbOcurrencesOf(b))
            ).get();

        }

    }

    public int nbOcurrencesOf(DistrictColor color) {
       return (int)districts.stream().filter(d -> d.hasColor(color)).count();
    }
    public int nbOcurrencesOf(String color) {
        return (int)districts.stream().filter(d -> d.hasColor(color)).count();
     }

	public void removeDistrict(District toDelete) {
        districts.remove(toDelete);
    }
    
    public boolean containsAllColors(){
        HashSet<String> s=new HashSet<String>();
		districts.forEach((c)->{
			s.addAll(c.getColorsList());

		});
		return s.size()==5;
    }
    
    public int getSizeOfCity(){
		return districts.size();
    }
    
    public int getTotalValue(){
        return districts.stream().mapToInt(d -> d.getValue()).sum();
    }

	public void add(District theDistrict) {
        if(theDistrict!=null && !alreadyContains(theDistrict)){
            districts.add(theDistrict);
        }
    }
    
    public boolean alreadyContains(District aDistrict) {
        return districts.contains(aDistrict);
    }
    
    public ArrayList<District> getListDistricts(){
        return districts;
    }

	public boolean isEmpty() {
		return districts.isEmpty();
    }
    @Override
    public String toString() {
        return districts.toString();
    }

	public boolean containsWonder(String wonder) {
		return districts.stream().anyMatch(d -> d.getName().equals(wonder));
	}

	public Collection<District> toList() {
		return districts;
	}

    public Optional<District> cheaperDistrict(){
		return districts.stream().min(
                (a,b)->Integer.compare(a.getCost(), b.getCost())
            );
	}
    

}