package my.oktmo;

public class OktmoMain {

 public static void main(String[] args) {

     OktmoData data = new OktmoData();
     OktmoReader reader = new OktmoReader();

     reader.readPlaces("data-201710.csv", data);

     //data.printContent();
     System.out.println(data.allStatuses);
     data.sortPlaces();

     /*for (Place p : data.sortedPlaces)
     System.out.println(p);*/

     OktmoAnalyzer analyzer = new OktmoAnalyzer();
     //analyzer.findPlacesSixLettersOrLesser(data);
     //analyzer.findPlacesBeginningAndEndingTheSameConsonant(data);
 }

}
