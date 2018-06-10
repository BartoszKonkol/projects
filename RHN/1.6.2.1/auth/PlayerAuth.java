package net.polishgames.rhenowar.util.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import net.polishgames.rhenowar.util.Language;
import net.polishgames.rhenowar.util.Password;
import net.polishgames.rhenowar.util.PlayerRhenowar;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.auth.event.PlayerAuthEvent;
import net.polishgames.rhenowar.util.auth.task.AsyncTimeoutTask;
import net.polishgames.rhenowar.util.auth.task.SyncKickTask;
import net.polishgames.rhenowar.util.captcha.Captcha;
import net.polishgames.rhenowar.util.mysql.Argument;
import net.polishgames.rhenowar.util.mysql.ClausesData;
import net.polishgames.rhenowar.util.mysql.RhenowarPlayerSQL;
import net.polishgames.rhenowar.util.mysql.Table;
import net.polishgames.rhenowar.util.mysql.TypeData;
import net.polishgames.rhenowar.util.mysql.Value;

public final class PlayerAuth extends RhenowarPlayerSQL
{
	
	public static final Table TABLE = new Table("auth");
	public static final Map<String, Argument> COLUMNS = new HashMap<String, Argument>();
	
	static
	{
		if(!Util.hasUtil())
			Util.doThrowNPE();
		final Util util = Util.giveUtil();
		final List<Argument> list = new ArrayList<Argument>();
		list.add(util.giveArgumentName());
		list.add(util.giveArgumentVer());
		list.add(util.giveArgumentAgain());
		list.add(new Argument(		"pass"	,	TypeData.STRING		));
		list.add(new Argument(		"hint"	,	TypeData.STRING		));
		for(final Argument argument : new Argument[]
				{
					new Argument(	"ip"	,	TypeData.STRING		),
					new Argument(	"date"	,	TypeData.TIMESTAMP	),
				})
		{
			list.add(new Argument(argument.giveName() +	"_reg"			, argument.giveType()));
			list.add(new Argument(argument.giveName() +	"_last"			, argument.giveType()));
			list.add(new Argument(argument.giveName() +	"_last_well"	, argument.giveType()));
			list.add(new Argument(argument.giveName() +	"_last_fail"	, argument.giveType()));
		}
		for(final Argument column : list)
			PlayerAuth.COLUMNS.put(column.giveName(), column);
	}
	
	private final Configuration config;
	
	private PlayerAuth(final OfflinePlayer player) throws SQLException
	{
		super(player);
		this.doModifyStage(StageAuthorization.UNDEFINED);
		this.config = this.giveUtil().giveUtilConfig();
	}

	private volatile StageAuthorization stage;
	protected volatile boolean logged, change;
	protected volatile transient Password password, passtemp;
	protected volatile transient Captcha captcha;
	
	public final StageAuthorization giveStage()
	{
		return this.stage;
	}
	
	public final Configuration giveConfig()
	{
		return this.config;
	}
	
	public final Random giveRandom()
	{
		return this.giveUtil().giveRandom(this.giveUtil().giveSeed(this.givePlayer()) * this.hashCode());
	}
	
	@Override
	public final List<List<Value<?>>> giveContentTable(final ClausesData clauses, final Argument... columns) throws SQLException
	{
		return this.giveUtil().getSQL(PlayerAuth.TABLE, clauses, columns);
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("logged", this.logged);
		map.put("change", this.change);
		map.put("stage", this.giveStage());
		return super.giveProperties(map);
	}
	
	public final boolean isLogged()
	{
		return this.isOnline() && this.isRegistered() && this.logged;
	}
	
	public final boolean isChange()
	{
		return this.isLogged() && this.change;
	}
	
	public final synchronized boolean onJoin()
	{
		return this.doCheck();
	}
	
	public final synchronized boolean onLeave()
	{
		this.doModifyStage(StageAuthorization.UNDEFINED);
		this.doPasswordClose(this.passtemp);
		this.doPasswordClose(this.password);
		if(this.isRegistered() && this.logged)
		{
			this.logged = false;
			return true;
		}
		else
			return false;
	}
	
	public final synchronized boolean onChat(final Password password)
	{
		this.doPasswordClose(this.password);
		this.password = password;
		return this.doCheck();
	}
	
	public final void onErrorSQL(final Exception e)
	{
		if(e instanceof SQLException || e instanceof NullPointerException)
			this.onError(this.giveConfigString("message.ErrorSQL"));
		if(e instanceof SQLException)
			this.giveUtil().doReportErrorSQL((SQLException) e);
		else
			e.printStackTrace();
	}
	
	public final void onError(final String message)
	{
		this.doSend(message);
		this.onLeave();
		new SyncKickTask(this, message).runTask();
	}
	
	protected final PlayerAuth doModifyStage(final StageAuthorization stage)
	{
		this.stage = stage;
		this.giveUtil().givePluginManager().callEvent(new PlayerAuthEvent(this.givePlayer(), stage));
		return this;
	}
	
	public final synchronized boolean doCheck()
	{
		if(this.isChange())
			return this.doChange();
		else if(this.isLogged())
			return true;
		else
		{
			if(this.giveStage() == StageAuthorization.UNDEFINED)
				new AsyncTimeoutTask(this).runTaskLaterAsynchronously(this.giveConfigLong("auth.timeout"));
			if(this.isRegistered())
				return this.doLogin();
			else
				return this.doRegister();
		}
	}
	
	protected synchronized boolean doRegister()
	{
		if(!this.giveStage().isRegister())
			this.doModifyStage(StageAuthorization.REGISTER);
		if(this.isOnline() && !this.isRegistered() && !this.isLogged())
		{
			final String prefix = "register.", prefixCaptcha = prefix + "captcha.", prefixPassword = prefix + "password.";
			switch(this.giveStage())
			{
				case REGISTER:
				{
					final Random random = this.giveRandom();
					final Password password = new Password("");
					for(int i = 0; i < 4; i++)
						if(random.nextInt(random.nextBoolean() ? 2 : 3) == 0)
							password.append((char) (random.nextInt(26) + 65));
						else
							password.append(random.nextInt(10));
					this.captcha = new Captcha(random.nextInt(2) + 1, password.toString());
					this.doPasswordClose(password);
					this.doSend(prefixCaptcha + "welcome", new Object[0]);
					this.doSendClean(null);
					for(final String line : this.giveUtil().giveCaptcha(this.captcha))
						this.doSendClean(line);
					this.doModifyStage(StageAuthorization.REGISTER_CAPTCHA);
				} break;
				case REGISTER_CAPTCHA:
				{
					this.doSendClean(null);
					if(this.captcha.giveText().equalsIgnoreCase(this.password.toString()))
					{
						this.doSend(this.giveMessage(prefixPassword + "enter") + ":");
						this.doModifyStage(StageAuthorization.REGISTER_PASSWORD);
					}
					else
					{
						this.doSend(this.giveMessage(prefixCaptcha + "incorrect") + " " + this.giveConfigString("message.TryAgain") + ".");
						this.doModifyStage(StageAuthorization.REGISTER);
						this.doRegister();
					}
				} break;
				case REGISTER_PASSWORD:
				{
					this.doPasswordCheckCorrectness(prefixPassword, StageAuthorization.REGISTER_PASSWORD_RETYPE);
				} break;
				case REGISTER_PASSWORD_RETYPE:
				{
					if(this.password.toString().equals(this.passtemp.toString()))
					{
						final String postfix = "reg";
						final Crypt crypt = this.giveCrypt();
						try
						{
							this.giveUtil().addSQL(PlayerAuth.TABLE,
									this.giveValueName(),
									this.giveUtil().giveValueVer(),
									this.giveUtil().giveValueAgain(),
									this.giveValueIP(postfix),
									this.giveValueDate(postfix),
									new Value<String>(this.giveArgumentPass(), crypt.toString())
								);
						}
						catch(final SQLException | NullPointerException e)
						{
							this.onErrorSQL(e);
						}
						finally
						{
							crypt.close();
							this.doPasswordClose(this.passtemp);
							this.doPasswordClose(this.password);
						}
						this.doSend(prefixPassword + "correct", new Object[0]);
						this.doSend("welcome", new Object[0]);
						this.doModifyStage(StageAuthorization.REGISTER_COMPLETED);
						this.registered = true;
						this.logged = true;
						this.doSendConsole("register", this.givePlayer().getName());
					}
					else
					{
						this.doSend(this.giveMessage(prefixPassword + "incorrect") + " " + this.giveConfigString("message.TryAgain") + ".");
						this.doSend(this.giveMessage(prefixPassword + "enter") + ":");
						this.doModifyStage(StageAuthorization.REGISTER_PASSWORD);
					}
				} break;
				case REGISTER_COMPLETED:
					break;
				default:
					return false;
			}
			return true;
		}
		else
			return false;
	}
	
	protected synchronized boolean doLogin()
	{
		if(!this.giveStage().isLogin())
			this.doModifyStage(StageAuthorization.LOGIN);
		if(this.isOnline() && this.isRegistered() && !this.isLogged())
		{
			switch(this.giveStage())
			{
				case LOGIN:
					try
					{
						this.doLoginSQL(null, this.giveUtil().giveContentValueAgain(this));
					}
					catch(final SQLException | NullPointerException e)
					{
						this.onErrorSQL(e);
					}
				case LOGIN_PASSWORD:
				case LOGIN_COMPLETED:
					break;
				default:
					return false;
			}
			final String prefix = "login.";
			try
			{
				this.giveUtil().hasPremium(this.giveOfflinePlayer(), (final Object result) ->
				{
					if(result instanceof Boolean)
					{
						if((boolean) result)
						{
							this.doSend(prefix + "premium", new Object[0]);
							this.doModifyStage(StageAuthorization.LOGIN_COMPLETED);
							this.logged = true;
							this.doSendConsole("login.premium", this.givePlayer().getName());
						}
						else
							switch(this.giveStage())
							{
								case LOGIN:
								{
									this.doSend(prefix + "enter", new Object[0]);
									this.doModifyStage(StageAuthorization.LOGIN_PASSWORD);
								} break;
								case LOGIN_PASSWORD:
								{
									int again = 0;
									Password pass = null;
									try
									{
										again = this.giveUtil().giveContentValueAgain(this);
										pass = new Password(String.valueOf(this.giveUtil().giveContentValue(this, this.giveArgumentPass())));
									}
									catch(final SQLException | NullPointerException e)
									{
										this.doPasswordClose(this.password);
										this.onErrorSQL(e);
									}
									final Crypt crypt = this.giveCrypt();
									if(pass.toString().equalsIgnoreCase(crypt.toString()))
									{
										if(this.doLoginSQL("well", again))
										{
											this.doSend(prefix + "correct", new Object[0]);
											this.doModifyStage(StageAuthorization.LOGIN_COMPLETED);
											this.logged = true;
											this.doSendConsole("login", this.givePlayer().getName(), crypt.toString());
											
										}
									}
									else if(this.doLoginSQL("fail", again))
									{
										this.doSend(this.giveMessage("incorrect", new Object[0]) + " " + this.giveConfigString("message.TryAgain") + ":");
										this.doSendConsole("login.incorrect", this.givePlayer().getName(), crypt.toString());
									}
									crypt.close();
									this.doPasswordClose(pass);
									this.doPasswordClose(this.password);
								} break;
								default:
									break;
							}
						return true;
					}
					else
						return false;
				});
			}
			catch(final IOException e)
			{
				this.onError(String.format(this.giveConfigString("message.channel.error"), this.giveUtil().getUtilChannel().giveName(), e.getMessage()));
			}
			return true;
		}
		else
			return false;
	}
	
	protected synchronized boolean doChange()
	{
		if(!this.giveStage().isChange())
			this.doModifyStage(StageAuthorization.CHANGE);
		if(this.isChange())
		{
			final String prefix = "change.";
			switch(this.giveStage())
			{
				case CHANGE:
				{
					this.doSend(this.giveMessage(prefix + "enter") + ":");
					this.doModifyStage(StageAuthorization.CHANGE_PASSWORD);
				} break;
				case CHANGE_PASSWORD:
				{
					this.doPasswordCheckCorrectness(prefix, StageAuthorization.CHANGE_PASSWORD_RETYPE);
				} break;
				case CHANGE_PASSWORD_RETYPE:
				{
					if(this.password.toString().equals(this.passtemp.toString()))
					{
						this.doSendConsole("minigame.change", this.givePlayer().getName(), this.givePlayer().getUniqueId().toString(), this.giveUtil().givePlayerIP(this.givePlayer()), this.giveUtil().toString(new Rhenowar()
						{
							@Override
							public Map<String, Object> giveProperties(final Map<String, Object> map)
							{
								List<List<Value<?>>> content = null;
								try
								{
									content = PlayerAuth.this.giveContentTable(PlayerAuth.COLUMNS.values().toArray(new Argument[PlayerAuth.COLUMNS.size()]));
								}
								catch(final SQLException e)
								{
									PlayerAuth.this.giveUtil().doReportErrorSQL(e);
								}
								if(content != null && content.size() > 0)
									for(final Value<?> value : content.get(0))
										map.put(value.giveName(), value.giveValue());
								return map;
							}
						}, ""));
						final String postfix = "reg";
						final Crypt crypt = this.giveCrypt();
						try
						{
							this.giveUtil().setSQL(PlayerAuth.TABLE, this.giveClausesData(),
									this.giveUtil().giveValueVer(),
									this.giveUtil().giveValueAgain(this.giveUtil().giveContentValueAgain(this) + 1),
									this.giveValueIP(postfix),
									this.giveValueDate(postfix),
									new Value<String>(this.giveArgumentPass(), crypt.toString())
								);
						}
						catch(final SQLException | NullPointerException e)
						{
							this.onErrorSQL(e);
						}
						finally
						{
							crypt.close();
							this.doPasswordClose(this.passtemp);
							this.doPasswordClose(this.password);
						}
						this.doSend("change.correct", new Object[0]);
						this.doModifyStage(StageAuthorization.CHANGE_COMPLETED);
						this.change = false;
						this.doSendConsole("change", this.givePlayer().getName());
					}
					else
					{
						this.doSend(this.giveMessage("register.password.incorrect") + " " + this.giveConfigString("message.TryAgain") + ".");
						this.doSend(this.giveMessage(prefix + "enter") + ":");
						this.doModifyStage(StageAuthorization.CHANGE_PASSWORD);
					}
				} break;
				case CHANGE_COMPLETED:
					break;
				default:
					return false;
			}
			return true;
		}
		else
			return false;
	}
	
	public final synchronized boolean doChangeInduce()
	{
		if(this.isLogged() && !this.isChange())
		{
			this.change = true;
			return this.doCheck();
		}
		else
			return false;
	}
	
	public final void doPasswordClose(final Password password)
	{
		if(password != null && !password.isEmpty())
			password.close();
	}
	
	protected final void doPasswordCheckCorrectness(final String prefix, final StageAuthorization stage)
	{
		final String prefixPassword = "register.password.", prefixPass = "auth.password.", prefixPassLength = prefixPass + "length.", prefixPassRange = this.givePrefix(prefixPass + "range.");
		final int[] length = new int[]{this.giveConfigInteger(prefixPassLength + "min"), this.giveConfigInteger(prefixPassLength + "max")};
		final int lengthMin = Math.min(length[0], length[1]), lengthMax = Math.max(length[0], length[1]), lengthPass = this.password.length();
		if(Pattern.matches(this.giveConfig().getString(prefixPassRange + "regex"), this.password.toString()) && lengthPass >= lengthMin && lengthPass <= lengthMax)
		{
			this.doPasswordClose(this.passtemp);
			this.passtemp = this.password.clone();
			this.doSend(this.giveMessage(prefix + "enter") + " " + this.giveMessage(prefixPassword + "again") + ":");
			this.doModifyStage(stage);
		}
		else
		{
			final String prefixRequisite = prefixPassword + "requisite.";
			this.doSend("incorrect", new Object[0]);
			this.doSend(prefixPassword + "recovery", new Object[0]);
			this.doSend(prefixRequisite + "length", lengthMin == lengthMax ? String.valueOf(length[0]) : this.giveConfigString("message.from") + " " + lengthMin + " " + this.giveConfigString("message.to") + " " + lengthMax);
			this.doSend(prefixRequisite + "range", this.giveConfig().getString(prefixPassRange + "description"));
		}
	}
	
	public final boolean doLoginSQL(final String postfix, final int again)
	{
		try
		{
			final String text = "last" + (postfix == null ? "" : "_" + postfix);
			this.giveUtil().setSQL(PlayerAuth.TABLE, this.giveClausesData(),
					this.giveUtil().giveValueVer(),
					this.giveUtil().giveValueAgain(again + 1),
					this.giveValueIP(text),
					this.giveValueDate(text)
				);
			return true;
		}
		catch(final SQLException | NullPointerException e)
		{
			this.onErrorSQL(e);
			return false;
		}
	}
	
	public final boolean doCancelEvent(final Cancellable cancellable)
	{
		if(cancellable instanceof Event && (!this.isLogged() || this.isChange()))
		{
			cancellable.setCancelled(true);
			return true;
		}
		else
			return false;
	}
	
	public final void doSendConsole(final String prefix, final Object... args)
	{
		this.giveUtil().doSendColouredConsole(this.giveUtil().giveUtilPlugin(), ChatColor.YELLOW + String.format(this.giveUtil().giveMessage(this.givePrefix("message.auth." + Util.nonEmpty(prefix) + ".log"), Language.DEFAULT), Util.nonNull(args)));
	}
	
	public final void doSendClean(final String message)
	{
		this.giveUtil().doSend(this.givePlayer(), this.giveSendMessage(message));
	}
	
	public final void doSend(final String message)
	{
		this.giveUtil().doUtilSendMessage(this.givePlayer(), this.giveSendMessage(message));
	}
	
	public final void doSend(final String prefix, final Object... args)
	{
		this.doSend(this.giveMessage(prefix, args));
	}
	
	public final String giveSendMessage(final String message)
	{
		return message == null ? "" : message;
	}
	
	public final String giveMessage(final String prefix, final Object... args)
	{
		return String.format(this.giveConfigString("message.auth." + Util.nonEmpty(prefix)), Util.nonNull(args));
	}
	
	public final String givePrefix(final String prefix)
	{
		return this.giveUtil().giveUtilConfigPrefix() + Util.nonEmpty(prefix);
	}
	
	public final String giveConfigString(final String prefix)
	{
		return this.giveUtil().giveMessage(this.givePrefix(prefix), this.givePlayer());
	}
	
	public final int giveConfigInteger(final String prefix)
	{
		return this.giveConfig().getInt(this.givePrefix(prefix));
	}
	
	public final long giveConfigLong(final String prefix)
	{
		return this.giveConfig().getLong(this.givePrefix(prefix));
	}
	
	public final Crypt giveCrypt()
	{
		return this.giveUtil().giveCrypt(this.password);
	}
	
	public final Value<String> giveValueIP(final String postfix)
	{
		return new Value<String>(PlayerAuth.COLUMNS.get("ip_" + Util.nonEmpty(postfix)) , this.giveUtil().givePlayerIP(this.givePlayer()));
	}
	
	public final Value<Timestamp> giveValueDate(final String postfix)
	{
		return new Value<Timestamp>(PlayerAuth.COLUMNS.get("date_" + Util.nonEmpty(postfix)) , this.giveUtil().giveTimestamp());
	}
	
	public final Argument giveArgumentPass()
	{
		return PlayerAuth.COLUMNS.get("pass");
	}
	
	public static final PlayerAuth givePlayerAuth(final OfflinePlayer player) throws SQLException
	{
		final PlayerRhenowar playerRhenowar = PlayerRhenowar.givePlayerRhenowar(player);
		final List<PlayerAuth> list = playerRhenowar.giveRhenowarPlayers(PlayerAuth.class);
		if(list.size() > 0)
			return list.get(0);
		else
		{
			playerRhenowar.addRhenowarPlayer(new PlayerAuth(player));
			return PlayerAuth.givePlayerAuth(player);
		}
				
	}
	
	public static enum StageAuthorization implements Rhenowar
	{
		
		UNDEFINED,
		LOGIN,
		LOGIN_PASSWORD,
		LOGIN_COMPLETED,
		REGISTER,
		REGISTER_CAPTCHA,
		REGISTER_PASSWORD,
		REGISTER_PASSWORD_RETYPE,
		REGISTER_COMPLETED,
		CHANGE,
		CHANGE_PASSWORD,
		CHANGE_PASSWORD_RETYPE,
		CHANGE_COMPLETED,
		;
		
		public final String giveStage()
		{
			return this.name().toLowerCase().replace('_', '.');
		}
		
		public final String[] giveType()
		{
			return this.giveStage().split("\\.");
		}
		
		public final boolean isLogin()
		{
			return this.giveType()[0].equals("login");
		}
		
		public final boolean isRegister()
		{
			return this.giveType()[0].equals("register");
		}
		
		public final boolean isChange()
		{
			return this.giveType()[0].equals("change");
		}
		
		public final boolean isUndefined()
		{
			return !this.isLogin() && !this.isRegister() && !this.isChange();
		}
		
		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("name", this.name());
			map.put("stage", this.giveStage());
			map.put("type", this.giveType());
			map.put("login", this.isLogin());
			map.put("register", this.isRegister());
			map.put("undefined", this.isUndefined());
			return map;
		}
		
		@Override
		public String toString()
		{
			if(Util.hasUtil())
				return Util.giveUtil().toString(this);
			else
				return this.giveStage();
		}
		
	}
	
}
