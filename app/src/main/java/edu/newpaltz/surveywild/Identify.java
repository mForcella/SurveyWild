package edu.newpaltz.surveywild;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Identify extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        // initialize buttons with click function
        final Button plantBtn = (Button) findViewById(R.id.plantBtn);
        plantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new PlantFragment());
                loadIdFrag("plants", null, false);
            }
        });

        final Button animalBtn = (Button) findViewById(R.id.animalBtn);
        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new AnimalFragment());
                loadIdFrag("animals", null, false);
            }
        });

        final Button fungiBtn = (Button) findViewById(R.id.fungiBtn);
        fungiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new FungiFragment());
                loadIdFrag("fungi", null, false);
            }
        });

        final Button protistBtn = (Button) findViewById(R.id.protistBtn);
        protistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new ProtistFragment());
                loadIdFrag("protists", null, false);
            }
        });

        final Button invasiveBtn = (Button) findViewById(R.id.invasiveBtn);
        invasiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new InvasiveFragment());
                String [] subcategory = {"invasive"};
                loadIdFrag("plants", subcategory, false);
            }
        });
    }

    /**
     * Functions for loading fragments.
     */
    public void loadIdFrag(String category, String[] subcategories, boolean exclude) {
        IdentifyFragment identify = new IdentifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        if (subcategories != null)
            bundle.putStringArray("subcategory", subcategories);
        bundle.putBoolean("exclude", exclude);
        identify.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_page, identify, "main").commit();
    }

    public void removeIdFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("main");
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void loadSubFrag(Fragment subFrag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sub_menu, subFrag).commit();
        removeIdFrag();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_identify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The main fragment.
     */
    public static class IdentifyFragment extends Fragment {

        public IdentifyFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate layout
            View rootView = inflater.inflate(R.layout.fragment_identify, container, false);

            // get category and set text view
            Bundle bundle = this.getArguments();
            String category = bundle.getString("category", null);
            String[] subcategories = bundle.getStringArray("subcategory");
            boolean exclude = bundle.getBoolean("exclude", false);
            TextView viewing = (TextView) rootView.findViewById(R.id.viewing);
            viewing.setText("Viewing " + category + " in your area.");

            // get list of species and keywords
            DBAdapter db;
            try {
                db = new DBAdapter(getActivity());
                MyApplication.mListCName = db.getCNames(category);
                MyApplication.mListSpecies = db.getSpecies(category);
                MyApplication.mListKeywords = db.getKeywords();
                db.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // check for subcategory
            if (subcategories != null) {
                // filter species list by keyword
                MyApplication.mListCName = filterList(MyApplication.mListSpecies, subcategories, exclude);
            }

            // initialize list view
            ListView lvSpecies = (ListView)rootView.findViewById(R.id.lv_species);
            lvSpecies.setAdapter(new ListAdapter(getActivity(), R.id.lv_species, MyApplication.mListCName));

            return rootView;
        }

        public ArrayList<String> filterList(ArrayList<Species> list, String[] filters, boolean exclude) {
            ArrayList<String> species = new ArrayList<>();
            ArrayList<Integer> spId = new ArrayList<>();
            // get species id from keyword filter
            DBAdapter db;
            try {
                db = new DBAdapter(getActivity());
                spId = db.getSpId(filters);
                db.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // get species names from species list
            if (exclude) {
                Log.i("exclude: ",spId.toString());
                for (Species sp: list) {
                    boolean ex = false;
                    for (Integer i: spId) {
                        if (sp.getId() == i) ex = true;
                    }
                    if (!ex) species.add(sp.getcName());
                }
            }
            else {
                Log.i("exclude: ",spId.toString());
                for (Species sp: list) {
                    for (Integer i: spId) {
                        if (sp.getId() == i) species.add(sp.getcName());
                    }
                }
            }
            return species;
        }
    }

    /**
     * The plant sub-menu bar.
     */
    public static class PlantFragment extends Fragment {

        public PlantFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate layout
            View rootView = inflater.inflate(R.layout.fragment_plant, container, false);

            // initialize buttons with click function
            final Button treeBtn = (Button) rootView.findViewById(R.id.treeBtn);
            treeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Tree"};
                    ((Identify)getActivity()).loadIdFrag("plants", subcategory, false);
                }
            });

            final Button shrubBtn = (Button) rootView.findViewById(R.id.shrubBtn);
            shrubBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Shrub"};
                    ((Identify) getActivity()).loadIdFrag("plants", subcategory, false);
                }
            });

            final Button vineBtn = (Button) rootView.findViewById(R.id.vineBtn);
            vineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Vine"};
                    ((Identify)getActivity()).loadIdFrag("plants", subcategory, false);
                }
            });

            final Button grassBtn = (Button) rootView.findViewById(R.id.grassBtn);
            grassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Grass"};
                    ((Identify)getActivity()).loadIdFrag("plants", subcategory, false);
                }
            });

            final Button flowerBtn = (Button) rootView.findViewById(R.id.flowerBtn);
            flowerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Herb"};
                    ((Identify)getActivity()).loadIdFrag("plants", subcategory, false);
                }
            });

            final Button otherPlantBtn = (Button) rootView.findViewById(R.id.otherPlantBtn);
            otherPlantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Tree", "Shrub", "Vine", "Grass", "Herb"};
                    ((Identify)getActivity()).loadIdFrag("plants", subcategory, true);
                }
            });

            return rootView;
        }
    }

    /**
     * The animal sub-menu bar.
     */
    public static class AnimalFragment extends Fragment {

        public AnimalFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate layout
            View rootView = inflater.inflate(R.layout.fragment_animal, container, false);

            // initialize buttons with click function
            final Button birdBtn = (Button) rootView.findViewById(R.id.birdBtn);
            birdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Bird"};
                    ((Identify)getActivity()).loadIdFrag("animals", subcategory, false);
                }
            });

            final Button insectBtn = (Button) rootView.findViewById(R.id.insectBtn);
            insectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Insect"};
                    ((Identify)getActivity()).loadIdFrag("animals", subcategory, false);
                }
            });

            final Button mammalBtn = (Button) rootView.findViewById(R.id.mammalBtn);
            mammalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Mammal"};
                    ((Identify)getActivity()).loadIdFrag("animals", subcategory, false);
                }
            });

            final Button herpBtn = (Button) rootView.findViewById(R.id.herpBtn);
            herpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Reptile","Amphibian"};
                    ((Identify)getActivity()).loadIdFrag("animals", subcategory, false);
                }
            });

            final Button fishBtn = (Button) rootView.findViewById(R.id.fishBtn);
            fishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Fish"};
                    ((Identify)getActivity()).loadIdFrag("animals", subcategory, false);
                }
            });

            final Button otherAnimalBtn = (Button) rootView.findViewById(R.id.otherAnimalBtn);
            otherAnimalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] subcategory = {"Bird", "Insect", "Mammal", "Fish", "Reptile", "Amphibian"};
                    ((Identify)getActivity()).loadIdFrag("animals", subcategory, true);
                }
            });

            return rootView;
        }
    }

    /**
     * The fungi sub-menu bar.
     */
    public static class FungiFragment extends Fragment {

        public FungiFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate layout
            View rootView = inflater.inflate(R.layout.fragment_fungi, container, false);

            // load main fragment
            ((Identify)getActivity()).loadIdFrag("fungi", null, false);

            return rootView;
        }
    }

    /**
     * The fungi sub-menu bar.
     */
    public static class ProtistFragment extends Fragment {

        public ProtistFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate layout
            View rootView = inflater.inflate(R.layout.fragment_protist, container, false);

            // load main fragment
            ((Identify)getActivity()).loadIdFrag("protists", null, false);

            return rootView;
        }
    }

    /**
     * The invasive sub-menu bar.
     */
    public static class InvasiveFragment extends Fragment {

        public InvasiveFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate layout
            View rootView = inflater.inflate(R.layout.fragment_invasive, container, false);

            // load main fragment
            String [] subcategory = {"Invasive"};
            ((Identify)getActivity()).loadIdFrag("plants", subcategory, false);

            return rootView;
        }
    }

    /**
     * List adapter
     */
    public static class ListAdapter extends ArrayAdapter<String> {

        private ArrayList<String> mList;
        Context c;

        public ListAdapter(Context context, int textViewResourceId, ArrayList<String> list) {
            super(context, textViewResourceId, list);
            mList = list;
            c = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View view = convertView;
            try{
                if (view == null) {
                    LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = vi.inflate(R.layout.list_item, parent, false);
                }
                final String cName = mList.get(position);
                if (cName != null) {
                    // setting list_item views
                    ( (TextView) view.findViewById(R.id.tv_name) ).setText(cName);
                    // go to individual species page
                    ( view.findViewById(R.id.tv_name) ).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                        }
                    });
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return view;
        }
    }
}
