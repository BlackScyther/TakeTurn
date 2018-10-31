package at.rpgraz.taketurn.taketurn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

public class TT_IniTableActivity extends AppCompatActivity
{

    ArrayList<TT_DataModel> dataModels;
    ListView listView;
    private static TT_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt__ini_table);

        listView = (ListView)findViewById(R.id.iniListView);

        dataModels = new ArrayList<>();

        dataModels.add(new TT_DataModel("Caleb", "PC", "+3","16"));
        dataModels.add(new TT_DataModel("Kylar", "PC", "+4","0"));
        dataModels.add(new TT_DataModel("Roxy/Cerys", "PC", "+2","6"));
        dataModels.add(new TT_DataModel("Romrag", "PC", "+2","20"));
        dataModels.add(new TT_DataModel("Ellyobell","PC","+3","16"));
        dataModels.add(new TT_DataModel("Rafik", "PC", "+1","1"));
        dataModels.add(new TT_DataModel("Vincent", "Gegner", "+2","12"));
        dataModels.add(new TT_DataModel("Ritter", "Gegner", "+0","12"));
        dataModels.add(new TT_DataModel("Mädls", "Gegner", "+3","5"));
        adapter = new TT_Adapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //TT_DataModel dataModel= dataModels.get(position)
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0
                    , View arg1, int pos, long id) {
                TT_IniTableActivity.this.onItemLongClick(pos);
                return true;
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        listView.setItemChecked(0, true);
    }

    public void onItemLongClick(final int pos)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        adapter.remove(adapter.getItem(pos));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(TT_IniTableActivity.this);
        builder.setMessage("Delete Item?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void rollForInitiative(View view)
    {
        for(int i = 0 ; i < adapter.getCount() ; i++){
            TT_DataModel model = (TT_DataModel)adapter.getItem(i);
            String sMod = model.getIniMod();
            sMod.trim();
            int mod = Integer.parseInt(sMod);
            int ini = rollDice(1,20, mod);
            String sIni = String.valueOf(ini);
            model.initiative = sIni;
        }

        adapter.sort(new TT_IniSorter());

        adapter.notifyDataSetChanged();
    }

    public static  int rollDice(int number, int nSides, int modifier)
    {
        int num = 0;
        int roll = 0;
        Random r = new Random();
        if(nSides >=3)
        {
            for(int i = 0; i < number; i++)
            {
                roll = r.nextInt(nSides)+1;
                System.out.println("Roll is:  "+roll);
                num = num + roll;
            }
        }
        else
        {
            System.out.println("Error num needs to be from 3");
        }
        return num + modifier;
    }

    public void nextTurn(View view)
    {
        int pos =listView.getCheckedItemPosition();
        int nextPos = getNextPos(pos);
        listView.setItemChecked(nextPos, true);
    }

    public int getNextPos(int pos)
    {
        pos++;
        if(pos == listView.getAdapter().getCount())
        {
            pos = 0;
        }

        return pos;
    }

    public void addNew(View view)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TT_IniTableActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.tt_add_dialog,null);

        listView = (ListView)findViewById(R.id.iniListView);
        final EditText txtName = (EditText) mView.findViewById(R.id.txtName);
        final Spinner sType = (Spinner) mView.findViewById(R.id.sTypes);
        final EditText txtInitiative = (EditText) mView.findViewById(R.id.txtAddIni);
        final Button btnNext = (Button)  mView.findViewById(R.id.btnNext);
        final EditText txtIniMod = (EditText) mView.findViewById(R.id.txtIniMod);

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = txtName.getText().toString();

                String type = sType.getSelectedItem().toString();

                String iniMod = txtIniMod.getText().toString();

                String initiative = txtInitiative.getText().toString();

                if(initiative.isEmpty())
                {
                    initiative = "0";
                }

                //ToDo iniMod
                TT_DataModel dmAdd = new TT_DataModel(name, type, iniMod ,initiative);
                adapter.add(dmAdd);
                adapter.notifyDataSetChanged();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

}
