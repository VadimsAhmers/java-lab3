package my.oktmo;

import java.util.*;

public class OktmoData {

    ArrayList<Place> places = new ArrayList<>();
    ArrayList<Place> sortedPlaces;

    Set<String> allStatuses = new TreeSet<>();

    public void printContent(){

        for (Place place : places)
            System.out.println(place.toString());

    }

    public void sortPlaces(){

        sortedPlaces = new ArrayList<>(places);

        //sortedPlaces.sort(new SortedByName());
        Collections.sort(sortedPlaces, new SortedByName());

    }
 
}
