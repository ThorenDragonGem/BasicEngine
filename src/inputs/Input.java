package inputs;

import java.awt.Component;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private boolean[] keys, justKeysPressed, cantPressKeys, buttons, buttonsPressed, lastButtons;
	private int x, y, scrollX, scrollY;

	public Input(Component... components)
	{
		for(Component component : components)
		{
			component.addKeyListener(this);
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
			component.addMouseWheelListener(this);
		}
		keys = new boolean[Keyboard.LAST];
		justKeysPressed = new boolean[Keyboard.LAST];
		cantPressKeys = new boolean[Keyboard.LAST];
		buttons = new boolean[6];
		buttonsPressed = new boolean[6];
		lastButtons = new boolean[6];
		for(int i = 0; i < keys.length; i++)
		{
			keys[i] = false;
			justKeysPressed[i] = false;
			cantPressKeys[i] = false;
		}
		for(int i = 0; i < buttons.length; i++)
		{
			buttons[i] = false;
		}
	}

	public void update()
	{
		for(int i = 0; i < keys.length; i++)
		{
			if(cantPressKeys[i] && !keys[i])
			{
				cantPressKeys[i] = false;
			}
			else if(justKeysPressed[i])
			{
				cantPressKeys[i] = true;
				justKeysPressed[i] = false;
			}
			if(!cantPressKeys[i] && keys[i])
			{
				justKeysPressed[i] = true;
			}
		}
		for(int i = 0; i < buttons.length; i++)
		{
			lastButtons[i] = buttonsPressed[i];
			buttonsPressed[i] = false;
		}
	}

	public boolean isKeyDown(int keyCode)
	{
		if((keyCode < 0) || (keyCode >= keys.length))
		{
			return false;
		}
		return keys[keyCode];
	}

	public boolean isKeyPressed(int keyCode)
	{
		if((keyCode < 0) || (keyCode >= keys.length))
		{
			return false;
		}
		return justKeysPressed[keyCode];
	}

	public boolean isButtonDown(int buttonCode)
	{
		if((buttonCode < 0) || (buttonCode >= buttons.length))
		{
			return false;
		}
		return buttons[buttonCode];
	}

	public boolean isLastButtonDown(int buttonCode)
	{
		if((buttonCode < 0) || (buttonCode >= buttons.length))
		{
			return false;
		}
		return lastButtons[buttonCode];
	}

	public boolean isButtonPressed(int buttonCode)
	{
		if((buttonCode < 0) || (buttonCode >= buttons.length))
		{
			return false;
		}
		return lastButtons[buttonCode] && !buttonsPressed[buttonCode];
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	int sy;

	public int getScrollY()
	{
		int scroll = scrollY;
		sy++;
		if((sy % 2) == 0)
		{
			scrollY = 0;
			sy = 0;
		}
		return scroll;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if((e.getButton() < 0) || (e.getButton() >= buttons.length))
		{
			return;
		}
		buttons[e.getButton()] = true;
		buttonsPressed[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if((e.getButton() < 0) || (e.getButton() >= buttons.length))
		{
			return;
		}
		buttons[e.getButton()] = false;
		buttonsPressed[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if((e.getKeyCode() < 0) || (e.getKeyCode() >= keys.length))
		{
			return;
		}
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if((e.getKeyCode() < 0) || (e.getKeyCode() >= keys.length))
		{
			return;
		}
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		scrollY = e.getUnitsToScroll();
	}

}