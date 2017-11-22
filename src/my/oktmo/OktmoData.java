package my.oktmo;

import java.util.HashSet;

public class OktmoData {

    HashSet<Place> places = new HashSet<>();

    public void printContent(){

        for (Place place : places)
            System.out.println(place.toString());

    }
 
}
