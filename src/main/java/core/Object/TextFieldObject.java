package core.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Boot;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.KeyListener;
import utils.ObjectType;

public class TextFieldObject {

    private Texture             texture;
    private final Texture       pressedTexture;
    private final TextBoxObject text;
    private float               x;
    private float               y;
    private final float         width;
    private final float         height;
    private boolean             writingActive;
    private final KeyListener   keys;

    public TextFieldObject(Screen screen, int x, int y) {
        this.text                 = new TextBoxObject("", x, y, 'm');
        writingActive             = false;
        this.width                = 360;
        this.height               = 64;
        this.x                    = x;
        this.y                    = y;
        Body body                 = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.NONOBJECT);
        Texture notPressedTexture = new Texture("textures/textFieldNotPressed.png");
        this.pressedTexture       = new Texture("textures/textFieldPressed.png");
        this.texture              = notPressedTexture;
        this.keys                 = new KeyListener();
        this.x                    = body.getPosition().x * Config.PPM - (width /2);
        this.y                    = body.getPosition().y * Config.PPM - (height /2);
    }

    public void update() {
        if(this.isJustPressed()) {
            this.writingActive = !this.writingActive;
            this.texture       = this.pressedTexture;
        }

        if(writingActive) {
            char c = keys.keyPressed();
            if(c != '`') {
                text.addChar(c);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
                text.removeLastChar();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        this.text.render(batch);
    }

    private boolean verifyButtonJustPressed() {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) &&
                (Gdx.input.getY() <= (Boot.bootInstance.getScreenHeight() - this.y) &&
                        (Gdx.input.getY() >= (Boot.bootInstance.getScreenHeight() - (this.y + this.height)))) &&
                (Gdx.input.getX() <=  (this.x + this.width) && Gdx.input.getX() >= this.x);
    }

    public boolean isJustPressed() {
        return this.verifyButtonJustPressed();
    }

    public String getText() {
        return this.text.getText();
    }

}
