package com.example.firebaseinit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.studentViewHolder>{

    //ArrayList

    ArrayList<ClsStudent> studentsList;

    public AdapterStudent(ArrayList<ClsStudent> studentsList) {
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public AdapterStudent.studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentresource,null,false);
        return new AdapterStudent.studentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStudent.studentViewHolder holder, int position) {
        holder.tvid.setText(studentsList.get(position).getId());
        holder.tvname.setText(studentsList.get(position).getFullName());
        holder.tvmajor.setText(studentsList.get(position).getMajor());
        holder.tvsemester.setText(studentsList.get(position).getSemester());
        //System.out.println("debugger"+studentsList.get(position).getCheckBox().equals("Yes"));
        //Review the setchecked
        if(studentsList.get(position).getCheckBox().equals("Yes")){
            holder.cbactive.setChecked(true);
        }else{
            holder.cbactive.setChecked(false);
        }







    }

    @Override
    public int getItemCount() {

        return studentsList.size();
    }

    //Everything related to the cardview
    public static class studentViewHolder extends RecyclerView.ViewHolder {
        TextView tvid, tvname, tvsemester, tvmajor;
        CheckBox cbactive;


        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvmajor = itemView.findViewById(R.id.tvmajor);
            tvsemester = itemView.findViewById(R.id.tvsemester);
            cbactive = itemView.findViewById(R.id.cbactive);
        }
    }
}
