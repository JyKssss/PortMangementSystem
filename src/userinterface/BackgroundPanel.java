package userinterface;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	private Icon wallpaper;
	public BackgroundPanel() {
		// TODO Auto-generated constructor stub
	}
	protected void paintComponent(Graphics g,JFrame main) {
		 if (null != wallpaper) {
	            processBackGround(g,main);
	        }
	        System.out.println("f:paintComponent(Graphics g)");
	}
	public void setBackGround(Icon wallPaper) {
		this.wallpaper = wallPaper;
		this.repaint();
	}
	
	private void processBackGround(Graphics g,JFrame main) {
		ImageIcon icon = (ImageIcon) wallpaper;
        Image image = icon.getImage();
        int cw = main.getWidth();
        int ch = main.getHeight();
        int iw = image.getWidth(this);
        int ih = image.getHeight(this);
        int x = 0;
        int y = 0;
        while (y <= ch) {
            g.drawImage(image, x, y, this);
            x += iw;
            if (x >= cw) {
                x = 0;
                y += ih;
            }
        }
    }
	
}
