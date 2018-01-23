package my.oktmo;

import org.junit.Test;

import javax.swing.*;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestOktmo {

    @Test
    public void testReadPlaces(){

        OktmoData data = new OktmoData();
        OktmoReader reader = new OktmoReader();

        reader.readData("data-201710.csv", data);

        //10-й населенный пункт
        assertEquals(data.places.get(9).code, 160141311662L);
        assertEquals(data.places.get(9).getStatus(), "п");
        assertEquals(data.places.get(9).name, "Успеновка");

        //последний населенный пункт
        int lastPlaceIndex = data.places.size() - 1;
        assertEquals(data.places.get(lastPlaceIndex).code, 9970100000192L);
        assertEquals(data.places.get(lastPlaceIndex).getStatus(), "г");
        assertEquals(data.places.get(lastPlaceIndex).name, "Биробиджан");

        //сколько там точно - хз
        assertEquals(lastPlaceIndex, 155867);

        //проверяем количество районов в регионе
        OKTMOGroup rB = new OKTMOGroup(1, " ");
        for (Map.Entry<Long, OKTMOGroup> entry : data.level2.entrySet())
            if (entry.getValue().name.contains("Башкортостан")){
            rB = entry.getValue();
            }
        assertEquals(rB.innerGroups.size(), 9+54); // в Башкирии 9 городских округов и 54 района
    }

    @Test
    public void testReadPlacesUsingStreams(){
        OktmoData data = new OktmoData();
        OktmoReader reader = new OktmoReader();

        reader.readData("data-201710.csv", data);

        data.level2.entrySet().stream().filter(map->"Московской области".equals(map.getValue().name)).map(map->map.getValue());
    }

}
