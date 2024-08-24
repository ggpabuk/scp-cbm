Global CurrentParam, Flipped
Global PublicReturnedValue$
Global SelectedFont_

Function SE_GetParams()
	CurrentParam = SE_ARGUMENTS_NUMBER-1
End Function
Function SE_SetParams(count)
	CurrentParam = count
End Function

Function SE_FlipParams(args)
	SE_GetParams()
End Function

Function SE_ResetParam()
	SE_SetParams(-1)
End Function

Function SE_GetParamInt%()
	If Not Flipped Then CurrentParam = 0 : Flipped = True
	CurrentParam = CurrentParam+1
	Return SE_ToStringArg(CurrentParam-1)
End Function

Function SE_GetParamFloat#()
	If Not Flipped Then CurrentParam = 0 : Flipped = True
	CurrentParam = CurrentParam+1
	Return SE_ToStringArg(CurrentParam-1)
End Function

Function SE_GetParamString$()
	If Not Flipped Then CurrentParam = 0 : Flipped = True
	CurrentParam = CurrentParam+1
	Return SE_ToStringArg(CurrentParam-1)
End Function


Function SE_GetFlippedParamInt%()
	CurrentParam = CurrentParam-1
	If CurrentParam = -2 Then Return
	Return SE_ToStringArg(CurrentParam+1)
End Function

Function SE_GetFlippedParamFloat#()
	CurrentParam = CurrentParam-1
	If CurrentParam = -2 Then Return
	Return SE_ToStringArg(CurrentParam+1)
End Function

Function SE_GetFlippedParamString$()
	CurrentParam = CurrentParam-1
	If CurrentParam = -2 Then Return
	Return SE_ToStringArg(CurrentParam+1)
End Function

Const func_debuglog = 1
Const func_initloadingscreens = 2
Const func_initinfoclues = 3
Const func_stopallredirecttext = 4
Const func_setscriptlanguage = 5
Const func_getscriptpath = 6
Const func_freefonts = 7
Const func_loadfonts = 8
Const func_redirectfont = 9
Const func_redirectfile = 10
Const func_redirecttext = 11
Const func_stopredirecttext = 12
Const func_disableallworkshopscripts = 13
Const func_setintercomtimeout = 14
Const func_getintercomtimeout = 15
Const func_getinventoryslotitemname = 16
Const func_setselecteditemfrominventory = 17
Const func_isvaliditem = 18
Const func_getselecteditem = 19
Const func_getitemname = 20
Const func_getitemtempname = 21
Const func_getitemstate = 22
Const func_get427timer = 23
Const func_getblurtimer = 24
Const func_getstamina = 25
Const func_getblinktimer = 26
Const func_getcurrentweapon = 27
Const func_getcurrentweaponmagazines = 28
Const func_getcurrentweaponammo = 29
Const func_keyhit = 30
Const func_keydown = 31
Const func_createsound3d = 32
Const func_createlocalsound = 33
Const func_getvolume = 34
Const func_stopchannel = 35
Const func_resumechannel = 36
Const func_pausechannel = 37
Const func_channelpitch = 38
Const func_channelvolume = 39
Const func_channelpan = 40
Const func_channelplaying = 41
Const func_stop3dsounds = 42
Const func_setcamerashake = 43
Const func_freefont = 44
Const func_freeimage = 45
Const func_setfont = 46
Const func_color = 47
Const func_loadimage = 48
Const func_loadanimimage = 49
Const func_loadfont = 50
Const func_copyimage = 51
Const func_drawimage = 52
Const func_resizeimage = 53
Const func_rotateimage = 54
Const func_text = 55
Const func_oval = 56
Const func_rect = 57
Const func_mousex = 58
Const func_mousey = 59
Const func_mousehit1 = 60
Const func_mousehit2 = 61
Const func_mousedown1 = 62
Const func_mousedown2 = 63
Const func_connect = 64
Const func_disconnect = 65
Const func_setplayerroom = 66
Const func_createpivot = 67
Const func_loadmesh = 68
Const func_loadanimmesh = 69
Const func_getplayercollider = 70
Const func_getplayercamera = 71
Const func_cameraclscolor = 72
Const func_camerafogcolor = 73
Const func_setambientlight = 74
Const func_hidescreenocclusion = 75
Const func_showscreenocclusion = 76
Const func_getselectedscreen = 77
Const func_resetselectedscreen = 78
Const func_showsky = 79
Const func_hidesky = 80
Const func_getroomname = 81
Const func_entityinview = 82
Const func_entityvisible = 83
Const func_entityx = 84
Const func_entityy = 85
Const func_entityz = 86
Const func_entitypitch = 87
Const func_entityyaw = 88
Const func_entityroll = 89
Const func_entitypick = 90
Const func_positionentity = 91
Const func_rotateentity = 92
Const func_moveentity = 93
Const func_scaleentity = 94
Const func_setentitycollision = 95
Const func_removeentity = 96
Const func_setanimtime = 97
Const func_getdelta = 98
Const func_getmonitorwidth = 99
Const func_getmonitorheight = 100
Const func_sendrawpacket = 101
Const func_hexstring = 102
Const func_eof = 103
Const func_filetype = 104
Const func_filesize = 105
Const func_workshopfiletype = 106
Const func_workshopfilesize = 107
Const func_seekfile = 108
Const func_filepos = 109
Const func_catcherror = 110
Const func_writebytes = 111
Const func_readbytes = 112
Const func_createbank = 113
Const func_freebank = 114
Const func_banksize = 115
Const func_resizebank = 116
Const func_copybank = 117
Const func_peekbyte = 118
Const func_peekshort = 119
Const func_peekint = 120
Const func_peekfloat = 121
Const func_pokebyte = 122
Const func_pokeshort = 123
Const func_pokeint = 124
Const func_pokefloat = 125
Const func_movex = 126
Const func_movey = 127
Const func_movez = 128
Const func_move = 129
Const func_pointangle = 130
Const func_pointpitch = 131
Const func_pointat = 132
Const func_closefile = 133
Const func_readline = 134
Const func_readint = 135
Const func_readfloat = 136
Const func_readshort = 137
Const func_readbyte = 138
Const func_writeline = 139
Const func_writeint = 140
Const func_writefloat = 141
Const func_writeshort = 142
Const func_writebyte = 143
Const func_openfile = 144
Const func_readfile = 145
Const func_writefile = 146
Const func_openworkshopfile = 147
Const func_readworkshopfile = 148
Const func_writeworkshopfile = 149
Const func_getversion = 150
Const func_millisecs = 151
Const func_getping = 152
Const func_getname = 153
Const func_setgamemessage = 154
Const func_sendconsolecommand = 155
Const func_sendmessage = 156
Const func_plugin_remove = 157
Const func_plugin_call = 158
Const func_plugin_poke = 159
Const func_plugin_load = 160
Const func_int = 161
Const func_float = 162
Const func_str = 163
Const func_floor = 164
Const func_ceil = 165
Const func_sgn = 166
Const func_abs = 167
Const func_sqr = 168
Const func_sin = 169
Const func_cos = 170
Const func_tan = 171
Const func_asin = 172
Const func_acos = 173
Const func_atan = 174
Const func_atan2 = 175
Const func_exp = 176
Const func_log = 177
Const func_log10 = 178
Const func_rnd = 179
Const func_rand = 180
Const func_num = 181
Const func_left = 182
Const func_right = 183
Const func_mid = 184
Const func_replace = 185
Const func_instr = 186
Const func_lower = 187
Const func_upper = 188
Const func_trim = 189
Const func_chr = 190
Const func_asc = 191
Const func_hex = 192
Const func_bin = 193
Const func_repeat = 194
Const func_create = 195
Const func_push = 196
Const func_pop = 197
Const func_delete = 198
Const func_sort = 199
Const func_from_string = 200
Const func_getfov = 201
Const func_handleimage = 202
Const func_caninteract = 203
Const func_callglobalfunction = 204
Const func_callfunction = 205
Const func_ismenuopen = 206
Const func_isinventoryopen = 207
Const func_isotherinventoryopen = 208
Const func_lockmouse = 209
Const func_getoptionsmenu = 210
Const func_getachievementsmenu = 211
Const func_showpointer = 212
Const func_hidepointer = 213
Const func_iskeypadopen = 214
Const func_isconsoleopen = 215
Const func_iscoffeemachineopen = 216
Const func_getinivalue = 217
Const func_putinivalue = 218
Const func_updateinifile = 219
Const func_createtimer = 220
Const func_removetimer = 221
Const func_lerp = 222
Const func_lerpangle = 223
Const func_drawuibutton = 224
Const func_ismainmenuopen = 225
Const func_drawuiinputbox = 226
Const func_getkey = 227
Const func_preparemodelidentifier = 228
Const func_resetentity = 229
Const func_entityradius = 230
Const func_entitybox = 231
Const func_entitycollisiontype = 232
Const func_entitypickmode = 233
Const func_entitycollided = 234
Const func_countcollisions = 235
Const func_collisionx = 236
Const func_collisiony = 237
Const func_collisionz = 238
Const func_collisionnx = 239
Const func_collisionny = 240
Const func_collisionnz = 241
Const func_collisionentity = 242
Const func_getentitycollisiontype = 243
Const func_getplayerentity = 244
Const func_getplayerhitboxentity = 245
Const func_getplayermodelentity = 246
Const func_getcurrentspectateplayer = 247
Const func_deltayaw = 248
Const func_deltapitch = 249
Const func_entitydistance = 250
Const func_pointentity = 251
Const func_entityscalex = 252
Const func_entityscaley = 253
Const func_entityscalez = 254
Const func_meshwidth = 255
Const func_meshheight = 256
Const func_meshdepth = 257

Const func_createsprite = 258
Const func_loadsprite = 259
Const func_spriteviewmode = 260
Const func_scalesprite = 261
Const func_entityfx = 262
Const func_entityblend = 263
Const func_showentity = 264
Const func_hideentity = 265
Const func_getparent = 266
Const func_entityautofade = 267
Const func_entitycolor = 268
Const func_entityshininess = 269
Const func_entityalpha = 270
Const func_entitytexture = 271
Const func_createtexture = 272
Const func_loadtexture = 273
Const func_scaletexture = 274
Const func_texturewidth = 275
Const func_textureheight = 276
Const func_entityparent = 277

Const func_TCP_Get = 278
Const func_TCP_Close = 279

Const func_browser_create = 280
Const func_browser_Destroy = 281
Const func_browser_getimage = 282
Const func_browser_gettexture = 283
Const func_browser_mousedown = 284
Const func_browser_setmousepos = 285
Const func_browser_mouseup = 286

Const func_drawblock = 287

Const func_isvalidobject = 288
Const func_getobjectentity = 289
Const func_browser_loadurl = 290
Const func_scaleimage = 291
Const func_imagewidth = 292
Const func_imageheight = 293

Const func_stringwidth = 294
Const func_stringheight = 295

Const func_formattext = 296
Const func_turnentity = 297
Const func_gettabmenu = 298
Const func_setmousehit1 = 299
Const func_setmousedown1 = 300
Const func_camerarange = 301
Const func_camerafogrange = 302

Const func_createcube = 303
Const func_createcone = 304
Const func_createcylinder = 305
Const func_createsphere = 306
Const func_createevent = 307
Const func_setradio = 308
Const func_getservertimeout = 309
Const func_getitemtemplate = 310
Const func_createitemtemplate = 311
Const func_changeroommesh = 312
Const func_breachrole_CreatePlayerRole = 313
Const func_breachrole_GetRoleName = 314
Const func_breachrole_SetRoleSettings = 315
Const func_breachrole_SetRoleEffects = 316
Const func_breachrole_SetRoleAmbientSound = 317
Const func_breachrole_SetRoleInstruction = 318
Const func_breachrole_SetRoleDeadAnimation = 319
Const func_breachrole_SetRoleHitboxScales = 320
Const func_breachrole_SetRoleBone = 321
Const func_breachrole_SetRoleAnimation = 322
Const func_breachrole_SetRoleArmedAnimation = 323
Const func_breachrole_MarkAsFriend = 324
Const func_breachrole_RoleTakeRoleSpawn = 325
Const func_breachrole_SetRolePositionsOffset = 326
Const func_breachrole_SetRoleHoldingGrenade = 327
Const func_breachrole_SetRoleHoldingItem = 328
Const func_breachrole_SetRoleHandcuff = 329
Const func_breachrole_AllowRoleWeaponAttaches = 330
Const func_breachrole_AllowItemsAttaches = 331
Const func_breachrole_MarkRoleAsSCP = 332
Const func_breachrole_MarkAs035 = 333
Const func_breachrole_MarkAs049 = 334
Const func_breachrole_IsA035 = 335
Const func_breachrole_IsA049 = 336
Const func_breachrole_IsAFriend = 337
Const func_breachrole_GetMaxHP = 338
Const func_breachrole_GetRoleScale = 339
Const func_RedirectSound = 340
Const func_breachrole_createviewmodelforrole = 341
Const func_breachrole_setroleviewmodelanimation = 342

Const func_createdecal = 343
Const func_updateplayermodel = 344

Function SE_GetFunctionID(functionname$)
	Select FunctionName$
		Case "breachrole_setroleviewmodelanimation": Return func_breachrole_SetRoleViewmodelAnimation
		Case "breachrole_createviewmodelforrole": Return func_breachrole_createviewmodelforrole
		Case "redirectsound": Return func_RedirectSound
		Case "breachrole_createplayerrole": Return func_breachrole_createplayerrole
		Case "breachrole_getrolename": Return func_breachrole_getrolename
		Case "breachrole_setrolesettings": Return func_breachrole_setrolesettings
		Case "breachrole_setroleeffects": Return func_breachrole_setroleeffects
		Case "breachrole_setroleambientsound": Return func_breachrole_setroleambientsound
		Case "breachrole_setroleinstruction": Return func_breachrole_setroleinstruction
		Case "breachrole_setroledeadanimation" Return func_breachrole_setroledeadanimation
		Case "breachrole_setrolehitboxscales": Return func_breachrole_setrolehitboxscales
        Case "breachrole_setrolebone": Return func_breachrole_setrolebone
		Case "breachrole_setroleanimation": Return func_breachrole_setroleanimation
		Case "breachrole_setrolearmedanimation": Return func_breachrole_setrolearmedanimation
		Case "breachrole_markasfriend": Return func_breachrole_markasfriend
		Case "breachrole_roletakerolespawn": Return func_breachrole_RoleTakeRoleSpawn
		Case "breachrole_setrolepositionsoffset": Return func_breachrole_setrolepositionsoffset
		Case "breachrole_setroleholdinggrenade": Return func_breachrole_setroleholdinggrenade
		Case "breachrole_setroleholdingitem": Return func_breachrole_setroleholdingitem
		Case "breachrole_setrolehandcuff": Return func_breachrole_setrolehandcuff
		Case "breachrole_allowroleweaponattaches": Return func_breachrole_allowroleweaponattaches
		Case "breachrole_allowitemsattaches": Return func_breachrole_allowitemsattaches
		Case "breachrole_markroleasscp": Return func_breachrole_markroleasscp
		Case "breachrole_markas035": Return func_breachrole_markas035
		Case "breachrole_markas049": Return func_breachrole_markas049
		Case "breachrole_isa035": Return func_breachrole_isa035
		Case "breachrole_isa049": Return func_breachrole_isa049
		Case "breachrole_isafriend": Return func_breachrole_isafriend
		Case "breachrole_getmaxhp": Return func_breachrole_getmaxhp
		Case "breachrole_getrolescale": Return func_breachrole_getrolescale
		Case "createdecal": Return func_createdecal
        Case "updateplayermodel": Return func_updateplayermodel

		Case "getitemtemplate": Return func_getitemtemplate
		Case "getservertimeout": Return func_getservertimeout
		Case "camerarange": Return func_camerarange
		Case "camerafogrange": Return func_camerafogrange
		Case "gettabmenu": Return func_gettabmenu
		Case "turnentity": Return func_turnentity
		Case "formattext": Return func_formattext
		Case "stringwidth": Return func_stringwidth
		Case "stringheight": Return func_stringheight
		Case "imagewidth": Return func_imagewidth
		Case "imageheight": Return func_imageheight
		Case "scaleimage": Return func_scaleimage
		Case "isvalidobject": Return func_isvalidobject
		Case "getobjectentity": Return func_getobjectentity
		Case "drawblock": Return func_drawblock
		Case "browser_create": Return func_browser_create
		Case "browser_destroy": Return func_browser_Destroy
		Case "browser_getimage": Return func_browser_getimage
		Case "browser_gettexture": Return func_browser_gettexture
		Case "browser_mousedown": Return func_browser_mousedown
		Case "browser_mouseup": Return func_browser_mouseup
		Case "browser_setmousepos": Return func_browser_setmousepos
		Case "browser_loadurl": Return func_browser_loadurl
		
		Case "tcp_get": Return func_tcp_get
		Case "tcp_close": Return func_tcp_close
		Case "createsprite": Return func_createsprite
		Case "loadsprite": Return func_loadsprite
		Case "spriteviewmode": Return func_spriteviewmode
		Case "scalesprite": Return func_scalesprite
		Case "entityfx": Return func_entityfx
		Case "entityblend": Return func_entityblend
		Case "showentity": Return func_showentity
		Case "hideentity": Return func_hideentity
		Case "getparent": Return func_getparent
		Case "entityautofade": Return func_entityautofade
		Case "entitycolor": Return func_entitycolor
		Case "entityshininess": Return func_entityshininess
		Case "entityalpha": Return func_entityalpha
		Case "entitytexture": Return func_entitytexture
		Case "createtexture": Return func_createtexture
		Case "loadtexture": Return func_loadtexture
		Case "scaletexture": Return func_scaletexture
		Case "texturewidth": Return func_texturewidth
		Case "textureheight": Return func_textureheight
		Case "entityparent": Return func_entityparent
		
		Case "entityscalex": Return func_entityscalex
		Case "entityscaley": Return func_entityscaley
		Case "entityscalez": Return func_entityscalez
		Case "meshwidth": Return func_meshwidth
		Case "meshheight": Return func_meshheight
		Case "meshdepth": Return func_meshdepth
		
		Case "pointentity": Return func_pointentity
		;
		Case "getplayerentity": Return func_getplayerentity
		Case "getplayerhitboxentity": Return func_getplayerhitboxentity
		Case "getplayermodelentity": Return func_getplayermodelentity
		Case "getcurrentspectateplayer": Return func_getcurrentspectateplayer
		;
		Case "resetentity": Return func_resetentity
		Case "entityradius": Return func_entityradius
		Case "entitybox": Return func_entitybox
		Case "entitycollisiontype": Return func_entitycollisiontype
		Case "entitypickmode": Return func_entitypickmode
		Case "entitycollided": Return func_entitycollided
		Case "countcollisions": Return func_countcollisions
		Case "collisionx": Return func_collisionx
		Case "collisiony": Return func_collisionx
		Case "collisionz": Return func_collisionz
		Case "collisionnx": Return func_collisionnx
		Case "collisionny": Return func_collisionny
		Case "collisionnz": Return func_collisionnz
		Case "collisionentity": Return func_collisionentity
		Case "getentitycollisiontype": Return func_getentitycollisiontype
		;
		Case "preparemodelidentifier": Return func_preparemodelidentifier
		Case "getkey"
			Return func_getkey
		Case "drawuiinputbox"
			Return func_drawuiinputbox
		Case "drawuibutton"
			Return func_drawuibutton
		Case "ismainmenuopen"
			Return func_ismainmenuopen
		Case "debuglog"
			Return func_debuglog
		Case "initloadingscreens"
			Return func_initloadingscreens
		Case "initinfoclues"
			Return func_initinfoclues
		Case "stopallredirecttext"
			Return func_stopallredirecttext
		Case "setscriptlanguage"
			Return func_setscriptlanguage
		Case "getscriptpath"
			Return func_getscriptpath
		Case "freefonts"
			Return func_freefonts
		Case "loadfonts"
			Return func_loadfonts
		Case "redirectfont"
			Return func_redirectfont
		Case "redirectfile"
			Return func_redirectfile
		Case "redirecttext"
			Return func_redirecttext
		Case "stopredirecttext"
			Return func_stopredirecttext
		Case "disableallworkshopscripts"
			Return func_disableallworkshopscripts
		Case "setintercomtimeout"
			Return func_setintercomtimeout
		Case "getintercomtimeout"
			Return func_getintercomtimeout
		Case "getinventoryslotitemname"
			Return func_getinventoryslotitemname
		Case "setselecteditemfrominventory"
			Return func_setselecteditemfrominventory
		Case "isvaliditem"
			Return func_isvaliditem
		Case "getselecteditem"
			Return func_getselecteditem
		Case "getitemname"
			Return func_getitemname
		Case "getitemtempname"
			Return func_getitemtempname
		Case "getitemstate"
			Return func_getitemstate
		Case "get427timer"
			Return func_get427timer
		Case "getblurtimer"
			Return func_getblurtimer
		Case "getstamina"
			Return func_getstamina
		Case "getblinktimer"
			Return func_getblinktimer
		Case "getcurrentweapon"
			Return func_getcurrentweapon
		Case "getcurrentweaponmagazines"
			Return func_getcurrentweaponmagazines
		Case "getcurrentweaponammo"
			Return func_getcurrentweaponammo
		Case "keyhit"
			Return func_keyhit
		Case "keydown"
			Return func_keydown
		Case "createsound3d"
			Return func_createsound3d
		Case "createlocalsound"
			Return func_createlocalsound
		Case "getvolume"
			Return func_getvolume
		;Case "stopchannel"
		;	Return func_stopchannel
		;Case "resumechannel"
		;	Return func_resumechannel
		;Case "pausechannel"
		;	Return func_pausechannel
		;Case "channelpitch"
		;	Return func_channelpitch
		;Case "channelvolume"
		;	Return func_channelvolume
		;Case "channelpan"
		;	Return func_channelpan
		;Case "channelplaying"
		;	Return func_channelplaying
		Case "stop3dsounds"
			Return func_stop3dsounds
		Case "setcamerashake"
			Return func_setcamerashake
		Case "freefont"
			Return func_freefont
		Case "freeimage"
			Return func_freeimage
		Case "setfont"
			Return func_setfont
		Case "color"
			Return func_color
		Case "loadimage"
			Return func_loadimage
		Case "loadanimimage"
			Return func_loadanimimage
		Case "loadfont"
			Return func_loadfont
		Case "copyimage"
			Return func_copyimage
		Case "drawimage"
			Return func_drawimage
		Case "resizeimage"
			Return func_resizeimage
		Case "rotateimage"
			Return func_rotateimage
		Case "text"
			Return func_text
		Case "oval"
			Return func_oval
		Case "rect"
			Return func_rect
		Case "mousex"
			Return func_mousex
		Case "mousey"
			Return func_mousey
		Case "mousehit1"
			Return func_mousehit1
		Case "mousehit2"
			Return func_mousehit2
		Case "mousedown1"
			Return func_mousedown1
		Case "mousedown2"
			Return func_mousedown2
		Case "connect"
			Return func_connect
		Case "disconnect"
			Return func_disconnect
		Case "setplayerroom"
			Return func_setplayerroom
		Case "createpivot"
			Return func_createpivot
		Case "loadmesh"
			Return func_loadmesh
		Case "loadanimmesh"
			Return func_loadanimmesh
		Case "getplayercollider"
			Return func_getplayercollider
		Case "getplayercamera"
			Return func_getplayercamera
		Case "cameraclscolor"
			Return func_cameraclscolor
		Case "camerafogcolor"
			Return func_camerafogcolor
		Case "setambientlight"
			Return func_setambientlight
		Case "hidescreenocclusion"
			Return func_hidescreenocclusion
		Case "showscreenocclusion"
			Return func_showscreenocclusion
		Case "getselectedscreen"
			Return func_getselectedscreen
		Case "resetselectedscreen"
			Return func_resetselectedscreen
		Case "showsky"
			Return func_showsky
		Case "hidesky"
			Return func_hidesky
		Case "getroomname"
			Return func_getroomname
		Case "entityinview"
			Return func_entityinview
		Case "entityvisible"
			Return func_entityvisible
		Case "entityx"
			Return func_entityx
		Case "entityy"
			Return func_entityy
		Case "entityz"
			Return func_entityz
		Case "entitypitch"
			Return func_entitypitch
		Case "entityyaw"
			Return func_entityyaw
		Case "entityroll"
			Return func_entityroll
		Case "entitypick"
			Return func_entitypick
		Case "positionentity"
			Return func_positionentity
		Case "rotateentity"
			Return func_rotateentity
		Case "moveentity"
			Return func_moveentity
		Case "scaleentity"
			Return func_scaleentity
		Case "setentitycollision"
			Return func_setentitycollision
		Case "removeentity"
			Return func_removeentity
		Case "setanimtime"
			Return func_setanimtime
		Case "getdelta"
			Return func_getdelta
		Case "getmonitorwidth"
			Return func_getmonitorwidth
		Case "getmonitorheight"
			Return func_getmonitorheight
		Case "sendrawpacket"
			Return func_sendrawpacket
		Case "hexstring"
			Return func_hexstring
		Case "eof"
			Return func_eof
		Case "filetype"
			Return func_filetype
		Case "filesize"
			Return func_filesize
		Case "workshopfiletype"
			Return func_workshopfiletype
		Case "workshopfilesize"
			Return func_workshopfilesize
		Case "seekfile"
			Return func_seekfile
		Case "filepos"
			Return func_filepos
		Case "catcherror"
			Return func_catcherror
		Case "writebytes"
			Return func_writebytes
		Case "readbytes"
			Return func_readbytes
		Case "createbank"
			Return func_createbank
		Case "freebank"
			Return func_freebank
		Case "banksize"
			Return func_banksize
		Case "resizebank"
			Return func_resizebank
		Case "copybank"
			Return func_copybank
		Case "peekbyte"
			Return func_peekbyte
		Case "peekshort"
			Return func_peekshort
		Case "peekint"
			Return func_peekint
		Case "peekfloat"
			Return func_peekfloat
		Case "pokebyte"
			Return func_pokebyte
		Case "pokeshort"
			Return func_pokeshort
		Case "pokeint"
			Return func_pokeint
		Case "pokefloat"
			Return func_pokefloat
		Case "movex"
			Return func_movex
		Case "movey"
			Return func_movey
		Case "movez"
			Return func_movez
		Case "move"
			Return func_move
		Case "pointangle"
			Return func_pointangle
		Case "pointpitch"
			Return func_pointpitch
		Case "pointat"
			Return func_pointat
		Case "closefile"
			Return func_closefile
		Case "readline"
			Return func_readline
		Case "readint"
			Return func_readint
		Case "readfloat"
			Return func_readfloat
		Case "readshort"
			Return func_readshort
		Case "readbyte"
			Return func_readbyte
		Case "writeline"
			Return func_writeline
		Case "writeint"
			Return func_writeint
		Case "writefloat"
			Return func_writefloat
		Case "writeshort"
			Return func_writeshort
		Case "writebyte"
			Return func_writebyte
		Case "openfile"
			Return func_openfile
		Case "readfile"
			Return func_readfile
		Case "writefile"
			Return func_writefile
		Case "openworkshopfile"
			Return func_openworkshopfile
		Case "readworkshopfile"
			Return func_readworkshopfile
		Case "writeworkshopfile"
			Return func_writeworkshopfile
		Case "getversion"
			Return func_getversion
		Case "millisecs"
			Return func_millisecs
		Case "getping"
			Return func_getping
		Case "getname"
			Return func_getname
		Case "setgamemessage"
			Return func_setgamemessage
		Case "sendconsolecommand"
			Return func_sendconsolecommand
		Case "sendmessage"
			Return func_sendmessage
		Case "plugin_remove"
			Return func_plugin_remove
		Case "plugin_call"
			Return func_plugin_call
		Case "plugin_poke"
			Return func_plugin_poke
		Case "plugin_load"
			Return func_plugin_load
		Case "int"
			Return func_int
		Case "float"
			Return func_float
		Case "str"
			Return func_str
		Case "floor"
			Return func_floor
		Case "ceil"
			Return func_ceil
		Case "sgn"
			Return func_sgn
		Case "abs"
			Return func_abs
		Case "sqr"
			Return func_sqr
		Case "sin"
			Return func_sin
		Case "cos"
			Return func_cos
		Case "tan"
			Return func_tan
		Case "asin"
			Return func_asin
		Case "acos"
			Return func_acos
		Case "atan"
			Return func_atan
		Case "atan2"
			Return func_atan2
		Case "exp"
			Return func_exp
		Case "log"
			Return func_log
		Case "log10"
			Return func_log10
		Case "rnd"
			Return func_rnd
		Case "rand"
			Return func_rand
		Case "num"
			Return func_num
		Case "left"
			Return func_left
		Case "right"
			Return func_right
		Case "mid"
			Return func_mid
		Case "replace"
			Return func_replace
		Case "instr"
			Return func_instr
		Case "lower"
			Return func_lower
		Case "upper"
			Return func_upper
		Case "trim"
			Return func_trim
		Case "chr"
			Return func_chr
		Case "asc"
			Return func_asc
		Case "hex"
			Return func_hex
		Case "bin"
			Return func_bin
		Case "repeat"
			Return func_repeat
		Case "create"
			Return func_create
		Case "push"
			Return func_push
		Case "pop"
			Return func_pop
		Case "delete"
			Return func_delete
		Case "sort"
			Return func_sort
		Case "from_string"
			Return func_from_string
		Case "getfov"
			Return func_getfov
		Case "handleimage"
			Return func_handleimage
		Case "caninteract"
			Return func_caninteract
		Case "callglobalfunction"
			Return func_callglobalfunction
		Case "callfunction"
			Return func_callfunction
		Case "ismenuopen"
			Return func_ismenuopen
		Case "isinventoryopen"
			Return func_isinventoryopen
		Case "isotherinventoryopen"
			Return func_isotherinventoryopen
		Case "lockmouse"
			Return func_lockmouse
		Case "getoptionsmenu"
			Return func_getoptionsmenu
		Case "getachievementsmenu"
			Return func_getachievementsmenu
		Case "showpointer"
			Return func_showpointer
		Case "hidepointer"
			Return func_hidepointer
		Case "iskeypadopen"
			Return func_iskeypadopen
		Case "isconsoleopen"
			Return func_isconsoleopen
		Case "iscoffeemachineopen"
			Return func_iscoffeemachineopen
		Case "getinivalue"
			Return func_getinivalue
		Case "putinivalue"
			Return func_putinivalue
		Case "updateinifile"
			Return func_updateinifile
			
		Case "createtimer"
			Return func_createtimer
		Case "removetimer"
			Return func_removetimer
			
		Case "lerp"
			Return func_lerp
		Case "lerpangle"
			Return func_lerpangle
		Case "deltayaw": Return func_deltayaw
		Case "deltapitch": Return func_deltapitch
		Case "entitydistance": Return func_entitydistance
		Case "setmousehit1": Return func_setmousehit1
		Case "setmousedown1": Return func_setmousedown1
		Case "createcube": Return func_createcube
		Case "createsphere": Return func_createsphere
		Case "createcylinder": Return func_createcylinder
		Case "createcone": Return func_createcone
		Case "createevent": Return func_createevent
		Case "setradio": Return func_setradio
		Case "createitemtemplate": Return func_createitemtemplate
		Case "changeroommesh": Return func_changeroommesh
	End Select
End Function

; ******* my
	Global pointyaw#, pointpitch#, movex#, movey#, movez#
; *******
Function SE_ExecuteFunction(FunctionID%, CurrentFuncPtr.SE_FuncPtr)
	Local G_SE_ReturnVal$
	Local G_SE_ReturnType% = SE_NULL
	SE_ResetParam()
	SE_GetParams()
	Flipped = False
	
	Select FunctionID
		Case func_breachrole_SetRoleViewmodelAnimation
			multiplayer_breach_SetRoleViewmodelAnimation(se_tointarg(0), se_tointarg(1), se_tointarg(2), se_tointarg(3), se_tofloatarg(4))
		Case func_breachrole_CreateViewmodelForRole
			multiplayer_breach_CreateViewmodelForRole(se_tointarg(0), se_tostringarg(1), SE_ToFloatArg(2), SE_ToFloatArg(3), SE_ToFloatArg(4), SE_ToFloatArg(5))
		Case func_RedirectSound
			RedirectSound(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToIntArg(2))
		; === custom roles
		Case func_breachrole_CreatePlayerRole
			multiplayer_breach_CreatePlayerRole(SE_ToStringArg(0), SE_ToIntArg(1), SE_ToFloatArg(2), SE_ToStringArg(3), SE_ToIntArg(4), SE_ToIntArg(5), SE_ToIntArg(6),SE_ToIntArg(7))
		Case func_breachrole_GetRoleName
			G_SE_ReturnVal = GetTypeName(SE_ToIntArg(0))
			G_SE_ReturnType = SE_STRING
		Case func_breachrole_SetRoleSettings
			multiplayer_breach_SetRoleSettings(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3),SE_ToFloatArg(4),SE_ToIntArg(5),SE_ToStringArg(6),SE_ToIntArg(7),SE_ToIntArg(8))
		Case func_breachrole_SetRoleEffects
			multiplayer_breach_SetRoleEffects(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3),SE_ToIntArg(4),SE_ToIntArg(5),SE_ToIntArg(6),SE_ToIntArg(7),SE_ToIntArg(8),SE_ToIntArg(9),SE_ToIntArg(10))
		Case func_breachrole_SetRoleAmbientSound
			multiplayer_breach_SetRoleAmbientSound(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToStringArg(2))
		Case func_breachrole_SetRoleInstruction
			multiplayer_breach_SetRoleInstruction(SE_ToIntArg(0), SE_ToStringArg(1))
		Case func_breachrole_SetRoleDeadAnimation
			multiplayer_breach_SetRoleDeadAnimation(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2))
		Case func_breachrole_SetRoleHitboxScales
            multiplayer_breach_SetRoleHitboxScales(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3))
        Case func_breachrole_SetRoleBone
			multiplayer_breach_SetRoleBone(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToStringArg(2))
		Case func_breachrole_SetRoleAnimation
			multiplayer_breach_SetRoleAnimation(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3), SE_ToFloatArg(4))
		Case func_breachrole_SetRoleArmedAnimation
			multiplayer_breach_SetRoleArmedAnimation(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3), SE_ToFloatArg(4))
		Case func_breachrole_MarkAsFriend
			multiplayer_breach_MarkAsFriend(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2))
		Case func_breachrole_RoleTakeRoleSpawn
			multiplayer_breach_RoleTakeRoleSpawn(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_breachrole_SetRolePositionsOffset
			multiplayer_breach_SetRolePositionsOffset(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3))
		Case func_breachrole_SetRoleHoldingGrenade
			multiplayer_breach_SetRoleHoldingGrenade(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3))
		Case func_breachrole_SetRoleHoldingItem
			multiplayer_breach_SetRoleHoldingItem(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
		Case func_breachrole_SetRoleHandcuff
			multiplayer_breach_SetRoleHandcuff(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3),SE_ToFloatArg(4), SE_ToStringArg(5), SE_ToFloatArg(6), SE_ToFloatArg(7),SE_ToFloatArg(8))
		Case func_breachrole_AllowRoleWeaponAttaches
			multiplayer_breach_AllowRoleWeaponAttaches(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_breachrole_AllowItemsAttaches
			multiplayer_breach_AllowItemsAttaches(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_breachrole_MarkRoleAsSCP
			multiplayer_breach_MarkRoleAsSCP(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_breachrole_MarkAs035
			multiplayer_breach_MarkAs035(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_breachrole_MarkAs049
			multiplayer_breach_MarkAs049(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_breachrole_IsA035
			G_SE_ReturnVal = multiplayer_breach_IsA035(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_IsA049
			G_SE_ReturnVal = multiplayer_breach_IsA049(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_IsAFriend
			G_SE_ReturnVal = multiplayer_IsAFriend(SE_ToIntArg(0),SE_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_GetMaxHP
			G_SE_ReturnVal = multiplayer_breach_GetMaxHP(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_GetRoleScale
			G_SE_ReturnVal = multiplayer_breach_GetRoleScale(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		; ===
        Case func_createdecal
            Local decal.Decals = CreateDecal.Decals(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3), SE_ToFloatArg(4), SE_ToFloatArg(5), SE_ToFloatArg(6), SE_ToFloatArg(7), SE_ToFloatArg(8))
            If decal <> Null Then
                multiplayer_WriteDecal(decal, 1)
            EndIf
        Case func_updateplayermodel
			multiplayer_UpdatePlayerModel(se_tointarg(0))
		Case func_changeroommesh
			Local roomclip$ = SE_ToStringArg(0)
			For r2.Rooms = Each Rooms
				If r2\RoomTemplate\Name = roomclip Then
					ChangeRMesh(r2, SE_ToStringArg(1))
				EndIf
			Next
		Case func_createitemtemplate
			itt.ItemTemplates = CreateItemTemplate(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToStringArg(2), SE_ToStringArg(3), SE_ToStringArg(4), SE_ToFloatArg(5))
			itt\sound = SE_ToIntArg(6)
			G_SE_ReturnVal = itt\templateID
			G_SE_ReturnType = SE_INT
		Case func_getitemtemplate
			G_SE_ReturnVal = M_Item[SE_ToIntArg(0)]\itemtemplate\templateid
			G_SE_ReturnType = SE_INT
		Case func_getservertimeout
			
			G_SE_ReturnVal = MilliSecs()-NetworkServer\TimeOut-NetworkServer\ServerTimeout
			G_SE_ReturnType = SE_INT
		Case func_getcurrentspectateplayer
			G_SE_ReturnVal = Spectate\SpectatePlayer
			G_SE_ReturnType = SE_INT
		Case func_setradio
			MyPlayer\CurrentRadio = SE_GetParamInt()
		Case func_createevent
			e2.Events = CreateEvent(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToIntArg(2), SE_ToFloatArg(3))
			
			If e2 <> Null Then G_SE_ReturnVal = e2\ID
			G_SE_ReturnType = SE_INT
		Case func_createcube
			G_SE_ReturnVal = CreateCube()
			G_SE_ReturnType = SE_INT
		Case func_createsphere
			G_SE_ReturnVal = CreateSphere()
			G_SE_ReturnType = SE_INT
		Case func_createcylinder
			G_SE_ReturnVal = CreateCylinder()
			G_SE_ReturnType = SE_INT
		Case func_createcone
			G_SE_ReturnVal = CreateCone()
			G_SE_ReturnType = SE_INT
		Case func_camerarange
			CameraRange(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
		Case func_camerafogrange
			CameraFogRange(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
		Case func_setmousehit1
			MouseHit1 = SE_ToIntArg(0)
		Case func_setmousedown1
			MouseDown1 = SE_ToIntArg(0)
		Case func_gettabmenu
			G_SE_ReturnVal = TAB_MENU_STATE
			G_SE_ReturnType = SE_INT
		Case func_turnentity
			TurnEntity(SE_ToIntArg(0), SE_ToFloatArg(1),SE_ToFloatArg(2),SE_ToFloatArg(3),SE_ToIntArg(4))
		Case func_formattext
			FormatTextDefault(SE_ToFloatArg(0), SE_ToFloatArg(1), SE_ToStringArg(2), SE_ToIntArg(3), SE_ToIntArg(4), SE_ToFloatArg(5), SE_ToIntArg(6))
		Case func_stringwidth
			G_SE_ReturnVal = StringWidth(SE_ToStringArg(0))
			G_SE_ReturnType = SE_INT
		Case func_stringheight
			G_SE_ReturnVal = StringHeight(SE_ToStringArg(0))
			G_SE_ReturnType = SE_INT
		Case func_imagewidth
			G_SE_ReturnVal = ImageWidth(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_imageheight
			G_SE_ReturnVal = ImageHeight(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_scaleimage
			ScaleImage(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
		Case func_isvalidobject
			G_SE_ReturnVal = multiplayer_Object[SE_ToIntArg(0)]<>Null
			G_SE_ReturnType = SE_INT
		Case func_getobjectentity
			G_SE_ReturnVal = multiplayer_Object[SE_ToIntArg(0)]\obj
			G_SE_ReturnType = SE_INT
		Case func_browser_loadurl
			SteamBrowser_LoadURL(Object.SteamBrowser(SE_ToIntArg(0)), SE_ToStringArg(1), SE_ToStringArg(2))
		Case func_browser_create
			SB.SteamBrowser = SteamBrowser_Create(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToStringArg(2))
			
			G_SE_ReturnVal = Handle(SB)
			G_SE_ReturnType = SE_INT
		Case func_browser_destroy
			SteamBrowser_Destroy(Object.SteamBrowser(SE_ToIntArg(0)))
		Case func_browser_getimage
			G_SE_ReturnVal = SteamBrowser_GetImageHandle(Object.SteamBrowser(SE_ToIntArg(0)))
			G_SE_ReturnType = SE_INT
		Case func_browser_gettexture
			G_SE_ReturnVal = SteamBrowser_GetTextureHandle(Object.SteamBrowser(SE_ToIntArg(0)))
			G_SE_ReturnType = SE_INT
		Case func_browser_MouseDown
			SB.SteamBrowser = Object.SteamBrowser(SE_ToIntArg(0))
			
			BS_ISteamHTMLSurface_MouseDown BS_SteamHTMLSurface(), SB\Id, 0
		Case func_browser_MouseUp
			SB.SteamBrowser = Object.SteamBrowser(SE_ToIntArg(0))
			
			BS_ISteamHTMLSurface_MouseUp BS_SteamHTMLSurface(), SB\Id, 0
		Case func_browser_setmousepos
			SB.SteamBrowser = Object.SteamBrowser(SE_ToIntArg(0))
			
			BS_ISteamHTMLSurface_MouseMove BS_SteamHTMLSurface(), SB\Id, SE_TOIntArg(1), SE_ToIntArg(2)
		Case func_tcp_get
			G_SE_ReturnVal = DownloadFile(SE_ToStringArg(0))
			G_SE_ReturnType = SE_INT
		Case func_tcp_close
			CloseTCPStream(SE_ToIntArg(0))
			
		Case func_createsprite
			G_SE_ReturnVal = CreateSprite()
			G_SE_ReturnType = SE_INT
		Case func_loadsprite
			G_SE_ReturnVal = LoadSprite(SE_ToStringArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_spriteviewmode
			SpriteViewMode(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_scalesprite
			ScaleSprite(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
		Case func_entityfx
			EntityFX(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_entityblend
			EntityBlend(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_showentity
			ShowEntity(SE_ToIntArg(0))
		Case func_hideentity
			HideEntity(SE_ToIntArg(0))
		Case func_getparent
			G_SE_ReturnVal = GetParent(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_entityautofade
			EntityAutoFade(SE_ToIntArg(0), SE_ToFloatArg(0), SE_ToFloatArg(1))
		Case func_entitycolor
			EntityColor(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3))
		Case func_entityshininess
			EntityShininess(SE_ToIntArg(0), SE_ToFloatArg(1))
		Case func_entityalpha
			EntityAlpha(SE_ToIntArg(0), SE_ToFloatArg(1))
		Case func_entitytexture
			EntityTexture(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3))
		Case func_createtexture
			G_SE_ReturnVal = CreateTexture(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3))
			G_SE_ReturnType = SE_INT
		Case func_loadtexture
			G_SE_ReturnVal = LoadTexture(SE_ToStringArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_scaletexture
			ScaleTexture(SE_ToIntArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
		Case func_texturewidth
			G_SE_ReturnVal = TextureWidth(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_textureheight
			G_SE_ReturnVal = TextureHeight(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_entityparent
			EntityParent(SE_ToIntArg(0), SE_ToIntArg(1))
			
		Case func_entityscalex
			G_SE_ReturnVal = EntityScaleX2(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_entityscaley
			G_SE_ReturnVal = EntityScaleY2(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_entityscalez
			G_SE_ReturnVal = EntityScaleZ2(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
			
		Case func_meshwidth
			G_SE_ReturnVal = MeshWidth(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_meshheight
			G_SE_ReturnVal = MeshHeight(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_meshdepth
			G_SE_ReturnVal = MeshDepth(SE_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
			
		Case func_pointentity
			PointEntity(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_entitydistance
			G_SE_ReturnVal = EntityDistance(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_deltayaw
			G_SE_ReturnVal = DeltaYaw(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_deltapitch
			G_SE_ReturnVal = DeltaPitch(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_isplayerconnected
			G_SE_ReturnVal = (Player[SE_ToIntArg(0)]<>Null)
			G_SE_ReturnType = SE_INT
		Case func_getplayerentity
			;G_SE_ReturnVal = Player[SE_ToIntArg(0)]\Pivot
			;G_SE_ReturnType = SE_INT
		Case func_getplayerhitboxentity
			;G_SE_ReturnVal = Player[SE_ToIntArg(0)]\Hitbox
			;G_SE_ReturnType = SE_INT
		Case func_getplayermodelentity
			;G_SE_ReturnVal = Player[SE_ToIntArg(0)]\obj
			;G_SE_ReturnType = SE_INT
		; coll
		Case func_resetentity
			ResetEntity(SE_ToIntArg(0))
		Case func_entityradius
			EntityRadius(SE_ToIntArg(0), SE_ToFloatArg(1))
		Case func_entitybox
			EntityBox(SE_TointArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3), SE_ToFloatArg(4), SE_ToFloatArg(5), SE_ToFloatArg(6))
		Case func_entitycollisiontype
			EntityType(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2))
		Case func_entitypickmode
			EntityPickMode(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2))
		Case func_entitycollided
			G_SE_ReturnVal = EntityCollided(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_countcollisions
			G_SE_ReturnVal = CountCollisions(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_collisionx
			G_SE_ReturnVal = CollisionX(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisiony
			G_SE_ReturnVal = CollisionY(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionz
			G_SE_ReturnVal = CollisionZ(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionnx
			G_SE_ReturnVal = CollisionNX(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionny
			G_SE_ReturnVal = CollisionNY(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionnz
			G_SE_ReturnVal = CollisionNZ(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionentity
			G_SE_ReturnVal = CollisionEntity(SE_ToIntArg(0), SE_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_getentitycollisiontype
			G_SE_ReturnVal = GetEntityType(SE_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		;
		Case func_preparemodelidentifier
			preparemodelidentifier(SE_ToIntArg(0), SE_ToStringArg(1))
		Case func_getkey
			G_SE_ReturnVal = GetKey()
			G_SE_ReturnType = SE_INT
		Case func_drawuiinputbox
			G_SE_ReturnVal = InputBox(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3), SE_ToStringArg(4), SE_ToIntArg(5))
			G_SE_ReturnType = SE_STRING
		Case func_drawuibutton
			G_SE_ReturnVal = DrawButton(SE_ToIntArg(0), SE_ToIntArg(1), SE_ToIntArg(2), SE_ToIntArg(3), SE_ToStringArg(4), SE_ToIntArg(5), SE_ToIntArg(6), True, -1, -1, -1, -1, -1, -1, True)
			G_SE_ReturnType = SE_INT
		Case func_ismainmenuopen
			G_SE_ReturnVal = MainMenuOpen
			G_SE_ReturnType = SE_INT
		Case func_lerp
			If SE_ToIntArg(3) = 0 Then
				G_SE_ReturnVal = curvevalue(SE_ToFloatArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
			Else
				G_SE_ReturnVal = tovalue(SE_ToFloatArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
			EndIf
			G_SE_ReturnType = SE_FLOAT
		Case func_lerpangle
			If SE_ToIntArg(3) = 0 Then
				G_SE_ReturnVal = curveangle(SE_ToFloatArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
			Else
				G_SE_ReturnVal = toangle(SE_ToFloatArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2))
			EndIf
			G_SE_ReturnType = SE_FLOAT
		Case func_removetimer
			RemoveTimer(Object.timers(SE_GetParamInt()))
		Case func_createtimer
			timername$ = SE_GetParamString()
			interval = SE_GetParamInt()
			loop = SE_GetParamInt()
			Local bytestr.bs = CreateByteStream(372)
			Local identifiers$
			For i = 3 To SE_ARGUMENTS_NUMBER-1
				If SE_ArgType(i) <> SE_NULL Then
					Select SE_ArgType(i)
						Case SE_INT
							identifiers = identifiers+"i"
							ByteStreamWriteInt(bytestr,SE_ToIntArg(i))
						Case SE_FLOAT
							identifiers = identifiers+"f"
							ByteStreamWriteFloat(bytestr,SE_ToFloatArg(i))
						Case SE_STRING
							identifiers = identifiers+"s"
							ByteStreamWriteString(bytestr,SE_ToStringArg(i))
					End Select
				EndIf
			Next
			G_SE_ReturnVal = SetTimer(CurrentFuncPtr\Script, timername, interval, loop, identifiers, bytestr)
			G_SE_ReturnType = SE_INT
			
		Case func_getinivalue
			filename$ = SE_GetParamString()
			section$ = SE_GetParamString()
			key$ = SE_GetParamString()
			defaultreturnvalue$ = SE_GetParamString()
			
			G_SE_ReturnType = SE_STRING
			G_SE_ReturnVal = GetIniString(filename, section, key, defaultreturnvalue)
		Case func_putinivalue
			filename$ = SE_GetParamString()
			section$ = SE_GetParamString()
			key$ = SE_GetParamString()
			defaultreturnvalue$ = SE_GetParamString()
			PutIniValue(filename, section, key, defaultreturnvalue)
		Case func_updateinifile
			UpdateINIFile(SE_GetParamString())
			
		Case func_callglobalfunction
			Local CurrentScriptThread.SE_Script = Null
			
			For s.ScriptsThread = Each ScriptsThread
				If s\scriptthread <> Null Then
					If Lower(s\path) = Lower(SE_ToStringArg(0)) Then
						CurrentScriptThread = s\scriptthread
						Exit
					EndIf
				EndIf
			Next
			
			If CurrentScriptThread = Null Then
				For WT.WorkshopThread = Each WorkshopThread
					If WT\scriptthread <> Null Then
						If Lower(WT\scriptfile) = Lower(SE_ToStringArg(0)) Then
							CurrentScriptThread = WT\scriptthread
							Exit
						EndIf
					EndIf
				Next
			EndIf
			
			For i = 2 To SE_ARGUMENTS_NUMBER
				If SE_ArgType(i) <> SE_NULL Then public_addparam(SE_ToStringArg(i), SE_ArgType(i))
			Next
			public_update_by_func(SE_FindFunc(CurrentScriptThread, Lower(SE_ToStringArg(1))), True)
			public_clear()
			G_SE_ReturnVal = SE_GetReturnValue()
			G_SE_ReturnType = SE_RETURN_VALUE\ValueType
		Case func_callfunction
			For i = 1 To SE_ARGUMENTS_NUMBER
				If SE_ArgType(i) <> SE_NULL Then public_addparam(SE_ToStringArg(i), SE_ArgType(i))
			Next
			public_update_by_func(SE_FindFunc(CurrentFuncPtr\Script, Lower(SE_ToStringArg(0))), True)
			public_clear()
			G_SE_ReturnVal = SE_GetReturnValue()
			G_SE_ReturnType = SE_RETURN_VALUE\ValueType
		Case func_iskeypadopen
			G_SE_ReturnVal = (SelectedDoor<>Null)
			G_SE_ReturnType = SE_INT
		Case func_isconsoleopen
			G_SE_ReturnVal = ConsoleOpen
			G_SE_ReturnType = SE_INT
		Case func_iscoffeemachineopen
			G_SE_ReturnVal = Using294
			G_SE_ReturnType = SE_INT
		Case func_ismenuopen
			G_SE_ReturnVal = MenuOpen
			G_SE_ReturnType = SE_INT
		Case func_isinventoryopen
			G_SE_ReturnVal = InvOpen
			G_SE_ReturnType = SE_INT
		Case func_isotherinventoryopen
			G_SE_ReturnVal = (OtherOpen<>Null)
			G_SE_ReturnType = SE_INT
		Case func_lockmouse
			LockMouse = SE_GetParamInt()
		Case func_getoptionsmenu
			G_SE_ReturnVal = OptionsMenu
			G_SE_ReturnType = SE_INT
		Case func_getachievementsmenu
			G_SE_ReturnVal = achievementsmenu
			G_SE_ReturnType = SE_INT
		Case func_showpointer
			ShowPointer()
		Case func_hidepointer
			HidePointer()
		Case func_debuglog
			DebugLog SE_GetParamString()
		Case func_initloadingscreens
			InitLoadingScreens(SE_GetParamString())
		Case func_initinfoclues
			InitInfoClues(SE_GetParamString())
		Case func_stopallredirecttext
			ws_ClearRedirectText()
		Case func_setscriptlanguage
			If CurrentFuncPtr <> Null Then
				G_SE_ReturnType = SE_STRING
				For WT.WorkshopThread = Each WorkshopThread
					If WT\scriptthread = CurrentFuncPtr\Script Then
						WT\IsLanguage$ = SE_GetParamString()
						Exit
					EndIf
				Next
			EndIf
		Case func_getscriptpath
			If CurrentFuncPtr <> Null Then
				G_SE_ReturnType = SE_STRING
				For WT.WorkshopThread = Each WorkshopThread
					If WT\scriptthread = CurrentFuncPtr\Script Then
						G_SE_ReturnVal = WT\scriptpath
						Exit
					EndIf
				Next
			EndIf
		Case func_freefonts
			FreeAllFonts(SE_GetParamInt())
		Case func_loadfonts
			LoadAllFonts(SE_GetParamInt())
			;ReloadAAFont()
		Case func_redirectfont
			orig$ = SE_GetParamString()
			redir$ = SE_GetParamString()
			G_SE_ReturnVal = ws_RedirectFont(orig, redir)
			G_SE_ReturnType = SE_INT
		Case func_redirectfile
			orig$ = SE_GetParamString()
			redir$ = SE_GetParamString()
			G_SE_ReturnVal = ws_RedirectFile(orig, redir)
			G_SE_ReturnType = SE_INT
		Case func_redirecttext
			orig$ = SE_GetParamString()
			redir$ = SE_GetParamString()
			G_SE_ReturnVal = ws_RedirectText(orig, redir)
			G_SE_ReturnType = SE_INT
		Case func_stopredirecttext
			orig$ = SE_GetParamString()
			G_SE_ReturnVal = ws_StopRedirectText(orig)
			G_SE_ReturnType = SE_INT
		Case func_disableallworkshopscripts
			ws_DisableAll()
			; =========================== Other
		Case func_setintercomtimeout
			;	PlayerIntercom\IntercomTimeout = SE_GetParamInt()
		Case func_getintercomtimeout
			;	G_SE_ReturnVal = PlayerIntercom\IntercomTimeout
			;	G_SE_ReturnType = SE_INT
			; =========================== Inventory
		Case func_getinventoryslotitemname
			InvSlot = SE_GetParamInt()
			G_SE_ReturnVal = "Null"
			G_SE_ReturnType = SE_STRING
			
			If Inventory(InvSlot) <> Null Then
			G_SE_ReturnVal = Inventory(InvSlot)\itemtemplate\name
			G_SE_ReturnType = SE_STRING
			EndIf
		Case func_setselecteditemfrominventory
			InvSlot = SE_GetParamInt()
			If Inventory(InvSlot) <> Null Then
			SelectedItem = Inventory(InvSlot)
			EndIf
			; =========================== States
		Case func_isvaliditem
			itemid = SE_GetParamInt()
			
			G_SE_ReturnType = SE_INT
			If itemid > 0 And itemid < MAX_ITEMS Then
			G_SE_ReturnVal = (M_Item[itemid]<>Null)
			EndIf
		Case func_getselecteditem
			G_SE_ReturnType = SE_INT
			If SelectedItem <> Null Then
			G_SE_ReturnVal = SelectedItem\ID
			Else
			G_SE_ReturnVal = 0
			EndIf
		Case func_getitemname
			itemid = SE_GetParamInt()
			G_SE_ReturnType = SE_STRING
			If itemid > 0 And itemid < MAX_ITEMS Then
			G_SE_ReturnVal = M_Item[itemid]\itemtemplate\name
			EndIf
		Case func_getitemtempname
			itemid = SE_GetParamInt()
			G_SE_ReturnType = SE_STRING
			If itemid > 0 And itemid < MAX_ITEMS Then
			G_SE_ReturnVal = M_Item[itemid]\itemtemplate\tempname
			EndIf
		Case func_getitemstate
			itemid = SE_GetParamInt()
			G_SE_ReturnType = SE_FLOAT
			If itemid > 0 And itemid < MAX_ITEMS Then G_SE_ReturnVal = M_Item[itemid]\state
		Case func_get427timer
			G_SE_ReturnVal = I_427\Timer
			G_SE_ReturnType = SE_FLOAT
		Case func_getblurtimer
			G_SE_ReturnVal = BlurTimer
			G_SE_ReturnType = SE_FLOAT
		Case func_getstamina
			G_SE_ReturnVal = Stamina
			G_SE_ReturnType = SE_FLOAT
		Case func_getblinktimer
			G_SE_ReturnVal = BlinkTimer
			G_SE_ReturnType = SE_FLOAT
		Case func_getcurrentweapon
			If EqquipedGun <> Null Then
			G_SE_ReturnVal = EqquipedGun\ID
			G_SE_ReturnType = SE_INT
			EndIf
		Case func_getcurrentweaponmagazines
			If EqquipedGun <> Null Then
			G_SE_ReturnVal = EqquipedGun\Magazines
			G_SE_ReturnType = SE_INT
			EndIf
		Case func_getcurrentweaponammo
			If EqquipedGun <> Null Then
			G_SE_ReturnVal = EqquipedGun\CurrAmmo
			G_SE_ReturnType = SE_INT
			EndIf
			; =========================== Keys
		Case func_keyhit
			G_SE_ReturnVal = KeyHit(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_keydown
			G_SE_ReturnVal = KeyDown(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			; =========================== Sound
		Case func_createsound3d
			SE_FlipParams(6)
			channel = 0
			yawRocket# = SE_GetFlippedParamString()
			pitchRocket# = SE_GetFlippedParamString()
			zRocket# = SE_GetFlippedParamString()
			yRocket# = SE_GetFlippedParamString()
			xrocket# = SE_GetFlippedParamString()
			filename$ = SE_GetFlippedParamString()
			For snd.sound = Each Sound
				If snd\name = filename Then
					channel = Play3DSoundEntity(Handle(snd), Camera, tempent, floatpitch, floatyaw)
					filename = ""
					Exit
				EndIf
			Next
			If filename <> "" Then channel = Play3DSoundEntity(0, Camera, tempent, floatpitch, floatyaw, filename)
			
			G_SE_ReturnVal = channel
			G_SE_ReturnType = SE_INT
		Case func_createlocalsound
			channel = PlaySound_Strict(LoadTempSound(SE_ToStringArg(0)))
			ChannelVolume channel, SE_ToFloatArg(1)
			
			G_SE_ReturnVal = channel
			G_SE_ReturnType = SE_INT
		Case func_getvolume
			G_SE_ReturnVal = SFXVolume
			G_SE_ReturnType = SE_FLOAT
		;Case func_stopchannel
		;	StopChannel(SE_GetParamInt())
		;Case func_resumechannel
		;	ResumeChannel(SE_GetParamInt())
		;Case func_pausechannel
		;	PauseChannel(SE_GetParamInt())
		;Case func_channelpitch
		;	channel = SE_GetParamInt()
		;	volume# = SE_GetParamFloat()
		;	ChannelPitch(channel, volume)
		;Case func_channelvolume
		;	channel = SE_GetParamInt()
		;	volume# = SE_GetParamFloat()
		;	ChannelVolume(channel, volume)
		;Case func_channelpan
		;	channel = SE_GetParamInt()
		;	volume# = SE_GetParamFloat()
		;	ChannelPan(channel, volume)
		;Case func_channelplaying
		;	G_SE_ReturnVal = ChannelPlaying(SE_GetParamInt())
		;	G_SE_ReturnType = SE_INT
		Case func_stop3dsounds
			For snd2.snd3d = Each snd3d
				If Not snd2\fmod Then
					StopChannel(snd2\soundchn)
					If snd2\sound <> 0 Then FreeSound(snd2\sound)
					If snd2\tempentity Then FreeEntity snd2\entity
					Delete snd2
				Else
					FSOUND_StopSound(snd2\soundchn)
					FSOUND_Stream_Close(snd2\sound)
					Delete snd2
				EndIf
			Next
		Case func_setcamerashake
			CameraShake = SE_GetParamFloat()
			; =========================== Drawing
		Case func_freefont
			FreeFont SE_GetParamInt()
		Case func_freeimage
			FreeImage SE_GetParamInt()
		Case func_setfont
			SelectedFont_ = SE_GetParamInt()
			SetFont SelectedFont_
		Case func_color
			r = SE_GetParamInt()
			g = SE_GetParamInt()
			b = SE_GetParamInt()
			Color r,g,b
		Case func_loadimage
			G_SE_ReturnVal = LoadImage(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_loadanimimage
			fnt$ = SE_GetParamString()
			width = SE_GetParamInt()
			height = SE_GetParamInt()
			firs = SE_GetParamInt()
			count = SE_GetParamInt()
			G_SE_ReturnVal = LoadAnimImage(SE_GetParamString(),width,height,firs,count)
			G_SE_ReturnType = SE_INT
		Case func_loadfont
			fnt$ = SE_GetParamString()
			size = SE_GetParamInt()
			bold = SE_GetParaMint()
			italic = SE_GetParaMint()
			underlined = SE_GetParaMint()
			G_SE_ReturnVal = LoadFont(fnt, size, bold, italic, underlined)
			G_SE_ReturnType = SE_INT
		Case func_copyimage
			G_SE_ReturnVal = CopyImage(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_drawimage
			img = SE_GetParamInt()
			r = SE_GetParamInt()
			g = SE_GetParamInt()
			frame = SE_GetParamInt()
			DrawImage img,r,g, frame
		Case func_drawblock
			img = SE_GetParamInt()
			r = SE_GetParamInt()
			g = SE_GetParamInt()
			frame = SE_GetParamInt()
			DrawBlock img,r,g, frame
		Case func_resizeimage
			img = SE_GetParamInt()
			width = SE_GetParamInt()
			height = SE_GetParamInt()
			ResizeImage img, width, height
		Case func_rotateimage
			img = SE_GetParamInt()
			rot# = SE_GetParamFloat()
			RotateImage img, rot
		Case func_text
			x = SE_GetParamInt()
			y = SE_GetParamInt()
			txt$ = SE_GetParamString()
			centerx = SE_GetParamInt()
			centery = SE_GetParamInt()
			Text x, y, txt, centerx, centery
		Case func_oval
			x = SE_GetParamInt()
			y = SE_GetParamInt()
			width = SE_GetParamString()
			height = SE_GetParamInt()
			solid = SE_GetParamInt()
			Oval x, y, width, height, solid
		Case func_rect
			x = SE_GetParamInt()
			y = SE_GetParamInt()
			width = SE_GetParamString()
			height = SE_GetParamInt()
			solid = SE_GetParamInt()
			Rect x, y, width, height, solid
			; =========================== Mouse
		Case func_mousex
			G_SE_ReturnVal = MouseX()
			G_SE_ReturnType = SE_INT
		Case func_mousey
			G_SE_ReturnVal = MouseY()
			G_SE_ReturnType = SE_INT
		Case func_mousehit1
			G_SE_ReturnVal = MouseHit1
			G_SE_ReturnType = SE_INT
		Case func_mousehit2
			G_SE_ReturnVal = MouseHit2
			G_SE_ReturnType = SE_INT
		Case func_mousedown1
			G_SE_ReturnVal = MouseDown1
			G_SE_ReturnType = SE_INT
		Case func_mousedown2
			G_SE_ReturnVal = MouseDown(2)
			G_SE_ReturnType = SE_INT
		Case func_connect
			ip$ = SE_GetParamString()
			port = SE_GetParamInt()
			pass$ = SE_GetParamString()
			DisconnectServer()
			multiplayer_ConnectTo(ip, port, pass)
		Case func_disconnect
			DisconnectServer(SE_GetParamString())
		Case func_setplayerroom
			Local roomn$ = SE_GetParamString()
			For rm.rooms = Each rooms
				If rm\roomtemplate\name = roomn Then
					G_SE_ReturnVal = rm\ID
					G_SE_ReturnType = SE_INT
					PlayerRoom = rm
					Exit
				EndIf
			Next
		Case func_createpivot
			G_SE_ReturnVal = CreatePivot()
			G_SE_ReturnType = SE_INT
		Case func_loadmesh
			G_SE_ReturnVal = LoadMesh(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_loadanimmesh
			G_SE_ReturnVal = LoadAnimMesh(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_getplayercollider
			G_SE_ReturnVal = Collider
			G_SE_ReturnType = SE_INT
		Case func_getplayercamera
			G_SE_ReturnVal = Camera
			G_SE_ReturnType = SE_INT
		Case func_cameraclscolor
			r = SE_GetParamInt()
			g = SE_GetParamInt()
			b = SE_GetParamInt()
			CameraClsColor(Camera, r,g,b)
		Case func_camerafogcolor
			r = SE_GetParamInt()
			g = SE_GetParamInt()
			b = SE_GetParamInt()
			CameraFogColor(Camera, r,g,b)
		Case func_setambientlight
			r = SE_GetParamInt()
			g = SE_GetParamInt()
			b = SE_GetParamInt()
			AmbientLight(r,g,b)
		Case func_hidescreenocclusion
			HideEntity Fog
		Case func_showscreenocclusion
			ShowEntity Fog
		Case func_getselectedscreen
			G_SE_ReturnVal = (SelectedScreen<>Null)
			G_SE_ReturnType = SE_INT
		Case func_resetselectedscreen
			SelectedScreen = Null
		Case func_showsky
			If sky <> 0 Then ShowEntity sky
		Case func_hidesky
			If sky <> 0 Then HideEntity sky
		Case func_getroomname
			If PlayerRoom <> Null Then
				G_SE_ReturnVal = PlayerRoom\RoomTemplate\Name
			EndIf
			G_SE_ReturnType = SE_STRING
			; ===================================== Entity Control
		Case func_entityinview
			ent = SE_GetParamInt()
			G_SE_ReturnVal = EntityInView(ent, SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_entityvisible
			ent = SE_GetParamInt()
			G_SE_ReturnVal = EntityVisible(ent, SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_entityx
			G_SE_ReturnVal = EntityX(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_entityy
			G_SE_ReturnVal = EntityY(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_entityz
			G_SE_ReturnVal = EntityZ(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_entitypitch
			G_SE_ReturnVal = EntityPitch(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_entityyaw
			G_SE_ReturnVal = EntityYaw(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_entityroll
			G_SE_ReturnVal = EntityRoll(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_entitypick
			ent = SE_GetParamInt()
			G_SE_ReturnVal = EntityPick(ent, SE_GetParamFloat())
			G_SE_ReturnType = SE_INT
			
		Case func_positionentity
			ent = SE_GetParamInt()
			xf# = SE_GetParamFloat()
			yf# = SE_GetParamFloat()
			zf# = SE_GetParamFloat()
			PositionEntity ent, xf,yf,zf,SE_GetParamInt()
		Case func_rotateentity
			ent = SE_GetParamInt()
			xf# = SE_GetParamFloat()
			yf# = SE_GetParamFloat()
			zf# = SE_GetParamFloat()
			RotateEntity ent, xf,yf,zf,SE_GetParamInt()
		Case func_moveentity
			If SE_ToIntArg(4) = 0 Then
				ent = SE_GetParamInt()
				xf# = SE_GetParamFloat()
				yf# = SE_GetParamFloat()
				zf# = SE_GetParamFloat()
				MoveEntity ent, xf,yf,zf
			Else
				ent = SE_GetParamInt()
				xf# = SE_GetParamFloat()
				yf# = SE_GetParamFloat()
				zf# = SE_GetParamFloat()
				TranslateEntity ent, xf,yf,zf
			EndIf
		Case func_scaleentity
			ent = SE_GetParamInt()
			xf# = SE_GetParamFloat()
			ScaleEntity ent, xf,xf,xf
		Case func_setentitycollision
			ent = SE_GetParamInt()
			EntityType ent, HIT_MAP*SE_GetParamInt()
		Case func_removeentity
			FreeEntity SE_GetParamInt()
		Case func_setanimtime
			ent = SE_GetParamInt()
			xf# = SE_GetParamFloat()
			SetAnimTime ent, xf
		Case func_getdelta
			G_SE_ReturnVal = FPSfactor
			G_SE_ReturnType = SE_FLOAT
		Case func_getmonitorwidth
			G_SE_ReturnVal = GraphicWidth
			G_SE_ReturnType = SE_INT
		Case func_getmonitorheight
			G_SE_ReturnVal = GraphicHeight
			G_SE_ReturnType = SE_INT
		Case func_sendrawpacket
			bank = SE_GetParamInt()
			
			udp_WriteByte M_RAWPACKET
			udp_WriteByte NetworkServer\MyID
			udp_WriteBytes bank, 0, BankSize(bank)
			udp_SendMessage()
		Case func_hexstring
			Local towrite, strings$ = SE_GetParamString()
			For i = 1 To Len(strings)
			towrite = towrite + (Asc(Mid(strings, i, 1)) Shl (8*(i-1)))
			Next
			G_SE_ReturnVal = towrite
			G_SE_ReturnType = SE_INT
		Case func_eof
			G_SE_ReturnVal = Eof(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_filetype
			G_SE_ReturnVal = FileType("multiplayer\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_filesize
			G_SE_ReturnVal = FileSize("multiplayer\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_workshopfiletype
			G_SE_ReturnVal = FileType("workshop\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_workshopfilesize
			G_SE_ReturnVal = FileSize("workshop\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_seekfile
			filehandle = SE_GetParamint()
			seek = SE_GetParamint()
			SeekFile(filehandle, seek)
		Case func_filepos
			G_SE_ReturnVal = FilePos(SE_GetParamint())
			G_SE_ReturnType = SE_INT
		Case func_catcherror
			G_SE_ReturnVal = ErrorLog()
			G_SE_ReturnType = SE_STRING
		Case func_writebytes
			bank = SE_GetParamint()
			stream = SE_GetParamInt()
			offset = SE_GetParamInt()
			count = SE_GetParaMint()
			G_SE_ReturnVal = WriteBytes(bank, stream, offset, count)
			G_SE_ReturnType = SE_INT
		Case func_readbytes
			bank = SE_GetParamint()
			stream = SE_GetParamInt()
			offset = SE_GetParamInt()
			count = SE_GetParaMint()
			G_SE_ReturnVal = ReadBytes(bank, stream, offset, count)
			G_SE_ReturnType = SE_INT
		Case func_createbank
			G_SE_ReturnVal = CreateBank(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_freebank
			FreeBank(SE_GetParamInt())
		Case func_banksize
			G_SE_ReturnVal = BankSize(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_resizebank
			src_bank = SE_GetParamInt()
			count = SE_GetParamint()
			ResizeBank(src_bank, count)
		Case func_copybank
			src_bank = SE_GetParamInt()
			src_offset = SE_GetParamInt()
			dest_bank = SE_GetParamInt()
			dest_offset = SE_GetParamInt()
			count = SE_GetParamInt()
			CopyBank(src_bank, src_offset, dest_bank, dest_offset, count)
		Case func_peekbyte
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			G_SE_ReturnVal = PeekByte(bank, offset)
			G_SE_ReturnType = SE_INT
		Case func_peekshort
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			G_SE_ReturnVal = PeekShort(bank, offset)
			G_SE_ReturnType = SE_INT
		Case func_peekint
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			G_SE_ReturnVal = PeekInt(bank, offset)
			G_SE_ReturnType = SE_INT
		Case func_peekfloat
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			G_SE_ReturnVal = PeekFloat(bank, offset)
			G_SE_ReturnType = SE_FLOAT
		Case func_pokebyte
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			value = SE_GetParamInt()
			PokeByte(bank, offset, value)
		Case func_pokeshort
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			value = SE_GetParamInt()
			PokeShort(bank, offset, value)
		Case func_pokeint
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			value = SE_GetParamInt()
			PokeInt(bank, offset, value)
		Case func_pokefloat
			bank = SE_GetParamInt()
			offset = SE_GetParamInt()
			value = SE_GetParamFloat()
			PokeFloat(bank, offset, value)
		Case func_movex
			G_SE_ReturnVal = movex
			G_SE_ReturnType = SE_FLOAT
		Case func_movey
			G_SE_ReturnVal = movey
			G_SE_ReturnType = SE_FLOAT
		Case func_movez
			G_SE_ReturnVal = movez
			G_SE_ReturnType = SE_FLOAT
		Case func_move
			SE_FlipParams(8)
			pitch# = SE_GetFlippedParamString()
			angle# = SE_GetFlippedParamString()
			zat# = SE_GetFlippedParamString()
			yat# = SE_GetFlippedParamString()
			xat# = SE_GetFlippedParamString()
			zf# = SE_GetFlippedParamString()
			yf# = SE_GetFlippedParamString()
			xf# = SE_GetFlippedParamString()
			pv = CreatePivot()
			PositionEntity pv, xf, yf, zf
			RotateEntity pv, pitch, angle, 0
			MoveEntity pv, xat, yat, zat
			movex = EntityX(pv)
			movey = EntityY(pv)
			movez = EntityZ(pv)
			FreeEntity pv
		Case func_pointangle
			G_SE_ReturnVal = pointyaw
			G_SE_ReturnType = SE_FLOAT
		Case func_pointpitch
			G_SE_ReturnVal = pointpitch
			G_SE_ReturnType = SE_FLOAT
		Case func_pointat
			SE_FlipParams(6)
			zat# = SE_GetFlippedParamString()
			yat# = SE_GetFlippedParamString()
			xat# = SE_GetFlippedParamString()
			zf# = SE_GetFlippedParamString()
			yf# = SE_GetFlippedParamString()
			xf# = SE_GetFlippedParamString()
			pv = CreatePivot()
			pv2 = CreatePivot()
			PositionEntity pv, xf, yf, zf
			PositionEntity pv2, xat, yat, zat
			PointEntity pv, pv2
			pointyaw# = EntityYaw(pv)
			pointpitch# = EntityPitch(pv)
			FreeEntity pv
			FreeEntity pv2
		Case func_closefile
			CloseFile SE_GetParamInt()
		Case func_readline
			G_SE_ReturnVal = ReadLine(SE_GetParamInt())
			G_SE_ReturnType = SE_STRING
		Case func_readint
			G_SE_ReturnVal = ReadInt(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_readfloat
			G_SE_ReturnVal = ReadFloat(SE_GetParamInt())
			G_SE_ReturnType = SE_FLOAT
		Case func_readshort
			G_SE_ReturnVal = ReadShort(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_readbyte
			G_SE_ReturnVal = ReadByte(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_writeline
			SE_FlipParams(2)
			smsg$ = SE_GetFlippedParamString()
			val = SE_GetFlippedParamString()
			WriteLine val, smsg
		Case func_writeint
			SE_FlipParams(2)
			smsg$ = SE_GetFlippedParamString()
			val = SE_GetFlippedParamString()
			WriteInt val, smsg
		Case func_writefloat
			SE_FlipParams(2)
			smsg$ = SE_GetFlippedParamString()
			val = SE_GetFlippedParamString()
			WriteFloat val, smsg
		Case func_writeshort
			SE_FlipParams(2)
			smsg$ = SE_GetFlippedParamString()
			val = SE_GetFlippedParamString()
			WriteShort val, smsg
		Case func_writebyte
			SE_FlipParams(2)
			smsg$ = SE_GetFlippedParamString()
			val = SE_GetFlippedParamString()
			WriteByte val, smsg
		Case func_openfile
			G_SE_ReturnVal = OpenFile("multiplayer\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_readfile
			G_SE_ReturnVal = ReadFile("multiplayer\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_writefile
			filename$ = "multiplayer\"+SE_ToStringArg(0)
			If Not IsFolderSecured(filename) Then Return

			G_SE_ReturnVal = WriteFile(filename)
			G_SE_ReturnType = SE_INT
		Case func_openworkshopfile
			G_SE_ReturnVal = OpenFile("workshop\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_readworkshopfile
			G_SE_ReturnVal = ReadFile("workshop\"+SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_writeworkshopfile
			filename$ = "workshop\"+SE_ToStringArg(0)
			If Not IsFolderSecured(filename) Then Return

			G_SE_ReturnVal = WriteFile(filename)
			G_SE_ReturnType = SE_INT
		Case func_getversion
			G_SE_ReturnVal = MULTIPLAYER_VERSION
			G_SE_ReturnType = SE_STRING
		Case func_millisecs
			G_SE_ReturnVal = MilliSecs()
			G_SE_ReturnType = SE_INT
		Case func_getping
			G_SE_ReturnVal = ServerPing
			G_SE_ReturnType = SE_INT
		Case func_getname
			G_SE_ReturnVal = NickName
			G_SE_ReturnType = SE_STRING
		Case func_setgamemessage
			Msg = SE_GetParamString()
			MsgTimer = 70*SE_GetParamInt()
		Case func_sendconsolecommand
			ExecuteConsoleCommand(SE_GetParamString())
		Case func_sendmessage
			smsg$ = SE_GetParamString()
			loca = SE_GetParamInt()
			multiplayer_AddChatMsg(smsg, loca)
		Case func_plugin_remove
			If 0 Then
				G_SE_ReturnVal = plugin_remove(SE_GetParamInt())
				G_SE_ReturnType = SE_INT
			EndIf
		Case func_plugin_call
			If 0 Then
				SE_FlipParams(3)
				returntype = SE_GetFlippedParamString()
				functionname$ = SE_GetFlippedParamString()
				pluginid = SE_GetFlippedParamString()
				G_SE_ReturnVal = plugin_call(pluginid, functionname, returntype)
				G_SE_ReturnType = SE_STRING
			EndIf
		Case func_plugin_poke
			If 0 Then
				SE_FlipParams(3)
				i_type = SE_GetFlippedParamString()
				functionname$ = SE_GetFlippedParamString()
				pluginid = SE_GetFlippedParamString()
				G_SE_ReturnVal = plugin_poke(pluginid,functionname,i_type)
				G_SE_ReturnType = SE_INT
			EndIf
		Case func_plugin_load
			If 0 Then
				G_SE_ReturnVal = plugin_init(SE_GetParamString())
				G_SE_ReturnType = SE_INT
			EndIf
		Case func_int
			SE_ReturnInt(SE_ToIntArg(0))
			Return
		Case func_float
			SE_ReturnFloat(SE_ToFloatArg(0))
			Return
		Case func_str
			SE_ReturnString(SE_ToFloatArg(0))
			Return
		Case func_floor
			SE_ReturnFloat(Floor(SE_ToFloatArg(0)))
			Return
		Case func_ceil
			SE_ReturnFloat(Ceil(SE_ToFloatArg(0)))
			Return
		Case func_sgn
			SE_ReturnInt(Sgn(SE_ToIntArg(0)))
			Return
		Case func_abs
			If SE_ArgType(0)=SE_INT
				SE_ReturnInt(Ceil(SE_IntArg(0)))
			Else
				SE_ReturnFloat(Ceil(SE_ToFloatArg(0)))
			EndIf
			Return
		Case func_sqr
			SE_ReturnFloat(Sqr(SE_ToFloatArg(0)))
			Return
		Case func_sin
			SE_ReturnFloat(Sin(SE_ToFloatArg(0)))
			Return
		Case func_cos
			SE_ReturnFloat(Cos(SE_ToFloatArg(0)))
			Return
		Case func_tan
			SE_ReturnFloat(Tan(SE_ToFloatArg(0)))
			Return
		Case func_asin
			SE_ReturnFloat(ASin(SE_ToFloatArg(0)))
			Return
		Case func_acos
			SE_ReturnFloat(ACos(SE_ToFloatArg(0)))
			Return
		Case func_atan
			SE_ReturnFloat(ATan(SE_ToFloatArg(0)))
			Return
		Case func_atan2
			SE_ReturnFloat(ATan2(SE_ToFloatArg(0), SE_ToFloatArg(1)))
			Return
		Case func_exp
			SE_ReturnFloat(Exp(SE_ToFloatArg(0)))
			Return
		Case func_log
			SE_ReturnFloat(Log(SE_ToFloatArg(0)))
			Return
			
		Case func_log10
			SE_ReturnFloat(Log10(SE_ToFloatArg(0)))
			Return
			
		Case func_rnd
			SE_ReturnFloat(Rnd(SE_ToFloatArg(0), SE_ToFloatArg(1)))
			Return
			
		Case func_rand
			SE_ReturnInt(Rand(SE_ToIntArg(0), SE_ToIntArg(1)))
			Return
			
		Case func_num
			SE_ReturnString(SE_ToStringArg(0))
			Return
			
		Case func_left
			SE_ReturnString(Left(SE_ToStringArg(0), SE_ToIntArg(1)))
			Return
			
		Case func_right
			SE_ReturnString(Right(SE_ToStringArg(0), SE_ToIntArg(1)))
			Return
			
		Case func_mid
			SE_ReturnString(Mid(SE_ToStringArg(0), SE_ToIntArg(1), SE_ToIntArg(2)))
			Return
			
		Case func_replace
			SE_ReturnString(Replace(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToStringArg(2)))
			Return
			
		Case func_instr
			SE_ReturnInt(Instr(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToIntArg(2)))
			Return
			
		Case func_lower
			SE_ReturnString(Lower(SE_ToStringArg(0)))
			Return
		Case func_upper
			SE_ReturnString(Upper(SE_ToStringArg(0)))
			Return
		Case func_trim
			SE_ReturnString(Trim(SE_ToStringArg(0)))
			Return
		Case func_chr
			SE_ReturnString(Chr(SE_ToIntArg(0)))
			Return
		Case func_asc
			SE_ReturnInt(Asc(SE_ToStringArg(0)))
			Return
		Case func_hex
			SE_ReturnString(Hex(SE_ToIntArg(0)))
			Return
		Case func_bin
			SE_ReturnString(Bin(SE_ToIntArg(0)))
			Return
		Case func_repeat
			SE_ReturnString(String(SE_ToStringArg(0), SE_ToIntArg(1)))
			Return
			
		Case func_create
			SE_BL_Array_Create()
			
		Case func_push
			SE_BL_Array_Push()
			
		Case func_pop
			SE_BL_Array_Pop()
			
		Case func_delete
			SE_BL_Array_Delete()
			
		Case func_sort
			SE_BL_Array_Sort()
			
		Case func_from_string
			SE_BL_Array_FromString()
			
		Case func_getfov
			G_SE_ReturnVal = MainFOV
			G_SE_ReturnType = SE_FLOAT
			
		Case func_handleimage
			img = SE_GetParamInt()
			xh = SE_GetParamInt()
			yh = SE_GetParamInt()
			HandleImage img, xh, yh
		Case func_caninteract
			G_SE_ReturnVal = CanInteract()
			G_SE_ReturnType = SE_INT
	End Select
	
	If G_SE_ReturnType = SE_NULL Then 
		G_SE_ReturnType = SE_INT
		G_SE_ReturnVal = 0
	EndIf
	
	Select G_SE_ReturnType
		Case SE_INT
			SE_ReturnInt(G_SE_ReturnVal)
		Case SE_FLOAT
			SE_ReturnFloat(G_SE_ReturnVal)
		Case SE_STRING
			SE_ReturnString(G_SE_ReturnVal)
	End Select
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D