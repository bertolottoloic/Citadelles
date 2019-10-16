package fr.unice.polytech.startingpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

class Deck{
    private ArrayList <District> list=new ArrayList<>();
    private final String srcData="src/main/resources/infos_districts.json";
    Deck(){
        try {
            JSONArray infos=new JSONArray(readFile(srcData));

            
            infos.forEach(e->{
                int n=((JSONObject)e).getInt("nb");

                for(int i=0;i<n;i++){
                    list.add(new District(((JSONObject)e).getInt("cost"),((JSONObject)e).getInt("value"),((JSONObject)e).getString("color")));
                }

            });
            
        } catch (Exception e) {
            //TODO: handle exception
        }

        this.shuffle();//We shuffle the districts
    }

    private String readFile(String fichier) throws FileNotFoundException,IOException{
        File f=new File(fichier);
        FileInputStream rf=new FileInputStream(f);
        String tmp=new String(rf.readAllBytes());
        rf.close();
        return tmp;
    }

    public ArrayList<District> getList() {
        return list;
    }

    
    public void shuffle(){
        Collections.shuffle(list);
    }

    public District withdraw(){

        if(!list.isEmpty()){
            return list.remove(0);
        }
        return null;
    }

    /**
     * @param d
     *      un district
            
        On remet le district d sur le Deck
        
     * 
     */
    public void putbackOne(District d){
        list.add(d);
        this.shuffle();
    }

    /**
     * @param c
     *      une collection de District
            
        On remet le district d sur le Deck
        
     * 
     */
    public void putbackMany(Collection<? extends District> c){
        list.addAll(c);
        this.shuffle();
    }

    /**
     * @param d
     *      un district
            

        @return 
            Un district

            La méthode peut renvoyer null si il n'y avait plus de District dans 
            le deck. Il faut donc vérifier la valeur de retour
        
     * 
     */
    public District exchangeOne(District d){
        District tmp=this.withdraw();
        list.add(d);
        this.shuffle();
        return tmp;
    }

    /**
     * @param c
            Une collection de cartes à remettre dans le deck

        @return 
            Une arraylist de district de même taille que la taille du paramètre

            Si le nombre de Districts à échanger est supérieur au nombre de cartes du deck 
            aucune action n'est entreprise
        
     * 
     */
    public ArrayList<District> exchangeMany(Collection<? extends District> c){
        ArrayList <District> l=new ArrayList<>();
        if(c.size()>list.size()){
            for(int i=0;i<c.size();i++){
                l.add(this.withdraw());
            }
            list.addAll(c);
        }
        return l;
    }

    public int lenght(){
        return list.size();
    }





    
}