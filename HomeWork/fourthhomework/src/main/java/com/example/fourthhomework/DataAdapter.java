package com.example.fourthhomework;

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
    private MainActivity.OnItemClickListener listener;

    public DataAdapter (List<Item> items, MainActivity.OnItemClickListener listener) {
        this.items = new ArrayList<>(items);
        this.itemsAll = new ArrayList<>(items);
        this.listener = listener;
    }

    public void setItems(List<Item> itemsNew) {
        items.clear();
        itemsAll.clear();
        items.addAll(itemsNew);
        itemsAll.addAll(itemsNew);
        notifyDataSetChanged();
    }

    public void editItem(String name, String contact, int position) {
        items.get(position).setName(name);
        items.get(position).setContact(contact);
        itemsAll.get(position).setName(name);
        itemsAll.get(position).setContact(contact);
        notifyItemChanged(position);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, position);
            }
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