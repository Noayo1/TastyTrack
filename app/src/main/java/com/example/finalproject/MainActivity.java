package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;
import com.example.finalproject.R;
import com.example.finalproject.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void login (View view)
    {
        String email = ((EditText)findViewById(R.id.Email)).getText().toString();
        String password = ((EditText)findViewById(R.id.Password)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_login_Page_to_OptionsFragment);
                            ((EditText)findViewById(R.id.Email)).setText("");
                            ((EditText)findViewById(R.id.Password)).setText("");
                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Login Not Success",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void reg(View v)
    {
        String email = ((EditText)findViewById(R.id.EmailReg)).getText().toString();
        String password = ((EditText)findViewById(R.id.PasswordReg)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this,"Registration successful!",Toast.LENGTH_LONG).show();
                            ((EditText)findViewById(R.id.EmailReg)).setText("");
                            ((EditText)findViewById(R.id.PasswordReg)).setText("");
                            ((EditText)findViewById(R.id.PhoneReg)).setText("");
                            ((EditText)findViewById(R.id.repeatPasswordReg)).setText("");
                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"This email is already registered",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void addData()
    {
        String phone = ((EditText)findViewById(R.id.PhoneReg)).getText().toString();
        String email = ((EditText)findViewById(R.id.EmailReg)).getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(phone);

        User s = new User(phone,email);
        myRef.setValue(s);
    }
}
