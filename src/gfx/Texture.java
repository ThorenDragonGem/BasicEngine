package gfx;

import java.awt.image.BufferedImage;

public class Texture extends Skin2D
{
	private BufferedImage texture;

	public Texture(BufferedImage texture)
	{
		this.texture = texture;
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
