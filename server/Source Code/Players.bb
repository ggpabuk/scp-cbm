
; Constants
Const JumpFalling# = -1.0
Const CHEAT_NOITEM = 1
Const CHEAT_INCORRECT_ROLE = 2
Const CHEAT_INCORRECT_POS = 3
Const CHEAT_CONSOLE_USE = 4
Const CHEAT_SPAWN_ITEM = 5
Const CHEAT_INTERCOM = 6
Const CHEAT_NORECOILORSPREAD = 7

Const PLAYER_SITTING_IDLING = 5
Const PLAYER_SITTING_WALKING_LEFT = 7
Const PLAYER_SITTING_WALKING_RIGHT = 8
Const PLAYER_SITTING_WALKING_BACK = 9
Const PLAYER_SITTING_WALKING_FORWARD = 10
Const PLAYER_IDLING = 11
Const PLAYER_WALKING = 12
Const PLAYER_RUNNING = 13
Const PLAYER_CRYING = 14



Function mp_CreatePlayerObject(i)
	Local br.breachtypes = GetBreachType(CLASSD_MODEL)
	
	Player[i]\Obj = CopyEntity(br\model)
	ScaleEntity Player[i]\Obj, br\scale, br\scale, br\scale
	MeshCullBox (Player[i]\Obj, -MeshWidth(Player[i]\Obj)*2, -MeshHeight(Player[i]\Obj)*2, -MeshDepth(Player[i]\Obj)*2, MeshWidth(Player[i]\Obj)*2, MeshHeight(Player[i]\Obj)*4, MeshDepth(Player[i]\Obj)*4)

	Player[i]\Camera = CreateCamera()
	CameraViewport Player[i]\Camera,0,0,Player[i]\monitor_width,Player[i]\monitor_height
	CameraRange(Player[i]\Camera, 0.05, 35)
	CameraProjMode(Player[i]\Camera, 0)
	If Player[i]\Pivot = 0 Then
		Player[i]\Pivot = CreatePivot()
		EntityRadius Player[i]\Pivot, 0.15, 0.3
		EntityType Player[i]\Pivot, HIT_PLAYER
		
		Player[i]\Hitbox = CreateCube()
		EntityParent Player[i]\Hitbox, Player[i]\Pivot
		EntityPickMode Player[i]\Hitbox, 2, False
		;EntityType Player[i]\Hitbox, HIT_BULLETHITBOX
		MoveEntity Player[i]\Hitbox, 0, -0.2, 0
		EntityAlpha Player[i]\Hitbox, 0.0
		
		Player[i]\AnticheatPivot = CreatePivot()
		EntityRadius Player[i]\AnticheatPivot, 0.15, 0.3
		EntityType Player[i]\AnticheatPivot, HIT_ITEM
	EndIf
	
	br.breachtypes = GetBreachType(Player[i]\BreachType)
	ScaleEntity Player[i]\Hitbox, br\hitboxx, br\hitboxy, br\hitboxz
	
	EntityParent Player[i]\Camera, Player[i]\Pivot
	MoveEntity Player[i]\Camera, 0, 0.6, 0
	
	Player[i]\JumpState = JumpFalling
	Player[i]\NoclipSpeed = 2.0
	
	ResetPlayerSize(i)
	
End Function
Function mp_GetPlayer(p.players)
	If p\ID < 1 Then Return
	If p\isdead Then
		If Server\Breach Then SetPlayerType(p\ID, 0)
	EndIf
	Local FixPivot# = 0.3, FixRotate# = 180, FixPitch = 0
	Select p\BreachType
		Case MODEL_966
			FixPivot = 0.2
		Case NTF_MODEL
			FixPivot = 0.32
		Case GUARD_MODEL
			FixPivot = 0.32
		Case MODEL_049
			FixPivot = 0.32
		Case MODEL_096
			FixPivot = 0.03
	End Select
	
	If (Not p\BreachType) Or p\IsDead Then
		If p\ShowPlayer Then
			HideEntity p\obj
			HideEntity p\Pivot
			HideEntity p\AnticheatPivot
			p\ShowPlayer = False
		EndIf
		p\DropSpeed = 0.01
	Else
		If Not p\ShowPlayer Then
			ShowEntity p\obj
			ShowEntity p\Pivot
			ShowEntity p\AnticheatPivot
			p\ShowPlayer = True
			p\DropSpeed = 0.01
		EndIf
	EndIf

	Local angle#
	
	If p\BreachType = MODEL_096 And p\PLAYER_MOVE = 13 Then
		For d.Doors = Each doors
			If EntityDistance(p\pivot, d\frameobj) < 1.5 Then
				If Not d\locked Then
					If d\dir = 0 Or d\dir = 1 Then d\open = True
				EndIf
			EndIf
		Next
	EndIf
	TraceCamera(p\ID)
	
	PositionEntity(p\Pivot, EntityX(p\AnticheatPivot), EntityY(p\AnticheatPivot)+0.32, EntityZ(p\AnticheatPivot))
	RotateEntity(p\Pivot,0,p\diryaw,0)
	ResetEntity p\Pivot
	
	PositionEntity(p\obj, EntityX(p\Pivot), EntityY(p\Pivot) - FixPivot - 0.32, EntityZ(p\Pivot))
	RotateEntity(p\obj,FixPitch,EntityYaw(p\Pivot)-FixRotate, 0, True)
	
	p\JumpState = JumpFalling
	mp_UpdatePlayerState(p)

	If Server\NoclipAnticheat And p\BreachType > 0 Then
		
		If Not p\Noclip Then 
			If p\ShouldPlayerTeleport = p\CurrentPositionID Then
				If Distance(EntityX(p\AnticheatPivot), EntityZ(p\AnticheatPivot), p\x, p\z) > Server\AC_TeleportRate*p\AC_ShouldSpeed*p\SpeedMult Then
					p\AC_PositionTick = -1
				EndIf
			EndIf
		
			If p\AC_PositionTick >= 0 Then
				If Distance3(p\x, p\y, p\z, EntityX(p\AnticheatPivot), EntityY(p\AnticheatPivot), EntityZ(p\AnticheatPivot)) > 2 Then
					p\AC_PositionTick = p\AC_PositionTick-FPSfactor
				Else
					p\AC_PositionTick = 70
					
					p\NeedFixX = EntityX(p\AnticheatPivot)
					p\NeedFixY = EntityY(p\AnticheatPivot)
					p\NeedFixZ = EntityZ(p\AnticheatPivot)
					p\PrevPlayerRoomID = p\PlayerRoomID
				EndIf
			EndIf
		EndIf
		
		If p\AC_PositionTick < 1 Then
			SetPlayerPositionEx(p\ID, p\PrevPlayerRoomID, p\NeedFixX, p\NeedFixY, p\NeedFixZ)
			p\AC_PositionTick = 70
		Else
			If p\ShouldPlayerTeleport = p\CurrentPositionID Then
				PositionEntity(p\AnticheatPivot, curvevalue(p\x, EntityX(p\AnticheatPivot), 5), curvevalue(p\y, EntityY(p\AnticheatPivot),5), curvevalue(p\z, EntityZ(p\AnticheatPivot), 5))
				If p\Noclip Then ResetEntity(p\AnticheatPivot)
			EndIf
		EndIf
		
	Else
		PositionEntity(p\AnticheatPivot, curvevalue(p\x, EntityX(p\AnticheatPivot), 5), curvevalue(p\y, EntityY(p\AnticheatPivot),5), curvevalue(p\z, EntityZ(p\AnticheatPivot), 5))
		ResetEntity p\AnticheatPivot
	EndIf
	
	p\p_byte = ReadBool(p\p_byte, 0) + (ReadBool(p\p_byte, 1)*2) + (ReadBool(p\p_byte, 2)*4) + (ReadBool(p\p_byte, 3)*8) + (p\isdead*16) + (ReadBool(p\p_byte, 5)*32) + (ReadBool(p\p_byte, 6)*64)
	
	If p\BreachType = MODEL_035 Then 
		If GivePlayerHealth(p\ID, -0.1*FPSfactor, "was decomposed") Then
			If GetScripts() Then
				pbid = public_inqueue(public_OnPlayerKillPlayer)
				public_addparam(pbid, p\ID)
				public_addparam(pbid, p\ID)
				public_addparam(pbid, 0)
				callback
			EndIf
		EndIf
		If p\Health > 500 Then p\Injuries = 0.0
	EndIf
	
	UpdatePlayerSize(p\ID)
	
	If p\ShouldSendShootTicks > 0 Then
		If p\BreachType > 0 And (Not p\isdead) And (Not p\HandCuffed) Then
			Local StreamToSend.bs = CreateByteStream(23*p\ShouldSendShootTicks)
			For i = 1 To p\ShouldSendShootTicks
				itemid = ByteStreamReadShort(p\ShouldSendShoot[i])
				If itemid < MAX_ITEMS Then
					it.Items = M_Item[itemid]
					If it <> Null Then
						If it\picker = p\ID And IsAGun(it\itemtemplate\tempname) = p\UsedWeapon Then
							xRocket# = ByteStreamReadFloat(p\ShouldSendShoot[i])
							yRocket# = ByteStreamReadFloat(p\ShouldSendShoot[i])
							zRocket# = ByteStreamReadFloat(p\ShouldSendShoot[i])
							pitchRocket# = ByteStreamReadFloat(p\ShouldSendShoot[i])
							yawRocket# = ByteStreamReadFloat(p\ShouldSendShoot[i])
							spreadmax# = Max(Float(ByteStreamReadChar(p\ShouldSendShoot[i]))/10.0, 0.7)
							If (Not mp_IsASCP(p\BreachType)) And Distance3(xRocket, yRocket, zRocket, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot)) < 4 Then
								If GetScripts() Then
									pbid = public_inqueue(public_OnPlayerShoot)
									public_addparam(pbid, p\ID)
									public_addparam(pbid, xRocket, SE_FLOAT)
									public_addparam(pbid, yRocket, SE_FLOAT)
									public_addparam(pbid, zRocket, SE_FLOAT)
									public_addparam(pbid, yawRocket, SE_FLOAT)
									public_addparam(pbid, pitchRocket, SE_FLOAT)
									callback
								EndIf
								
								If Not SE_RETURN_VALUE\StaticIndex Then
									If p\prevyaw = yawrocket Or p\prevpitch = pitchrocket Then OnCheatDetected(p\ID, CHEAT_NORECOILORSPREAD)
									p\prevyaw = yawRocket
									p\prevpitch = pitchRocket
									
									ShootTicks = GetGunShootTicks(p\UsedWeapon)
									For i = 1 To ShootTicks
										CreateBullet(p\ID, BULLET_SPEED, xRocket, yRocket, zRocket, pitchRocket+Rnd(-spreadmax*GetGunSpreadRate(p\UsedWeapon),spreadmax*GetGunSpreadRate(p\UsedWeapon)),yawRocket+Rnd(-spreadmax*GetGunSpreadRate(p\UsedWeapon), spreadmax*GetGunSpreadRate(p\UsedWeapon)), p\UsedWeapon)
									Next
									
									
									ByteStreamWriteShort StreamToSend,0
									ByteStreamWriteFloat StreamToSend,xrocket
									ByteStreamWriteFloat StreamToSend,yrocket
									ByteStreamWriteFloat StreamToSend,zrocket
									ByteStreamWriteFloat StreamToSend,pitchRocket
									ByteStreamWriteFloat StreamToSend,yawRocket
									ByteStreamWriteChar StreamToSend,spreadmax*10
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			Next
			
			For i = 1 To Server\PlayersCount
				If Distance3(xRocket, yRocket, zRocket, EntityX(PlayerOptimize[i]\Pivot), EntityY(PlayerOptimize[i]\Pivot), EntityZ(PlayerOptimize[i]\Pivot)) < 50 Then
					udp_WriteByte M_CREATEBULLET
					udp_WriteByte p\ID
					udp_WriteBytes(GetByteStreamData(StreamToSend), 0, GetByteStreamDataSize(StreamToSend))
					udp_SendMessage(PlayerOptimize[i]\ID)
				EndIf
			Next
			
			RemoveByteStream(StreamToSend)
		EndIf
		
		For i = 1 To p\ShouldSendShootTicks
			RemoveByteStream(p\ShouldSendShoot[i])
		Next
		
		p\ShouldSendShootTicks = 0
	EndIf
	;p\PredictionTicks = 0
	;FPSfactor = PrevFPS
End Function

Function ResetPlayerSize(playerid)
	Player[playerid]\prevsize = -999
End Function

Function UpdatePlayerSize(playerid)
	If Player[playerid]\size <> Player[playerid]\prevsize Then
		For br.breachtypes = Each breachtypes
			If br\constid = Player[playerid]\BreachType Then
				ScaleEntity Player[playerid]\Hitbox, br\hitboxx*Player[playerid]\Size, br\hitboxy*Player[playerid]\Size, br\hitboxz*Player[playerid]\Size
				
				PositionEntity Player[playerid]\HitBox,0,0,0
				PositionEntity Player[playerid]\Camera,0,0,0
				
				MoveEntity Player[playerid]\HitBox, 0, -0.2/Player[playerid]\Size, 0
				MoveEntity Player[playerid]\Camera, 0, 0.6*Player[playerid]\Size, 0
				Exit
			EndIf
		Next
		
		Player[playerid]\prevsize = Player[playerid]\size
	EndIf
	
	
End Function

Function mp_UpdatePlayerPrediction(p.players, himselfcollide% = False)
	;PrevFPS# = FPSfactor
	Local UpdateSCP% = Player_UpdateSCP(p)
	Local sprint# = 1.0, speed# = 0.018

	For i = 1 To Min(p\playerdirs, GetMaxPredictionTicks()*4)
		temp = False
		angle# = 0
		p\dynamicdirs = i
		
		dir = p\PlayerDir[p\DynamicDirs]
		p\diryaw = p\PlayerDirAngle[p\DynamicDirs]
		p\dirpitch = p\PlayerDirPitch[p\DynamicDirs]
		;FPSfactor = GetTickDuration()
		;if p\PlayerDirFactor[p\DynamicDirs] <> 0 Then FPSfactor = GetTickDuration() ;Min(p\PlayerDirFactor[p\DynamicDirs], Min(Max((GetMaxPredictionTicks()*4)-p\playerdirs, 0.0001*70), 5.0)) ; Anticheat for delta time
		
		p\MoveForward = ReadBool(dir, 0)
		p\MoveBack = ReadBool(dir, 1)
		p\MoveLeft = ReadBool(dir, 2)
		p\MoveRight = ReadBool(dir, 3)
		p\MoveSprint = ReadBool(dir, 4)
		p\CrouchState = ReadBool(dir, 5)
		If readbool(dir, 6) <> 0 Then p\MoveJump = True
	
		RotateEntity(p\Pivot, 0, p\diryaw, 0)
		p\yaw = EntityYaw(p\Pivot)
		
		sprint# = 1.0
		speed# = 0.018
		
		If p\BreachType = MODEL_173 Then Speed = Speed * 9
		
		If p\PlayerRoomName = "pocketdimension" Then 
			If EntityY(p\Pivot)<2000*RoomScale Or EntityY(p\Pivot)>2608*RoomScale Then
				p\Stamina = 0
				Speed = 0.015
				Sprint = 1.0
			EndIf
		EndIf
		
		If ((Not p\CrouchState) And p\MoveSprint And p\Stamina > 0.0 And (Not (p\BreachType = MODEL_173 Or p\BreachType = MODEL_966))) Or p\ForceMove > 0 Then
			Sprint = 2.5+(p\BreachType = MODEL_096)
			If p\BreachType = MODEL_096 Then
				If Not p\Raged Then 
					Sprint = 1.0
				EndIf
			EndIf
			
			If p\BreachType = MODEL_106 Or p\BreachType = MODEL_049 Then Sprint = 1.0
			
			If p\ForceMove>0 Then Speed=Speed*p\ForceMove
			
		End If

		;p\BonePitch = p\PlayerDirPitch[p\DynamicDirs]
		
		temp2# = (Speed * Sprint) / (1.0+p\CrouchState)
		If p\NoClip Then
			p\CurrSpeed = 0
			p\CrouchState = 0
			
			p\BonePitch = p\DirPitch
			RotateEntity p\Pivot, WrapAngle(p\BonePitch), EntityYaw(p\Pivot), 0
			
			temp2# = temp2# * p\NoClipSpeed * p\SpeedMult
			
			If p\MoveBack Then MoveEntity p\Pivot, 0, 0, -temp2*FPSfactor
			If p\MoveForward Then MoveEntity p\Pivot, 0, 0, temp2*FPSfactor
			
			If p\MoveLeft Then MoveEntity p\Pivot, -temp2*FPSfactor, 0, 0
			If p\MoveRight Then MoveEntity p\Pivot, temp2*FPSfactor, 0, 0	
			p\DropSpeed = temp2
			
			ResetEntity p\Pivot
		Else
			If p\JumpState = JumpFalling Then
				temp2# = temp2 / Max((p\Injuries+3.0)/3.0,1.0)
				;If p\Injuries > 0.5 Then 
				;	temp2 = temp2*Min((Sin(p\Shake/2)+1.2),1.0)
				;EndIf
				temp = False
				If p\MoveBack Then
					temp = True 
					angle = 180
					If p\MoveLeft Then angle = 135 
					If p\MoveRight Then angle = -135 
				ElseIf p\MoveForward Then
					temp = True
					angle = 0
					If p\MoveLeft Then angle = 45 
					If p\MoveRight Then angle = -45
				ElseIf p\ForceMove>0 Then
					temp=True
					angle = 0.0
				Else
					If p\MoveLeft Then angle = 90 : temp = True
					If p\MoveRight Then angle = -90 : temp = True 
				EndIf
				angle = WrapAngle(EntityYaw(p\Pivot)+angle+90.0)
				
				If UpdateSCP = False Then 
					temp2 = 0.0
					p\CurrSpeed = 0.0
				EndIf
				

				If temp Then 
					p\CurrSpeed = CurveValue(temp2, p\CurrSpeed, 20.0)
				Else
					p\CurrSpeed = Max(CurveValue(0.0, p\CurrSpeed-0.1, 1.0),0.0)
				EndIf
				
				If Server\JumpMode = True And p\BreachType <> MODEL_173 Then
					If p\MoveJump Then
						;debuglog "moveeeeeeeeeeeeeeeeeeeeeeeeeeee "+himselfcollide
						If himselfcollide Then
							;debuglog "JUMP!"
							p\JumpState = 0.05
							If p\MoveForward Then p\LastZSpeed = p\CurrSpeed*p\SpeedMult
							If p\MoveBack Then p\LastZSpeed = -p\CurrSpeed*p\SpeedMult
							If p\MoveLeft Then p\LastXSpeed = -p\CurrSpeed*p\SpeedMult
							If p\MoveRight Then p\LastXSpeed = p\CurrSpeed*p\SpeedMult
							p\MoveJump = False
							himselfcollide = False
						EndIf
					EndIf
				EndIf
				
				TranslateEntity p\Pivot, Cos(angle)*(p\CurrSpeed*p\SpeedMult) * FPSfactor, 0, Sin(angle)*(p\CurrSpeed*p\SpeedMult) * FPSfactor
			
				;if not p\noclip then
					;if p\JumpState = JumpFalling Then
						
					;EndIf
				;endif
			Else
				p\MoveJump = False
				If himselfcollide Then
					If p\JumpState <= -0.14 And Server\FallDamage Then TakeFallDamage(p, Sqr(p\JumpState*p\JumpState))
					p\LastXSpeed = 0
					p\LastZSpeed = 0
					p\JumpState = JumpFalling
				Else
					MoveEntity p\Pivot, p\LastXSpeed*FPSfactor, p\JumpState*FPSfactor, p\LastZSpeed*FPSfactor
				EndIf
				;debuglog "p jumpstate: "+p\jumpstate
				p\JumpState = p\JumpState-(Server\JumpHeight*FPSfactor)
				If p\JumpState <= JumpFalling Then p\JumpState = JumpFalling
				If Server\JumpMode = False Then p\JumpState = JumpFalling
			EndIf
		EndIf
		
		;p\DynamicDirs = p\DynamicDirs + 1
		;if p\DynamicDirs > p\PlayerDirs Then p\DynamicDirs = 0
	Next
	
	If p\JumpState = JumpFalling Then
		If Not himselfcollide Then
			p\DropSpeed# = Min(Max(p\DropSpeed - 0.005 * FPSfactor, -2.0), 0.0)
		Else
			If p\DropSpeed <= -0.17 And Server\FallDamage Then TakeFallDamage(p, Sqr(p\DropSpeed*p\DropSpeed))
			p\DropSpeed = 0
		EndIf
		
		TranslateEntity p\Pivot, 0, p\DropSpeed*FPSfactor, 0
	EndIf
	
	;p\dynamicdirs = p\playerdirs
	;p\PlayerDirs = 0
	;FPSfactor = PrevFPS
End Function

Function IsPlayerAdmin(playerid)
	Return Player[playerid]\Authorized
End Function

Function GivePlayerHealth(playerid, hp#, reason$ = "")
	If (Player[playerid]\Godmode And hp < 0) Or (Not Server\FullSync) Then Return
	
	If Player[playerid]\isdead Then Return
	
	Player[playerid]\Health = Player[playerid]\Health + hp
	
	If Player[playerid]\MaxHealth <= Player[playerid]\Health Then 
		Player[playerid]\MaxHealth = Player[playerid]\Health
		Player[playerid]\Injuries = 0
	EndIf
	
	If Not mp_IsASCP(Player[playerid]\BreachType) Then 
		If Player[playerid]\Health >= Player[playerid]\MaxHealth Then
			Player[playerid]\Injuries = 0
		Else
			Player[playerid]\Injuries = Max((Player[playerid]\MaxHealth/Max(Player[playerid]\Health, 1))/1.5, 0.0)
		EndIf
	Else
		Player[playerid]\Injuries = 0
	EndIf
	
	If Player[playerid]\Health < 1 Then 
		PlayerDead(playerid, reason)
		Return True
	EndIf
	
	Return False
End Function

Function GetPlayerHealth(playerid)
	Return Player[playerid]\Health
End Function

Function GetPlayerInjuries#(playerid)
	Return Player[playerid]\Injuries
End Function

Function OnPlayerUseItem(playerid, tempname$)
	Select Lower(tempname)
		Case "scp500"
			If Player[playerid]\BreachType = MODEL_035 Then Return
			Player[playerid]\Health = 0
			GivePlayerHealth(playerid, Player[playerid]\MaxHealth+200)
		Case "veryfinefirstaid"
			If Player[playerid]\BreachType = MODEL_035 Then Return
			Player[playerid]\Health = 0
			GivePlayerHealth(playerid, Player[playerid]\MaxHealth)
		Case "firstaid", "finefirstaid", "firstaid2"
			If Player[playerid]\BreachType = MODEL_035 Then Return
			Player[playerid]\health = 0
			GivePlayerHealth(playerid, Player[playerid]\MaxHealth)
		Case "scp035"
			SetPlayerType(playerid, MODEL_035)
	End Select
End Function

Function PlayerDead(playerid, reason$ = "")
	If Server\Breach Then SetPlayerType(playerid, 0)
	Player[playerid]\Health = 0
	Player[playerid]\IsDead = True
	Player[playerid]\injuries = 0
	Player[playerid]\PocketDimensionState = 0
	
	If reason <> "" Then
		If GetScripts() Then
			Local chatthread = public_inqueue(public_OnPlayerChat)
			public_addparam(chatthread, playerid)
			public_addparam(chatthread, " "+reason, SE_STRING)
			callback
		EndIf
		
		If Not SE_RETURN_VALUE\StaticIndex Then
			If Server\Breach And (Not Server\BreachChat) Then Return
			AddLog(Player[playerid]\name+" "+reason)
		EndIf
	EndIf
End Function

Function SetPlayerType(playerid, breachtype)
	Player[playerid]\BreachType = breachtype
	If breachtype > 0 Then 
		Player[playerid]\IsDead = False
	Else
		Player[playerid]\IsDead = True
	EndIf
End Function

Function GetPlayerHead(i)
	Return Player[i]\PlayerHead
End Function
Function GetPlayerHand(i)
	Return Player[i]\PlayerHand
End Function
Function GetCamera(id)
	Return Player[id]\Camera
End Function
Function TraceCamera(id)
	RotateEntity Player[id]\Camera, Player[id]\BonePitch,Player[id]\yaw,0, True
End Function


Function mp_UpdatePlayers(UpdateType% = 0)
	Select updatetype
		Case 0
			For p.players = Each players
				mp_GetPlayer(p)
			Next
		Case 1
			For p.players = Each players
				mp_updateplayerrole(p\id)
			Next
	End Select
End Function

Function mp_UpdatePlayerPosition(p.Players, mode = False)
	If Not mode Then
		PositionEntity p\Pivot, p\x, p\y, p\z
		ResetEntity p\Pivot
	Else
		p\x = EntityX(p\Pivot)
		p\y = EntityY(p\Pivot)
		p\z = EntityZ(p\Pivot)
	EndIf
	
	PositionEntity p\AnticheatPivot, p\x, p\y, p\z
	ResetEntity p\AnticheatPivot
	
		
	mp_SetRoomNameToPlayer(p)
	p\DropSpeed = 0.01

	p\NeedFixX = EntityX(p\AnticheatPivot)
	p\NeedFixY = EntityY(p\AnticheatPivot)
	p\NeedFixZ = EntityZ(p\AnticheatPivot)
	p\PrevPlayerRoomID = p\PlayerRoomID
End Function

Function SetPlayerPositionEx(playerid, roomid, X#, Y#, Z#)
	If Player[playerid] = Null Then Return
	;if (Not Server\FullSync) Or ((Not Server\Interpolation) And (Not Server\Prediction)) Then
	;	udp_WriteByte M_SETPOS
	;	udp_WriteByte 1
	;	udp_WriteLine room
	;	udp_WriteFloat X
	;	udp_WriteFloat Y
	;	udp_WriteFloat Z
	;	udp_SendMessage playerid
	;EndIf
	
	Player[playerid]\ShouldPlayerTeleport = Player[playerid]\ShouldPlayerTeleport Mod 65535
	Player[playerid]\ShouldPlayerTeleport = Player[playerid]\ShouldPlayerTeleport+1
	Player[playerid]\ShouldPlayerTeleportX = X
	Player[playerid]\ShouldPlayerTeleportY = Y
	Player[playerid]\ShouldPlayerTeleportZ = Z
	Player[playerid]\ShouldPlayerTeleportRoom = roomid
	Player[playerid]\ShouldPlayerTeleportIsID = True
	
	
	Player[playerid]\PrevPlayerRoomID = roomid
	Player[playerid]\PlayerRoomID = roomid
	Player[playerid]\x = x
	Player[playerid]\y = Y
	Player[playerid]\z = Z
	mp_UpdatePlayerPosition(Player[playerid])
End Function

Function SetPlayerPosition(playerid, room$, X#, Y#, Z#)
	If Player[playerid] = Null Then Return
	;if (Not Server\FullSync) Or ((Not Server\Interpolation) And (Not Server\Prediction)) Then
	;	udp_WriteByte M_SETPOS
	;	udp_WriteByte 1
	;	udp_WriteLine room
	;	udp_WriteFloat X
	;	udp_WriteFloat Y
	;	udp_WriteFloat Z
	;	udp_SendMessage playerid
	;EndIf
	
	Player[playerid]\ShouldPlayerTeleport = Player[playerid]\ShouldPlayerTeleport Mod 65535
	Player[playerid]\ShouldPlayerTeleport = Player[playerid]\ShouldPlayerTeleport+1
	Player[playerid]\ShouldPlayerTeleportX = X
	Player[playerid]\ShouldPlayerTeleportY = Y
	Player[playerid]\ShouldPlayerTeleportZ = Z
	Player[playerid]\ShouldPlayerTeleportRoom = room
	Player[playerid]\ShouldPlayerTeleportIsID = False
	
	For r.Rooms = Each Rooms
		If r\RoomTemplate\Name = room Then
			Player[playerid]\PlayerRoomID = r\ID
			Player[playerid]\PrevPlayerRoomID = Player[playerid]\PlayerRoomID
			Exit
		EndIf
	Next
	
	Player[playerid]\x = x
	Player[playerid]\y = Y
	Player[playerid]\z = Z
	mp_UpdatePlayerPosition(Player[playerid])
End Function

Function CreatePlayerSound(playerid, filename$, x#, y#, z#, distance#, volume#)
	If Player[playerid] = Null Then Return
	udp_WriteByte M_SOUND3D
	udp_WriteByte playerid
	udp_WriteLine(filename)
	udp_WriteFloat x
	udp_WriteFloat y
	udp_WriteFloat z
	udp_WriteFloat distance
	udp_WriteFloat volume
	udp_SendMessage(playerid)
End Function
Function PlaySoundForPlayers(playerid, filename$, distance#, volume#)
	If Player[playerid] = Null Then Return
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte M_SOUND
			udp_WriteByte playerid
			udp_WriteLine(filename)
			udp_WriteFloat distance
			udp_WriteFloat volume
			udp_SendMessage(i)
		EndIf
	Next
End Function

Function mp_SetPlayerRoomID(p.Players, room.Rooms)
	p\PlayerRoomID = room\ID
End Function

Function multiplayer_UpdateplayerRoom(p.players)
	Local foundnewroom% = False, x#, z#, ClientRoom.Rooms = Room[p\PlayerRoomID]
	If ClientRoom <> Null Then
		If Abs(EntityY(p\Pivot) - EntityY(ClientRoom\obj)) < 1.5 Then
			x = Abs(ClientRoom\x-EntityX(p\Pivot))
			z = Abs(ClientRoom\z-EntityZ(p\Pivot))	
			If x < 4.0 Then
				If z < 4.0 Then foundnewroom = True
			EndIf
			If foundnewroom = False Then
				For i=0 To 3
					If ClientRoom\Adjacent[i]<>Null Then
						x = Abs(ClientRoom\Adjacent[i]\x-EntityX(p\Pivot,True))
						If x < 4.0 Then
							z = Abs(ClientRoom\Adjacent[i]\z-EntityZ(p\Pivot,True))
							If z < 4.0 Then
								mp_SetPlayerRoomID(p, ClientRoom\Adjacent[i])
								mp_SetRoomNameToPlayer(p)
								Return
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		Else
			foundnewroom = True
		EndIf
	EndIf
	If Not foundnewroom Then
		For r.Rooms = Each rooms
			If r <> ClientRoom Then
				x = Abs(r\x-EntityX(p\Pivot))
				z = Abs(r\z-EntityZ(p\Pivot))	
				If x < 4.0 Then
					If z < 4.0 Then
						If Abs(EntityY(p\Pivot) - EntityY(r\obj)) < 1.5 Then 
							mp_SetPlayerRoomID(p, r)
							mp_SetRoomNameToPlayer(p)
							Return
						EndIf
					EndIf
				EndIf
			EndIf
		Next
	EndIf
End Function

Function OnPlayerConsole(playerid, cinput$, shouldadmin%=False)
	Local StrTemp$
	If Instr(cinput, " ") > 0 Then
		StrTemp$ = Lower(Left(cinput, Instr(cinput, " ") - 1))
	Else
		StrTemp$ = Lower(cinput)
	End If
		
	p.players = Player[playerid]
	ID = playerid
	
	If IsPlayerAdmin(playerid) Or (Not Server\NoCheat) Or shouldadmin Then
		Select Lower(StrTemp)
			Case "scp-420-j","420","weed"
				For i = 1 To 20
					If Rand(2)=1 Then
						it.Items = CreateItem("Some SCP-420-J","420", EntityX(p\Pivot,True)+Cos((360.0/20.0)*i)*Rnd(0.3,0.5), EntityY(GetCamera(playerid),True), EntityZ(p\Pivot,True)+Sin((360.0/20.0)*i)*Rnd(0.3,0.5))
					Else
						it.Items = CreateItem("Joint","420s", EntityX(p\Pivot,True)+Cos((360.0/20.0)*i)*Rnd(0.3,0.5), EntityY(GetCamera(playerid),True), EntityZ(p\Pivot,True)+Sin((360.0/20.0)*i)*Rnd(0.3,0.5))
					EndIf
					EntityType (it\collider, HIT_ITEM)
				Next
			Case "unlockexits"
				If GetScripts() Then
					pbid = public_inqueue(public_OnPlayerRequestUnlockExits)
					public_addparam(pbid, playerid)
					callback
				EndIf
				For e.Events = Each Events
					If e\EventName = "gateaentrance" Then
						e\EventState3 = 1
						e\room\RoomDoors[1]\open = True
						e\room\RoomDoors[1]\locked = False
					ElseIf e\EventName = "exit1" Then
						e\EventState3 = 1
						e\room\RoomDoors[4]\open = True
						e\room\RoomDoors[4]\locked = False
					EndIf
				Next
				
				RemoteDoorOn = True
			Case "spawn"
				args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				StrTemp$ = Piece$(args$, 1)
				StrTemp2$ = Piece$(args$, 2)
				
				;Hacky fix for when the user doesn't input a second parameter.
				If (StrTemp <> StrTemp2) Then
					Console_SpawnNPC(Player[playerid]\Pivot, StrTemp, StrTemp2)
				Else
					Console_SpawnNPC(Player[playerid]\Pivot, StrTemp)
				EndIf
			Case "stfu", "stopsound"
				If Curr173 <> Null Then
					For e.Events = Each Events
						If e\EventName = "alarm" Then 
							If e\room\NPC[0] <> Null Then RemoveNPC(e\room\NPC[0])
							If e\room\NPC[1] <> Null Then RemoveNPC(e\room\NPC[1])
							If e\room\NPC[2] <> Null Then RemoveNPC(e\room\NPC[2])

							PositionEntity Curr173\Collider, 0,0,0
							ResetEntity Curr173\Collider
							ShowEntity Curr173\obj
							RemoveEvent(e)
							Exit
						EndIf
					Next
					For i = 1 To Server\Maxplayers
						If Player[i] <> Null Then
							udp_WriteByte M_STOPSOUND
							udp_WriteByte playerid
							udp_SendMessage(i)
						EndIf
					Next
				EndIf
			Case "notarget"
				
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				
				Select StrTemp
					Case "on", "1", "true"
						NoTarget% = True						
					Case "off", "0", "false"
						NoTarget% = False	
					Default
						NoTarget% = Not NoTarget%
				End Select
				
				If GetScripts() Then
					public_inqueue(public_OnPlayerRequestNoTarget)
					public_addparam(0, playerid)
					callback
				EndIf
			Case "noclip","fly"
				;[Block]
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				
				Select StrTemp
					Case "on", "1", "true"
						p\NoClip = True
					Case "off", "0", "false"
						p\NoClip = False	
					Default
						p\NoClip = Not p\NoClip
				End Select
				
				Player[playerid]\DropSpeed = 0
			Case "noclipspeed"
				;[Block]
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				
				p\NoClipSpeed = Float(StrTemp)
			Case "revive","undead","resurrect"
				Player[ID]\IsDead = False
			Case "spawnitem"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				temp = False 
				For itt.Itemtemplates = Each ItemTemplates
					If (Lower(itt\name) = StrTemp) Then
						temp = True
						Exit
					Else If (Lower(itt\tempname) = StrTemp) Then
						temp = True
						Exit
					End If
				Next
				If temp = True Then
					it.Items = CreateItem(itt\name, itt\tempname, EntityX(Player[ID]\Pivot), EntityY(GetCamera(ID), True)-0.1, EntityZ(Player[ID]\Pivot))
					EntityType(it\collider, HIT_ITEM)
				EndIf
			Case "role"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If Int(StrTemp) >= 0 And Int(StrTemp) <= LAST_BREACH_TYPE-1 Then SetPlayerType(ID, Int(StrTemp))
			Case "tele"
				;[Block]
				args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				StrTemp$ = Piece$(args$,1," ")
				StrTemp2$ = Piece$(args$,2," ")
				StrTemp3$ = Piece$(args$,3," ")
				PositionEntity p\Pivot,Float(StrTemp$),Float(StrTemp2$),Float(StrTemp3$)
				ResetEntity p\Pivot
				SetPlayerPosition(p\ID, Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
			Case "tfd"
				If p\PlayerRoomName = "dimension1499" Then
					PositionEntity (p\Pivot, p\DimensionPrevX, p\DimensionPrevY, p\DimensionPrevZ)
					ResetEntity(p\Pivot)
					
					mp_SetPlayerRoomID(p, p\DimensionPrevRoom)
					mp_UpdatePlayerPosition(p, True)
					
					SetPlayerPosition(p\ID, Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
					
					p\DimensionPrevX = 0 
					p\DimensionPrevY = 0 
					p\DimensionPrevZ = 0
					p\DimensionPrevRoom = Null
				EndIf
			Case "teleport"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				
				If StrTemp = "dimension1499" Then
					For it.Items = Each Items
						If it\picker = playerid Then
							If it\itemtemplate\name = "SCP-1499" Then
								For e.Events = Each Events
									If e\EventConst = e_dimension1499 Then
										If Room[p\playerroomid]\roomtemplate\name <> "dimension1499" Then
											p\DimensionPrevX = EntityX(p\Pivot)
											p\DimensionPrevY = EntityY(p\Pivot) 
											p\DimensionPrevZ = EntityZ(p\Pivot)
											p\DimensionPrevRoom = Room[p\PlayerRoomID]
										EndIf
										
										PositionEntity (p\Pivot, EntityX(e\room\obj), EntityY(e\room\obj)+1, EntityZ(e\room\obj))
										ResetEntity(p\Pivot)
										mp_SetPlayerRoomID(p, e\room)
										mp_UpdatePlayerPosition(p, True)
										SetPlayerPosition(p\ID, Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
										p\PocketDimensionState = 0
										Return
									EndIf
								Next
								Exit
							EndIf
						EndIf
					Next
				EndIf
		
				Select StrTemp
					Case "895", "scp-895"
						StrTemp = "coffin"
					Case "scp-914"
						StrTemp = "914"
					Case "offices", "office"
						StrTemp = "room2offices"
				End Select
						
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = StrTemp Then
						PositionEntity (p\Pivot, EntityX(r\obj), EntityY(r\obj)+1, EntityZ(r\obj))
						ResetEntity(p\Pivot)
						mp_SetPlayerRoomID(p, r)
						mp_UpdatePlayerPosition(p, True)
						SetPlayerPositionEx(p\ID, r\ID, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
						StrTemp = ""
						p\PocketDimensionState = 0
						Exit
					EndIf
				Next

				For p2.players = Each players
					If p2\name <> "" Then
						If Lower(p2\Name) = Lower(StrTemp) Then
							PositionEntity (p\Pivot, EntityX(p2\Pivot), EntityY(p2\Pivot)+0.1, EntityZ(p2\Pivot))
							ResetEntity(p\Pivot)
							
							For r.Rooms = Each Rooms
								If r\RoomTemplate\Name = p2\PlayerRoomName Then
									mp_SetPlayerRoomID(p, r)
									mp_UpdatePlayerPosition(p, True)
									Exit
								EndIf
							Next
							
							p\PocketDimensionState = 0
							StrTemp = ""
							
							SetPlayerPositionEx(p\ID, p\PlayerRoomID, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
							Exit
						EndIf
					EndIf
				Next
			Case "godmode", "god"
				;[Block]
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				
				Select StrTemp
					Case "on", "1", "true"
						p\GodMode = True
					Case "off", "0", "false"
						p\GodMode = False
					Default
						p\GodMode = Not p\GodMode
				End Select	
			Case "heal"
				p\Injuries = 0
				p\Health = p\MaxHealth
			Case "ban"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If IsPlayerAdmin(playerid) Then
					If IsValidPlayer(Int(StrTemp)) Then
						If Player[Int(StrTemp)] <> Null Then RCON_BanIP("banlist", Player[Int(StrTemp)]\PlayerIP)
					EndIf
				EndIf
			Case "bansteam"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If IsPlayerAdmin(playerid) Then
					If IsValidPlayer(Int(StrTemp)) Then
						If Player[Int(StrTemp)] <> Null Then RCON_BanSteamID("banliststeam", Player[Int(StrTemp)]\PlayerSteamID)
					EndIf
				EndIf
			Case "kick"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If IsPlayerAdmin(playerid) Then
					If IsValidPlayer(Int(StrTemp)) Then
						If Player[Int(StrTemp)] <> Null Then RCON_Kick(Player[Int(StrTemp)]\name)
					EndIf
				EndIf
			Case "tpto"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If IsPlayerAdmin(playerid) Then
					If IsValidPlayer(Int(StrTemp)) Then
						PositionEntity (p\Pivot, EntityX(Player[Int(StrTemp)]\Pivot),EntityY(Player[Int(StrTemp)]\Pivot)+0.1, EntityZ(Player[Int(StrTemp)]\Pivot))
						ResetEntity(p\Pivot)
						
						mp_SetPlayerRoomID(p, Room[Player[Int(StrTemp)]\PlayerRoomID])
						mp_UpdatePlayerPosition(p, True)
						
						SetPlayerPosition(p\ID, Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot)+0.1, EntityZ(p\Pivot))
					EndIf
				EndIf
			Case "tpme"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If IsPlayerAdmin(playerid) Then
					If IsValidPlayer(Int(StrTemp)) Then
						PositionEntity (Player[Int(StrTemp)]\Pivot, EntityX(p\Pivot),EntityY(p\Pivot)+0.1, EntityZ(p\Pivot))
						ResetEntity(Player[Int(StrTemp)]\Pivot)
						
						mp_SetPlayerRoomID(Player[Int(StrTemp)], Room[p\PlayerRoomID])
						mp_UpdatePlayerPosition(Player[Int(StrTemp)], True)
						
						SetPlayerPosition(Int(StrTemp), Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot)+0.1, EntityZ(p\Pivot))
					EndIf
				EndIf
			Case "mute"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
				If IsPlayerAdmin(playerid) Then
					If IsValidPlayer(Int(StrTemp)) Then
						If Player[Int(StrTemp)] <> Null Then 
							Player[Int(StrTemp)]\Muted = Not Player[Int(StrTemp)]\Muted
							If Player[Int(StrTemp)]\Muted Then
								AddTextToChat("[RCON] You muted the "+Player[Int(StrTemp)]\name, p\ID)
								AddTextToChat("You get muted.", Player[Int(StrTemp)]\ID)
							Else
								AddTextToChat("[RCON] You unmuted the "+Player[Int(StrTemp)]\name, p\ID)
								AddTextToChat("You get unmute.", Player[Int(StrTemp)]\ID)
							EndIf
						EndIf
					EndIf
				EndIf
				
			Case "giverole"
				If IsPlayerAdmin(playerid) Then
					args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
					StrTemp$ = Piece$(args$,1," ")
					StrTemp2$ = Piece$(args$,2," ")
					
					If IsValidPlayer(Int(StrTemp)) Then
						If Int(StrTemp2) >= 0 And Int(StrTemp2) <= LAST_BREACH_TYPE-1 Then 
							SetPlayerType(Int(StrTemp), Int(StrTemp2))
							AddTextToChat("[RCON] You successfully give "+mp_Breach_GetName(Int(StrTemp2))+" to: "+Player[Int(StrTemp)]\name, p\ID)
						EndIf
					EndIf
				EndIf
			Case "giveitem"
				If IsPlayerAdmin(playerid) Then
					args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
					StrTemp$ = Piece$(args$,1," ")
					StrTemp2$ = Piece$(args$,2," ")
					
					If IsValidPlayer(Int(StrTemp)) Then
						If Player[Int(StrTemp)] <> Null Then
							For itt.ItemTemplates = Each Itemtemplates
								If Lower(itt\name) = Lower(StrTemp2) Or Lower(itt\tempname) = Lower(StrTemp2) Then
									GiveItem(itt\name, itt\tempname, Int(StrTemp))
									AddTextToChat("[RCON] You successfully give "+StrTemp2+" to: "+Player[Int(StrTemp)]\name, p\ID)
									Exit
								EndIf
							Next
						EndIf
					EndIf
				EndIf
			Case "startmatch"
				If IsPlayerAdmin(playerid) Then
					If Not breach_IsStarted() Then 
						ShouldStartRound = True
						AddTextToChat("[RCON] The match successfully started", p\ID)
					Else
						AddTextToChat("[RCON] The match already started", p\ID)
					EndIf
				EndIf
			Case "restart"
				If IsPlayerAdmin(playerid) Then
					ShouldRestartServer = True
				EndIf
			Case "spawnchaos"
				If IsPlayerAdmin(playerid) Then
					If breach_IsStarted() Then
						If gameinfo\b\ChaosTickets >= 1 Then
							gameinfo\b\Teams = 1
							SpawnMTF()
							AddTextToChat("[RCON] Chaos successfully spawned!", p\ID)
						Else
							AddTextToChat("[RCON] Chaos doesn't have tickets!", p\ID)
						EndIf
					EndIf
				EndIf
			Case "spawnmtf"
				If IsPlayerAdmin(playerid) Then
					If breach_IsStarted() Then
						If gameinfo\b\MTFTickets >= 1 Then
							gameinfo\b\Teams = 0
							SpawnMTF()
							AddTextToChat("[RCON] MTF successfully spawned!", p\ID)
						Else
							AddTextToChat("[RCON] MTF doesn't have tickets!", p\ID)
						EndIf
					EndIf
				EndIf
			Case "lob"
				If IsPlayerAdmin(playerid) Then
					If Not breach_IsStarted() Then
						StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
						gameinfo\b\LobbyTimer = MilliSecs()+(Int(StrTemp)*60000)
						gameInfo\b\BreachTimer = MilliSecs()+Server\BreachTime+(Int(StrTemp)*60000)
						gameInfo\b\TimeoutBreach = MilliSecs()+(Int(StrTemp)*60000)
					EndIf
				EndIf
			Case "shouldannounc"
				If IsPlayerAdmin(playerid) Then
					If breach_IsStarted() Then
						Player[playerid]\ShouldAnnouncement = Not Player[playerid]\ShouldAnnouncement
						If Player[playerid]\ShouldAnnouncement Then 
							PlaySoundForPlayer(playerid, "GFX\multiplayer\game\sounds\Announcement.ogg")
						Else
							PlaySoundForPlayer(playerid, "GFX\multiplayer\game\sounds\Announcement2.ogg")
						EndIf
					EndIf
				EndIf
			Case "cancelwarheads"
				If IsPlayerAdmin(playerid) Then
					If breach_IsStarted() Then
						DeactivateWarheads()
					EndIf
				EndIf
			Case "activatewarheads"
				If IsPlayerAdmin(playerid) Then
					If breach_IsStarted() Then
						ActivateWarheads("Warheads")
						gameinfo\b\ExplodeTimeout = MilliSecs()+20000
						AddTextToChat("[RCON] Warheads successfully activated!", p\ID)
					EndIf
				EndIf
			Case "forcewarheads"
				If IsPlayerAdmin(playerid) Then
					If breach_IsStarted() Then
						If GetScripts() Then
							public_inqueue(public_OnWarheadsExplosion)
							callback
						EndIf
						playerzone = 0
						For i = 1 To Server\Maxplayers
							If Player[i] <> Null Then
								playerzone = GetPlayerZone(i)
								If playerzone > 0 And playerzone < 4 And (Not (Player[i]\PlayerRoomName = "exit1" And EntityY(Player[i]\Pivot) > 1040.0*RoomScale)) Then
									udp_WriteByte(M_EXPL)
									udp_WriteByte(1)
									udp_WriteInt(1)
									udp_SendMessage(i)
								EndIf
							EndIf
						Next
						gameinfo\b\Exploded = 2
						gameinfo\b\BreachTimer = MilliSecs()+5000
					EndIf
				EndIf
			Case "setmtftickets"
				If IsPlayerAdmin(playerid) Then
					StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
					gameinfo\b\MTFTickets = Float(StrTemp)
					AddTextToChat("[RCON] "+gameinfo\b\MTFtickets+" tickets set to MTF", p\ID)
				EndIf
			Case "setchaostickets"
				If IsPlayerAdmin(playerid) Then
					StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
					gameinfo\b\ChaosTickets = Float(StrTemp)
					AddTextToChat("[RCON] "+gameinfo\b\ChaosTickets+" tickets set to Chaos", p\ID)
				EndIf
			Case "sanic"
				p\AC_ShouldSpeed = 6.0
		End Select
	Else
		Select Lower(StrTemp)
			Case "tfd"
				If p\PlayerRoomName = "dimension1499" Then
					PositionEntity (p\Pivot, p\DimensionPrevX, p\DimensionPrevY, p\DimensionPrevZ)
					ResetEntity(p\Pivot)
					
					mp_SetPlayerRoomID(p, p\DimensionPrevRoom)
					mp_UpdatePlayerPosition(p, True)
					SetPlayerPosition(p\ID, Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
					p\DimensionPrevX = 0 
					p\DimensionPrevY = 0 
					p\DimensionPrevZ = 0
					p\DimensionPrevRoom = Null
					Return
				EndIf
			Case "teleport"
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
				If StrTemp = "dimension1499" Then
					For it.Items = Each Items
						If it\picker = playerid Then
							If it\itemtemplate\name = "SCP-1499" Then
								For e.Events = Each Events
									If e\EventConst = e_dimension1499 Then
										If Room[p\PlayerRoomID]\RoomTemplate\Name <> "dimension1499" Then
											p\DimensionPrevX = EntityX(p\Pivot)
											p\DimensionPrevY = EntityY(p\Pivot) 
											p\DimensionPrevZ = EntityZ(p\Pivot)
											p\DimensionPrevRoom = Room[p\PlayerRoomID]
										EndIf
						
										PositionEntity (p\Pivot, EntityX(e\room\obj), EntityY(e\room\obj)+0.7, EntityZ(e\room\obj))
										ResetEntity(p\Pivot)
										mp_SetPlayerRoomID(p, e\room)
										mp_UpdatePlayerPosition(p, True)
										p\PocketDimensionState = 0
										SetPlayerPosition(p\ID, Room[p\PlayerRoomID]\RoomTemplate\Name, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
										Return
									EndIf
								Next
								Return
							EndIf
						EndIf
					Next
				EndIf
		End Select
		OnCheatDetected(playerid, CHEAT_CONSOLE_USE)
	EndIf
End Function

Function MoveToPocketDimension(ID)
	Local r.Rooms
	
	For e.Events = Each events
		If e\EventConst = e_pocketdimension Then
			PositionEntity(Player[ID]\Pivot, EntityX(e\room\obj),1,EntityZ(e\room\obj))
			Player[ID]\DropSpeed = 0
			ResetEntity Player[ID]\Pivot
			
			If Not Server\Breach Then GivePlayerHealth(ID, -20, "was killed by SCP-106")
			
			mp_SetPlayerRoomID(Player[ID], e\room)
			mp_UpdatePlayerPosition(Player[ID], True)
			SetPlayerPosition(ID, Room[Player[ID]\PlayerRoomID]\RoomTemplate\Name, EntityX(Player[ID]\Pivot),EntityY(Player[ID]\Pivot)+0.1, EntityZ(Player[ID]\Pivot))
			Return
		EndIf
	Next
End Function
Function Player_UpdateSCP(ReceivePlayer.players)
	Local move = True
	Select ReceivePlayer\BreachType
		Case MODEL_096
			For p.players = Each players
				If p\ID <> 0 And p\ID <> ReceivePlayer\ID Then
					If (Not mp_IsAFriend(ReceivePlayer\BreachType,p\BreachType)) And (Not p\IsDead) Then
						If EntityDistance(ReceivePlayer\Pivot, p\Pivot) < 30*2 Then
							If (p\BlinkTimer < - 16 Or p\BlinkTimer > - 6) Then
								If EntityInView(GetCamera(ReceivePlayer\ID), GetCamera(p\ID)) And EntityInView(GetCamera(p\ID), GetCamera(ReceivePlayer\ID)) Then
									If EntityVisible(ReceivePlayer\Hitbox, p\Hitbox) Then
										receiveplayer\ragedupdate = MilliSecs()+15000
										
										If receiveplayer\ragestart = 0.0 And receiveplayer\raged = False Then
											receiveplayer\ragestart = 70*27
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			Next
			
			receiveplayer\ragestart = Max(0, receiveplayer\ragestart-FPSfactor)
			If receiveplayer\ragestart = 0.0 Then
				receiveplayer\raged = True
				If receiveplayer\ragedupdate < MilliSecs() Then 
					receiveplayer\raged = False
				EndIf
			Else
				receiveplayer\raged = False
				receiveplayer\ragedupdate = MilliSecs()+15000
			EndIf
		Case MODEL_173
			For p.players = Each players
				If p\ID <> 0 And p\ID <> ReceivePlayer\ID Then
					If (Not mp_IsAFriend(ReceivePlayer\BreachType,p\BreachType)) Then
						If (p\BlinkTimer < - 16 Or p\BlinkTimer > - 6) Then
							If EntityDistance(ReceivePlayer\Pivot, p\Pivot) < 30*2 Then
								If EntityInView(ReceivePlayer\Hitbox, GetCamera(p\ID)) Then
									If EntityVisible(ReceivePlayer\Hitbox, p\Hitbox) Then
										move = False
										Exit
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			Next
		Case MODEL_049

		Case MODEL_106

		Case MODEL_966
			For p.players = Each Players
					If p\ID <> 0 And p\ID <> ReceivePlayer\ID Then
						If (Not multiplayer_IsAFriend(ReceivePlayer\BreachType,p\BreachType)) Then
							If EntityDistance(Collider, p\Pivot) < 3.0 Then
									Stamina = Stamina - FPSfactor * 0.7
									StaminaEffectTimer = 30
									StaminaEffect = 0.1 ;insomnia
									ReceivePlayer\insomniaupdate = MilliSecs()+2000
								End If
							End If
						End If 
			Next
			
			If ReceivePlayer\insomniaupdate < MilliSecs() And ReceivePlayer\insomniaupdate <> 0 Then
				StaminaEffect = 1.0
				StaminaEffectTimer = 0
				ReceivePlayer\insomniaupdate = 0
			End If
		Case MODEL_939

		Case MODEL_ZOMBIE
		Default
			ReceivePlayer\Raged = False
	End Select
	Return move
End Function

Function TakeFallDamage(p.players, damage#)
	GivePlayerHealth(p\ID, -damage*32, "was killed by fall")
	CreateSound("SFX\General\BodyFall.ogg", EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot))
End Function

Function mp_UpdatePlayerState(p.players)
	i = p\ID
	
	If p\Announcement And p\TimeIntercom < MilliSecs() Then p\Announcement = False
	
	If p\ShouldAnnouncement Then p\Announcement = True
	
	If p\MicroHIDSHOOT <> 0 Then
		If p\UsedWeapon = GUN_MICROHID Then
			If p\MicroHIDItem = 0 Then
				For it.Items = Each Items
					If it\picker = i Then
						If it\itemtemplate\tempname = "microhid" Then
							p\MicroHIDItem = it\ID
							Exit
						EndIf
					EndIf
				Next
				If p\MicroHIDItem <> 0 Then 
					p\MicroHIDSHOOT = True
					p\MicroHIDAnticheatTimer = MilliSecs()+10000
				Else
					p\MicroHIDSHOOT = False
				EndIf
			Else
				If p\MicroHIDAnticheatTimer < MilliSecs() Then 
					p\MicroHIDSHOOT = False
					p\MicroHIDAnticheatTimer = 0
					If M_Item[p\MicroHIDItem] <> Null Then RemoveItem(M_Item[p\MicroHIDItem])
					p\MicroHIDItem = 0
				EndIf
			EndIf
		Else
			p\MicroHIDSHOOT = False
		EndIf
	Else
		If p\MicroHIDAnticheatTimer <> 0 Then
			p\MicroHIDAnticheatTimer = 0
			If M_Item[p\MicroHIDItem] <> Null Then RemoveItem(M_Item[p\MicroHIDItem])
			p\MicroHIDItem = 0
		EndIf
	EndIf
	
	If p\MicroHIDShoot Then
		EntityPickMode p\Hitbox, 0, False
		;EntityPickMode p\Pivot, 0
		;EntityPickMode p\obj, 0, False
			
		Local pvt = CreatePivot()
		PositionEntity pvt, EntityX(GetCamera(p\ID), True), EntityY(GetCamera(p\ID), True), EntityZ(GetCamera(p\ID), True)
		RotateEntity pvt, p\bonepitch, p\yaw, 0
		
		
		If EntityPick(pvt, 30) <> 0 Then
			For p2.players = Each players
				If p <> p2 Then
					If (Not mp_IsAFriend(p2\BreachType, p\BreachType)) And (Not p2\IsDead) Then
						If Distance3(PickedX(),PickedY(),PickedZ(), EntityX(p2\Pivot), EntityY(p2\Pivot), EntityZ(p2\Pivot)) < 1 Then
							If GivePlayerHealth(p2\ID, -GetGunDamage(GUN_MICROHID)*FPSfactor, "was killed by Micro-HID by "+p\name) Then
								If GetScripts() Then
									pbid = public_inqueue(public_OnPlayerKillPlayer)
									public_addparam(pbid, p\ID)
									public_addparam(pbid, p2\ID)
									public_addparam(pbid, p\UsedWeapon)
									callback
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			Next
		EndIf
		EntityPickMode p\Hitbox, 2, False
		;EntityPickMode p\Pivot, 1
		;EntityPickMode p\obj, 2, False
			
		FreeEntity pvt
	EndIf
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D