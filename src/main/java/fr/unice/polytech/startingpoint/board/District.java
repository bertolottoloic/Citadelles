package fr.unice.polytech.startingpoint.board;

public class District{
    private final int cost;
    private final int value;
    private String color; /* color aussi pourrait etre final dépend de si on implémente la carte
    "Cour des Miracles" ou de la facon dont on l'implémente*/
    private final String nom;
    

    
    public District(int cost, int value, String color, String nom){
        this.cost=cost;
        this.value=value;
        this.color=color;
        this.nom=nom;

    }

    public int getCost() {
        return cost;
    }


    public int getValue() {
        return value;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString() {
        
        return "******************\n"+
                "Couleur: "+color+"\n"+
                "Valeur: "+value+"\n"+
                "Cout: "+cost+"\n"+
                "******************\n";

    }


    public boolean equals(District other) {
            return  this.nom.equals(other.nom) && this.color.equals(other.color);
    }

    public String getNom() {
        return nom;
    }
}