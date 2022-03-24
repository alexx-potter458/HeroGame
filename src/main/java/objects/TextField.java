package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Boot;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class TextField {

    private Texture texture;
    private final Texture notPressedTexture;
    private final Texture pressedTexture;
    private final TextBox text;
    private float x;
    private float y;
    private final float width;
    private final float height;
    private boolean writingActive;

    public TextField(Screen screen, int x, int y) {
        this.text = new TextBox("", x, y, 'm');
        writingActive = false;
        this.width   = 256;
        this.height  = 64;
        this.x = x;
        this.y = y;
        Body body = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.BUTTON);
        this.notPressedTexture = new Texture("textures/textFieldNotPressed.png");
        this.pressedTexture = new Texture("textures/textFieldPressed.png");
        this.texture = this.notPressedTexture;

        this.x = body.getPosition().x * Config.PPM - (width /2);
        this.y = body.getPosition().y * Config.PPM - (height /2);
    }

    public void update() {
        if(this.isJustPressed()) {
            this.writingActive = !this.writingActive;
            this.texture = this.pressedTexture;
        }

        if(writingActive) {
            Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
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

}
