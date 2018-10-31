package at.rpgraz.taketurn.taketurn;

import java.util.Comparator;

public class TT_IniSorter implements Comparator<TT_DataModel>
{
    public int compare(TT_DataModel a, TT_DataModel b)
    {
        return b.getInitiativeInt() - a.getInitiativeInt();
    }
}
