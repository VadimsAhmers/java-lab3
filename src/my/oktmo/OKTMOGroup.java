package my.oktmo;

import java.util.ArrayList;
import java.util.List;

public class OKTMOGroup extends GroupsAndPlaces{

    enum OKTMOLevel {

        LEVEL2_KRAI_OBL_RESP,
        LEVEL3_RAYON_GOROKRUG,
        LEVEL4_POSELENIE
    }

    OKTMOLevel level;
    List<OKTMOGroup> innerGroups;  // список групп, вложенных в данную группу
    List<Place> innerPlaces;

    public OKTMOGroup(long code, String name) {
        this.code = code;
        this.name = name;
        innerGroups = new ArrayList<>();
        innerPlaces = new ArrayList<>();
    }
}

