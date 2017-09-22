package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;

public class Utils
{
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map)
	{
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>()
		{
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for(Map.Entry<K, V> entry : list)
		{
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static BufferedImage buildImage(int width, int height, int type, BufferedImage... imagesToBuild)
	{
		BufferedImage res = new BufferedImage(width, height, type);
		Graphics2D g = res.createGraphics();
		for(BufferedImage image : imagesToBuild)
		{
			g.drawImage(image, 0, 0, width, height, null);
		}
		return res;
	}
}
