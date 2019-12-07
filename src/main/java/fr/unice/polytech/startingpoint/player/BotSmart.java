package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Role;

/**
 * @author Patrick Anagonou 
 * @author Heba Bouzidi
 * @author Loic Bertolotto
 * @author Clement Poueyto
 *
 * */

public class BotSmart extends Player {
    

    public BotSmart(int id) {
        super(id);
    }


	public Tmp buildables(List<District> toConsider,int limit){
        if(toConsider.isEmpty() || limit<=0){
            return new Tmp(0,new ArrayList<District>());
        }
        else if(toConsider.get(0).getCost()>limit){
            //Explore right branch only
            return buildables(toConsider.subList(1,toConsider.size()), limit);
        }
        else{
            var nextItem=toConsider.get(0);
            //Explore left branch
            Tmp resultLeft=buildables(toConsider.subList(1, toConsider.size()), limit-nextItem.getCost());
            resultLeft.addVal(nextItem.getValue());
            //Explore right branch
            Tmp resultRight=buildables(toConsider.subList(1, toConsider.size()), limit);

            if(resultLeft.getVal()>resultRight.getVal()){
                resultLeft.getDistricts().add(nextItem);
                
                return resultLeft;
            }
            else{
                return resultRight;
            }
        }
    }

    /* -------------OVERRIDING ------------------*/

    @Override
    public List<District> processCardsToExchange() {
        return this.hand.badCards(this.getGold());
    }
    @Override
    public Player processWhoToExchangeHandWith() {
        if((this.hand.size()-this.hand.badCards(this.getGold()).size())<=(this.hand.size()/2)){
            return null;
        }
        if(this.getBoard().playerWithTheBiggestHand(this).getHand().size()>=this.hand.size()){
            return this.getBoard().playerWithTheBiggestHand(this);
        }
        return null;
    }

    @Override
    public Player processWhoseDistrictToDestroy() {
        return board.playerWithTheBiggestCity(this);
    }

    @Override
    public District processDistrictToDestroy(Player target) {
        Optional<District> tmp=target.city.cheaperDistrict();
        if(tmp.isPresent()){
            return tmp.get();
        }
        else{
            return null;
        }
    }

    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 8;
    }

    @Override
    public Role processWhoToKill() {
        this.attributeProbsToPlayer();
        Set<String> targets = matches.possibleRolesFor(this.board.playerWithTheBiggestCity(this).getId());
        targets.remove(this.getCharacter().toString());//on exclut son propre role
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }

    @Override
    public Role processWhoToRob() {
        Set<String> targets = matches.possibleRolesFor(board.richestPlayer(this).getId());
        targets.remove(this.getCharacter().toString());//on exclut son propre role
        /*Optional<Role> optKilled=dealRoles.roleKilled();
        if(optKilled.isPresent()){
            targets.remove(optKilled.get().toString());
        }
        */
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }

    /**
     * avant de commencer à trier
     * On garde les cartes les moins cheres
     * Bien sûr on doit toujours avoir getNumberDistrictKeepable() cartes à la fin
     */
    @Override
    public void discard(List<District> d){
        d.sort((a,b)->
                Integer.compare(a.getCost(),b.getCost())
        );
        while(d.size()>this.getCharacter().getNumberDistrictKeepable()){
            this.deck.putbackOne(d.remove(d.size()-1));

        }
    }

    /**
     * quand on a dans sa main une carte identique à une
     * de celle de sa cité il est avantageux d'utiliser
     * le labo pour gagner de l'argent avec
     * puisqu'on a pas le droit de le poser de toute facon
     */
    @Override
    public Optional<District> wantsToUseLabo() {
        ArrayList<District> list = hand.cardsAboveGold(getGold());
        if(!list.isEmpty() && city.getSizeOfCity() >= 6) {
            District dis = hand.highCostDistrict(getGold());
            return Optional.of(dis);
        }
        return super.wantsToUseLabo();
    }

    /* ---------------------------------------------*/



    /**
     * Cette fonction permet d'attribuer
     * des probabilités pour permettre au joueur 
     * de deviner le rôle qu'ont les autres joueurs
     *  Ex: Au cours de la distribution
     *  si on a a choisir parmi des personnages (p1,p2,p3)
     * Cela veut dire que tous les joueurs ayant choisi
     * avant n'ont pas pris (p1,p2,p3) d'où
     * la probabilité pour qu'ils aient (p1,p2 ou p3)
     * comme role est 0
     * 
     */

    public void attributeProbsToPlayer(){
        ArrayList<Player> pl=board.getPlayers();
        ArrayList<Role> toConsider=new ArrayList<>(this.dealRoles.getLeftRoles());
        var hiddenRole=this.dealRoles.getHidden();
        ArrayList<Role> visibles=this.dealRoles.getVisible();

        if(this.nextPlayer!=null && this.nextPlayer.alreadyChosenRole){
            toConsider.add(hiddenRole);
        }
        for (Player player : pl) {
            //tous ceux qui ont déja choisi leurs roles n'ont évidemment pas 
            //pu prendre ce qui reste
            if(player.alreadyChosenRole){
                for (Role role : toConsider) {
                    matches.setProbability(player.getId(), role.toString(), 0);
                }
            }
            //personne n'a les roles visbibles
            for (Role role : visibles) {
                matches.setProbability(player.getId(), role.toString(), 0);
            }
            
            
        }
    }

    public ArrayList<DistrictColor> missingColors(){
        Set <DistrictColor> tmpHand=hand.colorsOfHand();
        Set <DistrictColor> tmpCity=city.colorsOfCity();
        
        Set<DistrictColor> tmp=new HashSet<DistrictColor>(Set.of(DistrictColor.values()));;
        tmp.removeAll(tmpHand);
        tmp.removeAll(tmpCity);
        
        return new ArrayList<DistrictColor>(tmp);
    }




    

}