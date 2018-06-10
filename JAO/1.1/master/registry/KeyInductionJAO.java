package javax.jao.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.jao.Util;

public final class KeyInductionJAO implements IKey
{

	private final  KeyParentJAO keyParent;
	private final List<KeyJAO> keys;
	
	public KeyInductionJAO(final KeyParentJAO keyParent, final KeyJAO... keys)
	{
		final List<KeyJAO> keysList = new ArrayList<KeyJAO>();
		keysList.add(new KeyJAO(Util.doRequireNonNull(keyParent).giveName()));
		for(KeyJAO key : Util.doRequireNonNull(keys))
			keysList.add(key);
		this.keyParent = keyParent;
		this.keys = Collections.unmodifiableList(keysList);
	}
	
	public KeyInductionJAO(final KeyParentJAO keyParent, final KeyInductionJAO keyInduction, final KeyJAO... keys)
	{
		final List<KeyJAO> keysFromInduction = Util.doRequireNonNull(keyInduction).giveKeys();
		final List<KeyJAO> keysList = new ArrayList<KeyJAO>();
		keysList.add(new KeyJAO(Util.doRequireNonNull(keyParent).giveName()));
		for(int i = 1; i < keysFromInduction.size(); i++)
			keysList.add(keysFromInduction.get(i));
		for(final KeyJAO key : Util.doRequireNonNull(keys))
			keysList.add(key);
		this.keyParent = keyParent;
		this.keys = Collections.unmodifiableList(keysList);
	}
	
	public KeyInductionJAO(final KeyInductionJAO keyInduction, final KeyJAO... keys)
	{
		this(keyInduction.giveKeyParent(), keyInduction, keys);
	}
	
	public final KeyParentJAO giveKeyParent()
	{
		return this.keyParent;
	}
	
	public final List<KeyJAO> giveKeys()
	{
		return this.keys;
	}

	@Override
	public final String giveKey()
	{
		if(this.keys.size() < 3)
			return null;
		else
		{
			String result = "";
			for(int i = 0; i < this.keys.size(); i++)
			{
				final String str = this.keys.get(i).giveKey();
				if(i <= 1)
					result += str.toUpperCase();
				else
					result += str;
				result += ".";
			}
			return result.substring(0, result.length() - 1);
		}
	}
	
}
