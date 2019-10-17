package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Player {
	private final int id;
	private Role character;
	private int gold;
	private Hand hand;
	private ArrayList<District> city;

	/*Attributs qui permettront à l'IA de désigner ses cibles*/
	private Role targetToKill;
	private Role targetToRob;
	private Player targetToDestroyDistrict;
	private District districtToDestroy;
	private Player targetToExchangeHandWith;
	
	
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
		if(character.equals(Assets.TheBishop))
		hand.removeDistrict(toDelete);
	}
	
	@Override
	public String toString() {
		return "**********\n"
			+ "Player #" + id + "\n"
			+ "Current role: " + character +"\n"
			+ "Amount of gold: " + gold + "\n"
			+ "City :" + city +"\n";
	}

	public Role getTargetToKill() {
		return targetToKill;
	}

	public void setTargetToKill(Role targetToKill) {
		this.targetToKill = targetToKill;
	}

	public Role getTargetToRob() {
		return targetToRob;
	}

	public void setTargetToRob(Role targetToRob) {
		this.targetToRob = targetToRob;
	}

	public Player getTargetToDestroyDistrict() {
		return targetToDestroyDistrict;
	}

	public void setTargetToDestroyDistrict(Player targetToDestroyDistrict) {
		this.targetToDestroyDistrict = targetToDestroyDistrict;
	}

	public District getDistrictToDestroy() {
		return districtToDestroy;
	}

	public void setDistrictToDestroy(District districtToDestroy) {
		this.districtToDestroy = districtToDestroy;
	}

	public Player getTargetToExchangeHandWith() {
		return targetToExchangeHandWith;
	}

	public void setTargetToExchangeHandWith(Player targetToExchangeHandWith) {
		this.targetToExchangeHandWith = targetToExchangeHandWith;
	}

	public Role getCharacter() {
		return character;
	}

	public void setCharacter(Role character) {
		this.character = character;
		character.setPlayer(this);
	}
}
