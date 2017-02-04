package models;

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

    @Override
    public String toString() {
        return color.name();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tile))
            return false;

        return (this.color == ((Tile) obj).color);
    }
}
