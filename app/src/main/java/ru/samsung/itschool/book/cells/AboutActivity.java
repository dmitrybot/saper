package ru.samsung.itschool.book.cells;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        generate();
    }
    void generate(){ // надпись о победе или поражении
        TextView text = (TextView) findViewById(R.id.textView_about_game_result);
        Bundle arguments = getIntent().getExtras();
        text.setText(arguments.get("game_result").toString());
    }
    public void restart(View view) { // возвращение к стартовому меню после нажатия кнопки
        Intent intent = new Intent(AboutActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}