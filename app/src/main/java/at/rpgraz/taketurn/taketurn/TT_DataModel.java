package at.rpgraz.taketurn.taketurn;

public class TT_DataModel {

    String name;
    String type;
    String iniMod;
    String initiative;

    public TT_DataModel(String name, String type, String iniMod, String initiative ) {
        this.name = name;
        this.type = type;
        this.iniMod = iniMod;
        this.initiative = initiative;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getInitiative() {
        return initiative;
    }

    public int getInitiativeInt() {return Integer.parseInt(getInitiative());}

    public String getIniMod(){return iniMod;}

}
