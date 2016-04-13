package com.example.tiktaktoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class MainActivity extends AppCompatActivity {
    public final int tableSizeX = 4;
    public final int tableSizeY = 8;
    public boolean botEnabled = true;
    private Game game;
    private Button[][] buttons = new Button[tableSizeY][tableSizeX];
    private TableLayout tablelayout;
    //private TextView TurnMessage = (TextView)findViewById(R.id.InfoTurn);
    public MainActivity()
    {
        game = new Game();
        game.start();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablelayout = (TableLayout) findViewById(R.id.main_l);
        buildGameField();
        refresh();
        //switchPlayersTurnMessage();
    }
    private void buildGameField() {
        Square[][] field = game.getField();
        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
            TableRow row = new TableRow(this);
            row.setBackgroundResource(R.drawable.cells_50);// СЃРѕР·РґР°РЅРёРµ СЃС‚СЂРѕРєРё С‚Р°Р±Р»РёС†С‹
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                Button button = new Button(this);

                buttons[i][j] = button;
                //buttons[i][j].setText(field[i][j].getPlayer().getName());
                button.setOnClickListener(new Listener(i, j)); // СѓСЃС‚Р°РЅРѕРІРєР° СЃР»СѓС€Р°С‚РµР»СЏ, СЂРµР°РіРёСЂСѓСЋС‰РµРіРѕ РЅР° РєР»РёРє РїРѕ РєРЅРѕРїРєРµ
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)); // РґРѕР±Р°РІР»РµРЅРёРµ РєРЅРѕРїРєРё РІ СЃС‚СЂРѕРєСѓ С‚Р°Р±Р»РёС†С‹
            }
            //row.setBackgroundResource(R.drawable.cells);

            tablelayout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT)); // РґРѕР±Р°РІР»РµРЅРёРµ СЃС‚СЂРѕРєРё РІ С‚Р°Р±Р»РёС†Сѓ

        }
        //индикатор хода  и стартовый счет
        TableRow tableRow = new TableRow(this);
        TextView infoTurn = new TextView(this);

        infoTurn.setText(getResources().getText(R.string.WhiteTurn));
        tableRow.addView(infoTurn, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tablelayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


        TableRow lastRow = new TableRow(this);
        TextView p1 = new TextView(this);
        TextView p2 = new TextView(this);
        p1.setText("White: 2");
        p2.setText("Black: 2");
        lastRow.addView(p1, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        lastRow.addView(p2, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tablelayout.addView(lastRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }
    public void switchPlayersTurnMessage()
    {
        TableLayout table = (TableLayout)findViewById(R.id.main_l);
        table.removeViewsInLayout(8, 2);


        TableRow tableRow = new TableRow(this);
        TextView infoTurn = new TextView(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        if (game.getCurrentActivePlayer().getName().equals("White"))
        infoTurn.setText(getResources().getText(R.string.WhiteTurn));
        else infoTurn.setText(getResources().getText(R.string.BlackTurn));
        tableRow.addView(infoTurn, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tablelayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        //счетчик
        TableRow lastRow = new TableRow(this);
        TextView p1 = new TextView(this);
        TextView p2 = new TextView(this);
        p1.setText("White: "+ game.countWhite());
        p2.setText("Black: " + game.countBlack());
        lastRow.addView(p1, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        lastRow.addView(p2, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tablelayout.addView(lastRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));



    }
    public class Listener implements View.OnClickListener
    {
        private int x = 0;
        private int y = 0;

        public Listener(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            Game g = game;
            Player player = g.getCurrentActivePlayer();
            colorTurns();
            game.clearTurns();

            if (makeTurn(x, y)) {
                //button.setText(player.getName());
                refresh();
                colorTurns();
                switchPlayersTurnMessage();
                game.clearTurns();

            } else SendMessage("Wrong cell");


            if (g.isFieldFilled()) {  // РІ СЃР»СѓС‡Р°Рµ, РµСЃР»Рё РїРѕР»Рµ Р·Р°РїРѕР»РЅРµРЅРѕ
                Player winner = g.checkWinner();
                if (winner != null) {
                    gameOver(winner);
                }
                else gameOver();
            }
            if (botEnabled) {
                while (game.getCurrentActivePlayer().getName().equals("Black"))
                {
                    //SendMessage("Bot plays");
                    /*try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}*/
                    game.makeBotTurn();
                    refresh();
                    colorTurns();
                    switchPlayersTurnMessage();

                    game.clearTurns();
                    if (g.isFieldFilled()) {  // РІ СЃР»СѓС‡Р°Рµ, РµСЃР»Рё РїРѕР»Рµ Р·Р°РїРѕР»РЅРµРЅРѕ
                        Player winner = g.checkWinner();
                        if (winner != null) {
                            gameOver(winner);
                        }
                        else gameOver();
                    }
                }
            }

        }
    }
    private boolean makeTurn(int x, int y) {
        return game.makeTurn(x, y);
    }

    private void gameOver(Player player) {//TO DO
        SendMessage("Player \"" + player.getName() + "\" won!\nWhite: " + game.countWhite() + " Black: " + game.countBlack());
        game.reset();
        refresh();
    }
    public void SendMessage(String mes)
    {
        CharSequence text = mes;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    private void gameOver() {
        SendMessage("Draw\nWhite: "+ game.countWhite() + " Black: " + game.countBlack());
        game.reset();
        refresh();
    }
    private void colorTurns()
    {
        Square[][] field = game.getField();
        game.FindTurns();
        for (int i=0;i<tableSizeY;i++)
            for(int j=0;j<tableSizeX;j++)
            {
                if (field[i][j].getPlayer().getName().equals("turn"))
                {
                    buttons[i][j].setBackgroundColor(getResources().getColor(R.color.color_turn));
                }
            }
    }

    private void refresh() {
        Square[][] field = game.getField();

        for (int i = 0, len = field.length; i < len; i++) {
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackgroundDrawable(null);
                } else {
                    buttons[i][j].setText(field[i][j].getPlayer().getName());
                    if (field[i][j].getPlayer().getName().equals("White"))
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.color_White));
                    else if(field[i][j].getPlayer().getName().equals("Black"))
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.color_Black));
                    else
                    {
                        buttons[i][j].setBackgroundDrawable(null);
                        buttons[i][j].setText("");
                    }
                }
            }
        }
        colorTurns();
        switchPlayersTurnMessage();
    }



}
