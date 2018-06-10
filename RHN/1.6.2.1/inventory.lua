
-- RhenowarUtil - MinigameInventory

hand[3] =
{
	action	=	"AccountSettings"		,
	item	=	"nether_star"			,
	name	=	"*.minigame.account"	,
	glow	=	true					,
}

hand[5] =
{
	action	=	"!SelectionGames"		,
	item	=	"compass"				,
	name	=	"*.minigame.games"		,
	glow	=	true					,
}

hand[7] =
{
	action	=	"ShopSMS"				,
	item	=	"emerald"				,
	name	=	"*.minigame.shop"		,
	glow	=	true					,
}

function AccountSettings()
local prefix = hand[3]["name"];
return
{
	type = "inv";
	inv =
	{
		title = prefix,
		size = 4,
		[2] =
		{
			[5] =
			{
				action		=	"broken"				,
				item		=	"skull_item"			,
				name		=	'@'..player				,
				glow		=	false					,
				item_meta	=
				{
					variant	=	3						,
					owner	=	player					,
				},
			},
		},
		[3] =
		{
			[4] =
			{
				action	=	"ChangePassword"			,
				item	=	"redstone"					,
				name	=	prefix..".change.password"	,
				glow	=	false						,
			},
			[6] =
			{
				action	=	"!ChangeLanguage"			,
				item	=	"clay_ball"					,
				name	=	prefix..".change.language"	,
				glow	=	false						,
			},
		},
	},
}
end

function ChangePassword() return
{
	type = "inv",
	inv =
	{
		title = hand[3]["name"]..".change.password.confirm",
		size = 1,
		[1] =
		{
			[4] =
			{
				action		=	"!ChangePassword"	,
				item		=	"wool"				,
				name		=	"*.yes"				,
				glow		=	false				,
				item_meta	=
				{
					color	=	5					,
				},
			},
			[6] =
			{
				action		=	"AccountSettings"	,
				item		=	"wool"				,
				name		=	"*.no"				,
				glow		=	false				,
				item_meta	=
				{
					color	=	14					,
				},
			},
		},
	},
}
end

function ShopSMS() return
{
	type = "com",
	com = hand[7]["name"]..".broken",
}
end

function glass() return
{
	action		=	"broken"				,
	item		=	"stained_glass_pane"	,
	name		=	'@'						,
	glow		=	false					,
	item_meta	=
	{
		color	=	7						,
	},
}
end

function clayRed() return
{
	action		=	"broken"		,
	item		=	"stained_clay"	,
	name		=	'@'				,
	glow		=	false			,
	item_meta	=
	{
		color	=	14				,
	},
}
end

function clayYellow() return
{
	action		=	"broken"		,
	item		=	"stained_clay"	,
	name		=	'@'				,
	glow		=	false			,
	item_meta	=
	{
		color	=	4				,
	},
}
end

function clayGreen() return
{
	action		=	"broken"		,
	item		=	"stained_clay"	,
	name		=	'@'				,
	glow		=	false			,
	item_meta	=
	{
		color	=	5				,
	},
}
end

function back() return
{
	action		=	"!back"				,
	item		=	"skull_item"		,
	name		=	"*.back"			,
	glow		=	false				,
	item_meta	=
	{
		variant	=	3					,
		owner	=	"MHF_ArrowDown"		,
	},
}
end

function broken()
	return nil;
end
