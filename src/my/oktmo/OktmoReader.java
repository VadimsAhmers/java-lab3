package my.oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoReader {

    public void readPlaces(String fileName, OktmoData data){

        int lineCount=0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251")))
        {
            Pattern pattern = Pattern.compile("[А-ЯЁ0-9](.*)");

            String s;
            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;
                //if (lineCount==100) break;

                String[] stringArray = s.split("\"?;\"?");

                if (stringArray[3].equals("000")) continue;

                String codeString = "";

                for (int i = 0; i < 6; i++)
                    codeString += stringArray[i];

                long code = Long.parseLong(codeString.substring(1));
                String name, status = "";

                Matcher matcher = pattern.matcher(stringArray[6]);

                if (matcher.matches()) {
                    name = stringArray[6];
                    //System.out.println(name);
                }
                else {
                    String[] rawStatusPlusName = stringArray[6].split(" ", 2);

                    status = rawStatusPlusName[0];
                    name  = rawStatusPlusName[1];
                }

                if (!data.allStatuses.contains(status)) data.allStatuses.add(status);

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
