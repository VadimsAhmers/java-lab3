package my.oktmo;

import java.util.Map;

public class OktmoMain {

 public static void main(String[] args) {

     OktmoData data = new OktmoData();
     OktmoReader reader = new OktmoReader();

     reader.readData("data-201710.csv", data);

     //data.printContent();
     //System.out.println(data.allStatuses);
     data.sortPlaces();

     /*for (Place p : data.sortedPlaces)
     System.out.println(p);*/

     OktmoAnalyzer analyzer = new OktmoAnalyzer();
     //analyzer.findPlacesSixLettersOrLesser(data);
     //analyzer.findPlacesBeginningAndEndingTheSameConsonant(data);
     for (Map.Entry<Long, OKTMOGroup> entry : data.level2.entrySet())
     System.out.println(entry.getValue().name);

    //for (GroupsAndPlaces groups : reader.currentLevel4Group.innerGroups)
      // System.out.println(groups.code + " " + groups.name);
 }

}
