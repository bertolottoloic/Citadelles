package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.board.Crown;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Collections;

class DealRoles{
    private ArrayList<Role> leftRoles;
    private ArrayList<Role> visible = new ArrayList<>();
    private ArrayList<Player> players;
    private Role hidden;
    DealRoles(ArrayList<Player> player){
        this.players = new ArrayList<>(player);
        this.visible=new ArrayList<Role>();
    }

    ArrayList<Role> getLeftRoles(){
        return this.leftRoles;
    }

    ArrayList<Role> getVisible(){
        return this.visible;
    }

    void selectRole(Player p, ArrayList<Role> roles){
        roles.get(0).setPlayer(p);
        p.setCharacter(roles.remove(0));
    }

    void distributeRoles(Crown c){
        Player player = c.getCrownOwner();
        selectRole(player,leftRoles);
        while((player=player.getNextPlayer())!=c.getCrownOwner()){
            selectRole(player, leftRoles);
        }
    }

    void  readyToDistribute(ArrayList<Role> roles){
        ArrayList<Role> al = new ArrayList<Role>(roles);

        Collections.shuffle(al);
        hidden=al.remove(0);
        Collections.shuffle(al);
        visible.add(al.remove(0)); //Le Roi ne peut pas etre le Role Visible
        //Je crois qu'il faut choisir d'abord les visibles

        leftRoles=al;

    }

}