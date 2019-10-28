package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;

class DealRoles{
    private ArrayList<Role> roles;
    private ArrayList<Role> visible;
    private Role hidden;
    DealRoles(ArrayList<Player> player){
        this.roles=new ArrayList<Role>();
        this.visible=new ArrayList<Role>();
        this.roles.addAll(Assets.getRoles());
        

        Collections.shuffle(this.roles);
        System.out.println(this.roles);
        this.visible.add(this.roles.remove(0));
        this.roles.add(Assets.TheKing);
        Collections.shuffle(this.roles);
        this.hidden=this.roles.remove(0);

        for(Player p : player){
            Role r=selectRole(p,this.roles);
            p.setCharacter(r);
            this.roles.remove(r);
        }
    }

    ArrayList<Role> getRoles(){
        return this.roles;
    }

    ArrayList<Role> getVisible(){
        return this.visible;
    }

    /*Role selectRole(Player p,ArrayList<Role> roles, ArrayList<Role> visible){
        return p.selectRole(roles,visible);
    }*/

    Role selectRole(Player p,ArrayList<Role> roles){
        return this.roles.get(0);
    }

}