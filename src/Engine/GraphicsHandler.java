package Engine;

import GameObject.ImageEffect;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class GraphicsHandler {
    private Graphics2D g;

    public Graphics2D getGraphics() {
        return g;
    }

    public void setGraphics(Graphics2D g) {
        this.g = g;
    }

    public void drawImage(BufferedImage image, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height, ImageEffect imageEffect) {
        switch (imageEffect) {
            case NONE:
                drawImage(image, x, y, width, height);
                break;
            case FLIP_HORIZONTAL:
                g.drawImage(image, x + width, y, -width, height, null);
                break;
            case FLIP_VERTICAL:
                g.drawImage(image, x, y + height, width, -height, null);
                break;
            case FLIP_H_AND_V:
                g.drawImage(image, x + width, y + height, -width, -height, null);
                break;
            case ROTATE_90_CLOCKWISE:
                g.rotate(Math.toRadians(90), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-90), x + width / 2, y + height / 2);
                break;
            case ROTATE_180_CLOCKWISE:
                g.rotate(Math.toRadians(180), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-180), x + width / 2, y + height / 2);
                break;
            case ROTATE_270_CLOCKWISE:
                g.rotate(Math.toRadians(270), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-270), x + width / 2, y + height / 2);
                break;
            case ROTATE_45_CLOCKWISE:
                g.rotate(Math.toRadians(45), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-45), x + width / 2, y + height / 2);
                break;
            case ROTATE_135_CLOCKWISE:
                g.rotate(Math.toRadians(135), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-135), x + width / 2, y + height / 2);
                break;
            case ROTATE_225_CLOCKWISE:
                g.rotate(Math.toRadians(225), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-225), x + width / 2, y + height / 2);
                break;
            case ROTATE_315_CLOCKWISE:
                g.rotate(Math.toRadians(315), x + width / 2, y + height / 2);
                g.drawImage(image, x, y, width, height, null);
                g.rotate(Math.toRadians(-315), x + width / 2, y + height / 2);
                break;
        }
    }

    public void drawRectangle(int x, int y, int width, int height, Color color) {
        Color oldColor = g.getColor();

        g.setColor(color);
        g.drawRect(x, y, width, height);

        g.setColor(oldColor);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color){
        Color oldcolor = g.getColor();

        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);

        g.setColor(oldcolor);
    }

    public void drawRectangle(int x, int y, int width, int height, Color color, int borderThickness) {
        Stroke oldStroke = g.getStroke();
        Color oldColor = g.getColor();

        g.setStroke(new BasicStroke(borderThickness));
        g.setColor(color);
        g.drawRect(x, y, width, height);

        g.setStroke(oldStroke);
        g.setColor(oldColor);
    }

    public void drawFilledRectangle(int x, int y, int width, int height, Color color) {
        Color oldColor = g.getColor();

        g.setColor(color);
        g.fillRect(x, y, width, height);

        g.setColor(oldColor);
    }

    public void drawFilledRectangleWithBorder(int x, int y, int width, int height, Color fillColor, Color borderColor, int borderThickness) {
        drawFilledRectangle(x, y, width, height, fillColor);
        drawRectangle(x, y, width, height, borderColor, borderThickness);
    }

    public void drawString(String text, int x, int y, Font font, Color color) {
        Font oldFont = g.getFont();
        Color oldColor = g.getColor();

        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);

        g.setFont(oldFont);
        g.setColor(oldColor);
    }

    // https://stackoverflow.com/a/35222059 and https://stackoverflow.com/a/31831120
    public void drawStringWithOutline(String text, int x, int y, Font font, Color textColor, Color outlineColor, float outlineThickness) {
        // remember original settings
        Color originalColor = g.getColor();
        Stroke originalStroke = g.getStroke();
        RenderingHints originalHints = g.getRenderingHints();
        g.setStroke(new BasicStroke(outlineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // create a glyph vector from your text
        GlyphVector glyphVector = font.createGlyphVector(g.getFontRenderContext(), text);

        // get the shape object
        Shape textShape = glyphVector.getOutline();
        AffineTransform at = new AffineTransform();
        at.setToTranslation(Math.round(x), Math.round(y));
        textShape = at.createTransformedShape(textShape);

        // activate anti aliasing for text rendering (if you want it to look nice)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setColor(outlineColor);
        g.draw(textShape); // draw outline

        g.setColor(textColor);
        g.fill(textShape); // fill the shape

        // reset to original settings after painting
        g.setColor(originalColor);
        g.setStroke(originalStroke);
        g.setRenderingHints(originalHints);
    }
}
