package gfx;

import java.awt.image.BufferedImage;

public class Animation extends Skin2D
{
	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;

	public Animation(int speed, BufferedImage[] frames)
	{
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void update(double delta)
	{
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if(timer > speed)
		{
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
		setCurrentSkin(frames[index]);
	}
}
