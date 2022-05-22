package com.example.darurat;

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
import com.google.firebase.database.FirebaseDatabase;

public class signupTabFragment extends Fragment implements View.OnClickListener {
    private Button buttondaftar;
    private EditText editTextNama, editTextNomorTelepon, editTextEmail, editTextpassword, editTextkonfirmasipassword;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        buttondaftar = root.findViewById(R.id.buttondaftar);
        buttondaftar.setOnClickListener(this);

        editTextNama = root.findViewById(R.id.Nama);
        editTextNomorTelepon = root.findViewById(R.id.NomorTelepon);
        editTextEmail = root.findViewById(R.id.Email);
        editTextpassword = root.findViewById(R.id.password);
        editTextkonfirmasipassword = root.findViewById(R.id.konfirmasipassword);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttondaftar:
                SignUp();
                break;
        }
    }

    private void SignUp(){
        String nama = editTextNama.getText().toString().trim();
        String nomortelepon = editTextNomorTelepon.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String konfirmasipassword = editTextkonfirmasipassword.getText().toString().trim();

        if(nama.isEmpty()){
            editTextNama.setError("Nama masih kosong!");
            editTextNama.requestFocus();
            return;
        }

        if(nomortelepon.isEmpty()){
            editTextNomorTelepon.setError("Nomor Telepon masih kosong!");
            editTextNomorTelepon.requestFocus();
            return;
        }

        if(!Patterns.PHONE.matcher(nomortelepon).matches()){
            editTextNomorTelepon.setError("Masukkan Nomor Telepon yang benar!");
            editTextNomorTelepon.requestFocus();
            return;
        }

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

        if(password.length() < 6){
            editTextpassword.setError("Password yang dimasukkan terlalu pendek!");
            editTextpassword.requestFocus();
            return;
        }

        if(!konfirmasipassword.equals(password)){
            editTextkonfirmasipassword.setError("Konfirmasi Password salah!");
            editTextkonfirmasipassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        user user = new user(nama, nomortelepon, email);

                        FirebaseDatabase.getInstance("https://authentication-be458-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Pengguna berhasil di-daftarkan!", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getActivity(), "Pengguna gagal untuk di-daftarkan!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getActivity(),"Pengguna gagal untuk di-daftarkan!", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
