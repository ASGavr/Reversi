/*package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.lang.reflect.Field;


public class KrestikinolikiActivity extends Activity {

        private Game game;
        private Button[][] buttons = new Button[3][3];
        private TableLayout layout;


        public KrestikinolikiActivity() {
                game = new Game();
                game.start();
        }
        /**
         * Called when the activity is first created.
         *//*
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);
                layout = (TableLayout) findViewById(R.id.main_l);
                buildGameField();
        }

        private void buildGameField() {
                Square[][] field = game.getField();
                for (int i = 0, lenI = field.length; i < lenI; i++ ) {
                        TableRow row = new TableRow(this); // создание строки таблицы
                        for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                                Button button = new Button(this);
                                buttons[i][j] = button;
                                button.setOnClickListener(new Listener(i, j)); // установка слушателя, реагирующего на клик по кнопке
                                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                        TableRow.LayoutParams.WRAP_CONTENT)); // добавление кнопки в строку таблицы
                                button.setWidth(107);
                                button.setHeight(107);
                        }
                        layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT)); // добавление строки в таблицу
                }
        }

        public class Listener implements View.OnClickListener {
                private int x = 0;
                private int y = 0;

                public Listener(int x, int y) {
                        this.x = x;
                        this.y = y;
                }

                public void onClick(View view) {
                        Button button = (Button) view;
                        Game g = game;
                        Player player = g.getCurrentActivePlayer();
                        if (makeTurn(x, y)) {
                                button.setText(player.getName());
                        }
                        Player winner = g.checkWinner();
                        if (winner != null) {
                                gameOver(winner);
                        }
                        if (g.isFieldFilled()) {  // в случае, если поле заполнено
                                gameOver();
                        }
                }
        }

        private boolean makeTurn(int x, int y) {
                return game.makeTurn(x, y);
        }

        private void gameOver(Player player) {
                CharSequence text = "Player \"" + player.getName() + "\" won!";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                game.reset();
                refresh();
        }

        private void gameOver() {
                CharSequence text = "Draw";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                game.reset();
                refresh();
        }

        private void refresh() {
                Square[][] field = game.getField();

                for (int i = 0, len = field.length; i < len; i++) {
                        for (int j = 0, len2 = field[i].length; j < len2; j++) {
                                if (field[i][j].getPlayer() == null) {
                                        buttons[i][j].setText("");
                                } else {
                                        buttons[i][j].setText(field[i][j].getPlayer().getName());
                                }
                        }
                }
        }
}*/