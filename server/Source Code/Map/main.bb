

Include "Source Code\Map\Difficulty.bb"
Include "Source Code\Map\Items.bb"
Include "Source Code\Map\StrictLoads.bb"
Include "Source Code\Map\DevilParticleSystem.bb"
; ----------------------------------------------------------
Const INFINITY# = (999.0) ^ (99999.0), NAN# = (-1.0) ^ (0.5)
Global SelectedEnding$, EndingScreen%, EndingTimer#
Global SelectedItem.Items
Global SoundEmitter%
Global TempSounds%[10]
Global TempSoundCHN%
Global TempSoundIndex% = 0
Global NoTarget%
Global CurTime#, PrevTime#, LoopDelay%, FPSfactor#, FPSfactor2#, PrevFPSFactor#, FPSFactorLimit#
Global CheckFPS%, ElapsedLoops%, FPS%, ElapsedTime#
Global MenuScale# = 1.0
Global AmbientLightRoomTex
Global Monitor2, Monitor3, MonitorTexture2, MonitorTexture3, MonitorTexture4, MonitorTextureOff
Global MonitorTimer# = 0.0, MonitorTimer2# = 0.0, UpdateCheckpoint1%, UpdateCheckpoint2%
Global NTF_OBJECT
		Global HAZMAT_OBJECT
		Global PISTOL_OBJECT
		Global OBJECT_173
		Global P90_OBJECT
		Global MP5_OBJECT
		Global RPG_OBJECT
		Global ROCKET_OBJECT
		Global MINIGUN_OBJECT
		Global GRENADE_OBJECT
		Global SURGEON_ZOMBIE
		Global OBJECT_096
		Global HAOS_OBJECT
		Global GUARD_OBJECT

Global AccessCode%
Global RetValMillisecs%

Global Mesh_MinX#, Mesh_MinY#, Mesh_MinZ#
Global Mesh_MaxX#, Mesh_MaxY#, Mesh_MaxZ#
Global Mesh_MagX#, Mesh_MagY#, Mesh_MagZ#
;----------------------------------- meshes and textures ----------------------------------------------------------------
Global DeathMSG$
Global FogTexture%, Fog%
Global GasMaskTexture%, GasMaskOverlay%
Global InfectTexture%, InfectOverlay%
Global DarkTexture%, Dark%
Global Collider%, Head%

Global FogNVTexture%
Global NVTexture%, NVOverlay%

Global TeslaTexture%

Global LightTexture%, Light%
Dim LightSpriteTex%(5)
Global DoorOBJ%, DoorFrameOBJ%

Global LeverOBJ%, LeverBaseOBJ%

Global DoorColl%
Global ButtonOBJ%, ButtonKeyOBJ%, ButtonCodeOBJ%, ButtonScannerOBJ%

Dim DecalTextures%(20)

Global Monitor%, MonitorTexture%
Global CamBaseOBJ%, CamOBJ%

Global LiquidObj%,MTFObj%,GuardObj%,ClassDObj%,Obj939%,NPC106Obj%,NPC966Obj%,ClerkMP%
Global ApacheObj%,ApacheRotorObj%

Global UnableToMove% = False
Global ShouldEntitiesFall% = True
Global PlayerFallingPickDistance# = 10.0

Global Save_MSG$ = ""
Global Save_MSG_Timer# = 0.0
Global Save_MSG_Y# = 0.0

Global MTF_CameraCheckTimer# = 0.0
Global MTF_CameraCheckDetected% = False
;player stats -------------------------------------------------------------------------------------------------------
Global KillTimer#, KillAnim%, FallTimer#, DeathTimer#
Global Sanity#, ForceMove#, ForceAngle#
Global RestoreSanity%

Global Playable% = True

Global BLINKFREQ#
Global BlinkTimer#, EyeIrritation#, EyeStuck#, BlinkEffect# = 1.0, BlinkEffectTimer#

Global Stamina#, StaminaEffect#=1.0, StaminaEffectTimer#

Global CameraShakeTimer#, Vomit%, VomitTimer#, Regurgitate%

Global SCP1025state#[6]

Global HeartBeatRate#, HeartBeatTimer#, HeartBeatVolume#

Global WearingGasMask%, WearingHazmat%, WearingVest%, Wearing714%, WearingNightVision%
Global NVTimer#

Global SuperMan%, SuperManTimer#

Global Injuries#, Bloodloss#, Infect#, HealTimer#

Global RefinedItems%
;player coordinates, angle, speed, movement etc ---------------------------------------------------------------------
Global DropSpeed#, HeadDropSpeed#, CurrSpeed#
Global user_camera_pitch#, side#
Global Crouch%, CrouchState#

Global PlayerZone%, PlayerRoom.Rooms

Global GrabbedEntity%

Global InvertMouse% = 0
Global MouseHit1%, MouseDown1%, MouseHit2%, DoubleClick%, LastMouseHit1%, MouseUp1%

Global GodMode%, NoClip%, NoClipSpeed# = 2.0

Global CoffinDistance# = 100.0

Global PlayerSoundVolume#
;camera/lighting effects (blur, camera shake, etc)-------------------------------------------------------------------
Global Shake#

Global ExplosionTimer#, ExplosionSFX%

Global LightsOn% = True

Global SoundTransmission%
;misc ---------------------------------------------------------------------------------------------------------------

Global MTFtimer#, MTFrooms.Rooms[10], MTFroomState%[10]

Dim RadioState#(10)
Dim RadioState3%(10)
Dim RadioState4%(9)
Dim RadioCHN%(8)

Dim OldAiPics%(5)

Global PlayTime%
Global ConsoleFlush%
Global ConsoleFlushSnd% = 0, ConsoleMusFlush% = 0, ConsoleMusPlay% = 0

Global InfiniteStamina% = False
Global NVBlink%
Global IsNVGBlinking% = False

;---------------------------------------------------------------------------------------------------

Global DebugHUD%

Global BlurVolume#, BlurTimer#

Global LightBlink#, LightFlash#

Global BumpEnabled% = 0
Global HUDenabled% = 0

Global Camera%, CameraShake#, CurrCameraZoom#

Global Brightness% = 0
Global CameraFogNear# = 0.5
Global CameraFogFar# = 6

Global StoredCameraFogFar# = CameraFogFar

Global MouseSens# = 0

Global EnableVRam% = 0

Dim LightSpriteTex(10)

;[End block]

Const HIT_MAP% = 1, HIT_PLAYER% = 2, HIT_ITEM% = 3, HIT_APACHE% = 4, HIT_178% = 5, HIT_DEAD% = 6, HIT_BULLET% = 7, HIT_GRENADE% = 8, HIT_INVISIBLEWALL% = 9, HIT_BULLETHITBOX% = 10, HIT_DOOR% = 11, HIT_OLDMAN% = 12

Dim OpenDoorSFX%(3,3), CloseDoorSFX%(3,3)

Global KeyCardSFX1 
Global KeyCardSFX2 
Global ButtonSFX2 
Global ScannerSFX1
Global ScannerSFX2 

Global OpenDoorFastSFX
Global CautionSFX% 

Global NuclearSirenSFX%

Global CameraSFX  

Global StoneDragSFX% 

Global GunshotSFX% 
Global Gunshot2SFX% 
Global Gunshot3SFX% 
Global BullethitSFX% 

Global TeslaIdleSFX 
Global TeslaActivateSFX 
Global TeslaPowerUpSFX 

Global MagnetUpSFX%, MagnetDownSFX
Global FemurBreakerSFX%
Global EndBreathCHN%
Global EndBreathSFX%

Dim DecaySFX%(5)

Global BurstSFX 

Dim RustleSFX%(3)

Global Use914SFX%
Global Death914SFX% 

Dim DripSFX%(4)

Global LeverSFX%, LightSFX% 
Global ButtGhostSFX% 

Dim RadioSFX(5,10) 

Global RadioSquelch 
Global RadioStatic 
Global RadioBuzz 

Global ElevatorBeepSFX, ElevatorMoveSFX  

Dim PickSFX%(10)

Global AmbientSFXCHN%, CurrAmbientSFX%
Dim AmbientSFXAmount(6)
;0 = light containment, 1 = heavy containment, 2 = entrance
AmbientSFXAmount(0)=8 : AmbientSFXAmount(1)=11 : AmbientSFXAmount(2)=12
;3 = general, 4 = pre-breach
AmbientSFXAmount(3)=15 : AmbientSFXAmount(4)=5
;5 = forest
AmbientSFXAmount(5)=10

Dim AmbientSFX%(6, 15)

Dim OldManSFX%(8)

Dim Scp173SFX%(3)

Dim HorrorSFX%(20)

Dim IntroSFX%(20)

;IntroSFX(13) = LoadSound_Strict("SFX\intro\shoot1.ogg")
;IntroSFX(14) = LoadSound_Strict("SFX\intro\shoot2.ogg")


Dim AlarmSFX%(5)

Dim CommotionState%(25)

Global HeartBeatSFX 

Global VomitSFX%

Dim BreathSFX(2,5)
Global BreathCHN%


Dim NeckSnapSFX(3)

Dim DamageSFX%(9)

Dim MTFSFX%(8)

Dim CoughSFX%(3)
Global CoughCHN%, VomitCHN%
Global DoorOpen079SFX%, DoorClose079SFX%

Global MachineSFX% 
Global ApacheSFX
Global CurrStepSFX
Global Triggered096SFX
Dim StepSFX%(5, 2, 8) ;(normal/metal, walk/run, id)

Dim Step2SFX(6)

Global NTF_1499PrevX#
Global NTF_1499PrevY#
Global NTF_1499PrevZ#
Global NTF_1499PrevRoom.Rooms
Global NTF_1499X#
Global NTF_1499Y#
Global NTF_1499Z#
Global NTF_1499Sky%

Global OptionsMenu% = 0
Global QuitMSG% = 0

Global InFacility% = True

Global PrevMusicVolume# = MusicVolume#
Global PrevSFXVolume# = SFXVolume#
Global DeafPlayer% = False
Global DeafTimer# = 0.0

Global IsZombie% = False

Global room2gw_brokendoor% = False
Global room2gw_x# = 0.0
Global room2gw_z# = 0.0

Global Menu_TestIMG
Global menuroomscale# = 8.0 / 2048.0

Global NavBG = CreateImage(GraphicWidth,GraphicHeight)

Global LightConeModel

Global ParticleEffect[10]

Const MaxDTextures=11
Global DTextures[MaxDTextures]

Global NPC049OBJ, NPC0492OBJ
Global ClerkOBJ

Global IntercomStreamCHN%

Global ForestNPC,ForestNPCTex,ForestNPCData#[3]

Global CurrMenu_TestIMG$ = ""

Global ParticleAmount% = 0

; ------------------------------------------------------------------------------ Variables
Include "Source Code\Map\NPCs.bb"

Function PlaySound2%(SoundHandle%, cam%, entity%, range# = 10, volume# = 1.0)
	If entity = 0 Then Return
	range# = Max(range, 1.0)
	Local soundchn% = 0
	
	If volume > 0 Then 
		Local dist# = EntityDistance(cam, entity) / range#
		If 1 - dist# > 0 And 1 - dist# < 1
			Local panvalue# = Sin(-DeltaYaw(cam,entity))
			soundchn% = PlaySound_Strict (SoundHandle)
			
			ChannelVolume(soundchn, volume# * (1 - dist#)*SFXVolume#)
			ChannelPan(soundchn, panvalue)			
		EndIf
	EndIf
	
	Return soundchn
End Function

Function LoopSound2%(SoundHandle%, Chn%, cam%, entity%, range# = 10, volume# = 1.0)
	range# = Max(range,1.0)
	
	If volume>0 Then
		
		Local dist# = EntityDistance(cam, entity) / range#
		;If 1 - dist# > 0 And 1 - dist# < 1 Then
			
			Local panvalue# = Sin(-DeltaYaw(cam,entity))
			
			If Chn = 0 Then
				Chn% = PlaySound_Strict (SoundHandle)
			Else
				If (Not ChannelPlaying(Chn)) Then Chn% = PlaySound_Strict (SoundHandle)
			EndIf
			
			ChannelVolume(Chn, volume# * (1 - dist#)*SFXVolume#)
			ChannelPan(Chn, panvalue)
		;EndIf
	Else
		If Chn <> 0 Then
			ChannelVolume (Chn, 0)
		EndIf 
	EndIf
	
	Return Chn
End Function
Function LoopSound3%(SoundHandle%, Chn%, cam%, entity%, range# = 10, volume# = 1.0)
	range# = Max(range,1.0)
	
	If volume>0 Then
		
		Local dist# = EntityDistance(cam, entity) / range#
		
		If Chn = 0 Then
			Chn% = PlaySound_Strict (SoundHandle)
		Else
			If (Not ChannelPlaying(Chn)) Then Chn% = PlaySound_Strict (SoundHandle)
		EndIf
		If 1 - dist# > 0 And 1 - dist# < 1 Then
			Local panvalue# = Sin(-DeltaYaw(cam,entity))
			
			ChannelVolume(Chn, volume# * (1 - dist#)*SFXVolume#)
			ChannelPan(Chn, panvalue)
		Else
			ChannelVolume(Chn, 0)
		EndIf
	Else
		If Chn <> 0 Then
			ChannelVolume (Chn, 0)
		EndIf 
	EndIf
	
	Return Chn
End Function
;--------------------------------------- decals -------------------------------------------------------

Type Decals
	Field obj%
	Field SizeChange#, Size#, MaxSize#
	Field AlphaChange#, Alpha#
	Field blendmode%
	Field fx%
	Field ID%
	Field Timer#
	
	Field lifetime#
	
	Field x#, y#, z#
	Field pitch#, yaw#, roll#
End Type

Function CreateDecal.Decals(id%, x#, y#, z#, pitch#, yaw#, roll#)
	Local d.Decals = New Decals
	
	d\x = x
	d\y = y
	d\z = z
	d\pitch = pitch
	d\yaw = yaw
	d\roll = roll
	
	d\MaxSize = 1.0
	
	d\Alpha = 1.0
	d\Size = 1.0
	d\obj = CreateSprite()
	d\blendmode = 1
	
	EntityTexture(d\obj, DecalTextures(id))
	EntityFX(d\obj, 0)
	SpriteViewMode(d\obj, 2)
	PositionEntity(d\obj, x, y, z)
	RotateEntity(d\obj, pitch, yaw, roll)
	
	d\ID = id
	
	If DecalTextures(id) = 0 Or d\obj = 0 Then Return Null
	
	Return d
End Function

Function UpdateDecals()
	Local d.Decals
	For d.Decals = Each Decals
		If d\SizeChange <> 0 Then
			d\Size=d\Size + d\SizeChange * FPSfactor
			ScaleSprite(d\obj, d\Size, d\Size)
			
			Select d\ID
				Case 0
					If d\Timer <= 0 Then
						Local angle# = Rand(360)
						Local temp# = Rnd(d\Size)
						Local d2.Decals = CreateDecal(1, EntityX(d\obj) + Cos(angle) * temp, EntityY(d\obj) - 0.0005, EntityZ(d\obj) + Sin(angle) * temp, EntityPitch(d\obj), Rnd(360), EntityRoll(d\obj))
						d2\Size = Rnd(0.1, 0.5) : ScaleSprite(d2\obj, d2\Size, d2\Size)
						PlaySound2(DecaySFX(Rand(1, 3)), Camera, d2\obj, 10.0, Rnd(0.1, 0.5))
						;d\Timer = d\Timer + Rand(50,150)
						d\Timer = Rand(50, 100)
					Else
						d\Timer= d\Timer-FPSfactor
					End If
				;Case 6
				;	EntityBlend d\obj, 2
			End Select
			
			If d\Size >= d\MaxSize Then d\SizeChange = 0 : d\Size = d\MaxSize
		End If
		
		If d\AlphaChange <> 0 Then
			d\Alpha = Min(d\Alpha + FPSfactor * d\AlphaChange, 1.0)
			EntityAlpha(d\obj, d\Alpha)
		End If
		
		If d\lifetime > 0 Then
			d\lifetime=Max(d\lifetime-FPSfactor,5)
		EndIf
		If EntityDistance(d\obj, Collider) > HideDistance Then 
			HideEntity d\obj
		Else
			ShowEntity d\obj
		EndIf
		If d\Size <= 0 Or d\Alpha <= 0 Or d\lifetime=5.0  Then
			FreeEntity(d\obj)
			Delete d
		End If
	Next
End Function
; -----------------------------------------------------
; ------------------------------------------------------- doors -------------------------------
Type Doors
	Field obj%, obj2%, frameobj%, buttons%[2]
	Field locked%, open%, angle%, openstate#, fastopen%
	Field dir%
	Field timer%, timerstate#
	Field KeyCard%
	Field room.Rooms
	
	Field DisableWaypoint%
	
	Field dist#
	
	Field SoundCHN%
	
	Field Code$
	
	Field ID%
	
	Field Level%
	Field LevelDest%
	
	Field AutoClose%
	
	Field LinkedDoor.Doors
	
	Field IsElevatorDoor% = False
	
	Field MTFClose% = True
	Field NPCCalledElevator% = False
	
	Field DoorHitOBJ%
	Field ReadyToWrite
	Field liftchn, liftstate#
	Field liftparent.Doors, liftid, liftobj, liftmaindoor.Doors, TriggeredElevatorButton
	Field ChangedKeycard%, ChangedCode%
End Type 
Global MP_Door.Doors[MAX_DOORS]
Dim BigDoorOBJ(2), HeavyDoorObj(2)
Dim OBJTunnel(7)
Function CreateDoor.Doors(lvl, x#, y#, z#, angle#, room.Rooms, dopen% = False,  big% = False, keycard% = False, code$="", useCollisionMesh% = False)
	Local d.Doors, parent, i%
	If room <> Null Then parent = room\obj
	
	Local d2.Doors
	
	d.Doors = New Doors
	If big=1 Then
		d\obj = CopyEntity(BigDoorOBJ(0))
		ScaleEntity(d\obj, 55 * RoomScale, 55 * RoomScale, 55 * RoomScale)
		d\obj2 = CopyEntity(BigDoorOBJ(1))
		ScaleEntity(d\obj2, 55 * RoomScale, 55 * RoomScale, 55 * RoomScale)
		
		d\frameobj = CopyEntity(DoorColl)	;CopyMesh				
		ScaleEntity(d\frameobj, RoomScale, RoomScale, RoomScale)
		EntityType d\frameobj, HIT_DOOR
		EntityAlpha d\frameobj, 0.0
	ElseIf big=2 Then
		d\obj = CopyEntity(HeavyDoorObj(0))
		ScaleEntity(d\obj, RoomScale, RoomScale, RoomScale)
		d\obj2 = CopyEntity(HeavyDoorObj(1))
		ScaleEntity(d\obj2, RoomScale, RoomScale, RoomScale)
		
		d\frameobj = CopyEntity(DoorFrameOBJ)
	ElseIf big=3 Then
		For d2 = Each Doors
			If d2 <> d And d2\dir = 3 Then
				d\obj = CopyEntity(d2\obj)
				d\obj2 = CopyEntity(d2\obj2)
				ScaleEntity d\obj, RoomScale, RoomScale, RoomScale
				ScaleEntity d\obj2, RoomScale, RoomScale, RoomScale
				Exit
			EndIf
		Next
		If d\obj=0 Then
			d\obj = LoadMesh_Strict("GFX\map\elevatordoor.b3d")
			d\obj2 = CopyEntity(d\obj)
			ScaleEntity d\obj, RoomScale, RoomScale, RoomScale
			ScaleEntity d\obj2, RoomScale, RoomScale, RoomScale
		EndIf
		d\frameobj = CopyEntity(DoorFrameOBJ)
	Else
		d\obj = CopyEntity(DoorOBJ)
		ScaleEntity(d\obj, (204.0 * RoomScale) / MeshWidth(d\obj), 312.0 * RoomScale / MeshHeight(d\obj), 16.0 * RoomScale / MeshDepth(d\obj))
		
		d\frameobj = CopyEntity(DoorFrameOBJ)
		d\obj2 = CopyEntity(DoorOBJ)
		
		ScaleEntity(d\obj2, (204.0 * RoomScale) / MeshWidth(d\obj), 312.0 * RoomScale / MeshHeight(d\obj), 16.0 * RoomScale / MeshDepth(d\obj))
		;entityType d\obj2, HIT_MAP
	End If
	
	;scaleentity(d\obj, 0.1, 0.1, 0.1)
	PositionEntity d\frameobj, x, y, z	
	ScaleEntity(d\frameobj, (8.0 / 2048.0), (8.0 / 2048.0), (8.0 / 2048.0))
	EntityPickMode d\frameobj,2
	EntityType d\obj, HIT_DOOR
	EntityType d\obj2, HIT_DOOR
	
	d\ID = FindFreeDoorID()
	;DoorTempID = DoorTempID+1
	MP_Door[d\ID] = d
	d\KeyCard = keycard
	d\Code = code
	
	d\Level = lvl
	d\LevelDest = 66
	
	For i% = 0 To 1
		If code <> "" Then 
			d\buttons[i]= CopyEntity(ButtonCodeOBJ)
			EntityFX(d\buttons[i], 1)
		Else
			If keycard>0 Then
				d\buttons[i]= CopyEntity(ButtonKeyOBJ)
			ElseIf keycard<0
				d\buttons[i]= CopyEntity(ButtonScannerOBJ)	
			Else
				d\buttons[i] = CopyEntity(ButtonOBJ)
			End If
		EndIf
		
		ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
	Next
	
	If big=1 Then
		PositionEntity d\buttons[0], x - 432.0 * RoomScale, y + 0.7, z + 192.0 * RoomScale
		PositionEntity d\buttons[1], x + 432.0 * RoomScale, y + 0.7, z - 192.0 * RoomScale
		RotateEntity d\buttons[0], 0, 90, 0
		RotateEntity d\buttons[1], 0, 270, 0
	Else
		PositionEntity d\buttons[0], x + 0.6, y + 0.7, z - 0.1
		PositionEntity d\buttons[1], x - 0.6, y + 0.7, z + 0.1
		RotateEntity d\buttons[1], 0, 180, 0		
	End If
	EntityParent(d\buttons[0], d\frameobj)
	EntityParent(d\buttons[1], d\frameobj)
	
	PositionEntity d\obj, x, y, z
	
	RotateEntity d\obj, 0, angle, 0
	RotateEntity d\frameobj, 0, angle, 0
	
	If d\obj2 <> 0 Then
		PositionEntity d\obj2, x, y, z
		If big=1 Then
			RotateEntity(d\obj2, 0, angle, 0)
		Else
			RotateEntity(d\obj2, 0, angle + 180, 0)
		EndIf
		EntityParent(d\obj2, parent)
	EndIf
	
	EntityParent(d\frameobj, parent)
	EntityParent(d\obj, parent)
	
	d\angle = angle
	d\open = dopen		
	
	EntityPickMode(d\obj, 2)
	If d\obj2 <> 0 Then
		EntityPickMode(d\obj2, 2)
	EndIf
	
	EntityPickMode d\frameobj,2
	
	If d\open And big = False And Rand(8) = 1 Then d\AutoClose = True
	d\dir=big
	d\room=room
	
	d\MTFClose = True
	
	If useCollisionMesh Then
		For d2.Doors = Each Doors
			If d2 <> d Then
				If d2\DoorHitOBJ <> 0 Then
					d\DoorHitOBJ = CopyEntity(d2\DoorHitOBJ,d\frameobj)
					EntityAlpha d\DoorHitOBJ,0.0
					EntityFX d\DoorHitOBJ,1
					EntityType d\DoorHitOBJ,HIT_DOOR
					EntityColor d\DoorHitOBJ,255,0,0
					HideEntity d\DoorHitOBJ
					Exit
				EndIf
			EndIf
		Next
		If d\DoorHitOBJ=0 Then
			d\DoorHitOBJ = LoadMesh_Strict("GFX\doorhit.b3d",d\frameobj)
			EntityAlpha d\DoorHitOBJ,0.0
			EntityFX d\DoorHitOBJ,1
			EntityType d\DoorHitOBJ,HIT_DOOR
			EntityColor d\DoorHitOBJ,255,0,0
			HideEntity d\DoorHitOBJ
		EndIf
	EndIf
	
	If allowroomdoorsinit.rooms <> Null Then
		d\room = allowroomdoorsinit
	EndIf
	
	Return d
	
End Function

Function CreateButton(x#,y#,z#, pitch#,yaw#,roll#=0)
	Local obj = CopyEntity(ButtonOBJ)	
	
	ScaleEntity(obj, 0.03, 0.03, 0.03)
	
	PositionEntity obj, x,y,z
	RotateEntity obj, pitch,yaw,roll

	Return obj
End Function
Function UpdateDoors()

	For d.Doors = Each Doors
		If (Not d\room\IsRoomShowed) And (Not d\IsElevatorDoor) Then
			If d\obj <> 0 Then HideEntity d\obj
			If d\frameobj <> 0 Then HideEntity d\frameobj
			If d\obj2 <> 0 Then HideEntity d\obj2
			If d\buttons[0] <> 0 Then HideEntity d\buttons[0]
			If d\buttons[1] <> 0 Then HideEntity d\buttons[1]
		Else
			If d\room\IsRoomShowed Then
				If d\obj <> 0 Then ShowEntity d\obj
				If d\frameobj <> 0 Then ShowEntity d\frameobj
				If d\obj2 <> 0 Then ShowEntity d\obj2
				If d\buttons[0] <> 0 Then ShowEntity d\buttons[0]
				If d\buttons[1] <> 0 Then ShowEntity d\buttons[1]
			Else
				If d\obj <> 0 Then HideEntity d\obj
				If d\frameobj <> 0 Then HideEntity d\frameobj
				If d\obj2 <> 0 Then HideEntity d\obj2
				If d\buttons[0] <> 0 Then HideEntity d\buttons[0]
				If d\buttons[1] <> 0 Then HideEntity d\buttons[1]
			EndIf
			
			If d\open Then
				If d\openstate < 180 Then
					Select d\dir
						Case 0
							d\openstate = Min(180, d\openstate + FPSfactor * 2 * (d\fastopen+1))
							MoveEntity(d\obj, Sin(d\openstate) * (d\fastopen*2+1) * FPSfactor / 80.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate)* (d\fastopen+1) * FPSfactor / 80.0, 0, 0)		
						Case 1
							d\openstate = Min(180, d\openstate + FPSfactor * 0.8)
							MoveEntity(d\obj, Sin(d\openstate) * FPSfactor / 180.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, -Sin(d\openstate) * FPSfactor / 180.0, 0, 0)
						Case 2
							d\openstate = Min(180, d\openstate + FPSfactor * 2 * (d\fastopen+1))
							MoveEntity(d\obj, Sin(d\openstate) * (d\fastopen+1) * FPSfactor / 85.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate)* (d\fastopen*2+1) * FPSfactor / 120.0, 0, 0)
						Case 3
							d\openstate = Min(180, d\openstate + FPSfactor * 2 * (d\fastopen+1))
							MoveEntity(d\obj, Sin(d\openstate) * (d\fastopen*2+1) * FPSfactor / 162.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate)* (d\fastopen*2+1) * FPSfactor / 162.0, 0, 0)
						Case 4 ;Used for 914 only
							d\openstate = Min(180, d\openstate + FPSfactor * 1.4)
							MoveEntity(d\obj, Sin(d\openstate) * FPSfactor / 114.0, 0, 0)
					End Select
				Else
					d\fastopen = 0
					ResetEntity(d\obj)
					If d\obj2 <> 0 Then ResetEntity(d\obj2)
					If d\timerstate > 0 Then
						d\timerstate = Max(0, d\timerstate - FPSfactor)
						If d\timerstate + FPSfactor > 110 And d\timerstate <= 110 Then d\SoundCHN = PlaySound2(CautionSFX, Camera, d\obj)
						;If d\timerstate = 0 Then d\open = (Not d\open) : PlaySound2(CloseDoorSFX(Min(d\dir,1),Rand(0, 2)), Camera, d\obj)
						Local sound%
						If d\dir = 1 Then sound% = Rand(0, 1) Else sound% = Rand(0, 2)
						If d\timerstate = 0 Then d\open = (Not d\open) : d\SoundCHN = PlaySound2(CloseDoorSFX(d\dir,sound%), Camera, d\obj)
					EndIf
					If d\AutoClose And RemoteDoorOn = True Then
						If EntityDistance(Camera, d\obj) < 2.1 Then
							If (Not Wearing714) Then PlaySound_Strict HorrorSFX(7)
							d\open = False : d\SoundCHN = PlaySound2(CloseDoorSFX(Min(d\dir,1), Rand(0, 2)), Camera, d\obj) : d\AutoClose = False
						EndIf
					EndIf				
				EndIf
			Else
				If d\openstate > 0 Then
					Select d\dir
						Case 0
							d\openstate = Max(0, d\openstate - FPSfactor * 2 * (d\fastopen+1))
							MoveEntity(d\obj, Sin(d\openstate) * -FPSfactor * (d\fastopen+1) / 80.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate) * (d\fastopen+1) * -FPSfactor / 80.0, 0, 0)	
						Case 1
							d\openstate = Max(0, d\openstate - FPSfactor*0.8)
							MoveEntity(d\obj, Sin(d\openstate) * -FPSfactor / 180.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate) * FPSfactor / 180.0, 0, 0)
							If d\openstate < 15 And d\openstate+FPSfactor => 15

							EndIf
						Case 2
							d\openstate = Max(0, d\openstate - FPSfactor * 2 * (d\fastopen+1))
							MoveEntity(d\obj, Sin(d\openstate) * -FPSfactor * (d\fastopen+1) / 85.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate) * (d\fastopen+1) * -FPSfactor / 120.0, 0, 0)
						Case 3
							d\openstate = Max(0, d\openstate - FPSfactor * 2 * (d\fastopen+1))
							MoveEntity(d\obj, Sin(d\openstate) * -FPSfactor * (d\fastopen+1) / 162.0, 0, 0)
							If d\obj2 <> 0 Then MoveEntity(d\obj2, Sin(d\openstate) * (d\fastopen+1) * -FPSfactor / 162.0, 0, 0)
						Case 4 ;Used for 914 only
							d\openstate = Min(180, d\openstate - FPSfactor * 1.4)
							MoveEntity(d\obj, Sin(d\openstate) * -FPSfactor / 114.0, 0, 0)
					End Select
					
					If d\angle = 0 Or d\angle=180 Then
						If Abs(EntityZ(d\frameobj, True)-EntityZ(Collider))<0.15 Then
							If Abs(EntityX(d\frameobj, True)-EntityX(Collider))<0.7*(d\dir*2+1) Then
								z# = CurveValue(EntityZ(d\frameobj,True)+0.15*Sgn(EntityZ(Collider)-EntityZ(d\frameobj, True)), EntityZ(Collider), 5)
								PositionEntity Collider, EntityX(Collider), EntityY(Collider), z
							EndIf
						EndIf
					Else
						If Abs(EntityX(d\frameobj, True)-EntityX(Collider))<0.15 Then	
							If Abs(EntityZ(d\frameobj, True)-EntityZ(Collider))<0.7*(d\dir*2+1) Then
								x# = CurveValue(EntityX(d\frameobj,True)+0.15*Sgn(EntityX(Collider)-EntityX(d\frameobj, True)), EntityX(Collider), 5)
								PositionEntity Collider, x, EntityY(Collider), EntityZ(Collider)
							EndIf
						EndIf
					EndIf
					
					If d\DoorHitOBJ <> 0 Then
						ShowEntity d\DoorHitOBJ
					EndIf
				Else
					d\fastopen = 0
					PositionEntity(d\obj, EntityX(d\frameobj, True), EntityY(d\frameobj, True), EntityZ(d\frameobj, True))
					If d\obj2 <> 0 Then PositionEntity(d\obj2, EntityX(d\frameobj, True), EntityY(d\frameobj, True), EntityZ(d\frameobj, True))
					If d\obj2 <> 0 And d\dir = 0 Then
						MoveEntity(d\obj, 0, 0, 8.0 * RoomScale)
						MoveEntity(d\obj2, 0, 0, 8.0 * RoomScale)
					EndIf
					If d\DoorHitOBJ <> 0 Then
						HideEntity d\DoorHitOBJ
					EndIf
				EndIf
			EndIf
			If d\locked Then
				If GetEntityType(d\obj) = HIT_DOOR Then
					If d\frameobj <> 0 Then 
						If GetEntityType(d\frameobj) = HIT_DOOR Then EntityType d\frameobj, HIT_MAP
					EndIf
					If d\doorhitobj <> 0 Then
						If GetEntityType(d\DoorHitOBJ) = HIT_DOOR Then EntityType d\DoorHitOBJ, HIT_MAP
					EndIf
					EntityType d\obj, HIT_MAP
					If d\obj2 <> 0 Then EntityType d\obj2, HIT_MAP
				EndIf
			Else
				If GetEntityType(d\obj) = HIT_MAP Then
					If d\frameobj <> 0 Then 
						If GetEntityType(d\frameobj) = HIT_MAP Then EntityType d\frameobj, HIT_DOOR
					EndIf
					If d\doorhitobj <> 0 Then
						If GetEntityType(d\DoorHitOBJ) = HIT_MAP Then EntityType d\DoorHitOBJ, HIT_DOOR
					EndIf
					EntityType d\obj, HIT_DOOR
					If d\obj2 <> 0 Then EntityType d\obj2, HIT_DOOR
				EndIf
			EndIf
		EndIf
	Next
End Function
Function UseDoor(d.Doors, showmsg%=True, playsfx%=True, give = 0, itemid = 0)
	Local itt.itemtemplates
	Local temp% = 0
	If IsValidPlayer(give) Then
		If Player[give] <> Null Then
			If itemid > 0 And itemid < MAX_ITEMS Then
				If M_Item[itemid] <> Null Then
					If M_Item[itemid]\picker = give Then
						itt = M_Item[itemid]\ItemTemplate
					EndIf	
				EndIf
			EndIf
		EndIf
	EndIf
	If d\KeyCard > 0 Then
		If itt = Null Then
			If showmsg = True Then
				If (Instr(Msg,"The keycard")=0 And Instr(Msg,"A keycard with")=0) Or (MsgTimer<70*3) Then
					Msg = "A keycard is required to operate this door."
					MsgTimer = 70 * 7
				EndIf
			EndIf
			Return
		Else
			Select itt\tempname
				Case "key1"
					temp = 1
				Case "key2"
					temp = 2
				Case "key3"
					temp = 3
				Case "key4"
					temp = 4
				Case "key5"
					temp = 5
				Case "key6"
					temp = 6
				Default 
					temp = -1
			End Select
			itt = Null
			If temp =-1 Then 
				If showmsg = True Then
					If (Instr(Msg,"The keycard")=0 And Instr(Msg,"A keycard with")=0) Or (MsgTimer<70*3) Then
						Msg = "A keycard is required to operate this door."
						MsgTimer = 70 * 7
					EndIf
				EndIf
				Return				
			ElseIf temp >= d\KeyCard 
				If showmsg = True Then
					If d\locked Then
						Msg = "The keycard was inserted into the slot but nothing happened."
						MsgTimer = 70 * 7
						Return
					Else
						Msg = "The keycard was inserted into the slot."
						MsgTimer = 70 * 7	
					EndIf
				EndIf
			Else
				If showmsg = True Then 				
					If d\locked Then
						Msg = "The keycard was inserted into the slot but nothing happened."
					Else
						Msg = "A keycard with security clearance "+d\KeyCard+" or higher is required to operate this door."
					EndIf
					MsgTimer = 70 * 7				
				EndIf
				Return
			End If
		EndIf	
	ElseIf d\KeyCard < 0
		;I can't find any way to produce short circuited boolean expressions so work around this by using a temporary variable - risingstar64
		If itt <> Null Then
			temp = (itt\tempname = "hand" And d\KeyCard=-1) Or (itt\tempname = "hand2" And d\KeyCard=-2)
		EndIf
		If temp <> 0 Then
			If (Instr(Msg,"You placed your")=0) Or (MsgTimer < 70*3) Then
				Msg = "You place the palm of the hand onto the scanner. The scanner reads: "+Chr(34)+"DNA verified. Access granted."+Chr(34)
			EndIf
			MsgTimer = 70 * 10
			itt = Null
		Else
			If showmsg = True Then 
				Msg = "You placed your palm onto the scanner. The scanner reads: "+Chr(34)+"DNA does not match known sample. Access denied."+Chr(34)
				MsgTimer = 70 * 10
			EndIf
			Return	
		EndIf
	Else
		If d\locked Then
			If showmsg = True Then 
				If Not (d\IsElevatorDoor>0) Then
					If PlayerRoom\RoomTemplate\Name <> "room2elevator" Then
                        If d\open Then
                            Msg = "You pushed the button but nothing happened."
                        Else    
                            Msg = "The door appears to be locked."
                        EndIf    
                    Else
                        Msg = "The elevator appears to be broken."
                    EndIf
					MsgTimer = 70 * 5
				Else
					If d\IsElevatorDoor = 1 Then
						Msg = "You called the elevator."
						MsgTimer = 70 * 5
					ElseIf d\IsElevatorDoor = 3 Then
						Msg = "The elevator is already on this floor."
						MsgTimer = 70 * 5
					ElseIf (Msg<>"You called the elevator.")
						If (Msg="You already called the elevator.") Or (MsgTimer<70*3)	
							Select Rand(10)
								Case 1
									Msg = "Stop spamming the button."
									MsgTimer = 70 * 7
								Case 2
									Msg = "Pressing it harder does not make the elevator come faster."
									MsgTimer = 70 * 7
								Case 3
									Msg = "If you continue pressing this button I will generate a Memory Access Violation."
									MsgTimer = 70 * 7
								Default
									Msg = "You already called the elevator."
									MsgTimer = 70 * 7
							End Select
						EndIf
					Else
						Msg = "You already called the elevator."
						MsgTimer = 70 * 7
					EndIf
				EndIf
				
			EndIf
			Return
		EndIf	
	EndIf
	
	d\open = (Not d\open)
	If d\LinkedDoor <> Null Then d\LinkedDoor\open = (Not d\LinkedDoor\open)
	
	Local sound = 0
	;If d\dir = 1 Then sound = 0 Else sound=Rand(0, 2)
	If d\dir = 1 Then sound=Rand(0, 1) Else sound=Rand(0, 2)
	
	If playsfx=True Then
		If d\open Then
			If d\LinkedDoor <> Null Then d\LinkedDoor\timerstate = d\LinkedDoor\timer
			d\timerstate = d\timer
		EndIf
	Else
		If d\open Then
			If d\LinkedDoor <> Null Then d\LinkedDoor\timerstate = d\LinkedDoor\timer
			d\timerstate = d\timer
		EndIf
	EndIf
	;if give = False Or give = -1 Then
	;	udp_WriteByte(M_DOOR)
	;	udp_WriteByte(1)
	;	udp_WriteShort(d\ID)
	;	udp_WriteByte(d\open)
	;	udp_WriteByte(d\locked)
	;	udp_SendMessage()
	;EndIf
End Function
Function RemoveDoor(d.Doors)
	If d\buttons[0] <> 0 Then EntityParent d\buttons[0], 0
	If d\buttons[1] <> 0 Then EntityParent d\buttons[1], 0	
	
	If d\obj <> 0 Then FreeEntity d\obj
	If d\obj2 <> 0 Then FreeEntity d\obj2
	If d\frameobj <> 0 Then FreeEntity d\frameobj
	If d\buttons[0] <> 0 Then FreeEntity d\buttons[0]
	If d\buttons[1] <> 0 Then FreeEntity d\buttons[1]
	MP_Door[d\ID] = Null
	Delete d
End Function
Type MapZones
	Field Transition%[1]
	Field HasCustomForest%
	Field HasCustomMT%
End Type

Global I_Zone.MapZones = New MapZones
Include "Source Code\Map\Particles.bb"
Include "Source Code\Map\MapSystem.bb"
Include "Source Code\Map\Events.bb"
Include "Source Code\Map\Achievements.bb"

; ---------------------------------------------------------------------------
Function InitNewGame()
	;CatchErrors("Uncaught (InitNewGame)")
	ItemsRotateRand = Rand(360)
	Local i%, de.Decals, d.Doors, it.Items, r.Rooms, sc.SecurityCams, e.Events
	LoadNecessaryEntities
	LoadEntities()
	LoadAllSounds()
	If Player[0] = Null Then
		CreatePlayer(0)
		Player[0]\BreachType = MODEL_WAIT
		Player[0]\fake = True
		
		mp_CreatePlayerObject(0)
	EndIf
	
	SeedRnd GenerateSeedNumber(server\randomseed)
	AccessCode = 0
	While True
		For i = 0 To 3
			AccessCode = AccessCode + Rand(1,9)*(10^i)
		Next
		If AccessCode <> 7816 Then Exit
		
		AccessCode = 0
	Wend
	
	If Server\Custommap <> "" Then
		LoadMap(server\custommap)
	Else
		CreateMap(server\randomseed, 3-Server\MapSize)
	EndIf
	InitEvents()
	InitWayPoints()
	
	For r.Rooms = Each Rooms
		If r\RoomTemplate\Name = "start" Then
			PositionEntity (Camera, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
			ResetEntity Camera
		EndIf
		For i = 0 To MaxRoomLights
			If r\Lights[i]<>0 Then EntityParent(r\Lights[i],0)
		Next
		
		If (Not r\RoomTemplate\DisableDecals) Then
			If Rand(4) = 1 Then
				de.Decals = CreateDecal(Rand(2, 3), EntityX(r\obj)+Rnd(- 2,2), 0.003, EntityZ(r\obj)+Rnd(-2,2), 90, Rand(360), 0)
				de\Size = Rnd(0.1, 0.4) : ScaleSprite(de\obj, de\Size, de\Size)
				EntityAlpha(de\obj, Rnd(0.85, 0.95))
			EndIf
			
			If Rand(4) = 1 Then
				de.Decals = CreateDecal(0, EntityX(r\obj)+Rnd(- 2,2), 0.003, EntityZ(r\obj)+Rnd(-2,2), 90, Rand(360), 0)
				de\Size = Rnd(0.5, 0.7) : EntityAlpha(de\obj, 0.7) : de\ID = 1 : ScaleSprite(de\obj, de\Size, de\Size)
				EntityAlpha(de\obj, Rnd(0.7, 0.85))
			EndIf
		EndIf
	Next

	Curr173 = CreateNPC(NPCtype173, 0, -30.0, 0)
	Curr106 = CreateNPC(NPCtypeOldMan, 0, -30.0, 0)
	Curr106\State = 70 * 60 * Rand(12,17)

	Player[0]\x = 99998
	Player[0]\y = 20
	Player[0]\z = 99998
	
	
	For d.Doors = Each Doors
		EntityParent(d\obj, 0)
		If d\obj2 <> 0 Then EntityParent(d\obj2, 0)
		If d\frameobj <> 0 Then EntityParent(d\frameobj, 0)
		If d\buttons[0] <> 0 Then EntityParent(d\buttons[0], 0)
		If d\buttons[1] <> 0 Then EntityParent(d\buttons[1], 0)
		
		If d\obj2 <> 0 And d\dir = 0 Then
			MoveEntity(d\obj, 0, 0, 8.0 * RoomScale)
			MoveEntity(d\obj2, 0, 0, 8.0 * RoomScale)
		EndIf	
	Next
	For it.Items = Each Items
		
		EntityParent(it\collider, 0)
	Next
	
	For sc.SecurityCams= Each SecurityCams
		sc\angle = EntityYaw(sc\obj) + sc\angle
		EntityParent(sc\obj, 0)
	Next
	
	Local rt.RoomTemplates
	For rt.RoomTemplates = Each RoomTemplates
		FreeEntity (rt\obj)
	Next	
	
	Local tw.TempWayPoints
	For tw.TempWayPoints = Each TempWayPoints
		Delete tw
	Next


	For e.Events = Each Events
		If e\EventName = "room2nuke"
			e\EventState = 1
			DebugLog "room2nuke"
		EndIf
		If e\EventName = "room106"
			e\EventState2 = 1
			DebugLog "room106"
		EndIf	
		If e\EventName = "room2sl"
			e\EventState3 = 1
			DebugLog "room2sl"
		EndIf
	Next
	
	For r.Rooms = Each Rooms
		For i = 0 To MaxRoomObjects-1
			r\ObjectStatic[i] = True
		Next
		CountRoomTriggerBox(r)
	Next
	
	BlinkTimer = -10
	BlurTimer = 100
	Stamina = 100

	FreeTextureCache
	
	FlushKeys
	FlushMouse
	
	PrevTime = MilliSecs()
	
	For fr.Forest = Each forest
		HideEntity fr\Forest_Pivot
		For tx% = 0 To gridsize-1
			For ty% = 0 To gridsize-1
				If fr\TileEntities[tx+(ty*gridsize)]<>0 Then
					HideEntity fr\TileEntities[tx+(ty*gridsize)]
				EndIf
			Next
		Next
	Next
	
	;For e.Events = Each Events
	;	if e\EventName = "alarm" Then
	;		Local pv = CreatePivot()
	;		e\NearObject = pv
	;		PositionEntity e\NearObject, EntityX(e\room\Triggerbox[0]), EntityY(e\room\Triggerbox[0]), EntityZ(e\room\Triggerbox[0])
	;		CheckTriggers(e)
	;		FreeEntity pv
	;	EndIf
	;Next
End Function

Function NullMap(playbuttonsfx%=True)
	;CatchErrors("Uncaught (NullGame)")
	Local i%, x%, y%, lvl
	Local itt.ItemTemplates, s.Screens, lt.LightTemplates, d.Doors, m.Materials
	Local wp.WayPoints, twp.TempWayPoints, r.Rooms, it.Items
	sound_onkill()
	For pl.players = Each players
		If pl\obj <> 0 Then
			FreeEntity(pl\obj)
			pl\obj = 0
		EndIf
		If pl\Pivot <> 0 Then 
			FreeEntity(pl\Pivot)
			pl\Pivot = 0
		EndIf
	Next
	Delete Each rockets
	Delete Each bullets
	Delete Each grenades
	For i = 1 To MAX_OBJECTS-1
		If multiplayer_Object[i] <> Null Then Delete multiplayer_Object[i]
	Next
	GameLoad = 0
	HoldingGun = 0
	ClassDObj = 0
	ClerkMP = 0
	FreeParticles()
	ClearTextureCache
	
	DebugHUD = False
	
	UnableToMove% = False
	
	QuickLoadPercent = -1
	QuickLoadPercent_DisplayTimer# = 0
	QuickLoad_CurrEvent = Null
	
	DeathMSG$=""
	
	SelectedMap = ""
	
	UsedConsole = False
	
	DoorTempID = 0
	RoomTempID = 0
	
	GameSaved = 0
	
	HideDistance# = 15.0
	
	For lvl = 0 To 0
		For x = 0 To MapWidth+1
			For y = 0 To MapHeight+1
				MapTemp(x, y) = 0
				MapFound(x, y) = 0
			Next
		Next
	Next
	
	ForceMove = 0.0
	ForceAngle = 0.0	
	Playable = True
	
	CoffinDistance = 100
	
	Contained106 = False
	If Curr173 <> Null Then Curr173\Idle = False
	
	MTFtimer = 0
	
	For i = 0 To MAXACHIEVEMENTS-1
		Achievements(i)=0
	Next
	RefinedItems = 0
	
	ConsoleInput = ""
	ConsoleOpen = False
	
	EyeIrritation = 0
	EyeStuck = 0
	
	ShouldPlay = 0
	
	KillTimer = 0
	FallTimer = 0
	Stamina = 100
	BlurTimer = 0
	SuperMan = False
	SuperManTimer = 0
	Sanity = 0
	RestoreSanity = True
	Crouch = False
	CrouchState = 0.0
	LightVolume = 0.0
	Vomit = False
	VomitTimer = 0.0
	SecondaryLightOn# = True
	PrevSecondaryLightOn# = True
	RemoteDoorOn = True

	
	ClosestButton = 0
	
	For d.Doors = Each Doors
		Delete d
	Next
	
	For lt.LightTemplates = Each LightTemplates
		Delete lt
	Next 
	
	For m.Materials = Each Materials
		Delete m
	Next
	
	For wp.WayPoints = Each WayPoints
		Delete wp
	Next
	
	Delete Each TempScreens
	
	For twp.TempWayPoints = Each TempWayPoints
		Delete twp
	Next	
	
	For r.Rooms = Each Rooms
		Delete r
	Next
	
	For itt.ItemTemplates = Each ItemTemplates
		Delete itt
	Next 
	
	For it.Items = Each Items
		Delete it
	Next
	
	For pr.Props = Each Props
		Delete pr
	Next
	
	For de.decals = Each Decals
		Delete de
	Next
	
	For n.NPCS = Each NPCs
		Delete n
	Next
	Curr173 = Null
	Curr106 = Null
	Curr096 = Null
	For i = 0 To 6
		MTFrooms[i]=Null
	Next
	ForestNPC = 0
	ForestNPCTex = 0
	
	Local e.Events
	
	For sc.securitycams = Each SecurityCams
		Delete sc
	Next
	
	For e.Events = Each Events
		Delete e
	Next
	
	For em.emitters = Each Emitters
		Delete em
	Next	
	
	For p.particles = Each Particles
		Delete p
	Next
	
	For rt.RoomTemplates = Each RoomTemplates
		rt\obj = 0
	Next
	
	NTF_1499PrevX# = 0.0
	NTF_1499PrevY# = 0.0
	NTF_1499PrevZ# = 0.0
	NTF_1499PrevRoom = Null
	NTF_1499X# = 0.0
	NTF_1499Y# = 0.0
	NTF_1499Z# = 0.0
	Wearing1499% = False
	DeleteChunks()
	
	DeleteDevilEmitters()
	
	NoTarget% = False
	
	OptionsMenu% = -1
	QuitMSG% = -1
	AchievementsMenu% = -1
	
	MusicVolume# = PrevMusicVolume
	SFXVolume# = PrevSFXVolume
	DeafPlayer% = False
	DeafTimer# = 0.0
	
	IsZombie% = False
	CurrAchvMSGID = 0
	
	For e.Events = Each Events
		If e\Sound<>0 Then FreeSound_Strict e\Sound
		If e\Sound2<>0 Then FreeSound_Strict e\Sound2
	Next
	Delete Each rockets
	Delete Each bullets
	Delete Each grenades
	Delete Each Doors
	Delete Each LightTemplates
	Delete Each Materials
	Delete Each WayPoints
	Delete Each TempWayPoints
	Delete Each TempScreens
	Delete Each Props
	Delete Each Rooms
	Delete Each ItemTemplates
	Delete Each Items
	Delete Each Props
	Delete Each Decals
	Delete Each NPCs
	Delete Each Events
	Delete Each Screens
	Delete Each SecurityCams
	Delete Each Emitters
	Delete Each Particles
	Delete Each Forest
	
	For rt.RoomTemplates = Each RoomTemplates
		rt\obj = 0
	Next
	
	ClearWorld
	;CatchErrors("NullGame")
End Function
Function LoadMap(file$)
	;CatchErrors("Uncaught (LoadMap)")
	Local f%, x%, y%, name$, angle%, prob#
	Local r.Rooms, rt.RoomTemplates, e.Events
	Local roomamount%,forestpieceamount%,mtpieceamount%,i%
	
	f% = ReadFile(file)
	AddLog("Loading custom map: "+file, 0, False)
	
	Dim MapTemp%(MapWidth+1, MapHeight+1)
	Dim MapFound%(MapWidth+1, MapHeight+1)
	CoffinDistance = 100
	
	For x = 0 To MapWidth+1
		For y = 0 To MapHeight+1
			MapTemp(x,y)=False
			MapFound(x,y)=False
		Next
	Next
	
	If Right(file,6)="cbmap2" Then
		ReadLine(f)
		ReadLine(f)
		I_Zone\Transition[0] = ReadByte(f)
		I_Zone\Transition[1] = ReadByte(f)
		roomamount = ReadInt(f)
		forestpieceamount = ReadInt(f)
		mtpieceamount = ReadInt(f)
		
		If forestpieceamount > 0 Then
			I_Zone\HasCustomForest = True
		EndIf
		If mtpieceamount > 0 Then
			I_Zone\HasCustomMT = True
		EndIf
		
		;Facility rooms
		For i = 0 To roomamount-1
			x = ReadByte(f)
			y = ReadByte(f)
			name$ = Lower(ReadString(f))
			
			angle = ReadByte(f)*90.0
			
			DebugLog x+", "+y+": "+name
			DebugLog "angle: "+angle
			
			For rt.RoomTemplates=Each RoomTemplates
				If Lower(rt\Name) = name Then
					
					r.Rooms = CreateRoom(0, rt\Shape, (MapWidth-x) * 8.0, 0, y * 8.0, name)
					DebugLog "createroom"
					
					r\angle = angle
					If r\angle<>90 And r\angle<>270
						r\angle = r\angle + 180
					EndIf
					r\angle = WrapAngle(r\angle)
					
					TurnEntity(r\obj, 0, r\angle, 0)
					
					MapTemp(MapWidth-x,y)=True
					
					Exit
				EndIf
			Next
			
			name = ReadString(f)
			prob# = ReadFloat(f)
			
			If r<>Null Then
				If prob>0.0 Then
					If Rnd(0.0,1.0)<=prob Then
						e.Events = New Events
						e\EventName = name
						e\room = r
						SetEventVar(e)
						e\EventConst = FindEventConst(e\EventName)
					EndIf
				ElseIf prob = 0.0 And name <> "" Then
					e.Events = New Events
					e\EventName = name
					e\room = r
					SetEventVar(e)
					e\EventConst = FindEventConst(e\EventName)
				EndIf
			EndIf
		Next
		
		Local ForestRoom.Rooms
		For r.Rooms = Each Rooms
			If r\RoomTemplate\Name = "room860" Then
				ForestRoom = r
				Exit
			EndIf
		Next
		
		If ForestRoom<>Null Then
			Local fr.Forest = New Forest
		EndIf
		
		;Forest rooms
		For i = 0 To forestpieceamount-1
			x = ReadByte(f)
			y = ReadByte(f)
			name$ = Lower(ReadString(f))
			
			angle = ReadByte(f)
			
			DebugLog x+", "+y+": "+name
			DebugLog "angle: "+angle
			
			If angle <> 0 And angle <> 2 Then
				angle = angle + 2
			EndIf
			angle = angle + 1
			If angle > 3 Then
				angle = (angle Mod 4)
			EndIf
			
			x = (gridsize-1)-x
			
			If fr<>Null Then
				Select name
					;1,2,3,4 = ROOM1
					;5,6,7,8 = ROOM2
					;9,10,11,12 = ROOM2C
					;13,14,15,16 = ROOM3
					;17,18,19,20 = ROOM4
					;21,22,23,24 = DOORROOM
					Case "scp-860-1 endroom"
						fr\grid[(y*gridsize)+x] = 1+angle
					Case "scp-860-1 path"
						fr\grid[(y*gridsize)+x] = 5+angle
					Case "scp-860-1 corner"
						fr\grid[(y*gridsize)+x] = 9+angle
					Case "scp-860-1 t-shaped path"
						fr\grid[(y*gridsize)+x] = 13+angle
					Case "scp-860-1 4-way path"
						fr\grid[(y*gridsize)+x] = 17+angle
					Case "scp-860-1 door"
						fr\grid[(y*gridsize)+x] = 21+angle
				End Select
				DebugLog "created forest piece "+Chr(34)+name+Chr(34)+" successfully"
			EndIf
		Next
		
		If fr<>Null Then
			ForestRoom\fr=fr
			PlaceForest_MapCreator(ForestRoom\fr,ForestRoom\x,ForestRoom\y+30.0,ForestRoom\z,ForestRoom)
		EndIf
		
		Local MTRoom.Rooms
		For r.Rooms = Each Rooms
			If r\RoomTemplate\Name = "room2tunnel" Then
				MTRoom = r
				Exit
			EndIf
		Next
		
		If MTRoom<>Null Then
			MTRoom\grid = New Grids
		EndIf
		
		;Maintenance tunnels rooms
		For i = 0 To mtpieceamount-1
			x = ReadByte(f)
			y = ReadByte(f)
			name$ = Lower(ReadString(f))
			
			angle = ReadByte(f)
			
			DebugLog x+", "+y+": "+name
			DebugLog "angle: "+angle
			
			If angle<>1 And angle<>3 Then
				angle = angle + 2
			EndIf
			If name = "maintenance tunnel corner" Or name = "maintenance tunnel t-shaped room" Then
				angle = angle + 3
			EndIf
			If angle > 3 Then
				angle = (angle Mod 4)
			EndIf
			
			x = (gridsz-1)-x
			
			If MTRoom<>Null Then
				Select name
					Case "maintenance tunnel endroom"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM1
					Case "maintenance tunnel corridor"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM2
					Case "maintenance tunnel corner"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM2C
					Case "maintenance tunnel t-shaped room"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM3
					Case "maintenance tunnel 4-way room"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM4
					Case "maintenance tunnel elevator"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM4+1
					Case "maintenance tunnel generator room"
						MTRoom\grid\grid[x+(y*gridsz)]=ROOM4+2
				End Select
				MTRoom\grid\angles[x+(y*gridsz)]=angle
				DebugLog "created mtunnel piece "+Chr(34)+name+Chr(34)+" successfully"
			EndIf
		Next
		
		;If MTRoom<>Null Then
		;	PlaceGrid_MapCreator(MTRoom)
		;EndIf
	Else
		I_Zone\Transition[0] = 13
		I_Zone\Transition[1] = 7
		I_Zone\HasCustomForest = False
		I_Zone\HasCustomMT = False
		While Not Eof(f)
			x = ReadByte(f)
			y = ReadByte(f)
			name$ = Lower(ReadString(f))
			
			angle = ReadByte(f)*90.0
			
			DebugLog x+", "+y+": "+name
			DebugLog "angle: "+angle
			
			For rt.RoomTemplates=Each RoomTemplates
				If Lower(rt\Name) = name Then
					
					r.Rooms = CreateRoom(0, rt\Shape, (MapWidth-x) * 8.0, 0, y * 8.0, name)
					DebugLog "createroom"
					
					r\angle = angle
					If r\angle<>90 And r\angle<>270
						r\angle = r\angle + 180
					EndIf
					r\angle = WrapAngle(r\angle)
					
					TurnEntity(r\obj, 0, r\angle, 0)
					
					MapTemp(MapWidth-x,y)=True
					
					Exit
				EndIf
			Next
			
			name = ReadString(f)
			prob# = ReadFloat(f)
			
			If r<>Null Then
				If prob>0.0 Then
					If Rnd(0.0,1.0)<=prob Then
						e.Events = New Events
						e\EventName = name
						e\room = r
						SetEventVar(e)
						e\EventConst = FindEventConst(e\EventName)
					EndIf
				ElseIf prob = 0.0 And name <> "" Then
					e.Events = New Events
					e\EventName = name
					e\room = r
					SetEventVar(e)
					e\EventConst = FindEventConst(e\EventName)
				EndIf
			EndIf
			
		Wend
	EndIf
	
	CloseFile f
	
	Local temp = 0, zone
	Local spacing# = 8.0
	Local shouldSpawnDoor% = False
	Local d.Doors
	For y = MapHeight To 0 Step -1
		
		If y<I_Zone\Transition[1] Then
			zone=3
		ElseIf y>=I_Zone\Transition[1] And y<I_Zone\Transition[0] Then
			zone=2
		Else
			zone=1
		EndIf
		
		For x = MapWidth To 0 Step -1
			If MapTemp(x,y) > 0 Then
				If zone = 2 Then temp=2 Else temp=0
                
                For r.Rooms = Each Rooms
					r\angle = WrapAngle(r\angle)
					If Int(r\x/8.0)=x And Int(r\z/8.0)=y Then
						shouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								If r\angle=90
									shouldSpawnDoor = True
								EndIf
							Case ROOM2
								If r\angle=90 Or r\angle=270
									shouldSpawnDoor = True
								EndIf
							Case ROOM2C
								If r\angle=0 Or r\angle=90
									shouldSpawnDoor = True
								EndIf
							Case ROOM3
								If r\angle=0 Or r\angle=180 Or r\angle=90
									shouldSpawnDoor = True
								EndIf
							Default
								shouldSpawnDoor = True
						End Select
						If shouldSpawnDoor
							If (x+1)<(MapWidth+1)
								If MapTemp(x + 1, y) > 0 Then
									d.Doors = CreateDoor(r\zone, Float(x) * spacing + spacing / 2.0, 0, Float(y) * spacing, 90, r, Max(Rand(-3, 1), 0), temp)
									r\AdjDoor[0] = d
								EndIf
							EndIf
						EndIf
						
						shouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								If r\angle=180
									shouldSpawnDoor = True
								EndIf
							Case ROOM2
								If r\angle=0 Or r\angle=180
									shouldSpawnDoor = True
								EndIf
							Case ROOM2C
								If r\angle=180 Or r\angle=90
									shouldSpawnDoor = True
								EndIf
							Case ROOM3
								If r\angle=180 Or r\angle=90 Or r\angle=270
									shouldSpawnDoor = True
								EndIf
							Default
								shouldSpawnDoor = True
						End Select
						If shouldSpawnDoor
							If (y+1)<(MapHeight+1)
								If MapTemp(x, y + 1) > 0 Then
									d.Doors = CreateDoor(r\zone, Float(x) * spacing, 0, Float(y) * spacing + spacing / 2.0, 0, r, Max(Rand(-3, 1), 0), temp)
									r\AdjDoor[3] = d
								EndIf
							EndIf
						EndIf
						
						Exit
					EndIf
                Next
                
			End If
			
		Next
	Next
	
	;r = CreateRoom(0, ROOM1, 8, 0, (MapHeight-1) * 8, "173")
	;r = CreateRoom(0, ROOM1, (MapWidth-1) * 8, 0, (MapHeight-1) * 8, "pocketdimension")
	;r = CreateRoom(0, ROOM1, 0, 0, 8, "gatea")
	r = CreateRoom(0, ROOM1, 8, 0, (MapHeight+2) * 8, "173")
	r = CreateRoom(0, ROOM1, (MapWidth+2) * 8, 0, (MapHeight+2) * 8, "pocketdimension")
	r = CreateRoom(0, ROOM1, 0, 500, -16, "gatea")
	r = CreateRoom(0, ROOM1, -16, 800, 0, "dimension1499")
	
	CreateEvent("173", "173", 0)
	CreateEvent("pocketdimension", "pocketdimension", 0)   
	CreateEvent("gatea", "gatea", 0)
	CreateEvent("dimension1499", "dimension1499", 0)
	
	For r.Rooms = Each Rooms
		r\Adjacent[0]=Null
		r\Adjacent[1]=Null
		r\Adjacent[2]=Null
		r\Adjacent[3]=Null
		For r2.Rooms = Each Rooms
			If r<>r2 Then
				If r2\z=r\z Then
					If (r2\x)=(r\x+8.0) Then
						r\Adjacent[0]=r2
						If r\AdjDoor[0] = Null Then r\AdjDoor[0] = r2\AdjDoor[2]
					ElseIf (r2\x)=(r\x-8.0)
						r\Adjacent[2]=r2
						If r\AdjDoor[2] = Null Then r\AdjDoor[2] = r2\AdjDoor[0]
					EndIf
				ElseIf r2\x=r\x Then
					If (r2\z)=(r\z-8.0) Then
						r\Adjacent[1]=r2
						If r\AdjDoor[1] = Null Then r\AdjDoor[1] = r2\AdjDoor[3]
					ElseIf (r2\z)=(r\z+8.0)
						r\Adjacent[3]=r2
						If r\AdjDoor[3] = Null Then r\AdjDoor[3] = r2\AdjDoor[1]
					EndIf
				EndIf
			EndIf
			If (r\Adjacent[0]<>Null) And (r\Adjacent[1]<>Null) And (r\Adjacent[2]<>Null) And (r\Adjacent[3]<>Null) Then Exit
		Next
	Next
	
	For x = 0 To MapWidth+1
		For y = 0 To MapHeight+1
			If MapTemp(x,y)>0 Then
				DebugLog "MapTemp("+x+","+y+") = True"
			Else
				DebugLog "MapTemp("+x+","+y+") = False"
			EndIf
		Next
	Next
	
	;CatchErrors("LoadMap")
End Function
Function CreateMap(RandomSeed$, SizeReduce = False)
	SizeReduce = Max(Min(SizeReduce, 3), 0)
	
	AddLog("Generating a map using the seed "+RandomSeed+"...", 0, False)
	
	Dim MapTemp%(MapWidth+1, MapHeight+1)
	Dim MapFound%(MapWidth+1, MapHeight+1)
	
	I_Zone\Transition[0] = 13
	I_Zone\Transition[1] = 7
	I_Zone\HasCustomForest = False
	I_Zone\HasCustomMT = False
	
	Local x%, y%, temp%
	Local i%, x2%, y2%
	Local width%, height%
	
	Local zone%
	
	SeedRnd GenerateSeedNumber(RandomSeed)
	
	Dim MapName$(MapWidth, MapHeight)
	
	Dim MapRoomID%(ROOM4 + 1)
	
	x = Floor(MapWidth / 2)
	y = MapHeight - 2;Rand(3, 5)
	
	For i = y To MapHeight - 1
		MapTemp(x, i) = True
	Next
	Repeat
		;mp_send(M_ONSERV, 0)
		
		width = Rand(10/(SizeReduce+1), 15/(SizeReduce+1))
		
		If x > MapWidth*0.6 Then
			width = -width
		ElseIf x > MapWidth*0.4
			x = x-width/2
		EndIf
		
		;make sure the hallway doesn't go outside the array
		If x+width > MapWidth-3 Then
			;x = -width+MapWidth-4
			
			width=MapWidth-3-x
		ElseIf x+width < 2
			
			;x = 3-width
			width=-x+2
		EndIf
		
		x = Min(x, x + width)
		width = Abs(width)
		For i = x To x + width
			MapTemp(Min(i,MapWidth), y) = True
		Next
		
		height = Rand(Max(2, 3-SizeReduce), 4-(SizeReduce/2))
		If y - height < 1 Then height = y-1
		
		yhallways = Rand(4,5)
		
		If GetZone(y-height)<>GetZone(y-height+1) Then height=height-1
		
		For i = 1 To yhallways
			
			x2 = Max(Min(Rand(x, x + width-1),MapWidth-2),2)
			While MapTemp(x2, y - 1) Or MapTemp(x2 - 1, y - 1) Or MapTemp(x2 + 1, y - 1)
				x2=x2+1
			Wend
			
			If x2<x+width Then
				If i = 1 Then
					tempheight = height 
					If Rand(2)=1 Then x2 = x Else x2 = x+width
				Else
					tempheight = Rand(1,height)
				EndIf
				
				For y2 = y - tempheight To y
					If GetZone(y2)<>GetZone(y2+1) Then ;a room leading from zone to another
						MapTemp(x2, y2) = 255
					Else
						MapTemp(x2, y2) = True
					EndIf
				Next
				
				If tempheight = height Then temp = x2
			End If
			
		Next
		
		x = temp
		y = y - height
	Until y < 2
	
	
	Local ZoneAmount=3
	Local Room1Amount%[3], Room2Amount%[3],Room2CAmount%[3],Room3Amount%[3],Room4Amount%[3]
	
	;count the amount of rooms
	For y = 1 To MapHeight - 1
		;mp_send(M_ONSERV, 0)
		zone% = GetZone(y)
		
		For x = 1 To MapWidth - 1
			If MapTemp(x, y) > 0 Then
				temp = Min(MapTemp(x + 1, y),1) + Min(MapTemp(x - 1, y),1)
				temp = temp + Min(MapTemp(x, y + 1),1) + Min(MapTemp(x, y - 1),1)			
				If MapTemp(x,y)<255 Then MapTemp(x, y) = temp
				Select MapTemp(x,y)
					Case 1
						Room1Amount[zone]=Room1Amount[zone]+1
					Case 2
						If Min(MapTemp(x + 1, y),1) + Min(MapTemp(x - 1, y),1)= 2 Then
							Room2Amount[zone]=Room2Amount[zone]+1	
						ElseIf Min(MapTemp(x, y + 1),1) + Min(MapTemp(x , y - 1),1)= 2
							Room2Amount[zone]=Room2Amount[zone]+1	
						Else
							Room2CAmount[zone] = Room2CAmount[zone]+1
						EndIf
					Case 3
						Room3Amount[zone]=Room3Amount[zone]+1
					Case 4
						Room4Amount[zone]=Room4Amount[zone]+1
				End Select
			EndIf
		Next
	Next		
	
	;force more room1s (if needed)
	For i = 0 To 2
		;need more rooms if there are less than 5 of them
		temp = -Room1Amount[i]+Max(1, 5-(SizeReduce*2))
		
		If temp > 0 Then
			
			For y = (MapHeight/ZoneAmount)*(2-i)+1 To ((MapHeight/ZoneAmount) * ((2-i)+1.0))-2
				
				For x = 2 To MapWidth - 2
					If MapTemp(x, y) = 0 Then
						
						If (Min(MapTemp(x + 1, y),1) + Min(MapTemp(x - 1, y),1) + Min(MapTemp(x, y + 1),1) + Min(MapTemp(x, y - 1),1)) = 1 Then
							;If Rand(4)=1 Then
							
							If MapTemp(x + 1, y) Then
								x2 = x+1 : y2 = y
							ElseIf MapTemp(x - 1, y)
								x2 = x-1 : y2 = y
							ElseIf MapTemp(x, y+1)
								x2 = x : y2 = y+1	
							ElseIf MapTemp(x, y-1)
								x2 = x : y2 = y-1
							EndIf
							
							placed = False
							If MapTemp(x2,y2)>1 And MapTemp(x2,y2)<4 Then 
								Select MapTemp(x2,y2)
									Case 2
										If Min(MapTemp(x2 + 1, y2),1) + Min(MapTemp(x2 - 1, y2),1)= 2 Then
											Room2Amount[i]=Room2Amount[i]-1
											Room3Amount[i]=Room3Amount[i]+1
											placed = True
										ElseIf Min(MapTemp(x2, y2 + 1),1) + Min(MapTemp(x2, y2 - 1),1)= 2
											Room2Amount[i]=Room2Amount[i]-1
											Room3Amount[i]=Room3Amount[i]+1
											placed = True
										EndIf
									Case 3
										Room3Amount[i]=Room3Amount[i]-1
										Room4Amount[i]=Room4Amount[i]+1	
										placed = True
								End Select
								
								If placed Then
									MapTemp(x2,y2)=MapTemp(x2,y2)+1
									
									MapTemp(x, y) = 1
									Room1Amount[i] = Room1Amount[i]+1	
									
									temp=temp-1
								EndIf
							EndIf
						EndIf
						
					EndIf
					If temp = 0 Then Exit
				Next
				If temp = 0 Then Exit
			Next
		EndIf
	Next
	
	
	
	
	
	;force more room4s and room2Cs
	For i = 0 To 2
		
		Select i
			Case 2
				zone=2
				temp2=MapHeight/3;-1
			Case 1
				zone=MapHeight/3+1
				temp2=MapHeight*(2.0/3.0)-1
			Case 0
				zone=MapHeight*(2.0/3.0)+1
				temp2=MapHeight-2
		End Select
		
		If Room4Amount[i]<1 Then ;we want at least 1 ROOM4
			DebugLog "forcing a ROOM4 into zone "+i
			temp=0
			
			For y = zone To temp2
				For x = 2 To MapWidth - 2
					;mp_send(M_ONSERV, 0)
					If MapTemp(x,y)=3 Then
						Select 0 ;see if adding a ROOM1 is possible
							Case (MapTemp(x+1,y) Or MapTemp(x+1,y+1) Or MapTemp(x+1,y-1) Or MapTemp(x+2,y))
								MapTemp(x+1,y)=1
								temp=1
							Case (MapTemp(x-1,y) Or MapTemp(x-1,y+1) Or MapTemp(x-1,y-1) Or MapTemp(x-2,y))
								MapTemp(x-1,y)=1
								temp=1
							Case (MapTemp(x,y+1) Or MapTemp(x+1,y+1) Or MapTemp(x-1,y+1) Or MapTemp(x,y+2))
								MapTemp(x,y+1)=1
								temp=1
							Case (MapTemp(x,y-1) Or MapTemp(x+1,y-1) Or MapTemp(x-1,y-1) Or MapTemp(x,y-2))
								MapTemp(x,y-1)=1
								temp=1
						End Select
						If temp=1 Then
							MapTemp(x,y)=4 ;turn this room into a ROOM4
							DebugLog "ROOM4 forced into slot ("+x+", "+y+")"
							Room4Amount[i]=Room4Amount[i]+1
							Room3Amount[i]=Room3Amount[i]-1
							Room1Amount[i]=Room1Amount[i]+1
						EndIf
					EndIf
					If temp=1 Then Exit
				Next
				If temp=1 Then Exit
			Next
			
			If temp=0 Then DebugLog "Couldn't place ROOM4 in zone "+i
		EndIf
		
		If Room2CAmount[i]<1 Then ;we want at least 1 ROOM2C
			DebugLog "forcing a ROOM2C into zone "+i
			temp=0
			
			zone=zone+1
			temp2=temp2-1
			
			For y = zone To temp2
				For x = 3 To MapWidth - 3
					;mp_send(M_ONSERV, 0)
					If MapTemp(x,y)=1 Then
						Select True ;see if adding some rooms is possible
							Case MapTemp(x-1,y)>0
								If (MapTemp(x,y-1)+MapTemp(x,y+1)+MapTemp(x+2,y))=0 Then
									If (MapTemp(x+1,y-2)+MapTemp(x+2,y-1)+MapTemp(x+1,y-1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x+1,y)=2
										DebugLog "ROOM2C forced into slot ("+(x+1)+", "+(y)+")"
										MapTemp(x+1,y-1)=1
										temp=1
									Else If (MapTemp(x+1,y+2)+MapTemp(x+2,y+1)+MapTemp(x+1,y+1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x+1,y)=2
										DebugLog "ROOM2C forced into slot ("+(x+1)+", "+(y)+")"
										MapTemp(x+1,y+1)=1
										temp=1
									EndIf
								EndIf
							Case MapTemp(x+1,y)>0
								If (MapTemp(x,y-1)+MapTemp(x,y+1)+MapTemp(x-2,y))=0 Then
									If (MapTemp(x-1,y-2)+MapTemp(x-2,y-1)+MapTemp(x-1,y-1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x-1,y)=2
										DebugLog "ROOM2C forced into slot ("+(x-1)+", "+(y)+")"
										MapTemp(x-1,y-1)=1
										temp=1
									Else If (MapTemp(x-1,y+2)+MapTemp(x-2,y+1)+MapTemp(x-1,y+1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x-1,y)=2
										DebugLog "ROOM2C forced into slot ("+(x-1)+", "+(y)+")"
										MapTemp(x-1,y+1)=1
										temp=1
									EndIf
								EndIf
							Case MapTemp(x,y-1)>0
								If (MapTemp(x-1,y)+MapTemp(x+1,y)+MapTemp(x,y+2))=0 Then
									If (MapTemp(x-2,y+1)+MapTemp(x-1,y+2)+MapTemp(x-1,y+1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x,y+1)=2
										DebugLog "ROOM2C forced into slot ("+(x)+", "+(y+1)+")"
										MapTemp(x-1,y+1)=1
										temp=1
									Else If (MapTemp(x+2,y+1)+MapTemp(x+1,y+2)+MapTemp(x+1,y+1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x,y+1)=2
										DebugLog "ROOM2C forced into slot ("+(x)+", "+(y+1)+")"
										MapTemp(x+1,y+1)=1
										temp=1
									EndIf
								EndIf
							Case MapTemp(x,y+1)>0
								If (MapTemp(x-1,y)+MapTemp(x+1,y)+MapTemp(x,y-2))=0 Then
									If (MapTemp(x-2,y-1)+MapTemp(x-1,y-2)+MapTemp(x-1,y-1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x,y-1)=2
										DebugLog "ROOM2C forced into slot ("+(x)+", "+(y-1)+")"
										MapTemp(x-1,y-1)=1
										temp=1
									Else If (MapTemp(x+2,y-1)+MapTemp(x+1,y-2)+MapTemp(x+1,y-1))=0 Then
										MapTemp(x,y)=2
										MapTemp(x,y-1)=2
										DebugLog "ROOM2C forced into slot ("+(x)+", "+(y-1)+")"
										MapTemp(x+1,y-1)=1
										temp=1
									EndIf
								EndIf
						End Select
						If temp=1 Then
							Room2CAmount[i]=Room2CAmount[i]+1
							Room2Amount[i]=Room2Amount[i]+1
						EndIf
					EndIf
					If temp=1 Then Exit
				Next
				If temp=1 Then Exit
			Next
			
			If temp=0 Then DebugLog "Couldn't place ROOM2C in zone "+i
		EndIf
		
	Next
	
	Local MaxRooms% = 55*MapWidth/20
	MaxRooms=Max(MaxRooms,Room1Amount[0]+Room1Amount[1]+Room1Amount[2]+1)
	MaxRooms=Max(MaxRooms,Room2Amount[0]+Room2Amount[1]+Room2Amount[2]+1)
	MaxRooms=Max(MaxRooms,Room2CAmount[0]+Room2CAmount[1]+Room2CAmount[2]+1)
	MaxRooms=Max(MaxRooms,Room3Amount[0]+Room3Amount[1]+Room3Amount[2]+1)
	MaxRooms=Max(MaxRooms,Room4Amount[0]+Room4Amount[1]+Room4Amount[2]+1)
	Dim MapRoom$(ROOM4 + 1, MaxRooms)
	
	
	;zone 1 --------------------------------------------------------------------------------------------------
	
	Local min_pos = 1, max_pos = Room1Amount[0]-1
	
	MapRoom(ROOM1, 0) = "start"	
	SetRoom("roompj", ROOM1, Floor(0.1*Float(Room1Amount[0])),min_pos,max_pos)
	SetRoom("914", ROOM1, Floor(0.3*Float(Room1Amount[0])),min_pos,max_pos)
	SetRoom("room1archive",ROOM1,Floor(0.5*Float(Room1Amount[0])),min_pos,max_pos)
	SetRoom("room205", ROOM1, Floor(0.6*Float(Room1Amount[0])),min_pos,max_pos)
	
	MapRoom(ROOM2C, 0) = "lockroom"
	
	min_pos = 1
	max_pos = Room2Amount[0]-1
	
	MapRoom(ROOM2, 0) = "room2closets"
	SetRoom("room2testroom2", ROOM2, Floor(0.1*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room2scps", ROOM2, Floor(0.2*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room2storage", ROOM2, Floor(0.3*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room2gw_b", ROOM2, Floor(0.4*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room2sl", ROOM2, Floor(0.5*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room012", ROOM2, Floor(0.55*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room2scps2",ROOM2,Floor(0.6*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room1123",ROOM2,Floor(0.7*Float(Room2Amount[0])),min_pos,max_pos)
	SetRoom("room2elevator",ROOM2,Floor(0.85*Float(Room2Amount[0])),min_pos,max_pos)
	
	
	MapRoom(ROOM3, Floor(Rnd(0.2,0.8)*Float(Room3Amount[0]))) = "room3storage"
	
	MapRoom(ROOM2C, Floor(0.5*Float(Room2CAmount[0]))) = "room1162"
	
	MapRoom(ROOM4, Floor(0.3*Float(Room4Amount[0]))) = "room4info"
	
	;zone 2 --------------------------------------------------------------------------------------------------
	
	min_pos = Room1Amount[0]
	max_pos = Room1Amount[0]+Room1Amount[1]-1
	SetRoom("room079", ROOM1, Room1Amount[0]+Floor(0.15*Float(Room1Amount[1])),min_pos,max_pos)
    SetRoom("room106", ROOM1, Room1Amount[0]+Floor(0.3*Float(Room1Amount[1])),min_pos,max_pos)
    SetRoom("008", ROOM1, Room1Amount[0]+Floor(0.4*Float(Room1Amount[1])),min_pos,max_pos)
    SetRoom("room035", ROOM1, Room1Amount[0]+Floor(0.5*Float(Room1Amount[1])),min_pos,max_pos)
    SetRoom("coffin", ROOM1, Room1Amount[0]+Floor(0.7*Float(Room1Amount[1])),min_pos,max_pos)
	
	min_pos = Room2Amount[0]
	max_pos = Room2Amount[0]+Room2Amount[1]-1
 
	MapRoom(ROOM2, Room2Amount[0]+Floor(0.1*Float(Room2Amount[1]))) = "room2nuke"
	SetRoom("room2tunnel", ROOM2, Room2Amount[0]+Floor(0.25*Float(Room2Amount[1])),min_pos,max_pos)
	SetRoom("room049", ROOM2, Room2Amount[0]+Floor(0.4*Float(Room2Amount[1])),min_pos,max_pos)
	SetRoom("room2shaft",ROOM2,Room2Amount[0]+Floor(0.6*Float(Room2Amount[1])),min_pos,max_pos)
	SetRoom("testroom", ROOM2, Room2Amount[0]+Floor(0.7*Float(Room2Amount[1])),min_pos,max_pos)
	SetRoom("room2servers", ROOM2, Room2Amount[0]+Floor(0.9*Room2Amount[1]),min_pos,max_pos)
	
	MapRoom(ROOM3, Room3Amount[0]+Floor(0.3*Float(Room3Amount[1]))) = "room513"
	MapRoom(ROOM3, Room3Amount[0]+Floor(0.6*Float(Room3Amount[1]))) = "room966"
	
	MapRoom(ROOM2C, Room2CAmount[0]+Floor(0.5*Float(Room2CAmount[1]))) = "room2cpit"
	
	
	;zone 3  --------------------------------------------------------------------------------------------------
	
	MapRoom(ROOM1, Room1Amount[0]+Room1Amount[1]+Room1Amount[2]-2) = "exit1"
	MapRoom(ROOM1, Room1Amount[0]+Room1Amount[1]+Room1Amount[2]-1) = "gateaentrance"
	MapRoom(ROOM1, Room1Amount[0]+Room1Amount[1]) = "room1lifts"
	
	min_pos = Room2Amount[0]+Room2Amount[1]
	max_pos = Room2Amount[0]+Room2Amount[1]+Room2Amount[2]-1		
	
	MapRoom(ROOM2, min_pos+Floor(0.1*Float(Room2Amount[2]))) = "room2poffices"
	SetRoom("room2cafeteria", ROOM2, min_pos+Floor(0.2*Float(Room2Amount[2])),min_pos,max_pos)
	SetRoom("room2sroom", ROOM2, min_pos+Floor(0.3*Float(Room2Amount[2])),min_pos,max_pos)
	SetRoom("room2servers2", ROOM2, min_pos+Floor(0.4*Room2Amount[2]),min_pos,max_pos)	
	SetRoom("room2offices", ROOM2, min_pos+Floor(0.45*Room2Amount[2]),min_pos,max_pos)
	SetRoom("room2offices4", ROOM2, min_pos+Floor(0.5*Room2Amount[2]),min_pos,max_pos)	
	SetRoom("room860", ROOM2, min_pos+Floor(0.6*Room2Amount[2]),min_pos,max_pos)
	SetRoom("medibay", ROOM2, min_pos+Floor(0.7*Float(Room2Amount[2])),min_pos,max_pos)
	SetRoom("room2poffices2", ROOM2, min_pos+Floor(0.8*Room2Amount[2]),min_pos,max_pos)
	SetRoom("room2offices2", ROOM2, min_pos+Floor(0.9*Float(Room2Amount[2])),min_pos,max_pos)
	
	MapRoom(ROOM2C, Room2CAmount[0]+Room2CAmount[1]) = "room2ccont"	
	MapRoom(ROOM2C, Room2CAmount[0]+Room2CAmount[1]+1) = "lockroom2"		
	
	MapRoom(ROOM3, Room3Amount[0]+Room3Amount[1]+Floor(0.3*Float(Room3Amount[2]))) = "room3servers"
	MapRoom(ROOM3, Room3Amount[0]+Room3Amount[1]+Floor(0.7*Float(Room3Amount[2]))) = "room3servers2"
	;MapRoom(ROOM3, Room3Amount[0]+Room3Amount[1]) = "room3gw"
	MapRoom(ROOM3, Room3Amount[0]+Room3Amount[1]+Floor(0.5*Float(Room3Amount[2]))) = "room3offices"
	
	;----------------------- luodaan kartta --------------------------------
	
	temp = 0
	Local r.Rooms, spacing# = 8.0
	For y = MapHeight - 1 To 1 Step - 1
		;mp_send(M_ONSERV, 0)
		;zone% = GetZone(y)
		
		If y < MapHeight/3+1 Then
			zone=3
		ElseIf y < MapHeight*(2.0/3.0);-1
			zone=2
		Else
			zone=1
		EndIf
		
		For x = 1 To MapWidth - 2
			If MapTemp(x, y) = 255 Then
				If y>MapHeight/2 Then ;zone = 2
					r = CreateRoom(zone, ROOM2, x * 8, 0, y * 8, "checkpoint1")
				Else ;If zone = 3
					r = CreateRoom(zone, ROOM2, x * 8, 0, y * 8, "checkpoint2")
				EndIf
			ElseIf MapTemp(x, y) > 0
				
				temp = Min(MapTemp(x + 1, y),1) + Min(MapTemp(x - 1, y),1) + Min(MapTemp(x, y + 1),1) + Min(MapTemp(x, y - 1),1)
				
				Select temp ;viereisiss� ruuduissa olevien huoneiden m��r�
					Case 1
						If MapRoomID(ROOM1) < MaxRooms And MapName(x,y) = "" Then
							If MapRoom(ROOM1, MapRoomID(ROOM1)) <> "" Then MapName(x, y) = MapRoom(ROOM1, MapRoomID(ROOM1))	
						EndIf
						
						r = CreateRoom(zone, ROOM1, x * 8, 0, y * 8, MapName(x, y))
						If MapTemp(x, y + 1) Then
							r\angle = 180 
							TurnEntity(r\obj, 0, r\angle, 0)
						ElseIf MapTemp(x - 1, y)
							r\angle = 270
							TurnEntity(r\obj, 0, r\angle, 0)
						ElseIf MapTemp(x + 1, y)
							r\angle = 90
							TurnEntity(r\obj, 0, r\angle, 0)
						Else 
							r\angle = 0
						End If
						MapRoomID(ROOM1)=MapRoomID(ROOM1)+1
					Case 2
						If MapTemp(x - 1, y)>0 And MapTemp(x + 1, y)>0 Then
							If MapRoomID(ROOM2) < MaxRooms And MapName(x,y) = ""  Then
								If MapRoom(ROOM2, MapRoomID(ROOM2)) <> "" Then MapName(x, y) = MapRoom(ROOM2, MapRoomID(ROOM2))	
							EndIf
							r = CreateRoom(zone, ROOM2, x * 8, 0, y * 8, MapName(x, y))
							If Rand(2) = 1 Then r\angle = 90 Else r\angle = 270
							TurnEntity(r\obj, 0, r\angle, 0)
							MapRoomID(ROOM2)=MapRoomID(ROOM2)+1
						ElseIf MapTemp(x, y - 1)>0 And MapTemp(x, y + 1)>0
							If MapRoomID(ROOM2) < MaxRooms And MapName(x,y) = ""  Then
								If MapRoom(ROOM2, MapRoomID(ROOM2)) <> "" Then MapName(x, y) = MapRoom(ROOM2, MapRoomID(ROOM2))	
							EndIf
							r = CreateRoom(zone, ROOM2, x * 8, 0, y * 8, MapName(x, y))
							If Rand(2) = 1 Then r\angle = 180 Else r\angle = 0
							TurnEntity(r\obj, 0, r\angle, 0)
							MapRoomID(ROOM2)=MapRoomID(ROOM2)+1
						Else
							If MapRoomID(ROOM2C) < MaxRooms And MapName(x,y) = ""  Then
								If MapRoom(ROOM2C, MapRoomID(ROOM2C)) <> "" Then MapName(x, y) = MapRoom(ROOM2C, MapRoomID(ROOM2C))	
							EndIf
							
							If MapTemp(x - 1, y)>0 And MapTemp(x, y + 1)>0 Then
								r = CreateRoom(zone, ROOM2C, x * 8, 0, y * 8, MapName(x, y))
								r\angle = 180
								TurnEntity(r\obj, 0, r\angle, 0)
							ElseIf MapTemp(x + 1, y)>0 And MapTemp(x, y + 1)>0
								r = CreateRoom(zone, ROOM2C, x * 8, 0, y * 8, MapName(x, y))
								r\angle = 90
								TurnEntity(r\obj, 0, r\angle, 0)
							ElseIf MapTemp(x - 1, y)>0 And MapTemp(x, y - 1)>0
								r = CreateRoom(zone, ROOM2C, x * 8, 0, y * 8, MapName(x, y))
								TurnEntity(r\obj, 0, 270, 0)
								r\angle = 270
							Else
								r = CreateRoom(zone, ROOM2C, x * 8, 0, y * 8, MapName(x, y))
							EndIf
							MapRoomID(ROOM2C)=MapRoomID(ROOM2C)+1
						EndIf
					Case 3
						If MapRoomID(ROOM3) < MaxRooms And MapName(x,y) = ""  Then
							If MapRoom(ROOM3, MapRoomID(ROOM3)) <> "" Then MapName(x, y) = MapRoom(ROOM3, MapRoomID(ROOM3))	
						EndIf
						
						r = CreateRoom(zone, ROOM3, x * 8, 0, y * 8, MapName(x, y))
						If (Not MapTemp(x, y - 1)) Then
							TurnEntity(r\obj, 0, 180, 0)
							r\angle = 180
						ElseIf (Not MapTemp(x - 1, y))
							TurnEntity(r\obj, 0, 90, 0)
							r\angle = 90
						ElseIf (Not MapTemp(x + 1, y))
							TurnEntity(r\obj, 0, -90, 0)
							r\angle = 270
						End If
						MapRoomID(ROOM3)=MapRoomID(ROOM3)+1
					Case 4
						If MapRoomID(ROOM4) < MaxRooms And MapName(x,y) = ""  Then
							If MapRoom(ROOM4, MapRoomID(ROOM4)) <> "" Then MapName(x, y) = MapRoom(ROOM4, MapRoomID(ROOM4))	
						EndIf
						
						r = CreateRoom(zone, ROOM4, x * 8, 0, y * 8, MapName(x, y))
						MapRoomID(ROOM4)=MapRoomID(ROOM4)+1
				End Select
				
			EndIf
			
		Next
	Next		
	
	r = CreateRoom(0, ROOM1, (MapWidth-1) * 8, 500, 8, "gatea")
	MapRoomID(ROOM1)=MapRoomID(ROOM1)+1
	
	r = CreateRoom(0, ROOM1, (MapWidth-1) * 8, 0, (MapHeight-1) * 8, "pocketdimension")
	MapRoomID(ROOM1)=MapRoomID(ROOM1)+1	
	
	r = CreateRoom(0, ROOM1, 8, 0, (MapHeight-1) * 8, "173")
	MapRoomID(ROOM1)=MapRoomID(ROOM1)+1
	
	r = CreateRoom(0, ROOM1, 8, 800, 0, "dimension1499")
	MapRoomID(ROOM1)=MapRoomID(ROOM1)+1
	
	For r.Rooms = Each Rooms
		PreventRoomOverlap(r)
	Next
	
	If 0 Then 
		Repeat
			;mp_send(M_ONSERV, 0)
			Cls
			For x = 0 To MapWidth - 1
				For y = 0 To MapHeight - 1
					If MapTemp(x, y) = 0 Then
						
						zone=GetZone(y)
						
						Color 50*zone, 50*zone, 50*zone
						Rect(x * 32, y * 32, 30, 30)
					Else
						If MapTemp(x, y) = 255 Then
							Color 0,200,0
						Else If MapTemp(x,y)=4 Then
							Color 50,50,255
						Else If MapTemp(x,y)=3 Then
							Color 50,255,255
						Else If MapTemp(x,y)=2 Then
							Color 255,255,50
						Else
							Color 255, 255, 255
						EndIf
						Rect(x * 32, y * 32, 30, 30)
					End If
				Next
			Next	
			
			For x = 0 To MapWidth - 1
				For y = 0 To MapHeight - 1
					
					If MouseX()>x*32 And MouseX()<x*32+32 Then
						If MouseY()>y*32 And MouseY()<y*32+32 Then
							Color 255, 0, 0
						Else
							Color 200, 200, 200
						EndIf
					Else
						Color 200, 200, 200
					EndIf
					
					If MapTemp(x, y) > 0 Then
						Text x * 32 +2, (y) * 32 + 2,MapTemp(x, y) +" "+ MapName(x,y)
					End If
				Next
			Next			
			
			Flip
		Until KeyHit(28)		
	EndIf
	
	
	For y = 0 To MapHeight
		For x = 0 To MapWidth
			;mp_send(M_ONSERV, 0)
			MapTemp(x, y) = Min(MapTemp(x, y),1)
		Next
	Next
	
	Local d.Doors
	Local shouldSpawnDoor%
	For y = MapHeight To 0 Step -1
		;mp_send(M_ONSERV, 0)
		If y<I_Zone\Transition[1]-1 Then
			zone=3
		ElseIf y>=I_Zone\Transition[1]-1 And y<I_Zone\Transition[0]-1 Then
			zone=2
		Else
			zone=1
		EndIf
		
		For x = MapWidth To 0 Step -1
			If MapTemp(x,y) > 0 Then
				If zone = 2 Then temp=2 Else temp=0
                
                For r.Rooms = Each Rooms
					r\angle = WrapAngle(r\angle)
					If Int(r\x/8.0)=x And Int(r\z/8.0)=y Then
						shouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								If r\angle=90
									shouldSpawnDoor = True
								EndIf
							Case ROOM2
								If r\angle=90 Or r\angle=270
									shouldSpawnDoor = True
								EndIf
							Case ROOM2C
								If r\angle=0 Or r\angle=90
									shouldSpawnDoor = True
								EndIf
							Case ROOM3
								If r\angle=0 Or r\angle=180 Or r\angle=90
									shouldSpawnDoor = True
								EndIf
							Default
								shouldSpawnDoor = True
						End Select
						If shouldSpawnDoor
							If (x+1)<(MapWidth+1)
								If MapTemp(x + 1, y) > 0 Then
									d.Doors = CreateDoor(r\zone, Float(x) * spacing + spacing / 2.0, 0, Float(y) * spacing, 90, r, Max(Rand(-3, 1), 0), temp)
									r\AdjDoor[0] = d
								EndIf
							EndIf
						EndIf
						
						shouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								If r\angle=180
									shouldSpawnDoor = True
								EndIf
							Case ROOM2
								If r\angle=0 Or r\angle=180
									shouldSpawnDoor = True
								EndIf
							Case ROOM2C
								If r\angle=180 Or r\angle=90
									shouldSpawnDoor = True
								EndIf
							Case ROOM3
								If r\angle=180 Or r\angle=90 Or r\angle=270
									shouldSpawnDoor = True
								EndIf
							Default
								shouldSpawnDoor = True
						End Select
						If shouldSpawnDoor
							If (y+1)<(MapHeight+1)
								If MapTemp(x, y + 1) > 0 Then
									d.Doors = CreateDoor(r\zone, Float(x) * spacing, 0, Float(y) * spacing + spacing / 2.0, 0, r, Max(Rand(-3, 1), 0), temp)
									r\AdjDoor[3] = d
								EndIf
							EndIf
						EndIf
						
						Exit
					EndIf
                Next
                
			End If
			
		Next
	Next
	
	For r.Rooms = Each Rooms
		;If r\angle >= 360
        ;    r\angle = r\angle-360
        ;EndIf
		r\angle = WrapAngle(r\angle)
		r\Adjacent[0]=Null
		r\Adjacent[1]=Null
		r\Adjacent[2]=Null
		r\Adjacent[3]=Null
		For r2.Rooms = Each Rooms
			If r<>r2 Then
				If r2\z=r\z Then
					If (r2\x)=(r\x+8.0) Then
						r\Adjacent[0]=r2
						If r\AdjDoor[0] = Null Then r\AdjDoor[0] = r2\AdjDoor[2]
					ElseIf (r2\x)=(r\x-8.0)
						r\Adjacent[2]=r2
						If r\AdjDoor[2] = Null Then r\AdjDoor[2] = r2\AdjDoor[0]
					EndIf
				ElseIf r2\x=r\x Then
					If (r2\z)=(r\z-8.0) Then
						r\Adjacent[1]=r2
						If r\AdjDoor[1] = Null Then r\AdjDoor[1] = r2\AdjDoor[3]
					ElseIf (r2\z)=(r\z+8.0)
						r\Adjacent[3]=r2
						If r\AdjDoor[3] = Null Then r\AdjDoor[3] = r2\AdjDoor[1]
					EndIf
				EndIf
			EndIf
			If (r\Adjacent[0]<>Null) And (r\Adjacent[1]<>Null) And (r\Adjacent[2]<>Null) And (r\Adjacent[3]<>Null) Then Exit
		Next
	Next
	;mp_send(M_ONSERV, 0)
	;udpreceive();;mp_send(M_ONSERV, 0)
End Function
Global ark_blur_cam
Function LoadEntities()
	;CatchErrors("Uncaught (LoadEntities)")
	Local i%
	
	For i=0 To 9
		TempSounds[i]=0
	Next
	PauseMenuIMG% = LoadImage_Strict("GFX\menu\pausemenu.jpg")
	MaskImage PauseMenuIMG, 255,255,0
	ScaleImage PauseMenuIMG,MenuScale,MenuScale
	
	SprintIcon% = LoadImage_Strict("GFX\sprinticon.png")
	BlinkIcon% = LoadImage_Strict("GFX\blinkicon.png")
	CrouchIcon% = LoadImage_Strict("GFX\sneakicon.png")
	HandIcon% = LoadImage_Strict("GFX\handsymbol.png")
	HandIcon2% = LoadImage_Strict("GFX\handsymbol2.png")

	StaminaMeterIMG% = LoadImage_Strict("GFX\staminameter.jpg")

	KeypadHUD =  LoadImage_Strict("GFX\keypadhud.jpg")
	MaskImage(KeypadHUD, 255,0,255)

	Panel294 = LoadImage_Strict("GFX\294panel.jpg")
	MaskImage(Panel294, 255,0,255)
	
	
	Brightness% = 0
	CameraFogNear# = 0.5
	CameraFogFar# = 6.0
	StoredCameraFogFar# = CameraFogFar
	
	;TextureLodBias
	
	AmbientLightRoomTex% = CreateTexture(2,2,257)
	TextureBlend AmbientLightRoomTex,5
	SetBuffer(TextureBuffer(AmbientLightRoomTex))
	ClsColor 0,0,0
	Cls
	SetBuffer BackBuffer()
	AmbientLightRoomVal = 0
	
	SoundEmitter = CreatePivot()
	
	ScreenTexs[0] = CreateTexture(512, 512, 1)
	ScreenTexs[1] = CreateTexture(512, 512, 1)

	;Listener = CreateListener(Camera)
	
	Camera = CreateCamera()
	CameraViewport Camera,0,0,1280,720
	CameraRange(Camera, 0.05, 1000)
	CameraFogMode (Camera, 1)
	CameraFogRange (Camera, 0.05, 1000)
	
	FogTexture = LoadTexture_Strict("GFX\fog.jpg", 1)
	
	Fog = CreateSprite(ark_blur_cam)
	ScaleSprite(Fog, Max(GraphicWidth / 1240.0, 1.0), Max(GraphicHeight / 960.0 * 0.8, 0.8))
	EntityTexture(Fog, FogTexture)
	EntityBlend (Fog, 2)
	EntityOrder Fog, -1000
	MoveEntity(Fog, 0, 0, 1.0)
	
	GasMaskTexture = LoadTexture_Strict("GFX\GasmaskOverlay.jpg", 1)
	GasMaskOverlay = CreateSprite(ark_blur_cam)
	ScaleSprite(GasMaskOverlay, Max(GraphicWidth / 1024.0, 1.0), Max(GraphicHeight / 1024.0 * 0.8, 0.8))
	EntityTexture(GasMaskOverlay, GasMaskTexture)
	EntityBlend (GasMaskOverlay, 2)
	EntityFX(GasMaskOverlay, 1)
	EntityOrder GasMaskOverlay, -1003
	MoveEntity(GasMaskOverlay, 0, 0, 1.0)
	HideEntity(GasMaskOverlay)
	
	InfectTexture = LoadTexture_Strict("GFX\InfectOverlay.jpg", 1)
	InfectOverlay = CreateSprite(ark_blur_cam)
	ScaleSprite(InfectOverlay, Max(GraphicWidth / 1024.0, 1.0), Max(GraphicHeight / 1024.0 * 0.8, 0.8))
	EntityTexture(InfectOverlay, InfectTexture)
	EntityBlend (InfectOverlay, 3)
	EntityFX(InfectOverlay, 1)
	EntityOrder InfectOverlay, -1003
	MoveEntity(InfectOverlay, 0, 0, 1.0)
	;EntityAlpha (InfectOverlay, 255.0)
	HideEntity(InfectOverlay)
	
	NVTexture = LoadTexture_Strict("GFX\NightVisionOverlay.jpg", 1)
	NVOverlay = CreateSprite(ark_blur_cam)
	ScaleSprite(NVOverlay, Max(GraphicWidth / 1024.0, 1.0), Max(GraphicHeight / 1024.0 * 0.8, 0.8))
	EntityTexture(NVOverlay, NVTexture)
	EntityBlend (NVOverlay, 2)
	EntityFX(NVOverlay, 1)
	EntityOrder NVOverlay, -1003
	MoveEntity(NVOverlay, 0, 0, 1.0)
	HideEntity(NVOverlay)
	NVBlink = CreateSprite(ark_blur_cam)
	ScaleSprite(NVBlink, Max(GraphicWidth / 1024.0, 1.0), Max(GraphicHeight / 1024.0 * 0.8, 0.8))
	EntityColor(NVBlink,0,0,0)
	EntityFX(NVBlink, 1)
	EntityOrder NVBlink, -1005
	MoveEntity(NVBlink, 0, 0, 1.0)
	HideEntity(NVBlink)
	
	FogNVTexture = LoadTexture_Strict("GFX\fogNV.jpg", 1)
	
	
	DarkTexture = CreateTexture(1024, 1024, 1 + 2)
	SetBuffer TextureBuffer(DarkTexture)
	Cls
	SetBuffer BackBuffer()
	
	Dark = CreateSprite(Camera)
	ScaleSprite(Dark, Max(GraphicWidth / 1240.0, 1.0), Max(GraphicHeight / 960.0 * 0.8, 0.8))
	EntityTexture(Dark, DarkTexture)
	EntityBlend (Dark, 1)
	EntityOrder Dark, -1002
	MoveEntity(Dark, 0, 0, 1.0)
	EntityAlpha Dark, 0.0
	
	LightTexture = CreateTexture(1024, 1024, 1 + 2)
	SetBuffer TextureBuffer(LightTexture)
	ClsColor 255, 255, 255
	Cls
	ClsColor 0, 0, 0
	SetBuffer BackBuffer()
	
	TeslaTexture = LoadTexture_Strict("GFX\map\tesla.jpg", 1+2)
	
	Light = CreateSprite(Camera)
	ScaleSprite(Light, Max(GraphicWidth / 1240.0, 1.0), Max(GraphicHeight / 960.0 * 0.8, 0.8))
	EntityTexture(Light, LightTexture)
	EntityBlend (Light, 1)
	EntityOrder Light, -1002
	MoveEntity(Light, 0, 0, 1.0)
	HideEntity Light

	;Other NPCs pre-loaded
	;[Block]
	;[End Block]
	
;	For i=0 To 4
;		Select True
;			Case i=2
;				tempStr="2c"
;			Case i>2
;				tempStr=Str(i)
;			Default
;				tempStr=Str(i+1)
;		End Select
;		OBJTunnel(i)=LoadRMesh("GFX\map\mt"+tempStr+".rmesh",Null)
;		HideEntity OBJTunnel(i)
;	Next
	
;	OBJTunnel(0)=LoadRMesh("GFX\map\mt1.rmesh",Null)	
;	HideEntity OBJTunnel(0)				
;	OBJTunnel(1)=LoadRMesh("GFX\map\mt2.rmesh",Null)	
;	HideEntity OBJTunnel(1)
;	OBJTunnel(2)=LoadRMesh("GFX\map\mt2c.rmesh",Null)	
;	HideEntity OBJTunnel(2)				
;	OBJTunnel(3)=LoadRMesh("GFX\map\mt3.rmesh",Null)	
;	HideEntity OBJTunnel(3)	
;	OBJTunnel(4)=LoadRMesh("GFX\map\mt4.rmesh",Null)	
;	HideEntity OBJTunnel(4)				
;	OBJTunnel(5)=LoadRMesh("GFX\map\mt_elevator.rmesh",Null)
;	HideEntity OBJTunnel(5)
;	OBJTunnel(6)=LoadRMesh("GFX\map\mt_generator.rmesh",Null)
;	HideEntity OBJTunnel(6)
	
	LightSpriteTex(0) = LoadTexture_Strict("GFX\light1.jpg", 1)
	LightSpriteTex(1) = LoadTexture_Strict("GFX\light2.jpg", 1)
	LightSpriteTex(2) = LoadTexture_Strict("GFX\lightsprite.jpg",1)
	
	
	DoorOBJ = LoadMesh_Strict("GFX\map\door01.x")
	HideEntity DoorOBJ
	DoorFrameOBJ = LoadMesh_Strict("GFX\map\doorframe.x")
	HideEntity DoorFrameOBJ
	
	HeavyDoorObj(0) = LoadMesh_Strict("GFX\map\heavydoor1.x")
	HideEntity HeavyDoorObj(0)
	HeavyDoorObj(1) = LoadMesh_Strict("GFX\map\heavydoor2.x")
	HideEntity HeavyDoorObj(1)
	
	DoorColl = LoadMesh_Strict("GFX\map\doorcoll.x")
	HideEntity DoorColl
	
	ButtonOBJ = LoadMesh_Strict("GFX\map\Button.x")
	HideEntity ButtonOBJ
	ButtonKeyOBJ = LoadMesh_Strict("GFX\map\ButtonKeycard.x")
	HideEntity ButtonKeyOBJ
	ButtonCodeOBJ = LoadMesh_Strict("GFX\map\ButtonCode.x")
	HideEntity ButtonCodeOBJ	
	ButtonScannerOBJ = LoadMesh_Strict("GFX\map\ButtonScanner.x")
	HideEntity ButtonScannerOBJ	
	
	BigDoorOBJ(0) = LoadMesh_Strict("GFX\map\ContDoorLeft.x")
	HideEntity BigDoorOBJ(0)
	BigDoorOBJ(1) = LoadMesh_Strict("GFX\map\ContDoorRight.x")
	HideEntity BigDoorOBJ(1)
	
	LeverBaseOBJ = LoadMesh_Strict("GFX\map\leverbase.x")
	HideEntity LeverBaseOBJ
	LeverOBJ = LoadMesh_Strict("GFX\map\leverhandle.x")
	HideEntity LeverOBJ

	
	For i = 0 To 5
		GorePics(i) = LoadTexture_Strict("GFX\895pics\pic" + (i + 1) + ".jpg")
	Next
	
	OldAiPics(0) = LoadTexture_Strict("GFX\AIface.jpg")
	OldAiPics(1) = LoadTexture_Strict("GFX\AIface2.jpg")	
	
	
	For i = 0 To 6
		DecalTextures(i) = LoadTexture_Strict("GFX\decal" + (i + 1) + ".png", 1 + 2)
	Next
	DecalTextures(7) = LoadTexture_Strict("GFX\items\INVpaperstrips.jpg", 1 + 2)
	For i = 8 To 12
		DecalTextures(i) = LoadTexture_Strict("GFX\decalpd"+(i-7)+".jpg", 1 + 2)	
	Next
	For i = 13 To 14
		DecalTextures(i) = LoadTexture_Strict("GFX\bullethole"+(i-12)+".jpg", 1 + 2)	
	Next	
	For i = 15 To 16
		DecalTextures(i) = LoadTexture_Strict("GFX\blooddrop"+(i-14)+".png", 1 + 2)	
	Next
	DecalTextures(17) = LoadTexture_Strict("GFX\decal8.png", 1 + 2)	
	DecalTextures(18) = LoadTexture_Strict("GFX\decalpd6.dc", 1 + 2)	
	DecalTextures(19) = LoadTexture_Strict("GFX\decal19.png", 1 + 2)
	DecalTextures(20) = LoadTexture_Strict("GFX\decal427.png", 1 + 2)
	
	ParticleTextures(0) = LoadTexture_Strict("GFX\smoke.png", 1 + 2)
	ParticleTextures(1) = LoadTexture_Strict("GFX\flash.jpg", 1 + 2)
	ParticleTextures(2) = LoadTexture_Strict("GFX\dust.jpg", 1 + 2)
	ParticleTextures(3) = LoadTexture_Strict("GFX\npcs\hg.pt", 1 + 2)
	ParticleTextures(4) = LoadTexture_Strict("GFX\map\sun.jpg", 1 + 2)
	ParticleTextures(5) = LoadTexture_Strict("GFX\bloodsprite.png", 1 + 2)
	ParticleTextures(6) = LoadTexture_Strict("GFX\smoke2.png", 1 + 2)
	ParticleTextures(7) = LoadTexture_Strict("GFX\spark.jpg", 1 + 2)
	ParticleTextures(8) = LoadTexture_Strict("GFX\particle.png", 1 + 2)
	
	Monitor = LoadMesh_Strict("GFX\map\monitor.b3d")
	HideEntity Monitor
	MonitorTexture = LoadTexture_Strict("GFX\monitortexture.jpg")
	
	CamBaseOBJ = LoadMesh_Strict("GFX\map\cambase.x")
	HideEntity(CamBaseOBJ)
	CamOBJ = LoadMesh_Strict("GFX\map\CamHead.b3d")
	HideEntity(CamOBJ)
	
	Monitor2 = LoadMesh_Strict("GFX\map\monitor_checkpoint.b3d")
	HideEntity Monitor2
	Monitor3 = LoadMesh_Strict("GFX\map\monitor_checkpoint.b3d")
	HideEntity Monitor3
	MonitorTexture2 = LoadTexture_Strict("GFX\map\LockdownScreen2.jpg")
	MonitorTexture3 = LoadTexture_Strict("GFX\map\LockdownScreen.jpg")
	MonitorTexture4 = LoadTexture_Strict("GFX\map\LockdownScreen3.jpg")
	MonitorTextureOff = CreateTexture(1,1)
	SetBuffer TextureBuffer(MonitorTextureOff)
	ClsColor 0,0,0
	Cls
	SetBuffer BackBuffer()
	LightConeModel = LoadMesh_Strict("GFX\lightcone.b3d")
	HideEntity LightConeModel
	
	For i = 2 To CountSurfaces(Monitor2)
		sf = GetSurface(Monitor2,i)
		b = GetSurfaceBrush(sf)
		If b<>0 Then
			t1 = GetBrushTexture(b,0)
			If t1<>0 Then
				name$ = StripPath(TextureName(t1))
				If Lower(name) <> "monitortexture.jpg"
					BrushTexture b, MonitorTextureOff, 0, 0
					PaintSurface sf,b
				EndIf
				If name<>"" Then FreeTexture t1
			EndIf
			FreeBrush b
		EndIf
	Next
	For i = 2 To CountSurfaces(Monitor3)
		sf = GetSurface(Monitor3,i)
		b = GetSurfaceBrush(sf)
		If b<>0 Then
			t1 = GetBrushTexture(b,0)
			If t1<>0 Then
				name$ = StripPath(TextureName(t1))
				If Lower(name) <> "monitortexture.jpg"
					BrushTexture b, MonitorTextureOff, 0, 0
					PaintSurface sf,b
				EndIf
				If name<>"" Then FreeTexture t1
			EndIf
			FreeBrush b
		EndIf
	Next
	
	InitItemTemplates()
	
	SetChunkDataValues()
	
	;NPCtypeD - different models with different textures (loaded using "CopyEntity") - ENDSHN
	;[Block]
	For i=1 To MaxDTextures
		DTextures[i] = CopyEntity(ClassDObj)
		HideEntity DTextures[i]
	Next
	;Gonzales
	tex = LoadTexture_Strict("GFX\npcs\gonzales.jpg")
	EntityTexture DTextures[1],tex
	FreeTexture tex
	;SCP-970 corpse
	tex = LoadTexture_Strict("GFX\npcs\corpse.jpg")
	EntityTexture DTextures[2],tex
	FreeTexture tex
	;scientist 1
	tex = LoadTexture_Strict("GFX\npcs\scientist.jpg")
	EntityTexture DTextures[3],tex
	FreeTexture tex
	;scientist 2
	tex = LoadTexture_Strict("GFX\npcs\scientist2.jpg")
	EntityTexture DTextures[4],tex
	FreeTexture tex
	;janitor
	tex = LoadTexture_Strict("GFX\npcs\janitor.jpg")
	EntityTexture DTextures[5],tex
	FreeTexture tex
	;106 Victim
	tex = LoadTexture_Strict("GFX\npcs\106victim.jpg")
	EntityTexture DTextures[6],tex
	FreeTexture tex
	;2nd ClassD
	tex = LoadTexture_Strict("GFX\npcs\classd2.jpg")
	EntityTexture DTextures[7],tex
	FreeTexture tex
	;035 victim
	tex = LoadTexture_Strict("GFX\npcs\035victim.jpg")
	EntityTexture DTextures[8],tex
	FreeTexture tex
	;room2nuke body
	tex = LoadTexture_Strict("GFX\npcs\body2.jpg")
	EntityTexture DTextures[9],tex
	FreeTexture tex
	;intro3npcs
	tex = LoadTexture_Strict("GFX\npcs\classd3.jpg")
	EntityTexture DTextures[10], tex
	FreeTexture tex
	;room4tunnels body
	tex = LoadTexture_Strict("GFX\npcs\body1.jpg")
	EntityTexture DTextures[11], tex
	FreeTexture tex
	;clerk
	;tex = LoadTexture_Strict("GFX\npcs\clerk_d1.jpg")
	;EntityTexture DTextures[12], tex
	;FreeTexture tex
	;[End Block]
	
	LoadMaterials("Data\materials.ini")
	
	OBJTunnel(0)=LoadRMesh("GFX\map\mt1.rmesh",Null)	
	HideEntity OBJTunnel(0)				
	OBJTunnel(1)=LoadRMesh("GFX\map\mt2.rmesh",Null)	
	HideEntity OBJTunnel(1)
	OBJTunnel(2)=LoadRMesh("GFX\map\mt2c.rmesh",Null)	
	HideEntity OBJTunnel(2)				
	OBJTunnel(3)=LoadRMesh("GFX\map\mt3.rmesh",Null)	
	HideEntity OBJTunnel(3)	
	OBJTunnel(4)=LoadRMesh("GFX\map\mt4.rmesh",Null)	
	HideEntity OBJTunnel(4)				
	OBJTunnel(5)=LoadRMesh("GFX\map\mt_elevator.rmesh",Null)
	HideEntity OBJTunnel(5)
	OBJTunnel(6)=LoadRMesh("GFX\map\mt_generator.rmesh",Null)
	HideEntity OBJTunnel(6)
	
	;TextureLodBias TextureBias
	TextureLodBias TextureFloat#
	;Devil Particle System
	;ParticleEffect[] numbers:
	;	0 - electric spark
	;	1 - smoke effect
	
	Local t0
	
	InitParticles(Camera)
	
	;Spark Effect (short)
	ParticleEffect[0] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[0], 3)
	SetTemplateInterval(ParticleEffect[0], 1)
	SetTemplateParticlesPerInterval(ParticleEffect[0], 6)
	SetTemplateEmitterLifeTime(ParticleEffect[0], 6)
	SetTemplateParticleLifeTime(ParticleEffect[0], 20, 30)
	SetTemplateTexture(ParticleEffect[0], "GFX\Spark.png", 2, 3)
	SetTemplateOffset(ParticleEffect[0], -0.1, 0.1, -0.1, 0.1, -0.1, 0.1)
	SetTemplateVelocity(ParticleEffect[0], -0.0375, 0.0375, -0.0375, 0.0375, -0.0375, 0.0375)
	SetTemplateAlignToFall(ParticleEffect[0], True, 45)
	SetTemplateGravity(ParticleEffect[0], 0.001)
	SetTemplateAlphaVel(ParticleEffect[0], True)
	;SetTemplateSize(ParticleEffect[0], 0.0625, 0.125, 0.7, 1)
	SetTemplateSize(ParticleEffect[0], 0.03125, 0.0625, 0.7, 1)
	SetTemplateColors(ParticleEffect[0], $0000FF, $6565FF)
	SetTemplateFloor(ParticleEffect[0], 0.0, 0.5)
	
	;Smoke effect (for some vents)
	ParticleEffect[1] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[1], 1)
	SetTemplateInterval(ParticleEffect[1], 1)
	SetTemplateEmitterLifeTime(ParticleEffect[1], 3)
	SetTemplateParticleLifeTime(ParticleEffect[1], 30, 45)
	SetTemplateTexture(ParticleEffect[1], "GFX\smoke2.png", 2, 1)
	;SetTemplateOffset(ParticleEffect[1], -.3, .3, -.3, .3, -.3, .3)
	SetTemplateOffset(ParticleEffect[1], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
	;SetTemplateVelocity(ParticleEffect[1], -.04, .04, .1, .2, -.04, .04)
	SetTemplateVelocity(ParticleEffect[1], 0.0, 0.0, 0.02, 0.025, 0.0, 0.0)
	SetTemplateAlphaVel(ParticleEffect[1], True)
	;SetTemplateSize(ParticleEffect[1], 3, 3, .5, 1.5)
	SetTemplateSize(ParticleEffect[1], 0.4, 0.4, 0.5, 1.5)
	SetTemplateSizeVel(ParticleEffect[1], .01, 1.01)
	
	;Smoke effect (for decontamination gas)
	ParticleEffect[2] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[2], 1)
	SetTemplateInterval(ParticleEffect[2], 1)
	SetTemplateEmitterLifeTime(ParticleEffect[2], 3)
	SetTemplateParticleLifeTime(ParticleEffect[2], 30, 45)
	SetTemplateTexture(ParticleEffect[2], "GFX\smoke.png", 2, 1)
	SetTemplateOffset(ParticleEffect[2], -0.1, 0.1, -0.1, 0.1, -0.1, 0.1)
	SetTemplateVelocity(ParticleEffect[2], -0.005, 0.005, 0.0, -0.03, -0.005, 0.005)
	SetTemplateAlphaVel(ParticleEffect[2], True)
	SetTemplateSize(ParticleEffect[2], 0.4, 0.4, 0.5, 1.5)
	SetTemplateSizeVel(ParticleEffect[2], .01, 1.01)
	SetTemplateGravity(ParticleEffect[2], 0.005)
	t0 = CreateTemplate()
	SetTemplateEmitterBlend(t0, 1)
	SetTemplateInterval(t0, 1)
	SetTemplateEmitterLifeTime(t0, 3)
	SetTemplateParticleLifeTime(t0, 30, 45)
	SetTemplateTexture(t0, "GFX\smoke2.png", 2, 1)
	SetTemplateOffset(t0, -0.1, 0.1, -0.1, 0.1, -0.1, 0.1)
	SetTemplateVelocity(t0, -0.005, 0.005, 0.0, -0.03, -0.005, 0.005)
	SetTemplateAlphaVel(t0, True)
	SetTemplateSize(t0, 0.4, 0.4, 0.5, 1.5)
	SetTemplateSizeVel(t0, .01, 1.01)
	SetTemplateGravity(ParticleEffect[2], 0.005)
	SetTemplateSubTemplate(ParticleEffect[2], t0)

	;LoadRoomMeshes()
	
	;CatchErrors("LoadEntities")
End Function

Function LoadNecessaryEntities()
	Delete Each breachtypes
	Collider = CreatePivot()
	EntityType Collider, HIT_PLAYER
	EntityRadius Collider, 0.32
	
	Head = CreatePivot()
	EntityType Collider, HIT_PLAYER
	EntityRadius Collider, 0.15
	
	OBJECT_173 = LoadAnimMesh_Strict("GFX\npcs\173_2.b3d")
	MTFObj = LoadAnimMesh_Strict("GFX\npcs\MTF2.b3d") ;optimized MTFs
	GuardObj = LoadAnimMesh_Strict("GFX\npcs\guard.b3d") ;optimized Guards
	ApacheObj = LoadAnimMesh_Strict("GFX\apache.b3d") ;optimized Apaches (helicopters)
	ApacheRotorObj = LoadAnimMesh_Strict("GFX\apacherotor.b3d") ;optimized the Apaches even more
	ClassDObj = LoadAnimMesh_Strict("GFX\npcs\classd.b3d") ;optimized Class-D's and scientists/researchers
	NTF_OBJECT = LoadAnimMesh_Strict("GFX\npcs\MTFMP.b3d")
	HAOS_OBJECT = LoadanimMesh_Strict("GFX\multiplayer\game\models\CI.b3d")
	GUARD_OBJECT = LoadAnimMesh_Strict("GFX\npcs\guardMP.b3d")

	NPC049OBJ = LoadAnimMesh_Strict("GFX\npcs\scp-049.b3d")
	NPC0492OBJ = LoadAnimMesh_Strict("GFX\npcs\zombie1.b3d")
	ClerkOBJ = LoadAnimMesh_Strict("GFX\npcs\clerk.b3d")
	ClerkMP = LoadAnimMesh_Strict("GFX\npcs\clerkMP.b3d")
	Obj939 = LoadAnimMesh_Strict("GFX\NPCs\scp-939.b3d")
	HAZMAT_OBJECT = LoadAnimMesh_Strict("GFX\multiplayer\game\models\hazmat.b3d")
	NPC106Obj = LoadAnimMesh_Strict("GFX\npcs\106_2.b3d")
	NPC966Obj = LoadAnimMesh_Strict("GFX\npcs\scp-966.b3d")
	SURGEON_ZOMBIE = LoadAnimMesh_Strict("GFX\npcs\zombiesurgeon.b3d")
	OBJECT_096 = LoadAnimMesh_Strict("GFX\npcs\scp096.b3d")
	GRENADE_OBJECT = LoadMesh_Strict("GFX\items\grenadeworldmodel.b3d")
	
	HideEntity Obj939
	HideEntity NPC049OBJ
	HideEntity NPC0492OBJ
	HideEntity ClerkOBJ	
	HideEntity ClerkMP
	HideEntity OBJECT_173
	HideEntity GRENADE_OBJECT
	HideEntity ClassDObj
	HideEntity MTFObj
	HideEntity GuardObj
	HideEntity ApacheObj
	HideEntity ApacheRotorObj
	HideEntity NTF_OBJECT
	HideEntity HAOS_OBJECT
	HideEntity GUARD_OBJECT
	HideEntity HAZMAT_OBJECT
	HideEntity NPC106Obj
	HideEntity NPC966Obj
	HideEntity SURGEON_ZOMBIE
	HideEntity OBJECT_096
	
	LAST_BREACH_TYPE = 0
	multiplayer_breach_CreatePlayerRole("Spectator", ClassDObj, 0.5 / MeshWidth(ClassDObj), "",192,192,192)
	NTF_MODEL = multiplayer_breach_CreatePlayerRole("MTF", NTF_OBJECT, 0.3 / 2.7, "", 0,0,255, 150)
	GUARD_MODEL = multiplayer_breach_CreatePlayerRole("Guard", GUARD_OBJECT, 0.27 / 2.5, "" ,0,0,255, 120)
	CLASSD_MODEL = multiplayer_breach_CreatePlayerRole("Class D", ClassDObj, 0.5 / MeshWidth(ClassDObj), "" ,212,113,0, 100)
	SCIENTIST_MODEL = multiplayer_breach_CreatePlayerRole("Scientist", ClassDObj, 0.5 / MeshWidth(ClassDObj), "GFX\npcs\scientist.jpg",255,255,255, 100)
	MODEL_173 = multiplayer_breach_CreatePlayerRole("SCP-173", OBJECT_173, 0.35 / MeshDepth(OBJECT_173), "" ,255,0,0, 5000)
	MODEL_049 = multiplayer_breach_CreatePlayerRole("SCP-049", NPC049OBJ, 1.2, "",255,0,0, 5000)
	HAOS_MODEL = multiplayer_breach_CreatePlayerRole("Chaos Soldier", HAOS_OBJECT, 0.515 / MeshWidth(HAOS_OBJECT), "",26,64,1, 150)
	JANITOR_MODEL = multiplayer_breach_CreatePlayerRole("Janitor", ClassDObj, 0.5 / MeshWidth(ClassDObj), "GFX\npcs\janitor.jpg",199,219,252, 100)
	WORKER_MODEL = multiplayer_breach_CreatePlayerRole("Janitor", ClassDObj, 0.5 / MeshWidth(ClassDObj), "GFX\npcs\janitor2.png",199,219,252, 100)
	MODEL_939 = multiplayer_breach_CreatePlayerRole("SCP-939", Obj939, 0.5/2.5, "",255,0,0, 2000)
	MODEL_106 = multiplayer_breach_CreatePlayerRole("SCP-106", NPC106Obj, 0.25/2.2, "",255,0,0, 5000)
	MODEL_966 = multiplayer_breach_CreatePlayerRole("SCP-966", NPC966Obj, 0.011, "",255,0,0, 2000)
	MODEL_ZOMBIE = multiplayer_breach_CreatePlayerRole("SCP-049-2", SURGEON_ZOMBIE, 0.5 / MeshWidth(SURGEON_ZOMBIE), "",255,0,0, 300)
	MODEL_096 = multiplayer_breach_CreatePlayerRole("SCP-096", OBJECT_096, 0.6 / 3.0, "",255,0,0, 2000)
	MODEL_860 = multiplayer_breach_CreatePlayerRole("SCP-860-2", OBJECT_860, 0.02, "",255,0,0, 2000)
	MODEL_035 = multiplayer_breach_CreatePlayerRole("SCP-035", OBJECT_035, 0.5 / MeshWidth(ClassDObj),"",255,0,0, 3000)
	MODEL_CLERK = multiplayer_breach_CreatePlayerRole("Clerk", ClerkMP, 0.5 / MeshWidth(ClerkMP), "",255,255,255, 100) ;newcode
	MODEL_WAIT = multiplayer_breach_CreatePlayerRole("", ClassDObj, 0.5 / MeshWidth(ClassDObj), "",255,255,255, 100)
	;local temprole = multiplayer_breach_CreatePlayerRole("SCP-049-2", ClassDObj, 0.5 / MeshWidth(ClassDObj),"GFX\npcs\corpse.jpg",255,0,0, 500)
	
	multiplayer_breach_MarkRoleAsSCP(MODEL_173)
	multiplayer_breach_MarkRoleAsSCP(MODEL_049)
	multiplayer_breach_MarkRoleAsSCP(MODEL_939)
	multiplayer_breach_MarkRoleAsSCP(MODEL_966)
	multiplayer_breach_MarkRoleAsSCP(MODEL_106)
	multiplayer_breach_MarkRoleAsSCP(MODEL_ZOMBIE)
	multiplayer_breach_MarkRoleAsSCP(MODEL_096)
	multiplayer_breach_MarkRoleAsSCP(MODEL_860)
	;multiplayer_breach_MarkRoleAsSCP(temprole)
	
	multiplayer_breach_SetRoleSettings(MODEL_173, True, 2, 100, 1.0, True, "", 0) ; just marking for allowing scp update
	multiplayer_breach_SetRoleSettings(MODEL_096, True, 2, 500, 1.0, True, "", 0) ; just marking for allowing scp update
	
	multiplayer_breach_SetRoleSettings(MODEL_049, True, 2, 8000, 1.0, False, "SFX\SCP\049\Searching.ogg", 10000, 6)
	multiplayer_breach_SetRoleSettings(MODEL_106, True, 2, 3000, 1.0, False, "SFX\SCP\106\Laugh.ogg", 10000)
	multiplayer_breach_SetRoleSettings(MODEL_035, True, 1, 1000, 1.0, True, "", 0)
	multiplayer_breach_SetRoleSettings(MODEL_966, True, 1, 1000, 1.0, True, "SFX\SCP\966\Echo.ogg", 10000, 3)
	multiplayer_breach_SetRoleSettings(MODEL_860, True, 1, 2000, 1.5, True, "SFX\SCP\860\Cancer.ogg", 10000, 5)
	multiplayer_breach_SetRoleSettings(MODEL_939, True, 1, 2000, 1.5, True, "SFX\SCP\939\0Lure.ogg", 10000, 10)
	multiplayer_breach_SetRoleSettings(MODEL_ZOMBIE, True, 1, 1500, 1.0, True, "SFX\SCP\049\0492Breath.ogg", 3000)
	;multiplayer_breach_SetRoleSettings(temprole, True, 1, 1500, 1.0, True, "SFX\SCP\049\0492Breath.ogg", 3000)
	
	multiplayer_breach_SetRoleEffects(MODEL_966, 0.0, 0, 0, False, True, False, True, False, True, True)
	multiplayer_breach_SetRoleEffects(MODEL_173, 0.08, 0, 0, False, False, False, True, False, False, True)
	multiplayer_breach_SetRoleEffects(MODEL_939, -0.1, 0, 0, False, True, True, True, False, False, False)
	multiplayer_breach_SetRoleEffects(MODEL_860, -0.1, 0, 0, False, True, True, True, False, True, False)
	multiplayer_breach_SetRoleEffects(MODEL_049, 0, 0, -180, True, True, False, True, False, True, True)
	multiplayer_breach_SetRoleEffects(MODEL_106, 0, 0, -180, True, True, False, True, False, True, True)
	multiplayer_breach_SetRoleEffects(MODEL_096, 0, 0, -180, True, True, False, True, False, False, False)
	multiplayer_breach_SetRoleEffects(MODEL_ZOMBIE, 0, 0, 0, False, True, True, True, False, False, True)
	multiplayer_breach_SetRoleEffects(GUARD_MODEL, 0.0, 0, -180, True, True, False, False, False, False, False)
	;multiplayer_breach_SetRoleEffects(temprole, 0, 0, 0, False, True, True, False, False, False, True)
	
	multiplayer_breach_SetRoleAmbientSound(MODEL_096, PLAYER_RUNNING, "SFX\SCP\096\Scream.ogg")
	multiplayer_breach_SetRoleAmbientSound(MODEL_049, 0, "SFX\Horror\Horror12.ogg")
	multiplayer_breach_SetRoleAmbientSound(MODEL_049, PLAYER_WALKING, "SFX\Music\049Chase.ogg")
	
	multiplayer_breach_SetRoleHitboxScales(0, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(CLASSD_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(GUARD_MODEL, 0.15, 0.6, 0.15)
	multiplayer_breach_SetRoleHitboxScales(SCIENTIST_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(NTF_MODEL, 0.15, 0.55, 0.15)
	multiplayer_breach_SetRoleHitboxScales(HAOS_MODEL, 0.15, 0.55, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_173, 0.15, 0.67, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_049, 0.15, 0.58, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_939, 0.5, 0.3, 0.5)
	multiplayer_breach_SetRoleHitboxScales(MODEL_966, 0.15, 0.6, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_106, 0.15, 0.55, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_ZOMBIE, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_096, 0.15, 0.7, 0.15)
	multiplayer_breach_SetRoleHitboxScales(JANITOR_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(WORKER_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_860, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_035, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_CLERK, 0.15, 0.4, 0.15) ;newcode
	multiplayer_breach_SetRoleHitboxScales(MODEL_WAIT, 0.15, 0.52, 0.15)
	;multiplayer_breach_SetRoleHitboxScales(temprole, 0.15, 0.52, 0.15)
	
	multiplayer_breach_SetRoleInstruction(NTF_MODEL, "Your task is to evacuate scientists.")
	multiplayer_breach_SetRoleInstruction(GUARD_MODEL, "Your task is to evacuate scientists.")
	multiplayer_breach_SetRoleInstruction(HAOS_MODEL, "Your task is to evacuate the Class D")
	multiplayer_breach_SetRoleInstruction(CLASSD_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(SCIENTIST_MODEL, "Escape from the facility")
	multiplayer_breach_SetRoleInstruction(MODEL_CLERK, "Escape from the facility") ;newcode
	multiplayer_breach_SetRoleInstruction(JANITOR_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(WORKER_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(MODEL_173, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_939, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_106, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_966, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_096, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_860, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_035, "You can kill anyone or teaming with somebody. You also can take items") 
	multiplayer_breach_SetRoleInstruction(MODEL_049, "Cure everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_ZOMBIE, "Follow instructions from SCP-049 and kill everyone.")
	;multiplayer_breach_SetRoleInstruction(temprole, "Follow instructions from SCP-049 and kill everyone. You're mega version.")
	
	For i = 0 To 18
		Select i
			Case CLASSD_MODEL,SCIENTIST_MODEL,MODEL_CLERK,WORKER_MODEL,JANITOR_MODEL,MODEL_WAIT,MODEL_035;,temprole ;newcode
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_IDLING, 357, 381, 0.1)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_IDLING, 210, 235, 0.1)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_RUNNING, 301, 319, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_WALKING, 236, 260, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_FORWARD, 382, 406, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_BACK, 382, 406, -0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_JUMPING, 834, 894, 1)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_LEFT, 261, 280, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_RIGHT, 281, 300, 0.3)
				
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_IDLING, 733, 757, 0.1)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_IDLING, 573, 595, 0.1)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_RUNNING, 664, 682, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_WALKING, 599, 623, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_FORWARD, 758, 782, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_BACK, 758, 782, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_JUMPING, 834, 894, 1)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_LEFT, 758, 782, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_RIGHT, 758, 782, 0.3)
		End Select
	Next
	
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_CRYING, 737, 822, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_SITTING_IDLING, 423, 471, 0.2)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_IDLING, 423, 471, 0.2)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_WALKING, 1383, 1456, 0.9)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_RUNNING, 907, 935, 0.9)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_JUMPING, 2, 26, 0.7)
		
	multiplayer_breach_SetRoleAnimation(MODEL_ZOMBIE, PLAYER_SITTING_IDLING, 323, 344, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_ZOMBIE, PLAYER_IDLING, 323, 344, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_ZOMBIE, PLAYER_WALKING, 96, 125, 0.6)

	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_JUMPING, 1484, 1518, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 7, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 8, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 9, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 10, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_SITTING_IDLING, 1519,1520, 0.01)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_WALKING, 1484,1518, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_RUNNING, 1484,1518, 0.6)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_IDLING, 1480,1480, 0.01)
	
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_JUMPING, 488, 522, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 7, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 8, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 9, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 10, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_SITTING_IDLING, 1521,1522, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_IDLING, 488, 488, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_WALKING, 488, 522, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_RUNNING, 488, 522, 0.6)

	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_JUMPING, 750,786, 0.4)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 7, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 8, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 9, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 10, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_SITTING_IDLING, 864,864, 0.01)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_WALKING, 750,786, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_RUNNING, 750,786, 0.6)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_IDLING, 700,748, 0.01)
	
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_JUMPING, 39,76, 0.6)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 7, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 8, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 9, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 10, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_SITTING_IDLING, 866,866, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_IDLING, 245,245, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_WALKING, 39,76, 0.6)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_RUNNING, 39,76, 0.6)
	
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_SITTING_IDLING, 270,345, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_IDLING, 270,345, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_WALKING, 428,463, 0.35)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_BITE, 538, 660, 1.5)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_BITE_STOP, 538, 660, -1)

	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_JUMPING, 380,408, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 7, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 8, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 9, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 10, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_SITTING_IDLING, 509,509, 0.01)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_WALKING, 380,408, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_RUNNING, 380,408, 0.6)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_IDLING, 410,500, 0.01)
	
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_IDLING, 183,218, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 7, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 8, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 9, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 10, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_SITTING_IDLING, 551,551, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_IDLING, 4,170, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_WALKING, 183,218, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_RUNNING, 183,218, 0.6)

	multiplayer_breach_SetRoleAnimation(MODEL_106, PLAYER_SITTING_IDLING, 280, 280, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_106, PLAYER_IDLING, 280, 280, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_106, PLAYER_WALKING, 284, 333, 0.3)

	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_BITE, 18,68, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_SITTING_IDLING, 445, 445, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_IDLING, 445, 445, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_WALKING, 449,464, 0.1)

	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_BITE, 451,493, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_SITTING_IDLING, 200, 297, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_IDLING, 200, 297, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 7, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 8, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 9, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 10, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_WALKING, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_RUNNING, 298,316, 0.7)

	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_BITE, 488, 517, 0.5)
	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_SITTING_IDLING, 2, 72, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_IDLING, 2, 72, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_WALKING, 580,628, 0.3)
	
	multiplayer_breach_SetRoleDeadAnimation(SCIENTIST_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_CLERK, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_939, 70,164)
	multiplayer_breach_SetRoleDeadAnimation(CLASSD_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(JANITOR_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_035, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(WORKER_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(GUARD_MODEL, 249,285)
	multiplayer_breach_SetRoleDeadAnimation(NTF_MODEL, 2,26)
	multiplayer_breach_SetRoleDeadAnimation(HAOS_MODEL, 593,640)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_ZOMBIE, 344,363)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_173, 1,40)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_106, 496,526)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_860, 570,585)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_049, 1123,1163)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_096, 1472,1502)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_966, 753,791)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_WAIT, 0, 19)
	
	multiplayer_breach_SetRolePositionsOffset(MODEL_966, 0.2, 0, -90)
	multiplayer_breach_SetRolePositionsOffset(MODEL_106, 0.3, -180, 0)
	multiplayer_breach_SetRolePositionsOffset(NTF_MODEL, 0.3, 0, -90)
	multiplayer_breach_SetRolePositionsOffset(GUARD_MODEL, 0.3, -180, 0)
	multiplayer_breach_SetRolePositionsOffset(MODEL_049, 0.3, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(HAOS_MODEL, 0.3, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(MODEL_939, 0.3, 0, -90)
	multiplayer_breach_SetRolePositionsOffset(MODEL_096, 0.03, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(MODEL_860, 0.3, 0, -90)

	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_HEAD,"head")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_HEAD, "Head")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_HEAD, "NeckHead")
	multiplayer_breach_SetRoleBone(MODEL_049, BONE_HEAD, "Bone_019")
	multiplayer_breach_SetRoleBone(MODEL_106, BONE_HEAD, "Bone_022")
	multiplayer_breach_SetRoleBone(MODEL_939, BONE_HEAD, "Bone_004")
	multiplayer_breach_SetRoleBone(MODEL_966, BONE_HEAD, "face")
	multiplayer_breach_SetRoleBone(MODEL_096, BONE_HEAD, "head")
	multiplayer_breach_SetRoleBone(MODEL_860, BONE_HEAD, "Bone_006")

	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_HAND, "hand_R")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_HAND, "Thumb01.R.001")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_HAND, "M4MB")
	multiplayer_breach_SetRoleBone(MODEL_096, BONE_HAND, "Bone_013")

	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_SPINE, "spine")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_SPINE, "Chest")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_SPINE, "Spine2")
	
	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_FOREARM, "forearm_R")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_FOREARM, "Lower Arm.R")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_FOREARM, "RArmForearm1")

	For i = 0 To 17
		Select i
			Case CLASSD_MODEL,SCIENTIST_MODEL,MODEL_CLERK,WORKER_MODEL,JANITOR_MODEL,MODEL_WAIT,MODEL_035 ;newcode
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
		End Select
	Next
	
	multiplayer_breach_AllowRoleWeaponAttaches(NTF_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(GUARD_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(HAOS_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(MODEL_035, True)
	multiplayer_breach_AllowRoleWeaponAttaches(CLASSD_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(JANITOR_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(WORKER_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(SCIENTIST_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(MODEL_CLERK, True) ;newcode
	multiplayer_breach_AllowRoleWeaponAttaches(MODEL_WAIT, True)

	multiplayer_breach_AllowItemsAttaches(CLASSD_MODEL, True)
	multiplayer_breach_AllowItemsAttaches(SCIENTIST_MODEL, True)
	multiplayer_breach_AllowItemsAttaches(MODEL_CLERK, True) ;newcode
	multiplayer_breach_AllowItemsAttaches(JANITOR_MODEL, True)
	multiplayer_breach_AllowItemsAttaches(WORKER_MODEL, True)
	multiplayer_breach_AllowItemsAttaches(MODEL_035, True)

	For i = 0 To 17
		Select i
			; ========= SCPS
			Case MODEL_173,MODEL_106,MODEL_939,MODEL_966,MODEL_049,MODEL_ZOMBIE,MODEL_096,MODEL_860,MODEL_035,0
				For a = 0 To 17
					Select a
						Case MODEL_173,MODEL_106,MODEL_939,MODEL_966,MODEL_049,MODEL_ZOMBIE,MODEL_096,MODEL_860,MODEL_035,0
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
			; ========= CLASS D
			Case CLASSD_MODEL,HAOS_MODEL
				For a = 0 To 17
					Select a
						Case CLASSD_MODEL,HAOS_MODEL
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
			; ========= SECURITY
			Case NTF_MODEL,GUARD_MODEL,JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
				For a = 0 To 17
					Select a
						Case NTF_MODEL,GUARD_MODEL,JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
			; ========= STAFF
			Case JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
				For a = 0 To 17
					Select a
						Case JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
		End Select
	Next
	
	multiplayer_breach_AllowRoleGenerate(NTF_MODEL, False)
	multiplayer_breach_AllowRoleGenerate(HAOS_MODEL, False)
	multiplayer_breach_AllowRoleGenerate(MODEL_ZOMBIE, False)
	multiplayer_breach_AllowRoleGenerate(MODEL_035, False)
	multiplayer_breach_AllowRoleGenerate(MODEL_WAIT, False)
	multiplayer_breach_AllowRoleGenerate(0, False)
	
	multiplayer_breach_SetMaxRoleCount(MODEL_173, 1)
	multiplayer_breach_SetMaxRoleCount(MODEL_049, 1)
	multiplayer_breach_SetMaxRoleCount(MODEL_939, 1)
	multiplayer_breach_SetMaxRoleCount(MODEL_966, 1)
	multiplayer_breach_SetMaxRoleCount(MODEL_106, 1)
	multiplayer_breach_SetMaxRoleCount(MODEL_096, 1)
	multiplayer_breach_SetMaxRoleCount(MODEL_860, 1)
	;multiplayer_breach_SetMaxRoleCount(temprole, 1)
	
	multiplayer_breach_SetMaxRoleCount(GUARD_MODEL, 4)
	multiplayer_breach_SetMaxRoleCount(CLASSD_MODEL, 9999)
	multiplayer_breach_SetMaxRoleCount(JANITOR_MODEL, 3)
	multiplayer_breach_SetMaxRoleCount(WORKER_MODEL, 3)
	multiplayer_breach_SetMaxRoleCount(SCIENTIST_MODEL, 4)
	multiplayer_breach_SetMaxRoleCount(MODEL_CLERK, 4) ;newcode
	
	multiplayer_breach_SetRoleCategory(MODEL_173, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_049, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_106, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_939, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_966, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_ZOMBIE, CATEGORY_ANOMALY)
	;multiplayer_breach_SetRoleCategory(temprole, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_096, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_860, CATEGORY_ANOMALY)
	multiplayer_breach_SetRoleCategory(MODEL_035, CATEGORY_ANOMALY)
	
	multiplayer_breach_SetRoleCategory(GUARD_MODEL, CATEGORY_NTF)
	multiplayer_breach_SetRoleCategory(SCIENTIST_MODEL, CATEGORY_NTF)
	multiplayer_breach_SetRoleCategory(MODEL_CLERK, CATEGORY_NTF) ;newcode
	multiplayer_breach_SetRoleCategory(JANITOR_MODEL, CATEGORY_NTF)
	multiplayer_breach_SetRoleCategory(WORKER_MODEL, CATEGORY_NTF)
	multiplayer_breach_SetRoleCategory(CLASSD_MODEL, CATEGORY_CHAOS)

	multiplayer_breach_SetRoleCategory(0,CATEGORY_NULL)
	multiplayer_breach_SetRoleCategory(NTF_MODEL, CATEGORY_NTF)
	multiplayer_breach_SetRoleCategory(HAOS_MODEL, CATEGORY_CHAOS)
	multiplayer_breach_SetRoleCategory(MODEL_WAIT, CATEGORY_STALEMATE)
	
	For br.breachtypes = Each breachtypes
		multiplayer_breach_SetRoleSpeedMult(br\constid, GetINIFloat("ServerConfig\roles.ini",Lower(br\name),"speedmult", 1.0))
		multiplayer_breach_SetRoleHealth(br\constid, GetINIInt("ServerConfig\roles.ini",Lower(br\name),"health", -1))
		For i = 1 To 10
			multiplayer_breach_SetRoleItem(br\constid, GetINIString("ServerConfig\roles.ini",Lower(br\name),"item"+i, "null"), i-1)
		Next
	Next
	
	For i = 1 To GUN_GRENADESMOKE
		GunDamage[i] = GetIniInt("ServerConfig\guns.ini", GetGunName(i), "damage", 25)
		GunShootTicks[i] = GetIniInt("ServerConfig\guns.ini", GetGunName(i), "shootticks", 1)
		GunSpread[i] = GetIniFloat("ServerConfig\guns.ini", GetGunName(i), "spread", 1.0)
	Next
	
	Server\FallDamage = GetINIInt("ServerConfig\game.ini", "player", "falldamage", 1)
	Server\Prediction = 0;GetINIInt("ServerConfig\game.ini", "player", "prediction", 1)
	Server\Interpolation = 0;GetINIInt("ServerConfig\game.ini", "player", "interpolation", 1)
	
	Server\BulletAnticheat = GetINIInt("ServerConfig\game.ini", "server", "throughwallsbulletanticheat", 1)
	Framelimit = GetINIInt("ServerConfig\game.ini", "server", "framelimit", 300)
	FPSFactorLimit = GetINIFloat("ServerConfig\game.ini", "server", "deltalimit", 5.0)
	Server\Pickitems = GetINIInt("ServerConfig\game.ini", "server", "itemsraycastcollision", 0)
	FixedTimeSteps = GetINIInt("ServerConfig\game.ini", "server", "fixedtimesteps", 0)
	Server\MaxSpawnPlayers = GetINIInt("ServerConfig\game.ini", "server", "maxplayersspawn", 8)
	Server\friendlyfire = GetINIInt("ServerConfig\game.ini", "server", "friendlyfire", 0)
	Server\friendlyfireexplosion = GetINIInt("ServerConfig\game.ini", "server", "friendlyfireexplosion", 1)
	Server\ItemsMoveOnCollision = GetINIInt("ServerConfig\game.ini", "server", "itemsmoveoncollision", 0)
	ClearCollisions()
	Collisions HIT_PLAYER, HIT_MAP, 2, 2
	Collisions HIT_PLAYER, HIT_DOOR, 2, 2
	If GetINIInt("ServerConfig\game.ini", "player", "playertoplayercollision", 1)+Server\Prediction > 0 Then Collisions HIT_PLAYER, HIT_PLAYER, 1, 3
	Collisions HIT_ITEM, HIT_MAP, 2, 2
	Collisions HIT_ITEM, HIT_DOOR, 2, 2
	Collisions HIT_APACHE, HIT_APACHE, 1, 2
	Collisions HIT_178, HIT_MAP, 2, 2
	Collisions HIT_178, HIT_178, 1, 3
	Collisions HIT_DEAD, HIT_MAP, 2, 2
	Collisions HIT_DEAD, HIT_DOOR, 2, 2
	Collisions HIT_GRENADE, HIT_MAP, 2, 2
	Collisions HIT_GRENADE, HIT_DOOR, 2, 2
	Collisions HIT_GRENADE, HIT_PLAYER, 1, 2
	Collisions HIT_PLAYER, HIT_INVISIBLEWALL, 2, 2

	Collisions HIT_OLDMAN, HIT_MAP, 2, 2
	Collisions HIT_OLDMAN, HIT_PLAYER, 1, 3
	Collisions HIT_GRENADE, HIT_OLDMAN, 1, 2
	Collisions HIT_OLDMAN, HIT_INVISIBLEWALL, 2, 2
	
	;if (not server\interpolation) and (not server\prediction) then server\fullsync = false
	
	;debuglog "fullsync: "+server\fullsync
End Function

Function multiplayer_breach_CreatePlayerRole%(name$, model, scale#, texture$, r,g,b, hp=0)
	Local br.breachtypes = New breachtypes
	br\name = name
	br\constid = LAST_BREACH_TYPE
	br\model = model
	br\scale = scale
	br\r = r
	br\g = g
	br\b = b
	br\texture = texture
	br\consthp = hp
	
	; defaults
	
	br\BoneName[BONE_HEAD] = "Bip01_Head"
	br\BoneName[BONE_HAND] = "FIRESPOT"
	br\BoneName[BONE_SPINE] = "Bip01_Spine1"
	br\BoneName[BONE_SPINE2] = "Bip01_Spine2"
	br\BoneName[BONE_FOREARM] = "Bip01_R_Forearm"
	br\AllowJump = True
	br\AllowToGenerate = True
	br\CopiedGroup = br\constid
	br\CopiedSpawn = br\constid
	br\maxrolecount = 1
	;
	
	BreachTypesArray[br\constid] = br
	
	LAST_BREACH_TYPE = LAST_BREACH_TYPE+1
	Return br\constid
End Function

Function multiplayer_breach_SetRoleSpeedMult(constid, speedmult#)
	Local br.BreachTypes = GetBreachType(constid)
	br\speedmult = speedmult
End Function

Function multiplayer_breach_SetRoleHealth(constid, hp)
	If hp <> -1 Then
		Local br.BreachTypes = GetBreachType(constid)
		br\consthp = hp
	EndIf
End Function

Function multiplayer_breach_SetRoleItem(constid, item$, id)
	If item = "null" Then
		; Set standart items
		Select constid
			Case MODEL_860
				Select id
					Case 0
						item = "Level 3 Key Card/key3"
				End Select
		End Select
	EndIf
	
	Local br.BreachTypes = GetBreachType(constid)
	br\SpawnItem[id] = item
End Function

Function mp_Breach_GetName$(role)
	Local BT.BreachTypes = GetBreachType(role)
	Return BT\name
End Function

Function multiplayer_breach_SetRoleCategory(role, category)
	Local BT.BreachTypes = GetBreachType(role)
	BT\categoryimportance = category
End Function

Function breach_GetCategoryColor(category)
	For br.breachtypes = Each breachtypes
		If br\categoryimportance = category Then
			Color br\r, br\g, br\b
			Exit
		EndIf
	Next
End Function

Function breach_getcategoryname$(category)
	Select category
	Case CATEGORY_ANOMALY
		Return "SCPs"
	Case CATEGORY_SECURITY,CATEGORY_NTF
		Return "Security"
	Case CATEGORY_TRASH,CATEGORY_CHAOS
		Return "Class D"
	Case CATEGORY_STAFF
		Return "Staff"
	Case CATEGORY_STALEMATE
		Return "Stalemate"
	End Select
	Return ""
End Function

Function breach_getcategoryid(category$)
	Select category
	Case "SCPs"
		Return CATEGORY_ANOMALY
	Case "Security"
		Return CATEGORY_SECURITY
	Case "Class D"
		Return CATEGORY_TRASH
	Case "Staff"
		Return CATEGORY_STAFF
	End Select
	Return 0
End Function

Function GetBreachType.breachtypes(id)
	If BreachTypesArray[id] = Null Then Return BreachTypesArray[CLASSD_MODEL]
	Return BreachTypesArray[id]
End Function

Function multiplayer_breach_SetMaxRoleCount(role%, rolecount%)
	Local BT.breachtypes = GetBreachType(role)
	BT\MaxRoleCount = rolecount
End Function

Function multiplayer_breach_AllowRoleGenerate(role%, Bool%)
	Local BT.breachtypes = GetBreachType(role)
	BT\AllowToGenerate = Bool
End Function

Function multiplayer_breach_SetRoleSettings(role%, allowupdate%, attacktype%, attackinterval, attackradius#, haveattacksound, sound$, soundinterval, randomsounds%=0)
	Local BT.breachtypes = GetBreachType(role)
	BT\AllowUpdate = allowupdate
	BT\AllowAttack = attacktype%
	BT\HitInterval = attackinterval
	BT\AttackRadius = attackradius
	BT\SoundInterval = soundinterval
	BT\Sound = sound
	BT\RandomSounds = randomsounds
	BT\AttackSound = haveattacksound
End Function

Function multiplayer_breach_SetRoleEffects(role%, cameraoffset#, boneheadroleoffset#, boneheadyawoffset#, flipboneheadpitch%, allowjump, usualsprint, disablecrouch, disablegodmode, disableinjuries, disablesprint)
	Local BT.breachtypes = GetBreachType(role)
	BT\AllowJump = allowjump
	BT\UsualSprint = usualsprint
	BT\DisableCrouch = disablecrouch
	BT\DisableGodmode = disablegodmode
	BT\DisableInjuries = disableinjuries
	BT\DisableSprint = disablesprint
	BT\CameraOffset = cameraoffset
	BT\rolloffset = boneheadroleoffset
	BT\RoleYawOffset = boneheadyawoffset
	BT\HeadPitchFlip = flipboneheadpitch
End Function

Function multiplayer_breach_SetRoleAmbientSound(role%, animationid%, sound$)
	Local BT.breachtypes = GetBreachType(role)
	BT\AmbientSoundAnims[animationid] = sound
End Function

Function multiplayer_breach_SetRoleInstruction(roleid, txt$)
	Local BT.breachtypes = GetBreachType(roleid)
	BT\Instructions = txt
End Function

Function multiplayer_breach_SetRoleDeadAnimation(role, FirstFrame, EndFrame)
	Local BT.breachtypes = GetBreachType(role)
	BT\DeadAnimationData = 0
End Function

Function multiplayer_breach_SetRoleBone(role, boneid, name$)
	Local BT.breachtypes = GetBreachType(role)
	BT\BoneName[boneid] = name
End Function

Function multiplayer_breach_SetRoleAnimation(role, animationid, FirstFrame, EndFrame, Speed#)
	Local BT.breachtypes = GetBreachType(role)
	BT\AnimationFrameData[animationid] = 0;PackTwoValues(FirstFrame, EndFrame)
	BT\AnimationSpeed[animationid] = Speed
End Function

Function multiplayer_breach_SetRoleArmedAnimation(role, animationid, FirstFrame, EndFrame, Speed#)
	Local BT.breachtypes = GetBreachType(role)
	BT\AnimationArmedFrameData[animationid] = 0;PackTwoValues(FirstFrame, EndFrame)
	BT\AnimationArmedSpeed[animationid] = Speed
End Function
Function multiplayer_breach_MarkAsFriend(role, friendrole, Bool=True)
	Local BT.breachtypes = GetBreachType(role)
	BT\IsAFriend[friendrole] = Bool
End Function
Function multiplayer_breach_RoleTakeRoleSpawn(myrole, role)
	Local BT.breachtypes = GetBreachType(myrole)
	BT\CopiedSpawn = role
End Function
Function multiplayer_breach_RoleTakeRoleGroup(myrole, role)
	Local BT.breachtypes = GetBreachType(myrole)
	BT\CopiedGroup = role
End Function
Function multiplayer_breach_SetRolePositionsOffset(role, FixPivot#, FixRotate#, FixPitch#)
	Local BT.breachtypes = GetBreachType(role)
	BT\FixPivot = FixPivot
	BT\FixRotate = FixRotate
	BT\FixPitch = FixPitchn
End Function
Function multiplayer_breach_SetRoleHoldingGrenade(role, bonename$, bonex#, boney#)
	Local BT.breachtypes = GetBreachType(role)
	BT\HoldingGrenade = (Float(bonex+boney)<>0.0)
	BT\HoldingGrenadeData[0] = bonename
	BT\HoldingGrenadeData[1] = Str(bonex)
	BT\HoldingGrenadeData[2] = Str(boney)
End Function
Function multiplayer_breach_SetRoleHoldingItem(role, bonex#, boney#)
	Local BT.breachtypes = GetBreachType(role)
	BT\HoldingItem = (Float(bonex+boney)<>0.0)
	BT\HoldingItemData[0] = bonex
	BT\HoldingItemData[1] = boney
End Function
Function multiplayer_breach_SetRoleHandcuff(role, bone1$, bonex1#, boney1#, bonez1#, bone2$, bonex2#, boney2#, bonez2#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\Handcuffs = (bone1<>"" Or bone2<>"")
	BT\HandCuffData[0] = bone1
	BT\HandCuffData[1] = Str(bonex1)
	BT\HandCuffData[2] = Str(boney1)
	BT\HandCuffData[3] = Str(bonez1)
	
	BT\HandCuffData[4] = bone2
	BT\HandCuffData[5] = Str(bonex2)
	BT\HandCuffData[6] = Str(boney2)
	BT\HandCuffData[7] = Str(bonez2)
End Function
Function multiplayer_breach_AllowRoleWeaponAttaches(role, bool)
	Local BT.BreachTypes = GetBreachType(role)
	BT\AllowWeaponsAttaches = bool
End Function
Function multiplayer_breach_AllowItemsAttaches(role, bool)
	Local BT.BreachTypes = GetBreachType(role)
	BT\AllowItemsAttaches = bool
End Function
Function multiplayer_breach_MarkRoleAsSCP(role, bool=True)
	Local BT.BreachTypes = GetBreachType(role)
	BT\IsASCP = bool
	If bool = True And BT\IsA035 Then BT\IsASCP = False
End Function
Function multiplayer_breach_MarkAs035(role, bool=True)
	Local BT.BreachTypes = GetBreachType(role)
	BT\IsA035 = bool
	If bool = True Then BT\IsASCP = False
End Function
Function multiplayer_breach_MarkAs049(role, bool=True)
	Local BT.BreachTypes = GetBreachType(role)
	BT\IsA049 = bool
End Function
Function multiplayer_breach_IsA049(role)
	Local BT.BreachTypes = GetBreachType(role)
	Return BT\IsA049
End Function
Function multiplayer_breach_IsA035(role)
	Local BT.BreachTypes = GetBreachType(role)
	Return BT\IsA035
End Function
Function multiplayer_breach_GetRoleScale#(role)
	Local BT.BreachTypes = GetBreachType(role)
	Return BT\Scale
End Function

Function multiplayer_IsAFriend(myrole, role)
	Local BT.BreachTypes = GetBreachType(myrole)
	Return BT\IsAFriend[role]
End Function

Function multiplayer_breach_SetRoleHitboxScales(constid, x#, y#, z#)
	Local BT.BreachTypes = GetBreachType(constid)
	BT\hitboxx = x
	BT\hitboxy = y
	BT\hitboxz = z
End Function
Function multiplayer_breach_GetMaxHP(constid)
	Local BT.BreachTypes = GetBreachType(constid)
	Return BT\ConstHP
End Function

Function GetTypeName$(bType)
	Local BT.BreachTypes = GetBreachType(bType)
	Return BT\Name
End Function

Function CheckTriggers$(e.Events)
	Local i%,sx#,sy#,sz#
	
	If e\room\TriggerboxAmount = 0
		Return ""
	Else
		For i = 0 To e\room\TriggerboxAmount-1

			EntityAlpha e\room\Triggerbox[i],0.0

			If EntityX(e\NearObject)>((e\room\TriggerBox_sx[i]*e\room\TriggerBox_Mesh_MinX[i])+e\room\x) And EntityX(e\NearObject)<((e\room\TriggerBox_sx[i]*e\room\TriggerBox_Mesh_MaxX[i])+e\room\x)
				If EntityY(e\NearObject)>((e\room\TriggerBox_sy[i]*e\room\TriggerBox_Mesh_MinY[i])+e\room\y) And EntityY(e\NearObject)<((e\room\TriggerBox_sy[i]*e\room\TriggerBox_Mesh_MaxY[i])+e\room\y)
					If EntityZ(e\NearObject)>((e\room\TriggerBox_sz[i]*e\room\TriggerBox_Mesh_MinZ[i])+e\room\z) And EntityZ(e\NearObject)<((e\room\TriggerBox_sz[i]*e\room\TriggerBox_Mesh_MaxZ[i])+e\room\z)
						Return e\room\TriggerboxName[i]
					EndIf
				EndIf
			EndIf
		Next
	EndIf
	
End Function
Type Corpses
	Field id%
	Field breachtype
	Field x#,y#,z#
	Field yaw
	Field timeout%
	Field playerscale
	Field FallFactor#
	
	Field IsCorpseTimedOut%, IsCorpseFalled%
End Type

Global M_Corpse.Corpses[MAX_CORPSES]

Function CreateRoleCorpse(p.players, role%)
	Local c.Corpses = New Corpses
	c\breachtype = role
	c\x = EntityX(p\pivot)
	c\y = EntityY(p\pivot)
	c\z = EntityZ(p\pivot)
	c\yaw = converttoshort(p\yaw)
	c\playerscale = Int(Player[playerid]\Size*100)
	c\timeout = MilliSecs()+120000
	c\FallFactor = 70*6
	c\id = FindFreeCorpseID()
	M_Corpse[c\ID] = c
End Function
Function UpdateDeadBodies()
	For c.corpses = Each corpses
		c\IsCorpseFalled = (MilliSecs() > c\Timeout-118000)
		
		If c\Timeout < MilliSecs() Then
			c\IsCorpseTimedOut = True
			
			If c\FallFactor < 0 Then
				Delete c
			Else
				c\y = c\y-0.001*FPSfactor
				c\FallFactor = c\FallFactor-FPSfactor
			EndIf
		EndIf
	Next
End Function
Function FindFreeCorpseID()
	For i = 1 To MAX_CORPSES-1
		If M_Corpse[i] = Null Then Return i
	Next
End Function

;--------------------------------------- random -------------------------------------------------------

Function f2s$(n#, count%)
	Return Left(n, Len(Int(n))+count+1)
End Function

Function AnimateNPC(n.NPCs, start#, quit#, speed#, loop=True)
	Local newTime#
	
	If speed > 0.0 Then 
		newTime = Max(Min(n\Frame + speed * FPSfactor,quit),start)
		
		If loop And newTime => quit Then
			newTime = start
		EndIf
	Else
		If start < quit Then
			temp% = start
			start = quit
			quit = temp
		EndIf
		
		If loop Then
			newTime = n\Frame + speed * FPSfactor
			
			If newTime < quit Then 
				newTime = start
			Else If newTime > start 
				newTime = quit
			EndIf
		Else
			newTime = Max(Min(n\Frame + speed * FPSfactor,start),quit)
		EndIf
	EndIf
	SetNPCFrame(n, newTime)
	
End Function

Function SetNPCFrame(n.NPCs, frame#)
	If (Abs(n\Frame-frame)<0.001) Then Return
	
	SetAnimTime n\obj, frame
	
	n\Frame = frame
End Function

Function Animate2#(entity%, curr#, start%, quit%, speed#, loop=True)
	
	Local newTime#
	
	If speed > 0.0 Then 
		newTime = Max(Min(curr + speed * FPSfactor,quit),start)
		
		If loop Then
			If newTime => quit Then 
				;SetAnimTime entity, start
				newTime = start
			Else
				;SetAnimTime entity, newTime
			EndIf
		Else
			;SetAnimTime entity, newTime
		EndIf
	Else
		If start < quit Then
			temp% = start
			start = quit
			quit = temp
		EndIf
		
		If loop Then
			newTime = curr + speed * FPSfactor
			
			If newTime < quit Then newTime = start
			If newTime > start Then newTime = quit
			
			;SetAnimTime entity, newTime
		Else
			;SetAnimTime (entity, Max(Min(curr + speed * FPSfactor,start),quit))
			newTime = Max(Min(curr + speed * FPSfactor,start),quit)
		EndIf
	EndIf
	
	SetAnimTime entity, newTime
	Return newTime
	
End Function 


Function Use914(item.Items, setting$, x#, y#, z#)
	RefinedItems = RefinedItems+1
	
	If GetScripts() Then
		public_inqueue(public_OnItemRefine)
		public_addparam(0, setting, SE_STRING)
		public_addparam(0, x, SE_FLOAT)
		public_addparam(0, y, SE_FLOAT)
		public_addparam(0, z, SE_FLOAT)
		public_addparam(0, item\ID)
		public_addparam(0, 0)
		callback
	EndIf
	
	If SE_RETURN_VALUE\StaticIndex Then Return 
	
	Local it2.Items
	Select item\itemtemplate\name
					;===========WEAPON REFINE SYSTEM==============
			
		;grenades
		Case "Grenade"
			Select setting
				Case "rough", "coarse"
					it2 = CreateItem("Smoke grenade", "grenadesmoke", x, y, z)
					RemoveItem(item)
				Case "1:1"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					If Rand(4)=1 Then
						it2 = CreateItem("Rocket Launcher", "rpg", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("Flashbang", "grenadeflashbang", x, y, z)
						RemoveItem(item)
					EndIf
			End Select
		Case "Smoke grenade"
			Select setting
				Case "rough", "course"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					it2 = CreateItem("Flashbang", "grenadeflashbang", x, y, z)
					RemoveItem(item)
				Case "fine", "very fine"
					If Rand(6)=1 Then
						it2 = CreateItem("Micro-HID", "microhid", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("Grenade", "grenade", x, y, z)
						RemoveItem(item)
					EndIf
			End Select
		Case "Flashbang"
			Select setting
				Case "rough", "course"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					it2 = CreateItem("Smoke grenade", "grenadesmoke", x, y, z)
					RemoveItem(item)
				Case "fine", "very fine"
					If Rand(6)=1 Then
						it2 = CreateItem("Micro-HID", "microhid", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("Grenade", "grenade", x, y, z)
						RemoveItem(item)
					EndIf
			End Select
		;guns
		Case "Minigun"
			Select setting
				Case "rough", "course"
					it2 = CreateItem("HK-G36", "hkg36", x, y, z)
					RemoveItem(item)
				Case "1:1"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
			End Select
		Case "HK-G36"
			Select setting
				Case "rough", "course"
					it2 = CreateItem("FN P90", "p90", x, y, z)
					RemoveItem(item)
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					If Rand(4)=1 Then
						it2 = CreateItem("Minigun", "minigun", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("HK-G36", "hkg36", x, y, z)
						RemoveItem(item)
					EndIf
			End Select
		Case "FN P90"
			Select setting
				Case "rough", "course"
					If Rand(2)=1 Then
						it2 = CreateItem("Desert Eagle", "deagle", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("SPAS-12", "spas12", x, y, z)
						RemoveItem(item)
					EndIf
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					If Rand(2)=1 Then
						it2 = CreateItem("Combat knife", "knife", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("HK-G36", "hkg36", x, y, z)
						RemoveItem(item)
					EndIf
			End Select
		Case "Desert Eagle", "SPAS-12"
			Select setting
				Case "rough", "course"
					it2 = CreateItem("M4A4", "m4a4", x, y, z)
					RemoveItem(item)
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					it2 = CreateItem("FN P90", "p90", x, y, z)
					RemoveItem(item)
			End Select
		Case "M4A4"
			Select setting
				Case "rough", "course"
					it2 = CreateItem("MP5-SD", "mp5sd", x, y, z)
					RemoveItem(item)
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					If Rand(2)=1 Then
						it2 = CreateItem("Desert Eagle", "deagle", x, y, z)
						RemoveItem(item)
					Else
						it2 = CreateItem("SPAS-12", "spas12", x, y, z)
						RemoveItem(item)
					EndIf
			End Select
		Case "MP5-SD"
			Select setting
				Case "rough", "course"
					it2 = CreateItem("USP Tactical", "usp", x, y, z)
					RemoveItem(item)
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					it2 = CreateItem("M4A4", "m4a4", x, y, z)
					RemoveItem(item)
			End Select	
		Case "USP Tactical"
			Select setting
				Case "rough", "course"
					it2 = CreateItem("Combat knife", "knife", x, y, z)
					RemoveItem(item)
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					it2 = CreateItem("M4A4", "m4a4", x, y, z)
					RemoveItem(item)
			End Select	
		Case "Combat knife"
			Select setting
				Case "rough", "course"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					it2 = CreateItem("USP Tactical", "usp", x, y, z)
					RemoveItem(item)
			End Select
;===========END OF WEAPON REFINE SYSTEM==============
			
		Case "Gas Mask", "Heavy Gas Mask"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					it2 = CreateItem("Gas Mask", "supergasmask", x, y, z)
					RemoveItem(item)
			End Select
		Case "Cooked Chicken"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine", "very fine"
					n.NPCs = CreateNPC(NPCtypeD,x,y,z)
					n\State = 1
					RemoveItem(item)
			End Select
		Case "SCP-1499"
				Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					it2 = CreateItem("Gas Mask", "gasmask", x, y, z)
					RemoveItem(item)
				Case "fine"
					it2 = CreateItem("SCP-1499", "super1499", x, y, z)
					RemoveItem(item)
				Case "very fine"
					n.NPCs = CreateNPC(NPCtype1499,x,y,z)
					n\IsSpawned = True
					n\State = 1
					n\Sound = LoadSound_Strict("SFX\SCP\1499\Triggered.ogg")
					n\SoundChn = PlaySound2(n\Sound, Camera, n\Collider,20.0)
					n\State3 = 1
					RemoveItem(item)
			End Select
		Case "Ballistic Vest"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine"
					it2 = CreateItem("Heavy Ballistic Vest", "finevest", x, y, z)
					RemoveItem(item)
				Case "very fine"
					it2 = CreateItem("Bulky Ballistic Vest", "veryfinevest", x, y, z)
					RemoveItem(item)
			End Select
		Case "Cowbell"
			Select setting
				Case "rough","coarse"
					d.Decals = CreateDecal(0, x, 8*RoomScale+0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1","fine","very fine"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
			End Select
		Case "Night Vision Goggles"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
					RemoveItem(item)
				Case "1:1"
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)
				Case "fine"
					it2 = CreateItem("Night Vision Goggles", "finenvgoggles", x, y, z)
					RemoveItem(item)
				Case "very fine"
					it2 = CreateItem("Night Vision Goggles", "supernv", x, y, z)
					it2\state = 1000
					RemoveItem(item)
			End Select
		Case "Metal Panel", "SCP-148 Ingot"
			Select setting
				Case "rough", "coarse"
					it2 = CreateItem("SCP-148 Ingot", "scp148ingot", x, y, z)
					RemoveItem(item)
				Case "1:1", "fine", "very fine"
					it2 = Null
					For it.Items = Each Items
						If it<>item And it\collider <> 0 And it\Picked = False Then
							If Distance(EntityX(it\collider,True), EntityZ(it\collider,True), EntityX(item\collider, True), EntityZ(item\collider, True)) < (180.0 * RoomScale) Then
								it2 = it
								Exit
							ElseIf Distance(EntityX(it\collider,True), EntityZ(it\collider,True), x,z) < (180.0 * RoomScale)
								it2 = it
								Exit
							End If
						End If
					Next
					
					If it2<>Null Then
						Select it2\itemtemplate\tempname
							Case "gasmask", "supergasmask"
								RemoveItem (it2)
								RemoveItem (item)
								
								it2 = CreateItem("Heavy Gas Mask", "gasmask3", x, y, z)
							Case "vest"
								RemoveItem (it2)
								RemoveItem(item)
								it2 = CreateItem("Heavy Ballistic Vest", "finevest", x, y, z)
							Case "hazmatsuit","hazmatsuit2"
								RemoveItem (it2)
								RemoveItem(item)
								it2 = CreateItem("Heavy Hazmat Suit", "hazmatsuit3", x, y, z)
						End Select
					Else 
						If item\itemtemplate\name="SCP-148 Ingot" Then
							it2 = CreateItem("Metal Panel", "scp148", x, y, z)
							RemoveItem(item)
						Else
							PositionEntity(item\collider, x, y, z)
							ResetEntity(item\collider)							
						EndIf
					EndIf					
			End Select
		Case "Severed Hand", "Black Severed Hand"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(3, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1", "fine", "very fine"
					If (item\itemtemplate\name = "Severed Hand")
						it2 = CreateItem("Black Severed Hand", "hand2", x, y, z)
					Else
						it2 = CreateItem("Severed Hand", "hand", x, y, z)
					EndIf
			End Select
			RemoveItem(item)
		Case "First Aid Kit", "Blue First Aid Kit"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
				If Rand(2)=1 Then
					it2 = CreateItem("Blue First Aid Kit", "firstaid2", x, y, z)
				Else
				    it2 = CreateItem("First Aid Kit", "firstaid", x, y, z)
				EndIf
				Case "fine"
					it2 = CreateItem("Small First Aid Kit", "finefirstaid", x, y, z)
				Case "very fine"
					it2 = CreateItem("Strange Bottle", "veryfinefirstaid", x, y, z)
			End Select
			RemoveItem(item)
		Case "Level 1 Key Card", "Level 2 Key Card", "Level 3 Key Card", "Level 4 Key Card", "Level 5 Key Card", "Key Card"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.07 : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("Playing Card", "misc", x, y, z)
				Case "fine"
					Select item\itemtemplate\name
						Case "Level 1 Key Card"
							Select SelectedDifficulty\otherFactors
								Case EASY
									it2 = CreateItem("Level 2 Key Card", "key2", x, y, z)
								Case NORMAL
									If Rand(5)=1 Then
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									Else
										it2 = CreateItem("Level 2 Key Card", "key2", x, y, z)
									EndIf
								Case HARD
									If Rand(4)=1 Then
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									Else
										it2 = CreateItem("Level 2 Key Card", "key2", x, y, z)
									EndIf
							End Select
						Case "Level 2 Key Card"
							Select SelectedDifficulty\otherFactors
								Case EASY
									it2 = CreateItem("Level 3 Key Card", "key3", x, y, z)
								Case NORMAL
									If Rand(4)=1 Then
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									Else
										it2 = CreateItem("Level 3 Key Card", "key3", x, y, z)
									EndIf
								Case HARD
									If Rand(3)=1 Then
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									Else
										it2 = CreateItem("Level 3 Key Card", "key3", x, y, z)
									EndIf
							End Select
						Case "Level 3 Key Card"
							Select SelectedDifficulty\otherFactors
								Case EASY
									If Rand(10)=1 Then
										it2 = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2 = CreateItem("Playing Card", "misc", x, y, z)	
									EndIf
								Case NORMAL
									If Rand(15)=1 Then
										it2 = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2 = CreateItem("Playing Card", "misc", x, y, z)	
									EndIf
								Case HARD
									If Rand(20)=1 Then
										it2 = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2 = CreateItem("Playing Card", "misc", x, y, z)	
									EndIf
							End Select
						Case "Level 4 Key Card"
							Select SelectedDifficulty\otherFactors
								Case EASY
									it2 = CreateItem("Level 5 Key Card", "key5", x, y, z)
								Case NORMAL
									If Rand(4)=1 Then
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									Else
										it2 = CreateItem("Level 5 Key Card", "key5", x, y, z)
									EndIf
								Case HARD
									If Rand(3)=1 Then
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									Else
										it2 = CreateItem("Level 5 Key Card", "key5", x, y, z)
									EndIf
							End Select
						Case "Level 5 Key Card"	
							Local CurrAchvAmount%=0
							For i = 0 To MAXACHIEVEMENTS-1
								If Achievements(i)=True
									CurrAchvAmount=CurrAchvAmount+1
								EndIf
							Next
							
							DebugLog CurrAchvAmount
							
							Select SelectedDifficulty\otherFactors
								Case EASY
									If Rand(0,((MAXACHIEVEMENTS-1)*3)-((CurrAchvAmount-1)*3))=0
										it2 = CreateItem("Key Card Omni", "key6", x, y, z)
									Else
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									EndIf
								Case NORMAL
									If Rand(0,((MAXACHIEVEMENTS-1)*4)-((CurrAchvAmount-1)*3))=0
										it2 = CreateItem("Key Card Omni", "key6", x, y, z)
									Else
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									EndIf
								Case HARD
									If Rand(0,((MAXACHIEVEMENTS-1)*5)-((CurrAchvAmount-1)*3))=0
										it2 = CreateItem("Key Card Omni", "key6", x, y, z)
									Else
										it2 = CreateItem("Mastercard", "misc", x, y, z)
									EndIf
							End Select		
					End Select
				Case "very fine"
					CurrAchvAmount%=0
					For i = 0 To MAXACHIEVEMENTS-1
						If Achievements(i)=True
							CurrAchvAmount=CurrAchvAmount+1
						EndIf
					Next
					
					DebugLog CurrAchvAmount
					
					Select SelectedDifficulty\otherFactors
						Case EASY
							If Rand(0,((MAXACHIEVEMENTS-1)*3)-((CurrAchvAmount-1)*3))=0
								it2 = CreateItem("Key Card Omni", "key6", x, y, z)
							Else
								it2 = CreateItem("Mastercard", "misc", x, y, z)
							EndIf
						Case NORMAL
							If Rand(0,((MAXACHIEVEMENTS-1)*4)-((CurrAchvAmount-1)*3))=0
								it2 = CreateItem("Key Card Omni", "key6", x, y, z)
							Else
								it2 = CreateItem("Mastercard", "misc", x, y, z)
							EndIf
						Case HARD
							If Rand(0,((MAXACHIEVEMENTS-1)*5)-((CurrAchvAmount-1)*3))=0
								it2 = CreateItem("Key Card Omni", "key6", x, y, z)
							Else
								it2 = CreateItem("Mastercard", "misc", x, y, z)
							EndIf
					End Select
			End Select
			
			RemoveItem(item)
		Case "Key Card Omni"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.07 : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					If Rand(2)=1 Then
						it2 = CreateItem("Mastercard", "misc", x, y, z)
					Else
						it2 = CreateItem("Playing Card", "misc", x, y, z)			
					EndIf	
				Case "fine", "very fine"
					it2 = CreateItem("Key Card Omni", "key6", x, y, z)
			End Select			
			
			RemoveItem(item)
		Case "Playing Card", "Coin", "Quarter"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.07 : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("Level 1 Key Card", "key1", x, y, z)	
			    Case "fine", "very fine"
					it2 = CreateItem("Level 2 Key Card", "key2", x, y, z)
			End Select
			RemoveItem(item)
		Case "Mastercard"
			Select setting
				Case "rough"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
					d\Size = 0.07 : ScaleSprite(d\obj, d\Size, d\Size)
				Case "coarse"
					it2 = CreateItem("Quarter", "25ct", x, y, z)
					Local it3.Items,it4.Items,it5.Items
					it3 = CreateItem("Quarter", "25ct", x, y, z)
					it4 = CreateItem("Quarter", "25ct", x, y, z)
					it5 = CreateItem("Quarter", "25ct", x, y, z)
				Case "1:1"
					it2 = CreateItem("Level 1 Key Card", "key1", x, y, z)	
			    Case "fine", "very fine"
					it2 = CreateItem("Level 2 Key Card", "key2", x, y, z)
			End Select
			RemoveItem(item)
		Case "S-NAV 300 Navigator", "S-NAV 310 Navigator", "S-NAV Navigator", "S-NAV Navigator Ultimate"
			Select setting
				Case "rough", "coarse"
					it2 = CreateItem("Electronical components", "misc", x, y, z)
				Case "1:1"
					it2 = CreateItem("S-NAV Navigator", "nav", x, y, z)
					it2\state = 100
				Case "fine"
					it2 = CreateItem("S-NAV 310 Navigator", "nav", x, y, z)
					it2\state = 100
				Case "very fine"
					it2 = CreateItem("S-NAV Navigator Ultimate", "nav", x, y, z)
					it2\state = 101
			End Select
			
			RemoveItem(item)
		Case "Radio Transceiver"
			Select setting
				Case "rough", "coarse"
					it2 = CreateItem("Electronical components", "misc", x, y, z)
				Case "1:1"
					it2 = CreateItem("Radio Transceiver", "18vradio", x, y, z)
					it2\state = 100
				Case "fine"
					it2 = CreateItem("Radio Transceiver", "fineradio", x, y, z)
					it2\state = 101
				Case "very fine"
					it2 = CreateItem("Radio Transceiver", "veryfineradio", x, y, z)
					it2\state = 101
			End Select
			
			RemoveItem(item)
		Case "SCP-513"
			Select setting
				Case "rough", "coarse"
					PlaySound_Strict LoadTempSound("SFX\SCP\513\914Refine.ogg")
					For n.npcs = Each NPCs
						If n\npctype = NPCtype5131 Then RemoveNPC(n, True)
					Next
					d.Decals = CreateDecal(0, x, 8*RoomScale+0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1", "fine", "very fine"
					it2 = CreateItem("SCP-513", "scp513", x, y, z)
					
			End Select
			
			RemoveItem(item)
		Case "Some SCP-420-J", "Cigarette"
			Select setting
				Case "rough", "coarse"			
					d.Decals = CreateDecal(0, x, 8*RoomScale+0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("Cigarette", "cigarette", x + 1.5, y + 0.5, z + 1.0)
				Case "fine"
					it2 = CreateItem("Joint", "420s", x + 1.5, y + 0.5, z + 1.0)
				Case "very fine"
					it2 = CreateItem("Smelly Joint", "420s", x + 1.5, y + 0.5, z + 1.0)
			End Select
			
			RemoveItem(item)
		Case "9V Battery", "18V Battery", "Strange Battery"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("18V Battery", "18vbat", x, y, z)
				Case "fine"
					it2 = CreateItem("Strange Battery", "killbat", x, y, z)
				Case "very fine"
					it2 = CreateItem("Strange Battery", "killbat", x, y, z)
			End Select
			
			RemoveItem(item)
		Case "ReVision Eyedrops", "RedVision Eyedrops", "Eyedrops"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("RedVision Eyedrops", "eyedrops", x,y,z)
				Case "fine"
					it2 = CreateItem("Eyedrops", "fineeyedrops", x,y,z)
				Case "very fine"
					it2 = CreateItem("Eyedrops", "supereyedrops", x,y,z)
			End Select
			
			RemoveItem(item)		
		Case "Hazmat Suit"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("Hazmat Suit", "hazmatsuit", x,y,z)
				Case "fine"
					it2 = CreateItem("Hazmat Suit", "hazmatsuit2", x,y,z)
				Case "very fine"
					it2 = CreateItem("Hazmat Suit", "hazmatsuit2", x,y,z)
			End Select
			
			RemoveItem(item)
			
		Case "Syringe"
			Select item\itemtemplate\tempname
				Case "syringe"
					Select setting
						Case "rough", "coarse"
							d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
							d\Size = 0.07 : ScaleSprite(d\obj, d\Size, d\Size)
						Case "1:1"
							it2 = CreateItem("Small First Aid Kit", "finefirstaid", x, y, z)	
						Case "fine"
							it2 = CreateItem("Syringe", "finesyringe", x, y, z)
						Case "very fine"
							it2 = CreateItem("Syringe", "veryfinesyringe", x, y, z)
					End Select
					
				Case "finesyringe"
					Select setting
						Case "rough"
							d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
							d\Size = 0.07 : ScaleSprite(d\obj, d\Size, d\Size)
						Case "coarse"
							it2 = CreateItem("First Aid Kit", "firstaid", x, y, z)
						Case "1:1"
							it2 = CreateItem("Blue First Aid Kit", "firstaid2", x, y, z)	
						Case "fine", "very fine"
							it2 = CreateItem("Syringe", "veryfinesyringe", x, y, z)
					End Select
					
				Case "veryfinesyringe"
					Select setting
						Case "rough", "coarse", "1:1", "fine"
							it2 = CreateItem("Electronical components", "misc", x, y, z)	
						Case "very fine"
							n.NPCs = CreateNPC(NPCtype008,x,y,z)
							n\IsSpawned = True
							n\State = 2
					End Select
			End Select
			
			RemoveItem(item)
			
		Case "SCP-500-01", "Upgraded pill", "Pill"
			Select setting
				Case "rough", "coarse"
					d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.010, z, 90, Rand(360), 0)
					d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
				Case "1:1"
					it2 = CreateItem("Pill", "pill", x, y, z)
					RemoveItem(item)
				Case "fine"
					Local no427Spawn% = False
					For it3.Items = Each Items
						If it3\itemtemplate\tempname = "scp427" Then
							no427Spawn = True
							Exit
						EndIf
					Next
					If (Not no427Spawn) Then
						it2 = CreateItem("SCP-427", "scp427", x, y, z)
					Else
						it2 = CreateItem("Upgraded pill", "scp500death", x, y, z)
					EndIf
					RemoveItem(item)
				Case "very fine"
					it2 = CreateItem("Upgraded pill", "scp500death", x, y, z)
					RemoveItem(item)
			End Select
			
		Default
			
			Select item\itemtemplate\tempname
				Case "cup"
					Select setting
						Case "rough", "coarse"
							d.Decals = CreateDecal(0, x, 8 * RoomScale + 0.010, z, 90, Rand(360), 0)
							d\Size = 0.2 : EntityAlpha(d\obj, 0.8) : ScaleSprite(d\obj, d\Size, d\Size)
						Case "1:1"
							it2 = CreateItem("cup", "cup", x,y,z)
							it2\name = item\name
							it2\r = 255-item\r
							it2\g = 255-item\g
							it2\b = 255-item\b
						Case "fine"
							it2 = CreateItem("cup", "cup", x,y,z)
							it2\name = item\name
							it2\state = 1.0
							it2\r = Min(item\r*Rnd(0.9,1.1),255)
							it2\g = Min(item\g*Rnd(0.9,1.1),255)
							it2\b = Min(item\b*Rnd(0.9,1.1),255)
						Case "very fine"
							it2 = CreateItem("cup", "cup", x,y,z)
							it2\name = item\name
							it2\state = Max(it2\state*2.0,2.0)	
							it2\r = Min(item\r*Rnd(0.5,1.5),255)
							it2\g = Min(item\g*Rnd(0.5,1.5),255)
							it2\b = Min(item\b*Rnd(0.5,1.5),255)
							If Rand(5)=1 Then
								ExplosionTimer = 135
							EndIf
					End Select	
					
					RemoveItem(item)
				Case "paper"
					Select setting
						Case "rough", "coarse"
							d.Decals = CreateDecal(7, x, 8 * RoomScale + 0.005, z, 90, Rand(360), 0)
							d\Size = 0.12 : ScaleSprite(d\obj, d\Size, d\Size)
						Case "1:1"
							Select Rand(6)
								Case 1
									it2 = CreateItem("Document SCP-106", "paper", x, y, z)
								Case 2
									it2 = CreateItem("Document SCP-079", "paper", x, y, z)
								Case 3
									it2 = CreateItem("Document SCP-173", "paper", x, y, z)
								Case 4
									it2 = CreateItem("Document SCP-895", "paper", x, y, z)
								Case 5
									it2 = CreateItem("Document SCP-682", "paper", x, y, z)
								Case 6
									it2 = CreateItem("Document SCP-860", "paper", x, y, z)
							End Select
						Case "fine", "very fine"
							it2 = CreateItem("Origami", "misc", x, y, z)
					End Select
					
					RemoveItem(item)
				Default
					PositionEntity(item\collider, x, y, z)
					ResetEntity(item\collider)	
			End Select
			
	End Select
	If d <> Null Then mp_WriteDecal(d,1)
	If it <> Null Then
		it\picker = 0
		it\picked = False
	EndIf
	
	If it2 <> Null Then 
		it2\picker = 0
		it2\picked = False
	EndIf
End Function

Function CalculateDist#(obj, r.Rooms)
	Return Max(Abs(r\x-EntityX(obj,True)),Abs(r\z-EntityZ(obj,True)))
End Function

Function MilliSecs()
	RetValMillisecs% = api_timeGetTime()
	If RetValMillisecs < 0 Then RetValMillisecs = RetValMillisecs + 2147483648
	Return RetValMillisecs
End Function

Function MicroSecs()
	RetValMillisecs% = GetMicroSecs()
	If RetValMillisecs < 0 Then RetValMillisecs = RetValMillisecs + 2147483648
	Return RetValMillisecs
End Function

Function MilliSecs2()
	Return MilliSecs()
End Function

Function FindFreeDoorID()
	For i = 1 To MAX_DOORS-1
		If MP_Door[i] = Null Then Return i
	Next
End Function
Function FindFreeItemID%()
	For i = 1 To MAX_ITEMS-1
		If M_Item[i] = Null Then Return i
	Next
End Function
Function FindFreeNPCID%()
	For i = 1 To MAX_NPCS-1
		If M_NPC[i] = Null Then Return i
	Next
End Function
Function FindFreeEventID()
	For i = 1 To MAX_EVENTS-1
		If M_Event[i] = Null Then Return i
	Next
End Function
Function GetItemID(it.Items)
	If it <> Null Then Return it\ID
	Return 0
End Function
Function GetRoomID(r.Rooms)
	If r <> Null Then Return r\ID
	Return 0
End Function

Function converttoshort(fvalue#)
	Return (fvalue*10.0)+32767
End Function

Function convertshorttovalue#(fvalue#)
	Return (fvalue-32767.0)/10.0
End Function

Function CurveValue#(number#, old#, smooth#)
	If FPSfactor = 0 Then Return old
	
	If number < old Then
		Return Max(old + (number - old) * (1.0 / smooth * FPSfactor), number)
	Else
		Return Min(old + (number - old) * (1.0 / smooth * FPSfactor), number)
	EndIf
End Function

Function ToValue#(number#, old#, speed#)
	If FPSfactor = 0 Then Return old
	
	If number < old Then
		Return Max(number, old-(speed*FPSfactor))
	Else
		Return Min(number, old+(speed*FPSfactor))
	EndIf
End Function

Function CurveAngle#(val#, old#, smooth#)
	If FPSfactor = 0 Then Return old
	
   Local diff# = WrapAngle(val) - WrapAngle(old)
   If diff > 180 Then diff = diff - 360
   If diff < - 180 Then diff = diff + 360
   Return WrapAngle(old + diff * (1.0 / smooth * FPSfactor))
End Function

Function LoadTempSound(file$)
	Return 0
End Function

Function LoadEventSound(e.Events,file$,num%=0)
	
	If num=0 Then
		If e\Sound<>0 Then FreeSound_Strict e\Sound : e\Sound=0
		e\Sound=LoadSound_Strict(file)
		Return e\Sound
	Else If num=1 Then
		If e\Sound2<>0 Then FreeSound_Strict e\Sound2 : e\Sound2=0
		e\Sound2=LoadSound_Strict(file)
		Return e\Sound2
	EndIf
End Function

Function UpdateSoundOrigin2(Chn%, cam%, entity%, range# = 10, volume# = 1.0)

End Function

Function UpdateSoundOrigin(Chn%, cam%, entity%, range# = 10, volume# = 1.0)

End Function

Function IntRound#(value#, Round% = 10)
	Return Int(Int(value/round)*round)
End Function

Function FloatRound#(value#, Round% = 10)
	Return IntRound(value*Round)/Round
End Function
Function GenerateIndex(dat$)
	Local Value%
	For i = 1 To Len(dat)
		Value = Value + Asc(Mid(dat, i, 1))
	Next
	Return Value
End Function
Function point_direction#(x1#,z1#,x2#,z2#)
	Local dx#, dz#
	dx = x1 - x2
	dz = z1 - z2
	Return ATan2(dz,dx)
End Function

Function point_distance#(x1#,z1#,x2#,z2#)
	Local dx#,dy#
	dx = x1 - x2
	dy = z1 - z2
	Return Sqr((dx*dx)+(dy*dy)) 
End Function

Function angleDist#(a0#,a1#)
	Local b# = a0-a1
	Local bb#
	If b<-180.0 Then
		bb = b+360.0
	Else If b>180.0 Then
		bb = b-360.0
	Else
		bb = b
	EndIf
	Return bb
End Function

Function CorrectAngleValue(value#)
	Local numb#
	
	If value# <= 180.0
		numb# = value#
	Else
		numb# = -360+value#
	EndIf
	
	Return numb#
End Function

Function Inverse#(number#)
	
	Return Float(1.0-number#)
	
End Function

Function sqrvalue#(val#)
	Return Sqr(val*val)
End Function

Function Distance3#(x1#, y1#, z1#, x2#, y2#, z2#)
	Return ((x2-x1)^2 + (y2-y1)^2 + (z2-z1)^2)^0.5
End Function

; ========================== BouncingSystem

Type bVector
	Field x#, y#
End Type

Function BounceWall#(wallAngle#, Direction#)

	returnVector.bVector = bBounce(bCreateVector(Sin(wallAngle),Cos(wallAngle)), bCreateVector(Sin(Direction),Cos(Direction)))
	ResultAngle# = VectorYaw(returnVector\x, 0, returnVector\y)

	Delete Each bVector
	
	Return ResultAngle
End Function

Function bAdd.bVector(a.bVector, b.bVector)
    Return bCreateVector(a\x + b\x, a\y + b\y)
End Function

Function bMult.bVector(a.bVector, s#)
    Return bCreateVector(a\x * s, a\y * s)
End Function

Function bDot#(a.bVector, b.bVector)
    Return a\x * b\x + a\y * b\y
End Function

Function bNorm#(a.bVector)
    Return Sqr(a\x * a\x + a\y * a\y)
End Function

Function bNormalize.bVector(a.bVector)
    Return bMult(a, 1/bNorm(a))
End Function

Function bProject.bVector(v.bVector, a.bVector)
    Return bMult(bNormalize(a), bDot(v, a)/bNorm(a))
End Function

Function bBounce.bVector(v.bVector, n.bVector)
    Return bAdd(v, bMult(bProject(v, n), -2.0))
End Function

Function bCreateVector.bVector(x#, y#)
	Local v.bvector = New bvector
	v\x = x
	v\y = y
	Return v
End Function
; ========================================================================

Function FlipAngle#(angle#)
	If Angle < 0 Then
		Return sqrvalue(angle)
	Else
		Return -angle
	EndIf
End Function

Function WrapAngle#(angle#)
	If angle = INFINITY Then Return 0.0
	While angle < 0
		angle = angle + 360
	Wend 
	While angle >= 360
		angle = angle - 360
	Wend
	Return angle
End Function

Function TeleportEntity(entity%,x#,y#,z#,customradius#=0.3,isglobal%=False,pickrange#=2.0,dir%=0)
	Local pvt,pick
	;dir = 0 - towards the floor (default)
	;dir = 1 - towrads the ceiling (mostly for PD decal after leaving dimension)
	
	pvt = CreatePivot()
	PositionEntity(pvt, x,y+0.05,z,isglobal)
	If dir%=0
		RotateEntity pvt,90,0,0
	Else
		RotateEntity pvt,-90,0,0
	EndIf
	pick = EntityPick(pvt,pickrange)
	If pick<>0
		If dir%=0
			PositionEntity(entity, x,PickedY()+customradius#+0.02,z,isglobal)
		Else
			PositionEntity(entity, x,PickedY()+customradius#-0.02,z,isglobal)
		EndIf
		DebugLog "Entity teleported successfully"
	Else
		PositionEntity(entity,x,y,z,isglobal)
		DebugLog "Warning: no ground found when teleporting an entity"
	EndIf
	FreeEntity pvt
	ResetEntity entity
	DebugLog "Teleported entity to: "+EntityX(entity)+"/"+EntityY(entity)+"/"+EntityZ(entity)
End Function
Function GetStepSound(ent%)
End Function
Function MakeCollBox(mesh%)
	Local sx# = EntityScaleX(mesh, 1)
	Local sy# = Max(EntityScaleY(mesh, 1), 0.001)
	Local sz# = EntityScaleZ(mesh, 1)
	GetMeshExtents(mesh)
	EntityBox mesh, Mesh_MinX * sx, Mesh_MinY * sy, Mesh_MinZ * sz, Mesh_MagX * sx, Mesh_MagY * sy, Mesh_MagZ * sz
End Function

; Find mesh extents
Function GetMeshExtents(Mesh%)
	Local s%, surf%, surfs%, v%, verts%, x#, y#, z#
	Local minx# = INFINITY
	Local miny# = INFINITY
	Local minz# = INFINITY
	Local maxx# = -INFINITY
	Local maxy# = -INFINITY
	Local maxz# = -INFINITY
	
	surfs = CountSurfaces(Mesh)
	
	For s = 1 To surfs
		surf = GetSurface(Mesh, s)
		verts = CountVertices(surf)
		
		For v = 0 To verts - 1
			x = VertexX(surf, v)
			y = VertexY(surf, v)
			z = VertexZ(surf, v)
			
			If (x < minx) Then minx = x
			If (x > maxx) Then maxx = x
			If (y < miny) Then miny = y
			If (y > maxy) Then maxy = y
			If (z < minz) Then minz = z
			If (z > maxz) Then maxz = z
		Next
	Next
	
	Mesh_MinX = minx
	Mesh_MinY = miny
	Mesh_MinZ = minz
	Mesh_MaxX = maxx
	Mesh_MaxY = maxy
	Mesh_MaxZ = maxz
	Mesh_MagX = maxx-minx
	Mesh_MagY = maxy-miny
	Mesh_MagZ = maxz-minz
	
End Function

Function EntityScaleX#(entity%, globl% = False)
	If globl Then TFormVector 1, 0, 0, entity, 0 Else TFormVector 1, 0, 0, entity, GetParent(entity)
	Return Sqr(TFormedX() * TFormedX() + TFormedY() * TFormedY() + TFormedZ() * TFormedZ())
End Function 

Function EntityScaleY#(entity%, globl% = False)
	If globl Then TFormVector 0, 1, 0, entity, 0 Else TFormVector 0, 1, 0, entity, GetParent(entity)
	Return Sqr(TFormedX() * TFormedX() + TFormedY() * TFormedY() + TFormedZ() * TFormedZ())
End Function 

Function EntityScaleZ#(entity%, globl% = False)
	If globl Then TFormVector 0, 0, 1, entity, 0 Else TFormVector 0, 0, 1, entity, GetParent(entity)
	Return Sqr(TFormedX() * TFormedX() + TFormedY() * TFormedY() + TFormedZ() * TFormedZ())
End Function 

Function EntityScaleX2#(Entity)

    Vx# = GetMatElement(Entity, 0, 0)
    Vy# = GetMatElement(Entity, 0, 1)
    Vz# = GetMatElement(Entity, 0, 2)   

    Scale# = Sqr(Vx#*Vx# + Vy#*Vy# + Vz#*Vz#)

    Return Scale#

End Function
Function EntityScaleY2#(Entity)

    Vx# = GetMatElement(Entity, 1, 0)
    Vy# = GetMatElement(Entity, 1, 1)
    Vz# = GetMatElement(Entity, 1, 2)   

    Scale# = Sqr(Vx#*Vx# + Vy#*Vy# + Vz#*Vz#)

    Return Scale#

End Function
Function EntityScaleZ2#(Entity)

    Vx# = GetMatElement(Entity, 2, 0)
    Vy# = GetMatElement(Entity, 2, 1)
    Vz# = GetMatElement(Entity, 2, 2)   

    Scale# = Sqr(Vx#*Vx# + Vy#*Vy# + Vz#*Vz#)

    Return Scale#

End Function

Function PlayAnnouncement(file$, give = True) ;This function streams the announcement currently playing
	If GetScripts() Then
		public_inqueue(public_OnPlayAnnouncement)
		public_addparam(0, file, SE_STRING)
		callback
	EndIf
	If Not give Then Return
	
	For i = 1 To Server\Maxplayers
	If Player[i] <> Null Then
		udp_WriteByte(M_ANNOUNC)
		udp_WriteByte(1)
		udp_WriteLine(file)
		udp_SendMessage(i)
	EndIf
	Next
	
End Function
Function mp_WriteDecal(de.decals, editdecal = 1, send = True)
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte M_DECAL 
			udp_WriteByte 1
			udp_WriteByte de\ID
			udp_WriteFloat de\x 
			udp_WriteFloat de\y 
			udp_WriteFloat de\z 
			udp_WriteFloat de\yaw 
			udp_WriteFloat de\roll 
			udp_WriteFloat de\pitch 
			udp_WriteFloat de\SizeChange 
			udp_WriteFloat de\Size
			udp_WriteFloat de\MaxSize 
			udp_WriteFloat de\AlphaChange 
			udp_WriteFloat de\Alpha
			udp_WriteFloat de\Timer 
			udp_SendMessage(i)
		EndIf
	Next
End Function

Function IsANotRemovedEvent%(e.Events)
	Select e\eventconst
		Case e_173
			Return True
		Case e_exit1
			Return True
		Case e_Gatea
			Return True
		Case e_room3storage
			Return True
		Case e_breach
			Return True
		Case e_room2tesla
			Return True
		Case e_pocketdimension
			Return True
		Case e_room860
			Return True
		Case e_dimension1499
			Return True
		Case e_914
			Return True
	End Select
	Return False
End Function
Function IsABlockedEvent%(e.Events)
	If e\StaticEvent = True Then Return True
	Select e\eventconst
		Case e_checkpoint
			Return True
		Case e_room1123,e_room1162
			Return True
		Case e_1048a
			Return True
		Case e_room2tesla
			Return True
		Case e_breach
			Return True
		Case e_pocketdimension
			Return True
		Case e_dimension1499
			Return True
		Case e_room860
			Return True
	End Select
	Return False
End Function
Function IsABlockedRoom%(r.Rooms)
	Select Lower(r\RoomTemplate\name)
		Case "dimension1499": Return True
	End Select
	Return False
End Function
Function UpdateMTF%()

	Local r.Rooms, n.NPCs
	Local dist#, i%
	
	;mtf ei viel?¤ spawnannut, spawnataan jos pelaaja menee tarpeeksi l?¤helle gate b:t?¤
	If MTFtimer = 0 Then
		If Rand(30)=1 Then
			Local entrance.Rooms = Null
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "gateaentrance" Then entrance = r : Exit
			Next
			If entrance <> Null Then
				For p.players = Each players
					If (p\X <> 0 Or p\Y <> 0 Or p\Z <> 0) And p\IsLoad And p\playerroomid <> 0 Then
						If p\PlayerRoomName <> "pocketdimension" And p\playerroomname <> "dimension1499" And p\playerroomname <> "" Then
							If Abs(EntityZ(entrance\obj)-EntityZ(p\Pivot))<30.0 Then
								MTFtimer = FPSfactor
								PlayAnnouncement("SFX\Character\MTF\Announc.ogg")
								Local leader.NPCs
								For i = 0 To 2
									n.NPCs = CreateNPC(NPCtypeMTF, EntityX(entrance\obj)+0.3*(i-1), 1.0,EntityZ(entrance\obj)+8.0)
									If i = 0 Then 
										leader = n
									Else
										n\MTFLeader = leader
									EndIf
									n\IsSpawned = True
									n\PrevX = i
								Next
								Return
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		EndIf
	Else
		If MTFtimer <= 70*120 ;70*120
			MTFtimer = MTFtimer + FPSfactor
		ElseIf MTFtimer > 70*120 And MTFtimer < 10000
			PlayAnnouncement("SFX\Character\MTF\AnnouncAfter1.ogg")
			MTFtimer = 10000
		ElseIf MTFtimer >= 10000 And MTFtimer <= 10000+(70*120) ;70*120
			MTFtimer = MTFtimer + FPSfactor
		ElseIf MTFtimer > 10000+(70*120) And MTFtimer < 20000
			PlayAnnouncement("SFX\Character\MTF\AnnouncAfter2.ogg")
			MTFtimer = 20000
		ElseIf MTFtimer >= 20000 And MTFtimer <= 20000+(70*60) ;70*120
			MTFtimer = MTFtimer + FPSfactor
		ElseIf MTFtimer > 20000+(70*60) And MTFtimer < 25000
				;If the player has an SCP in their inventory play special voice line.
			For it.items = Each items
				If it\picker <> 0 Then
					If (Left(it\itemtemplate\name, 4) = "SCP-") And (Left(it\itemtemplate\name, 7) <> "SCP-035") And (Left(it\itemtemplate\name, 7) <> "SCP-093")
						PlayAnnouncement("SFX\Character\MTF\ThreatAnnouncPossession.ogg")
						MTFtimer = 25000
						Return
					EndIf
				EndIf
			Next
			
			PlayAnnouncement("SFX\Character\MTF\ThreatAnnounc"+Rand(1,3)+".ogg")
			MTFtimer = 25000
			
		ElseIf MTFtimer >= 25000 And MTFtimer <= 25000+(70*60) ;70*120
			MTFtimer = MTFtimer + FPSfactor
		ElseIf MTFtimer > 25000+(70*60) And MTFtimer < 30000
			PlayAnnouncement("SFX\Character\MTF\ThreatAnnouncFinal.ogg")
			MTFtimer = 30000
			
		EndIf
	EndIf
	
End Function
Const ROCKET_SPEED# = 15
Const BULLET_SPEED# = 0.6
Type grenades
	Field ID
	Field Pivot, obj
	Field speed#, rollcurr#, angle#
	Field channel
	Field synced
	Field constpitch#
	Field constyaw#
	Field rocketparticle.emitters
	Field timer#
	Field prevfloor, prevy#
	Field shooter
	Field fallingspeed#
	Field shootertype
End Type
Type rockets
	Field ID
	Field Pivot, obj
	Field rocketspeed#, rocketspeedSmooth#
	Field channel
	Field synced
	Field constpitch#
	Field constyaw#
	Field rocketparticle.emitters
	Field timer#
	Field shooter, shootertype
End Type
Function CreateGrenade(x#, y#, z#, pitch#, yaw#, shooter%, throwtype%)
	Local g.grenades = New grenades
	g\obj = CopyEntity(GRENADE_OBJECT)
	ScaleEntity g\obj, 0.012,0.012, 0.012
	PositionEntity g\obj, x,y,z
	RotateEntity g\obj, pitch, yaw, -20
	EntityType g\obj, HIT_GRENADE
	EntityRadius g\obj, 0.03
	;EntityColor g\obj, 3000, 3000, 3000
	g\speed = 0.12-(throwtype*0.09)
	g\angle = yaw
	g\shooter = shooter
	If Player[shooter] <> Null Then g\shootertype = Player[shooter]\BreachType
	
	If throwtype = 1 Then RotateEntity g\obj, -30, yaw, -20
End Function
Function UpdateGrenades()
	Local CanVisible%
	For g.grenades = Each grenades
		g\timer = g\timer + FPSfactor
		If g\timer > 259 Then
		
			Local CanShoot%
			For p.players = Each players
				If EntityDistance(g\obj, p\Pivot) < 3 And p\BreachType <> 0 And (Not p\IsDead) Then
					canshoot = True
					
					If multiplayer_IsAFriend(g\shootertype, p\BreachType) And (Not Server\FriendlyFireExplosion) Then canshoot = False
					If canShoot Then
						CanVisible = EntityVisible(p\Pivot, g\obj)
						If Not canvisible Then CanVisible = EntityVisible(p\Hitbox, g\obj)
						If Not canvisible Then CanVisible = EntityVisible(p\obj, g\obj)
						If canvisible Then
							Local givehealth# = Max(0, ((7*GetGunDamage(GUN_GRENADE))-(EntityDistance(p\Pivot, g\obj)*20)))
							
							GivePlayerHealth(p\ID, -givehealth, "was killed by explosion by "+Player[g\shooter]\name)
							
							pbid = public_inqueue(public_OnPlayerHitPlayer)
							public_addparam(pbid, g\shooter)
							public_addparam(pbid, p\ID)
							public_addparam(pbid, GiveHealth, SE_FLOAT)
							public_addparam(pbid, Player[g\shooter]\UsedWeapon)
							callback
						EndIf
					EndIf
				EndIf
			Next
			For n.NPCs = Each NPCs
				If n\IsDead = False Then
					If n\NPCtype = NPCtypeZombie Or n\NPCtype = NPCtype008 Then
						If EntityDistance(n\Collider, g\obj) < 5 Then
							n\hp = n\hp - (7*5)-(EntityDistance(n\Collider, g\obj)*5)
							If n\hp < 1 Then n\IsDead = True
						EndIf
					EndIf
					If n\NPCtype = NPCtypeOldman Then
						If EntityDistance(n\Collider, g\obj) < 3 Then
							n\State = Rand(22000, 27000)
							PositionEntity n\Collider,0,500,0
						EndIf
					EndIf
				EndIf
			Next
			FreeEntity g\obj
			
			Delete g
		Else
			If g\speed > 0.01 Then
				If CountCollisions(g\obj) <> 0 Then
					If Abs(CollisionNY(g\obj, 1)) = 1 Then ; Jump
						RotateEntity g\obj, FlipAngle(EntityPitch(g\obj)), EntityYaw(g\obj), EntityRoll(g\obj)
						g\fallingspeed = 0
					Else
						RotateEntity g\obj, EntityPitch(g\obj), BounceWall(VectorYaw(CollisionNX(g\obj, 1), 0, -CollisionNZ(g\obj, 1)), EntityYaw(g\obj)), EntityRoll(g\obj)
					EndIf
					g\speed = g\speed*0.65 ; Reducing speed if collided
				Else
					MoveEntity g\obj, 0, 0, g\speed*FPSfactor

					TranslateEntity g\obj, 0, g\fallingspeed*FPSfactor, 0
					g\fallingspeed = Max(g\fallingspeed - 0.001*FPSfactor, -0.2)
					
					If EntityPitch(g\obj) < 90 Then 
						RotateEntity g\obj, WrapAngle(EntityPitch(g\obj)+0.8*FPSfactor), EntityYaw(g\obj), WrapAngle(EntityRoll(g\obj)+(g\speed*24)*FPSfactor)
					Else
						RotateEntity g\obj, EntityPitch(g\obj), EntityYaw(g\obj), WrapAngle(EntityRoll(g\obj)+(g\speed*24)*FPSfactor)
					EndIf
				EndIf
			Else
				g\speed = Max(g\speed-0.0001*FPSfactor, 0)
				Local prevpitch# = EntityPitch(g\obj)
				RotateEntity(g\obj, 0, EntityYaw(g\obj), CurveValue(90, EntityRoll(g\obj), 15))
				MoveEntity g\obj, 0, 0, g\speed*FPSfactor

				RotateEntity(g\obj, WrapAngle(prevpitch+(g\speed*24)*FPSfactor), EntityYaw(g\obj), CurveValue(90, EntityRoll(g\obj), 15))

				TranslateEntity g\obj, 0, g\fallingspeed*FPSfactor, 0
				g\fallingspeed = Max(g\fallingspeed - 0.001*FPSfactor, -0.2)
			EndIf
		EndIf
	Next
End Function
Function UpdateRockets()
	Local CanVisible
	Local Shooter%
	For rc.rockets = Each rockets
		rc\RocketSpeed = CurveValue(ROCKET_SPEED, rc\rocketspeed, 3000)
		MoveEntity rc\Pivot, 0, 0, rc\rocketspeed*FPSfactor
		RotateEntity rc\Pivot, WrapAngle(EntityPitch(rc\Pivot)+0.05*FPSfactor), EntityYaw(rc\Pivot), EntityRoll(rc\Pivot)
		rc\timer = rc\timer + FPSfactor
		
		Shooter = rc\shooter
		EntityPickMode Player[shooter]\Hitbox, 0, False
		
		canshoot = False
		If EntityPick(rc\pivot, 1) Or rc\timer > 400 Then
			For p.players = Each players
				If EntityDistance(rc\Pivot, p\Pivot) < 3 And p\BreachType <> 0 And (Not p\IsDead) Then
					canshoot = True
					
					If multiplayer_IsAFriend(rc\shootertype, p\BreachType) And (Not Server\FriendlyFireExplosion) Then canshoot = False
					If CanShoot Then
						CanVisible = EntityVisible(p\Pivot, rc\Pivot)
						If Not canvisible Then CanVisible = EntityVisible(p\Hitbox, rc\Pivot)
						If Not canvisible Then CanVisible = EntityVisible(p\obj, rc\Pivot)
						
						If canvisible Then
							Local GiveHealth# = Max(0, ((7*GetGunDamage(GUN_BAZOOKA))-(EntityDistance(p\Pivot, rc\Pivot)*20)))
							GivePlayerHealth(p\ID, -GiveHealth, "was killed by explosion by "+Player[shooter]\name)
							
							pbid = public_inqueue(public_OnPlayerHitPlayer)
							public_addparam(pbid, shooter)
							public_addparam(pbid, p\ID)
							public_addparam(pbid, GiveHealth, SE_FLOAT)
							public_addparam(pbid, Player[shooter]\UsedWeapon)
							callback
						EndIf
					EndIf
				EndIf
			Next
			For n.NPCs = Each NPCs
				If n\IsDead = False Then
					If n\NPCtype = NPCtypeZombie Or n\NPCtype = NPCtype008 Then
						If EntityDistance(n\Collider, rc\pivot) < 5 Then
							n\hp = n\hp - (7*5)-(EntityDistance(n\Collider, rc\pivot)*5)
							If n\hp < 1 Then n\IsDead = True
						EndIf
					EndIf
					If n\NPCtype = NPCtypeOldman Then
						If EntityDistance(n\Collider, rc\pivot) < 3 Then
							n\State = Rand(22000, 27000)
							PositionEntity n\Collider,0,500,0
						EndIf
					EndIf
				EndIf
			Next

			FreeEntity rc\pivot
			Delete rc
		EndIf
		EntityPickMode Player[shooter]\Hitbox, 2, False
	Next
	UpdateGrenades
End Function
Function CreateRocket.rockets(rocketspeed#, x#, y#, z#, constpitch#, constyaw#, shooter%)
	rc.rockets = New rockets
	rc\constpitch = constpitch
	rc\constyaw = constyaw
	rc\pivot = CreatePivot()
	;rc\rocketspeed = rocketspeed
	PositionEntity rc\pivot, x, y+0.2, z
	RotateEntity rc\pivot, constpitch, constyaw, 0
	ResetEntity rc\pivot
	MoveEntity rc\pivot, 0.1, -0.17, 0
	rc\shooter = shooter
	If Player[shooter] <> Null Then rc\shootertype = Player[shooter]\BreachType
	Return rc
End Function
Type bullets
	Field obj
	Field bulletspeed#, timer#, shooter
	Field pivot
	Field gunid
End Type

Function CreateBullet.bullets(shooter%, bulletspeed#, x#, y#, z#, constpitch#, constyaw#, gunid)
	b.bullets = New bullets
	b\obj = CreateCube()
	ScaleEntity b\obj, 0.0003, 0.0003, 0
	EntityColor b\obj, 9999, 9999, 0
	EntityShininess b\obj, 100000
	PositionEntity b\obj, x, y, z
	RotateEntity b\obj, 0, constyaw, 0
	RotateEntity b\obj, constpitch, constyaw, 0
	;EntityType b\obj, HIT_BULLET
	;EntityRadius b\obj, 0.01
	MoveEntity b\obj, 0, 0, -0.5
	
	;For p.players = Each players
	;	EntityPickmode p\Hitbox, 2, False
	;	EntityPickmode p\obj, 2, False
	;	EntityPickmode p\Pivot, 1, False
	;Next

	EntityPickMode Player[shooter]\Hitbox, 0, True
	;EntityPickmode Player[shooter]\obj, 0, False
	;EntityPickmode Player[shooter]\Pivot, 0
	;if EntityPick(b\obj, 10000) Then
	;	pp = CreatePivot()
	;	PositionEntity(pp, PickedX(), PickedY()-0.05, PickedZ())
	;	PointEntity b\obj, pp
	;	FreeEntity pp
	;EndIf
	b\shooter = shooter
	b\bulletspeed = bulletspeed
	b\gunid = gunid
	
	Local pv = CreatePivot()
	PositionEntity pv, x, y, z
	RotateEntity pv, constpitch, constyaw, 0
	MoveEntity pv, 0, 0, -0.5
	
	Local CanVisible%
	If EntityPick(pv, 30) Then
		Local temp = 0, pickent = PickedEntity()
		If pickent <> 0 Then
			For pl.players = Each players
				If Not pl\IsDead Then
					If pickent = pl\obj Or pickent = pl\hitbox Or pickent = pl\Pivot Then
						If pl\ID <> b\shooter And ((Not mp_IsAFriend(Player[b\shooter]\BreachType, pl\BreachType) Or (Not Server\Breach)) Or Server\FriendlyFire) Then
							EntityPickMode pl\Hitbox, 0, True
							;EntityPickmode pl\obj, 0, False
							;EntityPickmode pl\Pivot, 0
							CanVisible = Not Server\BulletAnticheat
							If Not CanVisible Then CanVisible = EntityVisible(Player[b\shooter]\Hitbox, pl\Hitbox)
							If CanVisible Then ; anticheat.
								Local GiveHealth# = Rnd(GetGunDamage(b\gunid)-3, GetGunDamage(b\gunid)+3)-(Player[pl\ID]\WearingVest*8)
								CreateSound("SFX\General\BulletHit.ogg", EntityX(pl\obj), EntityY(pl\obj), EntityZ(pl\obj), 10, 1.0)
								
								If Server\FullSync Then
									If GetScripts() Then
										pbid = public_inqueue(public_OnPlayerHitPlayer)
										public_addparam(pbid, b\shooter)
										public_addparam(pbid, pl\ID)
										public_addparam(pbid, GiveHealth, SE_FLOAT)
										public_addparam(pbid, Player[b\shooter]\UsedWeapon)
										callback
									EndIf
								
									If Not SE_RETURN_VALUE\StaticIndex Then 
										GivePlayerHealth(pl\ID, -GiveHealth, "was killed by "+Player[b\shooter]\name)
										
										If pl\IsDead Then
											If GetScripts() Then
												pbid = public_inqueue(public_OnPlayerKillPlayer)
												public_addparam(pbid, b\shooter)
												public_addparam(pbid, pl\ID)
												public_addparam(pbid, Player[b\shooter]\UsedWeapon)
												callback
											EndIf

											category = breach_GetCategoryByType(Player[b\shooter]\breachtype, True)
												
											If pl\prevbreachtype = SCIENTIST_MODEL And category = CATEGORY_CHAOS Then breach_givetickets(1, 1)
											If pl\prevbreachtype = CLASSD_MODEL And category = CATEGORY_NTF Then breach_givetickets(0, 1)
										EndIf
										If mp_IsASCP(pl\BreachType) Or pl\BreachType = MODEL_035 Then
											category = breach_GetCategoryByType(Player[b\shooter]\breachtype, True)
											If category = CATEGORY_NTF Then breach_givetickets(0, 0.01)
											If category = CATEGORY_CHAOS Then breach_givetickets(1, 0.01)
										EndIf
									EndIf
								Else
									udp_WriteByte M_SHOOT
									udp_WriteByte b\shooter
									udp_SendMessage(pl\ID)
									If mp_IsASCP(pl\BreachType) Or pl\BreachType = MODEL_035 Then
										category = breach_GetCategoryByType(Player[b\shooter]\breachtype, True)
										If category = CATEGORY_NTF Then breach_givetickets(0, 0.01)
										If category = CATEGORY_CHAOS Then breach_givetickets(1, 0.01)
									EndIf
								EndIf
								
							
								temp = 1
							Else
								temp = -1
							EndIf
							EntityPickMode pl\Hitbox, 2, False
							;EntityPickmode pl\obj, 1, False
							;EntityPickmode pl\Pivot, 1, True
						Else
							temp = -1
						EndIf
						Exit
					EndIf
				EndIf
			Next
			If temp <> 1 Then
				For n.NPCs = Each NPCs
					If pickent = n\Collider Or pickent = n\obj Then
						If n\NPCtype = NPCtype008 Or n\NPCtype = NPCtypeZombie Then
							n\hp = n\hp - 20+Rnd(1,3)
							If n\hp < 1 Then n\IsDead = True
						EndIf
						temp = 1
						Exit
					EndIf
				Next
			EndIf
		EndIf
	EndIf

	EntityPickMode Player[shooter]\Hitbox, 2, False
;;	EntityPickmode Player[shooter]\obj, 2, False
	;EntityPickmode Player[shooter]\Pivot, 1, True
	
	;For p.players = Each players
	;	EntityPickmode p\Hitbox, 2, False
	;	EntityPickmode p\obj, 2, False
	;	EntityPickmode p\Pivot, 1, True
	;Next
	
	FreeEntity b\obj
	FreeEntity pv
	Delete b
End Function

Function IsItemGoodFor1162(itt.ItemTemplates)
	Local IN$ = itt\tempname$
	
	Select itt\tempname
		Case "key1", "key2", "key3"
			Return True
		Case "misc", "420", "cigarette"
			Return True
		Case "vest", "finevest","gasmask"
			Return True
		Case "radio","18vradio"
			Return True
		Case "clipboard","eyedrops","nvgoggles"
			Return True
		Case "drawing"
			If itt\img<>0 Then FreeImage itt\img	
			itt\img = LoadImage_Strict("GFX\items\1048\1048_"+Rand(1,20)+".jpg") ;Gives a random drawing.
			Return True
		Default
			If itt\tempname <> "paper" Then
				Return False
			Else If Instr(itt\name, "Leaflet")
				Return False
			Else
				;if the item is a paper, only allow spawning it if the name contains the word "note" or "log"
				;(because those are items created recently, which D-9341 has most likely never seen)
				Return ((Not Instr(itt\name, "Note")) And (Not Instr(itt\name, "Log")))
			EndIf
	End Select
End Function

Function CheckForMultiPlayerInFacility(NearObject)
	;False (=0): NPC is not in facility (mostly meant for "dimension1499")
	;True (=1): NPC is in facility
	;2: NPC is in tunnels (maintenance tunnels/049 tunnels/939 storage room, etc...)
	
	If EntityY(NearObject)>100.0
		Return False
	EndIf
	If EntityY(NearObject)< -10.0
		Return 2
	EndIf
	If EntityY(NearObject)> 7.0 And EntityY(NearObject)<=100.0
		Return 2
	EndIf
	
	Return True
End Function

Function LoadAllSounds()
;Dim OpenDoorSFX%(3,3), CloseDoorSFX%(3,3)
For i = 0 To 2
	OpenDoorSFX(0,i) = LoadSound_Strict("SFX\Door\DoorOpen" + (i + 1) + ".ogg")
	CloseDoorSFX(0,i) = LoadSound_Strict("SFX\Door\DoorClose" + (i + 1) + ".ogg")
	OpenDoorSFX(2,i) = LoadSound_Strict("SFX\Door\Door2Open" + (i + 1) + ".ogg")
	CloseDoorSFX(2,i) = LoadSound_Strict("SFX\Door\Door2Close" + (i + 1) + ".ogg")
	OpenDoorSFX(3,i) = LoadSound_Strict("SFX\Door\ElevatorOpen" + (i + 1) + ".ogg")
	CloseDoorSFX(3,i) = LoadSound_Strict("SFX\Door\ElevatorClose" + (i + 1) + ".ogg")
Next
For i = 0 To 1
	OpenDoorSFX(1,i) = LoadSound_Strict("SFX\Door\BigDoorOpen" + (i + 1) + ".ogg")
	CloseDoorSFX(1,i) = LoadSound_Strict("SFX\Door\BigDoorClose" + (i + 1) + ".ogg")
Next
EatingSFX = LoadSound_Strict("SFX\General\Eating.mp3")
KeyCardSFX1 = LoadSound_Strict("SFX\Interact\KeyCardUse1.ogg")
KeyCardSFX2 = LoadSound_Strict("SFX\Interact\KeyCardUse2.ogg")
ButtonSFX2 = LoadSound_Strict("SFX\Interact\Button2.ogg")
ScannerSFX1 = LoadSound_Strict("SFX\Interact\ScannerUse1.ogg")
ScannerSFX2 = LoadSound_Strict("SFX\Interact\ScannerUse2.ogg")

OpenDoorFastSFX=LoadSound_Strict("SFX\Door\DoorOpenFast.ogg")
CautionSFX% = LoadSound_Strict("SFX\Room\LockroomSiren.ogg")

 ;NuclearSirenSFX%

CameraSFX = LoadSound_Strict("SFX\General\Camera.ogg") 

StoneDragSFX% = LoadSound_Strict("SFX\SCP\173\StoneDrag.ogg")

GunshotSFX% = LoadSound_Strict("SFX\General\Gunshot.ogg")
Gunshot2SFX% = LoadSound_Strict("SFX\General\Gunshot2.ogg")
Gunshot3SFX% = LoadSound_Strict("SFX\General\BulletMiss.ogg")
BullethitSFX% = LoadSound_Strict("SFX\General\BulletHit.ogg")

TeslaIdleSFX = LoadSound_Strict("SFX\Room\Tesla\Idle.ogg")
TeslaActivateSFX = LoadSound_Strict("SFX\Room\Tesla\WindUp.ogg")
TeslaPowerUpSFX = LoadSound_Strict("SFX\Room\Tesla\PowerUp.ogg")

MagnetUpSFX% = LoadSound_Strict("SFX\Room\106Chamber\MagnetUp.ogg") 
MagnetDownSFX = LoadSound_Strict("SFX\Room\106Chamber\MagnetDown.ogg")
 ;FemurBreakerSFX%
 ;EndBreathCHN%
 ;EndBreathSFX%

;Dim DecaySFX%(5)
For i = 0 To 3
	DecaySFX(i) = LoadSound_Strict("SFX\SCP\106\Decay" + i + ".ogg")
Next

BurstSFX = LoadSound_Strict("SFX\Room\TunnelBurst.ogg")

;DrawLoading(20, True)

;Dim RustleSFX%(3)
For i = 0 To 2
	RustleSFX(i) = LoadSound_Strict("SFX\SCP\372\Rustle" + i + ".ogg")
Next

Death914SFX% = LoadSound_Strict("SFX\SCP\914\PlayerDeath.ogg") 
Use914SFX% = LoadSound_Strict("SFX\SCP\914\PlayerUse.ogg")

;Dim DripSFX%(4)
For i = 0 To 3
	DripSFX(i) = LoadSound_Strict("SFX\Character\D9341\BloodDrip" + i + ".ogg")
Next

LeverSFX% = LoadSound_Strict("SFX\Interact\LeverFlip.ogg") 
LightSFX% = LoadSound_Strict("SFX\General\LightSwitch.ogg")

ButtGhostSFX% = LoadSound_Strict("SFX\SCP\Joke\789J.ogg")

;Dim RadioSFX(5,10)
RadioSFX(1,0) = LoadSound_Strict("SFX\Radio\RadioAlarm.ogg")
RadioSFX(1,1) = LoadSound_Strict("SFX\Radio\RadioAlarm2.ogg")
For i = 0 To 8
	RadioSFX(2,i) = LoadSound_Strict("SFX\Radio\scpradio"+i+".ogg")
Next
RadioSquelch = LoadSound_Strict("SFX\Radio\squelch.ogg")
RadioStatic = LoadSound_Strict("SFX\Radio\static.ogg")
RadioBuzz = LoadSound_Strict("SFX\Radio\buzz.ogg")

ElevatorBeepSFX = LoadSound_Strict("SFX\General\Elevator\Beep.ogg") 
ElevatorMoveSFX = LoadSound_Strict("SFX\General\Elevator\Moving.ogg") 

;Dim PickSFX%(10)
For i = 0 To 3
	PickSFX(i) = LoadSound_Strict("SFX\Interact\PickItem" + i + ".ogg")
Next
 ;AmbientSFXCHN% 
;CurrAmbientSFX%
;Dim AmbientSFXAmount(6)
;0 = light containment, 1 = heavy containment, 2 = entrance
AmbientSFXAmount(0)=11 : AmbientSFXAmount(1)=11 : AmbientSFXAmount(2)=12
;3 = general, 4 = pre-breach
AmbientSFXAmount(3)=15 : AmbientSFXAmount(4)=5
;5 = forest
AmbientSFXAmount(5)=10

;Dim AmbientSFX%(6, 15)

;Dim OldManSFX%(6)
For i = 0 To 2
	OldManSFX(i) = LoadSound_Strict("SFX\SCP\106\Corrosion" + (i + 1) + ".ogg")
Next
OldManSFX(3) = LoadSound_Strict("SFX\SCP\106\Laugh.ogg")
OldManSFX(4) = LoadSound_Strict("SFX\SCP\106\Breathing.ogg")
OldManSFX(5) = LoadSound_Strict("SFX\Room\PocketDimension\Enter.ogg")
For i = 0 To 2
	OldManSFX(6+i) = LoadSound_Strict("SFX\SCP\106\WallDecay"+(i+1)+".ogg")
Next

;Dim Scp173SFX%(3)
For i = 0 To 2
	Scp173SFX(i) = LoadSound_Strict("SFX\SCP\173\Rattle" + (i + 1) + ".ogg")
Next

;Dim HorrorSFX%(20)
For i = 0 To 11
	HorrorSFX(i) = LoadSound_Strict("SFX\Horror\Horror" + i + ".ogg")
Next
For i = 14 To 15
	HorrorSFX(i) = LoadSound_Strict("SFX\Horror\Horror" + i + ".ogg")
Next

;DrawLoading(25, True)

;Dim IntroSFX%(20)

For i = 7 To 9
	IntroSFX(i) = LoadSound_Strict("SFX\Room\Intro\Bang" + (i - 6) + ".ogg")
Next
For i = 10 To 12
	IntroSFX(i) = LoadSound_Strict("SFX\Room\Intro\Light" + (i - 9) + ".ogg")
Next
;IntroSFX(13) = LoadSound_Strict("SFX\intro\shoot1.ogg")
;IntroSFX(14) = LoadSound_Strict("SFX\intro\shoot2.ogg")
IntroSFX(15) = LoadSound_Strict("SFX\Room\Intro\173Vent.ogg")

;Dim AlarmSFX%(5)
AlarmSFX(0) = LoadSound_Strict("SFX\Alarm\Alarm.ogg")
;AlarmSFX(1) = LoadSound_Strict("SFX\Alarm\Alarm2.ogg")
AlarmSFX(2) = LoadSound_Strict("SFX\Alarm\Alarm3.ogg")

;room_gw alarms
AlarmSFX(3) = LoadSound_Strict("SFX\Alarm\Alarm4.ogg")
AlarmSFX(4) = LoadSound_Strict("SFX\Alarm\Alarm5.ogg")

;Dim CommotionState%(23)

 HeartBeatSFX = LoadSound_Strict("SFX\Character\D9341\Heartbeat.ogg")

 ;VomitSFX%

;Dim BreathSFX(2,5)
 ;BreathCHN%
For i = 0 To 4
	BreathSFX(0,i)=LoadSound_Strict("SFX\Character\D9341\breath"+i+".ogg")
	BreathSFX(1,i)=LoadSound_Strict("SFX\Character\D9341\breath"+i+"gas.ogg")
Next


;Dim NeckSnapSFX(3)
For i = 0 To 2
	NeckSnapSFX(i) =  LoadSound_Strict("SFX\SCP\173\NeckSnap"+(i+1)+".ogg")
Next

;Dim DamageSFX%(9)
For i = 0 To 8
	DamageSFX(i) = LoadSound_Strict("SFX\Character\D9341\Damage"+(i+1)+".ogg")
Next

;Dim MTFSFX%(8)

;Dim CoughSFX%(3)
	;CoughCHN% 
	;VomitCHN%
For i = 0 To 2
	CoughSFX(i) = LoadSound_Strict("SFX\Character\D9341\Cough" + (i + 1) + ".ogg")
Next

 MachineSFX% = LoadSound_Strict("SFX\SCP\914\Refining.ogg")

 ApacheSFX = LoadSound_Strict("SFX\Character\Apache\Propeller.ogg")

 ;CurrStepSFX
;Dim StepSFX%(4, 2, 8) ;(normal/metal, walk/run, id)
For i = 0 To 7
	StepSFX(0, 0, i) = LoadSound_Strict("SFX\Step\Step" + (i + 1) + ".ogg")
	StepSFX(1, 0, i) = LoadSound_Strict("SFX\Step\StepMetal" + (i + 1) + ".ogg")
	StepSFX(0, 1, i)= LoadSound_Strict("SFX\Step\Run" + (i + 1) + ".ogg")
	StepSFX(1, 1, i) = LoadSound_Strict("SFX\Step\RunMetal" + (i + 1) + ".ogg")
	If i < 3
		StepSFX(2, 0, i) = LoadSound_Strict("SFX\Character\MTF\Step" + (i + 1) + ".ogg")
		StepSFX(3, 0, i) = LoadSound_Strict("SFX\SCP\049\Step"+ (i + 1) + ".ogg")
	EndIf
	If i < 4
        StepSFX(4, 0, i) = LoadSound_Strict("SFX\Step\SCP\StepSCP" + (i + 1) + ".ogg");new one 1.3.9
    EndIf
Next

;Dim Step2SFX(6)
For i = 0 To 2
	Step2SFX(i) = LoadSound_Strict("SFX\Step\StepPD" + (i + 1) + ".ogg")
	Step2SFX(i+3) = LoadSound_Strict("SFX\Step\StepForest" + (i + 1) + ".ogg")
Next
DoorOpen079SFX = LoadSound_Strict("SFX\Door\DoorOpen079.ogg")
DoorClose079SFX = LoadSound_Strict("SFX\Door\DoorClose079.ogg")
Triggered096SFX = LoadSound_Strict("SFX\SCP\096\Triggered.ogg")
End Function










Function mp_IsASCP(role)
	Local BT.BreachTypes = GetBreachType(role)
	Return BT\IsASCP
End Function
Global gay
Global prevgl$
Type gl_
	Field name$
	Field ms, gms
End Type
Function gl_add(name$)
	If prevgl <> "" Then gl_stop(prevgl)
	p.gl_ = New gl_
	p\name = name
	p\ms = MilliSecs()
	prevgl = name
End Function
Function gl_stop(name$)
	For p.gl_ = Each gl_
		If p\name = name Then
			p\gms = MilliSecs()-p\ms
			Return
		EndIf
	Next
End Function

Include "Source Code\FPSfactor.bb"

Function UpdateMap(optional = 0)

	;While FPSfactor = 0.0
	
	If Framelimit > 0 Then
	    ;Framelimit
		Delay (1000.0 / Framelimit) - (MilliSecs2() - LoopDelay)
		LoopDelay = MillISecs2()
	EndIf
	
	If PlayerRoom = Null Then
		For r.Rooms = Each Rooms
			If r\RoomTemplate\Name = "pocketdimension" Then 
				PlayerRoom = r
				Exit
			EndIf
		Next
	EndIf
	
	
	If FixedTimeSteps Then

		SetCurrTime(MilliSecs2())
		AddToTimingAccumulator(GetCurrTime()-GetPrevTime())
		SetPrevTime(GetCurrTime())
		FPSfactor = GetTickDuration()
		FPSfactor2 = FPSfactor
		
		While (ft\accumulator>0.0)
			ft\accumulator = ft\accumulator-GetTickDuration()
			If GetScripts() Then
				pbid = public_inqueue(public_OnMapUpdate)
				public_addparam(pbid, FPSfactor, SE_FLOAT)
				callback
			EndIf
			UpdateServerMain
		Wend
	Else
		CurTime = MicroSecs()
		ElapsedTime = (CurTime - PrevTime) / 1000000.0
		PrevTime = CurTime
		PrevFPSFactor = FPSfactor
		FPSfactor = Min(Max(ElapsedTime * 70, 0.000001), FPSFactorLimit)
		FPSfactor2 = FPSfactor
		
		If GetScripts() Then
			pbid = public_inqueue(public_OnMapUpdate)
			public_addparam(pbid, FPSfactor, SE_FLOAT)
			callback
		EndIf
			
		UpdateServerMain
	EndIf
	
	;if KeyHit(28) Then gay = Not gay
	
	;if MouseHit(2) then
	;	For p.players = Each players
	;		if p\ID <> 0 then
				;if p\breachtype = classd_model then
	;				PositionEntity Camera, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot)
				;endif
	;		endif
	;	next
	;endif
	;AmbientLight 255,255,255
	;CameraProjMode Camera,1
	;RenderWorld()
	;Flip 0
	;DebugLog HideDistance
End Function

Function UpdateServerMain()
	UpdateRooms()
	UpdateNPCs()
	mp_UpdatePlayers()
	UpdateEmitters()
	UpdateDoors()
	UpdateParticles()
	UpdateDevilEmitters()
	UpdateParticles_Devil()
	UpdateDecals()
	UpdateMTF()
	UpdateRockets()
	UpdateSecurityCams()
	UpdateItems()
	UpdateDeadBodies()
	UpdateWorld()
	
	QuickLoadEvents()
	UpdateEvents()
	UpdateDimension1499()
	
	;if not gay Then
	;	If KeyDown(31) Then MoveEntity Camera, 0, 0, -0.1*FPSfactor
	;	;If KeyDown(17) Then MoveEntity Camera, 0, 0, 0.1*FPSfactor
	;	
	;	If KeyDown(30) Then MoveEntity Camera, -0.1*FPSfactor, 0, 0
	;	If KeyDown(32) Then MoveEntity Camera, 0.1*FPSfactor, 0, 0
	;	TurnEntity Camera, MouseYSpeed()*0.2, -MouseXSpeed()*0.2, 0
	;Else
	;	MouseXSpeed()
	;	MouseYSpeed()
	;EndIf
End Function

; ============================== guns

	; ================================ Fire weapon
	Const GUN_USP = 1
	Const GUN_P90 = 2
	Const GUN_MP5SD = 3
	Const GUN_BAZOOKA = 4
	Const GUN_MINIGUN = 5
	Const GUN_MICROHID = 6
	Const GUN_DEAGLE = 7
	Const GUN_SHOTGUN = 8
	Const GUN_HKG36 = 9
	Const GUN_M4A4 = 10

	; ================================= Steel weapon
	Const GUN_HANDCUFFS = 11
	Const GUN_KNIFE = 12
	Const GUN_GRENADE = 13
	Const GUN_GRENADEFLASHBANG = 14
	Const GUN_GRENADESMOKE = 15
	; =================================
	
; =============================

Global GunDamage#[GUN_GRENADESMOKE+1]
Global GunSpread#[GUN_GRENADESMOKE+1]
Global GunShootTicks[GUN_GRENADESMOKE+1]

Function IsAGun(tempname$)
	Select Lower(tempname)
		Case "usp"
			Return GUN_USP
		Case "p90"
			Return GUN_P90
		Case "mp5sd"
			Return GUN_MP5SD
		Case "rpg"
			Return GUN_BAZOOKA
		Case "minigun"
			Return GUN_MINIGUN
		Case "grenade"
			Return GUN_GRENADE
		Case "grenadeflashbang"
			Return GUN_GRENADEFLASHBANG
		Case "grenadesmoke"
			Return GUN_GRENADESMOKE
		Case "microhid"
			Return GUN_MICROHID
		Case "deagle"
			Return GUN_DEAGLE
		Case "spas12"
			Return GUN_SHOTGUN
		Case "m4a4"
			Return GUN_M4A4
		Case "handcuffs"
			Return GUN_HANDCUFFS
		Case "knife": Return GUN_KNIFE
		Case "hkg36": Return GUN_HKG36
	End Select
	Return 0
End Function

Function GetGunName$(gID)
	Select gID
		Case GUN_USP
			Return "usp"
		Case GUN_P90
			Return "p90"
		Case GUN_MP5SD
			Return "mp5sd"
		Case GUN_BAZOOKA
			Return "rpg"
		Case GUN_MINIGUN
			Return "minigun"
		Case GUN_GRENADE
			Return "grenade"
		Case GUN_MICROHID
			Return "microhid"
		Case GUN_DEAGLE
			Return "deagle"
		Case GUN_SHOTGUN
			Return "spas12"
		Case GUN_M4A4
			Return "m4a4"
		Case GUN_HANDCUFFS
			Return "handcuffs"
		Case GUN_GRENADESMOKE
			Return "grenadesmoke"
		Case GUN_GRENADEFLASHBANG
			Return "grenadeflashbang"
		Case GUN_KNIFE: Return "knife"
		Case GUN_HKG36: Return "hkg36"
	End Select
	Return ""
End Function

Function GetGunShootTicks(gID)
	Return GunShootTicks[gID]
End Function

Function GetGunSpreadRate#(gID)
	Return GunSpread[gID]
End Function

Function GetGunDamage#(gID)
	Return GunDamage[gID]
End Function

Function IsValidGun(gID)
	Return (gID >= 0 And gID <= GUN_GRENADESMOKE)
End Function

Function IsAHalloween%()
	Month$ = Right(Left(CurrentDate(), 6), 3)
	Number% = Int(Left(CurrentDate(), 2))
	
	Select Month
		Case "Oct"
			Return (Number >= 1 And Number <= 31)
		Case "Nov"
			Return (Number=1)
	End Select
	
	Return False
End Function

Function IsANewYear%()
	Month$ = Right(Left(CurrentDate(), 6), 3)
	Number% = Int(Left(CurrentDate(), 2))
	
	Select Month
		Case "Dec"
			Return (Number >= 23 And Number <= 31)
		Case "Jan"
			Return (Number >= 1 And Number <= 4)
	End Select
	
	Return False
End Function

Function PlaceHalloweenScene(r.Rooms, id, Scene%, x#, y#, z#, a#)
	If HALLOWEENINDEX Then
	
		Ent = CreatePivot()
		PositionEntity Ent, x, y+1, z

		Local l = AddLight(r, EntityX(ent, True), EntityY(ent, True), EntityZ(ent, True), 2, 0.2, 242, 157, 0)
		EntityParent l, ent
		MoveEntity l, 0, 1.5, 0
		For i = 0 To r\MaxLights
			If r\Lights[i] = l Then
				EntityParent r\LightSpritesPivot[i], ent
				MoveEntity r\LightSpritesPivot[i], 0, 1.5, 0
				EntityParent r\LightSprites[i], ent
				MoveEntity r\LightSprites[i], 0, 1.5, 0
			EndIf
		Next
		
		EntityParent(Ent, r\obj)
		ScaleEntity Ent, 0.7+scl, 0.7+scl, 0.7+scl
		
		;AddEntityToRoomProps(r, Ent)
		
	EndIf
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D