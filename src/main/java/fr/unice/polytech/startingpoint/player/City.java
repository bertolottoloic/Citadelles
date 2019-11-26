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
    private int presentDate=0;
    
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

	public boolean removeDistrict(District toDelete) {
        toDelete.resetBuildDate();
        return districts.remove(toDelete);
    }
    
    
    public boolean containsAllColors(){
        HashSet<String> s=new HashSet<String>();
		districts.forEach((c)->{
            if(c.getName().equals("Cour des Miracles")){
                s.add(DistrictColor.Wonder.toString());
            }
            else{
                s.addAll(c.getColorsList());
            }
			

		});
		return s.size()==5;
    }
    
    public int getSizeOfCity(){
		return districts.size();
    }
    /**
     * La valeur de la City  avec les potentiels bonus
     * @return
     */
    public int totalValue(){
        if(this.containsAllColors()){
            return 3+this.netValue();
        }
        else{
            return this.netValue();
        }
    }
    /**
     * La valeur nette de la City sans le bonus 
     * utiliser totalValue() pour les potentiels bonus
     */
    public int netValue(){
        return districts.stream().mapToInt(d -> d.getValue()).sum();
    }

    public void add(District theDistrict) {
        if(theDistrict!=null && !alreadyContains(theDistrict)){
            districts.add(theDistrict);
            theDistrict.setBuildDate(this.presentDate);
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
    
    public void nextDay(){
        this.presentDate+=1;
    }
    

}