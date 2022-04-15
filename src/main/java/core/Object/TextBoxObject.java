package core.Object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Boot;
import org.apache.commons.lang3.StringUtils;

public class TextBoxObject {
    private BitmapFont font;
    private final GlyphLayout layout;
    private String text;
    private float width;
    private float height;
    private final int x;
    private int y;

    public TextBoxObject(String text, int x, int y, char size) {
        this.setFontSize(size);
        this.text   = text;
        this.x      = x;
        this.y      = y;
        this.layout = new GlyphLayout(font, this.text);
        this.width  = layout.width / 2;
        this.height = layout.height / 2;
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        font.draw(batch, layout, (x - width), (y + height));
    }

    public void moveY(int value) {
        this.y += value;
    }

    public void setText(String value) {
        this.text   = value;
        this.layout.setText(font, this.text);
        this.width  = layout.width / 2;
        this.height = layout.height / 2;
    }
    public String getText() {
        return this.text;
    }

    public void setFontOpacity(double value) {
        Color rgba = new Color(1f,1f,1f, (float) value);
        this.layout.setText(font, this.text,0, this.text.length(), rgba, 0.0F, 8, false, null);
    }

    public void setTextAndOpacity(String text, double opacity) {
        this.setText(text);
        this.setFontOpacity(opacity);
    }

    private void setFontSize(char size) {
        if(size == 's')
            font = Boot.bootInstance.getFontSmall();
        if(size == 'm')
            font = Boot.bootInstance.getFontMedium();
        if(size == 'l')
            font = Boot.bootInstance.getFontLarge();
        else
            font = Boot.bootInstance.getFontMedium();
    }

    public void changeFontSize(char size) {
        this.setFontSize(size);
        this.layout.setText(font, this.text);
        this.width  = layout.width/2;
        this.height = layout.height/2;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int value) {
         this.y = value;
    }

    public void addChar(char c) {
        this.setText(this.getText() + c);
    }

    public void removeLastChar() {
         this.setText(StringUtils.chop(this.getText()));
    }

}