package com.example.organizze.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.organizze.MainActivity;
import com.example.organizze.config.FirebaseConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.organizze.R;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class LoggedMainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView textWelcome, textBalance;
    private FloatingActionButton addBtn, incomesBtn, expenditureBtn;
    private boolean addBtnClicked;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);
        startCalendarView();

        textWelcome = findViewById(R.id.textWelcome);
        textBalance = findViewById(R.id.textBalance);

        addBtn = findViewById(R.id.addBtn);
        incomesBtn = findViewById(R.id.incomesBtn);
        expenditureBtn = findViewById(R.id.expenditureBtn);
        addBtnClicked = false;

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonClick();
            }
        });

        incomesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncomesActivity();
            }
        });

        expenditureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExpenditureActivity();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuExit :
                auth = FirebaseConfig.getFirebaseAuth();
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addButtonClick(){
        setVisibility(addBtnClicked);
        setAnimation(addBtnClicked);
        setClickable(addBtnClicked);
        addBtnClicked = !addBtnClicked;
    }

    private void setVisibility(boolean clicked){
        if(!clicked) {
            incomesBtn.show();
            expenditureBtn.show();
        } else {
            incomesBtn.hide();
            expenditureBtn.hide();
        }

    }

    private void setAnimation(boolean clicked) {
        if(!clicked) {
            incomesBtn.startAnimation(fromBottom);
            expenditureBtn.startAnimation(fromBottom);
            addBtn.startAnimation(rotateOpen);
        } else {
            incomesBtn.startAnimation(toBottom);
            expenditureBtn.startAnimation(toBottom);
            addBtn.startAnimation(rotateClose);
        }
    }

    private void setClickable(boolean clicked) {
        if(!clicked) {
            incomesBtn.setClickable(true);
            expenditureBtn.setClickable(true);
        } else {
            incomesBtn.setClickable(false);
            expenditureBtn.setClickable(false);
        }
    }

    private void openIncomesActivity() {
        addButtonClick();
        startActivity(new Intent(this, IncomesActivity.class));
    }

    private void openExpenditureActivity(){
        addButtonClick();
        startActivity(new Intent(this, ExpendituresActivity.class));
    }

    private void startCalendarView() {
        CharSequence months[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(months);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }
}
