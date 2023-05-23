package uz.gita.puzzle15.ui.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import uz.gita.puzzle15.R;


public class CustomExitDialog extends AlertDialog {
    private AppCompatButton btnYes,btnNo;
    OnDialogButtonClickListener listener;
    public CustomExitDialog(@NonNull Context context) {
        super(context);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_dialog);
        loadViews();
        attachListeners();
    }

    private void loadViews(){
        btnYes = findViewById(R.id.btn_Yes);
        btnNo = findViewById(R.id.btn_No);
    }

    private void attachListeners(){
        btnYes.setOnClickListener(view -> {
            cancel();
            listener.onClick();
        });

        btnNo.setOnClickListener(view -> cancel());
    }


    public void setOnDialogButtonClickListener(OnDialogButtonClickListener listener){
        this.listener = listener;
    }

    public interface OnDialogButtonClickListener {
        void onClick();

    }
}
