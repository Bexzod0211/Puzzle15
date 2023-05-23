package uz.gita.puzzle15.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uz.gita.puzzle15.R;
import uz.gita.puzzle15.ui.dialogs.WinDialog;
import uz.gita.puzzle15.utils.MySharedPref;

public class MainActivity extends AppCompatActivity {
    private TextView txtMoves;
    private ImageView imgHome,imgRestart,img_sound;
    TextView[][] cells;
    private ConstraintLayout table;
    private int emptyX = 3,emptyY = 3,countMoves;
    private List<Integer> numbers;
    Chronometer time;
    MediaPlayer click_sound,fon_music,negative_sound,music_win;
    int[] bests;
    boolean isPlaying;
//    SharedPreferences pref;
    MySharedPref pref;
    private StringBuilder sb;
    private String NUMBERS = "Numbers",COUNT_MOVES = "COUNT_MOVES",TIME = "TIME";
    private long timeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        loadDataToView();
    }


    @Override
    protected void onPause() {
        super.onPause();
        fon_music.pause();
        saveMusicState();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sb = new StringBuilder(savedInstanceState.getString(NUMBERS, null));

        countMoves = savedInstanceState.getInt(COUNT_MOVES,0);

        String[] arr = sb.toString().split("#");


        Log.d("TTT",cells[0][0].getText().toString());

        Log.d("TTT",Arrays.toString(arr));
        if (arr.length != 0) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("16")) {
                    cells[i / 4][i % 4].setVisibility(View.INVISIBLE);
                    emptyX = i/4;
                    emptyY = i%4;
                }else{
                    cells[i / 4][i % 4].setText(arr[i]);
                    cells[i / 4][i % 4].setVisibility(View.VISIBLE);

                }
            }
        }
        Log.d("TTT",cells[0][0].getText().toString());
        txtMoves.setText("Moves : "+countMoves+"");
        test();

        timeValue = savedInstanceState.getLong(TIME,0);
        if (timeValue == 0){
            time.setBase(SystemClock.elapsedRealtime());
        }
        else {
            time.setBase(timeValue);
        }

        time.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            fon_music.start();
            fon_music.setLooping(true);
            if (!fon_music.isPlaying()) {
                fon_music.start();
            }
            img_sound.setImageResource(R.drawable.ic_music_on);
        }else {
            img_sound.setImageResource(R.drawable.ic_music_off);
        }



    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        sb = new StringBuilder();
        cells[emptyX][emptyY].setText("16");
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Log.d("TTT","number : "+cells[i][j].getText().toString());
                sb.append(cells[i][j].getText().toString()).append("#");
            }
        }


        outState.putString(NUMBERS,sb.toString());
        outState.putInt(COUNT_MOVES,countMoves);
        outState.putLong(TIME,time.getBase());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        showDialogBackPressed();
    }
    private void loadViews() {

        cells = new TextView[4][4];
        bests = new int[3];


        txtMoves = findViewById(R.id.txt_moves);
        imgHome = findViewById(R.id.img_home);
        imgRestart = findViewById(R.id.img_restart);
        img_sound = findViewById(R.id.img_sound);
        time = findViewById(R.id.time);
        table = findViewById(R.id.table);

        click_sound = MediaPlayer.create(this,R.raw.sound_click);
        fon_music = MediaPlayer.create(this,R.raw.music_fon);
        negative_sound = MediaPlayer.create(this,R.raw.wrong_sound_click);
        music_win = MediaPlayer.create(this,R.raw.music_win);

        pref = MySharedPref.getInstance();
        sb = new StringBuilder();


        isPlaying = pref.getMusicState();
        countMoves = pref.getMoves();


        bests[0] = pref.getFirstPlace();
        bests[1] = pref.getSecondPlace();
        bests[2] = pref.getThirdPlace();





        imgHome.setOnClickListener(v -> {
            onBackPressed();
        });

        imgRestart.setOnClickListener(view->{
            restart();
        });


        img_sound.setOnClickListener(v->{
            if (isPlaying){
                fon_music.pause();
                isPlaying = false;
                img_sound.setImageResource(R.drawable.ic_music_off);
            }
            else {
                fon_music = MediaPlayer.create(this,R.raw.music_fon);
                fon_music.start();
                isPlaying = true;
                fon_music.setLooping(true);
                img_sound.setImageResource(R.drawable.ic_music_on);
            }
        });

        for (int i = 0; i < table.getChildCount(); i++) {
            table = findViewById(R.id.table);
            TextView txt = (TextView) table.getChildAt(i);
            cells[i/4][i%4] = txt;
            int X = i%4;
            int Y = i/4;
            txt.setOnClickListener(view -> {
                move(Y,X);
            });
        }

    }

    private void loadDataToView(){
        cells[emptyX][emptyY].setVisibility(View.VISIBLE);




        numbers = pref.getNumbers();

        if (numbers.get(0) == 0) {

            emptyX = 3;
            emptyY = 3;

            cells[emptyX][emptyY].setVisibility(View.INVISIBLE);
            numbers.clear();
            for (int i = 1; i <= 15; i++) {
                numbers.add(i);
            }

            shuffle();

            for (int i = 0; i < 15; i++) {
                cells[i/4][i%4].setText(String.valueOf(numbers.get(i)));
                Log.d("TTT","loadViewIf Number : "+numbers.get(i)+" : "+cells[i/4][i%4].getText().toString());
            }
        }
        else {

            for (int i = 0; i <= 15; i++) {
                if (numbers.get(i) == 16) {
                    emptyX = i/4;
                    emptyY = i%4;
                    cells[i / 4][i % 4].setVisibility(View.INVISIBLE);
                } else {
                    cells[i / 4][i % 4].setText(String.valueOf(numbers.get(i)));
                    Log.d("TTT","loadViewElse Number:"+numbers.get(i));
                }
            }

        }

        txtMoves.setText("Moves : "+countMoves+"");
        test();
   

        if (pref.getTime() == 0){
            time.setBase(SystemClock.elapsedRealtime()-pref.getTime());
        }
        else {
            time.setBase(pref.getTime());
        }

        time.start();

    }




    private void showDialogBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Finish")
                .setMessage("Would you like to save positions?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                    saveNumbers();
                    pref.saveMoves(countMoves);
                    pref.saveTime(SystemClock.elapsedRealtime() - time.getBase());
                    time.stop();
                    finish();
                })
                .setNegativeButton("No", ((dialogInterface, i) -> {
                    dialogInterface.cancel();
                    saveNullNumbers();
                    pref.saveTime(0);
                    pref.saveMoves(0);
                    finish();
                }))
                .create()
                .show();
    }


    private void saveMusicState(){
        pref.saveMusicState(isPlaying);
    }
    private void saveNullNumbers(){
        List<Integer> list = new ArrayList<>(1);

//        cells[emptyX][emptyY].setText("16");

        list.add(0);


        pref.putNumbers(list);
    }

    private void saveNumbers(){
        List<Integer> list = new ArrayList<>();

        cells[emptyX][emptyY].setText("16");

        for (int i = 0; i <= 15; i++) {
            list.add(Integer.parseInt(cells[i/4][i%4].getText().toString()));
        }

        pref.putNumbers(list);
    }

    private void loadDataToViewRestart(){
        cells[emptyX][emptyY].setVisibility(View.VISIBLE);

        emptyX = 3;
        emptyY = 3;

        cells[3][3].setVisibility(View.INVISIBLE);

        numbers = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }

        shuffle();

        for (int i = 0; i < 15; i++) {
            cells[i/4][i%4].setText(String.valueOf(numbers.get(i)));
        }

        countMoves = 0;
        txtMoves.setText("Moves : 0");
        test();
    }
    private void shuffle(){
        Collections.shuffle(numbers);

        while (!isSolvable()){
            Collections.shuffle(numbers);
        }
    }

    boolean isSolvable() {
        int[] puzzle15 = new int[15];

        for (int i = 0; i < numbers.size(); i++) {
            puzzle15[i] = numbers.get(i);
        }

        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (puzzle15[j] > puzzle15[i])
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }
    private void move(int x,int y) {
        int dx = Math.abs(emptyX - x);
        int dy = Math.abs(emptyY - y);

        if (dx + dy == 1) {
            if (click_sound != null){
                click_sound.start();
            }
            String str = cells[x][y].getText().toString();
            cells[x][y].setText(16+"");
            cells[x][y].setVisibility(View.INVISIBLE);
            cells[emptyX][emptyY].setText(str);
            cells[emptyX][emptyY].setVisibility(View.VISIBLE);

            emptyX = x;
            emptyY = y;

            countMoves++;
            txtMoves.setText("Moves : " + countMoves);
//            if (countMoves == 1){
//                time.setBase(SystemClock.elapsedRealtime());
//                time.start();
//            }
            test();
            if (checkWin()){

                if (countMoves<bests[0]){
                    bests[2] = bests[1];
                    bests[1] = bests[0];
                    bests[0] =countMoves;
                }else if (countMoves<bests[1]){
                    bests[2] = bests[1];
                    bests[1] = countMoves;
                }else if (countMoves<bests[2]){
                    bests[2] = countMoves;
                }

                pref.putFirstPlace(bests[0]);
                if (bests[1]!= Integer.MAX_VALUE){
                   pref.putSecondPlace(bests[1]);
                }
                if (bests[2] != Integer.MAX_VALUE){
                    pref.putThirdPlace(bests[2]);
                }

                saveNullNumbers();

                time.stop();
                showDialog(this,countMoves);
                countMoves = 0;

            }
        }
        else {
            negative_sound.start();
        }
    }

    private void test(){
        for (int i = 0; i < 15; i++) {
            if (cells[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                cells[i/4][i%4].setBackground(getResources().getDrawable(R.drawable.db_cell_select));
            }
            if (!cells[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                cells[i/4][i%4].setBackground(getResources().getDrawable(R.drawable.db_cell_select_wrong));
            }
        }

    }

    private boolean checkWin(){
        if (emptyX != 3&&emptyY !=3){
            return false;
        }
        for (int i = 0; i < 15; i++) {
            if (!cells[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                return false;
            }
        }

        return true;
    }

    public void showDialog(Context context, int countMoves) {

        music_win.start();
        fon_music.stop();

        WinDialog dialog = new WinDialog(context,countMoves);
        dialog.setOnclickListener(new WinDialog.OnClickListener() {
            @Override
            public void onYesClick() {
                music_win.stop();
                restart();
            }

            @Override
            public void onNoClick() {
                music_win.stop();
                MainActivity.this.countMoves = 0;
                pref.saveMoves(MainActivity.this.countMoves);
                finish();
            }
        });

        dialog.create();
        dialog.show();

    }

    private void restart(){
        loadDataToViewRestart();
        test();
        time.setBase(SystemClock.elapsedRealtime());
        time.start();
        fon_music.stop();
        if (isPlaying){
            fon_music = MediaPlayer.create(this,R.raw.music_fon);
            fon_music.start();
            fon_music.setLooping(true);
        }

    }
}