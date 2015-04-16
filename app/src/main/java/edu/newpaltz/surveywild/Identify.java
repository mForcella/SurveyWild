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
import java.util.ArrayList;

public class Identify extends ActionBarActivity {

    String TAG_FRAGMENT = "main";

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
                loadIdFrag("plants", null);
            }
        });

        final Button animalBtn = (Button) findViewById(R.id.animalBtn);
        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new AnimalFragment());
                loadIdFrag("animals", null);
            }
        });

        final Button fungiBtn = (Button) findViewById(R.id.fungiBtn);
        fungiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new FungiFragment());
                loadIdFrag("fungi", null);
            }
        });

        final Button protistBtn = (Button) findViewById(R.id.protistBtn);
        protistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new ProtistFragment());
                loadIdFrag("protists", null);
            }
        });

        final Button invasiveBtn = (Button) findViewById(R.id.invasiveBtn);
        invasiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new InvasiveFragment());
                loadIdFrag("plants", "invasive");
            }
        });
    }

    /**
     * Functions for loading fragments.
     */
    public void loadIdFrag(String category, String subcategory) {
        IdentifyFragment identify = new IdentifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        if (subcategory != null)
            bundle.putString("subcategory", subcategory);
        identify.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_page, identify, TAG_FRAGMENT).commit();
    }

    public void removeIdFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
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
            String subcategory = bundle.getString("subcategory", null);
            TextView viewing = (TextView) rootView.findViewById(R.id.viewing);
            viewing.setText("Viewing " + category + " in your area.");

            // get list of species
            DBAdapter db;
            try {
                db = new DBAdapter(getActivity());
                MyApplication.mListSpecies = db.getSpecies(category);
                db.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // initialize list view
            ListView lvSpecies = (ListView)rootView.findViewById(R.id.lv_species);
            lvSpecies.setAdapter(new ListAdapter(getActivity(), R.id.lv_species, MyApplication.mListSpecies));

            // check for subcategory

            // populate list view

            return rootView;
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
                    ((Identify)getActivity()).loadIdFrag("plants", "trees");
                }
            });

            final Button shrubBtn = (Button) rootView.findViewById(R.id.shrubBtn);
            shrubBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify) getActivity()).loadIdFrag("plants", "shrubs");
                }
            });

            final Button vineBtn = (Button) rootView.findViewById(R.id.vineBtn);
            vineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("plants", "vines");
                }
            });

            final Button grassBtn = (Button) rootView.findViewById(R.id.grassBtn);
            grassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("plants", "grasses");
                }
            });

            final Button flowerBtn = (Button) rootView.findViewById(R.id.flowerBtn);
            flowerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("plants", "flowers");
                }
            });

            final Button otherPlantBtn = (Button) rootView.findViewById(R.id.otherPlantBtn);
            otherPlantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("plants", "other plants");
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
                    ((Identify)getActivity()).loadIdFrag("animals", "birds");
                }
            });

            final Button insectBtn = (Button) rootView.findViewById(R.id.insectBtn);
            insectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("animals", "insects");
                }
            });

            final Button mammalBtn = (Button) rootView.findViewById(R.id.mammalBtn);
            mammalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("animals", "mammals");
                }
            });

            final Button herpBtn = (Button) rootView.findViewById(R.id.herpBtn);
            herpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("animals", "reptiles & amphibians");
                }
            });

            final Button fishBtn = (Button) rootView.findViewById(R.id.fishBtn);
            fishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("animals", "fish");
                }
            });

            final Button otherAnimalBtn = (Button) rootView.findViewById(R.id.otherAnimalBtn);
            otherAnimalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("animals", "other animals");
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
            ((Identify)getActivity()).loadIdFrag("fungi", null);

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
            ((Identify)getActivity()).loadIdFrag("protists", null);

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
            ((Identify)getActivity()).loadIdFrag("plants", "invasives");

            return rootView;
        }
    }

    /**
     * List adapter
     */
    public static class ListAdapter extends ArrayAdapter<Species> {

        private ArrayList<Species> mList;
        Context c;

        // constructor, assigns mListObjects to mList
        public ListAdapter(Context context, int textViewResourceId, ArrayList<Species> list) {
            super(context, textViewResourceId, list);
            mList = list;
            c = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View view = convertView;
            try{
                if (view == null) {
                    LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = vi.inflate(R.layout.list_item, parent, false);
                }
                final Species species = mList.get(position);
                if (species != null) {
                    String cName = species.getcName();
                    // setting list_item views
                    ( (TextView) view.findViewById(R.id.tv_name) ).setTextSize(20);
                    ( (TextView) view.findViewById(R.id.tv_name) ).setText(cName);
                    // go to individual sighting page
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
