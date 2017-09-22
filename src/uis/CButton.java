package uis;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CButton extends AbstractButton
{
	private Color color, hoverColor, clickColor, disabledColor, imperativeColor;

	public CButton(Color color, Color hoverColor, Color clickColor, Color disableColor, int x, int y, int width, int height, ClickEvent event)
	{
		super(x, y, width, height, event);
		this.color = color;
		this.hoverColor = hoverColor == null ? color.darker() : hoverColor;
		this.clickColor = clickColor == null ? color.brighter() : clickColor;
		this.disabledColor = disableColor == null ? Color.gray : disableColor;
		this.imperativeColor = null;
	}

	@Override
	public void update(double delta)
	{
		super.update(delta);
	}

	@Override
	public void render(Graphics graphics)
	{
		((Graphics2D)graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		if(!enabled)
		{
			graphics.setColor(disabledColor);
		}
		else
		{
			if(imperativeColor != null)
			{
				graphics.setColor(imperativeColor);
			}
			else if(hover)
			{
				if(clicked)
				{
					graphics.setColor(clickColor);
				}
				else
				{
					graphics.setColor(hoverColor);
				}
			}
			else
			{
				graphics.setColor(color);
			}
		}
		graphics.fillRect(x, y, width, height);
		super.render(graphics);
	}

	public CButton setColor(Color color)
	{
		this.imperativeColor = color;
		return this;
	}
}
