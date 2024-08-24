Global GunPivot, Recoil#
Global HoldingGun = 0

Global ShootEmptySFX

Global MuzzleFlash

Global KEY_RELOAD = 19

Global GunPivot_Y#
Global GunPivot_YSide% = 0
Global GunPivot_X#
Global GunPivot_XSide% = 0

Global GunRoll#

Global AmmoText$
Global AmmoTimer#

Global WeaponShake#
;Global RecoilRegen#

Const DOWNRECOIL# = 0 ; this recoil reduces the pitch after shots

Const StandartRecoil# = DOWNRECOIL
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

Global GUN_INFO.Guns[GUN_GRENADESMOKE]

; ------ anims
Const IDLE_ANIM = 1
Const RELOAD_ANIM = 2
Const SHOOT_ANIM = 3
Const DEPLOY_ANIM = 4
Const DEPLOY_FRAME = 5
Const START_ANIM = 6

Type Guns
	Field ID					;<--- The ID of the gun. WARNING: Overwriting existing IDs could cause to the game would be glitched or at the worst case a MAV
	Field CurrAmmo				;<--- Current ammo in a magazine
	Field MaxCurrAmmo			;<--- Max ammo in a magazine
	Field ShootState# = 0.0		;<--- Dont change this variables in the "CreateGun" or "InitGuns" functions!
	Field ShootSpeed# 			;<--- speed shoot
	Field ReloadState# = 0.0	;<--- 								"-"
	Field Deployed% = 0			;<---								"-"
	Field ShootAnim = 0			;<---								"-"
	Field PrevFrame#
	Field ReloadSound
	Field ViewModel%
	Field RecoilForce#
	Field ChannelGun
	Field ReloadAnim#[2]
	Field IdleAnim#
	Field DeployFrame#
	Field DeployAnim#[2]
	Field ShootFrame#[2]
	Field Magazines
	Field ShootSound,ShootSoundName$
	Field DeploySound
	Field StartFrame[2]
	Field SingleMode
	Field GunParticle
	Field gmovex#, gmovey#, gmovez#
	Field SightRate#, SightState%, CurrentSightMove#[1]
	Field GunDamage#
	Field MaxMagazines
	Field BulletTicks%
	Field GunSpreadRate#
	Field GrenadeSlow%
End Type

Global EqquipedGun.Guns
Global PrevEqquipedGun.Guns

Function CreateGun.Guns(id, single, ViewModel$, shootsound$, reloadsound$, deploysound$, speed#, ammo, maxammo, magazines, recoilf#, scalex#, scaley#, scalez#, movex#=0, movey#=0, movez#=0, partx#, party#, partz#)
	Local g.Guns = New Guns
	
	g\ID = id
	g\CurrAmmo = ammo
	g\MaxCurrAmmo = maxammo
	g\Magazines = magazines
	g\MaxMagazines = magazines
	g\ShootSpeed# = speed#

	g\ViewModel = LoadAnimMesh_Strict(ViewModel)
	if g\ID = GUN_GRENADE or g\ID = GUN_GRENADEFLASHBANG Or g\ID = GUN_GRENADESMOKE Then RotateMesh g\ViewModel, 0, -90, 0
	;if g\ID = GUN_MINIGUN Then RotateMesh g\ViewModel, 0, 180, 0
	g\recoilforce = recoilf
	
	ScaleEntity g\ViewModel,scalex#,scaley#,scalez#
	EntityParent g\ViewModel,GunPivot
	MoveEntity g\ViewModel,movex,movey,movez
	HideEntity g\ViewModel
	g\ShootSoundName = shootsound
	
	g\shootsound = LoadSound_Strict(shootsound)
	g\reloadsound = LoadSound_Strict(reloadsound)
	g\deploysound = LoadSound_Strict(deploysound)
	g\SingleMode = single
	
	g\GunParticle = CreateSprite()
	EntityParent g\GunParticle,GunPivot
	MoveEntity g\GunParticle,partx,party,partz
	EntityTexture(g\GunParticle, MuzzleFlash)
	EntityFX(g\GunParticle, 1 + 8)
	SpriteViewMode (g\GunParticle, 3)
	EntityBlend(g\GunParticle, BLEND_ADD)
	ScaleSprite(g\GunParticle, 0.07, 0.07)
	HideEntity g\GunParticle
	
	AddShootTicksToGun(g, 1)
	AddSpreadRateToGun(g, 1.0)
	g\gmovex = movex
	g\gmovey = movey
	g\gmovez = movez
	MeshCullBox (g\ViewModel, -MeshWidth(g\ViewModel), -MeshHeight(g\ViewModel), -MeshDepth(g\ViewModel), MeshWidth(g\ViewModel)*8, MeshHeight(g\ViewModel)*8, MeshDepth(g\ViewModel)*8)
	
	GUN_INFO[id] = g
	Return g
End Function

Function InitGuns()
	HoldingGun = 0

	ShootEmptySFX = LoadSound_Strict("SFX\Guns\shoot_empty.ogg")

	GunPivot = CreatePivot()
	
	MuzzleFlash = LoadTexture("GFX\flash.jpg",1+2)
	
	CreateGun(GUN_USP,True, "GFX\Guns\USP_Tactical_Viewmodel.b3d", "SFX\Guns\USP\shoot_in_01.ogg", "SFX\Guns\USP\reload1.ogg", "SFX\Guns\USP\deploy.ogg", 7.5, 12, 12, 10, 1, 0.017, 0.017, 0.017,0.01,0.0,0.02,0.08,-0.05,0.45)
	CreateGun(GUN_P90,False, "GFX\Guns\P90_Viewmodel.b3d", "SFX\Guns\P90\shoot_in_01.ogg", "SFX\Guns\P90\reload.ogg", "SFX\Guns\P90\deploy.ogg", 6, 50, 50, 10, 0.8, 0.017, 0.017, 0.017, 0.01,0.0,0,0.06,-0.05,0.45)
	CreateGun(GUN_MP5SD, False, "GFX\Guns\mp5sd_viewmodel.b3d", "SFX\Guns\MP5\shoot_in_01.ogg", "SFX\Guns\MP5\reload.ogg", "SFX\Guns\MP5\deploy.ogg", 6, 30, 30, 10, 0.78, 0.015, 0.015, 0.015, -0.02,0.01,-0.06,0.06,-0.06,0.47)
	CreateGun(GUN_BAZOOKA, True, "GFX\Guns\rpg_viewmodel.b3d", "empty", "SFX\Guns\Bazooka\reload.mp3", "SFX\Guns\P90\deploy.ogg", 10, 1, 1, 5, 3, 0.013, 0.013, 0.013, 0.00, 0.0063, 0.0,0.06,-0.03,0.45)
	CreateGun(GUN_MINIGUN, False, "GFX\Guns\M134_Viewmodel.b3d", "SFX\Guns\Minigun\shoot.ogg", "SFX\Guns\Minigun\reload.mp3", "SFX\Guns\P90\deploy.ogg", 5, 100, 100, 5, 1.7, 0.013, 0.013, 0.013, 0.00,0.01,0,0.06,-0.06,0.45)
	CreateGun(GUN_MICROHID, True, "GFX\Guns\MicroHid_Viewmodel.b3d", "SFX\Guns\MicroHID\hid.ogg", "SFX\Guns\P90\deploy.ogg", "SFX\Guns\P90\deploy.ogg", 1050, 1, 1, 0, 0.2, 0.013, 0.013, 0.013, 0.02,-0.05,0.13,0.06,-0.06,0.45)
	CreateGun(GUN_DEAGLE,True, "GFX\Guns\Deagle_Viewmodel.b3d", "SFX\Guns\Deagle\shoot1.ogg", "SFX\Guns\Deagle\reload.ogg", "SFX\Guns\USP\deploy.ogg", 9, 7, 7, 8, 1.2, 0.02, 0.02, 0.02,0.01,0.0,0.0,0.08,-0.05,0.45)
	CreateGun(GUN_SHOTGUN,True, "GFX\Guns\SPAS_Viewmodel.b3d", "SFX\Guns\Shotgun\shoot1.ogg", "SFX\Guns\Shotgun\reload.mp3", "SFX\Guns\USP\deploy.ogg", 15, 6, 6, 5, 0.6, 0.017, 0.017, 0.017,0.0,0.0,0.0,0.08,-0.03,0.6)
	CreateGun(GUN_M4A4,False, "GFX\Guns\M4_Viewmodel.b3d", "SFX\Guns\M4A4\shoot1.ogg", "SFX\Guns\M4A4\reload.ogg", "SFX\Guns\USP\deploy.ogg", 7, 30, 30, 8, 0.8, 0.026, 0.026, 0.026, 0.0,0.01,-0.03,0.08,-0.05,0.45)
	CreateGun(GUN_HANDCUFFS, True, "GFX\Guns\Handcuts_Viewmodel.b3d", "", "", "SFX\Guns\USP\deploy.ogg", 1, 0, 0, 0, 0, 0.021, 0.021, 0.021, 0.05,0.01,0.05,0.08,-0.05,0.45)
	if Not IsAHalloween() Then
		CreateGun(GUN_GRENADE, True, "GFX\Guns\grenadeviewmodel.b3d", "", "", "SFX\Guns\P90\deploy.ogg", 1, 0, 0, 0, 0, 0.02, 0.02, 0.02, 0.1,-1.2,-0.0,0.06,-0.03,0.6)
	Else
		CreateGun(GUN_GRENADE, True, "GFX\Guns\grenadeviewmodelHw.b3d", "", "", "SFX\Guns\P90\deploy.ogg", 1, 0, 0, 0, 0, 0.02, 0.02, 0.02, 0.1,-1.2,-0.0,0.06,-0.03,0.6)
	EndIf
	
	CreateGun(GUN_GRENADEFLASHBANG, True, "GFX\Guns\flashgrenadeviewmodel.b3d", "", "", "SFX\Guns\P90\deploy.ogg", 1, 0, 0, 0, 0, 0.02, 0.02, 0.02, 0.1,-1.2,-0.0,0.06,-0.03,0.6)
	CreateGun(GUN_GRENADESMOKE, True, "GFX\Guns\smokegrenadeviewmodel.b3d", "", "", "SFX\Guns\P90\deploy.ogg", 1, 0, 0, 0, 0, 0.02, 0.02, 0.02, 0.1,-1.2,-0.0,0.06,-0.03,0.6)
	CreateGun(GUN_KNIFE, False, "GFX\Guns\knife_viewmodel.b3d", "", "", "SFX\Guns\P90\deploy.ogg", 35, 0, 0, 0, 0, 0.017, 0.017, 0.017, 0.01,-0.05,0,0.06,-0.05,0.45)
	CreateGun(GUN_HKG36,False, "GFX\Guns\HKG36_Viewmodel.b3d", "SFX\Guns\HKG36\shoot.ogg", "SFX\Guns\HKG36\reload.ogg", "SFX\Guns\HKG36\deploy.ogg", 6, 30, 30, 10, 0.8, 0.017, 0.017, 0.017, -0.02,-0.1,-0.02,0.07,-0.05,0.45)
	
	For g.Guns = Each guns
		Select g\ID
			Case GUN_USP: 
				AddFrameToGun(g, IDLE_ANIM, 41)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 37)
				AddFrameToGun(g, DEPLOY_FRAME, 12)
				AddFrameToGun(g, RELOAD_ANIM, 105, 168)
				AddFrameToGun(g, SHOOT_ANIM,171,197)
				
				SetGunDamage(g, 17)
				AddSightRateToGun(g, -0.055)
			Case GUN_P90: 
				AddFrameToGun(g, IDLE_ANIM, 32)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 30)
				AddFrameToGun(g, DEPLOY_FRAME, 2)
				AddFrameToGun(g, RELOAD_ANIM, 50, 199)
				AddFrameToGun(g, SHOOT_ANIM,200,228)
				AddSightRateToGun(g, -0.048)
				SetGunDamage(g, 18)
			Case GUN_MP5SD:
				AddFrameToGun(g, IDLE_ANIM, 162)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 36)
				AddFrameToGun(g, DEPLOY_FRAME, 22)
				AddFrameToGun(g, RELOAD_ANIM, 37, 141)
				AddFrameToGun(g, SHOOT_ANIM,142,161)
				AddSightRateToGun(g, -0.07)
				SetGunDamage(g, 18)
			Case GUN_BAZOOKA
				AddFrameToGun(g, IDLE_ANIM, 90)
				AddFrameToGun(g, DEPLOY_ANIM, 92, 107)
				AddFrameToGun(g, DEPLOY_FRAME, 94)
				AddFrameToGun(g, RELOAD_ANIM, 109, 157)
				AddFrameToGun(g, SHOOT_ANIM,159,179)
				AddSightRateToGun(g, -0.06)
				SetGunDamage(g, 90)
			Case GUN_MINIGUN
				AddFrameToGun(g, IDLE_ANIM, 1)
				AddFrameToGun(g, DEPLOY_ANIM, 3, 33)
				AddFrameToGun(g, DEPLOY_FRAME, 7)
				AddFrameToGun(g, RELOAD_ANIM, 34, 184)
				AddFrameToGun(g, SHOOT_ANIM,186,215)
				AddSightRateToGun(g, -0.04)
				SetGunDamage(g, 23)
			Case GUN_MICROHID
				AddFrameToGun(g, IDLE_ANIM, 92)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 81)
				AddFrameToGun(g, DEPLOY_FRAME, 20)
				AddFrameToGun(g, RELOAD_ANIM, 92, 92)
				AddFrameToGun(g, SHOOT_ANIM,94,390)
				AddSightRateToGun(g, -0.06)
				SetGunDamage(g, 200)
			Case GUN_DEAGLE: 
				AddFrameToGun(g, IDLE_ANIM, 32)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 31)
				AddFrameToGun(g, DEPLOY_FRAME, 12)
				AddFrameToGun(g, RELOAD_ANIM, 33, 110)
				AddFrameToGun(g, SHOOT_ANIM,112,128)
				
				SetGunDamage(g, 24)
				AddSightRateToGun(g, -0.04)
			Case GUN_SHOTGUN
				AddFrameToGun(g, IDLE_ANIM, 24)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 21)
				AddFrameToGun(g, DEPLOY_FRAME, 5)
				AddFrameToGun(g, RELOAD_ANIM, 34, 262)
				AddFrameToGun(g, SHOOT_ANIM,263,282)
				AddShootTicksToGun(g, 6)
				SetGunDamage(g, 20)
				AddSightRateToGun(g, -0.09)
				AddSpreadRateToGun(g, 4)
			Case GUN_M4A4
				AddFrameToGun(g, IDLE_ANIM, 45)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 43)
				AddFrameToGun(g, DEPLOY_FRAME, 12)
				AddFrameToGun(g, RELOAD_ANIM, 56, 160)
				AddFrameToGun(g, SHOOT_ANIM,165,175)
				
				SetGunDamage(g, 22)
				AddSightRateToGun(g, -0.072)
			Case GUN_HKG36
				AddFrameToGun(g, IDLE_ANIM, 45)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 41)
				AddFrameToGun(g, DEPLOY_FRAME, 20)
				AddFrameToGun(g, RELOAD_ANIM, 74, 150)
				AddFrameToGun(g, SHOOT_ANIM,53,73)
				
				SetGunDamage(g, 22)
				AddSightRateToGun(g, -0.08)
			Case GUN_GRENADE,GUN_HANDCUFFS,GUN_GRENADEFLASHBANG,GUN_GRENADESMOKE
				g\Deployed = True
			Case GUN_KNIFE
				AddFrameToGun(g, IDLE_ANIM, 18)
				AddFrameToGun(g, DEPLOY_ANIM, 1, 16)
				AddFrameToGun(g, SHOOT_ANIM,34,55)
		End Select
	Next
	FreeTexture MuzzleFlash
End Function
Function GetGunShootTicks(gID)
	Return GUN_INFO[gID]\BulletTicks
End Function
Function AddShootTicksToGun(g.Guns, ticks)
	g\BulletTicks = ticks
End Function
Function AddFrameToGun(g.Guns, aType, start, endanim = 0)
	Select aType
		Case IDLE_ANIM
			g\IdleAnim = start
		Case RELOAD_ANIM
			g\ReloadAnim[0] = start
			g\ReloadAnim[1] = endanim
		Case SHOOT_ANIM
			g\ShootFrame[0] = start
			g\ShootFrame[1] = endanim
		Case DEPLOY_ANIM
			g\DeployAnim[0] = start
			g\DeployAnim[1] = endanim
		Case DEPLOY_FRAME
			g\DeployFrame = start
	End Select
End Function
Function AddSpreadRateToGun(g.Guns, spreadrate#)
	g\GunSpreadRate = spreadrate
End Function
Function AddSightRateToGun(g.Guns, aType#)
	g\SightRate = aType
End Function
Function SetGunDamage(g.Guns, Damage#)
	g\GunDamage = Damage
End Function
Function GetGunSpreadRate#(gID)
	Return GUN_INFO[gid]\GunSpreadRate
End Function
Function GetGunDamage#(gID)
	Return GUN_INFO[gid]\GunDamage
End Function
Function GetGunSound$(gID)
	Return GUN_INFO[gID]\ShootSoundName
End Function

Function GetIdleAnim#(g.Guns)
	Return g\IdleAnim
End Function
Function GetReloadAnim#(g.Guns, startend)
	Return g\ReloadAnim[startend]
End Function
Function GetShootAnim#(g.Guns, startend)
	Return g\ShootFrame[startend]
End Function
Function GetDeployFrame#(g.Guns)
	Return g\DeployFrame
End Function
Function GetDeployAnim#(g.Guns, startend)
	Return g\DeployAnim[startend]
End Function
Function DeleteGuns()
	
	For g.Guns = Each Guns
		if g\deploysound <> 0 Then FreeSound_Strict g\deploysound
		if g\shootsound <> 0 Then FreeSound_Strict g\shootsound
		if g\reloadsound <> 0 Then FreeSound_Strict g\reloadsound
		Delete g
	Next
	
	FreeSound_Strict ShootEmptySFX : ShootEmptySFX = 0
	
End Function

Const MAX_ANGLE_LIMIT = 5

Function UpdateGuns()
	Local g.Guns, n.NPCs
	Local de.Decals
	HoldingGun = 0
	
	Local ShouldShoot% = False
	Local ShouldReload% = False
	Local ShouldClearShoot% = False
	
	if Spectate\SpectatePlayer <> -1 Then
		EqquipedGun = Null
		HoldingGun = 0
			
		if Spectate\SpectateType = 1 Then
			if Player[Spectate\SpectatePlayer] <> Null Then
				if Player[Spectate\SpectatePlayer]\UsedWeapon > 0 then
					EqquipedGun = GetGunType(Player[Spectate\SpectatePlayer]\UsedWeapon)
					if EqquipedGun <> Null Then
						if EqquipedGun\ID < GUN_HANDCUFFS Or EqquipedGun\ID = GUN_KNIFE Then
							if EqquipedGun\Deployed = 0 Then
								Animate2(EqquipedGun\ViewModel,AnimTime(EqquipedGun\ViewModel),GetidleAnim(EqquipedGun),GetidleAnim(EqquipedGun),1,False)
								EqquipedGun\PrevFrame# = GetidleAnim(EqquipedGun)
								EqquipedGun\Deployed = 1
							EndIf
						EndIf
						
						if Player[Spectate\SpectatePlayer]\IsShooted Then 
							ShouldShoot = True
						EndIf
						
						if Player[Spectate\SpectatePlayer]\IsReloaded Then 
							ShouldReload = True
						EndIf
						
						if Player[Spectate\SpectatePlayer]\IsClearShoot Then 
							ShouldClearShoot = True
						EndIf
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
	
	For p.players = Each players
		p\IsShooted = False
		p\IsReloaded = False
		p\IsClearShoot = False
	Next

	; ============================================ Update Gun
		g = EqquipedGun
		
		if g <> PrevEqquipedGun Then
			if PrevEqquipedGun <> Null Then
				HideEntity PrevEqquipedGun\ViewModel
				HideEntity PrevEqquipedGun\GunParticle
				if PrevEqquipedGun\ID < GUN_HANDCUFFS Or PrevEqquipedGun\ID = GUN_KNIFE Then PrevEqquipedGun\Deployed=0
				if PrevEqquipedGun\ID = GUN_MICROHID Then MyPlayer\MicroHIDSHOOT = False
				PrevEqquipedGun\ShootState# = 0.0
				PrevEqquipedGun\ReloadState# = 0.0
				PrevEqquipedGun\ShootAnim = 0
				PrevEqquipedGun\PrevFrame = 0
				SetAnimTime(PrevEqquipedGun\ViewModel, 0)
			EndIf
			PrevEqquipedGun = g
		EndIf
		
		if g <> Null Then
			shaking# = (Cos(MilliSecs()*0.07)*0.3);
			g\PrevFrame = AnimTime(g\viewmodel)
			RotateEntity(GunPivot, CurveAngle(EntityPitch(Camera)+shaking, EntityPitch(GunPivot),4),CurveAngle(EntityYaw(Camera)+shaking, EntityYaw(GunPivot), 4), EntityRoll(Camera)+(shaking*0.5))
			RotateEntity(GunPivot, EntityPitch(GunPivot)+Min(6, Recoil),EntityYaw(GunPivot)+GetRecoilLR(), EntityRoll(GunPivot))
			
			ShowEntity g\ViewModel
			HoldingGun = g\ID
			
			if ShouldReload Then
				if g\ReloadState# = 0.0 Then
					g\CurrAmmo = 0
					g\PrevFrame# = GetReloadAnim(g, 0)
					Animate2(g\viewmodel,AnimTime(g\viewmodel),GetReloadAnim(g, 0),GetReloadAnim(g, 0),0.5,False)
					g\ReloadState# = 1.0
				EndIf
			Elseif ShouldShoot Then
				if g\ReloadState = 0.0 Then
					g\ShootAnim = 0
					Animate2(g\ViewModel,AnimTime(g\ViewModel),GetShootAnim(g, 0),GetShootAnim(g, 0),0.5,False)
					g\PrevFrame# = GetShootAnim(g, 0)
					g\ShootState = 1.0
				EndIf
			Elseif ShouldClearShoot Then
				if g\ReloadState = 0.0 Then
					g\ShootAnim = 0
					Animate2(g\ViewModel,AnimTime(g\ViewModel),GetShootAnim(g, 0),GetShootAnim(g, 0),0.5,False)
					g\PrevFrame# = GetShootAnim(g, 0)
					g\ShootState = 1.0
				EndIf
			EndIf
			
			If (g\ReloadState# = 0.0 And g\ShootState# = 0.0) And Spectate\SpectatePlayer = -1
				if ((ClosestButton=0 And ClosestItem=Null And GrabbedEntity%=0 And ClosestDoor=Null) Or (Not MOUSEINTERACT)) And DrawHandIcon = False And OtherOpen = Null And MenuOpen = False And SelectedDoor = Null And InvOpen = False And TAB_MENU_STATE < 2 And (Not(NetworkServer\ChatOpen = True Or ConsoleOpen)) And (Not BlockGuns) Then
					If g\Deployed = 1 Then
						If g\CurrAmmo < g\MaxCurrAmmo And g\Magazines > 0 Then
							If AnimTime(g\viewmodel) = GetIdleAnim(g) Then
								if (not LockMouse) then
									If KeyHit(KEY_RELOAD) Then
										g\CurrAmmo = 0
										PlayGunSound(g\ReloadSound,10)
										g\PrevFrame# = GetReloadAnim(g, 0)
										Animate2(g\viewmodel,AnimTime(g\viewmodel),GetReloadAnim(g, 0),GetReloadAnim(g, 0),0.5,False)
										g\ReloadState# = 1.0
										SetTempAmmoText()
										
									EndIf
								EndIf
							Else
								If KeyHit(KEY_RELOAD) Then
									SetTempAmmoText()
									FlushKeys()
								EndIf
							EndIf
						Else
							If KeyHit(KEY_RELOAD) Then 
								FlushKeys()
								SetTempAmmoText()
							Endif
						EndIf
					Else
						If KeyHit(KEY_RELOAD) Then 
							FlushKeys()
							SetTempAmmoText()
						EndIf
					EndIf
				EndIf
			ElseIf g\ReloadState# > 0.0 And g\ShootState# = 0.0
				Animate2(g\ViewModel,AnimTime(g\ViewModel), GetReloadAnim(g, 0), GetReloadAnim(g, 1), 0.5, False)
				If g\PrevFrame# < GetReloadAnim(g, 1)-1 And AnimTime(g\ViewModel)=> GetReloadAnim(g, 1)-1
					g\CurrAmmo = g\MaxCurrAmmo
					g\magazines = g\magazines - 1
					g\ReloadState# = 0.0
					Animate2(g\ViewModel,AnimTime(g\ViewModel),GetidleAnim(g),GetidleAnim(g),1,False)
					g\PrevFrame# = GetidleAnim(g)
					if Spectate\SpectatePlayer = -1 Then SetTempAmmoText()
				EndIf
			EndIf

			If g\ReloadState# = 0.0
				If g\Deployed = 0
					Animate2(g\ViewModel,AnimTime(g\ViewModel),GetDeployAnim(g, 0),GetDeployAnim(g, 1),0.5,False)
					If g\PrevFrame# < GetDeployFrame(g) And AnimTime(g\ViewModel)=>GetDeployFrame(g)
						PlayGunSound(g\DeploySound,10)
					ElseIf g\PrevFrame# < GetDeployAnim(g, 1)-0.5 And AnimTime(g\ViewModel)=>GetDeployAnim(g, 1)-0.5	
						Animate2(g\ViewModel,AnimTime(g\ViewModel),GetidleAnim(g),GetidleAnim(g),1,False)
						g\PrevFrame# = GetidleAnim(g)
						g\Deployed = 1
					EndIf
				Else
					If g\ShootAnim = 1
						Animate2(g\ViewModel,AnimTime(g\ViewModel),GetShootAnim(g, 0),GetShootAnim(g, 1),0.5,False)
						If g\PrevFrame# < GetShootAnim(g, 1)-1 And AnimTime(g\ViewModel) => GetShootAnim(g, 1)-1
							Animate2(g\ViewModel,AnimTime(g\ViewModel),GetidleAnim(g),GetidleAnim(g),1,False)
							g\PrevFrame# = GetidleAnim(g)
							g\ShootAnim = 0
						EndIf
					EndIf
					
					if Spectate\SpectatePlayer = -1 Then
						If g\ShootState# = 0.0 Then
							if g\SingleMode = True Or g\currAmmo < 1 Then
								If MouseHit1 And (not LockMouse) Then
									If ((ClosestButton=0 And ClosestItem=Null And GrabbedEntity%=0 And ClosestDoor=Null) Or (Not MOUSEINTERACT)) And OtherOpen = Null And DrawHandIcon = False And SelectedDoor = Null  And MenuOpen = False And InvOpen = False And TAB_MENU_STATE < 2 And (Not(NetworkServer\ChatOpen = True Or ConsoleOpen)) And (Not BlockGuns) Then
										g\ShootAnim = 0
										Animate2(g\ViewModel,AnimTime(g\ViewModel),GetShootAnim(g, 0),GetShootAnim(g, 0),0.5,False)
										g\PrevFrame# = GetShootAnim(g, 0)
										g\ShootState = 1.0
										
										if g\ID = GUN_MICROHID Then PlayGunSound(g\ShootSound,5)
									EndIf
								EndIf
							Else
								If MouseDown1 And (not LockMouse) Then
									If ((ClosestButton=0 And ClosestItem=Null And GrabbedEntity%=0 And ClosestDoor=Null) Or (Not MOUSEINTERACT)) And OtherOpen = Null And DrawHandIcon = False And SelectedDoor = Null  And MenuOpen = False And InvOpen = False And TAB_MENU_STATE < 2 And (Not(NetworkServer\ChatOpen = True Or ConsoleOpen)) And (Not BlockGuns) Then
										g\ShootAnim = 0
										Animate2(g\ViewModel,AnimTime(g\ViewModel),GetShootAnim(g, 0),GetShootAnim(g, 0),0.5,False)
										g\PrevFrame# = GetShootAnim(g, 0)
										g\ShootState = 1.0
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
					
					If g\ShootState = 1.0 Then
						Select g\ID
							Case GUN_MICROHID:
								g\ShootState = 1.01
							Case GUN_BAZOOKA:
								if ShouldShoot Then
									ShowEntity g\GunParticle
									RotateEntity g\GunParticle,0,0,Rnd(360)
									g\ShootState = 1.01
									g\ShootAnim = 1
								Else
									if g\CurrAmmo > 0 And (Not ShouldClearShoot) Then
									
										if udp_GetStream() Then
											For i = 0 To MaxItemAmount-1
												if Inventory(i) <> null Then
													if IsAGun(Inventory(i)\ItemTemplate\tempname) = g\ID Then
														udp_ByteStreamWriteChar M_CREATEROCKET
														udp_ByteStreamWriteShort Inventory(i)\ID
														udp_ByteStreamWriteFloat entityx(Camera)
														udp_ByteStreamWriteFloat entityy(Camera)-0.1
														udp_ByteStreamWriteFloat entityz(Camera)
														udp_ByteStreamWriteFloat entitypitch(camera)
														udp_ByteStreamWriteFloat entityyaw(camera)
														Exit
													EndIf
												EndIf
											Next
										EndIf
										CreateRocket(ROCKET_SPEED, entityx(Camera), entityy(Camera)-0.1, entityz(Camera), entitypitch(camera),entityyaw(camera), MyPlayer\ID)
										
										;if RecoilRegen = 0 Then RecoilRegen = EntityPitch(Camera)
										Recoil = CurveValue(20, Recoil, 15-g\RecoilForce-Rnd(1,2))

										g\ShootState = 1.01
										g\CurrAmmo = g\CurrAmmo - 1
									Else
										PlaySound_Strict ShootEmptySFX
										multiplayer_WriteSound ShootEmptySFX
										g\ShootState = 100
									EndIf
								EndIf
							Case GUN_GRENADE,GUN_GRENADEFLASHBANG,GUN_GRENADESMOKE
								if Spectate\SpectatePlayer = -1 Then
									CreateGrenade(EqquipedGun\ID, entityx(Camera), entityy(Camera)-0.1, entityz(Camera), entitypitch(camera),entityyaw(camera), NetworkServer\MyID, EqquipedGun\GrenadeSlow)
									
									if udp_GetStream() Then
										For i = 0 To MaxItemAmount-1
											if Inventory(i) <> null Then
												if IsAGun(Inventory(i)\ItemTemplate\tempname) = g\ID Then
													udp_ByteStreamWriteChar M_CREATEGRENADE
													udp_ByteStreamWriteShort Inventory(i)\ID
													udp_ByteStreamWriteFloat entityx(Camera)
													udp_ByteStreamWriteFloat entityy(Camera)-0.1
													udp_ByteStreamWriteFloat entityz(Camera)
													udp_ByteStreamWriteFloat entitypitch(camera)
													udp_ByteStreamWriteFloat entityyaw(camera)
													udp_ByteStreamWriteChar EqquipedGun\ID
													udp_ByteStreamWriteChar EqquipedGun\GrenadeSlow
													Exit
												EndIf
											EndIf
										Next
									EndIf
									EqquipedGun\GrenadeSlow = False
									OnPlayerDropGrenade(EqquipedGun\ID)
									EqquipedGun = Null
									g\ShootState = 1.01
								EndIf
							Case GUN_HANDCUFFS
								if UseHandcuffs() then
									EqquipedGun = Null
									g\ShootState = 1.01
								Else
									g\ShootState = 0
								EndIf
							Case GUN_KNIFE
								if not ShouldShoot Then
									EntityPickmode Collider, 0
									EntityPickmode MyHitbox, 0, False

									local knifesound$ = "SFX\Guns\Knife\knife_miss"+Rand(1,3)+".ogg"
									if EntityPick(Camera, 0.5) then
										knifesound = "SFX\Guns\Knife\knife_wall.ogg"
										For i = 0 To MaxItemAmount-1
											if Inventory(i) <> null Then
												if IsAGun(Inventory(i)\ItemTemplate\tempname) = g\ID Then
													For picked.players = Each players
														if PickedEntity() = picked\Hitbox Or PickedEntity() = picked\obj Or PickedEntity() = picked\Pivot Then
															udp_ByteStreamWriteChar M_KNIFEDAMAGE
															udp_ByteStreamWriteShort Inventory(i)\ID
															udp_ByteStreamWriteChar picked\ID
															
															knifesound = "SFX\Guns\Knife\knife_slash.ogg"
															Exit
														EndIf
													Next
												EndIf
											EndIf
										Next
									EndIf
									
									PlaySound_Strict(LoadTempSound(knifesound))
									multiplayer_WriteTempSound(knifesound)
									EntityPickmode Collider, 1
									EntityPickmode MyHitbox, 2, False
								EndIf
								g\ShootState = 1.01
								g\ShootAnim = 1
							Default
								if ShouldShoot Then
									ShowEntity g\GunParticle
									RotateEntity g\GunParticle,0,0,Rnd(360)
									g\ShootState = 1.01
									g\ShootAnim = 1
								Else
									if g\CurrAmmo > 0 And (Not ShouldClearShoot) Then
										ShowEntity g\GunParticle
										RotateEntity g\GunParticle,0,0,Rnd(360)
										
										local spreadmax# = float(eqquipedgun\sightstate*4)/(10+(5*Crouch*g\ID=GUN_SHOTGUN))
										if spreadmax = 0.0 then spreadmax = 1
										spreadmax = spreadmax+(CurrSpeed*50)
										;if RecoilRegen = 0 Then RecoilRegen = EntityPitch(Camera)
										Recoil = Recoil+(spreadmax*8)
										
										if not ShouldShoot Then
											PlayGunSound(g\ShootSound,5,False)
											if udp_network\stream <> 0 Then
												For i = 0 To MaxItemAmount-1
													if Inventory(i) <> null Then
														if IsAGun(Inventory(i)\ItemTemplate\tempname) = g\ID Then
															if not multiplayer_IsFullSync() then
																udp_WriteByte M_CREATEBULLET
																udp_WriteByte NetworkServer\MyID
																udp_WriteShort Inventory(i)\ID
																udp_WriteFloat entityx(g\GunParticle, True)
																udp_WriteFloat entityy(g\GunParticle, True)
																udp_WriteFloat entityz(g\GunParticle, True)
																udp_WriteFloat entitypitch(camera, True)
																udp_WriteFloat entityyaw(camera, True)
																udp_WriteByte spreadmax*10
																udp_SendMessage()
															else
																if MyPlayer\ShouldSendShoot <> null Then RemoveByteStream(MyPlayer\ShouldSendShoot)
																MyPlayer\ShouldSendShoot = CreateByteStream(23)
																ByteStreamWriteShort MyPlayer\ShouldSendShoot,Inventory(i)\ID
																ByteStreamWriteFloat MyPlayer\ShouldSendShoot,entityx(g\GunParticle, True)
																ByteStreamWriteFloat MyPlayer\ShouldSendShoot,entityy(g\GunParticle, True)
																ByteStreamWriteFloat MyPlayer\ShouldSendShoot,entityz(g\GunParticle, True)
																ByteStreamWriteFloat MyPlayer\ShouldSendShoot,entitypitch(camera, True)
																ByteStreamWriteFloat MyPlayer\ShouldSendShoot,entityyaw(camera, True)
																ByteStreamWriteChar MyPlayer\ShouldSendShoot,spreadmax*10
															EndIf
															Exit
														EndIf
													EndIf
												Next
											EndIf
											g\CurrAmmo = g\CurrAmmo - 1
										EndIf
										For i = 1 To g\BulletTicks
											Local spreadx# = Rnd(-spreadmax*g\gunspreadrate,spreadmax*g\gunspreadrate), spready# = Rnd(-spreadmax*g\gunspreadrate, spreadmax*g\gunspreadrate)
											CreateBullet(NetworkServer\MyID, BULLET_SPEED, entityx(g\GunParticle, True), entityy(g\GunParticle, True), entityz(g\GunParticle, True), entitypitch(Camera, True)+spreadx-2, entityyaw(Camera, True)+spready)
										Next

										Recoil = CurveValue(20, Recoil, 15-g\RecoilForce-Rnd(1,2))
										
										g\ShootState = 1.01
										g\ShootAnim = 1
									Else
										PlaySound_Strict ShootEmptySFX
										multiplayer_WriteSound ShootEmptySFX
										g\ShootState = 100
									EndIf
								EndIf
						End Select
					EndIf
					
					If g\ShootState >= 1.01 Then
						g\ShootState = g\ShootState + FPSfactorNoLimit
						
						Select g\ID
							Case GUN_MICROHID:
								if g\ShootState > 455 And g\ShootState < 1020 Then
									if Spectate\SpectatePlayer = -1 Then
										MyPlayer\MicroHIDSHOOT = True
										g\CurrAmmo = 0
									EndIf
								Else
									MyPlayer\MicroHIDSHOOT = False
								EndIf
								Animate2(g\ViewModel,AnimTime(g\ViewModel),GetShootAnim(g, 0),GetShootAnim(g, 1),0.25,False)
						End Select
						If g\ShootState-1.01 >= g\ShootSpeed Then
							HideEntity g\GunParticle
							if g\ID <> GUN_HANDCUFFS Then g\ShootAnim = 1
							g\ShootState# = 0.0
							
							Select g\ID
								Case GUN_MICROHID:
									g\ShootAnim = 0
									EqquipedGun = Null
									HoldingGun = 0
									
									For i = 0 To MaxItemAmount-1
										if Inventory(i) <> null Then
											if IsAGun(Inventory(i)\ItemTemplate\tempname) = g\ID Then
												RemoveItem(Inventory(i))
												Exit
											EndIf
										EndIf
									Next
							End Select
						EndIf
					EndIf
				EndIf
			EndIf
			; remove object if there no ammo
			if g\ReloadState = 0.0 And (Not g\ShootAnim) And g\ShootState = 0 Then
				if g\CurrAmmo < 1 Then
					Select g\ID
						Case GUN_BAZOOKA

					End Select
				EndIf
			EndIf
		EndIf
	; ===========================
	
	if Spectate\SpectatePlayer = -1 Then
		if EqquipedGun <> Null Then
			Local FoundGun = False
			For i = 0 To MaxItemAmount-1
				if Inventory(i) <> Null Then
					if IsAGun(Inventory(i)\ItemTemplate\TempName) = EqquipedGun\ID And Inventory(i)\Picker = NetworkServer\MyID Then 
						FoundGun = True
						Exit
					EndIf
				EndIf
			Next
			
			if Not FoundGun Then 
				EqquipedGun = Null
				HoldingGun = 0
			EndIf
		EndIf
	EndIf
	
	if HoldingGun = 0 Then 
		Recoil = 0
		CurrentFOV = CurveValue(MainFOV, CurrentFOV, 5)
	EndIf

	BlockGuns = False
	DrawHandIcon = False

End Function

Function AnimateGuns()
	if eqquipedgun <> null then
		PositionEntity GunPivot, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
		
		;RotateEntity GunPivot, EntityPitch(GunPivot), EntityYaw(GunPivot), -Sin(Shake/2)*2.5
		GET_ANGLE% = False
		if GetAngleMax(EntityPitch(Camera), EntityPitch(GunPivot)) >= MAX_ANGLE_LIMIT then
			RotateEntity(GunPivot, wrapangle(EntityPitch(Camera)-MAX_ANGLE_LIMIT), EntityYaw(GunPivot), EntityRoll(GunPivot))
			GET_ANGLE = True
		EndIf
		if GetAngleMax(EntityPitch(Camera), EntityPitch(GunPivot)) <= -MAX_ANGLE_LIMIT then
			RotateEntity(GunPivot, wrapangle(EntityPitch(Camera)+MAX_ANGLE_LIMIT), EntityYaw(GunPivot), EntityRoll(GunPivot))
			GET_ANGLE = True
		EndIf
		
		if GetAngleMax(EntityYaw(Camera), EntityYaw(GunPivot)) >= MAX_ANGLE_LIMIT then
			RotateEntity(GunPivot, EntityPitch(GunPivot), wrapangle(EntityYaw(Camera)-MAX_ANGLE_LIMIT), EntityRoll(GunPivot))
			GET_ANGLE = True
		EndIf
		if GetAngleMax(EntityYaw(Camera), EntityYaw(GunPivot)) <= -MAX_ANGLE_LIMIT then
			RotateEntity(GunPivot, EntityPitch(GunPivot), wrapangle(EntityYaw(Camera)+MAX_ANGLE_LIMIT), EntityRoll(GunPivot))
			GET_ANGLE = True
		EndIf
			
		if EqquipedGun\ID < GUN_HANDCUFFS Then
			MoveEntity GunPivot, EqquipedGun\CurrentSightMove[0], EqquipedGun\CurrentSightMove[1], 0
			if EqquipedGun\SightState = 1 And EqquipedGun\ReloadState# = 0.0 Then
				EqquipedGun\CurrentSightMove[0] = CurveValue(EqquipedGun\SightRate, EqquipedGun\CurrentSightMove[0], 10)
				;EqquipedGun\CurrentSightMove[1] = CurveValue(max(EqquipedGun\gmovey, 0.02)*1.2, EqquipedGun\CurrentSightMove[1], 10)
				CurrentFOV = CurveValue(MainFOV-10, CurrentFOV, 5)
			Else
				EqquipedGun\CurrentSightMove[0] = CurveValue(0.0, EqquipedGun\CurrentSightMove[0], 10)
				;EqquipedGun\CurrentSightMove[1] = CurveValue(0.0, EqquipedGun\CurrentSightMove[1], 10)
				CurrentFOV = CurveValue(MainFOV, CurrentFOV, 5)
			EndIf
			EqquipedGun\SightState = MouseDown(2)*CanInteract()
			
			if Spectate\SpectatePlayer <> -1 Then
				if Player[Spectate\SpectatePlayer] <> Null Then
					if Player[Spectate\SpectatePlayer]\Sighting Then EqquipedGun\SightState = 1
				EndIf
			EndIf
		EndIf
		
		if EqquipedGun\SightState = 1 And EqquipedGun\ReloadState# = 0.0 then
			WeaponShake = CurveValue(0.0, WeaponShake, 10)
		Else
			WeaponShake = CurveValue((-Sin(Shake/2)*2.5)*0.0012, WeaponShake, 10)
		EndIf
		
		MoveEntity GunPivot, WeaponShake, (Cos(MilliSecs()*0.05)*0.003),0;+((-Sin(Shake/2)*2.5)*0.0004), 0
		
		Select eqquipedgun\ID
			Case GUN_GRENADE,GUN_GRENADEFLASHBANG,GUN_GRENADESMOKE
				MoveEntity GunPivot, 0.3, -0.05, 0
				RotateEntity GunPivot, EntityPitch(GunPivot), EntityYaw(GunPivot), -90
				
				if MouseDown(2) Then
					eqquipedgun\ShootAnim = 0
					Animate2(eqquipedgun\ViewModel,AnimTime(eqquipedgun\ViewModel),GetShootAnim(eqquipedgun, 0),GetShootAnim(eqquipedgun, 0),0.5,False)
					eqquipedgun\PrevFrame# = GetShootAnim(eqquipedgun, 0)
					eqquipedgun\ShootState = 1.0
					EqquipedGun\GrenadeSlow = True
				EndIf
			;Case GUN_KNIFE
			;	;RotateEntity GunPivot, EntityPitch(GunPivot)+45, EntityYaw(GunPivot), EntityRoll(GunPivot)
		End Select
	endif
End Function

Function PlayGunSound(sound, dist#=0,sendsound%=true)
	if sound = 0 Then Return
	EqquipedGun\ChannelGun = PlaySound_Strict(sound)
	if sendsound then multiplayer_WriteSound(sound, 0,0,0,20-dist)
End Function

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
		Case "grenadeflashbang"
			Return GUN_GRENADEFLASHBANG
		Case "grenadesmoke"
			Return GUN_GRENADESMOKE
		Case "knife"
			Return GUN_KNIFE
		Case "hkg36"
			Return GUN_HKG36
	End Select
	Return 0
End Function

Function GetGunType.Guns(ID)
	Return GUN_INFO[ID]
End Function

Function DrawGunGUI()
	UpdateGunCrosshair(Recoil-(5*CrouchState)+(200*CurrSpeed)-(2*EqquipedGun\SightState))
End Function

Const CROSSHAIR_SIZE = 10
Function UpdateGunCrosshair(ar#)
	if Spectate\SpectatePlayer = -1 Then
		if Hudenabled then
			if EqquipedGun\ID < GUN_HANDCUFFS Then
				;if EqquipedGun\SightState = 0.0 Then
					Local axis# = Max(-16.0, ar)
					Color 255,255,255
					Rect(GraphicWidth/2 - (CROSSHAIR_SIZE*2) - axis, GraphicHeight/2, CROSSHAIR_SIZE, 1)
					Rect(GraphicWidth/2 + CROSSHAIR_SIZE + axis, GraphicHeight/2, CROSSHAIR_SIZE, 1)
					Rect(GraphicWidth/2, GraphicHeight/2 - (CROSSHAIR_SIZE*2) - axis, 1, CROSSHAIR_SIZE)
					Rect(GraphicWidth/2, GraphicHeight/2 + CROSSHAIR_SIZE + axis, 1, CROSSHAIR_SIZE)
					
					Rect(GraphicWidth/2 - (CROSSHAIR_SIZE*2) - axis, GraphicHeight/2, CROSSHAIR_SIZE, 1)
					Rect(GraphicWidth/2 + CROSSHAIR_SIZE + axis, GraphicHeight/2, CROSSHAIR_SIZE, 1)
					Rect(GraphicWidth/2, GraphicHeight/2 - (CROSSHAIR_SIZE*2) - axis, 1, CROSSHAIR_SIZE)
					Rect(GraphicWidth/2, GraphicHeight/2 + CROSSHAIR_SIZE + axis, 1, CROSSHAIR_SIZE)
				;EndIf
			EndIf
		endif
	EndIf
End Function
Function PickupGun(it.Items)
	if Not multiplayer_IsASCP(MyPlayer\BreachType) Then
		if EqquipedGun = Null Then
			HoldingGun = IsAGun(it\itemtemplate\tempname)
			if HoldingGun <> 0 Then 
				EqquipedGun = GetGunType(HoldingGun)
				SelectedItem = Null
			EndIf
		Elseif IsAGun(it\itemtemplate\tempname) Then
			if IsAGun(it\itemtemplate\tempname) = EqquipedGun\ID Then 
				EqquipedGun = Null
				SelectedItem = Null
			Else
				HoldingGun = IsAGun(it\itemtemplate\tempname)
				if HoldingGun <> 0 Then 
					EqquipedGun = GetGunType(HoldingGun)
					SelectedItem = Null
				EndIf
			EndIf
		endIf
	EndIf
End Function
Function DropGun(it.Items)
	if Not multiplayer_IsASCP(MyPlayer\BreachType) Then
		if eqquipedgun <> null then
			if IsAGun(it\itemtemplate\tempname) = EqquipedGun\ID Then
				EqquipedGun = Null
				SelectedItem = Null
			endIf
		endif
	EndIf
End Function

Function IsGunShooting%()
	if EqquipedGun <> Null Then Return (EqquipedGun\ShootState>=1.01)
End Function

Function IsGunSighting%()
	if EqquipedGun <> Null Then Return EqquipedGun\SightState
End Function

Const MAX_RECOIL_PATTERN# = 0.001

Function GetRecoilPattern#(RecoilPattern# = 0.0)
	if eqquipedgun <> Null Then
		if eqquipedgun\ShootState >= 1.01 Then
			if eqquipedgun\ID = GUN_MICROHID And eqquipedgun\ShootState < 456 then return 0.0
			if Rand(0,1) = 0 then
				Return (EqquipedGun\RecoilForce*0.2)*FPSfactor
			else
				Return -(EqquipedGun\RecoilForce*0.2)*FPSfactor
			endif
		;	RecoilPattern = CurveAngle(Rnd(-MAX_RECOIL_PATTERN, MAX_RECOIL_PATTERN), RecoilPattern, 1)
		;Else
		;	RecoilPattern = CurveAngle(0, RecoilPattern, 5)
		EndIf
	EndIf
	Return RecoilPattern
End Function

Function GetRecoilForce#()
	if EqquipedGun <> Null Then
		if EqquipedGun\ShootState >= 1.01 Then
			if eqquipedgun\ID = GUN_MICROHID And eqquipedgun\ShootState < 456 then return 0.0
			if EqquipedGun\SightState = 0 Then 
				Return EqquipedGun\RecoilForce*FPSfactor
			Else
				Return (EqquipedGun\RecoilForce*0.9)*FPSfactor
			EndIf
		EndIf
	EndIf
End Function

Function UpdateRecoil()
	Recoil = CurveValue(0, Recoil, 15)
End Function

Function GetRecoilLR#()
	Local recoilfloat# = 0.0
	if eqquipedgun <> Null Then
		if EqquipedGun\ShootState >= 1.0 Then 
			recoilFloat# = Rnd(0.3, 0.4)*FPSfactor
			if Rand(1,2) = 1 Then recoilFloat = -recoilFloat
		EndIf
	EndIf
	Return recoilfloat
End Function

Function SetTempAmmoText()
	AmmoTimer = 70*5
End Function

Function RenderAmmoText()
	if EqquipedGun <> Null Then
		If AmmoTimer > 0 Then
			Color 255,255,255
			AASetFont Font3
			AAText GraphicWidth-AAStringWidth(EqquipedGun\CurrAmmo+" / "+EqquipedGun\Magazines)-50*MenuScale, GraphicHeight-70*MenuScale, EqquipedGun\CurrAmmo+" / "+EqquipedGun\Magazines, True, False, Min(AmmoTimer / 2, 255)/255.0
			AmmoTimer = AmmoTimer - FPSfactor
		End If
	EndIf
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D
