package uis;

import java.awt.*;

import engine.Engine;
import inputs.Mouse;

public abstract class AbstractButton
{
	protected ClickEvent event;
	protected Color textColor;
	protected String text;
	protected int x, y, width, height;
	protected float alpha;
	protected boolean hover, clicked, enabled;

	public AbstractButton(int x, int y, int width, int heigth, ClickEvent event)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		height = heigth;
		this.event = event;
		hover = false;
		clicked = false;
		enabled = true;
		alpha = 1f;
	}

	public void update(double delta)
	{
		if(!enabled)
		{
			return;
		}
		hover = false;
		clicked = false;
		if((Engine.inputs.getX() >= x) && (Engine.inputs.getX() <= (x + width)))
		{
			if((Engine.inputs.getY() >= y) && (Engine.inputs.getY() <= (y + height)))
			{
				hover = true;
			}
		}
		if(hover)
		{
			if(Engine.inputs.isButtonDown(Mouse.ONE))
			{
				clicked = true;
			}
			if(Engine.inputs.isButtonPressed(Mouse.ONE))
			{
				event.onClick();
			}
		}
	}

	public void render(Graphics graphics)
	{
		if(textColor != null)
		{
			// TODO: setFont(defaultFont);
			graphics.setColor(textColor);
			graphics.drawString(text, (x + (width / 2)) - (graphics.getFontMetrics().stringWidth(text) / 2), (y + (height / 2)) - (graphics.getFontMetrics().getAscent() / 2));
		}
		((Graphics2D)graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public boolean isHover()
	{
		return hover;
	}

	public boolean isClicked()
	{
		return clicked;
	}

	public AbstractButton setText(String text, Color textColor)
	{
		this.text = text;
		this.textColor = textColor;
		return this;
	}

	public String getText()
	{
		return text;
	}

	public Color getTextColor()
	{
		return textColor;
	}

	public AbstractButton disable()
	{
		enabled = false;
		return this;
	}

	public AbstractButton enable()
	{
		enabled = true;
		return this;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public AbstractButton setBounds(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		return this;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public AbstractButton setClickEvent(ClickEvent event)
	{
		this.event = event;
		return this;
	}

	public float getAlpha()
	{
		return alpha;
	}

	public AbstractButton setAlpha(float alpha)
	{
		this.alpha = alpha;
		return this;
	}
}
