package com.example.memoireversionfinale.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.memoireversionfinale.R;
import com.example.memoireversionfinale.viemodele.donordata;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearcheDonoradapter extends RecyclerView.Adapter<SearcheDonoradapter.PostHolder> {
    private List<donordata> postLists;

    public SearcheDonoradapter(List<donordata> donorItem) {
    }

    public static class PostHolder extends RecyclerView.ViewHolder
    {
        TextView Name, Address, contact, posted, totaldonate;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.donorName);
            contact = itemView.findViewById(R.id.donorContact);
            totaldonate = itemView.findViewById(R.id.totaldonate);
            Address = itemView.findViewById(R.id.donorAddress);
            posted = itemView.findViewById(R.id.lastdonate);

        }
    }

    public void SearchDonorAdapter(List<donordata> postLists)
    {
        this.postLists = postLists;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View listitem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_donor_item, viewGroup, false);

        return new PostHolder(listitem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, int i) {

        if(i%2==0)
        {
            postHolder.itemView.setBackgroundColor(Color.parseColor("#C13F31"));
        }
        else
        {
            postHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        donordata donordata = postLists.get(i);
        postHolder.Name.setText("Name: "+donordata.getName());
        postHolder.contact.setText(donordata.getContact());
        postHolder.Address.setText("Address: "+donordata.getAddress());
        postHolder.totaldonate.setText("Total Donation: "+donordata.getTotalDonate()+" times");
        postHolder.posted.setText("Last Donation: "+donordata.getLastDonate());


    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
