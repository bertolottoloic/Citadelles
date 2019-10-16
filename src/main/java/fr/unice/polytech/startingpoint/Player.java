package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Player {
	private final int id;
	Role character;
	private int gold;
	private Hand hand;
	private ArrayList<District> city;
	
	Player(int id){
		this.id = id;
		gold = 2;
		hand = new Hand();
		city = new ArrayList<>();
	}
	
	public int getGold() {
		return gold;
	}

	public Hand getHand(){return hand;}
	
	public int getId() {
		return id;
	}
	
	ArrayList<District> getCity() {
		return city;
	}
	
	void pickNewDistrict(District d) {
		hand.addDistrict(d);
	}
	
	boolean addToTheCity(District theDistrict) {
		if(gold - theDistrict.getCost() >= 0) {
			gold -= theDistrict.getCost();
			city.add(theDistrict);
			hand.removeDistrict(theDistrict);
			return true;
		}
		return false;
	}
	
	
	void addMoney(int amount) {
		gold+= amount;
	}

	public void deleteDistrictFromHand(District toDelete){
		hand.deleteDistrict(toDelete);
	}
	
	@Override
	public String toString() {
		return "**********\n"
			+ "Player #" + id + "\n"
			+ "Current role: " + character +"\n"
			+ "Amount of gold: " + gold + "\n"
			+ "City :" + city +"\n";
	}
}
