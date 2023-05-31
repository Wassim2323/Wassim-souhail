package com.example.memoireversionfinale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.memoireversionfinale.adapters.SearcheDonoradapter;
import com.example.memoireversionfinale.viemodele.donordata;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class searche_donor extends Fragment {

    private View view;

    FirebaseAuth mAuth;
    FirebaseUser fuser;
    FirebaseDatabase fdb;
    DatabaseReference db_ref, user_ref;

    Spinner bloodgroup, division;
    Button btnsearch;
    ProgressDialog pd;
    List<donordata> donorItem;
    private RecyclerView recyclerView;

    private SearcheDonoradapter sdadapter;

    public searche_donor() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_searche_donor, container, false);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);




        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        fdb = FirebaseDatabase.getInstance();
        db_ref = fdb.getReference("donors");

        bloodgroup = view.findViewById(R.id.btngetBloodGroup);
        division = view.findViewById(R.id.btngetDivison);
        btnsearch = view.findViewById(R.id.btnSearch);

        getActivity().setTitle("Find Blood Donor");

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                donorItem = new ArrayList<>();
                sdadapter = new SearcheDonoradapter(donorItem);
                recyclerView = view.findViewById(R.id.showDonorList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                RecyclerView.LayoutManager searchdonor = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(searchdonor);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(sdadapter);
                Query qpath  = db_ref.child(division.getSelectedItem().toString())
                        .child(bloodgroup.getSelectedItem().toString());
                qpath.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot singleitem : dataSnapshot.getChildren())
                            {
                                donordata donorData = singleitem.getValue(donordata.class);
                                donorItem.add(donorData);
                                sdadapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {

                            Toast.makeText(getActivity(), "Database is empty now!",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());

                    }
                });
                pd.dismiss();
            }
        });
        return view;
    }

}
