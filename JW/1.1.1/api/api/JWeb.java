package net.polishgames.vis2.server.jweb.api;

import java.io.File;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.regex.Pattern;
import net.polishgames.vis2.server.api.CookieHTTP;
import net.polishgames.vis2.server.api.RequestHTTP;
import net.polishgames.vis2.server.jweb.ftp.GuideFTP;
import net.polishgames.vis2.server.jweb.jws.IScript;
import net.polishgames.vis2.server.jweb.jws.operation.AbstractKitJWS;
import net.polishgames.vis2.server.jweb.jws.operation.OperationJWS;
import net.polishgames.vis2.server.jweb.jws.parameter.ParameterJWS;

public abstract class JWeb
{
	
	private static JWeb instance;
	
	public abstract JWeb addScript(final String lang, final Function<String, String> function);
	public abstract JWeb delScript(final String lang, final Function<String, String> function);
	public abstract JWeb addEvent(final String name, final Function<ParameterJWS[], ParameterJWS> function);
	public abstract JWeb delEvent(final String name);
	public abstract JWeb addFunctions(final FunctionManager functions);
	public abstract JWeb delFunctions(final FunctionManager functions);
	public abstract JWeb addOperation(final Function<IScript, OperationJWS> function);
	public abstract JWeb delOperation(final Function<IScript, OperationJWS> function);
	public abstract JWeb addGuide(final GuideFTP guide);
	public abstract JWeb delGuide(final GuideFTP guide);
	public abstract JWeb addOutline(final String name, final Function<Properties, String> function);
	public abstract JWeb delOutline(final String name);
	public abstract JWeb addPlugin(final String hash, final Plugin plugin);
	public abstract JWeb delPlugin(final String hash, final Plugin plugin);
	public abstract Collection<Function<String, String>> giveScript(final String lang);
	public abstract Map<String, Function<ParameterJWS[], ParameterJWS>> giveEvents();
	public abstract Collection<FunctionManager> giveFunctions();
	public abstract Collection<Function<IScript, OperationJWS>> giveOperations();
	public abstract Collection<GuideFTP> giveGuides();
	public abstract Function<Properties, String> giveOutline(final String name);
	public abstract Collection<Plugin> givePlugins(final String hash);
	public abstract Collection<String> giveTypesHTML();
	public abstract Charset giveCharset();
	public abstract Pattern givePattern();
	public abstract MessageDigest giveMD5();
	public abstract String giveMD5(final String string, final int radix);
	public abstract String giveMD5(final String string);
	public abstract DateFormat giveFormatID();
	public abstract File giveWorkSpace();
	public abstract File giveErrors();
	public abstract File giveDumps();
	public abstract File giveSessions();
	public abstract String giveMessage(final String id, final String detail, final String content);
	public abstract Thread giveThread(final String name, final Runnable runnable);
	public abstract CookieHTTP giveCookie(final String name, final Collection<CookieHTTP> cookies);
	public abstract Map<String, Object> giveServerDetails(final RequestHTTP request);
	public abstract <K> Collection<Map<ParameterJWS, K>> giveOperationsKits(final Class<? extends AbstractKitJWS<K>> type, final IScript jws);
	public abstract JWeb setMediaType(final String extension, final String mime);
	public abstract String getMediaType(final String extension);
	public abstract String addID(final Date date);
	public abstract JWeb delID(final String id);
	public abstract String doExecuteScript(final String lang, final String content);
	public abstract String doExecuteScript(final BasicGroup group, final String lang, final String content);
	public abstract String doExecuteScript(final Collection<Function<String, String>> script, final String content);
	public abstract String onScriptPHP(final String content);
	public abstract String onScriptLUA(final String content);
	public abstract String onScriptJWS(final String content);
	
	public static final void setInstance(final JWeb instance)
	{
		JWeb.instance = instance;
	}
	
	public static final JWeb getInstance()
	{
		return JWeb.instance;
	}
	
	public static final boolean hasInstance()
	{
		return JWeb.getInstance() != null;
	}
	
}
