package net.polishgames.rhenowar.util.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandHandler
{
	
	public abstract String label();
	public abstract String[] sublabel() default "";
	public abstract String description() default "";
	public abstract String usage() default "";
	public abstract String[] alias() default {};
	public abstract String permission() default "";
	public abstract String permissionMessage() default "";
	public abstract int entitlement() default 0;
	public abstract CommandExecutorType executor() default CommandExecutorType.ALL;
	
}
