package com.sung.testdemo.metiraldesign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sung.testdemo.R;
import com.sung.testdemo.metiraldesign.appbarlayout.AppbarActivity;
import com.sung.testdemo.metiraldesign.fab.FABActivity;
import com.sung.testdemo.metiraldesign.toolbar.ToolBarActivity;

import java.util.ArrayList;
import java.util.List;

public class MetiralListActivity extends AppCompatActivity {
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final List<String> data = new ArrayList();
        data.add("Toolbar");
        data.add("AppbarLayout");
        data.add("FAB");

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        list.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                Holder holder1 = (Holder) holder;
                holder1.text.setText(data.get(position));
                holder1.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0){
                            startActivity(new Intent(MetiralListActivity.this, ToolBarActivity.class));
                        }
                        if (position == 1){
                            startActivity(new Intent(MetiralListActivity.this, AppbarActivity.class));
                        }
                        if (position == 2){
                            startActivity(new Intent(MetiralListActivity.this, FABActivity.class));
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView text;

        public Holder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
