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
            case LEVEL2_KRAI_OBL_RESP: for (OKTMOGroup rayon: group.innerGroups)
                innerPlaces = findPlacesInRayonWithoutStreams(rayon, innerPlaces);
                //innerPlaces = findPlacesInRayon(rayon, innerPlaces).stream().collect(Collectors.toList());
            case LEVEL3_RAYON_GOROKRUG: innerPlaces = findPlacesInRayonWithoutStreams(group, innerPlaces);
                                        break;
            case LEVEL4_POSELENIE: innerPlaces = Collections.unmodifiableList(group.innerPlaces);
                                    break;
        }
        return innerPlaces;
    }

    List<Place> findPlacesInRayonWithoutStreams(OKTMOGroup rayon, List<Place> innerPlaces){
         for (OKTMOGroup selpos: rayon.innerGroups)
            for (Place place: selpos.innerPlaces) innerPlaces.add(place);

        //innerPlaces =rayon.innerGroups.stream().flatMap(selpos->selpos.innerPlaces.stream()).collect(Collectors.toList());

        //);.map(listOfPlaces->listOfPlaces.addAll(innerPlaces));

        return innerPlaces;
    }
    List<Place> findPlacesInRayon(OKTMOGroup rayon){
        //  for (OKTMOGroup selpos: rayon.innerGroups)
        //    for (Place place: selpos.innerPlaces) innerPlaces.add(place);
        List<Place> innerPlaces;
        innerPlaces =rayon.innerGroups.stream().flatMap(selpos->selpos.innerPlaces.stream()).collect(Collectors.toList());

        //);.map(listOfPlaces->listOfPlaces.addAll(innerPlaces));

        return innerPlaces;
    }
    
    String findMostPopularPlaceName(String regionName, Map<String, OKTMOGroup> namedregionsMap){
        OKTMOGroup targetGroup = new OKTMOGroup(0, "");
        //Map<String, AtomicInteger> countedPlaces = new TreeMap<>();
        //AtomicInteger count = new AtomicInteger(0);

        for (Map.Entry<String, OKTMOGroup> entry : namedregionsMap.entrySet())
            if (entry.getKey().contains(regionName))
                targetGroup = entry.getValue();

        List<Place> placesInRegion = findAllPlacesInGroupWithoutStreams(targetGroup);

    //    Map<String, List<Place>> collect = 
        Map<String, Long> collect = placesInRegion.stream().collect(Collectors.groupingBy(Place::getName, Collectors.counting()));
        //for (Place place : placesInRegion)
            //countedPlaces.put(place.name, count.addAndGet(1));
        String mostPopularPlaceName = "";
        long count = 0;
        for (Map.Entry<String, Long> entry : collect.entrySet())
            if (entry.getValue()>count) {
            mostPopularPlaceName = entry.getKey();
            count = entry.getValue();
            }
        return mostPopularPlaceName;

    }

    void printStatusTableForRegion(String regionName, Map<String, OKTMOGroup> namedRegionsMap){

        OKTMOGroup targetGroup = new OKTMOGroup(0, "");
        for (Map.Entry<String, OKTMOGroup> entry : namedRegionsMap.entrySet())
            if (entry.getKey().contains(regionName))
                targetGroup = entry.getValue();

        List<Place> places = findAllPlacesInGroupWithoutStreams(targetGroup);
        Map<String, Long> collect = places.stream().collect(Collectors.groupingBy(Place::getStatus, Collectors.counting()));

        System.out.println("Статус            Количество");
        for (Map.Entry<String, Long> entry : collect.entrySet())
            System.out.println(entry.getKey() + "    " + entry.getValue());
    }
}
