package my.oktmo;

import my.oktmo.OKTMOGroup.OKTMOLevel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoReader {


    OKTMOLevel previousLevel = OKTMOLevel.LEVEL2_KRAI_OBL_RESP;
    OKTMOGroup currentLevel2Group;
    OKTMOGroup currentLevel3Group;
    OKTMOGroup currentLevel4Group;

    /*  Общий метод для чтения строк
     *  Читает строки, распознает уровень иерархии муниципальных образований
     *  Создает объект OKTMOGroup или Place согласно считанному уровню
     */
    public void readData(String fileName, OktmoData data){

        int lineCount=0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(fileName), "cp1251")))
        {
            Pattern pattern = Pattern.compile("[А-ЯЁ0-9](.*)");

            String s;

            while ((s=br.readLine()) !=null ) {
                lineCount++;

                if (s.contains("Населенные пункты, входящие в состав") || lineCount<3) continue;
                if (s.contains("поселения")) continue;
                if (s.contains("Межселенные территории")) continue;

                String[] stringArray = s.split("\"?;\"?");
                boolean isGroup = false;
                if (stringArray[3].equals("000")) isGroup = true;

                String codeString = "";

                for (int i = 0; i < 6; i++)
                    codeString += stringArray[i];

                long code = Long.parseLong(codeString.substring(1));

                String name, status="";
                
                Matcher matcher = pattern.matcher(stringArray[6]);

                if (matcher.matches()) {
                    name = stringArray[6];

                    if (isGroup) {
                        addGroup(new OKTMOGroup(code, name), data);
                        continue;
                    }
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

    private void addGroup(OKTMOGroup group, OktmoData data) {

        int type = parseType(group.code);

        switch (type){
            case 2: group.level = OKTMOLevel.LEVEL2_KRAI_OBL_RESP;

                    // Нормализация наименования субъекта РФ
                    if (group.name.contains("Городские округа"))
                        group.name = group.name.replace("Городские округа ", "");
                    if (group.name.contains("Муниципальные образования"))
                        group.name = group.name.replace("Муниципальные образования ", "");
                    if (group.name.contains("Муниципальные районы"))
                        group.name = group.name.replace("Муниципальные районы ", "");
                    if (group.name.contains("и городские округа"))
                        group.name = group.name.replace("и городские округа ", "");

                    data.level2.put(group.code, group);
                    break;

            case 3: group.level = OKTMOLevel.LEVEL3_RAYON_GOROKRUG;
                    data.level3.put(group.code, group);
                    break;
            case 4: group.level = OKTMOLevel.LEVEL4_POSELENIE;
            data.level4.put(group.code, group);
            break;

            }

        switch (group.level){
            case LEVEL2_KRAI_OBL_RESP: currentLevel2Group = group;
                    break;
            case LEVEL3_RAYON_GOROKRUG: currentLevel3Group = group;
                    currentLevel2Group.innerGroups.add(group);
                    break;
            case LEVEL4_POSELENIE:currentLevel4Group = group;
                    currentLevel3Group.innerGroups.add(group);

        }

        }

    private int parseType(long code) {

        int type=2;
        String codeString = Long.toString(code);
        if (codeString.contains("000000000")) {
            type = 2; //регион
        }
        else if (codeString.contains("000000")){
            type = 3; //район или городской округ
        }
        else if (codeString.contains("000")){
            type = 4; //сельское или городское поселение
        }

        return type;

    }



    public void addPlace(Long code, String status, String name, OktmoData data){

        Place place = new Place();
        place.code =code;
        place.name = name;
        place.setStatus(status);

        data.places.add(place);

        currentLevel4Group.innerGroups.add(place);

        //System.out.println(place.getStatus() + " " + place.getName());
    }
 
}
