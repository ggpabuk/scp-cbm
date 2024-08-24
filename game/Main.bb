;SCP - Containment Breach Multiplayer
;
;    The game is based on the works of the SCP Foundation community (http://www.scp-wiki.net/).
;
;    The source code is licensed under Creative Commons Attribution-ShareAlike 3.0 License.
;    http://creativecommons.org/licenses/by-sa/3.0/
;
;    See Credits.txt for a list of contributors

Global RetValMillisecs%
Global SCREEN_HWND = SystemProperty("AppHWND")
Global FontSL, FontServers
Global OptionFile$ = GetEnv("AppData") + "\SCP Containment Breach Multiplayer\data\options.ini"
;Global OptionFile$ = "options.ini"
Global ErrorFile$ = "error_log_"

If FileSize(OptionFile) = 0 Then CreateFile(OptionFile) And NewOptionsINI()

Function NewOptionsINI()
	
	PutINIValue(OptionFile, "options", "brightness", 50)
	PutINIValue(OptionFile, "options", "mouse sensitivity", 0.0)
	PutINIValue(OptionFile, "options", "invert mouse y", 0)
	PutINIValue(OptionFile, "options", "bump mapping enabled", 1)			
	PutINIValue(OptionFile, "options", "HUD enabled", 1)
	PutINIValue(OptionFile, "options", "screengamma", 1.0)
	PutINIValue(OptionFile, "options", "antialias", 1)
	PutINIValue(OptionFile, "options", "vsync", 0)
	PutINIValue(OptionFile, "options", "show FPS", 1)
	PutINIValue(OptionFile, "options", "showscpviewmodel", 1)
	PutINIValue(OptionFile, "options", "camera fog near", 0.5)
	PutINIValue(OptionFile, "options", "camera fog far", 6.0)
	PutINIValue(OptionFile, "options", "fog r", 0)
	PutINIValue(OptionFile, "options", "fog g", 0)
	PutINIValue(OptionFile, "options", "fog b", 0)
	PutINIValue(OptionFile, "options", "framelimit", Framelimit%)
	PutINIValue(OptionFile, "options", "achievement popup enabled", 1)
	PutINIValue(OptionFile, "options", "room lights enabled", 1)
	PutINIValue(OptionFile, "options", "texture details", 2)
	PutINIValue(OptionFile, "console", "enabled", 0)
	PutINIValue(OptionFile, "console", "auto opening", 0)
	PutINIValue(OptionFile, "options", "antialiased text", 0)
	PutINIValue(OptionFile, "options", "particle amount", 2)
	PutINIValue(OptionFile, "options", "enable vram", 0)
	PutINIValue(OptionFile, "options", "mouse smoothing", 1.0)
	PutINIValue(OptionFile, "options", "gammaoff", 0)
	PutINIValue(OptionFile, "options", "cameraupd", 2)
	PutINIValue(OptionFile, "options", "cameraquality", 2)
	PutINIValue(OptionFile, "options", "removedecals", 0)
	PutINIValue(OptionFile, "options", "removeparticles", 0)
	PutINIValue(OptionFile, "options", "fov", 70.0)
	PutINIValue(OptionFile, "options", "enablebullets", 1)
	PutINIValue(OptionFile, "options", "play startup video", 1)
	
	PutINIValue(OptionFile, "audio", "music volume", 1.0)
	PutINIValue(OptionFile, "audio", "sound volume", 1.0)
	PutINIValue(OptionFile, "audio", "sfx release", 1)
	PutINIValue(OptionFile, "audio", "enable user tracks", 1)
	PutINIValue(OptionFile, "audio", "user track setting", 1)
	
	PutINIValue(OptionFile, "binds", "Right key", 32)
	PutINIValue(OptionFile, "binds", "Left key", 30)
	PutINIValue(OptionFile, "binds", "Up key", 17)
	PutINIValue(OptionFile, "binds", "Down key", 31)
	PutINIValue(OptionFile, "binds", "Blink key", 48)
	PutINIValue(OptionFile, "binds", "Sprint key", 42)
	PutINIValue(OptionFile, "binds", "Inventory key", 15)
	PutINIValue(OptionFile, "binds", "Crouch key", 29)
	PutINIValue(OptionFile, "binds", "Save key", 63)
	PutINIValue(OptionFile, "binds", "Console key", 61)
	PutINIValue(OptionFile, "binds", "mouseinteract", 1)
	
	PutINIValue(OptionFile, "binds", "Voice key", 47)
	PutINIValue(OptionFile, "binds", "Chat key", 64)
	PutINIValue(OptionFile, "binds", "Jump key", 57)
	
	PutINIValue(OptionFile, "binds", "LeanR key", 18)
	PutINIValue(OptionFile, "binds", "LeanL key", 16)
	PutINIValue(OptionFile, "binds", "Using key", 33)
	
	PutINIValue(OptionFile, "launcher", "launcher width", 640)
	PutINIValue(OptionFile, "launcher", "launcher height", 480)
	PutINIValue(OptionFile, "launcher", "launcher enabled", 1)
	
	
End Function

Include "SourceCode\FPSfactor.bb"
Include "SourceCode\KeyName.bb"
Include "SourceCode\Difficulty.bb"
Include "SourceCode\Multiplayer.bb"
Include "SourceCode\FMod.bb"
Include "SourceCode\StrictLoads.bb"
Include "SourceCode\fullscreen_window_fix.bb"
Include "SourceCode\Blitz_Basic_Bank.bb"
Include "SourceCode\Blitz_File_FileName.bb"
Include "SourceCode\Blitz_File_ZipApi.bb"
;Include "SourceCode\Update.bb"
Include "SourceCode\DevilParticleSystem.bb"

AnalyzeErrorFile()

Function AnalyzeErrorFile()
	Local ErrorFileInd%
	While FileType(ErrorFile+Str(ErrorFileInd)+".txt")<>0
		ErrorFileInd = ErrorFileInd+1
	Wend
	ErrorFile = ErrorFile+Str(ErrorFileInd)+".txt"
End Function

; ================================================= updating player files to original
ReplaceDataFile(1, "Data\1499chunks.ini", True)
ReplaceDataFile(2, "Data\rooms.ini", True)
ReplaceDataFile(3, "Data\NPCs.ini", True)
; =================================================

Global UpdaterFont%
Global Font1%, Font2%, Font3%, Font4%, Font5%, Font1_2%
Global ConsoleFont%
Global VersionNumber$ = "1.3.12"
Global CompatibleNumber$ = "1.3.11" ;Only change this if the version given isn't working with the current build version - ENDSHN
Global MenuWhite%, MenuBlack%
Global ButtonSFX% = LoadSound_Strict("SFX\Interact\Button.ogg")
Global EnableSFXRelease% = GetINIInt(OptionFile, "audio", "sfx release")
Global EnableSFXRelease_Prev% = EnableSFXRelease%
Global CanOpenConsole% = GetINIInt(OptionFile, "console", "enabled")

Dim ArrowIMG(4)

;[Block]

Global LauncherWidth%= Min(GetINIInt(OptionFile, "launcher", "launcher width"), 1024)
Global LauncherHeight% = Min(GetINIInt(OptionFile, "launcher", "launcher height"), 768)
Global LauncherEnabled% = GetINIInt(OptionFile, "launcher", "launcher enabled")*(Not GetINIInt(OptionFile, "launcher", "changeres"))
Global LauncherIMG%

Global GraphicWidth% = GetINIInt(OptionFile, "options", "width")
Global GraphicHeight% = GetINIInt(OptionFile, "options", "height")
Global Depth% = 0, Fullscreen% = GetINIInt(OptionFile, "options", "fullscreen")
Global SelectedGFXMode%
Global SelectedGFXDriver% = Max(GetINIInt(OptionFile, "options", "gfx driver new"), 0)
Global TurnOnGamma% = GetINIInt(OptionFile, "options", "gammaoff")
Global CamUpdate% = GetINIInt(OptionFile, "options", "cameraupd", 2)
Global CamQuality% = GetINIInt(OptionFile, "options", "cameraquality", 2)
Global RemoveDecals% = GetINIInt(OptionFile, "options", "removedecals", 0)
Global RemoveParticles% = GetINIInt(OptionFile, "options", "removeparticles", 0)
Global MainFOV# = Min(Max(GetINIFloat(OptionFile, "options", "fov", 70.0), 60), 90)
Global EnableBullets% = GetINIInt(OptionFile, "options", "enablebullets", 1)
CurrentFOV = MainFOV

If GraphicWidth = 0 Or GraphicHeight = 0 Then 
	GraphicWidth = 1280
	GraphicHeight = 720
EndIf

	
Function LoadAllFonts(InitAAFonts% = False)
	If InitAAFonts Then InitAAFont()
	Font1% = AALoadFont("GFX\font\cour\Courier New Rus.ttf", Int(19 * (GraphicHeight / 1024.0)), 0,0,0)
	Font1_2% = AALoadFont("GFX\font\cour\Courier New Rus.ttf", Int(28 * (GraphicHeight / 1024.0)), 0,0,0)
	Font2% = AALoadFont("GFX\font\courbd\Courier New Rus.ttf", Int(58 * (GraphicHeight / 1024.0)), 0,0,0)
	Font3% = AALoadFont("GFX\font\DS-DIGI\DS-Digital.ttf", Int(22 * (GraphicHeight / 1024.0)), 0,0,0)
	Font4% = AALoadFont("GFX\Font\DS-DIGI\DS-Digital.ttf", Int(60 * (GraphicHeight / 1024.0)), 0,0,0)
	Font5% = AALoadFont("GFX\fOnt\Journal\Journal.ttf", Int(58 * (GraphicHeight / 1024.0)),0,0,0)
	FontSL% = AALoadFont("GFX\foNt\cour\Courier New Rus.ttf", Int(16 * (GraphicHeight / 1024.0)), 0,0,0)
	FontSL_1% = AALoadFont("GFX\fonT\cour\Courier New Rus.ttf", Int(16 * (GraphicHeight / 1024.0)), 0,0,1)
	FontServers% = AALoadFont("GFX\foNt\cour\Courier New Rus.ttf", Int(16/1.16 * (GraphicHeight / 1024.0)), 0,0,0)
	ConsoleFont% = AALoadFont("Blitz", Int(20 * (GraphicHeight / 1024.0)), 0,0,0,1)
	FontRes% = AALoadFont("GFX\fonT\cour\Courier New Rus.ttf", Int(18 * (GraphicHeight / 1024.0)), 0,0,0)
End Function

Function FreeAllFonts(RemoveRedirect% = False, RemoveEasy% = False)
	For font.AAFont = Each AAFont
		If Not RemoveEasy Then 
			AAFreeFont(Handle(font))
		Else
			Delete font
		EndIf
	Next
	
	For lf.loadedfonts = Each loadedfonts
		If lf\isadefaultfont Then FreeFont(lf\mfont)
	Next
	Delete Each loadedfonts
	If RemoveRedirect Then Delete Each RedirectFont
	
	If Not RemoveEasy Then
		If (Not AATextEnable) Then
			If AATextCam <> 0 Then FreeEntity AATextCam
			;For i%=0 To 149
			;	FreeEntity AATextSprite[i]
			;Next
		EndIf
	EndIf
End Function
			
Function GetCameraQuality(quality)
	Local texsize
	Select quality
		Case 0
			texsize = 64
		Case 1
			texsize = 128
		Case 2
			texsize = 256
		Default
			Return 256
	End Select
	Return Min(texsize, GraphicHeight)
End Function

Global fresize_image%, fresize_texture%, fresize_texture2%
Global fresize_cam%

Global ShowFPS = GetINIInt(OptionFile, "options", "show FPS")

Global ShowSCPViewmodel% = GetINIInt(OptionFile, "options", "showscpviewmodel")

Global WireframeState
Global HalloweenTex

Global TotalGFXModes% = CountGfxModes3D(), GFXModes%
Dim GfxModeWidths%(TotalGFXModes), GfxModeHeights%(TotalGFXModes)

Global BorderlessWindowed% = GetINIInt(OptionFile, "options", "borderless windowed")
Global RealGraphicWidth%,RealGraphicHeight%
Global AspectRatioRatio#
Global ConsoleOpening% = 0

Global EnableRoomLights% = GetINIInt(OptionFile, "options", "room lights enabled")

Global TextureDetails% = GetINIInt(OptionFile, "options", "texture details")
Global TextureFloat#
Select TextureDetails%
	Case 0
		TextureFloat# = 1.5
	Case 1
		TextureFloat# = 0.4
	Case 2
		TextureFloat# = 0.0
	Case 3
		TextureFloat# = -0.4
	Case 4
		TextureFloat# = -0.8
End Select
Global SFXVolume# = GetINIFloat(OptionFile, "audio", "sound volume")

Global Bit16Mode = GetINIInt(OptionFile, "options", "16bit")

Include "SourceCode\AAText.bb"

Type MEMORYSTATUS
    Field dwLength%
    Field dwMemoryLoad%
    Field dwTotalPhys%
    Field dwAvailPhys%
    Field dwTotalPageFile%
    Field dwAvailPageFile%
    Field dwTotalVirtual%
    Field dwAvailVirtual%
End Type


If AATextEnable Then
	Graphics3D 640, 480, 32, 2

	SE_Init()
	GetCameraQuality(4)

	ws_BackupOriginal()
	ws_CheckSubscribedItems(True)
	ws_LoadScripts("workshop")

	If GetScripts() Then public_inqueue(public_OnLoadingFonts, True)

	EndGraphics()

Else

	SE_Init()
	GetCameraQuality(4)

	ws_BackupOriginal()
	ws_CheckSubscribedItems(True)
	ws_LoadScripts("workshop")

	If GetScripts() Then public_inqueue(public_OnLoadingFonts, True)

	InitAAFont()
EndIf

ButtonSFX% = LoadSound_Strict("SFX\Interact\Button.ogg")

If LauncherEnabled Then 
	AspectRatioRatio = 1.0
	UpdateLauncher()
	;New "fake fullscreen" - ENDSHN Psst, it's called borderless windowed mode --Love Mark,
	If BorderlessWindowed
		DebugLog "Using Borderless Windowed Mode"
		Graphics3DExt GraphicWidth, GraphicHeight, 0, 3
		
		; -- Change the window style to 'WS_POPUP' and then set the window position to force the style to update.
		api_SetWindowLong( G_app_handle, C_GWL_STYLE, C_WS_POPUP )
		api_SetWindowPos( G_app_handle, C_HWND_TOP, G_viewport_x, G_viewport_y, G_viewport_width, G_viewport_height, C_SWP_SHOWWINDOW )
		
		RealGraphicWidth = GraphicWidth
		RealGraphicHeight = GraphicHeight
		
		AspectRatioRatio = (Float(GraphicWidth)/Float(GraphicHeight))/(Float(RealGraphicWidth)/Float(RealGraphicHeight))
		
		Fullscreen = False
	Else
		AspectRatioRatio = 1.0
		RealGraphicWidth = GraphicWidth
		RealGraphicHeight = GraphicHeight
		If Fullscreen Then
			Graphics3DExt(GraphicWidth, GraphicHeight, (16*Bit16Mode), 1)
		Else
			Graphics3DExt(GraphicWidth, GraphicHeight, 0, 2)
		End If
	EndIf
	
Else
	For i% = 1 To TotalGFXModes
		Local samefound% = False
		For  n% = 0 To TotalGFXModes - 1
			If GfxModeWidths(n) = GfxModeWidth(i) And GfxModeHeights(n) = GfxModeHeight(i) Then samefound = True : Exit
		Next
		If samefound = False Then
			If GraphicWidth = GfxModeWidth(i) And GraphicHeight = GfxModeHeight(i) Then SelectedGFXMode = GFXModes
			GfxModeWidths(GFXModes) = GfxModeWidth(i)
			GfxModeHeights(GFXModes) = GfxModeHeight(i)
			GFXModes=GFXModes+1
		End If
	Next
	
	GraphicWidth = GfxModeWidths(SelectedGFXMode)
	GraphicHeight = GfxModeHeights(SelectedGFXMode)
	
	;New "fake fullscreen" - ENDSHN Psst, it's called borderless windowed mode --Love Mark,
	If BorderlessWindowed
		DebugLog "Using Faked Fullscreen"
		Graphics3DExt G_viewport_width, G_viewport_height, 0, 3
		
		; -- Change the window style to 'WS_POPUP' and then set the window position to force the style to update.
		api_SetWindowLong( G_app_handle, C_GWL_STYLE, C_WS_POPUP )
		api_SetWindowPos( G_app_handle, C_HWND_TOP, G_viewport_x, G_viewport_y, G_viewport_width, G_viewport_height, C_SWP_SHOWWINDOW )
		
		RealGraphicWidth = G_viewport_width
		RealGraphicHeight = G_viewport_height
		
		AspectRatioRatio = (Float(GraphicWidth)/Float(GraphicHeight))/(Float(RealGraphicWidth)/Float(RealGraphicHeight))
		
		Fullscreen = False
	Else
		AspectRatioRatio = 1.0
		RealGraphicWidth = GraphicWidth
		RealGraphicHeight = GraphicHeight
		If Fullscreen Then
			Graphics3DExt(GraphicWidth, GraphicHeight, (16*Bit16Mode), 1)
		Else
			Graphics3DExt(GraphicWidth, GraphicHeight, 0, 2)
		End If
	EndIf
	
EndIf

If SelectedGFXDriver > 0 Then SetGfxDriver(SelectedGFXDriver)

PutINIValue(OptionFile, "launcher", "isstarted", 1)

If AATextEnable Then InitAAFont()

Global MenuScale# = GraphicHeight / 1024.0

Function IntRound2#(value#, Round% = 10)
	Return Int(Int(value/Round)*Round)
End Function
Function FloatRound#(value#, Round% = 10)
	Return IntRound2(value*Round)/Round
End Function

SetBuffer(BackBuffer())

AppTitle "SCP - Containment Breach Multiplayer"
If Not api_IsWindowVisible(SCREEN_HWND) Then api_showWindow(SCREEN_HWND, 1)

If (Not GetINIInt(OptionFile, "launcher", "changeres")) Then 
	PlayStartupVideos()
Else
	ClsColor 0,0,0
	Cls
	Flip
EndIf

PutINIValue(OptionFile, "launcher", "changeres", 0)

Global CurTime#, PrevTime#, LoopDelay, FPSfactor#, FPSfactor2#, PrevFPSFactor#, FPSfactorNoLimit#, DeltaMillisecs%, FactorDelta#
Global CheckFPS%, ElapsedLoops%, FPS%, ElapsedTime#

Function GetDeltaTime#()
	Return FactorDelta
End Function

Function SetFixedTimeStepsBackup()
	FPSfactor = GetDeltaTime()
End Function

Function ReturnFixedTimeSteps()
	FPSfactor = GetTickDuration()
End Function

Global Framelimit% = GetINIInt(OptionFile, "options", "framelimit")
Global VerticalSync% = GetINIInt(OptionFile, "options", "vsync")

Global Opt_AntiAlias = GetINIInt(OptionFile, "options", "antialias")
Global CurrFrameLimit# = (Framelimit%-19)/100.0

Global ScreenGamma# = GetINIFloat(OptionFile, "options", "screengamma")
;If Fullscreen Then UpdateScreenGamma()

Const HIT_MAP% = 1, HIT_PLAYER% = 2, HIT_ITEM% = 3, HIT_APACHE% = 4, HIT_178% = 5, HIT_DEAD% = 6, HIT_BULLET% = 7, HIT_GRENADE% = 8, HIT_INVISIBLEWALL% = 9, HIT_BULLETHITBOX% = 10, HIT_DOOR% = 11, HIT_OLDMAN% = 12, HIT_DEADBODY% = 13

SeedRnd MilliSecs()

;[End block]

Global GameSaved%

Global CanSave% = True

Global darkA#

;---------------------------------------------------------------------------------------------------------------------

;[Block]

Global SMALLEST_POWER_TWO#
Global SMALLEST_POWER_TWO_HALF#

Global CursorIMG% = LoadImage_Strict("GFX\cursor.png")

Global SelectedLoadingScreen.LoadingScreens, LoadingScreenAmount%, LoadingScreenText%
Global LoadingBack% = LoadImage_Strict("Loadingscreens\loadingback.jpg")
MaskImage LoadingBack, 212, 212, 243
InitLoadingScreens("Loadingscreens\loadingscreens.ini")

Global FontSL_1
Global FontRes
;For some reason, Blitz3D doesn't load fonts that have filenames that
;don't match their "internal name" (i.e. their display name in applications
;like Word and such). As a workaround, I moved the files and renamed them so they
;can load without FastText.
LoadAllFonts(False)

Global Text3DFont.tGG_Font
Global AFK3DFont.tGG_Font
Global Sound3DFont.tGG_Font
Global CreditsFont%,CreditsFont2%

If GetScripts() Then public_inqueue(public_OnLaunchGame, True)

AASetFont Font2
Global BlinkMeterIMG% = LoadImage_Strict("GFX\blinkmeter.jpg")

GameLoad = 1
DrawLoading(0, True)
DrawLoading(5, True)
; - -Viewport.
Global viewport_center_x% = GraphicWidth / 2, viewport_center_y% = GraphicHeight / 2

; -- Mouselook.
Global mouselook_x_inc# = 0.3 ; This sets both the sensitivity and direction (+/-) of the mouse on the X axis.
Global mouselook_y_inc# = 0.3 ; This sets both the sensitivity and direction (+/-) of the mouse on the Y axis.
; Used to limit the mouse movement to within a certain number of pixels (250 is used here) from the center of the screen. This produces smoother mouse movement than continuously moving the mouse back to the center each loop.
Global mouse_left_limit% = 250*MenuScale
Global mouse_right_limit% = GraphicWidth - mouse_left_limit
Global mouse_top_limit% = 150*MenuScale
Global mouse_bottom_limit% = GraphicHeight - mouse_top_limit ; As above.
Global mouse_x_speed_1#, mouse_y_speed_1#, mouse_x_recoil#, mouse_y_recoil#, mouse_y_recoil_energy#, mouse_y_recoil_1#

Global KEY_RIGHT = GetINIInt(OptionFile, "binds", "Right key")
Global KEY_LEFT = GetINIInt(OptionFile, "binds", "Left key")
Global KEY_UP = GetINIInt(OptionFile, "binds", "Up key")
Global KEY_DOWN = GetINIInt(OptionFile, "binds", "Down key")

Global KEY_BLINK = GetINIInt(OptionFile, "binds", "Blink key")
Global KEY_SPRINT = GetINIInt(OptionFile, "binds", "Sprint key")
Global KEY_INV = GetINIInt(OptionFile, "binds", "Inventory key")
Global KEY_CROUCH = GetINIInt(OptionFile, "binds", "Crouch key")
Global KEY_SAVE = GetINIInt(OptionFile, "binds", "Save key")
Global KEY_CONSOLE = GetINIInt(OptionFile, "binds", "Console key")

Global KEY_CHAT = GetINIInt(OptionFile, "binds", "Chat key", 64)
Global KEY_VOICE = GetINIInt(OptionFile, "binds", "Voice key", 47)
Global KEY_JUMP = GetINIInt(OptionFile, "binds", "Jump key", 56)
Global KEY_LEANL = GetINIInt(OptionFile, "binds", "LeanL key", 16)
Global KEY_LEANR = GetINIInt(OptionFile, "binds", "LeanR key", 18)
Global KEY_USING = GetINIInt(OptionFile, "binds", "Using key", 18)

Global MOUSEINTERACT = GetINIInt(OptionFile, "binds", "mouseinteract", 1)

Global MouseSmooth# = GetINIFloat(OptionFile,"options", "mouse smoothing", 1.0)

Const INFINITY# = (999.0) ^ (99999.0), NAN# = (-1.0) ^ (0.5)

Global Mesh_MinX#, Mesh_MinY#, Mesh_MinZ#
Global Mesh_MaxX#, Mesh_MaxY#, Mesh_MaxZ#
Global Mesh_MagX#, Mesh_MagY#, Mesh_MagZ#

;menus, GUI ---------------------------------------------------------------------------------------------------------
Global MainMenuOpen%, MenuOpen%, StopHidingTimer#, InvOpen%
Global OtherOpen.Items = Null

Global SelectedEnding$, EndingScreen%, EndingTimer#

Global MsgTimer#, Msg$, DeathMSG$

Global AccessCode%, KeypadInput$, KeypadTimer#, KeypadMSG$

Global DrawHandIcon%
Dim DrawArrowIcon%(4)

Global LockMouse%

;player stats -------------------------------------------------------------------------------------------------------
Global KillTimer#, KillAnim%, FallTimer#, DeathTimer#
Global Sanity#, ForceMove#, ForceAngle#
Global RestoreSanity%

Global Playable% = True

Global BLINKFREQ#
Global BlinkTimer#, EyeIrritation#, EyeStuck#, BlinkEffect# = 1.0, BlinkEffectTimer#

Global Stamina#, StaminaEffect#=1.0, StaminaEffectTimer#

Global SCP1025state#[6]
Global CameraShakeTimer#, Vomit%, VomitTimer#, Regurgitate%

Global HeartBeatRate#, HeartBeatTimer#, HeartBeatVolume#

Global WearingGasMask%, WearingHazmat%, WearingVest%, Wearing714%, WearingNightVision%
Global NVTimer#

Global SuperMan%, SuperManTimer#

Global Injuries#, Bloodloss#, Infect#, HealTimer#

Global RefinedItems%

Include "SourceCode\Achievements.bb"

;misc ---------------------------------------------------------------------------------------------------------------
Global ShouldNullGame% = False

Global MTFtimer#

Dim RadioState#(10)
Dim RadioState3%(10)
Dim RadioState4%(9)
Dim RadioCHN%(8)

Dim OldAiPics%(5)

Global PlayTime%
Global ConsoleFlush%
Global ConsoleFlushSnd% = 0, ConsoleMusFlush% = 0, ConsoleMusPlay% = 0
Global ConsoleOpen%

Global InfiniteStamina% = False
Global NVBlink%
Global IsNVGBlinking% = False

;[End block]

;camera/lighting effects (blur, camera shake, etc)-------------------------------------------------------------------
Global Shake#

Global ExplosionTimer#, ExplosionSFX%

Global LightsOn% = True

Global SoundTransmission%

;player coordinates, angle, speed, movement etc ---------------------------------------------------------------------
Global GodMode%, NoClip%, NoClipSpeed# = 2.0

Global DropSpeed#, HeadDropSpeed#, CurrSpeed#
Global user_camera_pitch#, side#
Global Crouch%, CrouchState#

Global PlayerZone%, PlayerRoom.Rooms

Global GrabbedEntity%

Global InvertMouse% = GetINIInt(OptionFile, "options", "invert mouse y")
Global MouseHit1%, MouseDown1%, MouseHit2%, DoubleClick%, LastMouseHit1%, MouseUp1%, KeyHitE, KeyDownE

Global CoffinDistance# = 100.0

Global PlayerSoundVolume#


Global DebugHUD%

Global BlurVolume#, BlurTimer#

Global LightBlink#, LightFlash#

Global BumpEnabled% = GetINIInt(OptionFile, "options", "bump mapping enabled")
Global HUDenabled% = GetINIInt(OptionFile, "options", "HUD enabled")

Global Camera%, CameraShake#, CurrCameraZoom#

Global Brightness% = GetINIFloat(OptionFile, "options", "brightness")
Global CameraFogNear# = 0.5
Global CameraFogFar# = 6

Global StoredCameraFogFar# = CameraFogFar

Global MouseSens# = GetINIFloat(OptionFile, "options", "mouse sensitivity")

Global EnableVRam% = GetINIInt(OptionFile, "options", "enable vram")

Include "SourceCode\dreamfilter.bb"

Dim LightSpriteTex(10)

;----------------------------------------------  Console -----------------------------------------------------

Global ConsoleInput$
Global ConsoleScroll#,ConsoleScrollDragging%
Global ConsoleMouseMem%
Global ConsoleReissue.ConsoleMsg = Null
Global ConsoleR% = 255,ConsoleG% = 255,ConsoleB% = 255

Type ConsoleMsg
	Field txt$
	Field isCommand%
	Field r%,g%,b%
End Type

Function CreateConsoleMsg(txt$,r%=-1,g%=-1,b%=-1,isCommand%=False)
	Local c.ConsoleMsg = New ConsoleMsg
	Insert c Before First ConsoleMsg
	
	c\txt = txt
	c\isCommand = isCommand
	
	c\r = r
	c\g = g
	c\b = b
	
	If (c\r<0) Then c\r = ConsoleR
	If (c\g<0) Then c\g = ConsoleG
	If (c\b<0) Then c\b = ConsoleB
End Function

Function UpdateConsole()
	Local e.Events
	
	If CanOpenConsole = False Then
		ConsoleOpen = False
		Return
	EndIf
	
	If ConsoleOpen Then
		Local cm.ConsoleMsg
		
		AASetFont ConsoleFont
		
		ConsoleR = 255 : ConsoleG = 255 : ConsoleB = 255
		
		Local x% = 0, y% = GraphicHeight-300*MenuScale, width% = GraphicWidth, height% = 300*MenuScale-30*MenuScale
		Local StrTemp$, temp%,  i%
		Local ev.Events, r.Rooms, it.Items
		
		DrawFrame x,y,width,height+30*MenuScale
		
		Local consoleHeight% = 0
		Local scrollbarHeight% = 0
		For cm.ConsoleMsg = Each ConsoleMsg
			consoleHeight = consoleHeight + 15*MenuScale
		Next
		scrollbarHeight = (Float(height)/Float(consoleHeight))*height
		If scrollbarHeight>height Then scrollbarHeight = height
		If consoleHeight<height Then consoleHeight = height
		
		Color 50,50,50
		inBar% = MouseOn(x+width-26*MenuScale,y,26*MenuScale,height)
		If inBar Then Color 70,70,70
		Rect x+width-26*MenuScale,y,26*MenuScale,height,True
		
		
		Color 120,120,120
		inBox% = MouseOn(x+width-23*MenuScale,y+height-scrollbarHeight+(ConsoleScroll*scrollbarHeight/height),20*MenuScale,scrollbarHeight)
		If inBox Then Color 200,200,200
		If ConsoleScrollDragging Then Color 255,255,255
		Rect x+width-23*MenuScale,y+height-scrollbarHeight+(ConsoleScroll*scrollbarHeight/height),20*MenuScale,scrollbarHeight,True
		
		If Not MouseDown(1) Then
			ConsoleScrollDragging=False
		ElseIf ConsoleScrollDragging Then
			ConsoleScroll = ConsoleScroll+((ScaledMouseY()-ConsoleMouseMem)*height/scrollbarHeight)
			ConsoleMouseMem = ScaledMouseY()
		EndIf
		
		If (Not ConsoleScrollDragging) Then
			If MouseHit1 Then
				If inBox Then
					ConsoleScrollDragging=True
					ConsoleMouseMem = ScaledMouseY()
				ElseIf inBar Then
					ConsoleScroll = ConsoleScroll+((ScaledMouseY()-(y+height))*consoleHeight/height+(height/2))
					ConsoleScroll = ConsoleScroll/2
				EndIf
			EndIf
		EndIf
		
		mouseScroll = MouseZSpeed()
		If mouseScroll=1 Then
			ConsoleScroll = ConsoleScroll - 15*MenuScale
		ElseIf mouseScroll=-1 Then
			ConsoleScroll = ConsoleScroll + 15*MenuScale
		EndIf
		
		Local reissuePos%
		If KeyHit(200) Then
			reissuePos% = 0
			If (ConsoleReissue=Null) Then
				ConsoleReissue=First ConsoleMsg
				
				While (ConsoleReissue<>Null)
					If (ConsoleReissue\isCommand) Then
						Exit
					EndIf
					reissuePos = reissuePos - 15*MenuScale
					ConsoleReissue = After ConsoleReissue
				Wend
				
			Else
				cm.ConsoleMsg = First ConsoleMsg
				While cm<>Null
					If cm=ConsoleReissue Then Exit
					reissuePos = reissuePos-15*MenuScale
					cm = After cm
				Wend
				ConsoleReissue = After ConsoleReissue
				reissuePos = reissuePos-15*MenuScale
				
				While True
					If (ConsoleReissue=Null) Then
						ConsoleReissue=First ConsoleMsg
						reissuePos = 0
					EndIf
				
					If (ConsoleReissue\isCommand) Then
						Exit
					EndIf
					reissuePos = reissuePos - 15*MenuScale
					ConsoleReissue = After ConsoleReissue
				Wend
			EndIf
			
			If ConsoleReissue<>Null Then
				ConsoleInput = ConsoleReissue\txt
				ConsoleScroll = reissuePos+(height/2)
			EndIf
		EndIf
		
		If KeyHit(208) Then
			reissuePos% = -consoleHeight+15*MenuScale
			If (ConsoleReissue=Null) Then
				ConsoleReissue=Last ConsoleMsg
				
				While (ConsoleReissue<>Null)
					If (ConsoleReissue\isCommand) Then
						Exit
					EndIf
					reissuePos = reissuePos + 15*MenuScale
					ConsoleReissue = Before ConsoleReissue
				Wend
				
			Else
				cm.ConsoleMsg = Last ConsoleMsg
				While cm<>Null
					If cm=ConsoleReissue Then Exit
					reissuePos = reissuePos+15*MenuScale
					cm = Before cm
				Wend
				ConsoleReissue = Before ConsoleReissue
				reissuePos = reissuePos+15*MenuScale
				
				While True
					If (ConsoleReissue=Null) Then
						ConsoleReissue=Last ConsoleMsg
						reissuePos=-consoleHeight+15*MenuScale
					EndIf
				
					If (ConsoleReissue\isCommand) Then
						Exit
					EndIf
					reissuePos = reissuePos + 15*MenuScale
					ConsoleReissue = Before ConsoleReissue
				Wend
			EndIf
			
			If ConsoleReissue<>Null Then
				ConsoleInput = ConsoleReissue\txt
				ConsoleScroll = reissuePos+(height/2)
			EndIf
		EndIf
		
		If ConsoleScroll<-consoleHeight+height Then ConsoleScroll = -consoleHeight+height
		If ConsoleScroll>0 Then ConsoleScroll = 0
		
		Color 255, 255, 255
		
		SelectedInputBox = 2
		Local oldConsoleInput$ = ConsoleInput
		ConsoleInput = InputBox(x, y + height, width, 30*MenuScale, ConsoleInput, 2)
		If oldConsoleInput<>ConsoleInput Then
			ConsoleReissue = Null
		EndIf
		ConsoleInput = Left(ConsoleInput, 100)
		
		If KeyHit(28) And ConsoleInput <> "" Then
			If GetScripts() Then 
				public_inqueue(public_OnConsole)
				public_addparam(ConsoleInput, SE_STRING)
				callback
			EndIf
			ConsoleReissue = Null
			ConsoleScroll = 0
			CreateConsoleMsg(ConsoleInput,255,255,0,True)
			ExecuteConsoleCommand(ConsoleInput)
			
			ConsoleInput = ""
		End If
		
		Local TempY% = y + height - 25*MenuScale - ConsoleScroll
		Local count% = 0
		For cm.ConsoleMsg = Each ConsoleMsg
			count = count+1
			If count>1000 Then
				Delete cm
			Else
				If TempY >= y And TempY < y + height - 20*MenuScale Then
					If cm=ConsoleReissue Then
						Color cm\r/4,cm\g/4,cm\b/4
						Rect x,TempY-2*MenuScale,width-30*MenuScale,24*MenuScale,True
					EndIf
					Color cm\r,cm\g,cm\b
					If cm\isCommand Then
						AAText(x + 20*MenuScale, TempY, "> "+cm\txt)
					Else
						AAText(x + 20*MenuScale, TempY, cm\txt)
					EndIf
				EndIf
				TempY = TempY - 15*MenuScale
			EndIf
			
		Next
		
		Color 255,255,255
		
		If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
	End If
	
	AASetFont Font1
	
End Function

ConsoleR = 0 : ConsoleG = 255 : ConsoleB = 255
CreateConsoleMsg("Console commands: ")
CreateConsoleMsg("  - teleport [room name]")
CreateConsoleMsg("  - godmode [on/off]")
CreateConsoleMsg("  - noclip [on/off]")
CreateConsoleMsg("  - noclipspeed [x] (default = 2.0)")
CreateConsoleMsg("  - wireframe [on/off]")
CreateConsoleMsg("  - debughud [on/off]")
CreateConsoleMsg("  - camerafog [near] [far]")
CreateConsoleMsg(" ")
CreateConsoleMsg("  - status")
CreateConsoleMsg("  - heal")
CreateConsoleMsg(" ")
CreateConsoleMsg("  - spawnitem [item name]")
CreateConsoleMsg(" ")
CreateConsoleMsg("  - 173speed [x] (default = 35)")
CreateConsoleMsg("  - disable173/enable173")
CreateConsoleMsg("  - disable106/enable106")
CreateConsoleMsg("  - 173state/106state/096state")
CreateConsoleMsg("  - spawn [npc type]")

;---------------------------------------------------------------------------------------------------

;----------------------------------------------  Sounds -----------------------------------------------------

;[Block]

Global SoundEmitter%
Global TempSounds%[10]
Global TempSoundCHN%
Global TempSoundIndex% = 0

;The Music now has to be pre-defined, as the new system uses streaming instead of the usual sound loading system Blitz3D has
Dim Music$(40)
Music(0) = "The Dread"
Music(1) = "HeavyContainment"
Music(2) = "EntranceZone"
Music(3) = "PD"
Music(4) = "079"
Music(5) = "GateB1"
Music(6) = "GateB2"
Music(7) = "Room3Storage"
Music(8) = "Room049"
Music(9) = "8601"
Music(10) = "106"
Music(11) = "Menu"
Music(12) = "8601Cancer"
Music(13) = "Intro"
Music(14) = "178"
Music(15) = "PDTrench"
Music(16) = "205"
Music(17) = "GateA"
Music(18) = "1499"
Music(19) = "1499Danger"
Music(20) = "049Chase"
Music(21) = "..\Ending\MenuBreath"
Music(22) = "914"
Music(23) = "Ending"
Music(24) = "Credits"
Music(25) = "SaveMeFrom"
Music(26) = "EarRing"

Global MusicVolume# = GetINIFloat(OptionFile, "audio", "music volume")
;Global MusicCHN% = StreamSound_Strict("SFX\Music\"+Music(2)+".ogg", MusicVolume, CurrMusicStream)

Global CurrMusicStream, MusicCHN
MusicCHN = StreamSound_Strict("SFX\Music\"+Music(2)+".ogg",MusicVolume,Mode)

Global CurrMusicVolume# = 1.0, NowPlaying%=2, ShouldPlay%=11
Global CurrMusic% = 1

DrawLoading(10, True)

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
DrawLoading(20, True)

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

DrawLoading(30, True)

;[End block]

;New Sounds and Meshes/Other things in SCP:CB 1.3 - ENDSHN
;[Block]
;Global NTF_1499EnterSFX% = LoadSound_Strict("SFX\SCP\1499\Enter.ogg")
;Global NTF_1499LeaveSFX% = LoadSound_Strict("SFX\SCP\1499\Exit.ogg")

Global PlayCustomMusic% = False, CustomMusic% = 0

Global Monitor2, Monitor3, MonitorTexture2, MonitorTexture3, MonitorTexture4, MonitorTextureOff
Global MonitorTimer# = 0.0, MonitorTimer2# = 0.0, UpdateCheckpoint1%, UpdateCheckpoint2%

;This variable is for when a camera detected the player
	;False: Player is not seen (will be set after every call of the Main Loop
	;True: The Player got detected by a camera
Global PlayerDetected%
Global PrevInjuries#,PrevBloodloss#
Global NoTarget% = False

Global NVGImages = LoadAnimImage("GFX\battery.png",64,64,0,2)
MaskImage NVGImages,255,0,255

Global Wearing1499% = False
Global AmbientLightRoomTex%, AmbientLightRoomVal%

Global EnableUserTracks% = GetINIInt(OptionFile, "audio", "enable user tracks")
Global UserTrackMode% = GetINIInt(OptionFile, "audio", "user track setting")
Global UserTrackCheck% = 0, UserTrackCheck2% = 0
Global UserTrackMusicAmount% = 0, CurrUserTrack%, UserTrackFlag% = False
Dim UserTrackName$(256)

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
Global SuicideMSG
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

Global CurrMenu_TestIMG$ = ""

Global ParticleAmount% = GetINIInt(OptionFile,"options","particle amount")

Dim NavImages(5)
For i = 0 To 3
	NavImages(i) = LoadImage_Strict("GFX\navigator\roomborder"+i+".png")
	MaskImage NavImages(i),255,0,255
Next
NavImages(4) = LoadImage_Strict("GFX\navigator\batterymeter.png")

Global NavBG = CreateImage(GraphicWidth,GraphicHeight)

Global LightConeModel

Global ParticleEffect[10]

Const MaxDTextures=11
Global DTextures[MaxDTextures]

Global NPC049OBJ, NPC0492OBJ
Global ClerkOBJ

Global IntercomStreamCHN%

Global ForestNPC,ForestNPCTex,ForestNPCData#[3]
;[End Block]

; ---------------------------------------- Console ---------------------------------------------------------
Function ExecuteConsoleCommand(cinput$, ErrorMessage% = True, SendConsoleTick% = True)
	If Instr(cinput, " ") > 0 Then
		StrTemp$ = Lower(Left(cinput, Instr(cinput, " ") - 1))
	Else
		StrTemp$ = Lower(cinput)
	End If
	
	;if Lower(StrTemp) <> "playtempsound" then
	If SendConsoleTick Then
		If udp_GetStream() Then
			udp_ByteStreamWriteChar M_GLOBALCONSOLE
			udp_ByteStreamWriteLine cinput
			udp_setmicrobyte(M_GLOBALCONSOLE)
		EndIf
	EndIf
	
	Select Lower(StrTemp)
		Case "help"
			;[Block]
			If Instr(cinput, " ")<>0 Then
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Else
				StrTemp$ = ""
			EndIf
			ConsoleR = 0 : ConsoleG = 255 : ConsoleB = 255
			
			Select Lower(StrTemp)
				Case "1",""
					CreateConsoleMsg("LIST OF COMMANDS - PAGE 1/3")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("- asd")
					CreateConsoleMsg("- status")
					CreateConsoleMsg("- camerapick")
					CreateConsoleMsg("- ending")
					CreateConsoleMsg("- noclipspeed")
					CreateConsoleMsg("- noclip")
					CreateConsoleMsg("- injure [value]")
					CreateConsoleMsg("- infect [value]")
					CreateConsoleMsg("- heal")
					CreateConsoleMsg("- teleport [room name]")
					CreateConsoleMsg("- spawnitem [item name]")
					CreateConsoleMsg("- wireframe")
					CreateConsoleMsg("- 173speed")
					CreateConsoleMsg("- 106speed")
					CreateConsoleMsg("- 173state")
					CreateConsoleMsg("- 106state")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Use "+Chr(34)+"help 2/3"+Chr(34)+" to find more commands.")
					CreateConsoleMsg("Use "+Chr(34)+"help [command name]"+Chr(34)+" to get more information about a command.")
					CreateConsoleMsg("******************************")
				Case "2"
					CreateConsoleMsg("LIST OF COMMANDS - PAGE 2/3")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("- spawn [npc type] [state]")
					CreateConsoleMsg("- reset096")
					CreateConsoleMsg("- disable173")
					CreateConsoleMsg("- enable173")
					CreateConsoleMsg("- disable106")
					CreateConsoleMsg("- enable106")
					CreateConsoleMsg("- halloween")
					CreateConsoleMsg("- sanic")
					CreateConsoleMsg("- scp-420-j")
					CreateConsoleMsg("- godmode")
					CreateConsoleMsg("- revive")
					CreateConsoleMsg("- noclip")
					CreateConsoleMsg("- showfps")
					CreateConsoleMsg("- 096state")
					CreateConsoleMsg("- debughud")
					CreateConsoleMsg("- camerafog [near] [far]")
					CreateConsoleMsg("- gamma [value]")
					CreateConsoleMsg("- infinitestamina")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Use "+Chr(34)+"help [command name]"+Chr(34)+" to get more information about a command.")
					CreateConsoleMsg("******************************")
				Case "3"
					CreateConsoleMsg("- playmusic [clip + .wav/.ogg]")
					CreateConsoleMsg("- notarget")
					CreateConsoleMsg("- unlockexits")
				Case "asd"
					CreateConsoleMsg("HELP - asd")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Actives godmode, noclip, wireframe and")
					CreateConsoleMsg("sets fog distance to 20 near, 30 far")
					CreateConsoleMsg("******************************")
				Case "camerafog"
					CreateConsoleMsg("HELP - camerafog")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Sets the draw distance of the fog.")
					CreateConsoleMsg("The fog begins generating at 'CameraFogNear' units")
					CreateConsoleMsg("away from the camera and becomes completely opaque")
					CreateConsoleMsg("at 'CameraFogFar' units away from the camera.")
					CreateConsoleMsg("Example: camerafog 20 40")
					CreateConsoleMsg("******************************")
				Case "gamma"
					CreateConsoleMsg("HELP - gamma")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Sets the gamma correction.")
					CreateConsoleMsg("Should be set to a value between 0.0 and 2.0.")
					CreateConsoleMsg("Default is 1.0.")
					CreateConsoleMsg("******************************")
				Case "noclip","fly"
					CreateConsoleMsg("HELP - noclip")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Toggles noclip, unless a valid parameter")
					CreateConsoleMsg("is specified (on/off).")
					CreateConsoleMsg("Allows the camera to move in any direction while")
					CreateConsoleMsg("bypassing collision.")
					CreateConsoleMsg("******************************")
				Case "godmode","god"
					CreateConsoleMsg("HELP - godmode")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Toggles godmode, unless a valid parameter")
					CreateConsoleMsg("is specified (on/off).")
					CreateConsoleMsg("Prevents player death under normal circumstances.")
					CreateConsoleMsg("******************************")
				Case "wireframe"
					CreateConsoleMsg("HELP - wireframe")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Toggles wireframe, unless a valid parameter")
					CreateConsoleMsg("is specified (on/off).")
					CreateConsoleMsg("Allows only the edges of geometry to be rendered,")
					CreateConsoleMsg("making everything else transparent.")
					CreateConsoleMsg("******************************")
				Case "spawnitem"
					CreateConsoleMsg("HELP - spawnitem")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Spawns an item at the player's location.")
					CreateConsoleMsg("Any name that can appear in your inventory")
					CreateConsoleMsg("is a valid parameter.")
					CreateConsoleMsg("Example: spawnitem Key Card Omni")
					CreateConsoleMsg("******************************")
				Case "spawn"
					CreateConsoleMsg("HELP - spawn")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Spawns an NPC at the player's location.")
					CreateConsoleMsg("Valid parameters are:")
					CreateConsoleMsg("008zombie / 049 / 049-2 / 066 / 096 / 106 / 173")
					CreateConsoleMsg("/ 178-1 / 372 / 513-1 / 966 / 1499-1 / class-d")
					CreateConsoleMsg("/ guard / mtf / apache / tentacle")
					CreateConsoleMsg("******************************")
				Case "revive","undead","resurrect"
					CreateConsoleMsg("HELP - revive")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Resets the player's death timer after the dying")
					CreateConsoleMsg("animation triggers.")
					CreateConsoleMsg("Does not affect injury, blood loss")
					CreateConsoleMsg("or 008 infection values.")
					CreateConsoleMsg("******************************")
				Case "teleport"
					CreateConsoleMsg("HELP - teleport")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Teleports the player to the first instance")
					CreateConsoleMsg("of the specified room. Any room that appears")
					CreateConsoleMsg("in rooms.ini is a valid parameter.")
					CreateConsoleMsg("******************************")
				Case "stopsound", "stfu"
					CreateConsoleMsg("HELP - stopsound")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Stops all currently playing sounds.")
					CreateConsoleMsg("******************************")
				Case "camerapick"
					CreateConsoleMsg("HELP - camerapick")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Prints the texture name and coordinates of")
					CreateConsoleMsg("the model the camera is pointing at.")
					CreateConsoleMsg("******************************")
				Case "status"
					CreateConsoleMsg("HELP - status")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Prints player, camera, and room information.")
					CreateConsoleMsg("******************************")
				Case "weed","scp-420-j","420"
					CreateConsoleMsg("HELP - 420")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Generates dank memes.")
					CreateConsoleMsg("******************************")
				Case "playmusic"
					CreateConsoleMsg("HELP - playmusic")
					CreateConsoleMsg("******************************")
					CreateConsoleMsg("Will play tracks in .ogg/.wav format")
					CreateConsoleMsg("from "+Chr(34)+"SFX\Music\Custom\"+Chr(34)+".")
					CreateConsoleMsg("******************************")
					
				Default
					CreateConsoleMsg("There is no help available for that command.",255,150,0)
			End Select
			
			;[End Block]
		;Case "playtempsound"
		;	args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
		;	PlaySound_Strict(LoadTempSound(Piece$(args$, 1)))
		;	multiplayer_WriteTempSound(Piece$(args$, 1), 0,0,0, Piece$(args$, 2), Piece$(args$, 3))
		Case "decal"
			pvt = CreatePivot()
			PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
			TurnEntity pvt, 90, 0, 0
			EntityPick(pvt,0.3)
			de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
			de\size = Rnd(0.03,0.08)*Min(Injuries,3.0) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
			tempchn% = PlaySound_Strict (DripSFX(Rand(0,2)))
			ChannelVolume tempchn, Rnd(0.0,0.8)*SFXVolume
			ChannelPitch tempchn, Rand(20000,30000)
			
			FreeEntity pvt
			multiplayer_WriteDecal(de,1)
		Case "ambient"
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			ShouldPlay = Int(StrTemp)
		Case "asd"
			;[Block]
			WireFrame 1
			WireframeState=1
			GodMode = 1
			NoClip = 1
			CameraFogNear = 15
			CameraFogFar = 20
			;[End Block]
		Case "status"
			;[Block]
			ConsoleR = 0 : ConsoleG = 255 : ConsoleB = 0
			CreateConsoleMsg("******************************")
			CreateConsoleMsg("Status: ")
			CreateConsoleMsg("Coordinates: ")
			CreateConsoleMsg("    - collider: "+EntityX(Collider)+", "+EntityY(Collider)+", "+EntityZ(Collider))
			CreateConsoleMsg("    - camera: "+EntityX(Camera)+", "+EntityY(Camera)+", "+EntityZ(Camera))
			
			CreateConsoleMsg("Rotation: ")
			CreateConsoleMsg("    - collider: "+EntityPitch(Collider)+", "+EntityYaw(Collider)+", "+EntityRoll(Collider))
			CreateConsoleMsg("    - camera: "+EntityPitch(Camera)+", "+EntityYaw(Camera)+", "+EntityRoll(Camera))
			
			CreateConsoleMsg("Room: "+PlayerRoom\RoomTemplate\Name)
			For ev.Events = Each Events
				If ev\room = PlayerRoom Then
					CreateConsoleMsg("Room event: "+ev\EventName)	
					CreateConsoleMsg("-    state: "+ev\EventState)
					CreateConsoleMsg("-    state2: "+ev\EventState2)	
					CreateConsoleMsg("-    state3: "+ev\EventState3)
					Exit
				EndIf
			Next
			
			CreateConsoleMsg("Room coordinates: "+Floor(EntityX(PlayerRoom\obj) / 8.0 + 0.5)+", "+ Floor(EntityZ(PlayerRoom\obj) / 8.0 + 0.5))
			CreateConsoleMsg("Stamina: "+Stamina)
			CreateConsoleMsg("Death timer: "+KillTimer)					
			CreateConsoleMsg("Blinktimer: "+BlinkTimer)
			CreateConsoleMsg("Injuries: "+Injuries)
			CreateConsoleMsg("Bloodloss: "+Bloodloss)
			CreateConsoleMsg("******************************")
			;[End Block]
		Case "camerapick"
			;[Block]
			ConsoleR = 0 : ConsoleG = 255 : ConsoleB = 0
			c = CameraPick(Camera,GraphicWidth/2, GraphicHeight/2)
			If c = 0 Then
				CreateConsoleMsg("******************************")
				CreateConsoleMsg("No entity  picked")
				CreateConsoleMsg("******************************")								
			Else
				CreateConsoleMsg("******************************")
				CreateConsoleMsg("Picked entity:")
				sf = GetSurface(c,1)
				b = GetSurfaceBrush( sf )
				t = GetBrushTexture(b,0)
				texname$ =  StripPath(TextureName(t))
				CreateConsoleMsg("Texture name: "+texname)
				CreateConsoleMsg("Coordinates: "+EntityX(c)+", "+EntityY(c)+", "+EntityZ(c))
				CreateConsoleMsg("******************************")							
			EndIf
			;[End Block]
		Case "ending"
			;[Block]
			SelectedEnding = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			KillTimer = -0.1
			;EndingTimer = -0.1
			;[End Block]
		Case "noclipspeed"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			NoClipSpeed = Float(StrTemp)
			;[End Block]
		Case "injure"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Injuries = Float(StrTemp)
			;[End Block]
		Case "infect"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Infect = Float(StrTemp)
			;[End Block]
		Case "hidedistance"
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			HideDistance = Float(StrTemp)
		Case "heal"
			;[Block]
			Injuries = 0
			Bloodloss = 0
			;[End Block]
		Case "freeitems"
			For it.Items = Each Items
				RemoveItem(it)
			Next
		Case "role"
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			If Int(StrTemp) >= 0 And Int(StrTemp) <= LAST_BREACH_TYPE-1 Then multiplayer_RequestRole(StrTemp)
		Case "daun"
			ChangeRMesh(PlayerRoom, "GFX\map\room106_opt.rmesh")
		Case "teleport"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
		
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
					;PositionEntity (Collider, EntityX(r\obj), 0.7, EntityZ(r\obj))
					PositionEntity (Collider, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
					ResetEntity(Collider)
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					Exit
				EndIf
			Next
			found = False
			For p.players = Each players
				If p\ID <> NetworkServer\MyID Then
					If Lower(p\Name) = Lower(StrTemp) Then
						For r.Rooms = Each Rooms
							If r\RoomTemplate\Name = p\PlayerRoomName Then
								PositionEntity (Collider, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot))
								ResetEntity(Collider)
								UpdateDoors()
								UpdateRooms()
								For it.Items = Each Items
									it\disttimer = 0
								Next
								PlayerRoom = r
								found = True
								Exit
							EndIf
						Next
						If Not found Then
							PositionEntity (Collider, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot))
							ResetEntity(Collider)
							UpdateDoors()
							UpdateRooms()
							For it.Items = Each Items
								it\disttimer = 0
							Next
							found = True
						EndIf
					EndIf
				EndIf
			Next
			If PlayerRoom\RoomTemplate\Name <> StrTemp And found = False Then CreateConsoleMsg("Room or player not found.",255,150,0)
			;[End Block]
		Case "spawnitem"
			;[Block]
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
				If NetworkServer\MainPlayer Then
					GameLoad = 1
					CreateConsoleMsg(itt\name + " spawned.")
					it.Items = CreateItem(itt\name, itt\tempname, EntityX(Collider), EntityY(Camera,True)-0.1, EntityZ(Collider))
					EntityType(it\collider, HIT_ITEM)
					GameLoad = 0
				EndIf
			EndIf
			If temp = False Then CreateConsoleMsg("Item not found.",255,150,0)
			;[End Block]
		Case "wireframe"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Select StrTemp
				Case "on", "1", "true"
					WireframeState = True							
				Case "off", "0", "false"
					WireframeState = False
				Default
					WireframeState = Not WireframeState
			End Select
			
			If WireframeState Then
				CreateConsoleMsg("WIREFRAME ON")
			Else
				CreateConsoleMsg("WIREFRAME OFF")	
			EndIf
			
			WireFrame WireframeState
			;[End Block]
		Case "173speed"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Curr173\Speed = Float(StrTemp)
			CreateConsoleMsg("173's speed set to " + StrTemp)
			;[End Block]
		Case "106speed"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Curr106\Speed = Float(StrTemp)
			CreateConsoleMsg("106's speed set to " + StrTemp)
			;[End Block]
		Case "173state"
			;[Block]
			CreateConsoleMsg("SCP-173")
			CreateConsoleMsg("Position: " + EntityX(Curr173\obj) + ", " + EntityY(Curr173\obj) + ", " + EntityZ(Curr173\obj))
			CreateConsoleMsg("Idle: " + Curr173\Idle)
			CreateConsoleMsg("State: " + Curr173\State)
			;[End Block]
		Case "106state"
			;[Block]
			CreateConsoleMsg("SCP-106")
			CreateConsoleMsg("Position: " + EntityX(Curr106\obj) + ", " + EntityY(Curr106\obj) + ", " + EntityZ(Curr106\obj))
			CreateConsoleMsg("Idle: " + Curr106\Idle)
			CreateConsoleMsg("State: " + Curr106\State)
			;[End Block]
		Case "reset096"
			;[Block]
			For n.NPCs = Each NPCs
				If n\NPCtype = NPCtype096 Then
					n\State = 0
					StopStream_Strict(n\SoundChn) : n\SoundChn=0
					If n\SoundChn2<>0
						StopStream_Strict(n\SoundChn2) : n\SoundChn2=0
					EndIf
					Exit
				EndIf
			Next
			;[End Block]
		Case "disable173"
			;[Block]
			Curr173\Idle = 3 ;This phenominal comment is brought to you by PolyFox. His absolute wisdom in this fatigue of knowledge brought about a new era of 173 state checks.
			HideEntity Curr173\obj
			HideEntity Curr173\Collider
			;[End Block]
		Case "enable173"
			;[Block]
			Curr173\Idle = False
			ShowEntity Curr173\obj
			ShowEntity Curr173\Collider
			;[End Block]
		Case "disable106"
			;[Block]
			Curr106\Idle = True
			Curr106\State = 200000
			Contained106 = True
			;[End Block]
		Case "enable106"
			;[Block]
			Curr106\Idle = False
			Contained106 = False
			ShowEntity Curr106\Collider
			ShowEntity Curr106\obj
			;[End Block]
		Case "halloween"
			;[Block]
			HalloweenTex = Not HalloweenTex
			If HalloweenTex Then
				Local tex = LoadTexture_Strict("GFX\npcs\173h.pt", 1)
				EntityTexture Curr173\obj, tex, 0, 0
				FreeTexture tex
				CreateConsoleMsg("173 JACK-O-LANTERN ON")
			Else
				Local tex2 = LoadTexture_Strict("GFX\npcs\173texture.jpg", 1)
				EntityTexture Curr173\obj, tex2, 0, 0
				FreeTexture tex2
				CreateConsoleMsg("173 JACK-O-LANTERN OFF")
			EndIf
			;[End Block]
		Case "sanic"
			;[Block]
			SuperMan = Not SuperMan
			If SuperMan = True Then
				CreateConsoleMsg("GOTTA GO FAST")
			Else
				CreateConsoleMsg("WHOA SLOW DOWN")
			EndIf
			;[End Block]
		Case "scp-420-j","420","weed"
			;[Block]
			If NetworkServer\MainPlayer Then
				For i = 1 To 20
					If Rand(2)=1 Then
						it.Items = CreateItem("Some SCP-420-J","420", EntityX(Collider,True)+Cos((360.0/20.0)*i)*Rnd(0.3,0.5), EntityY(Camera,True), EntityZ(Collider,True)+Sin((360.0/20.0)*i)*Rnd(0.3,0.5))
					Else
						it.Items = CreateItem("Joint","420s", EntityX(Collider,True)+Cos((360.0/20.0)*i)*Rnd(0.3,0.5), EntityY(Camera,True), EntityZ(Collider,True)+Sin((360.0/20.0)*i)*Rnd(0.3,0.5))
					EndIf
					EntityType (it\collider, HIT_ITEM)
				Next
				PlaySound_Strict LoadTempSound("SFX\Music\420J.ogg")
				multiplayer_WriteTempSound "SFX\Music\420J.ogg",0,0,0,20
			EndIf
			;[End Block]
		Case "godmode", "god"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Select StrTemp
				Case "on", "1", "true"
					GodMode = True						
				Case "off", "0", "false"
					GodMode = False
				Default
					GodMode = Not GodMode
			End Select	
			If GodMode Then
				CreateConsoleMsg("GODMODE ON")
			Else
				CreateConsoleMsg("GODMODE OFF")	
			EndIf
			;[End Block]
		Case "revive","undead","resurrect"
			;[Block]
			Stamina# = 60
			StaminaEffect#=1
			StaminaEffectTimer# = 0
			For i = 0 To 5
				SCP1025state[i] = 0
			Next
			Bloodloss = 0
			
			SuperMan = False
			SuperManTimer = 0
			
			DropSpeed = -0.1
			HeadDropSpeed = 0.0
			Shake = 0
			CurrSpeed = 0
			
			HeartBeatVolume = 0
			Infect = 0
			CameraShake = 0
			Shake = 0
			LightFlash = 0
			BlurTimer = 0
		
			FallTimer = 0
			MenuOpen = False
			
			GodMode = 0
			NoClip = 0
			
			ShowEntity Collider
			PositionEntity Collider, EntityX(Collider), EntityY(Collider)+1, EntityZ(Collider)
			ResetEntity Collider
			
			KillTimer = 0
			KillAnim = 0
			MyPlayer\IsDead = 0
			EyeIrritation = 0
			HideEntity Head
			If GetScripts() Then public_inqueue(public_OnSpawn, True)
			;[End Block]
		Case "noclip","fly"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Select StrTemp
				Case "on", "1", "true"
					NoClip = True
					Playable = True
				Case "off", "0", "false"
					NoClip = False	
					RotateEntity Collider, 0, EntityYaw(Collider), 0
					RotateEntity Collider, 0, EntityYaw(Collider), 0
				Default
					NoClip = Not NoClip
					If NoClip = False Then		
						RotateEntity Collider, 0, EntityYaw(Collider), 0
						RotateEntity Collider, 0, EntityYaw(Collider), 0
					Else
						Playable = True
					EndIf
			End Select
			
			If NoClip Then
				CreateConsoleMsg("NOCLIP ON")
			Else
				CreateConsoleMsg("NOCLIP OFF")
			EndIf
			
			DropSpeed = 0
			;[End Block]
		Case "showfps"
			;[Block]
			ShowFPS = Not ShowFPS
			CreateConsoleMsg("ShowFPS: "+Str(ShowFPS))
			;[End Block]
		Case "096state"
			;[Block]
			For n.NPCs = Each NPCs
				If n\NPCtype = NPCtype096 Then
					CreateConsoleMsg("SCP-096")
					CreateConsoleMsg("Position: " + EntityX(n\obj) + ", " + EntityY(n\obj) + ", " + EntityZ(n\obj))
					CreateConsoleMsg("Idle: " + n\Idle)
					CreateConsoleMsg("State: " + n\State)
					Exit
				EndIf
			Next
			CreateConsoleMsg("SCP-096 has not spawned.")
			;[End Block]
		Case "debughud"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Select StrTemp
				Case "on", "1", "true"
					DebugHUD = True
				Case "off", "0", "false"
					DebugHUD = False
				Default
					DebugHUD = Not DebugHUD
			End Select
			
			If DebugHUD Then
				CreateConsoleMsg("Debug Mode On")
			Else
				CreateConsoleMsg("Debug Mode Off")
			EndIf
			;[End Block]
		Case "vomit"
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			VomitTimer = Int(StrTemp)
			CreateConsoleMsg("Vomit timer set to "+VomitTimer)
		Case "stopsound", "stfu"
			;[Block]
			For snd.Sound = Each Sound
				For i = 0 To 31
					If snd\channels[i]<>0 Then
						StopChannel snd\channels[i]
					EndIf
				Next
			Next
			If (Not udp_GetStream()) Or NetworkServer\MainPlayer Then
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
				CreateConsoleMsg("Stopped all sounds.")
			Else
				multiplayer_Send(M_STOPSOUND)
			EndIf
			
			For snd2.snd3d = Each snd3d
				If Not snd2\fmod Then
					StopChannel(snd2\soundchn)
					If snd2\tempentity Then FreeEntity snd2\entity
					Delete snd2
				Else
					FSOUND_StopSound(snd2\soundchn)
					FSOUND_Stream_Stop(snd2\sound)
					FSOUND_Stream_Close(snd2\sound)
					Delete snd2
				EndIf
			Next
			;[End Block]
		Case "camerafog"
			;[Block]
			args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			CameraFogNear = Float(Left(args, Len(args) - Instr(args, " ")))
			CameraFogFar = Float(Right(args, Len(args) - Instr(args, " ")))
			CreateConsoleMsg("Near set to: " + CameraFogNear + ", far set to: " + CameraFogFar)
			If CameraFogFar > HideDistance Then HideEntity Fog Else ShowEntity Fog
			;[End Block]
		Case "gamma"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			ScreenGamma = Int(StrTemp)
			CreateConsoleMsg("Gamma set to " + ScreenGamma)
			;[End Block]
		Case "spawn"
			;[Block]
			args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			StrTemp$ = Piece$(args$, 1)
			StrTemp2$ = Piece$(args$, 2)
			
			;Hacky fix for when the user doesn't input a second parameter.
			If (StrTemp <> StrTemp2) Then
				Console_SpawnNPC(StrTemp, StrTemp2)
			Else
				Console_SpawnNPC(StrTemp)
			EndIf
			;[End Block]
		;new Console Commands in SCP:CB 1.3 - ENDSHN
		Case "infinitestamina","infstam"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Select StrTemp
				Case "on", "1", "true"
					InfiniteStamina% = True						
				Case "off", "0", "false"
					InfiniteStamina% = False
				Default
					InfiniteStamina% = Not InfiniteStamina%
			End Select
			
			If InfiniteStamina
				CreateConsoleMsg("INFINITE STAMINA ON")
			Else
				CreateConsoleMsg("INFINITE STAMINA OFF")	
			EndIf
			;[End Block]
		Case "unlockexits"
			;[Block]
			For e.Events = Each Events
				If e\EventName = "gateaentrance" Then
					e\EventState3 = 1
					e\room\RoomDoors[1]\open = True
				ElseIf e\EventName = "exit1" Then
					e\EventState3 = 1
					e\room\RoomDoors[4]\open = True
				EndIf
			Next
			CreateConsoleMsg("Gate A and B are now unlocked.")	
			
			RemoteDoorOn = True
			multiplayer_Send(M_UNLOCKEXITS)
			;[End Block]
		Case "kill","suicide"
			;[Block]
			Select Rand(4)
				Case 1
					DeathMSG = "[REDACTED]"
				Case 2
					DeathMSG = "Subject D-9341 found dead in Sector [REDACTED]. "
					DeathMSG = DeathMSG + "The subject appears to have attained no physical damage, and there is no visible indication as to what killed him. "
					DeathMSG = DeathMSG + "Body was sent for autopsy."
				Case 3
					DeathMSG = "EXCP_ACCESS_VIOLATION"
				Case 4
					DeathMSG = "Subject D-9341 found dead in Sector [REDACTED]. "
					DeathMSG = DeathMSG + "The subject appears to have scribbled the letters "+Chr(34)+"kys"+Chr(34)+" in his own blood beside him. "
					DeathMSG = DeathMSG + "No other signs of physical trauma or struggle can be observed. Body was sent for autopsy."
			End Select
			Kill("decided to gnaw their veins")
			;[End Block]
		Case "playmusic"
			;[Block]
			; I think this might be broken since the FMod library streaming was added. -Mark
			If Instr(cinput, " ")<>0 Then
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Else
				StrTemp$ = ""
			EndIf
			
			If StrTemp$ <> ""
				PlayCustomMusic% = True
				If CustomMusic <> 0 Then FreeSound_Strict CustomMusic : CustomMusic = 0
				If MusicCHN <> 0 Then StopChannel MusicCHN
				CustomMusic = LoadSound_Strict("SFX\Music\Custom\"+StrTemp$)
				If CustomMusic = 0
					PlayCustomMusic% = False
				EndIf
			Else
				PlayCustomMusic% = False
				If CustomMusic <> 0 Then FreeSound_Strict CustomMusic : CustomMusic = 0
				If MusicCHN <> 0 Then StopChannel MusicCHN
			EndIf
			;[End Block]
		Case "tp"
			;[Block]
			For n.NPCs = Each NPCs
				If n\NPCtype = NPCtypeMTF
					If n\MTFLeader = Null
						PositionEntity Collider,EntityX(n\Collider),EntityY(n\Collider)+5,EntityZ(n\Collider)
						ResetEntity Collider
						Exit
					EndIf
				EndIf
			Next
			;[End Block]
		Case "tele"
			;[Block]
			args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			StrTemp$ = Piece$(args$,1," ")
			StrTemp2$ = Piece$(args$,2," ")
			StrTemp3$ = Piece$(args$,3," ")
			PositionEntity Collider,Float(StrTemp$),Float(StrTemp2$),Float(StrTemp3$)
			PositionEntity Camera,Float(StrTemp$),Float(StrTemp2$),Float(StrTemp3$)
			ResetEntity Collider
			ResetEntity Camera
			CreateConsoleMsg("Teleported to coordinates (X|Y|Z): "+EntityX(Collider)+"|"+EntityY(Collider)+"|"+EntityZ(Collider))
			;[End Block]
		Case "notarget"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Select StrTemp
				Case "on", "1", "true"
					NoTarget% = True						
				Case "off", "0", "false"
					NoTarget% = False	
				Default
					NoTarget% = Not NoTarget%
			End Select
			
			If NoTarget% = False Then
				CreateConsoleMsg("NOTARGET OFF")
			Else
				CreateConsoleMsg("NOTARGET ON")	
			EndIf
			multiplayer_Send(M_NOTARGET)
			;[End Block]
		Case "spawnradio"
			;[Block]
			it.Items = CreateItem("Radio Transceiver", "fineradio", EntityX(Collider), EntityY(Camera,True), EntityZ(Collider))
			EntityType(it\collider, HIT_ITEM)
			it\state = 101
			;[End Block]
		Case "spawnnvg"
			;[Block]
			it.Items = CreateItem("Night Vision Goggles", "nvgoggles", EntityX(Collider), EntityY(Camera,True), EntityZ(Collider))
			EntityType(it\collider, HIT_ITEM)
			it\state = 1000
			;[End Block]
		Case "spawnpumpkin","pumpkin"
			;[Block]
			CreateConsoleMsg("What pumpkin?")
			;[End Block]
		Case "spawnnav"
			;[Block]
			it.Items = CreateItem("S-NAV Navigator Ultimate", "nav", EntityX(Collider), EntityY(Camera,True), EntityZ(Collider))
			EntityType(it\collider, HIT_ITEM)
			it\state = 101
			;[End Block]
		Case "teleport173"
			;[Block]
			PositionEntity Curr173\Collider,EntityX(Collider),EntityY(Collider)+0.2,EntityZ(Collider)
			ResetEntity Curr173\Collider
			;[End Block]
		Case "spawnparticles"
			;[Block]
			If Instr(cinput, " ")<>0 Then
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Else
				StrTemp$ = ""
			EndIf
			
			If Int(StrTemp) > -1 And Int(StrTemp) <= 1 ;<--- This is the maximum ID of particles by Devil Particle system, will be increased after time - ENDSHN
				SetEmitter(Collider,ParticleEffect[Int(StrTemp)])
				CreateConsoleMsg("Spawned particle emitter with ID "+Int(StrTemp)+" at player's position.")
			Else
				CreateConsoleMsg("Particle emitter with ID "+Int(StrTemp)+" not found.",255,150,0)
			EndIf
			;[End Block]
		Case "giveachievement"
			;[Block]
			If Instr(cinput, " ")<>0 Then
				StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			Else
				StrTemp$ = ""
			EndIf
			
			If Int(StrTemp)>=0 And Int(StrTemp)<MAXACHIEVEMENTS
				Achievements(Int(StrTemp))=True
				CreateConsoleMsg("Achievemt "+AchievementStrings(Int(StrTemp))+" unlocked.")
			Else
				CreateConsoleMsg("Achievement with ID "+Int(StrTemp)+" doesn't exist.",255,150,0)
			EndIf
			;[End Block]
		Case "427state"
			;[Block]
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			I_427\Timer = Float(StrTemp)*70.0
			;[End Block]
		Case "teleport106"
			;[Block]
			Curr106\State = 0
			Curr106\Idle = False
			;[End Block]
		Case "setblinkeffect"
			;[Block]
			args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			BlinkEffect = Float(Left(args, Len(args) - Instr(args, " ")))
			BlinkEffectTimer = Float(Right(args, Len(args) - Instr(args, " ")))
			CreateConsoleMsg("Set BlinkEffect to: " + BlinkEffect + "and BlinkEffect timer: " + BlinkEffectTimer)
			;[End Block]
		Case "jorge"
			;[Block]	
			CreateConsoleMsg(Chr(74)+Chr(79)+Chr(82)+Chr(71)+Chr(69)+Chr(32)+Chr(72)+Chr(65)+Chr(83)+Chr(32)+Chr(66)+Chr(69)+Chr(69)+Chr(78)+Chr(32)+Chr(69)+Chr(88)+Chr(80)+Chr(69)+Chr(67)+Chr(84)+Chr(73)+Chr(78)+Chr(71)+Chr(32)+Chr(89)+Chr(79)+Chr(85)+Chr(46))
;					Return
;					ConsoleFlush = True 
;					
;					If ConsoleFlushSnd = 0 Then
;						ConsoleFlushSnd = LoadSound(Chr(83)+Chr(70)+Chr(88)+Chr(92)+Chr(83)+Chr(67)+Chr(80)+Chr(92)+Chr(57)+Chr(55)+Chr(48)+Chr(92)+Chr(116)+Chr(104)+Chr(117)+Chr(109)+Chr(98)+Chr(115)+Chr(46)+Chr(100)+Chr(98))
;						;FMOD_Pause(MusicCHN)
;						;FSOUND_Stream_Stop()
;						ConsoleMusFlush% = LoadSound(Chr(83)+Chr(70)+Chr(88)+Chr(92)+Chr(77)+Chr(117)+Chr(115)+Chr(105)+Chr(99)+Chr(92)+Chr(116)+Chr(104)+Chr(117)+Chr(109)+Chr(98)+Chr(115)+Chr(46)+Chr(100)+Chr(98))
;						ConsoleMusPlay = PlaySound(ConsoleMusFlush)
;					Else
;						CreateConsoleMsg(Chr(74)+Chr(32)+Chr(79)+Chr(32)+Chr(82)+Chr(32)+Chr(71)+Chr(32)+Chr(69)+Chr(32)+Chr(32)+Chr(67)+Chr(32)+Chr(65)+Chr(32)+Chr(78)+Chr(32)+Chr(78)+Chr(32)+Chr(79)+Chr(32)+Chr(84)+Chr(32)+Chr(32)+Chr(66)+Chr(32)+Chr(69)+Chr(32)+Chr(32)+Chr(67)+Chr(32)+Chr(79)+Chr(32)+Chr(78)+Chr(32)+Chr(84)+Chr(32)+Chr(65)+Chr(32)+Chr(73)+Chr(32)+Chr(78)+Chr(32)+Chr(69)+Chr(32)+Chr(68)+Chr(46))
;					EndIf
			;[End Block]
		Default
			;[Block]
			If ErrorMessage Then CreateConsoleMsg("Command not found.",255,0,0)
			Return
			
			;[End Block]
	End Select
	
	UsedConsole = True
End Function

;-----------------------------------------  Images ----------------------------------------------------------

Global PauseMenuIMG%

Global SprintIcon%
Global BlinkIcon%
Global CrouchIcon%
Global HandIcon%
Global HandIcon2%

Global StaminaMeterIMG%

Global KeypadHUD

Global Panel294, Using294%, Input294$

DrawLoading(35, True)

;----------------------------------------------  Items  -----------------------------------------------------

Include "SourceCode\Items.bb"

;--------------------------------------- Particles ------------------------------------------------------------

Include "SourceCode\Particles.bb"

;-------------------------------------  Doors --------------------------------------------------------------

Global ClosestButton%, ClosestDoor.Doors
Global SelectedDoor.Doors, UpdateDoorsTimer#
Global DoorTempID%
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
	Field liftchn, liftstate#
	Field mpx#,mpy#,mpz#
	Field hided%, BlockToShow%
	Field IsAdj%
	Field TeleportedObject%
	Field liftparent.Doors, liftid
	Field ChangedCode%
End Type 
Global multiplayer_Door.Doors[MAX_DOORS]
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
		;entityType d\obj2, HIT_DOOR
	End If
	
	;scaleentity(d\obj, 0.1, 0.1, 0.1)
	PositionEntity d\frameobj, x, y, z	
	ScaleEntity(d\frameobj, (8.0 / 2048.0), (8.0 / 2048.0), (8.0 / 2048.0))
	EntityPickMode d\frameobj,2
	EntityType d\obj, HIT_DOOR
	EntityType d\obj2, HIT_DOOR
	
	d\ID = FindFreeDoorID()
	;DoorTempID = DoorTempID+1
	multiplayer_Door[d\ID] = d
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
	d\mpx = x
	d\mpy = y
	d\mpz = z
	
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
	
	If AllowRoomsDoorsInit Then
		Local RoomDoorLine.RoomsDoorsLine = New RoomsDoorsLine
		RoomDoorLine\Door = d
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
Function ShowRoomDoorOriginal(d.Doors)
	If d\obj <> 0 Then 
		ShowEntity d\obj
		EntityAlpha d\obj, 1.0
	EndIf
	If d\frameobj <> 0 Then 
		ShowEntity d\frameobj
		EntityAlpha d\frameobj, 1.0
	EndIf
	If d\obj2 <> 0 Then 
		ShowEntity d\obj2
		EntityAlpha d\obj2, 1.0
	EndIf
	If d\buttons[0] <> 0 Then 
		ShowEntity d\buttons[0]
		EntityAlpha d\buttons[0], 1.0
	EndIf
	If d\buttons[1] <> 0 Then 
		ShowEntity d\buttons[1]
		EntityAlpha d\buttons[1], 1.0
	EndIf
End Function
Function HideRoomDoorOriginal(d.Doors)
	If d\obj <> 0 Then 
		HideEntity d\obj
		EntityAlpha d\obj, 0.0
	EndIf
	If d\frameobj <> 0 Then 
		HideEntity d\frameobj
		EntityAlpha d\frameobj, 0.0
	EndIf
	If d\obj2 <> 0 Then 
		HideEntity d\obj2
		EntityAlpha d\obj2, 0.0
	EndIf
	If d\buttons[0] <> 0 Then 
		HideEntity d\buttons[0]
		EntityAlpha d\buttons[0], 0.0
	EndIf
	If d\buttons[1] <> 0 Then 
		HideEntity d\buttons[1]
		EntityAlpha d\buttons[1], 0.0
	EndIf
End Function
Function UpdateDoors()
	
	Local i%, d.Doors, x#, z#, dist#
	ClosestButton = 0
	ClosestDoor = Null

	If NetworkServer\MainPlayer Then
		If UpdateDoorsTimer <= 0 Then
			For d.Doors = Each Doors
				For pl.players = Each players
					xdist# = Abs(EntityX(pl\pivot)-EntityX(d\obj,True))
					zdist# = Abs(EntityZ(pl\pivot)-EntityZ(d\obj,True))
					
					d\dist = xdist+zdist
					If d\dist < HideDistance*2 Then
						If d\obj <> 0 Then ShowEntity d\obj
						If d\frameobj <> 0 Then ShowEntity d\frameobj
						If d\obj2 <> 0 Then ShowEntity d\obj2
						If d\buttons[0] <> 0 Then ShowEntity d\buttons[0]
						If d\buttons[1] <> 0 Then ShowEntity d\buttons[1]
						Exit
					Else
						If d\obj <> 0 Then HideEntity d\obj
						If d\frameobj <> 0 Then HideEntity d\frameobj
						If d\obj2 <> 0 Then HideEntity d\obj2
						If d\buttons[0] <> 0 Then HideEntity d\buttons[0]
						If d\buttons[1] <> 0 Then HideEntity d\buttons[1]
					EndIf
				Next
			Next
			UpdateDoorsTimer = 30
		Else
			UpdateDoorsTimer = Max(UpdateDoorsTimer-FPSfactor,0)
		EndIf
	Else
		If UpdateDoorsTimer <= 0 Then
			For d.Doors = Each Doors
				xdist# = Abs(EntityX(Collider)-EntityX(d\obj,True))
				zdist# = Abs(EntityZ(Collider)-EntityZ(d\obj,True))
				
				d\dist = xdist+zdist
				If d\dist < HideDistance*2 Then
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
			Next
			UpdateDoorsTimer = 30
		Else
			UpdateDoorsTimer = Max(UpdateDoorsTimer-FPSfactor,0)
		EndIf
	EndIf
	
	For d.Doors = Each Doors
		If d\dist < HideDistance*2 Or d\IsElevatorDoor>0 Then ;Make elevator doors update everytime because if not, this can cause a bug where the elevators suddenly won't work, most noticeable in room2tunnel - ENDSHN
			If (d\openstate >= 180 Or d\openstate <= 0) And GrabbedEntity = 0 And GetMilliSecs(0) Then
				For i% = 0 To 1
					If d\buttons[i] <> 0 Then
						If Abs(EntityX(Collider)-EntityX(d\buttons[i],True)) < 1.0 Then 
							If Abs(EntityZ(Collider)-EntityZ(d\buttons[i],True)) < 1.0 Then 
								dist# = Distance(EntityX(Collider, True), EntityZ(Collider, True), EntityX(d\buttons[i], True), EntityZ(d\buttons[i], True));entityDistance(collider, d\buttons[i])
								If dist < 0.7 Then
									EntityPickMode d\buttons[i], 2
									Local temp% = CreatePivot()
									PositionEntity temp, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
									PointEntity temp,d\buttons[i]
									
									If EntityPick(temp, 0.6) = d\buttons[i] Then
										If ClosestButton = 0 Then
											ClosestButton = d\buttons[i]
											ClosestDoor = d
										Else
											If dist < EntityDistance(Collider, ClosestButton) Then ClosestButton = d\buttons[i] : ClosestDoor = d
										End If							
									End If
									EntityPickMode d\buttons[i], 0
									
									FreeEntity temp
									
								EndIf							
							EndIf
						EndIf
						
					EndIf
				Next
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
							If d\open Then
								If (Not Wearing714) Then 
									PlaySound_Strict HorrorSFX(7)
									multiplayer_WriteSound HorrorSFX(7)
								EndIf
								UseDoor(d,False)
								d\AutoClose = False
							EndIf
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
								If ParticleAmount=2
									For i = 0 To Rand(75,99)
										Local pvt% = CreatePivot()
										PositionEntity(pvt, EntityX(d\frameobj,True)+Rnd(-0.2,0.2), EntityY(d\frameobj,True)+Rnd(0.0,1.2), EntityZ(d\frameobj,True)+Rnd(-0.2,0.2))
										RotateEntity(pvt, 0, Rnd(360), 0)
										
										Local p.Particles = CreateParticle(EntityX(pvt), EntityY(pvt), EntityZ(pvt), 2, 0.002, 0, 300)
										p\speed = 0.005
										RotateEntity(p\pvt, Rnd(-20, 20), Rnd(360), 0)
										
										p\SizeChange = -0.00001
										p\size = 0.01
										ScaleSprite p\obj,p\size,p\size
										
										p\Achange = -0.01
										
										EntityOrder p\obj,-1
										
										FreeEntity pvt
									Next
								EndIf
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
			;if MyPlayer\BreachType = MODEL_106 And d\DoorHitOBJ <> 0 Then ResetEntity d\DoorHitOBJ
		EndIf
		UpdateSoundOrigin(d\SoundCHN,Camera,d\frameobj)
		
		If d\DoorHitOBJ<>0 Then
			If DebugHUD Then
				EntityAlpha d\DoorHitOBJ,0.5
			Else
				EntityAlpha d\DoorHitOBJ,0.0
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
	Next
End Function

Function UseDoor(d.Doors, showmsg%=True, playsfx%=True, give = True, code$ = "", forceopen%=False)
	Local temp% = 0

	If multiplayer_IsFullSync() Then
		If give = True And udp_GetStream() Then
			multiplayer_SendDoor(d.Doors, SelectedItem, code)
		EndIf
	EndIf
	
	If Not forceopen Then
		If d\KeyCard > 0 Then
			If SelectedItem = Null And give = True Then
				If showmsg = True Then
					If (Instr(Msg,"The keycard")=0 And Instr(Msg,"A keycard with")=0) Or (MsgTimer<70*3) Then
						Msg = "A keycard is required to operate this door."
						MsgTimer = 70 * 7
					EndIf
				EndIf
				Return
			Else
				If give = True Then
					Select SelectedItem\itemtemplate\tempname
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
				Else
					temp = 1000
				EndIf
				SelectedItem = Null
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
							If give = True Then PlaySound_Strict KeyCardSFX2
							Msg = "The keycard was inserted into the slot but nothing happened."
							MsgTimer = 70 * 7
							If give = True Then multiplayer_WriteSound(KeyCardSFX2, EntityX(Collider), EntityY(Collider), EntityZ(Collider))
							Return
						Else
							If give = True Then PlaySound_Strict KeyCardSFX1
							Msg = "The keycard was inserted into the slot."
							MsgTimer = 70 * 7	
							If give = True Then multiplayer_WriteSound(KeyCardSFX1, EntityX(Collider), EntityY(Collider), EntityZ(Collider))
						EndIf
					EndIf
				Else
					If showmsg = True Then 
						If give = True Then PlaySound_Strict KeyCardSFX2					
						If d\locked Then
							Msg = "The keycard was inserted into the slot but nothing happened."
						Else
							Msg = "A keycard with security clearance "+d\KeyCard+" or higher is required to operate this door."
						EndIf
						MsgTimer = 70 * 7
						If give = True Then multiplayer_WriteSound(KeyCardSFX2, EntityX(Collider), EntityY(Collider), EntityZ(Collider))					
					EndIf
					Return
				End If
			EndIf	
		ElseIf d\KeyCard < 0
			;I can't find any way to produce short circuited boolean expressions so work around this by using a temporary variable - risingstar64
			If SelectedItem <> Null Then
				temp = (SelectedItem\itemtemplate\tempname = "hand" And d\KeyCard=-1) Or (SelectedItem\itemtemplate\tempname = "hand2" And d\KeyCard=-2)
			EndIf
			If give = False Then temp = 1
			If temp <> 0 Then
				If give = True Then PlaySound_Strict ScannerSFX1
				If give = True Then multiplayer_WriteSound(ScannerSFX1, EntityX(Collider), EntityY(Collider), EntityZ(Collider))
				If (Instr(Msg,"You placed your")=0) Or (MsgTimer < 70*3) Then
					Msg = "You place the palm of the hand onto the scanner. The scanner reads: "+Chr(34)+"DNA verified. Access granted."+Chr(34)
				EndIf
				MsgTimer = 70 * 10
				SelectedItem = Null
			Else
				If showmsg = True Then 
					If give = True Then PlaySound_Strict ScannerSFX2
					Msg = "You placed your palm onto the scanner. The scanner reads: "+Chr(34)+"DNA does not match known sample. Access denied."+Chr(34)
					MsgTimer = 70 * 10
					If give = True Then multiplayer_WriteSound(ScannerSFX2, EntityX(Collider), EntityY(Collider), EntityZ(Collider))
				EndIf
				Return	
			EndIf
		Else
			If d\locked Then
				If showmsg = True Then 
					If Not (d\IsElevatorDoor>0) Then
						If give = True Then
							If give = True Then PlaySound_Strict ButtonSFX2
							If give = True Then multiplayer_WriteSound(ButtonSFX2)
						EndIf
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
							If (Msg="You already called the elevator.") Or (MsgTimer<70*3) Or Msg = "" Then	
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
			d\SoundCHN = PlaySound2 (OpenDoorSFX(d\dir, sound), Camera, d\obj)
		Else
			d\SoundCHN = PlaySound2 (CloseDoorSFX(d\dir, sound), Camera, d\obj)
		EndIf
		UpdateSoundOrigin(d\SoundCHN,Camera,d\obj)
	Else
		If d\open Then
			If d\LinkedDoor <> Null Then d\LinkedDoor\timerstate = d\LinkedDoor\timer
			d\timerstate = d\timer
		EndIf
	EndIf
	
	If d\liftparent <> Null Then
		d\TeleportedObject = True
		d\liftparent\TeleportedObject = True
	EndIf
	
	If Not multiplayer_IsFullSync() Then
		If give = True And udp_GetStream() Then
			multiplayer_SendDoor(d.Doors, SelectedItem, code)
		EndIf
	EndIf
	Return True
End Function
Function RemoveDoor(d.Doors)
	If d\buttons[0] <> 0 Then EntityParent d\buttons[0], 0
	If d\buttons[1] <> 0 Then EntityParent d\buttons[1], 0	
	
	If d\obj <> 0 Then FreeEntity d\obj
	If d\obj2 <> 0 Then FreeEntity d\obj2
	If d\frameobj <> 0 Then FreeEntity d\frameobj
	If d\buttons[0] <> 0 Then FreeEntity d\buttons[0]
	If d\buttons[1] <> 0 Then FreeEntity d\buttons[1]
	multiplayer_Door[d\ID] = Null
	Delete d
End Function

DrawLoading(40,True)

Include "SourceCode\MapSystem.bb"

InitMultiplayer(-1, False)

DrawLoading(80,True)

Include "SourceCode\NPCs.bb"

;-------------------------------------  Events --------------------------------------------------------------

Type Events
	Field EventName$

	Field room.Rooms
	
	Field EventState#, EventState2#, EventState3#
	Field SoundCHN%, SoundCHN2%
	Field Sound, Sound2
	Field SoundCHN_isStream%, SoundCHN2_isStream%
	
	Field EventStr$
	
	Field img%
	Field NearObject,NearObjectQueue
	Field ID
	Field Players
	Field prob#
	Field probs#
	Field ide
	Field dist#, Tick
	Field EventConst
	Field Received
	Field StaticEvent ; if in event npc
	Field de.Decals
End Type
Global event173.Events
Global GateAEvent.Events
Global breachEvent.Events
Global exit1event.Events
Global Dimension1499.Events
Global room3storageevent.Events
Global room860event.Events
Global pocketdimension106.Events
Global LastEventID
Global M_Event.Events[MAX_EVENTS]

Const e_173 = 0
Const e_alarm = 1
Const e_pocketdimension = 2
Const e_tunnel106 = 3
Const e_lockroom173 = 4
Const e_room2trick = 5
Const e_1048a = 6
Const e_room2storage = 7
Const e_lockroom096 = 8
Const e_endroom106 = 9
Const e_room2poffices2 = 10
Const e_room2fan = 11
Const e_room2elevator2 = 12
Const e_room2elevator = 13
Const e_room3storage = 14
Const e_tunnel2smoke = 15
Const e_tunnel2 = 16
Const e_room2doors173 = 17
Const e_room2offices2 = 18
Const e_room2closets = 19
Const e_room2cafeteria = 20
Const e_room3pitduck = 21
Const e_room3pit1048 = 22
Const e_room2offices3 = 23
Const e_room2servers = 24
Const e_room3servers = 25
Const e_room3tunnel = 26
Const e_room4 = 27
Const e_682roar = 28
Const e_testroom173 = 29
Const e_room2tesla = 30
Const e_room2nuke = 31
Const e_coffin106 = 32
Const e_coffin = 33
Const e_checkpoint = 34
Const e_room3door = 35
Const e_106victim = 36
Const e_106sinkhole = 37
Const e_room079 = 38
Const e_room049 = 39
Const e_room012 = 40
Const e_room035 = 41
Const e_008 = 42
Const e_room106 = 43
Const e_pj = 44
Const e_914 = 45
Const e_buttghost = 46
Const e_toiletguard = 47
Const e_room2pipes106 = 48
Const e_room2pit = 49
Const e_testroom = 50
Const e_room2tunnel = 51
Const e_room2ccont = 52
Const e_gateaentrance = 53
Const e_gatea = 54
Const e_exit1 = 55
Const e_room205 = 56
Const e_room860 = 57
Const e_room966 = 58
Const e_room1123 = 59
Const e_room4tunnels = 60
Const e_room_gw = 61
Const e_dimension1499 = 62
Const e_room1162 = 63
Const e_room2scps2 = 64
Const e_room2sl = 65
Const e_medibay = 66
Const e_room2shaft = 67
Const e_room1lifts = 68
Const e_room2gw_b = 69
Const e_096spawn = 70
Const e_room2offices035 = 71
Const e_room2pit106 = 72
Const e_room1archive = 73
Const e_breach = 74

Function CreateEvent.Events(eventname$, roomname$, id%, prob# = 0.0)
	;roomname = the name of the room(s) you want the event to be assigned to
	
	;the id-variable determines which of the rooms the event is assigned to,
	;0 will assign it to the first generated room, 1 to the second, etc
	
	;the prob-variable can be used to randomly assign events into some rooms
	;0.5 means that there's a 50% chance that event is assigned to the rooms
	;1.0 means that the event is assigned to every room
	;the id-variable is ignored if prob <> 0.0

	Local i% = 0, temp%, e.Events, e2.Events, r.Rooms
	If prob = 0.0 Then
		For r.Rooms = Each Rooms
			If (roomname = "" Or roomname = r\RoomTemplate\Name) Then
				temp = False
				For e2.Events = Each Events
					If e2\room = r Then temp = True : Exit
				Next
				
				i=i+1
				If i >= id And temp = False Then
					e.Events = New Events
					e\EventName = eventname
					SetEventVar(e)
					e\room = r
					e\EventConst = FindEventConst(eventname)
					Return e
				End If
			EndIf
		Next
	Else
		For r.Rooms = Each Rooms
			If (roomname = "" Or roomname = r\RoomTemplate\Name) Then
				temp = False
				For e2.Events = Each Events
					If e2\room = r Then temp = True : Exit
				Next
				If Rnd(0.0, 1.0) < prob And temp = False Then
					e.Events = New Events
					e\EventName = eventname
					SetEventVar(e)
					e\EventConst = FindEventConst(eventname)
					e\room = r
				End If
			EndIf
		Next		
	EndIf
	
	Return Null
End Function
Function SetEventVar(e.Events)
	Select e\EventName
		Case "exit1": exit1event = e
		Case "gatea": GateAEvent = e
		Case "173": event173 = e
		Case "dimension1499": Dimension1499 = e
		Case "room3storage": room3storageevent = e
		Case "room860": room860event = e
		Case "breach": breachEvent = e
		Case "pocketdimension": pocketdimension106 = e
	End Select
	SetEventID(e, FindFreeEventID())
End Function

Function SetEventVarEx(e.Events, withoutFind = False)
	Select e\EventName
		Case "exit1": exit1event = e
		Case "gatea": GateAEvent = e
		Case "173": event173 = e
		Case "dimension1499": Dimension1499 = e
		Case "room3storage": room3storageevent = e
		Case "room860": room860event = e
		Case "breach": breachEvent = e
		Case "pocketdimension": pocketdimension106 = e
	End Select
	If Not withoutFind Then SetEventID(e, FindFreeEventID())
End Function

Function FindEventConst(eventname$)
	;-example
	;Case "EVENTNAME": Return EVENT CONST (example e_173)
	;-
	Select eventname
		Case "173" Return 0
		Case "alarm" Return 1
		Case "pocketdimension" Return 2
		Case "tunnel106" Return 3
		Case "lockroom173" Return 4
		Case "room2trick" Return 5
		Case "1048a" Return 6
		Case "room2storage" Return 7
		Case "lockroom096" Return 8
		Case "endroom106" Return 9
		Case "room2poffices2" Return 10
		Case "room2fan" Return 11
		Case "room2elevator2" Return 12
		Case "room2elevator" Return 13
		Case "room3storage" Return 14
		Case "tunnel2smoke" Return 15
		Case "tunnel2" Return 16
		Case "room2doors173" Return 17
		Case "room2offices2" Return 18
		Case "room2closets" Return 19
		Case "room2cafeteria" Return 20
		Case "room3pitduck" Return 21
		Case "room3pit1048" Return 22
		Case "room2offices3" Return 23
		Case "room2servers" Return 24
		Case "room3servers" Return 25
		Case "room3tunnel" Return 26
		Case "room4" Return 27
		Case "682roar" Return 28
		Case "testroom173" Return 29
		Case "room2tesla" Return 30
		Case "room2nuke" Return 31
		Case "coffin106" Return 32
		Case "coffin" Return 33
		Case "checkpoint" Return 34
		Case "room3door" Return 35
		Case "106victim" Return 36
		Case "106sinkhole" Return 37
		Case "room079" Return 38
		Case "room049" Return 39
		Case "room012" Return 40
		Case "room035" Return 41
		Case "008" Return 42
		Case "room106" Return 43
		Case "pj" Return 44
		Case "914" Return 45
		Case "buttghost" Return 46
		Case "toiletguard" Return 47
		Case "room2pipes106" Return 48
		Case "room2pit" Return 49
		Case "testroom" Return 50
		Case "room2tunnel" Return 51
		Case "room2ccont" Return 52
		Case "gateaentrance" Return 53
		Case "gatea" Return 54
		Case "exit1" Return 55
		Case "room205" Return 56
		Case "room860" Return 57
		Case "room966" Return 58
		Case "room1123" Return 59
		Case "room4tunnels" Return 60
		Case "room_gw" Return 61
		Case "dimension1499" Return 62
		Case "room1162" Return 63
		Case "room2scps2" Return 64
		Case "room2sl" Return 65
		Case "medibay" Return 66
		Case "room2shaft" Return 67
		Case "room1lifts" Return 68
		Case "room2gw_b" Return 69
		Case "096spawn" Return 70
		Case "room2offices035" Return 71
		Case "room2pit106" Return 72
		Case "room1archive" Return 73
		Case "breach" Return 74
	End Select
	Return 0
End Function

Function FindEventNameConst$(eventconst)
	;-example
	;Case "EVENTNAME": Return EVENT CONST (example e_173)
	;-
	Select eventname
		Case e_173 Return "173"
		Case e_alarm Return "alarm"
		Case e_pocketdimension Return "pocketdimension"
		Case e_tunnel106 Return "tunnel106"
		Case e_lockroom173 Return "lockroom173"
		Case e_room2trick Return "room2trick"
		Case e_1048a Return "1048a"
		Case e_room2storage Return "room2storage"
		Case e_lockroom096 Return "lockroom096"
		Case e_endroom106 Return "endroom106"
		Case e_room2poffices2 Return "room2poffices2"
		Case e_room2fan Return "room2fan"
		Case e_room2elevator2 Return "room2elevator2"
		Case e_room2elevator Return "room2elevator"
		Case e_room3storage Return "room3storage"
		Case e_tunnel2smoke Return "tunnel2smoke"
		Case e_tunnel2 Return "tunnel2"
		Case e_room2doors173 Return "room2doors173"
		Case e_room2offices2 Return "room2offices2"
		Case e_room2closets Return "room2closets"
		Case e_room2cafeteria Return "room2cafeteria"
		Case e_room3pitduck Return "room3pitduck"
		Case e_room3pit1048 Return "room3pit1048"
		Case e_room2offices3 Return "room2offices3"
		Case e_room2servers Return "room2servers"
		Case e_room3servers Return "room3servers"
		Case e_room3tunnel Return "room3tunnel"
		Case e_room4 Return "room4"
		Case e_682roar Return "682roar"
		Case e_testroom173 Return "testroom173"
		Case e_room2tesla Return "room2tesla"
		Case e_room2nuke Return "room2nuke"
		Case e_coffin106 Return "coffin106"
		Case e_coffin Return "coffin"
		Case e_checkpoint Return "checkpoint"
		Case e_room3door Return "room3door"
		Case e_106victim Return "106victim"
		Case e_106sinkhole Return "106sinkhole"
		Case e_room079 Return "room079"
		Case e_room049 Return "room049"
		Case e_room012 Return "room012"
		Case e_room035 Return "room035"
		Case e_008 Return "008"
		Case e_room106 Return "room106"
		Case e_pj Return "pj"
		Case e_914 Return "914"
		Case e_buttghost Return "buttghost"
		Case e_toiletguard Return "toiletguard"
		Case e_room2pipes106 Return "room2pipes106"
		Case e_room2pit Return "room2pit"
		Case e_testroom Return "testroom"
		Case e_room2tunnel Return "room2tunnel"
		Case e_room2ccont Return "room2ccont"
		Case e_gateaentrance Return "gateaentrance"
		Case e_gatea Return "gatea"
		Case e_exit1 Return "exit1"
		Case e_room205 Return "room205"
		Case e_room860 Return "room860"
		Case e_room966 Return "room966"
		Case e_room1123 Return "room1123"
		Case e_room4tunnels Return "room4tunnels"
		Case e_room_gw Return "room_gw"
		Case e_dimension1499 Return "dimension1499"
		Case e_room1162 Return "room1162"
		Case e_room2scps2 Return "room2scps2"
		Case e_room2sl Return "room2sl"
		Case e_medibay Return "medibay"
		Case e_room2shaft Return "room2shaft"
		Case e_room1lifts Return "room1lifts"
		Case e_room2gw_b Return "room2gw_b"
		Case e_096spawn Return "096spawn"
		Case e_room2offices035 Return "room2offices035"
		Case e_room2pit106 Return "room2pit106"
		Case e_room1archive Return "room1archive"
		Case e_breach Return "breach"
	End Select
	Return ""
End Function

Function InitEvents()
	Local e.Events
	If IntroEnabled And (Not NetworkServer\Breach) Then CreateEvent("173", "173", 0)
	If (Not NetworkServer\Breach) Then CreateEvent("alarm", "start", 0)
	
	CreateEvent("pocketdimension", "pocketdimension", 0)	
	
	;there's a 7% chance that 106 appears in the rooms named "tunnel"
	CreateEvent("tunnel106", "tunnel", 0, 0.07 + (0.1*SelectedDifficulty\aggressiveNPCs))
	
	;the chance for 173 appearing in the first lockroom is about 66%
	;there's a 30% chance that it appears in the later lockrooms
	If Rand(3)<3 Then CreateEvent("lockroom173", "lockroom", 0)
	CreateEvent("lockroom173", "lockroom", 0, 0.3 + (0.5*SelectedDifficulty\aggressiveNPCs))
	
	CreateEvent("room2trick", "room2", 0, 0.15)	
	
	CreateEvent("1048a", "room2", 0, 1.0)	
	
	CreateEvent("room2storage", "room2storage", 0)	
	
	;096 spawns in the first (and last) lockroom2
	CreateEvent("lockroom096", "lockroom2", 0)
	
	CreateEvent("endroom106", "endroom", Rand(0,1))
	
	CreateEvent("room2poffices2", "room2poffices2", 0)
	
	CreateEvent("room2fan", "room2_2", 0, 1.0)
	
	CreateEvent("room2elevator2", "room2elevator", 0)
	CreateEvent("room2elevator", "room2elevator", Rand(1,2))
	
	CreateEvent("room3storage", "room3storage", 0, 0)
	
	CreateEvent("tunnel2smoke", "tunnel2", 0, 0.2)
	CreateEvent("tunnel2", "tunnel2", Rand(0,2), 0)
	CreateEvent("tunnel2", "tunnel2", 0, (0.2*SelectedDifficulty\aggressiveNPCs))
	
	;173 appears in half of the "room2doors" -rooms
	CreateEvent("room2doors173", "room2doors", 0, 0.5 + (0.4*SelectedDifficulty\aggressiveNPCs))
	
	;the anomalous duck in room2offices2-rooms
	CreateEvent("room2offices2", "room2offices2", 0, 0.7)
	
	CreateEvent("room2closets", "room2closets", 0)	
	
	CreateEvent("room2cafeteria", "room2cafeteria", 0)	
	
	CreateEvent("room3pitduck", "room3pit", 0)
	CreateEvent("room3pit1048", "room3pit", 1)
	
	;the event that causes the door to open by itself in room2offices3
	CreateEvent("room2offices3", "room2offices3", 0, 1.0)	
	
	CreateEvent("room2servers", "room2servers", 0)	
	
	CreateEvent("room3servers", "room3servers", 0)	
	CreateEvent("room3servers", "room3servers2", 0)
	
	;the dead guard
	CreateEvent("room3tunnel","room3tunnel", 0, 0.08)
	
	CreateEvent("room4","room4", 0)
	
	If Rand(5)<5 Then 
		Select Rand(3)
			Case 1
				CreateEvent("682roar", "tunnel", Rand(0,2), 0)	
			Case 2
				CreateEvent("682roar", "room3pit", Rand(0,2), 0)		
			Case 3
				;CreateEvent("682roar", "room2offices", 0, 0)
				CreateEvent("682roar", "room2z3", 0, 0)
		End Select 
	EndIf 
	
	CreateEvent("testroom173", "room2testroom2", 0, 1.0)	
	
	CreateEvent("room2tesla", "room2tesla", 0, 0.9)
	
	CreateEvent("room2nuke", "room2nuke", 0, 0)
	
	If Rand(5) < 5 Then 
		CreateEvent("coffin106", "coffin", 0, 0)
	Else
		CreateEvent("coffin", "coffin", 0, 0)
	EndIf 
	
	CreateEvent("checkpoint", "checkpoint1", 0, 1.0)
	CreateEvent("checkpoint", "checkpoint2", 0, 1.0)
	
	CreateEvent("room3door", "room3", 0, 0.1)
	CreateEvent("room3door", "room3tunnel", 0, 0.1)	
	
	If Rand(2)=1 Then
		CreateEvent("106victim", "room3", Rand(1,2))
		CreateEvent("106sinkhole", "room3_2", Rand(2,3))
	Else
		CreateEvent("106victim", "room3_2", Rand(1,2))
		CreateEvent("106sinkhole", "room3", Rand(2,3))
	EndIf
	CreateEvent("106sinkhole", "room4", Rand(1,2))
	
	CreateEvent("room079", "room079", 0, 0)	
	
	CreateEvent("room049", "room049", 0, 0)
	
	CreateEvent("room012", "room012", 0, 0)
	
	CreateEvent("room035", "room035", 0, 0)
	
	CreateEvent("008", "008", 0, 0)
	
	CreateEvent("room106", "room106", 0, 0)	
	
	CreateEvent("pj", "roompj", 0, 0)
	
	CreateEvent("914", "914", 0, 0)
	
	CreateEvent("buttghost", "room2toilets", 0, 0)
	CreateEvent("toiletguard", "room2toilets", 1, 0)
	
	CreateEvent("room2pipes106", "room2pipes", Rand(0, 3)) 
	
	CreateEvent("room2pit", "room2pit", 0, 0.4 + (0.4*SelectedDifficulty\aggressiveNPCs))
	
	CreateEvent("testroom", "testroom", 0)
	
	CreateEvent("room2tunnel", "room2tunnel", 0)
	
	CreateEvent("room2ccont", "room2ccont", 0)
	
	CreateEvent("gateaentrance", "gateaentrance", 0)
	CreateEvent("gatea", "gatea", 0)	
	CreateEvent("exit1", "exit1", 0)
	
	CreateEvent("room205", "room205", 0)
	
	CreateEvent("room860","room860", 0)
	
	CreateEvent("room966","room966", 0)
	
	CreateEvent("room1123", "room1123", 0, 0)
	;CreateEvent("room2test1074","room2test1074",0)
	;CreateEvent("room038","room038",0,0)
	;CreateEvent("room009","room009",0,0)
	;CreateEvent("medibay", "medibay", 0)
	;CreateEvent("room409", "room409", 0)
	;CreateEvent("room178", "room178", 0)
	;CreateEvent("room020", "room020", 0)
	CreateEvent("room2tesla", "room2tesla_lcz", 0, 0.9)
	CreateEvent("room2tesla", "room2tesla_hcz", 0, 0.9)
	
	;New Events in SCP:CB Version 1.3 - ENDSHN
	CreateEvent("room4tunnels","room4tunnels",0)
	CreateEvent("room_gw","room2gw",0,1.0)
	CreateEvent("dimension1499","dimension1499",0)
	CreateEvent("room1162","room1162",0)
	CreateEvent("room2scps2","room2scps2",0)
	CreateEvent("room_gw","room3gw",0,1.0)
	CreateEvent("room2sl","room2sl",0)
	CreateEvent("medibay","medibay",0)
	CreateEvent("room2shaft","room2shaft",0)
	CreateEvent("room1lifts","room1lifts",0)
	
	CreateEvent("room2gw_b","room2gw_b",Rand(0,1))
	
	CreateEvent("096spawn","room4pit",0,0.6+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","room3pit",0,0.6+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","room2pipes",0,0.4+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","room2pit",0,0.5+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","room3tunnel",0,0.6+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","room4tunnels",0,0.7+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","tunnel",0,0.6+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","tunnel2",0,0.4+(0.2*SelectedDifficulty\aggressiveNPCs))
	CreateEvent("096spawn","room3z2",0,0.7+(0.2*SelectedDifficulty\aggressiveNPCs))
	
	CreateEvent("room2pit","room2_4",0,0.4 + (0.4*SelectedDifficulty\aggressiveNPCs))
	
	CreateEvent("room2offices035","room2offices",0)
	
	CreateEvent("room2pit106", "room2pit", 0, 0.07 + (0.1*SelectedDifficulty\aggressiveNPCs))
	
	CreateEvent("room1archive", "room1archive", 0, 1.0)
	;CreateEvent("alarm", "start", 0)

	If NetworkServer\Breach = True Then
		e.Events = New Events
		e\EventName = "breach"
		SetEventVar(e)
		e\EventConst = e_breach
	EndIf
	For e.Events = Each Events
		e\StaticEvent = True
	Next
End Function

Include "SourceCode\UpdateEvents.bb"
Function SetEventID(e.Events, ID)
	M_Event[e\ID] = Null
	e\ID = ID
	M_Event[e\ID] = e
End Function
Function RemoveEvent(e.Events)
	If e = Null Then Return
	If e\Sound<>0 Then FreeSound_Strict e\Sound
	If e\Sound2<>0 Then FreeSound_Strict e\Sound2
	If e\img<>0 Then FreeImage e\img
	DebugLog "Removed "+e\EventName
	Delete e
End Function

Collisions HIT_PLAYER, HIT_MAP, 2, 2
Collisions HIT_PLAYER, HIT_DOOR, 2, 2
Collisions HIT_PLAYER, HIT_PLAYER, 1, 3
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

DrawLoading(90, True)

;----------------------------------- meshes and textures ----------------------------------------------------------------

Global FogTexture%, Fog%
Global GasMaskTexture%, GasMaskOverlay%
Global InfectTexture%, InfectOverlay%
Global DarkTexture%, Dark%
Global Collider%, Head%, MyHitbox%, ViewmodelPivot%
Global CollidedFloor%

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

;---------------------------------------------------------------------------------------------------

Include "SourceCode\menu.bb"
MainMenuOpen = True

;---------------------------------------------------------------------------------------------------
FlushKeys()
FlushMouse()

DrawLoading(100, True)
LoopDelay = MilliSecs()

Global UpdateParticles_Time# = 0.0

Global CurrTrisAmount%

Global Input_ResetTime# = 0

Type SCP427
	Field Using%
	Field Timer#
	Field Sound[1]
	Field SoundCHN[1]
End Type

Global I_427.SCP427 = New SCP427

Type MapZones
	Field Transition%[1]
	Field HasCustomForest%
	Field HasCustomMT%
End Type

Global Stated% = False

Global I_Zone.MapZones = New MapZones

Global CurrentTick.ticks
Global UpdateTickTime%
Type ticks
	Field name$
	Field lastms%
	Field Spendms#
End Type

Function createtick(name$)
	Return
	If CurrentTick <> Null Then
		CurrentTick\Spendms = (Float(MicroSecs()-CurrentTick\lastms))/1000.0
		CurrentTick = Null
	EndIf
	If name = "" Then Return
	CurrentTick = New ticks
	CurrentTick\name = name
	CurrentTick\lastms = MicroSecs()
End Function

Function ResultTicks()
	Return
	If UpdateTickTime < MilliSecs() Then
		Local resultMsg$, AllMs#
		For t.Ticks = Each ticks
			resultMsg = resultMsg + t\name+" spend in "+t\spendms+" ms |"
			AllMs = AllMs + t\spendms
		Next
		DebugLog resultMsg
		DebugLog "Tick eating: "+AllMs+" milliseconds"
		UpdateTickTime = MilliSecs()+1500
	EndIf
	Delete Each ticks
End Function
Global msi, currentclick, latestpos#[3]

Function UpdateInput()
	;SetFixedTimeStepsBackup
	If Input_ResetTime<=0.0
		KeyHitE = KeyHit(KEY_USING)
		KeyDownE = KeyDown(18)
		DoubleClick = False
		MouseHit1 = (MouseHit(1)<>0)
		If MouseHit1 Then
			If MilliSecs2() - LastMouseHit1 < 300 Then DoubleClick = True
			LastMouseHit1 = MilliSecs2()
		EndIf
		
		prevmousedown1 = MouseDown1
		MouseDown1 = (MouseDown(1)<>0)
		If prevmousedown1 = True And MouseDown1=False Then MouseUp1 = True Else MouseUp1 = False
		
		MouseHit2 = (MouseHit(2)<>0)
		
		If (Not MouseDown1) And (Not MouseHit1) Then GrabbedEntity = 0
	Else
		Input_ResetTime = Max(Input_ResetTime-FPSfactor,0.0)
	EndIf

	KeyBuffer(KEY_LEFT) = KeyDown(KEY_LEFT)
	KeyBuffer(KEY_RIGHT) = KeyDown(KEY_RIGHT)
	KeyBuffer(KEY_UP) = KeyDown(KEY_UP)
	KeyBuffer(KEY_DOWN) = KeyDown(KEY_DOWN)
	KeyBuffer(KEY_SPRINT) = KeyDown(KEY_SPRINT)

	;ReturnFixedTimeSteps
End Function
;----------------------------------------------------------------------------------------------------------------------------------------------------
;----------------------------------------------       		MAIN LOOP                 ---------------------------------------------------------------
;----------------------------------------------------------------------------------------------------------------------------------------------------

For sc.Servers = Each Servers
	If sc\sType = SELECTED_SERVERS And sc\spage = SELECTED_PAGE Then multiplayer_list_UpdateServer(sc)
Next

FullscreenSetting = Fullscreen
BorderlessWindowedSetting = BorderlessWindowed
Bit16ModeSetting = Bit16Mode
LauncherEnabledSetting = LauncherEnabled
SelectedGFXDriverSetting = SelectedGFXDriver

Repeat

	If Ready = "" Then
		Ready = "Not Ready"
		ws_CheckSubscribedItems(True)
		If STEAM_RELEASE Then
			If Nickname = "" Then Nickname = BS_ISteamFriends_GetPersonaName(BS_SteamFriends())
		EndIf
	EndIf
	
	
	
	UpdateFocus()
	If KeyHit(62) Then TakeScreenshot()
	
	Cls
	UpdateMusic()
	AutoReleaseSounds()
	UpdateInput()
	
	If MainMenuOpen Then
		UpdateFrame(False)
		UpdateMainMenu()
		multiplayer_UpdateGUI()
		multiplayer_Update()
		voice_update()
		
		If STEAM_RELEASE Then
			If Nickname = "concheliga2" Then Nickname = BS_ISteamFriends_GetPersonaName(BS_SteamFriends())
		EndIf
		
	Else
		UpdateFrame(False)
		
		If (Not MenuOpen) Or udp_GetStream() Then
			
			;SetFixedTimeStepsBackup()
			UpdateStreamSounds()
			UpdateAmbient()
			UpdateEmitters()
			UpdateDeafPlayer()
			UpdateMisc()
			UpdateCamera()
			voice_update()
			UpdateSecurityCams()
			;ReturnFixedTimeSteps()
			
			.FixedTimeStepsVariables:
				Local PrevMouseHit1% = MouseHit1
				Local PrevMouseHit2% = MouseHit2
				Local PrevKeyHitE% = KeyHitE
				Local PrevSecurityCameras% = False
				
				
			ft\accumulator = GetTickDuration() ; Disable fixed timesteps
			
			While (ft\accumulator>0.0)
				ft\accumulator = ft\accumulator-GetTickDuration()
				;if ft\accumulator <= 0.0 and ((not networkserver\prediction) or (not multiplayer_IsFullSync())) Then CaptureWorld()
				
				;[Block]
				SetPlayerVariables()
				multiplayer_UpdatePlayerSync()
				
				OutSCP = multiplayer_UpdateSCPs()
				If SelectedDoor = Null Then
					If Spectate\SpectatePlayer = -1 Then
						If CanInteract() Then
							MouseLook(True)
							MovePlayer(Not OutSCP)
						Else
							MouseLook(False)
							MovePlayer(True)
							UpdateExistAnimation()
						EndIf
					EndIf
				Else
					UpdateExistAnimation()
				EndIf
				SetPlayerVariables()
				multiplayer_breach_Update()
				multiplayer_UpdatePlayers() ; remove from fixedtimesteps
				multiplayer_UpdateSpectate() ; remove from fixedtimesteps
				multiplayer_updateobjects()
				multiplayer_AnimateViewmodel()
				UpdateGuns()
				AnimateGuns()
				UpdateDeadBodies()
				UpdateExplosion()
				UpdateDoors()
				UpdateRooms()
				UpdateEvents()
				
				If PlayerRoom\RoomTemplate\Name = "dimension1499"
					UpdateDimension1499()
					UpdateLeave1499()
				Else
					UpdateScreens()
					TimeCheckpointMonitors()
					Update294()
				EndIf
				UpdateDecals()
				UpdateMTF()
				UpdateNPCs()
				UpdateRockets()
				UpdateItems()
				UpdateParticles()
				Use427()
				UpdateMonitorSaving()
				UpdateGame()
				;[End Block]
				
				multiplayer_Update()
				
				.FixedTimeStepsUse:
					MouseHit1 = False
					KeyHitE = False
					MouseHit2 = False
					PrevSecurityCameras = True
			Wend
			
			MouseHit1 = PrevMouseHit1
			KeyHitE = PrevKeyHitE
			MouseHit2 = PrevMouseHit2
			
			;SetFixedTimeStepsBackup()
			UpdateButtons()
			Update3DSounds()
		EndIf

		Render3D(1.0) ;Max(0.0, 1.0 + (ft\Accumulator / GetTickDuration())))
		Render2D()
		UpdateHotkeys()
	
		
		;ReturnFixedTimeSteps()
	End If

	If GetScripts() Then public_inqueue(public_OnUpdate, True)
	UpdateTimers()
	UpdateResolution()
Forever
If udp_GetStream() Then DisconnectServer("exit")
CloseGame()
;FreeSoundLib()
;----------------------------------------------------------------------------------------------------------------------------------------------------
;----------------------------------------------------------------------------------------------------------------------------------------------------
;----------------------------------------------------------------------------------------------------------------------------------------------------

Function QuickLoadEvents()
	;CatchErrors("Uncaught (QuickLoadEvents)")
	
	If QuickLoad_CurrEvent = Null Then
		QuickLoadPercent = -1
		Return
	EndIf
	
	Local e.Events = QuickLoad_CurrEvent
	
	Local r.Rooms,sc.SecurityCams,sc2.SecurityCams,scale#,pvt%,n.NPCs,tex%,i%,x#,z#
	
	;might be a good idea to use QuickLoadPercent to determine the "steps" of the loading process 
	;instead of magic values in e\eventState and e\eventStr

	Select e\EventConst
		Case e_room2sl
			;[Block]
			If e\EventState = 0 And e\EventStr <> ""
				If e\EventStr <> "" And Left(e\EventStr,4) <> "load"
					QuickLoadPercent = QuickLoadPercent + 5
					If Int(e\EventStr) > 9
						e\EventStr = "load2"
					Else
						e\EventStr = Int(e\EventStr) + 1
					EndIf
				ElseIf e\EventStr = "load2"
					;For SCP-049
					Local skip = False
					If e\room\NPC[0]=Null Then
						For n.NPCs = Each NPCs
							If n\NPCtype = NPCtype049
								;e\room\NPC[0] = n
								skip = True
								Exit
							EndIf
						Next
						
						If (Not skip)
							e\room\NPC[0] = CreateNPC(NPCtype049,EntityX(e\room\Objects[7],True),EntityY(e\room\Objects[7],True)+5,EntityZ(e\room\Objects[7],True))
							e\room\NPC[0]\HideFromNVG = True
							PositionEntity e\room\NPC[0]\Collider,EntityX(e\room\Objects[7],True),EntityY(e\room\Objects[7],True)+5,EntityZ(e\room\Objects[7],True)
							ResetEntity e\room\NPC[0]\Collider
							RotateEntity e\room\NPC[0]\Collider,0,e\room\angle+180,0
							e\room\NPC[0]\State = 0
							e\room\NPC[0]\PrevState = 2
							
							DebugLog(EntityX(e\room\Objects[7],True)+", "+EntityY(e\room\Objects[7],True)+", "+EntityZ(e\room\Objects[7],True))
						Else
							DebugLog "Skipped 049 spawning in room2sl"
						EndIf
					EndIf
					QuickLoadPercent = 80
					e\EventStr = "load3"
				ElseIf e\EventStr = "load3"
					;PositionEntity e\room\NPC[0]\Collider,EntityX(e\room\Objects[7],True),EntityY(e\room\Objects[7],True)+5,EntityZ(e\room\Objects[7],True)
					;ResetEntity e\room\NPC[0]\Collider
					;RotateEntity e\room\NPC[0]\Collider,0,e\room\angle+180,0
					
					;DebugLog(EntityX(e\room\Objects[7],True)+", "+EntityY(e\room\Objects[7],True)+", "+EntityZ(e\room\Objects[7],True))
					
					;e\room\NPC[0]\State = 0
					;e\room\NPC[0]\PrevState = 2
					
					e\EventState = 1
					If e\EventState2 = 0 Then e\EventState2 = -(70*5)
					
					QuickLoadPercent = 100
				EndIf
			EndIf
			;[End Block]
		Case e_room2closets
			;[Block]
			e\StaticEvent = False
			If e\EventState = 0
				If e\EventStr = "load0"
					QuickLoadPercent = 10
					If e\room\NPC[0]=Null Then
						e\room\NPC[0] = CreateNPC(NPCtypeD, EntityX(e\room\Objects[0],True),EntityY(e\room\Objects[0],True),EntityZ(e\room\Objects[0],True))
					EndIf
					
					ChangeNPCTextureID(e\room\NPC[0],4)
					e\EventStr = "load1"
				ElseIf e\EventStr = "load1"
					QuickLoadPercent = 20
					e\room\NPC[0]\Sound=LoadSound_Strict("SFX\Room\Storeroom\Escape1.ogg")
					e\EventStr = "load2"
				ElseIf e\EventStr = "load2"
					QuickLoadPercent = 35
					e\room\NPC[0]\SoundChn = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 12)
					e\EventStr = "load3"
				ElseIf e\EventStr = "load3"
					QuickLoadPercent = 55
					If e\room\NPC[1]=Null Then
						e\room\NPC[1] = CreateNPC(NPCtypeD, EntityX(e\room\Objects[1],True),EntityY(e\room\Objects[1],True),EntityZ(e\room\Objects[1],True))
					EndIf
					
					ChangeNPCTextureID(e\room\NPC[1],2)
					e\EventStr = "load4"
				ElseIf e\EventStr = "load4"
					QuickLoadPercent = 80
					e\room\NPC[1]\Sound=LoadSound_Strict("SFX\Room\Storeroom\Escape2.ogg")
					e\EventStr = "load5"
				ElseIf e\EventStr = "load5"
					QuickLoadPercent = 100
					PointEntity e\room\NPC[0]\Collider, e\room\NPC[1]\Collider
					PointEntity e\room\NPC[1]\Collider, e\room\NPC[0]\Collider
					
					e\EventState=1
				EndIf
			EndIf
			;[End Block]
		Case e_room049
			;[Block]
			DebugLog "Creating..."
			If e\EventState = 0 Then
				If e\EventStr = "load0"
					n.NPCs = CreateNPC(NPCtypeZombie, EntityX(e\room\Objects[4],True),EntityY(e\room\Objects[4],True),EntityZ(e\room\Objects[4],True))
					PointEntity n\Collider, e\room\obj
					TurnEntity n\Collider, 0, 190, 0
					QuickLoadPercent = 20
					e\EventStr = "load1"
				ElseIf e\EventStr = "load1"
					n.NPCs = CreateNPC(NPCtypeZombie, EntityX(e\room\Objects[5],True),EntityY(e\room\Objects[5],True),EntityZ(e\room\Objects[5],True))
					PointEntity n\Collider, e\room\obj
					TurnEntity n\Collider, 0, 20, 0
					QuickLoadPercent = 60
					e\EventStr = "load2"
				ElseIf e\EventStr = "load2"
					For n.NPCs = Each NPCs
						If n\NPCtype = NPCtype049
							e\room\NPC[0]=n
							e\room\NPC[0]\State = 2
							e\room\NPC[0]\Idle = 1
							e\room\NPC[0]\HideFromNVG = True
							PositionEntity e\room\NPC[0]\Collider,EntityX(e\room\Objects[4],True),EntityY(e\room\Objects[4],True)+3,EntityZ(e\room\Objects[4],True)
							ResetEntity e\room\NPC[0]\Collider
							Exit
						EndIf
					Next
					If e\room\NPC[0]=Null
						n.NPCs = CreateNPC(NPCtype049, EntityX(e\room\Objects[4],True), EntityY(e\room\Objects[4],True)+3, EntityZ(e\room\Objects[4],True))
						PointEntity n\Collider, e\room\obj
						n\State = 2
						n\Idle = 1
						n\HideFromNVG = True
						e\room\NPC[0]=n
					EndIf
					QuickLoadPercent = 100
					e\EventState=1
				EndIf
			EndIf
			;[End Block]
		Case e_room205
			;[Block]
			If e\EventState=0 Or e\EventStr <> "loaddone" Then
				If e\EventStr = "load0"
					e\room\Objects[3] = LoadAnimMesh_Strict("GFX\npcs\205_demon1.b3d")
					QuickLoadPercent = 10
					e\EventStr = "load1"
				ElseIf e\EventStr = "load1"
					e\room\Objects[4] = LoadAnimMesh_Strict("GFX\npcs\205_demon2.b3d")
					QuickLoadPercent = 20
					e\EventStr = "load2"
				ElseIf e\EventStr = "load2"
					e\room\Objects[5] = LoadAnimMesh_Strict("GFX\npcs\205_demon3.b3d")
					QuickLoadPercent = 30
					e\EventStr = "load3"
				ElseIf e\EventStr = "load3"
					e\room\Objects[6] = LoadAnimMesh_Strict("GFX\npcs\205_woman.b3d")
					QuickLoadPercent = 40
					e\EventStr = "load4"
				ElseIf e\EventStr = "load4"
					QuickLoadPercent = 50
					e\EventStr = "load5"
				ElseIf e\EventStr = "load5"
					For i = 3 To 6
						PositionEntity e\room\Objects[i], EntityX(e\room\Objects[0],True), EntityY(e\room\Objects[0],True), EntityZ(e\room\Objects[0],True), True
						RotateEntity e\room\Objects[i], -90, EntityYaw(e\room\Objects[0],True), 0, True
						ScaleEntity(e\room\Objects[i], 0.05, 0.05, 0.05, True)
					Next
					QuickLoadPercent = 70
					e\EventStr = "load6"
				ElseIf e\EventStr = "load6"
					;GiveAchievement(Achv205)
					
					HideEntity(e\room\Objects[3])
					HideEntity(e\room\Objects[4])
					HideEntity(e\room\Objects[5])
					QuickLoadPercent = 100
					e\EventStr = "loaddone"
					;e\EventState = 1
				EndIf
			EndIf
			;[End Block]
		Case e_room860
			;[Block]
			If e\EventStr = "load0"
				QuickLoadPercent = 15
				ForestNPC = CreateSprite()
				;0.75 = 0.75*(410.0/410.0) - 0.75*(width/height)
				ScaleSprite ForestNPC,0.75*(140.0/410.0),0.75
				SpriteViewMode ForestNPC,4
				EntityFX ForestNPC,1+8
				ForestNPCTex = LoadAnimTexture("GFX\npcs\AgentIJ.AIJ",1+2,140,410,0,4)
				ForestNPCData[0] = 0
				EntityTexture ForestNPC,ForestNPCTex,ForestNPCData[0]
				ForestNPCData[1]=0
				ForestNPCData[2]=0
				HideEntity ForestNPC
				e\EventStr = "load1"
			ElseIf e\EventStr = "load1"
				QuickLoadPercent = 40
				e\EventStr = "load2"
			ElseIf e\EventStr = "load2"
				QuickLoadPercent = 100
				If e\room\NPC[0]=Null Then e\room\NPC[0]=CreateNPC(NPCtype860, 0,0,0)
				e\EventStr = "loaddone"
			EndIf
			;[End Block]
		Case e_room966
			;[Block]
			If e\EventState = 1
				e\EventState2 = e\EventState2+FPSfactor
				If e\EventState2>30 Then
					If e\EventStr = ""
						CreateNPC(NPCtype966, EntityX(e\room\Objects[0],True), EntityY(e\room\Objects[0],True), EntityZ(e\room\Objects[0],True))
						QuickLoadPercent = 50
						e\EventStr = "load0"
					ElseIf e\EventStr = "load0"
						CreateNPC(NPCtype966, EntityX(e\room\Objects[2],True), EntityY(e\room\Objects[2],True), EntityZ(e\room\Objects[2],True))
						QuickLoadPercent = 100
						e\EventState=2
					EndIf
				Else
					QuickLoadPercent = Int(e\EventState2)
				EndIf
			EndIf
			;[End Block]
		Case e_dimension1499
			;[Block]
			If e\EventState = 0.0
				If e\EventStr = "load0"
					QuickLoadPercent = 10
					e\room\Objects[0] = LoadMesh_Strict("GFX\map\dimension1499\1499plane.b3d")
					;Local planetex% = LoadTexture_Strict("GFX\map\dimension1499\grit3.jpg")
					;ScaleTexture planetex%,0.5,0.5
					;EntityTexture e\room\Objects[0],planetex%
					;FreeTexture planetex%
					HideEntity e\room\Objects[0]
					e\EventStr = "load1"
				ElseIf e\EventStr = "load1"
					QuickLoadPercent = 30
					NTF_1499Sky = sky_CreateSky("GFX\map\sky\1499sky")
					e\EventStr = 1
				Else
					If Int(e\EventStr)<16
						QuickLoadPercent = QuickLoadPercent + 2
						e\room\Objects[Int(e\EventStr)] = LoadMesh_Strict("GFX\map\dimension1499\1499object"+(Int(e\EventStr))+".b3d")
						HideEntity e\room\Objects[Int(e\EventStr)]
						e\EventStr = Int(e\EventStr)+1
					ElseIf Int(e\EventStr)=16
						QuickLoadPercent = 90
						CreateChunkParts(e\room)
						e\EventStr = 17
					ElseIf Int(e\EventStr) = 17
						QuickLoadPercent = 100
						x# = EntityX(e\room\obj)
						z# = EntityZ(e\room\obj)
						Local ch.Chunk
						For i = -2 To 0 Step 2
							ch = CreateChunk(-1,x#*(i*2.5),EntityY(e\room\obj),z#,True)
						Next
						For i = -2 To 0 Step 2
							ch = CreateChunk(-1,x#*(i*2.5),EntityY(e\room\obj),z#-40,True)
						Next
						e\EventState = 2.0
						e\EventStr = 18
					EndIf
				EndIf
			EndIf
			;[End Block]
	End Select
	If QuickLoadPercent = 0 Then
		QuickLoadPercent = -1
		QuickLoad_CurrEvent = Null
	EndIf
	;CatchErrors("QuickLoadEvents "+e\EventName)
	
End Function
Function Kill(killmsg$ = "was killed", ForceKill = False)
	If Not ForceKill Then
		If GodMode Then Return
	EndIf
	
	If player_isdead() Then Return
	
	If BreathCHN <> 0 Then
		If ChannelPlaying(BreathCHN) Then StopChannel(BreathCHN)
	EndIf
	
	If KillTimer >= 0 Then
		i = NetworkServer\MyID

		If Not NetworkServer\Breach Then CreateRoleCorpse(MyPlayer\BreachType, EntityX(MyPlayer\Pivot), EntityY(MyPlayer\Pivot)+0.32, EntityZ(MyPlayer\Pivot), EntityYaw(MyPlayer\Pivot), MyPlayer\size)
		;Select MyPlayer\BreachType
		;	Case SCIENTIST_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, ClassDObj, "GFX\npcs\scientist.jpg", 0,19)
		;	Case MODEL_939: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw, Obj939, "", 70,164, 0.5/2.5, -90)
		;	Case CLASSD_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, ClassDObj, "GFX\npcs\classd"+MyPlayer\TextureID+".jpg", 0,19)
		;	Case JANITOR_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, ClassDObj, "GFX\npcs\janitor.jpg", 0,19)
		;	Case MODEL_035: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, OBJECT_035, "", 0,19)
		;	Case WORKER_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, ClassDObj, "GFX\npcs\janitor2.png", 0,19)
		;	Case GUARD_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, GuardObj, "", 249,285,0.25 / 2.5)
		;	Case NTF_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw, MTFObj, "", 2,26,0.25 / 2.5, -90)
		;	Case HAOS_MODEL: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.1-0.32,MyPlayer\z,MyPlayer\yaw-180, HAOS_OBJECT, "", 593,640, 0.55 / MeshWidth(HAOS_OBJECT))
		;	Case MODEL_ZOMBIE: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, SURGEON_ZOMBIE, "", 344,363,0.5/MeshWidth(SURGEON_ZOMBIE))
		;	Case MODEL_173: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, OBJECT_173, "", 1,40,0.35 / MeshDepth(OBJECT_173))
		;	Case MODEL_106: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, NPC106Obj, "", 496,526,0.25/2.2)
		;	Case MODEL_860: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw-180, OBJECT_860, "", 570,585,0.02, -90)
		;	Case MODEL_049: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw, NPC049OBJ, "", 1123,1163,1.2)
		;	Case MODEL_096: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw, OBJECT_096, "", 1472,1502,0.6 / 3.0)
		;	Case MODEL_966: CreateDeadBody(MyPlayer\x,MyPlayer\y-0.32,MyPlayer\z,MyPlayer\yaw, NPC966Obj, "", 753,791,0.011, -90)
		;	Default:
		;End Select
		
		KillAnim = Rand(0,1)
		PlaySound_Strict(DamageSFX(0))
		If SelectedDifficulty\permaDeath Then
			DeleteFile(CurrentDir() + SavePath + CurrSave+"\save.txt") 
			DeleteDir(SavePath + CurrSave)
			LoadSaveGames()
		End If
		KillTimer = Min(-1, KillTimer)
		ShowEntity Head
		PositionEntity(Head, EntityX(Camera, True), EntityY(Camera, True), EntityZ(Camera, True), True)
		ResetEntity (Head)
		RotateEntity(Head, 0, EntityYaw(Camera), 0)
		MyPlayer\Handcuffed = False
		Using294 = False
		
		If (Not NetworkServer\keepitems) Or NetworkServer\Breach = True Then
			If WearingNightVision Then CameraFogFar = StoredCameraFogFar
			WearingHazmat = 0
			WearingGasMask = 0
			WearingNightVision = 0
			Wearing714 = 0
			Wearing1499 = 0
			For i = 0 To MaxItemAmount-1
				If Inventory(i)<>Null Then
					DropItem(Inventory(i))
				EndIf
			Next
		EndIf
		
		If NetworkServer\Breach = True Then
			If killmsg <> "" Then multiplayer_AddChatMsg(" "+killmsg)
			multiplayer_RequestRole(0)
		Else
			If killmsg <> "" Then multiplayer_AddChatMsg(" "+killmsg)
			If multiplayer_IsFullSync() Then multiplayer_RequestRole(0)
		EndIf
		
		If GetScripts() Then public_inqueue(public_OnDead, True)
		HoldingGun = 0
	EndIf
End Function

Function DrawEnding()
	
	ShowPointer()
	
	FPSfactor = 0
	;EndingTimer=EndingTimer-FPSfactor2
	If EndingTimer>-2000
		EndingTimer=Max(EndingTimer-FPSfactor2,-1111)
	Else
		EndingTimer=EndingTimer-FPSfactor2
	EndIf
	
	GiveAchievement(Achv055)
	If (Not UsedConsole) Then GiveAchievement(AchvConsole)
	If SelectedDifficulty\name = "Keter" Then GiveAchievement(AchvKeter)
	Local x,y,width,height, temp
	Local itt.ItemTemplates, r.Rooms
	
	Select Lower(SelectedEnding)
		Case "b2", "a1"
			ClsColor Max(255+(EndingTimer)*2.8,0), Max(255+(EndingTimer)*2.8,0), Max(255+(EndingTimer)*2.8,0)
		Default
			ClsColor 0,0,0
	End Select
	
	ShouldPlay = 66
	
	Cls
	
	If EndingTimer<-200 Then
		
		If BreathCHN <> 0 Then
			If ChannelPlaying(BreathCHN) Then StopChannel BreathCHN : Stamina = 100
		EndIf
		
		;If EndingTimer <-400 Then 
		;	ShouldPlay = 13
		;EndIf
		
		If EndingScreen = 0 Then
			EndingScreen = LoadImage_Strict("GFX\endingscreen.pt")
			
			ShouldPlay = 23
			CurrMusicVolume = MusicVolume
			
			CurrMusicVolume = MusicVolume
			StopStream_Strict(MusicCHN)
			MusicCHN = StreamSound_Strict("SFX\Music\"+Music(23)+".ogg",CurrMusicVolume,0)
			NowPlaying = ShouldPlay
			
			PlaySound_Strict LightSFX
		EndIf
		
		If EndingTimer > -700 Then 
			
			;-200 -> -700
			;Max(50 - (Abs(KillTimer)-200),0)    =    0->50
			If Rand(1,150)<Min((Abs(EndingTimer)-200),155) Then
				DrawImage EndingScreen, GraphicWidth/2-400, GraphicHeight/2-400
			Else
				Color 0,0,0
				Rect 100,100,GraphicWidth-200,GraphicHeight-200
				Color 255,255,255
			EndIf
			
			If EndingTimer+FPSfactor2 > -450 And EndingTimer <= -450 Then
				Select Lower(SelectedEnding)
					Case "a1", "a2"
						PlaySound_Strict LoadTempSound("SFX\Ending\GateA\Ending"+SelectedEnding+".ogg")
					Case "b1", "b2", "b3"
						PlaySound_Strict LoadTempSound("SFX\Ending\GateB\Ending"+SelectedEnding+".ogg")
				End Select
			EndIf			
			
		Else
			
			DrawImage EndingScreen, GraphicWidth/2-400, GraphicHeight/2-400
			
			If EndingTimer < -1000 And EndingTimer > -2000
				
				width = ImageWidth(PauseMenuIMG)
				height = ImageHeight(PauseMenuIMG)
				x = GraphicWidth / 2 - width / 2
				y = GraphicHeight / 2 - height / 2
				
				DrawImage PauseMenuIMG, x, y
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2 + 40*MenuScale, y + 20*MenuScale, "THE END", True)
				AASetFont Font1
				
				If AchievementsMenu=0 Then 
					x = x+132*MenuScale
					y = y+122*MenuScale
					
					Local roomamount = 0, roomsfound = 0
					For r.Rooms = Each Rooms
						If r\RoomTemplate\Name <> "dimension1499" And r\RoomTemplate\Name <> "gatea" And r\RoomTemplate\Name <> "pocketdimension" Then 
							roomamount = roomamount + 1
							roomsfound = roomsfound + r\found
						EndIf
					Next
					
					Local docamount=0, docsfound=0
					For itt.ItemTemplates = Each ItemTemplates
						If itt\tempname = "paper" Then
							docamount=docamount+1
							docsfound=docsfound+itt\found
						EndIf
					Next
					
					Local scpsEncountered=1
					For i = 0 To 24
						scpsEncountered = scpsEncountered+Achievements(i)
					Next
					
					Local achievementsUnlocked =0
					For i = 0 To MAXACHIEVEMENTS-1
						achievementsUnlocked = achievementsUnlocked + Achievements(i)
					Next
					
					AAText x, y, "SCPs encountered: " +scpsEncountered
					AAText x, y+20*MenuScale, "Achievements unlocked: " + achievementsUnlocked+"/"+(MAXACHIEVEMENTS)
					AAText x, y+40*MenuScale, "Rooms found: " + roomsfound+"/"+roomamount
					AAText x, y+60*MenuScale, "Documents discovered: " +docsfound+"/"+docamount
					AAText x, y+80*MenuScale, "Items refined in SCP-914: " +RefinedItems			
					
					x = GraphicWidth / 2 - width / 2
					y = GraphicHeight / 2 - height / 2
					x = x+width/2
					y = y+height-100*MenuScale
					
					If DrawButton(x-145*MenuScale,y-200*MenuScale,390*MenuScale,60*MenuScale,"ACHIEVEMENTS", True) Then
						AchievementsMenu = 1
					EndIf
					
;					If DrawButton(x-145*MenuScale,y-100*MenuScale,390*MenuScale,60*MenuScale,"MAIN MENU", True) Then
;						NullGame()
;						StopStream_Strict(MusicCHN)
;						;Music(21) = LoadSound_Strict("SFX\Ending\MenuBreath.ogg")
;						ShouldPlay = 21
;						MenuOpen = False
;						MainMenuOpen = True
;						MainMenuTab = 0
;						CurrSave = ""
;						FlushKeys()
;					EndIf
					
					If DrawButton(x-145*MenuScale,y-100*MenuScale,390*MenuScale,60*MenuScale,"MAIN MENU", True)
						ShouldPlay = 24
						NowPlaying = ShouldPlay
						For i=0 To 9
							If TempSounds[i]<>0 Then FreeSound_Strict TempSounds[i] : TempSounds[i]=0
						Next
						StopStream_Strict(MusicCHN)
						MusicCHN = StreamSound_Strict("SFX\Music\"+Music(NowPlaying)+".ogg",0.0,Mode)
						SetStreamVolume_Strict(MusicCHN,1.0*MusicVolume)
						FlushKeys()
						EndingTimer=-2000
						InitCredits()
					EndIf
				Else
					ShouldPlay = 23
					DrawMenu()
				EndIf
			;Credits
			ElseIf EndingTimer<=-2000
				ShouldPlay = 24
				DrawCredits()
			EndIf
			
		EndIf
		
	EndIf
	
	If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
	
	AASetFont Font1
End Function

Type CreditsLine
	Field txt$
	Field id%
	Field stay%
End Type

Global CreditsTimer# = 0.0
Global CreditsScreen%

Function InitCredits()
	Local cl.CreditsLine
	Local file% = OpenFile("Credits.txt")
	Local l$
	
	CreditsFont% = LoadFont_Strict("GFX\font\cour\Courier New.ttf", Int(21 * (GraphicHeight / 1024.0)), 0,0,0)
	CreditsFont2% = LoadFont_Strict("GFX\font\courbd\Courier New.ttf", Int(35 * (GraphicHeight / 1024.0)), 0,0,0)
	
	If CreditsScreen = 0
		CreditsScreen = LoadImage_Strict("GFX\creditsscreen.pt")
	EndIf
	
	Repeat
		l = ReadLine(file)
		cl = New CreditsLine
		cl\txt = l
	Until Eof(file)
	
	Delete First CreditsLine
	CreditsTimer = 0
	
End Function

Function DrawCredits()
	Ready = "Not Ready"
    Local credits_Y# = (EndingTimer+2000)/2+(GraphicHeight+10)
    Local cl.CreditsLine
    Local id%
    Local endlinesamount%
	Local LastCreditLine.CreditsLine
	
    Cls
	
	If Rand(1,300)>1
		DrawImage CreditsScreen, GraphicWidth/2-400, GraphicHeight/2-400
	EndIf
	
	id = 0
	endlinesamount = 0
	LastCreditLine = Null
	Color 255,255,255
	For cl = Each CreditsLine
		cl\id = id
		If Left(cl\txt,1)="*"
			SetFont CreditsFont2
			If cl\stay=False
				Text GraphicWidth/2,credits_Y+(24*cl\id*MenuScale),Right(cl\txt,Len(cl\txt)-1),True
			EndIf
		ElseIf Left(cl\txt,1)="/"
			LastCreditLine = Before(cl)
		Else
			SetFont CreditsFont
			If cl\stay=False
				Text GraphicWidth/2,credits_Y+(24*cl\id*MenuScale),cl\txt,True
			EndIf
		EndIf
		If LastCreditLine<>Null
			If cl\id>LastCreditLine\id
				cl\stay = True
			EndIf
		EndIf
		If cl\stay
			endlinesamount=endlinesamount+1
		EndIf
		id=id+1
	Next
	If (credits_Y+(24*LastCreditLine\id*MenuScale))<-StringHeight(LastCreditLine\txt)
		CreditsTimer=CreditsTimer+(0.5*FPSfactor2)
		If CreditsTimer>=0.0 And CreditsTimer<255.0
			Color Max(Min(CreditsTimer,255),0),Max(Min(CreditsTimer,255),0),Max(Min(CreditsTimer,255),0)
		ElseIf CreditsTimer>=255.0
			Color 255,255,255
			If CreditsTimer>500.0
				CreditsTimer=-255.0
			EndIf
		Else
			Color Max(Min(-CreditsTimer,255),0),Max(Min(-CreditsTimer,255),0),Max(Min(-CreditsTimer,255),0)
			If CreditsTimer>=-1.0
				CreditsTimer=-1.0
			EndIf
		EndIf
		DebugLog CreditsTimer
	EndIf
	If CreditsTimer<>0.0
		For cl = Each CreditsLine
			If cl\stay
				SetFont CreditsFont
				If Left(cl\txt,1)="/"
					Text GraphicWidth/2,(GraphicHeight/2)+(endlinesamount/2)+(24*cl\id*MenuScale),Right(cl\txt,Len(cl\txt)-1),True
				Else
					Text GraphicWidth/2,(GraphicHeight/2)+(24*(cl\id-LastCreditLine\id)*MenuScale)-((endlinesamount/2)*24*MenuScale),cl\txt,True
				EndIf
			EndIf
		Next
	EndIf
	
	If GetKey() Then CreditsTimer=-1
	
	If CreditsTimer=-1
		FreeFont CreditsFont
		FreeFont CreditsFont2
		FreeImage CreditsScreen
		CreditsScreen = 0
		FreeImage EndingScreen
		EndingScreen = 0
		Delete Each CreditsLine
        StopStream_Strict(MusicCHN)
        ShouldPlay = 21
        DisconnectServer()
	EndIf
    
End Function

;[Block]
;Function SetSaveMSG(txt$)
;	
;	Save_MSG = txt
;	Save_MSG_Timer = 0.0
;	Save_MSG_Y = 0.0
;	
;End Function
;
;Function UpdateSaveMSG()
;	Local scale# = GraphicHeight/768.0
;	;Local width% = 200*scale
;	Local width = AAStringWidth(Save_MSG)+20*scale
;	Local height% = 30*scale
;	Local x% = (GraphicWidth/2)-(width/2)
;	Local y% = (-height)+Save_MSG_Y
;	
;	If Save_MSG <> ""
;		If Save_MSG_Timer < 70*5
;			If Save_MSG_Y < height
;				Save_MSG_Y = Min(Save_MSG_Y+2*FPSfactor2,height)
;			Else
;				Save_MSG_Y = height
;			EndIf
;			Save_MSG_Timer = Save_MSG_Timer + FPSfactor2
;		Else
;			If Save_MSG_Y > 0
;				Save_MSG_Y = Max(Save_MSG_Y-2*FPSfactor2,0)
;			Else
;				Save_MSG = ""
;				Save_MSG_Timer = 0.0
;				Save_MSG_Y = 0.0
;			EndIf
;		EndIf
;		DrawFrame(x,y,width,height)
;		Color 255,255,255
;		AASetFont Font1
;		AAText(GraphicWidth/2,y+(height/2),Save_MSG,True,True)
;	EndIf
;	
;End Function
;[End Block]

;--------------------------------------- player controls -------------------------------------------
Function MovePlayer(canmove = False)
	;CatchErrors("Uncaught (MovePlayer)")
	Local Sprint# = 1.0, Speed# = 0.018, i%, angle#
	If Spectate\SpectatePlayer <> -1 Then Return
	If MyPlayer\BreachType = MODEL_106 Then CurrStepSFX=1
	
	Local BT.breachtypes = GetBreachType(MyPlayer\BreachType)
	
	If SuperMan Then
		Speed = Speed * (3+((MyPlayer\BreachType = MODEL_173)*5))
		
		If MyPlayer\BreachType <> MODEL_173 And MyPlayer\BreachType <> MODEL_096 Then
			SuperManTimer=SuperManTimer+FPSfactor
		
			CameraShake = Sin(SuperManTimer / 5.0) * (SuperManTimer / 1500.0)
			If SuperManTimer > 70 * 50 Then
				DeathMSG = "A Class D jumpsuit found in [DATA REDACTED]. Upon further examination, the jumpsuit was found to be filled with 12.5 kilograms of blue ash-like substance. "
				DeathMSG = DeathMSG + "Chemical analysis of the substance remains non-conclusive. Most likely related to SCP-914."
				Kill("was killed by SCP-914")
				ShowEntity Fog
			Else
				BlurTimer = 500		
				HideEntity Fog
			EndIf
		Else
			If MyPlayer\BreachType = MODEL_173 Then
				Shake = 0
				CameraShake = 0
			EndIf
			BlurTimer = 0
		EndIf
	End If
	
	If DeathTimer > 0 Then
		DeathTimer=DeathTimer-FPSfactor
		If DeathTimer < 1 Then DeathTimer = -1.0
	ElseIf DeathTimer < 0 
		Kill()
	EndIf
	
	If CurrSpeed > 0 Then
        Stamina = Min(Stamina + 0.15 * (FPSfactor/(1.25+((NetworkServer\Breach-Max((MyPlayer\BreachType=MODEL_939 Or MyPlayer\BreachType = MODEL_860 Or MyPlayer\BreachType = MODEL_ZOMBIE),0))/2))), 100.0)
    Else
        Stamina = Min(Stamina + 0.15 * (FPSfactor*(1.25-((NetworkServer\Breach-Max((MyPlayer\BreachType=MODEL_939 Or MyPlayer\BreachType = MODEL_860 Or MyPlayer\BreachType = MODEL_ZOMBIE),0))/2))), 100.0)
    EndIf
	
	If StaminaEffectTimer > 0 Then
		StaminaEffectTimer = StaminaEffectTimer - (FPSfactor/70)
	Else
		If StaminaEffect <> 1.0 Then StaminaEffect = 1.0
	EndIf
	
	Local temp#
	
	If PlayerRoom\RoomTemplate\Name<>"pocketdimension" Then 
		If KeyBuffer(KEY_SPRINT) Then
			If Stamina < 5-(3*NetworkServer\Breach) Then
				temp = 0
				If WearingGasMask>0 Or Wearing1499>0 Then temp=1
				If ChannelPlaying(BreathCHN)=False Then 
					BreathCHN = PlaySound_Strict(BreathSFX((temp), 0))
					multiplayer_WriteSound(BreathSFX((temp), 0), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 4.0)
				EndIf
				
			ElseIf Stamina < 50
				If BreathCHN=0 Then
					temp = 0
					If WearingGasMask>0 Or Wearing1499>0 Then temp=1
					BreathCHN = PlaySound_Strict(BreathSFX((temp), Rand(1,3)))
					ChannelVolume BreathCHN, Min((70.0-Stamina)/70.0,1.0)*SFXVolume
					multiplayer_WriteSound(BreathSFX((temp), Rand(1,3)), EntityX(Collider), EntityY(Collider), EntityZ(Collider),4.0)
				Else
					If ChannelPlaying(BreathCHN)=False Then
						temp = 0
						If WearingGasMask>0 Or Wearing1499>0 Then temp=1
						BreathCHN = PlaySound_Strict(BreathSFX((temp), Rand(1,3)))
						ChannelVolume BreathCHN, Min((70.0-Stamina)/70.0,1.0)*SFXVolume
						multiplayer_WriteSound(BreathSFX((temp), Rand(1,3)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 4.0)						
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
	
	For i = 0 To MaxItemAmount-1
		If Inventory(i)<>Null Then
			If Inventory(i)\itemtemplate\tempname = "finevest" Then Stamina = Min(Stamina, 60)
		EndIf
	Next
	
	If Wearing714 Then
		Stamina = Min(Stamina, 10)
		Sanity = Max(-850, Sanity)
	EndIf
	
	If IsZombie Then Crouch = False
	
	If Abs(CrouchState-Crouch)<0.001 Then 
		CrouchState = Crouch
	Else
		CrouchState = CurveValue(Crouch, CrouchState, 10.0)
	EndIf
	
	;MyPlayer\MoveDir = MyPlayer\MoveDir + (32*Crouch)
	
	If (Not canmove) Then
		If (Not NoClip) Then 
			If ((KeyBuffer(KEY_DOWN) Or KeyBuffer(KEY_UP)) Or (KeyBuffer(KEY_RIGHT) Or KeyBuffer(KEY_LEFT)) And Playable) Or ForceMove>0 Then
				
				If Crouch = 0 And (KeyBuffer(KEY_SPRINT)) And Stamina > 0.0 And (Not IsZombie) And (Not (MyPlayer\BreachType = MODEL_173 Or MyPlayer\BreachType = MODEL_966)) Then
					;MyPlayer\MoveDir = MyPlayer\MoveDir + 16
					
					Sprint = 2.5+(MyPlayer\BreachType = MODEL_096)
					If MyPlayer\BreachType = MODEL_096 Then
						If Not SCP\Raged Then 
							Sprint = 1.0
						EndIf

					ElseIf BT\DisableSprint Then
						
						Sprint = 1.0
					Else
						;MyPlayer\MoveDir = MyPlayer\MoveDir + 32

						Stamina = Stamina - FPSfactor * (0.4-(0.2*(NetworkServer\Breach-BT\UsualSprint))) * StaminaEffect
						If Stamina <= 0 Then Stamina = -20.0
					EndIf
				End If
				
				If PlayerRoom\RoomTemplate\Name = "pocketdimension" Then 
					If EntityY(Collider)<2000*RoomScale Or EntityY(Collider)>2608*RoomScale Then
						Stamina = 0
						Speed = 0.015
						Sprint = 1.0					
					EndIf
				EndIf	
				
				If ForceMove>0 Then Speed=Speed*ForceMove
				
				If SelectedItem<>Null Then
					If SelectedItem\itemtemplate\tempname = "firstaid" Or SelectedItem\itemtemplate\tempname = "finefirstaid" Or SelectedItem\itemtemplate\tempname = "firstaid2" Or SelectedItem\itemtemplate\tempname = "chicken" Or SelectedItem\itemtemplate\tempname = "boxofammo" Then 
						Sprint = 0
					EndIf
				EndIf
				
				temp# = (Shake Mod 360)
				Local tempchn%
				If (Not UnableToMove%) Then Shake# = (Shake + FPSfactor * Min(Sprint, 1.5) * 7) Mod 720
				If temp < 180 And (Shake Mod 360) >= 180 And KillTimer>=0 Then
					If CurrStepSFX=0 Then
						temp = GetStepSound(Collider)
						
						If Sprint = 2.5 Then
							PlayerSoundVolume = Max(4.0,PlayerSoundVolume)
							tempchn% = PlaySound_Strict(StepSFX(temp, 0, Rand(0, 7)))
							ChannelVolume tempchn, (1.0-(Crouch*0.6))*SFXVolume#
						Else
							PlayerSoundVolume = Max(2.5-(Crouch*0.6),PlayerSoundVolume)
							tempchn% = PlaySound_Strict(StepSFX(temp, 1, Rand(0, 7)))
							ChannelVolume tempchn, (1.0-(Crouch*0.6))*SFXVolume#
						End If
						multiplayer_WriteSound(StepSFX(temp,0, Rand(0,7)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0, 1.0-(0.5*Crouch))
					ElseIf CurrStepSFX=1
						tempchn% = PlaySound_Strict(Step2SFX(Rand(0, 2)))
						ChannelVolume tempchn, (1.0-(Crouch*0.4))*SFXVolume#
						multiplayer_WriteSound(Step2SFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0, 1.0-(0.5*Crouch))
					ElseIf CurrStepSFX=2
						tempchn% = PlaySound_Strict(Step2SFX(Rand(3,5)))
						ChannelVolume tempchn, (1.0-(Crouch*0.4))*SFXVolume#
						multiplayer_WriteSound(Step2SFX(Rand(3,5)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0, 1.0-(0.5*Crouch))
					ElseIf CurrStepSFX=3
						If Sprint = 2.5 Then
							PlayerSoundVolume = Max(4.0,PlayerSoundVolume)
							tempchn% = PlaySound_Strict(StepSFX(0, 0, Rand(0, 7)))
							ChannelVolume tempchn, (1.0-(Crouch*0.6))*SFXVolume#
							multiplayer_WriteSound(StepSFX(0, 0, Rand(0, 7)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0, 1.0-(0.5*Crouch))
						Else
							PlayerSoundVolume = Max(2.5-(Crouch*0.6),PlayerSoundVolume)
							tempchn% = PlaySound_Strict(StepSFX(0, 1, Rand(0, 7)))
							ChannelVolume tempchn, (1.0-(Crouch*0.6))*SFXVolume#
							multiplayer_WriteSound(StepSFX(0, 1, Rand(0, 7)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0, 1.0-(0.5*Crouch))
						End If
					EndIf
					If MyPlayer\BreachType = MODEL_106 And Rand(1,60) = 2 Then
						pvt = CreatePivot() 
						PositionEntity pvt, EntityX(Collider),EntityY(Collider),EntityZ(Collider)
						TurnEntity pvt, 90, 0, 0
						If EntityPick(pvt,1) <> 0 Then
							de.decals = CreateDecal(0, PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
							de\Size = 0.05 : de\SizeChange = 0.001 : EntityAlpha(de\obj, 0.8) : UpdateDecals()
							de\lifetime = 70*15
						EndIf
						FreeEntity pvt
						multiplayer_WriteDecal(de)
					EndIf
				EndIf	
			EndIf
		Else ;noclip on
			If (KeyBuffer(KEY_SPRINT)) Then
				;MyPlayer\MoveDir = MyPlayer\MoveDir + 32
				Sprint = 2.5
			ElseIf KeyDown(KEY_CROUCH)
				Sprint = 0.5
			EndIf
		EndIf
	
		If KeyHit(KEY_CROUCH) And Playable Then Crouch = (Not Crouch)
	EndIf
	
	Local SpeedMult# = 1.0
	
	If MyPlayer\SpeedMult <> 0 Then SpeedMult = MyPlayer\SpeedMult
	
	Local temp2# = (Speed * Sprint * SpeedMult) / (1.0+Crouch)
	
	CollidedFloor% = False
	
	PREV_PLAYER_MOVE = PLAYER_MOVE
	PLAYER_MOVE = PLAYER_IDLING
	If NoClip Then
		If (Not canmove) Then
			Shake = 0
			CurrSpeed = 0
			CrouchState = 0
			Crouch = 0
			
			RotateEntity Collider, WrapAngle(EntityPitch(Camera)), WrapAngle(EntityYaw(Camera)), 0
			
			temp2 = temp2 * NoClipSpeed
			
			If KeyBufferFactor>MilliSecs() Then
				temp2 = False
				CurrSpeed = 0
				KeyBufferCant = True
			Else
				KeyBufferCant = False
			EndIf
			
			If KeyBuffer(KEY_DOWN) Then MoveEntity Collider, 0, 0, -temp2*FPSfactor; : MyPlayer\MoveDir = MyPlayer\MoveDir + 2
			If KeyBuffer(KEY_UP) Then MoveEntity Collider, 0, 0, temp2*FPSfactor; : MyPlayer\MoveDir = MyPlayer\MoveDir + 1
			
			If KeyBuffer(KEY_LEFT) Then MoveEntity Collider, -temp2*FPSfactor, 0, 0; : MyPlayer\MoveDir = MyPlayer\MoveDir + 4
			If KeyBuffer(KEY_RIGHT) Then MoveEntity Collider, temp2*FPSfactor, 0, 0; : MyPlayer\MoveDir = MyPlayer\MoveDir + 8
			DropSpeed = temp2
			
			ResetEntity Collider
		EndIf
	Else 
		If JumpState = JumpFalling Then
			temp2# = temp2 / Max((Injuries+3.0)/3.0,1.0)
			;If Injuries > 0.5 Then 
			;	temp2 = temp2*Min((Sin(Shake/2)+1.2),1.0)
			;EndIf

			temp = False
			If (Not IsZombie%)
				If KeyBuffer(KEY_DOWN) And Playable Then
					;MyPlayer\MoveDir = MyPlayer\MoveDir + 2
					temp = True 
					angle = 180
					If KeyBuffer(KEY_LEFT) Then angle = 135; : MyPlayer\MoveDir = MyPlayer\MoveDir + 4
					If KeyBuffer(KEY_RIGHT) Then angle = -135; : MyPlayer\MoveDir = MyPlayer\MoveDir + 8
				ElseIf (KeyBuffer(KEY_UP) And Playable) Then; Or ForceMove>0
					;MyPlayer\MoveDir = MyPlayer\MoveDir + 1
					temp = True
					angle = 0
					If KeyBuffer(KEY_LEFT) Then angle = 45; : MyPlayer\MoveDir = MyPlayer\MoveDir + 4
					If KeyBuffer(KEY_RIGHT) Then angle = -45; : MyPlayer\MoveDir = MyPlayer\MoveDir + 8
				ElseIf ForceMove>0 Then
					temp=True
					angle = ForceAngle
				Else If Playable Then
					If KeyBuffer(KEY_LEFT) Then angle = 90 : temp = True; : MyPlayer\MoveDir = MyPlayer\MoveDir + 4
					If KeyBuffer(KEY_RIGHT) Then angle = -90 : temp = True; : MyPlayer\MoveDir = MyPlayer\MoveDir + 8
				EndIf
			Else
				temp=True
				angle = ForceAngle
			EndIf
			
			;if KeyBufferFactor>MilliSecs() then
			;	temp = false
			;	currspeed = 0
			;	KeyBufferCant = True
			;Else
			;	KeyBufferCant = False
			;EndIf
			
			angle = WrapAngle(EntityYaw(Collider,True)+angle+90.0)
			
			If temp Then 
				CurrSpeed = CurveValue(temp2, CurrSpeed, 20.0)
			Else
				CurrSpeed = Max(CurveValue(0.0, CurrSpeed-0.1, 1.0),0.0)
			EndIf
			;if JumpState <> JumpFalling Then TranslateEntity Collider, (Cos(angle)*LastXSpeed)*FPSfactor, JumpState * FPSfactor, (Sin(angle)*LastZSpeed)*FPSfactor, True
			
			CollidedFloor = False
			
			Local ccl = CountCollisions(Collider)
			For i = 1 To ccl
				If CollisionY(Collider, i) < EntityY(Collider) - 0.25 Then CollidedFloor = True
			Next
			
			If (Not multiplayer_IsFullSync()) Or NetworkServer\Prediction Or ((Not NetworkServer\Interpolation) And (Not NetworkServer\Prediction)) Then
				If NetworkServer\JumpMode = True And BT\AllowJump Then
					If (Not UnableToMove%) And (Not canmove) Then
						If CollidedFloor = True Then
							If (Not(NetworkServer\chatOpen Or ConsoleOpen Or MenuOpen)) Then
								If KeyHit(KEY_JUMP) Then
									If Stamina > 20 Then
										;multiplayer_Send(M_UPDATE, -1, 2)
										JumpState = 0.05*Max(MyPlayer\size/1.5, 1.0)
										If KeyBuffer(KEY_UP) Then LastZSpeed = CurrSpeed
										If KeyBuffer(KEY_DOWN) Then LastZSpeed = -CurrSpeed
										If KeyBuffer(KEY_LEFT) Then LastXSpeed = -CurrSpeed
										If KeyBuffer(KEY_RIGHT) Then LastXSpeed = CurrSpeed
										Stamina = Stamina-10
										NetworkServer\Jumped = True
										;MyPlayer\MoveDir = MyPlayer\MoveDir + 64
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			Else
				If (Not(NetworkServer\chatOpen Or ConsoleOpen Or MenuOpen)) Then
					If KeyHit(KEY_JUMP) Then
						If Stamina > 20 Then
							;multiplayer_Send(M_UPDATE, -1, 2)
							Stamina = Stamina-10
							NetworkServer\Jumped = True
							;MyPlayer\MoveDir = MyPlayer\MoveDir + 64
						EndIf
					EndIf
				EndIf
			EndIf
			
			If CollidedFloor = True Then
				If DropSpeed# < - 0.07 Then 
					If CurrStepSFX=0 Then
						PlaySound_Strict(StepSFX(GetStepSound(Collider), 0, Rand(0, 7)))
					ElseIf CurrStepSFX=1
						PlaySound_Strict(Step2SFX(Rand(0, 2)))
					ElseIf CurrStepSFX=2
						PlaySound_Strict(Step2SFX(Rand(3, 5)))
					ElseIf CurrStepSFX=3
						PlaySound_Strict(StepSFX(0, 0, Rand(0, 7)))
					EndIf
					PlayerSoundVolume = Max(3.0,PlayerSoundVolume)
					multiplayer_WriteSound(StepSFX(GetStepSound(Collider),0, Rand(0,7)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0)
					If DropSpeed <= -0.17 And NetworkServer\Breach And (Not multiplayer_IsFullSync()) Then TakeFallDamage(Sqr(DropSpeed*DropSpeed))
				EndIf
				DropSpeed# = 0
			Else
				;DropSpeed# = Min(Max(DropSpeed - 0.006 * FPSfactor, -2.0), 0.0)
				If PlayerFallingPickDistance#<>0.0
					If PlayerFallingPickDistance = -2147 Then
						Local l = CreatePivot()
						PositionEntity l, EntityX(Collider),EntityY(Collider)+0.2,EntityZ(Collider)
						pick = LinePick(EntityX(l),EntityY(l),EntityZ(l),0,-0.5,0)
						If pick
							DropSpeed# = 0
						Else
							DropSpeed# = Min(Max(DropSpeed - 0.005 * FPSfactor, -2.0), 0.0)
						EndIf
						FreeEntity l
					Else
						pick = LinePick(EntityX(Collider),EntityY(Collider),EntityZ(Collider),0,-PlayerFallingPickDistance,0)
						If pick
							DropSpeed# = Min(Max(DropSpeed - 0.005 * FPSfactor, -2.0), 0.0)
						Else
							DropSpeed# = 0
							CollidedFloor = False
						EndIf
					EndIf
				Else
					DropSpeed# = Min(Max(DropSpeed - 0.005 * FPSfactor, -2.0), 0.0)
				EndIf
			EndIf
			PlayerFallingPickDistance# = 10.0
			;if NetworkServer\FullSync Then DropSpeed = 0
			
			;TranslateEntity Collider, 0, -0.00001*FPSfactor, 0
			
			If (Not multiplayer_IsFullSync()) Or (Not NetworkServer\DetectIncorrect) Or (NetworkServer\Prediction) Then
				If (Not UnableToMove%) And (Not canmove) Then TranslateEntity Collider, Cos(angle)*CurrSpeed * FPSfactor, 0, Sin(angle)*CurrSpeed * FPSfactor, True
				If (Not UnableToMove%) And ShouldEntitiesFall Then TranslateEntity Collider, 0, DropSpeed * FPSfactor, 0
			EndIf
		Else
			CollidedFloor = False
			ccl = CountCollisions(Collider)
			For i = 1 To ccl
				If CollisionY(Collider, i) < EntityY(Collider) - 0.25 Then CollidedFloor = True
			Next
			If CollidedFloor = True Then
				If JumpState <= -0.14 And NetworkServer\Breach And (Not multiplayer_IsFullSync()) Then TakeFallDamage(Sqr(JumpState*JumpState))
				JumpState = JumpFalling
				If CurrStepSFX=0 Then
					PlaySound_Strict(StepSFX(GetStepSound(Collider), 0, Rand(0, 7)))
				ElseIf CurrStepSFX=1
					PlaySound_Strict(Step2SFX(Rand(0, 2)))
				ElseIf CurrStepSFX=2
					PlaySound_Strict(Step2SFX(Rand(3, 5)))
				ElseIf CurrStepSFX=3
					PlaySound_Strict(StepSFX(0, 0, Rand(0, 7)))
				EndIf
				LastXSpeed = 0
				LastZSpeed = 0
				multiplayer_WriteSound(StepSFX(GetStepSound(Collider),0, Rand(0,7)), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0)
				PlayerSoundVolume = Max(3.0,PlayerSoundVolume)
			Else
				If PlayerFallingPickDistance <> 0.0 Then
					pick = LinePick(EntityX(Collider),EntityY(Collider),EntityZ(Collider),0,-PlayerFallingPickDistance,0)
					If pick
						MoveEntity Collider, LastXSpeed*FPSfactor, JumpState*FPSfactor, LastZSpeed*FPSfactor
					Else
						JumpState = JumpFalling
						LastXSpeed = 0
						LastZSpeed = 0
					EndIf
				Else
					MoveEntity Collider, LastXSpeed*FPSfactor, JumpState*FPSfactor, LastZSpeed*FPSfactor
				EndIf
			EndIf
			PlayerFallingPickDistance# = 10.0
		EndIf
	EndIf
	
	;Select MyPlayer\BreachType
	;	Case MODEL_173,MODEL_939,MODEL_966
	;		MyPlayer\PlayerSoundVolume = 0
	;End Select
	
	JumpState = JumpState-(NetworkServer\JumpHeight*FPSfactor)
	If JumpState <= JumpFalling Then JumpState = JumpFalling
	If NetworkServer\JumpMode = False Then JumpState = JumpFalling
	;DebugLog "JumpState = "+JumpState
	ForceMove = False
	
	If Injuries > 1.0 Then
		temp2 = Bloodloss
		BlurTimer = Max(Max(Sin(MilliSecs2()/100.0)*Bloodloss*30.0,Bloodloss*2*(2.0-CrouchState)),BlurTimer)
		If (Not I_427\Using And I_427\Timer < 70*360) Then
			Bloodloss = Min(Bloodloss + (Min(Injuries,3.5)/300.0)*FPSfactor,100)
		EndIf
		
		If temp2 <= 60 And Bloodloss > 60 Then
			Msg = "You are feeling faint from the amount of blood you have lost."
			MsgTimer = 70*4
		EndIf
	EndIf
	
	UpdateInfect()
	
	If Bloodloss > 0 Then
		If Rnd(200)<Min(Injuries,4.0) Then
			pvt = CreatePivot()
			PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
			TurnEntity pvt, 90, 0, 0
			EntityPick(pvt,0.3)
			de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
			de\size = Rnd(0.03,0.08)*Min(Injuries,3.0) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
			tempchn% = PlaySound_Strict (DripSFX(Rand(0,2)))
			ChannelVolume tempchn, Rnd(0.0,0.8)*SFXVolume
			ChannelPitch tempchn, Rand(20000,30000)
			
			FreeEntity pvt
			multiplayer_WriteDecal(de,1)
		EndIf
		
		CurrCameraZoom = Max(CurrCameraZoom, (Sin(Float(MilliSecs2())/20.0)+1.0)*Bloodloss*0.2)
		
		If Bloodloss > 60 Then Crouch = True
		If Bloodloss => 100 Then 
			Kill("died of blood loss")
			HeartBeatVolume = 0.0
		ElseIf Bloodloss > 80.0
			HeartBeatRate = Max(150-(Bloodloss-80)*5,HeartBeatRate)
			HeartBeatVolume = Max(HeartBeatVolume, 0.75+(Bloodloss-80.0)*0.0125)	
		ElseIf Bloodloss > 35.0
			HeartBeatRate = Max(70+Bloodloss,HeartBeatRate)
			HeartBeatVolume = Max(HeartBeatVolume, (Bloodloss-35.0)/60.0)			
		EndIf
	EndIf
	
	If HealTimer > 0 Then
		DebugLog HealTimer
		HealTimer = HealTimer - (FPSfactor / 70)
		Bloodloss = Min(Bloodloss + (2 / 400.0) * FPSfactor, 100)
		Injuries = Max(Injuries - (FPSfactor / 70) / 30, 0.0)
	EndIf
		
	If Playable Then
		If KeyHit(KEY_BLINK) Then BlinkTimer = 0
		If KeyDown(KEY_BLINK) And BlinkTimer < - 10 Then BlinkTimer = -10
	EndIf
	
	
	If HeartBeatVolume > 0 Then
		If HeartBeatTimer <= 0 Then
			tempchn = PlaySound_Strict (HeartBeatSFX)
			ChannelVolume tempchn, HeartBeatVolume*SFXVolume#
			
			HeartBeatTimer = 70.0*(60.0/Max(HeartBeatRate,1.0))
		Else
			HeartBeatTimer = HeartBeatTimer - FPSfactor
		EndIf
		
		HeartBeatVolume = Max(HeartBeatVolume - FPSfactor*0.05, 0)
	EndIf
	If ( (Not KeyBuffer(KEY_UP)) And (Not KeyBuffer(KEY_DOWN)) And (Not KeyBuffer(KEY_LEFT)) And (Not KeyBuffer(KEY_RIGHT)) ) Then
		If Crouch = 1 Then
			PLAYER_MOVE = PLAYER_SITTING_IDLING 
		Else 
			PLAYER_MOVE = PLAYER_IDLING
		EndIf
	Else
		If (Not KeyBuffer(KEY_SPRINT)) Then
			If Crouch = 1 Then
				If KeyBuffer(KEY_LEFT) Then 
					PLAYER_MOVE = PLAYER_SITTING_WALKING_LEFT
				ElseIf KeyBuffer(KEY_RIGHT) Then 
					PLAYER_MOVE = PLAYER_SITTING_WALKING_RIGHT
				ElseIf KeyBuffer(KEY_UP) Then 
					PLAYER_MOVE = PLAYER_SITTING_WALKING_FORWARD
				ElseIf KeyBuffer(KEY_DOWN) Then 
					PLAYER_MOVE = PLAYER_SITTING_WALKING_BACK
				EndIf
			Else
				If KeyBuffer(KEY_DOWN) Or KeyBuffer(KEY_UP) Or KeyBuffer(KEY_LEFT) Or KeyBuffer(KEY_RIGHT) Then PLAYER_MOVE = PLAYER_WALKING
			EndIf
		Else
			If (Not Crouch) Then
				If KeyBuffer(KEY_DOWN) Or KeyBuffer(KEY_UP) Or KeyBuffer(KEY_LEFT) Or KeyBuffer(KEY_RIGHT) Then PLAYER_MOVE = PLAYER_RUNNING
			EndIf
		EndIf
	EndIf
	If JumpState <> JumpFalling Then PLAYER_MOVE = PLAYER_JUMPING
	
	Select MyPlayer\BreachType
		Case MODEL_096
			If scp\ragestart <> 0.0 Then
				PLAYER_MOVE = PLAYER_CRYING
			EndIf
			If SCP\Raged Then
				If PLAYER_MOVE = PLAYER_SITTING_IDLING Or PLAYER_MOVE = PLAYER_IDLING Or PLAYER_MOVE = PLAYER_WALKING Then PLAYER_MOVE = PLAYER_CRYING
				;if (Not ChannelPlaying(MyPlayer\SoundChn)) Then 
				;	MyPlayer\SoundChn = PlaySound_Strict(LoadTempSound("SFX\SCP\096\Scream.ogg"))
				;EndIf
			ElseIf Not(scp\ragestart <> 0.0) Then
				If PLAYER_MOVE = PLAYER_RUNNING Then PLAYER_MOVE = PLAYER_WALKING
			EndIf
	End Select
	
	;debuglog PLAYER_MOVE+ " "+JumpState+" "+JumpFalling
	;CatchErrors("MovePlayer")
End Function

Function MouseLook(Turn = True)
	Local i%
	Local BT.breachtypes = GetBreachType(MyPlayer\BreachType)
	;CameraZoomTemp = CurveValue(CurrCameraZoom,CameraZoomTemp, 5.0)
	CameraZoom(Camera, Min(1.0+(CurrCameraZoom/400.0),1.1)  / (Tan((2.0 * ATan(Tan((CurrentFOV) / 2.0) * (Float(RealGraphicWidth) / Float(RealGraphicHeight)))) / 2.0)) )
	CurrCameraZoom = Max(CurrCameraZoom - FPSfactor, 0)
	
	If KillTimer >= 0 And FallTimer >=0 Then
		
		HeadDropSpeed = 0
		
		;If 0 Then 
		;fixing the black screen bug with some bubblegum code 
		Local Zero# = 0.0
		Local Nan1# = 0.0 / Zero
		If Int(EntityX(Collider))=Int(Nan1) Then
			
			PositionEntity Collider, EntityX(Camera, True), EntityY(Camera, True) - 0.5, EntityZ(Camera, True), True
			Msg = "EntityX(Collider) = NaN, RESETTING COORDINATES    -    New coordinates: "+EntityX(Collider)
			MsgTimer = 300				
		EndIf
		;EndIf
		
		Local up# = (Sin(Shake) / (20.0+CrouchState*20.0))*0.6
		Local roll# = Max(Min(Sin(Shake/2)*2.5*Min(Injuries+0.25,3.0),8.0),-8.0)
		
		;===================================================== Q and E sheltering
		Local GetRoll = False
		
		If GetMilliSecs(0) Then
			If EqquipedGun <> Null Then
				If EqquipedGun\ID <= GUN_M4A4 Then 
					;if ((ClosestButton=0 And ClosestItem=Null And GrabbedEntity%=0 And ClosestDoor=Null And (Not MouseInteract)) Or MOUSEINTERACT) And OtherOpen = Null And DrawHandIcon = False And SelectedDoor = Null  And MenuOpen = False And InvOpen = False And TAB_MENU_STATE < 2 And (Not(NetworkServer\ChatOpen = True Or ConsoleOpen)) And (Not BlockGuns) Then
					If KeyDown(KEY_LEANL) Then
						pvt2 = CreatePivot()
						PositionEntity pvt2, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
						RotateEntity pvt2, EntityPitch(Camera), EntityYaw(Camera)+90, 0
						
						If Not EntityPick(pvt2, 0.3) Then
							GunRoll = CurveValue(15, GunRoll, 7.0)
							GetRoll = True
						EndIf
						FreeEntity pvt2
					EndIf
					If KeyDown(KEY_LEANR) Then 
						pvt2 = CreatePivot()
						PositionEntity pvt2, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
						RotateEntity pvt2, EntityPitch(Camera), EntityYaw(Camera)-90, 0
						
						If Not EntityPick(pvt2, 0.3) Then
							GunRoll = CurveValue(-15, GunRoll, 7.0)
							GetRoll = True
						EndIf
						FreeEntity pvt2
					EndIf
				EndIf
				;EndIf
			EndIf
		EndIf
		
		If Not GetRoll Then 
			GunRoll = CurveValue(0, GunRoll, 7.0)
			If GunRoll >= -0.01 And GunRoll <= 0.01 Then GunRoll = 0
		EndIf
		
		; ====================================================
		
		;k?¤?¤nnet?¤?¤n kameraa sivulle jos pelaaja on vammautunut
		;RotateEntity Collider, EntityPitch(Collider), EntityYaw(Collider), Max(Min(up*30*Injuries,50),-50)
	
		PositionEntity Camera, EntityX(Collider), EntityY(Collider)+BT\CameraOffset, EntityZ(Collider)
		RotateEntity Camera, 0, EntityYaw(Collider), (roll*0.5)+(GunRoll/1)
		MoveEntity Camera, side, up + 0.6 + CrouchState * MyPlayer\size * -0.3, 0

		If MyPlayer\size <> 1.0 Then
			If MyPlayer\size >= 1.0 Then 
				MoveEntity Camera, 0, (MyPlayer\size-1.0), 0
			Else
				MoveEntity Camera, 0, -(1.0 - MyPlayer\size), 0
			EndIf
		EndIf
		;RotateEntity Collider, EntityPitch(Collider), EntityYaw(Collider), 0
		;moveentity player, side, up, 0	
		; -- Update the smoothing que To smooth the movement of the mouse.
		If Turn Then
			
			mouse_x_speed_1# = CurveValue(MouseXSpeed() * (MouseSens + 0.6) , mouse_x_speed_1, (6.0 / (MouseSens + 1.0))*MouseSmooth)
			mouse_x_recoil# = CurveValue(GetRecoilPattern(), mouse_x_recoil, 10)
			
			If Int(mouse_x_speed_1) = Int(Nan1) Then mouse_x_speed_1 = 0
			;if PrevFPSFactor>0 Then
			;	If Abs(FPSfactor/PrevFPSFactor-1.0)>1.0 Then
					;lag spike detected - stop all camera movement
			;		mouse_x_speed_1 = 0
			;		mouse_y_speed_1 = 0
			;	EndIf
			;EndIf

			If InvertMouse Then
				
				If IsGunShooting() Then 
					mouse_y_recoil# = CurveValue(GetRecoilForce(), mouse_y_recoil, 10)
				Else
					mouse_y_recoil = 0
				
				EndIf
				mouse_y_speed_1# = CurveValue(-MouseYSpeed() * (MouseSens + 0.6), mouse_y_speed_1, (6.0/(MouseSens+1.0))*MouseSmooth)
			Else
				If IsGunShooting() Then 
					mouse_y_recoil# = CurveValue(-GetRecoilForce(), mouse_y_recoil,10)
					;mouse_y_recoil_energy = 0
					;mouse_y_recoil_1 = 0
				Else
					mouse_y_recoil = 0

					;if EqquipedGun <> Null Then
					;	if ((not MouseDown1) Or EqquipedGun\CurrAmmo = 0) Then
					;		if user_camera_pitch < RecoilRegen And RecoilRegen <> 0 then
					;			mouse_y_recoil_1 = CurveValue(0.3, mouse_y_recoil_1, 10)
					;			if mouse_y_recoil_1 > 0.28 Then
					;				mouse_y_recoil_1 = CurveValue(0.0, mouse_y_recoil_1, 50)
					;			EndIf
					;			
					;			mouse_y_recoil_energy = mouse_y_recoil_energy+sqrvalue(mouse_y_recoil_1*FPSfactor)
					;			if mouse_y_recoil_energy > 20 then 
					;				RecoilRegen = 0
					;				mouse_y_recoil_energy = 0
					;				mouse_y_recoil_1 = 0
					;			endif
					;		else
					;			RecoilRegen = 0
					;			mouse_y_recoil_energy = 0
					;			mouse_y_recoil_1 = 0
					;		endif
					;	EndIf
					;Else
					;	RecoilRegen = 0
					;	mouse_y_recoil_energy = 0
					;	mouse_y_recoil_1 = 0
					;EndIf
				EndIf
				
				mouse_y_speed_1# = CurveValue(MouseYSpeed() * (MouseSens + 0.6), mouse_y_speed_1, (6.0/(MouseSens+1.0))*MouseSmooth) 
			EndIf
			
			
			
			If Int(mouse_y_speed_1) = Int(Nan1) Then mouse_y_speed_1 = 0
			
			Local the_yaw# = (((mouse_x_speed_1#)) * mouselook_x_inc# / (1.0+(WearingVest/(1+NetworkServer\Breach))))+mouse_x_recoil
			Local the_pitch# = (((mouse_y_speed_1)) * mouselook_y_inc# / (1.0+(WearingVest/(1+NetworkServer\Breach))))+mouse_y_recoil;+(mouse_y_recoil_1*FPSfactor)

			TurnEntity Collider, 0.0, -the_yaw#, 0.0 ; Turn the user on the Y (yaw) axis.
			user_camera_pitch# = user_camera_pitch# + the_pitch#
			
			UpdateRecoil()
		EndIf
			; -- Limit the user;s camera To within 180 degrees of pitch rotation. ;EntityPitch(); returns useless values so we need To use a variable To keep track of the camera pitch.
		If user_camera_pitch# > 70.0 Then user_camera_pitch# = 70.0
		If user_camera_pitch# < - 70.0 Then user_camera_pitch# = -70.0
		
		RotateEntity Camera, WrapAngle(user_camera_pitch + Rnd(-CameraShake, CameraShake)), WrapAngle(EntityYaw(Collider) + Rnd(-CameraShake, CameraShake)), roll+GunRoll ; Pitch the user;s camera up And down.
	
		If PlayerRoom\RoomTemplate\Name = "pocketdimension" Then
			If EntityY(Collider)<2000*RoomScale Or EntityY(Collider)>2608*RoomScale Then
				RotateEntity Camera, WrapAngle(EntityPitch(Camera)),WrapAngle(EntityYaw(Camera)), roll+WrapAngle(Sin(MilliSecs2()/150.0)*30.0) ; Pitch the user;s camera up And down.
			EndIf
		EndIf
	Else
		HideEntity Collider
		PositionEntity Camera, EntityX(Head), EntityY(Head), EntityZ(Head)
		
		CollidedFloor% = False
		For i = 1 To CountCollisions(Head)
			If CollisionY(Head, i) < EntityY(Head) - 0.01 Then CollidedFloor = True
		Next
		
		If CollidedFloor = True Then
			HeadDropSpeed# = 0
		Else
			
			If KillAnim = 0 Then 
				MoveEntity Head, 0, 0, HeadDropSpeed
				RotateEntity(Head, CurveAngle(-90.0, EntityPitch(Head), 20.0), EntityYaw(Head), EntityRoll(Head))
				RotateEntity(Camera, CurveAngle(EntityPitch(Head) - 40.0, EntityPitch(Camera), 40.0), EntityYaw(Camera), EntityRoll(Camera))
			Else
				MoveEntity Head, 0, 0, -HeadDropSpeed
				RotateEntity(Head, CurveAngle(90.0, EntityPitch(Head), 20.0), EntityYaw(Head), EntityRoll(Head))
				RotateEntity(Camera, CurveAngle(EntityPitch(Head) + 40.0, EntityPitch(Camera), 40.0), EntityYaw(Camera), EntityRoll(Camera))
			EndIf
			
			HeadDropSpeed# = HeadDropSpeed - 0.002 * FPSfactor
		EndIf
		
		If InvertMouse Then
			TurnEntity (Camera, -MouseYSpeed() * 0.05 * FPSfactor, -MouseXSpeed() * 0.15 * FPSfactor, 0)
		Else
			TurnEntity (Camera, MouseYSpeed() * 0.05 * FPSfactor, -MouseXSpeed() * 0.15 * FPSfactor, 0)
		End If
		
	EndIf
	
	;p?¶lyhiukkasia
	If ParticleAmount=2
		If Rand(35) = 1 Then
			Local pvt% = CreatePivot()
			PositionEntity(pvt, EntityX(Camera, True), EntityY(Camera, True), EntityZ(Camera, True))
			RotateEntity(pvt, 0, Rnd(360), 0)
			If Rand(2) = 1 Then
				MoveEntity(pvt, 0, Rnd(-0.5, 0.5), Rnd(0.5, 1.0))
			Else
				MoveEntity(pvt, 0, Rnd(-0.5, 0.5), Rnd(0.5, 1.0))
			End If
			
			Local p.Particles = CreateParticle(EntityX(pvt), EntityY(pvt), EntityZ(pvt), 2, 0.002, 0, 300)
			p\speed = 0.001
			RotateEntity(p\pvt, Rnd(-20, 20), Rnd(360), 0)
			
			p\SizeChange = -0.00001
			
			FreeEntity pvt
		End If
	EndIf
	
	; -- Limit the mouse;s movement. Using this method produces smoother mouselook movement than centering the mouse Each loop.
	If Turn Then
		;local canturn = true
		;If not ((ScaledMouseX() > mouse_right_limit) Or (ScaledMouseX() < mouse_left_limit) Or (ScaledMouseY() > mouse_bottom_limit) Or (ScaledMouseY() < mouse_top_limit)) then canturn = false
		;if canturn then 
		MoveMouse viewport_center_x, viewport_center_y
	Else
		ResetMouse()
	EndIf
	If WearingGasMask Or WearingHazmat Or Wearing1499 Then
		If Wearing714 = False Then
			If WearingGasMask = 2 Or Wearing1499 = 2 Or WearingHazmat = 2 Then
				Stamina = Min(100, Stamina + (100.0-Stamina)*0.01*FPSfactor)
			EndIf
		EndIf
		If WearingHazmat = 1 Then
			Stamina = Min(60, Stamina)
		EndIf
		
		ShowEntity(GasMaskOverlay)
	Else
		HideEntity(GasMaskOverlay)
	End If
	
	If (Not WearingNightVision=0) Then
		ShowEntity(NVOverlay)
		If WearingNightVision=2 Then
			EntityColor(NVOverlay, 0,100,255)
			AmbientLightRooms(15)
		ElseIf WearingNightVision=3 Then
			EntityColor(NVOverlay, 255,0,0)
			AmbientLightRooms(15)
		Else
			EntityColor(NVOverlay, 0,255,0)
			AmbientLightRooms(15)
		EndIf
		EntityTexture(Fog, FogNVTexture)
	Else
		AmbientLightRooms(0)
		HideEntity(NVOverlay)
		EntityTexture(Fog, FogTexture)
	EndIf
	
	For i = 0 To 5
		If SCP1025state[i]>0 Then
			Select i
				Case 0 ;common cold
					If FPSfactor>0 Then 
						If Rand(1000)=1 Then
							If CoughCHN = 0 Then
								CoughCHN = PlaySound_Strict(CoughSFX(Rand(0, 2)))
							Else
								If Not ChannelPlaying(CoughCHN) Then CoughCHN = PlaySound_Strict(CoughSFX(Rand(0, 2)))
							End If
							multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
						EndIf
					EndIf
					Stamina = Stamina - FPSfactor * 0.3
				Case 1 ;chicken pox
					If Rand(9000)=1 And Msg="" Then
						Msg="Your skin is feeling itchy."
						MsgTimer =70*4
					EndIf
				Case 2 ;cancer of the lungs
					If FPSfactor>0 Then 
						If Rand(800)=1 Then
							If CoughCHN = 0 Then
								CoughCHN = PlaySound_Strict(CoughSFX(Rand(0, 2)))
							Else
								If Not ChannelPlaying(CoughCHN) Then CoughCHN = PlaySound_Strict(CoughSFX(Rand(0, 2)))
							End If
							multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
						EndIf
					EndIf
					Stamina = Stamina - FPSfactor * 0.1
				Case 3 ;appendicitis
					;0.035/sec = 2.1/min
					If (Not I_427\Using And I_427\Timer < 70*360) Then
						SCP1025state[i]=SCP1025state[i]+FPSfactor*0.0005
					EndIf
					If SCP1025state[i]>20.0 Then
						If SCP1025state[i]-FPSfactor<=20.0 Then 
							Msg="The pain in your stomach is becoming unbearable."
							MsgTimer = 70*4
						EndIf
						Stamina = Stamina - FPSfactor * 0.3
					ElseIf SCP1025state[i]>10.0
						If SCP1025state[i]-FPSfactor<=10.0 Then 
							Msg="Your stomach is aching."
							MsgTimer = 70*4
						EndIf
					EndIf
				Case 4 ;asthma
					If Stamina < 35 Then
						If Rand(Int(140+Stamina*8))=1 Then
							If CoughCHN = 0 Then
								CoughCHN = PlaySound_Strict(CoughSFX(Rand(0, 2)))
							Else
								If Not ChannelPlaying(CoughCHN) Then CoughCHN = PlaySound_Strict(CoughSFX(Rand(0, 2)))
							End If
						EndIf
						multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
						CurrSpeed = CurveValue(0, CurrSpeed, 10+Stamina*15)
					EndIf
				Case 5;cardiac arrest
					If (Not I_427\Using And I_427\Timer < 70*360) Then
						SCP1025state[i]=SCP1025state[i]+FPSfactor*0.35
					EndIf
					;35/sec
					If SCP1025state[i]>110 Then
						HeartBeatRate=0
						BlurTimer = Max(BlurTimer, 500)
						If SCP1025state[i]>140 Then 
							DeathMSG = Chr(34)+"He died of a cardiac arrest after reading SCP-1025, that's for sure. Is there such a thing as psychosomatic cardiac arrest, or does SCP-1025 have some "
							DeathMSG = DeathMSG + "anomalous properties we are not yet aware of?"+Chr(34)
							Kill("died of a cardiac arrest")
						EndIf
					Else
						HeartBeatRate=Max(HeartBeatRate, 70+SCP1025state[i])
						HeartBeatVolume = 1.0
					EndIf
			End Select 
		EndIf
	Next
	
	
End Function

Function UpdateExistAnimation()
	If Crouch = 1 Then PLAYER_MOVE = PLAYER_SITTING_IDLING Else PLAYER_MOVE = PLAYER_IDLING
	Select MyPlayer\BreachType
		Case MODEL_096
			If SCP\rageStart <> 0.0 Then
				PLAYER_MOVE = PLAYER_CRYING
			EndIf
			If SCP\Raged Then
				If PLAYER_MOVE = PLAYER_SITTING_IDLING Or PLAYER_MOVE = PLAYER_IDLING Or PLAYER_MOVE = PLAYER_WALKING Then PLAYER_MOVE = PLAYER_CRYING
				;if (Not ChannelPlaying(MyPlayer\SoundChn)) Then 
				;	MyPlayer\SoundChn = PlaySound_Strict(LoadTempSound("SFX\SCP\096\Scream.ogg"))
				;EndIf
			ElseIf Not(SCP\rageStart <> 0.0) Then
				If PLAYER_MOVE = PLAYER_RUNNING Then PLAYER_MOVE = PLAYER_WALKING
			EndIf
	End Select
End Function

;--------------------------------------- GUI, menu etc ------------------------------------------------

Function DrawGUI()
	;CatchErrors("Uncaught (DrawGUI)")
	
	Local temp%, x%, y%, z%, i%, yawvalue#, pitchvalue#
	Local x2#,y2#,z2#
	Local n%, xtemp, ytemp, strtemp$
	
	Local e.Events, it.Items
	
	If Not LockMouse Then
		If MenuOpen Or ConsoleOpen Or SelectedDoor <> Null Or InvOpen Or OtherOpen<>Null Or EndingTimer < 0 Or TAB_MENU_STATE > 1 Or NetworkServer\chatOpen Then
			ShowPointer()
		Else
			HidePointer()
		EndIf
	EndIf
	If PlayerIntercom\Picked Then
		AASetFont Font1
		
		seconds = ((PlayerIntercom\CheckIntercom-MilliSecs()) / 1000) Mod 60
		minutes = (PlayerIntercom\CheckIntercom-MilliSecs()) / (1000*60) Mod 60
		
		If MyPlayer\Announcement Then
		
			seconds = ((PlayerIntercom\TimeIntercom-MilliSecs()) / 1000) Mod 60
			minutes = (PlayerIntercom\TimeIntercom-MilliSecs()) / (1000*60) Mod 60
			secstr$ = seconds
			If seconds < 10 Then secstr = "0"+seconds
			
			AAText GraphicWidth/2-50, GraphicHeight/2+100, "YOU CAN SPEAKING (left: "+minutes+":"+secstr+")"
			
		ElseIf PlayerIntercom\CheckIntercom > MilliSecs() And seconds+minutes <> 0 Then
			seconds = ((PlayerIntercom\CheckIntercom-MilliSecs()) / 1000) Mod 60
			minutes = (PlayerIntercom\CheckIntercom-MilliSecs()) / (1000*60) Mod 60
			secstr$ = seconds
			If seconds < 10 Then secstr = "0"+seconds
			
			AAText GraphicWidth/2-50, GraphicHeight/2+100, "YOU CAN SPEAK IN "+minutes+":"+secstr
		Else
			AAText GraphicWidth/2-50, GraphicHeight/2+100, "PRESS LMB TO ENABLE"
		EndIf
	ElseIf PlayerRoom <> Null Then
		If MyPlayer\Announcement Then
			If (Not (Distance3(PlayerRoom\x-265.0*RoomScale, PlayerRoom\y+1280.0*RoomScale, PlayerRoom\z+105.0*RoomScale, EntityX(Collider), EntityY(Collider), EntityZ(Collider)) < 3)) Or (Not PlayerRoom\RoomTemplate\Name = "room2ccont") Then 
				UseIntercom()
			EndIf
		EndIf
	EndIf
	If PlayerRoom\RoomTemplate\Name = "pocketdimension" Then
		For e.Events = Each Events
			If e\room = PlayerRoom Then
				If Float(e\EventStr)<1000.0 Then
					If e\EventState > 600 Then
						If BlinkTimer < -3 And BlinkTimer > -10 Then
							If e\img = 0 Then
								If BlinkTimer > -5 And Rand(30)=1 Then
									PlaySound_Strict DripSFX(0)
									If e\img = 0 Then e\img = LoadImage_Strict("GFX\npcs\106face.jpg")
								EndIf
							Else
								DrawImage e\img, GraphicWidth/2-Rand(390,310), GraphicHeight/2-Rand(290,310)
							EndIf
						Else
							If e\img <> 0 Then FreeImage e\img : e\img = 0
						EndIf
							
						Exit
					EndIf
				Else
					If BlinkTimer < -3 And BlinkTimer > -10 Then
						If e\img = 0 Then
							If BlinkTimer > -5 Then
								If e\img = 0 Then
									e\img = LoadImage_Strict("GFX\kneelmortal.pd")
									If (ChannelPlaying(e\SoundCHN)) Then
										StopChannel(e\SoundCHN)
									EndIf
									e\SoundCHN = PlaySound_Strict(e\Sound)
								EndIf
							EndIf
						Else
							DrawImage e\img, GraphicWidth/2-Rand(390,310), GraphicHeight/2-Rand(290,310)
						EndIf
					Else
						If e\img <> 0 Then FreeImage e\img : e\img = 0
						If BlinkTimer < -3 Then
							If (Not ChannelPlaying(e\SoundCHN)) Then
								e\SoundCHN = PlaySound_Strict(e\Sound)
							EndIf
						Else
							If (ChannelPlaying(e\SoundCHN)) Then
								StopChannel(e\SoundCHN)
							EndIf
						EndIf
					EndIf
					
					Exit
				EndIf
			EndIf
		Next
	EndIf
	If EqquipedGun <> Null Then DrawGunGUI()
	If Spectate\SpectatePlayer = -1 Then
		If ClosestButton <> 0 And SelectedDoor = Null And InvOpen = False And MenuOpen = False And OtherOpen = Null Then
			temp% = CreatePivot()
			PositionEntity temp, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
			PointEntity temp, ClosestButton
			yawvalue# = WrapAngle(EntityYaw(Camera) - EntityYaw(temp))
			If yawvalue > 90 And yawvalue <= 180 Then yawvalue = 90
			If yawvalue > 180 And yawvalue < 270 Then yawvalue = 270
			pitchvalue# = WrapAngle(EntityPitch(Camera) - EntityPitch(temp))
			If pitchvalue > 90 And pitchvalue <= 180 Then pitchvalue = 90
			If pitchvalue > 180 And pitchvalue < 270 Then pitchvalue = 270
			
			FreeEntity (temp)
			
			DrawImage(HandIcon, GraphicWidth / 2 + Sin(yawvalue) * (GraphicWidth / 3) - 32, GraphicHeight / 2 - Sin(pitchvalue) * (GraphicHeight / 3) - 32)
			
			If IsMouseUp() Then
				If ClosestDoor <> Null Then 
					If ClosestDoor\Code <> "" Then
						SelectedDoor = ClosestDoor
					ElseIf Playable Then
						PlaySound2(ButtonSFX, Camera, ClosestButton)
						multiplayer_WriteSound ButtonSFX
						MouseUp1 = False
						If UseDoor(ClosestDoor,True) And (Not ClosestDoor\IsElevatorDoor) Then StartMilliSecs(0, 1000)
					EndIf
				EndIf
			EndIf
		EndIf
	;	if format_out Then FreeSoundLib()
	;EndIf
		If ClosestItem <> Null Then
			yawvalue# = -DeltaYaw(Camera, ClosestItem\collider)
			If yawvalue > 90 And yawvalue <= 180 Then yawvalue = 90
			If yawvalue > 180 And yawvalue < 270 Then yawvalue = 270
			pitchvalue# = -DeltaPitch(Camera, ClosestItem\collider)
			If pitchvalue > 90 And pitchvalue <= 180 Then pitchvalue = 90
			If pitchvalue > 180 And pitchvalue < 270 Then pitchvalue = 270
			
			Color 255,255,255
			AASetFont Font1
			AAText(GraphicWidth / 2 + Sin(yawvalue) * (GraphicWidth / 3),GraphicHeight / 2 - Sin(pitchvalue) * (GraphicHeight / 3) - 70, ClosestItem\name, True)
			DrawImage(HandIcon2, GraphicWidth / 2 + Sin(yawvalue) * (GraphicWidth / 3) - 32, GraphicHeight / 2 - Sin(pitchvalue) * (GraphicHeight / 3) - 32)
		EndIf
		
		If DrawHandIcon Then DrawImage(HandIcon, GraphicWidth / 2 - 32, GraphicHeight / 2 - 32)
		For i = 0 To 3
			If DrawArrowIcon(i) Then
				x = GraphicWidth / 2 - 32
				y = GraphicHeight / 2 - 32		
				Select i
					Case 0
						y = y - 64 - 5
					Case 1
						x = x + 64 + 5
					Case 2
						y = y + 64 + 5
					Case 3
						x = x - 5 - 64
				End Select
				DrawImage(HandIcon, x, y)
				Color 0, 0, 0
				Rect(x + 4, y + 4, 64 - 8, 64 - 8)
				DrawImage(ArrowIMG(i), x + 21, y + 21)
				DrawArrowIcon(i) = False
			End If
		Next
		If Using294 Then Use294()
		
		If HUDenabled Then 
			
			Local width% = 204, height% = 20
			x% = 80
			y% = GraphicHeight - 95
			
			Color 255, 255, 255	
			Rect (x, y, width, height, False)
			
			iint = Int(((width - 2) * (BlinkTimer / (BLINKFREQ))) / 10)
			For i = 1 To iint
				DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
			Next
			Color 0, 0, 0
			Rect(x - 50, y, 30, 30)
			
			If EyeIrritation > 0 Then
				Color 200, 0, 0
				Rect(x - 50 - 3, y - 3, 30 + 6, 30 + 6)
			End If
			
			Color 255, 255, 255
			Rect(x - 50 - 1, y - 1, 30 + 2, 30 + 2, False)
			
			DrawImage BlinkIcon, x - 50, y
			
			y = GraphicHeight - 55
			Color 255, 255, 255
			Rect (x, y, width, height, False)
			
			iint = Int(((width - 2) * (Stamina / 100.0)) / 10)
			For i = 1 To iint
				DrawImage(StaminaMeterIMG, x + 3 + 10 * (i - 1), y + 3)
			Next	
			
			Color 0, 0, 0
			Rect(x - 50, y, 30, 30)
			
			Color 255, 255, 255
			Rect(x - 50 - 1, y - 1, 30 + 2, 30 + 2, False)
			If Crouch Then
				DrawImage CrouchIcon, x - 50, y
			Else
				DrawImage SprintIcon, x - 50, y
			EndIf
			
			If DebugHUD Then
				Color 255, 255, 255
				AASetFont ConsoleFont
				
				;Text x + 250, 50, "Zone: " + (EntityZ(Collider)/8.0)
				AAText x - 50, 50, "Player Position: (" + f2s(EntityX(Collider), 3) + ", " + f2s(EntityY(Collider), 3) + ", " + f2s(EntityZ(Collider), 3) + ")"
				AAText x - 50, 70, "Camera Position: (" + f2s(EntityX(Camera), 3)+ ", " + f2s(EntityY(Camera), 3) +", " + f2s(EntityZ(Camera), 3) + ")"
				AAText x - 50, 100, "Player Rotation: (" + f2s(EntityPitch(Collider), 3) + ", " + f2s(EntityYaw(Collider), 3) + ", " + f2s(EntityRoll(Collider), 3) + ")"
				AAText x - 50, 120, "Camera Rotation: (" + f2s(EntityPitch(Camera), 3)+ ", " + f2s(EntityYaw(Camera), 3) +", " + f2s(EntityRoll(Camera), 3) + ")"
				AAText x - 50, 150, "Room: " + PlayerRoom\RoomTemplate\Name
				For ev.Events = Each Events
					If ev\room = PlayerRoom Then
						AAText x - 50, 170, "Room event: " + ev\EventName   
						AAText x - 50, 190, "state: " + ev\EventState
						AAText x - 50, 210, "state2: " + ev\EventState2   
						AAText x - 50, 230, "state3: " + ev\EventState3
						AAText x - 50, 250, "str: "+ ev\EventStr
						Exit
					EndIf
				Next
				AAText x - 50, 280, "Room coordinates: (" + Floor(EntityX(PlayerRoom\obj) / 8.0 + 0.5) + ", " + Floor(EntityZ(PlayerRoom\obj) / 8.0 + 0.5) + ", angle: "+PlayerRoom\angle + ")"
				AAText x - 50, 300, "Stamina: " + f2s(Stamina, 3)
				AAText x - 50, 320, "Death timer: " + f2s(KillTimer, 3)               
				AAText x - 50, 340, "Blink timer: " + f2s(BlinkTimer, 3)
				AAText x - 50, 360, "Injuries: " + Injuries
				AAText x - 50, 380, "PREV_PLAYER_MOVE: " + PREV_PLAYER_MOVE + " | PLAYER_MOVE : "+ PLAYER_MOVE+ " | PLAYER_MOVE_TIMED: "+PLAYER_MOVE_TIMED
				If Curr173 <> Null
					AAText x - 50, 410, "SCP - 173 Position (collider): (" + f2s(EntityX(Curr173\Collider), 3) + ", " + f2s(EntityY(Curr173\Collider), 3) + ", " + f2s(EntityZ(Curr173\Collider), 3) + ")"
					AAText x - 50, 430, "SCP - 173 Position (obj): (" + f2s(EntityX(Curr173\obj), 3) + ", " + f2s(EntityY(Curr173\obj), 3) + ", " + f2s(EntityZ(Curr173\obj), 3) + ")"
					;Text x - 50, 410, "SCP - 173 Idle: " + Curr173\Idle
					AAText x - 50, 450, "SCP - 173 State: " + Curr173\State
				EndIf
				If Curr106 <> Null
					AAText x - 50, 470, "SCP - 106 Position: (" + f2s(EntityX(Curr106\obj), 3) + ", " + f2s(EntityY(Curr106\obj), 3) + ", " + f2s(EntityZ(Curr106\obj), 3) + ")"
					AAText x - 50, 490, "SCP - 106 Idle: " + Curr106\Idle
					AAText x - 50, 510, "SCP - 106 State: " + Curr106\State
				EndIf
				offset% = 0
				For npc.NPCs = Each NPCs
					If npc\NPCtype = NPCtype096 Then
						AAText x - 50, 530, "SCP - 096 Position: (" + f2s(EntityX(npc\obj), 3) + ", " + f2s(EntityY(npc\obj), 3) + ", " + f2s(EntityZ(npc\obj), 3) + ")"
						AAText x - 50, 550, "SCP - 096 Idle: " + npc\Idle
						AAText x - 50, 570, "SCP - 096 State: " + npc\State
						AAText x - 50, 590, "SCP - 096 Speed: " + f2s(npc\currspeed, 5)
					EndIf
					If npc\NPCtype = NPCtypeMTF Then
						AAText x - 50, 620 + 60 * offset, "MTF " + offset + " Position: (" + f2s(EntityX(npc\obj), 3) + ", " + f2s(EntityY(npc\obj), 3) + ", " + f2s(EntityZ(npc\obj), 3) + ")"
						AAText x - 50, 640 + 60 * offset, "MTF " + offset + " State: " + npc\State
						AAText x - 50, 660 + 60 * offset, "MTF " + offset + " LastSeen: " + npc\lastseen					
						offset = offset + 1
					EndIf
				Next
				If PlayerRoom\RoomTemplate\Name$ = "dimension1499"
					AAText x + 350, 50, "Current Chunk X/Z: ("+(Int((EntityX(Collider)+20)/40))+", "+(Int((EntityZ(Collider)+20)/40))+")"
					Local CH_Amount% = 0
					For ch.Chunk = Each Chunk
						CH_Amount = CH_Amount + 1
					Next
					AAText x + 350, 70, "Current Chunk Amount: "+CH_Amount
				Else
					AAText x + 350, 50, "Current Room Position: ("+PlayerRoom\x+", "+PlayerRoom\y+", "+PlayerRoom\z+")"
				EndIf
				GlobalMemoryStatus m.MEMORYSTATUS
				AAText x + 350, 90, (m\dwAvailPhys%/1024/1024)+" MB/"+(m\dwTotalPhys%/1024/1024)+" MB ("+(m\dwAvailPhys%/1024)+" KB/"+(m\dwTotalPhys%/1024)+" KB)"
				AAText x + 350, 110, "Triangles rendered: "+CurrTrisAmount
				AAText x + 350, 130, "Active textures: "+ActiveTextures()
				AAText x + 350, 150, "SCP-427 state (secs): "+Int(I_427\Timer/70.0)
				AAText x + 350, 170, "SCP-008 infection: "+Infect
				For i = 0 To 5
					AAText x + 350, 190+(20*i), "SCP-1025 State "+i+": "+SCP1025state[i]
				Next
				If SelectedMonitor <> Null Then
					AAText x + 350, 310, "Current monitor: "+SelectedMonitor\ScrObj
				Else
					AAText x + 350, 310, "Current monitor: NULL"
				EndIf
				AAText x + 350, 330, "Received (KB): "+udp_network\ReceivedAvail
				AAText x + 350, 350, "Writed (KB): "+udp_network\WriteAvail
				
				AASetFont Font1
			EndIf
			
		EndIf
		
		If SelectedScreen <> Null Then
			DrawImage SelectedScreen\img, GraphicWidth/2-ImageWidth(SelectedScreen\img)/2,GraphicHeight/2-ImageHeight(SelectedScreen\img)/2
			
			If IsMouseUp() Or MouseHit2 Then
				SelectedScreen = Null
				MouseUp1 = False
			EndIf
		EndIf
		
		Local PrevInvOpen% = InvOpen, MouseSlot% = 66
		
		Local shouldDrawHUD%=True
		If SelectedDoor <> Null Then
			SelectedItem = Null
			
			If shouldDrawHUD Then
				pvt = CreatePivot()
				PositionEntity pvt, EntityX(ClosestButton,True),EntityY(ClosestButton,True),EntityZ(ClosestButton,True)
				RotateEntity pvt, 0, EntityYaw(ClosestButton,True)-180,0
				MoveEntity pvt, 0,0,0.22
				PositionEntity Camera, EntityX(pvt),EntityY(pvt),EntityZ(pvt)
				PointEntity Camera, ClosestButton
				FreeEntity pvt	
				
				CameraProject(Camera, EntityX(ClosestButton,True),EntityY(ClosestButton,True)+MeshHeight(ButtonOBJ)*0.015,EntityZ(ClosestButton,True))
				projY# = ProjectedY()
				CameraProject(Camera, EntityX(ClosestButton,True),EntityY(ClosestButton,True)-MeshHeight(ButtonOBJ)*0.015,EntityZ(ClosestButton,True))
				scale# = (ProjectedY()-projy)/462.0
				
				x = GraphicWidth/2-ImageWidth(KeypadHUD)*scale/2
				y = GraphicHeight/2-ImageHeight(KeypadHUD)*scale/2		
				
				AASetFont Font3
				If KeypadMSG <> "" Then 
					KeypadTimer = KeypadTimer-FPSfactor2
					
					If (KeypadTimer Mod 70) < 35 Then AAText GraphicWidth/2, y+124*scale, KeypadMSG, True,True
					If KeypadTimer =<0 Then
						KeypadMSG = ""
						SelectedDoor = Null
						ResetMouse()
					EndIf
				Else
					AAText GraphicWidth/2, y+70*scale, "ACCESS CODE: ",True,True	
					AASetFont Font4
					AAText GraphicWidth/2, y+124*scale, KeypadInput,True,True	
				EndIf
				
				x = x+44*scale
				y = y+249*scale
				
				For n = 0 To 3
					For i = 0 To 2
						xtemp = x+Int(58.5*scale*n)
						ytemp = y+(67*scale)*i
						
						temp = False
						If MouseOn(xtemp,ytemp, 54*scale,65*scale) And KeypadMSG = "" Then
							If MouseUp1 Then 
								PlaySound_Strict ButtonSFX
								multiplayer_WriteSound ButtonSFX
								Select (n+1)+(i*4)
									Case 1,2,3
										KeypadInput=KeypadInput + ((n+1)+(i*4))
									Case 4
										KeypadInput=KeypadInput + "0"
									Case 5,6,7
										KeypadInput=KeypadInput + ((n+1)+(i*4)-1)
									Case 8 ;enter
										If SelectedDoor\ChangedCode Then
											multiplayer_SendDoor(SelectedDoor, Null, KeypadInput)
										Else
											If KeypadInput = SelectedDoor\Code Then
												PlaySound_Strict ScannerSFX1
												
												If SelectedDoor\Code = Str(AccessCode) Then
													GiveAchievement(AchvMaynard)
												ElseIf SelectedDoor\Code = "7816"
													GiveAchievement(AchvHarp)
												EndIf									
												
												SelectedDoor\locked = 0
												UseDoor(SelectedDoor,True,True,True,SelectedDoor\Code)
												SelectedDoor = Null
												ResetMouse()
											Else
												If udp_GetStream() Then multiplayer_SendDoor(SelectedDoor, Null, KeypadInput)
												PlaySound_Strict ScannerSFX2
												KeypadMSG = "ACCESS DENIED"
												KeypadTimer = 210
												KeypadInput = ""	
											EndIf
										EndIf
									Case 9,10,11
										KeypadInput=KeypadInput + ((n+1)+(i*4)-2)
									Case 12
										KeypadInput = ""
								End Select 
								If SelectedDoor <> Null Then
									If Len(SelectedDoor\Code) <> 8 Then
										If Len(KeypadInput)> 4 Then KeypadInput = Left(KeypadInput,4)
									Else
										If Len(KeypadInput) > 8 Then KeypadInput = Left(KeypadInput,4)
									EndIf
								Else
									If Len(KeypadInput) > 8 Then KeypadInput = Left(KeypadInput,4)
								EndIf
							EndIf
							
						Else
							temp = False
						EndIf
						
					Next
				Next
				
				If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
				
				If MouseHit2 Then
					SelectedDoor = Null
					ResetMouse()
				EndIf
			Else
				SelectedDoor = Null
			EndIf
		Else
			KeypadInput = ""
			KeypadTimer = 0
			KeypadMSG = ""
		EndIf
	EndIf
	If KeyHit(1) And EndingTimer=0 And (Not Using294) Then
		If OptionsMenu > 0 Then SaveOptionsINI()
		MenuOpen = (Not MenuOpen)
		MENU_OPEN_TYPE = MenuOpen

		AchievementsMenu = 0
		OptionsMenu = 0
		QuitMSG = 0
		SuicideMSG = 0
		
		
		SelectedDoor = Null
		SelectedScreen = Null
		SelectedMonitor = Null
		If SelectedItem <> Null Then
			If Instr(SelectedItem\itemtemplate\tempname,"vest") Or Instr(SelectedItem\itemtemplate\tempname,"hazmatsuit") Then
				If (Not WearingVest) And (Not WearingHazmat) Then
					DropItem(SelectedItem)
				EndIf
				SelectedItem = Null
			EndIf
		EndIf
		If Not udp_GetStream() Then
			If (Not MenuOpen) Then 
				ResumeSounds()
				ResetMouse()
			Else
				PauseSounds()
			EndIf
		EndIf
	EndIf
	
	Local spacing%
	Local PrevOtherOpen.Items
	
	Local OtherSize%,OtherAmount%
	
	Local isEmpty%
	
	Local isMouseOn%
	
	Local closedInv%
	
	If OtherOpen<>Null Then
		Stated = False
		;[Block] 
		If (PlayerRoom\RoomTemplate\Name = "gatea") Then
			If IsCoopMode() Then
				HideEntity Fog
				CameraFogRange Camera, 5,30
				CameraFogColor (Camera,200,200,200)
				CameraClsColor (Camera,200,200,200)					
				CameraRange(Camera, 0.05, 50)
			Else
				;CameraFogRange Camera, 5,30
				CameraFogColor (Camera,0,0,0)
				CameraClsColor (Camera,0,0,0)					
				;CameraRange(Camera, 0.05, 50)
			EndIf
		Else If (PlayerRoom\RoomTemplate\Name = "exit1") And (EntityY(Collider)>1040.0*RoomScale)
			If IsCoopMode() Then
				HideEntity Fog
				CameraFogRange Camera, 5,45
				CameraFogColor (Camera,200,200,200)
				CameraClsColor (Camera,200,200,200)					
				CameraRange(Camera, 0.05, 60)
			Else
				;CameraFogRange Camera, 5,45
				CameraFogColor (Camera,0,0,0)
				CameraClsColor (Camera,0,0,0)					
				;CameraRange(Camera, 0.05, 60)
			EndIf
		EndIf
		
		PrevOtherOpen = OtherOpen
		OtherSize=OtherOpen\invSlots;Int(OtherOpen\state2)
		
		For i%=0 To OtherSize-1
			If OtherOpen\SecondInv[i] <> Null Then
				OtherAmount = OtherAmount+1
			EndIf
		Next
		
		;If OtherAmount > 0 Then
		;	OtherOpen\state = 1.0
		;Else
		;	OtherOpen\state = 0.0
		;EndIf
		InvOpen = False
		SelectedDoor = Null
		Local tempX% = 0
		
		width = 70
		height = 70
		spacing% = 35
		
		x = GraphicWidth / 2 - (width * MaxItemAmount /2 + spacing * (MaxItemAmount / 2 - 1)) / 2
		y = GraphicHeight / 2 - (height * OtherSize /5 + height * (OtherSize / 5 - 1)) / 2;height
		
		ItemAmount = 0
		For  n% = 0 To OtherSize - 1
			isMouseOn% = False
			If ScaledMouseX() > x And ScaledMouseX() < x + width Then
				If ScaledMouseY() > y And ScaledMouseY() < y + height Then
					isMouseOn = True
				EndIf
			EndIf
			
			If isMouseOn Then
				MouseSlot = n
				Color 255, 0, 0
				Rect(x - 1, y - 1, width + 2, height + 2)
			EndIf
			
			DrawFrame(x, y, width, height, (x Mod 64), (x Mod 64))
			
			If OtherOpen = Null Then Exit
			
			If OtherOpen\SecondInv[n] <> Null Then
				If (SelectedItem <> OtherOpen\SecondInv[n] Or isMouseOn) Then DrawImage(OtherOpen\SecondInv[n]\invimg, x + width / 2 - 32, y + height / 2 - 32)
			EndIf
			DebugLog "otheropen: "+(OtherOpen<>Null)
			If OtherOpen\SecondInv[n] <> Null And SelectedItem <> OtherOpen\SecondInv[n] Then
			;drawimage(OtherOpen\SecondInv[n].InvIMG, x + width / 2 - 32, y + height / 2 - 32)
				If isMouseOn Then
					
					AASetFont Font1
					Color 0,0,0
					AAText(x + width / 2 + 1, y + height + spacing - 15 + 1, OtherOpen\SecondInv[n]\itemtemplate\name, True)
					
					Color 255, 255, 255	
					AAText(x + width / 2, y + height + spacing - 15, OtherOpen\SecondInv[n]\itemtemplate\name, True)				
					If SelectedItem = Null Then
						If MouseHit1 Then
							SelectedItem = OtherOpen\SecondInv[n]
							MouseHit1 = False
							
							If DoubleClick Then
								If OtherOpen\SecondInv[n]\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(OtherOpen\SecondInv[n]\itemtemplate\sound))
								OtherOpen = Null
								closedInv=True
								InvOpen = False
								DoubleClick = False
								BlockGuns = True
							EndIf
							
						EndIf
					Else
						
					EndIf
				EndIf
				
				ItemAmount=ItemAmount+1
			Else
				If isMouseOn And MouseHit1 Then
					For z% = 0 To OtherSize - 1
						If OtherOpen\SecondInv[z] = SelectedItem Then OtherOpen\SecondInv[z] = Null
					Next
					OtherOpen\SecondInv[n] = SelectedItem
				EndIf
				
			EndIf					
			
			x=x+width + spacing
			tempX=tempX + 1
			If tempX = 5 Then 
				tempX=0
				y = y + height*2 
				x = GraphicWidth / 2 - (width * MaxItemAmount /2 + spacing * (MaxItemAmount / 2 - 1)) / 2
			EndIf
		Next
		
		If SelectedItem <> Null Then
			If MouseDown1 Then
				If MouseSlot = 66 Then
					DrawImage(SelectedItem\invimg, ScaledMouseX() - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, ScaledMouseY() - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
				ElseIf SelectedItem <> PrevOtherOpen\SecondInv[MouseSlot]
					DrawImage(SelectedItem\invimg, ScaledMouseX() - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, ScaledMouseY() - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
				EndIf
			Else
				If MouseSlot = 66 Then
					DropItem(SelectedItem)
					OtherSize = OtherOpen\invSlots
					For z% = 0 To OtherSize - 1
						If OtherOpen\SecondInv[z] = SelectedItem Then OtherOpen\SecondInv[z] = Null
					Next
					
					isEmpty=True
					If OtherOpen\itemtemplate\tempname = "wallet" Then
						If (Not isEmpty) Then
							For z% = 0 To OtherSize - 1
								If OtherOpen\SecondInv[z]<>Null
									Local name$=OtherOpen\SecondInv[z]\itemtemplate\tempname
									If name$<>"25ct" And name$<>"coin" And name$<>"key" And name$<>"scp860" And name$<>"scp714" Then
										isEmpty=False
										Exit
									EndIf
								EndIf
							Next
						EndIf
					Else
						For z% = 0 To OtherSize - 1
							If OtherOpen\SecondInv[z]<>Null
								isEmpty = False
								Exit
							EndIf
						Next
					EndIf
					
					If isEmpty Then
						Select OtherOpen\itemtemplate\tempname
							Case "clipboard"
								OtherOpen\invimg = OtherOpen\itemtemplate\invimg2
								SetAnimTime OtherOpen\model,17.0
							Case "wallet"
								SetAnimTime OtherOpen\model,0.0
						End Select
					EndIf
					
					SelectedItem = Null
					OtherOpen = Null
					closedInv=True
					
					MoveMouse viewport_center_x, viewport_center_y
				Else
					
					If PrevOtherOpen\SecondInv[MouseSlot] = Null Then
						For z% = 0 To OtherSize - 1
							If PrevOtherOpen\SecondInv[z] = SelectedItem Then PrevOtherOpen\SecondInv[z] = Null
						Next
						PrevOtherOpen\SecondInv[MouseSlot] = SelectedItem
						SelectedItem = Null
					ElseIf PrevOtherOpen\SecondInv[MouseSlot] <> SelectedItem
						Select SelectedItem\itemtemplate\tempname
							Default
								Msg = "You cannot combine these two items."
								MsgTimer = 70 * 5
						End Select					
					EndIf
					
				EndIf
				SelectedItem = Null
			EndIf
		EndIf
		
		If Fullscreen Then DrawImage CursorIMG,ScaledMouseX(),ScaledMouseY()
		If (closedInv) And (Not InvOpen) Then 
			;ResumeSounds() 
			OtherOpen=Null
			ResetMouse()
		EndIf
		;[End Block]
		
	Else If InvOpen Then
		Stated = False
		If (PlayerRoom\RoomTemplate\Name = "gatea") Then
			If IsCoopMode() Then
				HideEntity Fog
				CameraFogRange Camera, 5,30
				CameraFogColor (Camera,200,200,200)
				CameraClsColor (Camera,200,200,200)					
				CameraRange(Camera, 0.05, 50)
			Else
				;CameraFogRange Camera, 5,30
				CameraFogColor (Camera,0,0,0)
				CameraClsColor (Camera,0,0,0)					
				;CameraRange(Camera, 0.05, 50)
			EndIf
		Else If (PlayerRoom\RoomTemplate\Name = "exit1") And (EntityY(Collider)>1040.0*RoomScale)
			If IsCoopMode() Then
				HideEntity Fog
				CameraFogRange Camera, 5,45
				CameraFogColor (Camera,200,200,200)
				CameraClsColor (Camera,200,200,200)					
				CameraRange(Camera, 0.05, 60)
			Else
				;CameraFogRange Camera, 5,45
				CameraFogColor (Camera,0,0,0)
				CameraClsColor (Camera,0,0,0)					
				;CameraRange(Camera, 0.05, 60)
			EndIf
		EndIf
		
		SelectedDoor = Null
		
		width% = 70
		height% = 70
		spacing% = 35
		
		x = GraphicWidth / 2 - (width * MaxItemAmount /2 + spacing * (MaxItemAmount / 2 - 1)) / 2
		y = GraphicHeight / 2 - (height * MaxItemAmount /5 + height * (MaxItemAmount / 5 - 1)) / 2
		
		ItemAmount = 0
		For  n% = 0 To MaxItemAmount - 1
			If Inventory(n) <> Null Then
				If Inventory(n)\Picked = False Then Inventory(n) = Null
			EndIf
			For a = 0 To MaxItemAmount-1
				If Inventory(a) <> Null And a <> n Then
					If Inventory(a) = Inventory(n) Then Inventory(a) = Null
				EndIf
			Next
			isMouseOn% = False
			If ScaledMouseX() > x And ScaledMouseX() < x + width Then
				If ScaledMouseY() > y And ScaledMouseY() < y + height Then
					isMouseOn = True
				End If
			EndIf
			
			If Inventory(n) <> Null Then
				Color 200, 200, 200
				Select Inventory(n)\itemtemplate\tempname
					Case "gasmask"
						If WearingGasMask=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "supergasmask"
						If WearingGasMask=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "gasmask3"
						If WearingGasMask=3 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "hazmatsuit"
						If WearingHazmat=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "hazmatsuit2"
						If WearingHazmat=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "hazmatsuit3"
						If WearingHazmat=3 Then Rect(x - 3, y - 3, width + 6, height + 6)	
					Case "vest"
						If WearingVest=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "finevest"
						If WearingVest=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "scp714"
						If Wearing714=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
						;BoH items
					;Case "ring"
					;	If Wearing714=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					;Case "scp178"
					;	If Wearing178=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
					;Case "glasses"
					;	If Wearing178=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "nvgoggles"
						If WearingNightVision=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "supernv"
						If WearingNightVision=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "scp1499"
						If Wearing1499=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "super1499"
						If Wearing1499=2 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "finenvgoggles"
						If WearingNightVision=3 Then Rect(x - 3, y - 3, width + 6, height + 6)
					Case "scp427"
						If I_427\Using=1 Then Rect(x - 3, y - 3, width + 6, height + 6)
				End Select
			EndIf
			
			If isMouseOn Then
				MouseSlot = n
				Color 255, 0, 0
				Rect(x - 1, y - 1, width + 2, height + 2)
			EndIf
			
			Color 255, 255, 255
			DrawFrame(x, y, width, height, (x Mod 64), (x Mod 64))
			
			If Inventory(n) <> Null Then
				If (SelectedItem <> Inventory(n) Or isMouseOn) Then 
					DrawImage(Inventory(n)\invimg, x + width / 2 - 32, y + height / 2 - 32)
				EndIf
			EndIf
			
			If Inventory(n) <> Null And SelectedItem <> Inventory(n) Then
				;drawimage(Inventory(n).InvIMG, x + width / 2 - 32, y + height / 2 - 32)
				If isMouseOn Then
					If SelectedItem = Null Then
						If MouseHit1 Then
							SelectedItem = Inventory(n)
							MouseHit1 = False
							
							If DoubleClick Then
								If WearingHazmat > 0 And Instr(SelectedItem\itemtemplate\tempname,"hazmatsuit")=0 Then
									Msg = "You cannot use any items while wearing a hazmat suit."
									MsgTimer = 70*5
									SelectedItem = Null
									Return
								EndIf
								;if Inventory(n)\itemtemplate\tempname = "chicken" Then
								;	Inventory(n)\SoundCHN = PlaySound_Strict(EatingSFX)
								;;	multiplayer_WriteSound(EatingSFX, EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0)
								;EndIf
								If Inventory(n)\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(Inventory(n)\itemtemplate\sound))
								If Not MyPlayer\MicroHIDSHOOT Then PickupGun(SelectedItem)
								InvOpen = False
								DoubleClick = False
								BlockGuns = True
							EndIf
							
						EndIf
						
						AASetFont Font1
						Color 0,0,0
						AAText(x + width / 2 + 1, y + height + spacing - 15 + 1, Inventory(n)\name, True)							
						Color 255, 255, 255	
						AAText(x + width / 2, y + height + spacing - 15, Inventory(n)\name, True)	
						
					EndIf
				EndIf
				
				ItemAmount=ItemAmount+1
			Else
				If isMouseOn And MouseHit1 Then
					For z% = 0 To MaxItemAmount - 1
						If Inventory(z) = SelectedItem Then Inventory(z) = Null
					Next
					Inventory(n) = SelectedItem
				End If
				
			EndIf					
			
			x=x+width + spacing
			If n = 4 Then 
				y = y + height*2 
				x = GraphicWidth / 2 - (width * MaxItemAmount /2 + spacing * (MaxItemAmount / 2 - 1)) / 2
			EndIf
		Next
		
		If SelectedItem <> Null Then
			If MouseDown1 Then
				If MouseSlot = 66 Then
					DrawImage(SelectedItem\invimg, ScaledMouseX() - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, ScaledMouseY() - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
				ElseIf SelectedItem <> Inventory(MouseSlot)
					DrawImage(SelectedItem\invimg, ScaledMouseX() - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, ScaledMouseY() - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
				EndIf
			Else
				If MouseSlot = 66 Then
					Select SelectedItem\itemtemplate\tempname
						Case "vest","finevest","hazmatsuit","hazmatsuit2","hazmatsuit3"
							Msg = "Double click on this item to take it off."
							MsgTimer = 70*5
						Case "scp1499","super1499"
							If Wearing1499>0 Then
								Msg = "Double click on this item to take it off."
								MsgTimer = 70*5
							Else
								DropItem(SelectedItem)
								SelectedItem = Null
								InvOpen = False
							EndIf
						Default
							DropItem(SelectedItem)
							SelectedItem = Null
							InvOpen = False
					End Select
					
					MoveMouse viewport_center_x, viewport_center_y
				Else
					If Inventory(MouseSlot) = Null Then
						For z% = 0 To MaxItemAmount - 1
							If Inventory(z) = SelectedItem Then Inventory(z) = Null
						Next
						Inventory(MouseSlot) = SelectedItem
						SelectedItem = Null
					ElseIf Inventory(MouseSlot) <> SelectedItem
						Select SelectedItem\itemtemplate\tempname
							Case "paper","key1","key2","key3","key4","key5","key6","misc","oldpaper","badge","ticket","25ct","coin","key","scp860"
								;[Block]
								If Inventory(MouseSlot)\itemtemplate\tempname = "clipboard" Then
									;Add an item to clipboard
									Local added.Items = Null
									Local b$ = SelectedItem\itemtemplate\tempname
									Local b2$ = SelectedItem\itemtemplate\name
									If (b<>"misc" And b<>"25ct" And b<>"coin" And b<>"key" And b<>"scp860" And b<>"scp714") Or (b2="Playing Card" Or b2="Mastercard") Then
										DebugLog Inventory(MouseSlot)\invSlots
										For c% = 0 To Inventory(MouseSlot)\invSlots-1
											If (Inventory(MouseSlot)\SecondInv[c] = Null)
												If SelectedItem <> Null Then
													Inventory(MouseSlot)\SecondInv[c] = SelectedItem
													Inventory(MouseSlot)\state = 1.0
													SetAnimTime Inventory(MouseSlot)\model,0.0
													Inventory(MouseSlot)\invimg = Inventory(MouseSlot)\itemtemplate\invimg
													
													For ri% = 0 To MaxItemAmount - 1
														If Inventory(ri) = SelectedItem Then
															Inventory(ri) = Null
															PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
														EndIf
													Next
													added = SelectedItem
													SelectedItem = Null : Exit
												EndIf
											EndIf
										Next
										If SelectedItem <> Null Then
											Msg = "The paperclip is not strong enough to hold any more items."
										Else
											If added\itemtemplate\tempname = "paper" Or added\itemtemplate\tempname = "oldpaper" Then
												Msg = "This document was added to the clipboard."
											ElseIf added\itemtemplate\tempname = "badge"
												Msg = added\itemtemplate\name + " was added to the clipboard."
											Else
												Msg = "The " + added\itemtemplate\name + " was added to the clipboard."
											EndIf
											
										EndIf
										MsgTimer = 70 * 5
									Else
										Msg = "You cannot combine these two items."
										MsgTimer = 70 * 5
									EndIf
									;EndIf
								ElseIf Inventory(MouseSlot)\itemtemplate\tempname = "wallet" Then
									;Add an item to clipboard
									added.Items = Null
									b$ = SelectedItem\itemtemplate\tempname
									b2$ = SelectedItem\itemtemplate\name
									If (b<>"misc" And b<>"paper" And b<>"oldpaper") Or (b2="Playing Card" Or b2="Mastercard") Then
										For c% = 0 To Inventory(MouseSlot)\invSlots-1
											If (Inventory(MouseSlot)\SecondInv[c] = Null)
												If SelectedItem <> Null Then
													Inventory(MouseSlot)\SecondInv[c] = SelectedItem
													Inventory(MouseSlot)\state = 1.0
													If b<>"25ct" And b<>"coin" And b<>"key" And b<>"scp860"
														SetAnimTime Inventory(MouseSlot)\model,3.0
													EndIf
													Inventory(MouseSlot)\invimg = Inventory(MouseSlot)\itemtemplate\invimg
													
													For ri% = 0 To MaxItemAmount - 1
														If Inventory(ri) = SelectedItem Then
															Inventory(ri) = Null
															PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
														EndIf
													Next
													added = SelectedItem
													SelectedItem = Null : Exit
												EndIf
											EndIf
										Next
										If SelectedItem <> Null Then
											Msg = "The wallet is full."
										Else
											Msg = "You put "+added\itemtemplate\name+" into the wallet."
										EndIf
										
										MsgTimer = 70 * 5
									Else
										Msg = "You cannot combine these two items."
										MsgTimer = 70 * 5
									EndIf
								Else
									Msg = "You cannot combine these two items."
									MsgTimer = 70 * 5
								EndIf
								SelectedItem = Null
								
								;[End Block]
							Case "battery", "bat"
								;[Block]
								Select Inventory(MouseSlot)\itemtemplate\name
									Case "S-NAV Navigator", "S-NAV 300 Navigator", "S-NAV 310 Navigator"
										If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))	
										RemoveItem (SelectedItem)
										SelectedItem = Null
										Inventory(MouseSlot)\state = 100.0
										Msg = "You replaced the navigator's battery."
										MsgTimer = 70 * 5
									Case "S-NAV Navigator Ultimate"
										Msg = "There seems to be no place for batteries in this navigator."
										MsgTimer = 70 * 5
									Case "Radio Transceiver"
										Select Inventory(MouseSlot)\itemtemplate\tempname 
											Case "fineradio", "veryfineradio"
												Msg = "There seems to be no place for batteries in this radio."
												MsgTimer = 70 * 5
											Case "18vradio"
												Msg = "The battery does not fit inside this radio."
												MsgTimer = 70 * 5
											Case "radio"
												If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))	
												RemoveItem (SelectedItem)
												SelectedItem = Null
												Inventory(MouseSlot)\state = 100.0
												Msg = "You replaced the radio's battery."
												MsgTimer = 70 * 5
										End Select
									Case "Night Vision Goggles"
										Local nvname$ = Inventory(MouseSlot)\itemtemplate\tempname
										If nvname$="nvgoggles" Or nvname$="supernv" Then
											If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))	
											RemoveItem (SelectedItem)
											SelectedItem = Null
											Inventory(MouseSlot)\state = 1000.0
											Msg = "You replaced the goggles' battery."
											MsgTimer = 70 * 5
										Else
											Msg = "There seems to be no place for batteries in these night vision goggles."
											MsgTimer = 70 * 5
										EndIf
									Default
										Msg = "You cannot combine these two items."
										MsgTimer = 70 * 5	
								End Select
								;[End Block]
							Case "18vbat"
								;[Block]
								Select Inventory(MouseSlot)\itemtemplate\name
									Case "S-NAV Navigator", "S-NAV 300 Navigator", "S-NAV 310 Navigator"
										Msg = "The battery does not fit inside this navigator."
										MsgTimer = 70 * 5
									Case "S-NAV Navigator Ultimate"
										Msg = "There seems to be no place for batteries in this navigator."
										MsgTimer = 70 * 5
									Case "Radio Transceiver"
										Select Inventory(MouseSlot)\itemtemplate\tempname 
											Case "fineradio", "veryfineradio"
												Msg = "There seems to be no place for batteries in this radio."
												MsgTimer = 70 * 5		
											Case "18vradio"
												If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))	
												RemoveItem (SelectedItem)
												SelectedItem = Null
												Inventory(MouseSlot)\state = 100.0
												Msg = "You replaced the radio's battery."
												MsgTimer = 70 * 5
										End Select 
									Default
										Msg = "You cannot combine these two items."
										MsgTimer = 70 * 5	
								End Select
								;[End Block]
							Default
								;[Block]
								Msg = "You cannot combine these two items."
								MsgTimer = 70 * 5
								;[End Block]
						End Select
					End If
					
				End If
				SelectedItem = Null
			End If
		End If
		
		If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
		
		If InvOpen = False Then 
			;ResumeSounds() 
			ResetMouse()
		EndIf
	Else ;invopen = False
		
		If SelectedItem <> Null Then
			Select SelectedItem\itemtemplate\tempname
				Case "nvgoggles"
					;[Block]
					If Wearing1499 = 0 And WearingHazmat=0 Then
						If WearingNightVision = 1 Then
							Msg = "You removed the goggles."
							CameraFogFar = StoredCameraFogFar
						Else
							Msg = "You put on the goggles."
							WearingGasMask = 0
							WearingNightVision = 0
							StoredCameraFogFar = CameraFogFar
							CameraFogFar = 30
						EndIf
						
						WearingNightVision = (Not WearingNightVision)
					ElseIf Wearing1499 > 0 Then
						Msg = "You need to take off SCP-1499 in order to put on the goggles."
					Else
						Msg = "You need to take off the hazmat suit in order to put on the goggles."
					EndIf
					SelectedItem = Null
					MsgTimer = 70 * 5
					;[End Block]
				Case "supernv"
					;[Block]
					If Wearing1499 = 0 And WearingHazmat=0 Then
						If WearingNightVision = 2 Then
							Msg = "You removed the goggles."
							CameraFogFar = StoredCameraFogFar
						Else
							Msg = "You put on the goggles."
							WearingGasMask = 0
							WearingNightVision = 0
							StoredCameraFogFar = CameraFogFar
							CameraFogFar = 30
						EndIf
						
						WearingNightVision = (Not WearingNightVision) * 2
					ElseIf Wearing1499 > 0 Then
						Msg = "You need to take off SCP-1499 in order to put on the goggles."
					Else
						Msg = "You need to take off the hazmat suit in order to put on the goggles."
					EndIf
					SelectedItem = Null
					MsgTimer = 70 * 5
					;[End Block]
				Case "finenvgoggles"
					;[Block]
					If Wearing1499 = 0 And WearingHazmat = 0 Then
						If WearingNightVision = 3 Then
							Msg = "You removed the goggles."
							CameraFogFar = StoredCameraFogFar
						Else
							Msg = "You put on the goggles."
							WearingGasMask = 0
							WearingNightVision = 0
							StoredCameraFogFar = CameraFogFar
							CameraFogFar = 30
						EndIf
						
						WearingNightVision = (Not WearingNightVision) * 3
					ElseIf Wearing1499 > 0 Then
						Msg = "You need to take off SCP-1499 in order to put on the goggles."
					Else
						Msg = "You need to take off the hazmat suit in order to put on the goggles."
					EndIf
					SelectedItem = Null
					MsgTimer = 70 * 5
					;[End Block]
				Case "ring"
					;[Block]
					If Wearing714=2 Then
						Msg = "You removed the ring."
						Wearing714 = False
					Else
						;Achievements(Achv714)=True
						Msg = "You put on the ring."
						Wearing714 = 2
					EndIf
					MsgTimer = 70 * 5
					SelectedItem = Null
					;[End Block]
				Case "1123"
					;[Block]
					If Not (Wearing714 = 1) Then
						If PlayerRoom\RoomTemplate\Name <> "room1123" Then
							ShowEntity Light
							LightFlash = 7
							PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))
							multiplayer_WriteTempSound("SFX\SCP\1123\Touch.ogg", EntityX(Collider),EntityY(Collider),EntityZ(Collider))
							DeathMSG = "Subject D-9341 was shot dead after attempting to attack a member of Nine-Tailed Fox. Surveillance tapes show that the subject had been "
							DeathMSG = DeathMSG + "wandering around the site approximately 9 minutes prior, shouting the phrase " + Chr(34) + "get rid of the four pests" + Chr(34)
							DeathMSG = DeathMSG + " in chinese. SCP-1123 was found in [REDACTED] nearby, suggesting the subject had come into physical contact with it. How "
							DeathMSG = DeathMSG + "exactly SCP-1123 was removed from its containment chamber is still unknown."
							Kill("was killed by SCP-1123")
							Return
						EndIf
						For e.Events = Each Events
							If e\EventName = "room1123" Then 
								If e\EventState = 0 Then
									ShowEntity Light
									LightFlash = 3
									PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))		
								EndIf
								e\EventState = Max(1, e\EventState)
								Exit
							EndIf
						Next
					EndIf
					;[End Block]
				Case "battery"
					;[Block]
					;InvOpen = True
					;[End Block]
				Case "key1", "key2", "key3", "key4", "key5", "key6", "keyomni", "scp860", "hand", "hand2", "25ct"
					;[Block]
					DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
					;[End Block]
				Case "scp513"
					;[Block]
					PlaySound_Strict LoadTempSound("SFX\SCP\513\Bell1.ogg")
					multiplayer_WriteTempSound "SFX\SCP\513\Bell1.ogg", EntityX(Collider), EntityY(Collider), EntityZ(Collider), 20, 0.8
					SelectedItem = Null
					;[End Block]
				Case "scp500"
					;[Block]
					If CanUseItem(False, False, True)
						GiveAchievement(Achv500)
						
						If Infect > 0 Then
							Msg = "You swallowed the pill. Your nausea is fading."
						Else
							Msg = "You swallowed the pill."
						EndIf
						MsgTimer = 70*7
						
						DeathTimer = 0
						Infect = 0
						Stamina = 100
						For i = 0 To 5
							SCP1025state[i]=0
						Next
						If StaminaEffect > 1.0 Then
							StaminaEffect = 1.0
							StaminaEffectTimer = 0.0
						EndIf
						MyPlayer\Health = MyPlayer\Health + 200
						
						RemoveItem(SelectedItem)
						SelectedItem = Null
					EndIf
				Case "boxofammo"
					;[Block]
					If CanUseItem(False, False, True)
						CurrSpeed = CurveValue(0, CurrSpeed, 5.0)
						
						DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
						
						width% = 300
						height% = 20
						x% = GraphicWidth / 2 - width / 2
						y% = GraphicHeight / 2 + 80
						Rect(x, y, width+4, height, False)
						For  i% = 1 To Int((width - 2) * (SelectedItem\state / 100.0) / 10)
							DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
						Next
						
						SelectedItem\state = Min(SelectedItem\state+(FPSfactor*0.5),100)			
						
						If SelectedItem\state = 100 Then
							
							RemoveItem(SelectedItem)
							SelectedItem = Null
							MsgTimer = 70*8
							Msg = "Ammo reserve fully replenished."
							
							For g.Guns = Each Guns
								g\Magazines = g\MaxMagazines
							Next
						EndIf
					EndIf
				Case "chicken"
					;[Block]
					If CanUseItem(False, False, True)
						CurrSpeed = CurveValue(0, CurrSpeed, 5.0)
						Crouch = True
						
						DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
						
						width% = 300
						height% = 20
						x% = GraphicWidth / 2 - width / 2
						y% = GraphicHeight / 2 + 80
						Rect(x, y, width+4, height, False)
						For  i% = 1 To Int((width - 2) * (SelectedItem\state / 100.0) / 10)
							DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
						Next
						
						SelectedItem\state = Min(SelectedItem\state+(FPSfactor/7.0),100)			
						
						If SelectedItem\state = 100 Then
							Select Rand(7)
								Case 0
									Msg = "You feel bad because you have gastritis"
									VomitTimer = 30
								Case 1
									Msg = "You feel very good!"
									Injuries = Max(Injuries-0.5, 0)
									BlurTimer = 100
								Case 2
									Msg = "You choked on a chicken and smother"
									PlaySound_Strict(CoughSFX(Rand(0, 2)))
									multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
									Kill("was killed by chicken")
									BlurTimer = 2000
								Case 3
									Msg = "You choked on a chicken"
									PlaySound_Strict(CoughSFX(Rand(0, 2)))
									multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
									BlurTimer = 2000
								Case 4
									Msg = "There were nails in the chicken"
									PlaySound_Strict(CoughSFX(Rand(0, 2)))
									multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
									Injuries = 30.0
									BloodLoss = 10.0
									pvt = CreatePivot()
									PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
									TurnEntity pvt, 90, 0, 0
									EntityPick(pvt,0.3)
									de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
									de\size = Rnd(0.03,0.08)*Min(Injuries,3.0) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
									
									FreeEntity pvt
									multiplayer_WriteDecal(de,1)
									BlurTimer = 800
								Case 5
									Msg = "The chicken was not tasty"
								Case 6
									pvt = CreatePivot()
									PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
									TurnEntity pvt, 90, 0, 0
									EntityPick(pvt,0.3)
									de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
									de\size = Rnd(0.03,0.08)
									EntityAlpha(de\obj, 1.0)
									ScaleSprite de\obj, de\size, de\size
									
									FreeEntity pvt
									multiplayer_WriteDecal(de,1)
									Msg = "You bit your tongue"
									BlurTimer = 200
								Case 7
									Msg = "The chicken turned out to be rotten"
									VomitTimer = 30
							End Select
							RemoveItem(SelectedItem)
							SelectedItem = Null
							MsgTimer = 70*8
						ElseIf SelectedItem\state > 50 Then
							If Rand(400) = 50 Then
								Select Rand(7)
									Case 0
										Msg = "You feel bad because you have gastritis"
										VomitTimer = 30
									Case 2
										Msg = "You choked on a chicken and smother"
										PlaySound_Strict(CoughSFX(Rand(0, 2)))
										multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
										Kill("was killed by chicken")
										BlurTimer = 2000
									Case 3
										Msg = "You choked on a chicken"
										PlaySound_Strict(CoughSFX(Rand(0, 2)))
										multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
										BlurTimer = 2000
									Case 4
										Msg = "There were nails in the chicken"
										PlaySound_Strict(CoughSFX(Rand(0, 2)))
										multiplayer_WriteSound(CoughSFX(Rand(0, 2)), EntityX(Collider), EntityY(Collider), EntityZ(Collider))
										Injuries = 30.0
										Bloodloss = 10.0
										pvt = CreatePivot()
										PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
										TurnEntity pvt, 90, 0, 0
										EntityPick(pvt,0.3)
										de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
										de\size = Rnd(0.03,0.08)*Min(Injuries,3.0) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
										
										FreeEntity pvt
										multiplayer_WriteDecal(de,1)
										BlurTimer = 800
									Case 5
										Msg = "The chicken was not tasty"
									Case 6
										pvt = CreatePivot()
										PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
										TurnEntity pvt, 90, 0, 0
										EntityPick(pvt,0.3)
										de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
										de\size = Rnd(0.03,0.08)
										EntityAlpha(de\obj, 1.0)
										ScaleSprite de\obj, de\size, de\size
										
										FreeEntity pvt
										multiplayer_WriteDecal(de,1)
										Msg = "You bit your tongue"
										BlurTimer = 200
									Case 7
										Msg = "The chicken turned out to be rotten"
										VomitTimer = 30
								End Select
								RemoveItem(SelectedItem)
								SelectedItem = Null
								MsgTimer = 70*8
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case "veryfinefirstaid"
					;[Block]
					If CanUseItem(False, False, True)
						Select Rand(5)
							Case 1
								Injuries = 3.5
								Msg = "You started bleeding heavily."
								MsgTimer = 70*7

							Case 2
								Injuries = 0
								Bloodloss = 0
								Msg = "Your wounds are healing up rapidly."
								MsgTimer = 70*7

							Case 3
								Injuries = Max(0, Injuries - Rnd(0.5,3.5))
								Bloodloss = Max(0, Bloodloss - Rnd(10,100))
								Msg = "You feel much better."
								MsgTimer = 70*7

							Case 4
								BlurTimer = 10000
								Bloodloss = 0
								Msg = "You feel nauseated."
								MsgTimer = 70*7
							Case 5
								BlinkTimer = -10
								Local roomname$ = PlayerRoom\RoomTemplate\Name
								If roomname = "dimension1499" Or roomname = "gatea" Or (roomname="exit1" And EntityY(Collider)>1040.0*RoomScale)
									Injuries = 2.5
									Msg = "You started bleeding heavily."
									MsgTimer = 70*7
								Else
									For r.Rooms = Each Rooms
										If r\RoomTemplate\Name = "pocketdimension" Then
											PositionEntity(Collider, EntityX(r\obj),0.8,EntityZ(r\obj))		
											ResetEntity Collider									
											UpdateDoors()
											UpdateRooms()
											PlaySound_Strict(Use914SFX)
											DropSpeed = 0
											Curr106\State = -2500
											Exit
										EndIf
									Next
									Msg = "For some inexplicable reason, you find yourself inside the pocket dimension."
									MsgTimer = 70*8
								EndIf
						End Select
						MyPlayer\Health = 100
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "firstaid", "finefirstaid", "firstaid2"
					;[Block]
					If Bloodloss = 0 And Injuries = 0 Then
						Msg = "You do not need to use a first aid kit right now."
						MsgTimer = 70*5
						SelectedItem = Null
					Else
						If CanUseItem(False, True, True)
							CurrSpeed = CurveValue(0, CurrSpeed, 5.0)
							Crouch = True
							
							DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
							
							width% = 300
							height% = 20
							x% = GraphicWidth / 2 - width / 2
							y% = GraphicHeight / 2 + 80
							Rect(x, y, width+4, height, False)
							For  i% = 1 To Int((width - 2) * (SelectedItem\state / 100.0) / 10)
								DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
							Next
							
							SelectedItem\state = Min(SelectedItem\state+(FPSfactor/5.0),100)			
							
							If SelectedItem\state = 100 Then
								If SelectedItem\itemtemplate\tempname = "finefirstaid" Then
									Bloodloss = 0
									Injuries = Max(0, Injuries - 2.0)
									If Injuries = 0 Then
										Msg = "You bandaged the wounds and took a painkiller. You feel fine."
									ElseIf Injuries > 1.0
										Msg = "You bandaged the wounds and took a painkiller, but you were not able to stop the bleeding."
									Else
										Msg = "You bandaged the wounds and took a painkiller, but you still feel sore."
									EndIf
									MyPlayer\Health = 100
									MsgTimer = 70*5
									RemoveItem(SelectedItem)
								Else
									Bloodloss = Max(0, Bloodloss - Rand(10,20))
									If Injuries => 2.5 Then
										Msg = "The wounds were way too severe to staunch the bleeding completely."
										Injuries = Max(2.5, Injuries-Rnd(0.3,0.7))
									ElseIf Injuries > 1.0
										Injuries = Max(0.5, Injuries-Rnd(0.5,1.0))
										If Injuries > 1.0 Then
											Msg = "You bandaged the wounds but were unable to staunch the bleeding completely."
										Else
											Msg = "You managed to stop the bleeding."
										EndIf
									Else
										If Injuries > 0.5 Then
											Injuries = 0.5
											Msg = "You took a painkiller, easing the pain slightly."
										Else
											Injuries = 0.5
											Msg = "You took a painkiller, but it still hurts to walk."
										EndIf
									EndIf
									
									If SelectedItem\itemtemplate\tempname = "firstaid2" Then 
										Select Rand(6)
											Case 1
												SuperMan = True
												Msg = "You have becomed overwhelmedwithadrenalineholyshitWOOOOOO~!"
											Case 2
												InvertMouse = (Not InvertMouse)
												Msg = "You suddenly find it very difficult to turn your head."
											Case 3
												BlurTimer = 5000
												Msg = "You feel nauseated."
											Case 4
												BlinkEffect = 0.6
												BlinkEffectTimer = Rand(20,30)
											Case 5
												Bloodloss = 0
												Injuries = 0
												Msg = "You bandaged the wounds. The bleeding stopped completely and you feel fine."
											Case 6
												Msg = "You bandaged the wounds and blood started pouring heavily through the bandages."
												Injuries = 3.5
										End Select
									EndIf
									MyPlayer\Health = 100
									MsgTimer = 70*5
									RemoveItem(SelectedItem)
								EndIf							
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case "eyedrops"
					;[Block]
					If CanUseItem(False,False,False)
						If (Not (Wearing714=1)) Then ;wtf is this
							BlinkEffect = 0.6
							BlinkEffectTimer = Rand(20,30)
							BlurTimer = 200
						EndIf
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "fineeyedrops"
					;[Block]
					If CanUseItem(False,False,False)
						If (Not (Wearing714=1)) Then 
							BlinkEffect = 0.4
							BlinkEffectTimer = Rand(30,40)
							Bloodloss = Max(Bloodloss-1.0, 0)
							BlurTimer = 200
						EndIf
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "supereyedrops"
					;[Block]
					If CanUseItem(False,False,False)
						If (Not (Wearing714 = 1)) Then
							BlinkEffect = 0.0
							BlinkEffectTimer = 60
							EyeStuck = 10000
						EndIf
						BlurTimer = 1000
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "paper", "ticket"
					;[Block]
					If SelectedItem\itemtemplate\img = 0 Then
						Select SelectedItem\itemtemplate\name
							Case "Burnt Note" 
								SelectedItem\itemtemplate\img = LoadImage_Strict("GFX\items\bn.it")
								SetBuffer ImageBuffer(SelectedItem\itemtemplate\img)
								Color 0,0,0
								AASetFont Font1
								AAText 277, 469, AccessCode, True, True
								Color 255,255,255
								SetBuffer BackBuffer()
							Case "Document SCP-372"
								SelectedItem\itemtemplate\img = LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
								SelectedItem\itemtemplate\img = ResizeImage2(SelectedItem\itemtemplate\img, ImageWidth(SelectedItem\itemtemplate\img) * MenuScale, ImageHeight(SelectedItem\itemtemplate\img) * MenuScale)
								
								SetBuffer ImageBuffer(SelectedItem\itemtemplate\img)
								Color 37,45,137
								AASetFont Font5
								temp = ((Int(AccessCode)*3) Mod 10000)
								If temp < 1000 Then temp = temp+1000
								AAText 383*MenuScale, 734*MenuScale, temp, True, True
								Color 255,255,255
								SetBuffer BackBuffer()
							Case "Movie Ticket"
								;don't resize because it messes up the masking
								SelectedItem\itemtemplate\img=LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
								
								If (SelectedItem\state = 0) Then
									Msg = Chr(34)+"Hey, I remember this movie!"+Chr(34)
									MsgTimer = 70*10
									PlaySound_Strict LoadTempSound("SFX\SCP\1162\NostalgiaCancer"+Rand(1,5)+".ogg")
									SelectedItem\state = 1
								EndIf
							Default 
								SelectedItem\itemtemplate\img=LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
								SelectedItem\itemtemplate\img = ResizeImage2(SelectedItem\itemtemplate\img, ImageWidth(SelectedItem\itemtemplate\img) * MenuScale, ImageHeight(SelectedItem\itemtemplate\img) * MenuScale)
						End Select
						
						MaskImage(SelectedItem\itemtemplate\img, 255, 0, 255)
					EndIf
					
					DrawImage(SelectedItem\itemtemplate\img, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\img) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\img) / 2)
					;[End Block]
				Case "scp1025"
					;[Block]
					GiveAchievement(Achv1025) 
					If SelectedItem\itemtemplate\img=0 Then
						SelectedItem\state = Rand(0,5)
						SelectedItem\itemtemplate\img=LoadImage_Strict("GFX\items\1025\1025_"+Int(SelectedItem\state)+".jpg")	
						SelectedItem\itemtemplate\img = ResizeImage2(SelectedItem\itemtemplate\img, ImageWidth(SelectedItem\itemtemplate\img) * MenuScale, ImageHeight(SelectedItem\itemtemplate\img) * MenuScale)
						
						MaskImage(SelectedItem\itemtemplate\img, 255, 0, 255)
					EndIf
					
					If (Not Wearing714) Then SCP1025state[SelectedItem\state]=Max(1,SCP1025state[SelectedItem\state])
					
					DrawImage(SelectedItem\itemtemplate\img, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\img) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\img) / 2)
					;[End Block]
				Case "cup"
					;[Block]
					If CanUseItem(False,False,True)
						SelectedItem\name = Trim(Lower(SelectedItem\name))
						If Left(SelectedItem\name, Min(6,Len(SelectedItem\name))) = "cup of" Then
							SelectedItem\name = Right(SelectedItem\name, Len(SelectedItem\name)-7)
						ElseIf Left(SelectedItem\name, Min(8,Len(SelectedItem\name))) = "a cup of" 
							SelectedItem\name = Right(SelectedItem\name, Len(SelectedItem\name)-9)
						EndIf
						
						;the state of refined items is more than 1.0 (fine setting increases it by 1, very fine doubles it)
						x2 = (SelectedItem\state+1.0)
						
						Local iniStr$ = "DATA\SCP-294.ini"
						
						Local loc% = GetINISectionLocation(iniStr, SelectedItem\name)
						
						;Stop
						
						strtemp = GetINIString2(iniStr, loc, "message")
						If strtemp <> "" Then Msg = strtemp : MsgTimer = 70*6
						
						If GetINIInt2(iniStr, loc, "lethal") Or GetINIInt2(iniStr, loc, "deathtimer") Then 
							DeathMSG = GetINIString2(iniStr, loc, "deathmessage")
							If GetINIInt2(iniStr, loc, "lethal") Then Kill()
						EndIf
						BlurTimer = GetINIInt2(iniStr, loc, "blur")*70;*temp
						If VomitTimer = 0 Then VomitTimer = GetINIInt2(iniStr, loc, "vomit")
						CameraShakeTimer = GetINIString2(iniStr, loc, "camerashake")
						Injuries = Max(Injuries + GetINIInt2(iniStr, loc, "damage"),0);*temp
						Bloodloss = Max(Bloodloss + GetINIInt2(iniStr, loc, "blood loss"),0);*temp
						strtemp =  GetINIString2(iniStr, loc, "sound")
						If strtemp<>"" Then
							PlaySound_Strict LoadTempSound(strtemp)
						EndIf
						If GetINIInt2(iniStr, loc, "stomachache") Then SCP1025state[3]=1
						
						DeathTimer=GetINIInt2(iniStr, loc, "deathtimer")*70
						
						BlinkEffect = Float(GetINIString2(iniStr, loc, "blink effect", 1.0))*x2
						BlinkEffectTimer = Float(GetINIString2(iniStr, loc, "blink effect timer", 1.0))*x2
						
						StaminaEffect = Float(GetINIString2(iniStr, loc, "stamina effect", 1.0))*x2
						StaminaEffectTimer = Float(GetINIString2(iniStr, loc, "stamina effect timer", 1.0))*x2
						
						strtemp = GetINIString2(iniStr, loc, "refusemessage")
						If strtemp <> "" Then
							Msg = strtemp 
							MsgTimer = 70*6		
						Else
							it.Items = CreateItem("Empty Cup", "emptycup", 0,0,0)
							it\Picked = True
							For i = 0 To MaxItemAmount-1
								If Inventory(i)=SelectedItem Then Inventory(i) = it : Exit
							Next					
							EntityType (it\collider, HIT_ITEM)
							
							RemoveItem(SelectedItem)						
						EndIf
						
						SelectedItem = Null
					EndIf
					;[End Block]
				Case "syringe"
					;[Block]
					If CanUseItem(False,True,True)
						HealTimer = 30
						StaminaEffect = 0.5
						StaminaEffectTimer = 20
						
						Msg = "You injected yourself with the syringe and feel a slight adrenaline rush."
						MsgTimer = 70 * 8
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "finesyringe"
					;[Block]
					If CanUseItem(False,True,True)
						HealTimer = Rnd(20, 40)
						StaminaEffect = Rnd(0.5, 0.8)
						StaminaEffectTimer = Rnd(20, 30)
						
						Msg = "You injected yourself with the syringe and feel an adrenaline rush."
						MsgTimer = 70 * 8
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "veryfinesyringe"
					;[Block]
					If CanUseItem(False,True,True)
						Select Rand(3)
							Case 1
								HealTimer = Rnd(40, 60)
								StaminaEffect = 0.1
								StaminaEffectTimer = 30
								Msg = "You injected yourself with the syringe and feel a huge adrenaline rush."
							Case 2
								SuperMan = True
								Msg = "You injected yourself with the syringe and feel a humongous adrenaline rush."
							Case 3
								VomitTimer = 30
								Msg = "You injected yourself with the syringe and feel a pain in your stomach."
						End Select
						
						MsgTimer = 70 * 8
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "radio","18vradio","fineradio","veryfineradio"
					;[Block]
					If SelectedItem\state <= 100 Then SelectedItem\state = Max(0, SelectedItem\state - FPSfactor * 0.004)
					
					If SelectedItem\itemtemplate\img=0 Then
						SelectedItem\itemtemplate\img=LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
						MaskImage(SelectedItem\itemtemplate\img, 255, 0, 255)
					EndIf
					
					;radiostate(5) = has the "use the number keys" -message been shown yet (true/false)
					;radiostate(6) = a timer for the "code channel"
					;RadioState(7) = another timer for the "code channel"
					
					If RadioState(5) = 0 Then 
						Msg = "Use the numbered keys 1 through 9 to cycle between various channels."
						MsgTimer = 70 * 5
						RadioState(5) = 1
						RadioState(0) = -1
					EndIf
					
					strtemp$ = ""
					
					x = GraphicWidth - ImageWidth(SelectedItem\itemtemplate\img) ;+ 120
					y = GraphicHeight - ImageHeight(SelectedItem\itemtemplate\img) ;- 30
					
					DrawImage(SelectedItem\itemtemplate\img, x, y)
					
					RadioUse = True
					If PlayerRoom\RoomTemplate\Name = "pocketdimension" Or CoffinDistance < 4.0 Then
						ResumeChannel(RadioCHN(5))
						If ChannelPlaying(RadioCHN(5)) = False Then RadioCHN(5) = PlaySound_Strict(RadioStatic)	
					Else
						MyPlayer\CurrentRadio = Int(SelectedItem\state2)
						Select Int(SelectedItem\state2)
							Case 0 ;randomkanava
								ResumeChannel(RadioCHN(0))
								strtemp = "        USER TRACK PLAYER - "
								If (Not EnableUserTracks)
									If ChannelPlaying(RadioCHN(0)) = False Then RadioCHN(0) = PlaySound_Strict(RadioStatic)
									strtemp = strtemp + "NOT ENABLED     "
								ElseIf UserTrackMusicAmount<1
									If ChannelPlaying(RadioCHN(0)) = False Then RadioCHN(0) = PlaySound_Strict(RadioStatic)
									strtemp = strtemp + "NO TRACKS FOUND     "
								Else
									If (Not ChannelPlaying(RadioCHN(0)))
										If (Not UserTrackFlag%)
											If UserTrackMode
												If RadioState(0)<(UserTrackMusicAmount-1)
													RadioState(0) = RadioState(0) + 1
												Else
													RadioState(0) = 0
												EndIf
												UserTrackFlag = True
											Else
												RadioState(0) = Rand(0,UserTrackMusicAmount-1)
											EndIf
										EndIf
										If CurrUserTrack%<>0 Then FreeSound_Strict(CurrUserTrack%) : CurrUserTrack% = 0
										CurrUserTrack% = LoadSound_Strict("SFX\Radio\UserTracks\"+UserTrackName$(RadioState(0)))
										RadioCHN(0) = PlaySound_Strict(CurrUserTrack%)
										DebugLog "CurrTrack: "+RadioState(0)
										DebugLog UserTrackName$(RadioState(0))
									Else
										strtemp = strtemp + Upper(UserTrackName$(RadioState(0))) + "          "
										UserTrackFlag = False
									EndIf
									
									If KeyHit(2) Then
										PlaySound_Strict RadioSquelch
										If (Not UserTrackFlag%)
											If UserTrackMode
												If RadioState(0)<(UserTrackMusicAmount-1)
													RadioState(0) = RadioState(0) + 1
												Else
													RadioState(0) = 0
												EndIf
												UserTrackFlag = True
											Else
												RadioState(0) = Rand(0,UserTrackMusicAmount-1)
											EndIf
										EndIf
										If CurrUserTrack%<>0 Then FreeSound_Strict(CurrUserTrack%) : CurrUserTrack% = 0
										CurrUserTrack% = LoadSound_Strict("SFX\Radio\UserTracks\"+UserTrackName$(RadioState(0)))
										RadioCHN(0) = PlaySound_Strict(CurrUserTrack%)
										DebugLog "CurrTrack: "+RadioState(0)
										DebugLog UserTrackName$(RadioState(0))
									EndIf
								EndIf
							Case 1 ;h?¤lytyskanava
								DebugLog RadioState(1) 
								
								ResumeChannel(RadioCHN(1))
								strtemp = "        WARNING - CONTAINMENT BREACH          "
								If ChannelPlaying(RadioCHN(1)) = False Then
									
									If RadioState(1) => 5 Then
										RadioCHN(1) = PlaySound_Strict(RadioSFX(1,1))	
										RadioState(1) = 0
									Else
										RadioState(1)=RadioState(1)+1	
										RadioCHN(1) = PlaySound_Strict(RadioSFX(1,0))	
									EndIf
									
								EndIf
								
							Case 2 ;scp-radio
								ResumeChannel(RadioCHN(2))
								strtemp = "        SCP Foundation On-Site Radio          "
								If ChannelPlaying(RadioCHN(2)) = False Then
									RadioState(2)=RadioState(2)+1
									If RadioState(2) = 17 Then RadioState(2) = 1
									If Floor(RadioState(2)/2)=Ceil(RadioState(2)/2) Then ;parillinen, soitetaan normiviesti
										RadioCHN(2) = PlaySound_Strict(RadioSFX(2,Int(RadioState(2)/2)))	
									Else ;pariton, soitetaan musiikkia
										RadioCHN(2) = PlaySound_Strict(RadioSFX(2,0))
									EndIf
								EndIf 
							Case 3
								ResumeChannel(RadioCHN(3))
								strtemp = "             EMERGENCY CHANNEL - RESERVED FOR COMMUNICATION IN THE EVENT OF A CONTAINMENT BREACH         "
								If ChannelPlaying(RadioCHN(3)) = False Then RadioCHN(3) = PlaySound_Strict(RadioStatic)
								
								If MTFtimer > 0 Then 
									RadioState(3)=RadioState(3)+Max(Rand(-10,1),0)
									Select RadioState(3)
										Case 40
											If Not RadioState3(0) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random1.ogg"))
												RadioState(3) = RadioState(3)+1	
												RadioState3(0) = True	
											EndIf											
										Case 400
											If Not RadioState3(1) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random2.ogg"))
												RadioState(3) = RadioState(3)+1	
												RadioState3(1) = True	
											EndIf	
										Case 800
											If Not RadioState3(2) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random3.ogg"))
												RadioState(3) = RadioState(3)+1	
												RadioState3(2) = True
											EndIf													
										Case 1200
											If Not RadioState3(3) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random4.ogg"))	
												RadioState(3) = RadioState(3)+1	
												RadioState3(3) = True
											EndIf
										Case 1600
											If Not RadioState3(4) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random5.ogg"))	
												RadioState(3) = RadioState(3)+1
												RadioState3(4) = True
											EndIf
										Case 2000
											If Not RadioState3(5) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random6.ogg"))	
												RadioState(3) = RadioState(3)+1
												RadioState3(5) = True
											EndIf
										Case 2400
											If Not RadioState3(6) Then
												RadioCHN(3) = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random7.ogg"))	
												RadioState(3) = RadioState(3)+1
												RadioState3(6) = True
											EndIf
									End Select
								EndIf
							Case 4
								ResumeChannel(RadioCHN(6)) ;taustalle kohinaa
								If ChannelPlaying(RadioCHN(6)) = False Then RadioCHN(6) = PlaySound_Strict(RadioStatic)									
								
								ResumeChannel(RadioCHN(4))
								If ChannelPlaying(RadioCHN(4)) = False Then 
									If RemoteDoorOn = False And RadioState(8) = False Then
										RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\Chatter3.ogg"))	
										RadioState(8) = True
									Else
										RadioState(4)=RadioState(4)+Max(Rand(-10,1),0)
										
										Select RadioState(4)
											Case 10
												If (Not Contained106)
													If Not RadioState4(0) Then
														RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\OhGod.ogg"))
														RadioState(4) = RadioState(4)+1
														RadioState4(0) = True
													EndIf
												EndIf
											Case 100
												If Not RadioState4(1) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\Chatter2.ogg"))
													RadioState(4) = RadioState(4)+1
													RadioState4(1) = True
												EndIf		
											Case 158
												If MTFtimer = 0 And (Not RadioState4(2)) Then 
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\franklin1.ogg"))
													RadioState(4) = RadioState(4)+1
													RadioState(2) = True
												EndIf
											Case 200
												If Not RadioState4(3) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\Chatter4.ogg"))
													RadioState(4) = RadioState(4)+1
													RadioState4(3) = True
												EndIf		
											Case 260
												If Not RadioState4(4) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\SCP\035\RadioHelp1.ogg"))
													RadioState(4) = RadioState(4)+1
													RadioState4(4) = True
												EndIf		
											Case 300
												If Not RadioState4(5) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\Chatter1.ogg"))	
													RadioState(4) = RadioState(4)+1	
													RadioState4(5) = True
												EndIf		
											Case 350
												If Not RadioState4(6) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\franklin2.ogg"))
													RadioState(4) = RadioState(4)+1
													RadioState4(6) = True
												EndIf		
											Case 400
												If Not RadioState4(7) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\SCP\035\RadioHelp2.ogg"))
													RadioState(4) = RadioState(4)+1
													RadioState4(7) = True
												EndIf		
											Case 450
												If Not RadioState4(8) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\franklin3.ogg"))	
													RadioState(4) = RadioState(4)+1		
													RadioState4(8) = True
												EndIf		
											Case 600
												If Not RadioState4(9) Then
													RadioCHN(4) = PlaySound_Strict(LoadTempSound("SFX\radio\franklin4.ogg"))	
													RadioState(4) = RadioState(4)+1	
													RadioState4(9) = True
												EndIf		
										End Select
									EndIf
								EndIf
						End Select 
						
						x=x+66
						y=y+419
						
						Color (30,30,30)
						
						If SelectedItem\state <= 100 Then
							;Text (x - 60, y - 20, "BATTERY")
							For i = 0 To 4
								Rect(x, y+8*i, 43 - i * 6, 4, Ceil(SelectedItem\state / 20.0) > 4 - i )
							Next
						EndIf	
						
						AASetFont Font3
						AAText(x+60, y, "CHN")						
						
						If SelectedItem\itemtemplate\tempname = "veryfineradio" Then ;"KOODIKANAVA"
							ResumeChannel(RadioCHN(0))
							If ChannelPlaying(RadioCHN(0)) = False Then RadioCHN(0) = PlaySound_Strict(RadioStatic)
							
							;radiostate(7)=kuinka mones piippaus menossa
							;radiostate(8)=kuinka mones access coden numero menossa
							RadioState(6)=RadioState(6) + FPSfactor
							temp = Mid(Str(AccessCode),RadioState(8)+1,1)
							If RadioState(6)-FPSfactor =< RadioState(7)*50 And RadioState(6)>RadioState(7)*50 Then
								PlaySound_Strict(RadioBuzz)
								RadioState(7)=RadioState(7)+1
								If RadioState(7)=>temp Then
									RadioState(7)=0
									RadioState(6)=-100
									RadioState(8)=RadioState(8)+1
									If RadioState(8)=4 Then RadioState(8)=0 : RadioState(6)=-200
								EndIf
							EndIf
							
							strtemp = ""
							For i = 0 To Rand(5, 30)
								strtemp = strtemp + Chr(Rand(1,100))
							Next
							
							AASetFont Font4
							AAText(x+97, y+16, Rand(0,9),True,True)
							
						Else
							For i = 2 To 10
								If KeyHit(i) Then
									If SelectedItem\state2 <> i-2 Then ;pausetetaan nykyinen radiokanava
										PlaySound_Strict RadioSquelch
										If RadioCHN(Int(SelectedItem\state2)) <> 0 Then PauseChannel(RadioCHN(Int(SelectedItem\state2)))
									EndIf
									SelectedItem\state2 = i-2
									;jos nykyist?¤ kanavaa ollaan soitettu, laitetaan jatketaan toistoa samasta kohdasta
									If RadioCHN(SelectedItem\state2)<>0 Then ResumeChannel(RadioCHN(SelectedItem\state2))
								EndIf
							Next
							
							AASetFont Font4
							AAText(x+97, y+16, Int(SelectedItem\state2+1),True,True)
						EndIf
						
						AASetFont Font3
						If strtemp <> "" Then
							strtemp = Right(Left(strtemp, (Int(MilliSecs2()/300) Mod Len(strtemp))),10)
							AAText(x+32, y+33, strtemp)
						EndIf
						
						AASetFont Font1
						
					EndIf
					;[End Block]
				Case "cigarette"
					;[Block]
					If CanUseItem(False,False,True)
						If SelectedItem\state = 0 Then
							Select Rand(6)
								Case 1
									Msg = Chr(34)+"I don't have anything to light it with. Umm, what about that... Nevermind."+Chr(34)
								Case 2
									Msg = "You are unable to get lit."
								Case 3
									Msg = Chr(34)+"I quit that a long time ago."+Chr(34)
									RemoveItem(SelectedItem)
								Case 4
									Msg = Chr(34)+"Even if I wanted one, I have nothing to light it with."+Chr(34)
								Case 5
									Msg = Chr(34)+"Could really go for one now... Wish I had a lighter."+Chr(34)
								Case 6
									Msg = Chr(34)+"Don't plan on starting, even at a time like this."+Chr(34)
									RemoveItem(SelectedItem)
							End Select
							SelectedItem\state = 1 
						Else
							Msg = "You are unable to get lit."
						EndIf
						
						MsgTimer = 70 * 5
					EndIf
					;[End Block]
				Case "420"
					;[Block]
					If CanUseItem(False,False,True)
						If Wearing714=1 Then
							Msg = Chr(34) + "DUDE WTF THIS SHIT DOESN'T EVEN WORK" + Chr(34)
						Else
							Msg = Chr(34) + "MAN DATS SUM GOOD ASS SHIT" + Chr(34)
							Injuries = Max(Injuries-0.5, 0)
							BlurTimer = 500
							GiveAchievement(Achv420)
							PlaySound_Strict LoadTempSound("SFX\Music\420J.ogg")
							multiplayer_WriteTempSound("SFX\Music\420J.ogg", EntityX(Collider),EntityY(Collider),EntityZ(Collider),20)
						EndIf
						MsgTimer = 70 * 5
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "urancandy"
					If CanUseItem(False, False, True)
						Msg= "There's something wrong with your stomach."
						MsgTimer = 70*6
						VomitTimer = 70*1
						RemoveItem(SelectedItem)
					EndIf
				Case "420s"
					;[Block]
					If CanUseItem(False,False,True)
						If Wearing714=1 Then
							Msg = Chr(34) + "DUDE WTF THIS SHIT DOESN'T EVEN WORK" + Chr(34)
						Else
							DeathMSG = "Subject D-9341 found in a comatose state in [DATA REDACTED]. The subject was holding what appears to be a cigarette while smiling widely. "
							DeathMSG = DeathMSG+"Chemical analysis of the cigarette has been inconclusive, although it seems to contain a high concentration of an unidentified chemical "
							DeathMSG = DeathMSG+"whose molecular structure is remarkably similar to that of tetrahydrocannabinol."
							Msg = Chr(34) + "UH WHERE... WHAT WAS I DOING AGAIN... MAN I NEED TO TAKE A NAP..." + Chr(34)
							Kill("was killed by SCP-420", True)			
						EndIf
						MsgTimer = 70 * 6
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "scp714"
					;[Block]
					If Wearing714=1 Then
						Msg = "You removed the ring."
						Wearing714 = False
					Else
						GiveAchievement(Achv714)
						Msg = "You put on the ring."
						Wearing714 = True
					EndIf
					MsgTimer = 70 * 5
					SelectedItem = Null	
					;[End Block]
				Case "hazmatsuit", "hazmatsuit2", "hazmatsuit3"
					;[Block]
					If WearingVest = 0 Then
						CurrSpeed = CurveValue(0, CurrSpeed, 5.0)
						
						DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
						
						width% = 300
						height% = 20
						x% = GraphicWidth / 2 - width / 2
						y% = GraphicHeight / 2 + 80
						Rect(x, y, width+4, height, False)
						For  i% = 1 To Int((width - 2) * (SelectedItem\state / 100.0) / 10)
							DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
						Next
						
						SelectedItem\state = Min(SelectedItem\state+(FPSfactor/4.0),100)
						
						If SelectedItem\state=100 Then
							If WearingHazmat>0 Then
								Msg = "You removed the hazmat suit."
								WearingHazmat = False
								DropItem(SelectedItem)
							Else
								If SelectedItem\itemtemplate\tempname="hazmatsuit" Then
									;Msg = "Hazmat1."
									WearingHazmat = 1
								ElseIf SelectedItem\itemtemplate\tempname="hazmatsuit2" Then
									;Msg = "Hazmat2."
									WearingHazmat = 2
								Else
									;Msg = "Hazmat3."
									WearingHazmat = 3
								EndIf
								If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
								Msg = "You put on the hazmat suit."
								If WearingNightVision Then CameraFogFar = StoredCameraFogFar
								WearingGasMask = 0
								WearingNightVision = 0
							EndIf
							SelectedItem\state=0
							MsgTimer = 70 * 5
							SelectedItem = Null
						EndIf
					EndIf
					;[End Block]
				Case "vest","finevest"
					;[Block]
					CurrSpeed = CurveValue(0, CurrSpeed, 5.0)
					
					DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
					
					width% = 300
					height% = 20
					x% = GraphicWidth / 2 - width / 2
					y% = GraphicHeight / 2 + 80
					Rect(x, y, width+4, height, False)
					For  i% = 1 To Int((width - 2) * (SelectedItem\state / 100.0) / 10)
						DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
					Next
					
					SelectedItem\state = Min(SelectedItem\state+(FPSfactor/(2.0+(0.5*(SelectedItem\itemtemplate\tempname="finevest")))),100)
					
					If SelectedItem\state=100 Then
						If WearingVest>0 Then
							Msg = "You removed the vest."
							WearingVest = False
							DropItem(SelectedItem)
						Else
							If SelectedItem\itemtemplate\tempname="vest" Then
								Msg = "You put on the vest and feel slightly encumbered."
								WearingVest = 1
							Else
								Msg = "You put on the vest and feel heavily encumbered."
								WearingVest = 2
							EndIf
							If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
						EndIf
						SelectedItem\state=0
						MsgTimer = 70 * 5
						SelectedItem = Null
					EndIf
					;[End Block]
				Case "gasmask", "supergasmask", "gasmask3"
					;[Block]
					If Wearing1499 = 0 And WearingHazmat = 0 Then
						If WearingGasMask Then
							Msg = "You removed the gas mask."
						Else
							If SelectedItem\itemtemplate\tempname = "supergasmask"
								Msg = "You put on the gas mask and you can breathe easier."
							Else
								Msg = "You put on the gas mask."
							EndIf
							If WearingNightVision Then CameraFogFar = StoredCameraFogFar
							WearingNightVision = 0
							WearingGasMask = 0
						EndIf
						If SelectedItem\itemtemplate\tempname="gasmask3" Then
							If WearingGasMask = 0 Then WearingGasMask = 3 Else WearingGasMask=0
						ElseIf SelectedItem\itemtemplate\tempname="supergasmask"
							If WearingGasMask = 0 Then WearingGasMask = 2 Else WearingGasMask=0
						Else
							WearingGasMask = (Not WearingGasMask)
						EndIf
					ElseIf Wearing1499 > 0 Then
						Msg = "You need to take off SCP-1499 in order to put on the gas mask."
					Else
						Msg = "You need to take off the hazmat suit in order to put on the gas mask."
					EndIf
					SelectedItem = Null
					MsgTimer = 70 * 5
					;[End Block]
				Case "navigator", "nav"
					;[Block]
					If SelectedItem\itemtemplate\name = "S-NAV Navigator Ultimate" Then SelectedItem\state = 101
					
					If SelectedItem\itemtemplate\img=0 Then
						SelectedItem\itemtemplate\img=LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
						MaskImage(SelectedItem\itemtemplate\img, 255, 0, 255)
					EndIf
					
					If SelectedItem\state <= 100 Then SelectedItem\state = Max(0, SelectedItem\state - FPSfactor * 0.005)
					
					x = GraphicWidth - ImageWidth(SelectedItem\itemtemplate\img)*0.5+20
					y = GraphicHeight - ImageHeight(SelectedItem\itemtemplate\img)*0.4-85
					width = 287
					height = 256
					
					Local PlayerX,PlayerZ
					
					DrawImage(SelectedItem\itemtemplate\img, x - ImageWidth(SelectedItem\itemtemplate\img) / 2, y - ImageHeight(SelectedItem\itemtemplate\img) / 2 + 85)
					
					AASetFont Font3
					
					Local NavWorks% = True
					If PlayerRoom\RoomTemplate\Name$ = "pocketdimension" Or PlayerRoom\RoomTemplate\Name$ = "dimension1499" Then
						NavWorks% = False
					ElseIf PlayerRoom\RoomTemplate\Name$ = "room860" Then
						If room860event\EventState = 1.0 Then NavWorks% = False
					EndIf
					
					If (Not NavWorks) Then
						If (MilliSecs2() Mod 1000) > 300 Then
							Color(200, 0, 0)
							AAText(x, y + height / 2 - 80, "ERROR 06", True)
							AAText(x, y + height / 2 - 60, "LOCATION UNKNOWN", True)						
						EndIf
					Else
						
						If (Rnd(CoffinDistance + 15.0) > 1.0 Or PlayerRoom\RoomTemplate\Name <> "coffin") Then
							
							PlayerX% = Floor((EntityX(PlayerRoom\obj)+8) / 8.0 + 0.5)
							PlayerZ% = Floor((EntityZ(PlayerRoom\obj)+8) / 8.0 + 0.5)
							
							SetBuffer ImageBuffer(NavBG)
							Local xx = x-ImageWidth(SelectedItem\itemtemplate\img)/2
							Local yy = y-ImageHeight(SelectedItem\itemtemplate\img)/2+85
							DrawImage(SelectedItem\itemtemplate\img, xx, yy)
							
							x = x - 12 + (((EntityX(Collider)-4.0)+8.0) Mod 8.0)*3
							y = y + 12 - (((EntityZ(Collider)-4.0)+8.0) Mod 8.0)*3
							For x2 = Max(0, PlayerX - 6) To Min(MapWidth, PlayerX + 6)
								For z2 = Max(0, PlayerZ - 6) To Min(MapHeight, PlayerZ + 6)
									
									If CoffinDistance > 16.0 Or Rnd(16.0)<CoffinDistance Then 
										If MapTemp(x2, z2)>0 And (MapFound(x2, z2) > 0 Or SelectedItem\itemtemplate\name = "S-NAV 310 Navigator" Or SelectedItem\itemtemplate\name = "S-NAV Navigator Ultimate") Then
											Local drawx% = x + (PlayerX - 1 - x2) * 24 , drawy% = y - (PlayerZ - 1 - z2) * 24
											
											If x2+1<=MapWidth Then
												If MapTemp(x2+1,z2)=False
													DrawImage NavImages(3),drawx-12,drawy-12
												EndIf
											Else
												DrawImage NavImages(3),drawx-12,drawy-12
											EndIf
											If x2-1>=0 Then
												If MapTemp(x2-1,z2)=False
													DrawImage NavImages(1),drawx-12,drawy-12
												EndIf
											Else
												DrawImage NavImages(1),drawx-12,drawy-12
											EndIf
											If z2-1>=0 Then
												If MapTemp(x2,z2-1)=False
													DrawImage NavImages(0),drawx-12,drawy-12
												EndIf
											Else
												DrawImage NavImages(0),drawx-12,drawy-12
											EndIf
											If z2+1<=MapHeight Then
												If MapTemp(x2,z2+1)=False
													DrawImage NavImages(2),drawx-12,drawy-12
												EndIf
											Else
												DrawImage NavImages(2),drawx-12,drawy-12
											EndIf
										EndIf
									EndIf
									
								Next
							Next
							
							SetBuffer BackBuffer()
							DrawImageRect NavBG,xx+80,yy+70,xx+80,yy+70,270,230
							Color 30,30,30
							If SelectedItem\itemtemplate\name = "S-NAV Navigator" Then Color(100, 0, 0)
							Rect xx+80,yy+70,270,230,False
							
							x = GraphicWidth - ImageWidth(SelectedItem\itemtemplate\img)*0.5+20
							y = GraphicHeight - ImageHeight(SelectedItem\itemtemplate\img)*0.4-85
							
							If SelectedItem\itemtemplate\name = "S-NAV Navigator" Then 
								Color(100, 0, 0)
							Else
								Color (30,30,30)
							EndIf
							If (MilliSecs2() Mod 1000) > 300 Then
								If SelectedItem\itemtemplate\name <> "S-NAV 310 Navigator" And SelectedItem\itemtemplate\name <> "S-NAV Navigator Ultimate" Then
									AAText(x - width/2 + 10, y - height/2 + 10, "MAP DATABASE OFFLINE")
								EndIf
								
								yawvalue = EntityYaw(Collider)-90
								x1 = x+Cos(yawvalue)*6 : y1 = y-Sin(yawvalue)*6
								x2 = x+Cos(yawvalue-140)*5 : y2 = y-Sin(yawvalue-140)*5				
								x3 = x+Cos(yawvalue+140)*5 : y3 = y-Sin(yawvalue+140)*5
								
								Line x1,y1,x2,y2
								Line x1,y1,x3,y3
								Line x2,y2,x3,y3
							EndIf
							
							Local SCPs_found% = 0
							If SelectedItem\itemtemplate\name = "S-NAV Navigator Ultimate" And (MilliSecs2() Mod 600) < 400 Then
								If Curr173<>Null Then
									Local dist# = EntityDistance(Camera, Curr173\obj)
									dist = Ceil(dist / 8.0) * 8.0
									If dist < 8.0 * 4 Then
										Color 100, 0, 0
										Oval(x - dist * 3, y - 7 - dist * 3, dist * 3 * 2, dist * 3 * 2, False)
										AAText(x - width / 2 + 10, y - height / 2 + 30, "SCP-173")
										SCPs_found% = SCPs_found% + 1
									EndIf
								EndIf
								If Curr106<>Null Then
									dist# = EntityDistance(Camera, Curr106\obj)
									If dist < 8.0 * 4 Then
										Color 100, 0, 0
										Oval(x - dist * 1.5, y - 7 - dist * 1.5, dist * 3, dist * 3, False)
										AAText(x - width / 2 + 10, y - height / 2 + 30 + (20*SCPs_found), "SCP-106")
										SCPs_found% = SCPs_found% + 1
									EndIf
								EndIf
								If Curr096<>Null Then 
									dist# = EntityDistance(Camera, Curr096\obj)
									If dist < 8.0 * 4 Then
										Color 100, 0, 0
										Oval(x - dist * 1.5, y - 7 - dist * 1.5, dist * 3, dist * 3, False)
										AAText(x - width / 2 + 10, y - height / 2 + 30 + (20*SCPs_found), "SCP-096")
										SCPs_found% = SCPs_found% + 1
									EndIf
								EndIf
								For np.NPCs = Each NPCs
									If np\NPCtype = NPCtype049
										dist# = EntityDistance(Camera, np\obj)
										If dist < 8.0 * 4 Then
											If (Not np\HideFromNVG) Then
												Color 100, 0, 0
												Oval(x - dist * 1.5, y - 7 - dist * 1.5, dist * 3, dist * 3, False)
												AAText(x - width / 2 + 10, y - height / 2 + 30 + (20*SCPs_found), "SCP-049")
												SCPs_found% = SCPs_found% + 1
											EndIf
										EndIf
										Exit
									EndIf
								Next
								If PlayerRoom\RoomTemplate\Name = "coffin" Then
									If CoffinDistance < 8.0 Then
										dist = Rnd(4.0, 8.0)
										Color 100, 0, 0
										Oval(x - dist * 1.5, y - 7 - dist * 1.5, dist * 3, dist * 3, False)
										AAText(x - width / 2 + 10, y - height / 2 + 30 + (20*SCPs_found), "SCP-895")
									EndIf
								EndIf
							End If
							
							Color (30,30,30)
							If SelectedItem\itemtemplate\name = "S-NAV Navigator" Then Color(100, 0, 0)
							If SelectedItem\state <= 100 Then
								;AAText (x - width/2 + 10, y - height/2 + 10, "BATTERY")
								;xtemp = x - width/2 + 10
								;ytemp = y - height/2 + 30		
								;Line xtemp, ytemp, xtemp+20, ytemp
								;Line xtemp, ytemp+100, xtemp+20, ytemp+100
								;Line xtemp, ytemp, xtemp, ytemp+100
								;Line xtemp+20, ytemp, xtemp+20, ytemp+100
								;
								;AASetFont Font4
								;For i = 1 To Ceil(SelectedItem\state / 10.0)
								;	AAText (xtemp+11, ytemp+i*10-26, "-", True)
								;	;Rect(x - width/2, y+i*15, 40 - i * 6, 5, Ceil(SelectedItem\state / 20.0) > 4 - i)
								;Next
								;AASetFont Font3
								
								xtemp = x - width/2 + 196
								ytemp = y - height/2 + 10
								Rect xtemp,ytemp,80,20,False
								
								For i = 1 To Ceil(SelectedItem\state / 10.0)
									DrawImage NavImages(4),xtemp+i*8-6,ytemp+4
								Next
								
								AASetFont Font3
						EndIf
						EndIf
						
					EndIf
					;[End Block]
				;new Items in SCP:CB 1.3
				Case "scp1499","super1499"
					;[Block]
					If WearingHazmat>0
						Msg = "You are not able to wear SCP-1499 and a hazmat suit at the same time."
						MsgTimer = 70 * 5
						SelectedItem=Null
						Return
					EndIf
					
					CurrSpeed = CurveValue(0, CurrSpeed, 5.0)
					
					DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
					
					width% = 300
					height% = 20
					x% = GraphicWidth / 2 - width / 2
					y% = GraphicHeight / 2 + 80
					Rect(x, y, width+4, height, False)
					For  i% = 1 To Int((width - 2) * (SelectedItem\state / 100.0) / 10)
						DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
					Next
					
					SelectedItem\state = Min(SelectedItem\state+(FPSfactor),100)
					
					If SelectedItem\state=100 Then
						If Wearing1499>0 Then
							;Msg = "1499remove."
							Wearing1499 = False
							;DropItem(SelectedItem)
							If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
							Local r1499.Rooms
							For r.Rooms = Each Rooms
								If r = NTF_1499PrevRoom
									BlinkTimer = -1
									NTF_1499X# = EntityX(Collider)
									NTF_1499Y# = EntityY(Collider)
									NTF_1499Z# = EntityZ(Collider)
									PositionEntity (Collider, NTF_1499PrevX#, NTF_1499PrevY#+0.05, NTF_1499PrevZ#)
									ResetEntity(Collider)
									PlayerRoom = r
									UpdateDoors()
									UpdateRooms()
									If PlayerRoom\RoomTemplate\Name = "room3storage"
										If EntityY(Collider)<-4600*RoomScale
											For i = 0 To 2
												PlayerRoom\NPC[i]\State = 2
												PositionEntity(PlayerRoom\NPC[i]\Collider, EntityX(PlayerRoom\Objects[PlayerRoom\NPC[i]\State2],True),EntityY(PlayerRoom\Objects[PlayerRoom\NPC[i]\State2],True)+0.2,EntityZ(PlayerRoom\Objects[PlayerRoom\NPC[i]\State2],True))
												ResetEntity PlayerRoom\NPC[i]\Collider
												PlayerRoom\NPC[i]\State2 = PlayerRoom\NPC[i]\State2 + 1
												If PlayerRoom\NPC[i]\State2 > PlayerRoom\NPC[i]\PrevState Then PlayerRoom\NPC[i]\State2 = (PlayerRoom\NPC[i]\PrevState-3)
											Next
										EndIf
									ElseIf PlayerRoom\RoomTemplate\Name = "pocketdimension"
										CameraFogColor Camera, 0,0,0
										CameraClsColor Camera, 0,0,0
									EndIf
									For r2.Rooms = Each Rooms
										If r2\RoomTemplate\Name = "dimension1499"
											r1499 = r2
											Exit
										EndIf
									Next
									For it.Items = Each Items
										it\disttimer = 0
										If it\itemtemplate\tempname = "scp1499" Or it\itemtemplate\tempname = "super1499"
											If EntityY(it\collider) >= EntityY(r1499\obj)-5
												PositionEntity it\collider,NTF_1499PrevX#,NTF_1499PrevY#+(EntityY(it\collider)-EntityY(r1499\obj)),NTF_1499PrevZ#
												ResetEntity it\collider
												Exit
											EndIf
										EndIf
									Next
									r1499 = Null
									ShouldEntitiesFall = False
									PlaySound_Strict (LoadTempSound("SFX\SCP\1499\Exit.ogg"))
									NTF_1499PrevX# = 0.0
									NTF_1499PrevY# = 0.0
									NTF_1499PrevZ# = 0.0
									NTF_1499PrevRoom = Null
									ExecuteConsoleCommand("tfd")
									Exit
								EndIf
							Next
						Else
							If SelectedItem\itemtemplate\tempname="scp1499" Then
								;Msg = "scp1499."
								Wearing1499 = 1
							Else
								;Msg = "super1499."
								Wearing1499 = 2
							EndIf
							If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
							GiveAchievement(Achv1499)
							If WearingNightVision Then CameraFogFar = StoredCameraFogFar
							WearingGasMask = 0
							WearingNightVision = 0
							
							If multiplayer_IsFullSync() Then ExecuteConsoleCommand("teleport dimension1499")
							
							For r.Rooms = Each Rooms
								If r\RoomTemplate\Name = "dimension1499" Then
									BlinkTimer = -1
									NTF_1499PrevRoom = PlayerRoom
									NTF_1499PrevX# = EntityX(Collider)
									NTF_1499PrevY# = EntityY(Collider)
									NTF_1499PrevZ# = EntityZ(Collider)
									
									If NTF_1499X# = 0.0 And NTF_1499Y# = 0.0 And NTF_1499Z# = 0.0 Then
										PositionEntity (Collider, r\x+6086.0*RoomScale, r\y+304.0*RoomScale, r\z+2292.5*RoomScale)
										RotateEntity Collider,0,90,0,True
									Else
										PositionEntity (Collider, NTF_1499X#, NTF_1499Y#+0.05, NTF_1499Z#)
									EndIf
									ResetEntity(Collider)
									UpdateDoors()
									UpdateRooms()
									For it.Items = Each Items
										it\disttimer = 0
									Next
									PlayerRoom = r
									PlaySound_Strict (LoadTempSound("SFX\SCP\1499\Enter.ogg"))
									NTF_1499X# = 0.0
									NTF_1499Y# = 0.0
									NTF_1499Z# = 0.0
									If Curr096<>Null Then
										If Curr096\SoundChn<>0 Then
											SetStreamVolume_Strict(Curr096\SoundChn,0.0)
										EndIf
									EndIf
									For e.Events = Each Events
										If e\EventName = "dimension1499" Then
											If EntityDistance(e\room\obj,Collider)>8300.0*RoomScale Then
												If e\EventState2 < 5 Then
													e\EventState2 = e\EventState2 + 1
												EndIf
											EndIf
											Exit
										EndIf
									Next
									DebugLog "In dimension"
									Exit
								EndIf
							Next
						EndIf
						SelectedItem\state=0
						;MsgTimer = 70 * 5
						SelectedItem = Null
					EndIf
					;[End Block]
				Case "badge"
					;[Block]
					If SelectedItem\itemtemplate\img=0 Then
						SelectedItem\itemtemplate\img=LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
						;SelectedItem\itemtemplate\img = ResizeImage2(SelectedItem\itemtemplate\img, ImageWidth(SelectedItem\itemtemplate\img) * MenuScale, ImageHeight(SelectedItem\itemtemplate\img) * MenuScale)
						
						MaskImage(SelectedItem\itemtemplate\img, 255, 0, 255)
					EndIf
					
					DrawImage(SelectedItem\itemtemplate\img, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\img) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\img) / 2)
					
					If SelectedItem\state = 0 Then
						PlaySound_Strict LoadTempSound("SFX\SCP\1162\NostalgiaCancer"+Rand(6,10)+".ogg")
						Select SelectedItem\itemtemplate\name
							Case "Old Badge"
								Msg = Chr(34)+"Huh? This guy looks just like me!"+Chr(34)
								MsgTimer = 70*10
						End Select
						
						SelectedItem\state = 1
					EndIf
					;[End Block]
				Case "key"
					;[Block]
					If SelectedItem\state = 0 Then
						PlaySound_Strict LoadTempSound("SFX\SCP\1162\NostalgiaCancer"+Rand(6,10)+".ogg")
						
						Msg = Chr(34)+"Isn't this the key to that old shack? The one where I... No, it can't be."+Chr(34)
						MsgTimer = 70*10						
					EndIf
					
					SelectedItem\state = 1
					SelectedItem = Null
					;[End Block]
				Case "oldpaper"
					;[Block]
					If SelectedItem\itemtemplate\img = 0 Then
						SelectedItem\itemtemplate\img = LoadImage_Strict(SelectedItem\itemtemplate\imgpath)	
						SelectedItem\itemtemplate\img = ResizeImage2(SelectedItem\itemtemplate\img, ImageWidth(SelectedItem\itemtemplate\img) * MenuScale, ImageHeight(SelectedItem\itemtemplate\img) * MenuScale)
						
						MaskImage(SelectedItem\itemtemplate\img, 255, 0, 255)
					EndIf
					
					DrawImage(SelectedItem\itemtemplate\img, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\img) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\img) / 2)
					
					If SelectedItem\state = 0
						Select SelectedItem\itemtemplate\name
							Case "Disciplinary Hearing DH-S-4137-17092"
								BlurTimer = 1000
								
								Msg = Chr(34)+"Why does this seem so familiar?"+Chr(34)
								MsgTimer = 70*10
								PlaySound_Strict LoadTempSound("SFX\SCP\1162\NostalgiaCancer"+Rand(6,10)+".ogg")
								SelectedItem\state = 1
						End Select
					EndIf
					;[End Block]
				Case "coin"
					;[Block]
					If SelectedItem\state = 0
						PlaySound_Strict LoadTempSound("SFX\SCP\1162\NostalgiaCancer"+Rand(1,5)+".ogg")
					EndIf
					
					Msg = ""
					
					SelectedItem\state = 1
					DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
					;[End Block]
				Case "scp427"
					;[Block]
					If I_427\Using=1 Then
						Msg = "You closed the locket."
						I_427\Using = False
					Else
						GiveAchievement(Achv427)
						Msg = "You opened the locket."
						I_427\Using = True
					EndIf
					MsgTimer = 70 * 5
					SelectedItem = Null
					;[End Block]
				Case "pill"
					;[Block]
					If CanUseItem(False, False, True)
						Msg = "You swallowed the pill."
						MsgTimer = 70*7
						
						RemoveItem(SelectedItem)
						SelectedItem = Null
					EndIf	
					;[End Block]
				Case "scp500death"
					;[Block]
					If CanUseItem(False, False, True)
						Msg = "You swallowed the pill."
						MsgTimer = 70*7
						
						If I_427\Timer < 70*360 Then
							I_427\Timer = 70*360
						EndIf
						
						RemoveItem(SelectedItem)
						SelectedItem = Null
					EndIf
					;[Block]
				Default
					;check if the item is an inventory-type object
					If GetScripts() Then 
						public_inqueue(public_OnUsingItem)
						public_addparam(SelectedItem\ID, SE_INT)
						callback
					EndIf
					;	
					;	if SE_RETURN_VALUE\StaticIndex Then
					;		DoubleClick = 0
					;		MouseHit1 = 0
					;		MouseDown1 = 0
					;		LastMouseHit1 = 0
					;		If SelectedItem\invSlots>0 Then OtherOpen = SelectedItem
					;		SelectedItem = Null
						;Else
							;DrawImage(SelectedItem\itemtemplate\invimg, GraphicWidth / 2 - ImageWidth(SelectedItem\itemtemplate\invimg) / 2, GraphicHeight / 2 - ImageHeight(SelectedItem\itemtemplate\invimg) / 2)
						;EndIf
					;Else
						DoubleClick = 0
						MouseHit1 = 0
						MouseDown1 = 0
						LastMouseHit1 = 0
						If SelectedItem\invSlots>0 Then OtherOpen = SelectedItem
						SelectedItem = Null
					;EndIf
					
					;[End Block]
			End Select
			
			If SelectedItem <> Null Then
				If SelectedItem\itemtemplate\img <> 0
					Local IN$ = SelectedItem\itemtemplate\tempname
					If IN$ = "paper" Or IN$ = "badge" Or IN$ = "oldpaper" Or IN$ = "ticket" Then
						For a_it.Items = Each Items
							If a_it <> SelectedItem
								Local IN2$ = a_it\itemtemplate\tempname
								If IN2$ = "paper" Or IN2$ = "badge" Or IN2$ = "oldpaper" Or IN2$ = "ticket" Then
									If a_it\itemtemplate\img<>0
										If a_it\itemtemplate\img <> SelectedItem\itemtemplate\img
											FreeImage(a_it\itemtemplate\img)
											a_it\itemtemplate\img = 0
										EndIf
									EndIf
								EndIf
							EndIf
						Next
					EndIf
				EndIf			
			EndIf
			
			If MouseHit2 Then
				If SelectedItem <> Null Then
					EntityAlpha Dark, 0.0
					
					IN$ = SelectedItem\itemtemplate\tempname
					If IN$ = "scp1025" Then
						If SelectedItem\itemtemplate\img<>0 Then FreeImage(SelectedItem\itemtemplate\img)
						SelectedItem\itemtemplate\img=0
					ElseIf IN$ = "firstaid" Or IN$="finefirstaid" Or IN$="firstaid2" Or IN$="chicken" Or IN$="boxofammo" Then
						SelectedItem\state = 0
					ElseIf IN$ = "vest" Or IN$="finevest"
						SelectedItem\state = 0
						If (Not WearingVest)
							DropItem(SelectedItem,False)
						EndIf
					ElseIf IN$="hazmatsuit" Or IN$="hazmatsuit2" Or IN$="hazmatsuit3"
						SelectedItem\state = 0
						If (Not WearingHazmat)
							DropItem(SelectedItem,False)
						EndIf
					ElseIf IN$="scp1499" Or IN$="super1499"
						SelectedItem\state = 0
						;If (Not Wearing1499)
						;	DropItem(SelectedItem,False)
						;EndIf
					EndIf
					
					If SelectedItem\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(SelectedItem\itemtemplate\sound))
					SelectedItem = Null
				EndIf
			EndIf
		End If		
	EndIf
	
	If SelectedItem = Null Then
		For i = 0 To 6
			If RadioCHN(i) <> 0 Then 
				If ChannelPlaying(RadioCHN(i)) Then PauseChannel(RadioCHN(i))
			EndIf
		Next
	EndIf
	If Not Stated Then
		For it.Items = Each Items
			If it<>SelectedItem
				Select it\itemtemplate\tempname
					Case "firstaid","finefirstaid","firstaid2","vest","finevest","hazmatsuit","hazmatsuit2","hazmatsuit3","scp1499","super1499","chicken","boxofammo"
						it\state = 0
				End Select
			EndIf
		Next
		Stated = True
	EndIf
	If PrevInvOpen And (Not InvOpen) Then MoveMouse viewport_center_x, viewport_center_y
	
	;CatchErrors("DrawGUI")
End Function

Function DrawMenu()
	;CatchErrors("Uncaught (DrawMenu)")
	Local x%, y%, width%, height%
	If InFocus = 0 And MenuOpen = False Then ;Game is out of focus -> pause the game
		If (Not Using294) Then
			MenuOpen = True
			If Not udp_GetStream() Then PauseSounds()
		EndIf
    EndIf
	If MenuOpen Then
		
		;DebugLog AchievementsMenu+"|"+OptionsMenu+"|"+QuitMSG
		
		If PlayerRoom\RoomTemplate\Name$ <> "exit1" And PlayerRoom\RoomTemplate\Name$ <> "gatea"
			If StopHidingTimer = 0 Then
				If Curr173 <> Null Then
					If EntityDistance(Curr173\Collider, Collider)<4.0 Or EntityDistance(Curr106\Collider, Collider)<4.0 Then 
						StopHidingTimer = 1
					EndIf
				EndIf
			ElseIf StopHidingTimer < 40
				If KillTimer >= 0 Then 
					StopHidingTimer = StopHidingTimer+FPSfactor
					
					If StopHidingTimer => 40 Then
						PlaySound_Strict(HorrorSFX(15))
						Msg = "STOP HIDING"
						MsgTimer = 6*70
						MenuOpen = False
						If Not udp_GetStream() Then ResumeSounds()
						Return
					EndIf
				EndIf
			EndIf
		EndIf
		
		InvOpen = False
		
		width = ImageWidth(PauseMenuIMG)
		height = ImageHeight(PauseMenuIMG)
		x = GraphicWidth / 2 - width / 2
		y = GraphicHeight / 2 - height / 2
		
		DrawImage PauseMenuIMG, x, y
		
		Color(255, 255, 255)
		
		x = x+132*MenuScale
		y = y+122*MenuScale	
		
		If (Not MouseDown1)
			OnSliderID = 0
		EndIf
		
		If AchievementsMenu > 0 Then
			AASetFont Font2
			AAText(x, y-(122-45)*MenuScale, "ACHIEVEMENTS",False,True)
			AASetFont Font1
		ElseIf OptionsMenu > 0 Then
			AASetFont Font2
			AAText(x, y-(122-45)*MenuScale, "OPTIONS",False,True)
			AASetFont Font1
		ElseIf QuitMSG > 0 Then
			AASetFont Font2
			AAText(x, y-(122-45)*MenuScale, "QUIT?",False,True)
			AASetFont Font1
		ElseIf SuicideMSG > 0 Then
			AASetFont Font2
			AAText(x, y-(122-45)*MenuScale, "SUICIDE?",False,True)
			AASetFont Font1
		ElseIf KillTimer >= 0 Then
			AASetFont Font2
			AAText(x, y-(122-45)*MenuScale, "PAUSED",False,True)
			AASetFont Font1
		Else
			AASetFont Font2
			AAText(x, y-(122-45)*MenuScale, "YOU DIED",False,True)
			AASetFont Font1
		End If		
		
		Local AchvXIMG% = (x + (22*MenuScale))
		Local scale# = GraphicHeight/768.0
		Local SeparationConst% = 76*scale
		Local imgsize% = 64
		
		If AchievementsMenu <= 0 And OptionsMenu <= 0 And QuitMSG <= 0 And SuicideMSG <= 0
			AASetFont Font1
			AAText x, y, "Difficulty: "+SelectedDifficulty\name
			AAText x, y+20*MenuScale, "Map seed: "+RandomSeed
		ElseIf AchievementsMenu <= 0 And OptionsMenu > 0 And QuitMSG <= 0 And SuicideMSG <= 0 And KillTimer >= 0
			If DrawButton(x + 101 * MenuScale, y + 390 * MenuScale, 230 * MenuScale, 60 * MenuScale, "Back") Then
				AchievementsMenu = 0
				OptionsMenu = 0
				QuitMSG = 0
				SuicideMSG = 0
				MouseHit1 = False
				SaveOptionsINI()
				
				AntiAlias Opt_AntiAlias
				TextureLodBias TextureFloat#
			EndIf
			
			Color 0,255,0
			If OptionsMenu = 1
				Rect(x-10*MenuScale,y-5*MenuScale,110*MenuScale,40*MenuScale,True)
			ElseIf OptionsMenu = 2
				Rect(x+100*MenuScale,y-5*MenuScale,110*MenuScale,40*MenuScale,True)
			ElseIf OptionsMenu = 3
				Rect(x+210*MenuScale,y-5*MenuScale,110*MenuScale,40*MenuScale,True)
			ElseIf OptionsMenu = 4
				Rect(x+320*MenuScale,y-5*MenuScale,110*MenuScale,40*MenuScale,True)
			EndIf
			
			If DrawButton(x-5*MenuScale,y,100*MenuScale,30*MenuScale,"GRAPHICS",False) Then OptionsMenu = 1
			If DrawButton(x+105*MenuScale,y,100*MenuScale,30*MenuScale,"AUDIO",False) Then OptionsMenu = 2
			If DrawButton(x+215*MenuScale,y,100*MenuScale,30*MenuScale,"CONTROLS",False) Then OptionsMenu = 3
			If DrawButton(x+325*MenuScale,y,100*MenuScale,30*MenuScale,"ADVANCED",False) Then OptionsMenu = 4
			
			Local tx# = (GraphicWidth/2)+(width/2)
			Local ty# = y
			Local tw# = 400*MenuScale
			Local th# = 150*MenuScale
			
			Color 255,255,255
			Select OptionsMenu
				Case 1 ;Graphics
					AASetFont Font1
					;[Block]
					
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x, y, "VSync:")
					VerticalSync% = DrawTick(x + 270 * MenuScale, y + MenuScale, VerticalSync%)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"vsync")
					EndIf
					
					Color 255,255,255
					AAText(x + 300 * MenuScale, y, "Particles:")
					RemoveParticles% = Not DrawTick(x + 400 * MenuScale, y + MenuScale, Not RemoveParticles%)
					For p.Particles = Each Particles
						p\ForceRemove = RemoveParticles
					Next
					;If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
					;	DrawOptionsTooltip(tx,ty,tw,th,"vsync")
					;EndIf
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Anti-aliasing:")
					Opt_AntiAlias = DrawTick(x + 270 * MenuScale, y + MenuScale, Opt_AntiAlias%)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"antialias")
					EndIf
					
					Color 255,255,255
					AAText(x + 300 * MenuScale, y, "Decals:")
					RemoveDecals% = Not DrawTick(x + 400 * MenuScale, y + MenuScale, Not RemoveDecals)
					
					For d.Decals = Each Decals
						d\ForceRemove = RemoveDecals
					Next
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Enable room lights:")
					EnableRoomLights = DrawTick(x + 270 * MenuScale, y + MenuScale, EnableRoomLights)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"roomlights")
					EndIf
					
					MainFOV = Min(Max(SlideBar(x + 300*MenuScale, y+6*MenuScale, 90*MenuScale, MainFOV, False), 60), 90)
					Color 255,255,255
					AASetFont FontSL
					AAText(x + 340 * MenuScale, y+40*MenuScale, "FOV: "+Int(MainFOV))
					AASetFont Font1
					
					CurrentFOV = MainFOV
					
					If Not udp_GetStream() Then CameraZoom(Camera, Min(1.0+(CurrCameraZoom/400.0),1.1)  / (Tan((2.0 * ATan(Tan((CurrentFOV) / 2.0) * (Float(RealGraphicWidth) / Float(RealGraphicHeight)))) / 2.0)) )
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Disable gamma update:")
					TurnOnGamma = DrawTick(x + 270 * MenuScale, y + MenuScale, TurnOnGamma)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"gu")
					EndIf
					
					
					y=y+30*MenuScale
					
					ScreenGamma = (SlideBar(x + 270*MenuScale, y+6*MenuScale, 100*MenuScale, ScreenGamma*50.0)/50.0)
					Color 255,255,255
					AAText(x, y, "Screen gamma")
					If MouseOn(x+270*MenuScale,y+6*MenuScale,100*MenuScale+14,20) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"gamma",ScreenGamma)
					EndIf
					
					;y = y + 50*MenuScale
					
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Particle amount:")
					ParticleAmount = Slider3(x+270*MenuScale,y+6*MenuScale,100*MenuScale,ParticleAmount,2,"MINIMAL","REDUCED","FULL")
					If (MouseOn(x + 270 * MenuScale, y-6*MenuScale, 100*MenuScale+14, 20) And OnSliderID=0) Or OnSliderID=2
						DrawOptionsTooltip(tx,ty,tw,th,"particleamount",ParticleAmount)
					EndIf
					
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Texture LOD Bias:")
					TextureDetails = Slider5(x+270*MenuScale,y+6*MenuScale,100*MenuScale,TextureDetails,3,"1.5","0.4","0.0","-0.4","-0.8")
					Select TextureDetails%
						Case 0
							TextureFloat# = 1.5
						Case 1
							TextureFloat# = 0.4
						Case 2
							TextureFloat# = 0.0
						Case 3
							TextureFloat# = -0.4
						Case 4
							TextureFloat# = -0.8
					End Select
					TextureLodBias TextureFloat
					If (MouseOn(x+270*MenuScale,y-6*MenuScale,100*MenuScale+14,20) And OnSliderID=0) Or OnSliderID=3
						DrawOptionsTooltip(tx,ty,tw,th+100*MenuScale,"texquality")
					EndIf
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Cameras update interval:")
					
					Local CamerasSpeed#
					CamUpdate = Slider5(x+270*MenuScale,y+6*MenuScale,100*MenuScale,CamUpdate,55,"Very slow speed","Slow speed","Medium speed","High speed","Very high speed")

					Select CamUpdate
						Case 0
							CamerasSpeed# = 70
						Case 1
							CamerasSpeed# = 35
						Case 2
							CamerasSpeed# = 12
						Case 3
							CamerasSpeed# = 6
						Case 4
							CamerasSpeed# = 1
					End Select
					
					If (MouseOn(x+270*MenuScale,y-6*MenuScale,100*MenuScale+14,20) And OnSliderID=0) Or OnSliderID=55
						DrawOptionsTooltip(tx,ty,tw,th+100*MenuScale,"cam")
					EndIf
					
					For sc.SecurityCams = Each SecurityCams
						sc\RenderInterval = CamerasSpeed
					Next
					
					;[End Block]
				Case 2 ;Audio
					AASetFont Font1
					;[Block]
					y = y + 50*MenuScale
					
					MusicVolume = (SlideBar(x + 250*MenuScale, y-4*MenuScale, 100*MenuScale, MusicVolume*100.0)/100.0)
					Color 255,255,255
					AAText(x, y, "Music volume:")
					If MouseOn(x+250*MenuScale,y-4*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"musicvol",MusicVolume)
					EndIf
					
					y = y + 30*MenuScale
					
					
					If Not DeafPlayer Then
						PrevSFXVolume = (SlideBar(x + 250*MenuScale, y-4*MenuScale, 100*MenuScale, SFXVolume*100.0)/100.0)
						SFXVolume# = PrevSFXVolume#
					Else
						SlideBar(x + 250*MenuScale, y-4*MenuScale, 100*MenuScale, SFXVolume*100.0)
					EndIf
					Color 255,255,255
					AAText(x, y, "Sound volume:")
					If MouseOn(x+250*MenuScale,y-4*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"soundvol",PrevSFXVolume)
					EndIf
					
					y = y + 30*MenuScale
					
					Color 100,100,100
					AAText x, y, "Sound auto-release:"
					EnableSFXRelease = DrawTick(x + 270 * MenuScale, y + MenuScale, EnableSFXRelease,True)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th+220*MenuScale,"sfxautorelease")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 100,100,100
					AAText x, y, "Enable user tracks:"
					EnableUserTracks = DrawTick(x + 270 * MenuScale, y + MenuScale, EnableUserTracks,True)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"usertrack")
					EndIf
					
					If EnableUserTracks
						y = y + 30 * MenuScale
						Color 255,255,255
						AAText x, y, "User track mode:"
						UserTrackMode = DrawTick(x + 270 * MenuScale, y + MenuScale, UserTrackMode)
						If UserTrackMode
							AAText x, y + 20 * MenuScale, "Repeat"
						Else
							AAText x, y + 20 * MenuScale, "Random"
						EndIf
						If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
							DrawOptionsTooltip(tx,ty,tw,th,"usertrackmode")
						EndIf
						;DrawButton(x, y + 30 * MenuScale, 190 * MenuScale, 25 * MenuScale, "Scan for User Tracks",False)
						;If MouseOn(x,y+30*MenuScale,190*MenuScale,25*MenuScale)
						;	DrawOptionsTooltip(tx,ty,tw,th,"usertrackscan")
						;EndIf
					EndIf
					;[End Block]
				Case 3 ;Controls
					AASetFont Font1
					;[Block]
					y = y + 50*MenuScale
					
					MouseSens = (SlideBar(x + 270*MenuScale, y-4*MenuScale, 100*MenuScale, (MouseSens+0.5)*100.0)/100.0)-0.5
					Color(255, 255, 255)
					AAText(x, y, "Mouse sensitivity:")
					If MouseOn(x+270*MenuScale,y-4*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"mousesensitivity",MouseSens)
					EndIf
					
					y = y + 30*MenuScale
					
					MouseSmooth = (SlideBar(x + 270*MenuScale, y-4*MenuScale, 100*MenuScale, (MouseSmooth)*50.0)/50.0)
					Color(255, 255, 255)
					AAText(x, y, "Mouse smoothing:")
					If MouseOn(x+270*MenuScale,y-4*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"mousesmoothing",MouseSmooth)
					EndIf
					
					y = y + 30*MenuScale
					
					Color(255, 255, 255)
					AAText(x, y, "Invert mouse Y-axis:")
					InvertMouse = DrawTick(x + 270 * MenuScale, y + MenuScale, InvertMouse)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"mouseinvert")
					EndIf
					
					Color(255, 255, 255)
					
					y = y + 30*MenuScale
					AAText(x, y, "Using button (instead of mouse):")
					MOUSEINTERACT = Not DrawTick(x + 270 * MenuScale, y + MenuScale, Not MOUSEINTERACT)
					y = y + 10*MenuScale
					
					AAText(x, y + 20 * MenuScale, "Move Forward")
					InputBox(x + 200 * MenuScale, y + 20 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_UP,210)),5)		
					AAText(x, y + 40 * MenuScale, "Strafe Left")
					InputBox(x + 200 * MenuScale, y + 40 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEFT,210)),3)	
					AAText(x, y + 60 * MenuScale, "Move Backward")
					InputBox(x + 200 * MenuScale, y + 60 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_DOWN,210)),6)				
					AAText(x, y + 80 * MenuScale, "Strafe Right")
					InputBox(x + 200 * MenuScale, y + 80 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_RIGHT,210)),4)
					
					AAText(x, y + 100 * MenuScale, "Manual Blink")
					InputBox(x + 200 * MenuScale, y + 100 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_BLINK,210)),7)				
					AAText(x, y + 120 * MenuScale, "Sprint")
					InputBox(x + 200 * MenuScale, y + 120 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SPRINT,210)),8)
					AAText(x, y + 140 * MenuScale, "Open/Close Inventory")
					InputBox(x + 200 * MenuScale, y + 140 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_INV,210)),9)
					AAText(x, y + 160 * MenuScale, "Crouch")
					InputBox(x + 200 * MenuScale, y + 160 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CROUCH,210)),10)
					AAText(x, y + 180 * MenuScale, "Quick Save")
					InputBox(x + 200 * MenuScale, y + 180 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SAVE,210)),11)	
					AAText(x, y + 200 * MenuScale, "Open/Close Console")
					InputBox(x + 200 * MenuScale, y + 200 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CONSOLE,210)),12)
					
					
					AAText(x + 300 * MenuScale, y + 20 * MenuScale, "Chat")
					InputBox(x + 350 * MenuScale, y + 20 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CHAT,210)),13)

					AAText(x + 300 * MenuScale, y + 40 * MenuScale, "Voice")
					InputBox(x + 350 * MenuScale, y + 40 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_VOICE,210)),14)
					
					AAText(x + 300 * MenuScale, y + 60 * MenuScale, "Jump")
					InputBox(x + 350 * MenuScale, y + 60 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_JUMP,210)),15)
					
					AAText(x + 300 * MenuScale, y + 80 * MenuScale, "Lean L")
					InputBox(x + 350 * MenuScale, y + 80 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEANL,210)),16)
					
					AAText(x + 300 * MenuScale, y + 100 * MenuScale, "Lean R")
					InputBox(x + 350 * MenuScale, y + 100 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEANR,210)),17)
					
					AAText(x + 300 * MenuScale, y + 120 * MenuScale, "Using")
					InputBox(x + 350 * MenuScale, y + 120 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_USING,210)),18)
					
					If MouseOn(x,y,300*MenuScale,220*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"controls")
					EndIf
					
					For i = 0 To 227
						If KeyHit(i) Then key = i : Exit
					Next
					If key <> 0 Then
						Select SelectedInputBox
							Case 3
								KEY_LEFT = key
							Case 4
								KEY_RIGHT = key
							Case 5
								KEY_UP = key
							Case 6
								KEY_DOWN = key
							Case 7
								KEY_BLINK = key
							Case 8
								KEY_SPRINT = key
							Case 9
								KEY_INV = key
							Case 10
								KEY_CROUCH = key
							Case 11
								KEY_SAVE = key
							Case 12
								KEY_CONSOLE = key
							Case 13
								KEY_CHAT = key
							Case 14
								KEY_VOICE = Key
							Case 15
								KEY_JUMP = Key
							Case 16
								KEY_LEANL = Key
							Case 17
								KEY_LEANR = Key
							Case 18
								KEY_USING = Key
						End Select
						SelectedInputBox = 0
					EndIf
					;[End Block]
				Case 4 ;Advanced
					AASetFont Font1
					;[Block]
					y = y + 50*MenuScale
					
					Color 255,255,255				
					AAText(x, y, "Show HUD:")	
					HUDenabled = DrawTick(x + 270 * MenuScale, y + MenuScale, HUDenabled)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"hud")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Enable console:")
					CanOpenConsole = DrawTick(x +270 * MenuScale, y + MenuScale, CanOpenConsole)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"consoleenable")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Open console on error:")
					DrawTick(x + 270 * MenuScale, y + MenuScale, 0)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"consoleerror")
					EndIf
					
					y = y + 50*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Achievement popups:")
					AchvMSGenabled% = DrawTick(x + 270 * MenuScale, y, AchvMSGenabled%)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"achpopup")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Show FPS:")
					ShowFPS% = DrawTick(x + 270 * MenuScale, y, ShowFPS%)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"showfps")
					EndIf

					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Show SCP Viewmodel:")
					ShowSCPViewmodel% = DrawTick(x + 270 * MenuScale, y, ShowSCPViewmodel%)
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"showscpviewmodel")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Framelimit:")
					
					Color 255,255,255
					If DrawTick(x + 270 * MenuScale, y, CurrFrameLimit > 0.0) Then
						;CurrFrameLimit# = (SlideBar(x + 150*MenuScale, y+30*MenuScale, 100*MenuScale, CurrFrameLimit#*50.0)/50.0)
						;CurrFrameLimit = Max(CurrFrameLimit, 0.1)
						;Framelimit% = CurrFrameLimit#*100.0
						CurrFrameLimit# = (SlideBar(x + 150*MenuScale, y+30*MenuScale, 100*MenuScale, CurrFrameLimit#*99.0)/99.0)
						CurrFrameLimit# = Max(CurrFrameLimit, 0.01)
						Framelimit% = 19+(CurrFrameLimit*100.0)
						Color 255,255,0
						AAText(x + 5 * MenuScale, y + 25 * MenuScale, Framelimit%+" FPS")
					Else
						CurrFrameLimit# = 0.0
						Framelimit = 0
					EndIf
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"framelimit",Framelimit)
					EndIf
					If MouseOn(x+150*MenuScale,y+30*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"framelimit",Framelimit)
					EndIf
					
					y = y + 80*MenuScale
					
					Color 255,255,255
					AAText(x, y, "Antialiased text:")
					AATextEnable% = DrawTick(x + 270 * MenuScale, y + MenuScale, AATextEnable%)
					If AATextEnable_Prev% <> AATextEnable
						FreeAllFonts(False)
						LoadAllFonts(True)
					
						;ReloadAAFont()
						AATextEnable_Prev% = AATextEnable
					EndIf
					If MouseOn(x+270*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"antialiastext")
					EndIf
					;[End Block]
			End Select
		ElseIf AchievementsMenu <= 0 And OptionsMenu <= 0 And QuitMSG > 0 And KillTimer >= 0
			If DrawButton(x, y + 60*MenuScale, 390*MenuScale, 60*MenuScale, "Quit") Then DisconnectServer()
			If DrawButton(x+101*MenuScale, y + 344*MenuScale, 230*MenuScale, 60*MenuScale, "Back") Then
				AchievementsMenu = 0
				OptionsMenu = 0
				QuitMSG = 0
				
				MouseHit1 = False
			EndIf
		ElseIf AchievementsMenu <= 0 And OptionsMenu <= 0 And QuitMSG <= 0 And KillTimer >= 0 And SuicideMsg > 0 
			If DrawButton(x, y + 60*MenuScale, 390*MenuScale, 60*MenuScale, "Suicide") Then
				If MyPlayer\BreachType <> MODEL_WAIT Then 
					Kill("decided to gnaw their veins", True)
					MenuOpen = False
					MENU_OPEN_TYPE = MenuOpen

					AchievementsMenu = 0
					OptionsMenu = 0
					QuitMSG = 0
					SuicideMsg = 0
					MouseHit1 = False
				EndIf
			EndIf
			If DrawButton(x+101*MenuScale, y + 344*MenuScale, 230*MenuScale, 60*MenuScale, "Back") Then
				AchievementsMenu = 0
				OptionsMenu = 0
				SuicideMSG = 0
				
				MouseHit1 = False
			EndIf
		Else
			If DrawButton(x+101*MenuScale, y + 344*MenuScale, 230*MenuScale, 60*MenuScale, "Back") Then
				AchievementsMenu = 0
				OptionsMenu = 0
				QuitMSG = 0
				SuicideMSG = 0
				MouseHit1 = False
			EndIf
			
			If AchievementsMenu>0 Then
				;DebugLog AchievementsMenu
				If AchievementsMenu <= Floor(Float(MAXACHIEVEMENTS-1)/12.0) Then 
					If DrawButton(x+341*MenuScale, y + 344*MenuScale, 50*MenuScale, 60*MenuScale, ">") Then
						AchievementsMenu = AchievementsMenu+1
					EndIf
				EndIf
				If AchievementsMenu > 1 Then
					If DrawButton(x+41*MenuScale, y + 344*MenuScale, 50*MenuScale, 60*MenuScale, "<") Then
						AchievementsMenu = AchievementsMenu-1
					EndIf
				EndIf
				
				For i=0 To 11
					If i+((AchievementsMenu-1)*12)<MAXACHIEVEMENTS Then
						DrawAchvIMG(AchvXIMG,y+((i/4)*120*MenuScale),i+((AchievementsMenu-1)*12))
					Else
						Exit
					EndIf
				Next
				
				For i=0 To 11
					If i+((AchievementsMenu-1)*12)<MAXACHIEVEMENTS Then
						If MouseOn(AchvXIMG+((i Mod 4)*SeparationConst),y+((i/4)*120*MenuScale),64*scale,64*scale) Then
							AchievementTooltip(i+((AchievementsMenu-1)*12))
							Exit
						EndIf
					Else
						Exit
					EndIf
				Next
				
			EndIf
		EndIf
		
		y = y+10
		
		If AchievementsMenu<=0 And OptionsMenu<=0 And QuitMSG<=0 And SuicideMSG<=0 Then
			If KillTimer >= 0 Then	
				
				y = y+ 72*MenuScale
				
				If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Resume", True, True) Then
					MenuOpen = False
					If Not udp_GetStream() Then ResumeSounds()
					ResetMouse()
				EndIf
				y = y + 75*MenuScale
				If Not udp_GetStream() Then
					If (Not SelectedDifficulty\permaDeath) Then
						If GameSaved Then
							If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Load Game") Then
								ClearServer()
								StartLoadGame(SavePath + CurrSave + "\", CurrSave)
								;DrawLoading(0)
								
								;MenuOpen = False
								;LoadGameQuick(SavePath + CurrSave + "\")
								
								;;MoveMouse viewport_center_x,viewport_center_y
								;AASetFont Font1
								;HidePointer ()
								;
								;FlushKeys()
								;FlushMouse()
								;Playable=True
								;
								;;UpdateRooms()
								;
								;For r.Rooms = Each Rooms
								;	x = Abs(EntityX(Collider) - EntityX(r\obj))
								;	z = Abs(EntityZ(Collider) - EntityZ(r\obj))
								;	
								;	If x < 12.0 And z < 12.0 Then
								;		MapFound(Floor(EntityX(r\obj) / 8.0), Floor(EntityZ(r\obj) / 8.0)) = Max(MapFound(Floor(EntityX(r\obj) / 8.0), Floor(EntityZ(r\obj) / 8.0)), 1)
								;		If x < 4.0 And z < 4.0 Then
								;			If Abs(EntityY(Collider) - EntityY(r\obj)) < 1.5 Then PlayerRoom = r
								;			MapFound(Floor(EntityX(r\obj) / 8.0), Floor(EntityZ(r\obj) / 8.0)) = 1
								;		EndIf
								;	End If
								;Next
								;
								;DrawLoading(100)
								;
								;DropSpeed=0
								;
								;UpdateWorld 0.0
								;
								;PrevTime = MilliSecs()
								;FPSfactor = 0
								;
								;ResetInput()
							EndIf
						Else
							DrawFrame(x,y,390*MenuScale, 60*MenuScale)
							Color (100, 100, 100)
							AASetFont Font2
							AAText(x + (390*MenuScale) / 2, y + (60*MenuScale) / 2, "Load Game", True, True)
						EndIf
						y = y + 75*MenuScale
					EndIf
				EndIf
				If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Achievements") Then AchievementsMenu = 1
				y = y + 75*MenuScale
				
				If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Options") Then OptionsMenu = 1
				y = y + 75*MenuScale
				If udp_GetStream() Then
					If Spectate\SpectatePlayer = -1 Then
						If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Suicide") Then SuicideMSG = 1
						y = y + 75*MenuScale
					EndIf
				EndIf
				If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Quit") Then QuitMSG = 1
				y = y + 75*MenuScale
			Else
				y= y + 80*MenuScale
				If udp_GetStream() Then
					If NetworkServer\Breach = False Then
						If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Spawn") Then
							RN$ = PlayerRoom\RoomTemplate\Name$
							If RN$ = "173" Then
								Msg = "You cannot respawn in this location"
								MsgTimer = 70 * 4
								Return
							EndIf
							EyeIrritation = 0
							Stamina# = 60
							StaminaEffect#=1
							StaminaEffectTimer# = 0
							For i = 0 To 5
								SCP1025state[i] = 0
							Next
							SuperMan = False
							MyPlayer\IsDead = 0
							SuperManTimer = 0
							DrawLoading(0)
							MenuOpen = False
							DropSpeed = 0
							Shake = 0
							CurrSpeed = 0
							Infect = 0
							HeartBeatVolume = 0
							Bloodloss = 0
							CameraShake = 0
							Shake = 0
							LightFlash = 0
							BlurTimer = 0
							Crouch = False
							KillTimer = 0
							FallTimer = 0
							MenuOpen = False
							
							GodMode = 0
							NoClip = 0
							
							HideEntity Head
							;ShowEntity Collider
							
							MoveMouse viewport_center_x,viewport_center_y
							AASetFont Font1
							HidePointer ()
							
							FlushKeys()
							FlushMouse()
							Playable=True
							
							DrawLoading(100)
							
							DropSpeed=0
							
							UpdateWorld 0.0
							
							PrevTime = MilliSecs()
							FPSfactor = 0
							
							ResetInput()
							UpdateDoors()
							DropSpeed = -0.1
							HeadDropSpeed = 0.0
							Shake = 0
							CurrSpeed = 0
							
							HeartBeatVolume = 0
							
							CameraShake = 0
							Shake = 0
							LightFlash = 0
							BlurTimer = 0
							
							FallTimer = 0
							MenuOpen = False
							
							GodMode = 0
							NoClip = 0
							
							ShowEntity Collider
							
							KillTimer = 0
							KillAnim = 0
							
							PositionEntity Collider,SavePosX, SavePosY+0.7, SavePosZ
							RotateEntity Collider, 0, SavePosA, 0
							ResetEntity(Collider)
							Injuries = 0
							If GetScripts() Then public_inqueue(public_OnSpawn, True)
							
							If udp_GetStream() Then
								udp_ByteStreamWriteChar M_RESPAWN
								udp_setmicrobyte(M_RESPAWN)
							EndIf
							For r.Rooms = Each Rooms
								If r\RoomTemplate\Name = SaveRoom Then
									UpdateDoors()
									UpdateRooms()
									For it.Items = Each Items
										it\disttimer = 0
									Next
									PlayerRoom = r
									Exit
								EndIf
							Next
						EndIf
						y=y + 80*MenuScale
						If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Quit") Then DisconnectServer()
					Else
						If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Quit") Then DisconnectServer()
					EndIf
				Else
					If (Not SelectedDifficulty\permaDeath) Then
						If GameSaved Then
							If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Load Game") Then
								ClearServer()
								StartLoadGame(SavePath + CurrSave + "\", CurrSave)
								;DrawLoading(0)
								
								;MenuOpen = False
								;LoadGameQuick(SavePath + CurrSave + "\")
								
								;;MoveMouse viewport_center_x,viewport_center_y
								;AASetFont Font1
								;HidePointer ()
								;
								;FlushKeys()
								;FlushMouse()
								;Playable=True
								;
								;;UpdateRooms()
								;
								;For r.Rooms = Each Rooms
								;	x = Abs(EntityX(Collider) - EntityX(r\obj))
								;	z = Abs(EntityZ(Collider) - EntityZ(r\obj))
								;	
								;	If x < 12.0 And z < 12.0 Then
								;		MapFound(Floor(EntityX(r\obj) / 8.0), Floor(EntityZ(r\obj) / 8.0)) = Max(MapFound(Floor(EntityX(r\obj) / 8.0), Floor(EntityZ(r\obj) / 8.0)), 1)
								;		If x < 4.0 And z < 4.0 Then
								;			If Abs(EntityY(Collider) - EntityY(r\obj)) < 1.5 Then PlayerRoom = r
								;			MapFound(Floor(EntityX(r\obj) / 8.0), Floor(EntityZ(r\obj) / 8.0)) = 1
								;		EndIf
								;	End If
								;Next
								;
								;DrawLoading(100)
								;
								;DropSpeed=0
								;
								;UpdateWorld 0.0
								;
								;PrevTime = MilliSecs()
								;FPSfactor = 0
								;
								;ResetInput()
							EndIf
						Else
							DrawFrame(x,y,390*MenuScale, 60*MenuScale)
							Color (100, 100, 100)
							AASetFont Font2
							AAText(x + (390*MenuScale) / 2, y + (60*MenuScale) / 2, "Load Game", True, True)
						EndIf
						y = y + 75*MenuScale
					EndIf
					If DrawButton(x, y, 390*MenuScale, 60*MenuScale, "Quit to Menu") Then
						ClearServer()
						CurrSave = ""
						FlushKeys()
					EndIf
					y= y + 80*MenuScale
				EndIf
			EndIf
			
			AASetFont Font1
			If KillTimer < 0 Then RowText(DeathMSG$, x, y + 80*MenuScale, 390*MenuScale, 600*MenuScale)
		EndIf
		
		If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
		
	End If
	
	AASetFont Font1
	
	;CatchErrors("DrawMenu")
End Function

Function MouseOn%(x%, y%, width%, height%)
	If ScaledMouseX() > x And ScaledMouseX() < x + width Then
		If ScaledMouseY() > y And ScaledMouseY() < y + height Then
			Return True
		End If
	End If
	Return False
End Function

;----------------------------------------------------------------------------------------------

Include "SourceCode\LoadAllSounds.bb"
Function NameEntityEx(n.NPCs, name$)
	n\ObjName = name
End Function
Function ResetNPC(n.NPCs, NPCtype)
	If n\NPCtype = NPCtype Then Return
	Local X#,Y#,Z#
	X = EntityX(n\Collider)
	Y = EntityY(n\Collider)
	Z = EntityZ(n\Collider)
	If n\obj2 <> 0 Then 
		FreeEntity n\obj2
		n\obj2 = 0
	EndIf
	If n\obj3 <> 0 Then 
		FreeEntity n\obj3
		n\obj3 = 0
	EndIf
	If n\obj4 <> 0 Then 
		FreeEntity n\obj4
		n\obj4 = 0
	EndIf
	FreeEntity(n\obj) : n\obj = 0
	FreeEntity(n\Collider) : n\Collider = 0
	
	FillNPC(n, NPCtype)
	
	n\NPCtype = NPCtype
	If Not n\Collider Then
		n\Collider = CreatePivot()
		EntityRadius n\Collider, 0.32
		EntityType n\Collider, HIT_PLAYER
	EndIf
	PositionEntity n\Collider,X,Y,Z, True
	ResetEntity n\Collider
End Function
Function LoadEntities()
	;CatchErrors("Uncaught (LoadEntities)")
	NetworkServer\EntitiesLoaded = False
	DrawLoading(0)
	ReloadHolidaysEntities()
	
	;if ShouldNullGame Then
	;;	Delay 500
	;	DrawLoading(5)
	;	NullGame(False)
	;	ShouldNullGame = False
	;EndIf
	
	Local i%
	
	For i=0 To 9
		TempSounds[i]=0
	Next
	
	If PauseMenuIMG <> 0 Then FreeImage PauseMenuIMG
	If SprintIcon <> 0 Then FreeImage SprintIcon
	If BlinkIcon <> 0 Then FreeImage BlinkIcon
	If CrouchIcon <> 0 Then FreeImage CrouchIcon
	If HandIcon <> 0 Then FreeImage HandIcon
	If HandIcon2 <> 0 Then FreeImage HandIcon2
	If StaminaMeterIMG <> 0 Then FreeImage StaminaMeterIMG
	If Panel294 <> 0 Then FreeImage Panel294
	
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
	
	
	Brightness% = GetINIFloat(OptionFile, "options", "brightness")
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
	
	Camera = CreateCamera()
	CameraViewport Camera,0,0,GraphicWidth,GraphicHeight
	CameraRange(Camera, 0.05, CameraFogFar)
	CameraFogMode (Camera, 1)
	CameraFogRange (Camera, CameraFogNear, CameraFogFar)
	CameraFogColor (Camera, GetINIInt(OptionFile, "options", "fog r"), GetINIInt(OptionFile, "options", "fog g"), GetINIInt(OptionFile, "options", "fog b"))
	AmbientLight Brightness, Brightness, Brightness
	
	ScreenTexs[0] = CreateTexture(GetCameraQuality(CamQuality), GetCameraQuality(CamQuality), 1+256)
	ScreenTexs[1] = CreateTexture(GetCameraQuality(CamQuality), GetCameraQuality(CamQuality), 1+256)
	
	CreateBlurImage()
	CameraProjMode ark_blur_cam,0
	;Listener = CreateListener(Camera)
	
	FogTexture = LoadTexture_Strict("GFX\fog.jpg", 1)
	
	Fog = CreateSprite(ark_blur_cam)
	ScaleSprite(Fog, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityTexture(Fog, FogTexture)
	EntityBlend (Fog, 2)
	EntityOrder Fog, -1000
	MoveEntity(Fog, 0, 0, 1.0)
	
	GasMaskTexture = LoadTexture_Strict("GFX\GasmaskOverlay.jpg", 1)
	GasMaskOverlay = CreateSprite(ark_blur_cam)
	ScaleSprite(GasMaskOverlay, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityTexture(GasMaskOverlay, GasMaskTexture)
	EntityBlend (GasMaskOverlay, 2)
	EntityFX(GasMaskOverlay, 1)
	EntityOrder GasMaskOverlay, -1003
	MoveEntity(GasMaskOverlay, 0, 0, 1.0)
	HideEntity(GasMaskOverlay)
	
	InfectTexture = LoadTexture_Strict("GFX\InfectOverlay.jpg", 1)
	InfectOverlay = CreateSprite(ark_blur_cam)
	ScaleSprite(InfectOverlay, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityTexture(InfectOverlay, InfectTexture)
	EntityBlend (InfectOverlay, 3)
	EntityFX(InfectOverlay, 1)
	EntityOrder InfectOverlay, -1003
	MoveEntity(InfectOverlay, 0, 0, 1.0)
	;EntityAlpha (InfectOverlay, 255.0)
	HideEntity(InfectOverlay)
	
	NVTexture = LoadTexture_Strict("GFX\NightVisionOverlay.jpg", 1)
	NVOverlay = CreateSprite(ark_blur_cam)
	ScaleSprite(NVOverlay, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityTexture(NVOverlay, NVTexture)
	EntityBlend (NVOverlay, 2)
	EntityFX(NVOverlay, 1)
	EntityOrder NVOverlay, -1003
	MoveEntity(NVOverlay, 0, 0, 1.0)
	HideEntity(NVOverlay)
	NVBlink = CreateSprite(ark_blur_cam)
	ScaleSprite(NVBlink, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityColor(NVBlink,0,0,0)
	EntityFX(NVBlink, 1)
	EntityOrder NVBlink, -1005
	MoveEntity(NVBlink, 0, 0, 1.0)
	HideEntity(NVBlink)
	
	
	FogNVTexture = LoadTexture_Strict("GFX\fogNV.jpg", 1)
	
	DrawLoading(5)
	
	DarkTexture = CreateTexture(SMALLEST_POWER_TWO_HALF, SMALLEST_POWER_TWO_HALF, 1 + 2)
	SetBuffer TextureBuffer(DarkTexture)
	Cls
	SetBuffer BackBuffer()
	
	Dark = CreateSprite(ark_blur_cam)
	ScaleSprite(Dark, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityTexture(Dark, DarkTexture)
	EntityBlend (Dark, 1)
	EntityOrder Dark, -1002
	MoveEntity(Dark, 0, 0, 1.0)
	EntityAlpha Dark, 0.0
	
	LightTexture = CreateTexture(SMALLEST_POWER_TWO_HALF, SMALLEST_POWER_TWO_HALF, 1 + 2+256)
	SetBuffer TextureBuffer(LightTexture)
	ClsColor 255, 255, 255
	Cls
	ClsColor 0, 0, 0
	SetBuffer BackBuffer()
	
	TeslaTexture = LoadTexture_Strict("GFX\map\tesla.jpg", 1+2)
	
	Light = CreateSprite(ark_blur_cam)
	ScaleSprite(Light, 1.0, Float(GraphicHeight)/Float(GraphicWidth))
	EntityTexture(Light, LightTexture)
	EntityBlend (Light, 1)
	EntityOrder Light, -1002
	MoveEntity(Light, 0, 0, 1.0)
	HideEntity Light

	InitGuns()

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
	
	DrawLoading(10)
	
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
	
	;For i = 0 To 1
	;	HideEntity BigDoorOBJ(i)
	;	;If BumpEnabled And 0 Then
	;	If BumpEnabled
	;		
	;		Local bumptex = LoadTexture_Strict("GFX\map\containmentdoorsbump.jpg")
	;		;TextureBlend bumptex, FE_BUMP
	;		Local tex = LoadTexture_Strict("GFX\map\containment_doors.jpg")	
	;		EntityTexture BigDoorOBJ(i), bumptex, 0, 0
	;		EntityTexture BigDoorOBJ(i), tex, 0, 1
	;		
	;		;FreeEntity tex
	;		;FreeEntity bumptex
	;		FreeTexture tex
	;		FreeTexture bumptex
	;	EndIf
	;Next
	
	DrawLoading(15)
	
	For i = 0 To 5
		GorePics(i) = LoadTexture_Strict("GFX\895pics\pic" + (i + 1) + ".jpg")
	Next
	
	OldAiPics(0) = LoadTexture_Strict("GFX\AIface.jpg")
	OldAiPics(1) = LoadTexture_Strict("GFX\AIface2.jpg")	
	
	DrawLoading(20)
	
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
	
	DrawLoading(25)
	
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
	
	UserTrackMusicAmount% = 0
	If EnableUserTracks Then
		Local dirPath$ = "SFX\Radio\UserTracks\"
		If FileType(dirPath)<>2 Then
			CreateDir(dirPath)
		EndIf
		
		Local Dir% = ReadDir("SFX\Radio\UserTracks\")
		Repeat
			file$=NextFile(Dir)
			If file$="" Then Exit
			If FileType("SFX\Radio\UserTracks\"+file$) = 1 Then
				test = LoadSound("SFX\Radio\UserTracks\"+file$)
				If test<>0
					UserTrackName$(UserTrackMusicAmount%) = file$
					UserTrackMusicAmount% = UserTrackMusicAmount% + 1
				EndIf
				FreeSound test
			EndIf
		Forever
		CloseDir Dir
	EndIf
	If EnableUserTracks Then DebugLog "User Tracks found: "+UserTrackMusicAmount
	
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
	
	LoadMaterials("DATA\materials.ini")
	
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
	
	ParticleEffect[3] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[3], 3)
	SetTemplateInterval(ParticleEffect[3], 1)
	SetTemplateParticlesPerInterval(ParticleEffect[3], 6)
	SetTemplateEmitterLifeTime(ParticleEffect[3], 6)
	SetTemplateParticleLifeTime(ParticleEffect[3], 20, 30)
	SetTemplateTexture(ParticleEffect[3], "GFX\flash.jpg", 1+2, 3)
	SetTemplateOffset(ParticleEffect[3], -0.1, 0.1, 0.15, 0.15, -0.1, 0.1)
	SetTemplateVelocity(ParticleEffect[3], -0.0375, 0.0375, -0.0375, 0.0375, -0.0375, 0.0375)
	SetTemplateAlignToFall(ParticleEffect[3], True, -45)
	SetTemplateGravity(ParticleEffect[3], 0.001)
	SetTemplateAlphaVel(ParticleEffect[3], True)
	;SetTemplateSize(ParticleEffect[3], 0.0625, 0.125, 0.7, 1)
	SetTemplateSize(ParticleEffect[3], 0.0625*2, 0.0625*2, 0.7*2, 1*2)
	SetTemplateFloor(ParticleEffect[3], 0.0, 0.5)
	
	ParticleEffect[4] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[4], 1)
	SetTemplateInterval(ParticleEffect[4], 7)
	SetTemplateParticlesPerInterval(ParticleEffect[4], 5)
	SetTemplateEmitterLifeTime(ParticleEffect[4], 20)
	SetTemplateParticleLifeTime(ParticleEffect[4], 200, 220)
	SetTemplateTexture(ParticleEffect[4], "GFX\smoke.png", 2, 1)
	SetTemplateOffset(ParticleEffect[4], -0.1, 0.1, 0.15, 0.15, -0.1, 0.1)
	SetTemplateVelocity(ParticleEffect[4], -0.0175, 0.0175, 0.001, 0.001, -0.0175, 0.0075)
	SetTemplateGravity(ParticleEffect[4], 0.000)
	SetTemplateAlphaVel(ParticleEffect[4], True)
	SetTemplateSize(ParticleEffect[4], 0.9, 0.7)
	
	ParticleEffect[5] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[5], 1)
	SetTemplateInterval(ParticleEffect[5], 14)
	SetTemplateParticlesPerInterval(ParticleEffect[5], 4)
	SetTemplateEmitterLifeTime(ParticleEffect[5], 400)
	SetTemplateParticleLifeTime(ParticleEffect[5], 200, 220)
	SetTemplateTexture(ParticleEffect[5], "GFX\smoke2.png", 2, 1)
	SetTemplateOffset(ParticleEffect[5], -0.1, 0.1, 0.9, 0.9, -0.1, 0.1)
	SetTemplateVelocity(ParticleEffect[5], -0.0175, 0.0175, 0.001, 0.001, -0.0175, 0.0075)
	SetTemplateGravity(ParticleEffect[5], 0.000)
	SetTemplateAlphaVel(ParticleEffect[5], True)
	SetTemplateSize(ParticleEffect[5], 1.5, 1.3)
	
	ParticleEffect[6] = CreateTemplate()
	SetTemplateEmitterBlend(ParticleEffect[6], 1)
	SetTemplateInterval(ParticleEffect[6], 7)
	SetTemplateParticlesPerInterval(ParticleEffect[6], 5)
	SetTemplateEmitterLifeTime(ParticleEffect[6], 20)
	SetTemplateParticleLifeTime(ParticleEffect[6], 100, 120)
	SetTemplateTexture(ParticleEffect[6], "GFX\smoke.png", 2, 1)
	SetTemplateOffset(ParticleEffect[6], -0.1, 0.1, 0.15, 0.15, -0.1, 0.1)
	SetTemplateVelocity(ParticleEffect[6], -0.0175/2, 0.0175/2, 0.001, 0.001, -0.0175/2, 0.0075/2)
	SetTemplateGravity(ParticleEffect[6], 0.000)
	SetTemplateAlphaVel(ParticleEffect[6], True)
	SetTemplateSize(ParticleEffect[6], 0.9/4, 0.7/4)
	
	;SetTemplateOffset(ParticleEffect[5], -0.1, 0.1, 0.7, 0.7, -0.1, 0.1)
	;SetTemplateVelocity(ParticleEffect[5], -0.00175, 0.00175, 0.0001, 0.0001, -0.00175, 0.00075)
	;SetTemplateGravity(ParticleEffect[5], 0.000)
	;SetTemplateAlphaVel(ParticleEffect[5], True)
	;SetTemplateSize(ParticleEffect[5], 2, 1.7)
	
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
	
	Room2slCam = CreateCamera()
	CameraViewport(Room2slCam, 0, 0, 128, 128)
	CameraRange Room2slCam, 0.05, 6.0
	CameraZoom(Room2slCam, 0.8)
	HideEntity(Room2slCam)
	NetworkServer\EntitiesLoaded = True
	DrawLoading(30)
	;LoadRoomMeshes()
	
	;CatchErrors("LoadEntities")
End Function

Function InitNewGame()

	If Not udp_GetStream() Then
		NetworkServer\MyID = 1
		NetworkServer\Maxplayers = 1
		NetworkServer\PlayersCount = 1
		multiplayer_CreatePlayer(NetworkServer\MyID)
		NetworkServer\MainPlayer = True
		MyPlayer\BreachType = CLASSD_MODEL
	EndIf
	
	If STEAM_RELEASE Then BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "steam_display", "#Status_Connecting")
	
	Discord_API_SetState("Loading to game")
	Discord_API_SetState("", 2)
	
	MyPlayer\IsLoad = False
	MyPlayer\Pivot = Collider
	MyPlayer\Hitbox = MyHitbox
	MyPlayer\PlayerHead = Camera
	
	Spectate\SpectatePlayer = -1
	NPCcount = 0
	LastRoomID = 0
	GameLoad = 1
	NetworkServer\checkVoice = False
	SELECTED_P_PAGE = 0
	chatScrollDragging = 0
	chatScroll# = 0
	chatMouseMem# = 0
	
	Local i%, de.Decals, d.Doors, it.Items, sc.SecurityCams, e.Events
	
	DrawLoading(45)
	HideDistance# = 15.0
	HeartBeatRate = 70
	
	SeedRnd GenerateSeedNumber(RandomSeed)
	
	AccessCode = 0
	While True
		For i = 0 To 3
			AccessCode = AccessCode + Rand(1,9)*(10^i)
		Next
		If AccessCode <> 7816 Then Exit
		
		AccessCode = 0
	Wend
	
	If NetworkServer\ServerCustomMap <> "" Then
		updatequery()
		LoadMap("multiplayer\serversdata\servermaps\"+NetworkServer\ServerCustomMap)
	ElseIf SelectedMap <> "" And (Not udp_GetStream()) Then
		LoadMap("Map Creator\Maps\"+SelectedMap)
	Else
		CreateMap(3-NetworkServer\MapSize)
	EndIf
	
	InitEvents()
	InitWayPoints()
	
	For r.Rooms = Each Rooms
		For i = 0 To r\MaxLights
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
		
		If (r\RoomTemplate\Name = "start" And (IntroEnabled = False Or NetworkServer\Breach)) Then 
			PositionEntity (Collider, EntityX(r\obj)+3584*RoomScale, 704*RoomScale, EntityZ(r\obj)+1024*RoomScale)
			ResetEntity Collider
			;it.Items = CreateItem("Key Card Omni", "key6", 0,0,0)
			;it\picker = 2
			;it\picked = true
			;Player[2]\x = EntityX(Collider)
			;Player[2]\y = EntityY(Collider)-1
			;Player[2]\z = EntityZ(Collider)
			;Player[2]\SelectedItem = it\ID
			;Player[2]\PLAYER_MOVE = PLAYER_IDLING
			PlayerRoom = r
			If NetworkServer\Breach = False Then
				If NetworkServer\MainPlayer = True Then
					GiveItem("Class D Orientation Leaflet", "paper")
					GiveItem("Document SCP-173", "paper")
				EndIf
			EndIf
		ElseIf (r\RoomTemplate\Name = "173") Then
			If NetworkServer\Breach Then
				PositionEntity Collider, EntityX(r\Objects[5], True), 0.5, EntityZ(r\Objects[5], True)
				ResetEntity Collider
				PlayerRoom = r
				
				RemoteDoorOn = True
				For e.Events = Each Events
					If e\EventName = "gateaentrance" Then
						e\EventState3 = 1
						e\room\RoomDoors[1]\open = True
					ElseIf e\EventName = "exit1" Then
						e\EventState3 = 1
						e\room\RoomDoors[4]\open = True
					EndIf
				Next
			ElseIf IntroEnabled Then
				x = EntityX(r\obj)-(3224.0+1024.0)*RoomScale
				y = 136.0*RoomScale
				z = EntityZ(r\obj)+8.0*RoomScale	
				PositionEntity (Collider, x, y+0.1, z)
				ResetEntity Collider
				PlayerRoom = r
			EndIf
		EndIf
	Next
	
	Curr173 = CreateNPC(NPCtype173, 0, -30.0, 0)
	Curr106 = CreateNPC(NPCtypeOldMan, 0, -30.0, 0)
	Curr106\State = 70 * 60 * Rand(12,17)
	
	DrawLoading(79)
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
		EntityType (it\collider, HIT_ITEM)
		EntityParent(it\collider, 0)
	Next
	
	DrawLoading(80)
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
	TurnEntity(Collider, 0, Rand(160, 200), 0)
	
	ResetEntity Collider

	
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
	
	MoveMouse viewport_center_x,viewport_center_y;320, 240
	
	AASetFont Font1
	
	HidePointer()
	
	BlinkTimer = -10
	BlurTimer = 100
	Stamina = 100
	
	For i% = 0 To 70
		FPSfactor = 1.0
		FlushKeys()
		MovePlayer()
		UpdateDoors()
		UpdateNPCs()
		UpdateWorld()
		;Cls
		If (Int(Float(i)*0.27)<>Int(Float(i-1)*0.27)) Then
			DrawLoading(80+Int(Float(i)*0.27))
		EndIf
	Next
	FreeTextureCache
	
	FlushKeys
	FlushMouse
	
	DropSpeed = 0
	MyPlayer\IsLoad = True
	If udp_GetStream() Then
		NetworkServer\Timeout = MilliSecs()+15000
		
		multiplayer_Update()
		
		If NetworkServer\Breach = False Then
			Repeat
				If Not udp_GetStream() Then Exit
				UpdateFrame(False)
				
				SetPlayerVariables()
				
				multiplayer_Update()
				multiplayer_UpdatePlayers()
				voice_update()
				playersload = 0
				DrawLoading(99, False, True, True)
				MyPlayer\IsLoad = True
				For p.players = Each players
					If p\IsLoad Then playersload = playersload+1
				Next
				If playersload >= NetworkServer\PlayersCount Then Exit
				If Player[1] <> Null Then
					If Player[1]\IsLoad = True Then Exit
				EndIf
			Forever
		EndIf

		If NetworkServer\HaveFiles Then
			DrawLoading(99.3, False, True, True)
			multiplayer_loadobjects(M_WANTFILES)

			Local QueryTimeout% = MilliSecs()+5000
			
			While QueryTimeout > MilliSecs()
				UpdateFrame(False)
				SetPlayerVariables()
				multiplayer_Update()
				multiplayer_UpdatePlayers()
				voice_update()
				
				DrawLoading(99.3, False, True, True)

				If have_querys() Then QueryTimeout = MilliSecs()+1000
			Wend
		
		EndIf
		
		If NetworkServer\Breach Then
			CameraFogNear = 0
			CameraFogFar = HideDistance
		EndIf
		
		If NetworkServer\pspawnx <> 0 Or NetworkServer\pspawny <> 0 Or NetworkServer\pspawnz <> 0 Then
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = NetworkServer\pspawnname Then
					PositionEntity (Collider, NetworkServer\pspawnx,NetworkServer\pspawny,NetworkServer\pspawnz)
					ResetEntity Collider
					PlayerRoom = r
					Exit
				EndIf
			Next
		EndIf
		If GetScripts() Then public_inqueue(public_OnSpawn, True)
	EndIf
	SetPlayerVariables();
	GameLoad = 0
	DrawLoading(100, False, True, True)
	PrevTime = MilliSecs()

	For r.rooms = Each rooms
		If r\roomtemplate\name = "start" Then
			SetSavingPosition(r\RoomTemplate\Name, EntityX(r\obj)+3584*RoomScale, (704*RoomScale), EntityZ(r\obj)+1024*RoomScale, 130.3)
			Exit
		EndIf
	Next
	
	If NetworkServer\Breach Then
		For w.Waypoints = Each Waypoints
			FreeEntity w\obj
			Delete w
		Next
	EndIf
	
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
	
	If MenuBrowser <> Null Then SteamBrowser_Destroy(MenuBrowser)
	
	;Local count = 0
	;for r.rooms = each rooms
	;	count = count + 1
	;n;ext
	;debuglog "room count: "+count
	;count = 0
	;for d.doors = each doors
	;	count = count + 1
	;next
	;debuglog "room count: "+count
End Function

Function InitLoadGame()
	;CatchErrors("Uncaught (InitLoadGame)")
	Local d.Doors, sc.SecurityCams, rt.RoomTemplates, e.Events
	If Not udp_GetStream() Then
		NetworkServer\Maxplayers = 1
		NetworkServer\MyID = 1
		multiplayer_CreatePlayer(NetworkServer\MyID)
		NetworkServer\PlayersCount = 1
		NetworkServer\MainPlayer = True
		MyPlayer\BreachType = CLASSD_MODEL
	EndIf
	Discord_API_SetState("Loading to game")
	Discord_API_SetState("", 2)
	
	NetworkServer\checkvoice = False
	MyPlayer\IsLoad = False
	MyPlayer\Pivot = Collider
	MyPlayer\Hitbox = MyHitbox
	
	Spectate\SpectatePlayer = -1
	NPCcount = 0
	LastRoomID = 0
	GameLoad = 1
	DrawLoading(80)
	
	For d.Doors = Each Doors
		EntityParent(d\obj, 0)
		If d\obj2 <> 0 Then EntityParent(d\obj2, 0)
		If d\frameobj <> 0 Then EntityParent(d\frameobj, 0)
		If d\buttons[0] <> 0 Then EntityParent(d\buttons[0], 0)
		If d\buttons[1] <> 0 Then EntityParent(d\buttons[1], 0)
		
	Next
	
	For sc.SecurityCams = Each SecurityCams
		sc\angle = EntityYaw(sc\obj) + sc\angle
		EntityParent(sc\obj, 0)
	Next
	
	ResetEntity Collider
	
	;InitEvents()
	
	DrawLoading(90)
	
	MoveMouse viewport_center_x,viewport_center_y
	
	AASetFont Font1
	
	HidePointer ()
	
	BlinkTimer = BLINKFREQ
	Stamina = 100
	
	For rt.RoomTemplates = Each RoomTemplates
		If rt\obj <> 0 Then FreeEntity(rt\obj) : rt\obj = 0
	Next
	
	DropSpeed = 0.0
	
	For e.Events = Each Events
		;Loading the necessary stuff for dimension1499, but this will only be done if the player is in this dimension already
		If e\EventName = "dimension1499"
			If e\EventState = 2
				;[Block]
				DrawLoading(91)
				e\room\Objects[0] = CreatePlane()
				Local planetex% = LoadTexture_Strict("GFX\map\dimension1499\grit3.jpg")
				EntityTexture e\room\Objects[0],planetex%
				FreeTexture planetex%
				PositionEntity e\room\Objects[0],0,EntityY(e\room\obj),0
				EntityType e\room\Objects[0],HIT_MAP
				;EntityParent e\room\Objects[0],e\room\obj
				DrawLoading(92)
				NTF_1499Sky = sky_CreateSky("GFX\map\sky\1499sky")
				DrawLoading(93)
				For i = 1 To 15
					e\room\Objects[i] = LoadMesh_Strict("GFX\map\dimension1499\1499object"+i+".b3d")
					HideEntity e\room\Objects[i]
				Next
				DrawLoading(96)
				CreateChunkParts(e\room)
				DrawLoading(97)
				x# = EntityX(e\room\obj)
				z# = EntityZ(e\room\obj)
				Local ch.Chunk
				For i = -2 To 2 Step 2
					ch = CreateChunk(-1,x#*(i*2.5),EntityY(e\room\obj),z#)
				Next
				DrawLoading(98)
				UpdateChunks(e\room,15,False)
				;MoveEntity Collider,0,10,0
				;ResetEntity Collider
				
				DebugLog "Loaded dimension1499 successful"
				
				Exit
				;[End Block]
			EndIf
		EndIf
	Next
	
	FreeTextureCache
	MyPlayer\IsLoad = True
	;CatchErrors("InitLoadGame")
	GameLoad = 0
	DrawLoading(100)
	
	PrevTime = MilliSecs()
	FPSfactor = 0
	ResetInput()
	CameraFogNear = 0.5
	CameraFogFar = 6.0
	For r.rooms = Each Rooms
		If r\roomtemplate\name = "start" Then
			SetSavingPosition(r\RoomTemplate\Name, EntityX(r\obj)+3584*RoomScale, (704*RoomScale), EntityZ(r\obj)+1024*RoomScale, 130.3)
			Exit
		EndIf
	Next
	FlushKeys()
	
End Function

Function NullGame(playbuttonsfx%=True)
	If playbuttonsfx Then PlaySound_Strict ButtonSFX
	
	If MenuBrowser <> Null Then SteamBrowser_Destroy(MenuBrowser)
	
	DeleteGuns()
	ReloadDifficulties()
	FreeParticles()
	ClearTextureCache
	KillSounds()
	cube = 0
	GameLoad = 0
	VEST_OBJ = 0
	NVG_OBJ = 0
	GASMASK_OBJ = 0
	Stated = False
	Spectate\SpectatePlayer = -1
	HoldingGun = 0
	ClassDObj = 0
	ClerkMP = 0
	using294 = False
	input294 = ""
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
	DropSpeed = 0
	Shake = 0
	CurrSpeed = 0
	DeathTimer=0
	HeartBeatVolume = 0
	StaminaEffect = 1.0
	StaminaEffectTimer = 0
	BlinkEffect = 1.0
	BlinkEffectTimer = 0
	Bloodloss = 0
	Injuries = 0
	Infect = 0
	SelectedEnding = ""
	EndingTimer = 0
	ExplosionTimer = 0
	CameraShake = 0
	Shake = 0
	LightFlash = 0
	GodMode = 0
	NoClip = 0
	WireframeState = 0
	WireFrame 0
	WearingGasMask = 0
	WearingHazmat = 0
	WearingVest = 0
	Wearing714 = 0
	If WearingNightVision Then
		CameraFogFar = StoredCameraFogFar
		WearingNightVision = 0
	EndIf
	For s.Screens = Each Screens
		If s\img <> 0 Then FreeImage s\img : s\img = 0
	Next
	For i = 0 To 5
		SCP1025state[i] = 0
	Next
	For i = 0 To MAXACHIEVEMENTS-1
		Achievements(i)=0
	Next
	For x = 0 To MapWidth+1
		For y = 0 To MapHeight+1
			MapTemp(x, y) = 0
			MapFound(x, y) = 0
		Next
	Next
	For itt.ItemTemplates = Each ItemTemplates
		itt\found = False
		If itt\invimg <> 0 Then FreeImage itt\invimg
		If itt\invimg2 <> 0 Then FreeImage itt\invimg2
		If itt\img <> 0 Then FreeImage itt\img
	Next
	I_427\Using = 0
	I_427\Timer = 0.0
	ForceMove = 0.0
	ForceAngle = 0.0	
	Playable = True
	CoffinDistance = 100
	Contained106 = False
	MTFtimer = 0
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
	SoundTransmission = False
	ClosestButton = 0
	InfiniteStamina% = False
	Msg = ""
	MsgTimer = 0
	SelectedItem = Null
	ForestNPC = 0
	ForestNPCTex = 0
	
	For e.Events = Each Events
		If e\Sound<>0 Then FreeSound_Strict e\Sound
		If e\Sound2<>0 Then FreeSound_Strict e\Sound2
	Next
	
	Delete Each rockets
	Delete Each bullets
	Delete Each grenades
	Delete Each p_obj
	Delete Each SingleLights
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
	Delete Each AchievementMsg
	Delete Each Forest
	
	For std.SoundToDelete = Each SoundToDelete
		If std\snd <> 0 Then FreeSound(std\snd)
	Next
	For RS.RedirectedSound = Each RedirectedSound
		If RS\KillOnExit Then
			Local RS2.RedirectedSound = RedirectSound(RS\newfile, RS\file)
			Delete RS2
			Delete RS
		EndIf
	Next
	For rt.RoomTemplates = Each RoomTemplates
		rt\obj = 0
	Next
	
	For i = 0 To 5
		If ChannelPlaying(RadioCHN(i)) Then StopChannel(RadioCHN(i))
	Next
	
	CurrAchvMSGID = 0
	NoTarget% = False
	OptionsMenu% = -1
	QuitMSG% = -1
	AchievementsMenu% = -1
	MusicVolume# = PrevMusicVolume
	SFXVolume# = PrevSFXVolume
	DeafPlayer% = False
	DeafTimer# = 0.0
	IsZombie% = False
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
	AATextCam = 0
	
	FreeAllFonts(False, False)
	
	ClearWorld()
	
	Camera = 0
	ark_blur_cam = 0
	Collider = 0
	Sky = 0
	; ========================== Reload font
	LoadAllFonts(True)
	
	InitFastResize()
	ReloadNecessaryEntities()
End Function

Include "SourceCode\save.bb"

;--------------------------------------- music & sounds ----------------------------------------------

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
				If (Not ChannelPlaying(Chn)) Then
					StopChannel(Chn)
					Chn% = PlaySound_Strict (SoundHandle)
				EndIf
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

Function LoadTempSound(file$)
	If Instr(file, " ") = 1 Then file = Trim$(file)
	If TempSounds[TempSoundIndex]<>0 Then FreeSound_Strict(TempSounds[TempSoundIndex])
	TempSound = LoadSound_Strict(file)
	TempSounds[TempSoundIndex] = TempSound
	
	TempSoundIndex=(TempSoundIndex+1) Mod 10
	
	Return TempSound
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

Function UpdateMusic()
	;SetFixedTimeStepsBackup
	If ConsoleFlush Then
		If Not ChannelPlaying(ConsoleMusPlay) Then ConsoleMusPlay = PlaySound(ConsoleMusFlush)
	ElseIf (Not PlayCustomMusic)
		If NowPlaying <> ShouldPlay ; playing the wrong clip, fade out
			CurrMusicVolume# = Max(CurrMusicVolume - (FPSfactor / 250.0), 0)
			If CurrMusicVolume = 0 Or ShouldPlay = 26 Then
				If NowPlaying<66
					StopStream_Strict(MusicCHN)
				EndIf
				NowPlaying = ShouldPlay
				MusicCHN = 0
				CurrMusic=0
			EndIf
		Else ; playing the right clip
			CurrMusicVolume = CurrMusicVolume + (MusicVolume - CurrMusicVolume) * (0.1*FPSfactor)
		EndIf
		
		If NowPlaying < 66
			If CurrMusic = 0
				MusicCHN = StreamSound_Strict("SFX\Music\"+Music(NowPlaying)+".ogg",0.0,Mode)
				CurrMusic = 1
			EndIf
			SetStreamVolume_Strict(MusicCHN,CurrMusicVolume)
		EndIf
	Else
		If FPSfactor > 0 Or OptionsMenu = 2 Then
			;CurrMusicVolume = 1.0
			If (Not ChannelPlaying(MusicCHN)) Then MusicCHN = PlaySound_Strict(CustomMusic)
			ChannelVolume MusicCHN,1.0*MusicVolume
		EndIf
	EndIf
	;ReturnFixedTimeSteps
End Function 

Function PauseSounds()
	For e.events = Each Events
		If e\soundchn <> 0 Then
			If (Not e\soundchn_isstream)
				If ChannelPlaying(e\soundchn) Then PauseChannel(e\soundchn)
			Else
				SetStreamPaused_Strict(e\soundchn,True)
			EndIf
		EndIf
		If e\soundchn2 <> 0 Then
			If (Not e\soundchn2_isstream)
				If ChannelPlaying(e\soundchn2) Then PauseChannel(e\soundchn2)
			Else
				SetStreamPaused_Strict(e\soundchn2,True)
			EndIf
		EndIf		
	Next
	
	For n.npcs = Each NPCs
		If n\soundchn <> 0 Then
			If (Not n\soundchn_isstream)
				If ChannelPlaying(n\soundchn) Then PauseChannel(n\soundchn)
			Else
				If n\soundchn_isstream=True
					SetStreamPaused_Strict(n\soundchn,True)
				EndIf
			EndIf
		EndIf
		If n\soundchn2 <> 0 Then
			If (Not n\soundchn2_isstream)
				If ChannelPlaying(n\soundchn2) Then PauseChannel(n\soundchn2)
			Else
				If n\soundchn2_isstream=True
					SetStreamPaused_Strict(n\soundchn2,True)
				EndIf
			EndIf
		EndIf
	Next	
	
	For d.doors = Each Doors
		If d\soundchn <> 0 Then
			If ChannelPlaying(d\soundchn) Then PauseChannel(d\soundchn)
		EndIf
	Next
	
	For dem.DevilEmitters = Each DevilEmitters
		If dem\soundchn <> 0 Then
			If ChannelPlaying(dem\soundchn) Then PauseChannel(dem\soundchn)
		EndIf
	Next
	
	If AmbientSFXCHN <> 0 Then
		If ChannelPlaying(AmbientSFXCHN) Then PauseChannel(AmbientSFXCHN)
	EndIf
	
	If BreathCHN <> 0 Then
		If ChannelPlaying(BreathCHN) Then PauseChannel(BreathCHN)
	EndIf
	
	If CoughCHN <> 0 Then
		If ChannelPlaying(CoughCHN) Then PauseChannel(CoughCHN)
	EndIf

	If VomitCHN <> 0 Then
		If ChannelPlaying(VomitCHN) Then PauseChannel(VomitCHN)
	EndIf
	
	If IntercomStreamCHN <> 0
		SetStreamPaused_Strict(IntercomStreamCHN,True)
	EndIf
End Function

Function ResumeSounds()
	For e.events = Each Events
		If e\soundchn <> 0 Then
			If (Not e\soundchn_isstream)
				If ChannelPlaying(e\soundchn) Then ResumeChannel(e\soundchn)
			Else
				SetStreamPaused_Strict(e\soundchn,False)
			EndIf
		EndIf
		If e\soundchn2 <> 0 Then
			If (Not e\soundchn2_isstream)
				If ChannelPlaying(e\soundchn2) Then ResumeChannel(e\soundchn2)
			Else
				SetStreamPaused_Strict(e\soundchn2,False)
			EndIf
		EndIf	
	Next
	
	For n.npcs = Each NPCs
		If n\soundchn <> 0 Then
			If (Not n\soundchn_isstream)
				If ChannelPlaying(n\soundchn) Then ResumeChannel(n\soundchn)
			Else
				If n\soundchn_isstream=True
					SetStreamPaused_Strict(n\soundchn,False)
				EndIf
			EndIf
		EndIf
		If n\soundchn2 <> 0 Then
			If (Not n\soundchn2_isstream)
				If ChannelPlaying(n\soundchn2) Then ResumeChannel(n\soundchn2)
			Else
				If n\soundchn2_isstream=True
					SetStreamPaused_Strict(n\soundchn2,False)
				EndIf
			EndIf
		EndIf
	Next	
	
	For d.doors = Each Doors
		If d\soundchn <> 0 Then
			If ChannelPlaying(d\soundchn) Then ResumeChannel(d\soundchn)
		EndIf
	Next
	
	For dem.DevilEmitters = Each DevilEmitters
		If dem\soundchn <> 0 Then
			If ChannelPlaying(dem\soundchn) Then ResumeChannel(dem\soundchn)
		EndIf
	Next
	
	If AmbientSFXCHN <> 0 Then
		If ChannelPlaying(AmbientSFXCHN) Then ResumeChannel(AmbientSFXCHN)
	EndIf	
	
	If BreathCHN <> 0 Then
		If ChannelPlaying(BreathCHN) Then ResumeChannel(BreathCHN)
	EndIf
	
	If IntercomStreamCHN <> 0
		SetStreamPaused_Strict(IntercomStreamCHN,False)
	EndIf
End Function

Function KillSounds()
	Local i%,e.Events,n.NPCs,d.Doors,dem.DevilEmitters,snd.Sound
	
	For i=0 To 9
		If TempSounds[i]<>0 Then FreeSound_Strict TempSounds[i] : TempSounds[i]=0
	Next
	For e.Events = Each Events
		If e\SoundCHN <> 0 Then
			If (Not e\SoundCHN_isStream)
				If ChannelPlaying(e\SoundCHN) Then StopChannel(e\SoundCHN)
			Else
				StopStream_Strict(e\SoundCHN)
			EndIf
		EndIf
		If e\SoundCHN2 <> 0 Then
			If (Not e\SoundCHN2_isStream)
				If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2)
			Else
				StopStream_Strict(e\SoundCHN2)
			EndIf
		EndIf		
	Next
	For n.NPCs = Each NPCs
		If n\SoundChn <> 0 Then
			If (Not n\SoundChn_IsStream)
				If ChannelPlaying(n\SoundChn) Then StopChannel(n\SoundChn)
			Else
				StopStream_Strict(n\SoundChn)
			EndIf
		EndIf
		If n\SoundChn2 <> 0 Then
			If (Not n\SoundChn2_IsStream)
				If ChannelPlaying(n\SoundChn2) Then StopChannel(n\SoundChn2)
			Else
				StopStream_Strict(n\SoundChn2)
			EndIf
		EndIf
	Next	
	For d.Doors = Each Doors
		If d\SoundCHN <> 0 Then
			If ChannelPlaying(d\SoundCHN) Then StopChannel(d\SoundCHN)
		EndIf
	Next
	For dem.DevilEmitters = Each DevilEmitters
		If dem\SoundCHN <> 0 Then
			If ChannelPlaying(dem\SoundCHN) Then StopChannel(dem\SoundCHN)
		EndIf
	Next
	If AmbientSFXCHN <> 0 Then
		If ChannelPlaying(AmbientSFXCHN) Then StopChannel(AmbientSFXCHN)
	EndIf
	If BreathCHN <> 0 Then
		If ChannelPlaying(BreathCHN) Then StopChannel(BreathCHN)
	EndIf
	If IntercomStreamCHN <> 0
		StopStream_Strict(IntercomStreamCHN)
		IntercomStreamCHN = 0
	EndIf
	For snd.Sound = Each Sound
		snd\channelscount = 0
		If snd\internalHandle <> 0 Then
			FreeSound snd\internalHandle
			snd\internalHandle = 0
			snd\releaseTime = 0
			Delete snd
		EndIf
	Next
	
	For snd.Sound = Each Sound
		For i = 0 To 31
			If snd\channels[i]<>0 Then
				StopChannel snd\channels[i]
			EndIf
		Next
	Next
	
	DebugLog "Terminated all sounds"
	
	ButtonSFX% = LoadSound_Strict("SFX\Interact\Button.ogg")
	
End Function

Function GetStepSound(entity%)
    Local picker%,brush%,texture%,name$
    Local mat.Materials
    
    picker = LinePick(EntityX(entity),EntityY(entity),EntityZ(entity),0,-1,0)
    If picker <> 0 Then
        If GetEntityType(picker) <> HIT_MAP Then Return 0
        brush = GetSurfaceBrush(GetSurface(picker,CountSurfaces(picker)))
        If brush <> 0 Then
            texture = GetBrushTexture(brush,3)
            If texture <> 0 Then
                name = StripPath(TextureName(texture))
                If (name <> "") Then FreeTexture(texture)
				For mat.Materials = Each Materials
					If mat\name = name Then
						If mat\StepSound > 0 Then
							FreeBrush(brush)
							Return mat\StepSound-1
						EndIf
						Exit
					EndIf
				Next                
			EndIf
			texture = GetBrushTexture(brush,2)
			If texture <> 0 Then
				name = StripPath(TextureName(texture))
				If (name <> "") Then FreeTexture(texture)
				For mat.Materials = Each Materials
					If mat\name = name Then
						If mat\StepSound > 0 Then
							FreeBrush(brush)
							Return mat\StepSound-1
						EndIf
						Exit
					EndIf
				Next                
			EndIf
			texture = GetBrushTexture(brush,1)
			If texture <> 0 Then
				name = StripPath(TextureName(texture))
				If (name <> "") Then FreeTexture(texture)
				FreeBrush(brush)
				For mat.Materials = Each Materials
					If mat\name = name Then
						If mat\StepSound > 0 Then
							Return mat\StepSound-1
						EndIf
						Exit
					EndIf
				Next                
			EndIf
		EndIf
	EndIf
    
    Return 0
End Function

Function UpdateSoundOrigin2(Chn%, cam%, entity%, range# = 10, volume# = 1.0)
	range# = Max(range,1.0)
	
	If volume>0 Then
		
		Local dist# = EntityDistance(cam, entity) / range#
		If 1 - dist# > 0 And 1 - dist# < 1 Then
			
			Local panvalue# = Sin(-DeltaYaw(cam,entity))
			
			ChannelVolume(Chn, volume# * (1 - dist#))
			ChannelPan(Chn, panvalue)
		Else
			ChannelVolume(Chn, 0)
		EndIf
	Else
		If Chn <> 0 Then
			ChannelVolume (Chn, 0)
		EndIf 
	EndIf
End Function

Function UpdateSoundOrigin(Chn%, cam%, entity%, range# = 10, volume# = 1.0)
	range# = Max(range,1.0)
	
	If volume>0 Then
		
		Local dist# = EntityDistance(cam, entity) / range#
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
End Function
Function UpdateSoundFMOD(Chn%, cam%, entity%, range# = 10, volume# = 1.0)
	range# = Max(range,1.0)
	
	If volume>0 Then
		
		Local dist# = EntityDistance(cam, entity) / range#
		If 1 - dist# > 0 And 1 - dist# < 1 Then
			
			Local panvalue# = Sin(-DeltaYaw(cam,entity))
			
			FSOUND_SetVolume(Chn, (volume# * (1 - dist#)*SFXVolume#)*255)
			FSOUND_SetPan(Chn, Int((255.0/2.0)+((255.0/2.0)*panvalue)))
		Else
			FSOUND_SetVolume(Chn, 0)
		EndIf
	Else
		If Chn <> 0 Then FSOUND_SetVolume (Chn, 0)
	EndIf
End Function
;--------------------------------------- random -------------------------------------------------------

Function f2s$(n#, count%)
	Return Left(n, Len(Int(Str(n)))+count+1)
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
			If newTime >= quit Then 
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
	If NetworkServer\MainPlayer = False Then Return
	DebugLog "Using item "+item\itemtemplate\name
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
					If NetworkServer\Breach = False Then
						n.NPCs = CreateNPC(NPCtype1499,x,y,z)
						n\IsSpawned = True
						n\State = 1
						n\Sound = LoadSound_Strict("SFX\SCP\1499\Triggered.ogg")
						n\SoundChn = PlaySound2(n\Sound, Camera, n\Collider,20.0)
						n\State3 = 1
					EndIf
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
					EntityType (it3\collider, HIT_ITEM)
					EntityType (it4\collider, HIT_ITEM)
					EntityType (it5\collider, HIT_ITEM)
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
							If NetworkServer\Breach = False Then
								n.NPCs = CreateNPC(NPCtype008,x,y,z)
								n\IsSpawned = True
								n\State = 2
							EndIf
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
	If item <> Null Then
		item\picker = 0
		item\Picked = False
	EndIf
	If it2 <> Null Then
		it2\picker = 0
		it2\Picked = False
		EntityType (it2\collider, HIT_ITEM)
	EndIf
	If d <> Null Then multiplayer_WriteDecal(d,1)
End Function

Function Use294()
	Local x#,y#, xtemp%,ytemp%, strtemp$, temp%
	
	ShowPointer()
	
	x = GraphicWidth/2 - (ImageWidth(Panel294)/2)
	y = GraphicHeight/2 - (ImageHeight(Panel294)/2)
	DrawImage Panel294, x, y
	If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
	
	temp = True
	If PlayerRoom\SoundCHN<>0 Then temp = False
	
	AAText x+907, y+185, Input294, True,True
	
	If temp Then
		If MouseHit1 Then
			xtemp = Floor((ScaledMouseX()-x-228) / 35.5)
			ytemp = Floor((ScaledMouseY()-y-342) / 36.5)
			
			If ytemp => 0 And ytemp < 5 Then
				If xtemp => 0 And xtemp < 10 Then 
					PlaySound_Strict ButtonSFX
					strtemp = ""
			
					temp = False
					
					Select ytemp
						Case 0
							strtemp = (xtemp + 1) Mod 10
						Case 1
							Select xtemp
								Case 0
									strtemp = "Q"
								Case 1
									strtemp = "W"
								Case 2
									strtemp = "E"
								Case 3
									strtemp = "R"
								Case 4
									strtemp = "T"
								Case 5
									strtemp = "Y"
								Case 6
									strtemp = "U"
								Case 7
									strtemp = "I"
								Case 8
									strtemp = "O"
								Case 9
									strtemp = "P"
							End Select
						Case 2
							Select xtemp
								Case 0
									strtemp = "A"
								Case 1
									strtemp = "S"
								Case 2
									strtemp = "D"
								Case 3
									strtemp = "F"
								Case 4
									strtemp = "G"
								Case 5
									strtemp = "H"
								Case 6
									strtemp = "J"
								Case 7
									strtemp = "K"
								Case 8
									strtemp = "L"
								Case 9 ;dispense
									temp = True
							End Select
						Case 3
							Select xtemp
								Case 0
									strtemp = "Z"
								Case 1
									strtemp = "X"
								Case 2
									strtemp = "C"
								Case 3
									strtemp = "V"
								Case 4
									strtemp = "B"
								Case 5
									strtemp = "N"
								Case 6
									strtemp = "M"
								Case 7
									strtemp = "-"
								Case 8
									strtemp = " "
								Case 9
									Input294 = Left(Input294, Max(Len(Input294)-1,0))
							End Select
						Case 4
							strtemp = " "
					End Select
				EndIf
			EndIf
			
			
			Input294 = Input294 + strtemp
			
			Input294 = Left(Input294, Min(Len(Input294),15))
			
			If temp And Input294<>"" Then ;dispense
				Input294 = Trim(Lower(Input294))
				If Left(Input294, Min(7,Len(Input294))) = "cup of " Then
					Input294 = Right(Input294, Len(Input294)-7)
				ElseIf Left(Input294, Min(9,Len(Input294))) = "a cup of " 
					Input294 = Right(Input294, Len(Input294)-9)
				EndIf
				
				If Input294<>""
					Local loc% = GetINISectionLocation("DATA\SCP-294.ini",Input294)
				EndIf
				
				If loc > 0 Then
					strtemp$ = GetINIString2("DATA\SCP-294.ini", loc, "dispensesound")
					If strtemp="" Then
						PlayerRoom\SoundCHN = PlaySound_Strict (LoadTempSound("SFX\SCP\294\dispense1.ogg"))
						multiplayer_WriteTempSound("SFX\SCP\294\dispense1.ogg")
					Else
						PlayerRoom\SoundCHN = PlaySound_Strict (LoadTempSound(strtemp))
						multiplayer_WriteTempSound(strtemp)
					EndIf
					
					If GetINIInt2("DATA\SCP-294.ini", loc, "explosion")=True Then 
						ExplosionTimer = 135
						If udp_GetStream() Then
							udp_WriteByte(M_EXPL)
							udp_WriteByte(NetworkServer\MyID)
							udp_WriteInt(ExplosionTimer)
							udp_SendMessage()
						EndIf
						DeathMSG = GetINIString2("DATA\SCP-294.ini", loc, "deathmessage")
					EndIf
					
					strtemp$ = GetINIString2("DATA\SCP-294.ini", loc, "color")
					
					sep1 = Instr(strtemp, ",", 1)
					sep2 = Instr(strtemp, ",", sep1+1)
					r% = Trim(Left(strtemp, sep1-1))
					g% = Trim(Mid(strtemp, sep1+1, sep2-sep1-1))
					b% = Trim(Right(strtemp, Len(strtemp)-sep2))
					
					alpha# = Float(GetINIString2("DATA\SCP-294.ini", loc, "alpha",1.0))
					glow = GetINIInt2("DATA\SCP-294.ini", loc, "glow")
					;If alpha = 0 Then alpha = 1.0
					If glow Then alpha = -alpha
					
					it.items = CreateItem("Cup", "cup", EntityX(PlayerRoom\Objects[1],True),EntityY(PlayerRoom\Objects[1],True),EntityZ(PlayerRoom\Objects[1],True), r,g,b,alpha)
					it\name = "Cup of "+Input294
					EntityType (it\collider, HIT_ITEM)
					;SendItemInfo(it)
				Else
					;out of range
					Input294 = "OUT OF RANGE"
					PlayerRoom\SoundCHN = PlaySound_Strict (LoadTempSound("SFX\SCP\294\outofrange.ogg"))
					multiplayer_WriteTempSound("SFX\SCP\294\outofrange.ogg")
				EndIf
				
			EndIf
			
		EndIf ;if mousehit1
		
		If MouseHit2 Or (Not Using294) Then 
			HidePointer()
			Using294 = False
			Input294 = ""
			ResetMouse()
		EndIf
		
	Else ;playing a dispensing sound
		If Input294 <> "OUT OF RANGE" Then Input294 = "DISPENSING..."
		
		If Not ChannelPlaying(PlayerRoom\SoundCHN) Then
			If Input294 <> "OUT OF RANGE" Then
				HidePointer()
				
				Using294 = False
				ResetMouse()
				Local e.Events
				For e.Events = Each Events
					If e\room = PlayerRoom
						e\EventState2 = 0
						Exit
					EndIf
				Next
			EndIf
			Input294=""
			PlayerRoom\SoundCHN=0
		EndIf
	EndIf
	
End Function

Function Use427()
	Local i%,pvt%,de.Decals,tempchn%
	Local prevI427Timer# = I_427\Timer
	
	If I_427\Timer < 70*360
		If I_427\Using=True Then
			I_427\Timer = I_427\Timer + FPSfactor
			If Injuries > 0.0 Then
				Injuries = Max(Injuries - 0.0005 * FPSfactor,0.0)
			EndIf
			If Bloodloss > 0.0 And Injuries <= 1.0 Then
				Bloodloss = Max(Bloodloss - 0.001 * FPSfactor,0.0)
			EndIf
			If Infect > 0.0 Then
				Infect = Max(Infect - 0.001 * FPSfactor,0.0)
			EndIf
			For i = 0 To 5
				If SCP1025state[i]>0.0 Then
					SCP1025state[i] = Max(SCP1025state[i] - 0.001 * FPSfactor,0.0)
				EndIf
			Next
			If I_427\Sound[0]=0 Then
				I_427\Sound[0] = LoadSound_Strict("SFX\SCP\427\Effect.ogg")
			EndIf
			If (Not ChannelPlaying(I_427\SoundCHN[0])) Then
				I_427\SoundCHN[0] = PlaySound_Strict(I_427\Sound[0])
				multiplayer_WriteSound(I_427\Sound[0])
			EndIf
			If I_427\Timer => 70*180 Then
				If I_427\Sound[1]=0 Then
					I_427\Sound[1] = LoadSound_Strict("SFX\SCP\427\Transform.ogg")
				EndIf
				If (Not ChannelPlaying(I_427\SoundCHN[1])) Then
					I_427\SoundCHN[1] = PlaySound_Strict(I_427\Sound[1])
					multiplayer_WriteSound(I_427\Sound[1])
				EndIf
			EndIf
			If prevI427Timer < 70*60 And I_427\Timer => 70*60 Then
				Msg = "You feel refreshed and energetic."
				MsgTimer = 70*5
			ElseIf prevI427Timer < 70*180 And I_427\Timer => 70*180 Then
				Msg = "You feel gentle muscle spasms all over your body."
				MsgTimer = 70*5
			EndIf
		Else
			For i = 0 To 1
				If I_427\SoundCHN[i]<>0 Then
					If ChannelPlaying(I_427\SoundCHN[i]) Then
						StopChannel(I_427\SoundCHN[i])
					EndIf
				EndIf
			Next
		EndIf
	Else
		If prevI427Timer-FPSfactor < 70*360 And I_427\Timer => 70*360 Then
			Msg = "Your muscles are swelling. You feel more powerful than ever."
			MsgTimer = 70*5
		ElseIf prevI427Timer-FPSfactor < 70*390 And I_427\Timer => 70*390 Then
			Msg = "You can't feel your legs. But you don't need legs anymore."
			MsgTimer = 70*5
		EndIf
		I_427\Timer = I_427\Timer + FPSfactor
		If I_427\Sound[0]=0 Then
			I_427\Sound[0] = LoadSound_Strict("SFX\SCP\427\Effect.ogg")
		EndIf
		If I_427\Sound[1]=0 Then
			I_427\Sound[1] = LoadSound_Strict("SFX\SCP\427\Transform.ogg")
		EndIf
		For i = 0 To 1
			If (Not ChannelPlaying(I_427\SoundCHN[i])) Then
				I_427\SoundCHN[i] = PlaySound_Strict(I_427\Sound[i])
				multiplayer_WriteSound(I_427\Sound[i])
			EndIf
		Next
		If Rnd(200)<2.0 Then
			pvt = CreatePivot()
			PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
			TurnEntity pvt, 90, 0, 0
			EntityPick(pvt,0.3)
			de.Decals = CreateDecal(20, PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
			de\Size = Rnd(0.03,0.08)*2.0 : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\Size, de\Size
			multiplayer_WriteDecal(de)
			tempchn% = PlaySound_Strict (DripSFX(Rand(0,2)))
			ChannelVolume tempchn, Rnd(0.0,0.8)*SFXVolume
			ChannelPitch tempchn, Rand(20000,30000)
			FreeEntity pvt
			BlurTimer = 800
			multiplayer_WriteSound(DripSFX(Rand(0,2)), 0,0,0,10,0.8)
		EndIf
		If I_427\Timer >= 70*420 Then
			Kill("died mysteriously")
			DeathMSG = Chr(34)+"Requesting support from MTF Nu-7. We need more firepower to take this thing down."+Chr(34)
			I_427\Timer = 0
			I_427\Using = 0
		ElseIf I_427\Timer >= 70*390 Then
			Crouch = True
		EndIf
	EndIf
	
End Function


Function UpdateMTF%()
	If PlayerRoom\RoomTemplate\Name = "gateaentrance" Or (Not NetworkServer\MainPlayer) Then Return
	
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
					If (p\X <> 0 Or p\Y <> 0 Or p\Z <> 0) And p\IsLoad Then
						If p\PlayerRoomName <> "pocketdimension" And p\playerroomname <> "dimension1499" Then
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
			For it.Items = Each items
				If it\Picker <> 0 Then
					If (Left(it\itemtemplate\name, 4) = "SCP-") And (Left(it\itemtemplate\name, 7) <> "SCP-035") And (Left(it\itemtemplate\name, 7) <> "SCP-093")
						PlayAnnouncement("SFX\Character\MTF\ThreatAnnouncPossession.ogg")
						MTFtimer = 25000
						Return
						Exit
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


Function UpdateInfect()
	Local temp#, i%, r.Rooms
	
	Local teleportForInfect% = False
	
	If PlayerRoom\RoomTemplate\Name = "room860"
		If room860event\EventState = 1.0 Then teleportForInfect = False
	ElseIf PlayerRoom\RoomTemplate\Name = "dimension1499" Or PlayerRoom\RoomTemplate\Name = "pocketdimension" Or PlayerRoom\RoomTemplate\Name = "gatea"
		teleportForInfect = False
	ElseIf PlayerRoom\RoomTemplate\Name = "exit1" And EntityY(Collider)>1040.0*RoomScale
		teleportForInfect = False
	EndIf
	
	If Infect>0 Then
		ShowEntity InfectOverlay
		
		If Infect < 93.0 Then
			temp=Infect
			If (Not I_427\Using And I_427\Timer < 70*360) Then
				Infect = Min(Infect+FPSfactor*0.002,100)
			EndIf
			
			BlurTimer = Max(Infect*3*(2.0-CrouchState),BlurTimer)
			
			HeartBeatRate = Max(HeartBeatRate, 100)
			HeartBeatVolume = Max(HeartBeatVolume, Infect/120.0)
			
			EntityAlpha InfectOverlay, Min(((Infect*0.2)^2)/1000.0,0.5) * (Sin(MilliSecs2()/8.0)+2.0)
			
			For i = 0 To 6
				If Infect>i*15+10 And temp =< i*15+10 Then
					PlaySound_Strict LoadTempSound("SFX\SCP\008\Voices"+i+".ogg")
				EndIf
			Next
			
			If Infect > 20 And temp =< 20.0 Then
				Msg = "You feel kinda feverish."
				MsgTimer = 70*6
			ElseIf Infect > 40 And temp =< 40.0
				Msg = "You feel nauseated."
				MsgTimer = 70*6
			ElseIf Infect > 60 And temp =< 60.0
				Msg = "The nausea's getting worse."
				MsgTimer = 70*6
			ElseIf Infect > 80 And temp =< 80.0
				Msg = "You feel very faint."
				MsgTimer = 70*6
			ElseIf Infect =>91.5
				BlinkTimer = Max(Min(-10*(Infect-91.5),BlinkTimer),-10)
				Kill("was killed by SCP-008")
				Infect = 0
				HideEntity InfectOverlay
			EndIf
		Else
			
			temp=Infect
			Infect = Min(Infect+FPSfactor*0.004,100)
			
			If teleportForInfect
				If Infect < 94.7 Then
					EntityAlpha InfectOverlay, 0.5 * (Sin(MilliSecs2()/8.0)+2.0)
					BlurTimer = 900
					
					If Infect > 94.5 Then BlinkTimer = Max(Min(-50*(Infect-94.5),BlinkTimer),-10)
					PointEntity Collider, PlayerRoom\NPC[0]\Collider
					PointEntity PlayerRoom\NPC[0]\Collider, Collider
					PointEntity Camera, PlayerRoom\NPC[0]\Collider,EntityRoll(Camera)
					ForceMove = 0.75
					Injuries = 2.5
					Bloodloss = 0
					UnableToMove = False
					
					Animate2(PlayerRoom\NPC[0]\obj, AnimTime(PlayerRoom\NPC[0]\obj), 357, 381, 0.3)
				ElseIf Infect < 98.5
					
					EntityAlpha InfectOverlay, 0.5 * (Sin(MilliSecs2()/5.0)+2.0)
					BlurTimer = 950
					
					ForceMove = 0.0
					UnableToMove = True
					PointEntity Camera, PlayerRoom\NPC[0]\Collider
					
					If temp < 94.7 Then 
						PlayerRoom\NPC[0]\Sound = LoadSound_Strict("SFX\SCP\008\KillScientist2.ogg")
						PlayerRoom\NPC[0]\SoundChn = PlaySound_Strict(PlayerRoom\NPC[0]\Sound)
						
						DeathMSG = "Subject D-9341 found ingesting Dr. [REDACTED] at Sector [REDACTED]. Subject was immediately terminated by Nine-Tailed Fox and sent for autopsy. "
						DeathMSG = DeathMSG + "SCP-008 infection was confirmed, after which the body was incinerated."
						
						Kill()
						de.Decals = CreateDecal(3, EntityX(PlayerRoom\NPC[0]\Collider), 544*RoomScale + 0.01, EntityZ(PlayerRoom\NPC[0]\Collider),90,Rnd(360),0)
						de\Size = 0.8
						ScaleSprite(de\obj, de\Size,de\Size)
					ElseIf Infect > 96
						BlinkTimer = Max(Min(-10*(Infect-96),BlinkTimer),-10)
					Else
						KillTimer = Max(-350, KillTimer)
					EndIf
					
					If PlayerRoom\NPC[0]\State2=0 Then
						Animate2(PlayerRoom\NPC[0]\obj, AnimTime(PlayerRoom\NPC[0]\obj), 13, 19, 0.3,False)
						If AnimTime(PlayerRoom\NPC[0]\obj) => 19 Then PlayerRoom\NPC[0]\State2=1
					Else
						Animate2(PlayerRoom\NPC[0]\obj, AnimTime(PlayerRoom\NPC[0]\obj), 19, 13, -0.3)
						If AnimTime(PlayerRoom\NPC[0]\obj) =< 13 Then PlayerRoom\NPC[0]\State2=0
					EndIf
					
					If ParticleAmount>0
						If Rand(50)=1 Then
							p.Particles = CreateParticle(EntityX(PlayerRoom\NPC[0]\Collider),EntityY(PlayerRoom\NPC[0]\Collider),EntityZ(PlayerRoom\NPC[0]\Collider), 5, Rnd(0.05,0.1), 0.15, 200)
							p\speed = 0.01
							p\SizeChange = 0.01
							p\A = 0.5
							p\Achange = -0.01
							RotateEntity p\pvt, Rnd(360),Rnd(360),0
						EndIf
					EndIf
					
					PositionEntity Head, EntityX(PlayerRoom\NPC[0]\Collider,True), EntityY(PlayerRoom\NPC[0]\Collider,True)+0.65,EntityZ(PlayerRoom\NPC[0]\Collider,True),True
					RotateEntity Head, (1.0+Sin(MilliSecs2()/5.0))*15, PlayerRoom\angle-180, 0, True
					MoveEntity Head, 0,0,-0.4
					TurnEntity Head, 80+(Sin(MilliSecs2()/5.0))*30,(Sin(MilliSecs2()/5.0))*40,0
				EndIf
			Else
				Kill("was killed by SCP-008")
				BlinkTimer = Max(Min(-10*(Infect-96),BlinkTimer),-10)
				If PlayerRoom\RoomTemplate\Name = "dimension1499" Then
					DeathMSG = "The whereabouts of SCP-1499 are still unknown, but a recon team has been dispatched to investigate reports of a violent attack to a church in the Russian town of [REDACTED]."
				ElseIf PlayerRoom\RoomTemplate\Name = "gatea" Or PlayerRoom\RoomTemplate\Name = "exit1" Then
					DeathMSG = "Subject D-9341 found wandering around Gate "
					If PlayerRoom\RoomTemplate\Name = "gatea" Then
						DeathMSG = DeathMSG + "A"
					Else
						DeathMSG = DeathMSG + "B"
					EndIf
					DeathMSG = DeathMSG + ". Subject was immediately terminated by Nine-Tailed Fox and sent for autopsy. "
					DeathMSG = DeathMSG + "SCP-008 infection was confirmed, after which the body was incinerated."
				Else
					DeathMSG = ""
				EndIf
			EndIf
		EndIf
		
		
	Else
		HideEntity InfectOverlay
	EndIf
End Function

;--------------------------------------- math -------------------------------------------------------
Function PackTwoValues(one, two)
	Return (one Shl 16) Or two
End Function

Function GetFirstPackedValue(value)
	Return value Shr 16
End Function

Function GetSecondPackedValue(value)
	Return value And $FFFF
End Function

Function GenerateSeedNumber(seed$)
 	Local temp% = 0
 	Local shift% = 0
 	For i = 1 To Len(seed)
 		temp = temp Xor (Asc(Mid(seed,i,1)) Shl shift)
 		shift=(shift+1) Mod 24
	Next
 	Return temp
End Function

Function GenerateIndex(dat$)
	Local Value%
	For i = 1 To Len(dat)
		Value = Value + Asc(Mid(dat, i, 1))
	Next
	Return Value
End Function

Function ReverseTo#(value#, startval#, endval#)
	If value > endval Or value < startval Then value = startval#
	Return value
End Function

Function Distance#(x1#, y1#, x2#, y2#)
	Local x# = x2 - x1, y# = y2 - y1
	Return(Sqr(x*x + y*y))
End Function

Function Distance3#(x1#, y1#, z1#, x2#, y2#, z2#)
	Return ((x2-x1)^2 + (y2-y1)^2 + (z2-z1)^2)^0.5
End Function

Function GetAngleMax#(val#, old#)
	Local diff# = WrapAngle(val) - WrapAngle(old)
	If diff > 180 Then diff = diff - 360
	If diff < - 180 Then diff = diff + 360
	Return diff
End Function

;Function AngleMax(angle#, max#)
;	if angle < 0 then
;		return min(max, angle)
;	else
;		return max(max, angle)
;	endif
;End Function

;Function AngleMin(angle#, min#)
;	if angle < 0 then
;		return max(min, angle)
;	else
;		return min(min, angle)
;	endif
;End Function

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

Function converttoshort(fvalue#)
	Return (fvalue*10.0)+32767
End Function

Function convertshorttovalue#(fvalue#)
	Return (fvalue-32767.0)/10.0
End Function

Function DegToRad#(dir#)
	Return (dir*Pi)/180
End Function
Function RadToDeg#(dir#)
	Return dir * 180 / Pi
End Function

Function FlipAngle#(angle#)
	If Angle < 0 Then
		Return sqrvalue(angle)
	Else
		Return -angle
	EndIf
End Function

Function FlipValue#(val#)
	If val < 0 Then
		Return sqrvalue(val)
	Else
		Return -val
	EndIf
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

Function ToAngle#(val#, old#, speed#)
	If FPSfactor = 0 Then Return old

	Local diff# = WrapAngle(val) - WrapAngle(old)
	If diff > 180 Then diff = diff - 360
	If diff < - 180 Then diff = diff + 360
	Return WrapAngle(old + diff * (speed*FPSfactor))
End Function

Function CurveAngle#(val#, old#, smooth#)
	If FPSfactor = 0 Then Return old
	
   Local diff# = WrapAngle(val) - WrapAngle(old)
   If diff > 180 Then diff = diff - 360
   If diff < - 180 Then diff = diff + 360
   Return WrapAngle(old + diff * (1.0 / smooth * FPSfactor))
End Function

Function WrapAngle#(angle#)

	Return ((angle Mod 360) + 360) Mod 360
	
	;If angle = INFINITY Then Return 0.0
	
	;While angle < 0
	;	angle = angle + 360
	;Wend 
	;While angle >= 360
	;	angle = angle - 360
	;Wend
	;Return angle
End Function

Function WrapYaw#(angle#)
	If angle = INFINITY Then Return 0.0
	While angle < -180
		angle = angle + 180
	Wend 
	While angle >= 180
		angle = angle - 180
	Wend
	Return angle
End Function

Function GetAngle#(x1#, y1#, x2#, y2#)
	Return ATan2( y2 - y1, x2 - x1 )
End Function

Function CircleToLineSegIsect% (cx#, cy#, r#, l1x#, l1y#, l2x#, l2y#)
	
	;Palauttaa:
	;  True (1) kun:
	;      Ympyr?¤ [keskipiste = (cx, cy): s?¤de = r]
	;      leikkaa janan, joka kulkee pisteiden (l1x, l1y) & (l2x, l2y) kaitta
	;  False (0) muulloin
	
	;Ympyr?¤n keskipisteen ja (ainakin toisen) janan p?¤?¤tepisteen et?¤isyys < r
	;-> leikkaus
	If Distance(cx, cy, l1x, l1y) <= r Then
		Return True
	EndIf
	
	If Distance(cx, cy, l2x, l2y) <= r Then
		Return True
	EndIf	
	
	;Vektorit (janan vektori ja vektorit janan p?¤?¤tepisteist?¤ ympyr?¤n keskipisteeseen)
	Local SegVecX# = l2x - l1x
	Local SegVecY# = l2y - l1y
	
	Local PntVec1X# = cx - l1x
	Local PntVec1Y# = cy - l1y
	
	Local PntVec2X# = cx - l2x
	Local PntVec2Y# = cy - l2y
	
	;Em. vektorien pistetulot
	Local dp1# = SegVecX * PntVec1X + SegVecY * PntVec1Y
	Local dp2# = -SegVecX * PntVec2X - SegVecY * PntVec2Y
	
	;Tarkistaa onko toisen pistetulon arvo 0
	;tai molempien merkki sama
	If dp1 = 0 Or dp2 = 0 Then
	ElseIf (dp1 > 0 And dp2 > 0) Or (dp1 < 0 And dp2 < 0) Then
	Else
		;Ei kumpikaan -> ei leikkausta
		Return False
	EndIf
	
	;Janan p?¤?¤tepisteiden kautta kulkevan suoran ;yht?¤l?¶; (ax + by + c = 0)
	Local a# = (l2y - l1y) / (l2x - l1x)
	Local b# = -1
	Local c# = -(l2y - l1y) / (l2x - l1x) * l1x + l1y
	
	;Ympyr?¤n keskipisteen et?¤isyys suorasta
	Local d# = Abs(a * cx + b * cy + c) / Sqr(a * a + b * b)
	
	;Ympyr?¤ on liian kaukana
	;-> ei leikkausta
	If d > r Then Return False
	
	;Local kateetin_pituus# = Cos(angle) * hyp
	
	;Jos p?¤?¤st?¤?¤n t?¤nne saakka, ympyr?¤ ja jana leikkaavat (tai ovat sis?¤kk?¤in)
	Return True
End Function

Function lerp#(start#, end#, time#)
    Return start * (1 - time) + End * time
End Function

Function AxisEquals%(a#,b#)
	Return Abs(a-b)<1.0
End Function

Function IntLimit(a#, maxInt#, minusMax = False)
	If minusMax Then
		If a > maxInt Then Return maxInt 
		Return a
	Else
		If a < maxInt Then Return a
		Return maxInt
	EndIf
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

Function Rnd_Array#(numb1#,numb2#,Array1#,Array2#)
	Local whatarray% = Rand(1,2)
	
	If whatarray% = 1
		Return Rnd(Array1#,numb1#)
	Else
		Return Rnd(numb2#,Array2#)
	EndIf
	
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
	Field forceremove%
End Type

Function CreateDecal.Decals(id%, x#, y#, z#, pitch#, yaw#, roll#, size# = 1.0, maxsize# = 1.0)
	Local d.Decals = New Decals
	
	d\x = x
	d\y = y
	d\z = z
	d\pitch = pitch
	d\yaw = yaw
	d\roll = roll
	
	d\MaxSize = maxsize
	
	d\Alpha = 1.0
	d\Size = size
	d\obj = CreateSprite()
	d\blendmode = 1
	
	EntityTexture(d\obj, DecalTextures(id))
	EntityFX(d\obj, 0)
	SpriteViewMode(d\obj, 2)
	PositionEntity(d\obj, x, y, z)
	RotateEntity(d\obj, pitch, yaw, roll)
	d\forceremove = RemoveDecals*2
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
		
		d\forceremove = d\forceremove - 1
		If d\Size <= 0 Or d\Alpha <= 0 Or d\lifetime=5.0 Or d\forceremove = 0 Then
			FreeEntity(d\obj)
			Delete d
		End If
	Next
End Function


;--------------------------------------- INI-functions -------------------------------------------------------

Type INIFile
	Field name$
	Field bank%
	Field bankOffset% = 0
	Field size%
End Type

Function ReadINILine$(file.INIFile)
	Local rdbyte%
	Local firstbyte% = True
	Local offset% = file\bankOffset
	Local bank% = file\bank
	Local retStr$ = ""
	rdbyte = PeekByte(bank,offset)
	While ((firstbyte) Or ((rdbyte<>13) And (rdbyte<>10))) And (offset<file\size)
		rdbyte = PeekByte(bank,offset)
		If ((rdbyte<>13) And (rdbyte<>10)) Then
			firstbyte = False
			retStr=retStr+Chr(rdbyte)
		EndIf
		offset=offset+1
	Wend
	file\bankOffset = offset
	Return retStr
End Function

Function UpdateINIFile$(filename$)
	Local file.INIFile = Null
	For k.INIFile = Each INIFile
		If k\name = Lower(filename) Then
			file = k
		EndIf
	Next
	
	If file=Null Then Return
	
	If file\bank<>0 Then FreeBank file\bank
	Local f% = ReadFile(file\name)
	Local fleSize% = 1
	While fleSize<FileSize(file\name)
		fleSize=fleSize*2
	Wend
	file\bank = CreateBank(fleSize)
	file\size = 0
	While Not Eof(f)
		PokeByte(file\bank,file\size,ReadByte(f))
		file\size=file\size+1
	Wend
	CloseFile(f)
End Function

Function GetINIString$(file$, section$, parameter$, defaultvalue$="")
	Local TemporaryString$ = ""
	
	Local lfile.INIFile = Null
	For k.INIFile = Each INIFile
		If k\name = Lower(file) Then
			lfile = k
		EndIf
	Next
	
	If lfile = Null Then
		DebugLog "CREATE BANK FOR "+file
		lfile = New INIFile
		lfile\name = Lower(file)
		lfile\bank = 0
		UpdateINIFile(lfile\name)
	EndIf
	
	lfile\bankOffset = 0
	
	section = Lower(section)
	
	;While Not Eof(f)
	While lfile\bankOffset<lfile\size
		Local strtemp$ = ReadINILine(lfile)
		If Left(strtemp,1) = "[" Then
			strtemp$ = Lower(strtemp)
			If Mid(strtemp, 2, Len(strtemp)-2)=section Then
				Repeat
					TemporaryString = ReadINILine(lfile)
					If Lower(Trim(Left(TemporaryString, Max(Instr(TemporaryString, "=") - 1, 0)))) = Lower(parameter) Then
						;CloseFile f
						Return Trim( Right(TemporaryString,Len(TemporaryString)-Instr(TemporaryString,"=")) )
					EndIf
				Until (Left(TemporaryString, 1) = "[") Or (lfile\bankOffset>=lfile\size)
				
				;CloseFile f
				Return defaultvalue
			EndIf
		EndIf
	Wend
	
	Return defaultvalue
End Function

Function GetINIInt%(file$, section$, parameter$, defaultvalue% = 0)
	Local txt$ = GetINIString(file$, section$, parameter$, defaultvalue)
	If Lower(txt) = "true" Then
		Return 1
	ElseIf Lower(txt) = "false"
		Return 0
	Else
		Return Int(txt)
	EndIf
End Function

Function GetINIFloat#(file$, section$, parameter$, defaultvalue# = 0.0)
	Return Float(GetINIString(file$, section$, parameter$, defaultvalue))
End Function


Function GetINIString2$(file$, start%, parameter$, defaultvalue$="")
	Local TemporaryString$ = ""
	Local f% = ReadFile(file)
	
	Local n%=0
	While Not Eof(f)
		Local strtemp$ = ReadLine(f)
		n=n+1
		If n=start Then 
			Repeat
				TemporaryString = ReadLine(f)
				If Lower(Trim(Left(TemporaryString, Max(Instr(TemporaryString, "=") - 1, 0)))) = Lower(parameter) Then
					CloseFile f
					Return Trim( Right(TemporaryString,Len(TemporaryString)-Instr(TemporaryString,"=")) )
				EndIf
			Until Left(TemporaryString, 1) = "[" Or Eof(f)
			CloseFile f
			Return defaultvalue
		EndIf
	Wend
	
	CloseFile f	
	
	Return defaultvalue
End Function

Function GetINIInt2%(file$, start%, parameter$, defaultvalue$="")
	Local txt$ = GetINIString2(file$, start%, parameter$, defaultvalue$)
	If Lower(txt) = "true" Then
		Return 1
	ElseIf Lower(txt) = "false"
		Return 0
	Else
		Return Int(txt)
	EndIf
End Function


Function GetINISectionLocation%(file$, section$)
	Local Temp%
	Local f% = ReadFile(file)
	
	section = Lower(section)
	
	Local n%=0
	While Not Eof(f)
		Local strtemp$ = ReadLine(f)
		n=n+1
		If Left(strtemp,1) = "[" Then
			strtemp$ = Lower(strtemp)
			Temp = Instr(strtemp, section)
			If Temp>0 Then
				If Mid(strtemp, Temp-1, 1)="[" Or Mid(strtemp, Temp-1, 1)="|" Then
					CloseFile f
					Return n
				EndIf
			EndIf
		EndIf
	Wend
	
	CloseFile f
End Function



Function PutINIValue%(file$, INI_sSection$, INI_sKey$, INI_sValue$)
	
	; Returns: True (Success) Or False (Failed)
	
	INI_sSection = "[" + Trim$(INI_sSection) + "]"
	Local INI_sUpperSection$ = Upper$(INI_sSection)
	INI_sKey = Trim$(INI_sKey)
	INI_sValue = Trim$(INI_sValue)
	Local INI_sFilename$ = file$
	
	; Retrieve the INI Data (If it exists)
	
	Local INI_sContents$ = INI_FileToString(INI_sFilename)
	
		; (Re)Create the INI file updating/adding the SECTION, KEY And VALUE
	
	Local INI_bWrittenKey% = False
	Local INI_bSectionFound% = False
	Local INI_sCurrentSection$ = ""
	
	Local INI_lFileHandle% = WriteFile(INI_sFilename)
	If INI_lFileHandle = 0 Then Return False ; Create file failed!
	
	Local INI_lOldPos% = 1
	Local INI_lPos% = Instr(INI_sContents, Chr$(0))
	
	While (INI_lPos <> 0)
		
		Local INI_sTemp$ = Mid$(INI_sContents, INI_lOldPos, (INI_lPos - INI_lOldPos))
		
		If (INI_sTemp <> "") Then
			
			If Left$(INI_sTemp, 1) = "[" And Right$(INI_sTemp, 1) = "]" Then
				
					; Process SECTION
				
				If (INI_sCurrentSection = INI_sUpperSection) And (INI_bWrittenKey = False) Then
					INI_bWrittenKey = INI_CreateKey(INI_lFileHandle, INI_sKey, INI_sValue)
				End If
				INI_sCurrentSection = Upper$(INI_CreateSection(INI_lFileHandle, INI_sTemp))
				If (INI_sCurrentSection = INI_sUpperSection) Then INI_bSectionFound = True
				
			Else
				If Left(INI_sTemp, 1) = ":" Then
					WriteLine INI_lFileHandle, INI_sTemp
				Else
						; KEY=VALUE				
					Local lEqualsPos% = Instr(INI_sTemp, "=")
					If (lEqualsPos <> 0) Then
						If (INI_sCurrentSection = INI_sUpperSection) And (Upper$(Trim$(Left$(INI_sTemp, (lEqualsPos - 1)))) = Upper$(INI_sKey)) Then
							If (INI_sValue <> "") Then INI_CreateKey INI_lFileHandle, INI_sKey, INI_sValue
							INI_bWrittenKey = True
						Else
							WriteLine INI_lFileHandle, INI_sTemp
						End If
					End If
				EndIf
				
			End If
			
		End If
		
			; Move through the INI file...
		
		INI_lOldPos = INI_lPos + 1
		INI_lPos% = Instr(INI_sContents, Chr$(0), INI_lOldPos)
		
	Wend
	
		; KEY wasn;t found in the INI file - Append a New SECTION If required And create our KEY=VALUE Line
	
	If (INI_bWrittenKey = False) Then
		If (INI_bSectionFound = False) Then INI_CreateSection INI_lFileHandle, INI_sSection
		INI_CreateKey INI_lFileHandle, INI_sKey, INI_sValue
	End If
	
	CloseFile INI_lFileHandle
	
	Return True ; Success
	
End Function

Function INI_FileToString$(INI_sFilename$)
	
	Local INI_sString$ = ""
	Local INI_lFileHandle%= ReadFile(INI_sFilename)
	If INI_lFileHandle <> 0 Then
		While Not(Eof(INI_lFileHandle))
			INI_sString = INI_sString + ReadLine$(INI_lFileHandle) + Chr$(0)
		Wend
		CloseFile INI_lFileHandle
	End If
	Return INI_sString
	
End Function

Function INI_CreateSection$(INI_lFileHandle%, INI_sNewSection$)
	
	If FilePos(INI_lFileHandle) <> 0 Then WriteLine INI_lFileHandle, "" ; Blank Line between sections
	WriteLine INI_lFileHandle, INI_sNewSection
	Return INI_sNewSection
	
End Function

Function INI_CreateKey%(INI_lFileHandle%, INI_sKey$, INI_sValue$)
	
	WriteLine INI_lFileHandle, INI_sKey + " = " + INI_sValue
	Return True
	
End Function

;Save options to .ini.
Function SaveOptionsINI()
	
	PutINIValue(OptionFile, "options", "mouse sensitivity", MouseSens)
	PutINIValue(OptionFile, "options", "invert mouse y", InvertMouse)
	PutINIValue(OptionFile, "options", "bump mapping enabled", BumpEnabled)			
	PutINIValue(OptionFile, "options", "HUD enabled", HUDenabled)
	PutINIValue(OptionFile, "options", "screengamma", ScreenGamma)
	PutINIValue(OptionFile, "options", "antialias", Opt_AntiAlias)
	PutINIValue(OptionFile, "options", "vsync", VerticalSync)
	PutINIValue(OptionFile, "options", "show FPS", ShowFPS)
	PutINIValue(OptionFile, "options", "showscpviewmodel", ShowSCPViewmodel)
	PutINIValue(OptionFile, "options", "framelimit", Framelimit%)
	PutINIValue(OptionFile, "options", "achievement popup enabled", AchvMSGenabled%)
	PutINIValue(OptionFile, "options", "room lights enabled", EnableRoomLights%)
	PutINIValue(OptionFile, "options", "texture details", TextureDetails%)
	PutINIValue(OptionFile, "console", "enabled", CanOpenConsole%)
	PutINIValue(OptionFile, "console", "auto opening", ConsoleOpening%)
	PutINIValue(OptionFile, "options", "antialiased text", AATextEnable)
	PutINIValue(OptionFile, "options", "particle amount", ParticleAmount)
	PutINIValue(OptionFile, "options", "enable vram", EnableVRam)
	PutINIValue(OptionFile, "options", "mouse smoothing", MouseSmooth)
	PutINIValue(OptionFile, "options", "gammaoff", TurnOnGamma)
	PutINIValue(OptionFile, "options", "cameraupd", CamUpdate)
	PutINIValue(OptionFile, "options", "cameraquality", CamQuality)
	PutINIValue(OptionFile, "options", "removedecals", RemoveDecals)
	PutINIValue(OptionFile, "options", "removeparticles", RemoveParticles)
	PutINIValue(OptionFile, "options", "fov", MainFOV)
	PutINIValue(OptionFile, "options", "enablebullets", EnableBullets)
	
	PutINIValue(OptionFile, "audio", "music volume", MusicVolume)
	PutINIValue(OptionFile, "audio", "sound volume", PrevSFXVolume)
	PutINIValue(OptionFile, "audio", "sfx release", EnableSFXRelease)
	PutINIValue(OptionFile, "audio", "enable user tracks", EnableUserTracks%)
	PutINIValue(OptionFile, "audio", "user track setting", UserTrackMode%)
	
	PutINIValue(OptionFile, "binds", "Right key", KEY_RIGHT)
	PutINIValue(OptionFile, "binds", "Left key", KEY_LEFT)
	PutINIValue(OptionFile, "binds", "Up key", KEY_UP)
	PutINIValue(OptionFile, "binds", "Down key", KEY_DOWN)
	PutINIValue(OptionFile, "binds", "Blink key", KEY_BLINK)
	PutINIValue(OptionFile, "binds", "Sprint key", KEY_SPRINT)
	PutINIValue(OptionFile, "binds", "Inventory key", KEY_INV)
	PutINIValue(OptionFile, "binds", "Crouch key", KEY_CROUCH)
	PutINIValue(OptionFile, "binds", "Save key", KEY_SAVE)
	PutINIValue(OptionFile, "binds", "Console key", KEY_CONSOLE)
	PutINIValue(OptionFile, "binds", "mouseinteract", MOUSEINTERACT)
	
	PutINIValue(OptionFile, "binds", "Voice key", KEY_VOICE)
	PutINIValue(OptionFile, "binds", "Chat key", KEY_CHAT)
	PutINIValue(OptionFile, "binds", "Jump key", KEY_JUMP)
	
	PutINIValue(OptionFile, "binds", "LeanR key", KEY_LEANR)
	PutINIValue(OptionFile, "binds", "LeanL key", KEY_LEANL)
	PutINIValue(OptionFile, "binds", "Using key", KEY_USING)
	
End Function

;--------------------------------------- MakeCollBox -functions -------------------------------------------------------


; Create a collision box For a mesh entity taking into account entity scale
; (will not work in non-uniform scaled space)
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

Function Graphics3DExt%(width%,height%,depth%=32,mode%=2)
	;If FE_InitExtFlag = 1 Then DeInitExt() ;prevent FastExt from breaking itself
	;api_showWindow(SystemProperty("AppHWND"), 0)
	FreeAllFonts(False, True)
	Graphics3D width,height,depth,mode
	SMALLEST_POWER_TWO = 512.0
	While SMALLEST_POWER_TWO < width Or SMALLEST_POWER_TWO < height
		SMALLEST_POWER_TWO = SMALLEST_POWER_TWO*2.0
	Wend
	SMALLEST_POWER_TWO_HALF = SMALLEST_POWER_TWO / 2.0
	InitExternalSound()
	InitFastResize()
	;InitExt()
	AntiAlias GetINIInt(OptionFile,"options","antialias")
	;TextureAnisotropy% (GetINIInt(OptionFile,"options","anisotropy"),-1)
End Function

Function ResizeImage2(image%,width%,height%)
	ResizeImage(image, width, height)
	Return image
    img% = CreateImage(width,height)
	
	oldWidth% = ImageWidth(image)
	oldHeight% = ImageHeight(image)
	CopyRect 0,0,oldWidth,oldHeight,SMALLEST_POWER_TWO_HALF-oldWidth/2,SMALLEST_POWER_TWO_HALF-oldHeight/2,ImageBuffer(image),TextureBuffer(fresize_texture)
	SetBuffer BackBuffer()
	ScaleRender(0,0,SMALLEST_POWER_TWO / Float(RealGraphicWidth) * Float(width) / Float(oldWidth), SMALLEST_POWER_TWO / Float(RealGraphicWidth) * Float(height) / Float(oldHeight))
	;might want to replace Float(GraphicWidth) with Max(GraphicWidth,GraphicHeight) if portrait sizes cause issues
	;everyone uses landscape so it's probably a non-issue
	CopyRect RealGraphicWidth/2-width/2,RealGraphicHeight/2-height/2,width,height,0,0,BackBuffer(),ImageBuffer(img)
	
    FreeImage image
    Return img
End Function


Function Render3D(tween# = 1.0)
	;if room860event <> Null Then
	;	if PlayerRoom = room860event\room Then
	;		if room860event\EventState = 1.0 Then
	;			;x = 2.0
	;					
	;			If (Not DebugHUD)
	;				CameraClsColor Camera,min(98, 255),min(133, 255),min(162, 255)
	;				CameraRange Camera,RoomScale,8.5
	;				CameraFogRange Camera,0.5,8.0
	;				CameraFogColor Camera,min(98, 255),min(133, 255),min(162, 255)
	;			EndIf
	;			;If (Not DebugHUD)
	;			;	CameraClsColor Camera,74, 249, 255
	;			;	CameraRange Camera,RoomScale,8.5
	;			;	CameraFogRange Camera,0.5,8.0
	;			;	CameraFogColor Camera,74, 249, 255
	;			;EndIf
	;		EndIf
	;	EndIf
	;EndIf
	
	CameraProjMode ark_blur_cam,0
	CameraProjMode Camera,1
	
	If WearingNightVision>0 And WearingNightVision<3 Then
		AmbientLight Min(Brightness*2,255), Min(Brightness*2,255), Min(Brightness*2,255)
	ElseIf WearingNightVision=3
		AmbientLight 255,255,255
	ElseIf PlayerRoom<>Null
		If (PlayerRoom\RoomTemplate\Name<>"173") And (PlayerRoom\RoomTemplate\Name<>"exit1") And (PlayerRoom\RoomTemplate\Name<>"gatea") Then
			AmbientLight Brightness, Brightness, Brightness
		EndIf
	EndIf
	
	IsNVGBlinking% = False
	HideEntity NVBlink
	
	;CameraRange Camera, 0.05, 100000
	;camerafogrange Camera, 0.05, 100000
	
	CameraViewport Camera,0,0,GraphicWidth,GraphicHeight
	
	If GetScripts() Then public_inqueue(public_OnRenderWorld, True)
	
	Local hasBattery% = 2
	Local power% = 0
	If (WearingNightVision=1) Or (WearingNightVision=2)
		For i% = 0 To MaxItemAmount - 1
			If (Inventory(i)<>Null) Then
				If (WearingNightVision = 1 And Inventory(i)\itemtemplate\tempname = "nvgoggles") Or (WearingNightVision = 2 And Inventory(i)\itemtemplate\tempname = "supernv") Then
					Inventory(i)\state = Inventory(i)\state - (FPSfactor * (0.02 * WearingNightVision))
					power%=Int(Inventory(i)\state)
					hasBattery = 1
				EndIf
			EndIf
		Next
		
		If (hasBattery) Then
			RenderWorld(tween)
		EndIf
	Else
		RenderWorld(tween)
	EndIf
	
	CurrTrisAmount = TrisRendered()

	If hasBattery=0 And WearingNightVision<>3
		IsNVGBlinking% = True
		ShowEntity NVBlink%
	EndIf
	
	If BlinkTimer < - 16 Or BlinkTimer > - 6
		If WearingNightVision=2 And hasBattery<>0 Then ;show a HUD
			NVTimer=NVTimer-FPSfactor
			
			If NVTimer<=0.0 Then
				For np.NPCs = Each NPCs
					np\NVX = EntityX(np\Collider,True)
					np\NVY = EntityY(np\Collider,True)
					np\NVZ = EntityZ(np\Collider,True)
				Next
				IsNVGBlinking% = True
				ShowEntity NVBlink%
				If NVTimer<=-10
				NVTimer = 600.0
			EndIf
			EndIf
			
			Color 255,255,255
			
			AASetFont Font3
			
			Local plusY% = 0
			If hasBattery=1 Then plusY% = 40
			
			AAText GraphicWidth/2,(20+plusY)*MenuScale,"REFRESHING DATA IN",True,False
			
			AAText GraphicWidth/2,(60+plusY)*MenuScale,Max(f2s(NVTimer/60.0,1),0.0),True,False
			AAText GraphicWidth/2,(100+plusY)*MenuScale,"SECONDS",True,False
			
			temp% = CreatePivot() : temp2% = CreatePivot()
			PositionEntity temp, EntityX(Collider), EntityY(Collider), EntityZ(Collider)
			
			Color 255,255,255;*(NVTimer/600.0)
			
			For np.NPCs = Each NPCs
				If np\NVName<>"" And (Not np\HideFromNVG) Then ;don't waste your time if the string is empty
					PositionEntity temp2,np\NVX,np\NVY,np\NVZ
					dist# = EntityDistance(temp2,Collider)
					If dist<23.5 Then ;don't draw text if the NPC is too far away
						PointEntity temp, temp2
						yawvalue# = WrapAngle(EntityYaw(Camera) - EntityYaw(temp))
						xvalue# = 0.0
						If yawvalue > 90 And yawvalue <= 180 Then
							xvalue# = Sin(90)/90*yawvalue
						Else If yawvalue > 180 And yawvalue < 270 Then
							xvalue# = Sin(270)/yawvalue*270
						Else
							xvalue = Sin(yawvalue)
						EndIf
						pitchvalue# = WrapAngle(EntityPitch(Camera) - EntityPitch(temp))
						yvalue# = 0.0
						If pitchvalue > 90 And pitchvalue <= 180 Then
							yvalue# = Sin(90)/90*pitchvalue
						Else If pitchvalue > 180 And pitchvalue < 270 Then
							yvalue# = Sin(270)/pitchvalue*270
						Else
							yvalue# = Sin(pitchvalue)
						EndIf
						
						If (Not IsNVGBlinking%)
						AAText GraphicWidth / 2 + xvalue * (GraphicWidth / 2),GraphicHeight / 2 - yvalue * (GraphicHeight / 2),np\NVName,True,True
							AAText GraphicWidth / 2 + xvalue * (GraphicWidth / 2),GraphicHeight / 2 - yvalue * (GraphicHeight / 2) + 30.0 * MenuScale,f2s(dist,1)+" m",True,True
						EndIf
				EndIf
				EndIf
			Next
			
			FreeEntity (temp) : FreeEntity (temp2)
			
			Color 0,0,55
			For k=0 To 10
				Rect 45,GraphicHeight*0.5-(k*20),54,10,True
			Next
			Color 0,0,255
			For l=0 To Floor((power%+50)*0.01)
				Rect 45,GraphicHeight*0.5-(l*20),54,10,True
			Next
			DrawImage NVGImages,40,GraphicHeight*0.5+30,1
			
			Color 255,255,255
		ElseIf WearingNightVision=1 And hasBattery<>0
			Color 0,55,0
			For k=0 To 10
				Rect 45,GraphicHeight*0.5-(k*20),54,10,True
			Next
			Color 0,255,0
			For l=0 To Floor((power%+50)*0.01)
				Rect 45,GraphicHeight*0.5-(l*20),54,10,True
			Next
			DrawImage NVGImages,40,GraphicHeight*0.5+30,0
		EndIf
	EndIf
	
	;render sprites
	CameraProjMode ark_blur_cam,2
	CameraProjMode Camera,0
	RenderWorld(tween)
	CameraProjMode ark_blur_cam,0

End Function


Function ScaleRender(x#,y#,hscale#=1.0,vscale#=1.0)
	If Camera<>0 Then HideEntity Camera
	WireFrame 0
	ShowEntity fresize_image
	ScaleEntity fresize_image,hscale,vscale,1.0
	PositionEntity fresize_image, x, y, 1.0001
	ShowEntity fresize_cam
	RenderWorld()
	HideEntity fresize_cam
	HideEntity fresize_image
	WireFrame WireframeState
	If Camera<>0 Then ShowEntity Camera
End Function

Function InitFastResize()
    ;Create Camera
	Local cam% = CreateCamera()
	CameraProjMode cam, 2
	CameraZoom cam, 0.1
	CameraClsMode cam, 0, 0
	CameraRange cam, 0.1, 1.5
	MoveEntity cam, 0, 0, -10000
	
	fresize_cam = cam
	
    ;ark_sw = GraphicsWidth()
    ;ark_sh = GraphicsHeight()
	
    ;Create sprite
	Local spr% = CreateMesh(cam)
	Local sf% = CreateSurface(spr)
	AddVertex sf, -1, 1, 0, 0, 0
	AddVertex sf, 1, 1, 0, 1, 0
	AddVertex sf, -1, -1, 0, 0, 1
	AddVertex sf, 1, -1, 0, 1, 1
	AddTriangle sf, 0, 1, 2
	AddTriangle sf, 3, 2, 1
	EntityFX spr, 17
	ScaleEntity spr, SMALLEST_POWER_TWO / Float(RealGraphicWidth), SMALLEST_POWER_TWO / Float(RealGraphicHeight), 1
	PositionEntity spr, 0, 0, 1.0001
	EntityOrder spr, -100001
	EntityBlend spr, 1
	fresize_image = spr
	
    ;Create texture
	fresize_texture = CreateTexture(SMALLEST_POWER_TWO, SMALLEST_POWER_TWO, 1+256)
	fresize_texture2 = CreateTexture(SMALLEST_POWER_TWO, SMALLEST_POWER_TWO, 1+256)
	TextureBlend fresize_texture2,3
	SetBuffer(TextureBuffer(fresize_texture2))
	ClsColor 0,0,0
	Cls
	SetBuffer(BackBuffer())
	;TextureAnisotropy(fresize_texture)
	EntityTexture spr, fresize_texture,0,0
	EntityTexture spr, fresize_texture2,0,1
	
	HideEntity fresize_cam
End Function

;--------------------------------------- Some new 1.3 -functions -------------------------------------------------------

Function UpdateLeave1499()

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

Function CheckForPlayerInFacility()
	;False (=0): NPC is not in facility (mostly meant for "dimension1499")
	;True (=1): NPC is in facility
	;2: NPC is in tunnels (maintenance tunnels/049 tunnels/939 storage room, etc...)
	
	If EntityY(Collider)>100.0
		Return False
	EndIf
	If EntityY(Collider)< -10.0
		Return 2
	EndIf
	If EntityY(Collider)> 7.0 And EntityY(Collider)<=100.0
		Return 2
	EndIf
	
	Return True
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

Function ControlSoundVolume()
	Local snd.Sound,i
	
	;Local SndUpdates
	For snd.Sound = Each Sound
		;SndUpdates = 0
		;if snd\channelscount <> 0 then
		For i=0 To 31
			If snd\channels[i] <> 0 Then ChannelVolume snd\channels[i],SFXVolume#
		Next
		;endif
	Next
	
End Function

Function UpdateDeafPlayer()
	
	If DeafTimer > 0
		DeafTimer = DeafTimer-FPSfactor
		SFXVolume# = CurveValue(0.0, SFXVolume, 5.0)
		If SFXVolume# > 0.0
			ControlSoundVolume()
		EndIf
	Else
		DeafTimer = 0
		;If SFXVolume# < PrevSFXVolume#
		;	SFXVolume# = Min(SFXVolume# + (0.001*PrevSFXVolume)*FPSfactor,PrevSFXVolume#)
		;	ControlSoundVolume()
		;Else
			SFXVolume# = CurveValue(PrevSFXVolume, SFXVolume, 40.0)
			If DeafPlayer Then ControlSoundVolume()
			
			If sqrvalue(PrevSFXVolume-SFXVolume) < 0.001 Then DeafPlayer = False
		;EndIf
	EndIf
	
End Function

Function CheckTriggers$(e.Events)
	Local i%,sx#,sy#,sz#
	
	If e\room\TriggerboxAmount = 0
		Return ""
	Else
		For i = 0 To e\room\TriggerboxAmount-1
		
			If DebugHUD
				EntityColor e\room\Triggerbox[i],255,255,0
				EntityAlpha e\room\Triggerbox[i],0.2
			Else
				EntityColor e\room\Triggerbox[i],255,255,255
				EntityAlpha e\room\Triggerbox[i],0.0
 			EndIf
			
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

Function ScaledMouseX%()
	Return Float(MouseX()-(RealGraphicWidth*0.5*(1.0-AspectRatioRatio)))*Float(GraphicWidth)/Float(RealGraphicWidth*AspectRatioRatio)
End Function

Function ScaledMouseY%()
	Return Float(MouseY())*Float(GraphicHeight)/Float(RealGraphicHeight)
End Function

Function CatchErrors(location$)
	Local errStr$ = ErrorLog()
	Local errF%
	If Len(errStr)>0 Then
		If FileType(ErrorFile)=0 Then
			errF = WriteFile(ErrorFile)
			WriteLine errF,"An error occured in SCP - Containment Breach!"
			WriteLine errF,"Version: "+VersionNumber
			WriteLine errF,"Save compatible version: "+CompatibleNumber
			WriteLine errF,"Date and time: "+CurrentDate()+" at "+CurrentTime()
			WriteLine errF,"Total video memory (MB): "+TotalVidMem()/1024/1024
			WriteLine errF,"Available video memory (MB): "+AvailVidMem()/1024/1024
			GlobalMemoryStatus m.MEMORYSTATUS
			WriteLine errF,"Global memory status: "+(m\dwAvailPhys%/1024/1024)+" MB/"+(m\dwTotalPhys%/1024/1024)+" MB ("+(m\dwAvailPhys%/1024)+" KB/"+(m\dwTotalPhys%/1024)+" KB)"
			WriteLine errF,"Triangles rendered: "+CurrTrisAmount
			WriteLine errF,"Active textures: "+ActiveTextures()
			WriteLine errF,""
			WriteLine errF,"Error(s):"
		Else
			Local canwriteError% = True
			errF = OpenFile(ErrorFile)
			While (Not Eof(errF))
				Local l$ = ReadLine(errF)
				If Left(l,Len(location))=location
					canwriteError = False
					Exit
				EndIf
			Wend
			If canwriteError
				SeekFile errF,FileSize(ErrorFile)
			EndIf
		EndIf
		If canwriteError
			WriteLine errF,location+" ***************"
			While Len(errStr)>0
				WriteLine errF,errStr
				DebugLog errStr
				errStr = ErrorLog()
			Wend
		EndIf
		Msg = "Blitz3D Error! Details in "+Chr(34)+ErrorFile+Chr(34)
		MsgTimer = 20*70
		CloseFile errF
	EndIf
End Function

Function Create3DIcon(width%,height%,modelpath$,texturepath$, modelX#=0,modelY#=0,modelZ#=0,modelPitch#=0,modelYaw#=0,modelRoll#=0,modelscaleX#=1,modelscaleY#=1,modelscaleZ#=1,withfog%=False)
	Local img% = CreateImage(width,height)
	Local cam% = CreateCamera()
	Local model%, tex%
	
	CameraRange cam,0.01,16
	CameraViewport cam,0,0,width,height
	If withfog
		CameraFogMode cam,1
		CameraFogRange cam,CameraFogNear,CameraFogFar
	EndIf
	
	If Right(Lower(modelpath$),6)=".rmesh"
		model = LoadRMesh(modelpath$,Null)
	Else
		model = LoadMesh_Strict(modelpath$)
	EndIf
	tex = LoadTexture_Strict(texturepath)
	ScaleEntity model,modelscaleX,modelscaleY,modelscaleZ
	PositionEntity model,modelX#,modelY#,modelZ#
	RotateEntity model,modelPitch#,modelYaw#,modelRoll#
	EntityTexture model, tex
	;Cls
	RenderWorld
	CopyRect 0,0,width,height,0,0,BackBuffer(),ImageBuffer(img)
	
	FreeEntity model
	FreeEntity cam
	FreeTexture tex
	Return img%
End Function

Function PlayAnnouncement(file$, give = True, ignorePIRR = False) ;This function streams the announcement currently playing
	If IntercomStreamCHN <> 0 Then
		StopStream_Strict(IntercomStreamCHN)
		IntercomStreamCHN = 0
	EndIf
	
	If (PlayerInReachableRoom() Or ignorePIRR) Or NetworkServer\Breach = True Then 
		IntercomStreamCHN = StreamSound_Strict(file$,SFXVolume,0)
	EndIf
	If NetworkServer\MainPlayer And give = True Then
		For i = 1 To NetworkServer\Maxplayers
			If Player[i] <> Null And i <> 1 Then
				udp_WriteByte(M_ANNOUNC)
				udp_WriteByte(1)
				udp_WriteLine(file)
				udp_SendMessage(i)
			EndIf
		Next
	EndIf
	
End Function

Global UpdateStreamSFactor#
Function UpdateStreamSounds()
	
	If UpdateStreamSFactor > 10 Then
		Local e.Events
		
		If IntercomStreamCHN <> 0 Then
			SetStreamVolume_Strict(IntercomStreamCHN,SFXVolume)
		EndIf
		For e = Each Events
			If e\SoundCHN<>0 Then
				If e\SoundCHN_isStream
					SetStreamVolume_Strict(e\SoundCHN,SFXVolume)
				EndIf
			EndIf
			If e\SoundCHN2<>0 Then
				If e\SoundCHN2_isStream
					SetStreamVolume_Strict(e\SoundCHN2,SFXVolume)
				EndIf
			EndIf
		Next
		
		If (Not PlayerInReachableRoom()) Then
			If PlayerRoom\RoomTemplate\Name <> "exit1" And PlayerRoom\RoomTemplate\Name <> "gatea" Then
				If IntercomStreamCHN <> 0 Then
					StopStream_Strict(IntercomStreamCHN)
					IntercomStreamCHN = 0
				EndIf
				If PlayerRoom\RoomTemplate\Name$ <> "dimension1499" Then
					For e = Each Events
						If e\SoundCHN<>0 And e\SoundCHN_isStream Then
							StopStream_Strict(e\SoundCHN)
							e\SoundCHN = 0
							e\SoundCHN_isStream = 0
						EndIf
						If e\SoundCHN2<>0 And e\SoundCHN2_isStream Then
							StopStream_Strict(e\SoundCHN2)
							e\SoundCHN2 = 0
							e\SoundCHN2_isStream = 0
						EndIf
					Next
				EndIf
			EndIf
		EndIf
		UpdateStreamSFactor = 0
	Else
		UpdateStreamSFactor = UpdateStreamSFactor+FPSfactor
	EndIf
End Function

Function TeleportEntity(entity%,x#,y#,z#,customradius#=0.3,isglobal%=False,pickrange#=2.0,dir%=0)
	Local pvt,pick,result%
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
		result = True
		DebugLog "Entity teleported successfully"
	Else
		PositionEntity(entity,x,y,z,isglobal)
		DebugLog "Warning: no ground found when teleporting an entity"
		result = False
	EndIf
	FreeEntity pvt
	ResetEntity entity
	DebugLog "Teleported entity to: "+EntityX(entity)+"/"+EntityY(entity)+"/"+EntityZ(entity)
	
	Return result
	
End Function

Function PlayStartupVideos()
	
	If GetINIInt(OptionFile,"options","play startup video")=0 Then Return
	
	Local moviefile$ = "GFX\menu\startup_Undertow"
	Local movie = OpenMovie(moviefile$+".avi")
	Local SplashScreenAudio = StreamSound_Strict(moviefile$+".ogg",SFXVolume,0)
	Repeat
		Cls
		DrawMovie(movie, 0,0,RealGraphicWidth,RealGraphicHeight)
		Flip
	Until (GetKey() Or (Not IsStreamPlaying_Strict(SplashScreenAudio)))
	StopStream_Strict(SplashScreenAudio)
	CloseMovie movie
	Cls
	Flip
	
	moviefile$ = "GFX\menu\startup_TSS"
	movie = OpenMovie(moviefile$+".avi")
	SplashScreenAudio = StreamSound_Strict(moviefile$+".ogg",SFXVolume,0)
	Repeat
		Cls
		DrawMovie(movie, 0,0,RealGraphicWidth,RealGraphicHeight)
		Flip
	Until (GetKey() Or (Not IsStreamPlaying_Strict(SplashScreenAudio)))
	StopStream_Strict(SplashScreenAudio)
	CloseMovie movie
	Cls
	Flip
End Function

Function ProjectImage(img, w#, h#, Quad%, Texture%)
	
	Local img_w# = ImageWidth(img)
	Local img_h# = ImageHeight(img)
	If img_w > SMALLEST_POWER_TWO Then img_w = SMALLEST_POWER_TWO
	If img_h > SMALLEST_POWER_TWO Then img_h = SMALLEST_POWER_TWO
	If img_w < 1 Then img_w = 1
	If img_h < 1 Then img_h = 1
	
	If w > SMALLEST_POWER_TWO Then w = SMALLEST_POWER_TWO
	If h > SMALLEST_POWER_TWO Then h = SMALLEST_POWER_TWO
	If w < 1 Then w = 1
	If h < 1 Then h = 1
	
	Local w_rel# = w# / img_w#
	Local h_rel# = h# / img_h#
	Local g_rel# = SMALLEST_POWER_TWO / Float(RealGraphicWidth)
	Local dst_x = SMALLEST_POWER_TWO_HALF - (img_w / 2.0)
	Local dst_y = SMALLEST_POWER_TWO_HALF - (img_h / 2.0)
	CopyRect 0, 0, img_w, img_h, dst_x, dst_y, ImageBuffer(img), TextureBuffer(Texture)
	ScaleEntity Quad, w_rel * g_rel, h_rel * g_rel, 0.0001
	RenderWorld()
	
End Function

Function CreateQuad()
	
	mesh = CreateMesh()
	surf = CreateSurface(mesh)
	v0 = AddVertex(surf,-1.0, 1.0, 0, 0, 0)
	v1 = AddVertex(surf, 1.0, 1.0, 0, 1, 0)
	v2 = AddVertex(surf, 1.0,-1.0, 0, 1, 1)
	v3 = AddVertex(surf,-1.0,-1.0, 0, 0, 1)
	AddTriangle(surf, v0, v1, v2)
	AddTriangle(surf, v0, v2, v3)
	UpdateNormals mesh
	Return mesh
	
End Function
Function CanUseItem(canUseWithHazmat%, canUseWithGasMask%, canUseWithEyewear%)
	If (canUseWithHazmat = False And WearingHazmat) Then
		Msg = "You can't use that item while wearing a hazmat suit."
		MsgTimer = 70*5
		Return False
	Else If (canUseWithGasMask = False And (WearingGasMask Or Wearing1499))
		Msg = "You can't use that item while wearing a gas mask."
		MsgTimer = 70*5
		Return False
	Else If (canUseWithEyewear = False And (WearingNightVision))
		Msg = "You can't use that item while wearing headgear."
		MsgTimer = 70*5
		Return False
	EndIf
	
	Return True
End Function

Function ResetInput()
	
	FlushKeys()
	FlushMouse()
	MouseHit1 = 0
	MouseHit2 = 0
	MouseDown1 = 0
	MouseUp1 = 0
	MouseHit(1)
	MouseHit(2)
	MouseDown(1)
	GrabbedEntity = 0
	Input_ResetTime# = 10.0
	
End Function

Function ResetMouse()
	
	MouseXSpeed()
	MouseYSpeed()
	MouseZSpeed() 
	mouse_x_speed_1#=0.0
	mouse_y_speed_1#=0.0
	
End Function

Function Update096ElevatorEvent#(e.Events,EventState#,d.Doors,elevatorobj%)
	Local prevEventState# = EventState#
	
	If EventState < 0 Then
		EventState = 0
		prevEventState = 0
	EndIf
	
	If d\openstate = 0 And d\open = False Then
		If Abs(EntityX(e\NearObject)-EntityX(elevatorobj%,True))<=280.0*RoomScale+(0.015*FPSfactor) Then
			If Abs(EntityZ(e\NearObject)-EntityZ(elevatorobj%,True))<=280.0*RoomScale+(0.015*FPSfactor) Then
				If Abs(EntityY(e\NearObject)-EntityY(elevatorobj%,True))<=280.0*RoomScale+(0.015*FPSfactor) Then
					d\locked = True
					If EventState = 0 Then
						TeleportEntity(Curr096\Collider,EntityX(d\frameobj),EntityY(d\frameobj)+1.0,EntityZ(d\frameobj),Curr096\CollRadius)
						PointEntity Curr096\Collider,elevatorobj
						RotateEntity Curr096\Collider,0,EntityYaw(Curr096\Collider),0
						MoveEntity Curr096\Collider,0,0,-0.5
						ResetEntity Curr096\Collider
						Curr096\State = 6
						SetNPCFrame(Curr096,0)
						e\Sound = LoadSound_Strict("SFX\SCP\096\ElevatorSlam.ogg")
						EventState = EventState + FPSfactor * 1.4
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
	
	If EventState > 0 Then
		If prevEventState = 0 Then
			e\SoundCHN = PlaySound_Strict(e\Sound)
		EndIf
		
		If EventState > 70*1.9 And EventState < 70*2+FPSfactor
			CameraShake = 7
		ElseIf EventState > 70*4.2 And EventState < 70*4.25+FPSfactor
			CameraShake = 1
		ElseIf EventState > 70*5.9 And EventState < 70*5.95+FPSfactor
			CameraShake = 1
		ElseIf EventState > 70*7.25 And EventState < 70*7.3+FPSfactor
			CameraShake = 1
			d\fastopen = True
			d\open = True
			Curr096\State = 4
			Curr096\LastSeen = 1
		ElseIf EventState > 70*8.1 And EventState < 70*8.15+FPSfactor
			CameraShake = 1
		EndIf
		
		If EventState <= 70*8.1 Then
			d\openstate = Min(d\openstate,20)
		EndIf
		EventState = EventState + FPSfactor * 1.4
	EndIf
	Return EventState
	
End Function

Function RotateEntity90DegreeAngles(entity%)
	Local angle = WrapAngle(entity)
	
	If angle < 45.0 Then
		Return 0
	ElseIf angle >= 45.0 And angle < 135 Then
		Return 90
	ElseIf angle >= 135 And angle < 225 Then
		Return 180
	Else
		Return 270
	EndIf
	
End Function
Function UpdateExplosion()
	If ExplosionTimer > 0 Then
		ExplosionTimer = ExplosionTimer+FPSfactor
		
		If ExplosionTimer < 140.0 Then
			If ExplosionTimer-FPSfactor < 5.0 Then
				ExplosionSFX = LoadSound_Strict("SFX\Ending\GateB\Nuke1.ogg")
				PlaySound_Strict ExplosionSFX
				CameraShake = 10.0
				ExplosionTimer = 5.0
			EndIf
			
			CameraShake = CurveValue(ExplosionTimer/60.0,CameraShake, 50.0)
		Else
			CameraShake = Min((ExplosionTimer/20.0),20.0)
			If ExplosionTimer-FPSfactor < 140.0 Then
				BlinkTimer = 1.0
				ExplosionSFX = LoadSound_Strict("SFX\Ending\GateB\Nuke2.ogg")
				PlaySound_Strict ExplosionSFX				
				For i = 0 To (10+(10*(ParticleAmount+1)))
					p.Particles = CreateParticle(EntityX(Collider)+Rnd(-0.5,0.5),EntityY(Collider)-Rnd(0.2,1.5),EntityZ(Collider)+Rnd(-0.5,0.5),0, Rnd(0.2,0.6), 0.0, 350)	
					RotateEntity p\pvt,-90,0,0,True
					p\speed = Rnd(0.05,0.07)
				Next
			EndIf
			LightFlash = Min((ExplosionTimer-140.0)/10.0,5.0)
			
			If ExplosionTimer > 160 And (Not player_isdead()) Then Kill("was killed by explosion", True)
			If ExplosionTimer > 500 Then ExplosionTimer = 0

			PositionEntity Collider, EntityX(Collider), 200, EntityZ(Collider)
			ResetEntity Collider
		EndIf
	EndIf
End Function
Function UpdateResolution(ExecuteGamma% = True, VSyncChange% = -1)
	If Not UpdateFocus() Then Delay 50	 ; If game is Alt+Tab
	;UpdateFocus()
	; =============================================
		If ExecuteGamma Then
			If Not TurnOnGamma Then
				If BorderlessWindowed Then
					If (RealGraphicWidth<>GraphicWidth) Or (RealGraphicHeight<>GraphicHeight) Then
						SetBuffer TextureBuffer(fresize_texture)
						ClsColor 0,0,0 : Cls
						CopyRect 0,0,GraphicWidth,GraphicHeight,SMALLEST_POWER_TWO_HALF-GraphicWidth/2,SMALLEST_POWER_TWO_HALF-GraphicHeight/2,BackBuffer(),TextureBuffer(fresize_texture)
						SetBuffer BackBuffer()
						ClsColor 0,0,0 : Cls
						ScaleRender(0,0,SMALLEST_POWER_TWO / Float(GraphicWidth) * AspectRatioRatio, SMALLEST_POWER_TWO / Float(GraphicWidth) * AspectRatioRatio)
						;might want to replace Float(GraphicWidth) with Max(GraphicWidth,GraphicHeight) if portrait sizes cause issues
						;everyone uses landscape so it's probably a non-issue
					EndIf
				EndIf
				
				;not by any means a perfect solution
				;Not even proper gamma correction but it's a nice looking alternative that works in windowed mode
				If ScreenGamma>=1.0 Then
					CopyRect 0,0,RealGraphicWidth,RealGraphicHeight,SMALLEST_POWER_TWO_HALF-RealGraphicWidth/2,SMALLEST_POWER_TWO_HALF-RealGraphicHeight/2,BackBuffer(),TextureBuffer(fresize_texture)
					EntityBlend fresize_image,1
					ClsColor 0,0,0 : Cls
					ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth))
					EntityFX fresize_image,1+32
					EntityBlend fresize_image,3
					EntityAlpha fresize_image,ScreenGamma-1.0
					ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth))
				ElseIf ScreenGamma<1.0 Then ;todo: maybe optimize this if it's too slow, alternatively give players the option to disable gamma
					CopyRect 0,0,RealGraphicWidth,RealGraphicHeight,SMALLEST_POWER_TWO_HALF-RealGraphicWidth/2,SMALLEST_POWER_TWO_HALF-RealGraphicHeight/2,BackBuffer(),TextureBuffer(fresize_texture)
					EntityBlend fresize_image,1
					ClsColor 0,0,0 : Cls
					ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth))
					EntityFX fresize_image,1+32
					EntityBlend fresize_image,2
					EntityAlpha fresize_image,1.0
					SetBuffer TextureBuffer(fresize_texture2)
					ClsColor 255*ScreenGamma,255*ScreenGamma,255*ScreenGamma
					Cls
					SetBuffer BackBuffer()
					ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth),SMALLEST_POWER_TWO / Float(RealGraphicWidth))
					SetBuffer(TextureBuffer(fresize_texture2))
					ClsColor 0,0,0
					Cls
					SetBuffer(BackBuffer())
				EndIf
			Else
				If Camera<>0 Then HideEntity Camera
				RenderWorld()
				If Camera<>0 Then ShowEntity Camera
			EndIf
			EntityFX fresize_image,1
			EntityBlend fresize_image,1
			EntityAlpha fresize_image,1.0
		EndIf
	; ===========================================
	
	If InFocus Or BorderlessWindowed Or (Not Fullscreen) Then
		If VSyncChange = -1 Then
			Flip VerticalSync ; If Fullscreen Then doesnt update res
		Else
			Flip VSyncChange
		EndIf
	EndIf
	
	If ShouldRestartServer And udp_GetStream() Then
		sip$ = DottedIP(udp_network\msgip)
		prt = udp_network\msgport
		DisconnectServer("Restarting")
		RestartingServer(sip, prt)
		ShouldRestartServer = False
	Else
		ShouldRestartServer = False
	EndIf
End Function

Function PickEntity(x#, y#, z#, dist#)
	pvt = CreatePivot()
	PositionEntity pvt, x,y,z
	TurnEntity pvt, 90, 0, 0
	picked = EntityPick(pvt, dist)
	FreeEntity pvt
	Return picked
End Function

Function IsEntityInView(cam, entity, maxwidth = -300, maxheight = -300)
	Local VIEW_ENTITY_PIVOT,VIEW_ENTITY_PIVOT2
	VIEW_ENTITY_PIVOT = CreatePivot()
	VIEW_ENTITY_PIVOT2 = CreatePivot()
	PositionEntity VIEW_ENTITY_PIVOT, EntityX(cam), EntityY(cam), EntityZ(cam)
	PositionEntity VIEW_ENTITY_PIVOT2,EntityX(entity), EntityY(entity), EntityZ(entity)
	PointEntity VIEW_ENTITY_PIVOT, VIEW_ENTITY_PIVOT2
	yawvalue# = WrapAngle(EntityYaw(cam) - EntityYaw(VIEW_ENTITY_PIVOT))
	xvalue# = 0.0
	If yawvalue > 90 And yawvalue <= 180 Then
		xvalue# = Sin(90)/90*yawvalue
	Else If yawvalue > 180 And yawvalue < 270 Then
		xvalue# = Sin(270)/yawvalue*270
	Else
		xvalue = Sin(yawvalue)
	EndIf
	pitchvalue# = WrapAngle(EntityPitch(cam) - EntityPitch(VIEW_ENTITY_PIVOT))
	yvalue# = 0.0
	If pitchvalue > 90 And pitchvalue <= 180 Then
		yvalue# = Sin(90)/90*pitchvalue
	Else If pitchvalue > 180 And pitchvalue < 270 Then
		yvalue# = Sin(270)/pitchvalue*270
	Else
		yvalue# = Sin(pitchvalue)
	EndIf
	FreeEntity VIEW_ENTITY_PIVOT
	FreeEntity VIEW_ENTITY_PIVOT2
	Return ((GraphicWidth / 2 + xvalue * (GraphicWidth / 2)) >= maxwidth And (GraphicHeight / 2 - yvalue * (GraphicHeight / 2)) >= maxheight)
End Function

Function TakeFallDamage(damage#)
	Injuries = Injuries+damage
	ChannelVolume PlaySound_Strict(LoadTempSound("SFX\General\BodyFall.ogg")), 0.5*SFXVolume
	multiplayer_WriteTempSound("SFX\General\BodyFall.ogg")
End Function

Function CanInteract()
	Return (((Not MenuOpen) And (Not InvOpen) And (OtherOpen=Null) And (SelectedDoor = Null) And (Not LockMouse) And (ConsoleOpen = False) And (Using294 = False) And (SelectedScreen = Null) And 	EndingTimer=>0 And (Not NetworkServer\chatOpen) And Spectate\SpectatePlayer = -1 And (Not (TAB_MENU_STATE >= 2)) Or MainMenuOpen))
End Function

Function TakeScreenshot()
	If FileType("screens") <> 2 Then CreateDir("screens")
	
	Local ScreenShotTick%
	While(True)
		If FileSize("screens\scpmp-"+ScreenShotTick+".png") = 0 Then
			bank = CreateBank(RealGraphicWidth*RealGraphicHeight*3)
			If bank <> 0 Then
				LockBuffer(BackBuffer())
				For x = 0 To RealGraphicWidth-1
					For y = 0 To RealGraphicHeight-1
						pixel = ReadPixelFast(x,y,BackBuffer())
						PokeByte(bank,(y*(RealGraphicWidth*3))+(x*3),(pixel Shr 0) And $FF)
						PokeByte(bank,(y*(RealGraphicWidth*3))+(x*3)+1,(pixel Shr 8) And $FF)
						PokeByte(bank,(y*(RealGraphicWidth*3))+(x*3)+2,(pixel Shr 16) And $FF)
					Next
				Next
				UnlockBuffer(BackBuffer())
				fibuffer% = FI_ConvertFromRawBits%(bank,RealGraphicWidth,RealGraphicHeight,RealGraphicWidth*3,24,$FF0000,$00FF00,$0000FF,True)
				FI_Save(13,fibuffer,"screens\scpmp-"+ScreenShotTick+".png",0)
				FI_Unload(fibuffer%)
				FreeBank bank
				multiplayer_AddChatMsg("Screenshot taken in screens\scpmp-"+ScreenShotTick+".png",False)
			EndIf
			Exit
		EndIf
		ScreenShotTick = ScreenShotTick + 1
	Wend
End Function

Function UpdateFocus()
	InFocus = (api_GetForegroundWindow()=SCREEN_HWND)
	If InFocus = 0 Then 
		MENU_OPEN_TYPE = 2
		FlushKeys()
		FlushMouse()
	Else
		MENU_OPEN_TYPE = MenuOpen
	EndIf
	;if KeyDown(56) And KeyDown(15) Then
	;	Cls
	;	Flip
	;	FlushKeys()
	;	Delay 100
	;EndIf
	Return InFocus
End Function

Function UpdateFrame(FixedTimeSteps%)
	
	If FixedTimeSteps Then
		SetCurrTime(MilliSecs2())
		AddToTimingAccumulator(GetCurrTime()-GetPrevTime())
		SetPrevTime(GetCurrTime())
		FPSfactor = GetTickDuration()
		FPSfactor2 = GetTickDuration()
		FPSfactorNoLimit = GetTickDuration()
		PrevFPSFactor = GetTickDuration()
		
		CurTime = MicroSecs()
		DeltaMillisecs = CurTime-PrevTime
		ElapsedTime = DeltaMillisecs / 1000000.0
		PrevTime = CurTime
		FactorDelta = Max(Min(ElapsedTime * 70, 5.0), 0.0001)
	Else
		CurTime = MicroSecs()
		DeltaMillisecs = CurTime-PrevTime
		ElapsedTime = (CurTime - PrevTime) / 1000000.0
		PrevTime = CurTime
		PrevFPSFactor = FPSfactor
		FPSfactor = Max(Min(ElapsedTime * 70, 5.0), 0.0001)
		FPSfactorNoLimit = Max(ElapsedTime * 70, 0.0001)
		FPSfactor2 = FPSfactor
	EndIf
	
	If Framelimit > 0 Then
		ExecuteFramelimit(Framelimit, MilliSecs(), LoopDelay)
		LoopDelay = MilliSecs()
	Else
		If NetworkServer\Prediction Then 
			ExecuteFramelimit(PREDICTION_FRAMELIMIT, MilliSecs(), LoopDelay) ; Fixed framelimit for prediction
			LoopDelay = MilliSecs()
		EndIf
	EndIf
	
	If CheckFPS < MilliSecs2() Then
		FPS = ElapsedLoops
		ElapsedLoops = 0
		CheckFPS = MilliSecs2()+1000
	EndIf
	ElapsedLoops = ElapsedLoops + 1
	TicksCount = TicksCount + 1
End Function

Function RenderMessages()
	If MsgTimer > 0 Then
		Local temp% = False
		If (Not InvOpen%)
			If SelectedItem <> Null
				If SelectedItem\itemtemplate\tempname = "paper" Or SelectedItem\itemtemplate\tempname = "oldpaper"
					temp% = True
				EndIf
			EndIf
		EndIf
		
		If (Not temp%)
			Color 0,0,0
			AAText((GraphicWidth / 2)+1, (GraphicHeight / 2) + 201, Msg, True, False, Min(MsgTimer / 2, 255)/255.0)
			Color 255,255,255;Min(MsgTimer / 2, 255), Min(MsgTimer / 2, 255), Min(MsgTimer / 2, 255)
			If Left(Msg,14)="Blitz3D Error!" Then
				Color 255,0,0
			EndIf
			AAText((GraphicWidth / 2), (GraphicHeight / 2) + 200, Msg, True, False, Min(MsgTimer / 2, 255)/255.0)
		Else
			Color 0,0,0
			AAText((GraphicWidth / 2)+1, (GraphicHeight * 0.94) + 1, Msg, True, False, Min(MsgTimer / 2, 255)/255.0)
			Color 255,255,255;Min(MsgTimer / 2, 255), Min(MsgTimer / 2, 255), Min(MsgTimer / 2, 255)
			If Left(Msg,14)="Blitz3D Error!" Then
				Color 255,0,0
			EndIf
			AAText((GraphicWidth / 2), (GraphicHeight * 0.94), Msg, True, False, Min(MsgTimer / 2, 255)/255.0)
		EndIf
	End If
	RenderAmmoText()
	
	Color 255, 255, 255
	If ShowFPS Then 
		AASetFont ConsoleFont
		AAText 20, 20, "FPS: " + FPS 
		
		AASetFont FontSL
		AAText 20, 40, "MULTIPLAYER MOD v"+MULTIPLAYER_VERSION
		AASetFont Font1
	EndIf
	
	If EndingTimer < 0 Then
		If SelectedEnding <> "" Then DrawEnding()
	Else
		;createtick("men")
		DrawMenu()
	EndIf
	
	If b_br\YouAreTimer > 0 Then b_br\YouAreTimer=b_br\YouAreTimer-FPSfactor
	If MsgTimer > 0 Then MsgTimer=MsgTimer-FPSfactor
	If b_br\WonTimer > 0 Then b_br\WonTimer=b_br\WonTimer-FPSfactor
	
End Function

Function UpdateAmbient()
	ShouldPlay = Min(PlayerZone,2)
	If PlayerRoom\RoomTemplate\Name <> "pocketdimension" And PlayerRoom\RoomTemplate\Name <> "gatea" And PlayerRoom\RoomTemplate\Name <> "exit1" Then 
				
		If Rand(1500) = 1 Then
			For i = 0 To 5
				If AmbientSFX(i,CurrAmbientSFX)<>0 Then
					If ChannelPlaying(AmbientSFXCHN)=0 Then FreeSound_Strict AmbientSFX(i,CurrAmbientSFX) : AmbientSFX(i,CurrAmbientSFX) = 0
				EndIf			
			Next
			
			PositionEntity (SoundEmitter, EntityX(Camera) + Rnd(-1.0, 1.0), 0.0, EntityZ(Camera) + Rnd(-1.0, 1.0))
			
			If Rand(3)=1 Then PlayerZone = 3
			
			If PlayerRoom\RoomTemplate\Name = "173" Then 
				PlayerZone = 4
			ElseIf PlayerRoom\RoomTemplate\Name = "room860"
				If room860event\EventState = 1.0
					PlayerZone = 5
					PositionEntity (SoundEmitter, EntityX(SoundEmitter), 30.0, EntityZ(SoundEmitter))
				EndIf
			EndIf
			
			CurrAmbientSFX = Rand(0,AmbientSFXAmount(PlayerZone)-1)
			
			Select PlayerZone
				Case 0,1,2
					If AmbientSFX(PlayerZone,CurrAmbientSFX)=0 Then AmbientSFX(PlayerZone,CurrAmbientSFX)=LoadSound_Strict("SFX\Ambient\Zone"+(PlayerZone+1)+"\ambient"+(CurrAmbientSFX+1)+".ogg")
				Case 3
					If AmbientSFX(PlayerZone,CurrAmbientSFX)=0 Then AmbientSFX(PlayerZone,CurrAmbientSFX)=LoadSound_Strict("SFX\Ambient\General\ambient"+(CurrAmbientSFX+1)+".ogg")
				Case 4
					If AmbientSFX(PlayerZone,CurrAmbientSFX)=0 Then AmbientSFX(PlayerZone,CurrAmbientSFX)=LoadSound_Strict("SFX\Ambient\Pre-breach\ambient"+(CurrAmbientSFX+1)+".ogg")
				Case 5
					If AmbientSFX(PlayerZone,CurrAmbientSFX)=0 Then AmbientSFX(PlayerZone,CurrAmbientSFX)=LoadSound_Strict("SFX\Ambient\Forest\ambient"+(CurrAmbientSFX+1)+".ogg")
			End Select
			
			AmbientSFXCHN = PlaySound2(AmbientSFX(PlayerZone,CurrAmbientSFX), Camera, SoundEmitter)
			;ShouldPlay = Min(PlayerZone,2)
		EndIf
		UpdateSoundOrigin(AmbientSFXCHN,Camera, SoundEmitter)
		
		If Rand(50000) = 3 Then
			Local RN$ = PlayerRoom\RoomTemplate\Name$
			If RN$ <> "room860" And RN$ <> "room1123" And RN$ <> "173" And RN$ <> "dimension1499" Then
				If FPSfactor > 0 Then LightBlink = Rnd(1.0,2.0)
				PlaySound_Strict  LoadTempSound("SFX\SCP\079\Broadcast"+Rand(1,7)+".ogg")
			EndIf 
		EndIf
		ShouldPlay = Min(PlayerZone,2)
	EndIf
End Function

Function UpdateCamera()
	LightVolume = CurveValue(TempLightVolume, LightVolume, 50.0)
	CameraFogRange(Camera, CameraFogNear*LightVolume,CameraFogFar*LightVolume)
	CameraFogColor(Camera, 0,0,0)
	CameraFogMode Camera,1
	CameraRange(Camera, 0.05, Min(CameraFogFar*LightVolume*1.5,28))	
	
	If PlayerRoom\RoomTemplate\Name<>"pocketdimension" Then
		CameraClsColor(Camera, 0,0,0)
	EndIf
	
	AmbientLight Brightness, Brightness, Brightness
	CameraShake = Max(CameraShake - (FPSfactor / 10), 0)
	
End Function

Function UpdateMisc()
	If MouseHit1 And TAB_MENU_STATE < 2 And TAB_MENU_STATE > 0 Then 
		TAB_MENU_STATE = 2
		MouseHit1 = False
	EndIf
	ShouldEntitiesFall = True
		
	UpdateCheckpoint1 = False
	UpdateCheckpoint2 = False
	PlayerSoundVolume = CurveValue(0.0, PlayerSoundVolume, 5.0+(NetworkServer\Breach*30))
	CanSave% = True
	If PlayerRoom\RoomTemplate\Name = "dimension1499" And QuickLoadPercent > 0 And QuickLoadPercent < 100 Then ShouldEntitiesFall = False
	InFacility = CheckForPlayerInFacility()
End Function

Function UpdateGame()
	UpdateParticles_Time# = Min(1,UpdateParticles_Time#+FPSfactor)
	If UpdateParticles_Time#=1
		UpdateDevilEmitters()
		UpdateParticles_Devil()
		UpdateParticles_Time#=0
	EndIf
	If InfiniteStamina% Then Stamina = Min(100, Stamina + (100.0-Stamina)*0.01*FPSfactor)
	
	UpdateWorld(1*FPSfactor)
	
	
	BlurVolume = Min(CurveValue(0.0, BlurVolume, 20.0),0.95)
	If BlurTimer > 0.0 Then
		BlurVolume = Max(Min(0.95, BlurTimer / 1000.0), BlurVolume)
		BlurTimer = Max(BlurTimer - FPSfactor, 0.0)
	End If
	
	;[Block]
	darkA# = 0.0
	If Sanity < 0 Then
		If RestoreSanity Then Sanity = Min(Sanity + FPSfactor, 0.0)
		If Sanity < (-200) Then 
			darkA = Max(Min((-Sanity - 200) / 700.0, 0.6), darkA)
			If KillTimer => 0 Then 
				HeartBeatVolume = Min(Abs(Sanity+200)/500.0,1.0)
				HeartBeatRate = Max(70 + Abs(Sanity+200)/6.0,HeartBeatRate)
			EndIf
		EndIf
	End If
	
	If EyeStuck > 0 Then 
		BlinkTimer = BLINKFREQ
		EyeStuck = Max(EyeStuck-FPSfactor,0)
		
		If EyeStuck < 9000 Then BlurTimer = Max(BlurTimer, (9000-EyeStuck)*0.5)
		If EyeStuck < 6000 Then darkA = Min(Max(darkA, (6000-EyeStuck)/5000.0),1.0)
		If EyeStuck < 9000 And EyeStuck+FPSfactor =>9000 Then 
			Msg = "The eyedrops are causing your eyes to tear up."
			MsgTimer = 70*6
		EndIf
	EndIf
	
	If BlinkTimer < 0 Then
		If BlinkTimer > - 5 Then
			darkA = Max(darkA, Sin(Abs(BlinkTimer * 18.0)))
		ElseIf BlinkTimer > - 15
			darkA = 1.0
		Else
			darkA = Max(darkA, Abs(Sin(BlinkTimer * 18.0)))
		EndIf
		
		If BlinkTimer <= - 20 Then
			;Randomizes the frequency of blinking. Scales with difficulty.
			Select SelectedDifficulty\otherFactors
				Case EASY
					BLINKFREQ = Rnd(490,700)
				Case NORMAL
					BLINKFREQ = Rnd(455,665)
				Case HARD
					BLINKFREQ = Rnd(420,630)
			End Select 
			BlinkTimer = BLINKFREQ
		EndIf
		
		BlinkTimer = BlinkTimer - FPSfactor
	Else
		BlinkTimer = BlinkTimer - FPSfactor * 0.6 * BlinkEffect
		If EyeIrritation > 0 Then BlinkTimer=BlinkTimer-Min(EyeIrritation / 100.0 + 1.0, 4.0) * FPSfactor
		
		darkA = Max(darkA, 0.0)
	End If
	
	If LightFlash > 0 Then BlinkTimer = 0
	
	EyeIrritation = Max(0, EyeIrritation - FPSfactor)
	
	If BlinkEffectTimer > 0 Then
		BlinkEffectTimer = BlinkEffectTimer - (FPSfactor/70)
	Else
		If BlinkEffect <> 1.0 Then BlinkEffect = 1.0
	EndIf
	
	LightBlink = Max(LightBlink - (FPSfactor / 35.0), 0)
	If LightBlink > 0 Then darkA = Min(Max(darkA, LightBlink * Rnd(0.3, 0.8)), 1.0)
	
	If Using294 Then darkA=1.0
	
	If (Not WearingNightVision) Then darkA = Max((1.0-SecondaryLightOn)*0.9, darkA)
	
	If KillTimer < 0 Then
		InvOpen = False
		SelectedItem = Null
		SelectedScreen = Null
		SelectedMonitor = Null
		BlurTimer = Abs(KillTimer*5)
		KillTimer=KillTimer-(FPSfactor*0.8)
		If KillTimer < - 360 And (Not NetworkServer\Breach) Then
			MENU_OPEN_TYPE = 1
			MenuOpen = True 
			If SelectedEnding <> "" Then EndingTimer = Min(KillTimer,-0.1)
		EndIf
		darkA = Max(darkA, Min(Abs(KillTimer / 400.0), 1.0))
	EndIf
	
	If FallTimer < 0 Then
		If SelectedItem <> Null Then
			If Instr(SelectedItem\itemtemplate\tempname,"hazmatsuit") Or Instr(SelectedItem\itemtemplate\tempname,"vest") Then
				If WearingHazmat=0 And WearingVest=0 Then
					DropItem(SelectedItem)
				EndIf
			EndIf
		EndIf
		InvOpen = False
		SelectedItem = Null
		SelectedScreen = Null
		SelectedMonitor = Null
		BlurTimer = Abs(FallTimer*10)
		FallTimer = FallTimer-FPSfactor
		darkA = Max(darkA, Min(Abs(FallTimer / 400.0), 1.0))				
	EndIf
	
	If SelectedItem <> Null Then
		If SelectedItem\itemtemplate\tempname = "navigator" Or SelectedItem\itemtemplate\tempname = "nav" Then darkA = Max(darkA, 0.5)
	End If
	If SelectedScreen <> Null Then darkA = Max(darkA, 0.5)

	If DarkA <> 0.0 And (Not(LightFlash > 0)) Then
		ShowEntity(Dark)
		EntityAlpha(Dark, darkA)	
	Else
		HideEntity(Dark)
	EndIf
	
	If LightFlash > 0 Then
		ShowEntity Light
		EntityAlpha(Light, Max(Min(LightFlash + Rnd(-0.2, 0.2), 1.0), 0.0))
		LightFlash = Max(LightFlash - (FPSfactor / 70.0), 0)
	Else
		HideEntity Light
		;EntityAlpha(Light, LightFlash)
	EndIf
	
	EntityColor Light,255,255,255
End Function

Function UpdateButtons()
	If Spectate\SpectatePlayer = -1 Then
		If KeyHit(KEY_INV) And VomitTimer >= 0 Then
			If (Not UnableToMove) And (Not IsZombie) And (Not Using294) Then
				Local W$ = ""
				Local V# = 0
				If SelectedItem<>Null
					W$ = SelectedItem\itemtemplate\tempname
					V# = SelectedItem\state
				EndIf
				If (W<>"vest" And W<>"finevest" And W<>"hazmatsuit" And W<>"hazmatsuit2" And W<>"hazmatsuit3") Or V=0 Or V=100
					If InvOpen Then
						ResetMouse()
					EndIf
					InvOpen = Not InvOpen
					If OtherOpen<>Null Then OtherOpen=Null
					SelectedItem = Null
				EndIf
			EndIf
		EndIf
	EndIf
	If CurrSave = "" Then CurrSave = "untitled"
	If networkserver\breach = False And MyPlayer\IsDead = 0 Then
		If KeyHit(KEY_SAVE) And JumpState = JumpFalling Then
			If SelectedDifficulty\saveType = SAVEANYWHERE Then
				Local tosave = True
				Select PlayerRoom\RoomTemplate\Name
					Case "173"
						If EntityY(Collider)>1040.0*RoomScale Then
							tosave = False
							Msg = "You cannot save in this location."
							MsgTimer = 70 * 4
						EndIf
					Case "exit1","gatea","pocketdimension","dimension1499"
						tosave = False
						Msg = "You cannot save in this location."
						MsgTimer = 70 * 4
					Default
						If (Not CanSave) Or QuickLoadPercent > -1 Then
							Msg = "You cannot save at this moment."
							MsgTimer = 70 * 4
							;SetSaveMSG("You cannot save at this moment.")
							If QuickLoadPercent > -1
								Msg = Msg + " (game is loading)"
								;Save_MSG = Save_MSG + " (game is loading)"
							EndIf
						Else
							If NetworkServer\MainPlayer Then 
								SaveGame(SavePath + CurrSave + "\")
							Else
								Msg = "Game progress saved."
								If SelectedDifficulty\saveType = SAVEONSCREENS Then
									PlaySound_Strict(LoadTempSound("SFX\General\Save2.ogg"))
								Else
									PlaySound_Strict(LoadTempSound("SFX\General\Save1.ogg"))
								EndIf
								MsgTimer = 70*8
							EndIf
						EndIf
				End Select
				
			ElseIf SelectedDifficulty\saveType = SAVEONSCREENS
				If SelectedScreen=Null And SelectedMonitor=Null Then
					Msg = "Saving is only permitted on clickable monitors scattered throughout the facility."
					MsgTimer = 70 * 4
					;SetSaveMSG("Saving is only permitted on clickable monitors scattered throughout the facility.")
				Else
					tosave = True
					Select PlayerRoom\RoomTemplate\Name
						Case "173"
							If EntityY(Collider)>1040.0*RoomScale Then
								tosave = False
								Msg = "You cannot save in this location."
								MsgTimer = 70 * 4
							EndIf
						Case "exit1","gatea","pocketdimension","dimension1499"
							tosave = False
							Msg = "You cannot save in this location."
							MsgTimer = 70 * 4
						Default
							If (Not CanSave) Or QuickLoadPercent > -1
								Msg = "You cannot save at this moment."
								MsgTimer = 70 * 4
								;SetSaveMSG("You cannot save at this moment.")
								If QuickLoadPercent > -1
									Msg = Msg + " (game is loading)"
									;Save_MSG = Save_MSG + " (game is loading)"
								EndIf
							Else
								If SelectedScreen<>Null
									GameSaved = False
									Playable = True
									DropSpeed = 0
								EndIf
								If NetworkServer\MainPlayer Then 
									SaveGame(SavePath + CurrSave + "\")
								Else
									Msg = "Game progress saved."
									If SelectedDifficulty\saveType = SAVEONSCREENS Then
										PlaySound_Strict(LoadTempSound("SFX\General\Save2.ogg"))
									Else
										PlaySound_Strict(LoadTempSound("SFX\General\Save1.ogg"))
									EndIf
									MsgTimer = 70*8
								EndIf
								
							EndIf
					End Select
					
				EndIf
			EndIf

			If Msg = "Game progress saved." Then SetSavingPosition(PlayerRoom\RoomTemplate\Name$, EntityX(Collider), EntityY(Collider), EntityZ(Collider), EntityYaw(Collider))
		Else If SelectedDifficulty\saveType = SAVEONSCREENS And (SelectedScreen<>Null Or SelectedMonitor<>Null)
			If (Msg<>"Game progress saved." And Msg<>"You cannot save in this location."And Msg<>"You cannot save at this moment.") Or MsgTimer<=0 Then
				Msg = "Press "+KeyName(KEY_SAVE)+" to save."
				MsgTimer = 70*4
				;SetSaveMSG("Press "+KeyName(KEY_SAVE)+" to save.")
			EndIf
			
			If MouseHit2 Then SelectedMonitor = Null
		EndIf
	EndIf
	
	If KeyHit(KEY_CONSOLE) Then
		If rcon\Authorized Or NoCheat = 0 Or DEVELOPER_MODE Then
			If CanOpenConsole
				If ConsoleOpen Then
					ResetMouse()
				EndIf
				ConsoleOpen = (Not ConsoleOpen)
				FlushKeys()
			EndIf
		EndIf
	EndIf
	
	If Not(rcon\Authorized Or NoCheat = 0 Or DEVELOPER_MODE) Then ConsoleOpen = False
	
	If KeyDown(49) And (Not MainMenuOpen) And (((Not MenuOpen) And (Not InvOpen) And (OtherOpen=Null) And (SelectedDoor = Null) And (ConsoleOpen = False) And (Using294 = False) And (SelectedScreen = Null) And (Not LockMouse) And EndingTimer=>0 And (Not NetworkServer\ChatOpen) Or MainMenuOpen)) Then 
		If TAB_MENU_STATE < 2 Then TAB_MENU_STATE = 1
	Else
		If TAB_MENU_STATE < 2 Then
			TAB_MENU_STATE = 0
			currplayer = Null
		Else
			If Not((Not MainMenuOpen) And (((Not MenuOpen) And (Not InvOpen) And (OtherOpen=Null) And (SelectedDoor = Null) And (ConsoleOpen = False) And (Using294 = False) And (SelectedScreen = Null) And (Not LockMouse) And EndingTimer=>0 And (Not NetworkServer\ChatOpen) Or MainMenuOpen))) Then TAB_MENU_STATE = 0
		EndIf
	EndIf
End Function

Function Render2D()
	;createtick("blur")
	UpdateBlur(BlurVolume)
	;createtick("upgui")
	multiplayer_UpdateGUI()
	;createtick("drgui")
	DrawGUI()
	;createtick("cons")
	UpdateConsole()
	;createtick("rendermess")
	RenderMessages()
	;createtick("dql")
	DrawQuickLoading()
	;createtick("achv")
	UpdateAchievementMsg()
End Function

Function KeyEvent(button)
	If JOYSTICK_CONNECTED Then
		Return JoyEvent(button)
	Else
		Return KeyHit(button)
	EndIf
End Function

Function KeyDownEvent(button)
	If JOYSTICK_CONNECTED Then
		Return JoyDownEvent(button)
	Else
		Return KeyDown(Button)
	EndIf
End Function

Function JoyEvent(Button)
	Return ConvertJoy(Button)
End Function

Function JoyDownEvent(Button)
	Return ConvertJoy(Button)
End Function

Function ConvertJoy(Button)
	Select Button
		Case KEY_LEFT: 
		Case KEY_DOWN:
		Case KEY_RIGHT:
		Case KEY_UP:
	End Select
End Function

Function IsMouseUp()
	If MOUSEINTERACT Then Return MouseUp1
	Return KeyHitE
End Function

Function UpdateHotkeys()
	
	If CanInteract() Then
		For i = 1 To 5
			If KeyHit(i+1) Then
				If InvOpen Then Return
					If Inventory(i-1) <> Null Then
						If WearingHazmat > 0 And Instr(Inventory(i-1)\itemtemplate\tempname,"hazmatsuit")=0 Then
							Msg = "You cannot use any items while wearing a hazmat suit."
							MsgTimer = 70*5
							Return
						EndIf
						If (Not Instr(Inventory(i-1)\itemtemplate\tempname,"scp513")) And (SelectedItem = Null Or Inventory(i-1) = SelectedItem) Then
							;if Inventory(n)\itemtemplate\tempname = "chicken" Then
							;	Inventory(n)\SoundCHN = PlaySound_Strict(EatingSFX)
							;;	multiplayer_WriteSound(EatingSFX, EntityX(Collider), EntityY(Collider), EntityZ(Collider), 8.0)
							;EndIf
							If SelectedItem = Null Then 
								SelectedItem = Inventory(i-1)
							Else
								SelectedItem = Null
							EndIf
							If Inventory(i-1)\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(Inventory(i-1)\itemtemplate\sound))
							If Not MyPlayer\MicroHIDSHOOT Then 
								PickupGun(Inventory(i-1))
							EndIf
							InvOpen = False
							FlushKeys()
						EndIf
					EndIf
				EndIf
		Next
	ElseIf MyPlayer\CurrentRadio < 1 Then
		For i = 1 To 5
			KeyHit(i+1)
		Next
	EndIf

End Function

Function Trim$(txt$)
	txt0=Len(txt)
	If txt0=0 Then Return txt

	Local txt1 = 1
	While Mid(txt,txt1,1)=" "
		txt1=txt1+1
		If txt1>txt0 Then Return ""
	Wend
	
	Local txt2 = txt0
	While Mid(txt, txt2, 1)=" "
		txt2=txt2-1
	Wend
	Return Mid(txt,txt1,txt2+1-txt1)
End Function

Function StartNewGame()
	If CurrSave = "" Then CurrSave = "untitled"
	SelectedMap = ""
	GameLoad = 1
	MainMenuOpen = False
	LoadEntities()
	LoadAllSounds()
	InitNewGame()
	FlushKeys()
	FlushMouse()
End Function

Function StartLoadGame(savepath$, save$)
	GameLoad = 1
	MainMenuOpen = False
	MenuOpen = False
	LoadEntities()
	LoadAllSounds()
	
	LoadGame(savepath)
	CurrSave = save
	InitLoadGame()
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D