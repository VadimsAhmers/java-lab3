package my.oktmo;

import java.util.Comparator;

public class SortedByName implements Comparator<Place> {
    public int compare(Place place1, Place place2){
        int result = place1.name.compareTo(place2.name);

        if (result !=0) return result;
        return (place1.code>place2.code? 1 : -1);

    }
}
