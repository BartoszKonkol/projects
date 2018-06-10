
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Keys
 * 2014-03-13 - 2014-03-14 [JNF 1.1_05]
 * 2014-04-22 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import static org.lwjgl.input.Keyboard.*;

/**
 * 
 * Support for LWJGL<br>Keys
 * 		<p>
 * Class gives you access to keyboard events.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Keys extends LWJGL
{
	
	public static final Keys ESC =         new Keys(0xF00);
	public static final Keys F1 =          new Keys(0xF01);
	public static final Keys F2 =          new Keys(0xF02);
	public static final Keys F3 =          new Keys(0xF03);
	public static final Keys F4 =          new Keys(0xF04);
	public static final Keys F5 =          new Keys(0xF05);
	public static final Keys F6 =          new Keys(0xF06);
	public static final Keys F7 =          new Keys(0xF07);
	public static final Keys F8 =          new Keys(0xF08);
	public static final Keys F9 =          new Keys(0xF09);
	public static final Keys F10 =         new Keys(0xF0A);
	public static final Keys F11 =         new Keys(0xF0B);
	public static final Keys F12 =         new Keys(0xF0C);
	public static final Keys BACKSPACE =   new Keys(0xF10);
	public static final Keys TAB =         new Keys(0xF11);
	public static final Keys ENTER =       new Keys(0xF18);
	public static final Keys LEFT_SHIFT =  new Keys(0xF19);
	public static final Keys RIGHT_SHIFT = new Keys(0xF1D);
	public static final Keys LEFT_CTRL =   new Keys(0xF1E);
	public static final Keys LEFT_START =  new Keys(0xF1F);
	public static final Keys LEFT_ALT =    new Keys(0xF20);
	public static final Keys RIGHT_ALT =   new Keys(0xF22);
	public static final Keys RIGHT_START = new Keys(0xF23);
	public static final Keys MENU =        new Keys(0xF24);
	public static final Keys RIGHT_CTRL =  new Keys(0xF25);
	
	public static final Keys SHIFT = LEFT_SHIFT;
	public static final Keys CTRL =  LEFT_CTRL;
	public static final Keys START = LEFT_START;
	public static final Keys ALT =   LEFT_ALT;
	
	public static final Keys LEFT_WIN =  LEFT_START;
	public static final Keys RIGHT_WIN = RIGHT_START;
	
	public static final Keys WIN = LEFT_WIN;
	
	public static final Keys SPACE = new Keys(0xF21);
	public static final Keys UP =    new Keys(0xF26);
	public static final Keys LEFT =  new Keys(0xF27);
	public static final Keys DOWN =  new Keys(0xF28);
	public static final Keys RIGHT = new Keys(0xF29);
	
	public static final Keys NUMPAD_0 = new Keys(0xF2A);
	public static final Keys NUMPAD_1 = new Keys(0xF2B);
	public static final Keys NUMPAD_2 = new Keys(0xF2C);
	public static final Keys NUMPAD_3 = new Keys(0xF2D);
	public static final Keys NUMPAD_4 = new Keys(0xF2E);
	public static final Keys NUMPAD_5 = new Keys(0xF2F);
	public static final Keys NUMPAD_6 = new Keys(0xF30);
	public static final Keys NUMPAD_7 = new Keys(0xF31);
	public static final Keys NUMPAD_8 = new Keys(0xF32);
	public static final Keys NUMPAD_9 = new Keys(0xF33);
	
	public static final Keys Q = new Keys('Q');
	public static final Keys W = new Keys('W');
	public static final Keys E = new Keys('E');
	public static final Keys R = new Keys('R');
	public static final Keys T = new Keys('T');
	public static final Keys Y = new Keys('Y');
	public static final Keys U = new Keys('U');
	public static final Keys I = new Keys('I');
	public static final Keys O = new Keys('O');
	public static final Keys P = new Keys('P');
	public static final Keys A = new Keys('A');
	public static final Keys S = new Keys('S');
	public static final Keys D = new Keys('D');
	public static final Keys F = new Keys('F');
	public static final Keys G = new Keys('G');
	public static final Keys H = new Keys('H');
	public static final Keys J = new Keys('J');
	public static final Keys K = new Keys('K');
	public static final Keys L = new Keys('L');
	public static final Keys Z = new Keys('Z');
	public static final Keys X = new Keys('X');
	public static final Keys C = new Keys('C');
	public static final Keys V = new Keys('V');
	public static final Keys B = new Keys('B');
	public static final Keys N = new Keys('N');
	public static final Keys M = new Keys('M');

	public static final Keys NUM_0 = new Keys('0');
	public static final Keys NUM_1 = new Keys('1');
	public static final Keys NUM_2 = new Keys('2');
	public static final Keys NUM_3 = new Keys('3');
	public static final Keys NUM_4 = new Keys('4');
	public static final Keys NUM_5 = new Keys('5');
	public static final Keys NUM_6 = new Keys('6');
	public static final Keys NUM_7 = new Keys('7');
	public static final Keys NUM_8 = new Keys('8');
	public static final Keys NUM_9 = new Keys('9');
	
	/**
	 * The minimum value of the code key on the keyboard. <! VARIABLE >
	 * @since   1.1_05                                    <! PERMANENT >
	 * @version 1.1_05                                    <! VARIABLE >
	 * @author  Bartosz Konkol                            <! VARIABLE >
	 */
	protected static final int MIN_VALUE = 0xF00;
	/**
	 * The maximum value of the code key on the keyboard. <! VARIABLE >
	 * @since   1.1_05                                    <! PERMANENT >
	 * @version 1.1_05                                    <! VARIABLE >
	 * @author  Bartosz Konkol                            <! VARIABLE >
	 */
	protected static final int MAX_VALUE = 0xF39;
	
	/**
	 * 
	 * Defines the class of a specific key on the keyboard.
	 * 
	 * @param key <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	protected Keys(final int key)
	{
		this.setKey(key);
	}
	
	/**
	 * 
	 * Defines the class of a specific key on the keyboard.
	 * 
	 * @param key <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	protected Keys(final char key)
	{
		this.setKeyChar(key);
	}
	
	private int key;
	
	/**
	 * Specify the code of key on the keyboard. <! VARIABLE >
	 * @since   1.1_05                          <! PERMANENT >
	 * @version 1.1_05                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 */
	public final Keys setKey(final int key)
	{
		if(Keys.giveCorrectRange(key))
		{
			this.key = key;
			return this;
		}
		else
			return null;
	}
	
	/**
	 * Specify the code of key on the keyboard. <! VARIABLE >
	 * @since   1.1_05                          <! PERMANENT >
	 * @version 1.1_05                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 */
	public final Keys setKeyChar(final char key)
	{
		final char keyLowercase = (char) String.valueOf(key).toLowerCase().hashCode();
		
		if(Keys.giveCorrectRangeChar(keyLowercase))
			return this.setKey(keyLowercase);
		else
			return null;
	}
	
	/**
	 * Return the code of key on the keyboard. <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */
	public final int getKey()
	{
		return this.key;
	}
	
	/**
	 * Return the code of key on the keyboard. <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */
	public final char getKeyChar()
	{
		return (char) this.getKey();
	}
	
	/**
	 * javax.jnf.lwjgl.Keys.isKeyPress()
	 * @action <! VARIABLE >
	 * Checks whether the specified key has been pressed.
	 * @return <! VARIABLE >
	 *          If the specified key is pressing down, the result is 'true'. <br>
	 *          Otherwise, the result is 'false'.                            <br>
	 * [boolean]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public boolean isKeyPress()
	{
		final Keys key = Keys.giveKeyPress();
		
		if(key != null)
			return this.getKey() == key.getKey();
		
		return false;
	}
	
	/**
	 * Checks whether the specified character code is correct. <! VARIABLE >
	 * @since   1.1_05                                         <! PERMANENT >
	 * @version 1.1_05                                         <! VARIABLE >
	 * @author  Bartosz Konkol                                 <! VARIABLE >
	 */
	protected static boolean giveCorrectRangeChar(final char character)
	{
		return 
			character == 'q' ||
			character == 'w' ||
			character == 'e' ||
			character == 'r' ||
			character == 't' ||
			character == 'y' ||
			character == 'u' ||
			character == 'i' ||
			character == 'o' ||
			character == 'p' ||
			character == 'a' ||
			character == 's' ||
			character == 'd' ||
			character == 'f' ||
			character == 'g' ||
			character == 'h' ||
			character == 'j' ||
			character == 'k' ||
			character == 'l' ||
			character == 'z' ||
			character == 'x' ||
			character == 'c' ||
			character == 'v' ||
			character == 'b' ||
			character == 'n' ||
			character == 'm' ||
			character == '0' ||
			character == '1' ||
			character == '2' ||
			character == '3' ||
			character == '4' ||
			character == '5' ||
			character == '6' ||
			character == '7' ||
			character == '8' ||
			character == '9'
				;
	}
	
	/**
	 * Checks whether the specified character code is correct. <! VARIABLE >
	 * @since   1.1_05                                         <! PERMANENT >
	 * @version 1.1_05                                         <! VARIABLE >
	 * @author  Bartosz Konkol                                 <! VARIABLE >
	 */
	protected static boolean giveCorrectRange(final int number)
	{
		if(giveCorrectRangeChar((char) number))
			return true;
		else
			return number >= MIN_VALUE && number <= MAX_VALUE;
	}
	
	/**
	 * javax.jnf.lwjgl.Keys.giveKeys(int key)
	 * @action <! VARIABLE >
	 * Generates a class of key on the keyboard.
	 * @return <! VARIABLE >
	 *          If the character code is correct, the result is class. <br>
	 *          Otherwise, the result is 'null'.                       <br>
	 * [Keys]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static Keys giveKeys(final int key)
	{
		if(giveCorrectRange(key))
			return new Keys(key);
		else
			return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Keys.giveKeys(char key)
	 * @action <! VARIABLE >
	 * Generates a class of key on the keyboard.
	 * @return <! VARIABLE >
	 *          If the character code is correct, the result is class. <br>
	 *          Otherwise, the result is 'null'.                       <br>
	 * [Keys]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static Keys giveKeys(final char key)
	{
		if(giveCorrectRangeChar(key))
			return new Keys(key);
		else
			return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Keys.giveKeyPress()
	 * @action <! VARIABLE >
	 * Generates a class of key on the keyboard, on the basis of the currently pressed key.
	 * @return <! VARIABLE >
	 *          If the system 'Keys' supports the key, the result is the class of the key on the keyboard. <br>
	 *          Otherwise, the result is 'null'.                                                           <br>
	 * [Keys]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static Keys giveKeyPress()
	{
		if(isKeyDown(KEY_0))
			return NUM_0;
		if(isKeyDown(KEY_1))
			return NUM_1;
		if(isKeyDown(KEY_2))
			return NUM_2;
		if(isKeyDown(KEY_3))
			return NUM_3;
		if(isKeyDown(KEY_4))
			return NUM_4;
		if(isKeyDown(KEY_5))
			return NUM_5;
		if(isKeyDown(KEY_6))
			return NUM_6;
		if(isKeyDown(KEY_7))
			return NUM_7;
		if(isKeyDown(KEY_8))
			return NUM_8;
		if(isKeyDown(KEY_9))
			return NUM_9;
		if(isKeyDown(KEY_A))
			return A;
		if(isKeyDown(KEY_B))
			return B;
		if(isKeyDown(KEY_C))
			return C;
		if(isKeyDown(KEY_D))
			return D;
		if(isKeyDown(KEY_E))
			return E;
		if(isKeyDown(KEY_F))
			return F;
		if(isKeyDown(KEY_G))
			return G;
		if(isKeyDown(KEY_H))
			return H;
		if(isKeyDown(KEY_I))
			return I;
		if(isKeyDown(KEY_J))
			return J;
		if(isKeyDown(KEY_K))
			return K;
		if(isKeyDown(KEY_L))
			return L;
		if(isKeyDown(KEY_M))
			return M;
		if(isKeyDown(KEY_N))
			return N;
		if(isKeyDown(KEY_O))
			return O;
		if(isKeyDown(KEY_P))
			return P;
		if(isKeyDown(KEY_Q))
			return Q;
		if(isKeyDown(KEY_R))
			return R;
		if(isKeyDown(KEY_S))
			return S;
		if(isKeyDown(KEY_T))
			return T;
		if(isKeyDown(KEY_U))
			return U;
		if(isKeyDown(KEY_V))
			return V;
		if(isKeyDown(KEY_W))
			return W;
		if(isKeyDown(KEY_X))
			return X;
		if(isKeyDown(KEY_Y))
			return Y;
		if(isKeyDown(KEY_Z))
			return Z;
		if(isKeyDown(KEY_ESCAPE))
			return ESC;
		if(isKeyDown(KEY_F1))
			return F1;
		if(isKeyDown(KEY_F2))
			return F2;
		if(isKeyDown(KEY_F3))
			return F3;
		if(isKeyDown(KEY_F4))
			return F4;
		if(isKeyDown(KEY_F5))
			return F5;
		if(isKeyDown(KEY_F6))
			return F6;
		if(isKeyDown(KEY_F7))
			return F7;
		if(isKeyDown(KEY_F8))
			return F8;
		if(isKeyDown(KEY_F9))
			return F9;
		if(isKeyDown(KEY_F10))
			return F10;
		if(isKeyDown(KEY_F11))
			return F11;
		if(isKeyDown(KEY_F12))
			return F12;
		if(isKeyDown(KEY_GRAVE))
			return new Keys(0xF0D);
		if(isKeyDown(KEY_MINUS))
			return new Keys(0xF0E);
		if(isKeyDown(KEY_EQUALS))
			return new Keys(0xF0F);
		if(isKeyDown(KEY_BACK))
			return BACKSPACE;
		if(isKeyDown(KEY_TAB))
			return TAB;
		if(isKeyDown(KEY_LBRACKET))
			return new Keys(0xF12);
		if(isKeyDown(KEY_RBRACKET))
			return new Keys(0xF13);
		if(isKeyDown(KEY_BACKSLASH))
			return new Keys(0xF14);
		if(isKeyDown(KEY_CAPITAL))
			return new Keys(0xF15);
		if(isKeyDown(KEY_SEMICOLON))
			return new Keys(0xF16);
		if(isKeyDown(KEY_APOSTROPHE))
			return new Keys(0xF17);
		if(isKeyDown(KEY_RETURN))
			return ENTER;
		if(isKeyDown(KEY_LSHIFT))
			return LEFT_SHIFT;
		if(isKeyDown(KEY_COMMA))
			return new Keys(0xF1A);
		if(isKeyDown(KEY_PERIOD))
			return new Keys(0xF1B);
		if(isKeyDown(KEY_SLASH))
			return new Keys(0xF1C);
		if(isKeyDown(KEY_RSHIFT))
			return RIGHT_SHIFT;
		if(isKeyDown(KEY_LCONTROL))
			return LEFT_CTRL;
		if(isKeyDown(KEY_LMETA))
			return LEFT_START;
		if(isKeyDown(KEY_LMENU))
			return LEFT_ALT;
		if(isKeyDown(KEY_SPACE))
			return SPACE;
		if(isKeyDown(KEY_RMENU))
			return RIGHT_ALT;
		if(isKeyDown(KEY_RMETA))
			return RIGHT_START;
		if(isKeyDown(KEY_APPS))
			return MENU;
		if(isKeyDown(KEY_RCONTROL))
			return RIGHT_CTRL;
		if(isKeyDown(KEY_UP))
			return UP;
		if(isKeyDown(KEY_LEFT))
			return LEFT;
		if(isKeyDown(KEY_DOWN))
			return DOWN;
		if(isKeyDown(KEY_RIGHT))
			return RIGHT;
		if(isKeyDown(KEY_NUMPAD0))
			return NUMPAD_0;
		if(isKeyDown(KEY_NUMPAD1))
			return NUMPAD_1;
		if(isKeyDown(KEY_NUMPAD2))
			return NUMPAD_2;
		if(isKeyDown(KEY_NUMPAD3))
			return NUMPAD_3;
		if(isKeyDown(KEY_NUMPAD4))
			return NUMPAD_4;
		if(isKeyDown(KEY_NUMPAD5))
			return NUMPAD_5;
		if(isKeyDown(KEY_NUMPAD6))
			return NUMPAD_6;
		if(isKeyDown(KEY_NUMPAD7))
			return NUMPAD_7;
		if(isKeyDown(KEY_NUMPAD8))
			return NUMPAD_8;
		if(isKeyDown(KEY_NUMPAD9))
			return NUMPAD_9;
		if(isKeyDown(KEY_DIVIDE))
			return new Keys(0xF34);
		if(isKeyDown(KEY_MULTIPLY))
			return new Keys(0xF35);
		if(isKeyDown(KEY_SUBTRACT))
			return new Keys(0xF36);
		if(isKeyDown(KEY_ADD))
			return new Keys(0xF37);
		if(isKeyDown(KEY_NUMPADENTER))
			return new Keys(0xF38);
		if(isKeyDown(KEY_DECIMAL))
			return new Keys(0xF39);
		
		return null;
	}
	
}
