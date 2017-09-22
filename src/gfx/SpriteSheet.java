package gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet
{
	private BufferedImage sheet;

	public SpriteSheet(BufferedImage sheet)
	{
		this.sheet = sheet;
	}

	public BufferedImage crop(int x, int y, int width, int height, boolean ignoreBlanks)
	{
		if(ignoreBlanks)
		{
			int[] pixels = sheet.getRGB(x, y, width, height, null, 0, width);
			for(int i = 0; i < pixels.length; i++)
			{
				if(pixels[i] != 0)
					return sheet.getSubimage(x, y, width, height);
			}
			return null;
		}
		else
		{
			return sheet.getSubimage(x, y, width, height);
		}
	}

	public BufferedImage[] cropArray(int startX, int startY, int width, int height, boolean ignoreBlanks)
	{
		ignoreBlanks = true;
		List<BufferedImage> list = new ArrayList<>();
		for(int y = startY; y < sheet.getHeight(); y += height)
		{
			for(int x = startX; x < sheet.getWidth(); x += width)
			{
				BufferedImage r = crop(x, y, width, height, ignoreBlanks);
				if(r != null)
					list.add(r);
			}
		}
		BufferedImage[] res = new BufferedImage[list.size()];
		for(int i = 0; i < res.length; i++)
			res[i] = list.get(i);
		return res;
	}
}
