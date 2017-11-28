package my.oktmo;

import java.util.Comparator;

public class SortedByName implements Comparator<Place> {
    public int compare(Place place1, Place place2){
        int result = place1.getName().compareTo(place2.getName());

        if (result !=0) return result;
        return (place1.getCode()>place2.getCode()? 1 : -1);

    }
}
