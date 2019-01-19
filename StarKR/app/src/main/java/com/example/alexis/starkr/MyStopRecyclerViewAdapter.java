package com.example.alexis.starkr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexis.starkr.StopFragment.OnStopListFragmentInteractionListener;
import com.example.alexis.starkr.model.Stop;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Stop} and makes a call to the
 * specified {@link OnStopListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStopRecyclerViewAdapter extends RecyclerView.Adapter<MyStopRecyclerViewAdapter.ViewHolder> {

    private final List<Stop> mValues;
    private final OnStopListFragmentInteractionListener mListener;

    public MyStopRecyclerViewAdapter(List<Stop> items, OnStopListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getStop_code());
        holder.mContentView.setText(mValues.get(position).getStop_name());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onStopListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Stop mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.stop_number);
            mContentView = (TextView) view.findViewById(R.id.stop_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
