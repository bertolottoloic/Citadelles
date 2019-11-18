package fr.unice.polytech.startingpoint.board;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


class DistrictsInput{
    private final String srcData="/json/infos_districts.json";

    private String readFile(String fichier) throws FileNotFoundException,IOException{
        String tmp=new String(getClass().getResourceAsStream(srcData).readAllBytes());
        return tmp;
    }

	public  ArrayList<District> getDistricts() {
        ArrayList<District> list=new ArrayList<>();
		try {
            JSONArray infos=new JSONArray(readFile(srcData));

            
            infos.forEach(e->{
                int n=((JSONObject)e).getInt("nb");

                for(int i=0;i<n;i++){
                    list.add(new District(((JSONObject)e).getInt("cost"),((JSONObject)e).getInt("value"),((JSONObject)e).getString("color"),((JSONObject)e).getString("nom")));
                }

            });

} catch (Exception e) {
            System.out.println(e);;
        }

        return list;

	}

}