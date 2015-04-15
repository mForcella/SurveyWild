package edu.newpaltz.surveywild;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
            }
        });

        final Button animalBtn = (Button) findViewById(R.id.animalBtn);
        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new AnimalFragment());
            }
        });

        final Button fungiBtn = (Button) findViewById(R.id.fungiBtn);
        fungiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new FungiFragment());
                loadIdFrag("fungi");
            }
        });

        final Button invasiveBtn = (Button) findViewById(R.id.invasiveBtn);
        invasiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubFrag(new InvasiveFragment());
                loadIdFrag("invasive");
            }
        });
    }

    /**
     * Functions for loading fragments.
     */
    public void loadIdFrag(String category) {
        IdentifyFragment identify = new IdentifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
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
            TextView viewing = (TextView) rootView.findViewById(R.id.viewing);
            // get category and set text view
            Bundle bundle = this.getArguments();
            String category = bundle.getString("category", null);
            viewing.setText("Viewing " + category + " in your area.");
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
                    ((Identify)getActivity()).loadIdFrag("trees");
                }
            });

            final Button shrubBtn = (Button) rootView.findViewById(R.id.shrubBtn);
            shrubBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("shrubs");
                }
            });

            final Button vineBtn = (Button) rootView.findViewById(R.id.vineBtn);
            vineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("vines");
                }
            });

            final Button grassBtn = (Button) rootView.findViewById(R.id.grassBtn);
            grassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("grasses");
                }
            });

            final Button flowerBtn = (Button) rootView.findViewById(R.id.flowerBtn);
            flowerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("flowers");
                }
            });

            final Button otherPlantBtn = (Button) rootView.findViewById(R.id.otherPlantBtn);
            otherPlantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("plants (other)");
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
                    ((Identify)getActivity()).loadIdFrag("birds");
                }
            });

            final Button insectBtn = (Button) rootView.findViewById(R.id.insectBtn);
            insectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("insects");
                }
            });

            final Button mammalBtn = (Button) rootView.findViewById(R.id.mammalBtn);
            mammalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("mammals");
                }
            });

            final Button otherAnimalBtn = (Button) rootView.findViewById(R.id.otherAnimalBtn);
            otherAnimalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Identify)getActivity()).loadIdFrag("animals (other)");
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
            ((Identify)getActivity()).loadIdFrag("fungi");

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
            ((Identify)getActivity()).loadIdFrag("invasives");

            return rootView;
        }
    }
}
