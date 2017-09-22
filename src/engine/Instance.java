package engine;

import java.awt.*;

public interface Instance
{
	void init() throws Exception;

	void update(double delta);

	void render(Graphics graphics);
}
