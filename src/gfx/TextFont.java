package gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.DrawUtils;

/**
 * TODO optimizations: currently inefficient as possible
 * @author ThorenDragonGem
 */
public class TextFont
{

	public static TextFont loadFont(String path, int charWidth, int charHeight)
	{
		TextFont font = new TextFont();
		SpriteSheet sheet = new SpriteSheet(DrawUtils.loadImage(path));
		font.chars = sheet.cropArray(0, 0, charWidth, charHeight, false);
		return font;
	}

	public static TextFont loadFont(BufferedImage fontBitmap, int charWidth, int charHeight)
	{
		TextFont font = new TextFont();
		BufferedImage bufferedImage = DrawUtils.replace(fontBitmap, Color.BLACK, new Color(0, 0, 0, 0));
		SpriteSheet sheet = new SpriteSheet(bufferedImage);
		font.chars = sheet.cropArray(0, 0, charWidth, charHeight, false);
		return font;
	}

	private BufferedImage[] chars;
	private Color color = Color.white;

	public void drawChar(Graphics graphics, char c, int x, int y, int size, Color color)
	{
		graphics.drawImage(DrawUtils.replace(chars[getCharIndex(c)], this.color, color), x, y, size, size, null);
		color = this.color;
	}

	public void drawText(Graphics graphics, String text, int x, int y, int size, Color color)
	{
		char[] chars = text.toCharArray();
		for(int i = 0; i < chars.length; i++)
		{
			drawChar(graphics, chars[i], x + (i * size), y, size, color);
		}
	}

	public int getCharIndex(char c)
	{
		switch(c)
		{
			case ' ':
				return 0;
			case '!':
				return 1;
			case '"':
				return 2;
			case '#':
				return 3;
			case '$':
				return 4;
			case '%':
				return 5;
			case '&':
				return 6;
			case '\'':
				return 7;
			case '(':
				return 8;
			case ')':
				return 9;
			case '*':
				return 10;
			case '+':
				return 11;
			case ',':
				return 12;
			case '-':
				return 13;
			case '.':
				return 14;
			case '/':
				return 15;
			case '0':
				return 16;
			case '1':
				return 17;
			case '2':
				return 18;
			case '3':
				return 19;
			case '4':
				return 20;
			case '5':
				return 21;
			case '6':
				return 22;
			case '7':
				return 23;
			case '8':
				return 24;
			case '9':
				return 25;
			case ':':
				return 26;
			case ';':
				return 27;
			case '<':
				return 28;
			case '=':
				return 29;
			case '>':
				return 30;
			case '?':
				return 31;
			case '@':
				return 32;
			case 'A':
				return 33;
			case 'B':
				return 34;
			case 'C':
				return 35;
			case 'D':
				return 36;
			case 'E':
				return 37;
			case 'F':
				return 38;
			case 'G':
				return 39;
			case 'H':
				return 40;
			case 'I':
				return 41;
			case 'J':
				return 42;
			case 'K':
				return 43;
			case 'L':
				return 44;
			case 'M':
				return 45;
			case 'N':
				return 46;
			case 'O':
				return 47;
			case 'P':
				return 48;
			case 'Q':
				return 49;
			case 'R':
				return 50;
			case 'S':
				return 51;
			case 'T':
				return 52;
			case 'U':
				return 53;
			case 'V':
				return 54;
			case 'W':
				return 55;
			case 'X':
				return 56;
			case 'Y':
				return 57;
			case 'Z':
				return 58;
			case '[':
				return 59;
			case '\\':
				return 60;
			case ']':
				return 61;
			case '`':
				return 64;
			case 'a':
				return 65;
			case 'b':
				return 66;
			case 'c':
				return 67;
			case 'd':
				return 68;
			case 'e':
				return 69;
			case 'f':
				return 70;
			case 'g':
				return 71;
			case 'h':
				return 72;
			case 'i':
				return 73;
			case 'j':
				return 74;
			case 'k':
				return 75;
			case 'l':
				return 76;
			case 'm':
				return 77;
			case 'n':
				return 78;
			case 'o':
				return 79;
			case 'p':
				return 80;
			case 'q':
				return 81;
			case 'r':
				return 82;
			case 's':
				return 83;
			case 't':
				return 84;
			case 'u':
				return 85;
			case 'v':
				return 86;
			case 'w':
				return 87;
			case 'x':
				return 88;
			case 'y':
				return 89;
			case 'z':
				return 90;
			case '{':
				return 91;
			case '|':
				return 92;
			case '}':
				return 93;
			case '~':
				return 94;
			case '\u25AF':
				return 95;
			case '€':
				return 96;
			case '\u2026':
				return 100;
			case '^':
				return 105;
			default:
				return 0;
		}
	}

	public BufferedImage[] getChars()
	{
		return chars;
	}

	public Color getColor()
	{
		return color;
	}
}
