package com.example.darurat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AkunFragment extends Fragment {

    private FirebaseUser User;
    private DatabaseReference reference;
    private String userID;
    private TextView namalengkapTextView, nomortelponTextView;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_akun, container, false);

        User = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://authentication-be458-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        userID = User.getUid();

        namalengkapTextView = root.findViewById(R.id.namalengkap);
        nomortelponTextView = root.findViewById(R.id.nomortelpon);
        logout = root.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), login.class));
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user userProfile = snapshot.getValue(user.class);

                if (userProfile != null){
                    String namalengkap = userProfile.nama;
                    String nomortelpon = userProfile.nomortelepon;

                    namalengkapTextView.setText(namalengkap);
                    nomortelponTextView.setText(nomortelpon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}