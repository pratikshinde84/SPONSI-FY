package com.example.sponsi_fy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    String name,number,email,username,pass;
    FirebaseDatabase db;
    DatabaseReference reference;

    EditText et_name;
    EditText et_number;
    EditText et_email;
    EditText et_user;
    EditText et_pass;
    Button btn_reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register page");
        et_name=findViewById(R.id.et_entername);
        et_number=findViewById(R.id.et_enter_phone);
        et_email=findViewById(R.id.et_enteremail);
        et_user=findViewById(R.id.et_enterusername);
        et_pass=findViewById(R.id.et_enterpswd);
        btn_reg=findViewById(R.id.btn_reg);



        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getText().toString().isEmpty())
                {
                    et_name.setError("Please enter your name");
                }
                else if(et_number.getText().toString().isEmpty())
                {
                    et_number.setError("Please enter Mobile number");
                }
                else if(et_number.getText().toString().length()!=10)
                {
                    et_number.setError("Number must be of 10 digits");
                }
                else if (et_email.getText().toString().isEmpty())
                {
                    et_email.setError("Please enter email id");
                }
                else if (!et_email.getText().toString().contains("@")||!et_email.getText().toString().contains(".com"))
                {
                    et_email.setError("Please enter valid Email id");
                }
                else if (et_user.getText().toString().isEmpty())
                {
                    et_user.setError("Please enter Username");
                }
                else if (et_user.getText().toString().length()<8)
                {
                    et_user.setError("Username must be greater than 8 characters");
                }
                else if (et_pass.getText().toString().isEmpty())
                {
                    et_pass.setError("Please enter Password");
                }
                else if(et_pass.getText().toString().length()<8)
                {
                    et_pass.setError("Username must be greater than 8 characters");
                }
                else
                {
                    String username = et_user.getText().toString();
                    if (!hasLowercase(username) )
                    {
                        et_user.setError("Username must contain at least one Lowercase letter");
                    }
                    else if(!hasUppercase(username))
                    {
                        et_user.setError("Username must contain at least one Uppercase letter");
                    }
                    else if(!hasSpecialSymbol(username))
                    {
                        et_user.setError("Username must contain at least one special symbol");
                    }
                    else {
                        name=et_name.getText().toString();
                        number=et_number.getText().toString();
                        email=et_email.getText().toString();
                        username=et_user.getText().toString();
                        pass=et_pass.getText().toString();

                        Users users=new Users(name,number,email,username,pass);
                        db=FirebaseDatabase.getInstance();
                        reference=db.getReference("Users_Details");
                        reference.child(username).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Registration successfully done", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                    }
                }
            }
        });
    }
    private boolean hasLowercase(String text) {
        return text.matches(".*[a-z].*");
    }

    private boolean hasUppercase(String text) {
        return text.matches(".*[A-Z].*");
    }

    private boolean hasSpecialSymbol(String text) {
        return text.matches(".*[@#$%^&+=].*");
    }
}