package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Assets {
    public static final Deck TheDeck=new Deck();
    public static final Bank TheBank=new Bank();

    public static final Murderer TheMurderer=new Murderer();
    public static final Thief TheThief=new Thief();
    public static final Wizard TheWizard=new Wizard();
    public static final King TheKing=new King();
    public static final Bishop TheBishop=new Bishop();
    public static final Merchant TheMerchant=new Merchant();
    public static final Architect TheArchitect=new Architect();
    public static final Warlord TheWarlord=new Warlord();

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

    static ArrayList<Role> getRoles(){
        return allRoles;
    }


	
}
