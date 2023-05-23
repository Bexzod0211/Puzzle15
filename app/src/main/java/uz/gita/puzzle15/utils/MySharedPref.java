package uz.gita.puzzle15.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.sip.SipErrorCode;

import java.util.ArrayList;
import java.util.List;

public class MySharedPref {
    private SharedPreferences pref;
    private static MySharedPref instance;

    private String FIRST_PLACE = "FIRST";
    private String SECOND_PLACE = "SECOND";
    private String THIRD_PLACE = "THIRD";
    private String MUSIC_STATE = "MUSIC_STATE";
    private String MOVES = "MOVES";
    private String NUMBER = "NUMBER";
    private String TIME = "TIME";
    private MySharedPref(Context context) {
        pref = context.getSharedPreferences("Puzzle15", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MySharedPref(context);
        }
    }

    public static MySharedPref getInstance() {
        return instance;
    }


    public void putFirstPlace(int moves) {
        pref.edit().putInt(FIRST_PLACE, moves).apply();
    }

    public void putSecondPlace(int moves) {
        pref.edit().putInt(SECOND_PLACE, moves).apply();
    }

    public void putThirdPlace(int moves) {
        pref.edit().putInt(THIRD_PLACE, moves).apply();
    }




    public int getFirstPlace() {
        return pref.getInt(FIRST_PLACE, Integer.MAX_VALUE);
    }

    public int getSecondPlace() {
        return pref.getInt(SECOND_PLACE, Integer.MAX_VALUE);
    }

    public int getThirdPlace() {
        return pref.getInt(THIRD_PLACE, Integer.MAX_VALUE);
    }



    public void putNumbers(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            pref.edit().putInt(NUMBER + (i + 1), list.get(i)).apply();
        }
    }

    public List<Integer> getNumbers() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= 15; i++) {
            list.add(pref.getInt(NUMBER + (i + 1), 0));
        }

        return list;

    }



    public void saveMusicState(boolean state){
        pref.edit().putBoolean(MUSIC_STATE,state).apply();
    }

    public boolean getMusicState(){
        return pref.getBoolean(MUSIC_STATE,false);
    }

    public void saveMoves(int moves){
        pref.edit().putInt(MOVES,moves).apply();
    }

    public int getMoves(){
        return pref.getInt(MOVES,0);
    }

    public void saveTime(long time){
        pref.edit().putLong(TIME,time).apply();
    }

    public long getTime(){
        return pref.getLong(TIME,0);
    }
}
