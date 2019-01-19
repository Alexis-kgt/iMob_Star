package com.example.alexis.starkr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "zzzzz";
    OnDirectionSelectedListener mCallback;

    public void setOnDirectionSelectedListener(Activity activity) {
        mCallback = (OnDirectionSelectedListener) activity;
    }

    // Container Activity must implement this interface
    public interface OnDirectionSelectedListener {
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


        TextView time = v.findViewById(R.id.timeView);
        TextView date = v.findViewById(R.id.dateView);

        int annee = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int mois = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
        int jour = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        int heure = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        int minutes = java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE);

        time.setText(heure + ":" + minutes);
        date.setText(jour + "/" + mois + "/" + annee);

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

    /**
     * Créer une boite de dialogue pour renseigner la date
     */
    public void createDialogDatePicker(View v) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.date_picker, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        final TextView dateView = getView().findViewById(R.id.dateView);
        int jour = Integer.parseInt(dateView.getText().toString().split("/")[0]);
        int mois = Integer.parseInt(dateView.getText().toString().split("/")[1]);
        int annee = Integer.parseInt(dateView.getText().toString().split("/")[2]);
        final DatePicker dp = promptsView.findViewById(R.id.datePicker);
        dp.updateDate(annee, mois - 1, jour);
        alertDialogBuilder
                .setCancelable(false)
                //Valider l'étiquette
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dateView.setText(dp.getDayOfMonth() + "/" + (dp.getMonth() + 1) + "/" + dp.getYear());
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
    public void createDialogTimePicker(View v) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.time_picker, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptsView);
        final TextView timeView = getView().findViewById(R.id.timeView);
        int heure = Integer.parseInt(timeView.getText().toString().split(":")[0]);
        int minutes = Integer.parseInt(timeView.getText().toString().split(":")[1]);
        final TimePicker tp = promptsView.findViewById(R.id.timePicker);
        tp.setHour(heure);
        tp.setMinute(minutes);
        alertDialogBuilder
                .setCancelable(false)
                //Valider l'étiquette
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                timeView.setText(tp.getHour() + ":" + tp.getMinute());
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
