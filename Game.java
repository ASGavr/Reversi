package com.example.tiktaktoe;
import java.util.Random;
/**
 * Created by РђРЅРґСЂРµР№ on 31.03.2016.
 */public class Game
{
    public int[][] botPriority = {
            {10, 7, 7, 10},
            {4, 3, 3, 4},
            {7, 5, 5, 7},
            {7, 5, 5, 7},
            {7, 5, 5, 7},
            {7, 5, 5, 7},
            {4, 3, 3, 4},
            {10, 7, 7, 10}

    };
    public final int tableSizeX = 4;
    public final int tableSizeY = 8;
    //РёРіСЂРѕРєРё
    private Player[] players;
    // РїРѕР»Рµ
    private Square[][] field;
    //РЅР°С‡Р°С‚Р° Р»Рё РёРіСЂР°
    private boolean started;
    //С‚РµРєСѓС‰РёР№ РёРіСЂРѕРє
    private Player activePlayer;
    //С‡РёСЃР»Рѕ Р·Р°РїРѕР»РЅРµРЅРЅС‹С… СЏС‡РµРµРє
    private int filled;
    //РѕР±С‰РµРµ С‡РёСЃР»Рѕ СЏС‡РµРµРє
    private int squareCount;



    // РљРѕРЅСЃС‚СЂСѓРєС‚РѕСЂ
    public Game()
    {
        field = new Square[tableSizeY][tableSizeX];
        squareCount = 0;
        players = new Player[2];
        resetPlayers();
        // Р·Р°РїРѕР»РЅРµРЅРёРµ РїРѕР»СЏ
        for (int i = 0, l = field.length; i < l; i++)
        {
            for (int j = 0, l2 = field[i].length; j < l2; j++)
            {
                field[i][j] = new Square();
                field[i][j].fill(new Player(""));
                squareCount++;
                if ((i==3)&&(j==1)) field[i][j].fill(players[0]);
                if ((i==3)&&(j==2)) field[i][j].fill(players[1]);
                if ((i==4)&&(j==1)) field[i][j].fill(players[1]);
                if ((i==4)&&(j==2)) field[i][j].fill(players[0]);
            }
        }
        filled = 4;

        started=false;
        activePlayer=null;
    }
    public void start()
    {
        resetPlayers();
        started=true;
    }
    private void resetPlayers()
    {
        players[0] = new Player("White");
        players[1] = new Player("Black");
        setCurrentActivePlayer(players[0]);
    }
    private void setCurrentActivePlayer(Player player)
    {
        activePlayer = player;
    }

    public Square[][] getField()
    {
        return field;
    }

    public boolean makeTurn(int x, int y)
    {
        if (validTurn(x, y)) {
            makeFill(x, y);
            switchPlayers();
            if (!(gotATurn())){
                switchPlayers();
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){}
                if (!(gotATurn()))
                {
                    filled=squareCount;
                }
            }
            return true;
        } else return false;
    }
    public boolean makeBotTurn()
    {

        int maxPriority=0;
        int numTurns=0;
        for (int i = 0 ; i<tableSizeY; i++)
            for (int j = 0; j<tableSizeX; j++)
            {
                if (validTurn(i, j))
                {
                    if (botPriority[i][j]>maxPriority) {
                        maxPriority = botPriority[i][j];
                        numTurns=1;
                    }
                    if (botPriority[i][j]==maxPriority)
                    numTurns++;
                }
            }
        if (numTurns==0) return false;
        Random r = new Random();
        int t = r.nextInt(numTurns);
        for (int i = 0 ; i<tableSizeY; i++)
            for (int j = 0; j<tableSizeX; j++)
            {
                if (validTurn(i, j))
                {
                    if (botPriority[i][j]==maxPriority) {
                        if (t == 0) {
                            return(makeTurn(i, j));
                        } else t--;
                    }
                }
            }
        return false;
    }
    public boolean gotATurn()
    {
        for (int i = 0; i<tableSizeY; i++)
            for (int j = 0; j<tableSizeX; j++)
            {
                if (validTurn(i, j)) return true;
            }
        return false;
    }
    public Player getCurrentActivePlayer() {
        return activePlayer;
    }
    private void switchPlayers() {
        activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
    }
    public boolean isFieldFilled()
    {
        return squareCount == filled;
    }
    public void reset()
    {
        resetField();
        resetPlayers();
    }
    private void resetField()
    {
        for (int i = 0; i < tableSizeY; i++) {
            for (int j = 0; j < tableSizeX; j++) {
                field[i][j].fill(new Player(""));
                if ((i==3)&&(j==1)) field[i][j].fill(players[0]);
                if ((i==3)&&(j==2)) field[i][j].fill(players[1]);
                if ((i==4)&&(j==1)) field[i][j].fill(players[1]);
                if ((i==4)&&(j==2)) field[i][j].fill(players[0]);
            }
        }
        filled = 4;
    }
    public Player checkWinner()
    {
        int white=0;
        int black=0;
        for (int i = 0, l = field.length; i < l; i++) {
            for (int j = 0, l2 = field[i].length; j < l2; j++) {
                if (field[i][j].getPlayer().getName().equals("White"))
                    white++;
                else black++;
            }
        }
        if (white>black)
            return players[0];
        if (black>white)
            return players[1];
        else return null;
    }
    public int countWhite()
    {
        int count=0;
        for(int i = 0; i<tableSizeY;i++)
            for(int j=0; j<tableSizeX; j++)
            {
                if (field[i][j].isFilled())
                    if (field[i][j].getPlayer().getName().equals("White"))
                        count++;
            }
        return count;
    }
    public int countBlack()
    {
        int count=0;
        for(int i = 0; i<tableSizeY;i++)
            for(int j=0; j<tableSizeX; j++)
            {
                if (field[i][j].isFilled())
                    if (field[i][j].getPlayer().getName().equals("Black"))
                        count++;
            }
        return count;
    }
    public void FindTurns()
    {
        for (int i = 0; i<tableSizeY; i++)
            for (int j = 0; j<tableSizeX; j++)
            {
                if (validTurn(i, j))
                {
                    field[i][j].fill(new Player("turn"));
                }
            }
    }
    public void clearTurns()
    {
        for (int i = 0; i<tableSizeY; i++)
            for (int j = 0; j<tableSizeX; j++)
            {
                if (field[i][j].getPlayer().getName().equals("turn"))
                {
                    field[i][j].fill(new Player(""));
                }
            }
    }
    public boolean validTurn(int x, int y)
    {
        if (field[x][y].isFilled()) return false;
        int a;
        int b;
        boolean row;
        for (int i = -1; i<2; i++)
            for (int j = -1; j<2; j++)
            {
                a=x;
                b=y;
                row=false;
                if ((i!=0)||(j!=0))
                {
                    a+=i;
                    b+=j;

                    if (((a<tableSizeY)&&(a>=0)&&(b>=0)&&(b<tableSizeX))) {
                        if (field[a][b].isFilled())
                            if (!(field[a][b].getPlayer().getName().equals(getCurrentActivePlayer().getName()))) {
                                row = true;
                                a+=i;
                                b+=j;
                            }
                    }

                    while ((a<tableSizeY)&&(a>=0)&&(b>=0)&&(b<tableSizeX)&&(row))
                    {
                        if (field[a][b].isFilled()) {
                            if (field[a][b].getPlayer().getName().equals(getCurrentActivePlayer().getName())) {
                                return true;
                            }
                        } else row = false;
                        a+=i;
                        b+=j;
                    }

                }
            }
        return false;
    }
    public void makeFill(int x, int y)
    {
        field[x][y].fill(getCurrentActivePlayer());
        filled++;
        int a;
        int b;
        boolean row;
        for (int i = -1; i<2; i++)
            for (int j = -1; j<2; j++)
            {
                a=x;
                b=y;
                row=false;
                if ((i!=0)||(j!=0)) {
                    a += i;
                    b += j;

                    if (((a < tableSizeY) && (a >= 0) && (b >= 0) && (b < tableSizeX))) {
                        if (field[a][b].isFilled())
                            if (!(field[a][b].getPlayer().getName().equals(getCurrentActivePlayer().getName()))) {
                                row = true;
                                a += i;
                                b += j;
                            }
                    }

                    while ((a < tableSizeY) && (a >= 0) && (b >= 0) && (b < tableSizeX) && (row)) {
                        if (field[a][b].isFilled()) {
                            if (field[a][b].getPlayer().getName().equals(getCurrentActivePlayer().getName())) {

                                if (a == x) {
                                    int f = b;
                                    for (b = y + j; b != f; b += j) {
                                        field[a][b].fill(getCurrentActivePlayer());
                                        a += i;
                                    }
                                } else {
                                    int f = a;
                                    b = y;
                                    for (a = x + i; a != f; a += i) {
                                        b += j;
                                        field[a][b].fill(getCurrentActivePlayer());
                                    }
                                }
                                row=false;
                            }
                        }else row = false;
                        a += i;
                        b += j;
                    }


                }
            }

    }
}

