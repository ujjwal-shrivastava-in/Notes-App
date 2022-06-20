package com.example.mnotes.Adapters;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnotes.Models.Notes;
import com.example.mnotes.Models.NotesClickListeners;
import com.example.mnotes.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{
    Context context;
    List<Notes> list;
    NotesClickListeners listeners;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListeners listeners) {
        this.context = context;
        this.list = list;
        this.listeners = listeners;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(list.get(position).getNotes());
        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (list.get(position).isPinned()){
            holder.imageView_pin.setImageResource(R.drawable.pin_vector);
        }
        else{
            holder.imageView_pin.setImageResource(0);
        }
        int color_code = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeners.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listeners.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_container);
                return true;
            }
        });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color6);
        colorCode.add(R.color.white);
        colorCode.add(R.color.color6);
        colorCode.add(R.color.white);
        colorCode.add(R.color.color6);
        colorCode.add(R.color.white);

        Random random = new Random();
        int random_colors = random.nextInt(colorCode.size());
        return colorCode.get(random_colors);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView textView_title,textView_notes,textView_date;
    ImageView imageView_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        textView_date = itemView.findViewById(R.id.textview_date);
        textView_notes = itemView.findViewById(R.id.textview_notes);
        textView_title = itemView.findViewById(R.id.textview_title);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}
