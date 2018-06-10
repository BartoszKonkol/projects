package javax.jao.setup;

import java.net.URISyntaxException;
import javax.jnf.exception.DefectError;

public final class Main
{
	
	public static final void main(final String[] args)
	{
		if(args.length == 1)
		{
			final String arg = args[0];
			if(arg != null)
			{
				switch(arg.toLowerCase())
				{
					case "-install":
						new Install();
					case "-uninstall":
						new Uninstall();
					default:
						doError(1);
				}
			}
			doError(2);
		}
		doError(3);
	}
	
	public static final void doError(int status)
	{
		throw new DefectError(-status)
		{
			private static final long serialVersionUID = 99800626278570408L;

			@Override
			protected StackTraceElement giveStackTrace()
			{
				return this.getStackTrace()[1];
			}
		};
	}
	
}
