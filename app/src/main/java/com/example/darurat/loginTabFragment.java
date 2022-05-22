package com.example.darurat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginTabFragment extends Fragment implements View.OnClickListener {

    private EditText editTextEmail, editTextpassword;
    private Button masuk;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        masuk = root.findViewById(R.id.masuk);
        masuk.setOnClickListener(this);

        editTextEmail = root.findViewById(R.id.Email);
        editTextpassword = root.findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.masuk:
                SignIn();
                break;
        }
    }

    private void SignIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email masih kosong!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Masukkan format email yang benar!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("Password masih kosong!");
            editTextpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getActivity(), Navbar.class));
                }
                else {
                    Toast.makeText(getActivity(), "Gagal melakukan login, coba cek informasi kembali!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
