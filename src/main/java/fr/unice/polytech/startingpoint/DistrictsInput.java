package fr.unice.polytech.startingpoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


class DistrictsInput{
    private static final String srcData="src/main/resources/infos_districts.json";

    private static String readFile(String fichier) throws FileNotFoundException,IOException{
        File f=new File(fichier);
        FileInputStream rf=new FileInputStream(f);
        String tmp=new String(rf.readAllBytes());
        rf.close();
        return tmp;
    }

	public static ArrayList<District> getDistricts() {
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