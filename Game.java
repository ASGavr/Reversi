package com.example.tiktaktoe;

/**
 * Created by Андрей on 31.03.2016.
 */public class Game
{
    //игроки
    private Player[] players;
    // поле
    private Square[][] field;
    //начата ли игра
    private boolean started;
    //текущий игрок
    private Player activePlayer;
    //число заполненных ячеек
    private int filled;
    //общее число ячеек
    private int squareCount;



    // Конструктор
    public Game()
    {
        field = new Square[4][4];
        squareCount = 0;

        // заполнение поля
        for (int i = 0, l = field.length; i < l; i++)
        {
            for (int j = 0, l2 = field[i].length; j < l2; j++)
            {
                field[i][j] = new Square();
                squareCount++;
            }
        }
        players = new Player[2];
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
        if (field[x][y].isFilled()){
            return false;
        }
        field[x][y].fill(getCurrentActivePlayer());
        filled++;
        switchPlayers();
        return true;
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
        for (int i = 0, l = field.length; i < l; i++) {
            for (int j = 0, l2 = field[i].length; j < l2; j++) {
                field[i][j].fill(null);
            }
        }
        filled = 0;
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
}


