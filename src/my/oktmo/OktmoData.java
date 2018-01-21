package my.oktmo;

import java.util.*;

public class OktmoData {

    ArrayList<Place> places = new ArrayList<>();
    ArrayList<Place> sortedPlaces;

    Set<String> allStatuses = new TreeSet<>();

    Map<Long, OKTMOGroup> level2 = new TreeMap<>();
    Map<Long, OKTMOGroup> level3 = new TreeMap<>();
    Map<Long, OKTMOGroup> level4 = new TreeMap<>();


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
