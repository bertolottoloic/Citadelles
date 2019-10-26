package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.role.*;

import java.util.ArrayList;
import java.util.Collections;

public class Assets {

    public final static Murderer TheMurderer=new Murderer();
    public static final Thief TheThief=new Thief();
    public static final Wizard TheWizard=new Wizard();
    public static final King TheKing=new King();
    public static final Bishop TheBishop=new Bishop();
    public static final Merchant TheMerchant=new Merchant();
    public static final Architect TheArchitect=new Architect();
    public static final Warlord TheWarlord=new Warlord();

    public static Role HiddenRole;
    public static ArrayList<Role> VisibleRole=new ArrayList<>();
    public static ArrayList<Role> leftRoles;

    private static final ArrayList<Role> allRoles=listOfRoles();

    
    private static ArrayList<Role> listOfRoles(){
        ArrayList<Role> roles=new ArrayList<>();
        roles.add(TheMurderer);
        roles.add(TheThief);
        roles.add(TheWizard);
        roles.add(TheKing);
        roles.add(TheBishop);
        roles.add(TheMerchant);
        roles.add(TheArchitect);
        roles.add(TheWarlord);
        return roles;

    }

    static void reInitializeRoles(){
        for(Role r:allRoles){
            r.reInitialize();
        }

    }

    public static ArrayList<Role> getRoles(){
        ArrayList<Role> tmp=new ArrayList<>();
        tmp.addAll(allRoles);
        return tmp;
    }
    /**
     * Méthode qui set les Roles cachés et les roles visibles
     * et qui set les roles restants
     */
    static void  readyToDistribute(){
        ArrayList<Role> al=getRoles();
        
        Collections.shuffle(al);
        HiddenRole=al.remove(0);
        Collections.shuffle(al);
        VisibleRole.add(al.remove(0)); //Le Roi ne peut pas etre le Role Visible
        //Je crois qu'il faut choisir d'abord les visibles

        leftRoles=al;

    }


	
}
