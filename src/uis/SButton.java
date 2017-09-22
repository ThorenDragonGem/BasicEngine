package uis;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gfx.Skin2D;

public class SButton extends AbstractButton
{
	private Skin2D skin, hoverSkin, clickSkin, disableSkin, imperativeSkin;
	private Skin2D currentSkinUsed;

	public SButton(Skin2D skin, Skin2D hoverSkin, Skin2D clickSkin, Skin2D disableSkin, int x, int y, int width, int heigth, ClickEvent event)
	{
		super(x, y, width, heigth, event);
		this.skin = skin;
		this.hoverSkin = hoverSkin;
		this.clickSkin = clickSkin;
		this.disableSkin = disableSkin;
		this.imperativeSkin = null;
		this.currentSkinUsed = null;
	}

	@Override
	public void update(double delta)
	{
		super.update(delta);
		if(currentSkinUsed != null)
			currentSkinUsed.update(delta);
	}

	@Override
	public void render(Graphics graphics)
	{
		((Graphics2D)graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		if(!enabled)
		{
			currentSkinUsed = disableSkin;
		}
		else
		{
			if(imperativeSkin != null)
			{
				currentSkinUsed = imperativeSkin;
			}
			else if(hover)
			{
				if(clicked)
				{
					currentSkinUsed = clickSkin;
				}
				else
				{
					currentSkinUsed = hoverSkin;
				}
			}
			else
			{
				currentSkinUsed = skin;
			}
		}
		if(currentSkinUsed.getCurrentSkin() != null)
		{
			graphics.drawImage(currentSkinUsed.getCurrentSkin(), x, y, width, height, null);
		}
		else
		{
			graphics.setColor(Color.magenta);
			graphics.fillRect(x, y, width, height);
		}
		super.render(graphics);
	}

	public SButton setSkin(Skin2D skin)
	{
		this.imperativeSkin = skin;
		return this;
	}
}
