package my.oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OktmoReader {

    public void readPlaces(String fileName, OktmoData data){

        int lineCount=0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251")))
        {
            String s;
            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;
                if (lineCount==100000) break;

                String[] stringArray = s.split(";");

                if (stringArray[3].equals("\"000\"")) continue;

                String codeString = "";

                for (int i = 0; i<6; i++)
                    codeString +=stringArray[i];

                Long code = Long.parseLong(codeString.replace("\"", ""));

                String[] rawStatusPlusName = stringArray[6].replace("\"", "").split(" ", 2);

                String status = rawStatusPlusName[0];
                String name = rawStatusPlusName[1];

                addPlace(code, status, name, data);

                }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }

    }

    public void addPlace(Long code, String status, String name, OktmoData data){

        Place place = new Place();
        place.setCode(code);
        place.setName(name);
        place.setStatus(status);

        data.places.add(place);

        //System.out.println(place.getStatus() + " " + place.getName());
    }
 
}
