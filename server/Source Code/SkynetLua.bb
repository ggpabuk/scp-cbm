Include "Source Code\SQLite3.bb"

Global CurrentParam
Global PublicReturnedValue$

Function SE_GetParamInt%()
	CurrentParam = CurrentParam+1
	Return SE_AND_LUA_ToStringArg(CurrentParam-1)
End Function

Function SE_GetParamFloat#()
	CurrentParam = CurrentParam+1
	Return SE_AND_LUA_ToStringArg(CurrentParam-1)
End Function

Function SE_GetParamString$()
	CurrentParam = CurrentParam+1
	Return SE_AND_LUA_ToStringArg(CurrentParam-1)
End Function

Function SE_AND_LUA_ToIntArg(index)
	if CurrentLuaState <> 0 then
		Return SLUA_TO_INTEGER(CurrentLuaState, index+1)
	else
		Return SE_ToIntArg(index)
	endif
End Function
Function SE_AND_LUA_ToFloatArg#(index)
	if CurrentLuaState <> 0 then
		Return SLUA_TO_FLOAT(CurrentLuaState, index+1)
	else
		Return SE_ToFloatArg(index)
	endif
End Function
Function SE_AND_LUA_ToStringArg$(index)
	if CurrentLuaState <> 0 then
		Return SLUA_TO_STRING(CurrentLuaState, index+1)
	else
		Return SE_ToStringArg(index)
	endif
End Function

const func_setplayervelocity = 1
const func_getplayervelocity = 2
const func_setplayerhandcuff = 3
const func_getplayersteamid = 4
const func_giveplayerhealth = 5
const func_setplayerspeedmult = 6
const func_sendblinktimerforplayer = 7
const func_zlib_compress = 8
const func_zlib_uncompress = 9
const func_deactivatewarheads = 10
const func_spawnmtf = 11
const func_activatewarheads = 12
const func_getplayerzone = 13
const func_getbreachtimer = 14
const func_addaccessversion = 15
const func_getplayerinjuries = 16
const func_setplayerinjuries = 17
const func_getplayerheadpitch = 18
const func_setplayerheadpitch = 19
const func_createentitycamera = 20
const func_entityinview = 21
const func_entityvisible = 22
const func_entityx = 23
const func_entityy = 24
const func_entityz = 25
const func_entitypitch = 26
const func_entityyaw = 27
const func_entityroll = 28
const func_entitypick = 29
const func_positionentity = 30
const func_rotateentity = 31
const func_moveentity = 32
const func_scaleentity = 33
const func_setentitycollision = 34
const func_removeentity = 35
const func_setanimtime = 36
const func_getdelta = 37
const func_sendrawpacket = 38
const func_sendscript = 39
const func_sendfile = 40
const func_setgamestate = 41
const func_setservername = 42
const func_setserverdescription = 43
const func_setserverpassword = 44
const func_setservercurrentbreachtimer = 45
const func_setservernocheat = 46
const func_getplayerhealth = 47
const func_setplayerfakehealth = 48
const func_disableautokick = 49
const func_reloadapp = 50
const func_hexstring = 51
const func_removetimer = 52
const func_createtimer = 53
const func_serverwritebyte = 54
const func_serverwriteshort = 55
const func_serverwriteint = 56
const func_serverwritefloat = 57
const func_debug = 58
const func_eof = 59
const func_asc = 60
const func_chr = 61
const func_filetype = 62
const func_filesize = 63
const func_seekfile = 64
const func_filepos = 65
const func_catcherror = 66
const func_getpacketindex = 67
const func_sendrawdata = 68
const func_writebytes = 69
const func_readbytes = 70
const func_createbank = 71
const func_freebank = 72
const func_banksize = 73
const func_resizebank = 74
const func_copybank = 75
const func_peekbyte = 76
const func_peekshort = 77
const func_peekint = 78
const func_peekfloat = 79
const func_pokebyte = 80
const func_pokeshort = 81
const func_pokeint = 82
const func_pokefloat = 83
const func_disablelobby = 84
const func_removevar = 85
const func_setvarvalue = 86
const func_getvarvalue = 87
const func_removeplayervar = 88
const func_setplayervarvalue = 89
const func_getplayervarvalue = 90
const func_addarrayelements = 91
const func_getobjectentity = 92
const func_setobjectvisibleforplayer = 93
const func_setplayersspawnposition = 94
const func_setplayerfakeradiowave = 95
const func_getplayerholdinggun = 96
const func_getplayerradiowave = 97
const func_getplayerroomid = 98
const func_getplayernvg = 99
const func_getplayeranimation = 100
const func_getplayerhazmat = 101
const func_getplayervest = 102
const func_getplayerreadystate = 103
const func_getplayerblinktimer = 104
const func_callglobalfunction = 105
const func_callfunction = 106
const func_movex = 107
const func_movey = 108
const func_movez = 109
const func_move = 110
const func_pointangle = 111
const func_pointpitch = 112
const func_pointat = 113
const func_getplayertype = 114
const func_createsound = 115
const func_createplayersound = 116
const func_getplayerentity = 117
const func_setnotarget = 118
const func_setcontained106 = 119
const func_setremotedooron = 120
const func_setmtftimer = 121
const func_getroomname = 122
const func_getroomentity = 123
const func_isvalidroom = 124
const func_getroomobjectentity = 125
const func_setdooropenstate = 126
const func_setdoorlock = 127
const func_getdoorentity = 128
const func_getdoortype = 129
const func_getdooropenstate = 130
const func_getdoorlock = 131
const func_isvaliddoor = 132
const func_setitempicker = 133
const func_getitemtemplatename = 134
const func_getitemtemplatetempname = 135
const func_getitemtemplate = 136
const func_createitem = 137
const func_removeitem = 138
const func_getitementity = 139
const func_getitempicker = 140
const func_isvaliditem = 141
const func_isvalidnpc = 142
const func_createnpc = 143
const func_getnpctype = 144
const func_getnpcstate1 = 145
const func_getnpcstate2 = 146
const func_getnpcstate3 = 147
const func_setnpcstate = 148
const func_getnpcevent = 149
const func_getnpcentity = 150
const func_geteventindex = 151
const func_geteventstate1 = 152
const func_geteventstate2 = 153
const func_geteventstate3 = 154
const func_geteventstr = 155
const func_isvalidevent = 156
const func_removeevent = 157
const func_seteventstr = 158
const func_seteventstate = 159
const func_rconcommand = 160
const func_createrocket = 161
const func_closefile = 162
const func_readline = 163
const func_readint = 164
const func_readfloat = 165
const func_readshort = 166
const func_readbyte = 167
const func_writeline = 168
const func_writeint = 169
const func_writefloat = 170
const func_writeshort = 171
const func_writebyte = 172
const func_openfile = 173
const func_readfile = 174
const func_writefile = 175
const func_setlightvolume = 176
const func_getlightvolume = 177
const func_setserverversion = 178
const func_getserverversion = 179
const func_getplayerversion = 180
const func_setplayerafk = 181
const func_getplayerafk = 182
const func_setplayertextpos = 183
const func_setplayertextstring = 184
const func_setplayertextcolor = 185
const func_setplayerdrawcolor = 186
const func_setplayerdrawpos = 187
const func_millisecs = 188
const func_isplayerconnected = 189
const func_setplayerfogrange = 190
const func_restartserver = 191
const func_getplayermonitorwidth = 192
const func_getplayermonitorheight = 193
const func_setmapseed = 194
const func_removeobject = 195
const func_createobject = 196
const func_removeplayertext = 197
const func_createplayertext = 198
const func_removeplayerdraw = 199
const func_createplayerdraw = 200
const func_setplayertype = 201
const func_getplayerip = 202
const func_getplayerping = 203
const func_getplayernickname = 204
const func_shoot = 205
const func_setplayerping = 206
const func_isafakeplayer = 207
const func_createfakeplayer = 208
const func_getplayerloadstate = 209
const func_getplayerdeadstate = 210
const func_setplayerfakedeadstate = 211
const func_setplayerfakeholdinggun = 212
const func_setplayerfakenvg = 213
const func_setplayerfakegasmask = 214
const func_setplayerfakevest = 215
const func_setplayerfakeanimation = 216
const func_setplayerfakehazmat = 217
const func_setplayerfakeblinktimer = 218
const func_setplayerposition = 219
const func_setplayerangle = 220
const func_setplayermessage = 221
const func_giveadmin = 222
const func_removeadmin = 223
const func_isplayeradmin = 224
const func_servermessage = 225
const func_playerconsolecommand = 226
const func_playplayersound = 227
const func_playsound = 228
const func_sendmessage = 229
const func_kick = 230
const func_banip = 231
const func_plugin_remove = 232
const func_plugin_call = 233
const func_plugin_poke = 234
const func_plugin_load = 235
const func_delay = 236
const func_print = 237
const func_getinivalue = 238
const func_putinivalue = 239
const func_getunixtime = 240
const func_updateinifile = 241
const func_int = 242
const func_float = 243
const func_str = 244
const func_floor = 245
const func_ceil = 246
const func_sgn = 247
const func_abs = 248
const func_sqr = 249
const func_sin = 250
const func_cos = 251
const func_tan = 252
const func_asin = 253
const func_acos = 254
const func_atan = 255
const func_atan2 = 256
const func_exp = 257
const func_log = 258
const func_log10 = 259
const func_rnd = 260
const func_rand = 261
const func_num = 262
const func_left = 263
const func_right = 264
const func_mid = 265
const func_replace = 266
const func_instr = 267
const func_lower = 268
const func_upper = 269
const func_trim = 270
const func_hex = 274
const func_bin = 275
const func_repeat = 276
const func_array_create = 277
const func_array_push = 278
const func_array_pop = 279
const func_array_delete = 280
const func_array_sort = 281
const func_array_from_string = 282
const func_changeplayername = 283
const func_changeplayertag = 284
const func_isplayerpatron = 285
const func_getincomingconnectionversion = 286
const func_getincomingconnectionpatron = 287
const func_setmtftickets = 288
const func_setchaostickets = 289
const func_getmtftickets = 290
const func_getchaostickets = 291
const func_activatefilessending = 292
const func_setserverexplodetimeout = 293
const func_enableauthkeyonconnect = 294
const func_getserverexplodetimeout = 295
const func_getserverspawntimeout = 296
const func_setserverspawntimeout = 297

; sqlite region
const func_sqlite_OpenDatabase = 298
const func_sqlite_CloseDatabase = 299
const func_sqlite_SetDatabaseTimeout = 300
const func_sqlite_DatabaseVersion = 301
const func_sqlite_LastRowIDInserted = 302
const func_sqlite_RowsChangedByLastStatement = 303
const func_sqlite_RowsChangedThisSession = 304
const func_sqlite_AutoCommitIsOn = 305
const func_sqlite_BeginTransaction = 306
const func_sqlite_CommitTransaction = 307
const func_sqlite_RollbackTransaction = 308
const func_sqlite_LastDatabaseErrorCode = 309
const func_sqlite_LastDatabaseErrorMessage = 310
const func_sqlite_InterruptDatabase = 311
const func_sqlite_ExecuteSQL = 312
const func_sqlite_PrepareSQL = 313
const func_sqlite_GetNextDataRow = 314
const func_sqlite_FinaliseSQL = 315
const func_sqlite_ResetSQL = 316
const func_sqlite_SQLHasExpired = 317
const func_sqlite_GetDatabaseHandleFromStatementHandle = 318
const func_sqlite_GetColumnCount = 319
const func_sqlite_GetColumnName = 320
const func_sqlite_GetColumnType = 321
const func_sqlite_GetColumnDeclaredType = 322
const func_sqlite_GetColumnSize = 323
const func_sqlite_GetColumnValueAsInteger = 324
const func_sqlite_GetColumnValueAsFloat = 325
const func_sqlite_GetColumnValueAsString = 326
const func_sqlite_GetColumnValueAsBlob = 327
const func_sqlite_SQLParameterCount = 328
const func_sqlite_SQLParameterName = 329
const func_sqlite_SQLParameterIndex = 330
const func_sqlite_BindSQLParameterAsNull = 331
const func_sqlite_BindSQLParameterAsInteger = 332
const func_sqlite_BindSQLParameterAsFloat = 333
const func_sqlite_BindSQLParameterAsString = 334
const func_sqlite_BindSQLParameterAsBlob = 335
const func_sqlite_TransferSQLBindings = 336
const func_sqlite_SQLite3_ErrorHasOccurred = 336
;

; mysql region
const func_mysql_OpenSQLStream = 337
const func_mysql_SQLConnected = 338
const func_mysql_SQLQuery = 339
const func_mysql_SQLRowCount = 340
const func_mysql_SQLFetchRow = 341
const func_mysql_SQLFieldCount = 342
const func_mysql_ReadSQLField = 343
const func_mysql_ReadSQLFieldIndex = 344
const func_mysql_FreeSQLQuery = 345
const func_mysql_FreeSQLRow = 346
const func_mysql_CloseSQLStream = 347
;

const func_closeapp = 348
const func_setplayermute = 349
const func_getplayermute = 350
const func_enableoldresponse = 351
const func_geteventname = 352
const func_geteventroomid = 353
const func_getmaxspawnplayers = 354
const func_setmaxspawnplayers = 355
const func_setdescriptionline = 356
const func_getdescriptionline = 357
const func_createsteaminstance = 358
const func_getsteaminstancetag = 359
const func_removesteaminstance = 360
const func_connecttocentralserver = 361
const func_reconnecttocentralserver = 362

;

const func_dllfunctionvoid_0 = 363
const func_dllfunctionvoid_1 = 364
const func_dllfunctionvoid_2 = 365
const func_dllfunctionvoid_3 = 366
const func_dllfunctionvoid_4 = 367
const func_dllfunctionvoid_5 = 368
const func_dllfunctionvoid_6 = 369
const func_dllfunctionvoid_7 = 370
const func_dllfunctionvoid_8 = 371
const func_dllfunctionvoid_9 = 372

const func_dllfunctionint_0 = 373
const func_dllfunctionint_1 = 374
const func_dllfunctionint_2 = 375
const func_dllfunctionint_3 = 376
const func_dllfunctionint_4 = 377
const func_dllfunctionint_5 = 378
const func_dllfunctionint_6 = 379
const func_dllfunctionint_7 = 380
const func_dllfunctionint_8 = 381
const func_dllfunctionint_9 = 382

const func_dllfunctionfloat_0 = 383
const func_dllfunctionfloat_1 = 384
const func_dllfunctionfloat_2 = 385
const func_dllfunctionfloat_3 = 386
const func_dllfunctionfloat_4 = 387
const func_dllfunctionfloat_5 = 388
const func_dllfunctionfloat_6 = 389
const func_dllfunctionfloat_7 = 390
const func_dllfunctionfloat_8 = 391
const func_dllfunctionfloat_9 = 392

const func_dllfunctionstring_0 = 393
const func_dllfunctionstring_1 = 394
const func_dllfunctionstring_2 = 395
const func_dllfunctionstring_3 = 396
const func_dllfunctionstring_4 = 397
const func_dllfunctionstring_5 = 398
const func_dllfunctionstring_6 = 399
const func_dllfunctionstring_7 = 400
const func_dllfunctionstring_8 = 401
const func_dllfunctionstring_9 = 402
;

const func_lerp = 403
const func_lerpangle = 404
const func_changeplayersize = 405
const func_getplayersize = 406

const func_getroomdoor = 407
const func_isvalidroomdoor = 408
const func_ShouldPlayerAnnouncement = 409
const func_setobjectinterpolation = 410
const func_preparemodelidentifier = 411
const func_updateobjects = 412

const func_getplayerspeedmult = 414

const func_addplayerspawnposition = 415
const func_addclassspawnposition = 416
const func_removespawnposition = 417
const func_isvalidspawnposition = 418
const func_changeplayersteamid = 419

Function SE_IsValidFunction(functionname$)
	Select FunctionName$
		case "changeplayersteamid": Return True
		case "getplayerspeedmult": Return True
		case "updateobjects": Return True
		case "preparemodelidentifier": Return True
		case "setobjectinterpolation": Return True
		case "drawuiinputbox": Return True
		case "getkey": Return True
		case "isvalidroomdoor": Return True
		case "getroomdoor": Return True
		case "changeplayersize": Return True
		case "getplayersize": Return True
		;
		case "dllfunctionvoid_0": Return True
		case "dllfunctionvoid_1": Return True
		case "dllfunctionvoid_2": Return True
		case "dllfunctionvoid_3": Return True
		case "dllfunctionvoid_4": Return True
		case "dllfunctionvoid_5": Return True
		case "dllfunctionvoid_6": Return True
		case "dllfunctionvoid_7": Return True
		case "dllfunctionvoid_8": Return True
		case "dllfunctionvoid_9": Return True

		case "dllfunctionint_0": Return True
		case "dllfunctionint_1": Return True
		case "dllfunctionint_2": Return True
		case "dllfunctionint_3": Return True
		case "dllfunctionint_4": Return True
		case "dllfunctionint_5": Return True
		case "dllfunctionint_6": Return True
		case "dllfunctionint_7": Return True
		case "dllfunctionint_8": Return True
		case "dllfunctionint_9": Return True

		case "dllfunctionfloat_0": Return True
		case "dllfunctionfloat_1": Return True
		case "dllfunctionfloat_2": Return True
		case "dllfunctionfloat_3": Return True
		case "dllfunctionfloat_4": Return True
		case "dllfunctionfloat_5": Return True
		case "dllfunctionfloat_6": Return True
		case "dllfunctionfloat_7": Return True
		case "dllfunctionfloat_8": Return True
		case "dllfunctionfloat_9": Return True

		case "dllfunctionstring_0": Return True
		case "dllfunctionstring_1": Return True
		case "dllfunctionstring_2": Return True
		case "dllfunctionstring_3": Return True
		case "dllfunctionstring_4": Return True
		case "dllfunctionstring_5": Return True
		case "dllfunctionstring_6": Return True
		case "dllfunctionstring_7": Return True
		case "dllfunctionstring_8": Return True
		case "dllfunctionstring_9": Return True
		
		case "connecttocentralserver": Return True
		case "reconnecttocentralserver": Return True
		case "createsteaminstance": Return True
		case "getsteaminstancetag": Return True
		case "removesteaminstance": Return True
		case "setdescriptionline": Return True
		case "getdescriptionline": Return True
		case "setmaxspawnplayers": Return True
		case "getmaxspawnplayers": Return True
		case "geteventname": Return True
		case "geteventroomid": Return True
		case "enableoldresponse": Return True ;func_enableoldresponse
		case "closeapp": Return True ;func_closeapp
		case "getplayermute": Return True ;func_getplayermute
		case "setplayermute": Return True ;func_setplayermute
		; Sqlite
		case "opendatabase": Return True ;func_sqlite_opendatabase
		case "closedatabase": Return True ;func_sqlite_closedatabase
		case "setdatabasetimeout": Return True ;func_sqlite_setdatabasetimeout
		case "databaseversion": Return True ;func_sqlite_databaseversion
		case "lastrowidinserted": Return True ;func_sqlite_lastrowidinserted
		case "rowschangedbylaststatement": Return True ;func_sqlite_rowschangedbylaststatement
		case "rowschangedthissession": Return True ;func_sqlite_rowschangedthissession
		case "autocommitison": Return True ;func_sqlite_autocommitison
		case "begintransaction": Return True ;func_sqlite_begintransaction
		case "committransaction": Return True ;func_sqlite_committransaction
		case "rollbacktransaction": Return True ;func_sqlite_rollbacktransaction
		case "lastdatabaseerrorcode": Return True ;func_sqlite_lastdatabaseerrorcode
		case "lastdatabaseerrormessage": Return True ;func_sqlite_lastdatabaseerrormessage
		case "interruptdatabase": Return True ;func_sqlite_interruptdatabase
		case "executesql": Return True ;func_sqlite_executesql
		case "preparesql": Return True ;func_sqlite_preparesql
		case "getnextdatarow": Return True ;func_sqlite_getnextdatarow
		case "finalisesql": Return True ;func_sqlite_finalisesql
		case "resetsql": Return True ;func_sqlite_resetsql
		case "sqlhasexpired": Return True ;func_sqlite_sqlhasexpired
		case "getdatabasehandlefromstatementhandle": Return True ;func_sqlite_getdatabasehandlefromstatementhandle
		case "getcolumncount": Return True ;func_sqlite_getcolumncount
		case "getcolumnname": Return True ;func_sqlite_getcolumnname
		case "getcolumntype": Return True ;func_sqlite_getcolumntype
		case "getcolumndeclaredtype": Return True ;func_sqlite_getcolumndeclaredtype
		case "getcolumnsize": Return True ;func_sqlite_getcolumnsize
		case "getcolumnvalueasinteger": Return True ;func_sqlite_getcolumnvalueasinteger
		case "getcolumnvalueasfloat": Return True ;func_sqlite_getcolumnvalueasfloat
		case "getcolumnvalueasstring": Return True ;func_sqlite_getcolumnvalueasstring
		case "getcolumnvalueasblob": Return True ;func_sqlite_getcolumnvalueasblob
		case "sqlparametercount": Return True ;func_sqlite_sqlparametercount
		case "sqlparametername": Return True ;func_sqlite_sqlparametername
		case "sqlparameterindex": Return True ;func_sqlite_sqlparameterindex
		case "bindsqlparameterasnull": Return True ;func_sqlite_bindsqlparameterasnull
		case "bindsqlparameterasinteger": Return True ;func_sqlite_bindsqlparameterasinteger
		case "bindsqlparameterasfloat": Return True ;func_sqlite_bindsqlparameterasfloat
		case "bindsqlparameterasstring": Return True ;func_sqlite_bindsqlparameterasstring
		case "bindsqlparameterasblob": Return True ;func_sqlite_bindsqlparameterasblob
		case "transfersqlbindings": Return True ;func_sqlite_transfersqlbindings
		case "sqlite3_errorhasoccurred": Return True ;func_sqlite_sqlite3_errorhasoccurred
		; ==========================================================================
		
		; mysql
		case "opensqlstream": Return True ;func_mysql_opensqlstream
		case "sqlconnected": Return True ;func_mysql_sqlconnected
		case "sqlquery": Return True ;func_mysql_sqlquery
		case "sqlrowcount": Return True ;func_mysql_sqlrowcount
		case "sqlfetchrow": Return True ;func_mysql_sqlfetchrow
		case "sqlfieldcount": Return True ;func_mysql_sqlfieldcount
		case "readsqlfield": Return True ;func_mysql_readsqlfield
		case "readsqlfieldindex": Return True ;func_mysql_readsqlfieldindex
		case "freesqlquery": Return True ;func_mysql_freesqlquery
		case "freesqlrow": Return True ;func_mysql_freesqlrow
		case "closesqlstream": Return True ;func_mysql_closesqlstream
		; ==========================================================================
		case "getserverexplodetimeout"
			Return True ;func_getserverexplodetimeout
		case "getserverspawntimeout"
			Return True ;func_getserverspawntimeout
		case "setserverspawntimeout"
			Return True ;func_setserverspawntimeout
		case "enableauthkeyonconnect"
			Return True ;func_enableauthkeyonconnect
		case "setplayervelocity"
			Return True ;func_setplayervelocity
		case "getplayervelocity"
			Return True ;func_getplayervelocity
		case "setplayerhandcuff"
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
		case "spawnmtf"
			Return True ;func_spawnmtf
		case "activatewarheads"
			Return True ;func_activatewarheads
		case "getplayerzone"
			Return True ;func_getplayerzone
		case "getbreachtimer"
			Return True ;func_getbreachtimer
		case "addaccessversion"
			Return True ;func_addaccessversion
		Case "getplayerinjuries"
			Return True ;func_getplayerinjuries
		Case "setplayerinjuries"
			Return True ;func_setplayerinjuries
		case "getplayerheadpitch"
			Return True ;func_getplayerheadpitch
		case "setplayerheadpitch"
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
		case "getunixtime"
			Return True ;func_getunixtime
		case "updateinifile"
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
		case "getincomingconnectionversion"
			Return True ;func_getincomingconnectionversion
		case "getincomingconnectionpatron"
			Return True ;func_getincomingconnectionpatron
		case "setmtftickets"
			Return True ;func_setmtftickets
		case "setchaostickets"
			Return True ;func_setchaostickets
		case "getmtftickets"
			Return True ;func_getmtftickets
		case "getchaostickets"
			Return True ;func_getchaostickets
		case "activatefilessending"
			Return True ;func_activatefilessending
		case "setserverexplodetimeout"
			Return True ;func_setserverexplodetimeout
		case "debuglog"
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
		Case "stopchannel"
			Return True ;func_stopchannel
		Case "resumechannel"
			Return True ;func_resumechannel
		case "pausechannel"
			Return True ;func_pausechannel
		case "channelpitch"
			Return True ;func_channelpitch
		case "channelvolume"
			Return True ;func_channelvolume
		case "channelpan"
			Return True ;func_channelpan
		case "channelplaying"
			Return True ;func_channelplaying
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
		case "caninteract"
			Return True ;func_caninteract
		Case "callglobalfunction"
			Return True
		Case "callfunction"
			Return True
		case "ismenuopen"
			Return True
		case "isinventoryopen"
			Return True
		case "isotherinventoryopen"
			Return True
		case "lockmouse"
			Return True
		case "getoptionsmenu"
			Return True
		case "getachievementsmenu"
			Return True
		case "showpointer"
			Return True
		case "hidepointer"
			Return True
		case "iskeypadopen"
			Return True
		case "isconsoleopen"
			Return True
		case "iscoffeemachineopen"
			Return True
		case "drawuibutton": Return True
		case "lerp": Return True
		case "lerpangle": Return True
		case "ismainmenuopen": Return True
		case "shouldplayerannouncement": Return True
		case "addplayersspawnposition": Return true
		case "addclassspawnposition": Return true
		case "removespawnposition": Return true
		case "isvalidspawnposition": Return true
	End Select
	Return False
	
End Function

Function SE_GetFunctionID(functionname$)
	Select FunctionName$
		case "changeplayersteamid": Return func_changeplayersteamid
		case "addplayersspawnposition": Return func_addplayersspawnposition
		case "addclassspawnposition": Return func_addclassspawnposition
		case "removeclassspawnposition": Return func_removeclassspawnposition
		case "isvalidclassspawnposition": Return func_isvalidclassspawnposition
		
		case "getplayerspeedmult": return func_getplayerspeedmult
		case "updateobjects": Return func_updateobjects
		case "preparemodelidentifier": Return func_preparemodelidentifier
		case "setobjectinterpolation": Return func_setobjectinterpolation
		case "shouldplayerannouncement": Return func_shouldplayerannouncement
		case "getroomdoor": Return func_getroomdoor
		case "changeplayersize": Return func_changeplayersize
		case "getplayersize": Return func_getplayersize
		case "lerp": Return func_lerp
		case "lerpangle": Return func_lerpangle
		;
		case "dllfunctionvoid_0": Return func_dllfunctionvoid_0
		case "dllfunctionvoid_1": Return func_dllfunctionvoid_1
		case "dllfunctionvoid_2": Return func_dllfunctionvoid_2
		case "dllfunctionvoid_3": Return func_dllfunctionvoid_3
		case "dllfunctionvoid_4": Return func_dllfunctionvoid_4
		case "dllfunctionvoid_5": Return func_dllfunctionvoid_5
		case "dllfunctionvoid_6": Return func_dllfunctionvoid_6
		case "dllfunctionvoid_7": Return func_dllfunctionvoid_7
		case "dllfunctionvoid_8": Return func_dllfunctionvoid_8
		case "dllfunctionvoid_9": Return func_dllfunctionvoid_9
		
		case "dllfunctionint_0": Return func_dllfunctionint_0
		case "dllfunctionint_1": Return func_dllfunctionint_1
		case "dllfunctionint_2": Return func_dllfunctionint_2
		case "dllfunctionint_3": Return func_dllfunctionint_3
		case "dllfunctionint_4": Return func_dllfunctionint_4
		case "dllfunctionint_5": Return func_dllfunctionint_5
		case "dllfunctionint_6": Return func_dllfunctionint_6
		case "dllfunctionint_7": Return func_dllfunctionint_7
		case "dllfunctionint_8": Return func_dllfunctionint_8
		case "dllfunctionint_9": Return func_dllfunctionint_9
		
		case "dllfunctionfloat_0": Return func_dllfunctionfloat_0
		case "dllfunctionfloat_1": Return func_dllfunctionfloat_1
		case "dllfunctionfloat_2": Return func_dllfunctionfloat_2
		case "dllfunctionfloat_3": Return func_dllfunctionfloat_3
		case "dllfunctionfloat_4": Return func_dllfunctionfloat_4
		case "dllfunctionfloat_5": Return func_dllfunctionfloat_5
		case "dllfunctionfloat_6": Return func_dllfunctionfloat_6
		case "dllfunctionfloat_7": Return func_dllfunctionfloat_7
		case "dllfunctionfloat_8": Return func_dllfunctionfloat_8
		case "dllfunctionfloat_9": Return func_dllfunctionfloat_9
		
		case "dllfunctionstring_0": Return func_dllfunctionstring_0
		case "dllfunctionstring_1": Return func_dllfunctionstring_1
		case "dllfunctionstring_2": Return func_dllfunctionstring_2
		case "dllfunctionstring_3": Return func_dllfunctionstring_3
		case "dllfunctionstring_4": Return func_dllfunctionstring_4
		case "dllfunctionstring_5": Return func_dllfunctionstring_5
		case "dllfunctionstring_6": Return func_dllfunctionstring_6
		case "dllfunctionstring_7": Return func_dllfunctionstring_7
		case "dllfunctionstring_8": Return func_dllfunctionstring_8
		case "dllfunctionstring_9": Return func_dllfunctionstring_9
		; 
		case "connecttocentralserver": Return func_connecttocentralserver
		case "reconnecttocentralserver": Return func_reconnecttocentralserver
		case "createsteaminstance": Return func_createsteaminstance
		case "getsteaminstancetag": Return func_getsteaminstancetag
		case "removesteaminstance": Return func_removesteaminstance
		case "setdescriptionline": Return func_setdescriptionline
		case "getdescriptionline": Return func_getdescriptionline
		case "setmaxspawnplayers": Return func_setmaxspawnplayers
		case "getmaxspawnplayers": Return func_getmaxspawnplayers
		case "geteventname": Return func_geteventname
		case "geteventroomid": Return func_geteventroomid
		case "enableoldresponse": Return func_enableoldresponse
		case "closeapp": Return func_closeapp
		case "getplayermute": Return func_getplayermute
		case "setplayermute": Return func_setplayermute
		; Sqlite
		case "opendatabase": return func_sqlite_opendatabase
		case "closedatabase": return func_sqlite_closedatabase
		case "setdatabasetimeout": return func_sqlite_setdatabasetimeout
		case "databaseversion": return func_sqlite_databaseversion
		case "lastrowidinserted": return func_sqlite_lastrowidinserted
		case "rowschangedbylaststatement": return func_sqlite_rowschangedbylaststatement
		case "rowschangedthissession": return func_sqlite_rowschangedthissession
		case "autocommitison": return func_sqlite_autocommitison
		case "begintransaction": return func_sqlite_begintransaction
		case "committransaction": return func_sqlite_committransaction
		case "rollbacktransaction": return func_sqlite_rollbacktransaction
		case "lastdatabaseerrorcode": return func_sqlite_lastdatabaseerrorcode
		case "lastdatabaseerrormessage": return func_sqlite_lastdatabaseerrormessage
		case "interruptdatabase": return func_sqlite_interruptdatabase
		case "executesql": return func_sqlite_executesql
		case "preparesql": return func_sqlite_preparesql
		case "getnextdatarow": return func_sqlite_getnextdatarow
		case "finalisesql": return func_sqlite_finalisesql
		case "resetsql": return func_sqlite_resetsql
		case "sqlhasexpired": return func_sqlite_sqlhasexpired
		case "getdatabasehandlefromstatementhandle": return func_sqlite_getdatabasehandlefromstatementhandle
		case "getcolumncount": return func_sqlite_getcolumncount
		case "getcolumnname": return func_sqlite_getcolumnname
		case "getcolumntype": return func_sqlite_getcolumntype
		case "getcolumndeclaredtype": return func_sqlite_getcolumndeclaredtype
		case "getcolumnsize": return func_sqlite_getcolumnsize
		case "getcolumnvalueasinteger": return func_sqlite_getcolumnvalueasinteger
		case "getcolumnvalueasfloat": return func_sqlite_getcolumnvalueasfloat
		case "getcolumnvalueasstring": return func_sqlite_getcolumnvalueasstring
		case "getcolumnvalueasblob": return func_sqlite_getcolumnvalueasblob
		case "sqlparametercount": return func_sqlite_sqlparametercount
		case "sqlparametername": return func_sqlite_sqlparametername
		case "sqlparameterindex": return func_sqlite_sqlparameterindex
		case "bindsqlparameterasnull": return func_sqlite_bindsqlparameterasnull
		case "bindsqlparameterasinteger": return func_sqlite_bindsqlparameterasinteger
		case "bindsqlparameterasfloat": return func_sqlite_bindsqlparameterasfloat
		case "bindsqlparameterasstring": return func_sqlite_bindsqlparameterasstring
		case "bindsqlparameterasblob": return func_sqlite_bindsqlparameterasblob
		case "transfersqlbindings": return func_sqlite_transfersqlbindings
		case "sqlite3_errorhasoccurred": return func_sqlite_sqlite3_errorhasoccurred
		; ==========================================================================
		
		; mysql
		case "opensqlstream": return func_mysql_opensqlstream
		case "sqlconnected": return func_mysql_sqlconnected
		case "sqlquery": return func_mysql_sqlquery
		case "sqlrowcount": return func_mysql_sqlrowcount
		case "sqlfetchrow": return func_mysql_sqlfetchrow
		case "sqlfieldcount": return func_mysql_sqlfieldcount
		case "readsqlfield": return func_mysql_readsqlfield
		case "readsqlfieldindex": return func_mysql_readsqlfieldindex
		case "freesqlquery": return func_mysql_freesqlquery
		case "freesqlrow": return func_mysql_freesqlrow
		case "closesqlstream": return func_mysql_closesqlstream
		; ==========================================================================
		case "getserverexplodetimeout"
			return func_getserverexplodetimeout
		case "getserverspawntimeout"
			return func_getserverspawntimeout
		case "setserverspawntimeout"
			return func_setserverspawntimeout
		case "enableauthkeyonconnect"
			return func_enableauthkeyonconnect
		case "setplayervelocity"
			Return func_setplayervelocity
		case "getplayervelocity"
			Return func_getplayervelocity
		case "setplayerhandcuff"
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
		case "spawnmtf"
			Return func_spawnmtf
		case "activatewarheads"
			Return func_activatewarheads
		case "getplayerzone"
			Return func_getplayerzone
		case "getbreachtimer"
			Return func_getbreachtimer
		case "addaccessversion"
			Return func_addaccessversion
		Case "getplayerinjuries"
			Return func_getplayerinjuries
		Case "setplayerinjuries"
			Return func_setplayerinjuries
		case "getplayerheadpitch"
			Return func_getplayerheadpitch
		case "setplayerheadpitch"
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
		case "getunixtime"
			Return func_getunixtime
		case "updateinifile"
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
		case "getincomingconnectionversion"
			Return func_getincomingconnectionversion
		case "getincomingconnectionpatron"
			Return func_getincomingconnectionpatron
		case "setmtftickets"
			Return func_setmtftickets
		case "setchaostickets"
			Return func_setchaostickets
		case "getmtftickets"
			Return func_getmtftickets
		case "getchaostickets"
			Return func_getchaostickets
		case "activatefilessending"
			Return func_activatefilessending
		case "setserverexplodetimeout"
			Return func_setserverexplodetimeout
		case "isvalidroomdoor"
			Return func_isvalidroomdoor
	End Select
End Function

; ******* my
	Global pointyaw#, pointpitch#, movex#, movey#, movez#
	Global CurrentLuaState%
; *******

Function SE_ExecuteFunction(LuaState%, FunctionID)
	G_SE_ReturnVal$
	G_SE_ReturnType% = SE_NULL
	
	CurrentParam = 0
	CurrentLuaState = LuaState
	
	Select FunctionID
		case func_changeplayersteamid
			ChangePlayerSteamID(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		case func_addplayerspawnposition
			spawnpointer.SpawnPoint = New SpawnPoint
			spawnpointer\playerid = SE_AND_LUA_ToIntArg(0)
			spawnpointer\room = SE_AND_LUA_ToStringArg(1)
			spawnpointer\x = SE_AND_LUA_ToFloatArg(2)
			spawnpointer\y = SE_AND_LUA_ToFloatArg(3)
			spawnpointer\z = SE_AND_LUA_ToFloatArg(4)
			spawnpointer\role = -1
			
			G_SE_ReturnVal = Handle(spawnpointer)
			G_SE_ReturnType = SE_INT
			
		case func_addclassspawnposition
			spawnpointer.SpawnPoint = New SpawnPoint
			spawnpointer\role = SE_AND_LUA_ToIntArg(0)
			spawnpointer\room = SE_AND_LUA_ToStringArg(1)
			spawnpointer\x = SE_AND_LUA_ToFloatArg(2)
			spawnpointer\y = SE_AND_LUA_ToFloatArg(3)
			spawnpointer\z = SE_AND_LUA_ToFloatArg(4)
			spawnpointer\playerid = -1
			
			G_SE_ReturnVal = Handle(spawnpointer)
			G_SE_ReturnType = SE_INT
		case func_removespawnposition
			Delete Object.SpawnPoint(SE_AND_LUA_ToIntArg(0))
		case func_isvalidspawnposition
			G_SE_ReturnVal = (Object.SpawnPoint(SE_AND_LUA_ToIntArg(0)) <> Null)
			G_SE_ReturnType = SE_INT
		case func_ShouldPlayerAnnouncement
			Player[SE_AND_LUA_ToIntArg(0)]\ShouldAnnouncement = SE_AND_LUA_ToIntArg(1)
		case func_changeplayersize
			ChangePlayerSize(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1))
		case func_getplayersize
			G_SE_ReturnVal = Player[SE_AND_LUA_ToIntArg(0)]\Size
			G_SE_ReturnType = SE_FLOAT
		case func_lerp
			G_SE_ReturnVal = curvevalue(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
			G_SE_ReturnType = SE_FLOAT
		case func_lerpangle
			G_SE_ReturnVal = curveangle(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1), SE_AND_LUA_ToFloatArg(2))
			G_SE_ReturnType = SE_FLOAT
		case func_dllfunctionvoid_0
			DllFunctionVoid_0()
		case func_dllfunctionvoid_1
			DllFunctionVoid_1(SE_AND_LUA_ToStringArg(0))
		case func_dllfunctionvoid_2
			DllFunctionVoid_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
		case func_dllfunctionvoid_3
			DllFunctionVoid_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
		case func_dllfunctionvoid_4
			DllFunctionVoid_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
		case func_dllfunctionvoid_5
			DllFunctionVoid_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
		case func_dllfunctionvoid_6
			DllFunctionVoid_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
		case func_dllfunctionvoid_7
			DllFunctionVoid_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
		case func_dllfunctionvoid_8
			DllFunctionVoid_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
		case func_dllfunctionvoid_9
			DllFunctionVoid_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			
		case func_dllfunctionint_0
			G_SE_ReturnVal = DllFunctionint_0()
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_1
			G_SE_ReturnVal = DllFunctionint_1(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_2
			G_SE_ReturnVal = DllFunctionint_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_3
			G_SE_ReturnVal = DllFunctionint_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_4
			G_SE_ReturnVal = DllFunctionint_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_5
			G_SE_ReturnVal = DllFunctionint_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_6
			G_SE_ReturnVal = DllFunctionint_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_7
			G_SE_ReturnVal = DllFunctionint_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_8
			G_SE_ReturnVal = DllFunctionint_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_INT
		case func_dllfunctionint_9
			G_SE_ReturnVal = DllFunctionint_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			G_SE_ReturnType = SE_INT
			
		case func_dllfunctionfloat_0
			G_SE_ReturnVal = DllFunctionfloat_0()
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_1
			G_SE_ReturnVal = DllFunctionfloat_1(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_2
			G_SE_ReturnVal = DllFunctionfloat_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_3
			G_SE_ReturnVal = DllFunctionfloat_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_4
			G_SE_ReturnVal = DllFunctionfloat_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_5
			G_SE_ReturnVal = DllFunctionfloat_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_6
			G_SE_ReturnVal = DllFunctionfloat_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_7
			G_SE_ReturnVal = DllFunctionfloat_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_8
			G_SE_ReturnVal = DllFunctionfloat_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_float
		case func_dllfunctionfloat_9
			G_SE_ReturnVal = DllFunctionfloat_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			G_SE_ReturnType = SE_float
			
		case func_dllfunctionstring_0
			G_SE_ReturnVal = DllFunctionstring_0()
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_1
			G_SE_ReturnVal = DllFunctionstring_1(SE_AND_LUA_ToStringArg(0))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_2
			G_SE_ReturnVal = DllFunctionstring_2(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_3
			G_SE_ReturnVal = DllFunctionstring_3(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_4
			G_SE_ReturnVal = DllFunctionstring_4(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_5
			G_SE_ReturnVal = DllFunctionstring_5(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_6
			G_SE_ReturnVal = DllFunctionstring_6(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_7
			G_SE_ReturnVal = DllFunctionstring_7(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_8
			G_SE_ReturnVal = DllFunctionstring_8(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7))
			G_SE_ReturnType = SE_string
		case func_dllfunctionstring_9
			G_SE_ReturnVal = DllFunctionstring_9(SE_AND_LUA_ToStringArg(0),SE_AND_LUA_ToStringArg(1),SE_AND_LUA_ToStringArg(2),SE_AND_LUA_ToStringArg(3),SE_AND_LUA_ToStringArg(4),SE_AND_LUA_ToStringArg(5),SE_AND_LUA_ToStringArg(6),SE_AND_LUA_ToStringArg(7),SE_AND_LUA_ToStringArg(8))
			G_SE_ReturnType = SE_string
		
			
		case func_connecttocentralserver
			ip$ = SE_GetParamString()
			port% = SE_GetParamInt()
			ConnectToCentralServer(IP, Port)
		case func_reconnecttocentralserver
			ConnectToCentralServer(CENTRAL_SERVER_IP, CENTRAL_SERVER_PORT)
		case func_createsteaminstance
			steamid% = SE_GetParamInt()
			tag$ = SE_GetParamString()
			cr% = SE_GetParamInt()
			cg% = SE_GetParamInt()
			cb% = SE_GetParamInt()
			CreateSteamInstance(steamid, tag, cr, cg, cb)
		case func_removesteaminstance
			steamid% = SE_GetParamInt()
			For SI.SteamInstances = Each SteamInstances
				if SI\steamid = steamid then delete si
			Next
		case func_getsteaminstancetag
			G_SE_ReturnVal$ = ""
			G_SE_ReturnType = SE_STRING
			steamid% = SE_GetParamInt()
			For SI.SteamInstances = Each SteamInstances
				if SI\steamid = steamid then 
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
		case func_closeapp
			End
		; mysql
		
		case func_mysql_OpenSQLStream
			hostName$ = SE_GetParamString()
			port% = SE_GetParamInt()
			userName$ = SE_GetParamString()
			password$ = SE_GetParamString()
			dataBase$ = SE_GetParamString()
			flag% = SE_GetParamInt()
			G_SE_ReturnVal = OpenSQLStream(hostName$, port%, userName$, passWord$, dataBase$, flag%)
			G_SE_ReturnType = SE_INT
			
		case func_mysql_SQLConnected
			G_SE_ReturnVal = SQLConnected(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		case func_mysql_SQLQuery
			streamid% = SE_GetParamInt()
			query$ = SE_GetParamString()
			
			
			G_SE_ReturnVal = SQLQuery(streamid, query)
			G_SE_ReturnType = SE_INT
			;debuglog "Query: "+query+" result: "+G_SE_ReturnVal
			
		case func_mysql_SQLRowCount
			G_SE_ReturnVal = SQLRowCount(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		case func_mysql_SQLFetchRow
			G_SE_ReturnVal = SQLFetchRow(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		case func_mysql_SQLFieldCount
			G_SE_ReturnVal = SQLFieldCount(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		
		case func_mysql_ReadSQLField
			rowid% = SE_GetParamInt()
			fieldname$ = se_getparamstring()
			G_SE_ReturnVal = ReadSQLField(rowid, fieldname)
			G_SE_ReturnType = SE_STRING
			
		case func_mysql_ReadSQLFieldIndex
			rowid% = SE_GetParamInt()
			fieldindex% = SE_GetParamInt()
			G_SE_ReturnVal = ReadSQLFieldIndex(rowid, fieldindex)
			G_SE_ReturnType = SE_STRING
			
		case func_mysql_FreeSQLQuery
			G_SE_ReturnVal = FreeSQLQuery(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		case func_mysql_FreeSQLRow
			G_SE_ReturnVal = FreeSQLRow(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		case func_mysql_CloseSQLStream
			G_SE_ReturnVal = CloseSQLStream(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
			
		; Sqlite
		case func_sqlite_OpenDatabase
			filename$ = SE_GetParamString()
			openmode% = SE_GetParamInt()
			MakeCurrent% = SE_GetParamInt()
			ErrorsAreFatal% = SE_GetParamInt()
			G_SE_ReturnVal = OpenDatabase(filename, openmode, makecurrent, errorsarefatal)
			G_SE_ReturnType = SE_INT

		case func_sqlite_CloseDatabase
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal% = SE_GetParamInt()
			G_SE_ReturnVal = CloseDatabase(databasehandle, errorsarefatal)
			G_SE_ReturnType = SE_INT

		case func_sqlite_SetDatabaseTimeout
			TimeOutInMilliSecs = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal% = SE_GetParamInt()
			G_SE_ReturnVal = SetDatabaseTimeout(TimeOutInMilliSecs, databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT

		case func_sqlite_DatabaseVersion
			G_SE_ReturnVal = DatabaseVersion()
			G_SE_ReturnType = SE_STRING

		case func_sqlite_LastRowIDInserted
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = LastRowIDInserted(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_RowsChangedByLastStatement
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = RowsChangedByLastStatement(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_RowsChangedThisSession
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = RowsChangedThisSession(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_AutoCommitIsOn
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = AutoCommitIsOn(databasehandle)
			G_SE_ReturnType = SE_INT

		case func_sqlite_BeginTransaction
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = BeginTransaction(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_CommitTransaction
			KeepTransactionOpen = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = CommitTransaction(KeepTransactionOpen, databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_RollbackTransaction
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = RollbackTransaction(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT

		case func_sqlite_LastDatabaseErrorCode
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = LastDatabaseErrorCode(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_LastDatabaseErrorMessage
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = LastDatabaseErrorMessage(databasehandle)
			G_SE_ReturnType = SE_STRING

		case func_sqlite_InterruptDatabase
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = InterruptDatabase(databasehandle)
			G_SE_ReturnType = SE_INT

		case func_sqlite_ExecuteSQL
			SQL$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = ExecuteSQL(SQL, databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_PrepareSQL
			SQL$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			MakeCurrent = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = PrepareSQL(SQL, databasehandle, MakeCurrent, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT

		case func_sqlite_GetNextDataRow
			databasehandle = SE_GetParamInt()
			AutomaticallyFinalise = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = GetNextDataRow(databasehandle, AutomaticallyFinalise, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_FinaliseSQL
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = FinaliseSQL(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_ResetSQL
			databasehandle = SE_GetParamInt()
			ErrorsAreFatal = SE_GetParamInt()
			G_SE_ReturnVal = ResetSQL(databasehandle, ErrorsAreFatal)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_SQLHasExpired
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLHasExpired(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_GetDatabaseHandleFromStatementHandle
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetDatabaseHandleFromStatementHandle(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_GetColumnCount
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnCount(databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_GetColumnName
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnName(columnindex, databasehandle)
			G_SE_ReturnType = SE_STRING
			
		case func_sqlite_GetColumnType
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnType(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_GetColumnDeclaredType
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnDeclaredType(columnindex, databasehandle)
			G_SE_ReturnType = SE_STRING
			
		case func_sqlite_GetColumnSize
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnSize(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_GetColumnValueAsInteger
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsInteger(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_GetColumnValueAsFloat
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsFloat(columnindex, databasehandle)
			G_SE_ReturnType = SE_FLOAT
			
		case func_sqlite_GetColumnValueAsString
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsString(columnindex, databasehandle)
			G_SE_ReturnType = SE_STRING
			
		case func_sqlite_GetColumnValueAsBlob
			columnindex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = GetColumnValueAsBlob(columnindex, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_SQLParameterCount
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLParameterCount(databasehandle)
			G_SE_ReturnType = SE_INT

		case func_sqlite_SQLParameterName
			ParameterIndex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLParameterName(ParameterIndex, databasehandle)
			G_SE_ReturnType = SE_STRING

		case func_sqlite_SQLParameterIndex
			ParameterName$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = SQLParameterIndex(ParameterName, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_BindSQLParameterAsNull
			ParameterIndex = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsNull(ParameterIndex, databasehandle)
			G_SE_ReturnType = SE_INT

		case func_sqlite_BindSQLParameterAsInteger
			ParameterIndex = SE_GetParamInt()
			sqlvalue$ = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsInteger(ParameterIndex, sqlvalue, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_BindSQLParameterAsFloat
			ParameterIndex = SE_GetParamInt()
			sqlvalue$ = SE_GetParamFloat()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsFloat(ParameterIndex, sqlvalue, databasehandle)
			G_SE_ReturnType = SE_INT

		case func_sqlite_BindSQLParameterAsString
			ParameterIndex = SE_GetParamInt()
			sqlvalue$ = SE_GetParamString()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsString(ParameterIndex, sqlvalue, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_BindSQLParameterAsBlob
			ParameterIndex = SE_GetParamInt()
			BlobHandle = SE_GetParamInt()
			LengthOfBlob = SE_GetParamInt()
			databasehandle = SE_GetParamInt()
			G_SE_ReturnVal = BindSQLParameterAsBlob(ParameterIndex, BlobHandle, LengthOfBlob, databasehandle)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_TransferSQLBindings
			databasehandle = SE_GetParamInt()
			handle2 = SE_GetParamInt()
			G_SE_ReturnVal = TransferSQLBindings(databasehandle, handle2)
			G_SE_ReturnType = SE_INT
			
		case func_sqlite_SQLite3_ErrorHasOccurred
			declarename$ = SE_GetParamString()
			ErrorsAreFatal% = SE_GetParamInt()
			sqlMessage$ = SE_GetParamString()
			G_SE_ReturnVal = SQLite3_ErrorHasOccurred(declarename, ErrorsAreFatal, sqlMessage)
			G_SE_ReturnType = SE_INT
		; ======================================================================================
		
		Case func_setplayervelocity
			playerid = SE_GetParamInt()
			Player[playerid]\CurrSpeed = SE_GetParamFloat()
		Case func_getplayervelocity
			G_SE_ReturnVal = Player[playerid]\CurrSpeed
			G_SE_ReturnType = SE_FLOAT
		Case func_setplayerhandcuff
			playerid = SE_GetParamInt()
			Player[playerid]\handcuffed = SE_GetParamInt()
			
			if Player[playerid]\handcuffed then
				for it.items = each items
					if it\picker = playerid then
						PlayerDropItem(it)
					endif
				next
			endif
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
			G_SE_ReturnVal = ZipApi_Compress(SE_GetParamInt(), 1)
			G_SE_ReturnType = SE_INT
		Case func_zlib_uncompress
			bank = SE_GetParamInt()
			fullsize = SE_GetParamInt()
			G_SE_ReturnVal = ZipApi_Uncompress(bank, fullsize)
			G_SE_ReturnType = SE_INT
		Case func_deactivatewarheads
			deactivatewarheads()
		Case func_spawnmtf
			spawnmtf()
		Case func_activatewarheads
			activatewarheads("NULL")
		Case func_getplayerzone
			playerid = SE_GetParamInt()
			if IsValidPlayer(playerid) then
				G_SE_ReturnVal = GetPlayerZone(playerid)
				G_SE_ReturnType = SE_INT
			endif
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
			CameraViewPort G_SE_ReturnVal, 0, 0, 1920, 1080
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
			G_SE_ReturnVal = EntityX(SE_GetParamInt(),true)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityy
			G_SE_ReturnVal = EntityY(SE_GetParamInt(),true)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityz
			G_SE_ReturnVal = EntityZ(SE_GetParamInt(),true)
			G_SE_ReturnType = SE_FLOAT
		Case func_entitypitch
			G_SE_ReturnVal = entitypitch(SE_GetParamInt(),true)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityyaw
			G_SE_ReturnVal = entityyaw(SE_GetParamInt(),true)
			G_SE_ReturnType = SE_FLOAT
		Case func_entityroll
			G_SE_ReturnVal = entityroll(SE_GetParamInt(),true)
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
			PositionEntity ent, x,y,z,True
			ResetEntity(ent)
		Case func_rotateentity
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			z# = SE_GetParamFloat()
			rotateentity ent, x,y,z,True
		Case func_moveentity
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			z# = SE_GetParamFloat()
			moveentity ent, x,y,z
		Case func_scaleentity
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			scaleentity ent, x,x,x
		Case func_setentitycollision
			ent = SE_GetParamInt()
			EntityType ent, HIT_PLAYER*SE_GetParamInt()
		Case func_removeentity
			FreeEntity SE_GetParamInt()
		Case func_setanimtime
			ent = SE_GetParamInt()
			x# = SE_GetParamFloat()
			setanimtime ent, x
			
		Case func_getdelta
			G_SE_ReturnVal = FPSFactor
			G_SE_ReturnType = SE_FLOAT
		Case func_sendrawpacket
			playerid = SE_GetParamInt()
			bank = SE_GetParamInt()
			if Player[playerid] <> Null Then
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
			SendFile(playerid, filename, savepath)
		Case func_setgamestate
			Server\GameState = SE_GetParamString()
			AddLog("Game state changed to "+Server\GameState, 0, false)
		Case func_setservername
			Server\Servername = SE_GetParamString()
			AddLog("Server name changed to "+Server\Servername, 0, false)
		Case func_setserverdescription
			Server\Description = SE_GetParamString()
			AddLog("Description changed to "+Server\description, 0, false)
		Case func_setserverpassword
			RCON_Password(SE_GetParamString())
			AddLog("Password changed to "+Server\password, 0, false)
		Case func_setservercurrentbreachtimer
			gameinfo\b\BreachTimer = MilliSecs()+SE_GetParamInt()
			AddLog("Breach timer changed to "+Str(gameinfo\b\BreachTimer-MilliSecs()), 0, false)
		case func_setserverexplodetimeout
			gameinfo\b\ExplodeTimeout = MilliSecs()+SE_GetParamInt()
		case func_getserverexplodetimeout
			G_SE_ReturnVal = gameinfo\b\ExplodeTimeout-MilliSecs()
			G_SE_ReturnType = SE_INT
		case func_getserverspawntimeout
			G_SE_ReturnVal = gameinfo\b\NTFTimeout-MilliSecs()
			G_SE_ReturnType = SE_INT
		case func_setserverspawntimeout
			gameinfo\b\NTFTimeout = MilliSecs()+SE_GetParamInt()
		Case func_setservernocheat
			Server\Nocheat = SE_GetParamInt()
			AddLog("No cheat changed to "+Server\nocheat, 0, false)
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
			
			if CurrentLuaState = 0 Then
				for i = 3 To SE_ARGUMENTS_NUMBER-1
					if SE_ArgType(i) <> SE_NULL Then
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
				next
				
				G_SE_ReturnVal = SetTimer(SE_CURRENT_FUNCTION\Script, timername, interval, loop, identifiers, b)
				G_SE_ReturnType = SE_INT
			Else
				for i = 4 To 32
					if not SLUA_IS_NONE_OR_NIL(CurrentLuaState, i) Then
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
					else
						exit
					end
				next
				G_SE_ReturnVal = SetTimer(null, timername, interval, loop, identifiers, b, CurrentLuaState)
				G_SE_ReturnType = SE_INT
			EndIf
			
		Case func_serverwritebyte
			ByteStreamWriteChar(scriptbstream,SE_GetParamInt())
		Case func_serverwriteshort
			ByteStreamWriteShort(scriptbstream,SE_GetParamInt())
		Case func_serverwriteint
			ByteStreamWriteInt(scriptbstream,SE_GetParamInt())
		Case func_serverwritefloat
			ByteStreamWriteFloat(scriptbstream,SE_GetParamFloat())
		Case func_debug
			debuglog SE_GetParamString()
		Case func_eof
			G_SE_ReturnVal = Eof(SE_GetParamString())
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
			filehandle = SE_GetParamint()
			seek = SE_GetParamint()
			SeekFile(filehandle, seek)
		Case func_filepos
			G_SE_ReturnVal = FilePos(SE_GetParamint())
			G_SE_ReturnType = SE_INT
		Case func_catcherror
			G_SE_ReturnVal = ErrorLog()
			G_SE_ReturnType = SE_STRING
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
			
		Case func_disablelobby
			Server\LobbyDisabled = Not Server\LobbyDisabled
			Server\IsStarted = Server\LobbyDisabled
		;Case func_removevar
		;	var_remove(SE_GetParamString())
		;Case func_setvarvalue
		;	SE_FlipParams(2)
		;	smsg$ = SE_GetParamString()
		;	smsg2$ = SE_GetParamString()
		;	var_setvalue(smsg, smsg2)
		;Case func_getvarvalue
		;	G_SE_ReturnVal = var_getvalue(SE_GetParamString())
		;	G_SE_ReturnType = SE_STRING
		;Case func_removeplayervar
		;	SE_FlipParams(2)
		;	playerid = SE_GetParamInt()
		;	smsg$ = SE_GetParamString()
		;	player_var_remove(playerid, smsg)
		;Case func_setplayervarvalue
		;	SE_FlipParams(3)
		;	smsg2$ = SE_GetFlippedParamString()
		;	smsg$ = SE_GetFlippedParamString()
		;	playerid = SE_GetFlippedParamString()
		;	player_var_setvalue(playerid, smsg, smsg2)
		;Case func_getplayervarvalue
		;	SE_FlipParams(2)
		;	smsg$ = SE_GetFlippedParamString()
		;	playerid = SE_GetFlippedParamString()
		;	G_SE_ReturnVal = player_var_getvalue(playerid, smsg)
		;	G_SE_ReturnType = SE_STRING
		Case func_addarrayelements
			SE_Array_AddElements(SE_ArrayArg(0), SE_IntArg(1), SE_IntArg(2))
		case func_updateobjects
			objects_stream_update()
		Case func_getobjectentity
			G_SE_ReturnVal = multiplayer_Object[SE_GetParamInt()]\ent
			G_SE_ReturnType = SE_INT
		Case func_setobjectvisibleforplayer
			pointer = SE_GetParamInt()
			playerid = SE_GetParamInt()
			bool = SE_GetParamInt()
			object_update_visible(pointer, playerid, bool)
		case func_preparemodelidentifier
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
			Player[playerid]\CurrentRadio = SE_GetParamInt()-1
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
				if Lower(script\scriptname) = Lower(SE_AND_LUA_ToStringArg(0)) Then
					if script\luathread = 0 then
						for i = 2 To SE_ARGUMENTS_NUMBER
							if SE_ArgType(i) <> SE_NULL Then public_addparam(0, SE_AND_LUA_ToStringArg(i), SE_ArgType(i))
						next
						public_update_by_func(SE_FindFunc(script\scriptthread, Lower(SE_AND_LUA_ToStringArg(1))), True)
						public_clear
						G_SE_ReturnVal = SE_GetReturnValue()
						G_SE_ReturnType = SE_RETURN_VALUE\ValueType
					Else
					
						for i = 3 To 32
							if not SLUA_IS_NONE_OR_NIL(script\luathread, i) Then
								public_addparam(0, SLUA_TO_STRING(script\luathread, i), SLUA_TYPE(script\luathread, i))
							else
								exit
							endif
						next
						
						public_update_by_func(null, False, script\luathread, Lower(SE_AND_LUA_ToStringArg(1)))
						public_clear()
						
						G_SE_ReturnVal = SE_GetReturnValue()
						G_SE_ReturnType = SE_RETURN_VALUE\ValueType
						
					EndIf
					Exit
				EndIf
			Next
		Case func_callfunction
			if CurrentLuaState = 0 then
				for i = 1 To SE_ARGUMENTS_NUMBER
					if SE_ArgType(i) <> SE_NULL Then public_addparam(0, SE_AND_LUA_ToStringArg(i), SE_ArgType(i))
				next
				public_update_by_func(SE_FindFunc(SE_CURRENT_FUNCTION\Script, Lower(SE_AND_LUA_ToStringArg(0))), True)
				public_clear
				G_SE_ReturnVal = SE_GetReturnValue()
				G_SE_ReturnType = SE_RETURN_VALUE\ValueType
			Else
				for i = 1 To 32
					if not SLUA_IS_NONE_OR_NIL(CurrentLuaState, i) Then
						public_addparam(0, SLUA_TO_STRING(CurrentLuaState, i), SLUA_TYPE(CurrentLuaState, i))
					else
						exit
					endif
				next
				
				public_update_by_func(null, False, script\luathread, Lower(SE_AND_LUA_ToStringArg(1)))
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
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			z# = SE_GetParamFloat()
			xat# = SE_GetParamFloat()
			yat# = SE_GetParamFloat()
			zat# = SE_GetParamFloat()
			angle# = SE_GetParamFloat()
			pitch# = SE_GetParamFloat()
			pv = CreatePivot()
			PositionEntity pv, x, y, z
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
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			z# = SE_GetParamFloat()
			xat# = SE_GetParamFloat()
			yat# = SE_GetParamFloat()
			zat# = SE_GetParamFloat()
			pv = CreatePivot()
			pv2 = CreatePivot()
			PositionEntity pv, x, y, z
			PositionEntity pv2, xat, yat, zat
			PointEntity pv, pv2
			pointyaw# = EntityYaw(pv)
			pointpitch# = EntityPitch(pv)
			FreeEntity pv
			FreeEntity pv2
		Case func_getplayertype
			G_SE_ReturnVal = Player[SE_GetParamInt()]\BreachType
			G_SE_ReturnType = SE_INT
		Case func_createsound
			filename$ = SE_GetParamString()
			xrocket# = SE_GetParamFloat()
			yRocket# = SE_GetParamFloat()
			zRocket# = SE_GetParamFloat()
			pitchRocket# = SE_GetParamFloat()
			yawRocket# = SE_GetParamFloat()
			CreateSound(filename, xrocket, yrocket, zrocket, pitchrocket, yawrocket)
		Case func_createplayersound
			playerid = SE_GetParamInt()
			filename$ = SE_GetParamString()
			xrocket# = SE_GetParamFloat()
			yRocket# = SE_GetParamFloat()
			zRocket# = SE_GetParamFloat()
			pitchRocket# = SE_GetParamFloat()
			yawRocket# = SE_GetParamFloat()
			CreatePlayerSound(playerid, filename, xrocket, yrocket, zrocket, pitchrocket, yawrocket)
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
		case func_isvalidroomdoor
			G_SE_ReturnVal = 0
			G_SE_ReturnType = SE_INT
			if SE_AND_LUA_ToIntArg(1) >= 0 and SE_AND_LUA_ToIntArg(1) <= 7 then
				G_SE_ReturnVal = (Room[SE_AND_LUA_ToIntArg(0)]\RoomDoors[SE_AND_LUA_ToIntArg(1)]<>Null)
			EndIf
		case func_getroomdoor
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
		Case func_isvaliddoor
			G_SE_ReturnVal = (MP_Door[SE_GetParamInt()]<>Null)
			G_SE_ReturnType = SE_INT
			; ==================== Items
		Case func_setitempicker
			playerid = SE_GetParamInt()
			itemid = SE_GetParamInt()
			for it.items = each items
				if it\ID = itemid then
					it\picker = playerid
					if it\picker < 1 then it\picked = true
					exit
				endif
			next
		Case func_getitemtemplatename
			Local ittemp = SE_GetParamInt()
			For itt.ItemTemplates = Each ItemTemplates
			if itt\templateid = ittemp Then
			G_SE_ReturnVal = itt\name
			G_SE_ReturnType = SE_STRING
			Exit
			EndIf
			Next
		Case func_getitemtemplatetempname
			ittemp = SE_GetParamInt()
			For itt.ItemTemplates = Each ItemTemplates
				if itt\templateid = ittemp Then
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
				If Lower(itt\name) = lower(itname) And Lower(itt\tempname) = lower(ittempname) Then
					it.Items = CreateItem(itt\name, itt\tempname, 9999, 9999, 9999)
					Exit
				EndIf
			Next
			if it <> Null Then
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
			if n <> Null Then
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
			if M_Event[eID]\room <> Null Then G_SE_ReturnVal = M_Event[eID]\room\ID
			G_SE_ReturnType = SE_INT
		Case func_geteventindex
			Local evn$ = SE_GetParamSTring()
			For e.Events = Each events
				if e\EventName = evn Then
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
			msg = SE_GetParamString()
			Local cmd$ = RCON_findcmd(msg)
			if cmd = "Not found" Then Return AddTextToChat("[RCON] Command not found", ID)
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
			xrocket# = SE_GetParamFloat()
			yRocket# = SE_GetParamFloat()
			zRocket# = SE_GetParamFloat()
			pitchRocket# = SE_GetParamFloat()
			yawRocket# = SE_GetParamFloat()
			CreateRocket(ROCKET_SPEED, xRocket, yRocket, zRocket, pitchRocket,yawRocket, 0)
			For i = 1 To Server\Maxplayers
				if Player[i] <> Null Then
					udp_WriteByte M_CREATEROCKET
					udp_WriteByte 1
					udp_WriteShort 0
					udp_WriteFloat xrocket
					udp_WriteFloat yrocket
					udp_WriteFloat zrocket
					udp_WriteFloat pitchRocket
					udp_WriteFloat yawRocket
					udp_SendMessage(i)
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
			WriteLine SE_ToIntArg(0), SE_ToStringArg(1)
		Case func_writeint
			WriteInt SE_ToIntArg(0), SE_ToIntArg(1)
		Case func_writefloat
			WriteFloat SE_ToIntArg(0), SE_ToFloatArg(1)
		Case func_writeshort
			WriteShort SE_ToIntArg(0), SE_ToIntArg(1)
		Case func_writebyte
			WriteByte SE_ToIntArg(0), SE_ToIntArg(1)
		Case func_openfile
			G_SE_ReturnVal = OpenFile(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_readfile
			G_SE_ReturnVal = ReadFile(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_writefile
			G_SE_ReturnVal = WriteFile(SE_GetParamString())
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
			if afk < 0 And afk > 0 then return
			Player[playerid]\p_byte = (ReadBool(Player[playerid]\p_byte, 0) + (2 * ReadBool(Player[playerid]\p_byte, 1)) + (4 * ReadBool(Player[playerid]\p_byte, 2)) + (8 * afk) + (16 * ReadBool(Player[playerid]\p_byte, 4)) + (32 * ReadBool(Player[playerid]\p_byte, 5)))
		Case func_getplayerafk
			G_SE_ReturnVal = ReadBool(Player[SE_GetParamInt()]\p_byte, 3)
			G_SE_ReturnType = SE_INT
		Case func_setplayertextpos
			playerid = SE_GetParamINT()
			pointer = SE_GetParamINT()
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			text_setpos(playerid, pointer, x, y)
		Case func_setplayertextstring
			playerid = SE_GetParamInt()
			pointer = SE_GetParamInt()
			smsg$ = SE_GetParamString()
			text_settext(playerid, pointer, smsg)
		Case func_setplayertextcolor
			playerid = SE_GetParamInt()
			pointer = SE_GetParamInt()
			intcolor = SE_GetParamString()
			text_setcolor(playerid, pointer, intcolor)
		Case func_setplayerdrawcolor
			playerid = SE_GetParamInt()
			pointer = SE_GetParamInt()
			intcolor = SE_GetParamString()
			draw_setcolor(playerid, pointer, intcolor)
		Case func_setplayerdrawpos
			playerid = SE_GetParamInt()
			pointer = SE_GetParamInt()
			x# = SE_GetParamFloat()
			y# = SE_GetParamFloat()
			draw_setpos(playerid, pointer, x, y)
		Case func_millisecs
			G_SE_ReturnVal = MilliSecs()
			G_SE_ReturnType = SE_INT
		Case func_isplayerconnected
			playerid = SE_GetParamString()
			
			G_SE_ReturnVal = 0
			if playerid > 0 and playerid < MAX_PLAYERS then G_SE_ReturnVal = (Player[playerid]<>Null)
			
			G_SE_ReturnType = SE_INT
		Case func_setplayerfogrange
			SetPlayerFogRange(SE_ToIntArg(0), SE_ToFloatArg(1))
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
			text_remove(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_createplayertext
			playerid = SE_GetParamInt()
			txt$ = SE_GetParamString()
			x = SE_GetParamInt()
			y = SE_GetParamInt()
			intcolor = SE_GetParamInt()
			fntname$ = SE_GetParamString()
			fntsize# = SE_GetParamFloat()
			G_SE_ReturnVal = text_create(playerid, txt, x, y, intcolor, fntname, fntsize)
			G_SE_ReturnType = SE_INT
		Case func_removeplayerdraw
			draw_remove(SE_ToIntArg(0), SE_ToIntArg(1))
		Case func_createplayerdraw
			playerid = SE_GetParamInt()
			x = SE_GetParamInt()
			y = SE_GetParamInt()
			width = SE_GetParamInt()
			height = SE_GetParamInt()
			TYPE_DRAW = SE_GetParamInt()
			intcolor = SE_GetParamInt()
			filename = SE_GetParamString()
			G_SE_ReturnVal = draw_create(playerid, x, y, width, height, type_draw, intcolor, filename)
			G_SE_ReturnType = SE_INT
		Case func_setplayertype
			playerid = SE_GetParamInt()
			brtype = SE_GetParamInt()
			SetPlayerType(playerid, brtype)
		Case func_getplayerip
			playerid = SE_GetParamString()
			G_SE_ReturnVal = Player[playerid]\PlayerIP
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
			Shoot2(SE_ToFloatArg(0), SE_ToFloatArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3), SE_ToFloatArg(4))
		Case func_setplayerping
			Player[SE_ToIntArg(0)]\ping = SE_ToIntArg(1)
		Case func_isafakeplayer
			G_SE_ReturnVal = (Player[SE_GetParamInt()]\fake=True)
			G_SE_ReturnType = SE_INT
		Case func_createfakeplayer
			pl.players = CreatePlayer(findFreePlayerID())
			if pl = Null Then
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
				Player[ID]\BreachType = Rand(FIRST_BREACH_TYPE,LAST_BREACH_TYPE-1)
				AddLog(name+" has joined to server")
				mp_CreatePlayerObject(pl\ID)
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
			Player[SE_ToIntArg(0)]\p_byte = (1) + (2 * ReadBool(Player[playerid]\p_byte, 1)) + (4 * ReadBool(Player[playerid]\p_byte, 2)) + (8 * ReadBool(Player[playerid]\p_byte, 3)) + (16 * SE_ToIntArg(1)) + (32 * ReadBool(Player[playerid]\p_byte, 5))
		Case func_setplayerfakeholdinggun
			Player[SE_ToIntArg(0)]\UsedWeapon = SE_ToIntArg(1)
		Case func_setplayerfakenvg
			Player[SE_ToIntArg(0)]\WearingNightVision = SE_ToIntArg(1)
		Case func_setplayerfakegasmask
			Player[SE_ToIntArg(0)]\WearingGasmask = SE_ToIntArg(1)
		Case func_setplayerfakevest
			Player[SE_ToIntArg(0)]\Wearingvest = SE_ToIntArg(1)
		Case func_setplayerfakeanimation
			Player[SE_ToIntArg(0)]\PLAYER_MOVE = SE_ToIntArg(1)
		Case func_setplayerfakehazmat
			Player[SE_ToIntArg(0)]\WearingHazmat = SE_ToIntArg(1)
		Case func_setplayerfakeblinktimer
			Player[SE_ToIntArg(0)]\blinktimer = x
		Case func_setplayerposition
			SetPlayerPosition(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3), SE_ToFloatArg(4))
		Case func_setplayerangle
			playerid = SE_GetParamInt()
			z# = SE_GetParamFloat()
			Player[playerid]\yaw = z
		Case func_setplayermessage
			SendPlayerMsg(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToIntArg(2))
		Case func_giveadmin
			playerid = SE_GetParamInt()
			Player[playerid]\Authorized = True
			AddTextToChat("[RCON] You got the admin role.", playerid)
		Case func_removeadmin
			playerid = SE_GetParamInt()
			Player[playerid]\Authorized = False
		Case func_isplayeradmin
			playerid = SE_GetParamInt()
			G_SE_ReturnVal = Player[playerid]\authorized
			G_SE_ReturnType = SE_INT
		Case func_servermessage
			AddTextToChat(SE_GetParamString())
		Case func_playerconsolecommand
			SendConsoleCommand(SE_ToIntArg(0), SE_ToStringArg(1))
		Case func_playplayersound
			PlaySoundForPlayers(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToFloatArg(2), SE_ToFloatArg(3))
		Case func_playsound
			PlaySoundForPlayer(SE_ToIntArg(0), SE_ToStringArg(1))
		Case func_sendmessage
			AddTextToChat(SE_ToStringArg(0), SE_ToIntArg(1))
		Case func_kick
			Kick(SE_ToIntArg(0), SE_ToStringArg(1))
		Case func_banip
			RCON_BanIP("banlist", SE_GetParamString())
		Case func_plugin_remove
			G_SE_ReturnVal = plugin_remove(SE_GetParamInt())
			G_SE_ReturnType = SE_INT
		Case func_plugin_call
			G_SE_ReturnVal = plugin_call(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToIntArg(2))
			G_SE_ReturnType = SE_STRING
		Case func_plugin_poke
			G_SE_ReturnVal = plugin_poke(SE_ToIntArg(0), SE_ToStringArg(1), SE_ToIntArg(2))
			G_SE_ReturnType = SE_INT
		Case func_plugin_load
			G_SE_ReturnVal = plugin_init(SE_GetParamString())
			G_SE_ReturnType = SE_INT
		Case func_delay
			Delay SE_GetParamInt()
			
		Case func_print
			if Not COMPILER_SYSTEM Then
				ConsoleLog SE_GetParamString(), 7
			Else
				print SE_GetParamString()
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
			
		Case func_int
			SE_ReturnInt(SE_AND_LUA_ToIntArg(0))
			Return
		Case func_float
			SE_ReturnFloat(SE_AND_LUA_ToFloatArg(0))
			Return
		Case func_str
			SE_ReturnString(SE_AND_LUA_ToFloatArg(0))
			Return
		Case func_floor
			SE_ReturnFloat(Floor(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_ceil
			SE_ReturnFloat(Ceil(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_sgn
			SE_ReturnInt(Sgn(SE_AND_LUA_ToIntArg(0)))
			Return
		Case func_abs
			If SE_ArgType(0)=SE_INT
				SE_ReturnInt(Ceil(SE_IntArg(0)))
			Else
				SE_ReturnFloat(Ceil(SE_AND_LUA_ToFloatArg(0)))
			EndIf
			Return
		Case func_sqr
			SE_ReturnFloat(Sqr(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_sin
			SE_ReturnFloat(Sin(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_cos
			SE_ReturnFloat(Cos(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_tan
			SE_ReturnFloat(Tan(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_asin
			SE_ReturnFloat(ASin(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_acos
			SE_ReturnFloat(ACos(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_atan
			SE_ReturnFloat(ATan(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_atan2
			SE_ReturnFloat(ATan2(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1)))
			Return
		Case func_exp
			SE_ReturnFloat(Exp(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_log
			SE_ReturnFloat(Log(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_log10
			SE_ReturnFloat(Log10(SE_AND_LUA_ToFloatArg(0)))
			Return
		Case func_rnd
			SE_ReturnFloat(Rnd(SE_AND_LUA_ToFloatArg(0), SE_AND_LUA_ToFloatArg(1)))
			Return
		Case func_rand
			SE_ReturnInt(Rand(SE_AND_LUA_ToIntArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_num
			SE_ReturnString(SE_AND_LUA_ToStringArg(0))
			Return
		Case func_left
			SE_ReturnString(Left(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_right
			SE_ReturnString(Right(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1)))
			Return
		Case func_mid
			SE_ReturnString(Mid(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1), SE_AND_LUA_ToIntArg(2)))
			Return
		Case func_replace
			SE_ReturnString(Replace(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToStringArg(2)))
			Return
		Case func_instr
			SE_ReturnInt(Instr(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToStringArg(1), SE_AND_LUA_ToIntArg(2)))
			Return
		Case func_lower
			SE_ReturnString(Lower(SE_AND_LUA_ToStringArg(0)))
			Return
		Case func_upper
			SE_ReturnString(Upper(SE_AND_LUA_ToStringArg(0)))
			Return
		Case func_trim
			SE_ReturnString(Trim(SE_AND_LUA_ToStringArg(0)))
			Return
		Case func_hex
			SE_ReturnString(Hex(SE_AND_LUA_ToIntArg(0)))
			Return
		Case func_bin
			SE_ReturnString(Bin(SE_AND_LUA_ToIntArg(0)))
			Return
		Case func_repeat
			SE_ReturnString(String(SE_AND_LUA_ToStringArg(0), SE_AND_LUA_ToIntArg(1)))
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
		case func_getincomingconnectionversion
			G_SE_ReturnVal = incomingversion
			G_SE_ReturnType = SE_STRING
		case func_getincomingconnectionpatron
			G_SE_ReturnVal = incomingpatron
			G_SE_ReturnType = SE_INT
		case func_setmtftickets
			gameinfo\b\MTFTickets = SE_GetParamFloat()
		case func_setchaostickets
			gameinfo\b\ChaosTickets = SE_GetParamFloat()
		case func_getmtftickets
			G_SE_ReturnVal = gameinfo\b\MTFTickets
			G_SE_ReturnType = SE_FLOAT
		case func_getchaostickets
			G_SE_ReturnVal = gameinfo\b\ChaosTickets
			G_SE_ReturnType = SE_FLOAT
		case func_activatefilessending
			Server\FilesSendActivate = SE_GetParamInt()
		case func_getplayermute
			G_SE_ReturnVal = Player[Int(StrTemp)]\Muted
			G_SE_ReturnType = SE_INT
		case func_setplayermute
			playerid = SE_GetParamInt()
			Player[playerid]\Muted = SE_GetParamInt()
		case func_enableauthkeyonconnect
			Server\GetAuthKey = SE_GetParamInt()
	End Select
	if G_SE_ReturnType = SE_NULL Then 
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
