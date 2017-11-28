package my.oktmo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOktmo {

    @Test
    public void testReadPlaces(){

        OktmoData data = new OktmoData();
        OktmoReader reader = new OktmoReader();

        reader.readPlaces("data-201710.csv", data);

        //10-й населенный пункт
        assertEquals(data.places.get(9).getCode(), 160141311662L);
        assertEquals(data.places.get(9).getStatus(), "п");
        assertEquals(data.places.get(9).getName(), "Успеновка");

        //последний населенный пункт
        int lastPlaceIndex = data.places.size() - 1;
        assertEquals(data.places.get(lastPlaceIndex).getCode(), 9970100000192L);
        assertEquals(data.places.get(lastPlaceIndex).getStatus(), "г");
        assertEquals(data.places.get(lastPlaceIndex).getName(), "Биробиджан");

        //сколько там точно - хз
        assertEquals(lastPlaceIndex, 155867);


    }
}
