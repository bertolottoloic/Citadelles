package fr.unice.polytech.startingpoint;

class District{
    private final int cost;
    private final int value;
    private String color; /* color aussi pourrait etre final dépend de si on implémente la carte
    "Cour des Miracles" ou de la facon dont on l'implémente*/

    District(int cost,int value,String color){
        this.cost=cost;
        this.value=value;
        this.color=color;

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
}