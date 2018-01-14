package my.oktmo;

import java.util.List;

public class OKTMOGroup {

    enum OKTMOLevel {

        LEVEL2_KRAI_OBL_RESP,
        LEVEL3_RAYON_GOROKRUG,
        LEVEL4_POSELENIE
    }

    OKTMOLevel level;
    String name;
    long code;
    List<OKTMOGroup> innerGroups;

    public OKTMOGroup(OKTMOLevel level) {
        this.level = level;
    }
}

