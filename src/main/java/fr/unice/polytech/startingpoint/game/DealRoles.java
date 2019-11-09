package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.role.*;

import java.util.ArrayList;
import java.util.Collections;

public class DealRoles{
    private ArrayList<Role> leftRoles;
    private ArrayList<Role> roles = new ArrayList<>();
    private ArrayList<Role> visible = new ArrayList<>();
   
    private Role hidden;
    public DealRoles(){
        this.visible=new ArrayList<Role>();
        this.roles.add(new Murderer());
        this.roles.add(new Thief());
        this.roles.add(new Wizard());
        this.roles.add(new King());
        this.roles.add(new Bishop());
        this.roles.add(new Merchant());
        this.roles.add(new Architect());
        this.roles.add(new Warlord());
    }


    public void  readyToDistribute(){
        ArrayList<Role> al = new ArrayList<Role>(roles);

        Collections.shuffle(al);
        this.hidden=al.remove(0);
        Collections.shuffle(al);
        visible.add(al.remove(0)); //Le Roi ne peut pas etre le Role Visible
        //Je crois qu'il faut choisir d'abord les visibles

        leftRoles=al;

    }


    public void reInitializeRoles(){
        for(Role r:roles){
            r.reInitialize();
        }

    }
    public ArrayList<Role> getRoles(){
        return new ArrayList<Role>(this.roles);
    }

    public Role getRole(int index){
        return this.roles.get(index);
    }

    public ArrayList<Role> getLeftRoles(){
        return this.leftRoles;
    }

    ArrayList<Role> getVisible(){
        return this.visible;
    }

    public Role getHidden() {
        return hidden;
    }

    public void setHidden(Role hidden) {
        this.hidden = hidden;
    }
}