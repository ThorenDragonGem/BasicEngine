package gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap
{
	private int width, height;
	public int[] pixels;
	private BufferedImage bitmap;

	public Bitmap(int width, int height)
	{
		this.width = width;
		this.height = height;
		bitmap = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)bitmap.getRaster().getDataBuffer()).getData();
	}

	public void clear(int color)
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = color;
		}
	}

	public boolean drawPixel(int x, int y, int color)
	{
		if((x < 0) || (y < 0) || (x >= width) || (y >= height))
		{
			return false;
		}
		pixels[x + (y * width)] = color;
		return true;
	}

	public void drawRect(int xp, int yp, int w, int h, int color)
	{
		drawLine(xp, yp, xp + w, yp, color);
		drawLine(xp + w, yp, xp + w, yp + h, color);
		drawLine(xp + w, yp + h, xp, yp + h, color);
		drawLine(xp, yp + h, xp, yp, color);
	}

	public void drawSphere(final int centerX, final int centerY, final int radius, int circleColor)
	{
		int d = (5 - (radius * 4)) / 4;
		int x = 0;
		int y = radius;

		do
		{
			drawPixel(centerX + x, centerY + y, circleColor);
			drawPixel(centerX + x, centerY - y, circleColor);
			drawPixel(centerX - x, centerY + y, circleColor);
			drawPixel(centerX - x, centerY - y, circleColor);
			drawPixel(centerX + y, centerY + x, circleColor);
			drawPixel(centerX + y, centerY - x, circleColor);
			drawPixel(centerX - y, centerY + x, circleColor);
			drawPixel(centerX - y, centerY - x, circleColor);
			if(d < 0)
			{
				d += (2 * x) + 1;
			}
			else
			{
				d += (2 * (x - y)) + 1;
				y--;
			}
			x++;
		}
		while(x <= y);
	}

	public void fillRect(int xp, int yp, int w, int h, int color)
	{
		for(int x = 0; x < w; x++)
		{
			for(int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;

				drawPixel(xx, yy, color);
			}
		}
	}

	public void fillSphere(int xp, int yp, int radius, int color)
	{
		int x = radius;
		int y = 0;
		int xChange = 1 - (radius << 1);
		int yChange = 0;
		int radiusError = 0;

		while(x >= y)
		{
			for(int i = xp - x; i <= (xp + x); i++)
			{
				drawPixel(i, yp + y, color);
				drawPixel(i, yp - y, color);
			}
			for(int i = xp - y; i <= (xp + y); i++)
			{
				drawPixel(i, yp + x, color);
				drawPixel(i, yp - x, color);
			}

			y++;
			radiusError += yChange;
			yChange += 2;
			if(((radiusError << 1) + xChange) > 0)
			{
				x--;
				radiusError += xChange;
				xChange += 2;
			}
		}
	}

	public void drawLine(int x, int y, int x2, int y2, int thickness, int color)
	{
		// double xx = (x2 - x);
		// double yy = (y2 - y);
		// double theta = (Math.PI / 2) - Math.acos(Math.abs(x2 - x) /
		// (Math.sqrt((xx * xx) + (yy * yy))));
		// System.out.println(Math.toDegrees(theta));
		// System.out.println(Math.cos(theta) + " " + Math.sin(theta));
		// double xp = (Math.cos(theta) * (thickness / 2));
		// double yp = (Math.sin(theta) * (thickness / 2));
		//
		// System.out.println(xp + " " + yp);
		//
		// drawLine((int)(x - xp), (int)(y - yp), (int)(x2 - xp), (int)(y2 -
		// yp), color);
		// drawLine(x, y, x2, y2, color);
		// drawLine((int)(x + xp), (int)(y + yp), (int)(x2 + xp), (int)(y2 +
		// yp), color);

		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if(w < 0)
		{
			dx1 = -1;
		}
		else if(w > 0)
		{
			dx1 = 1;
		}
		if(h < 0)
		{
			dy1 = -1;
		}
		else if(h > 0)
		{
			dy1 = 1;
		}
		if(w < 0)
		{
			dx2 = -1;
		}
		else if(w > 0)
		{
			dx2 = 1;
		}
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if(!(longest > shortest))
		{
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if(h < 0)
			{
				dy2 = -1;
			}
			else if(h > 0)
			{
				dy2 = 1;
			}
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for(int i = 0; i <= longest; i++)
		{
			fillRect(x, y, thickness, thickness, color);
			numerator += shortest;
			if(!(numerator < longest))
			{
				numerator -= longest;
				x += dx1;
				y += dy1;
			}
			else
			{
				x += dx2;
				y += dy2;
			}
		}
	}

	public void drawLine(int x, int y, int x2, int y2, int color)
	{
		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if(w < 0)
		{
			dx1 = -1;
		}
		else if(w > 0)
		{
			dx1 = 1;
		}
		if(h < 0)
		{
			dy1 = -1;
		}
		else if(h > 0)
		{
			dy1 = 1;
		}
		if(w < 0)
		{
			dx2 = -1;
		}
		else if(w > 0)
		{
			dx2 = 1;
		}
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if(!(longest > shortest))
		{
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if(h < 0)
			{
				dy2 = -1;
			}
			else if(h > 0)
			{
				dy2 = 1;
			}
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for(int i = 0; i <= longest; i++)
		{
			drawPixel(x, y, color);
			numerator += shortest;
			if(!(numerator < longest))
			{
				numerator -= longest;
				x += dx1;
				y += dy1;
			}
			else
			{
				x += dx2;
				y += dy2;
			}
		}
	}

	public BufferedImage getBitmap()
	{
		return bitmap;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int[] getPixels()
	{
		return pixels;
	}
}
