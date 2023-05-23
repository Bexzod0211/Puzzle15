package uz.gita.puzzle15.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import uz.gita.puzzle15.R;
import uz.gita.puzzle15.utils.MySharedPref;

public class RecordsActivity extends AppCompatActivity {
    ImageView img_1_place,img_2_place,img_3_place,imgBack;
    TextView txt_1_place,txt_2_place,txt_3_place;
    MySharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        loadViews();
        loadDataToView();
        imgBack.setOnClickListener(view -> finish());
    }

    private void loadViews(){
        img_1_place = findViewById(R.id.img_1_place);
        img_2_place = findViewById(R.id.img_2_place);
        img_3_place = findViewById(R.id.img_3_place);
        txt_1_place = findViewById(R.id.txt_1_place);
        txt_2_place = findViewById(R.id.txt_2_place);
        txt_3_place = findViewById(R.id.txt_3_place);
        imgBack = findViewById(R.id.imgBackResults);

        pref = MySharedPref.getInstance();
    }

    private void loadDataToView(){
        int first = pref.getFirstPlace();
        int second = pref.getSecondPlace();
        int third = pref.getThirdPlace();


        if (first != Integer.MAX_VALUE){
            txt_1_place.setText(first + "");
        }else {
            txt_1_place.setText("-");
        }

        if (second != Integer.MAX_VALUE) {
            txt_2_place.setText(second + "");
        }else {
            txt_2_place.setText("-");
        }

        if (third != Integer.MAX_VALUE) {
            txt_3_place.setText(third + "");
        }else {
            txt_3_place.setText("-");
        }
    }

}