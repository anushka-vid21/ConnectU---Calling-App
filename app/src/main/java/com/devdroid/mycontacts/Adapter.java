package com.devdroid.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Model>modelArrayList;

    public Adapter(Context context,ArrayList<Model>modelArrayList){
        this.context=context;
        this.modelArrayList=modelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(modelArrayList.get(position).getName());
        holder.number.setText(modelArrayList.get(position).getNumber());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number =modelArrayList.get(position).getNumber();
                Intent in = new Intent(Intent.ACTION_CALL);

                in.setData(Uri.parse("tel:"+number));
                context.startActivity(in);
            }
        });

        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText message =new EditText(view.getContext());
                message.setHint("TYPE YOUR MESSAGE HERE");
                Intent in = new Intent(Intent.ACTION);
            }
        });

    }

    @Override
    public int getItemCount() {return modelArrayList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView call,sms;


        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            number=itemView.findViewById(R.id.item_number);
            call = itemView.findViewById(R.id.image_call);
            sms = itemView.findViewById(R.id.image_sms);
        }

    }
}
