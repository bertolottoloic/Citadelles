package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Bishop;
import fr.unice.polytech.startingpoint.role.Role;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;

public class Player {
	private final int id;
	private Role character;
	private int gold;
	private ArrayList<District> hand;
	protected ArrayList<District> city;
	private Player nextPlayer;
	private Board board;

	protected boolean gameOver=false;

	/*Attributs qui permettront à l'IA de designer ses cibles*/
	protected Role targetToKill;
	protected Role targetToRob;
	private Player targetToDestroyDistrict;
	private District districtToDestroy;
	protected Player targetToExchangeHandWith;
	protected PropertyChangeSupport support;

	/*Attributs permettants de savoir si on a déja joué ou choisi son personnage */
	private boolean alreadyChosenRole;

	/*Attribut permettant de savoir si le joueur a posé son huitième quartier en premier */
	private boolean firstToFinish=false;

	/**
	 * 
	 * @param id
	 */
	public Player(int id){
		support = new PropertyChangeSupport(this);
		this.id = id;
		hand = new ArrayList<>();
		city = new ArrayList<>();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
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
		if(!(character instanceof Bishop)){
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
		hand.clear();
		
		hand.addAll(targetToExchangeHandWith.hand);
		targetToExchangeHandWith.hand.removeAll(targetToExchangeHandWith.hand);
		targetToExchangeHandWith.hand.addAll(tmpHand);
	}

	
	
	void surrenderToThief(){
			System.out.println("Moi le joueur "+id+" j'ai été volé");
			board.getRole(1).getPlayer().addMoney(gold);//donne l'argent au player de Thief
			gold=0;//plus d'argent apres le vol
	}

	/**
	 * Méthode de choix de Personnages
	 * pour le moment il choisit le premier Personnage
	 *  puis appelle la méthode sur nextPlayer
	 */
	public void chooseRole(){
		if(!alreadyChosenRole){
			setCharacter(board.getDealRoles().getLeftRoles().remove(0));
			alreadyChosenRole=true;
			//appelle le prochain player
			if(nextPlayer!=null){
				nextPlayer.chooseRole();
			}
			
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
			Discard(districts);
			this.hand.addAll(districts);
			System.out.println("Joueur "+id+" prend "+districts.size()+" districts. \n" +
					"Il reste "+getBoard().numberOfCardsOfDeck()+" districts dans le deck.");
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
		character=null;
		targetToDestroyDistrict=null;
		targetToExchangeHandWith=null;
		targetToKill=null;
		targetToRob=null;

		// forgot something ??
	}

	public void Discard(ArrayList<District> d){
		if(!d.isEmpty()){
			getBoard().getDeck().putbackOne(d.remove(0)); }
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
	void collectMoneyFromDistricts(){
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
	 * Contrairement a ce que son nom peut laisser croire cette méthode ne se contente
	 * pas de vérifier si le joueur a fini de construire  8 districts
	 * Elle vérifie également si le joueur est bien le premier à avoir fini
	 * avec isFirstToFinish()
	 * @return true if the player is the first to reach at least 8 districts 
	 * 
	 */
	public boolean checkFinishBuilding()
	{
		if(this.isFirstToFinish() && getSizeOfCity()>=8){
			this.firstToFinish=true;
		}
		return this.firstToFinish;
	}
	/**
	 * Cette fonction doit permettre d'initialiser l'attribut
	 * firstToFinish, attribut qui sera utilisé plus tard dans la méthode
	 * points() pour attribuer ou non les bonus
	 * Le board et des joueurs doivent etre défini avant de lancer cette fonction
	 * @return un boolean selon que le joueur est le premier a avoir fini ou pas
	 */
	public boolean isFirstToFinish(){
		ArrayList<Player> players =board.getPlayers();

		for (Player player : players) {
			if(player.firstToFinish && player.id!=this.id){
				return false;
			}
		}
		
		return true;

	}

	public boolean cityContainsAllColors(){
		HashSet<String> s=new HashSet<String>();
		city.forEach((c)->{
			s.add(c.getColor());

		});
		return s.size()==5;
	}
	/**
	 * Cette méthode permet de compter les points en fin de partie
	 * @return le nombre de points
	 */
	public int points(){
		ArrayList<District> ct=getCity();
		int points=0;
         for(District d : ct){
             points+=d.getValue();
		 }
		 if(this.firstToFinish){
			//Le premier joueur à avoir posé son quartier reçoit +4
			points+=4;
		 }
		 else if(!this.firstToFinish && getSizeOfCity()>=8){
			 // + 2 pour les autres joueurs ayant huit quartiers.
			 points+=2;
		 }

		 if(this.cityContainsAllColors()){
			 // + 3 si la cité comprend des quartiers des cinq couleurs différentes.
			 points+=3;
		 }
		 
         return points;
	}

	

	/**
	 * Pour qu'on puisse utiliser HashMap<Player,Integer>
	 */
	@Override
	public int hashCode(){
		return id;
	}

	/* ----- setters et getters ----*/

	public int getSizeOfCity(){
		return city.size();
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

	public int getGold() {
		return gold;
	}

	public ArrayList<District> getHand(){return hand;}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<District> getCity() {
		return city;
	}

	/**
	 * Pour les tests uniquement, ne devrait pas etre
	 * utilisé ailleurs
	 * @param city
	 */
	public void setCity(ArrayList<District> city) {
		this.city = city;
	}

}