package ru.samsung.itschool.book.cells;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }
    public void restart1(View view) {
        Intent intent = new Intent(MenuActivity.this, CellsActivity.class);
        intent.putExtra("height", 10);
        intent.putExtra("width", 10);
        intent.putExtra("bombs", 10);
        startActivity(intent);
    }
    public void restart2(View view) {
        Intent intent = new Intent(MenuActivity.this, CellsActivity.class);
        intent.putExtra("height", 50);
        intent.putExtra("width", 50);
        intent.putExtra("bombs", 50);
        startActivity(intent);
    }
    public void restart3(View view) {
        Intent intent = new Intent(MenuActivity.this, CellsActivity.class);
        intent.putExtra("height", 100);
        intent.putExtra("width", 100);
        intent.putExtra("bombs", 100);
        startActivity(intent);
    }
}
