package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Player {
	private final int id;
	private Role character;
	private int gold;
	private Hand hand;
	private ArrayList<District> city;
	Player nextPlayer;
	Bot brain;

	/*Attributs qui permettront à l'IA de designer ses cibles*/
	private Role targetToKill;
	private Role targetToRob;
	private Player targetToDestroyDistrict;
	private District districtToDestroy;
	private Player targetToExchangeHandWith;

	Player(int id,Bot brain){
		this.brain=brain;
		this.id = id;
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
	
	void takeCardsAtBeginning(){
		hand.addAll(Assets.TheDeck.withdrawMany(4));//constante à retirer
	}
	void pickNewDistrict(District d) {
		hand.add(d);
	}
	
	boolean addToTheCity(District theDistrict) {
		if(gold - theDistrict.getCost() >= 0) {
			gold -= theDistrict.getCost();
			city.add(theDistrict);
			hand.remove(theDistrict);
			return true;
		}
		return false;
	}
	
	
	void addMoney(int amount) {
		gold+= amount;
	}

	/**
	 * Destruction de district dans city
	 * @param toDelete
	 */
	public void deleteDistrictFromCity(District toDelete){
		if(!character.equals(Assets.TheBishop)){
			city.remove(toDelete);
		}
			
	}
	
	/**
	 * Avant d'appeler cette méthode il faut appeler 
	 * Assets.TheBank.canWithdraw(nb) et vérifier la valeur retournée
	 * par celle ci cela permet de savoir si l'argent voulue est disponible
	 */
	public void takeCoinsFromBank(int nb){
		Assets.TheBank.withdraw(nb);
		System.out.println("Je retire "+character.getNumberGold()+" de la banque. ");
		gold+=character.getNumberGold();
	}

	public void exchangeHands() {
		ArrayList<District> tmpHand = new ArrayList<>();
		tmpHand.addAll(hand);
		hand.removeAll(hand);
		
		hand.addAll(targetToExchangeHandWith.hand);
		targetToExchangeHandWith.hand.removeAll(targetToExchangeHandWith.hand);
		targetToExchangeHandWith.hand.addAll(tmpHand);
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

	
	void surrenderToThief(){
			System.out.println("Moi le joueur "+id+" j'ai été volé");
			Assets.TheThief.getPlayer().addMoney(gold);//donne l'argent au player de Thief
			gold=0;//plus d'argent apres le vol
	}
	

	public void playTurn(Board board){
		//check if murdered
		//check if stolen
		//pick money from Bank or pick card from Deck
		//collect rentMoney
		//use special power of Role if any

		if(character.isMurdered()){
			System.out.println("Moi le joueur "+id+" je passe mon tour parce que j'ai été tué");
			return; //on sort de la fonction sans plus rien faire
		}
		else if(character.isStolen()){
			this.surrenderToThief();
			
		}

		this.collectMoneyFromDistricts();

		double f=Math.random();
		System.out.println(f);
		if(brain.whatToPick()==0){//on prend au hasard
			//après c'est l'IA qui doit prendre la décision
			
				this.takeCoinsFromBank(character.getNumberGold());	
		}
		else{
			
			ArrayList<District> districts=Assets.TheDeck.withdrawMany(this.character.getNumberDistrictPickable());
			this.hand.addAll(districts);
			System.out.println("Je prend "+this.character.getNumberDistrictPickable()+" districts");
		}

		if(brain.whatToDoFirst()==0){
			specialMove();
			action();

		}else{
			action();
			specialMove();
		}

		
		
	}

	private void action() {
		District d=brain.whatToBuild(hand,gold);
		if(d!=null){
			addToTheCity(d);
		}
	}

	public void specialMove() {

		switch (character.toString()) {
			case "Murderer":
				targetToKill=brain.whoToKill();
				break;

			case "Thief":
				targetToRob=brain.whoToSteal();
				break;
			case "Warlord":
				targetToDestroyDistrict=brain.whoseCityToDestroy();
				break;
			
			case "Wizard":
				//
				
		
			default:
				break;

			character.useSpecialPower();
		}

		character.useSpecialPower();
	}

	/**
	 * Méthodes pour collecter l'argent des districts
	 */
	public void collectMoneyFromDistricts(){
		character.collectRentMoney();
	}
	
	@Override
	public String toString() {
		return "**********\n"
			+ "Player #" + id + "\n"
			+ "Current role: " + character +"\n"
			+ "Amount of gold: " + gold + "\n"
			+ "City :" + city +"\n";
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public void setHand(ArrayList<District> liste) {
		this.hand.clear();
		this.hand.addAll(liste);
	}

	/**
	 * Pour qu'on puisse utiliser HashMap<Player,Integer>
	 */
	@Override
	public int hashCode(){
		return id;
	}
}