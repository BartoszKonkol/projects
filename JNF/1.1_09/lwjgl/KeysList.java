
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.KeysList
 * 2014-04-02 - 2014-04-03 [JNF 1.1_05]
 * 2014-04-22 - 2014-04-22 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.input.Keyboard.*;

/**
 * 
 * Support for LWJGL<br>Keys List
 * 		<p>
 * Class gives you expanded access to keyboard events.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_05         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 * 
 * @see javax.jnf.lwjgl.Keys
 *
 */

public class KeysList extends Keys
{

	public static final KeysList GRAVE =  new KeysList(0xF0D);
	public static final KeysList HYPHEN = new KeysList(0xF0E);
	public static final KeysList EQUALS = new KeysList(0xF0F);
	
	public static final KeysList LEFT_BRACKET =  new KeysList(0xF12);
	public static final KeysList RIGHT_BRACKET = new KeysList(0xF13);
	public static final KeysList BACK_SLASH =    new KeysList(0xF14);
	public static final KeysList CAPSLOCK =      new KeysList(0xF15);
	public static final KeysList SEMICOLON =     new KeysList(0xF16);
	public static final KeysList APOSTROPHE =    new KeysList(0xF17);
	
	public static final KeysList COMMA =       new KeysList(0xF1A);
	public static final KeysList PERIOD =      new KeysList(0xF1B);
	public static final KeysList FRONT_SLASH = new KeysList(0xF1C);
	
	public static final KeysList NUMPAD_SLASH = new KeysList(0xF34);
	public static final KeysList ASTERISK =     new KeysList(0xF35);
	public static final KeysList MINUS =        new KeysList(0xF36);
	public static final KeysList PLUS =         new KeysList(0xF37);
	public static final KeysList NUMPAD_ENTER = new KeysList(0xF38);
	public static final KeysList NUMPAD_COMMA = new KeysList(0xF39);
	
	public static final KeysList BRACKET = LEFT_BRACKET;
	public static final KeysList SLASH =   FRONT_SLASH;
	
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
	protected KeysList(int key)
	{
		super(key);
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
	protected KeysList(char key)
	{
		super(key);
	}
	
	/**
	 * javax.jnf.lwjgl.KeysList.isKeyPress()
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
	 */@Override
	public boolean isKeyPress()
	{
		final List<KeysList> keys = KeysList.giveKeysPressList();
		
		for(int i = 0; i < keys.size(); i++)
			if(this.getKey() == keys.get(i).getKey())
				return true;
		
		return false;
	}
	
	/**
	 * javax.jnf.lwjgl.Keys.giveKeyPress()
	 * @action <! VARIABLE >
	 * Generates a class of list keys on the keyboard, on the basis of the currently pressed keys.
	 * @return <! VARIABLE >
	 *                 If the system 'Keys' supports the key, the result is the class of the keys list on the keyboard. <br>
	 *                 Otherwise, the result is 'null'.                                                                 <br>
	 * [List<KeysList>]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static List<KeysList> giveKeysPressList()
	{
		final List<KeysList> keys = new ArrayList<KeysList>();
		
		if(isKeyDown(KEY_0))
			keys.add(giveKeysList(NUM_0));
		if(isKeyDown(KEY_1))
			keys.add(giveKeysList(NUM_1));
		if(isKeyDown(KEY_2))
			keys.add(giveKeysList(NUM_2));
		if(isKeyDown(KEY_3))
			keys.add(giveKeysList(NUM_3));
		if(isKeyDown(KEY_4))
			keys.add(giveKeysList(NUM_4));
		if(isKeyDown(KEY_5))
			keys.add(giveKeysList(NUM_5));
		if(isKeyDown(KEY_6))
			keys.add(giveKeysList(NUM_6));
		if(isKeyDown(KEY_7))
			keys.add(giveKeysList(NUM_7));
		if(isKeyDown(KEY_8))
			keys.add(giveKeysList(NUM_8));
		if(isKeyDown(KEY_9))
			keys.add(giveKeysList(NUM_9));
		if(isKeyDown(KEY_A))
			keys.add(giveKeysList(A));
		if(isKeyDown(KEY_B))
			keys.add(giveKeysList(B));
		if(isKeyDown(KEY_C))
			keys.add(giveKeysList(C));
		if(isKeyDown(KEY_D))
			keys.add(giveKeysList(D));
		if(isKeyDown(KEY_E))
			keys.add(giveKeysList(E));
		if(isKeyDown(KEY_F))
			keys.add(giveKeysList(F));
		if(isKeyDown(KEY_G))
			keys.add(giveKeysList(G));
		if(isKeyDown(KEY_H))
			keys.add(giveKeysList(H));
		if(isKeyDown(KEY_I))
			keys.add(giveKeysList(I));
		if(isKeyDown(KEY_J))
			keys.add(giveKeysList(J));
		if(isKeyDown(KEY_K))
			keys.add(giveKeysList(K));
		if(isKeyDown(KEY_L))
			keys.add(giveKeysList(L));
		if(isKeyDown(KEY_M))
			keys.add(giveKeysList(M));
		if(isKeyDown(KEY_N))
			keys.add(giveKeysList(N));
		if(isKeyDown(KEY_O))
			keys.add(giveKeysList(O));
		if(isKeyDown(KEY_P))
			keys.add(giveKeysList(P));
		if(isKeyDown(KEY_Q))
			keys.add(giveKeysList(Q));
		if(isKeyDown(KEY_R))
			keys.add(giveKeysList(R));
		if(isKeyDown(KEY_S))
			keys.add(giveKeysList(S));
		if(isKeyDown(KEY_T))
			keys.add(giveKeysList(T));
		if(isKeyDown(KEY_U))
			keys.add(giveKeysList(U));
		if(isKeyDown(KEY_V))
			keys.add(giveKeysList(V));
		if(isKeyDown(KEY_W))
			keys.add(giveKeysList(W));
		if(isKeyDown(KEY_X))
			keys.add(giveKeysList(X));
		if(isKeyDown(KEY_Y))
			keys.add(giveKeysList(Y));
		if(isKeyDown(KEY_Z))
			keys.add(giveKeysList(Z));
		if(isKeyDown(KEY_ESCAPE))
			keys.add(giveKeysList(ESC));
		if(isKeyDown(KEY_F1))
			keys.add(giveKeysList(F1));
		if(isKeyDown(KEY_F2))
			keys.add(giveKeysList(F2));
		if(isKeyDown(KEY_F3))
			keys.add(giveKeysList(F3));
		if(isKeyDown(KEY_F4))
			keys.add(giveKeysList(F4));
		if(isKeyDown(KEY_F5))
			keys.add(giveKeysList(F5));
		if(isKeyDown(KEY_F6))
			keys.add(giveKeysList(F6));
		if(isKeyDown(KEY_F7))
			keys.add(giveKeysList(F7));
		if(isKeyDown(KEY_F8))
			keys.add(giveKeysList(F8));
		if(isKeyDown(KEY_F9))
			keys.add(giveKeysList(F9));
		if(isKeyDown(KEY_F10))
			keys.add(giveKeysList(F10));
		if(isKeyDown(KEY_F11))
			keys.add(giveKeysList(F11));
		if(isKeyDown(KEY_F12))
			keys.add(giveKeysList(F12));
		if(isKeyDown(KEY_GRAVE))
			keys.add(GRAVE);
		if(isKeyDown(KEY_MINUS))
			keys.add(HYPHEN);
		if(isKeyDown(KEY_EQUALS))
			keys.add(EQUALS);
		if(isKeyDown(KEY_BACK))
			keys.add(giveKeysList(BACKSPACE));
		if(isKeyDown(KEY_TAB))
			keys.add(giveKeysList(TAB));
		if(isKeyDown(KEY_LBRACKET))
			keys.add(LEFT_BRACKET);
		if(isKeyDown(KEY_RBRACKET))
			keys.add(RIGHT_BRACKET);
		if(isKeyDown(KEY_BACKSLASH))
			keys.add(BACK_SLASH);
		if(isKeyDown(KEY_CAPITAL))
			keys.add(CAPSLOCK);
		if(isKeyDown(KEY_SEMICOLON))
			keys.add(SEMICOLON);
		if(isKeyDown(KEY_APOSTROPHE))
			keys.add(APOSTROPHE);
		if(isKeyDown(KEY_RETURN))
			keys.add(giveKeysList(ENTER));
		if(isKeyDown(KEY_LSHIFT))
			keys.add(giveKeysList(LEFT_SHIFT));
		if(isKeyDown(KEY_COMMA))
			keys.add(COMMA);
		if(isKeyDown(KEY_PERIOD))
			keys.add(PERIOD);
		if(isKeyDown(KEY_SLASH))
			keys.add(FRONT_SLASH);
		if(isKeyDown(KEY_RSHIFT))
			keys.add(giveKeysList(RIGHT_SHIFT));
		if(isKeyDown(KEY_LCONTROL))
			keys.add(giveKeysList(LEFT_CTRL));
		if(isKeyDown(KEY_LMETA))
			keys.add(giveKeysList(LEFT_START));
		if(isKeyDown(KEY_LMENU))
			keys.add(giveKeysList(LEFT_ALT));
		if(isKeyDown(KEY_SPACE))
			keys.add(giveKeysList(SPACE));
		if(isKeyDown(KEY_RMENU))
			keys.add(giveKeysList(RIGHT_ALT));
		if(isKeyDown(KEY_RMETA))
			keys.add(giveKeysList(RIGHT_START));
		if(isKeyDown(KEY_APPS))
			keys.add(giveKeysList(MENU));
		if(isKeyDown(KEY_RCONTROL))
			keys.add(giveKeysList(RIGHT_CTRL));
		if(isKeyDown(KEY_UP))
			keys.add(giveKeysList(UP));
		if(isKeyDown(KEY_LEFT))
			keys.add(giveKeysList(LEFT));
		if(isKeyDown(KEY_DOWN))
			keys.add(giveKeysList(DOWN));
		if(isKeyDown(KEY_RIGHT))
			keys.add(giveKeysList(RIGHT));
		if(isKeyDown(KEY_NUMPAD0))
			keys.add(giveKeysList(NUMPAD_0));
		if(isKeyDown(KEY_NUMPAD1))
			keys.add(giveKeysList(NUMPAD_1));
		if(isKeyDown(KEY_NUMPAD2))
			keys.add(giveKeysList(NUMPAD_2));
		if(isKeyDown(KEY_NUMPAD3))
			keys.add(giveKeysList(NUMPAD_3));
		if(isKeyDown(KEY_NUMPAD4))
			keys.add(giveKeysList(NUMPAD_4));
		if(isKeyDown(KEY_NUMPAD5))
			keys.add(giveKeysList(NUMPAD_5));
		if(isKeyDown(KEY_NUMPAD6))
			keys.add(giveKeysList(NUMPAD_6));
		if(isKeyDown(KEY_NUMPAD7))
			keys.add(giveKeysList(NUMPAD_7));
		if(isKeyDown(KEY_NUMPAD8))
			keys.add(giveKeysList(NUMPAD_8));
		if(isKeyDown(KEY_NUMPAD9))
			keys.add(giveKeysList(NUMPAD_9));
		if(isKeyDown(KEY_DIVIDE))
			keys.add(NUMPAD_SLASH);
		if(isKeyDown(KEY_MULTIPLY))
			keys.add(ASTERISK);
		if(isKeyDown(KEY_SUBTRACT))
			keys.add(MINUS);
		if(isKeyDown(KEY_ADD))
			keys.add(PLUS);
		if(isKeyDown(KEY_NUMPADENTER))
			keys.add(NUMPAD_ENTER);
		if(isKeyDown(KEY_DECIMAL))
			keys.add(NUMPAD_COMMA);
		
		return keys;
	}
	
	/**
	 * javax.jnf.lwjgl.KeysList.giveKeysList(Keys keys)
	 * @action <! VARIABLE >
	 * Converting class keys on class of keys list.
	 * @return <! VARIABLE >
	 *           Returns the converted class of keys list on the keyboard. <br>
	 * [KeysList]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static KeysList giveKeysList(final Keys keys)
	{
		return new KeysList(keys.getKey());
	}
	
	/**
	 * javax.jnf.lwjgl.KeysList.giveKeys(KeysList keys)
	 * @action <! VARIABLE >
	 * Converting class keys list on class of keys.
	 * @return <! VARIABLE >
	 *       Returns the converted class of keys on the keyboard. <br>
	 * [Keys]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static Keys giveKeys(final KeysList keys)
	{
		return new Keys(keys.getKey());
	}
	
}
