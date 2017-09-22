package gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SolidSkin extends Skin2D
{
	private BufferedImage texture;

	public SolidSkin(Color color)
	{
		texture = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		texture.setRGB(0, 0, color.getRGB());
		setCurrentSkin(texture);
	}

	@Override
	public void update(double delta)
	{
		setCurrentSkin(texture);
	}

	public BufferedImage getTexture()
	{
		return texture;
	}
}
