package com.example.tiktaktoe;

/**
 * Created by Андрей on 31.03.2016.
 */
public class Player
{
    private String name;

    public Player(String name)
    {
        this.name = name;
    }

    public CharSequence getName()
    {
        return (CharSequence) name;
    }
}