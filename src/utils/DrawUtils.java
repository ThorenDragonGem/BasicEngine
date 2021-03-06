package utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class DrawUtils
{
	public static BufferedImage loadImage(String path)
	{
		try
		{
			return createNew(ImageIO.read(DrawUtils.class.getResource(path)), BufferedImage.TYPE_INT_ARGB);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	public static BufferedImage createNew(BufferedImage image, int imageType)
	{
		BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), imageType);
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				res.setRGB(i, j, pixels[i + (j * image.getWidth())]);
			}
		}
		return res;
	}

	public static BufferedImage darkImage(BufferedImage bufferedImage, final double FACTOR)
	{
		BufferedImage image = copyImage(bufferedImage);
		int rgb, r, g, b;
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				if(image.getRGB(i, j) >= 0)
				{
					continue;
				}
				rgb = image.getRGB(i, j);
				r = Math.max((int)(((rgb & 0x00ff0000) >> 16) * FACTOR), 0);
				g = Math.max((int)(((rgb & 0x0000ff00) >> 8) * FACTOR), 0);
				b = Math.max((int)((rgb & 0x000000ff) * FACTOR), 0);
				Color color = new Color(r, g, b);
				image.setRGB(i, j, color.getRGB());
			}
		}
		return image;
	}

	public static BufferedImage brightImage(BufferedImage bufferedImage, final double FACTOR)
	{
		BufferedImage image = copyImage(bufferedImage);
		int rgb, r, g, b;
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				if(image.getRGB(i, j) >= 0)
				{
					continue;
				}
				rgb = image.getRGB(i, j);
				r = Math.min((int)((((rgb & 0x00ff0000) >> 16) * 1) / FACTOR), 255);
				g = Math.min((int)((((rgb & 0x0000ff00) >> 8) * 1) / FACTOR), 255);
				b = Math.min((int)(((rgb & 0x000000ff) * 1) / FACTOR), 255);
				Color color = new Color(r, g, b);
				image.setRGB(i, j, color.getRGB());
			}
		}
		return image;
	}

	public static void fillFullSizedRect(Graphics g, JComponent component, Color c)
	{
		g.setColor(c);
		g.fillRect(0, 0, component.getWidth(), component.getHeight());
	}

	public static BufferedImage colorImage(BufferedImage image, Color color)
	{
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setXORMode(color);
		graphics.drawImage(bufferedImage, null, 0, 0);
		graphics.dispose();
		return bufferedImage;
	}

	public static void activateAntialias(Graphics g)
	{
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	public static void drawCenteredString(Graphics g, String text, Rectangle parent)
	{
		FontMetrics fm = g.getFontMetrics();
		Point centerPos = getStringCenterPos(parent, text, fm, g);
		g.drawString(text, (int)centerPos.getX(), (int)centerPos.getY());
	}

	public static Point getStringCenterPos(Rectangle parent, String str, FontMetrics fontMetrics, Graphics g)
	{
		Rectangle2D stringBounds = fontMetrics.getStringBounds(str, g);
		double x = ((parent.getWidth() - stringBounds.getWidth()) / 2);
		double y = (((parent.getHeight() - stringBounds.getHeight()) / 2) + fontMetrics.getAscent());
		return new Point((int)x, (int)y);
	}

	public static void drawFullsizedImage(Graphics g, JComponent component, Image image)
	{
		g.drawImage(image, 0, 0, component.getWidth(), component.getHeight(), component);
	}

	public static Color getTransparentInstance(Color color, int transparency)
	{
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparency);
	}

	public static BufferedImage copyImage(BufferedImage image)
	{
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static Image fillImage(Image image, Color color, ImageObserver imageObserver)
	{
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, image.getWidth(imageObserver), image.getHeight(imageObserver));
		return image;
	}

	public static BufferedImage rotate(BufferedImage texture, float theta)
	{
		AffineTransform t = new AffineTransform();
		t.rotate(theta, texture.getWidth() / 2, texture.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
		texture = op.filter(texture, null);
		return texture;
	}

	public static BufferedImage setRGB(BufferedImage bufferedImage, float r, float g, float b)
	{
		BufferedImage image = copyImage(bufferedImage);
		int rgb = new Color(r, g, b).getRGB();
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				if(image.getRGB(i, j) < 0)
				{
					image.setRGB(i, j, rgb);
				}
			}
		}
		return image;
	}

	public static BufferedImage setRGBA(BufferedImage bufferedImage, float r, float g, float b, float a)
	{
		BufferedImage image = copyImage(bufferedImage);
		int rgb = new Color(r, g, b, a).getRGB();
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				if(image.getRGB(i, j) < 0)
				{
					image.setRGB(i, j, rgb);
				}
			}
		}
		return image;
	}

	public static BufferedImage applyRGBFilter(BufferedImage bufferedImage, float r, float g, float b)
	{
		BufferedImage image = copyImage(bufferedImage);
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				int ax = image.getColorModel().getAlpha(image.getRaster().getDataElements(i, j, null));
				int rx = image.getColorModel().getRed(image.getRaster().getDataElements(i, j, null));
				int gx = image.getColorModel().getGreen(image.getRaster().getDataElements(i, j, null));
				int bx = image.getColorModel().getBlue(image.getRaster().getDataElements(i, j, null));
				rx *= r;
				gx *= g;
				bx *= b;
				image.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
			}
		}
		return image;
	}

	public static BufferedImage applyRGBAFilter(BufferedImage bufferedImage, float r, float g, float b, float a)
	{
		BufferedImage image = copyImage(bufferedImage);
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				if(pixels[i + (j * image.getWidth())] >= 0)
				{
					continue;
				}
				// int ax =
				// image.getColorModel().getAlpha(image.getRaster().getDataElements(i,
				// j, null));
				// int rx =
				// image.getColorModel().getRed(image.getRaster().getDataElements(i,
				// j, null));
				// int gx =
				// image.getColorModel().getGreen(image.getRaster().getDataElements(i,
				// j, null));
				// int bx =
				// image.getColorModel().getBlue(image.getRaster().getDataElements(i,
				// j, null));
				int rgb = pixels[i + (j * image.getWidth())];
				int ax = ((rgb >> 24) & 0xFF);
				int rx = ((rgb >> 16) & 0xFF);
				int gx = ((rgb >> 8) & 0xFF);
				int bx = rgb & 0xFF;
				ax *= a;
				rx *= r;
				gx *= g;
				bx *= b;
				image.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx << 0));
			}
		}
		return image;
	}

	public static BufferedImage applyAlpha(BufferedImage bufferedImage, byte alpha)
	{
		BufferedImage image = copyImage(bufferedImage);
		alpha %= 0xFF;
		for(int i = 0; i < image.getWidth(); i++)
		{
			for(int j = 0; j < image.getHeight(); j++)
			{
				image.setRGB(i, j, image.getRGB(i, j) & ((alpha << 24) | 0x00FFFFFF));
			}
		}
		return image;
	}

	public static BufferedImage replace(BufferedImage image, Color toReplace, Color replaceColor)
	{
		BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < res.getWidth(); i++)
		{
			for(int j = 0; j < res.getHeight(); j++)
			{
				if(image.getRGB(i, j) == toReplace.getRGB())
				{
					res.setRGB(i, j, replaceColor.getRGB());
					continue;
				}
				res.setRGB(i, j, image.getRGB(i, j));
			}
		}
		return res;
	}
}
