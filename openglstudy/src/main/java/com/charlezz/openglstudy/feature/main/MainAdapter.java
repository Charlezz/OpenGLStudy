package com.charlezz.openglstudy.feature.main;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.charlezz.openglstudy.databinding.ViewMainMenuBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainMenu> items;

    public MainAdapter(List<MainMenu> items){
        this.items = items;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new MainViewHolder(ViewMainMenuBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int position) {
        mainViewHolder.binding.setItem(items.get(position));
        mainViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder{
        ViewMainMenuBinding binding;
        public MainViewHolder(ViewMainMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
