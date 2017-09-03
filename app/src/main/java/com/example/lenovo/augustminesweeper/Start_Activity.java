package com.example.lenovo.augustminesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Start_Activity extends AppCompatActivity {

    EditText editText;
    RadioGroup rg;
    RadioButton easy,medium,hard;
    public  static final String KEY_USERNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);
        final EditText edittext = (EditText)findViewById(R.id.edittext);
        Button b = (Button)findViewById(R.id.button);
        final RadioButton easy = (RadioButton)findViewById(R.id.easy);
        final RadioButton medium = (RadioButton)findViewById(R.id.medium);
        final RadioButton hard = (RadioButton)findViewById(R.id.hard);
        final SharedPreferences sharedPreferences = getSharedPreferences("Minesweeper",MODE_PRIVATE);
        String lastName = sharedPreferences.getString(KEY_USERNAME,"");
        edittext.setText(lastName);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edittext.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_USERNAME,name);
                editor.commit();

                if(easy.isChecked()){
                    Intent intent = new Intent(Start_Activity.this,MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString(KEY_USERNAME,name);
                    b.putInt("Level",5);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
                else if(medium.isChecked()){
                    Intent intent = new Intent(Start_Activity.this,MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString(KEY_USERNAME,name);
                    b.putInt("Level",10);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
                else if(hard.isChecked()){
                    Intent intent = new Intent(Start_Activity.this,MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString(KEY_USERNAME,name);
                    b.putInt("Level",12);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }
}
