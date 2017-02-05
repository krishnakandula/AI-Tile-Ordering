package models;

import static models.Tile.Color.black;
import static models.Tile.Color.white;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public class Tile{
    public enum Color {
        black,
        white,
        space
    }

    public Enum color;

    public Tile(char input){
        switch (input){
            case 'W':
                this.color = Color.white;
                break;
            case 'B':
                this.color = black;
                break;
            default:
                this.color = Color.space;
        }
    }

    @Override
    public String toString() {
        if(color == white)
            return "W";
        if(color == black)
            return "B";
        return "x";
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tile))
            return false;

        return (this.color == ((Tile) obj).color);
    }
}
