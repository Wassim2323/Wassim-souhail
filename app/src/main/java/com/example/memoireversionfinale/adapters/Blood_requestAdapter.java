package com.example.memoireversionfinale.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.memoireversionfinale.viemodele.customuserdata;
import com.example.memoireversionfinale.R;
import com.example.memoireversionfinale.viemodele.customuserdata;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Blood_requestAdapter extends RecyclerView.Adapter<Blood_requestAdapter.PostHolder> {
    private List<customuserdata> postLists;




    public class PostHolder extends RecyclerView.ViewHolder
    {
        TextView Name, bloodgroup, Address, contact, posted;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.reqstUser);
            contact = itemView.findViewById(R.id.targetCN);
            bloodgroup = itemView.findViewById(R.id.targetBG);
            Address = itemView.findViewById(R.id.reqstLocation);
            posted = itemView.findViewById(R.id.posted);

        }
    }

    public Blood_requestAdapter(List<customuserdata> postLists)
    {
        this.postLists = postLists;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View listitem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.request_list_item, viewGroup, false);

        return new PostHolder(listitem);
    }

    @Override
    public void onBindViewHolder(PostHolder postHolder, int i) {

        if(i%2==0)
        {
            postHolder.itemView.setBackgroundColor(Color.parseColor("#C13F31"));
        }
        else
        {
            postHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        customuserdata customUserData = postLists.get(i);
        postHolder.Name.setText("Posted by: "+customUserData.getName());
        postHolder.Address.setText("From: "+customUserData.getAddress()+", "+customUserData.getDivision());
        postHolder.bloodgroup.setText("Needs "+customUserData.getBloodGroup());
        postHolder.posted.setText("Posted on:"+customUserData.getTime()+", "+customUserData.getDate());
        postHolder.contact.setText(customUserData.getContact());

    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

}

