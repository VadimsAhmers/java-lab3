package my.oktmo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OktmoAnalyzer {

    public void findPlacesSixLettersOrLesser(OktmoData data){

        Pattern pattern = Pattern.compile("[А-ЯЁа-яё]{1,2}(ово)");

        for (Place p : data.places){
            Matcher matcher = pattern.matcher(p.name);
            if (matcher.matches()) System.out.println(p);
        }
    }

    public void findPlacesBeginningAndEndingTheSameConsonant(OktmoData data){

        Pattern pattern = Pattern.compile("([А-Я]).*\\1", Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);

        for (Place p : data.places){
            Matcher matcher = pattern.matcher(p.name);
            if (matcher.matches()) System.out.println(p);
        }
    }

    public List<Place> findAllPlacesInGroupWithoutStreams(OKTMOGroup group){

        List<Place> innerPlaces = new ArrayList<>();

        switch (group.level){
            case LEVEL2_KRAI_OBL_RESP: for (OKTMOGroup rayon: group.innerGroups) innerPlaces = findPlacesInRayon(rayon, innerPlaces);
                //innerPlaces = findPlacesInRayon(rayon, innerPlaces).stream().collect(Collectors.toList());
            case LEVEL3_RAYON_GOROKRUG: innerPlaces = findPlacesInRayon(group, innerPlaces);
                                        break;
            case LEVEL4_POSELENIE: innerPlaces = group.innerPlaces;
                                    break;
        }
        return innerPlaces;
    }

    List<Place> findPlacesInRayon(OKTMOGroup rayon, List<Place> innerPlaces){
        for (OKTMOGroup selpos: rayon.innerGroups)
            for (Place place: selpos.innerPlaces) innerPlaces.add(place);

        /*innerPlaces =*/ //rayon.innerGroups.stream().map(selpos->selpos.innerPlaces).map(listOfPlaces->listOfPlaces.addAll(innerPlaces));
        return innerPlaces;
    }
    String findMostPopularPlaceName(String regionName, Map<String, OKTMOGroup> namedregionsMap){
        OKTMOGroup targetGroup = new OKTMOGroup(0, "");
        Map<String, AtomicInteger> countedPlaces = new TreeMap<>();
        AtomicInteger count = new AtomicInteger(0);

        for (Map.Entry<String, OKTMOGroup> entry : namedregionsMap.entrySet())
            if (entry.getKey().contains(regionName))
                targetGroup = entry.getValue();

        List<Place> placesInRegion = findAllPlacesInGroupWithoutStreams(targetGroup);

        for (Place place : placesInRegion)
            countedPlaces.put(place.name, count.addAndGet(1));

    }
}
