package ru.andrewgavrilenko.tiktaktoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;



public class MainActivity extends AppCompatActivity {
    public final int[][] id = {
            {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8},
            {R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16},
            {R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24},
            {R.id.button25, R.id.button26, R.id.button27, R.id.button28, R.id.button29, R.id.button30, R.id.button31, R.id.button32},
            {R.id.button33, R.id.button34, R.id.button35, R.id.button36, R.id.button37, R.id.button38, R.id.button39, R.id.button40},
            {R.id.button41, R.id.button42, R.id.button43, R.id.button44, R.id.button45, R.id.button46, R.id.button47, R.id.button48},
            {R.id.button49, R.id.button50, R.id.button51, R.id.button52, R.id.button53, R.id.button54, R.id.button55, R.id.button56},
            {R.id.button57, R.id.button58, R.id.button59, R.id.button60, R.id.button61, R.id.button62, R.id.button63, R.id.button64}
    };
    public final int tableSizeX = 8;
    public final int tableSizeY = 8;
    public boolean botEnabled = true;
    public final int requestNumPlayers = 1;
    int numPlayers;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == requestNumPlayers) {
            if (resultCode == RESULT_OK) {
                numPlayers=data.getIntExtra("bot", 0);
                if (numPlayers==2)
                {
                    botEnabled=false;
                }
                else if (numPlayers==1)
                {
                    botEnabled=true;
                }
            }else {
                numPlayers=1;
                SendMessage("Выбран режим по умолчанию (игра против компьютера)");
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivityForResult(intent, requestNumPlayers);
        //onActivityResult(requestNumPlayers, 2, intent);
        tablelayout = (TableLayout) findViewById(R.id.main_l);
        buildGameField();
        refresh();



        /*if (numPlayers==1)
        {
            botEnabled=true;
        }
        else if (numPlayers==2) botEnabled=false;
        else SendMessage("Ошибка, игроков нелегальное количество");*/
        //switchPlayersTurnMessage();
    }
    private void buildGameField() {
        //Square[][] field = game.getField();
        for (int i = 0; i<tableSizeY; i++)
        {
            for(int j = 0; j< tableSizeX; j++)
            {
                buttons[i][j] = (Button)findViewById(id[i][j]);
                buttons[i][j].setOnClickListener(new Listener(i, j));
            }
        }



        //счетчик и ход
        TextView p1 = (TextView)findViewById(R.id.infoTurn);
        TextView p2 = (TextView)findViewById(R.id.infoCounter);
        if (game.getCurrentActivePlayer().getName().equals("White"))
            p1.setText(getResources().getText(R.string.WhiteTurn));
        else p1.setText(getResources().getText(R.string.BlackTurn));
        p2.setText("White: 2\n" + "Black: 2");

    }
    public void switchPlayersTurnMessage()
    {
        //счетчик и ход
        TextView p1 = (TextView)findViewById(R.id.infoTurn);
        TextView p2 = (TextView)findViewById(R.id.infoCounter);
        if (game.getCurrentActivePlayer().getName().equals("White"))
            p1.setText(getResources().getText(R.string.WhiteTurn));
        else p1.setText(getResources().getText(R.string.BlackTurn));
        p2.setText("White: " + game.countWhite() + "\nBlack: " + game.countBlack());
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
        SendMessage("Player \"" + player.getName() + "\" won!\nWhite: " + game.countWhite() + " Black: " + game.countBlack(), true);
        game.reset();
        refresh();
    }
    public void SendMessage(String mes)
    {
        CharSequence text = mes;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void SendMessage(String mes, boolean len)
    {

        CharSequence text = mes;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
    private void gameOver() {
        SendMessage("Draw\nWhite: "+ game.countWhite() + " Black: " + game.countBlack(), true);
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
                    buttons[i][j].setBackgroundResource(R.drawable.cell_turn_50);
                }
            }
    }

    private void refresh() {
        Square[][] field = game.getField();

        for (int i = 0, len = field.length; i < len; i++) {
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                    //buttons[i][j].setBackgroundDrawable(null);
                    buttons[i][j].setBackgroundResource(R.drawable.cell_50);
                } else {
                    //buttons[i][j].setText(field[i][j].getPlayer().getName());
                    if (field[i][j].getPlayer().getName().equals("White"))
                        //buttons[i][j].setBackgroundColor(getResources().getColor(R.color.color_White));
                        buttons[i][j].setBackgroundResource(R.drawable.cell_white_50);
                    else if(field[i][j].getPlayer().getName().equals("Black"))
                        buttons[i][j].setBackgroundResource(R.drawable.cell_black_50);
                        //buttons[i][j].setBackgroundColor(getResources().getColor(R.color.color_Black));
                    else
                    {
                        //buttons[i][j].setBackgroundDrawable(null);
                        buttons[i][j].setBackgroundResource(R.drawable.cell_50);
                        buttons[i][j].setText("");
                    }
                }
            }
        }
        colorTurns();
        switchPlayersTurnMessage();
    }



}
