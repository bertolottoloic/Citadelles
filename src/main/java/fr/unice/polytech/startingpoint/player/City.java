package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import fr.unice.polytech.startingpoint.board.District;

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
    public String mostPotentiallyPayingColor(){
        if(districts.isEmpty()){
            return null;
        }
        else{
            
            return List.of("soldatesque","commerce","noblesse","religion").stream().max((a,b)->
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

    public int nbOcurrencesOf(String color) {
       return (int)districts.stream().filter(d -> d.getColor().equals(color)).count();
    }

	public void removeDistrict(District toDelete) {
        districts.remove(toDelete);
    }
    
    public boolean containsAllColors(){
		HashSet<String> s=new HashSet<String>();
		districts.forEach((c)->{
			s.add(c.getColor());

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

}