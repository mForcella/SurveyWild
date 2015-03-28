package edu.newpaltz.surveywild;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
                loadPlantFrag();
            }
        });

        final Button animalBtn = (Button) findViewById(R.id.animalBtn);
        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAnimalFrag();
            }
        });

        final Button fungiBtn = (Button) findViewById(R.id.fungiBtn);
        fungiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIdFrag("fungi");
            }
        });

        final Button invasiveBtn = (Button) findViewById(R.id.invasiveBtn);
        invasiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadIdFrag("invasive");
            }
        });
    }

    /**
     * Functions for loading fragments.
     */
    public void loadIdFrag(String category) {
        IdentifyFragment identify = new IdentifyFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_page, identify).commit();
        TextView viewing = (TextView)findViewById(R.id.viewing);
        viewing.setText("Viewing " + category + " in your area.");
    }

    public void loadPlantFrag() {
        PlantFragment plant = new PlantFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sub_menu, plant).commit();
    }

    public void loadAnimalFrag() {
        AnimalFragment animal = new AnimalFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sub_menu, animal).commit();
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
            //View rootView = inflater.inflate(R.layout.fragment_animal, container, false);
            return inflater.inflate(R.layout.fragment_identify, container, false);
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
                    ((Identify)getActivity()).loadIdFrag("tree");
                }
            });

            final Button shrubBtn = (Button) rootView.findViewById(R.id.shrubBtn);
            shrubBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("shrub");
                }
            });

            final Button vineBtn = (Button) rootView.findViewById(R.id.vineBtn);
            vineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("vine");
                }
            });

            final Button grassBtn = (Button) rootView.findViewById(R.id.grassBtn);
            grassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("grass");
                }
            });

            final Button flowerBtn = (Button) rootView.findViewById(R.id.flowerBtn);
            flowerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("flower");
                }
            });

            final Button otherPlantBtn = (Button) rootView.findViewById(R.id.otherPlantBtn);
            otherPlantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("other_plant");
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
                    ((Identify)getActivity()).loadIdFrag("bird");
                }
            });

            final Button insectBtn = (Button) rootView.findViewById(R.id.insectBtn);
            insectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("insect");
                }
            });

            final Button mammalBtn = (Button) rootView.findViewById(R.id.mammalBtn);
            mammalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("mammal");
                }
            });

            final Button otherAnimalBtn = (Button) rootView.findViewById(R.id.otherAnimalBtn);
            otherAnimalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("other_animal");
                }
            });

            return rootView;
        }
    }
}
