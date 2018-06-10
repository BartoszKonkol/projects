package javax.jao.script;

import java.util.List;
import java.util.Map;
import java.util.Random;

public interface Type
{
	
	public char giveBaseType();
	public Class<?> giveClassType();
	public String giveType();
	
	public enum BaseType implements Type
	{
		
		ARRAY('a', Array.class, "array"),
		BYTE('b', Byte.class, "byte"),
		DOUBLE('d', Double.class, "double"),
		FLOAT('f', Float.class, "float"),
		INTEGER('i', Integer.class, "int"),
		LONG('j', Long.class, "long"),
		LIST('l', List.class, "list"),
		MAP('m', Map.class, "map"),
		RANDOM('r', Random.class, "random"),
		SHORT('s', Short.class, "short"),
		STRING('t', String.class, "string"),
		BOOLEAN('z', Boolean.class, "bool"),
		;
		
		private final char basetype;
		private final Class<?> classtype;
		private final String type;
		
		private BaseType(final char basetype, final Class<?> classtype, final String type)
		{
			this.basetype = basetype;
			this.classtype = classtype;
			this.type = type;
		}
		
		@Override
		public final char giveBaseType()
		{
			return this.basetype;
		}

		@Override
		public Class<?> giveClassType()
		{
			return this.classtype;
		}
		
		@Override
		public final String giveType()
		{
			return this.type;
		}
		
		public static final BaseType valueOfType(final String arg)
		{
			for(final BaseType type : BaseType.values())
				if(type.giveType().equalsIgnoreCase(arg) || (type.giveBaseType() + "*").equalsIgnoreCase(arg))
					return type;
			throw new IllegalArgumentException();
		}
	
	}
	
	public enum ArrayType implements Type
	{
		
		BYTE('b', Byte.class, "byte"),
		DOUBLE('d', Double.class, "double"),
		FLOAT('f', Float.class, "float"),
		INTEGER('i', Integer.class, "int"),
		LONG('j', Long.class, "long"),
		SHORT('s', Short.class, "short"),
		STRING('t', String.class, "string"),
		BOOLEAN('z', Boolean.class, "bool"),
		;
		
		private final char basetype;
		private final Class<?> classtype;
		private final String type;
		
		private ArrayType(final char basetype, final Class<?> classtype, final String type)
		{
			this.basetype = basetype;
			this.classtype = classtype;
			this.type = type;
		}
		
		@Override
		public final char giveBaseType()
		{
			return this.basetype;
		}

		@Override
		public Class<?> giveClassType()
		{
			return this.classtype;
		}
		
		@Override
		public final String giveType()
		{
			return this.type;
		}
	
	}
	
}
