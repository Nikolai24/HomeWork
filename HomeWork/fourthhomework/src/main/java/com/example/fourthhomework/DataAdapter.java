package com.example.fourthhomework;

import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private final List<Item> items;
    private final List<Item> itemsAll;
    Context mContext;

    public DataAdapter (Context context, List<Item> items) {
        this.items = items;
        this.itemsAll = items;
        mContext = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView, contactView;
        private ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            contactView = itemView.findViewById(R.id.contact);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameView.setText(item.getName());
        holder.contactView.setText(item.getContact());
        holder.imageView.setImageResource(item.getImage());

        holder.itemView.setOnClickListener(
                view -> {
                    Intent intent = new Intent(mContext, EditContactActivity.class);
                    intent.putExtra("name", item.getName());
                    intent.putExtra("contact", item.getContact());
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(itemsAll);
            } else {
                for (Item movie: itemsAll) {
                    if (movie.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((Collection<? extends Item>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}