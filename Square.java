package com.example.tiktaktoe;

/**
 * Created by Андрей on 31.03.2016.
 */
public class Square
{
    private Player player = null;

    public void fill(Player player)
    {
        this.player = player;
    }

    public boolean isFilled()
    {
        if (player != null)
        {
            return true;
        }
        return false;
    }

    public Player getPlayer()
    {
        return player;
    }
}