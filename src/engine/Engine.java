package engine;

import java.awt.image.BufferStrategy;

import assets.Assets;
import display.Display;
import inputs.Input;

public class Engine implements Runnable
{
	public static Input inputs;

	private Thread thread;
	private Display display;
	private static boolean running;
	private static boolean paused = false;

	private BufferStrategy bs;
	private java.awt.Graphics awtGraphics;
	// private Graphics graphics;
	private double tickTime;
	private double renderTime;
	private double delta;
	private double alpha;

	private Instance instance;
	private String title;
	private static int width, height;

	private static int tps = 60;
	private static int fps = -1;

	public static Timer timer;

	public Engine(Instance instance, String title, int width, int height, String assetsFolderName)
	{
		this.instance = instance;
		this.title = title;
		Engine.width = width;
		Engine.height = height;
		new Assets().load(assetsFolderName);
		start();
	}

	private void init() throws Exception
	{
		display = new Display(title, width, height);
		// graphics = new Graphics(null, new Bitmap(width, height));
		inputs = new Input(display.getFrame(), display.getCanvas());

		if(instance != null)
		{
			instance.init();
		}
	}

	private void update()
	{
		inputs.update();
		if(instance != null)
		{
			instance.update(delta / 1000000000);
		}
	}

	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		awtGraphics = bs.getDrawGraphics();
		awtGraphics.clearRect(0, 0, width, height);

		// graphics.setGraphics(awtGraphics);

		if(instance != null)
		{
			// graphics.getScreen().clear(0x000);
			instance.render(awtGraphics);
			// graphics.drawScreen(width, height);
		}

		bs.show();
		awtGraphics.dispose();
	}

	@Override
	public void run()
	{
		tickTime = 1000000000.0 / tps;
		renderTime = 1000000000.0 / fps;
		double updatedTime = 0.0;
		double renderedTime = 0.0;
		delta = 0;
		alpha = 0;

		int secondTime = 0;
		boolean second;
		int frames = 0;
		int ticks = 0;
		timer = new Timer();
		try
		{
			init();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		while(running)
		{
			second = false;
			delta = timer.getElapsed() - updatedTime;
			alpha = timer.getElapsed() - renderedTime;

			if(delta >= tickTime)
			{
				update();
				ticks++;

				secondTime++;
				if((secondTime % tps) == 0)
				{
					second = true;
					secondTime = 0;
				}
				updatedTime += tickTime;
			}
			else if(alpha >= renderTime)
			{
				render();
				frames++;
				renderedTime += renderTime;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			if(second)
			{
				display.getFrame().setTitle(title + " | " + ticks + " tps, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	public synchronized void start()
	{
		if(running)
		{
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop()
	{
		if(!running)
		{
			return;
		}
		running = false;
		try
		{
			System.exit(0);
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void pause()
	{
		paused = true;
	}

	public static void resume()
	{
		paused = false;
	}

	public static boolean isPaused()
	{
		return paused;
	}

	public static int getWidth()
	{
		return width;
	}

	public static int getHeight()
	{
		return height;
	}

	public static int getTps()
	{
		return tps;
	}

	public static void setTps(int tps)
	{
		Engine.tps = tps;
	}

	public static int getFps()
	{
		return fps;
	}

	public static void setFps(int fps)
	{
		Engine.fps = fps;
	}
}
