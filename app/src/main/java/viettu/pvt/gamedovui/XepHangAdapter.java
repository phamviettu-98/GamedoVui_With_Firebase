package viettu.pvt.gamedovui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class XepHangAdapter extends RecyclerView.Adapter<XepHangAdapter.MyViewHolder> {
    ArrayList<User> usersList;

    public XepHangAdapter(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public XepHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thong_tin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XepHangAdapter.MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
       TextView name, stt, diemso;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namr_rank);
            stt = itemView.findViewById(R.id.stt);
            diemso = itemView.findViewById(R.id.diem_xh);
        }
        public void bindView(int position){
            User u = usersList.get(position);
            stt.setText(""+(position+1));
            name.setText(u.getName());
            diemso.setText(""+u.getDiem());
        }
    }
}
