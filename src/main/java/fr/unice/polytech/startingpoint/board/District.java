package fr.unice.polytech.startingpoint.board;

import java.util.ArrayList;
import java.util.Arrays;

public class District {
    private final int cost;
    private final int value;
    private final String color; 
    private final String name;
    private final ArrayList<String> colorsList = new ArrayList<>();

    public District(int cost, int value, String color, String name) {
        this.cost = cost;
        this.value = value;
        this.color = color;
        this.name = name;
        colorsList.addAll(Arrays.asList(color.split("-")));
    }

    public District(int cost, int value, DistrictColor color, String name) {
        this.cost = cost;
        this.value = value;
        this.color = color.toString();
        this.name = name;
        colorsList.add(color.toString());

    }

    

    public boolean hasColor(DistrictColor color) {
        return colorsList.stream().anyMatch(s -> s.equals(color.toString()));
    }

    public boolean hasColor(String color2) {
        return colorsList.stream().anyMatch(s -> s.equals(color2));
    }

    @Override
    public String toString() {

        return "******************\n" + "Couleur: " + color + "\n" + "Valeur: " + value + "\n" + "Cout: " + cost + "\n"
                + "******************\n";

    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof District) {
            return this.name.equals(((District) other).name) && this.color.equals(((District) other).color);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    /**
     * C'est une copie de la liste qui est renvoyée
     * 
     * @return
     */
    public ArrayList<String> getColorsList() {
        return new ArrayList<>(colorsList);
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

}