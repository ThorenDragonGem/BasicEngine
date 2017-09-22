package display;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class Display
{
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	private long screenId;

	public Display(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		screenId = createScreen();
	}

	public long createScreen()
	{
		Random random = new Random();
		long id = random.nextLong();
		while(id == screenId)
		{
			id = random.nextLong();
		}
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack();

		return id;
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public Canvas getCanvas()
	{
		return canvas;
	}

	public String getTitle()
	{
		return title;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
