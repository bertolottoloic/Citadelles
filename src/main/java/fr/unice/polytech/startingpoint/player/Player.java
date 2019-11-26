package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Role;
import fr.unice.polytech.startingpoint.board.Bank;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Player {
	private final int id;
	private Role character;
	@Deprecated
	private int gold;
	protected Hand hand;
	protected City city;
	protected Player nextPlayer;
	protected Board board;

	protected boolean gameOver=false;

	/*Attributs qui permettront à l'IA de designer ses cibles*/
	protected Role targetToKill;
	protected Role targetToRob;
	protected Player targetToDestroyDistrict;
	protected District districtToDestroy;
	protected District destroyedDistrict;
	private ArrayList<District> cardsToExchange;
	protected Player targetToExchangeHandWith;
	protected PropertyChangeSupport support;
	protected ArrayList<Role> knownRole = new ArrayList<Role>();
	protected ArrayList<Role> unknownRole = new ArrayList<Role>();
	protected Role hidden;

	/*Attributs permettants de savoir si on a déja joué ou choisi son personnage */
	protected boolean alreadyChosenRole;

	/*Attribut permettant de savoir si le joueur a posé son huitième quartier en premier */
	private boolean firstToFinish=false;

	/*Attribut permettant d'attribuer des probabilités de possession de personnage*/
	protected MatchingProb matches;
	private boolean usingFabricPower=false;
	protected Bank bank;
	protected Deck deck;
	protected DealRoles dealRoles;
	

	/**
	 * 
	 * @param id
	 */
	public Player(int id){
		support = new PropertyChangeSupport(this);
		this.id = id;
		hand = new Hand();
		city = new City();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
	
	public void takeCardsAtBeginning(){
		hand.addAll(deck.withdrawMany(4));//constante à retirer
	}
	
	public void pickNewDistrict(District d) {
		hand.add(d);
	}
	
	public boolean addToTheCity(District theDistrict) {
		if(city.alreadyContains(theDistrict)){
			return false;
		}
		if(bank.deposit(theDistrict.getCost(),this)) {
			city.add(theDistrict);
			hand.remove(theDistrict);
			System.out.println("Joueur "+id+" construit \n"+theDistrict.toString());
			return true;
		}
		return false;
	}
	
	@Deprecated
	public void addMoney(int amount) {
		gold+= amount;
	}

	public int getGold(){
		return bank.getPlayerMoney(this);
	}

	/**
	 * Destruction de district dans city
	 * @param toDelete
	 */
	public void deleteDistrictFromCity(District toDelete){
		if(character!=null){
			if(!character.toString().equals("Bishop") && !toDelete.getName().equals("Donjon")){
				destroyedDistrict = toDelete;
				city.removeDistrict(toDelete);
			}
		}
		else{
			if(!toDelete.getName().equals("Donjon")){
				destroyedDistrict = toDelete;
				city.removeDistrict(toDelete);
			}
		}
	}
	
	/**
	 * Avant d'appeler cette méthode il faut appeler 
	 * Assets.TheBank.canWithdraw(nb) et vérifier la valeur retournée
	 * par celle ci cela permet de savoir si l'argent voulue est disponible
	 */
	public void takeCoinsFromBank(int nb){
		if(bank.withdraw(nb,this)){
			System.out.println("Joueur "+id+" retire " + nb + " de la banque. ");
		}
	}

	public void exchangeHands() {
		Hand tmpHand=this.hand;
		
		this.hand=targetToExchangeHandWith.hand;

		targetToExchangeHandWith.hand=tmpHand;
		
	}

	
	
	void surrenderToThief(){
			System.out.println("Moi le joueur "+id+" j'ai été volé");
			Player thief=dealRoles.getRole("Thief").getPlayer();
			if(thief!=null){
				bank.transferFromTo(this, thief);//donne l'argent au player de Thief
			}
	}

	/**
	 * Méthode de choix de Personnages
	 * pour le moment il choisit le premier Personnage
	 *  puis appelle la méthode sur nextPlayer
	 */
	public void chooseRole(){
		if(!alreadyChosenRole){
			setCharacter(dealRoles.getLeftRoles().remove(0));
			alreadyChosenRole=true;
			//appelle le prochain player
			if(nextPlayer!=null){
				nextPlayer.chooseRole();
			}
			
		}
    }

	@Deprecated
    public void roleInformations(){
		knownRole.addAll(this.dealRoles.getLeftRoles());
		knownRole.addAll(this.dealRoles.getVisible());
		unknownRole.addAll(this.dealRoles.getRoles());
		unknownRole.removeAll(knownRole);
		unknownRole.remove(character);
		if(unknownRole.size()==1) hidden = unknownRole.remove(0);
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
		isUsingLabo();
		isUsingGraveyard();
		
		boolean buildFirst = isBuildingFirst();
		if(coinsOrDistrict()){//on prend au hasard
			//après c'est l'IA qui doit prendre la décision
			
				this.takeCoinsFromBank(this.character.getNumberGold());
		}
		else{
			ArrayList<District> districts=this.deck.withdrawMany(this.character.getNumberDistrictPickable());
			discard(districts);
			hand.addAll(districts);
			System.out.println("Joueur "+id+" prend "+districts.size()+" districts. \n" +
					"Il reste "+this.deck.numberOfCards()+" districts dans le deck.");
		}
	
		if(buildFirst) {
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
	 * Cette fonction permet au player de décider si il 
	 * veut utiliser le pouvoir de la manufacture ou pas
	 * cette fonction renvoie un boolean utilisé par 
	 * Role pour déterminer le  nombre que renverra les 
	 * méthodes getNumberDistrictPickable et getNumberDistrictKeepable
	 * 
	 * @return 
	 */
	
	public boolean isUsingFabric() {
		if(!this.usingFabricPower){
			boolean resultat=getCity().containsWonder("Manufacture")
				&& gold >= 3; 
			if(resultat){
				this.gold -= 3;
				this.bank.deposit(3,this);
				this.usingFabricPower=true;
			}
			
		}
		return this.usingFabricPower;
	}
	
	protected void isUsingLabo() {
	}
	
	protected void isUsingGraveyard() {
	}
	/**
	 * Méthode pour remettre au default les valeurs 
	 * changées par le tour qui vient d'être joué
	 * Il peut y avoir une méthode pareille pour une 
	 * partie complète 
	 *
	 */
	public void reInitializeForNextTurn(){
		usingFabricPower=false;
		alreadyChosenRole=false;
		character=null;
		targetToDestroyDistrict=null;
		targetToExchangeHandWith=null;
		targetToKill=null;
		targetToRob=null;
		knownRole.removeAll(knownRole);
		unknownRole.removeAll(unknownRole);
		hidden = null;
		// forgot something ??
	}

	public void discard(ArrayList<District> d){
		if(!d.isEmpty()){
			this.deck.putbackOne(d.remove(0)); }
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
			+ "Amount of gold: " + getGold() + "\n"
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
		if(this.isFirstToFinish() && city.getSizeOfCity()>=8){
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


	/**
	 * Cette méthode permet de compter les points en fin de partie
	 * @return le nombre de points
	 */
	public int points(){
		int points=0;
         points+=city.getTotalValue();
		 if(this.firstToFinish){
			//Le premier joueur à avoir posé son quartier reçoit +4
			points+=4;
		 }
		 else if(!this.firstToFinish && city.getSizeOfCity()>=8){
			 // + 2 pour les autres joueurs ayant huit quartiers.
			 points+=2;
		 }

		 if(this.city.containsAllColors()){
			 // + 3 si la cité comprend des quartiers des cinq couleurs différentes.
			 points+=3;
		 }
		 
         return points;
	}

	public boolean hasTheDistrict(String districtName){
		return this.city.containsWonder(districtName);
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

	public void setHand(Hand liste) {
		this.hand=liste;
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
	
	public District GetDestroyedDistrict() {
		return destroyedDistrict;
	}
	
	public Player getTargetToExchangeHandWith() {
		return targetToExchangeHandWith;
	}

	public void setTargetToExchangeHandWith(Player targetToExchangeHandWith) {
		this.targetToExchangeHandWith = targetToExchangeHandWith;
	}

	public ArrayList<District> getCardsToExchange(){
		return new ArrayList<District>(cardsToExchange);
	}

	public void setCardsToExchange(ArrayList<District> cards){
		this.cardsToExchange = new ArrayList<District>(cards);
	}

	public Role getCharacter() {
		return character;
	}

	public void setCharacter(Role character) {
		this.character = character;
		character.setPlayer(this);
	}

	/**Dans cette méthode on crée et on initialise le MatchingProb
	 * 
	 */
	public void setBoard(Board board) {
		this.board = board;
		if(this.board.getPlayers()!=null){
			setMatches(new MatchingProb(this.board.getPlayers()));
		}
		
	}

	public void setMatches(MatchingProb matches){
		this.matches = matches;
	}

	public Hand getHand(){return hand;}
	
	public int getId() {
		return id;
	}
	
	public City getCity() {
		return city;
	}

	/**
	 * Pour les tests uniquement, ne devrait pas etre
	 * utilisé ailleurs
	 * @param city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public void setDeck(Deck d) {
		this.deck=d;
	}

	public DealRoles getDealRoles() {
		return dealRoles;
	}

	public void setDealRoles(DealRoles dealRoles) {
		this.dealRoles = dealRoles;
	}

	public Deck getDeck() {
		return deck;
	}

	public int sizeOfHand(){
		return hand.size();
	}

	public int sizeOfCity(){
		return city.getSizeOfCity();
	}
	public int totalValueOfCity(){
		return city.getTotalValue();
	}

}