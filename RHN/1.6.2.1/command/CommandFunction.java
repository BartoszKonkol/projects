package net.polishgames.rhenowar.util.command;

import java.util.function.Function;
import net.polishgames.rhenowar.util.IRhenowar;

@FunctionalInterface
public interface CommandFunction<R> extends IRhenowar, Function<String, R> {}
