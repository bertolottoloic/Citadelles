package fr.unice.polytech.startingpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

class Deck{
    private ArrayList <District> list=new ArrayList<>();
    private final String srcData="src/main/resources/infos_districts.json";
    Deck(){
        try {
            JSONArray infos=new JSONArray(readFile(srcData));

            Consumer <Object> c=e->{
                int n=((JSONObject)e).getInt("nb");

                for(int i=0;i<n;i++){
                    list.add(new District(((JSONObject)e).getInt("cost"),((JSONObject)e).getInt("value"),((JSONObject)e).getString("color")));
                }

            };

            
            infos.forEach(c);
            
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

    
}