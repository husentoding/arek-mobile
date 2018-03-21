package com.example.husen.motionqrscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPeserta extends AppCompatActivity {

    RecyclerView recyclerView;
    PesertaAdapter adapter;
    List<Peserta> list= new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Peserta");

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_peserta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.listPeserta);
        adapter = new PesertaAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        prepareData();



    }

    private void prepareData() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot key: dataSnapshot.getChildren()){
                    String nama = key.child("nama").getValue(String.class);
                    Peserta p = new Peserta(nama);
                    list.add(p);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            for (DataSnapshot key: dataSnapshot.getChildren()){
//
//
//                    list.add(new Peserta(key.getKey()));
//
//            }
//            adapter.notifyDataSetChanged();
////                for (DataSnapshot post: dataSnapshot.getChildren()){
////                    Peserta p = new Peserta(post.getKey().toString());
////                    list.add(p);
////                    adapter.notifyDataSetChanged();
////                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });

        adapter.notifyDataSetChanged();

    }

    public class PesertaAdapter extends RecyclerView.Adapter<PesertaAdapter.MyViewHolder>{
        private List<Peserta> pesertaList;
        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView name;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.namapeserta);
            }
        }

        public PesertaAdapter(List<Peserta> pesertaList) {
            this.pesertaList = pesertaList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler_peserta, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Peserta p = pesertaList.get(position);
            String text = Integer.toString(position)+". "+p.getName();
            holder.name.setText(text);

        }

        @Override
        public int getItemCount() {
            return pesertaList.size();
        }


    }
}
