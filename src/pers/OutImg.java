package pers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class OutImg {

	public static void main(String[] args) throws Exception {
		BufferedImage img = new BufferedImage(150, 100, BufferedImage.TYPE_BYTE_INDEXED);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.black);
		g.rotate(-Math.PI / 10);
		for (int i = 0; i < 12; i++) {
			g.setColor(Color.getHSBColor(random(35), random(35), random(35)));
			g.drawRect(random(100), random(80), 12, 12);
		}
		img.flush();
		ImageIO.write(img, "jpg", new File("G:\\th\\c.jpg"));
	}

	public static int random(int max) {
		return (int) (Math.random() * max);
	}

}
