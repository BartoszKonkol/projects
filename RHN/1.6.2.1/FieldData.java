package net.polishgames.rhenowar.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public final class FieldData extends RhenowarObject
{
	
	private final Field field;
	
	public FieldData(final Field field)
	{
		this.field = Util.nonNull(field);
		this.giveField().setAccessible(true);
	}
	
	public FieldData(final Class<?> clazz, final String nameField) throws NoSuchFieldException, SecurityException
	{
		this(Util.nonNull(clazz).getDeclaredField(Util.nonEmpty(nameField)));
	}
	
	public FieldData(final Object object, final String nameField) throws NoSuchFieldException, SecurityException
	{
		this(object.getClass(), nameField);
	}
	
	public FieldData(final IRhenowar rhenowar, final String nameField) throws NoSuchFieldException, SecurityException
	{
		this((Object) rhenowar, nameField);
		this.setRhenowar(rhenowar);
	}
	
	private IRhenowar rhenowar;
	
	public final Field giveField()
	{
		return this.field;
	}
	
	public final Object giveContent() throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			return this.giveField().get(this.getRhenowar());
		else
			return null;
	}
	
	public final Object giveContent(final IRhenowar rhenowar) throws IllegalArgumentException, IllegalAccessException
	{
		return this.setRhenowar(rhenowar).giveContent();
	}
	
	public final void doDefineContent(final Object value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().set(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final Object value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final byte value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setByte(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final byte value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final short value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setShort(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final short value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final int value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setInt(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final int value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final long value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setLong(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final long value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final float value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setFloat(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final float value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final double value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setDouble(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final double value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final boolean value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setBoolean(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final boolean value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final void doDefineContent(final char value) throws IllegalArgumentException, IllegalAccessException
	{
		if(this.hasRhenowar())
			this.giveField().setChar(this.getRhenowar(), value);
	}
	
	public final void doDefineContent(final IRhenowar rhenowar, final char value) throws IllegalArgumentException, IllegalAccessException
	{
		this.setRhenowar(rhenowar).doDefineContent(value);
	}
	
	public final String giveName()
	{
		return this.giveField().getName();
	}
	
	public final String giveType()
	{
		return this.giveField().getType().getTypeName();
	}
	
	public final String giveModifier()
	{
		return Modifier.toString(this.giveField().getModifiers());
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		map.put("type", this.giveType());
		map.put("modifier", this.giveModifier());
		try
		{
			map.put("content", this.giveContent());
		}
		catch(final IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return map;
	}
	
	public final FieldData setRhenowar(final IRhenowar rhenowar)
	{
		this.rhenowar = Util.nonNull(rhenowar);
		return this;
	}
	
	public final IRhenowar getRhenowar()
	{
		return this.rhenowar;
	}
	
	public final boolean hasRhenowar()
	{
		return this.getRhenowar() != null;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this, "(" + this.giveField().toString() + ")");
		else
			return super.toString();
	}
	
}
