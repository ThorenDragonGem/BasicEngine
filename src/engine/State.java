package engine;

import java.awt.Graphics;

public abstract class State
{
	public abstract void onEnter();

	public abstract void onExit();

	public abstract void init() throws Exception;

	public abstract void update(double delta);

	public abstract void render(Graphics graphics);

	private static State currentState;

	public static void setState(State state)
	{
		if(currentState != null)
			currentState.onExit();
		currentState = state;
		if(currentState != null)
			currentState.onEnter();
	}

	public static State getState()
	{
		return currentState;
	}
}
