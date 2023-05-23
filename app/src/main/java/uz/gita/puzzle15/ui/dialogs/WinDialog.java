package uz.gita.puzzle15.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import uz.gita.puzzle15.R;

public class WinDialog extends AlertDialog {
    private int moves;
    private TextView txt_moves;
    private AppCompatButton btnYes,btnNo;
    private OnClickListener listener;

    public WinDialog(@NonNull Context context,int moves) {
        super(context);
        this.moves = moves;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_win);
        loadViews();
        txt_moves.setText("Moves "+moves);
        attachListeners();
    }

    private void loadViews(){
        txt_moves = findViewById(R.id.txt_moves_win);
        btnYes = findViewById(R.id.btn_Yes_Win);
        btnNo = findViewById(R.id.btn_No_Win);
    }

    private void attachListeners(){
        btnYes.setOnClickListener(view -> {
            cancel();
            listener.onYesClick();

        });
        btnNo.setOnClickListener(view -> {
            cancel();
            listener.onNoClick();
        });
    }


    public void setOnclickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener {
        void onYesClick();
        void onNoClick();
    }
}
