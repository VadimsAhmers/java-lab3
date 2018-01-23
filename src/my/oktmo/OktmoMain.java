package my.oktmo;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
     for (Map.Entry<Long, OKTMOGroup> entry : data.level4.entrySet())
     System.out.println(entry.getValue().name);

    //for (GroupsAndPlaces groups : reader.currentLevel4Group.innerGroups)
      // System.out.println(groups.code + " " + groups.name);

     //System.out.println(data.level2.entrySet().stream().filter(map->"Московской области".equals(map.getValue().name)).map(map->map.getValue()).map(map->map.innerGroups).collect(Collectors.toList()));
    /* System.out.println(data.level3.entrySet()
             .stream().filter(map->"Волочаевское".equals(map.getValue().name))
             .map(entry -> entry.getValue())
             .collect(Collectors.toList())
             .map(group->analyzer.findAllPlacesInGroup(group)).collect(Collectors.toList())
     );*/


    Map<String, OKTMOGroup> namedRegionsMap = new TreeMap<>();
    for (Map.Entry<Long, OKTMOGroup> region: data.level2.entrySet())
        namedRegionsMap.put(region.getValue().name, region.getValue());

    for (Map.Entry<String, OKTMOGroup> entry: namedRegionsMap.entrySet())
        if (entry.getKey().contains("Башкортостан"))
            System.out.println(analyzer.findAllPlacesInGroupWithoutStreams(entry.getValue()).stream().map(place -> place.name)
                    .collect(Collectors.toList()));

     System.out.println(analyzer.findMostPopularPlaceName("Челяб", namedRegionsMap));
     analyzer.printStatusTableForRegion("Башк", namedRegionsMap);

 }

}
