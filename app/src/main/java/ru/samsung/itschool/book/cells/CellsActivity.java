package ru.samsung.itschool.book.cells;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import task.Stub;
import task.Task;

public class CellsActivity extends AppCompatActivity {

    private final Context context = this;
    private int WIDTH = 10;
    private int HEIGHT = 10;
    private int Bombs = 10;

    private Button[][] cells;
    private boolean[][] opened, closed, marked;
    private int[][] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        WIDTH = arguments.getInt("width");
        HEIGHT = arguments.getInt("height");
        Bombs = arguments.getInt("bombs");
        setContentView(R.layout.cells);
        makeCells();

        generate();

    }

    void generate() {
        opened = new boolean[HEIGHT][WIDTH]; // показывает открыты ки клетки
        closed = new boolean[HEIGHT][WIDTH]; // показывает закрыты ли клетки
        marked = new boolean[HEIGHT][WIDTH]; // показывает помечены ли клетки флажком
        values = new int[HEIGHT][WIDTH]; // показывает что лежит в клетке
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cells[i][j].setBackgroundColor(Color.GRAY);
                closed[i][j] = true;
                opened[i][j] = false;
                marked[i][j] = false;
                values[i][j] = 0;
            }
        }

        for (int i = 0; i < Bombs; i++){ // генерация бомб
            int x = ((int) (Math.random() * WIDTH));
            int y = ((int) (Math.random() * HEIGHT));
            while (values[y][x] == 9){
                x = ((int) (Math.random() * WIDTH));
                y = (int) (Math.random() * 9);
            }
            values[y][x] = 9;
            int st1 = y - 1, st2 = x - 1, en1 = y + 1, en2 = x + 1;
            if (x == 0){
                st2 = x;
            }
            if (y == 0){
                st1 = y;
            }
            if(x == WIDTH - 1){
                en2 = x;
            }
            if (y == HEIGHT - 1){
                en1 = y;
            }
            while (st1 <= en1){
                int k = st2;
                while (k <= en2){
                    if(values[st1][k] != 9){
                        values[st1][k]++;
                    }
                    k++;
                }
                st1++;
            }
        }
    }

    int getX(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[1]);
    }

    int getY(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[0]);
    }

    private int win_check() { // проверка победы игрока
        int sch = 0;
        for (int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++) {
                if (marked[i][j]){
                    if (values[i][j] == 9){
                        sch++;
                    } else {
                        sch = -1;
                    }
                }
            }
        }
        return sch;
    }
    private void opening(int x, int y){ // открывает клетки в которых однозначно не лежат бомбы при нажатии на соседнюю клетку
        int st1 = y - 1, st2 = x - 1, en1 = y + 1, en2 = x + 1;
        if (x == 0){
            st2 = x;
        }
        if (y == 0){
            st1 = y;
        }
        if(x == WIDTH - 1){
            en2 = x;
        }
        if (y == HEIGHT - 1){
            en1 = y;
        }
        while (st1 <= en1){
            int k = st2;
            while (st2 <= en2){
                if(st2 != x || st1 != y){
                    if(closed[st1][st2]){
                        cells[st1][st2].setBackgroundColor(Color.WHITE);
                        if (values[st1][st2] == 0){
                            cells[st1][st2].setText("");
                            closed[st1][st2] = false;
                            opened[st1][st2] = true;
                            opening(st2, st1);
                        } else if (values[st1][st2] == 9){
                            cells[st1][st2].setText("Bomb");
                            cells[st1][st2].setBackgroundColor(Color.RED);
                        } else {
                            cells[st1][st2].setText(String.valueOf(values[st1][st2]));
                        }
                        closed[st1][st2] = false;
                        opened[st1][st2] = true;
                    }
                }
                st2++;
            }
            st1++;
            st2 = k;
        }
    }
    void makeCells() {
        cells = new Button[HEIGHT][WIDTH];
        GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                cells[i][j] = (Button) inflater.inflate(R.layout.cell, cellsLayout, false);
                cells[i][j].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button tappedCell = (Button) v;
                        int tappedX = getX(tappedCell);
                        int tappedY = getY(tappedCell);
                        if(closed[tappedY][tappedX]){ // нажатие на закрытую клетку
                            tappedCell.setBackgroundColor(Color.WHITE);
                            if (values[tappedY][tappedX] == 0){ // клетка пустая
                                tappedCell.setText("");
                                closed[tappedY][tappedX] = false;
                                opened[tappedY][tappedX] = true;
                                opening(tappedX, tappedY);
                            } else if (values[tappedY][tappedX] == 9){ // в клетке есть бомба
                                tappedCell.setText("Bomb");
                                tappedCell.setBackgroundColor(Color.RED);
                                Intent intent = new Intent(CellsActivity.this, AboutActivity.class);
                                intent.putExtra("game_result", "You lose");
                                startActivity(intent);
                            } else { // рядом с клеткой есть бомба
                                tappedCell.setText(String.valueOf(values[tappedY][tappedX]));
                            }
                            closed[tappedY][tappedX] = false;
                            opened[tappedY][tappedX] = true;
                        } else if (opened[tappedY][tappedX]){ // нажатие на открытую кнопку
                            int x = tappedX, y = tappedY, schet = 0;
                            int st1 = y - 1, st2 = x - 1, en1 = y + 1, en2 = x + 1;
                            if (x == 0){
                                st2 = x;
                            }
                            if (y == 0){
                                st1 = y;
                            }
                            if(x == WIDTH - 1){
                                en2 = x;
                            }
                            if (y == HEIGHT - 1){
                                en1 = y;
                            }
                            while (st1 <= en1){
                                int k = st2;
                                while (st2 <= en2){
                                    if(st2 != x || st1 != y){
                                        if (marked[st1][st2]){
                                            schet++;
                                        }
                                    }
                                    st2++;
                                }
                                st1++;
                                st2 = k;
                            }
                            if (schet == values[y][x]){
                                opening(x, y);
                            }
                        }

                    }
                });
                cells[i][j].setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Button tappedCell = (Button) v;
                        int tappedX = getX(tappedCell);
                        int tappedY = getY(tappedCell);
                        if(closed[tappedY][tappedX]){ // установка флажка
                            tappedCell.setText("F");
                            tappedCell.setBackgroundColor(Color.GREEN);
                            closed[tappedY][tappedX] = false;
                            marked[tappedY][tappedX] = true;
                            int sch = win_check();
                            if (sch == Bombs){
                                Intent intent = new Intent(CellsActivity.this, AboutActivity.class);
                                intent.putExtra("game_result", "You win");
                                startActivity(intent);
                            }
                        } else if (marked[tappedY][tappedX]){ // снятие флажка
                            tappedCell.setText("");
                            tappedCell.setBackgroundColor(Color.GRAY);
                            closed[tappedY][tappedX] = true;
                            marked[tappedY][tappedX] = false;
                        }
                        return true;
                    }
                });
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }
}
