package com.example.firebaseinit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewHolder>{

    //ArrayList

    ArrayList<ClsStudent> studentsList;

    public StudentAdapter(ArrayList<ClsStudent> studentsList) {
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public StudentAdapter.studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentresource,null,false);
        return new StudentAdapter.studentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.studentViewHolder holder, int position) {
        holder.tvid.setText(studentsList.get(position).getId().toString());
        holder.tvname.setText(studentsList.get(position).getFullName().toString());
        holder.tvmajor.setText(studentsList.get(position).getMajor().toString());
        holder.tvsemester.setText(studentsList.get(position).getSemester().toString());
        //Review the setchecked
        //holder.checkBox.setSelected(studentsList.get(position).getCheckBox().isEmpty());






    }

    @Override
    public int getItemCount() {

        return studentsList.size();
    }

    //Everything related to the cardview
    public static class studentViewHolder extends RecyclerView.ViewHolder {
        TextView tvid, tvname, tvsemester, tvmajor;
        CheckBox checkBox;


        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvmajor = itemView.findViewById(R.id.tvmajor);
            tvsemester = itemView.findViewById(R.id.tvsemester);
            checkBox = itemView.findViewById(R.id.resourcecheckBox);
        }
    }
}
