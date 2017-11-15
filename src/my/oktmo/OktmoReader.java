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
                //if (lineCount==100) break;

                String[] stringArray = s.split(";");

                if (stringArray[3].equals("\"000\"")) continue;

                String codeString = "";

                for (int i = 0; i<6; i++)
                    codeString +=stringArray[i];

                Long code = Long.parseLong(codeString.replace("\"", ""));
                String status = stringArray[6].substring(1,2);
                String name = stringArray[6].substring(3);

                Place place = new Place();
                place.setCode(code);
                place.setName(name);
                place.setStatus(status);

                data.places.add(place);

                System.out.println(place.getStatus() + " " + place.getName() + lineCount);

                }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }


    }
 
}
