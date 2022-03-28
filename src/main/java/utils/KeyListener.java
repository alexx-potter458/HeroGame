package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyListener {

    public char keyPressed() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.A))
            return 'A';
        if(Gdx.input.isKeyJustPressed(Input.Keys.B))
            return 'B';
        if(Gdx.input.isKeyJustPressed(Input.Keys.C))
            return 'C';
        if(Gdx.input.isKeyJustPressed(Input.Keys.D))
            return 'D';
        if(Gdx.input.isKeyJustPressed(Input.Keys.E))
            return 'E';
        if(Gdx.input.isKeyJustPressed(Input.Keys.F))
            return 'F';
        if(Gdx.input.isKeyJustPressed(Input.Keys.G))
            return 'G';
        if(Gdx.input.isKeyJustPressed(Input.Keys.H))
            return 'H';
        if(Gdx.input.isKeyJustPressed(Input.Keys.I))
            return 'I';
        if(Gdx.input.isKeyJustPressed(Input.Keys.J))
            return 'J';
        if(Gdx.input.isKeyJustPressed(Input.Keys.K))
            return 'K';
        if(Gdx.input.isKeyJustPressed(Input.Keys.L))
            return 'L';
        if(Gdx.input.isKeyJustPressed(Input.Keys.M))
            return 'M';
        if(Gdx.input.isKeyJustPressed(Input.Keys.N))
            return 'N';
        if(Gdx.input.isKeyJustPressed(Input.Keys.O))
            return 'O';
        if(Gdx.input.isKeyJustPressed(Input.Keys.P))
            return 'P';
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q))
            return 'Q';
        if(Gdx.input.isKeyJustPressed(Input.Keys.R))
            return 'R';
        if(Gdx.input.isKeyJustPressed(Input.Keys.S))
            return 'S';
        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
            return 'T';
        if(Gdx.input.isKeyJustPressed(Input.Keys.U))
            return 'U';
        if(Gdx.input.isKeyJustPressed(Input.Keys.V))
            return 'V';
        if(Gdx.input.isKeyJustPressed(Input.Keys.W))
            return 'W';
        if(Gdx.input.isKeyJustPressed(Input.Keys.X))
            return 'X';
        if(Gdx.input.isKeyJustPressed(Input.Keys.Y))
            return 'Y';
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z))
            return 'Z';
        if(Gdx.input.isKeyJustPressed(Input.Keys.PERIOD))
            return '.';
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            return ' ';
        return '`';
    }

}
