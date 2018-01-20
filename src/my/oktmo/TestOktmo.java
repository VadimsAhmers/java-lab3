package my.oktmo;

import org.junit.Test;

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


    }
}
