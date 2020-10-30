package ru.samsung.itschool.book.cells;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;


import task.Stub;
import task.Task;

public class CellsActivity extends AppCompatActivity {

    private final Context context = this;

    private int WIDTH = 10;
    private int HEIGHT = 10;

    private Button[][] cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells);
        makeCells();

        generate();

    }

    void generate() {

        //Эту строку нужно удалить
        Task.showMessage(this, "Добавьте код в функцию активности generate() для генерации клеточного поля");


        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                //ADD YOUR CODE HERE
                //....

            }
    }

	/*
     * NOT FOR THE BEGINNERS
	 * ==================================================
	 */

    int getX(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[1]);
    }

    int getY(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[0]);
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
                        //Эту строку нужно удалить
                        Stub.show(context, "Добавьте код в функцию активности onClick() - реакцию на нажатие на клетку");

                        Button tappedCell = (Button) v;

                        //Получаем координтаты нажатой клетки
                        int tappedX = getX(tappedCell);
                        int tappedY = getY(tappedCell);
                        //ADD YOUR CODE HERE
                        //....
                    }
                });
                cells[i][j].setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Эту строку нужно удалить
                        Stub.show(context, "Добавьте код в функцию активности onLongClick() - реакцию на долгое нажатие на клетку");
                        return false;
                    }
                });
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }

}