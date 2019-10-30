package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.*;
import fr.unice.polytech.startingpoint.game.Assets;
import fr.unice.polytech.startingpoint.role.*;

import java.util.ArrayList;

public class Player {
	private final int id;
	private Role character;
	private int gold;
	private Hand hand;
	private ArrayList<District> city;
	private Player nextPlayer;
	private Board board;

	/*Attributs qui permettront à l'IA de designer ses cibles*/
	protected Role targetToKill;
	protected Role targetToRob;
	private Player targetToDestroyDistrict;
	private District districtToDestroy;
	protected Player targetToExchangeHandWith;

	/*Attributs permettants de savoir si on a déja joué ou choisi son personnage */
	private boolean alreadyChosenRole;
	private boolean alreadyPlayedTurn;

	/**
	 * 
	 * @param id
	 */
	public Player(int id){
		this.id = id;
		hand = new Hand();
		city = new ArrayList<>();
	}
	
	
	public void takeCardsAtBeginning(){
		hand.addAll(board.withdrawMany(4));//constante à retirer
	}
	/**
	 * En tout début de partie chacun prend deux pièces avant 
	 * même de prendre son personnage
	 * D'où cette méthode puisque si on appelle
	 * takeCoinsFromBank avant d'avoir les personnages on 
	 * obtient NullPointerException
	 */
	public void takeCoinsAtBeginning(){
		getBoard().withdraw(2);
		System.out.println("Joueur "+id+" retire "+2+" de la banque. ");
		gold+=2;
	}
	public void pickNewDistrict(District d) {
		hand.add(d);
	}
	
	public boolean addToTheCity(District theDistrict) {
		for(District aDis: city){
			if(theDistrict.equals(aDis)){
				return false;
			}
		}
		if(gold - theDistrict.getCost() >= 0) {
			gold -= theDistrict.getCost();
			getBoard().deposit(theDistrict.getCost());
			city.add(theDistrict);
			hand.remove(theDistrict);
			System.out.println("Joueur "+id+" construit \n"+theDistrict.toString());
			return true;
		}
		return false;
	}
	
	
	public void addMoney(int amount) {
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
		int pickGold = board.withdraw(nb);
		if(pickGold>0) {
			System.out.println("Joueur "+id+" retire " + nb + " de la banque. ");
			gold+=nb;
		}
	}

	public void exchangeHands() {
		ArrayList<District> tmpHand = new ArrayList<>();
		tmpHand.addAll(hand);
		hand.removeAll(hand);
		
		hand.addAll(targetToExchangeHandWith.hand);
		targetToExchangeHandWith.hand.removeAll(targetToExchangeHandWith.hand);
		targetToExchangeHandWith.hand.addAll(tmpHand);
	}

	
	
	private void surrenderToThief(){
			System.out.println("Moi le joueur "+id+" j'ai été volé");
			Assets.TheThief.getPlayer().addMoney(gold);//donne l'argent au player de Thief
			gold=0;//plus d'argent apres le vol
	}

	/**
	 * Méthode de choix de Personnages
	 * pour le moment il choisit le premier Personnage
	 *  puis appelle la méthode sur nextPlayer
	 */
	public void chooseRole(){
		if(!alreadyChosenRole){
			setCharacter(Assets.leftRoles.remove(0));
			alreadyChosenRole=true;
			//appele le prochain player
			nextPlayer.chooseRole();
		}
		
    }

    public boolean coinsOrDistrict(){
		return true;
	}

	/**
	 * check if murdered
	 * check if stolen
	 * pick money from Bank or pick card from Deck
	 * collect rentMoney
	 * use special power of Role if any
	 * 
	 * 
	 * 
	 * */
	public void playTurn(){
		if(character.isMurdered()){
			System.out.println("Joueur "+id+" passe son tour car il a été tué");
			return; //on sort de la fonction sans plus rien faire
		}
		else if(character.isStolen()){
			this.surrenderToThief();
			
		}

		this.collectMoneyFromDistricts();

		if(coinsOrDistrict()){//on prend au hasard
			//après c'est l'IA qui doit prendre la décision
			
				this.takeCoinsFromBank(this.character.getNumberGold());
		}
		else{
			
			ArrayList<District> districts=getBoard().withdrawMany(this.character.getNumberDistrictPickable());
			this.hand.addAll(districts);
			System.out.println("Joueur "+id+" prend "+this.character.getNumberDistrictPickable()+" districts");
		}

		if(isBuildingFirst()) {
			action();
			specialMove();
		}
		else{
			specialMove();
			action();
		}
		
	}

	protected boolean isBuildingFirst() {
		return true;
	}

	/**
	 * Méthode pour remettre au default les valeurs 
	 * changées par le tour qui vient d'être joué
	 * Il peut y avoir une méthode pareille pour une 
	 * partie complète 
	 *
	 */
	public void reInitializeForNextTurn(){
		alreadyChosenRole=false;
		alreadyPlayedTurn=false;
		character=null;
		targetToDestroyDistrict=null;
		targetToExchangeHandWith=null;
		targetToKill=null;
		targetToRob=null;

		// forgot something ??
	}

	protected void action() {

	}

	public void specialMove() {
		System.out.println("Joueur "+id+" active son effet de rôle");
		character.useSpecialPower();
	}


	/**
	 * Méthode pour collecter l'argent des districts
	 */
	private void collectMoneyFromDistricts(){
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

	

	/**
	 * Pour qu'on puisse utiliser HashMap<Player,Integer>
	 */
	@Override
	public int hashCode(){
		return id;
	}

	/* ----- setters et getters ----*/

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

	public Board getBoard() {
		return board;
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

	public void setBoard(Board board) {
		this.board = board;
	}

	int getGold() {
		return gold;
	}

	public Hand getHand(){return hand;}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<District> getCity() {
		return city;
	}

}