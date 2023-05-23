package uz.gita.puzzle15.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import uz.gita.puzzle15.R;
import uz.gita.puzzle15.ui.dialogs.CustomExitDialog;

public class EntryActivity extends AppCompatActivity {
    TextView txt_start,txt_records,txt_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        loadViews();
    }

    private void loadViews(){
        txt_start = findViewById(R.id.txt_open_main);
        txt_records = findViewById(R.id.txt_records);
        txt_about = findViewById(R.id.txt_about);

        txt_start.setOnClickListener(v->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        txt_records.setOnClickListener(v->{
            Intent intent = new Intent(this, RecordsActivity.class);
            startActivity(intent);
        });

        txt_about.setOnClickListener(v->{
            showDialog(this);
        });



    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showDialog(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Puzzle15")
                .setMessage("Bu dastur Mamatxalilov Bexzod tomonidan, GITA dasturchilar academiyasida o'qish davrida tuzilgan. O'yin dasturidan baxramand bo'ling!")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                })
                .create()
                .show();
    }

    private void showExitDialog(){
        CustomExitDialog dialog = new CustomExitDialog(this);
        dialog.setOnDialogButtonClickListener(this::finish);
        dialog.create();
        dialog.show();

    }
}