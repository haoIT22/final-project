import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class colorGUI extends JButton{
	private Color startColor;
    private Color endColor;

    public colorGUI(String text, Icon icon, Color startColor, Color endColor) {
        super(text, icon);
        this.startColor = startColor;
        this.endColor = endColor;
        setContentAreaFilled(false);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setFont(new Font("Arial", Font.BOLD, 13));
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint buttonGradient = new GradientPaint(
            0, 0, startColor,
            getWidth(), getHeight(), endColor
        );
        g2d.setPaint(buttonGradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
