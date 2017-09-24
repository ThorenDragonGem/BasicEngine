package assets;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import gfx.Colors;
import gfx.Skin2D;
import gfx.SpriteSheet;
import gfx.Texture;

public class Assets
{
	private static HashMap<String, Texture> textures;
	private static HashMap<String, Font> fonts;
	private boolean loaded;

	public Assets()
	{
		textures = new HashMap<>();
		fonts = new HashMap<>();
		loaded = false;
	}

	public boolean load(String assetsFolderName)
	{
		// add null image first just in case there is a 'null' image in textures
		// folder to replace it;
		BufferedImage nullImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		nullImage.setRGB(0, 0, Color.magenta.getRGB());
		nullImage.setRGB(1, 0, Color.black.getRGB());
		nullImage.setRGB(1, 1, Colors.magenta.getRGB());
		nullImage.setRGB(0, 1, Color.black.getRGB());
		nullImage = Scalr.resize(nullImage, Method.SPEED, 64, 64);
		textures.put("null", new Texture(nullImage));
		listFiles(assetsFolderName + "/textures/", Texture.class, "png", "jpg", "bmp");

		fonts.put("defaultFont", new Font("Courier New", Font.BOLD, 20));
		listFiles(assetsFolderName + "/fonts/", Font.class, "ttf", "ttc", "otf", "otc");
		return loaded;
	}

	public <T> List<File> listFiles(String directoryName, T mapType, String... extensions)
	{
		File directory = new File(directoryName);

		List<File> resultList = new ArrayList<>();

		File[] fList = directory.listFiles();
		if(fList != null)
		{
			resultList.addAll(Arrays.asList(fList));
			for(File file : fList)
			{
				if(file.isFile())
				{
					for(String s : extensions)
					{
						if(file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase().contains(s))
						{
							if(mapType == Texture.class)
							{
								addTexture(file);
							}
							else if(mapType == Font.class)
							{
								addFont(file);
							}
						}
					}
				}
				else if(file.isDirectory())
				{
					resultList.addAll(listFiles(file.getAbsolutePath(), mapType));
				}
			}
		}
		return resultList;
	}

	private void addTexture(File file)
	{
		try
		{
			String name = file.getName().substring(0, file.getName().lastIndexOf("."));
			if(name.contains("_sheet"))
			{
				if(name.length() > (name.lastIndexOf('t') + 1))
				{
					int w = Integer.parseInt(name.substring(name.lastIndexOf('t') + 1, name.lastIndexOf('x')));
					int h = Integer.parseInt(name.substring(name.lastIndexOf('x') + 1));
					SpriteSheet sheet = new SpriteSheet(ImageIO.read(file));
					String n = name.substring(0, name.lastIndexOf('x'));
					String m = n.substring(0, n.lastIndexOf('_'));
					String newName = new String(name.toCharArray(), 0, m.length());
					BufferedImage[] array = sheet.cropArray(0, 0, w, h, true);
					for(int i = 0; i < array.length; i++)
					{
						textures.put(newName + "_" + i, new Texture(array[i]));
					}
				}
				else
				{
					SpriteSheet sheet = new SpriteSheet(ImageIO.read(file));
					String n = name.substring(0, name.lastIndexOf('_'));
					String newName = new String(name.toCharArray(), 0, n.length());
					BufferedImage[] array = sheet.cropArray(0, 0, 64, 64, true);
					for(int i = 0; i < array.length; i++)
					{
						textures.put(newName + "_" + i, new Texture(array[i]));
					}
				}
			}
			else
			{
				textures.put(name, new Texture(ImageIO.read(file)));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void addFont(File file)
	{
		try
		{
			fonts.put(file.getName().substring(0, file.getName().lastIndexOf(".")), Font.createFont(Font.TRUETYPE_FONT, file));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static BufferedImage[] getArray(String prefix, int fromIndex, int toIndex)
	{
		BufferedImage[] res = new BufferedImage[(toIndex + 1) - fromIndex];
		for(int i = 0; i < res.length; i++)
		{
			if(textures.get(prefix + "_" + (fromIndex + i)) != null)
			{
				res[i] = textures.get(prefix + "_" + (fromIndex + i)).getTexture();
			}
		}
		return res;
	}

	public static Skin2D getTexture(String name)
	{
		if(textures.containsKey(name))
		{
			return textures.get(name);
		}
		return textures.get("null");
	}

	public static Font getFont(String name)
	{
		if(fonts.containsKey(name))
		{
			return fonts.get(name);
		}
		return fonts.get("defaultFont");
	}
}
