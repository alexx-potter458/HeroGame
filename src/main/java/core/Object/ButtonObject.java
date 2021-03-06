package core.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Boot;

public class ButtonObject {
    private Texture             texture;
    private final Texture       notPressedTexture;
    private final Texture       pressedTexture;
    private final TextBoxObject text;
    private float               x;
    private float               y;
    private final float         width;
    private final float         height;

    public ButtonObject(int x, int y, String text) {
        this.text               = new TextBoxObject(text, x, y, 'm');
        this.width              = 256;
        this.height             = 64;
        this.x                  = x;
        this.y                  = y;
        this.notPressedTexture  = new Texture("textures/button.png");
        this.pressedTexture     = new Texture("textures/pressedButton.png");
        this.texture            = this.notPressedTexture;
        this.x                  = this.x - (width /2);
        this.y                  = this.y - (height /2);
    }

    public void update() {
        if(this.verifyButtonPressed())
            this.texture = this.pressedTexture;
         else
            this.texture = this.notPressedTexture;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        this.text.render(batch);
    }

    private boolean verifyButtonPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&
                (Gdx.input.getY() <= (Boot.bootInstance.getScreenHeight() - this.y) &&
                (Gdx.input.getY() >= (Boot.bootInstance.getScreenHeight() - (this.y + this.height)))) &&
                (Gdx.input.getX() <=  (this.x + this.width) && Gdx.input.getX() >= this.x);
    }

    private boolean verifyButtonJustPressed() {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) &&
                (Gdx.input.getY() <= (Boot.bootInstance.getScreenHeight() - this.y) &&
                (Gdx.input.getY() >= (Boot.bootInstance.getScreenHeight() - (this.y + this.height)))) &&
                (Gdx.input.getX() <=  (this.x + this.width) && Gdx.input.getX() >= this.x);
    }

    public boolean isPressed() {
        return this.verifyButtonPressed();
    }

    public boolean isJustPressed() {
        return this.verifyButtonJustPressed();
    }

    public void changeText(String newText) {
        this.text.setText(newText);
    }

}