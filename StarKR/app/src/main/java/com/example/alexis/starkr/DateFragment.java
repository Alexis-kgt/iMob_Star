package com.example.alexis.starkr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.alexis.starkr.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "zzzzz";
    TextView date, time;
    OnDirectionSelectedListener mCallback;

    public void setOnDirectionSelectedListener(Activity activity) {
        mCallback = (OnDirectionSelectedListener) activity;
    }

    // Container Activity must implement this interface
    public interface OnDirectionSelectedListener {
        void refreshDateFragmentDate(String date);
        void refreshDateFragmentTime(String time);
        void onDirectionSelected(int position);
    }

//    @Override
//    public void onDirectionItemClick(ListView l, View v, int position, long id) {
//        // Send the event to the host activity
//        mCallback.onDirectionSelected(position);
//    }


    public DateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateFragment newInstance() {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_date, container, false);

        time = v.findViewById(R.id.timeView);
        date = v.findViewById(R.id.dateView);
        Button dateBtn = v.findViewById(R.id.datePickerBtn);
        Button timeBtn = v.findViewById(R.id.timePickerBtn);
        timeBtn.setOnClickListener(timeClickListener);
        dateBtn.setOnClickListener(dateClickListener);

        Spinner lignesSpinner = v.findViewById(R.id.lignesSpinner);
        lignesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                populateDirectionsSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        Spinner directionsSpinner = v.findViewById(R.id.directionsSpinner);
        directionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //TODO récupérer les arrêts du bus;

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//                StopFragment stopFragment = new StopFragment();
//                fragmentTransaction.add(R.id.stopFragment, stopFragment);
//                fragmentTransaction.commit();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void populateDirectionsSpinner() {
        Spinner lignesSpinner = getView().findViewById(R.id.lignesSpinner);
        String ligne = ((TextView) lignesSpinner.getSelectedView()).getText().toString();
        String ligne2 = ligne.split(":")[1];
        String direction1 = ligne2.split("<>")[0];
        String direction2 = ligne2.split("<>")[ligne2.split("<>").length - 1];

        final String[] directions = new String[3];
        directions[0] = "";
        directions[1] = direction1;
        directions[2] = direction2;
        Spinner directionsSpinner = getView().findViewById(R.id.directionsSpinner);
        directionsSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.ligne_item_spinner, directions));
    }

    private View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogDatePicker();
        }
    };
    private View.OnClickListener timeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogTimePicker();
        }
    };

    public void setSpinnerContent(){
        Spinner ligneSpinner = getActivity().findViewById(R.id.lignesSpinner);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ROUTES", null);
        ArrayList<String> lignes = new ArrayList();
        if (c.moveToFirst()) {
            do {
                lignes.add(c.getString(2) + " : " + c.getString(3) + "_____" + c.getString(7) + "_____" + c.getString(8));
            } while (c.moveToNext());
        }
        final String[] lignesArray = new String[lignes.size()];
        int cpt = 0;
        for (String l : lignes) {
            lignesArray[cpt] = l;
            cpt++;
        }

        ligneSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.ligne_item_spinner, lignesArray) {

            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setText(lignesArray[position].split("_____")[0]);
                tv.setBackgroundColor(Color.parseColor("#" + lignesArray[position].split("_____")[1]));
                tv.setTextColor(Color.parseColor("#" + lignesArray[position].split("_____")[2]));

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.ligne_item_spinner, null);
                }

                TextView tv = (TextView) v.findViewById(R.id.ligneItem);
                tv.setText(lignesArray[position].split("_____")[0]);
                tv.setBackgroundColor(Color.parseColor("#" + lignesArray[position].split("_____")[1]));
                tv.setTextColor(Color.parseColor("#" + lignesArray[position].split("_____")[2]));
                return v;
            }
        });
    }
    /**
     * Créer une boite de dialogue pour renseigner la date
     */
    public void createDialogDatePicker() {
        LayoutInflater li = LayoutInflater.from(this.getContext());
        View promptsView = li.inflate(R.layout.date_picker, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        int jour = Integer.parseInt(date.getText().toString().split("/")[0]);
        int mois = Integer.parseInt(date.getText().toString().split("/")[1]);
        int annee = Integer.parseInt(date.getText().toString().split("/")[2]);
        final DatePicker dp = promptsView.findViewById(R.id.datePicker);
        dp.updateDate(annee, mois - 1, jour);
        alertDialogBuilder
                .setCancelable(false)
                //Valider l'étiquette
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String day, month;
                                if(dp.getDayOfMonth() < 10)
                                    day = "0"+dp.getDayOfMonth();
                                else
                                    day = ""+dp.getDayOfMonth();
                                mCallback.refreshDateFragmentDate(day + "/" + dp.getMonth()+1+ "/" + dp.getYear());
                            }
                        })
                //L'arc n'a pas d'étiquette
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    /**
     * Créer une boite de dialogue pour renseigner l'heure
     */
    public void createDialogTimePicker() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.time_picker, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        int heure = Integer.parseInt(time.getText().toString().split(":")[0]);
        int minutes = Integer.parseInt(time.getText().toString().split(":")[1]);
        final TimePicker tp = promptsView.findViewById(R.id.timePicker);
        tp.setHour(heure);
        tp.setMinute(minutes);
        alertDialogBuilder
                .setCancelable(false)
                //Valider l'étiquette
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String hour, minutes;
                                if(tp.getHour() < 10)
                                    hour = "0"+tp.getHour();
                                else
                                    hour = ""+tp.getHour();
                                if(tp.getMinute() < 10)
                                    minutes = "0"+tp.getMinute();
                                else
                                    minutes = ""+tp.getMinute();
                                mCallback.refreshDateFragmentTime(hour+ ":" + minutes);
                            }
                        })
                //L'arc n'a pas d'étiquette
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
