package my.insta.androrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.insta.androrealm.models.Users;

public class Login extends AppCompatActivity {

    private TextView signup;
    private TextInputLayout Email, Pass;
    private Button Login;
    private FirebaseAuth FAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeUI();
        setupSignUpButton();
        setupLoginButton();
    }

    private void initializeUI() {
        signup = findViewById(R.id.signup);
        Email = findViewById(R.id.login_email);
        Pass = findViewById(R.id.login_password);
        Login = findViewById(R.id.Login_btn);

        FAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void setupSignUpButton() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
                finish();
            }
        });
    }

    private void setupLoginButton() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getEditText().getText().toString().trim();
                String pass = Pass.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, pass);
                }
            }
        });
    }

    private void loginUser(String email, String pass) {
        FAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FAuth.getCurrentUser();
                            fetchUserType(user.getUid());
                        } else {
                            Toast.makeText(Login.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchUserType(final String userId) {
        databaseReference.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        String userType = user.getType();
                        redirectToActivity(userType);
                    } else {
                        Toast.makeText(Login.this, "User data not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToActivity(String userType) {
        Intent intent;
        switch (userType) {
            case "coffee lover":
                //intent = new Intent(Login.this, CoffeeLoverActivity.class);
                break;
            case "supplier":
                //intent = new Intent(Login.this, SupplierActivity.class);
                break;
            default:
                //intent = new Intent(Login.this, UserActivity.class);
                break;
        }
        //startActivity(intent);
        finish();
    }
}
