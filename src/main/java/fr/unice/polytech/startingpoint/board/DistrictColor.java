package fr.unice.polytech.startingpoint.board;

public enum DistrictColor {
    Noble("noblesse"),
    Wonder("merveille"),
    Warlord("soldatesque"),
    Religion("religion"),
    Commerce("commerce");

    private String color;

    DistrictColor(String color){
        this.color=color;
    }

    

    @Override
    public String toString() {
        return color;
    }
}