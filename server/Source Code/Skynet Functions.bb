Include "Source Code\SQLite3.bb"

Const LUA_TNONE = -1

Const LUA_TNIL = 0
Const LUA_TBOOLEAN = 1
Const LUA_TLIGHTUSERDATA = 2
Const LUA_TNUMBER = 3
Const LUA_TSTRING = 4
Const LUA_TTABLE = 5
Const LUA_TFUNCTION = 6
Const LUA_TUSERDATA = 7
Const LUA_TTHREAD = 8

Global CurrentParam
Global PublicReturnedValue$

Global InvokeFunctionAddress% = 0

Function SE_GetParamInt%()
	CurrentParam = CurrentParam+1
	Return SE_AND_LUA_ToIntArg(CurrentParam-1)
End Function

Function SE_GetParamFloat#()
	CurrentParam = CurrentParam+1
	Return SE_AND_LUA_ToFloatArg(CurrentParam-1)
End Function

Function SE_GetParamString$()
	CurrentParam = CurrentParam+1
	Return SE_AND_LUA_ToStringArg(CurrentParam-1)
End Function

Function SE_AND_LUA_ToIntArg%(index)
	If CurrentLuaState <> 0 Then
		Return SLUA_TO_INTEGER(CurrentLuaState, index+1)
	Else
		Return SE_ToIntArg(index)
	EndIf
End Function
Function SE_AND_LUA_ToFloatArg#(index)
	If CurrentLuaState <> 0 Then
		Return SLUA_TO_NUMBER(CurrentLuaState, index+1)
	Else
		Return SE_ToFloatArg(index)
	EndIf
End Function
Function SE_AND_LUA_ToStringArg$(index)
	If CurrentLuaState <> 0 Then
		Return SLUA_TO_STRING(CurrentLuaState, index+1)
	Else
		Return SE_ToStringArg(index)
	EndIf
End Function

Function SE_AND_LUA_ReturnInt(value)
	If CurrentLuaState <> 0 Then
		SLUA_PUSH_INTEGER(CurrentLuaState, value)
	Else
		SE_ReturnInt(value)
	EndIf
End Function

Function SE_AND_LUA_ReturnFloat(value#)
	If CurrentLuaState <> 0 Then
		SLUA_PUSH_NUMBER(CurrentLuaState, value)
	Else
		SE_ReturnFloat(value)
	EndIf
End Function

Function SE_AND_LUA_ReturnString(value$)
	If CurrentLuaState <> 0 Then
		SLUA_PUSH_STRING(CurrentLuaState, value)
	Else
		SE_ReturnString(value)
	EndIf
End Function

Const func_setplayervelocity = 1
Const func_getplayervelocity = 2
Const func_setplayerhandcuff = 3
Const func_getplayersteamid = 4
Const func_giveplayerhealth = 5
Const func_setplayerspeedmult = 6
Const func_sendblinktimerforplayer = 7
Const func_zlib_compress = 8
Const func_zlib_uncompress = 9
Const func_deactivatewarheads = 10
Const func_spawnmtf = 11
Const func_activatewarheads = 12
Const func_getplayerzone = 13
Const func_getbreachtimer = 14
Const func_addaccessversion = 15
Const func_getplayerinjuries = 16
Const func_setplayerinjuries = 17
Const func_getplayerheadpitch = 18
Const func_setplayerheadpitch = 19
Const func_createentitycamera = 20
Const func_entityinview = 21
Const func_entityvisible = 22
Const func_entityx = 23
Const func_entityy = 24
Const func_entityz = 25
Const func_entitypitch = 26
Const func_entityyaw = 27
Const func_entityroll = 28
Const func_entitypick = 29
Const func_positionentity = 30
Const func_rotateentity = 31
Const func_moveentity = 32
Const func_scaleentity = 33
Const func_setentitycollision = 34
Const func_removeentity = 35
Const func_setanimtime = 36
Const func_getdelta = 37
Const func_sendrawpacket = 38
Const func_sendscript = 39
Const func_sendfile = 40
Const func_setgamestate = 41
Const func_setservername = 42
Const func_setserverdescription = 43
Const func_setserverpassword = 44
Const func_setservercurrentbreachtimer = 45
Const func_setservernocheat = 46
Const func_getplayerhealth = 47
Const func_setplayerfakehealth = 48
Const func_disableautokick = 49
Const func_reloadapp = 50
Const func_hexstring = 51
Const func_removetimer = 52
Const func_createtimer = 53
Const func_serverwritebyte = 54
Const func_serverwriteshort = 55
Const func_serverwriteint = 56
Const func_serverwritefloat = 57
Const func_debug = 58
Const func_eof = 59
Const func_asc = 60
Const func_chr = 61
Const func_filetype = 62
Const func_filesize = 63
Const func_seekfile = 64
Const func_filepos = 65
Const func_catcherror = 66
Const func_getpacketindex = 67
Const func_sendrawdata = 68
Const func_writebytes = 69
Const func_readbytes = 70
Const func_createbank = 71
Const func_freebank = 72
Const func_banksize = 73
Const func_resizebank = 74
Const func_copybank = 75
Const func_peekbyte = 76
Const func_peekshort = 77
Const func_peekint = 78
Const func_peekfloat = 79
Const func_pokebyte = 80
Const func_pokeshort = 81
Const func_pokeint = 82
Const func_pokefloat = 83
Const func_disablelobby = 84
Const func_removevar = 85
Const func_setvarvalue = 86
Const func_getvarvalue = 87
Const func_removeplayervar = 88
Const func_setplayervarvalue = 89
Const func_getplayervarvalue = 90
Const func_addarrayelements = 91
Const func_getobjectentity = 92
Const func_setobjectvisibleforplayer = 93
Const func_setplayersspawnposition = 94
Const func_setplayerfakeradiowave = 95
Const func_getplayerholdinggun = 96
Const func_getplayerradiowave = 97
Const func_getplayerroomid = 98
Const func_getplayernvg = 99
Const func_getplayeranimation = 100
Const func_getplayerhazmat = 101
Const func_getplayervest = 102
Const func_getplayerreadystate = 103
Const func_getplayerblinktimer = 104
Const func_callglobalfunction = 105
Const func_callfunction = 106
Const func_movex = 107
Const func_movey = 108
Const func_movez = 109
Const func_move = 110
Const func_pointangle = 111
Const func_pointpitch = 112
Const func_pointat = 113
Const func_getplayertype = 114
Const func_createsound = 115
Const func_createplayersound = 116
Const func_getplayerentity = 117
Const func_setnotarget = 118
Const func_setcontained106 = 119
Const func_setremotedooron = 120
Const func_setmtftimer = 121
Const func_getroomname = 122
Const func_getroomentity = 123
Const func_isvalidroom = 124
Const func_getroomobjectentity = 125
Const func_setdooropenstate = 126
Const func_setdoorlock = 127
Const func_getdoorentity = 128
Const func_getdoortype = 129
Const func_getdooropenstate = 130
Const func_getdoorlock = 131
Const func_isvaliddoor = 132
Const func_setitempicker = 133
Const func_getitemtemplatename = 134
Const func_getitemtemplatetempname = 135
Const func_getitemtemplate = 136
Const func_createitem = 137
Const func_removeitem = 138
Const func_getitementity = 139
Const func_getitempicker = 140
Const func_isvaliditem = 141
Const func_isvalidnpc = 142
Const func_createnpc = 143
Const func_getnpctype = 144
Const func_getnpcstate1 = 145
Const func_getnpcstate2 = 146
Const func_getnpcstate3 = 147
Const func_setnpcstate = 148
Const func_getnpcevent = 149
Const func_getnpcentity = 150
Const func_geteventindex = 151
Const func_geteventstate1 = 152
Const func_geteventstate2 = 153
Const func_geteventstate3 = 154
Const func_geteventstr = 155
Const func_isvalidevent = 156
Const func_removeevent = 157
Const func_seteventstr = 158
Const func_seteventstate = 159
Const func_rconcommand = 160
Const func_createrocket = 161
Const func_closefile = 162
Const func_readline = 163
Const func_readint = 164
Const func_readfloat = 165
Const func_readshort = 166
Const func_readbyte = 167
Const func_writeline = 168
Const func_writeint = 169
Const func_writefloat = 170
Const func_writeshort = 171
Const func_writebyte = 172
Const func_openfile = 173
Const func_readfile = 174
Const func_writefile = 175
Const func_setlightvolume = 176
Const func_getlightvolume = 177
Const func_setserverversion = 178
Const func_getserverversion = 179
Const func_getplayerversion = 180
Const func_setplayerafk = 181
Const func_getplayerafk = 182
Const func_setplayertextpos = 183
Const func_setplayertextstring = 184
Const func_setplayertextcolor = 185
Const func_setplayerdrawcolor = 186
Const func_setplayerdrawpos = 187
Const func_millisecs = 188
Const func_isplayerconnected = 189
Const func_setplayerfogrange = 190
Const func_restartserver = 191
Const func_getplayermonitorwidth = 192
Const func_getplayermonitorheight = 193
Const func_setmapseed = 194
Const func_removeobject = 195
Const func_createobject = 196
Const func_removeplayertext = 197
Const func_createplayertext = 198
Const func_removeplayerdraw = 199
Const func_createplayerdraw = 200
Const func_setplayertype = 201
Const func_getplayerip = 202
Const func_getplayerping = 203
Const func_getplayernickname = 204
Const func_shoot = 205
Const func_setplayerping = 206
Const func_isafakeplayer = 207
Const func_createfakeplayer = 208
Const func_getplayerloadstate = 209
Const func_getplayerdeadstate = 210
Const func_setplayerfakedeadstate = 211
Const func_setplayerfakeholdinggun = 212
Const func_setplayerfakenvg = 213
Const func_setplayerfakegasmask = 214
Const func_setplayerfakevest = 215
Const func_setplayerfakeanimation = 216
Const func_setplayerfakehazmat = 217
Const func_setplayerfakeblinktimer = 218
Const func_setplayerposition = 219
Const func_setplayerangle = 220
Const func_setplayermessage = 221
Const func_giveadmin = 222
Const func_removeadmin = 223
Const func_isplayeradmin = 224
Const func_servermessage = 225
Const func_playerconsolecommand = 226
Const func_playplayersound = 227
Const func_playsound = 228
Const func_sendmessage = 229
Const func_kick = 230
Const func_banip = 231
Const func_plugin_remove = 232
Const func_plugin_call = 233
Const func_plugin_poke = 234
Const func_plugin_load = 235
Const func_delay = 236
Const func_print = 237
Const func_getinivalue = 238
Const func_putinivalue = 239
Const func_getunixtime = 240
Const func_updateinifile = 241
Const func_int = 242
Const func_float = 243
Const func_str = 244
Const func_floor = 245
Const func_ceil = 246
Const func_sgn = 247
Const func_abs = 248
Const func_sqr = 249
Const func_sin = 250
Const func_cos = 251
Const func_tan = 252
Const func_asin = 253
Const func_acos = 254
Const func_atan = 255
Const func_atan2 = 256
Const func_exp = 257
Const func_log = 258
Const func_log10 = 259
Const func_rnd = 260
Const func_rand = 261
Const func_num = 262
Const func_left = 263
Const func_right = 264
Const func_mid = 265
Const func_replace = 266
Const func_instr = 267
Const func_lower = 268
Const func_upper = 269
Const func_trim = 270
Const func_hex = 274
Const func_bin = 275
Const func_repeat = 276
Const func_array_create = 277
Const func_array_push = 278
Const func_array_pop = 279
Const func_array_delete = 280
Const func_array_sort = 281
Const func_array_from_string = 282
Const func_changeplayername = 283
Const func_changeplayertag = 284
Const func_isplayerpatron = 285
Const func_getincomingconnectionversion = 286
Const func_getincomingconnectionpatron = 287
Const func_setmtftickets = 288
Const func_setchaostickets = 289
Const func_getmtftickets = 290
Const func_getchaostickets = 291
Const func_activatefilessending = 292
Const func_setserverexplodetimeout = 293
Const func_enableauthkeyonconnect = 294
Const func_getserverexplodetimeout = 295
Const func_getserverspawntimeout = 296
Const func_setserverspawntimeout = 297

; sqlite region
Const func_sqlite_OpenDatabase = 298
Const func_sqlite_CloseDatabase = 299
Const func_sqlite_SetDatabaseTimeout = 300
Const func_sqlite_DatabaseVersion = 301
Const func_sqlite_LastRowIDInserted = 302
Const func_sqlite_RowsChangedByLastStatement = 303
Const func_sqlite_RowsChangedThisSession = 304
Const func_sqlite_AutoCommitIsOn = 305
Const func_sqlite_BeginTransaction = 306
Const func_sqlite_CommitTransaction = 307
Const func_sqlite_RollbackTransaction = 308
Const func_sqlite_LastDatabaseErrorCode = 309
Const func_sqlite_LastDatabaseErrorMessage = 310
Const func_sqlite_InterruptDatabase = 311
Const func_sqlite_ExecuteSQL = 312
Const func_sqlite_PrepareSQL = 313
Const func_sqlite_GetNextDataRow = 314
Const func_sqlite_FinaliseSQL = 315
Const func_sqlite_ResetSQL = 316
Const func_sqlite_SQLHasExpired = 317
Const func_sqlite_GetDatabaseHandleFromStatementHandle = 318
Const func_sqlite_GetColumnCount = 319
Const func_sqlite_GetColumnName = 320
Const func_sqlite_GetColumnType = 321
Const func_sqlite_GetColumnDeclaredType = 322
Const func_sqlite_GetColumnSize = 323
Const func_sqlite_GetColumnValueAsInteger = 324
Const func_sqlite_GetColumnValueAsFloat = 325
Const func_sqlite_GetColumnValueAsString = 326
Const func_sqlite_GetColumnValueAsBlob = 327
Const func_sqlite_SQLParameterCount = 328
Const func_sqlite_SQLParameterName = 329
Const func_sqlite_SQLParameterIndex = 330
Const func_sqlite_BindSQLParameterAsNull = 331
Const func_sqlite_BindSQLParameterAsInteger = 332
Const func_sqlite_BindSQLParameterAsFloat = 333
Const func_sqlite_BindSQLParameterAsString = 334
Const func_sqlite_BindSQLParameterAsBlob = 335
Const func_sqlite_TransferSQLBindings = 336
Const func_sqlite_SQLite3_ErrorHasOccurred = 336
;

; mysql region
Const func_mysql_OpenSQLStream = 337
Const func_mysql_SQLConnected = 338
Const func_mysql_SQLQuery = 339
Const func_mysql_SQLRowCount = 340
Const func_mysql_SQLFetchRow = 341
Const func_mysql_SQLFieldCount = 342
Const func_mysql_ReadSQLField = 343
Const func_mysql_ReadSQLFieldIndex = 344
Const func_mysql_FreeSQLQuery = 345
Const func_mysql_FreeSQLRow = 346
Const func_mysql_CloseSQLStream = 347
;

Const func_closeapp = 348
Const func_setplayermute = 349
Const func_getplayermute = 350
Const func_enableoldresponse = 351
Const func_geteventname = 352
Const func_geteventroomid = 353
Const func_getmaxspawnplayers = 354
Const func_setmaxspawnplayers = 355
Const func_setdescriptionline = 356
Const func_getdescriptionline = 357
Const func_createsteaminstance = 358
Const func_getsteaminstancetag = 359
Const func_removesteaminstance = 360
Const func_connecttocentralserver = 361
Const func_reconnecttocentralserver = 362

;

Const func_dllfunctionvoid_0 = 363
Const func_dllfunctionvoid_1 = 364
Const func_dllfunctionvoid_2 = 365
Const func_dllfunctionvoid_3 = 366
Const func_dllfunctionvoid_4 = 367
Const func_dllfunctionvoid_5 = 368
Const func_dllfunctionvoid_6 = 369
Const func_dllfunctionvoid_7 = 370
Const func_dllfunctionvoid_8 = 371
Const func_dllfunctionvoid_9 = 372

Const func_dllfunctionint_0 = 373
Const func_dllfunctionint_1 = 374
Const func_dllfunctionint_2 = 375
Const func_dllfunctionint_3 = 376
Const func_dllfunctionint_4 = 377
Const func_dllfunctionint_5 = 378
Const func_dllfunctionint_6 = 379
Const func_dllfunctionint_7 = 380
Const func_dllfunctionint_8 = 381
Const func_dllfunctionint_9 = 382

Const func_dllfunctionfloat_0 = 383
Const func_dllfunctionfloat_1 = 384
Const func_dllfunctionfloat_2 = 385
Const func_dllfunctionfloat_3 = 386
Const func_dllfunctionfloat_4 = 387
Const func_dllfunctionfloat_5 = 388
Const func_dllfunctionfloat_6 = 389
Const func_dllfunctionfloat_7 = 390
Const func_dllfunctionfloat_8 = 391
Const func_dllfunctionfloat_9 = 392

Const func_dllfunctionstring_0 = 393
Const func_dllfunctionstring_1 = 394
Const func_dllfunctionstring_2 = 395
Const func_dllfunctionstring_3 = 396
Const func_dllfunctionstring_4 = 397
Const func_dllfunctionstring_5 = 398
Const func_dllfunctionstring_6 = 399
Const func_dllfunctionstring_7 = 400
Const func_dllfunctionstring_8 = 401
Const func_dllfunctionstring_9 = 402
;

Const func_lerp = 403
Const func_lerpangle = 404
Const func_changeplayersize = 405
Const func_getplayersize = 406

Const func_getroomdoor = 407
Const func_isvalidroomdoor = 408
Const func_ShouldPlayerAnnouncement = 409
Const func_setobjectinterpolation = 410
Const func_preparemodelidentifier = 411
Const func_updateobjects = 412

Const func_getplayerspeedmult = 414

Const func_addplayerspawnposition = 415
Const func_addclassspawnposition = 416
Const func_removespawnposition = 417
Const func_isvalidspawnposition = 418
Const func_changeplayersteamid = 419
Const func_setplayerfakeroom = 420
Const func_getplayeroriginalx = 421
Const func_getplayeroriginaly = 422
Const func_getplayeroriginalz = 423

Const func_resetentity = 424
Const func_entityradius = 425
Const func_entitybox = 426
Const func_entitycollisiontype = 427
Const func_entitypickmode = 428
Const func_entitycollided = 429
Const func_countcollisions = 430
Const func_collisionx = 431
Const func_collisiony = 432
Const func_collisionz = 433
Const func_collisionnx = 434
Const func_collisionny = 435
Const func_collisionnz = 436
Const func_collisionentity = 437
Const func_getentitycollisiontype = 438
Const func_deltayaw = 439
Const func_deltapitch = 440
Const func_getplayercamera = 441
Const func_entitydistance = 442
Const func_getplayerhitbox = 443
Const func_pointentity = 444
Const func_setcustommap = 445

Const func_entityscalex = 446
Const func_entityscaley = 447
Const func_entityscalez = 448
Const func_meshwidth = 449
Const func_meshheight = 450
Const func_meshdepth = 451

Const func_enablenoclipanticheat = 452
Const func_enablecamerashakeondamage = 453

Const func_createsprite = 454
Const func_loadsprite = 455
Const func_spriteviewmode = 456
Const func_scalesprite = 457
Const func_entityfx = 458
Const func_entityblend = 459
Const func_showentity = 460
Const func_hideentity = 461
Const func_getparent = 462
Const func_entityautofade = 463
Const func_entitycolor = 464
Const func_entityshininess = 465
Const func_entityalpha = 466
Const func_entitytexture = 467
Const func_createtexture = 468
Const func_loadtexture = 469
Const func_scaletexture = 470
Const func_texturewidth = 471
Const func_textureheight = 472
Const func_entityparent = 473

Const func_TCP_Get = 474
Const func_TCP_Close = 475

Const func_createobjectsound = 476
Const func_createobjectsoundforplayer = 477

Const func_setplayerpositionid = 478
Const func_turnentity = 479

Const func_setplayerspeedhackmult = 480
Const func_getplayerspeedhackmult = 481

Const func_setplayernoclip = 482
Const func_getplayernoclip = 483

Const func_createpivot = 484
Const func_getplayerdownloadingcount = 485
Const func_isplayerdownloadingfile = 486

Const func_createcube = 487
Const func_createsphere = 488
Const func_createcylinder = 489
Const func_createcone = 490
Const func_ExecuteServerConsole = 491
Const func_setdoorkeycard = 492
Const func_getplayerselecteditem = 493
Const func_getdoorkeycard = 494
Const func_createevent = 495
Const func_spawnchaos = 496
Const func_getplayerhandcuff = 497
Const func_gettime = 498
Const func_getdate = 499
Const func_getdoorcode = 500
Const func_setdoorcode = 501
Const func_createerror = 502
Const func_enableintercom = 503
Const func_setintercomtimeout = 504
Const func_setintercomusingtime = 505
Const func_setplayerintercom = 506
Const func_setplayerintercomusingtime = 507
Const func_setplayerintercomtimeout = 508
Const func_isplayerusingintercom = 508
Const func_createitemtemplate = 509

Const func_breachrole_CreatePlayerRole = 510
Const func_breachrole_GetRoleName = 511
Const func_breachrole_SetRoleSettings = 512
Const func_breachrole_SetRoleEffects = 513
Const func_breachrole_SetRoleAmbientSound = 514
Const func_breachrole_SetRoleInstruction = 515
Const func_breachrole_SetRoleDeadAnimation = 516
Const func_breachrole_SetRoleHitboxScales = 517
Const func_breachrole_SetRoleBone = 518
Const func_breachrole_SetRoleAnimation = 519
Const func_breachrole_SetRoleArmedAnimation = 520
Const func_breachrole_MarkAsFriend = 521
Const func_breachrole_RoleTakeRoleSpawn = 522
Const func_breachrole_SetRolePositionsOffset = 523
Const func_breachrole_SetRoleHoldingGrenade = 524
Const func_breachrole_SetRoleHoldingItem = 525
Const func_breachrole_SetRoleHandcuff = 526
Const func_breachrole_AllowRoleWeaponAttaches = 527
Const func_breachrole_AllowItemsAttaches = 528
Const func_breachrole_MarkRoleAsSCP = 529
Const func_breachrole_MarkAs035 = 530
Const func_breachrole_MarkAs049 = 531
Const func_breachrole_IsA035 = 532
Const func_breachrole_IsA049 = 533
Const func_breachrole_IsAFriend = 534
Const func_breachrole_GetMaxHP = 535
Const func_breachrole_GetRoleScale = 536
Const func_sethalloweenmode = 537
Const func_setchristmasmode = 538
Const func_breachrole_SetMaxRoleCount = 539
Const func_breach_role_setrolecategory = 540
Function SE_IsValidFunction(functionname$)
	Select FunctionName$
		Case "breachrole_setroleviewmodelanimation": Return 1
		Case "breachrole_createviewmodelforrole": Return 1
		Case "redirectsound": Return 1
		Case "breachrole_setmaxrolecount": Return 1
		Case "sethalloweenmode": Return 1
		Case "setchristmasmode": Return 1
		Case "breachrole_createplayerrole": Return 1
		Case "breachrole_getrolename": Return 1
		Case "breachrole_setrolesettings": Return 1
		Case "breachrole_setroleeffects": Return 1
		Case "breachrole_setroleambientsound": Return 1
		Case "breachrole_setroleinstruction": Return 1
		Case "breachrole_setroledeadanimation": Return 1
        Case "breachrole_setrolehitboxscales": Return 1
		Case "breachrole_setrolebone": Return 1
		Case "breachrole_setroleanimation": Return 1
		Case "breachrole_setrolearmedanimation": Return 1
		Case "breachrole_markasfriend": Return 1
		Case "breachrole_roletakerolespawn": Return 1
		Case "breachrole_setrolepositionsoffset": Return 1
		Case "breachrole_setroleholdinggrenade": Return 1
		Case "breachrole_setroleholdingitem": Return 1
		Case "breachrole_setrolehandcuff": Return 1
		Case "breachrole_allowroleweaponattaches": Return 1
		Case "breachrole_allowitemsattaches": Return 1
		Case "breachrole_markroleasscp": Return 1
		Case "breachrole_markas035": Return 1
		Case "breachrole_markas049": Return 1
		Case "breachrole_isa035": Return 1
		Case "breachrole_isa049": Return 1
		Case "breachrole_isafriend": Return 1
		Case "breachrole_getmaxhp": Return 1
		Case "breachrole_getrolescale": Return 1
		Case "breachrole_setrolecategory": Return 1
        Case "createdecal": Return 1
		
		Case "changeroommesh": Return 1
		Case "enableintercom": Return 1
		Case "setintercomtimeout": Return 1
		Case "setintercomusingtime": Return 1
		Case "setplayerintercom": Return 1
		Case "setplayerintercomusingtime": Return 1
		Case "setplayerintercomtimeout": Return 1
		Case "isplayerusingintercom": Return 1
		
		Case "createerror": Return 1
		Case "gettime": Return 1
		Case "getdate": Return 1
		Case "setradio": Return 1
		Case "createevent": Return 1
		Case "getplayerselecteditem": Return 1
		Case "setdoorcode": Return 1
		Case "getdoorcode": Return 1
		Case "setdoorkeycard": Return 1
		Case "getdoorkeycard": Return 1
		Case "executeserverconsole": Return 1
		Case "createcube": Return 1
		Case "createsphere": Return 1
		Case "createcylinder": Return 1
		Case "createcone": Return 1
		Case "camerafogrange": Return 1
		Case "camerarange": Return 1
		Case "getplayerdownloadingcount": Return 1
		Case "isplayerdownloadingfile": Return 1
		Case "setmousehit1": Return 1
		Case "setmousedown1": Return 1
		Case "gettabmenu": Return 1
		Case "setplayernoclip"
			Return 1
		Case "getplayernoclip"
			Return 1
		Case "setplayerspeedhackmult": Return 1
		Case "getplayerspeedhackmult": Return 1
		Case "turnentity": Return 1
		Case "formattext": Return 1
		Case "stringwidth": Return 1
		Case "stringheight": Return 1
		Case "texturewidth": Return 1
		Case "textureheight": Return 1
		Case "imagewidth": Return 1
		Case "imageheight": Return 1
		Case "scaleimage": Return 1
		Case "isvalidobject": Return 1
		Case "drawblock": Return 1
		Case "browser_create": Return 1
		Case "browser_destroy": Return 1
		Case "browser_getimage": Return 1
		Case "browser_gettexture": Return 1
		Case "browser_mousedown": Return 1
		Case "browser_mouseup": Return 1
		Case "browser_setmousepos": Return 1
		Case "browser_loadurl": Return 1
		Case "browser_isready": Return 1

		Case "createobjectsound": Return 1
		Case "createobjectsoundforplayer": Return 1
		Case "tcp_get": Return 1
		Case "tcp_close": Return 1
		Case "createsprite": Return 1
		Case "loadsprite": Return 1
		Case "spriteviewmode": Return 1
		Case "scalesprite": Return 1
		Case "entityfx": Return 1
		Case "entityblend": Return 1
		Case "showentity": Return 1
		Case "hideentity": Return 1
		Case "getparent": Return 1
		Case "entityautofade": Return 1
		Case "entitycolor": Return 1
		Case "entityshininess": Return 1
		Case "entityalpha": Return 1
		Case "entitytexture": Return 1
		Case "createtexture": Return 1
		Case "loadtexture": Return 1
		Case "scaletexture": Return 1
		Case "texturewidth": Return 1
		Case "textureheight": Return 1
		Case "entityparent": Return 1
		
		Case "enablenoclipanticheat": Return True
		Case "enablecamerashakeondamage": Return True
		Case "pointentity": Return True
		Case "getplayerhitbox": Return True
		Case "deltayaw": Return True
		Case "deltapitch:" Return True
		;
		Case "getplayerentity": Return True
		Case "getplayerhitboxentity": Return True
		Case "getplayermodelentity": Return True
		Case "getcurrentspectateplayer": Return True
		Case "getservertimeout": Return True
		; coll
		Case "resetentity": Return True
		Case "entityradius": Return True
		Case "entitybox": Return True
		Case "entitycollisiontype": Return True
		Case "entitypickmode": Return True
		Case "entitycollided": Return True
		Case "countcollisions": Return True
		Case "collisionx": Return True
		Case "collisiony": Return True
		Case "collisionz": Return True
		Case "collisionnx": Return True
		Case "collisionny": Return True
		Case "collisionnz": Return True
		Case "collisionentity": Return True
		Case "getentitycollisiontype": Return True
		;
		Case "getplayeroriginalx": Return True
		Case "getplayeroriginaly": Return True
		Case "getplayeroriginalz": Return True
		Case "setplayerfakeroom": Return True
		Case "getplayerhandcuff": Return True
		Case "changeplayersteamid": Return True
		Case "getplayerspeedmult": Return True
		Case "updateobjects": Return True
		Case "preparemodelidentifier": Return True
		Case "setobjectinterpolation": Return True
		Case "drawuiinputbox": Return True
		Case "getkey": Return True
		Case "isvalidroomdoor": Return True
		Case "getroomdoor": Return True
		Case "changeplayersize": Return True
		Case "getplayersize": Return True
		;
		Case "dllfunctionvoid_0": Return True
		Case "dllfunctionvoid_1": Return True
		Case "dllfunctionvoid_2": Return True
		Case "dllfunctionvoid_3": Return True
		Case "dllfunctionvoid_4": Return True
		Case "dllfunctionvoid_5": Return True
		Case "dllfunctionvoid_6": Return True
		Case "dllfunctionvoid_7": Return True
		Case "dllfunctionvoid_8": Return True
		Case "dllfunctionvoid_9": Return True

		Case "dllfunctionint_0": Return True
		Case "dllfunctionint_1": Return True
		Case "dllfunctionint_2": Return True
		Case "dllfunctionint_3": Return True
		Case "dllfunctionint_4": Return True
		Case "dllfunctionint_5": Return True
		Case "dllfunctionint_6": Return True
		Case "dllfunctionint_7": Return True
		Case "dllfunctionint_8": Return True
		Case "dllfunctionint_9": Return True

		Case "dllfunctionfloat_0": Return True
		Case "dllfunctionfloat_1": Return True
		Case "dllfunctionfloat_2": Return True
		Case "dllfunctionfloat_3": Return True
		Case "dllfunctionfloat_4": Return True
		Case "dllfunctionfloat_5": Return True
		Case "dllfunctionfloat_6": Return True
		Case "dllfunctionfloat_7": Return True
		Case "dllfunctionfloat_8": Return True
		Case "dllfunctionfloat_9": Return True

		Case "dllfunctionstring_0": Return True
		Case "dllfunctionstring_1": Return True
		Case "dllfunctionstring_2": Return True
		Case "dllfunctionstring_3": Return True
		Case "dllfunctionstring_4": Return True
		Case "dllfunctionstring_5": Return True
		Case "dllfunctionstring_6": Return True
		Case "dllfunctionstring_7": Return True
		Case "dllfunctionstring_8": Return True
		Case "dllfunctionstring_9": Return True
		
		Case "connecttocentralserver": Return True
		Case "reconnecttocentralserver": Return True
		Case "createsteaminstance": Return True
		Case "getsteaminstancetag": Return True
		Case "removesteaminstance": Return True
		Case "setdescriptionline": Return True
		Case "getdescriptionline": Return True
		Case "setmaxspawnplayers": Return True
		Case "getmaxspawnplayers": Return True
		Case "geteventname": Return True
		Case "geteventroomid": Return True
		Case "enableoldresponse": Return True ;func_enableoldresponse
		Case "closeapp": Return True ;func_closeapp
		Case "getplayermute": Return True ;func_getplayermute
		Case "setplayermute": Return True ;func_setplayermute
		; Sqlite
		Case "opendatabase": Return True ;func_sqlite_opendatabase
		Case "closedatabase": Return True ;func_sqlite_closedatabase
		Case "setdatabasetimeout": Return True ;func_sqlite_setdatabasetimeout
		Case "databaseversion": Return True ;func_sqlite_databaseversion
		Case "lastrowidinserted": Return True ;func_sqlite_lastrowidinserted
		Case "rowschangedbylaststatement": Return True ;func_sqlite_rowschangedbylaststatement
		Case "rowschangedthissession": Return True ;func_sqlite_rowschangedthissession
		Case "autocommitison": Return True ;func_sqlite_autocommitison
		Case "begintransaction": Return True ;func_sqlite_begintransaction
		Case "committransaction": Return True ;func_sqlite_committransaction
		Case "rollbacktransaction": Return True ;func_sqlite_rollbacktransaction
		Case "lastdatabaseerrorcode": Return True ;func_sqlite_lastdatabaseerrorcode
		Case "lastdatabaseerrormessage": Return True ;func_sqlite_lastdatabaseerrormessage
		Case "interruptdatabase": Return True ;func_sqlite_interruptdatabase
		Case "executesql": Return True ;func_sqlite_executesql
		Case "preparesql": Return True ;func_sqlite_preparesql
		Case "getnextdatarow": Return True ;func_sqlite_getnextdatarow
		Case "finalisesql": Return True ;func_sqlite_finalisesql
		Case "resetsql": Return True ;func_sqlite_resetsql
		Case "sqlhasexpired": Return True ;func_sqlite_sqlhasexpired
		Case "getdatabasehandlefromstatementhandle": Return True ;func_sqlite_getdatabasehandlefromstatementhandle
		Case "getcolumncount": Return True ;func_sqlite_getcolumncount
		Case "getcolumnname": Return True ;func_sqlite_getcolumnname
		Case "getcolumntype": Return True ;func_sqlite_getcolumntype
		Case "getcolumndeclaredtype": Return True ;func_sqlite_getcolumndeclaredtype
		Case "getcolumnsize": Return True ;func_sqlite_getcolumnsize
		Case "getcolumnvalueasinteger": Return True ;func_sqlite_getcolumnvalueasinteger
		Case "getcolumnvalueasfloat": Return True ;func_sqlite_getcolumnvalueasfloat
		Case "getcolumnvalueasstring": Return True ;func_sqlite_getcolumnvalueasstring
		Case "getcolumnvalueasblob": Return True ;func_sqlite_getcolumnvalueasblob
		Case "sqlparametercount": Return True ;func_sqlite_sqlparametercount
		Case "sqlparametername": Return True ;func_sqlite_sqlparametername
		Case "sqlparameterindex": Return True ;func_sqlite_sqlparameterindex
		Case "bindsqlparameterasnull": Return True ;func_sqlite_bindsqlparameterasnull
		Case "bindsqlparameterasinteger": Return True ;func_sqlite_bindsqlparameterasinteger
		Case "bindsqlparameterasfloat": Return True ;func_sqlite_bindsqlparameterasfloat
		Case "bindsqlparameterasstring": Return True ;func_sqlite_bindsqlparameterasstring
		Case "bindsqlparameterasblob": Return True ;func_sqlite_bindsqlparameterasblob
		Case "transfersqlbindings": Return True ;func_sqlite_transfersqlbindings
		Case "sqlite3_errorhasoccurred": Return True ;func_sqlite_sqlite3_errorhasoccurred
		; ==========================================================================
		
		; mysql
		Case "opensqlstream": Return True ;func_mysql_opensqlstream
		Case "sqlconnected": Return True ;func_mysql_sqlconnected
		Case "sqlquery": Return True ;func_mysql_sqlquery
		Case "sqlrowcount": Return True ;func_mysql_sqlrowcount
		Case "sqlfetchrow": Return True ;func_mysql_sqlfetchrow
		Case "sqlfieldcount": Return True ;func_mysql_sqlfieldcount
		Case "readsqlfield": Return True ;func_mysql_readsqlfield
		Case "readsqlfieldindex": Return True ;func_mysql_readsqlfieldindex
		Case "freesqlquery": Return True ;func_mysql_freesqlquery
		Case "freesqlrow": Return True ;func_mysql_freesqlrow
		Case "closesqlstream": Return True ;func_mysql_closesqlstream
		; ==========================================================================
		Case "getserverexplodetimeout"
			Return True ;func_getserverexplodetimeout
		Case "getserverspawntimeout"
			Return True ;func_getserverspawntimeout
		Case "setserverspawntimeout"
			Return True ;func_setserverspawntimeout
		Case "enableauthkeyonconnect"
			Return True ;func_enableauthkeyonconnect
		Case "setplayervelocity"
			Return True ;func_setplayervelocity
		Case "getplayervelocity"
			Return True ;func_getplayervelocity
		Case "setplayerhandcuff"
			Return True ;func_setplayerhandcuff
		Case "getplayersteamid"
			Return True ;func_getplayersteamid
		Case "giveplayerhealth"
			Return True ;func_giveplayerhealth
		Case "setplayerspeedmult"
			Return True ;func_setplayerspeedmult
		Case "sendblinktimerforplayer"
			Return True ;func_sendblinktimerforplayer
		Case "zlib_compress"
			Return True ;func_zlib_compress
		Case "zlib_uncompress"
			Return True ;func_zlib_uncompress
		Case "deactivatewarheads"
			Return True ;func_deactivatewarheads
		Case "spawnmtf"
			Return True ;func_spawnmtf
		Case "activatewarheads"
			Return True ;func_activatewarheads
		Case "getplayerzone"
			Return True ;func_getplayerzone
		Case "getbreachtimer"
			Return True ;func_getbreachtimer
		Case "addaccessversion"
			Return True ;func_addaccessversion
		Case "getplayerinjuries"
			Return True ;func_getplayerinjuries
		Case "setplayerinjuries"
			Return True ;func_setplayerinjuries
		Case "getplayerheadpitch"
			Return True ;func_getplayerheadpitch
		Case "setplayerheadpitch"
			Return True ;func_setplayerheadpitch
		Case "createentitycamera"
			Return True ;func_createentitycamera
		Case "entityinview"
			Return True ;func_entityinview
		Case "entityvisible"
			Return True ;func_entityvisible
		Case "entityx"
			Return True ;func_entityx
		Case "entityy"
			Return True ;func_entityy
		Case "entityz"
			Return True ;func_entityz
		Case "entitypitch"
			Return True ;func_entitypitch
		Case "entityyaw"
			Return True ;func_entityyaw
		Case "entityroll"
			Return True ;func_entityroll
		Case "entitypick"
			Return True ;func_entitypick
		Case "positionentity"
			Return True ;func_positionentity
		Case "rotateentity"
			Return True ;func_rotateentity
		Case "moveentity"
			Return True ;func_moveentity
		Case "scaleentity"
			Return True ;func_scaleentity
		Case "setentitycollision"
			Return True ;func_setentitycollision
		Case "removeentity"
			Return True ;func_removeentity
		Case "setanimtime"
			Return True ;func_setanimtime
		Case "getdelta"
			Return True ;func_getdelta
		Case "sendrawpacket"
			Return True ;func_sendrawpacket
		Case "sendscript"
			Return True ;func_sendscript
		Case "sendfile"
			Return True ;func_sendfile
		Case "setgamestate"
			Return True ;func_setgamestate
		Case "setservername"
			Return True ;func_setservername
		Case "setserverdescription"
			Return True ;func_setserverdescription
		Case "setserverpassword"
			Return True ;func_setserverpassword
		Case "setservercurrentbreachtimer"
			Return True ;func_setservercurrentbreachtimer
		Case "setservernocheat"
			Return True ;func_setservernocheat
		Case "getplayerhealth"
			Return True ;func_getplayerhealth
		Case "setplayerfakehealth"
			Return True ;func_setplayerfakehealth
		Case "disableautokick"
			Return True ;func_disableautokick
		Case "reloadapp"
			Return True ;func_reloadapp
		Case "hexstring"
			Return True ;func_hexstring
		Case "removetimer"
			Return True ;func_removetimer
		Case "createtimer"
			Return True ;func_createtimer
		Case "serverwritebyte"
			Return True ;func_serverwritebyte
		Case "serverwriteshort"
			Return True ;func_serverwriteshort
		Case "serverwriteint"
			Return True ;func_serverwriteint
		Case "serverwritefloat"
			Return True ;func_serverwritefloat
		Case "debug"
			Return True ;func_debug
		Case "eof"
			Return True ;func_eof
		Case "asc"
			Return True ;func_asc
		Case "chr"
			Return True ;func_chr
		Case "filetype"
			Return True ;func_filetype
		Case "filesize"
			Return True ;func_filesize
		Case "seekfile"
			Return True ;func_seekfile
		Case "filepos"
			Return True ;func_filepos
		Case "catcherror"
			Return True ;func_catcherror
		Case "getpacketindex"
			Return True ;func_getpacketindex
		Case "sendrawdata"
			Return True ;func_sendrawdata
		Case "writebytes"
			Return True ;func_writebytes
		Case "readbytes"
			Return True ;func_readbytes
		Case "createbank"
			Return True ;func_createbank
		Case "freebank"
			Return True ;func_freebank
		Case "banksize"
			Return True ;func_banksize
		Case "resizebank"
			Return True ;func_resizebank
		Case "copybank"
			Return True ;func_copybank
		Case "peekbyte"
			Return True ;func_peekbyte
		Case "peekshort"
			Return True ;func_peekshort
		Case "peekint"
			Return True ;func_peekint
		Case "peekfloat"
			Return True ;func_peekfloat
		Case "pokebyte"
			Return True ;func_pokebyte
		Case "pokeshort"
			Return True ;func_pokeshort
		Case "pokeint"
			Return True ;func_pokeint
		Case "pokefloat"
			Return True ;func_pokefloat
		Case "disablelobby"
			Return True ;func_disablelobby
		Case "removevar"
			Return True ;func_removevar
		Case "setvarvalue"
			Return True ;func_setvarvalue
		Case "getvarvalue"
			Return True ;func_getvarvalue
		Case "removeplayervar"
			Return True ;func_removeplayervar
		Case "setplayervarvalue"
			Return True ;func_setplayervarvalue
		Case "getplayervarvalue"
			Return True ;func_getplayervarvalue
		Case "addarrayelements"
			Return True ;func_addarrayelements
		Case "getobjectentity"
			Return True ;func_getobjectentity
		Case "setobjectvisibleforplayer"
			Return True ;func_setobjectvisibleforplayer
		Case "setplayersspawnposition"
			Return True ;func_setplayersspawnposition
		Case "setplayerfakeradiowave"
			Return True ;func_setplayerfakeradiowave
		Case "getplayerholdinggun"
			Return True ;func_getplayerholdinggun
		Case "getplayerradiowave"
			Return True ;func_getplayerradiowave
		Case "getplayerroomid"
			Return True ;func_getplayerroomid
		Case "getplayernvg"
			Return True ;func_getplayernvg
		Case "getplayeranimation"
			Return True ;func_getplayeranimation
		Case "getplayerhazmat"
			Return True ;func_getplayerhazmat
		Case "getplayergasmask"
			Return True ;func_getplayergasmask
		Case "getplayervest"
			Return True ;func_getplayervest
		Case "getplayerreadystate"
			Return True ;func_getplayerreadystate
		Case "getplayerblinktimer"
			Return True ;func_getplayerblinktimer
		Case "callglobalfunction"
			Return True ;func_callglobalfunction
		Case "callfunction"
			Return True ;func_callfunction
		Case "movex"
			Return True ;func_movex
		Case "movey"
			Return True ;func_movey
		Case "movez"
			Return True ;func_movez
		Case "move"
			Return True ;func_move
		Case "pointangle"
			Return True ;func_pointangle
		Case "pointpitch"
			Return True ;func_pointpitch
		Case "pointat"
			Return True ;func_pointat
		Case "getplayertype"
			Return True ;func_getplayertype
		Case "createsound"
			Return True ;func_createsound
		Case "createplayersound"
			Return True ;func_createplayersound
		Case "getplayerentity"
			Return True ;func_getplayerentity
		Case "setnotarget"
			Return True ;func_setnotarget
		Case "setcontained106"
			Return True ;func_setcontained106
		Case "setremotedooron"
			Return True ;func_setremotedooron
		Case "setmtftimer"
			Return True ;func_setmtftimer
		Case "getroomname"
			Return True ;func_getroomname
		Case "getroomentity"
			Return True ;func_getroomentity
		Case "isvalidroom"
			Return True ;func_isvalidroom
		Case "getroomobjectentity"
			Return True ;func_getroomobjectentity
		Case "setdooropenstate"
			Return True ;func_setdooropenstate
		Case "setdoorlock"
			Return True ;func_setdoorlock
		Case "getdoorentity"
			Return True ;func_getdoorentity
		Case "getdoortype"
			Return True ;func_getdoortype
		Case "getdooropenstate"
			Return True ;func_getdooropenstate
		Case "getdoorlock"
			Return True ;func_getdoorlock
		Case "isvaliddoor"
			Return True ;func_isvaliddoor
		Case "setitempicker"
			Return True ;func_setitempicker
		Case "getitemtemplatename"
			Return True ;func_getitemtemplatename
		Case "getitemtemplatetempname"
			Return True ;func_getitemtemplatetempname
		Case "getitemtemplate"
			Return True ;func_getitemtemplate
		Case "createitem"
			Return True ;func_createitem
		Case "removeitem"
			Return True ;func_removeitem
		Case "getitementity"
			Return True ;func_getitementity
		Case "getitempicker"
			Return True ;func_getitempicker
		Case "isvaliditem"
			Return True ;func_isvaliditem
		Case "isvalidnpc"
			Return True ;func_isvalidnpc
		Case "createnpc"
			Return True ;func_createnpc
		Case "getnpctype"
			Return True ;func_getnpctype
		Case "getnpcstate1"
			Return True ;func_getnpcstate1
		Case "getnpcstate2"
			Return True ;func_getnpcstate2
		Case "getnpcstate3"
			Return True ;func_getnpcstate3
		Case "setnpcstate"
			Return True ;func_setnpcstate
		Case "getnpcevent"
			Return True ;func_getnpcevent
		Case "getnpcentity"
			Return True ;func_getnpcentity
		Case "geteventindex"
			Return True ;func_geteventindex
		Case "geteventstate1"
			Return True ;func_geteventstate1
		Case "geteventstate2"
			Return True ;func_geteventstate2
		Case "geteventstate3"
			Return True ;func_geteventstate3
		Case "geteventstr"
			Return True ;func_geteventstr
		Case "isvalidevent"
			Return True ;func_isvalidevent
		Case "removeevent"
			Return True ;func_removeevent
		Case "seteventstr"
			Return True ;func_seteventstr
		Case "seteventstate"
			Return True ;func_seteventstate
		Case "rconcommand"
			Return True ;func_rconcommand
		Case "createrocket"
			Return True ;func_createrocket
		Case "closefile"
			Return True ;func_closefile
		Case "readline"
			Return True ;func_readline
		Case "readint"
			Return True ;func_readint
		Case "readfloat"
			Return True ;func_readfloat
		Case "readshort"
			Return True ;func_readshort
		Case "readbyte"
			Return True ;func_readbyte
		Case "writeline"
			Return True ;func_writeline
		Case "writeint"
			Return True ;func_writeint
		Case "writefloat"
			Return True ;func_writefloat
		Case "writeshort"
			Return True ;func_writeshort
		Case "writebyte"
			Return True ;func_writebyte
		Case "openfile"
			Return True ;func_openfile
		Case "readfile"
			Return True ;func_readfile
		Case "writefile"
			Return True ;func_writefile
		Case "setlightvolume"
			Return True ;func_setlightvolume
		Case "getlightvolume"
			Return True ;func_getlightvolume
		Case "setserverversion"
			Return True ;func_setserverversion
		Case "getserverversion"
			Return True ;func_getserverversion
		Case "getplayerversion"
			Return True ;func_getplayerversion
		Case "setplayerafk"
			Return True ;func_setplayerafk
		Case "getplayerafk"
			Return True ;func_getplayerafk
		Case "setplayertextpos"
			Return True ;func_setplayertextpos
		Case "setplayertextstring"
			Return True ;func_setplayertextstring
		Case "setplayertextcolor"
			Return True ;func_setplayertextcolor
		Case "setplayerdrawcolor"
			Return True ;func_setplayerdrawcolor
		Case "setplayerdrawpos"
			Return True ;func_setplayerdrawpos
		Case "millisecs"
			Return True ;func_millisecs
		Case "isplayerconnected"
			Return True ;func_isplayerconnected
		Case "setplayerfogrange"
			Return True ;func_setplayerfogrange
		Case "restartserver"
			Return True ;func_restartserver
		Case "getplayermonitorwidth"
			Return True ;func_getplayermonitorwidth
		Case "getplayermonitorheight"
			Return True ;func_getplayermonitorheight
		Case "setmapseed"
			Return True ;func_setmapseed
		Case "removeobject"
			Return True ;func_removeobject
		Case "createobject"
			Return True ;func_createobject
		Case "removeplayertext"
			Return True ;func_removeplayertext
		Case "createplayertext"
			Return True ;func_createplayertext
		Case "removeplayerdraw"
			Return True ;func_removeplayerdraw
		Case "createplayerdraw"
			Return True ;func_createplayerdraw
		Case "setplayertype"
			Return True ;func_setplayertype
		Case "getplayerip"
			Return True ;func_getplayerip
		Case "getplayerping"
			Return True ;func_getplayerping
		Case "getplayernickname"
			Return True ;func_getplayernickname
		Case "shoot"
			Return True ;func_shoot
		Case "setplayerping"
			Return True ;func_setplayerping
		Case "isafakeplayer"
			Return True ;func_isafakeplayer
		Case "createfakeplayer"
			Return True ;func_createfakeplayer
		Case "getplayerloadstate"
			Return True ;func_getplayerloadstate
		Case "getplayerdeadstate"
			Return True ;func_getplayerdeadstate
		Case "setplayerfakedeadstate"
			Return True ;func_setplayerfakedeadstate
		Case "setplayerfakeholdinggun"
			Return True ;func_setplayerfakeholdinggun
		Case "setplayerfakenvg"
			Return True ;func_setplayerfakenvg
		Case "setplayerfakegasmask"
			Return True ;func_setplayerfakegasmask
		Case "setplayerfakevest"
			Return True ;func_setplayerfakevest
		Case "setplayerfakeanimation"
			Return True ;func_setplayerfakeanimation
		Case "setplayerfakehazmat"
			Return True ;func_setplayerfakehazmat
		Case "setplayerfakeblinktimer"
			Return True ;func_setplayerfakeblinktimer
		Case "setplayerposition"
			Return True ;func_setplayerposition
		Case "setplayerpositionid"
			Return True ;func_setplayerposition
		Case "setplayerangle"
			Return True ;func_setplayerangle
		Case "setplayermessage"
			Return True ;func_setplayermessage
		Case "giveadmin"
			Return True ;func_giveadmin
		Case "removeadmin"
			Return True ;func_removeadmin
		Case "isplayeradmin"
			Return True ;func_isplayeradmin
		Case "servermessage"
			Return True ;func_servermessage
		Case "playerconsolecommand"
			Return True ;func_playerconsolecommand
		Case "playplayersound"
			Return True ;func_playplayersound
		Case "playsound"
			Return True ;func_playsound
		Case "sendmessage"
			Return True ;func_sendmessage
		Case "kick"
			Return True ;func_kick
		Case "banip"
			Return True ;func_banip
		Case "plugin_remove"
			Return True ;func_plugin_remove
		Case "plugin_call"
			Return True ;func_plugin_call
		Case "plugin_poke"
			Return True ;func_plugin_poke
		Case "plugin_load"
			Return True ;func_plugin_load
		Case "delay"
			Return True ;func_delay
		Case "print"
			Return True ;func_print
		Case "getinivalue"
			Return True ;func_getinivalue
		Case "putinivalue"
			Return True ;func_putinivalue
		Case "getunixtime"
			Return True ;func_getunixtime
		Case "updateinifile"
			Return True ;func_updateinifile
		Case "int"
			Return True ;func_int
		Case "float"
			Return True ;func_float
		Case "str"
			Return True ;func_str
		Case "floor"
			Return True ;func_floor
		Case "ceil"
			Return True ;func_ceil
		Case "sgn"
			Return True ;func_sgn
		Case "abs"
			Return True ;func_abs
		Case "sqr"
			Return True ;func_sqr
		Case "sin"
			Return True ;func_sin
		Case "cos"
			Return True ;func_cos
		Case "tan"
			Return True ;func_tan
		Case "asin"
			Return True ;func_asin
		Case "acos"
			Return True ;func_acos
		Case "atan"
			Return True ;func_atan
		Case "atan2"
			Return True ;func_atan2
		Case "exp"
			Return True ;func_exp
		Case "log"
			Return True ;func_log
		Case "log10"
			Return True ;func_log10
		Case "rnd"
			Return True ;func_rnd
		Case "rand"
			Return True ;func_rand
		Case "num"
			Return True ;func_num
		Case "left"
			Return True ;func_left
		Case "right"
			Return True ;func_right
		Case "mid"
			Return True ;func_mid
		Case "replace"
			Return True ;func_replace
		Case "instr"
			Return True ;func_instr
		Case "lower"
			Return True ;func_lower
		Case "upper"
			Return True ;func_upper
		Case "trim"
			Return True ;func_trim
		Case "hex"
			Return True ;func_hex
		Case "bin"
			Return True ;func_bin
		Case "repeat"
			Return True ;func_repeat
		Case "array_create"
			Return True ;func_create
		Case "array_push"
			Return True ;func_push
		Case "array_pop"
			Return True ;func_pop
		Case "array_delete"
			Return True ;func_delete
		Case "array_sort"
			Return True ;func_sort
		Case "array_from_string"
			Return True ;func_from_string
		Case "changeplayername"
			Return True ;func_changeplayername
		Case "changeplayertag"
			Return True ;func_changeplayertag
		Case "isplayerpatron"
			Return True ;func_isplayerpatron
		Case "getincomingconnectionversion"
			Return True ;func_getincomingconnectionversion
		Case "getincomingconnectionpatron"
			Return True ;func_getincomingconnectionpatron
		Case "setmtftickets"
			Return True ;func_setmtftickets
		Case "setchaostickets"
			Return True ;func_setchaostickets
		Case "getmtftickets"
			Return True ;func_getmtftickets
		Case "getchaostickets"
			Return True ;func_getchaostickets
		Case "activatefilessending"
			Return True ;func_activatefilessending
		Case "setserverexplodetimeout"
			Return True ;func_setserverexplodetimeout
		Case "debuglog"
			Return True ;func_debuglog
		Case "initloadingscreens"
			Return True ;func_initloadingscreens
		Case "initinfoclues"
			Return True ;func_initinfoclues
		Case "stopallredirecttext"
			Return True ;func_stopallredirecttext
		Case "setscriptlanguage"
			Return True ;func_setscriptlanguage
		Case "getscriptpath"
			Return True ;func_getscriptpath
		Case "freefonts"
			Return True ;func_freefonts
		Case "loadfonts"
			Return True ;func_loadfonts
		Case "redirectfont"
			Return True ;func_redirectfont
		Case "redirectfile"
			Return True ;func_redirectfile
		Case "redirecttext"
			Return True ;func_redirecttext
		Case "stopredirecttext"
			Return True ;func_stopredirecttext
		Case "disableallworkshopscripts"
			Return True ;func_disableallworkshopscripts
		Case "setintercomtimeout"
			Return True ;func_setintercomtimeout
		Case "getintercomtimeout"
			Return True ;func_getintercomtimeout
		Case "getinventoryslotitemname"
			Return True ;func_getinventoryslotitemname
		Case "setselecteditemfrominventory"
			Return True ;func_setselecteditemfrominventory
		Case "isvaliditem"
			Return True ;func_isvaliditem
		Case "getselecteditem"
			Return True ;func_getselecteditem
		Case "getitemname"
			Return True ;func_getitemname
		Case "getitemtempname"
			Return True ;func_getitemtempname
		Case "getitemstate"
			Return True ;func_getitemstate
		Case "get427timer"
			Return True ;func_get427timer
		Case "getblurtimer"
			Return True ;func_getblurtimer
		Case "getstamina"
			Return True ;func_getstamina
		Case "getblinktimer"
			Return True ;func_getblinktimer
		Case "getcurrentweapon"
			Return True ;func_getcurrentweapon
		Case "getcurrentweaponmagazines"
			Return True ;func_getcurrentweaponmagazines
		Case "getcurrentweaponammo"
			Return True ;func_getcurrentweaponammo
		Case "keyhit"
			Return True ;func_keyhit
		Case "keydown"
			Return True ;func_keydown
		Case "createsound3d"
			Return True ;func_createsound3d
		Case "createlocalsound"
			Return True ;func_createlocalsound
		Case "getvolume"
			Return True ;func_getvolume
		;Case "stopchannel"
		;	Return True ;func_stopchannel
		;Case "resumechannel"
		;	Return True ;func_resumechannel
		;Case "pausechannel"
		;	Return True ;func_pausechannel
		;Case "channelpitch"
		;	Return True ;func_channelpitch
		;Case "channelvolume"
		;	Return True ;func_channelvolume
		;Case "channelpan"
		;	Return True ;func_channelpan
		;Case "channelplaying"
		;	Return True ;func_channelplaying
		Case "stop3dsounds"
			Return True ;func_stop3dsounds
		Case "setcamerashake"
			Return True ;func_setcamerashake
		Case "freefont"
			Return True ;func_freefont
		Case "freeimage"
			Return True ;func_freeimage
		Case "setfont"
			Return True ;func_setfont
		Case "color"
			Return True ;func_color
		Case "loadimage"
			Return True ;func_loadimage
		Case "loadanimimage"
			Return True ;func_loadanimimage
		Case "loadfont"
			Return True ;func_loadfont
		Case "copyimage"
			Return True ;func_copyimage
		Case "drawimage"
			Return True ;func_drawimage
		Case "resizeimage"
			Return True ;func_resizeimage
		Case "rotateimage"
			Return True ;func_rotateimage
		Case "text"
			Return True ;func_text
		Case "oval"
			Return True ;func_oval
		Case "rect"
			Return True ;func_rect
		Case "mousex"
			Return True ;func_mousex
		Case "mousey"
			Return True ;func_mousey
		Case "mousehit1"
			Return True ;func_mousehit1
		Case "mousehit2"
			Return True ;func_mousehit2
		Case "mousedown1"
			Return True ;func_mousedown1
		Case "mousedown2"
			Return True ;func_mousedown2
		Case "connect"
			Return True ;func_connect
		Case "disconnect"
			Return True ;func_disconnect
		Case "setplayerroom"
			Return True ;func_setplayerroom
		Case "createpivot"
			Return True ;func_createpivot
		Case "loadmesh"
			Return True ;func_loadmesh
		Case "loadanimmesh"
			Return True ;func_loadanimmesh
		Case "getplayercollider"
			Return True ;func_getplayercollider
		Case "getplayercamera"
			Return True ;func_getplayercamera
		Case "cameraclscolor"
			Return True ;func_cameraclscolor
		Case "camerafogcolor"
			Return True ;func_camerafogcolor
		Case "setambientlight"
			Return True ;func_setambientlight
		Case "hidescreenocclusion"
			Return True ;func_hidescreenocclusion
		Case "showscreenocclusion"
			Return True ;func_showscreenocclusion
		Case "getselectedscreen"
			Return True ;func_getselectedscreen
		Case "resetselectedscreen"
			Return True ;func_resetselectedscreen
		Case "showsky"
			Return True ;func_showsky
		Case "hidesky"
			Return True ;func_hidesky
		Case "getroomname"
			Return True ;func_getroomname
		Case "entityinview"
			Return True ;func_entityinview
		Case "entityvisible"
			Return True ;func_entityvisible
		Case "entityx"
			Return True ;func_entityx
		Case "entityy"
			Return True ;func_entityy
		Case "entityz"
			Return True ;func_entityz
		Case "entitypitch"
			Return True ;func_entitypitch
		Case "entityyaw"
			Return True ;func_entityyaw
		Case "entityroll"
			Return True ;func_entityroll
		Case "entitypick"
			Return True ;func_entitypick
		Case "positionentity"
			Return True ;func_positionentity
		Case "rotateentity"
			Return True ;func_rotateentity
		Case "moveentity"
			Return True ;func_moveentity
		Case "scaleentity"
			Return True ;func_scaleentity
		Case "setentitycollision"
			Return True ;func_setentitycollision
		Case "removeentity"
			Return True ;func_removeentity
		Case "setanimtime"
			Return True ;func_setanimtime
		Case "getdelta"
			Return True ;func_getdelta
		Case "getmonitorwidth"
			Return True ;func_getmonitorwidth
		Case "getmonitorheight"
			Return True ;func_getmonitorheight
		Case "sendrawpacket"
			Return True ;func_sendrawpacket
		Case "hexstring"
			Return True ;func_hexstring
		Case "eof"
			Return True ;func_eof
		Case "filetype"
			Return True ;func_filetype
		Case "filesize"
			Return True ;func_filesize
		Case "workshopfiletype"
			Return True ;func_workshopfiletype
		Case "workshopfilesize"
			Return True ;func_workshopfilesize
		Case "seekfile"
			Return True ;func_seekfile
		Case "filepos"
			Return True ;func_filepos
		Case "catcherror"
			Return True ;func_catcherror
		Case "writebytes"
			Return True ;func_writebytes
		Case "readbytes"
			Return True ;func_readbytes
		Case "createbank"
			Return True ;func_createbank
		Case "freebank"
			Return True ;func_freebank
		Case "banksize"
			Return True ;func_banksize
		Case "resizebank"
			Return True ;func_resizebank
		Case "copybank"
			Return True ;func_copybank
		Case "peekbyte"
			Return True ;func_peekbyte
		Case "peekshort"
			Return True ;func_peekshort
		Case "peekint"
			Return True ;func_peekint
		Case "peekfloat"
			Return True ;func_peekfloat
		Case "pokebyte"
			Return True ;func_pokebyte
		Case "pokeshort"
			Return True ;func_pokeshort
		Case "pokeint"
			Return True ;func_pokeint
		Case "pokefloat"
			Return True ;func_pokefloat
		Case "movex"
			Return True ;func_movex
		Case "movey"
			Return True ;func_movey
		Case "movez"
			Return True ;func_movez
		Case "move"
			Return True ;func_move
		Case "pointangle"
			Return True ;func_pointangle
		Case "pointpitch"
			Return True ;func_pointpitch
		Case "pointat"
			Return True ;func_pointat
		Case "closefile"
			Return True ;func_closefile
		Case "readline"
			Return True ;func_readline
		Case "readint"
			Return True ;func_readint
		Case "readfloat"
			Return True ;func_readfloat
		Case "readshort"
			Return True ;func_readshort
		Case "readbyte"
			Return True ;func_readbyte
		Case "writeline"
			Return True ;func_writeline
		Case "writeint"
			Return True ;func_writeint
		Case "writefloat"
			Return True ;func_writefloat
		Case "writeshort"
			Return True ;func_writeshort
		Case "writebyte"
			Return True ;func_writebyte
		Case "openfile"
			Return True ;func_openfile
		Case "readfile"
			Return True ;func_readfile
		Case "writefile"
			Return True ;func_writefile
		Case "openworkshopfile"
			Return True ;func_openworkshopfile
		Case "readworkshopfile"
			Return True ;func_readworkshopfile
		Case "writeworkshopfile"
			Return True ;func_writeworkshopfile
		Case "getversion"
			Return True ;func_getversion
		Case "millisecs"
			Return True ;func_millisecs
		Case "getping"
			Return True ;func_getping
		Case "getname"
			Return True ;func_getname
		Case "setgamemessage"
			Return True ;func_setgamemessage
		Case "sendconsolecommand"
			Return True ;func_sendconsolecommand
		Case "sendmessage"
			Return True ;func_sendmessage
		Case "plugin_remove"
			Return True ;func_plugin_remove
		Case "plugin_call"
			Return True ;func_plugin_call
		Case "plugin_poke"
			Return True ;func_plugin_poke
		Case "plugin_load"
			Return True ;func_plugin_load
		Case "int"
			Return True ;func_int
		Case "float"
			Return True ;func_float
		Case "str"
			Return True ;func_str
		Case "floor"
			Return True ;func_floor
		Case "ceil"
			Return True ;func_ceil
		Case "sgn"
			Return True ;func_sgn
		Case "abs"
			Return True ;func_abs
		Case "sqr"
			Return True ;func_sqr
		Case "sin"
			Return True ;func_sin
		Case "cos"
			Return True ;func_cos
		Case "tan"
			Return True ;func_tan
		Case "asin"
			Return True ;func_asin
		Case "acos"
			Return True ;func_acos
		Case "atan"
			Return True ;func_atan
		Case "atan2"
			Return True ;func_atan2
		Case "exp"
			Return True ;func_exp
		Case "log"
			Return True ;func_log
		Case "log10"
			Return True ;func_log10
		Case "rnd"
			Return True ;func_rnd
		Case "rand"
			Return True ;func_rand
		Case "num"
			Return True ;func_num
		Case "left"
			Return True ;func_left
		Case "right"
			Return True ;func_right
		Case "mid"
			Return True ;func_mid
		Case "replace"
			Return True ;func_replace
		Case "instr"
			Return True ;func_instr
		Case "lower"
			Return True ;func_lower
		Case "upper"
			Return True ;func_upper
		Case "trim"
			Return True ;func_trim
		Case "chr"
			Return True ;func_chr
		Case "asc"
			Return True ;func_asc
		Case "hex"
			Return True ;func_hex
		Case "bin"
			Return True ;func_bin
		Case "repeat"
			Return True ;func_repeat
		Case "array_create"
			Return True ;func_create
		Case "array_push"
			Return True ;func_push
		Case "array_pop"
			Return True ;func_pop
		Case "array_delete"
			Return True ;func_delete
		Case "array_sort"
			Return True ;func_sort
		Case "array_from_string"
			Return True ;func_from_string
		Case "getfov"
			Return True ;func_getfov
		Case "handleimage"
			Return True ;func_handleimage
		Case "caninteract"
			Return True ;func_caninteract
		Case "callglobalfunction"
			Return True
		Case "callfunction"
			Return True
		Case "ismenuopen"
			Return True
		Case "isinventoryopen"
			Return True
		Case "isotherinventoryopen"
			Return True
		Case "lockmouse"
			Return True
		Case "getoptionsmenu"
			Return True
		Case "getachievementsmenu"
			Return True
		Case "showpointer"
			Return True
		Case "hidepointer"
			Return True
		Case "iskeypadopen"
			Return True
		Case "isconsoleopen"
			Return True
		Case "iscoffeemachineopen"
			Return True
		Case "drawuibutton": Return True
		Case "lerp": Return True
		Case "lerpangle": Return True
		Case "ismainmenuopen": Return True
		Case "shouldplayerannouncement": Return True
		Case "addplayerspawnposition": Return True
		Case "addclassspawnposition": Return True
		Case "removespawnposition": Return True
		Case "isvalidspawnposition": Return True
		Case "entitydistance": Return True
		Case "setcustommap": Return True
		Case "entityscalex"
			Return True
		Case "entityscaley"
			Return True
		Case "entityscalez"
			Return True
		Case "meshwidth"
			Return True
		Case "meshheight"
			Return True
		Case "meshdepth"
			Return True
		Case "spawnchaos": Return True
		Case "createitemtemplate": Return True
	End Select
	Return False
	
End Function

Function SE_GetFunctionID(functionname$)
	Select FunctionName$
		Case "breachrole_setrolecategory": Return func_breachrole_setrolecategory
		Case "breachrole_setmaxrolecount": Return func_breachrole_SetMaxRoleCount
		Case "sethalloweenmode": Return func_sethalloweenmode
		Case "setchristmasmode": Return func_setchristmasmode
		Case "breachrole_createplayerrole": Return func_breachrole_createplayerrole
		Case "breachrole_getrolename": Return func_breachrole_getrolename
		Case "breachrole_setrolesettings": Return func_breachrole_setrolesettings
		Case "breachrole_setroleeffects": Return func_breachrole_setroleeffects
		Case "breachrole_setroleambientsound": Return func_breachrole_setroleambientsound
		Case "breachrole_setroleinstruction": Return func_breachrole_setroleinstruction
		Case "breachrole_setroledeadanimation": Return func_breachrole_setroledeadanimation
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
		
		Case "enableintercom": Return func_enableintercom
		Case "setintercomtimeout": Return func_setintercomtimeout
		Case "setintercomusingtime": Return func_setintercomusingtime
		Case "setplayerintercom": Return func_setplayerintercom
		Case "setplayerintercomusingtime": Return func_setplayerintercomusingtime
		Case "setplayerintercomtimeout": Return func_setplayerintercomtimeout
		Case "isplayerusingintercom": Return func_isplayerusingintercom
		
		Case "setdoorcode": Return func_setdoorcode
		Case "getdoorcode": Return func_getdoorcode
		Case "gettime": Return func_gettime
		Case "getdate": Return func_getdate
		Case "spawnchaos": Return func_spawnchaos
		Case "getdoorkeycard": Return func_getdoorkeycard
		Case "getplayerselecteditem": Return func_getplayerselecteditem
		Case "setdoorkeycard": Return func_setdoorkeycard
		Case "executeserverconsole": Return func_ExecuteServerConsole
		Case "createcube": Return func_createcube
		Case "createsphere": Return func_createsphere
		Case "createcylinder": Return func_createcylinder
		Case "createcone": Return func_createcone
		Case "getplayerdownloadingcount": Return func_getplayerdownloadingcount
		Case "isplayerdownloadingfile": Return func_isplayerdownloadingfile
		Case "createpivot"
			Return func_createpivot
		Case "setplayerspeedhackmult": Return func_setplayerspeedhackmult
		Case "getplayerspeedhackmult": Return func_getplayerspeedhackmult
		Case "turnentity": Return func_turnentity
		Case "createobjectsound": Return func_createobjectsound
		Case "createobjectsoundforplayer": Return func_createobjectsoundforplayer
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
		
		Case "enablenoclipanticheat": Return func_enablenoclipanticheat
		Case "enablecamerashakeondamage": Return func_enablecamerashakeondamage
		
		Case "pointentity": Return func_pointentity
		Case "getplayercamera": Return func_getplayercamera
		Case "deltayaw": Return func_deltayaw
		Case "deltapitch": Return func_deltapitch
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
		Case "getplayeroriginalx": Return func_getplayeroriginalx
		Case "getplayeroriginaly": Return func_getplayeroriginaly
		Case "getplayeroriginalz": Return func_getplayeroriginalz
		Case "setplayerfakeroom": Return func_setplayerfakeroom
		Case "changeplayersteamid": Return func_changeplayersteamid
		Case "addplayerspawnposition": Return func_addplayerspawnposition
		Case "addclassspawnposition": Return func_addclassspawnposition
		Case "removespawnposition": Return func_removespawnposition
		Case "isvalidspawnposition": Return func_isvalidspawnposition
		
		Case "getplayerspeedmult": Return func_getplayerspeedmult
		Case "updateobjects": Return func_updateobjects
		Case "preparemodelidentifier": Return func_preparemodelidentifier
		Case "setobjectinterpolation": Return func_setobjectinterpolation
		Case "shouldplayerannouncement": Return func_shouldplayerannouncement
		Case "getroomdoor": Return func_getroomdoor
		Case "changeplayersize": Return func_changeplayersize
		Case "getplayersize": Return func_getplayersize
		Case "lerp": Return func_lerp
		Case "lerpangle": Return func_lerpangle
		;
		Case "dllfunctionvoid_0": Return func_dllfunctionvoid_0
		Case "dllfunctionvoid_1": Return func_dllfunctionvoid_1
		Case "dllfunctionvoid_2": Return func_dllfunctionvoid_2
		Case "dllfunctionvoid_3": Return func_dllfunctionvoid_3
		Case "dllfunctionvoid_4": Return func_dllfunctionvoid_4
		Case "dllfunctionvoid_5": Return func_dllfunctionvoid_5
		Case "dllfunctionvoid_6": Return func_dllfunctionvoid_6
		Case "dllfunctionvoid_7": Return func_dllfunctionvoid_7
		Case "dllfunctionvoid_8": Return func_dllfunctionvoid_8
		Case "dllfunctionvoid_9": Return func_dllfunctionvoid_9
		
		Case "dllfunctionint_0": Return func_dllfunctionint_0
		Case "dllfunctionint_1": Return func_dllfunctionint_1
		Case "dllfunctionint_2": Return func_dllfunctionint_2
		Case "dllfunctionint_3": Return func_dllfunctionint_3
		Case "dllfunctionint_4": Return func_dllfunctionint_4
		Case "dllfunctionint_5": Return func_dllfunctionint_5
		Case "dllfunctionint_6": Return func_dllfunctionint_6
		Case "dllfunctionint_7": Return func_dllfunctionint_7
		Case "dllfunctionint_8": Return func_dllfunctionint_8
		Case "dllfunctionint_9": Return func_dllfunctionint_9
		
		Case "dllfunctionfloat_0": Return func_dllfunctionfloat_0
		Case "dllfunctionfloat_1": Return func_dllfunctionfloat_1
		Case "dllfunctionfloat_2": Return func_dllfunctionfloat_2
		Case "dllfunctionfloat_3": Return func_dllfunctionfloat_3
		Case "dllfunctionfloat_4": Return func_dllfunctionfloat_4
		Case "dllfunctionfloat_5": Return func_dllfunctionfloat_5
		Case "dllfunctionfloat_6": Return func_dllfunctionfloat_6
		Case "dllfunctionfloat_7": Return func_dllfunctionfloat_7
		Case "dllfunctionfloat_8": Return func_dllfunctionfloat_8
		Case "dllfunctionfloat_9": Return func_dllfunctionfloat_9
		
		Case "dllfunctionstring_0": Return func_dllfunctionstring_0
		Case "dllfunctionstring_1": Return func_dllfunctionstring_1
		Case "dllfunctionstring_2": Return func_dllfunctionstring_2
		Case "dllfunctionstring_3": Return func_dllfunctionstring_3
		Case "dllfunctionstring_4": Return func_dllfunctionstring_4
		Case "dllfunctionstring_5": Return func_dllfunctionstring_5
		Case "dllfunctionstring_6": Return func_dllfunctionstring_6
		Case "dllfunctionstring_7": Return func_dllfunctionstring_7
		Case "dllfunctionstring_8": Return func_dllfunctionstring_8
		Case "dllfunctionstring_9": Return func_dllfunctionstring_9
		; 
		Case "connecttocentralserver": Return func_connecttocentralserver
		Case "reconnecttocentralserver": Return func_reconnecttocentralserver
		Case "createsteaminstance": Return func_createsteaminstance
		Case "getsteaminstancetag": Return func_getsteaminstancetag
		Case "removesteaminstance": Return func_removesteaminstance
		Case "setdescriptionline": Return func_setdescriptionline
		Case "getdescriptionline": Return func_getdescriptionline
		Case "setmaxspawnplayers": Return func_setmaxspawnplayers
		Case "getmaxspawnplayers": Return func_getmaxspawnplayers
		Case "geteventname": Return func_geteventname
		Case "geteventroomid": Return func_geteventroomid
		Case "enableoldresponse": Return func_enableoldresponse
		Case "closeapp": Return func_closeapp
		Case "getplayermute": Return func_getplayermute
		Case "setplayermute": Return func_setplayermute
		; Sqlite
		Case "opendatabase": Return func_sqlite_opendatabase
		Case "closedatabase": Return func_sqlite_closedatabase
		Case "setdatabasetimeout": Return func_sqlite_setdatabasetimeout
		Case "databaseversion": Return func_sqlite_databaseversion
		Case "lastrowidinserted": Return func_sqlite_lastrowidinserted
		Case "rowschangedbylaststatement": Return func_sqlite_rowschangedbylaststatement
		Case "rowschangedthissession": Return func_sqlite_rowschangedthissession
		Case "autocommitison": Return func_sqlite_autocommitison
		Case "begintransaction": Return func_sqlite_begintransaction
		Case "committransaction": Return func_sqlite_committransaction
		Case "rollbacktransaction": Return func_sqlite_rollbacktransaction
		Case "lastdatabaseerrorcode": Return func_sqlite_lastdatabaseerrorcode
		Case "lastdatabaseerrormessage": Return func_sqlite_lastdatabaseerrormessage
		Case "interruptdatabase": Return func_sqlite_interruptdatabase
		Case "executesql": Return func_sqlite_executesql
		Case "preparesql": Return func_sqlite_preparesql
		Case "getnextdatarow": Return func_sqlite_getnextdatarow
		Case "finalisesql": Return func_sqlite_finalisesql
		Case "resetsql": Return func_sqlite_resetsql
		Case "sqlhasexpired": Return func_sqlite_sqlhasexpired
		Case "getdatabasehandlefromstatementhandle": Return func_sqlite_getdatabasehandlefromstatementhandle
		Case "getcolumncount": Return func_sqlite_getcolumncount
		Case "getcolumnname": Return func_sqlite_getcolumnname
		Case "getcolumntype": Return func_sqlite_getcolumntype
		Case "getcolumndeclaredtype": Return func_sqlite_getcolumndeclaredtype
		Case "getcolumnsize": Return func_sqlite_getcolumnsize
		Case "getcolumnvalueasinteger": Return func_sqlite_getcolumnvalueasinteger
		Case "getcolumnvalueasfloat": Return func_sqlite_getcolumnvalueasfloat
		Case "getcolumnvalueasstring": Return func_sqlite_getcolumnvalueasstring
		Case "getcolumnvalueasblob": Return func_sqlite_getcolumnvalueasblob
		Case "sqlparametercount": Return func_sqlite_sqlparametercount
		Case "sqlparametername": Return func_sqlite_sqlparametername
		Case "sqlparameterindex": Return func_sqlite_sqlparameterindex
		Case "bindsqlparameterasnull": Return func_sqlite_bindsqlparameterasnull
		Case "bindsqlparameterasinteger": Return func_sqlite_bindsqlparameterasinteger
		Case "bindsqlparameterasfloat": Return func_sqlite_bindsqlparameterasfloat
		Case "bindsqlparameterasstring": Return func_sqlite_bindsqlparameterasstring
		Case "bindsqlparameterasblob": Return func_sqlite_bindsqlparameterasblob
		Case "transfersqlbindings": Return func_sqlite_transfersqlbindings
		Case "sqlite3_errorhasoccurred": Return func_sqlite_sqlite3_errorhasoccurred
		; ==========================================================================
		
		; mysql
		Case "opensqlstream": Return func_mysql_opensqlstream
		Case "sqlconnected": Return func_mysql_sqlconnected
		Case "sqlquery": Return func_mysql_sqlquery
		Case "sqlrowcount": Return func_mysql_sqlrowcount
		Case "sqlfetchrow": Return func_mysql_sqlfetchrow
		Case "sqlfieldcount": Return func_mysql_sqlfieldcount
		Case "readsqlfield": Return func_mysql_readsqlfield
		Case "readsqlfieldindex": Return func_mysql_readsqlfieldindex
		Case "freesqlquery": Return func_mysql_freesqlquery
		Case "freesqlrow": Return func_mysql_freesqlrow
		Case "closesqlstream": Return func_mysql_closesqlstream
		; ==========================================================================
		Case "getserverexplodetimeout"
			Return func_getserverexplodetimeout
		Case "getserverspawntimeout"
			Return func_getserverspawntimeout
		Case "setserverspawntimeout"
			Return func_setserverspawntimeout
		Case "enableauthkeyonconnect"
			Return func_enableauthkeyonconnect
		Case "setplayervelocity"
			Return func_setplayervelocity
		Case "getplayervelocity"
			Return func_getplayervelocity
		Case "getplayerhandcuff"
			Return func_getplayerhandcuff
		Case "setplayerhandcuff"
			Return func_setplayerhandcuff
		Case "getplayersteamid"
			Return func_getplayersteamid
		Case "giveplayerhealth"
			Return func_giveplayerhealth
		Case "setplayerspeedmult"
			Return func_setplayerspeedmult
		Case "sendblinktimerforplayer"
			Return func_sendblinktimerforplayer
		Case "zlib_compress"
			Return func_zlib_compress
		Case "zlib_uncompress"
			Return func_zlib_uncompress
		Case "deactivatewarheads"
			Return func_deactivatewarheads
		Case "spawnmtf"
			Return func_spawnmtf
		Case "activatewarheads"
			Return func_activatewarheads
		Case "getplayerzone"
			Return func_getplayerzone
		Case "getbreachtimer"
			Return func_getbreachtimer
		Case "addaccessversion"
			Return func_addaccessversion
		Case "getplayerinjuries"
			Return func_getplayerinjuries
		Case "setplayerinjuries"
			Return func_setplayerinjuries
		Case "getplayerheadpitch"
			Return func_getplayerheadpitch
		Case "setplayerheadpitch"
			Return func_setplayerheadpitch
		Case "createentitycamera"
			Return func_createentitycamera
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
		Case "sendrawpacket"
			Return func_sendrawpacket
		Case "sendscript"
			Return func_sendscript
		Case "sendfile"
			Return func_sendfile
		Case "setgamestate"
			Return func_setgamestate
		Case "setservername"
			Return func_setservername
		Case "setserverdescription"
			Return func_setserverdescription
		Case "setserverpassword"
			Return func_setserverpassword
		Case "setservercurrentbreachtimer"
			Return func_setservercurrentbreachtimer
		Case "setservernocheat"
			Return func_setservernocheat
		Case "getplayerhealth"
			Return func_getplayerhealth
		Case "setplayerfakehealth"
			Return func_setplayerfakehealth
		Case "disableautokick"
			Return func_disableautokick
		Case "reloadapp"
			Return func_reloadapp
		Case "hexstring"
			Return func_hexstring
		Case "removetimer"
			Return func_removetimer
		Case "createtimer"
			Return func_createtimer
		Case "serverwritebyte"
			Return func_serverwritebyte
		Case "serverwriteshort"
			Return func_serverwriteshort
		Case "serverwriteint"
			Return func_serverwriteint
		Case "serverwritefloat"
			Return func_serverwritefloat
		Case "debug"
			Return func_debug
		Case "eof"
			Return func_eof
		Case "asc"
			Return func_asc
		Case "chr"
			Return func_chr
		Case "filetype"
			Return func_filetype
		Case "filesize"
			Return func_filesize
		Case "seekfile"
			Return func_seekfile
		Case "filepos"
			Return func_filepos
		Case "catcherror"
			Return func_catcherror
		Case "createerror"
			Return func_createerror
		Case "getpacketindex"
			Return func_getpacketindex
		Case "sendrawdata"
			Return func_sendrawdata
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
		Case "disablelobby"
			Return func_disablelobby
		Case "removevar"
			Return func_removevar
		Case "setvarvalue"
			Return func_setvarvalue
		Case "getvarvalue"
			Return func_getvarvalue
		Case "removeplayervar"
			Return func_removeplayervar
		Case "setplayervarvalue"
			Return func_setplayervarvalue
		Case "getplayervarvalue"
			Return func_getplayervarvalue
		Case "addarrayelements"
			Return func_addarrayelements
		Case "getobjectentity"
			Return func_getobjectentity
		Case "setobjectvisibleforplayer"
			Return func_setobjectvisibleforplayer
		Case "setplayersspawnposition"
			Return func_setplayersspawnposition
		Case "setplayerfakeradiowave"
			Return func_setplayerfakeradiowave
		Case "getplayerholdinggun"
			Return func_getplayerholdinggun
		Case "getplayerradiowave"
			Return func_getplayerradiowave
		Case "getplayerroomid"
			Return func_getplayerroomid
		Case "getplayernvg"
			Return func_getplayernvg
		Case "getplayeranimation"
			Return func_getplayeranimation
		Case "getplayerhazmat"
			Return func_getplayerhazmat
		Case "getplayergasmask"
			Return func_getplayergasmask
		Case "getplayervest"
			Return func_getplayervest
		Case "getplayerreadystate"
			Return func_getplayerreadystate
		Case "getplayerblinktimer"
			Return func_getplayerblinktimer
		Case "callglobalfunction"
			Return func_callglobalfunction
		Case "callfunction"
			Return func_callfunction
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
		Case "getplayertype"
			Return func_getplayertype
		Case "createsound"
			Return func_createsound
		Case "createplayersound"
			Return func_createplayersound
		Case "getplayerentity"
			Return func_getplayerentity
		Case "setnotarget"
			Return func_setnotarget
		Case "setcontained106"
			Return func_setcontained106
		Case "setremotedooron"
			Return func_setremotedooron
		Case "setmtftimer"
			Return func_setmtftimer
		Case "getroomname"
			Return func_getroomname
		Case "getroomentity"
			Return func_getroomentity
		Case "isvalidroom"
			Return func_isvalidroom
		Case "getroomobjectentity"
			Return func_getroomobjectentity
		Case "setdooropenstate"
			Return func_setdooropenstate
		Case "setdoorlock"
			Return func_setdoorlock
		Case "getdoorentity"
			Return func_getdoorentity
		Case "getdoortype"
			Return func_getdoortype
		Case "getdooropenstate"
			Return func_getdooropenstate
		Case "getdoorlock"
			Return func_getdoorlock
		Case "isvaliddoor"
			Return func_isvaliddoor
		Case "setitempicker"
			Return func_setitempicker
		Case "getitemtemplatename"
			Return func_getitemtemplatename
		Case "getitemtemplatetempname"
			Return func_getitemtemplatetempname
		Case "getitemtemplate"
			Return func_getitemtemplate
		Case "createitem"
			Return func_createitem
		Case "removeitem"
			Return func_removeitem
		Case "getitementity"
			Return func_getitementity
		Case "getitempicker"
			Return func_getitempicker
		Case "isvaliditem"
			Return func_isvaliditem
		Case "isvalidnpc"
			Return func_isvalidnpc
		Case "createnpc"
			Return func_createnpc
		Case "getnpctype"
			Return func_getnpctype
		Case "getnpcstate1"
			Return func_getnpcstate1
		Case "getnpcstate2"
			Return func_getnpcstate2
		Case "getnpcstate3"
			Return func_getnpcstate3
		Case "setnpcstate"
			Return func_setnpcstate
		Case "getnpcevent"
			Return func_getnpcevent
		Case "getnpcentity"
			Return func_getnpcentity
		Case "geteventindex"
			Return func_geteventindex
		Case "geteventstate1"
			Return func_geteventstate1
		Case "geteventstate2"
			Return func_geteventstate2
		Case "geteventstate3"
			Return func_geteventstate3
		Case "geteventstr"
			Return func_geteventstr
		Case "isvalidevent"
			Return func_isvalidevent
		Case "removeevent"
			Return func_removeevent
		Case "seteventstr"
			Return func_seteventstr
		Case "seteventstate"
			Return func_seteventstate
		Case "rconcommand"
			Return func_rconcommand
		Case "createrocket"
			Return func_createrocket
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
		Case "setlightvolume"
			Return func_setlightvolume
		Case "getlightvolume"
			Return func_getlightvolume
		Case "setserverversion"
			Return func_setserverversion
		Case "getserverversion"
			Return func_getserverversion
		Case "getplayerversion"
			Return func_getplayerversion
		Case "setplayerafk"
			Return func_setplayerafk
		Case "getplayerafk"
			Return func_getplayerafk
		Case "setplayertextpos"
			Return func_setplayertextpos
		Case "setplayertextstring"
			Return func_setplayertextstring
		Case "setplayertextcolor"
			Return func_setplayertextcolor
		Case "setplayerdrawcolor"
			Return func_setplayerdrawcolor
		Case "setplayerdrawpos"
			Return func_setplayerdrawpos
		Case "millisecs"
			Return func_millisecs
		Case "isplayerconnected"
			Return func_isplayerconnected
		Case "setplayerfogrange"
			Return func_setplayerfogrange
		Case "restartserver"
			Return func_restartserver
		Case "getplayermonitorwidth"
			Return func_getplayermonitorwidth
		Case "getplayermonitorheight"
			Return func_getplayermonitorheight
		Case "setmapseed"
			Return func_setmapseed
		Case "removeobject"
			Return func_removeobject
		Case "createobject"
			Return func_createobject
		Case "removeplayertext"
			Return func_removeplayertext
		Case "createplayertext"
			Return func_createplayertext
		Case "removeplayerdraw"
			Return func_removeplayerdraw
		Case "createplayerdraw"
			Return func_createplayerdraw
		Case "setplayertype"
			Return func_setplayertype
		Case "getplayerip"
			Return func_getplayerip
		Case "getplayerping"
			Return func_getplayerping
		Case "getplayernickname"
			Return func_getplayernickname
		Case "shoot"
			Return func_shoot
		Case "setplayerping"
			Return func_setplayerping
		Case "isafakeplayer"
			Return func_isafakeplayer
		Case "createfakeplayer"
			Return func_createfakeplayer
		Case "getplayerloadstate"
			Return func_getplayerloadstate
		Case "getplayerdeadstate"
			Return func_getplayerdeadstate
		Case "setplayerfakedeadstate"
			Return func_setplayerfakedeadstate
		Case "setplayerfakeholdinggun"
			Return func_setplayerfakeholdinggun
		Case "setplayerfakenvg"
			Return func_setplayerfakenvg
		Case "setplayerfakegasmask"
			Return func_setplayerfakegasmask
		Case "setplayerfakevest"
			Return func_setplayerfakevest
		Case "setplayerfakeanimation"
			Return func_setplayerfakeanimation
		Case "setplayerfakehazmat"
			Return func_setplayerfakehazmat
		Case "setplayerfakeblinktimer"
			Return func_setplayerfakeblinktimer
		Case "setplayerposition"
			Return func_setplayerposition
		Case "setplayerpositionid"
			Return func_setplayerpositionid
		Case "setplayerangle"
			Return func_setplayerangle
		Case "setplayermessage"
			Return func_setplayermessage
		Case "giveadmin"
			Return func_giveadmin
		Case "removeadmin"
			Return func_removeadmin
		Case "isplayeradmin"
			Return func_isplayeradmin
		Case "servermessage"
			Return func_servermessage
		Case "playerconsolecommand"
			Return func_playerconsolecommand
		Case "playplayersound"
			Return func_playplayersound
		Case "playsound"
			Return func_playsound
		Case "sendmessage"
			Return func_sendmessage
		Case "kick"
			Return func_kick
		Case "banip"
			Return func_banip
		Case "plugin_remove"
			Return func_plugin_remove
		Case "plugin_call"
			Return func_plugin_call
		Case "plugin_poke"
			Return func_plugin_poke
		Case "plugin_load"
			Return func_plugin_load
		Case "delay"
			Return func_delay
		Case "print"
			Return func_print
		Case "getinivalue"
			Return func_getinivalue
		Case "putinivalue"
			Return func_putinivalue
		Case "getunixtime"
			Return func_getunixtime
		Case "updateinifile"
			Return func_updateinifile
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
		Case "hex"
			Return func_hex
		Case "bin"
			Return func_bin
		Case "repeat"
			Return func_repeat
		Case "array_create"
			Return func_array_create
		Case "array_push"
			Return func_array_push
		Case "array_pop"
			Return func_array_pop
		Case "array_delete"
			Return func_array_delete
		Case "array_sort"
			Return func_array_sort
		Case "array_from_string"
			Return func_array_from_string
		Case "changeplayername"
			Return func_changeplayername
		Case "changeplayertag"
			Return func_changeplayertag
		Case "isplayerpatron"
			Return func_isplayerpatron
		Case "getincomingconnectionversion"
			Return func_getincomingconnectionversion
		Case "getincomingconnectionpatron"
			Return func_getincomingconnectionpatron
		Case "setmtftickets"
			Return func_setmtftickets
		Case "setchaostickets"
			Return func_setchaostickets
		Case "getmtftickets"
			Return func_getmtftickets
		Case "getchaostickets"
			Return func_getchaostickets
		Case "activatefilessending"
			Return func_activatefilessending
		Case "setserverexplodetimeout"
			Return func_setserverexplodetimeout
		Case "isvalidroomdoor"
			Return func_isvalidroomdoor
		Case "entitydistance"
			Return func_entitydistance
		Case "getplayerhitbox"
			Return func_getplayerhitbox
		Case "setcustommap"
			Return func_setcustommap
		Case "entityscalex"
			Return func_entityscalex
		Case "entityscaley"
			Return func_entityscaley
		Case "entityscalez"
			Return func_entityscalez
		Case "meshwidth"
			Return func_meshwidth
		Case "meshheight"
			Return func_meshheight
		Case "meshdepth"
			Return func_meshdepth
		Case "setplayernoclip"
			Return func_setplayernoclip
		Case "getplayernoclip"
			Return func_getplayernoclip
		Case "createevent": Return func_createevent
		Case "createitemtemplate": Return func_createitemtemplate
	End Select
End Function

; ******* my
	Global pointyaw#, pointpitch#, movex#, movey#, movez#
; *******

Global CurrentLuaState%
	
Function SE_ExecuteFunction(State%, FunctionID)
	
	If State <> 0 Then
		If InvokeFunctionAddress = 0 Then 
			InvokeFunctionAddress = SLUA_GET_CALLING_FUNCTION()
			SLUA_SET_GLOBAL_HANDLER(InvokeFunctionAddress)
			SLUA_LOAD_FUNCTIONS()
			Return
		EndIf
	EndIf
	
	G_SE_ReturnVal$ = ""
	G_SE_ReturnType% = SE_NULL
	
	CurrentParam = 0
	CurrentLuaState = State
	
	Select FunctionID
		Case func_sethalloweenmode
			HALLOWEENINDEX = SE_AND_LUA_ToIntArg(0)
		Case func_setchristmasmode
			NEWYEARINDEX = SE_AND_LUA_ToIntArg(0)
		; === custom roles
		Case func_breachrole_setrolecategory
			multiplayer_breach_SetRoleCategory(SE_AND_LUA_ToIntArg(0),SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_SetMaxRoleCount
			multiplayer_breach_SetMaxRoleCount(SE_AND_LUA_ToIntArg(0),SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_CreatePlayerRole
			multiplayer_breach_CreatePlayerRole(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToStringArg(3), SE_AND_LUA_ToIntArg(4), SE_AND_LUA_ToIntArg(5), SE_AND_LUA_ToIntArg(6),SE_AND_LUA_ToIntArg(7))
		Case func_breachrole_GetRoleName
			G_SE_ReturnVal = GetTypeName(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_STRING
		Case func_breachrole_SetRoleSettings
			multiplayer_breach_SetRoleSettings(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToIntArg(3),SE_AND_LUA_ToFloatArg(4),SE_AND_LUA_ToIntArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToIntArg(7),SE_AND_LUA_ToIntArg(8))
		Case func_breachrole_SetRoleEffects
			multiplayer_breach_SetRoleEffects(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3),SE_AND_LUA_ToIntArg(4),SE_AND_LUA_ToIntArg(5),SE_AND_LUA_ToIntArg(6),SE_AND_LUA_ToIntArg(7),SE_AND_LUA_ToIntArg(8),SE_AND_LUA_ToIntArg(9),SE_AND_LUA_ToIntArg(10))
		Case func_breachrole_SetRoleAmbientSound
			multiplayer_breach_SetRoleAmbientSound(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToStringArg(2))
		Case func_breachrole_SetRoleInstruction
			multiplayer_breach_SetRoleInstruction(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1))
		Case func_breachrole_SetRoleDeadAnimation
			multiplayer_breach_SetRoleDeadAnimation(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
        Case func_breachrole_SetRoleHitboxScales
            multiplayer_breach_SetRoleHitboxScales(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))    
		Case func_breachrole_SetRoleBone
			multiplayer_breach_SetRoleBone(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToStringArg(2))
		Case func_breachrole_SetRoleAnimation
			multiplayer_breach_SetRoleAnimation(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToIntArg(3), SE_AND_LUA_ToFloatArg(4))
		Case func_breachrole_SetRoleArmedAnimation
			multiplayer_breach_SetRoleArmedAnimation(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToIntArg(3), SE_AND_LUA_ToFloatArg(4))
		Case func_breachrole_MarkAsFriend
			multiplayer_breach_MarkAsFriend(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_breachrole_RoleTakeRoleSpawn
			multiplayer_breach_RoleTakeRoleSpawn(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_SetRolePositionsOffset
			multiplayer_breach_SetRolePositionsOffset(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))
		Case func_breachrole_SetRoleHoldingGrenade
			multiplayer_breach_SetRoleHoldingGrenade(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))
		Case func_breachrole_SetRoleHoldingItem
			multiplayer_breach_SetRoleHoldingItem(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
		Case func_breachrole_SetRoleHandcuff
			multiplayer_breach_SetRoleHandcuff(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3),SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToStringArg(5), SE_AND_LUA_ToFloatArg(6), SE_AND_LUA_ToFloatArg(7),SE_AND_LUA_ToFloatArg(8))
		Case func_breachrole_AllowRoleWeaponAttaches
			multiplayer_breach_AllowRoleWeaponAttaches(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_AllowItemsAttaches
			multiplayer_breach_AllowItemsAttaches(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_MarkRoleAsSCP
			multiplayer_breach_MarkRoleAsSCP(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_MarkAs035
			multiplayer_breach_MarkAs035(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_MarkAs049
			multiplayer_breach_MarkAs049(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_breachrole_IsA035
			G_SE_ReturnVal = multiplayer_breach_IsA035(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_IsA049
			G_SE_ReturnVal = multiplayer_breach_IsA049(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_IsAFriend
			G_SE_ReturnVal = multiplayer_IsAFriend(SE_AND_LUA_ToIntArg(0),SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_GetMaxHP
			G_SE_ReturnVal = multiplayer_breach_GetMaxHP(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_breachrole_GetRoleScale
			G_SE_ReturnVal = multiplayer_breach_GetRoleScale(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		; ===
		Case 900
			AddLog("Detected error #"+SE_AND_LUA_ToIntArg(0)+": "+SE_AND_LUA_ToStringArg(1), 0, False)
		Case func_createitemtemplate
		
			itt.ItemTemplates = CreateItemTemplate(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToStringArg(2), SE_AND_LUA_ToStringArg(3), SE_AND_LUA_ToStringArg(4), SE_AND_LUA_ToFloatArg(5))
			itt\sound = SE_AND_LUA_ToIntArg(6)
			G_SE_ReturnVal = itt\templateID
			G_SE_ReturnType = SE_INT
		Case func_enableintercom
			Server\DisableIntercom = 1-SE_GetParamInt()
		Case func_setintercomtimeout
			Server\IntercomTimeout = SE_GetParamInt()
		Case func_setintercomusingtime
			Server\IntercomUsingTime = SE_GetParamInt()
		Case func_setplayerintercom
			Player[SE_AND_LUA_ToIntArg(0)]\Announcement = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerintercomusingtime
			Player[SE_AND_LUA_ToIntArg(0)]\TimeIntercom = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerintercomtimeout
			Player[SE_AND_LUA_ToIntArg(0)]\CheckIntercom = SE_AND_LUA_ToIntArg(1)
		Case func_isplayerusingintercom
			G_SE_ReturnVal = (Player[SE_AND_LUA_ToIntArg(1)]\Announcement=True)
			G_SE_ReturnType = SE_INT
		Case func_createevent
			e2.Events = CreateEvent(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToFloatArg(3))
			
			If e2 <> Null Then G_SE_ReturnVal = e2\ID
			G_SE_ReturnType = SE_INT
		Case func_getplayerselecteditem
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\SelectedItem
			G_SE_ReturnType = SE_INT
		Case func_ExecuteServerConsole
			OnPlayerConsole(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2))
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
			
		Case func_getplayerdownloadingcount
			G_SE_ReturnVal = getplayerdownloadingcount(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_isplayerdownloadingfile
			G_SE_ReturnType = SE_INT

			For q.querys = Each querys
				If q\playerid = SE_AND_LUA_ToIntArg(0) Then
					If q\file = SE_AND_LUA_ToStringArg(1) Then
						G_SE_ReturnVal = True
						Exit
					EndIf
				EndIf
			Next
		Case func_createpivot
			G_SE_ReturnVal = CreatePivot()
			G_SE_ReturnType = SE_INT
		Case func_getplayerspeedhackmult
			G_SE_ReturnVal = Player[SE_GetParamInt()]\AC_ShouldSpeed
			G_SE_ReturnType = SE_FLOAT
		Case func_setplayerspeedhackmult
			Player[SE_AND_LUA_ToIntArg(0)]\AC_ShouldSpeed = SE_AND_LUA_ToFloatArg(1)
		Case func_setplayernoclip
			Player[SE_AND_LUA_ToIntArg(0)]\Noclip = SE_AND_LUA_ToIntArg(1)
		Case func_getplayernoclip
			G_SE_ReturnVal = Player[SE_GetParamInt()]\Noclip
			G_SE_ReturnType = SE_INT
		Case func_turnentity
			TurnEntity(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1),SE_AND_LUA_ToFloatArg(2),SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToIntArg(4))
		Case func_createobjectsound
			object_sound_create(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))
		Case func_createobjectsoundforplayer
			object_sound_create(SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToStringArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToIntArg(0))
		Case func_tcp_get
			G_SE_ReturnVal = DownloadFile(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_INT
		Case func_tcp_close
			CloseTCPStream(SE_AND_LUA_ToIntArg(0))
		Case func_createsprite
			G_SE_ReturnVal = CreateSprite()
			G_SE_ReturnType = SE_INT
		Case func_loadsprite
			G_SE_ReturnVal = LoadSprite(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_spriteviewmode
			SpriteViewMode(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_scalesprite
			ScaleSprite(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
		Case func_entityfx
			EntityFX(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_entityblend
			EntityBlend(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_showentity
			ShowEntity(SE_AND_LUA_ToIntArg(0))
		Case func_hideentity
			HideEntity(SE_AND_LUA_ToIntArg(0))
		Case func_getparent
			G_SE_ReturnVal = GetParent(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_entityautofade
			EntityAutoFade(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1))
		Case func_entitycolor
			EntityColor(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToIntArg(3))
		Case func_entityshininess
			EntityShininess(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1))
		Case func_entityalpha
			EntityAlpha(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1))
		Case func_entitytexture
			EntityTexture(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToIntArg(3))
		Case func_createtexture
			G_SE_ReturnVal = CreateTexture(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2), SE_AND_LUA_ToIntArg(3))
			G_SE_ReturnType = SE_INT
		Case func_loadtexture
			G_SE_ReturnVal = LoadTexture(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_scaletexture
			ScaleTexture(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
		Case func_texturewidth
			G_SE_ReturnVal = TextureWidth(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_textureheight
			G_SE_ReturnVal = TextureHeight(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_entityparent
			EntityParent(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			
		Case func_enablecamerashakeondamage
			Server\CameraShakeOnDamage = SE_GetParamInt()
		Case func_enablenoclipanticheat
			Server\NoclipAnticheat = SE_GetParamInt()
		Case func_entityscalex
			G_SE_ReturnVal = EntityScaleX2(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_entityscaley
			G_SE_ReturnVal = EntityScaleY2(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_entityscalez
			G_SE_ReturnVal = EntityScaleZ2(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
			
		Case func_meshwidth
			G_SE_ReturnVal = MeshWidth(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_meshheight
			G_SE_ReturnVal = MeshHeight(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
		Case func_meshdepth
			G_SE_ReturnVal = MeshDepth(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_FLOAT
			
		Case func_setcustommap
			Server\Custommap = SE_AND_LUA_ToStringArg(0)
			RestartServer()
		Case func_pointentity
			PointEntity(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_getplayerhitbox
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\Hitbox
			G_SE_ReturnType = SE_INT
		Case func_entitydistance
			G_SE_ReturnVal = EntityDistance(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_getplayercamera
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\Camera
			G_SE_ReturnType = SE_INT
		Case func_deltayaw
			G_SE_ReturnVal = DeltaYaw(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_deltapitch
			G_SE_ReturnVal = DeltaPitch(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		; coll
		Case func_resetentity
			ResetEntity(SE_AND_LUA_ToIntArg(0))
		Case func_entityradius
			EntityRadius(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1))
		Case func_entitybox
			EntityBox(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToFloatArg(5), SE_AND_LUA_ToFloatArg(6))
		Case func_entitycollisiontype
			EntityType(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_entitypickmode
			EntityPickMode(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_entitycollided
			G_SE_ReturnVal = EntityCollided(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_countcollisions
			G_SE_ReturnVal = CountCollisions(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		Case func_collisionx
			G_SE_ReturnVal = CollisionX(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisiony
			G_SE_ReturnVal = CollisionY(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionz
			G_SE_ReturnVal = CollisionZ(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionnx
			G_SE_ReturnVal = CollisionNX(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionny
			G_SE_ReturnVal = CollisionNY(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionnz
			G_SE_ReturnVal = CollisionNZ(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_FLOAT
		Case func_collisionentity
			G_SE_ReturnVal = CollisionEntity(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_getentitycollisiontype
			G_SE_ReturnVal = GetEntityType(SE_AND_LUA_ToIntArg(0))
			G_SE_ReturnType = SE_INT
		;
		Case func_getplayeroriginalx
			G_SE_ReturnVal = Player[SE_GetParamInt()]\x
			G_SE_ReturnType = SE_FLOAT
		Case func_getplayeroriginaly
			G_SE_ReturnVal = Player[SE_GetParamInt()]\y
			G_SE_ReturnType = SE_FLOAT
		Case func_getplayeroriginalz
			G_SE_ReturnVal = Player[SE_GetParamInt()]\z
			G_SE_ReturnType = SE_FLOAT
		Case func_setplayerfakeroom
			playerid = SE_GetParamInt()
			Player[playerid]\PlayerRoomID = SE_GetParamInt()
			
			mp_SetPlayerRoomID(Player[playerid], Room[Player[playerid]\PlayerRoomID])
		Case func_changeplayersteamid
			ChangePlayerSteamID(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_addplayerspawnposition
			spawnpointer.SpawnPoint = New SpawnPoint
			spawnpointer\playerid = SE_AND_LUA_ToIntArg(0)
			spawnpointer\room = SE_AND_LUA_ToStringArg(1)
			spawnpointer\x = SE_AND_LUA_ToFloatArg(2)
			spawnpointer\y = SE_AND_LUA_ToFloatArg(3)
			spawnpointer\z = SE_AND_LUA_ToFloatArg(4)
			spawnpointer\role = -1
			
			G_SE_ReturnVal = Handle(spawnpointer)
			G_SE_ReturnType = SE_INT
			
		Case func_addclassspawnposition
			spawnpointer.SpawnPoint = New SpawnPoint
			spawnpointer\role = SE_AND_LUA_ToIntArg(0)
			spawnpointer\room = SE_AND_LUA_ToStringArg(1)
			spawnpointer\x = SE_AND_LUA_ToFloatArg(2)
			spawnpointer\y = SE_AND_LUA_ToFloatArg(3)
			spawnpointer\z = SE_AND_LUA_ToFloatArg(4)
			spawnpointer\playerid = -1
			
			G_SE_ReturnVal = Handle(spawnpointer)
			G_SE_ReturnType = SE_INT
		Case func_removespawnposition
			Delete Object.SpawnPoint(SE_AND_LUA_ToIntArg(0))
		Case func_isvalidspawnposition
			G_SE_ReturnVal = (Object.SpawnPoint(SE_AND_LUA_ToIntArg(0)) <> Null)
			G_SE_ReturnType = SE_INT
		Case func_ShouldPlayerAnnouncement
			Player[SE_AND_LUA_ToIntArg(0)]\ShouldAnnouncement = SE_AND_LUA_ToIntArg(1)
		Case func_changeplayersize
			ChangePlayerSize(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_getplayersize
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\Size
			G_SE_ReturnType = SE_FLOAT
		Case func_lerp
			G_SE_ReturnVal = curvevalue(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
			G_SE_ReturnType = SE_FLOAT
		Case func_lerpangle
			G_SE_ReturnVal = curveangle(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
			G_SE_ReturnType = SE_FLOAT
		Case func_dllfunctionvoid_0
			DllFunctionVoid_0()
		Case func_dllfunctionvoid_1
			DllFunctionVoid_1(SE_AND_LUA_ToStringArg(0))
		Case func_dllfunctionvoid_2
			DllFunctionVoid_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
		Case func_dllfunctionvoid_3
			DllFunctionVoid_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
		Case func_dllfunctionvoid_4
			DllFunctionVoid_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
		Case func_dllfunctionvoid_5
			DllFunctionVoid_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
		Case func_dllfunctionvoid_6
			DllFunctionVoid_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
		Case func_dllfunctionvoid_7
			DllFunctionVoid_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
		Case func_dllfunctionvoid_8
			DllFunctionVoid_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
		Case func_dllfunctionvoid_9
			DllFunctionVoid_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			
		Case func_dllfunctionint_0
			G_SE_ReturnVal = DllFunctionint_0()
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_1
			G_SE_ReturnVal = DllFunctionint_1(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_2
			G_SE_ReturnVal = DllFunctionint_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_3
			G_SE_ReturnVal = DllFunctionint_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_4
			G_SE_ReturnVal = DllFunctionint_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_5
			G_SE_ReturnVal = DllFunctionint_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_6
			G_SE_ReturnVal = DllFunctionint_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_7
			G_SE_ReturnVal = DllFunctionint_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_8
			G_SE_ReturnVal = DllFunctionint_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_INT
		Case func_dllfunctionint_9
			G_SE_ReturnVal = DllFunctionint_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			G_SE_ReturnType = SE_INT
			
		Case func_dllfunctionfloat_0
			G_SE_ReturnVal = DllFunctionfloat_0()
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_1
			G_SE_ReturnVal = DllFunctionfloat_1(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_2
			G_SE_ReturnVal = DllFunctionfloat_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_3
			G_SE_ReturnVal = DllFunctionfloat_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_4
			G_SE_ReturnVal = DllFunctionfloat_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_5
			G_SE_ReturnVal = DllFunctionfloat_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_6
			G_SE_ReturnVal = DllFunctionfloat_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_7
			G_SE_ReturnVal = DllFunctionfloat_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_8
			G_SE_ReturnVal = DllFunctionfloat_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_float
		Case func_dllfunctionfloat_9
			G_SE_ReturnVal = DllFunctionfloat_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			G_SE_ReturnType = SE_float
			
		Case func_dllfunctionstring_0
			G_SE_ReturnVal = DllFunctionstring_0()
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_1
			G_SE_ReturnVal = DllFunctionstring_1(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_2
			G_SE_ReturnVal = DllFunctionstring_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_3
			G_SE_ReturnVal = DllFunctionstring_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_4
			G_SE_ReturnVal = DllFunctionstring_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_5
			G_SE_ReturnVal = DllFunctionstring_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_6
			G_SE_ReturnVal = DllFunctionstring_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_7
			G_SE_ReturnVal = DllFunctionstring_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_8
			G_SE_ReturnVal = DllFunctionstring_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_string
		Case func_dllfunctionstring_9
			G_SE_ReturnVal = DllFunctionstring_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			G_SE_ReturnType = SE_string
		
			
		Case func_connecttocentralserver
			ip$ = SE_GetParamString()
			port% = SE_GetParamInt()
			AddCentralServer(IP, Port)
		Case func_reconnecttocentralserver
			ConnectToCentralServer(CENTRAL_SERVER_IP, CENTRAL_SERVER_PORT)
		Case func_createsteaminstance
			steamid% = SE_GetParamInt()
			tag$ = SE_GetParamString()
			cr% = SE_GetParamInt()
			cg% = SE_GetParamInt()
			cb% = SE_GetParamInt()
			CreateSteamInstance(steamid, tag, cr, cg, cb)
		Case func_removesteaminstance
			steamid% = SE_GetParamInt()
			For SI.SteamInstances = Each SteamInstances
				If SI\steamid = steamid Then Delete si
			Next
		Case func_getsteaminstancetag
			G_SE_ReturnVal$ = ""
			G_SE_ReturnType = SE_STRING
			steamid% = SE_GetParamInt()
			For SI.SteamInstances = Each SteamInstances
				If SI\steamid = steamid Then 
					G_SE_ReturnVal$ = SI\tag
					Exit
				EndIf
			Next
		Case func_setdescriptionline
			descid% = SE_GetParamInt()
			descinfo$ = SE_GetParamString()
			Server\desclines[descid] = descinfo
			
		Case func_getdescriptionline
			G_SE_ReturnVal = Server\desclines[SE_GetParamInt()]
			G_SE_ReturnType = SE_INT
			
		Case func_setmaxspawnplayers
			Server\MaxSpawnPlayers = SE_GetParamInt()
		Case func_getmaxspawnplayers
			G_SE_ReturnVal = Server\MaxSpawnPlayers
			G_SE_ReturnType = SE_INT
			
		Case func_enableoldresponse
			Server\OldConnectionResponse = SE_GetParamInt()
		Case func_closeapp
			End
		; mysql
		
		Case func_mysql_OpenSQLStream
			hostName$ = SE_GetParamString()
			port% = SE_GetParamInt()
			userName$ = SE_GetParamString()
			password$ = SE_GetParamString()
			dataBase$ = SE_GetParamString()
			flag% = SE_GetParamInt()
			G_SE_ReturnVal = OpenSQLStream(hostName$, port%, userName$, passWord$, dataBase$, flag%)
			G_SE_ReturnType = SE_INT
			
		Case func_mysql_SQLConnected
			G_SE_ReturnVal = SQLConnected(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		Case func_mysql_SQLQuery
			streamid% = SE_GetParamInt()
			query$ = SE_GetParamString()
			
			
			G_SE_ReturnVal = SQLQuery(streamid, query)
			G_SE_ReturnType = SE_INT
			;debuglog "Query: "+query+" result: "+G_SE_ReturnVal
			
		Case func_mysql_SQLRowCount
			G_SE_ReturnVal = SQLRowCount(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		Case func_mysql_SQLFetchRow
			G_SE_ReturnVal = SQLFetchRow(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		Case func_mysql_SQLFieldCount
			G_SE_ReturnVal = SQLFieldCount(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		
		Case func_mysql_ReadSQLField
			rowid% = SE_GetParamInt()
			fieldname$ = SE_GetParamString()
			G_SE_ReturnVal = ReadSQLField(rowid, fieldname)
			G_SE_ReturnType = SE_STRING
			
		Case func_mysql_ReadSQLFieldIndex
			rowid% = SE_GetParamInt()
			fieldindex% = SE_GetParamInt()
			G_SE_ReturnVal = ReadSQLFieldIndex(rowid, fieldindex)
			G_SE_ReturnType = SE_STRING
			
		Case func_mysql_FreeSQLQuery
			G_SE_ReturnVal = FreeSQLQuery(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		Case func_mysql_FreeSQLRow
			G_SE_ReturnVal = FreeSQLRow(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		Case func_mysql_CloseSQLStream
			G_SE_ReturnVal = CloseSQLStream(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		; Sqlite
		Case func_sqlite_OpenDatabase
			filename$ = SE_GetParamString()
			openmode% = SE_GetParamInt()
			MakeCurrent% = SE_GetParamInt()
			ErrorsAreFatal% = SE_GetParamInt()
			G_SE_ReturnVal = OpenDatabase(filename, openmode, makecurrent, errorsarefatal)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_CloseDatabase
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal% = SE_GetParamInt()
			G_SE_ReturnVal = CloseDatabase(databasehandle, errorsarefatal)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_SetDatabaseTimeout
			TimeOutInMilliSecs = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal% = SE_GetParamInt()
			G_SE_ReturnVal = SetDatabaseTimeout(TimeOutInMilliSecs, databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_DatabaseVersion
			G_SE_ReturnVal = DatabaseVersion()
			G_SE_ReturnType = SE_STRING

		Case func_sqlite_LastRowIDInserted
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = LastRowIDInserted(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_RowsChangedByLastStatement
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = RowsChangedByLastStatement(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_RowsChangedThisSession
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = RowsChangedThisSession(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_AutoCommitIsOn
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = AutoCommitIsOn(databasehandle)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_BeginTransaction
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = BeginTransaction(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_CommitTransaction
			KeepTransactionOpen = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = CommitTransaction(KeepTransactionOpen, databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_RollbackTransaction
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = RollbackTransaction(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_LastDatabaseErrorCode
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = LastDatabaseErrorCode(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_LastDatabaseErrorMessage
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = LastDatabaseErrorMessage(databasehandle)
			G_SE_ReturnType = SE_STRING

		Case func_sqlite_InterruptDatabase
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = InterruptDatabase(databasehandle)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_ExecuteSQL
			SQL$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = ExecuteSQL(SQL, databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_PrepareSQL
			SQL$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			MakeCurrent = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = PrepareSQL(SQL, databasehandle, MakeCurrent, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_GetNextDataRow
			databasehandle = SE_GetParamInt()
			AutomaticallyFinalise = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = GetNextDataRow(databasehandle, AutomaticallyFinalise, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_FinaliseSQL
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = FinaliseSQL(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_ResetSQL
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = ResetSQL(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_SQLHasExpired
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLHasExpired(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_GetDatabaseHandleFromStatementHandle
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetDatabaseHandleFromStatementHandle(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_GetColumnCount
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnCount(databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_GetColumnName
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnName(columnindex, databasehandle)
			G_SE_ReturnType = SE_STRING
			
		Case func_sqlite_GetColumnType
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnType(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_GetColumnDeclaredType
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnDeclaredType(columnindex, databasehandle)
			G_SE_ReturnType = SE_STRING
			
		Case func_sqlite_GetColumnSize
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnSize(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_GetColumnValueAsInteger
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsInteger(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_GetColumnValueAsFloat
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsFloat(columnindex, databasehandle)
			G_SE_ReturnType = SE_FLOAT
			
		Case func_sqlite_GetColumnValueAsString
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsString(columnindex, databasehandle)
			G_SE_ReturnType = SE_STRING
			
		Case func_sqlite_GetColumnValueAsBlob
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsBlob(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_SQLParameterCount
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLParameterCount(databasehandle)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_SQLParameterName
			ParameterIndex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLParameterName(ParameterIndex, databasehandle)
			G_SE_ReturnType = SE_STRING

		Case func_sqlite_SQLParameterIndex
			ParameterName$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLParameterIndex(ParameterName, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_BindSQLParameterAsNull
			ParameterIndex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsNull(ParameterIndex, databasehandle)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_BindSQLParameterAsInteger
			ParameterIndex = SE_GetParamInt()
			sqlvalue$ = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsInteger(ParameterIndex, sqlvalue, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_BindSQLParameterAsFloat
			ParameterIndex = SE_GetParamInt()
			sqlvalue$ = SE_GetParamFloat()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsFloat(ParameterIndex, sqlvalue, databasehandle)
			G_SE_ReturnType = SE_INT

		Case func_sqlite_BindSQLParameterAsString
			ParameterIndex = SE_GetParamInt()
			sqlvalue$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsString(ParameterIndex, sqlvalue, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_BindSQLParameterAsBlob
			ParameterIndex = SE_GetParamInt()
			BlobHandle = SE_GetParamInt()
			LengthOfBlob = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsBlob(ParameterIndex, BlobHandle, LengthOfBlob, databasehandle)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_TransferSQLBindings
			databasehandle = SE_GetParamInt()
			handle2 = SE_GetParamInt()
			G_SE_ReturnVal = TransferSQLBindings(databasehandle, handle2)
			G_SE_ReturnType = SE_INT
			
		Case func_sqlite_SQLite3_ErrorHasOccurred
			declarename$ = SE_GetParamString()
			ErrorsAreFatal% = SE_GetParamInt()
			sqlMessage$ = SE_GetParamString()
			G_SE_ReturnVal = SQLite3_ErrorHasOccurred(declarename, ErrorsAreFatal, sqlMessage)
			G_SE_ReturnType = SE_INT
		; ======================================================================================
		
		Case func_setplayervelocity
			Player[SE_AND_LUA_ToIntArg(0)]\CurrSpeed = SE_AND_LUA_ToFloatArg(1)
		Case func_getplayervelocity
			G_SE_ReturnVal = Player[SE_GetParamInt()]\CurrSpeed
			G_SE_ReturnType = SE_FLOAT
		Case func_getplayerhandcuff
			G_SE_ReturnVal = Player[SE_GetParamInt()]\HandCuffed
			G_SE_ReturnType = SE_INT
		Case func_setplayerhandcuff
			playerid = SE_GetParamInt()
			Player[playerid]\handcuffed = SE_GetParamInt()
			
			If Player[playerid]\handcuffed Then
				For it.items = Each items
					If it\picker = playerid Then
						PlayerDropItem(it)
					EndIf
				Next
			EndIf
		Case func_getplayersteamid
			
			G_SE_ReturnVal = Player[SE_GetParamInt()]\PlayerSteamID
			G_SE_ReturnType = SE_INT
		Case func_giveplayerhealth
			playerid = SE_GetParamInt()
			GivePlayerHealth(playerid, SE_GetParamInt())
		Case func_setplayerspeedmult
			playerid = SE_GetParamInt()
			Player[playerid]\speedmult = SE_GetParamFloat()
		Case func_getplayerspeedmult
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\speedmult
			G_SE_ReturnType = SE_FLOAT
		Case func_sendblinktimerforplayer
			playerid = SE_GetParamInt()
			mp_sendblinktimer(playerid, SE_GetParamFloat())
		Case func_zlib_compress
			G_SE_ReturnVal = ZipApi_Compress(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_zlib_uncompress
			G_SE_ReturnVal = ZipApi_Uncompress(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
			G_SE_ReturnType = SE_INT
		Case func_deactivatewarheads
			deactivatewarheads()
		Case func_spawnmtf
			gameinfo\b\Teams = 0
			spawnmtf()
		Case func_spawnchaos
			gameinfo\b\Teams = 1
			spawnmtf()
		Case func_activatewarheads
			activatewarheads("NULL")
		Case func_getplayerzone
			playerid = SE_GetParamInt()
			If IsValidPlayer(playerid) Then
				G_SE_ReturnVal = GetPlayerZone(playerid)
				G_SE_ReturnType = SE_INT
			EndIf
		Case func_getbreachtimer
			G_SE_ReturnVal = gameinfo\b\BreachTimer-MilliSecs()
			G_SE_ReturnType = SE_INT
		Case func_addaccessversion
			AddVersion(SE_GetParamString())
		Case func_getplayerinjuries
			G_SE_ReturnVal = Player[SE_GetParamInt()]\Injuries
			G_SE_ReturnType = SE_FLOAT
		Case func_setplayerinjuries
			playerid = SE_GetParamInt()
			Player[playerid]\Injuries = SE_GetParamFloat()
		Case func_getplayerheadpitch
			G_SE_ReturnVal = Player[SE_GetParamInt()]\BonePitch
			G_SE_ReturnType = SE_FLOAT
		Case func_setplayerheadpitch
			playerid = SE_GetParamInt()
			Player[playerid]\BonePitch = SE_GetParamFloat()
			
		Case func_createentitycamera
			G_SE_ReturnVal = CreateCamera()
			G_SE_ReturnType = SE_INT
			CameraViewport G_SE_ReturnVal, 0, 0, 1920, 1080
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
			G_SE_ReturnVal = EntityX(SE_GetParamInt(),True)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityy
			G_SE_ReturnVal = EntityY(SE_GetParamInt(),True)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityz
			G_SE_ReturnVal = EntityZ(SE_GetParamInt(),True)
			G_SE_ReturnType = SE_FLOAT
		Case func_entitypitch
			G_SE_ReturnVal = EntityPitch(SE_GetParamInt(),True)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityyaw
			G_SE_ReturnVal = EntityYaw(SE_GetParamInt(),True)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityroll
			G_SE_ReturnVal = EntityRoll(SE_GetParamInt(),True)
			G_SE_ReturnType = SE_FLOAT
		Case func_entitypick
			ent = SE_GetParamInt()
			G_SE_ReturnVal = EntityPick(ent, SE_GetParamFloat())
			G_SE_ReturnType = SE_INT
			
		Case func_positionentity
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			z# = SE_GetParamFloat()
			PositionEntity ent, x,y,z,SE_GetParamInt()
		Case func_rotateentity
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			z# = SE_GetParamFloat()
			RotateEntity ent, x,y,z,SE_GetParamInt()
		Case func_moveentity
			If SE_AND_LUA_ToIntArg(4) = 0 Then
				ent = SE_GetParamInt()
				x# = SE_GetParamFloat()
				y# = SE_GetParamFloat()
				z# = SE_GetParamFloat()
				MoveEntity ent, x,y,z
			Else
				ent = SE_GetParamInt()
				x# = SE_GetParamFloat()
				y# = SE_GetParamFloat()
				z# = SE_GetParamFloat()
				TranslateEntity ent, x,y,z
			EndIf
		Case func_scaleentity
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			ScaleEntity ent, x,x,x
		Case func_setentitycollision
			ent = SE_GetParamInt()
			EntityType ent, HIT_PLAYER*SE_GetParamInt()
		Case func_removeentity
			FreeEntity SE_GetParamInt()
		Case func_setanimtime
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			SetAnimTime ent, x
			
		Case func_getdelta
			G_SE_ReturnVal = FPSFactor
			G_SE_ReturnType = SE_FLOAT
		Case func_sendrawpacket
			playerid = SE_GetParamInt()
			bank = SE_GetParamInt()
			If Player[playerid] <> Null Then
				udp_WriteByte M_RAWPACKET
				udp_WriteByte 1
				udp_WriteBytes bank, 0, BankSize(bank)
				udp_SendMessage(playerid)
			EndIf
		Case func_sendscript
			playerid = SE_GetParamInt()
			filename$ = SE_GetParamString()
			savepath$ = SE_GetParamString()
			SendFile(playerid, filename, savepath, True)
		Case func_sendfile
			playerid = SE_GetParamInt()
			filename$ = SE_GetParamString()
			savepath$ = SE_GetParamString()
			SendFile(playerid, filename, savepath, False, SE_AND_LUA_ToIntArg(3), SE_AND_LUA_ToIntArg(4))

		Case func_setgamestate
			Server\GameState = SE_GetParamString()
			AddLog("Game state changed to "+Server\GameState, 0, False)
		Case func_setservername
			Server\Servername = SE_GetParamString()
			AddLog("Server name changed to "+Server\Servername, 0, False)
		Case func_setserverdescription
			Server\Description = SE_GetParamString()
			AddLog("Description changed to "+Server\description, 0, False)
		Case func_setserverpassword
			RCON_Password(SE_GetParamString())
			AddLog("Password changed to "+Server\password, 0, False)
		Case func_setservercurrentbreachtimer
			gameinfo\b\BreachTimer = MilliSecs()+SE_GetParamInt()
			AddLog("Breach timer changed to "+Str(gameinfo\b\BreachTimer-MilliSecs()), 0, False)
		Case func_setserverexplodetimeout
			gameinfo\b\ExplodeTimeout = MilliSecs()+SE_GetParamInt()
		Case func_getserverexplodetimeout
			G_SE_ReturnVal = gameinfo\b\ExplodeTimeout-MilliSecs()
			G_SE_ReturnType = SE_INT
		Case func_getserverspawntimeout
			G_SE_ReturnVal = gameinfo\b\NTFTimeout-MilliSecs()
			G_SE_ReturnType = SE_INT
		Case func_setserverspawntimeout
			gameinfo\b\NTFTimeout = MilliSecs()+SE_GetParamInt()
		Case func_setservernocheat
			Server\Nocheat = SE_GetParamInt()
			AddLog("No cheat changed to "+Server\nocheat, 0, False)
		Case func_getplayerhealth
			G_SE_ReturnVal = Player[SE_GetParamInt()]\Health
			G_SE_ReturnType = SE_INT
		Case func_setplayerfakehealth
			playerid = SE_GetParamInt()
			health = SE_GetParamInt()
			Player[playerid]\Health = health
		Case func_disableautokick
			Server\AutoKick = Not Server\AutoKick
		Case func_reloadapp
			ReloadApplication()
		Case func_hexstring
			Local towrite, strings$ = SE_GetParamString()
			For i = 1 To Len(strings)
			towrite = towrite + (Asc(Mid(strings, i, 1)) Shl (8*(i-1)))
			Next
			G_SE_ReturnVal = towrite
			G_SE_ReturnType = SE_INT
		Case func_removetimer
			RemoveTimer(Object.timers(SE_GetParamInt()))
		Case func_createtimer
			timername$ = SE_GetParamString()
			interval = SE_GetParamInt()
			loop = SE_GetParamInt()
			Local b.bs = CreateByteStream(372)
			Local identifiers$
			
			If CurrentLuaState = 0 Then
				For i = 3 To SE_ARGUMENTS_NUMBER-1
					If SE_ArgType(i) <> SE_NULL Then
						Select SE_ArgType(i)
							Case SE_INT
								identifiers = identifiers+"i"
								ByteStreamWriteInt(b,SE_AND_LUA_ToIntArg(i))
							Case SE_FLOAT
								identifiers = identifiers+"f"
								ByteStreamWriteFloat(b,SE_AND_LUA_ToFloatArg(i))
							Case SE_STRING
								identifiers = identifiers+"s"
								ByteStreamWriteString(b,SE_AND_LUA_ToStringArg(i))
						End Select
					EndIf
				Next
				G_SE_ReturnVal = SetTimer(SE_CURRENT_FUNCTION\Script, timername, interval, loop, identifiers, b)
			Else
				For i = 4 To 32
					If Not SLUA_IS_NONE_OR_NIL(CurrentLuaState, i) Then
						If SLUA_IS_INTEGER(CurrentLuaState, i) Then
							identifiers = identifiers+"i"
							ByteStreamWriteInt(b,SLUA_TO_INTEGER(CurrentLuaState, i))
						ElseIf SLUA_IS_NUMBER(CurrentLuaState, i)
							identifiers = identifiers+"f"
							ByteStreamWriteFloat(b,SLUA_TO_NUMBER(CurrentLuaState, i))
						ElseIf SLUA_IS_STRING(CurrentLuaState, i)
							identifiers = identifiers+"s"
							ByteStreamWriteString(b,SLUA_TO_STRING(CurrentLuaState, i))
						EndIf
					Else
						Exit
					EndIf
				Next
				G_SE_ReturnVal = SetTimer(Null, timername, interval, loop, identifiers, b, CurrentLuaState)
			EndIf
			G_SE_ReturnType = SE_INT
		Case func_serverwritebyte
			ByteStreamWriteChar(scriptbstream,SE_GetParamInt())
		Case func_serverwriteshort
			ByteStreamWriteShort(scriptbstream,SE_GetParamInt())
		Case func_serverwriteint
			ByteStreamWriteInt(scriptbstream,SE_GetParamInt())
		Case func_serverwritefloat
			ByteStreamWriteFloat(scriptbstream,SE_GetParamFloat())
		Case func_debug
			DebugLog SE_GetParamString()
		Case func_eof
			G_SE_ReturnVal = Eof(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_asc
			G_SE_ReturnVal = Asc(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_chr
			G_SE_ReturnVal = Chr(SE_GetParamInt())
			G_SE_ReturnType = SE_STRING
		Case func_filetype
			G_SE_ReturnVal = FileType(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_filesize
			G_SE_ReturnVal = FileSize(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_seekfile
			filehandle = SE_GetParamInt()
			seek = SE_GetParamInt()
			SeekFile(filehandle, seek)
		Case func_filepos
			G_SE_ReturnVal = FilePos(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_catcherror
			G_SE_ReturnVal = ErrorLog()
			G_SE_ReturnType = SE_STRING
		Case func_createerror
			adderrorlog(SE_AND_LUA_ToStringArg(0))
		Case func_getpacketindex
			Select SE_GetParamString()
				Case "voice"
					G_SE_ReturnVal = M_VOICE
			End Select
			G_SE_ReturnType = SE_INT
		Case func_sendrawdata
			playerid = SE_GetParamInt()
			packet = SE_GetParamInt()
			bnk = SE_GetParamInt()
			offset = SE_GetParamInt()
			count = SE_GetParamInt()
			Select packet
			Case M_VOICE
			;debuglog "writed "+BankSize(bnk)+" to "+playerid+" offset = "+offset+" count = "+count
			udp_WriteByte(packet)
			udp_WriteBytes(GetByteStreamData(scriptbstream), 0, GetByteStreamDataSize(scriptbstream))
			udp_WriteBytes(bnk, offset, count)
			udp_SendMessage(playerid)
			G_SE_ReturnType = SE_INT
			G_SE_ReturnVal = True
			ByteStreamReset(scriptbstream)
			Default:
			G_SE_ReturnType = SE_INT
			G_SE_ReturnVal = False
			End Select
		Case func_writebytes
			bank = SE_GetParamInt()
			stream = SE_GetParamInt()
			offset = SE_GetParamInt()
			count = SE_GetParamInt()
			G_SE_ReturnVal = WriteBytes(bank, stream, offset, count)
			G_SE_ReturnType = SE_INT
		Case func_readbytes
			bank = SE_GetParamInt()
			stream = SE_GetParamInt()
			offset = SE_GetParamInt()
			count = SE_GetParamInt()
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
			count = SE_GetParamInt()
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
			
		Case func_disablelobby
			Server\LobbyDisabled = Not Server\LobbyDisabled
			Server\IsStarted = Server\LobbyDisabled
		Case func_removevar
			var_remove(SE_GetParamString())
		Case func_setvarvalue
			var_setvalue(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1))
		Case func_getvarvalue
			G_SE_ReturnVal = var_getvalue(SE_GetParamString())
			G_SE_ReturnType = SE_STRING
		Case func_removeplayervar
			player_var_remove(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1))
		Case func_setplayervarvalue
			player_var_setvalue(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToStringArg(2))
		Case func_getplayervarvalue
			G_SE_ReturnVal = player_var_getvalue(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_STRING
		Case func_addarrayelements
			SE_Array_AddElements(SE_ArrayArg(0), SE_IntArg(1), SE_IntArg(2))
		Case func_updateobjects
			objects_stream_update()
		Case func_getobjectentity
			G_SE_ReturnVal = multiplayer_Object[SE_GetParamInt()]\ent
			G_SE_ReturnType = SE_INT
		Case func_setobjectvisibleforplayer
			object_update_visible(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_preparemodelidentifier
			PrepareModelIdentifier(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1))
		Case func_setobjectinterpolation
			object_update_lerp(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_setplayersspawnposition
			Server\pspawnname = SE_GetParamString()
			Server\pspawnx = SE_GetParamString()
			Server\pspawny = SE_GetParamString()
			Server\pspawnz = SE_GetParamString()
		Case func_setplayerfakeradiowave
			playerid = SE_GetParamInt()
			Player[playerid]\CurrentRadio = SE_GetParamInt()
		Case func_getplayerholdinggun
			G_SE_ReturnVal = Player[SE_GetParamInt()]\UsedWeapon
			G_SE_ReturnType = SE_INT
		Case func_getplayerradiowave
			G_SE_ReturnVal = Player[SE_GetParamInt()]\CurrentRadio
			G_SE_ReturnType = SE_INT
		Case func_getplayerroomid
			G_SE_ReturnVal = Player[SE_GetParamInt()]\PlayerRoomID
			G_SE_ReturnType = SE_INT
		Case func_getplayernvg
			G_SE_ReturnVal = Player[SE_GetParamInt()]\WearingNightVision
			G_SE_ReturnType = SE_INT
		Case func_getplayeranimation
			G_SE_ReturnVal = Player[SE_GetParamInt()]\PLAYER_MOVE
			G_SE_ReturnType = SE_INT
		Case func_getplayerhazmat
			G_SE_ReturnVal = Player[SE_GetParamInt()]\WearingHazmat
			G_SE_ReturnType = SE_INT
		Case func_getplayergasmask
			G_SE_ReturnVal = Player[SE_GetParamInt()]\WearingGasMask 
			G_SE_ReturnType = SE_INT
		Case func_getplayervest
			G_SE_ReturnVal = Player[SE_GetParamInt()]\WearingVest
			G_SE_ReturnType = SE_INT
		Case func_getplayerreadystate
			G_SE_ReturnVal = Player[SE_GetParamInt()]\Ready
			G_SE_ReturnType = SE_INT
		Case func_getplayerblinktimer
			G_SE_ReturnVal = Player[SE_GetParamInt()]\BlinkTimer
			G_SE_ReturnType = SE_FLOAT
		Case func_callglobalfunction
			For script.ScriptsThread = Each ScriptsThread
				If Lower(script\scriptname) = Lower(SE_AND_LUA_ToStringArg(0)) Then
					If script\luathread = 0 Then
						For i = 2 To SE_ARGUMENTS_NUMBER
							If SE_ArgType(i) <> SE_NULL Then public_addparam(0, SE_AND_LUA_ToStringArg(i), SE_ArgType(i))
						Next
						public_update_by_func(SE_FindFunc(script\scriptthread, Lower(SE_AND_LUA_ToStringArg(1))), True)
						public_clear
						G_SE_ReturnVal = SE_GetReturnValue()
						G_SE_ReturnType = SE_RETURN_VALUE\ValueType
					Else
					
						For i = 3 To 32
							If Not SLUA_IS_NONE_OR_NIL(script\luathread, i) Then
								public_addparam(0, SLUA_TO_STRING(script\luathread, i), SLUA_TYPE(script\luathread, i))
							Else
								Exit
							EndIf
						Next
						
						public_update_by_func(Null, False, script\luathread, Lower(SE_AND_LUA_ToStringArg(1)))
						public_clear()
						
						G_SE_ReturnVal = SE_GetReturnValue()
						G_SE_ReturnType = SE_RETURN_VALUE\ValueType
						
					EndIf
					Exit
				EndIf
			Next
		Case func_callfunction
			If CurrentLuaState = 0 Then
				For i = 1 To SE_ARGUMENTS_NUMBER
					If SE_ArgType(i) <> SE_NULL Then public_addparam(0, SE_AND_LUA_ToStringArg(i), SE_ArgType(i))
				Next
				public_update_by_func(SE_FindFunc(SE_CURRENT_FUNCTION\Script, Lower(SE_AND_LUA_ToStringArg(0))), True)
				public_clear
				G_SE_ReturnVal = SE_GetReturnValue()
				G_SE_ReturnType = SE_RETURN_VALUE\ValueType
			Else
				For i = 1 To 32
					If Not SLUA_IS_NONE_OR_NIL(CurrentLuaState, i) Then
						public_addparam(0, SLUA_TO_STRING(CurrentLuaState, i), SLUA_TYPE(CurrentLuaState, i))
					Else
						Exit
					EndIf
				Next
				
				public_update_by_func(Null, False, State, Lower(SE_AND_LUA_ToStringArg(1)))
				public_clear()
				
				G_SE_ReturnVal = SE_GetReturnValue()
				G_SE_ReturnType = SE_RETURN_VALUE\ValueType
			EndIf
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
			pv = CreatePivot()
			PositionEntity pv, SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2)
			RotateEntity pv, SE_AND_LUA_ToFloatArg(7), SE_AND_LUA_ToFloatArg(6), 0
			MoveEntity pv, SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToFloatArg(5)
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
			pv = CreatePivot()
			pv2 = CreatePivot()
			PositionEntity pv, SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2)
			PositionEntity pv2, SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToFloatArg(5)
			PointEntity pv, pv2
			pointyaw# = EntityYaw(pv)
			pointpitch# = EntityPitch(pv)
			FreeEntity pv
			FreeEntity pv2
		Case func_getplayertype
			G_SE_ReturnVal = Player[SE_GetParamInt()]\BreachType
			G_SE_ReturnType = SE_INT
		Case func_createsound
			CreateSound(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToFloatArg(5))
		Case func_createplayersound
			CreatePlayerSound(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToFloatArg(5), SE_AND_LUA_ToFloatArg(6))
		Case func_getplayerentity
			G_SE_ReturnVal = Player[SE_GetParamInt()]\Pivot
			G_SE_ReturnType = SE_INT
		Case func_setnotarget
			NoTarget = SE_GetParamInt()
		Case func_setcontained106
			Contained106 = SE_GetParamInt()
		Case func_setremotedooron
			RemoteDoorOn = SE_GetParamInt()
		Case func_setmtftimer
			MTFtimer = SE_GetParamFloat()
			; ==================== Rooms
		Case func_getroomname
			G_SE_ReturnVal = Room[SE_GetParamInt()]\RoomTemplate\Name
			G_SE_ReturnType = SE_STRING
		Case func_getroomentity
			G_SE_ReturnVal = Room[SE_GetParamInt()]\obj
			G_SE_ReturnType = SE_INT
		Case func_isvalidroom
			G_SE_ReturnVal = (Room[SE_GetParamInt()] <> Null)
			G_SE_ReturnType = SE_INT
		Case func_isvalidroomdoor
			G_SE_ReturnVal = 0
			G_SE_ReturnType = SE_INT
			If SE_AND_LUA_ToIntArg(1) >= 0 And SE_AND_LUA_ToIntArg(1) <= 7 Then
				G_SE_ReturnVal = (Room[SE_AND_LUA_ToIntArg(0)]\RoomDoors[SE_AND_LUA_ToIntArg(1)]<>Null)
			EndIf
		Case func_getroomdoor
			G_SE_ReturnVal = Room[SE_AND_LUA_ToIntArg(0)]\RoomDoors[SE_AND_LUA_ToIntArg(1)]\ID
			G_SE_ReturnType = SE_INT
		Case func_getroomobjectentity
			rid = SE_GetParamInt()
			oi = SE_GetParamInt()
			Room[rid]\ObjectStatic[oi] = False
			G_SE_ReturnVal = Room[rid]\Objects[oi]
			G_SE_ReturnType = SE_INT
			; ==================== Doors
		Case func_setdooropenstate
			did = SE_GetParamInt()
			MP_Door[did]\open = SE_GetParamInt()
		Case func_setdoorlock
			did = SE_GetParamInt()
			MP_Door[did]\locked = SE_GetParamInt()
		Case func_getdoorentity
			G_SE_ReturnVal = MP_Door[SE_GetParamInt()]\obj
			G_SE_ReturnType = SE_INT
		Case func_getdoortype
			G_SE_ReturnVal = MP_Door[SE_GetParamInt()]\dir
			G_SE_ReturnType = SE_INT
		Case func_getdooropenstate
			G_SE_ReturnVal = MP_Door[SE_GetParamInt()]\open
			G_SE_ReturnType = SE_INT
		Case func_getdoorlock
			G_SE_ReturnVal = MP_Door[SE_GetParamInt()]\locked
			G_SE_ReturnType = SE_INT
		Case func_setdoorkeycard
			MP_Door[SE_AND_LUA_ToIntArg(0)]\ChangedKeycard = True
			MP_Door[SE_AND_LUA_ToIntArg(0)]\Keycard = SE_AND_LUA_ToIntArg(1)
		Case func_getdoorkeycard
			G_SE_ReturnVal = MP_Door[SE_GetParamInt()]\Keycard
			G_SE_ReturnType = SE_INT
		Case func_setdoorcode
			MP_Door[SE_AND_LUA_ToIntArg(0)]\ChangedCode = True
			MP_Door[SE_AND_LUA_ToIntArg(0)]\Code = SE_AND_LUA_ToStringArg(1)
		Case func_getdoorcode
			G_SE_ReturnVal = MP_Door[SE_GetParamInt()]\Code
			G_SE_ReturnType = SE_STRING
		Case func_isvaliddoor
			G_SE_ReturnVal = (MP_Door[SE_GetParamInt()]<>Null)
			G_SE_ReturnType = SE_INT
			; ==================== Items
		Case func_setitempicker
			playerid = SE_GetParamInt()
			itemid = SE_GetParamInt()
			For it.items = Each items
				If it\ID = itemid Then
					it\picker = playerid
					it\picked = (it\picker<>0)
					Exit
				EndIf
			Next
		Case func_getitemtemplatename
			Local ittemp = SE_GetParamInt()
			For itt.ItemTemplates = Each ItemTemplates
			If itt\templateid = ittemp Then
			G_SE_ReturnVal = itt\name
			G_SE_ReturnType = SE_STRING
			Exit
			EndIf
			Next
		Case func_getitemtemplatetempname
			ittemp = SE_GetParamInt()
			For itt.ItemTemplates = Each ItemTemplates
				If itt\templateid = ittemp Then
					G_SE_ReturnVal = itt\tempname
					G_SE_ReturnType = SE_STRING
					Exit
				EndIf
			Next
		Case func_getitemtemplate
			G_SE_ReturnVal = M_Item[SE_GetParamInt()]\itemtemplate\templateid
			G_SE_ReturnType = SE_INT
		Case func_createitem
			it.Items = Null
			itname$ = SE_GetParamString()
			ittempname$ = SE_GetParamString()
			For itt.Itemtemplates = Each ItemTemplates
				If Lower(itt\name) = Lower(itname) And Lower(itt\tempname) = Lower(ittempname) Then
					it.Items = CreateItem(itt\name, itt\tempname, 9999, 9999, 9999)
					Exit
				EndIf
			Next
			If it <> Null Then
				G_SE_ReturnVal = it\ID
				G_SE_ReturnType = SE_INT
			EndIf
		Case func_removeitem
			RemoveItem(M_Item[SE_GetParamInt()])
		Case func_getitementity
			G_SE_ReturnVal = M_Item[SE_GetParamInt()]\collider
			G_SE_ReturnType = SE_INT
		Case func_getitempicker
			G_SE_ReturnVal = M_Item[SE_GetParamInt()]\picker
			G_SE_ReturnType = SE_INT
		Case func_isvaliditem
			G_SE_ReturnVal = (M_Item[SE_GetParamInt()]<>Null)
			G_SE_ReturnType = SE_INT
			; ==================== NPCS
		Case func_isvalidnpc
			nID = SE_GetParamInt()
			G_SE_ReturnVal = (M_NPC[nID] <> Null)
			G_SE_ReturnType = SE_INT
		Case func_createnpc
			n.NPCs = CreateNPC(SE_GetParamInt(), 9999,9999,9999)
			If n <> Null Then
				G_SE_ReturnVal = n\ID
				G_SE_ReturnType = SE_INT
			EndIf
		Case func_getnpctype
			G_SE_ReturnVal = M_NPC[SE_GetParamInt()]\NPCtype
			G_SE_ReturnType = SE_INT
		Case func_getnpcstate1
			G_SE_ReturnVal = M_NPC[SE_GetParamInt()]\state
			G_SE_ReturnType = SE_FLOAT
		Case func_getnpcstate2
			G_SE_ReturnVal = M_NPC[SE_GetParamInt()]\state2
			G_SE_ReturnType = SE_FLOAT
		Case func_getnpcstate3
			G_SE_ReturnVal = M_NPC[SE_GetParamInt()]\state3
			G_SE_ReturnType = SE_FLOAT
		Case func_setnpcstate
			nID = SE_GetParamInt()
			M_NPC[nID]\State = SE_GetParamFloat()
			M_NPC[nID]\State2 = SE_GetParamFloat()
			M_NPC[nID]\State3 = SE_GetParamFloat()
		Case func_getnpcevent
			G_SE_ReturnVal = M_NPC[SE_GetParamInt()]\EventID
			G_SE_ReturnType = SE_INT
		Case func_getnpcentity
			G_SE_ReturnVal = M_NPC[SE_GetParamInt()]\collider
			G_SE_ReturnType = SE_INT
			; ==================== EVENTS
		Case func_geteventname
			eID = SE_GetParamInt()
			G_SE_ReturnVal = M_Event[eID]\EventName
			G_SE_ReturnType = SE_STRING
		Case func_geteventroomid
			eID = SE_GetParamInt()
			If M_Event[eID]\room <> Null Then G_SE_ReturnVal = M_Event[eID]\room\ID
			G_SE_ReturnType = SE_INT
		Case func_geteventindex
			Local evn$ = SE_GetParamString()
			For e.Events = Each events
				If e\EventName = evn Then
					G_SE_ReturnVal = e\ID
					G_SE_ReturnType = SE_INT
					Exit
				EndIf
			Next
		Case func_geteventstate1
			eID = SE_GetParamInt()
			G_SE_ReturnVal = M_Event[eID]\EventState
			G_SE_ReturnType = SE_FLOAT
		Case func_geteventstate2
			eID = SE_GetParamInt()
			G_SE_ReturnVal = M_Event[eID]\EventState2
			G_SE_ReturnType = SE_FLOAT
		Case func_geteventstate3
			eID = SE_GetParamInt()
			G_SE_ReturnVal = M_Event[eID]\EventState3
			G_SE_ReturnType = SE_FLOAT
		Case func_geteventstr
			eID = SE_GetParamInt()
			G_SE_ReturnVal = M_Event[eID]\EventStr
			G_SE_ReturnType = SE_STRING
		Case func_isvalidevent
			eID = SE_GetParamInt()
			G_SE_ReturnVal = (M_Event[eID] <> Null)
			G_SE_ReturnType = SE_INT
		Case func_removeevent
			RemoveEvent(M_Event[SE_GetParamInt()])
		Case func_seteventstr
			eID = SE_GetParamInt()
			M_Event[eID]\EventStr = SE_GetParamString()
		Case func_seteventstate
			eID = SE_GetParamInt()
			M_Event[eID]\EventState = SE_GetParamFloat()
			M_Event[eID]\EventState2 = SE_GetParamFloat()
			M_Event[eID]\EventState3 = SE_GetParamFloat()
			; ==============================
		Case func_rconcommand
			msg$ = SE_GetParamString()
			Local cmd$ = RCON_findcmd(msg)
			If cmd = "Not found" Then Return AddTextToChat("[RCON] Command not found", ID)
			Local attribute$ = RCON_GetAttribute(msg)
			Select RCON_ExecuteCMD(cmd, attribute)
				Case "gravity"
					AddLog("Gravity changed to "+attribute)
				Case "hostname"
					AddLog("Hostname changed to "+attribute)
				Case "password"
					AddLog("Password changed to "+attribute, 0, False)
			End Select
		Case func_createrocket
			CreateRocket(ROCKET_SPEED, SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3),SE_AND_LUA_ToFloatArg(4), 0)
			For i = 1 To Server\Maxplayers
				If Player[i] <> Null Then
					If Distance3(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), EntityX(Player[i]\Pivot), EntityY(Player[i]\Pivot), EntityZ(Player[i]\Pivot)) < 50 Then
						udp_WriteByte M_CREATEROCKET
						udp_WriteByte 0
						udp_WriteShort 0
						udp_WriteFloat SE_AND_LUA_ToFloatArg(0)
						udp_WriteFloat SE_AND_LUA_ToFloatArg(1)
						udp_WriteFloat SE_AND_LUA_ToFloatArg(2)
						udp_WriteFloat SE_AND_LUA_ToFloatArg(3)
						udp_WriteFloat SE_AND_LUA_ToFloatArg(4)
						udp_SendMessage(i)
					EndIf
				EndIf
			Next
			;Basic System Functions
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
			WriteLine SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1)
		Case func_writeint
			WriteInt SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1)
		Case func_writefloat
			WriteFloat SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1)
		Case func_writeshort
			WriteShort SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1)
		Case func_writebyte
			WriteByte SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1)
		Case func_openfile
			G_SE_ReturnVal = OpenFile(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_readfile
			G_SE_ReturnVal = ReadFile(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_writefile
		
			filename$ = SE_GetParamString()
			G_SE_ReturnVal = WriteFile(filename)
			G_SE_ReturnType = SE_INT
		Case func_setlightvolume
			SecondaryLightOn = SE_GetParamFloat()
		Case func_getlightvolume
			G_SE_ReturnVal = SecondaryLightOn
			G_SE_ReturnType = SE_FLOAT
		Case func_setserverversion
			MP_VERSION = SE_GetParamString()
		Case func_getserverversion
			G_SE_ReturnVal = Str(MP_VERSION)
			G_SE_ReturnType = SE_STRING
		Case func_getplayerversion
			G_SE_ReturnVal = Player[SE_GetParamInt()]\version
			G_SE_ReturnType = SE_STRING
		Case func_setplayerafk
			playerid = SE_GetParamInt()
			afk = SE_GetParamInt()
			If afk < 0 And afk > 0 Then Return
			Player[playerid]\p_byte = (ReadBool(Player[playerid]\p_byte, 0) + (2 * ReadBool(Player[playerid]\p_byte, 1)) + (4 * ReadBool(Player[playerid]\p_byte, 2)) + (8 * afk) + (16 * ReadBool(Player[playerid]\p_byte, 4)) + (32 * ReadBool(Player[playerid]\p_byte, 5)) + (64 * ReadBool(Player[i]\p_byte, 6)))
		Case func_getplayerafk
			G_SE_ReturnVal = ReadBool(Player[SE_GetParamInt()]\p_byte, 3)
			G_SE_ReturnType = SE_INT
		Case func_setplayertextpos
			text_setpos(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))
		Case func_setplayertextstring
			text_settext(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToStringArg(2))
		Case func_setplayertextcolor
			text_setcolor(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_setplayerdrawcolor
			draw_setcolor(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_setplayerdrawpos
			draw_setpos(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))
		Case func_millisecs
			G_SE_ReturnVal = MilliSecs()
			G_SE_ReturnType = SE_INT
		Case func_isplayerconnected
			playerid = SE_GetParamInt()
			
			G_SE_ReturnVal = 0
			If playerid > 0 And playerid < MAX_PLAYERS Then G_SE_ReturnVal = (Player[playerid]<>Null)
			
			G_SE_ReturnType = SE_INT
		Case func_setplayerfogrange
			SetPlayerFogRange(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1))
		Case func_restartserver
			RestartServer()
		Case func_getplayermonitorwidth
			G_SE_ReturnVal = Player[SE_GetParamInt()]\monitor_width
			G_SE_ReturnType = SE_INT
		Case func_getplayermonitorheight
			G_SE_ReturnVal = Player[SE_GetParamInt()]\monitor_height
			G_SE_ReturnType = SE_INT
		Case func_setmapseed
			RestartServer(SE_GetParamString())
		Case func_removeobject
			object_remove(SE_GetParamString())
		Case func_createobject
			G_SE_ReturnVal = object_create(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToIntArg(5), SE_AND_LUA_ToIntArg(6))
			G_SE_ReturnType = SE_INT
		Case func_removeplayertext
			text_remove(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_createplayertext
			G_SE_ReturnVal = text_create(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToIntArg(4), SE_AND_LUA_ToStringArg(5), SE_AND_LUA_ToFloatArg(6))
			G_SE_ReturnType = SE_INT
		Case func_removeplayerdraw
			draw_remove(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		Case func_createplayerdraw
			G_SE_ReturnVal = draw_create(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2),SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToIntArg(5), SE_AND_LUA_ToIntArg(6), SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_INT
		Case func_setplayertype
			playerid = SE_GetParamInt()
			brtype = SE_GetParamInt()
			SetPlayerType(playerid, brtype)
		Case func_getplayerip
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\PlayerIP
			G_SE_ReturnType = SE_STRING
		Case func_getplayerping
			playerid = SE_GetParamString()
			G_SE_ReturnVal = Player[playerid]\ping
			G_SE_ReturnType = SE_INT
		Case func_getplayernickname
			playerid = SE_AND_LUA_ToIntArg(0)
			G_SE_ReturnVal = Player[playerid]\name
			G_SE_ReturnType = SE_STRING
		Case func_shoot
			Shoot2(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4), SE_AND_LUA_ToIntArg(5))
		Case func_setplayerping
			Player[SE_AND_LUA_ToIntArg(0)]\ping = SE_AND_LUA_ToIntArg(1)
		Case func_isafakeplayer
			G_SE_ReturnVal = (Player[SE_GetParamInt()]\fake=True)
			G_SE_ReturnType = SE_INT
		Case func_createfakeplayer
			pl.players = CreatePlayer(findFreePlayerID())
			If pl = Null Then
				id = 0
			Else
				id = pl\ID
				name$ = SE_GetParamString();+" [BOT]"
				Player[id]\name = name
				Player[id]\fake = True
				Player[id]\Isload = True
				Player[id]\p_byte = (1) + (2 * ReadBool(Player[id]\p_byte, 1)) + (4 * ReadBool(Player[id]\p_byte, 2)) + (8 * ReadBool(Player[id]\p_byte, 3)) + (0) + (32)
				Player[id]\Ready = True
				Player[id]\ping = 5
				Player[ID]\BreachType = 0
				AddLog(name+" has joined to server")
				mp_CreatePlayerObject(pl\ID)
				
				public_inqueue(public_OnPlayerConnect)
				public_addparam(0, id)
				callback()
			EndIf
			G_SE_ReturnVal = id
			G_SE_ReturnType = SE_INT
		Case func_getplayerloadstate
			playerid = SE_GetParamInt()
			G_SE_ReturnVal = Player[playerid]\InGame
			G_SE_ReturnType = SE_INT
		Case func_getplayerdeadstate
			playerid = SE_GetParamInt()
			G_SE_ReturnVal = ReadBool(Player[playerid]\p_byte, 4)
			G_SE_ReturnType = SE_INT
		Case func_setplayerfakedeadstate
			Player[SE_AND_LUA_ToIntArg(0)]\p_byte = (1) + (2 * ReadBool(Player[playerid]\p_byte, 1)) + (4 * ReadBool(Player[playerid]\p_byte, 2)) + (8 * ReadBool(Player[playerid]\p_byte, 3)) + (16 * SE_AND_LUA_ToIntArg(1)) + (32 * ReadBool(Player[playerid]\p_byte, 5)) + (64 * ReadBool(Player[i]\p_byte, 6))
		Case func_setplayerfakeholdinggun
			Player[SE_AND_LUA_ToIntArg(0)]\UsedWeapon = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerfakenvg
			Player[SE_AND_LUA_ToIntArg(0)]\WearingNightVision = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerfakegasmask
			Player[SE_AND_LUA_ToIntArg(0)]\WearingGasMask = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerfakevest
			Player[SE_AND_LUA_ToIntArg(0)]\Wearingvest = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerfakeanimation
			Player[SE_AND_LUA_ToIntArg(0)]\PLAYER_MOVE = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerfakehazmat
			Player[SE_AND_LUA_ToIntArg(0)]\WearingHazmat = SE_AND_LUA_ToIntArg(1)
		Case func_setplayerfakeblinktimer
			Player[SE_AND_LUA_ToIntArg(0)]\blinktimer = SE_AND_LUA_ToFloatArg(1)
		Case func_setplayerposition
			SetPlayerPosition(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4))
		Case func_setplayerpositionid
			SetPlayerPositionEx(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3), SE_AND_LUA_ToFloatArg(4))
		Case func_setplayerangle
			playerid = SE_GetParamInt()
			z# = SE_GetParamFloat()
			Player[playerid]\yaw = z
		Case func_setplayermessage
			SendPlayerMsg(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_giveadmin
			playerid = SE_GetParamInt()
			Player[playerid]\Authorized = True
			AddTextToChat("[RCON] You got the admin role.", playerid)
		Case func_removeadmin
			Player[SE_GetParamInt()]\Authorized = False
		Case func_isplayeradmin
			G_SE_ReturnVal = Player[SE_GetParamInt()]\authorized
			G_SE_ReturnType = SE_INT
		Case func_servermessage
			AddTextToChat(SE_GetParamString())
		Case func_playerconsolecommand
			SendConsoleCommand(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2))
		Case func_playplayersound
			PlaySoundForPlayers(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToFloatArg(2), SE_AND_LUA_ToFloatArg(3))
		Case func_playsound
			PlaySoundForPlayer(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1))
		Case func_sendmessage
			AddTextToChat(SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(0))
		Case func_kick
			Kick(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1))
		Case func_banip
			RCON_BanIP("banlist", SE_GetParamString())
		Case func_plugin_remove
			plugin_remove(SE_GetParamInt())
		Case func_plugin_call
			G_SE_ReturnVal = plugin_call(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2))
			G_SE_ReturnType = SE_STRING
		Case func_plugin_poke
			G_SE_ReturnVal = plugin_poke(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToIntArg(2))
			G_SE_ReturnType = SE_INT
		Case func_plugin_load
			G_SE_ReturnVal = plugin_init(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_delay
			Delay SE_GetParamInt()
			
		Case func_print
			If Not COMPILER_SYSTEM Then
				AddLog(SE_AND_LUA_ToStringArg(0), 0, False, True)
			Else
				Print SE_GetParamString()
			EndIf
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
		Case func_getunixtime
			G_SE_ReturnVal = GetUnixTimestamp()
			G_SE_ReturnType = SE_INT
		Case func_gettime
			G_SE_ReturnVal = CurrentTime()
			G_SE_ReturnType = SE_STRING
		Case func_getdate
			G_SE_ReturnVal = CurrentDate()
			G_SE_ReturnType = SE_STRING
		Case func_int
			SE_AND_LUA_ReturnInt(SE_AND_LUA_ToIntArg(0))
			Return
		Case func_float
			SE_AND_LUA_ReturnFloat(SE_AND_LUA_ToFloatArg(0))
			Return
		Case func_str
			SE_AND_LUA_ReturnString(SE_AND_LUA_ToFloatArg(0))
			Return
		Case func_floor
			SE_AND_LUA_ReturnFloat(Floor(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_ceil
			SE_AND_LUA_ReturnFloat(Ceil(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_sgn
			SE_AND_LUA_ReturnInt(Sgn(SE_AND_LUA_ToIntArg(0)))
			Return
		Case func_abs
			SE_AND_LUA_ReturnInt(Abs(SE_IntArg(0)))
			Return
		Case func_sqr
			SE_AND_LUA_ReturnFloat(Sqr(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_sin
			SE_AND_LUA_ReturnFloat(Sin(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_cos
			SE_AND_LUA_ReturnFloat(Cos(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_tan
			SE_AND_LUA_ReturnFloat(Tan(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_asin
			SE_AND_LUA_ReturnFloat(ASin(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_acos
			SE_AND_LUA_ReturnFloat(ACos(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_atan
			SE_AND_LUA_ReturnFloat(ATan(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_atan2
			SE_AND_LUA_ReturnFloat(ATan2(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1)))
			Return
		Case func_exp
			SE_AND_LUA_ReturnFloat(Exp(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_log
			SE_AND_LUA_ReturnFloat(Log(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_log10
			SE_AND_LUA_ReturnFloat(Log10(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_rnd
			SE_AND_LUA_ReturnFloat(Rnd(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1)))
			Return
		Case func_rand
			SE_AND_LUA_ReturnInt(Rand(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_num
			SE_AND_LUA_ReturnString(SE_AND_LUA_ToStringArg(0))
			Return
		Case func_left
			SE_AND_LUA_ReturnString(Left(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_right
			SE_AND_LUA_ReturnString(Right(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_mid
			SE_AND_LUA_ReturnString(Mid(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2)))
			Return
		Case func_replace
			SE_AND_LUA_ReturnString(Replace(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToStringArg(2)))
			Return
		Case func_instr
			SE_AND_LUA_ReturnInt(Instr(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2)))
			Return
		Case func_lower
			SE_AND_LUA_ReturnString(Lower(SE_AND_LUA_ToStringArg(0)))
			Return
		Case func_upper
			SE_AND_LUA_ReturnString(Upper(SE_AND_LUA_ToStringArg(0)))
			Return
		Case func_trim
			SE_AND_LUA_ReturnString(Trim(SE_AND_LUA_ToStringArg(0)))
			Return
		Case func_hex
			SE_AND_LUA_ReturnString(Hex(SE_AND_LUA_ToIntArg(0)))
			Return
		Case func_bin
			SE_AND_LUA_ReturnString(Bin(SE_AND_LUA_ToIntArg(0)))
			Return
		Case func_repeat
			SE_AND_LUA_ReturnString(String(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_array_create
			SE_BL_Array_Create()
			
		Case func_array_push
			SE_BL_Array_Push()
			
		Case func_array_pop
			SE_BL_Array_Pop()
			
		Case func_array_delete
			SE_BL_Array_Delete()
			
		Case func_array_sort
			SE_BL_Array_Sort()
			
		Case func_array_from_string
			SE_BL_Array_FromString()
			
		Case func_changeplayername
			playerid = SE_GetParamInt()
			name$ = SE_GetParamString()
			ChangePlayerNickname(playerid, name)
		Case func_changeplayertag
			playerid = SE_GetParamInt()
			tag$ = SE_GetParamString()
			tagR = SE_GetParamInt()
			tagG = SE_GetParamInt()
			tagB = SE_GetParamInt()
			ChangePlayerTag(playerid, tag, tagR, tagG, tagB)
		Case func_isplayerpatron
			G_SE_ReturnVal = (Player[SE_GetParamInt()]\tag="PATRON")
			G_SE_ReturnType = SE_INT
		Case func_getincomingconnectionversion
			G_SE_ReturnVal = incomingversion
			G_SE_ReturnType = SE_STRING
		Case func_getincomingconnectionpatron
			G_SE_ReturnVal = incomingpatron
			G_SE_ReturnType = SE_INT
		Case func_setmtftickets
			gameinfo\b\MTFTickets = SE_GetParamFloat()
		Case func_setchaostickets
			gameinfo\b\ChaosTickets = SE_GetParamFloat()
		Case func_getmtftickets
			G_SE_ReturnVal = gameinfo\b\MTFTickets
			G_SE_ReturnType = SE_FLOAT
		Case func_getchaostickets
			G_SE_ReturnVal = gameinfo\b\ChaosTickets
			G_SE_ReturnType = SE_FLOAT
		Case func_activatefilessending
			Server\FilesSendActivate = SE_GetParamInt()
		Case func_getplayermute
			G_SE_ReturnVal = Player[Int(StrTemp)]\Muted
			G_SE_ReturnType = SE_INT
		Case func_setplayermute
			playerid = SE_GetParamInt()
			Player[playerid]\Muted = SE_GetParamInt()
		Case func_enableauthkeyonconnect
			Server\GetAuthKey = SE_GetParamInt()
	End Select

	Select G_SE_ReturnType
		Case SE_INT
			SE_AND_LUA_ReturnInt(G_SE_ReturnVal)
		Case SE_FLOAT
			SE_AND_LUA_ReturnFloat(G_SE_ReturnVal)
		Case SE_STRING
			SE_AND_LUA_ReturnString(G_SE_ReturnVal)
		Default
			SE_AND_LUA_ReturnInt(0)
	End Select
End Function

Function SLUA_LOAD_FUNCTIONS()
	SLUA_ADD_GLOBAL_FUNCTION("sethalloweenmode",SE_GetFunctionID("sethalloweenmode"))
	SLUA_ADD_GLOBAL_FUNCTION("setchristmasmode",SE_GetFunctionID("setchristmasmode"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolecategory",SE_GetFunctionID("breachrole_setrolecategory"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_createplayerrole",SE_GetFunctionID("breachrole_createplayerrole"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_getrolename",SE_GetFunctionID("breachrole_getrolename"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolesettings",SE_GetFunctionID("breachrole_setrolesettings"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroleeffects",SE_GetFunctionID("breachrole_setroleeffects"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroleambientsound",SE_GetFunctionID("breachrole_setroleambientsound"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroleinstruction",SE_GetFunctionID("breachrole_setroleinstruction"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroledeadanimation",SE_GetFunctionID("breachrole_setroledeadanimation"))
    SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolehitboxscales",SE_GetFunctionID("breachrole_setrolehitboxscales"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolebone",SE_GetFunctionID("breachrole_setrolebone"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroleanimation",SE_GetFunctionID("breachrole_setroleanimation"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolearmedanimation",SE_GetFunctionID("breachrole_setrolearmedanimation"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_markasfriend",SE_GetFunctionID("breachrole_markasfriend"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_roletakerolespawn",SE_GetFunctionID("breachrole_roletakerolespawn"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolepositionsoffset",SE_GetFunctionID("breachrole_setrolepositionsoffset"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroleholdinggrenade",SE_GetFunctionID("breachrole_setroleholdinggrenade"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setroleholdingitem",SE_GetFunctionID("breachrole_setroleholdingitem"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_setrolehandcuff",SE_GetFunctionID("breachrole_setrolehandcuff"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_allowroleweaponattaches",SE_GetFunctionID("breachrole_allowroleweaponattaches"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_allowitemsattaches",SE_GetFunctionID("breachrole_allowitemsattaches"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_markroleasscp",SE_GetFunctionID("breachrole_markroleasscp"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_markas035",SE_GetFunctionID("breachrole_markas035"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_markas049",SE_GetFunctionID("breachrole_markas049"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_isa035",SE_GetFunctionID("breachrole_isa035"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_isa049",SE_GetFunctionID("breachrole_isa049"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_isafriend",SE_GetFunctionID("breachrole_isafriend"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_getmaxhp",SE_GetFunctionID("breachrole_getmaxhp"))
	SLUA_ADD_GLOBAL_FUNCTION("breachrole_getrolescale",SE_GetFunctionID("breachrole_getrolescale"))
	SLUA_ADD_GLOBAL_FUNCTION("enableintercom",SE_GetFunctionID("enableintercom"))
	SLUA_ADD_GLOBAL_FUNCTION("setintercomtimeout",SE_GetFunctionID("setintercomtimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("setintercomusingtime",SE_GetFunctionID("setintercomusingtime"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerintercom",SE_GetFunctionID("setplayerintercom"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerintercomusingtime",SE_GetFunctionID("setplayerintercomusingtime"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerintercomtimeout",SE_GetFunctionID("setplayerintercomtimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("isplayerusingintercom",SE_GetFunctionID("isplayerusingintercom"))
	SLUA_ADD_GLOBAL_FUNCTION("setdoorcode",SE_GetFunctionID("setdoorcode"))
	SLUA_ADD_GLOBAL_FUNCTION("getdoorcode",SE_GetFunctionID("getdoorcode"))
	SLUA_ADD_GLOBAL_FUNCTION("gettime",SE_GetFunctionID("gettime"))
	SLUA_ADD_GLOBAL_FUNCTION("getdate",SE_GetFunctionID("getdate"))
	SLUA_ADD_GLOBAL_FUNCTION("spawnchaos",SE_GetFunctionID("spawnchaos"))
	SLUA_ADD_GLOBAL_FUNCTION("getdoorkeycard",SE_GetFunctionID("getdoorkeycard"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerselecteditem",SE_GetFunctionID("getplayerselecteditem"))
	SLUA_ADD_GLOBAL_FUNCTION("setdoorkeycard",SE_GetFunctionID("setdoorkeycard"))
	SLUA_ADD_GLOBAL_FUNCTION("executeserverconsole",SE_GetFunctionID("executeserverconsole"))
	SLUA_ADD_GLOBAL_FUNCTION("createcube",SE_GetFunctionID("createcube"))
	SLUA_ADD_GLOBAL_FUNCTION("createsphere",SE_GetFunctionID("createsphere"))
	SLUA_ADD_GLOBAL_FUNCTION("createcylinder",SE_GetFunctionID("createcylinder"))
	SLUA_ADD_GLOBAL_FUNCTION("createcone",SE_GetFunctionID("createcone"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerdownloadingcount",SE_GetFunctionID("getplayerdownloadingcount"))
	SLUA_ADD_GLOBAL_FUNCTION("isplayerdownloadingfile",SE_GetFunctionID("isplayerdownloadingfile"))
	SLUA_ADD_GLOBAL_FUNCTION("createpivot",SE_GetFunctionID("createpivot"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerspeedhackmult",SE_GetFunctionID("setplayerspeedhackmult"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerspeedhackmult",SE_GetFunctionID("getplayerspeedhackmult"))
	SLUA_ADD_GLOBAL_FUNCTION("turnentity",SE_GetFunctionID("turnentity"))
	SLUA_ADD_GLOBAL_FUNCTION("createobjectsound",SE_GetFunctionID("createobjectsound"))
	SLUA_ADD_GLOBAL_FUNCTION("createobjectsoundforplayer",SE_GetFunctionID("createobjectsoundforplayer"))
	SLUA_ADD_GLOBAL_FUNCTION("tcp_get",SE_GetFunctionID("tcp_get"))
	SLUA_ADD_GLOBAL_FUNCTION("tcp_close",SE_GetFunctionID("tcp_close"))
	SLUA_ADD_GLOBAL_FUNCTION("createsprite",SE_GetFunctionID("createsprite"))
	SLUA_ADD_GLOBAL_FUNCTION("loadsprite",SE_GetFunctionID("loadsprite"))
	SLUA_ADD_GLOBAL_FUNCTION("spriteviewmode",SE_GetFunctionID("spriteviewmode"))
	SLUA_ADD_GLOBAL_FUNCTION("scalesprite",SE_GetFunctionID("scalesprite"))
	SLUA_ADD_GLOBAL_FUNCTION("entityfx",SE_GetFunctionID("entityfx"))
	SLUA_ADD_GLOBAL_FUNCTION("entityblend",SE_GetFunctionID("entityblend"))
	SLUA_ADD_GLOBAL_FUNCTION("showentity",SE_GetFunctionID("showentity"))
	SLUA_ADD_GLOBAL_FUNCTION("hideentity",SE_GetFunctionID("hideentity"))
	SLUA_ADD_GLOBAL_FUNCTION("getparent",SE_GetFunctionID("getparent"))
	SLUA_ADD_GLOBAL_FUNCTION("entityautofade",SE_GetFunctionID("entityautofade"))
	SLUA_ADD_GLOBAL_FUNCTION("entitycolor",SE_GetFunctionID("entitycolor"))
	SLUA_ADD_GLOBAL_FUNCTION("entityshininess",SE_GetFunctionID("entityshininess"))
	SLUA_ADD_GLOBAL_FUNCTION("entityalpha",SE_GetFunctionID("entityalpha"))
	SLUA_ADD_GLOBAL_FUNCTION("entitytexture",SE_GetFunctionID("entitytexture"))
	SLUA_ADD_GLOBAL_FUNCTION("createtexture",SE_GetFunctionID("createtexture"))
	SLUA_ADD_GLOBAL_FUNCTION("loadtexture",SE_GetFunctionID("loadtexture"))
	SLUA_ADD_GLOBAL_FUNCTION("scaletexture",SE_GetFunctionID("scaletexture"))
	SLUA_ADD_GLOBAL_FUNCTION("texturewidth",SE_GetFunctionID("texturewidth"))
	SLUA_ADD_GLOBAL_FUNCTION("textureheight",SE_GetFunctionID("textureheight"))
	SLUA_ADD_GLOBAL_FUNCTION("entityparent",SE_GetFunctionID("entityparent"))
	SLUA_ADD_GLOBAL_FUNCTION("enablenoclipanticheat",SE_GetFunctionID("enablenoclipanticheat"))
	SLUA_ADD_GLOBAL_FUNCTION("enablecamerashakeondamage",SE_GetFunctionID("enablecamerashakeondamage"))
	SLUA_ADD_GLOBAL_FUNCTION("pointentity",SE_GetFunctionID("pointentity"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayercamera",SE_GetFunctionID("getplayercamera"))
	SLUA_ADD_GLOBAL_FUNCTION("deltayaw",SE_GetFunctionID("deltayaw"))
	SLUA_ADD_GLOBAL_FUNCTION("deltapitch",SE_GetFunctionID("deltapitch"))
	SLUA_ADD_GLOBAL_FUNCTION("resetentity",SE_GetFunctionID("resetentity"))
	SLUA_ADD_GLOBAL_FUNCTION("entityradius",SE_GetFunctionID("entityradius"))
	SLUA_ADD_GLOBAL_FUNCTION("entitybox",SE_GetFunctionID("entitybox"))
	SLUA_ADD_GLOBAL_FUNCTION("entitycollisiontype",SE_GetFunctionID("entitycollisiontype"))
	SLUA_ADD_GLOBAL_FUNCTION("entitypickmode",SE_GetFunctionID("entitypickmode"))
	SLUA_ADD_GLOBAL_FUNCTION("entitycollided",SE_GetFunctionID("entitycollided"))
	SLUA_ADD_GLOBAL_FUNCTION("countcollisions",SE_GetFunctionID("countcollisions"))
	SLUA_ADD_GLOBAL_FUNCTION("collisionx",SE_GetFunctionID("collisionx"))
	SLUA_ADD_GLOBAL_FUNCTION("collisiony",SE_GetFunctionID("collisiony"))
	SLUA_ADD_GLOBAL_FUNCTION("collisionz",SE_GetFunctionID("collisionz"))
	SLUA_ADD_GLOBAL_FUNCTION("collisionnx",SE_GetFunctionID("collisionnx"))
	SLUA_ADD_GLOBAL_FUNCTION("collisionny",SE_GetFunctionID("collisionny"))
	SLUA_ADD_GLOBAL_FUNCTION("collisionnz",SE_GetFunctionID("collisionnz"))
	SLUA_ADD_GLOBAL_FUNCTION("collisionentity",SE_GetFunctionID("collisionentity"))
	SLUA_ADD_GLOBAL_FUNCTION("getentitycollisiontype",SE_GetFunctionID("getentitycollisiontype"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayeroriginalx",SE_GetFunctionID("getplayeroriginalx"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayeroriginaly",SE_GetFunctionID("getplayeroriginaly"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayeroriginalz",SE_GetFunctionID("getplayeroriginalz"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakeroom",SE_GetFunctionID("setplayerfakeroom"))
	SLUA_ADD_GLOBAL_FUNCTION("changeplayersteamid",SE_GetFunctionID("changeplayersteamid"))
	SLUA_ADD_GLOBAL_FUNCTION("addplayerspawnposition",SE_GetFunctionID("addplayerspawnposition"))
	SLUA_ADD_GLOBAL_FUNCTION("addclassspawnposition",SE_GetFunctionID("addclassspawnposition"))
	SLUA_ADD_GLOBAL_FUNCTION("removespawnposition",SE_GetFunctionID("removespawnposition"))
	SLUA_ADD_GLOBAL_FUNCTION("isvalidspawnposition",SE_GetFunctionID("isvalidspawnposition"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerspeedmult",SE_GetFunctionID("getplayerspeedmult"))
	SLUA_ADD_GLOBAL_FUNCTION("updateobjects",SE_GetFunctionID("updateobjects"))
	SLUA_ADD_GLOBAL_FUNCTION("preparemodelidentifier",SE_GetFunctionID("preparemodelidentifier"))
	SLUA_ADD_GLOBAL_FUNCTION("setobjectinterpolation",SE_GetFunctionID("setobjectinterpolation"))
	SLUA_ADD_GLOBAL_FUNCTION("shouldplayerannouncement",SE_GetFunctionID("shouldplayerannouncement"))
	SLUA_ADD_GLOBAL_FUNCTION("getroomdoor",SE_GetFunctionID("getroomdoor"))
	SLUA_ADD_GLOBAL_FUNCTION("changeplayersize",SE_GetFunctionID("changeplayersize"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayersize",SE_GetFunctionID("getplayersize"))
	SLUA_ADD_GLOBAL_FUNCTION("lerp",SE_GetFunctionID("lerp"))
	SLUA_ADD_GLOBAL_FUNCTION("lerpangle",SE_GetFunctionID("lerpangle"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_0",SE_GetFunctionID("dllfunctionvoid_0"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_1",SE_GetFunctionID("dllfunctionvoid_1"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_2",SE_GetFunctionID("dllfunctionvoid_2"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_3",SE_GetFunctionID("dllfunctionvoid_3"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_4",SE_GetFunctionID("dllfunctionvoid_4"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_5",SE_GetFunctionID("dllfunctionvoid_5"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_6",SE_GetFunctionID("dllfunctionvoid_6"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_7",SE_GetFunctionID("dllfunctionvoid_7"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_8",SE_GetFunctionID("dllfunctionvoid_8"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionvoid_9",SE_GetFunctionID("dllfunctionvoid_9"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_0",SE_GetFunctionID("dllfunctionint_0"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_1",SE_GetFunctionID("dllfunctionint_1"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_2",SE_GetFunctionID("dllfunctionint_2"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_3",SE_GetFunctionID("dllfunctionint_3"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_4",SE_GetFunctionID("dllfunctionint_4"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_5",SE_GetFunctionID("dllfunctionint_5"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_6",SE_GetFunctionID("dllfunctionint_6"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_7",SE_GetFunctionID("dllfunctionint_7"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_8",SE_GetFunctionID("dllfunctionint_8"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionint_9",SE_GetFunctionID("dllfunctionint_9"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_0",SE_GetFunctionID("dllfunctionfloat_0"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_1",SE_GetFunctionID("dllfunctionfloat_1"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_2",SE_GetFunctionID("dllfunctionfloat_2"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_3",SE_GetFunctionID("dllfunctionfloat_3"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_4",SE_GetFunctionID("dllfunctionfloat_4"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_5",SE_GetFunctionID("dllfunctionfloat_5"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_6",SE_GetFunctionID("dllfunctionfloat_6"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_7",SE_GetFunctionID("dllfunctionfloat_7"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_8",SE_GetFunctionID("dllfunctionfloat_8"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionfloat_9",SE_GetFunctionID("dllfunctionfloat_9"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_0",SE_GetFunctionID("dllfunctionstring_0"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_1",SE_GetFunctionID("dllfunctionstring_1"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_2",SE_GetFunctionID("dllfunctionstring_2"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_3",SE_GetFunctionID("dllfunctionstring_3"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_4",SE_GetFunctionID("dllfunctionstring_4"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_5",SE_GetFunctionID("dllfunctionstring_5"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_6",SE_GetFunctionID("dllfunctionstring_6"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_7",SE_GetFunctionID("dllfunctionstring_7"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_8",SE_GetFunctionID("dllfunctionstring_8"))
	SLUA_ADD_GLOBAL_FUNCTION("dllfunctionstring_9",SE_GetFunctionID("dllfunctionstring_9"))
	SLUA_ADD_GLOBAL_FUNCTION("connecttocentralserver",SE_GetFunctionID("connecttocentralserver"))
	SLUA_ADD_GLOBAL_FUNCTION("reconnecttocentralserver",SE_GetFunctionID("reconnecttocentralserver"))
	SLUA_ADD_GLOBAL_FUNCTION("createsteaminstance",SE_GetFunctionID("createsteaminstance"))
	SLUA_ADD_GLOBAL_FUNCTION("getsteaminstancetag",SE_GetFunctionID("getsteaminstancetag"))
	SLUA_ADD_GLOBAL_FUNCTION("removesteaminstance",SE_GetFunctionID("removesteaminstance"))
	SLUA_ADD_GLOBAL_FUNCTION("setdescriptionline",SE_GetFunctionID("setdescriptionline"))
	SLUA_ADD_GLOBAL_FUNCTION("getdescriptionline",SE_GetFunctionID("getdescriptionline"))
	SLUA_ADD_GLOBAL_FUNCTION("setmaxspawnplayers",SE_GetFunctionID("setmaxspawnplayers"))
	SLUA_ADD_GLOBAL_FUNCTION("getmaxspawnplayers",SE_GetFunctionID("getmaxspawnplayers"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventname",SE_GetFunctionID("geteventname"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventroomid",SE_GetFunctionID("geteventroomid"))
	SLUA_ADD_GLOBAL_FUNCTION("enableoldresponse",SE_GetFunctionID("enableoldresponse"))
	SLUA_ADD_GLOBAL_FUNCTION("closeapp",SE_GetFunctionID("closeapp"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayermute",SE_GetFunctionID("getplayermute"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayermute",SE_GetFunctionID("setplayermute"))
	SLUA_ADD_GLOBAL_FUNCTION("opendatabase",SE_GetFunctionID("opendatabase"))
	SLUA_ADD_GLOBAL_FUNCTION("closedatabase",SE_GetFunctionID("closedatabase"))
	SLUA_ADD_GLOBAL_FUNCTION("setdatabasetimeout",SE_GetFunctionID("setdatabasetimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("databaseversion",SE_GetFunctionID("databaseversion"))
	SLUA_ADD_GLOBAL_FUNCTION("lastrowidinserted",SE_GetFunctionID("lastrowidinserted"))
	SLUA_ADD_GLOBAL_FUNCTION("rowschangedbylaststatement",SE_GetFunctionID("rowschangedbylaststatement"))
	SLUA_ADD_GLOBAL_FUNCTION("rowschangedthissession",SE_GetFunctionID("rowschangedthissession"))
	SLUA_ADD_GLOBAL_FUNCTION("autocommitison",SE_GetFunctionID("autocommitison"))
	SLUA_ADD_GLOBAL_FUNCTION("begintransaction",SE_GetFunctionID("begintransaction"))
	SLUA_ADD_GLOBAL_FUNCTION("committransaction",SE_GetFunctionID("committransaction"))
	SLUA_ADD_GLOBAL_FUNCTION("rollbacktransaction",SE_GetFunctionID("rollbacktransaction"))
	SLUA_ADD_GLOBAL_FUNCTION("lastdatabaseerrorcode",SE_GetFunctionID("lastdatabaseerrorcode"))
	SLUA_ADD_GLOBAL_FUNCTION("lastdatabaseerrormessage",SE_GetFunctionID("lastdatabaseerrormessage"))
	SLUA_ADD_GLOBAL_FUNCTION("interruptdatabase",SE_GetFunctionID("interruptdatabase"))
	SLUA_ADD_GLOBAL_FUNCTION("executesql",SE_GetFunctionID("executesql"))
	SLUA_ADD_GLOBAL_FUNCTION("preparesql",SE_GetFunctionID("preparesql"))
	SLUA_ADD_GLOBAL_FUNCTION("getnextdatarow",SE_GetFunctionID("getnextdatarow"))
	SLUA_ADD_GLOBAL_FUNCTION("finalisesql",SE_GetFunctionID("finalisesql"))
	SLUA_ADD_GLOBAL_FUNCTION("resetsql",SE_GetFunctionID("resetsql"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlhasexpired",SE_GetFunctionID("sqlhasexpired"))
	SLUA_ADD_GLOBAL_FUNCTION("getdatabasehandlefromstatementhandle",SE_GetFunctionID("getdatabasehandlefromstatementhandle"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumncount",SE_GetFunctionID("getcolumncount"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumnname",SE_GetFunctionID("getcolumnname"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumntype",SE_GetFunctionID("getcolumntype"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumndeclaredtype",SE_GetFunctionID("getcolumndeclaredtype"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumnsize",SE_GetFunctionID("getcolumnsize"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumnvalueasinteger",SE_GetFunctionID("getcolumnvalueasinteger"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumnvalueasfloat",SE_GetFunctionID("getcolumnvalueasfloat"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumnvalueasstring",SE_GetFunctionID("getcolumnvalueasstring"))
	SLUA_ADD_GLOBAL_FUNCTION("getcolumnvalueasblob",SE_GetFunctionID("getcolumnvalueasblob"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlparametercount",SE_GetFunctionID("sqlparametercount"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlparametername",SE_GetFunctionID("sqlparametername"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlparameterindex",SE_GetFunctionID("sqlparameterindex"))
	SLUA_ADD_GLOBAL_FUNCTION("bindsqlparameterasnull",SE_GetFunctionID("bindsqlparameterasnull"))
	SLUA_ADD_GLOBAL_FUNCTION("bindsqlparameterasinteger",SE_GetFunctionID("bindsqlparameterasinteger"))
	SLUA_ADD_GLOBAL_FUNCTION("bindsqlparameterasfloat",SE_GetFunctionID("bindsqlparameterasfloat"))
	SLUA_ADD_GLOBAL_FUNCTION("bindsqlparameterasstring",SE_GetFunctionID("bindsqlparameterasstring"))
	SLUA_ADD_GLOBAL_FUNCTION("bindsqlparameterasblob",SE_GetFunctionID("bindsqlparameterasblob"))
	SLUA_ADD_GLOBAL_FUNCTION("transfersqlbindings",SE_GetFunctionID("transfersqlbindings"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlite3_errorhasoccurred",SE_GetFunctionID("sqlite3_errorhasoccurred"))
	SLUA_ADD_GLOBAL_FUNCTION("opensqlstream",SE_GetFunctionID("opensqlstream"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlconnected",SE_GetFunctionID("sqlconnected"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlquery",SE_GetFunctionID("sqlquery"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlrowcount",SE_GetFunctionID("sqlrowcount"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlfetchrow",SE_GetFunctionID("sqlfetchrow"))
	SLUA_ADD_GLOBAL_FUNCTION("sqlfieldcount",SE_GetFunctionID("sqlfieldcount"))
	SLUA_ADD_GLOBAL_FUNCTION("readsqlfield",SE_GetFunctionID("readsqlfield"))
	SLUA_ADD_GLOBAL_FUNCTION("readsqlfieldindex",SE_GetFunctionID("readsqlfieldindex"))
	SLUA_ADD_GLOBAL_FUNCTION("freesqlquery",SE_GetFunctionID("freesqlquery"))
	SLUA_ADD_GLOBAL_FUNCTION("freesqlrow",SE_GetFunctionID("freesqlrow"))
	SLUA_ADD_GLOBAL_FUNCTION("closesqlstream",SE_GetFunctionID("closesqlstream"))
	SLUA_ADD_GLOBAL_FUNCTION("getserverexplodetimeout",SE_GetFunctionID("getserverexplodetimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("getserverspawntimeout",SE_GetFunctionID("getserverspawntimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("setserverspawntimeout",SE_GetFunctionID("setserverspawntimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("enableauthkeyonconnect",SE_GetFunctionID("enableauthkeyonconnect"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayervelocity",SE_GetFunctionID("setplayervelocity"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayervelocity",SE_GetFunctionID("getplayervelocity"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerhandcuff",SE_GetFunctionID("getplayerhandcuff"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerhandcuff",SE_GetFunctionID("setplayerhandcuff"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayersteamid",SE_GetFunctionID("getplayersteamid"))
	SLUA_ADD_GLOBAL_FUNCTION("giveplayerhealth",SE_GetFunctionID("giveplayerhealth"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerspeedmult",SE_GetFunctionID("setplayerspeedmult"))
	SLUA_ADD_GLOBAL_FUNCTION("sendblinktimerforplayer",SE_GetFunctionID("sendblinktimerforplayer"))
	SLUA_ADD_GLOBAL_FUNCTION("zlib_compress",SE_GetFunctionID("zlib_compress"))
	SLUA_ADD_GLOBAL_FUNCTION("zlib_uncompress",SE_GetFunctionID("zlib_uncompress"))
	SLUA_ADD_GLOBAL_FUNCTION("deactivatewarheads",SE_GetFunctionID("deactivatewarheads"))
	SLUA_ADD_GLOBAL_FUNCTION("spawnmtf",SE_GetFunctionID("spawnmtf"))
	SLUA_ADD_GLOBAL_FUNCTION("activatewarheads",SE_GetFunctionID("activatewarheads"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerzone",SE_GetFunctionID("getplayerzone"))
	SLUA_ADD_GLOBAL_FUNCTION("getbreachtimer",SE_GetFunctionID("getbreachtimer"))
	SLUA_ADD_GLOBAL_FUNCTION("addaccessversion",SE_GetFunctionID("addaccessversion"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerinjuries",SE_GetFunctionID("getplayerinjuries"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerinjuries",SE_GetFunctionID("setplayerinjuries"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerheadpitch",SE_GetFunctionID("getplayerheadpitch"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerheadpitch",SE_GetFunctionID("setplayerheadpitch"))
	SLUA_ADD_GLOBAL_FUNCTION("createentitycamera",SE_GetFunctionID("createentitycamera"))
	SLUA_ADD_GLOBAL_FUNCTION("entityinview",SE_GetFunctionID("entityinview"))
	SLUA_ADD_GLOBAL_FUNCTION("entityvisible",SE_GetFunctionID("entityvisible"))
	SLUA_ADD_GLOBAL_FUNCTION("entityx",SE_GetFunctionID("entityx"))
	SLUA_ADD_GLOBAL_FUNCTION("entityy",SE_GetFunctionID("entityy"))
	SLUA_ADD_GLOBAL_FUNCTION("entityz",SE_GetFunctionID("entityz"))
	SLUA_ADD_GLOBAL_FUNCTION("entitypitch",SE_GetFunctionID("entitypitch"))
	SLUA_ADD_GLOBAL_FUNCTION("entityyaw",SE_GetFunctionID("entityyaw"))
	SLUA_ADD_GLOBAL_FUNCTION("entityroll",SE_GetFunctionID("entityroll"))
	SLUA_ADD_GLOBAL_FUNCTION("entitypick",SE_GetFunctionID("entitypick"))
	SLUA_ADD_GLOBAL_FUNCTION("positionentity",SE_GetFunctionID("positionentity"))
	SLUA_ADD_GLOBAL_FUNCTION("rotateentity",SE_GetFunctionID("rotateentity"))
	SLUA_ADD_GLOBAL_FUNCTION("moveentity",SE_GetFunctionID("moveentity"))
	SLUA_ADD_GLOBAL_FUNCTION("scaleentity",SE_GetFunctionID("scaleentity"))
	SLUA_ADD_GLOBAL_FUNCTION("setentitycollision",SE_GetFunctionID("setentitycollision"))
	SLUA_ADD_GLOBAL_FUNCTION("removeentity",SE_GetFunctionID("removeentity"))
	SLUA_ADD_GLOBAL_FUNCTION("setanimtime",SE_GetFunctionID("setanimtime"))
	SLUA_ADD_GLOBAL_FUNCTION("getdelta",SE_GetFunctionID("getdelta"))
	SLUA_ADD_GLOBAL_FUNCTION("sendrawpacket",SE_GetFunctionID("sendrawpacket"))
	SLUA_ADD_GLOBAL_FUNCTION("sendscript",SE_GetFunctionID("sendscript"))
	SLUA_ADD_GLOBAL_FUNCTION("sendfile",SE_GetFunctionID("sendfile"))
	SLUA_ADD_GLOBAL_FUNCTION("setgamestate",SE_GetFunctionID("setgamestate"))
	SLUA_ADD_GLOBAL_FUNCTION("setservername",SE_GetFunctionID("setservername"))
	SLUA_ADD_GLOBAL_FUNCTION("setserverdescription",SE_GetFunctionID("setserverdescription"))
	SLUA_ADD_GLOBAL_FUNCTION("setserverpassword",SE_GetFunctionID("setserverpassword"))
	SLUA_ADD_GLOBAL_FUNCTION("setservercurrentbreachtimer",SE_GetFunctionID("setservercurrentbreachtimer"))
	SLUA_ADD_GLOBAL_FUNCTION("setservernocheat",SE_GetFunctionID("setservernocheat"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerhealth",SE_GetFunctionID("getplayerhealth"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakehealth",SE_GetFunctionID("setplayerfakehealth"))
	SLUA_ADD_GLOBAL_FUNCTION("disableautokick",SE_GetFunctionID("disableautokick"))
	SLUA_ADD_GLOBAL_FUNCTION("reloadapp",SE_GetFunctionID("reloadapp"))
	SLUA_ADD_GLOBAL_FUNCTION("hexstring",SE_GetFunctionID("hexstring"))
	SLUA_ADD_GLOBAL_FUNCTION("removetimer",SE_GetFunctionID("removetimer"))
	SLUA_ADD_GLOBAL_FUNCTION("createtimer",SE_GetFunctionID("createtimer"))
	SLUA_ADD_GLOBAL_FUNCTION("serverwritebyte",SE_GetFunctionID("serverwritebyte"))
	SLUA_ADD_GLOBAL_FUNCTION("serverwriteshort",SE_GetFunctionID("serverwriteshort"))
	SLUA_ADD_GLOBAL_FUNCTION("serverwriteint",SE_GetFunctionID("serverwriteint"))
	SLUA_ADD_GLOBAL_FUNCTION("serverwritefloat",SE_GetFunctionID("serverwritefloat"))
	SLUA_ADD_GLOBAL_FUNCTION("debug",SE_GetFunctionID("debug"))
	SLUA_ADD_GLOBAL_FUNCTION("eof",SE_GetFunctionID("eof"))
	SLUA_ADD_GLOBAL_FUNCTION("asc",SE_GetFunctionID("asc"))
	SLUA_ADD_GLOBAL_FUNCTION("chr",SE_GetFunctionID("chr"))
	SLUA_ADD_GLOBAL_FUNCTION("filetype",SE_GetFunctionID("filetype"))
	SLUA_ADD_GLOBAL_FUNCTION("filesize",SE_GetFunctionID("filesize"))
	SLUA_ADD_GLOBAL_FUNCTION("seekfile",SE_GetFunctionID("seekfile"))
	SLUA_ADD_GLOBAL_FUNCTION("filepos",SE_GetFunctionID("filepos"))
	SLUA_ADD_GLOBAL_FUNCTION("catcherror",SE_GetFunctionID("catcherror"))
	SLUA_ADD_GLOBAL_FUNCTION("createerror",SE_GetFunctionID("createerror"))
	SLUA_ADD_GLOBAL_FUNCTION("getpacketindex",SE_GetFunctionID("getpacketindex"))
	SLUA_ADD_GLOBAL_FUNCTION("sendrawdata",SE_GetFunctionID("sendrawdata"))
	SLUA_ADD_GLOBAL_FUNCTION("writebytes",SE_GetFunctionID("writebytes"))
	SLUA_ADD_GLOBAL_FUNCTION("readbytes",SE_GetFunctionID("readbytes"))
	SLUA_ADD_GLOBAL_FUNCTION("createbank",SE_GetFunctionID("createbank"))
	SLUA_ADD_GLOBAL_FUNCTION("freebank",SE_GetFunctionID("freebank"))
	SLUA_ADD_GLOBAL_FUNCTION("banksize",SE_GetFunctionID("banksize"))
	SLUA_ADD_GLOBAL_FUNCTION("resizebank",SE_GetFunctionID("resizebank"))
	SLUA_ADD_GLOBAL_FUNCTION("copybank",SE_GetFunctionID("copybank"))
	SLUA_ADD_GLOBAL_FUNCTION("peekbyte",SE_GetFunctionID("peekbyte"))
	SLUA_ADD_GLOBAL_FUNCTION("peekshort",SE_GetFunctionID("peekshort"))
	SLUA_ADD_GLOBAL_FUNCTION("peekint",SE_GetFunctionID("peekint"))
	SLUA_ADD_GLOBAL_FUNCTION("peekfloat",SE_GetFunctionID("peekfloat"))
	SLUA_ADD_GLOBAL_FUNCTION("pokebyte",SE_GetFunctionID("pokebyte"))
	SLUA_ADD_GLOBAL_FUNCTION("pokeshort",SE_GetFunctionID("pokeshort"))
	SLUA_ADD_GLOBAL_FUNCTION("pokeint",SE_GetFunctionID("pokeint"))
	SLUA_ADD_GLOBAL_FUNCTION("pokefloat",SE_GetFunctionID("pokefloat"))
	SLUA_ADD_GLOBAL_FUNCTION("disablelobby",SE_GetFunctionID("disablelobby"))
	SLUA_ADD_GLOBAL_FUNCTION("removevar",SE_GetFunctionID("removevar"))
	SLUA_ADD_GLOBAL_FUNCTION("setvarvalue",SE_GetFunctionID("setvarvalue"))
	SLUA_ADD_GLOBAL_FUNCTION("getvarvalue",SE_GetFunctionID("getvarvalue"))
	SLUA_ADD_GLOBAL_FUNCTION("removeplayervar",SE_GetFunctionID("removeplayervar"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayervarvalue",SE_GetFunctionID("setplayervarvalue"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayervarvalue",SE_GetFunctionID("getplayervarvalue"))
	SLUA_ADD_GLOBAL_FUNCTION("addarrayelements",SE_GetFunctionID("addarrayelements"))
	SLUA_ADD_GLOBAL_FUNCTION("getobjectentity",SE_GetFunctionID("getobjectentity"))
	SLUA_ADD_GLOBAL_FUNCTION("setobjectvisibleforplayer",SE_GetFunctionID("setobjectvisibleforplayer"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayersspawnposition",SE_GetFunctionID("setplayersspawnposition"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakeradiowave",SE_GetFunctionID("setplayerfakeradiowave"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerholdinggun",SE_GetFunctionID("getplayerholdinggun"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerradiowave",SE_GetFunctionID("getplayerradiowave"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerroomid",SE_GetFunctionID("getplayerroomid"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayernvg",SE_GetFunctionID("getplayernvg"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayeranimation",SE_GetFunctionID("getplayeranimation"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerhazmat",SE_GetFunctionID("getplayerhazmat"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayergasmask",SE_GetFunctionID("getplayergasmask"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayervest",SE_GetFunctionID("getplayervest"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerreadystate",SE_GetFunctionID("getplayerreadystate"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerblinktimer",SE_GetFunctionID("getplayerblinktimer"))
	SLUA_ADD_GLOBAL_FUNCTION("callglobalfunction",SE_GetFunctionID("callglobalfunction"))
	SLUA_ADD_GLOBAL_FUNCTION("callfunction",SE_GetFunctionID("callfunction"))
	SLUA_ADD_GLOBAL_FUNCTION("movex",SE_GetFunctionID("movex"))
	SLUA_ADD_GLOBAL_FUNCTION("movey",SE_GetFunctionID("movey"))
	SLUA_ADD_GLOBAL_FUNCTION("movez",SE_GetFunctionID("movez"))
	SLUA_ADD_GLOBAL_FUNCTION("move",SE_GetFunctionID("move"))
	SLUA_ADD_GLOBAL_FUNCTION("pointangle",SE_GetFunctionID("pointangle"))
	SLUA_ADD_GLOBAL_FUNCTION("pointpitch",SE_GetFunctionID("pointpitch"))
	SLUA_ADD_GLOBAL_FUNCTION("pointat",SE_GetFunctionID("pointat"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayertype",SE_GetFunctionID("getplayertype"))
	SLUA_ADD_GLOBAL_FUNCTION("createsound",SE_GetFunctionID("createsound"))
	SLUA_ADD_GLOBAL_FUNCTION("createplayersound",SE_GetFunctionID("createplayersound"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerentity",SE_GetFunctionID("getplayerentity"))
	SLUA_ADD_GLOBAL_FUNCTION("setnotarget",SE_GetFunctionID("setnotarget"))
	SLUA_ADD_GLOBAL_FUNCTION("setcontained106",SE_GetFunctionID("setcontained106"))
	SLUA_ADD_GLOBAL_FUNCTION("setremotedooron",SE_GetFunctionID("setremotedooron"))
	SLUA_ADD_GLOBAL_FUNCTION("setmtftimer",SE_GetFunctionID("setmtftimer"))
	SLUA_ADD_GLOBAL_FUNCTION("getroomname",SE_GetFunctionID("getroomname"))
	SLUA_ADD_GLOBAL_FUNCTION("getroomentity",SE_GetFunctionID("getroomentity"))
	SLUA_ADD_GLOBAL_FUNCTION("isvalidroom",SE_GetFunctionID("isvalidroom"))
	SLUA_ADD_GLOBAL_FUNCTION("getroomobjectentity",SE_GetFunctionID("getroomobjectentity"))
	SLUA_ADD_GLOBAL_FUNCTION("setdooropenstate",SE_GetFunctionID("setdooropenstate"))
	SLUA_ADD_GLOBAL_FUNCTION("setdoorlock",SE_GetFunctionID("setdoorlock"))
	SLUA_ADD_GLOBAL_FUNCTION("getdoorentity",SE_GetFunctionID("getdoorentity"))
	SLUA_ADD_GLOBAL_FUNCTION("getdoortype",SE_GetFunctionID("getdoortype"))
	SLUA_ADD_GLOBAL_FUNCTION("getdooropenstate",SE_GetFunctionID("getdooropenstate"))
	SLUA_ADD_GLOBAL_FUNCTION("getdoorlock",SE_GetFunctionID("getdoorlock"))
	SLUA_ADD_GLOBAL_FUNCTION("isvaliddoor",SE_GetFunctionID("isvaliddoor"))
	SLUA_ADD_GLOBAL_FUNCTION("setitempicker",SE_GetFunctionID("setitempicker"))
	SLUA_ADD_GLOBAL_FUNCTION("getitemtemplatename",SE_GetFunctionID("getitemtemplatename"))
	SLUA_ADD_GLOBAL_FUNCTION("getitemtemplatetempname",SE_GetFunctionID("getitemtemplatetempname"))
	SLUA_ADD_GLOBAL_FUNCTION("getitemtemplate",SE_GetFunctionID("getitemtemplate"))
	SLUA_ADD_GLOBAL_FUNCTION("createitem",SE_GetFunctionID("createitem"))
	SLUA_ADD_GLOBAL_FUNCTION("removeitem",SE_GetFunctionID("removeitem"))
	SLUA_ADD_GLOBAL_FUNCTION("getitementity",SE_GetFunctionID("getitementity"))
	SLUA_ADD_GLOBAL_FUNCTION("getitempicker",SE_GetFunctionID("getitempicker"))
	SLUA_ADD_GLOBAL_FUNCTION("isvaliditem",SE_GetFunctionID("isvaliditem"))
	SLUA_ADD_GLOBAL_FUNCTION("isvalidnpc",SE_GetFunctionID("isvalidnpc"))
	SLUA_ADD_GLOBAL_FUNCTION("createnpc",SE_GetFunctionID("createnpc"))
	SLUA_ADD_GLOBAL_FUNCTION("getnpctype",SE_GetFunctionID("getnpctype"))
	SLUA_ADD_GLOBAL_FUNCTION("getnpcstate1",SE_GetFunctionID("getnpcstate1"))
	SLUA_ADD_GLOBAL_FUNCTION("getnpcstate2",SE_GetFunctionID("getnpcstate2"))
	SLUA_ADD_GLOBAL_FUNCTION("getnpcstate3",SE_GetFunctionID("getnpcstate3"))
	SLUA_ADD_GLOBAL_FUNCTION("setnpcstate",SE_GetFunctionID("setnpcstate"))
	SLUA_ADD_GLOBAL_FUNCTION("getnpcevent",SE_GetFunctionID("getnpcevent"))
	SLUA_ADD_GLOBAL_FUNCTION("getnpcentity",SE_GetFunctionID("getnpcentity"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventindex",SE_GetFunctionID("geteventindex"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventstate1",SE_GetFunctionID("geteventstate1"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventstate2",SE_GetFunctionID("geteventstate2"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventstate3",SE_GetFunctionID("geteventstate3"))
	SLUA_ADD_GLOBAL_FUNCTION("geteventstr",SE_GetFunctionID("geteventstr"))
	SLUA_ADD_GLOBAL_FUNCTION("isvalidevent",SE_GetFunctionID("isvalidevent"))
	SLUA_ADD_GLOBAL_FUNCTION("removeevent",SE_GetFunctionID("removeevent"))
	SLUA_ADD_GLOBAL_FUNCTION("seteventstr",SE_GetFunctionID("seteventstr"))
	SLUA_ADD_GLOBAL_FUNCTION("seteventstate",SE_GetFunctionID("seteventstate"))
	SLUA_ADD_GLOBAL_FUNCTION("rconcommand",SE_GetFunctionID("rconcommand"))
	SLUA_ADD_GLOBAL_FUNCTION("createrocket",SE_GetFunctionID("createrocket"))
	SLUA_ADD_GLOBAL_FUNCTION("closefile",SE_GetFunctionID("closefile"))
	SLUA_ADD_GLOBAL_FUNCTION("readline",SE_GetFunctionID("readline"))
	SLUA_ADD_GLOBAL_FUNCTION("readint",SE_GetFunctionID("readint"))
	SLUA_ADD_GLOBAL_FUNCTION("readfloat",SE_GetFunctionID("readfloat"))
	SLUA_ADD_GLOBAL_FUNCTION("readshort",SE_GetFunctionID("readshort"))
	SLUA_ADD_GLOBAL_FUNCTION("readbyte",SE_GetFunctionID("readbyte"))
	SLUA_ADD_GLOBAL_FUNCTION("writeline",SE_GetFunctionID("writeline"))
	SLUA_ADD_GLOBAL_FUNCTION("writeint",SE_GetFunctionID("writeint"))
	SLUA_ADD_GLOBAL_FUNCTION("writefloat",SE_GetFunctionID("writefloat"))
	SLUA_ADD_GLOBAL_FUNCTION("writeshort",SE_GetFunctionID("writeshort"))
	SLUA_ADD_GLOBAL_FUNCTION("writebyte",SE_GetFunctionID("writebyte"))
	SLUA_ADD_GLOBAL_FUNCTION("openfile",SE_GetFunctionID("openfile"))
	SLUA_ADD_GLOBAL_FUNCTION("readfile",SE_GetFunctionID("readfile"))
	SLUA_ADD_GLOBAL_FUNCTION("writefile",SE_GetFunctionID("writefile"))
	SLUA_ADD_GLOBAL_FUNCTION("setlightvolume",SE_GetFunctionID("setlightvolume"))
	SLUA_ADD_GLOBAL_FUNCTION("getlightvolume",SE_GetFunctionID("getlightvolume"))
	SLUA_ADD_GLOBAL_FUNCTION("setserverversion",SE_GetFunctionID("setserverversion"))
	SLUA_ADD_GLOBAL_FUNCTION("getserverversion",SE_GetFunctionID("getserverversion"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerversion",SE_GetFunctionID("getplayerversion"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerafk",SE_GetFunctionID("setplayerafk"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerafk",SE_GetFunctionID("getplayerafk"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayertextpos",SE_GetFunctionID("setplayertextpos"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayertextstring",SE_GetFunctionID("setplayertextstring"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayertextcolor",SE_GetFunctionID("setplayertextcolor"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerdrawcolor",SE_GetFunctionID("setplayerdrawcolor"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerdrawpos",SE_GetFunctionID("setplayerdrawpos"))
	SLUA_ADD_GLOBAL_FUNCTION("millisecs",SE_GetFunctionID("millisecs"))
	SLUA_ADD_GLOBAL_FUNCTION("isplayerconnected",SE_GetFunctionID("isplayerconnected"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfogrange",SE_GetFunctionID("setplayerfogrange"))
	SLUA_ADD_GLOBAL_FUNCTION("restartserver",SE_GetFunctionID("restartserver"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayermonitorwidth",SE_GetFunctionID("getplayermonitorwidth"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayermonitorheight",SE_GetFunctionID("getplayermonitorheight"))
	SLUA_ADD_GLOBAL_FUNCTION("setmapseed",SE_GetFunctionID("setmapseed"))
	SLUA_ADD_GLOBAL_FUNCTION("removeobject",SE_GetFunctionID("removeobject"))
	SLUA_ADD_GLOBAL_FUNCTION("createobject",SE_GetFunctionID("createobject"))
	SLUA_ADD_GLOBAL_FUNCTION("removeplayertext",SE_GetFunctionID("removeplayertext"))
	SLUA_ADD_GLOBAL_FUNCTION("createplayertext",SE_GetFunctionID("createplayertext"))
	SLUA_ADD_GLOBAL_FUNCTION("removeplayerdraw",SE_GetFunctionID("removeplayerdraw"))
	SLUA_ADD_GLOBAL_FUNCTION("createplayerdraw",SE_GetFunctionID("createplayerdraw"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayertype",SE_GetFunctionID("setplayertype"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerip",SE_GetFunctionID("getplayerip"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerping",SE_GetFunctionID("getplayerping"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayernickname",SE_GetFunctionID("getplayernickname"))
	SLUA_ADD_GLOBAL_FUNCTION("shoot",SE_GetFunctionID("shoot"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerping",SE_GetFunctionID("setplayerping"))
	SLUA_ADD_GLOBAL_FUNCTION("isafakeplayer",SE_GetFunctionID("isafakeplayer"))
	SLUA_ADD_GLOBAL_FUNCTION("createfakeplayer",SE_GetFunctionID("createfakeplayer"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerloadstate",SE_GetFunctionID("getplayerloadstate"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerdeadstate",SE_GetFunctionID("getplayerdeadstate"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakedeadstate",SE_GetFunctionID("setplayerfakedeadstate"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakeholdinggun",SE_GetFunctionID("setplayerfakeholdinggun"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakenvg",SE_GetFunctionID("setplayerfakenvg"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakegasmask",SE_GetFunctionID("setplayerfakegasmask"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakevest",SE_GetFunctionID("setplayerfakevest"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakeanimation",SE_GetFunctionID("setplayerfakeanimation"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakehazmat",SE_GetFunctionID("setplayerfakehazmat"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerfakeblinktimer",SE_GetFunctionID("setplayerfakeblinktimer"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerposition",SE_GetFunctionID("setplayerposition"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerpositionid",SE_GetFunctionID("setplayerpositionid"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayerangle",SE_GetFunctionID("setplayerangle"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayermessage",SE_GetFunctionID("setplayermessage"))
	SLUA_ADD_GLOBAL_FUNCTION("giveadmin",SE_GetFunctionID("giveadmin"))
	SLUA_ADD_GLOBAL_FUNCTION("removeadmin",SE_GetFunctionID("removeadmin"))
	SLUA_ADD_GLOBAL_FUNCTION("isplayeradmin",SE_GetFunctionID("isplayeradmin"))
	SLUA_ADD_GLOBAL_FUNCTION("servermessage",SE_GetFunctionID("servermessage"))
	SLUA_ADD_GLOBAL_FUNCTION("playerconsolecommand",SE_GetFunctionID("playerconsolecommand"))
	SLUA_ADD_GLOBAL_FUNCTION("playplayersound",SE_GetFunctionID("playplayersound"))
	SLUA_ADD_GLOBAL_FUNCTION("playsound",SE_GetFunctionID("playsound"))
	SLUA_ADD_GLOBAL_FUNCTION("sendmessage",SE_GetFunctionID("sendmessage"))
	SLUA_ADD_GLOBAL_FUNCTION("kick",SE_GetFunctionID("kick"))
	SLUA_ADD_GLOBAL_FUNCTION("banip",SE_GetFunctionID("banip"))
	SLUA_ADD_GLOBAL_FUNCTION("plugin_remove",SE_GetFunctionID("plugin_remove"))
	SLUA_ADD_GLOBAL_FUNCTION("plugin_call",SE_GetFunctionID("plugin_call"))
	SLUA_ADD_GLOBAL_FUNCTION("plugin_poke",SE_GetFunctionID("plugin_poke"))
	SLUA_ADD_GLOBAL_FUNCTION("plugin_load",SE_GetFunctionID("plugin_load"))
	SLUA_ADD_GLOBAL_FUNCTION("delay",SE_GetFunctionID("delay"))
	SLUA_ADD_GLOBAL_FUNCTION("print",SE_GetFunctionID("print"))
	SLUA_ADD_GLOBAL_FUNCTION("getinivalue",SE_GetFunctionID("getinivalue"))
	SLUA_ADD_GLOBAL_FUNCTION("putinivalue",SE_GetFunctionID("putinivalue"))
	SLUA_ADD_GLOBAL_FUNCTION("getunixtime",SE_GetFunctionID("getunixtime"))
	SLUA_ADD_GLOBAL_FUNCTION("updateinifile",SE_GetFunctionID("updateinifile"))
	SLUA_ADD_GLOBAL_FUNCTION("int",SE_GetFunctionID("int"))
	SLUA_ADD_GLOBAL_FUNCTION("float",SE_GetFunctionID("float"))
	SLUA_ADD_GLOBAL_FUNCTION("str",SE_GetFunctionID("str"))
	SLUA_ADD_GLOBAL_FUNCTION("floor",SE_GetFunctionID("floor"))
	SLUA_ADD_GLOBAL_FUNCTION("ceil",SE_GetFunctionID("ceil"))
	SLUA_ADD_GLOBAL_FUNCTION("sgn",SE_GetFunctionID("sgn"))
	SLUA_ADD_GLOBAL_FUNCTION("abs",SE_GetFunctionID("abs"))
	SLUA_ADD_GLOBAL_FUNCTION("sqr",SE_GetFunctionID("sqr"))
	SLUA_ADD_GLOBAL_FUNCTION("sin",SE_GetFunctionID("sin"))
	SLUA_ADD_GLOBAL_FUNCTION("cos",SE_GetFunctionID("cos"))
	SLUA_ADD_GLOBAL_FUNCTION("tan",SE_GetFunctionID("tan"))
	SLUA_ADD_GLOBAL_FUNCTION("asin",SE_GetFunctionID("asin"))
	SLUA_ADD_GLOBAL_FUNCTION("acos",SE_GetFunctionID("acos"))
	SLUA_ADD_GLOBAL_FUNCTION("atan",SE_GetFunctionID("atan"))
	SLUA_ADD_GLOBAL_FUNCTION("atan2",SE_GetFunctionID("atan2"))
	SLUA_ADD_GLOBAL_FUNCTION("exp",SE_GetFunctionID("exp"))
	SLUA_ADD_GLOBAL_FUNCTION("log",SE_GetFunctionID("log"))
	SLUA_ADD_GLOBAL_FUNCTION("log10",SE_GetFunctionID("log10"))
	SLUA_ADD_GLOBAL_FUNCTION("rnd",SE_GetFunctionID("rnd"))
	SLUA_ADD_GLOBAL_FUNCTION("rand",SE_GetFunctionID("rand"))
	SLUA_ADD_GLOBAL_FUNCTION("num",SE_GetFunctionID("num"))
	SLUA_ADD_GLOBAL_FUNCTION("left",SE_GetFunctionID("left"))
	SLUA_ADD_GLOBAL_FUNCTION("right",SE_GetFunctionID("right"))
	SLUA_ADD_GLOBAL_FUNCTION("mid",SE_GetFunctionID("mid"))
	SLUA_ADD_GLOBAL_FUNCTION("replace",SE_GetFunctionID("replace"))
	SLUA_ADD_GLOBAL_FUNCTION("instr",SE_GetFunctionID("instr"))
	SLUA_ADD_GLOBAL_FUNCTION("lower",SE_GetFunctionID("lower"))
	SLUA_ADD_GLOBAL_FUNCTION("upper",SE_GetFunctionID("upper"))
	SLUA_ADD_GLOBAL_FUNCTION("trim",SE_GetFunctionID("trim"))
	SLUA_ADD_GLOBAL_FUNCTION("hex",SE_GetFunctionID("hex"))
	SLUA_ADD_GLOBAL_FUNCTION("bin",SE_GetFunctionID("bin"))
	SLUA_ADD_GLOBAL_FUNCTION("repeat",SE_GetFunctionID("repeat"))
	SLUA_ADD_GLOBAL_FUNCTION("array_create",SE_GetFunctionID("array_create"))
	SLUA_ADD_GLOBAL_FUNCTION("array_push",SE_GetFunctionID("array_push"))
	SLUA_ADD_GLOBAL_FUNCTION("array_pop",SE_GetFunctionID("array_pop"))
	SLUA_ADD_GLOBAL_FUNCTION("array_delete",SE_GetFunctionID("array_delete"))
	SLUA_ADD_GLOBAL_FUNCTION("array_sort",SE_GetFunctionID("array_sort"))
	SLUA_ADD_GLOBAL_FUNCTION("array_from_string",SE_GetFunctionID("array_from_string"))
	SLUA_ADD_GLOBAL_FUNCTION("changeplayername",SE_GetFunctionID("changeplayername"))
	SLUA_ADD_GLOBAL_FUNCTION("changeplayertag",SE_GetFunctionID("changeplayertag"))
	SLUA_ADD_GLOBAL_FUNCTION("isplayerpatron",SE_GetFunctionID("isplayerpatron"))
	SLUA_ADD_GLOBAL_FUNCTION("getincomingconnectionversion",SE_GetFunctionID("getincomingconnectionversion"))
	SLUA_ADD_GLOBAL_FUNCTION("getincomingconnectionpatron",SE_GetFunctionID("getincomingconnectionpatron"))
	SLUA_ADD_GLOBAL_FUNCTION("setmtftickets",SE_GetFunctionID("setmtftickets"))
	SLUA_ADD_GLOBAL_FUNCTION("setchaostickets",SE_GetFunctionID("setchaostickets"))
	SLUA_ADD_GLOBAL_FUNCTION("getmtftickets",SE_GetFunctionID("getmtftickets"))
	SLUA_ADD_GLOBAL_FUNCTION("getchaostickets",SE_GetFunctionID("getchaostickets"))
	SLUA_ADD_GLOBAL_FUNCTION("activatefilessending",SE_GetFunctionID("activatefilessending"))
	SLUA_ADD_GLOBAL_FUNCTION("setserverexplodetimeout",SE_GetFunctionID("setserverexplodetimeout"))
	SLUA_ADD_GLOBAL_FUNCTION("isvalidroomdoor",SE_GetFunctionID("isvalidroomdoor"))
	SLUA_ADD_GLOBAL_FUNCTION("entitydistance",SE_GetFunctionID("entitydistance"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayerhitbox",SE_GetFunctionID("getplayerhitbox"))
	SLUA_ADD_GLOBAL_FUNCTION("setcustommap",SE_GetFunctionID("setcustommap"))
	SLUA_ADD_GLOBAL_FUNCTION("entityscalex",SE_GetFunctionID("entityscalex"))
	SLUA_ADD_GLOBAL_FUNCTION("entityscaley",SE_GetFunctionID("entityscaley"))
	SLUA_ADD_GLOBAL_FUNCTION("entityscalez",SE_GetFunctionID("entityscalez"))
	SLUA_ADD_GLOBAL_FUNCTION("meshwidth",SE_GetFunctionID("meshwidth"))
	SLUA_ADD_GLOBAL_FUNCTION("meshheight",SE_GetFunctionID("meshheight"))
	SLUA_ADD_GLOBAL_FUNCTION("meshdepth",SE_GetFunctionID("meshdepth"))
	SLUA_ADD_GLOBAL_FUNCTION("setplayernoclip",SE_GetFunctionID("setplayernoclip"))
	SLUA_ADD_GLOBAL_FUNCTION("getplayernoclip",SE_GetFunctionID("getplayernoclip"))
	SLUA_ADD_GLOBAL_FUNCTION("createevent",SE_GetFunctionID("createevent"))
	SLUA_ADD_GLOBAL_FUNCTION("createitemtemplate",SE_GetFunctionID("createitemtemplate"))

	SLUA_ADD_GLOBAL_FUNCTION("onluaerror",900)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D
