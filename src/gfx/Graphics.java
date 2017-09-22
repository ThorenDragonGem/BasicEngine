package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Graphics
{
	private java.awt.Graphics graphics;
	private Bitmap screen;

	public Graphics(java.awt.Graphics graphics, Bitmap screen)
	{
		this.graphics = graphics;
		this.screen = screen;
	}

	public void drawString(String string, int x, int y, Font font, int color)
	{
		graphics.setColor(new Color(color));
		graphics.setFont(font);
		graphics.drawString(string, x, y);
	}

	public void drawScreen(int width, int height)
	{
		graphics.drawImage(screen.getBitmap(), 0, 0, width, height, null);
	}

	public void drawImage(BufferedImage image, int x, int y)
	{
		for(int i = x; i < (image.getWidth() + x); i++)
		{
			for(int j = y; j < (image.getHeight() + y); j++)
			{
				screen.drawPixel(i, j, image.getRGB(i - x, j - y));
			}
		}
	}

	public Graphics setScreen(Bitmap screen)
	{
		this.screen = screen;
		return this;
	}

	public Bitmap getScreen()
	{
		return screen;
	}

	public java.awt.Graphics getGraphics()
	{
		return graphics;
	}

	public Graphics setGraphics(java.awt.Graphics graphics)
	{
		this.graphics = graphics;
		return this;
	}
}