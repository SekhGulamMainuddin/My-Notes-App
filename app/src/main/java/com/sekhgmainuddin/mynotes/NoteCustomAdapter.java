package com.sekhgmainuddin.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteCustomAdapter extends ListAdapter<Note,NoteCustomAdapter.NoteViewHolder> {
    private OnItemClickedListener listener;

    protected NoteCustomAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_own_layout,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote=getItem(position);
        holder.textView.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
        holder.priority.setText(String.valueOf(currentNote.getId()));
    }

    public Note getNoteAt(int position){
        return getItem(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private TextView description;
        private TextView priority;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.TextView);
            description=itemView.findViewById(R.id.description);
            priority=itemView.findViewById(R.id.priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION){
                        listener.OnItemClicked(getItem(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickedListener{
        void OnItemClicked(Note note);
    }
    public void SetOnItemClickedListener(OnItemClickedListener listener){
        this.listener=listener;
    }
}
