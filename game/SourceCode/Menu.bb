Global MenuBack% = LoadImage_Strict("GFX\menu\back.jpg")
Global MenuText% = LoadImage_Strict("GFX\menu\scptext.jpg")
Global Menu173% = LoadImage_Strict("GFX\menu\173back.jpg")
MenuWhite = LoadImage_Strict("GFX\menu\menuwhite.jpg")
MenuBlack = LoadImage_Strict("GFX\menu\menublack.jpg")
MaskImage MenuBlack, 255,255,0
Global QuickLoadIcon% = LoadImage_Strict("GFX\menu\QuickLoading.png")

ResizeImage(MenuBack, ImageWidth(MenuBack) * MenuScale, ImageHeight(MenuBack) * MenuScale)
ResizeImage(MenuText, ImageWidth(MenuText) * MenuScale, ImageHeight(MenuText) * MenuScale)
ResizeImage(Menu173, ImageWidth(Menu173) * MenuScale, ImageHeight(Menu173) * MenuScale)
ResizeImage(QuickLoadIcon, ImageWidth(QuickLoadIcon) * MenuScale, ImageHeight(QuickLoadIcon) * MenuScale)

For i = 0 To 3
	ArrowIMG(i) = LoadImage_Strict("GFX\menu\arrow.png")
	RotateImage(ArrowIMG(i), 90 * i)
	HandleImage(ArrowIMG(i), 0, 0)
Next

Global RandomSeed$

Dim MenuBlinkTimer%(2), MenuBlinkDuration%(2)
MenuBlinkTimer%(0) = 1
MenuBlinkTimer%(1) = 1

Global MenuStr$, MenuStrX%, MenuStrY%

Global MainMenuTab%, PrevMainMenuTab%
Global SettingsMenu%

Global IntroEnabled% = GetINIInt(OptionFile, "options", "intro enabled")

Global SelectedInputBox%
Global InitedScreen
Global SavePath$ = "Saves\"
Global SaveMSG$
Global StringPick
;nykyisen tallennuksen nimi ja samalla miss?� kansiossa tallennustiedosto sijaitsee saves-kansiossa
Global CurrSave$

Global SaveGameAmount%
Dim SaveGames$(SaveGameAmount+1) 
Dim SaveGameTime$(SaveGameAmount + 1)
Dim SaveGameDate$(SaveGameAmount + 1)
Dim SaveGameVersion#(SaveGameAmount + 1)
Dim SaveGameSeed$(SaveGameAmount + 1)
Dim SaveGameDifficulty(SaveGameAmount + 1)

Global SavedMapsAmount% = 0
Dim SavedMaps$(SavedMapsAmount+1)
Dim SavedMapsAuthor$(SavedMapsAmount+1)

Global SelectedMap$

LoadSaveGames()

Local TempStream = CreateUDPStream()
Local RandomPort = UDPStreamPort(TempStream)
CloseUDPStream(TempStream)
Global FullscreenSetting, BorderlessWindowedSetting, Bit16ModeSetting, LauncherEnabledSetting
Global SelectedGFXDriverSetting
Global CurrLoadGamePage% = 0
Global timeservername$, Timeport, timerandomseed$, timeintroenabled, timetickrate, timegravity$, timejumpmode, timenocheat, timemaxplayers, timevoice, timepassword$
Global timekeepinventory
timegravity$ = "0.0023"
timetickrate = 128
timejumpmode = True
timemaxplayers = 16
timevoice = True
timeport = RandomPort
timerandomseed = SetRandomSeed()
timeservername = "SCP Server v"+MULTIPLAYER_VERSION

Global CurrentPercent#

Global currplayer.players
Function UpdateMainMenu()
	If ShouldPlay = 21 Then
		EndBreathSFX = LoadSound("SFX\Ending\MenuBreath.ogg")
		EndBreathCHN = PlaySound(EndBreathSFX)
		ShouldPlay = 66
	ElseIf ShouldPlay = 66
		If (Not ChannelPlaying(EndBreathCHN)) Then
			FreeSound(EndBreathSFX)
			ShouldPlay = 11
		EndIf
	Else
		ShouldPlay = 11
	EndIf
	
	Local x%, y%, width%, height%, temp%
	Color 0,0,0
	Rect 0,0,GraphicWidth,GraphicHeight,True
	
	ShowPointer()
	
	DrawImage(MenuBack, 0, 0)
	
	If (MilliSecs2() Mod MenuBlinkTimer(0)) >= Rand(MenuBlinkDuration(0)) Then
		DrawImage(Menu173, GraphicWidth - ImageWidth(Menu173), GraphicHeight - ImageHeight(Menu173))
	EndIf
	
	If Rand(300) = 1 Then
		MenuBlinkTimer(0) = Rand(4000, 8000)
		MenuBlinkDuration(0) = Rand(200, 500)
	End If
	AASetFont Font1
	
	MenuBlinkTimer(1)=MenuBlinkTimer(1)-FPSfactor
	If MenuBlinkTimer(1) < MenuBlinkDuration(1) Then
		Color(50, 50, 50)
		AAText(MenuStrX + Rand(-5, 5), MenuStrY + Rand(-5, 5), MenuStr, True)
		If MenuBlinkTimer(1) < 0 Then
			MenuBlinkTimer(1) = Rand(700, 800)
			MenuBlinkDuration(1) = Rand(10, 35)
			MenuStrX = Rand(700, 1000) * MenuScale
			MenuStrY = Rand(100, 600) * MenuScale
			
			Select Rand(0, 22)
				Case 0, 2, 3
					MenuStr = "DON'T BLINK"
				Case 4, 5
					MenuStr = "Secure. Contain. Protect."
				Case 6, 7, 8
					MenuStr = "You want happy endings? Fuck you."
				Case 9, 10, 11
					MenuStr = "Sometimes we would have had time to scream."
				Case 12, 19
					MenuStr = "NIL"
				Case 13
					MenuStr = "NO"
				Case 14
					MenuStr = "black white black white black white gray"
				Case 15
					MenuStr = "Stone does not care"
				Case 16
					MenuStr = "9341"
				Case 17
					MenuStr = "It controls the doors"
				Case 18
					MenuStr = "e8m106]af173o+079m895w914"
				Case 20
					MenuStr = "It has taken over everything"
				Case 21
					MenuStr = "The spiral is growing"
				Case 22
					MenuStr = Chr(34)+"Some kind of gestalt effect due to massive reality damage."+Chr(34)
			End Select
		EndIf
	EndIf
	
	AASetFont Font2
	
	DrawImage(MenuText, GraphicWidth / 2 - ImageWidth(MenuText) / 2, GraphicHeight - 20 * MenuScale - ImageHeight(MenuText))
	
	If GraphicWidth > 1240 * MenuScale Then
		DrawTiledImageRect(MenuWhite, 0, 5, 512, 7 * MenuScale, 985.0 * MenuScale, 407.0 * MenuScale, (GraphicWidth - 1240 * MenuScale) + 300, 7 * MenuScale)
	EndIf
	
	If (Not MouseDown1)
		OnSliderID = 0
	EndIf
	If GlobalServerUpdate < MilliSecs() Then
		For sc.Servers = Each servers
			If sc\sType = SELECTED_SERVERS Then multiplayer_list_UpdateServer(sc)
		Next
		GlobalServerUpdate = MilliSecs()+30000
	EndIf
	
	If MainMenuTab = 0 Then
		For i% = 0 To 4
			temp = False
			x = 159 * MenuScale
			y = (286 + 100 * i) * MenuScale
			
			width = 400 * MenuScale
			height = 70 * MenuScale
			
			temp = (MouseHit1 And MouseOn(x, y, width, height))
			
			If temp Then PlaySound_Strict(ButtonSFX)
			Local txt$
			Select i
				Case 0
					txt = "MULTIPLAYER"
					RandomSeed = ""
					If temp Then
						If udp_GetStream() Then 
							MainMenuTab = 14
						Else
							For sc.Servers = Each servers
								If sc\sType = SELECTED_SERVERS And sc\spage = SELECTED_PAGE Then multiplayer_list_UpdateServer(sc)
							Next
							MainMenuTab = 1
							
							chatheightc# = 0
							For sc.Servers = Each Servers
								If sc\sType = SELECTED_SERVERS And (Not (sc\ping = 0 And (sc\sType = SERVER_INTERNET Or sc\sType = SERVER_OFFICIAL))) Then chatheightc = chatheightc+35*MenuScale
							Next
							
							height = 206*MenuScale
							pluslist = (35.0/1.16)*5
							height=height+(pluslist*MenuScale)
							
							chatScroll = -chatHeightc+height
						EndIf
						Exit
					EndIf
				Case 1
					txt = "NEW GAME"
					RandomSeed = ""
					If temp Then
						If udp_GetStream() Then
							MainMenuTab = 14
						Else
							RandomSeed = SetRandomSeed()
							
							;RandomSeed = MilliSecs()
							MainMenuTab = 20
						EndIf
						Exit
					EndIf
				Case 2
					txt = "LOAD GAME"
					If temp Then
						If udp_GetStream() Then
							MainMenuTab = 14
						Else
							LoadSaveGames()
							MainMenuTab = 2
						EndIf
						Exit
					EndIf
				Case 3
					txt = "OPTIONS"
					If temp Then MainMenuTab = 3
				Case 4
					txt = "QUIT"
					If temp Then
						SendStatisticRequest(PLAYER_EXIT_GAME)
						CloseGame()
					EndIf
			End Select
			
			DrawButton(x, y, width, height, txt)
			
			;rect(x + 4, y + 4, width - 8, height - 8)
			;color 255, 255, 255	
			;text(x + width / 2, y + height / 2, Str, True, True)
		Next	
		
	Else
		
		x = 159 * MenuScale
		y = 286 * MenuScale
		
		width = 400 * MenuScale
		height = 70 * MenuScale
		
		DrawFrame(x, y, width, height)
		;If DrawButton(x + width + 20 * MenuScale, y-100, 580 * MenuScale - width - 20 * MenuScale, height, "XYETA", False) Then SendFile(1, "test\testg.ogg")
		If DrawButton(x + width + 20 * MenuScale, y, 580 * MenuScale - width - 20 * MenuScale, height-((height/2.1*udp_GetStream()*(MainMenuTab = 14))), "BACK", False) Then
			NetworkServer\CheckVoice = False
			SaveMultiplayerOptions()
			Select MainMenuTab
				Case 14
					MainMenuTab = 0
				Case 32
					MainMenuTab = PrevMainMenuTab
				Case 40
					MainMenuTab = 14
				Case 21,23
					MainMenuTab = 14
				Case 14 
					MainMenuTab= 1
				Case 12
					MainMenuTab = 1
				Case 9
					MainMenuTab = 1
				Case 8
					MainMenuTab = 1
				Case 1
					
					MainMenuTab = 0
				Case 2
					CurrLoadGamePage = 0
					MainMenuTab = 0
				Case 3,5,6,7 ;save the options
					SaveOptionsINI()
					
					UserTrackCheck% = 0
					UserTrackCheck2% = 0
					
					AntiAlias Opt_AntiAlias
					MainMenuTab = 0
				Case 4 ;move back to the "new game" tab
					MainMenuTab = 20
					CurrLoadGamePage = 0
					MouseHit1 = False
				Default
					MainMenuTab = 0
			End Select
			MouseHit1 = False
		EndIf
		
		If udp_GetStream()*(MainMenuTab = 14) Then
			If DrawButton(x + width + 20 * MenuScale, y+(height/2), 580 * MenuScale - width - 20 * MenuScale, height-((height/2.1)-4*MenuScale), "DISCONNECT", False) Then DisconnectServer()
		EndIf
		Local pages[MAX_SERVER_LIST_PAGES], ppages[MAX_PLAYER_LIST_PAGES]
		Select MainMenuTab
			Case 20
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "NEW GAME", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 330 * MenuScale
				
				DrawFrame(x, y, width, height)				
				
				AASetFont Font1
				
				AAText (x + 20 * MenuScale, y + 20 * MenuScale, "Name:")
				CurrSave = InputBox(x + 150 * MenuScale, y + 15 * MenuScale, 200 * MenuScale, 30 * MenuScale, CurrSave, 1)
				CurrSave = Left(CurrSave, 15)
				CurrSave = Replace(CurrSave,":","")
				CurrSave = Replace(CurrSave,".","")
				CurrSave = Replace(CurrSave,"/","")
				CurrSave = Replace(CurrSave,"\","")
				CurrSave = Replace(CurrSave,"<","")
				CurrSave = Replace(CurrSave,">","")
				CurrSave = Replace(CurrSave,"|","")
				CurrSave = Replace(CurrSave,"?","")
				CurrSave = Replace(CurrSave,Chr(34),"")
				CurrSave = Replace(CurrSave,"*","")
				
				Color 255,255,255
				If SelectedMap = "" Then
					AAText (x + 20 * MenuScale, y + 60 * MenuScale, "Map seed:")
					RandomSeed = Left(InputBox(x+150*MenuScale, y+55*MenuScale, 200*MenuScale, 30*MenuScale, RandomSeed, 3),15)	
				Else
					AAText (x + 20 * MenuScale, y + 60 * MenuScale, "Selected map:")
					Color (255, 255, 255)
					Rect(x+150*MenuScale, y+55*MenuScale, 200*MenuScale, 30*MenuScale)
					Color (0, 0, 0)
					Rect(x+150*MenuScale+2, y+55*MenuScale+2, 200*MenuScale-4, 30*MenuScale-4)
					
					Color (255, 0,0)
					If Len(SelectedMap)>15 Then
						AAText(x+150*MenuScale + 100*MenuScale, y+55*MenuScale + 15*MenuScale, Left(SelectedMap,14)+"...", True, True)
					Else
						AAText(x+150*MenuScale + 100*MenuScale, y+55*MenuScale + 15*MenuScale, SelectedMap, True, True)
					EndIf
					
					If DrawButton(x+370*MenuScale, y+55*MenuScale, 120*MenuScale, 30*MenuScale, "Deselect", False) Then
						SelectedMap=""
					EndIf
				EndIf	
				
				AAText(x + 20 * MenuScale, y + 110 * MenuScale, "Enable intro sequence:")
				IntroEnabled = DrawTick(x + 280 * MenuScale, y + 110 * MenuScale, IntroEnabled)	
				
				;Local modeName$, modeDescription$, selectedDescription$
				AAText (x + 20 * MenuScale, y + 150 * MenuScale, "Difficulty:")				
				For i = SAFE To CUSTOM
					If DrawTick(x + 20 * MenuScale, y + (180+30*i) * MenuScale, (SelectedDifficulty = difficulties(i))) Then SelectedDifficulty = difficulties(i)
					Color(difficulties(i)\r,difficulties(i)\g,difficulties(i)\b)
					AAText(x + 60 * MenuScale, y + (180+30*i) * MenuScale, difficulties(i)\name)
				Next
				
				Color(255, 255, 255)
				DrawFrame(x + 150 * MenuScale,y + 155 * MenuScale, 410*MenuScale, 150*MenuScale)
				
				If SelectedDifficulty\customizable Then
					SelectedDifficulty\permaDeath =  DrawTick(x + 160 * MenuScale, y + 165 * MenuScale, (SelectedDifficulty\permaDeath))
					AAText(x + 200 * MenuScale, y + 165 * MenuScale, "Permadeath")
					
					If DrawTick(x + 160 * MenuScale, y + 195 * MenuScale, SelectedDifficulty\saveType = SAVEANYWHERE And (Not SelectedDifficulty\permaDeath), SelectedDifficulty\permaDeath) Then 
						SelectedDifficulty\saveType = SAVEANYWHERE
					Else
						SelectedDifficulty\saveType = SAVEONSCREENS
					EndIf
					
					AAText(x + 200 * MenuScale, y + 195 * MenuScale, "Save anywhere")	
					
					SelectedDifficulty\aggressiveNPCs =  DrawTick(x + 160 * MenuScale, y + 225 * MenuScale, SelectedDifficulty\aggressiveNPCs)
					AAText(x + 200 * MenuScale, y + 225 * MenuScale, "Aggressive NPCs")
					
					;Other factor's difficulty
					Color 255,255,255
					DrawImage ArrowIMG(1),x + 155 * MenuScale, y+251*MenuScale
					If MouseHit1
						If ImageRectOverlap(ArrowIMG(1),x + 155 * MenuScale, y+251*MenuScale, ScaledMouseX(),ScaledMouseY(),0,0)
							If SelectedDifficulty\otherFactors < HARD
								SelectedDifficulty\otherFactors = SelectedDifficulty\otherFactors + 1
							Else
								SelectedDifficulty\otherFactors = EASY
							EndIf
							PlaySound_Strict(ButtonSFX)
						EndIf
					EndIf
					Color 255,255,255
					Select SelectedDifficulty\otherFactors
						Case EASY
							AAText(x + 200 * MenuScale, y + 255 * MenuScale, "Other difficulty factors: Easy")
						Case NORMAL
							AAText(x + 200 * MenuScale, y + 255 * MenuScale, "Other difficulty factors: Normal")
						Case HARD
							AAText(x + 200 * MenuScale, y + 255 * MenuScale, "Other difficulty factors: Hard")
					End Select
				Else
					RowText(SelectedDifficulty\description, x+160*MenuScale, y+160*MenuScale, (410-20)*MenuScale, 200)					
				EndIf
				
				If DrawButton(x, y + height + 20 * MenuScale, 160 * MenuScale, 70 * MenuScale, "Load map", False) Then
					MainMenuTab = 4
					LoadSavedMaps()
				EndIf
				
				AASetFont Font2
				
				If DrawButton(x + 420 * MenuScale, y + height + 20 * MenuScale, 160 * MenuScale, 70 * MenuScale, "START", False) Then
					If CurrSave = "" Then CurrSave = "untitled"
		
					If RandomSeed = "" Then
						RandomSeed = Abs(MilliSecs())
					EndIf
					
					SeedRnd GenerateSeedNumber(RandomSeed)
					
					Local SameFound% = 0
					
					For i% = 1 To SaveGameAmount
						If (SameFound = 0 And SaveGames(i - 1) = CurrSave) Or (SameFound > 0 And SaveGames(i - 1) = CurrSave + " (" + (SameFound + 1) + ")") Then
							SameFound = SameFound + 1
							i = 0
						EndIf
					Next
						
					If SameFound > 0 Then CurrSave = CurrSave + " (" + (SameFound + 1) + ")"
					
					LoadEntities()
					LoadAllSounds()
					InitNewGame()
					MainMenuOpen = False
					FlushKeys()
					FlushMouse()
					
					PutINIValue(OptionFile, "options", "intro enabled", IntroEnabled%)
					
				EndIf
			Case 32
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "INPUTS", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 330 * MenuScale
				
				DrawFrame(x, y, width, height + 60 * MenuScale)
				AASetFont Font1
				For i = 0 To snd_in_count()-1
					Color 0,255,0
					If voice\selectedinput = i Then AAText(x+(10+(AAStringWidth(snd_in_name(0))*2))*MenuScale, y + (20*(i+1)) * MenuScale, "SELECTED")
					Color 255,255,255
					If DrawButton(x + 10 * MenuScale, y + (20*(i+1)) * MenuScale, (AAStringWidth(snd_in_name(0))*2)*MenuScale, (AAStringHeight(snd_in_name(0))*1.5)*MenuScale, snd_in_name(0), False, False) Then voice\selectedinput = i
				Next
			Case 12,21
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "SETTINGS", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 330 * MenuScale
				
				DrawFrame(x, y, width, height + 60 * MenuScale)
				AASetFont Font1
				If DrawButton(x, y, width/2, 30 * MenuScale, "MULTIPLAYER", False) Then SelectedSettings = 0
				If DrawButton(x+(width/2), y, width/2, 30 * MenuScale, "VOICE", False) Then SelectedSettings = 1
				Select SelectedSettings
					Case 0
						y = y + 20*MenuScale
						AAText(x + 20 * MenuScale, y + 40 * MenuScale, "Name:")
						If Not udp_GetStream() Then
							Nickname$ = Left(InputBox(x + 115 * MenuScale, y + 35 * MenuScale, 250 * MenuScale, 30 * MenuScale, Nickname$, 1),24)
						Else
							AASetFont FontSL
							Color 255,0,0
							AAText(x + 115 * MenuScale, y + 15 * MenuScale, "Name change not available")
							Color 255,255,255
							AASetFont Font1
							InputBox(x + 115 * MenuScale, y + 35 * MenuScale, 250 * MenuScale, 30 * MenuScale, Nickname$, 1)
						EndIf

						AAText(x + 20 * MenuScale, y + 80 * MenuScale, "See players HUD:")
						NetworkServer\SeeNames = (DrawTick(x + 280 * MenuScale, y + 80 * MenuScale, NetworkServer\SeeNames)<>0)
						If MouseOn(x + 280 * MenuScale, y + 80 * MenuScale, 20*MenuScale, 20*MenuScale) Then
							DrawOptionsToolImage(x+580*MenuScale,y,150*MenuScale,246*MenuScale, mpimg\menu_image[NetworkServer\SeeNames])
						EndIf
						;AAText(x + 20 * MenuScale, y + 110 * MenuScale, "See players HP Bar:")
						;NetworkServer\hpbar = DrawTick(x + 280 * MenuScale, y + 110 * MenuScale, NetworkServer\hpbar)
						
						AAText(x + 20 * MenuScale, y + 110 * MenuScale, "Download speed (bytes/ms): "+Int(NetworkServer\downloadspeed))
						NetworkServer\downloadspeed = SlideBar(x + 270*MenuScale, y+110*MenuScale, 150*MenuScale, NetworkServer\downloadspeed*0.012, False)/0.012
						If NetworkServer\downloadspeed < 512 Or NetworkServer\downloadspeed > 8334 Then NetworkServer\downloadspeed = 512
						Local prevget = (mainplayersvolume<>0)
						AAText(x + 20 * MenuScale, y + 140 * MenuScale, "Voice chat:")
						prevvolume2# = mainplayersvolume
						Local get = DrawTick(x + 280 * MenuScale, y + 149 * MenuScale, prevget)
						If get <> prevget Then
							If prevget = 1 Then mainplayersvolume = 0 Else mainplayersvolume = 1.0
						EndIf
						mainplayersvolume = (SlideBar(x + 165*MenuScale, y+170*MenuScale, 150*MenuScale, mainplayersvolume*100.0, False)/100.0)
						AAText x + 20*MenuScale, y+170*MenuScale, "Players volume: "+Int(mainplayersvolume*100)
						If prevvolume2 <> mainplayersvolume Then
							For p.players = Each players
								p\volume = mainplayersvolume
							Next
						EndIf
						AASetFont FontSL
						If DrawButton(x + 20 * MenuScale, y + 200 * MenuScale, 230 * MenuScale, 25 * MenuScale, "Reload server list", False) Then
							For sc.servers = Each Servers
								multiplayer_list_DeleteServer(sc)
							Next
							SelectServer = -1
							LoadMultiplayerOptions()
							InitMultiplayer(4)
							
							For i = 0 To 10
								For x = 0 To 4
									ServerPages[i]\SERVER_LIST_SORT[x] = -1
								Next
							Next
						EndIf
						If PATRON_COMPILE Then
							AASetFont ConsoleFont
							Color 189, 84, 40
							AAText x+20*MenuScale, y+280*MenuScale, "You have the patreon benefits!"
						EndIf
						AASetFont Font1
					Case 1
						If voice\VoiceInstall Then
							Local prevvolume# = voice\volume
							voice\volume = (SlideBar(x + 20*MenuScale, y+110*MenuScale, 150*MenuScale, voice\volume*100.0, False)/100.0)
							AAText x + 50*MenuScale, y+90*MenuScale, "Volume: "+Int(voice\volume*100)
							Color 0,255,0
							AAText(x + 20 * MenuScale, y + 50 * MenuScale, "Current input: "+FSOUND_Record_GetDriverName(1))
							;if DrawButton(x + (80+(AAStringWidth(snd_in_name(0))*2.1)) * MenuScale, y + 50 * MenuScale, 30*MenuScale, 30*MenuScale, "+") Then PrevMainMenuTab = MainMenuTab : MainMenuTab = 32
						Else
							Color 255,0,0
							AAText x + 20 * MenuScale, y + 50 * MenuScale, "Microphone not connected"
						EndIf

						If DrawButton(x + 20 * MenuScale, y + 150 * MenuScale, 200 * MenuScale, 60 * MenuScale, "CHECK MICROPHONE", False) Then 
							NetworkServer\CheckVoice = (Not NetworkServer\CheckVoice)
							If Not udp_GetStream() Then
								NetworkServer\voicerate = 48000
								;voice_changeparameters(NetworkServer\voicerate, voice_getbytes())
							EndIf
						EndIf
						; ========
						sx# = x + 20 * MenuScale
						sy# = y + 230 * MenuScale
						
						ssx# = x + 20 * MenuScale
						ssy# = y + 230 * MenuScale
						
						DrawFrameBlack(ssx, ssy, 50*MenuScale, 50*MenuScale)
						
						ssx = ssx + 2*MenuScale
						ssy = ssy + 50*MenuScale
						
						Color 241,24,79
						RenderProgressBarY(ssx, ssy, (50*MenuScale)-(2*MenuScale), (50*MenuScale)-(2*MenuScale), 0.2, max(voice\SmoothOffset, 0))
						
						If NetworkServer\CheckVoice Then 
							voice\SmoothOffset = CurveValue(voice_get_offset()-0.74, voice\SmoothOffset, 7)
							DrawImage(mpimg\VoiceME, sx, sy)
						Else
							voice\SmoothOffset = CurveValue(0, voice\SmoothOffset, 7)
							DrawImage(mpimg\VoiceNO, sx, sy)
						EndIf
						; =============
						
						;If NetworkServer\CheckVoice Then
						;	DrawImage(mpimg\VoiceME, x + 20 * MenuScale, y + 230 * MenuScale)
						;Else
						;	DrawImage(mpimg\VoiceNO, x + 20 * MenuScale, y + 230 * MenuScale) 
						;EndIf
						Color 255,255,255
						AAText x + 20 * MenuScale, y + 355 * MenuScale, "Push-to-talk:"
						voice\PushToTalk = Not DrawTick(x + 165 * MenuScale, y + 355 * MenuScale, Not voice\PushToTalk)
						
						If DrawButton(x + 20 * MenuScale, y + 310 * MenuScale, 180 * MenuScale, 30 * MenuScale, "RESTART VOICE", False) Then
							voice_remove()
							voice_init()
							;if Not udp_GetStream() Then
								NetworkServer\voicerate = 48000
							;	voice_changeparameters(NetworkServer\voicerate, voice_getbytes())
							;Else
							;	voice_changeparameters(NetworkServer\voicerate, voice_getbytes())
							;EndIf
							Delay 1000
						EndIf
				End Select
			Case 1
				x = 159 * MenuScale
				y = (306 + 70 + 20 + 28) * MenuScale
				
				width = 606 * MenuScale
				height = 206*MenuScale
				pluslist = (35.0/1.16)*5
				height=height+(pluslist*MenuScale)
				
				chatHeightc# = 0*MenuScale
				scrollbarHeight% = 0
				
				For sc.Servers = Each Servers
					If sc\sType = SELECTED_SERVERS And (Not (sc\ping = 0 And (sc\sType = SERVER_INTERNET Or sc\sType = SERVER_OFFICIAL))) Then
						chatheightc = chatheightc+35*MenuScale
					EndIf
				Next
				
				If chatHeightc <> 0 Then
					scrollbarHeight = (Float(height)/Float(chatHeightc))*height
					If scrollbarHeight>height Then scrollbarHeight = height
					If chatHeightc<height Then chatHeightc = height
					
					Color 200,200,200
					inBar% = MouseOn(x+width-26*MenuScale,y-2*MenuScale,26*MenuScale,height+2*MenuScale)
					;If inBar Then Color 200,200,200
					DrawFrame x+width-26*MenuScale,y-2*MenuScale,26*MenuScale,height+2*MenuScale
					
					
					Color 100,100,100
					inBox% = MouseOn(x+width-23*MenuScale,y+height-scrollbarHeight+(chatScroll*scrollbarHeight/height),20*MenuScale,scrollbarHeight)
					If inBox Then Color 120,120,120
					If chatScrollDragging Then Color 130,130,130
					Rect x+width-23*MenuScale,y+height-scrollbarHeight+(chatScroll*scrollbarHeight/height)+1*MenuScale,20*MenuScale,scrollbarHeight-4*MenuScale,True
					
					If Not MouseDown(1) Then
						chatScrollDragging=False
					ElseIf chatScrollDragging Then
						chatScroll = chatScroll+((ScaledMouseY()-chatMouseMem)*height/scrollbarHeight)
						chatMouseMem = ScaledMouseY()
					EndIf
					
					If (Not chatScrollDragging) Then
						If MouseHit1 Then
							If inBox Then
								chatScrollDragging=True
								chatMouseMem = ScaledMouseY()
							ElseIf inBar Then
								chatScroll = chatScroll+((ScaledMouseY()-(y+height))*chatHeightc/height+(height/2))
								chatScroll = chatScroll/2
							EndIf
						EndIf
					EndIf
					
					
					Local mousescroll# = MouseZSpeed()
					
					If mousescroll > 0 Then 
						chatScroll = chatScroll - 15*MenuScale
					ElseIf mousescroll < 0 Then
						chatScroll = chatScroll + 15*MenuScale
					EndIf

					
					If chatScroll<-chatHeightc+height Then chatScroll = -chatHeightc+height
					If chatScroll>0 Then chatScroll = 0
		
					Local count
					
					Local cscroll# = sqrvalue((-chatHeightc+height)-chatscroll)
					
					Local TempY# = y+35*MenuScale
					For sc.Servers = Each Servers
						sc\spage = 1
						If sc\sType = SELECTED_SERVERS And (Not (sc\ping = 0 And (sc\sType = SERVER_INTERNET Or sc\sType = SERVER_OFFICIAL))) Then
							If TempY >= y+cscroll-35*MenuScale Then
								If TempY <= y + height + cscroll + 70*MenuScale Then
									sc\spage = 0
									;pages[i] = True
								EndIf
							EndIf
							
							TempY = TempY + 35*MenuScale
						EndIf
					Next
				EndIf
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "MULTIPLAYER", True, True)
				AASetFont Font1
				
				x = 159 * MenuScale
				y = 306 * MenuScale
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 335 * MenuScale
				DrawFrame(x, y, width, height+((42*(SELECTED_SERVERS <> SERVER_INTERNET And SELECTED_SERVERS <> SERVER_OFFICIAL))+pluslist)*MenuScale)
				DrawFrame(x + 290* MenuScale, y,2*MenuScale,25*MenuScale)
				DrawFrame(x + 390* MenuScale, y,2*MenuScale,25*MenuScale)
				DrawFrame(x + 490* MenuScale, y,2*MenuScale,25*MenuScale)
				DrawFrame(x, y + 25 * MenuScale, width, 2 * MenuScale)
				
				Local SERVER_SORT_TYPE_LOCAL%
				If DrawBaldButton(x+1*MenuScale, y+2*MenuScale, (290*MenuScale)-1*MenuScale, 28/1.16*MenuScale, " ", False, False) Then SERVER_SORT_TYPE_LOCAL = SERVER_SORT_SERVERS
				If DrawBaldButton(x + 290 * MenuScale, y+2*MenuScale, 99*MenuScale, 28/1.16*MenuScale, " ", False, False) Then SERVER_SORT_TYPE_LOCAL = SERVER_SORT_PLAYERS
				If DrawBaldButton(x + 391 * MenuScale, y+2*MenuScale, 99*MenuScale, 28/1.16*MenuScale, " ", False, False) Then SERVER_SORT_TYPE_LOCAL = SERVER_SORT_MAP
				If DrawBaldButton(x + 491 * MenuScale, y+2*MenuScale, 86*MenuScale, 28/1.16*MenuScale, " ", False, False) Then SERVER_SORT_TYPE_LOCAL = SERVER_SORT_PING
				
				If ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] >= 0 Then
					Select ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE
						Case SERVER_SORT_SERVERS
							DrawImage(mpimg\arrowimg[ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE]], x + 260 * MenuScale, y + 8 * MenuScale)
						Case SERVER_SORT_PLAYERS
							DrawImage(mpimg\arrowimg[ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE]], x + 370 * MenuScale, y + 8 * MenuScale)
						Case SERVER_SORT_MAP
							DrawImage(mpimg\arrowimg[ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE]], x + 471 * MenuScale, y + 8 * MenuScale)
						Case SERVER_SORT_PING
							DrawImage(mpimg\arrowimg[ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE]], x + 557 * MenuScale, y + 8 * MenuScale)
					End Select
				EndIf
				AASetFont FontServers
				AAText(x + 30 * MenuScale, y + 8 * MenuScale, "Servers", False)
				AAText(x + 305 * MenuScale, y + 8 * MenuScale, "Players", False)
				AAText(x + 410 * MenuScale, y + 8 * MenuScale, "Map", False)
				AAText(x + 510 * MenuScale, y + 8 * MenuScale, "Ping", False)
				AASetFont Font1
				
				For i = 0 To MAX_SERVER_LIST_PAGES-1
					If pages[i] = True Then
						If SELECTED_PAGE = i Then
							DrawButton(x + (20+(35*(i+1))) * MenuScale,  y + (260+pluslist-40) * MenuScale, 20*MenuScale, 20*MenuScale, i+1,False, False, True, -1, SELECTED_SERVERS)
						Else
							If DrawButton(x + (20+(35*(i+1))) * MenuScale,  y + (260+pluslist-40) * MenuScale, 20*MenuScale, 20*MenuScale,i+1,False, False, True, -1, -1, -1) Then 
								SELECTED_PAGE = i
								MouseHit1 = False
							EndIf
						EndIf
						DrawFrame(x + 20*MenuScale,  y + (260+pluslist-10) * MenuScale, width-40*MenuScale, 2)
					EndIf
				Next
				Local serverX, cc = -5*MenuScale
				
				count = 0
				DisableRedirectAccess = True
				For sc.Servers = Each Servers
					If sc\sType = SELECTED_SERVERS And sc\spage = SELECTED_PAGE And count < MAX_SERVERS_IN_PAGE Then
						serverX = 0
						If sc\voice Then serverx = serverx + 15/1.16
						If (sc\nocheat And sc\secured) Then serverx = serverx + 20/1.5
						
						Select SELECTED_SERVERS
							Case SERVER_INTERNET,SERVER_OFFICIAL,SERVER_LOCAL
								If sc\ping <> 0 Then
									count = count + 1
									cc = cc+35/1.16
									prevsfxvol# = SFXVolume
									If Not((Not ConnectMenu) And (Not PasswordMenu) And (Not ServerMenuOpen) And (Not AddServerMenu)) Then SFXVolume = 0.0
										
									If DrawButton(x, y + (cc-0) * MenuScale, width, 35/1.16 * MenuScale, "", False, False, True, Handle(sc)) Then
										If (Not ConnectMenu) And (Not PasswordMenu) And (Not ServerMenuOpen) And (Not AddServerMenu) Then
											SelectServer = Handle(sc)
											If DoubleClick Then
												ServerMenuOpen = Not ServerMenuOpen
												DoubleClick = False
												multiplayer_list_UpdateServer(sc, 3000, 2)
											Else
												multiplayer_list_UpdateServer(sc, 3000, 3)
											EndIf
										EndIf
									EndIf
									
									SFXVolume = prevsfxvol
									Select sc\State
										Case "Started"
											Color 255,255,0
										Case "Password"
											Color 255,0,0
										Case "In lobby"
											Color 0,255,0
										Case "Offline"
											Color 255,255,255
										Default
											Color 255,255,255
									End Select
									Oval(x + 15 * MenuScale, y + (cc+16/1.3) * MenuScale, 8/1.16*MenuScale,8/1.16*MenuScale)
									Color 255,255,255
									For i = 1 To 1
										;Oval(x + (15+((i-1)*10)) * MenuScale, y + (cc+30) * MenuScale, 7*MenuScale,7*MenuScale)
									Next
									If sc\nocheat And sc\secured Then 
										DrawImage mpimg\list_nocheat, x + 35/1.16 * MenuScale, y + (cc+15/1.3) * MenuScale
										If MouseOn(x + 35/1.16 * MenuScale, y + (cc+15/1.3) * MenuScale,ImageWidth(mpimg\list_nocheat),ImageHeight(mpimg\list_nocheat)) Then
											AASetFont FontSL
											DrawTextRect(x + 35/1.16 * MenuScale, y + (cc-7/1.3) * MenuScale,85*MenuScale,20*MenuScale, "Protected")
											AASetFont Font1
										EndIf
									EndIf
									
									If sc\voice Then
										DrawImage mpimg\list_voice, x + (20+serverx) * MenuScale, y + (cc+15/1.3) * MenuScale
										If MouseOn(x + (20+serverx) * MenuScale, y + (cc+15/1.3) * MenuScale,ImageWidth(mpimg\list_voice),ImageHeight(mpimg\list_voice)) Then
											AASetFont FontSL
											DrawTextRect(x + (20+serverx) * MenuScale, y + (cc-7/1.3) * MenuScale,85*MenuScale,20*MenuScale, "Voice chat")
											AASetFont Font1
										EndIf
									EndIf
									If MouseOn(x+15*MenuScale,y+(cc+16/1.3)*MenuScale,9/1.16*MenuScale,9/1.16*MenuScale) Then
										AASetFont FontSL
										DrawTextRect(x-15*MenuScale,y+(cc-8*1.3)*MenuScale,80*MenuScale,20*MenuScale, sc\state)
										
										AASetFont Font1
									EndIf
									Color 255,255,255
									AASetFont FontServers
									FormatText(x + (38+serverx) * MenuScale, y + (cc+15/1.3) * MenuScale, sc\Servername)
									AASetFont FontServers
									AAText(x + 300 * MenuScale, y + (cc+15/1.3) * MenuScale, sc\players)
									AAText(x + 400 * MenuScale, y + (cc+15/1.3) * MenuScale, sc\map)
									pp$ = Str(sc\ping)
									If sc\ping = 0 Then pp = "-"
									AAText(x + 500 * MenuScale, y + (cc+15/1.3) * MenuScale, pp)
								EndIf
							Case SERVER_HISTORY,SERVER_FAVOURITES
								count = count + 1
								cc = cc+35/1.16
								prevsfxvol# = SFXVolume
								If Not((Not ConnectMenu) And (Not PasswordMenu) And (Not ServerMenuOpen) And (Not AddServerMenu)) Then SFXVolume = 0.0
									
								If DrawButton(x, y + (cc-0) * MenuScale, width, 35/1.16 * MenuScale, "", False, False, True, Handle(sc)) Then
									If (Not ConnectMenu) And (Not PasswordMenu) And (Not ServerMenuOpen) And (Not AddServerMenu) Then
										SelectServer = Handle(sc)
										If DoubleClick Then 
											ServerMenuOpen = Not ServerMenuOpen
											DoubleClick = False
											multiplayer_list_UpdateServer(sc, 3000, 2)
										Else
											multiplayer_list_UpdateServer(sc, 3000, 3)
										EndIf
									EndIf
								EndIf
								
								SFXVolume = prevsfxvol
								Select sc\State
									Case "Started"
										Color 255,255,0
									Case "Password"
										Color 255,0,0
									Case "In lobby"
										Color 0,255,0
									Case "Offline"
										Color 255,255,255
									Default
										Color 255,255,255
								End Select
								Oval(x + 15 * MenuScale, y + (cc+16/1.3) * MenuScale, 8/1.16*MenuScale,8/1.16*MenuScale)
								Color 255,255,255
								For i = 1 To 1
									;Oval(x + (15+((i-1)*10)) * MenuScale, y + (cc+30) * MenuScale, 7*MenuScale,7*MenuScale)
								Next
								If sc\nocheat And sc\secured Then 
									DrawImage mpimg\list_nocheat, x + 35/1.16 * MenuScale, y + (cc+15/1.3) * MenuScale
									If MouseOn(x + 35/1.16 * MenuScale, y + (cc+15/1.3) * MenuScale,ImageWidth(mpimg\list_nocheat),ImageHeight(mpimg\list_nocheat)) Then
										AASetFont FontSL
										DrawTextRect(x + 35/1.16 * MenuScale, y + (cc-7/1.3) * MenuScale,85*MenuScale,20*MenuScale, "Protected")
										AASetFont Font1
									EndIf
								EndIf
								
								If sc\voice Then
									DrawImage mpimg\list_voice, x + (20+serverx) * MenuScale, y + (cc+15/1.3) * MenuScale
									If MouseOn(x + (20+serverx) * MenuScale, y + (cc+15/1.3) * MenuScale,ImageWidth(mpimg\list_voice),ImageHeight(mpimg\list_voice)) Then
										AASetFont FontSL
										DrawTextRect(x + (20+serverx) * MenuScale, y + (cc-7/1.3) * MenuScale,85*MenuScale,20*MenuScale, "Voice chat")
										AASetFont Font1
									EndIf
								EndIf
								If MouseOn(x+15*MenuScale,y+(cc+16/1.3)*MenuScale,9/1.16*MenuScale,9/1.16*MenuScale) Then
									AASetFont FontSL
									DrawTextRect(x-15*MenuScale,y+(cc-8*1.16)*MenuScale,80*MenuScale,20*MenuScale, sc\state)
									
									AASetFont Font1
								EndIf
								Color 255,255,255
								AASetFont FontServers
								FormatText(x + (38+serverx) * MenuScale, y + (cc+15/1.3) * MenuScale, sc\Servername)
								AASetFont FontServers
								AAText(x + 300 * MenuScale, y + (cc+15/1.3) * MenuScale, sc\players)
								AAText(x + 400 * MenuScale, y + (cc+15/1.3) * MenuScale, sc\map)
								pp$ = Str(sc\ping)
								If sc\ping = 0 Then pp = "-"
								AAText(x + 500 * MenuScale, y + (cc+15/1.3) * MenuScale, pp)
						End Select
					EndIf
				Next
				DisableRedirectAccess = False
				
				AASetFont Font1
				
				If Not count Then AAText(x + 25, y + 35* MenuScale, "Server list is clear.")
				
				AASetFont FontSL
				Color 255,255,255
				Local ErrorAxis#;, XErrorAxis#

				
				;DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"hostserver")
				
				AASetfont FontSL

				ErrorOffset# = 0
				For er.errors = Each errors
					ErrorOffset = ErrorOffset + (AAStringHeight(er\txt)*GetLineAmount(er\txt, 250*MenuScale,-1))+10*MenuScale
					;if AAStringWidth(er\txt) > 250*MenuScale Then 
					;	if AAStringWidth(er\txt) > XErrorAxis Then XErrorAxis = (AAStringWidth(er\txt)-(250*MenuScale))+50*MenuScale
					;EndIf
				Next
				
				
				For er.errors = Each errors
					If ErrorAxis = 0 Then
						SelectServer = -1
						
						;xs# = ((x+width/2)-100)-XErrorAxis
						;ys# = (y+height/2)-80
						DrawFrame(x+580*MenuScale,y,250*MenuScale,ErrorOffset+4*MenuScale)
					EndIf
				
					AASetfont FontSL
					
					;If er\interval>MilliSecs() Then
					;Color 255,0,0
					Color er\r,er\g,er\b
					RowText(er\txt, x + 580 * MenuScale, (y+5*MenuScale) + ErrorAxis, 250*MenuScale,ErrorOffset+4*MenuScale, 1, 0)
					Color 255,255,255
					ErrorAxis = ErrorAxis + (AAStringHeight(er\txt)*GetLineAmount(er\txt, 250*MenuScale,ErrorOffset+4*MenuScale))+10*MenuScale
					;Else
					;	Delete er
					;EndIf
				Next
				
				AASetFont Font1
				If ErrorAxis > 0 Then DrawButton(x+(580*MenuScale)+250*MenuScale,y, 80*MenuScale,30*MenuScale, "CLOSE",False)
				
				;Local Offset# = DrawOptionsToolText(x, y, width, height, )
				;if DrawButton(
	
				If DrawButton(x + 20 * MenuScale,  y + (300+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "DIRECT CONNECT", FontServers) Then
					multiplayer_MasterServerSet("", "")
					ConnectMenu = (Not ConnectMenu)
					AddServerMenu = False
					PasswordMenu = False
				EndIf
				If DrawButton(x + 20 * MenuScale,  y + (260+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "JOIN SERVER",FontServers) Then
					If SelectServer <> -1 Then
						For sc.servers = Each Servers
							If Handle(sc) = SelectServer Then
								multiplayer_MasterServerSet(sc\IP, sc\Port)
								multiplayer_ConnectTo(sc\IP, sc\Port)
								Exit
							EndIf
						Next
						SelectServer = -1
					EndIf
				EndIf
				If SELECTED_SERVERS = SERVER_FAVOURITES Then
					If DrawButton(x + 20 * MenuScale,  y + (340+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "ADD SERVER",FontServers) Then
						multiplayer_MasterServerSet("", "")
						AddServerMenu = (Not AddServerMenu)
						ConnectMenu = False
						PasswordMenu = False
					EndIf
				EndIf
				If SELECTED_SERVERS <> SERVER_INTERNET And SELECTED_SERVERS <> SERVER_OFFICIAL Then
					If DrawButton(x + 200 * MenuScale,  y + (300+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "DELETE SERVER", FontServers) Then
						If SelectServer <> -1 Then
							multiplayer_list_DeleteServer(Object.Servers(SelectServer))
							SelectServer = -1
						EndIf
					EndIf
					If DrawButton(x + 200 * MenuScale,  y + (340+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "SETTINGS", FontServers) Then
						MainMenuTab = 12
					EndIf
				Else
					If DrawButton(x + 200 * MenuScale,  y + (300+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "SETTINGS", FontServers) Then
						MainMenuTab = 12
					EndIf
				EndIf
				If DrawButton(x + 200 * MenuScale,  y + (260+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale, "REFRESH SERVER", FontServers) Then
					If SelectServer <> -1 Then
						multiplayer_list_UpdateServer(Object.Servers(SelectServer))
					EndIf
				EndIf
				;If MouseOn(x + 380 * MenuScale,  y + (260+pluslist) * MenuScale, 150 * MenuScale, 20 * MenuScale) And OnSliderID = 0 Then DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"hostserver")
				If DrawButton(x + 380 * MenuScale,  y + (260+pluslist) * MenuScale, 170 * MenuScale, 20 * MenuScale, HOST_SERVER_BUTTON_TEXT, FontServers) Then MainMenuTab = 9
				If DrawButton(x + 380 * MenuScale,  y + (300+pluslist) * MenuScale, 170 * MenuScale, 20 * MenuScale, "RENT A SERVER", FontServers) Then ExecFile("https://zap-hosting.com/containmentbreachmp")
				Color 255,255,255
				Color 255,255,255
				AASetFont Font1
				
				If ConnectMenu Then
					xs# = (x+width/2)-100
					ys# = (y+height/2)-80
					DrawFrame(xs, ys, 250*MenuScale,150*MenuScale)
					AAText(xs+20*MenuScale,ys+10*MenuScale,"IP:",False)
					AAText(xs+20*MenuScale,ys+50*MenuScale,"Port:",False)
					multiplayer_MasterServerSet(Left(InputBox(xs+60*MenuScale, ys+10*MenuScale, 130*MenuScale,25*MenuScale,multiplayer_MasterServerGetIP(),54),20), Left(InputBox(xs+70*MenuScale, ys+50*MenuScale, 80*MenuScale,25*MenuScale,multiplayer_MasterServerGetPort(),55),5) )
					If DrawButton(xs+20*MenuScale, ys+100*MenuScale, 80*MenuScale,30*MenuScale, "CONNECT",False,False,True) Then
						multiplayer_ConnectTo(multiplayer_MasterServerGetIP(), multiplayer_MasterServerGetPort())
					EndIf
					If DrawButton(xs+150*MenuScale, ys+100*MenuScale, 80*MenuScale,30*MenuScale, "CLOSE",False) Then ConnectMenu = False
				ElseIf AddServerMenu Then
					xs# = (x+width/2)-100
					ys# = (y+height/2)-80
					DrawFrame(xs, ys, 250*MenuScale,150*MenuScale)
					AAText(xs+20*MenuScale,ys+10*MenuScale,"IP:",False)
					AAText(xs+20*MenuScale,ys+50*MenuScale,"Port:",False)
					multiplayer_MasterServerSet(Left(InputBox(xs+60*MenuScale, ys+10*MenuScale, 130*MenuScale,25*MenuScale,multiplayer_MasterServerGetIP(),54),20), Left(InputBox(xs+70*MenuScale, ys+50*MenuScale, 80*MenuScale,25*MenuScale,multiplayer_MasterServerGetPort(),55),5) )
					If DrawButton(xs+20*MenuScale, ys+100*MenuScale, 80*MenuScale,30*MenuScale, "ADD",False,False,True) Then multiplayer_list_AddServer(multiplayer_MasterServerGetIP(), multiplayer_MasterServerGetPort(), SERVER_FAVOURITES)
					If DrawButton(xs+150*MenuScale, ys+100*MenuScale, 80*MenuScale,30*MenuScale, "CLOSE",False) Then AddServerMenu = False
				Else If PasswordMenu Then
					xs# = (x+width/2)-100
					ys# = (y+height/2)-80
					DrawFrame(xs, ys, 250*MenuScale,100*MenuScale)
					AAText(xs+20*MenuScale,ys+10*MenuScale,"Password",False)
					Password = InputBox(xs+100*MenuScale, ys+10*MenuScale, 130*MenuScale,25*MenuScale,Password,54)
					If DrawButton(xs+20*MenuScale, ys+50*MenuScale, 80*MenuScale,30*MenuScale, "CONNECT",False,False,True) Then multiplayer_ConnectTo(multiplayer_MasterServerGetIP(), multiplayer_MasterServerGetPort(), Password)
					If DrawButton(xs+150*MenuScale, ys+50*MenuScale, 80*MenuScale,30*MenuScale, "CLOSE",False) Then PasswordMenu = False
				EndIf
				Local xoffset# = 160*MenuScale, xoffsetsize# = 115.9
				
				PrevSel = SELECTED_SERVERS

				If DrawButton(xoffset,370*MenuScale,xoffsetsize*MenuScale,30/1.1*MenuScale,"OFFICIAL("+GetServersCount(SERVER_OFFICIAL, True)+")", FontServers,False,True,-1,SERVER_OFFICIAL) Then SELECTED_SERVERS = SERVER_OFFICIAL : SELECTED_PAGE = 0
				xoffset = xoffset+xoffsetsize*MenuScale
				If DrawButton(xoffset,370*MenuScale,xoffsetsize*MenuScale,30/1.1*MenuScale,"COMMUNITY("+GetServersCount(SERVER_INTERNET, True)+")", FontServers,False,True,-1,SERVER_INTERNET) Then SELECTED_SERVERS = SERVER_INTERNET : SELECTED_PAGE = 0
				xoffset = xoffset+xoffsetsize*MenuScale
				If DrawButton(xoffset,370*MenuScale,(xoffsetsize+2)*MenuScale,30/1.1*MenuScale,"HISTORY("+GetServersCount(SERVER_HISTORY, True)+")", FontServers,False,True,-1,SERVER_HISTORY) Then SELECTED_SERVERS = SERVER_HISTORY : SELECTED_PAGE = 0
				xoffset = xoffset+xoffsetsize*MenuScale
				If DrawButton(xoffset+1*MenuScale,370*MenuScale,xoffsetsize*MenuScale,30/1.1*MenuScale,"FAVORITES("+GetServersCount(SERVER_FAVOURITES, True)+")", FontServers,False,True,-1,SERVER_FAVOURITES) Then SELECTED_SERVERS = SERVER_FAVOURITES : SELECTED_PAGE = 0
				xoffset = xoffset+xoffsetsize*MenuScale
				If DrawButton(xoffset,370*MenuScale,(xoffsetsize+1)*MenuScale,30/1.1*MenuScale,"LOCAL("+GetServersCount(SERVER_LOCAL, True)+")", FontServers,False,True,-1,SERVER_LOCAL) Then SELECTED_SERVERS = SERVER_LOCAL : SELECTED_PAGE = 0 : multiplayer_FindLocalServers(50021)
				AASetFont Font1
				
				If PrevSel <> SELECTED_SERVERS Then
					chatheightc = 0
					For sc.Servers = Each Servers
						If sc\sType = SELECTED_SERVERS And (Not (sc\ping = 0 And (sc\sType = SERVER_INTERNET Or sc\sType = SERVER_OFFICIAL))) Then chatheightc = chatheightc+35*MenuScale
					Next
					
					height = 206*MenuScale
					pluslist = (35.0/1.16)*5
					height=height+(pluslist*MenuScale)
					
					chatScroll = -chatHeightc+height
				EndIf
				;Color 0,0,0
				;AASetFont FontSL
				;if MouseOn(160*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale) Then DrawTextRect(160*MenuScale,370*MenuScale,80*MenuScale,20*MenuScale, "Active: "+GetServersCount(SERVER_OFFICIAL, True))
				;if MouseOn(305*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale) Then DrawTextRect(305*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale, "Active: "+GetServersCount(SERVER_HISTORY, True))
				;if MouseOn(450*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale) Then DrawTextRect(450*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale, "Active: "+GetServersCount(SERVER_FAVOURITES, True))
				;if MouseOn(595*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale) Then DrawTextRect(595*MenuScale,370*MenuScale,145*MenuScale,30*MenuScale, "Active: "+GetServersCount(SERVER_LOCAL, True))
				
				;AASetFont Font1
				If SELECTED_SERVERS = SERVER_LOCAL Then
					Color 255,255,255
					AASetFont Font1
					DrawButton(690 * MenuScale,  y + (340+pluslist) * MenuScale, 20 * MenuScale, 20 * MenuScale, "?", False)
					If MouseOn(690 * MenuScale,  y + (340+pluslist) * MenuScale, 20 * MenuScale, 20 * MenuScale) And OnSliderID = 0 Then
						DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"srv")
					EndIf
				EndIf
				If SelectServer <> -1 Then
					sc.servers = Object.servers(SelectServer)
					If MilliSecs() > sc\ums Then
						multiplayer_list_UpdateServer(sc)
						sc\ums = MilliSecs()+2000
					EndIf
					
					Local getdesc% = False
					
					For i = 0 To MAX_DESC_LINES-1
						If sc\desclines[i] <> "" Then getdesc = True : Exit
					Next
					
					If getdesc Then
						y = y + 27*MenuScale
						DisableRedirectAccess = True
						Local yoffsetx# = 20, highestwidth#
						For i = 0 To MAX_DESC_LINES-1
							If sc\desclines[i] <> "" Then
								Local Options$ = FormatText(x+600*MenuScale, y+yoffsetx*MenuScale, sc\desclines[i])
								highest# = Piece(Options, 1)
								If Float(Piece(Options, 2)) > highestwidth And (380*MenuScale)<Float(Piece(Options, 2)) Then highestwidth = Piece(Options,2)
								yoffsetx = yoffsetx+highest+(20*MenuScale)
							EndIf
						Next
						
						If yoffsetx > 400*MenuScale Then
							DrawFrame(x+580*MenuScale,y,(400*MenuScale)+(max(0, (highestwidth+20*MenuScale)-380*MenuScale)),yoffsetx-20*MenuScale)
						Else
							DrawFrame(x+580*MenuScale,y,(400*MenuScale)+(max(0, (highestwidth+20*MenuScale)-380*MenuScale)),400*MenuScale)
						EndIf
						
						yoffsetx = 20
						For i = 0 To MAX_DESC_LINES-1
							If sc\desclines[i] <> "" Then
								highest = FormatText(x+600*MenuScale, y+yoffsetx*MenuScale, sc\desclines[i])
								yoffsetx = yoffsetx+highest+(20*MenuScale)
							EndIf
						Next
						
						If DrawButton(x+(980*MenuScale)+(max(0, (highestwidth+20*MenuScale)-380*MenuScale)), y,40*MenuScale,40*MenuScale, "X",False) Then
						
							For scs.servers = Each Servers
								If scs\pms <> 0 And scs\shostip = sc\shostip And scs\shostip <> 0 And scs\Port = sc\port And scs <> sc And scs\timed = False Then
									For i = 0 To MAX_DESC_LINES-1
										scs\desclines[i] = ""
									Next
								EndIf
							Next
							
							For i = 0 To MAX_DESC_LINES-1
								sc\desclines[i] = ""
							Next
						EndIf
						DisableRedirectAccess = False
					EndIf
					
					If ServerMenuOpen Then
						AASetFont FontSL
						Local sw# = 400*MenuScale
						Local sh# = 240*MenuScale
						sx# = GraphicWidth/2-400*MenuScale
						sy# = GraphicHeight/2-20*MenuScale
						
						pp$ = Str(sc\ping)
						If sc\ping = 0 Then pp = "-"
						
						DrawFrame(sx, sy, sw, sh)
						Color 255,255,255
						FormatText(sx+20*MenuScale, sy+10*MenuScale, "Server: "+sc\Servername)
						AAText(sx+20*MenuScale, sy+30*MenuScale, "Players: "+sc\players)
						AAText(sx+20*MenuScale, sy+50*MenuScale, "Map: "+sc\map)
						AAText(sx+20*MenuScale, sy+70*MenuScale, "Ping: "+pp)
						AAText(sx+20*MenuScale, sy+90*MenuScale, "Version: "+sc\version)
						If sc\weburl = "" Then
							AASetFont FontSL
							AAText(sx+20*MenuScale, sy+110*MenuScale, "Web URL: None")
						Else
							AAText(sx+20*MenuScale, sy+110*MenuScale, "Web URL: ")
							AASetFont FontSL_1
							If DrawClickableText(sx+((30*MenuScale)+AAStringWidth("Web URL: ")), sy+110*MenuScale, sc\weburl, 52, 229, 235) Then ExecFile(sc\weburl)
						EndIf
						AASetFont FontSL
						Rect(sx+20*MenuScale, sy+128*MenuScale, 360*MenuScale, 1*MenuScale)
						DisableRedirectAccess = True
						RowFormatText(sc\description, sx+20*MenuScale, sy+130*MenuScale, sw, sh)
						DisableRedirectAccess = False
						If DrawButton(sx+10*MenuScale, sy+200*MenuScale, 80*MenuScale, 20*MenuScale, "Connect",False) Then
							multiplayer_MasterServerSet(sc\IP, sc\Port)
							multiplayer_ConnectTo(sc\IP, sc\Port)
							ServerMenuOpen = False
						EndIf
						If DrawButton(sx+110*MenuScale, sy+200*MenuScale, 80*MenuScale, 20*MenuScale, "Refresh",False) Then
							multiplayer_list_UpdateServer(sc, 3000, 2)
						EndIf
						If DrawButton(sx+210*MenuScale, sy+200*MenuScale, 80*MenuScale, 20*MenuScale, "Delete",False) Then
							If sc\stype <> SERVER_INTERNET And sc\stype <> SERVER_OFFICIAL Then
								multiplayer_list_DeleteServer(sc)
							EndIf
						EndIf
						
						If DrawButton(sx+310*MenuScale, sy+200*MenuScale, 80*MenuScale, 20*MenuScale, "Close",False) Then
							ServerMenuOpen = False
						EndIf
					EndIf
				Else
					ServerMenuOpen = False
				EndIf
				
				If SERVER_SORT_TYPE_LOCAL <> 0 Then
					ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE = SERVER_SORT_TYPE_LOCAL
					Local ServerArray.TempServers[372], ServerArrayCount% = 0
					For sc.Servers = Each Servers
						If sc\sType = SELECTED_SERVERS Then
						
							ServerArray[ServerArrayCount] = New TempServers
							multiplayer_list_CopyTempServerInfo(ServerArray[ServerArrayCount], sc, True)
							
							ServerArrayCount = ServerArrayCount + 1
							
							multiplayer_list_DeleteServer(sc)
						EndIf
					Next
					
					Local FoundServer%, CreatedServer.Servers
					
					
					For i = 0 To 4
						If i <> ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE Then
							ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[i] = -1
						EndIf
					Next
					
					If ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] = -1 Then ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] = 0
					ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] = Not ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE]
					
					

					Select ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE
					
						Case SERVER_SORT_SERVERS
							For x = ServerArrayCount-1 To 0 Step -1
								If ServerArray[x] <> Null Then
									CreatedServer = New Servers
									multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
									
									Delete ServerArray[x]
								EndIf
							Next
						Case SERVER_SORT_MAP
							If ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] Then
								For i = 0 To ServerArrayCount-1
									For x = 0 To ServerArrayCount-1
										If ServerArray[x] <> Null Then
											FoundServer = True
											For y = 0 To ServerArrayCount-1
												If ServerArray[y] <> Null Then
													If Asc(Left(ServerArray[x]\map, 1)) > Asc(Left(ServerArray[y]\map, 1)) Then
														FoundServer = False
														Exit
													EndIf
												EndIf
											Next
											
											If FoundServer Then
												CreatedServer = New Servers
												multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
												
												Delete ServerArray[x]
											EndIf
										EndIf
									Next
								Next
							Else
								For i = 0 To ServerArrayCount-1
									For x = 0 To ServerArrayCount-1
										If ServerArray[x] <> Null Then
											FoundServer = True
											For y = 0 To ServerArrayCount-1
												If ServerArray[y] <> Null Then
													If Asc(Left(ServerArray[x]\map, 1)) < Asc(Left(ServerArray[y]\map, 1)) Then
														FoundServer = False
														Exit
													EndIf
												EndIf
											Next
											
											If FoundServer Then
												CreatedServer = New Servers
												multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
												
												Delete ServerArray[x]
											EndIf
										EndIf
									Next
								Next
							EndIf
						Case SERVER_SORT_PLAYERS
							If Not ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] Then
								For i = 0 To ServerArrayCount-1
									For x = 0 To ServerArrayCount-1
										If ServerArray[x] <> Null Then
											FoundServer = True
											For y = 0 To ServerArrayCount-1
												If ServerArray[y] <> Null Then
													If ServerArray[x]\playerscount < ServerArray[y]\playerscount Then
														FoundServer = False
														Exit
													EndIf
												EndIf
											Next
											
											If FoundServer Then
												CreatedServer = New Servers
												multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
												
												Delete ServerArray[x]
											EndIf
										EndIf
									Next
								Next
							Else
								For i = 0 To ServerArrayCount-1
									For x = 0 To ServerArrayCount-1
										If ServerArray[x] <> Null Then
											FoundServer = True
											For y = 0 To ServerArrayCount-1
												If ServerArray[y] <> Null Then
													If ServerArray[x]\playerscount > ServerArray[y]\playerscount Then
														FoundServer = False
														Exit
													EndIf
												EndIf
											Next
											
											If FoundServer Then
												CreatedServer = New Servers
												multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
												
												Delete ServerArray[x]
											EndIf
										EndIf
									Next
								Next
							EndIf
						Case SERVER_SORT_PING
							If ServerPages[SELECTED_SERVERS]\SERVER_LIST_SORT[ServerPages[SELECTED_SERVERS]\SERVER_SORT_TYPE] Then
								For i = 0 To ServerArrayCount-1
									For x = 0 To ServerArrayCount-1
										If ServerArray[x] <> Null Then
											FoundServer = True
											For y = 0 To ServerArrayCount-1
												If ServerArray[y] <> Null Then
													If ServerArray[x]\ping > ServerArray[y]\ping And ServerArray[y]\ping <> 0 Then
														FoundServer = False
														Exit
													EndIf
												EndIf
											Next
											
											If FoundServer Then
												CreatedServer = New Servers
												multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
												Delete ServerArray[x]
											EndIf
										EndIf
									Next
								Next
							Else
								For i = 0 To ServerArrayCount-1
									For x = 0 To ServerArrayCount-1
										If ServerArray[x] <> Null Then
											FoundServer = True
											For y = 0 To ServerArrayCount-1
												If ServerArray[y] <> Null Then
													If ServerArray[x]\ping < ServerArray[y]\ping And ServerArray[y]\ping <> 0 Then
														FoundServer = False
														Exit
													EndIf
												EndIf
											Next
											
											If FoundServer Then
												CreatedServer = New Servers
												multiplayer_list_CopyServerTempInfo(CreatedServer, ServerArray[x], True)
												Delete ServerArray[x]
											EndIf
										EndIf
									Next
								Next
							EndIf
					End Select
					
					For i = 0 To ServerArrayCount-1
						If ServerArray[i] <> Null Then Delete ServerArray[i]
					Next
				EndIf
				
				;g_UpdateGUIEditor()
			Case 14
				Local plcount
				For i = 1 To NetworkServer\Maxplayers
					If Player[i] <> Null Then
						If Player[i]\name <> "" Then
							For a = 0 To MAX_PLAYER_LIST_PAGES-1
								If plcount/MAX_PLAYERS_IN_PAGE = a Then 
									Player[i]\currpage = a
									ppages[a] = True
									Exit
								EndIf
							Next
							plcount = plcount + 1
						EndIf
					EndIf
				Next
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale

				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "LOBBY", True, True)
				AASetFont Font1
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = (330-(30*(Not(MilliSecs() < b_br\LobbyTimer)))) * MenuScale
				mwidth = 8*MenuScale
				width = 580 * MenuScale
			
				;DrawFrame(x+width - 10 * MenuScale, y, 180*menuscale, height+60*MenuScale)
				Local LeftStr$ = ""
				If 4-NetworkServer\PlayersCount > 0 And NetworkServer\Breach Then LeftStr = "%w%Requires %g%"+Str(4-NetworkServer\PlayersCount)+" %w%more players to start the game "
				
				If NetworkServer\SteamStream And NetworkServer\Hosted And NetworkServer\CurrentLobby <> 0 Then
					If DrawButton(x + width + 20 * MenuScale, y, 25*MenuScale, 25*MenuScale, " ") Then
						NetworkServer\CurrentLobbyType = Not NetworkServer\CurrentLobbyType
						BS_SteamMatchmaking_SetLobbyType(BS_SteamMatchmaking(), NetworkServer\CurrentLobby, NetworkServer\CurrentLobbyType)
					EndIf
					If NetworkServer\CurrentLobbyType Then
						DrawImage mpimg\lock_img, x + width + 26 * MenuScale, y + 5 * MenuScale
					Else
						DrawImage mpimg\lockr_img, x + width + 26 * MenuScale, y + 5 * MenuScale
					EndIf
					AASetFont FontSL
					If MouseOn(x + width + 20 * MenuScale, y, 25*MenuScale, 25*MenuScale) Then
						If Not NetworkServer\CurrentLobbyType Then
							DrawTextRect(ScaledMouseX(),ScaledMouseY()-20*MenuScale, 150*MenuScale, 20*MenuScale, "Private room")
						Else
							DrawTextRect(ScaledMouseX(),ScaledMouseY()-20*MenuScale, 150*MenuScale, 20*MenuScale, "Friends can join")
						EndIf
					EndIf
					AASetFont Font1
				EndIf
				DrawFrame(x, y, width, height+(120+(30*(LeftStr<>"")))*MenuScale)
				DrawFrame(x + 50* MenuScale, y,2*MenuScale,30*MenuScale)
				DrawFrame(x + 300* MenuScale, y,2*MenuScale,30*MenuScale)
				;DrawFrame(x + 280* MenuScale, y,2*MenuScale,30*MenuScale)
				;DrawFrame(x, y + 40 * MenuScale, width, 2 * MenuScale)
				AAText(x + 20 * MenuScale, y + 5 * MenuScale, "ID", False)
				AAText(x + 65 * MenuScale, y + 5 * MenuScale, "Nickname", False)
				If NetworkServer\Breach = False Then
					AAText(x + 320 * MenuScale, y + 5 * MenuScale, "Ready", False)
					AAText(x + 440 * MenuScale, y + 5 * MenuScale, "Ping", False)
				Else
					AAText(x + 320 * MenuScale, y + 5 * MenuScale, "Ping", False)
					AAText(x + 440 * MenuScale, y + 5 * MenuScale, "", False)
				EndIf
				AASetFont FontSL
				Local playerPos[MAX_PLAYERS]
				Local temps
				MyPlayer\Ready = Ready
				MyPlayer\name = Nickname
				MyPlayer\ping = ServerPing
				For i = 1 To NetworkServer\Maxplayers
					If Player[i] <> Null Then
						If Player[i]\currpage = SELECTED_P_PAGE Then
							If Player[i]\name <> "" Then
								muted$ = ""
								If Player[i]\volume = 0 Then muted = "(Muted)"
								pos = 30*(temps+1)
								DrawFrame(x, y + pos * MenuScale, width, 30 * MenuScale)
								DrawFrame(x + 300* MenuScale, y,2*MenuScale,(60+(30*temps))*MenuScale)
								If NetworkServer\Breach = False Then DrawFrame(x + 420* MenuScale, y,2*MenuScale,(60+(30*temps))*MenuScale)
								If i = NetworkServer\MyID Then Color 500,500,0
								If Player[i]\tag <> "" Then muted = Player[i]\tag
								DisableRedirectAccess = True
								AAText(x + 65 * MenuScale, y + (pos+10) * MenuScale, Player[i]\name)
								DisableRedirectAccess = False
								Color 255, 0, 0
								If Player[i]\tag <> "" Then Color Player[i]\tagr, Player[i]\tagg, Player[i]\tagb
								AAText(x + 230 * MenuScale, y + (pos+10) * MenuScale, muted)
								Color 255,255,255
								DrawFrame(x + 50* MenuScale, y,2*MenuScale,(60+(30*temps))*MenuScale)
								If Player[i]\Ready = "Ready" Then Color 0,255,0 Else Color 255,0,0
								If NetworkServer\Breach = False Then
									AAText(x + 320 * MenuScale, y + (pos+10) * MenuScale, Player[i]\Ready)
									Color 255,255,255
									AAText(x + 440 * MenuScale, y + (pos+10) * MenuScale, Player[i]\Ping)
								Else
									Color 255,255,255
									AAText(x + 320 * MenuScale, y + (pos+10) * MenuScale, Player[i]\Ping)
									AAText(x + 440 * MenuScale, y + (pos+10) * MenuScale, "")
								EndIf
								AAText(x + 20 * MenuScale, y + (pos+10) * MenuScale, i)
								temps=temps+1
								If DrawButton(x + 550 * MenuScale, (y-2) + (pos+10) * MenuScale, 15 * MenuScale, 15 * MenuScale, "+", False, False) Then MainMenuTab = 40 : currPlayer = Player[i]
								AASetFont FontSL
							EndIf
						EndIf
					EndIf
				Next
				For i = 0 To MAX_PLAYER_LIST_PAGES-1
					If ppages[i] = True Then
						If SELECTED_P_PAGE = i Then
							DrawButton(x + (20+(35*(i+1))) * MenuScale,  y + 295 * MenuScale, 20*MenuScale, 20*MenuScale, i+1,False, False, True, -1, -1, SELECTED_P_PAGE)
						Else
							If DrawButton(x + (20+(35*(i+1))) * MenuScale,  y + 295 * MenuScale, 20*MenuScale, 20*MenuScale,i+1,False, False, True, -1, -1, -1) Then SELECTED_P_PAGE = i
						EndIf
						Rect(x + 15 * MenuScale,  y + 320 * MenuScale, width-30, 2)
					EndIf
				Next
				Local xwidth = 20 * MenuScale
				If DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "SETTINGS", False) Then
					MainMenuTab = 21
				EndIf
				If NetworkServer\Breach = False Then
					xwidth = xwidth + 120 * MenuScale
					If DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "READY", False) Then
						If Ready = "Not Ready" Then 
							Ready = "Ready"
						Else
							Ready = "Not Ready"
						EndIf
					EndIf
					xwidth = xwidth + 120 * MenuScale
					If NetworkServer\PlayersCount < 2 Then
						If Not NetworkServer\MainPlayer Then
							DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "LOAD GAME", False,False,True,-1,SELECTED_SERVERS)
							If MouseOn(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale) And OnSliderID = 0 Then
								DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"cant2")
							EndIf
						Else
							If DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "LOAD GAME", False) Then LoadSaveGames() : MainMenuTab = 23
						EndIf
					Else
						DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "LOAD GAME", False,False,True,-1,SELECTED_SERVERS)
						If MouseOn(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale) And OnSliderID = 0 Then
							DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"cant")
						EndIf
					EndIf
				Else
					xwidth = xwidth + 120 * MenuScale
					DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "LOAD GAME", False,False,True,-1,SELECTED_SERVERS)
				EndIf
				If NetworkServer\MainPlayer Then
					xwidth = xwidth + 120 * MenuScale
					Local tempsa = 0
					For p.players = Each players
						If p\Ready = "Ready" Then tempsa = tempsa + 1
					Next
					If tempsa >= NetworkServer\PlayersCount Then
						If DrawButton(x + xwidth, y + 350 * MenuScale, 100*MenuScale, 50*MenuScale, "START GAME", False) Then
							
							If CurrSave = "" Then CurrSave = "untitled"
							SelectedMap = ""
							GameLoad = 1
							MainMenuOpen = False
							LoadEntities()
							MyPlayer\IsLoad = False
							MyPlayer\Pivot = Collider
							MyPlayer\Hitbox = MyHitbox
	
							multiplayer_UpdatePlayers()
							multiplayer_SendServerInformation()
							For p.players = Each players
								p\Timeout = MilliSecs()+180000
							Next
							LoadAllSounds()
							InitNewGame()
							FlushKeys()
							FlushMouse()
							Return
						EndIf
					EndIf
				EndIf

				If MilliSecs() < b_br\LobbyTimer Then
					seconds = ((b_br\LobbyTimer+999-MilliSecs()) / 1000) Mod 60
					minutes = (b_br\LobbyTimer+999-MilliSecs()) / (1000*60) Mod 60
					secstr$ = seconds
					If seconds < 10 Then secstr = "0"+seconds
					Color 255,255,255
					AASetFont Font1
					AAText(x + 20 * MenuScale, y + 420 * MenuScale, "Remaining before the start - "+minutes+":"+secstr)
					FormatText(x + 20 * MenuScale, y + 450 * MenuScale, LeftStr)
				EndIf
				;AAText(x + 20 * MenuScale, y + 420 * MenuScale, "
			Case 40
				If currplayer = Null Then
					MainMenuTab = 14
					Return
				EndIf
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale

				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "PLAYER", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 330 * MenuScale
				
				DrawFrame(x, y, width, height + 60 * MenuScale)
				AASetFont Font1
				
				currplayer\volume = (SlideBar(x + 20*MenuScale, y+50*MenuScale, 150*MenuScale, currplayer\volume*100.0, False)/100.0)
				AAText x + 30*MenuScale, y+30*MenuScale, "Player volume: "+Int(currplayer\volume*100)
				
				;currplayer\amplitude = (SlideBar(x + 20*MenuScale, y+100*MenuScale, 150*MenuScale, currplayer\amplitude*12.5, False)/12.5)
				;AAText x + 30*MenuScale, y+80*MenuScale, "Player volume gain: "+currplayer\amplitude

			Case 9 ; HOST
				;[Block]
				If SelectedDifficulty = difficulties(CUSTOM) Then SelectedDifficulty = difficulties(SAFE)
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, HOST_SERVER_BUTTON_TEXT, True, True)

				AASetFont FontSL
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 360 * MenuScale
				DrawFrame(x, y, width, height+(200-((80*MenuScale)*STEAM_RELEASE))*MenuScale)
				If DrawButton(x + 410 * MenuScale,  y + 15 * MenuScale, 100 * MenuScale, 20 * MenuScale, "CREATE", False) Then
					Steam_API_SetAchievement("AchvMultiplayer")
					timemaxplayers = Max(Min(MAX_PLAYERS, timemaxplayers), 1)
					If timerandomseed = "" Then timerandomseed = SetRandomSeed()
					timeport = Max(Min(65535, timeport), 80)
					multiplayer_HostServer(timeservername, Timeport, timerandomseed$, timeintroenabled, timetickrate, Float(timegravity), timejumpmode, timenocheat, timemaxplayers, timevoice, timepassword$)
				EndIf
				
				If MouseOn(x + 410 * MenuScale, y + 15 * MenuScale, 100*MenuScale,20*MenuScale) And OnSliderID = 0 Then
					DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"p2p")
				EndIf
				
				AAText(x + 20 * MenuScale, y + 20 * MenuScale, "Server name:")
				timeservername = Left(InputBox(x + 145 * MenuScale, y + 15 * MenuScale, 250 * MenuScale, 20 * MenuScale, timeservername, 1),64)

				AAText(x + 20 * MenuScale, y + 60 * MenuScale, "Map seed:")
				timerandomseed = Left(InputBox(x + 120 * MenuScale, y + 55 * MenuScale, 150 * MenuScale, 20 * MenuScale, timerandomseed, 2),18)
				If Not STEAM_RELEASE Then
					AAText(x + 20 * MenuScale, y + 100 * MenuScale, "Password:")
					timepassword = Left(InputBox(x + 120 * MenuScale, y + 95 * MenuScale, 150 * MenuScale, 20 * MenuScale, timepassword, 12),18)
				Else
					y = y - 40 * MenuScale
				EndIf
				
				AAText(x + 20 * MenuScale, y + 140 * MenuScale, "Enable intro sequence:")
				timeintroenabled = DrawTick(x + 240 * MenuScale, y + 140 * MenuScale, timeintroenabled)	
				;If MouseOn(x + 240 * MenuScale, y+ 140 * MenuScale, 20*MenuScale,20*MenuScale) And OnSliderID = 0 Then
				;	DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"bugs")
				;EndIf
				If Not STEAM_RELEASE Then
					Color 255,255,255
					AAText(x + 20 * MenuScale, y + 180 * MenuScale, "Port:")
					Timeport = Left(InputBox(x + 120 * MenuScale, y + 175 * MenuScale, 70 * MenuScale, 20 * MenuScale, Timeport, 3),5)
				Else
					y = y - 40 * MenuScale
				EndIf

				AAText (x + 20 * MenuScale, y + 220 * MenuScale, "Difficulty:")				
				For i = SAFE To 2
					If DrawTick(x + 20 * MenuScale, y + (250+30*i) * MenuScale, (SelectedDifficulty = difficulties(i))) Then SelectedDifficulty = difficulties(i)
					Color(difficulties(i)\r,difficulties(i)\g,difficulties(i)\b)
					AAText(x + 60 * MenuScale, y + (250+30*i) * MenuScale, difficulties(i)\name)
				Next

				AASetFont Font1
				Color 255,255,255
				AAText(x + 20 * MenuScale, y + 340 * MenuScale, "No Cheat:")
			
				timenocheat = DrawTick(x + 120 * MenuScale, y + 340 * MenuScale, timenocheat)
				If MouseOn(x + 120 * MenuScale, y+ 340 * MenuScale, 20*MenuScale,20*MenuScale) And OnSliderID = 0 Then
					DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"NoCheat")
				EndIf
				Color 255,255,255
				AAText(x + 20 * MenuScale, y + 380 * MenuScale, "Voice chat:")
				timevoice = DrawTick(x + 150 * MenuScale, y + 380 * MenuScale, timevoice)
				If MouseOn(x + 150 * MenuScale, y+ 380 * MenuScale, 20*MenuScale,20*MenuScale) And OnSliderID = 0 Then
					DrawOptionsTooltip(x+580*MenuScale,y,400*MenuScale,200*MenuScale,"voice")
				EndIf
				Color 255,255,255
				AAText(x + 20 * MenuScale, y + 420 * MenuScale, "Max players:")
				timemaxplayers = Left(InputBox(x + 150 * MenuScale, y + 415 * MenuScale, 150 * MenuScale, 20 * MenuScale, timemaxplayers, 13),2)
				
				AAText(x + 20 * MenuScale, y + 450 * MenuScale, "Jump mode:")
				timejumpmode = DrawTick(x + 150 * MenuScale, y + 450 * MenuScale, timejumpmode)

				AAText(x + 20 * MenuScale, y + 485 * MenuScale, "Gravity:")
				timegravity = InputBox(x + 120 * MenuScale, y + 480 * MenuScale, 150 * MenuScale, 20 * MenuScale, timegravity, 14)
				
				AAText(x + 20 * MenuScale, y + 515 * MenuScale, "Keep inventory:")
				timekeepinventory = DrawTick(x + 170 * MenuScale, y + 510 * MenuScale, timekeepinventory)
				;[End Block]
			Case 2; load game
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				;height = 300 * MenuScale
				height = 510 * MenuScale
				
				DrawFrame(x, y, width, height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "LOAD GAME", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 296 * MenuScale
				
				;AASetFont Font1	
				
				AASetFont Font2
				
				If CurrLoadGamePage < Ceil(Float(SaveGameAmount)/6.0)-1 And SaveMSG = "" Then 
					If DrawButton(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, ">") Then
						CurrLoadGamePage = CurrLoadGamePage+1
					EndIf
				Else
					DrawFrame(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+555*MenuScale, y + 537.5*MenuScale, ">", True, True)
				EndIf
				If CurrLoadGamePage > 0 And SaveMSG = "" Then
					If DrawButton(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, "<") Then
						CurrLoadGamePage = CurrLoadGamePage-1
					EndIf
				Else
					DrawFrame(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+25*MenuScale, y + 537.5*MenuScale, "<", True, True)
				EndIf
				
				DrawFrame(x+50*MenuScale,y+510*MenuScale,width-100*MenuScale,55*MenuScale)
				
				AAText(x+(width/2.0),y+536*MenuScale,"Page "+Int(Max((CurrLoadGamePage+1),1))+"/"+Int(Max((Int(Ceil(Float(SaveGameAmount)/6.0))),1)),True,True)
				
				AASetFont Font1
				
				If CurrLoadGamePage > Ceil(Float(SaveGameAmount)/6.0)-1 Then
					CurrLoadGamePage = CurrLoadGamePage - 1
				EndIf
				
				If SaveGameAmount = 0 Then
					AAText (x + 20 * MenuScale, y + 20 * MenuScale, "No saved games.")
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					
					For i% = (1+(6*CurrLoadGamePage)) To 6+(6*CurrLoadGamePage)
						If i <= SaveGameAmount Then
							DrawFrame(x,y,540* MenuScale, 70* MenuScale)
							
							If Float(SaveGameVersion(i-1)) < 1.22 Then
								Color 255,0,0
							Else
								Color 255,255,255
							EndIf
							
							AAText(x + 20 * MenuScale, y + 10 * MenuScale, SaveGames(i - 1))
							AAText(x + 20 * MenuScale, y + (10+18) * MenuScale, SaveGameTime(i - 1)) ;y + (10+23) * MenuScale
							AAText(x + 120 * MenuScale, y + (10+18) * MenuScale, SaveGameDate(i - 1))
							AAText(x + 20 * MenuScale, y + (10+36) * MenuScale, "v"+SaveGameVersion(i - 1))
							
							If SaveMSG = "" Then
								If Float(SaveGameVersion(i-1)) < 1.22 Then
									DrawFrame(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
									Color(255, 0, 0)
									AAText(x + 330 * MenuScale, y + 34 * MenuScale, "Load", True, True)
								Else
									If DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False) Then
										StartLoadGame(SavePath + SaveGames(i - 1) + "\", SaveGames(i - 1))
									EndIf
								EndIf
								
								If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Delete", False) Then
									SaveMSG = SaveGames(i - 1)
									DebugLog SaveMSG
									Exit
								EndIf
							Else
								DrawFrame(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								If Float(SaveGameVersion(i-1)) < 1.22 Then
									Color(255, 0, 0)
								Else
									Color(100, 100, 100)
								EndIf
								AAText(x + 330 * MenuScale, y + 34 * MenuScale, "Load", True, True)
								
								DrawFrame(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								Color(100, 100, 100)
								AAText(x + 450 * MenuScale, y + 34 * MenuScale, "Delete", True, True)
							EndIf
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
					
					If SaveMSG <> ""
						x = 740 * MenuScale
						y = 376 * MenuScale
						DrawFrame(x, y, 420 * MenuScale, 200 * MenuScale)
						RowText("Are you sure you want to delete this save? !>!??!?!?!?", x + 20 * MenuScale, y + 15 * MenuScale, 400 * MenuScale, 200 * MenuScale)
						;AAText(x + 20 * MenuScale, y + 15 * MenuScale, "Are you sure you want to delete this save?")
						If DrawButton(x + 50 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Yes", False) Then
							DeleteFile(CurrentDir() + SavePath + SaveMSG + "\save.txt")
							DeleteDir(CurrentDir() + SavePath + SaveMSG)
							SaveMSG = ""
							LoadSaveGames()
						EndIf
						If DrawButton(x + 250 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, "No", False) Then
							SaveMSG = ""
						EndIf
					EndIf
				EndIf
			Case 23 ;load game mp
				;[Block]
				If NetworkServer\PlayersCount > 1 Then 
					MainMenuTab = 14
					Return
				EndIf
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				;height = 300 * MenuScale
				height = 510 * MenuScale
				
				DrawFrame(x, y, width, height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "LOAD GAME", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 296 * MenuScale
				
				;AASetFont Font1	
				
				AASetFont Font2
				
				If CurrLoadGamePage < Ceil(Float(SaveGameAmount)/6.0)-1 And SaveMSG = "" Then 
					If DrawButton(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, ">") Then
						CurrLoadGamePage = CurrLoadGamePage+1
					EndIf
				Else
					DrawFrame(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+555*MenuScale, y + 537.5*MenuScale, ">", True, True)
				EndIf
				If CurrLoadGamePage > 0 And SaveMSG = "" Then
					If DrawButton(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, "<") Then
						CurrLoadGamePage = CurrLoadGamePage-1
					EndIf
				Else
					DrawFrame(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+25*MenuScale, y + 537.5*MenuScale, "<", True, True)
				EndIf
				
				DrawFrame(x+50*MenuScale,y+510*MenuScale,width-100*MenuScale,55*MenuScale)
				
				AAText(x+(width/2.0),y+536*MenuScale,"Page "+Int(Max((CurrLoadGamePage+1),1))+"/"+Int(Max((Int(Ceil(Float(SaveGameAmount)/6.0))),1)),True,True)
				
				AASetFont Font1
				
				If CurrLoadGamePage > Ceil(Float(SaveGameAmount)/6.0)-1 Then
					CurrLoadGamePage = CurrLoadGamePage - 1
				EndIf
				
				If SaveGameAmount = 0 Then
					AAText (x + 20 * MenuScale, y + 20 * MenuScale, "No saved games.")
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					
					For i% = (1+(6*CurrLoadGamePage)) To 6+(6*CurrLoadGamePage)
						If i <= SaveGameAmount Then
							DrawFrame(x,y,540* MenuScale, 70* MenuScale)
							
							If Float(SaveGameVersion(i-1)) < 1.22 Then
								Color 255,0,0
							Else
								Color 255,255,255
							EndIf
							
							AAText(x + 20 * MenuScale, y + 10 * MenuScale, SaveGames(i - 1))
							AAText(x + 20 * MenuScale, y + (10+18) * MenuScale, SaveGameTime(i - 1)) ;y + (10+23) * MenuScale
							AAText(x + 120 * MenuScale, y + (10+18) * MenuScale, SaveGameDate(i - 1))
							AAText(x + 20 * MenuScale, y + (10+36) * MenuScale, SaveGameSeed(i - 1))
							
							If SaveMSG = "" Then
								If Float(SaveGameVersion(i-1)) < 1.22 Or SaveGameSeed(i - 1) = "" Then
									DrawFrame(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
									Color(255, 0, 0)
									AAText(x + 330 * MenuScale, y + 34 * MenuScale, "Load", True, True)
								Else
									If DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False) Then
										multiplayer_RequestLoad(SavePath + SaveGames(i - 1) + "\", SaveGameSeed(i - 1), SaveGames(i - 1), SaveGameDifficulty(i - 1))
									EndIf
								EndIf
								
								If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Delete", False) Then
									SaveMSG = SaveGames(i - 1)
									DebugLog SaveMSG
									Exit
								EndIf
							Else
								DrawFrame(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								If Float(SaveGameVersion(i-1)) < 1.22 Or SaveGameSeed(i - 1) = "" Then
									Color(255, 0, 0)
								Else
									Color(100, 100, 100)
								EndIf
								AAText(x + 330 * MenuScale, y + 34 * MenuScale, "Load", True, True)
								
								DrawFrame(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								Color(100, 100, 100)
								AAText(x + 450 * MenuScale, y + 34 * MenuScale, "Delete", True, True)
							EndIf
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
					
					If SaveMSG <> ""
						x = 740 * MenuScale
						y = 376 * MenuScale
						DrawFrame(x, y, 420 * MenuScale, 200 * MenuScale)
						RowText("Are you sure you want to delete this save? !>!??!?!?!?", x + 20 * MenuScale, y + 15 * MenuScale, 400 * MenuScale, 200 * MenuScale)
						;AAText(x + 20 * MenuScale, y + 15 * MenuScale, "Are you sure you want to delete this save?")
						If DrawButton(x + 50 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Yes", False) Then
							DeleteFile(CurrentDir() + SavePath + SaveMSG + "\save.txt")
							DeleteDir(CurrentDir() + SavePath + SaveMSG)
							SaveMSG = ""
							LoadSaveGames()
						EndIf
						If DrawButton(x + 250 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, "No", False) Then
							SaveMSG = ""
						EndIf
					EndIf
				EndIf
				
				
				;[End Block]
			Case 3,5,6,7 ;options
				;[Block]
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "OPTIONS", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 60 * MenuScale
				DrawFrame(x, y, width, height)
				
				Color 0,255,0
				If MainMenuTab = 3
					Rect(x+15*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				ElseIf MainMenuTab = 5
					Rect(x+155*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				ElseIf MainMenuTab = 6
					Rect(x+295*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				ElseIf MainMenuTab = 7
					Rect(x+435*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				EndIf
				
				Color 255,255,255
				If DrawButton(x+20*MenuScale,y+15*MenuScale,width/5,height/2, "GRAPHICS", False) Then MainMenuTab = 3
				If DrawButton(x+160*MenuScale,y+15*MenuScale,width/5,height/2, "AUDIO", False) Then MainMenuTab = 5
				If DrawButton(x+300*MenuScale,y+15*MenuScale,width/5,height/2, "CONTROLS", False) Then MainMenuTab = 6
				If DrawButton(x+440*MenuScale,y+15*MenuScale,width/5,height/2, "ADVANCED", False) Then MainMenuTab = 7
				
				AASetFont Font1
				y = y + 70 * MenuScale
				
				If MainMenuTab <> 5
					UserTrackCheck% = 0
					UserTrackCheck2% = 0
				EndIf
				
				Local tx# = x+width
				Local ty# = y
				Local tw# = 400*MenuScale
				Local th# = 150*MenuScale
				
				;DrawOptionsTooltip(tx,ty,tw,th,"")
				
				If MainMenuTab = 3 ;Graphics
					;[Block]
					If SettingsMenu = 0 Then
						;height = 380 * MenuScale
						height = 380 * MenuScale
						DrawFrame(x, y, width, height)

						y=y+10*MenuScale
						
						If DrawButton(x + width - 60*MenuScale, y, 50*MenuScale, 20*MenuScale, ">>>", False) Then SettingsMenu = 1
						
						Color 255,255,255				
						AAText(x + 20 * MenuScale, y, "Enable bump mapping:")	
						BumpEnabled = DrawTick(x + 310 * MenuScale, y + MenuScale, BumpEnabled)
						If MouseOn(x + 310 * MenuScale, y + MenuScale, 20*MenuScale,20*MenuScale) And OnSliderID=0
							;DrawTooltip("Not available in this version")
							DrawOptionsTooltip(tx,ty,tw,th,"bump")
						EndIf
						
						Color 255,255,255				
						AAText(x + 360 * MenuScale, y, "Particles:")	
						RemoveParticles = Not DrawTick(x + 480 * MenuScale, y + MenuScale, Not RemoveParticles)
						
						y=y+30*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "VSync:")
						VerticalSync% = DrawTick(x + 310 * MenuScale, y + MenuScale, VerticalSync%)
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
							DrawOptionsTooltip(tx,ty,tw,th,"vsync")
						EndIf
						
						Color 255,255,255				
						AAText(x + 360 * MenuScale, y, "Decals:")	
						RemoveDecals = Not DrawTick(x + 480 * MenuScale, y + MenuScale, Not RemoveDecals)
						
						y=y+30*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Anti-aliasing:")
						Opt_AntiAlias = DrawTick(x + 310 * MenuScale, y + MenuScale, Opt_AntiAlias%)
						;AAText(x + 20 * MenuScale, y + 15 * MenuScale, "(fullscreen mode only)")
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
							DrawOptionsTooltip(tx,ty,tw,th,"antialias")
						EndIf
						
						Color 255,255,255
						AAText(x + 360*MenuScale, y, "Bullet lines:")
						EnableBullets = DrawTick(x + 480 * MenuScale, y +MenuScale, EnableBullets)
						
						y=y+30*MenuScale ;40
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Enable room lights:")
						EnableRoomLights = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableRoomLights)
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
							DrawOptionsTooltip(tx,ty,tw,th,"roomlights")
						EndIf
						
						MainFOV = Min(Max(SlideBar(x + 360*MenuScale, y+MenuScale, 90*MenuScale, MainFOV, False), 60), 90)
						Color 255,255,255
						AASetFont FONTSL
						AAText(x + 480 * MenuScale, y+6*MenuScale, "FOV: "+Int(MainFOV))
						AASetFont Font1
						
						y=y+30*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Disable gamma update:")
						TurnOnGamma = DrawTick(x + 310 * MenuScale, y + MenuScale, TurnOnGamma)
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
							DrawOptionsTooltip(tx,ty,tw,th,"gu")
						EndIf
						
						y=y+30*MenuScale
						
						;Local prevGamma# = ScreenGamma
						ScreenGamma = (SlideBar(x + 310*MenuScale, y+6*MenuScale, 150*MenuScale, ScreenGamma*50.0)/50.0)
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Screen gamma")
						If MouseOn(x+310*MenuScale,y+6*MenuScale,150*MenuScale+14,20) And OnSliderID=0
							DrawOptionsTooltip(tx,ty,tw,th,"gamma",ScreenGamma)
						EndIf
						
						y=y+50*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Particle amount:")
						ParticleAmount = Slider3(x+310*MenuScale,y+6*MenuScale,150*MenuScale,ParticleAmount,2,"MINIMAL","REDUCED","FULL")
						If (MouseOn(x + 310 * MenuScale, y-6*MenuScale, 150*MenuScale+14, 20) And OnSliderID=0) Or OnSliderID=2
							DrawOptionsTooltip(tx,ty,tw,th,"particleamount",ParticleAmount)
						EndIf
						
						y=y+50*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Texture LOD Bias:")
						TextureDetails = Slider5(x+310*MenuScale,y+6*MenuScale,150*MenuScale,TextureDetails,3,"1.5","0.4","0.0","-0.4","-0.8")
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
						If (MouseOn(x+310*MenuScale,y-6*MenuScale,150*MenuScale+14,20) And OnSliderID=0) Or OnSliderID=3
							DrawOptionsTooltip(tx,ty,tw,th+100*MenuScale,"texquality")
						EndIf
						
						y=y+50*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Cameras quality:")
						CamQuality = Slider3(x+310*MenuScale,y+6*MenuScale,150*MenuScale,CamQuality,56,"Low","Medium","High")
						If (MouseOn(x+310*MenuScale,y-6*MenuScale,150*MenuScale+14,20) And OnSliderID=0) Or OnSliderID=56
							DrawOptionsTooltip(tx,ty,tw,th+100*MenuScale,"cmqu")
						EndIf
						
						y=y+30*MenuScale
						
						Color 255,255,255
						AAText(x + 20 * MenuScale, y, "Save textures in the VRAM:")
						EnableVRam = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableVRam)
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
							DrawOptionsTooltip(tx,ty,tw,th,"vram")
						EndIf

					Else
						
						
						AASetFont FontRes
						
						height = 380 * MenuScale
						DrawFrame(x, y, width, height)
						
						If DrawButton(x + width - 60*MenuScale, y+10*MenuScale, 50*MenuScale, 20*MenuScale, "<<<", False) Then SettingsMenu = 0
						
						AAText x+10*MenuScale, y+10*MenuScale, "Select resolution:"
						y = y + 20*MenuScale
						
						Color 255,255,255
						
						Local lockapply% = False
						Local xres# = x+20*MenuScale, yres# = y+20*MenuScale
						For i = 0 To GFXModes-1
							If (GfxModeExists(GfxModeWidths(i), GfxModeHeights(i), 16) And Bit16ModeSetting) Or (Not Bit16ModeSetting) Then 
								Color 0, 0, 0
								If SelectedGFXMode = i Then 
									Color 255, 255, 255
									Rect(xres - 1, yres - 1, 100*MenuScale, 20*MenuScale, False)
								EndIf
								Color 255, 255, 255
								AAText(xres, yres, (GfxModeWidths(i) + "x" + GfxModeHeights(i)))
								If MouseOn(xres - 1, yres - 1, 100*MenuScale, 20*MenuScale) Then
									Color 255, 255, 255
									Rect(xres - 1, yres - 1, 100*MenuScale, 20*MenuScale, False)
									If MouseHit1 Then 
										SelectedGFXMode = i
										PlaySound_Strict(ButtonSFX)
									EndIf
								EndIf
								
								yres=yres+20*MenuScale
								If yres >= y+160*MenuScale Then 
									yres = y+20*MenuScale
									xres = xres+100*MenuScale
								EndIf
								lockapply = True
							EndIf
						Next
						
						
						Color 255,255,255
						If Not lockapply Then
							AAText(xres - 10*MenuScale, yres, "No graphics modes found.")
							yres = yres+20*MenuScale
							AAText(xres - 10*MenuScale, yres, "Make sure that the 16-bit mode is turned off.")
							yres = yres+20*MenuScale
						Else
							Rect(x + 10*MenuScale, y + 15*MenuScale, xres-x+(100*MenuScale), 150*MenuScale, False)
						EndIf
						
						x = x+18*MenuScale
						y = y+160*MenuScale
						
						AAText(x-8*MenuScale, y + 10*MenuScale, "Select graphics drivers:")
						Rect(x-8*MenuScale, y + 35*MenuScale, 305*MenuScale, (14+((20/1.0)*(CountGfxDrivers()+1))*MenuScale), False)
						
						y = y+47*MenuScale
						
						If SelectedGFXDriverSetting < 0 Or SelectedGFXDriverSetting > CountGfxDrivers() Then SelectedGFXDriverSetting = 1
						
						
						AASetFont FontRes
						Color 255, 255, 255
						If SelectedGFXDriverSetting = 0 Then Rect(x - 1, y - 1, (290/1.0)*MenuScale, (20/1.0)*MenuScale, False)
						;text(x, y, bbGfxDriverName(i))
						Color 255,255,255
						LimitText("Default (Recommended)", x, y, (290/1.0)*MenuScale)
						If MouseOn(x - 1, y - 1, (290/1.0)*MenuScale, (20/1.0)*MenuScale) Then
							Color 255, 255, 255
							Rect(x - 1, y - 1, (290/1.0)*MenuScale, (20/1.0)*MenuScale, False)
							If MouseHit1 Then 
								SelectedGFXDriverSetting = 0
								PlaySound_Strict(ButtonSFX)
							EndIf
						EndIf
						
						y=y+(20/1.0*MenuScale)
						
						For i = 1 To CountGfxDrivers()
							AASetFont FontRes
							Color 255, 255, 255
							If SelectedGFXDriverSetting = i Then
								Color 255, 255, 255
								Rect(x - 1, y - 1, (290/1.0)*MenuScale, (20/1.0)*MenuScale, False)
							EndIf
							;text(x, y, bbGfxDriverName(i))
							Color 255,255,255
							LimitText(GfxDriverName(i), x, y, (290/1.0)*MenuScale)
							If MouseOn(x - 1, y - 1, (290/1.0)*MenuScale, (20/1.0)*MenuScale) Then
								Color 255, 255, 255
								Rect(x - 1, y - 1, (290/1.0)*MenuScale, (20/1.0)*MenuScale, False)
								If MouseHit1 Then 
									SelectedGFXDriverSetting = i
									PlaySound_Strict(ButtonSFX)
								EndIf
							EndIf
							
							y=y+(20/1.0)*MenuScale
						Next
						
						y = y - 120*MenuScale
						AASetFont Font1
						FullscreenSetting = DrawTick(x + 520 *MenuScale, y + 55*MenuScale, FullscreenSetting, BorderlessWindowedSetting)
						BorderlessWindowedSetting = DrawTick(x + 520 *MenuScale, y + 75*MenuScale, BorderlessWindowedSetting)
						lock% = False

						If BorderlessWindowedSetting Or (Not FullscreenSetting) Then lock% = True
						Bit16ModeSetting = DrawTick(x + 520 *MenuScale, y + 95*MenuScale, Bit16ModeSetting,lock%)
						LauncherEnabledSetting = DrawTick(x + 520 *MenuScale, y + 115*MenuScale, LauncherEnabledSetting)
						
						Color 255,255,255
						AAText(x + 310 *MenuScale, y + 75*MenuScale, "Borderless mode")
						If BorderlessWindowedSetting
						   Color 255, 0, 0
						   FullscreenSetting = False
						Else
						  Color 255, 255, 255
						EndIf
						AAText(x + 310 *MenuScale, y + 55*MenuScale, "Fullscreen")
						
						If BorderlessWindowedSetting Or (Not FullscreenSetting)
						   Color 255, 0, 0
						   Bit16ModeSetting = False
						Else
							Color 255, 255, 255
						EndIf
					
						AAText(x + 310 *MenuScale, y + 95*MenuScale, "16 Bit")
						Color 255, 255, 255
						AAText(x + 310 *MenuScale, y + 115*MenuScale, "Use launcher")
						
						Color 255,255,255
						
						If lockapply Then
							If GfxModeWidths(SelectedGFXMode) <> GraphicWidth Or GfxModeHeights(SelectedGFXMode) <> GraphicHeight Or Bit16Mode <> Bit16ModeSetting Or FullscreenSetting <> FullScreen Or LauncherEnabled <> LauncherEnabledSetting Or BorderlessWindowed <> BorderlessWindowedSetting Or SelectedGFXDriverSetting <> SelectedGFXDriver Then
								If DrawButton(x+310*MenuScale, y+155*MenuScale, 90*MenuScale, 20*MenuScale, "APPLY", False) Then ChangeResolution(GfxModeWidths(SelectedGFXMode), GfxModeHeights(SelectedGFXMode))
								Color 255,0,0
								AAText(x, y+155*MenuScale, "After applying the settings,")
								AAText(x, y+175*MenuScale, "the game will be restarted")
							EndIf
						EndIf
					EndIf
					;[End Block]
				ElseIf MainMenuTab = 5 ;Audio
					;[Block]
					height = 220 * MenuScale
					DrawFrame(x, y, width, height)	
					
					y = y + 20*MenuScale
					
					MusicVolume = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, MusicVolume*100.0)/100.0)
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Music volume:")
					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"musicvol",MusicVolume)
					EndIf
					
					y = y + 30*MenuScale
					
					;SFXVolume = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, SFXVolume*100.0)/100.0)
					PrevSFXVolume = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, SFXVolume*100.0)/100.0)
					SFXVolume = PrevSFXVolume
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Sound volume:")
					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"soundvol",PrevSFXVolume)
					EndIf
					;If MouseDown1 Then
					;	If MouseX() >= x And MouseX() <= x + width + 14 And MouseY() >= y And MouseY() <= y + 20 Then
					;		PlayTestSound(True)
					;	Else
					;		PlayTestSound(False)
					;	EndIf
					;Else
					;	PlayTestSound(False)
					;EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText x + 20 * MenuScale, y, "Sound auto-release:"
					EnableSFXRelease = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableSFXRelease)
					If EnableSFXRelease_Prev% <> EnableSFXRelease
						If EnableSFXRelease%
							For snd.Sound = Each Sound
								For i=0 To 31
									If snd\channels[i]<>0 Then
										If ChannelPlaying(snd\channels[i]) Then
											StopChannel(snd\channels[i])
										EndIf
									EndIf
								Next
								If snd\internalHandle<>0 Then
									FreeSound snd\internalHandle
									snd\internalHandle = 0
								EndIf
								snd\channelscount = 0
								snd\releaseTime = 0
							Next
						Else
							For snd.Sound = Each Sound
								If snd\internalHandle = 0 Then snd\internalHandle = LoadSound(snd\name)
							Next
						EndIf
						EnableSFXRelease_Prev% = EnableSFXRelease
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th+220*MenuScale,"sfxautorelease")
					EndIf
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText x + 20 * MenuScale, y, "Enable user tracks:"
					EnableUserTracks = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableUserTracks)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"usertrack")
					EndIf
					
					If EnableUserTracks
						y = y + 30 * MenuScale
						Color 255,255,255
						AAText x + 20 * MenuScale, y, "User track mode:"
						UserTrackMode = DrawTick(x + 310 * MenuScale, y + MenuScale, UserTrackMode)
						If UserTrackMode
							AAText x + 350 * MenuScale, y + MenuScale, "Repeat"
						Else
							AAText x + 350 * MenuScale, y + MenuScale, "Random"
						EndIf
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
							DrawOptionsTooltip(tx,ty,tw,th,"usertrackmode")
						EndIf
						If DrawButton(x + 20 * MenuScale, y + 30 * MenuScale, 190 * MenuScale, 25 * MenuScale, "Scan for User Tracks",False)
							DebugLog "User Tracks Check Started"
							
							UserTrackCheck% = 0
							UserTrackCheck2% = 0
							
							Dir=ReadDir("SFX\Radio\UserTracks\")
							Repeat
								file$=NextFile(Dir)
								If file$="" Then Exit
								If FileType("SFX\Radio\UserTracks\"+file$) = 1 Then
									UserTrackCheck = UserTrackCheck + 1
									test = LoadSound("SFX\Radio\UserTracks\"+file$)
									If test<>0
										UserTrackCheck2 = UserTrackCheck2 + 1
									EndIf
									FreeSound test
								EndIf
							Forever
							CloseDir Dir
							
							DebugLog "User Tracks Check Ended"
						EndIf
						If MouseOn(x+20*MenuScale,y+30*MenuScale,190*MenuScale,25*MenuScale)
							DrawOptionsTooltip(tx,ty,tw,th,"usertrackscan")
						EndIf
						If UserTrackCheck%>0
							AAText x + 20 * MenuScale, y + 100 * MenuScale, "User tracks found ("+UserTrackCheck2+"/"+UserTrackCheck+" successfully loaded)"
						EndIf
					Else
						UserTrackCheck%=0
					EndIf
					;[End Block]
				ElseIf MainMenuTab = 6 ;Controls
					;[Block]
					height = 320 * MenuScale
					DrawFrame(x, y, width, height)	
					
					y = y + 20*MenuScale
					
					MouseSens = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, (MouseSens+0.5)*100.0)/100.0)-0.5
					Color(255, 255, 255)
					AAText(x + 20 * MenuScale, y, "Mouse sensitivity:")
					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"mousesensitivity",MouseSens)
					EndIf
					
					
					
					y = y + 30*MenuScale
					
					MouseSmooth = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, (MouseSmooth)*50.0)/50.0)
					Color(255, 255, 255)
					AAText(x + 20 * MenuScale, y, "Mouse smoothing:")
					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"mousesmoothing",MouseSmooth)
					EndIf
					
					y = y + 40*MenuScale
					
					Color(255, 255, 255)
					AAText(x + 20 * MenuScale, y, "Invert mouse Y-axis:")
					InvertMouse = DrawTick(x + 310 * MenuScale, y + MenuScale, InvertMouse)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"mouseinvert")
					EndIf
					
					Color(255, 255, 255)
					
					y = y + 30*MenuScale
					AAText(x + 20 * MenuScale, y, "Using button (instead of mouse):")
					MouseInteract = Not DrawTick(x + 310 * MenuScale, y + MenuScale, Not MouseInteract)
					y = y + 10*MenuScale
					
					AAText(x + 20 * MenuScale, y + 20 * MenuScale, "Move Forward")
					InputBox(x + 160 * MenuScale, y + 20 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_UP,210)),5)		
					AAText(x + 20 * MenuScale, y + 40 * MenuScale, "Strafe Left")
					InputBox(x + 160 * MenuScale, y + 40 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEFT,210)),3)	
					AAText(x + 20 * MenuScale, y + 60 * MenuScale, "Move Backward")
					InputBox(x + 160 * MenuScale, y + 60 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_DOWN,210)),6)				
					AAText(x + 20 * MenuScale, y + 80 * MenuScale, "Strafe Right")
					InputBox(x + 160 * MenuScale, y + 80 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_RIGHT,210)),4)	
					AAText(x + 20 * MenuScale, y + 100 * MenuScale, "Quick Save")
					InputBox(x + 160 * MenuScale, y + 100 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SAVE,210)),11)
					
					AAText(x + 280 * MenuScale, y + 20 * MenuScale, "Manual Blink")
					InputBox(x + 470 * MenuScale, y + 20 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_BLINK,210)),7)				
					AAText(x + 280 * MenuScale, y + 40 * MenuScale, "Sprint")
					InputBox(x + 470 * MenuScale, y + 40 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SPRINT,210)),8)
					AAText(x + 280 * MenuScale, y + 60 * MenuScale, "Open/Close Inventory")
					InputBox(x + 470 * MenuScale, y + 60 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_INV,210)),9)
					AAText(x + 280 * MenuScale, y + 80 * MenuScale, "Crouch")
					InputBox(x + 470 * MenuScale, y + 80 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CROUCH,210)),10)	
					AAText(x + 280 * MenuScale, y + 100 * MenuScale, "Open/Close Console")
					InputBox(x + 470 * MenuScale, y + 100 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CONSOLE,210)),12)
					
					
					AAText(x + 20 * MenuScale, y + 120 * MenuScale, "Chat")
					InputBox(x + 160 * MenuScale, y + 120 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CHAT,210)),13)
					AAText(x + 280 * MenuScale, y + 120 * MenuScale, "Voice")
					InputBox(x + 470 * MenuScale, y + 120 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_VOICE,210)),14)
					
					AAText(x + 20 * MenuScale, y + 140 * MenuScale, "Jump")
					InputBox(x + 160 * MenuScale, y + 140 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_JUMP,210)),15)
					
					AAText(x + 20 * MenuScale, y + 160 * MenuScale, "Lean L")
					InputBox(x + 160 * MenuScale, y + 160 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEANL,210)),16)
					
					AAText(x + 280 * MenuScale, y + 140 * MenuScale, "Lean R")
					InputBox(x + 470 * MenuScale, y + 140 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEANR,210)),17)
					
					AAText(x + 280 * MenuScale, y + 140 * MenuScale, "Lean R")
					InputBox(x + 470 * MenuScale, y + 140 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEANR,210)),17)
					
					AAText(x + 280 * MenuScale, y + 160 * MenuScale, "Using")
					InputBox(x + 470 * MenuScale, y + 160 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_USING,210)),18)
					
					If MouseOn(x+20*MenuScale,y,width-40*MenuScale,120*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"controls")
					EndIf
					
					For i = 0 To 227
						If KeyHit(i) Then key = i : Exit
					Next
					If key<>0 Then
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
				ElseIf MainMenuTab = 7 ;Advanced
					;[Block]
					height = 350 * MenuScale
					DrawFrame(x, y, width, height)	
					
					y = y + 20*MenuScale
					
					Color 255,255,255				
					AAText(x + 20 * MenuScale, y, "Show HUD:")	
					HUDenabled = DrawTick(x + 310 * MenuScale, y + MenuScale, HUDenabled)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"hud")
					EndIf
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Enable console:")
					CanOpenConsole = DrawTick(x + 310 * MenuScale, y + MenuScale, CanOpenConsole)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"consoleenable")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Open console on error:")
					DrawTick(x + 310 * MenuScale, y + MenuScale, 0)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"consoleerror")
					EndIf
					
					y = y + 50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Achievement popups:")
					AchvMSGenabled% = DrawTick(x + 310 * MenuScale, y + MenuScale, AchvMSGenabled%)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"achpopup")
					EndIf
					
					y = y + 50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Show FPS:")
					ShowFPS% = DrawTick(x + 310 * MenuScale, y + MenuScale, ShowFPS%)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"showfps")
					EndIf

					y = y + 50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Show SCP Viewmodel:")
					ShowSCPViewmodel% = DrawTick(x + 310 * MenuScale, y + MenuScale, ShowSCPViewmodel%)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"showscpviewmodel")
					EndIf

					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Framelimit:")
					Color 255,255,255
					If DrawTick(x + 310 * MenuScale, y, CurrFrameLimit > 0.0) Then
						;CurrFrameLimit# = (SlideBar(x + 150*MenuScale, y+30*MenuScale, 100*MenuScale, CurrFrameLimit#*50.0)/50.0)
						;CurrFrameLimit = Max(CurrFrameLimit, 0.1)
						;Framelimit% = CurrFrameLimit#*100.0
						CurrFrameLimit# = (SlideBar(x + 150*MenuScale, y+30*MenuScale, 100*MenuScale, CurrFrameLimit#*99.0)/99.0)
						CurrFrameLimit# = Max(CurrFrameLimit, 0.01)
						Framelimit% = 19+(CurrFrameLimit*100.0)
						Color 255,255,0
						AAText(x + 25 * MenuScale, y + 25 * MenuScale, Framelimit%+" FPS")
					Else
						CurrFrameLimit# = 0.0
						Framelimit = 0
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"framelimit",Framelimit)
					EndIf
					If MouseOn(x+150*MenuScale,y+30*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"framelimit",Framelimit)
					EndIf
					
					y = y + 50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, "Antialiased text:")
					AATextEnable% = DrawTick(x + 310 * MenuScale, y + MenuScale, AATextEnable%)
					If AATextEnable_Prev% <> AATextEnable
						FreeAllFonts(False)
						LoadAllFonts(True)
						AATextEnable_Prev% = AATextEnable
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"antialiastext")
					EndIf
					;[End Block]
				EndIf
				;[End Block]
			Case 4 ; load map
				;[Block]
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 510 * MenuScale
				
				DrawFrame(x, y, width, height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont Font2
				AAText(x + width / 2, y + height / 2, "LOAD MAP", True, True)
				
				x = 160 * MenuScale
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 350 * MenuScale
				
				AASetFont Font2
				
				tx# = x+width
				ty# = y
				tw# = 400*MenuScale
				th# = 150*MenuScale
				
				If CurrLoadGamePage < Ceil(Float(SavedMapsAmount)/6.0)-1 Then 
					If DrawButton(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, ">") Then
						CurrLoadGamePage = CurrLoadGamePage+1
					EndIf
				Else
					DrawFrame(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+555*MenuScale, y + 537.5*MenuScale, ">", True, True)
				EndIf
				If CurrLoadGamePage > 0 Then
					If DrawButton(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, "<") Then
						CurrLoadGamePage = CurrLoadGamePage-1
					EndIf
				Else
					DrawFrame(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+25*MenuScale, y + 537.5*MenuScale, "<", True, True)
				EndIf
				
				DrawFrame(x+50*MenuScale,y+510*MenuScale,width-100*MenuScale,55*MenuScale)
				
				AAText(x+(width/2.0),y+536*MenuScale,"Page "+Int(Max((CurrLoadGamePage+1),1))+"/"+Int(Max((Int(Ceil(Float(SavedMapsAmount)/6.0))),1)),True,True)
				
				AASetFont Font1
				
				If CurrLoadGamePage > Ceil(Float(SavedMapsAmount)/6.0)-1 Then
					CurrLoadGamePage = CurrLoadGamePage - 1
				EndIf
				
				AASetFont Font1
				
				If SavedMaps(0)="" Then 
					AAText (x + 20 * MenuScale, y + 20 * MenuScale, "No saved maps. Use the Map Creator to create new maps.")
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					For i = (1+(6*CurrLoadGamePage)) To 6+(6*CurrLoadGamePage)
						If i <= SavedMapsAmount Then
							DrawFrame(x,y,540* MenuScale, 70* MenuScale)
							
							AAText(x + 20 * MenuScale, y + 10 * MenuScale, SavedMaps(i - 1))
							AAText(x + 20 * MenuScale, y + (10+27) * MenuScale, SavedMapsAuthor(i - 1))
							
							If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, "Load", False) Then
								SelectedMap=SavedMaps(i - 1)
								MainMenuTab = 20
							EndIf
							If MouseOn(x + 400 * MenuScale, y + 20 * MenuScale, 100*MenuScale,30*MenuScale)
								DrawMapCreatorTooltip(tx,ty,tw,th,SavedMaps(i-1))
							EndIf
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
				EndIf
				;[End Block]
		End Select
		
	End If
	
	Color 255,255,255
	AASetFont ConsoleFont
	AAText 20,GraphicHeight-50,"Multiplayer Mod v"+MULTIPLAYER_VERSION ;testserver
	AAText 20,GraphicHeight-30,"v"+VersionNumber
	
	xoffset = GraphicWidth-34*MenuScale
	
	For WT.WorkshopThread = Each WorkshopThread
		If WT\IsLanguage <> "" Then
			If WT\LanguageImg = 0 Then 
				WT\LanguageImg = LoadImage_Strict(WT\IsLanguage)
				ResizeImage WT\LanguageImg, 60/2.5*MenuScale, 40/2.5*MenuScale
			EndIf
			If MouseOn(xoffset, GraphicHeight-30*MenuScale, 60/2*MenuScale, 40/2.5*MenuScale) Then
				Color 255,0,0
				Rect(xoffset-1*MenuScale, GraphicHeight-31*MenuScale, 61/2.5*MenuScale, 41/2.5*MenuScale)
				If MouseHit1 Then
					public_inqueue(public_OnClickWorkshopLanguage)
					public_update_current(WT\scriptthread)
					public_clear()
				EndIf
			EndIf
			DrawImage WT\LanguageImg, xoffset, GraphicHeight-30*MenuScale
			xoffset = xoffset - 34*MenuScale
		EndIf
	Next
	
	;DrawTiledImageRect(MenuBack, 985 * MenuScale, 860 * MenuScale, 200 * MenuScale, 20 * MenuScale, 1200 * MenuScale, 866 * MenuScale, 300, 20 * MenuScale)
	
	If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
	
	AASetFont Font1
End Function

Function UpdateLauncher()
	Local prevaa = AATextEnable
	AATextEnable = False
	
	MenuScale# = 1.0
	
	Graphics3DExt(LauncherWidth, LauncherHeight, 0, 2)
	;InitExt
	
	SetBuffer BackBuffer()
	
	RealGraphicWidth = GraphicWidth
	RealGraphicHeight = GraphicHeight
	
	Font1 = AALoadFont("GFX\font\cour\Courier New Rus.ttf", 16, 0,0,0)
	AASetFont Font1
	MenuWhite = LoadImage_Strict("GFX\menu\menuwhite.jpg")
	MenuBlack = LoadImage_Strict("GFX\menu\menublack.jpg")	
	MaskImage MenuBlack, 255,255,0
	LauncherIMG = LoadImage_Strict("GFX\menu\launcher.jpg")
	MaskImage LauncherImg, 212, 212, 234
	Local i%	
	
	Local TempFont% = AALoadFont("GFX\font\cour\Courier New Rus.ttf", 13, 0,0,0)
	
	
	For i = 0 To 3
		ArrowIMG(i) = LoadImage_Strict("GFX\menu\arrow.png")
		RotateImage(ArrowIMG(i), 90 * i)
		HandleImage(ArrowIMG(i), 0, 0)
	Next
	
	For i% = 1 To TotalGFXModes
		Local samefound% = False
		For  n% = 0 To TotalGFXModes - 1
			If GfxModeWidths(n) = GfxModeWidth(i) And GfxModeHeights(n) = GfxModeHeight(i) Then samefound = True : Exit
		Next
		If samefound = False Then
			If Not(GfxModeWidth(i) > 8192 Or GfxModeHeight(i) > 8192) Then
				If GraphicWidth = GfxModeWidth(i) And GraphicHeight = GfxModeHeight(i) Then SelectedGFXMode = GFXModes
				GfxModeWidths(GFXModes) = GfxModeWidth(i)
				GfxModeHeights(GFXModes) = GfxModeHeight(i)
				GFXModes=GFXModes+1 
			EndIf
		End If
	Next
	
	BlinkMeterIMG% = LoadImage_Strict("GFX\blinkmeter.jpg")
	;CheckForUpdates()
	
	AppTitle "SCP - Containment Breach Multiplayer Mod Launcher"
	
	disc = LoadImage_Strict("GFX\multiplayer\menu\discord.png")
	patr = LoadImage_Strict("GFX\multiplayer\menu\patreon.png")
	redd = LoadImage_Strict("GFX\multiplayer\menu\reddit.png")
	yt = LoadImage_Strict("GFX\multiplayer\menu\youtube.png")
	st = LoadImage_Strict("GFX\multiplayer\menu\steam.png")
	;zap = LoadImage_Strict("GFX\multiplayer\menu\zap.png")
	ResizeImage disc, 40/1.6, 40/1.6
	ResizeImage patr, 40/1.6, 40/1.6
	ResizeImage redd, 40/1.6, 40/1.6
	ResizeImage yt, 40/1.6, 40/1.6
	ResizeImage st, 40/1.6, 40/1.6
	;ResizeImage zap, 125*1.28, 25*1.28
	
	Discord_API_SetState("In main menu")
	
	Local x% = 40, y = 270-65
	Local mediaoffsetx, mediaoffsety

	Repeat
		;Cls
		Color 0,0,0
		Rect 0,0,LauncherWidth,LauncherHeight,True
		
		MouseHit1 = MouseHit(1)
		
		Color 255, 255, 255
		DrawImage(LauncherIMG, 0, 0)
		
		AAText(20, 240 - 65, "Resolution:")
		
		x% = 40
		y% = 270 - 65
		lockstart = False
		For i = 0 To (GFXModes - 1)
			If (GfxModeExists(GfxModeWidths(i), GfxModeHeights(i), 16) And Bit16Mode) Or (Not Bit16Mode) Then 
				Color 0, 0, 0
				If SelectedGFXMode = i Then Rect(x - 1, y - 1, 100, 20, False)
				AAText(x, y, (GfxModeWidths(i) + "x" + GfxModeHeights(i)))
				If MouseOn(x - 1, y - 1, 100, 20) Then
					Color 100, 100, 100
					Rect(x - 1, y - 1, 100, 20, False)
					If MouseHit1 Then SelectedGFXMode = i
				EndIf
				
				y=y+20
				lockstart = True
				If y >= 250 - 65 + (LauncherHeight - 80 - 260) Then y = 270 - 65 : x=x+100
			EndIf
		Next

		If Not lockstart Then
			Color 0,0,0
			AAText(x - 18, y, "No graphics modes found.")
			y = y+20
			AAText(x - 18, y, "Install the necessary components for the game to work")
			y = y+20
			AAText(x - 18, y, "or make sure that the 16-bit mode is turned off.")
			y = y+20
		EndIf
		
		;-----------------------------------------------------------------
		Color 255, 255, 255
		x = 30
		y = 369
		;AAText(x - 10, y - 30, "Media:")
		
		y = y - 39
		;AAText(x - 10, y + 1, "Graphics:")
		Rect(x - 10, y + 20, 300, 11+((20/1.3846153846153846153846153846154)*(CountGfxDrivers()+1)))
		
		y = y+27
		
		If SelectedGFXDriver < 0 Or SelectedGFXDriver > CountGfxDrivers() Then SelectedGFXDriver = 1
		
		
		AASetFont TempFont
		Color 0, 0, 0
		If SelectedGFXDriver = 0 Then Rect(x - 1, y - 1, 290/1.3846153846153846153846153846154, 20/1.3846153846153846153846153846154, False)
		;text(x, y, bbGfxDriverName(i))
		LimitText("Default (Recommended)", x, y, 290/1.3846153846153846153846153846154)
		If MouseOn(x - 1, y - 1, 290/1.3846153846153846153846153846154, 20/1.3846153846153846153846153846154) Then
			Color 100, 100, 100
			Rect(x - 1, y - 1, 290/1.3846153846153846153846153846154, 20/1.3846153846153846153846153846154, False)
			If MouseHit1 Then SelectedGFXDriver = 0
		EndIf
		
		y=y+20/1.3846153846153846153846153846154
		
		For i = 1 To CountGfxDrivers()
			AASetFont TempFont
			Color 0, 0, 0
			If SelectedGFXDriver = i Then Rect(x - 1, y - 1, 290/1.3846153846153846153846153846154, 20/1.3846153846153846153846153846154, False)
			;text(x, y, bbGfxDriverName(i))
			LimitText(GfxDriverName(i), x, y, 290/1.3846153846153846153846153846154)
			If MouseOn(x - 1, y - 1, 290/1.3846153846153846153846153846154, 20/1.3846153846153846153846153846154) Then
				Color 100, 100, 100
				Rect(x - 1, y - 1, 290/1.3846153846153846153846153846154, 20/1.3846153846153846153846153846154, False)
				If MouseHit1 Then SelectedGFXDriver = i
			EndIf
			
			y=y+20/1.3846153846153846153846153846154
		Next
		y = y + 50
		
		MediaOffsetX = x - 10
		MediaOffsetY = y - 30
		; ============================= Discord
		If MouseOn(MediaOffsetX, MediaOffsetY, 40/1.6, 40/1.6) Then 
			If ImageWidth(disc) <> 42/1.6 Then disc = ReloadImage("GFX\multiplayer\menu\discord.png", disc, 42/1.6, 42/1.6)
			If MouseHit1 Then
				SendStatisticRequest(PLAYER_GO_DISCORD)
				ExecFile("https://discord.com/invite/zSDXQTc")
			EndIf
		Else
			If ImageWidth(disc) <> 40/1.6 Then disc = ReloadImage("GFX\multiplayer\menu\discord.png", disc, 40/1.6, 40/1.6)
		EndIf
		DrawImage(disc, MediaOffsetX, MediaOffsetY)
		; ============================= Patreon
		If Not STEAM_RELEASE Then
			MediaOffsetX = MediaOffsetX + 60/1.8
			If MouseOn(MediaOffsetX, MediaOffsetY, 40/1.6, 40/1.6) Then 
				If ImageWidth(patr) <> 42/1.6 Then patr = ReloadImage("GFX\multiplayer\menu\patreon.png", patr, 42/1.6, 42/1.6)
				If MouseHit1 Then
					SendStatisticRequest(PLAYER_GO_PATREON)
					ExecFile("https://www.patreon.com/scpcbmultiplayermod")
				EndIf
			Else
				If ImageWidth(patr) <> 40/1.6 Then patr = ReloadImage("GFX\multiplayer\menu\patreon.png", patr, 40/1.6, 40/1.6)
			EndIf
			DrawImage(patr, MediaOffsetX, MediaOffsetY)
		EndIf
		; ============================= Reddit
		MediaOffsetX = MediaOffsetX + 60/1.8
		If MouseOn(MediaOffsetX, MediaOffsetY, 40/1.6, 40/1.6) Then 
			If ImageWidth(redd) <> 42/1.6 Then redd = ReloadImage("GFX\multiplayer\menu\reddit.png", redd, 42/1.6, 42/1.6)
			If MouseHit1 Then
				SendStatisticRequest(PLAYER_GO_REDDIT)
				ExecFile("https://www.reddit.com/r/scpcbmultiplayer")
			EndIf
		Else
			If ImageWidth(redd) <> 40/1.6 Then redd = ReloadImage("GFX\multiplayer\menu\reddit.png", redd, 40/1.6, 40/1.6)
		EndIf
		DrawImage(redd, MediaOffsetX, MediaOffsetY)
		; ============================= Youtube
		MediaOffsetX = MediaOffsetX + 60/1.8
		If MouseOn(MediaOffsetX, MediaOffsetY, 40/1.6, 40/1.6) Then 
			If ImageWidth(yt) <> 42/1.6 Then yt = ReloadImage("GFX\multiplayer\menu\youtube.png", yt, 42/1.6, 42/1.6)
			If MouseHit1 Then
				SendStatisticRequest(PLAYER_GO_YOUTUBE)
				ExecFile("https://www.youtube.com/watch?v=1KKxajC2lMw")
			EndIf
		Else
			If ImageWidth(yt) <> 40/1.6 Then yt = ReloadImage("GFX\multiplayer\menu\youtube.png", yt, 40/1.6, 40/1.6)
		EndIf
		DrawImage(yt, MediaOffsetX, MediaOffsetY)
		; ============================= Steam
		MediaOffsetX = MediaOffsetX + 60/1.8
		If MouseOn(MediaOffsetX, MediaOffsetY, 40/1.6, 40/1.6) Then 
			If ImageWidth(st) <> 42/1.6 Then st = ReloadImage("GFX\multiplayer\menu\steam.png", st, 42/1.6, 42/1.6)
			If MouseHit1 Then
				;SendStatisticRequest(PLAYER_GO_YOUTUBE)
				ExecFile("https://store.steampowered.com/app/1782380")
			EndIf
		Else
			If ImageWidth(st) <> 40/1.6 Then st = ReloadImage("GFX\multiplayer\menu\steam.png", st, 40/1.6, 40/1.6)
		EndIf
		DrawImage(st, MediaOffsetX, MediaOffsetY)

		
		AASetFont Font1
		;MediaOffsetY = MediaOffsetY+50
		; ============================= ZAP
		;if not STEAM_RELEASE Then
		;	MediaOffsetX = x - 10
		;	if MouseOn(MediaOffsetX, MediaOffsetY, 125*1.28, 25*1.28) Then 
		;		if ImageWidth(zap) <> 127*1.28 Then zap = ReloadImage("GFX\multiplayer\menu\zap.png", zap, 127*1.28, 26*1.28)
		;		if MouseHit1 Then
		;			ExecFile("https://zap-hosting.com/en/scp-containment-breach-mp-server-hosting/")
		;		EndIf
		;	Else
		;		if ImageWidth(zap) <> 125*1.28 Then zap = ReloadImage("GFX\multiplayer\menu\zap.png", zap, 125*1.28, 25*1.28)
		;	EndIf
		;	DrawImage(zap, MediaOffsetX, MediaOffsetY)
		;EndIf
		
		Fullscreen = DrawTick(40 + 430 - 15, 260 - 55 + 5 - 8, Fullscreen, BorderlessWindowed)
		BorderlessWindowed = DrawTick(40 + 430 - 15, 260 - 55 + 35, BorderlessWindowed)
		lock% = False

		If BorderlessWindowed Or (Not Fullscreen) Then lock% = True
		Bit16Mode = DrawTick(40 + 430 - 15, 260 - 55 + 65 + 8, Bit16Mode,lock%)
		LauncherEnabled = DrawTick(40 + 430 - 15, 260 - 55 + 95 + 8, LauncherEnabled)

		If BorderlessWindowed
 		   Color 255, 0, 0
 		   Fullscreen = False
		Else
  		  Color 255, 255, 255
		EndIf

		AAText(40 + 430 + 15, 262 - 55 + 5 - 8, "Fullscreen")
		Color 255, 255, 255
		AAText(40 + 430 + 15, 262 - 55 + 35 - 8, "Borderless",False,False)
		AAText(40 + 430 + 15, 262 - 55 + 35 + 12, "windowed mode",False,False)

		If BorderlessWindowed Or (Not Fullscreen)
 		   Color 255, 0, 0
 		   Bit16Mode = False
		Else
		    Color 255, 255, 255
		EndIf
	
		AAText(40 + 430 + 15, 262 - 55 + 65 + 8, "16 Bit")
		Color 255, 255, 255
		AAText(40 + 430 + 15, 262 - 55 + 95 + 8, "Use launcher")
		
		If (Not BorderlessWindowed)
			If Fullscreen
				AAText(40 + 320 + 15, 262 - 55 + 140, "Current Resolution: "+(GfxModeWidths(SelectedGFXMode) + "x" + GfxModeHeights(SelectedGFXMode) + "," + (16+(16*(Not Bit16Mode)))))
			Else
				AAText(40 + 320 + 15, 262 - 55 + 140, "Current Resolution: "+(GfxModeWidths(SelectedGFXMode) + "x" + GfxModeHeights(SelectedGFXMode) + ",32"))
			EndIf
		Else
			AAText(40+ 320 + 15, 262 - 55 + 140, "Current Resolution: "+GfxModeWidths(SelectedGFXMode) + "x" + GfxModeHeights(SelectedGFXMode) + ",32")
			If GfxModeWidths(SelectedGFXMode)<G_viewport_width Then
				AAText(40+ 320 + 15, 262 - 55 + 160, "(upscaled to")
				AAText(40+ 320 + 15, 262 - 55 + 180, G_viewport_width + "x" + G_viewport_height + ",32)")
			ElseIf GfxModeWidths(SelectedGFXMode)>G_viewport_width Then
				AAText(40+ 320 + 15, 262 - 55 + 160, "(downscaled to")
				AAText(40+ 320 + 15, 262 - 55 + 180, G_viewport_width + "x" + G_viewport_height + ",32)")
			EndIf
		EndIf
		If Not STEAM_RELEASE Then
			UpdateCheckEnabled = DrawTick(LauncherWidth - 275, LauncherHeight - 50, UpdateCheckEnabled)
			Color 255,255,255
			AAText LauncherWidth-250,LauncherHeight-70,"Check for"
			AAText LauncherWidth-250,LauncherHeight-50,"updates on"
			AAText LauncherWidth-250,LauncherHeight-30,"launch"
		EndIf
		Color 255,255,255
		
		If lockstart Then
			If DrawButton(LauncherWidth - 30 - 90, LauncherHeight - 50 - 55, 100, 30, "LAUNCH", False, False) Then
				SendStatisticRequest(PLAYER_LAUNCH_GAME)
				GraphicWidth = GfxModeWidths(SelectedGFXMode)
				GraphicHeight = GfxModeHeights(SelectedGFXMode)
				RealGraphicWidth = GraphicWidth
				RealGraphicHeight = GraphicHeight
				Exit
			EndIf
		EndIf
		
		If DrawButton(LauncherWidth - 30 - 90, LauncherHeight - 50, 100, 30, "EXIT", False, False) Then
			SendStatisticRequest(PLAYER_EXIT_LAUNCHER)
			CloseGame()
		EndIf
		Flip
		If Not api_IsWindowVisible(SCREEN_HWND) Then api_showWindow(SCREEN_HWND, 1)
		Delay 20
		
		Discord_API_Update()
		
		GraphicWidth = 640
		GraphicHeight = 480
		RealGraphicWidth = GraphicWidth
		RealGraphicHeight = GraphicHeight
	Forever
	
	PutINIValue(OptionFile, "options", "width", GfxModeWidths(SelectedGFXMode))
	PutINIValue(OptionFile, "options", "height", GfxModeHeights(SelectedGFXMode))
	If Fullscreen Then
		PutINIValue(OptionFile, "options", "fullscreen", "true")
	Else
		PutINIValue(OptionFile, "options", "fullscreen", "false")
	EndIf
	If LauncherEnabled Then
		PutINIValue(OptionFile, "launcher", "launcher enabled", "true")
	Else
		PutINIValue(OptionFile, "launcher", "launcher enabled", "false")
	EndIf
	If BorderlessWindowed Then
		PutINIValue(OptionFile, "options", "borderless windowed", "true")
	Else
		PutINIValue(OptionFile, "options", "borderless windowed", "false")
	EndIf
	If Bit16Mode Then
		PutINIValue(OptionFile, "options", "16bit", "true")
	Else
		PutINIValue(OptionFile, "options", "16bit", "false")
	EndIf
	
	
	PutINIValue(OptionFile, "options", "gfx driver new", SelectedGFXDriver)

	If UpdateCheckEnabled Then
		PutINIValue(OptionFile, "options", "check for updates", "true")
	Else
		PutINIValue(OptionFile, "options", "check for updates", "false")
	EndIf
	
	FreeImage redd
	FreeImage disc
	FreeImage patr
	FreeImage yt
	FreeImage st
	;FreeImage zap
	AAFreeFont(Font1)
	AAFreeFont(TempFont)
	AATextEnable = prevaa
End Function


Function DrawTiledImageRect(img%, srcX%, srcY%, srcwidth#, srcheight#, x%, y%, width%, height%)
	
	Local x2% = x
	While x2 < x+width
		If x2 + srcwidth > x + width Then srcwidth = (x + width) - x2
		Local y2% = y
		While y2 < y+height
			DrawImageRect(img, x2, y2, srcX, srcY, srcwidth, Min((y + height) - y2, srcheight))
			y2 = y2 + srcheight
		Wend
		x2 = x2 + srcwidth
	Wend
	
End Function



Type LoadingScreens
	Field imgpath$
	Field img%
	Field ID%
	Field title$
	Field alignx%, aligny%
	Field disablebackground%
	Field txt$[5], txtamount%
End Type

Function InitLoadingScreens(file$)
	LoadingScreenAmount = 0
	For ls.loadingscreens = Each LoadingScreens
		If ls\img <> 0 Then
			FreeImage(ls\img)
		EndIf
		Delete ls
	Next
		
	Local TemporaryString$, i%
	
	Local f = OpenFile(file)
	
	While Not Eof(f)
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString,1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			ls.LoadingScreens = New LoadingScreens
			LoadingScreenAmount=LoadingScreenAmount+1
			ls\ID = LoadingScreenAmount
			
			ls\title = TemporaryString
			ls\imgpath = GetINIString(file, TemporaryString, "image path")
			
			For i = 0 To 4
				ls\txt[i] = GetINIString(file, TemporaryString, "text"+(i+1))
				If ls\txt[i]<> "" Then ls\txtamount=ls\txtamount+1
			Next
			
			ls\disablebackground = GetINIInt(file, TemporaryString, "disablebackground")
			
			Select Lower(GetINIString(file, TemporaryString, "align x"))
				Case "left"
					ls\alignx = -1
				Case "middle", "center"
					ls\alignx = 0
				Case "right" 
					ls\alignx = 1
			End Select 
			
			Select Lower(GetINIString(file, TemporaryString, "align y"))
				Case "top", "up"
					ls\aligny = -1
				Case "middle", "center"
					ls\aligny = 0
				Case "bottom", "down"
					ls\aligny = 1
			End Select 			
			
		EndIf
	Wend
	
	CloseFile f
	
	LoadingScreenText=0
		
	temp = Rand(1,LoadingScreenAmount)
	For ls.loadingscreens = Each LoadingScreens
		If ls\id = temp Then
			If ls\img=0 Then 
				ls\img = LoadImage_Strict("Loadingscreens\"+ls\imgpath)
				ResizeImage(ls\img, ImageWidth(ls\img)*MenuScale, ImageHeight(ls\img)*MenuScale)
			EndIf
			SelectedLoadingScreen = ls 
			Exit
		EndIf
	Next
End Function



Function DrawLoading(percent#, shortloading=False, updatemultiplayergui = False, forceloading%=False)
	
	Local x%, y%
	
	If percent = 0 Then
		CurrentPercent = 0
		LoadingScreenText=0
		
		temp = Rand(1,LoadingScreenAmount)
		For ls.loadingscreens = Each LoadingScreens
			If ls\id = temp Then
				If ls\img=0 Then 
					ls\img = LoadImage_Strict("Loadingscreens\"+ls\imgpath)
					ResizeImage(ls\img, ImageWidth(ls\img)*MenuScale, ImageHeight(ls\img)*MenuScale)
				EndIf
				SelectedLoadingScreen = ls 
				Exit
			EndIf
		Next
	EndIf
	
	If info_Image = 0 Then 
		; ================ Starting clues
		InitInfoClues(CLUES_FILE)
	EndIf
	
	If MainMenuOpen Then GameLoad = 1
	firstloop = True

	If NetworkServer\MenuHTML <> "" And API_SteamHTML = True And MenuBrowser = Null Then MenuBrowser = SteamBrowser_Create(GraphicWidth, GraphicHeight, NetworkServer\MenuHTML)

		
	Local doneframe
	
	Repeat
		UpdateFrame(False)
		CurrentPercent = ToValue(percent, CurrentPercent, 0.5)
		If forceloading Then CurrentPercent = percent
		Steam_Update()
		Discord_API_Update()
		
		If MenuBrowser <> Null Then
			BS_ISteamHTMLSurface_MouseMove BS_SteamHTMLSurface(), MenuBrowser\Id, MouseX(), MouseY()

			If MouseDown(1) Then
				BS_ISteamHTMLSurface_MouseDown BS_SteamHTMLSurface(), MenuBrowser\Id, 0
			Else
				BS_ISteamHTMLSurface_MouseUp BS_SteamHTMLSurface(), MenuBrowser\Id, 0
			EndIf
		EndIf
		;Color 0,0,0
		;Rect 0,0,GraphicWidth,GraphicHeight,True
		;Color 255, 255, 255
		ClsColor 0,0,0
		Cls
		multiplayer_send(M_UPDATE, 0, 0)
		;Cls(True,False)

		If CurrentPercent > 20 Then
			UpdateMusic()
		EndIf
		
		If shortloading = False Then
			If CurrentPercent > (100.0 / SelectedLoadingScreen\txtamount)*(LoadingScreenText+1) Then
				LoadingScreenText=LoadingScreenText+1
			EndIf
		EndIf
		
		If MenuBrowser = Null Then
			If (Not SelectedLoadingScreen\disablebackground) Then
				DrawImage LoadingBack, GraphicWidth/2 - ImageWidth(LoadingBack)/2, GraphicHeight/2 - ImageHeight(LoadingBack)/2
			EndIf	
			
			If SelectedLoadingScreen\alignx = 0 Then
				x = GraphicWidth/2 - ImageWidth(SelectedLoadingScreen\img)/2 
			ElseIf  SelectedLoadingScreen\alignx = 1
				x = GraphicWidth - ImageWidth(SelectedLoadingScreen\img)
			Else
				x = 0
			EndIf
			
			If SelectedLoadingScreen\aligny = 0 Then
				y = GraphicHeight/2 - ImageHeight(SelectedLoadingScreen\img)/2 
			ElseIf  SelectedLoadingScreen\aligny = 1
				y = GraphicHeight - ImageHeight(SelectedLoadingScreen\img)
			Else
				y = 0
			EndIf	
			
			DrawImage SelectedLoadingScreen\img, x, y
			MaskImage SelectedLoadingScreen\img, 0,0,0
		Else	
			DrawBlock SteamBrowser_GetImageHandle(MenuBrowser), 0, 0
		EndIf

		Local width% = 300, height% = 20
		x% = GraphicWidth / 2 - width / 2
		y% = GraphicHeight / 2 + 30 - 100
		
		Color 255,255,255
		
		Rect(x, y, width+4, height, False)
		For  i% = 1 To Int((width - 2) * (CurrentPercent / 100.0) / 10)
			DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
		Next

		If SelectedLoadingScreen\title = "CWM" Then
			
			If Not shortloading Then 
				If firstloop Then 
					If CurrentPercent = 0 Then
						PlaySound_Strict LoadTempSound("SFX\SCP\990\cwm1.cwm")
					ElseIf CurrentPercent >= 99.99
						PlaySound_Strict LoadTempSound("SFX\SCP\990\cwm2.cwm")
					EndIf
				EndIf
			EndIf
			
			AASetFont Font2
			strtemp$ = ""
			temp = Rand(2,9)
			For i = 0 To temp
				strtemp$ = STRTEMP + Chr(Rand(48,122))
			Next
			AAText(GraphicWidth / 2, GraphicHeight / 2 + 80, strtemp, True, True)
			
			If percent = 0 Then 
				If Rand(5)=1 Then
					Select Rand(2)
						Case 1
							SelectedLoadingScreen\txt[0] = "It will happen on " + CurrentDate() + "."
						Case 2
							SelectedLoadingScreen\txt[0] = CurrentTime()
					End Select
				Else
					Select Rand(13)
						Case 1
							SelectedLoadingScreen\txt[0] = "A very fine radio might prove to be useful."
						Case 2
							SelectedLoadingScreen\txt[0] = "ThIS PLaCE WiLL BUrN"
						Case 3
							SelectedLoadingScreen\txt[0] = "You cannot control it."
						Case 4
							SelectedLoadingScreen\txt[0] = "eof9nsd3jue4iwe1fgj"
						Case 5
							SelectedLoadingScreen\txt[0] = "YOU NEED TO TRUST IT"
						Case 6 
							SelectedLoadingScreen\txt[0] = "Look my friend in the eye when you address him, isn't that the way of the gentleman?"
						Case 7
							SelectedLoadingScreen\txt[0] = "???____??_???__????n?"
						Case 8, 9
							SelectedLoadingScreen\txt[0] = "Jorge has been expecting you."
						Case 10
							SelectedLoadingScreen\txt[0] = "???????????"
						Case 11
							SelectedLoadingScreen\txt[0] = "Make her a member of the midnight crew."
						Case 12
							SelectedLoadingScreen\txt[0] = "oncluded that coming here was a mistake. We have to turn back."
						Case 13
							SelectedLoadingScreen\txt[0] = "This alloy contains the essence of my life."
					End Select
				EndIf
			EndIf
			
			strtemp$ = SelectedLoadingScreen\txt[0]
			temp = Int(Len(SelectedLoadingScreen\txt[0])-Rand(5))
			For i = 0 To Rand(10,15);temp
				strtemp$ = Replace(SelectedLoadingScreen\txt[0],Mid(SelectedLoadingScreen\txt[0],Rand(1,Len(strtemp)-1),1),Chr(Rand(130,250)))
			Next		
			AASetFont Font1
			RowText(strtemp, GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True)		
		Else
			AASetFont Font2
			Color 0,0,0
			AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 +80+1, SelectedLoadingScreen\title, True, True)
			Color 255,255,255
			AAText(GraphicWidth / 2, GraphicHeight / 2 +80, SelectedLoadingScreen\title, True, True)
			AASetFont Font1
			RowText(SelectedLoadingScreen\txt[LoadingScreenText], GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True, 1, True)
		EndIf
		Color 0,0,0
		AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 - 100 + 1, "LOADING - " + Int(CurrentPercent) + " %", True, True)
		Color 255,255,255
		AAText(GraphicWidth / 2, GraphicHeight / 2 - 100, "LOADING - " + Int(CurrentPercent) + " %", True, True)
		If percent >= 99.0 And GameLoad And udp_GetStream() Then
			If percent = 99.0 Then
				Local isloadcount
				For pl.players = Each players
					If pl\isload Then isloadcount = isloadcount + 1
				Next
				Color 255,255,255
				AAText(GraphicWidth / 2, GraphicHeight / 2 - 200, "WAITING FOR OTHER PLAYERS ( "+isloadcount+" / "+NetworkServer\PlayersCount+" )", True, True)
				
				Color 255,255,255
				AASetFont Font1
				AAText(20*MenuScale, 20*MenuScale, "Press ESC To exit")
				If KeyHit(1) Then
					DisconnectServer()
					Exit
				EndIf
			ElseIf percent >= 99 And percent <= 99.4 Then ; genius, yes?
				If percent = 99.1 Then
					AAText(GraphicWidth / 2, GraphicHeight / 2 - 200, "RECEIVING DATA.", True, True)
				ElseIf percent = 99.2 Then
					AAText(GraphicWidth / 2, GraphicHeight / 2 - 200, "RECEIVING DATA..", True, True)
				ElseIf percent = 99.3 Then
					AAText(GraphicWidth / 2, GraphicHeight / 2 - 200, "RECEIVING DATA...", True, True)
				EndIf
			EndIf
		EndIf
		If CurrentPercent >= 99.99 Then 
			If firstloop And SelectedLoadingScreen\title <> "CWM" Then PlaySound_Strict LoadTempSound(("SFX\Horror\Horror8.ogg"))
			AAText(GraphicWidth / 2, GraphicHeight - 50, "PRESS ANY KEY TO CONTINUE", True, True)
		Else
			FlushKeys()
			FlushMouse()
		EndIf

		If updatemultiplayergui Then multiplayer_UpdateGUI(False)
		If MainMenuOpen = True And ((GetKey()<>0 Or MouseHit(1))) Then
			GameLoad = 0
		EndIf
		DrawQuickClues()

		UpdateResolution(True, False)

		firstloop = False

		If CurrentPercent < 99.99 And CurrentPercent >= percent-0.01 Then
			gameload = 0
			Exit
		EndIf
		
	Until (GameLoad = 0 And CurrentPercent >= percent-0.05)
End Function


Global KeysClipboard,KeysClipboard2,RepeatClipboard, KeysASC,KeysASC2,KeysSave = 0,KeysSaveNumpad=0
Function rInput$(aString$)


	If Len(aString) = 0 Then StringPick = 0
	
	If Not KeyDown(29) Then
		Local value% = GetKey()
		Local numpadkey$
		
		If value = 0 Then 
			numpadkey = GetNumpadKey()
			value = Int(Piece(numpadkey, 2))
			If value <> 0 Then KeysSaveNumpad = Int(Piece(numpadkey, 1))
		EndIf
	Else
		GetKey()
	EndIf
	
	Local length% = Len(aString$)
	If value = 8 Then
		value = 0
		If length > 0 Then
			If StringPick = Len(aString) Then
				aString = ""
				StringPick = 0
				Return aString
			EndIf
			aString$ = Left(aString, length - 1)
			If SelectedWord = length-1 Then SelectedWord = SelectedWord - 1
		EndIf
		KeysDelete = MilliSecs()+500
	EndIf
	
	If KeyDown(29) And KeyDown(47) Then
		If RepeatClipboard = 1 Then
			If MilliSecs() > KeysClipboard Then
				If MilliSecs() > KeysClipboard2 Then
					If StringPick = Len(aString) Then
						aString = GetClipboardText()
						StringPick = 0
					Else
						aString = aString + GetClipboardText()
					EndIf
					KeysClipboard2 = MilliSecs()+20
					Return aString$
				EndIf
			EndIf
		Else
			If StringPick = Len(aString) Then
				aString = GetClipboardText()
				StringPick = 0
			Else
				aString = aString + GetClipboardText()
			EndIf
			RepeatClipboard = 1
			KeysClipboard = MilliSecs()+500
			Return aString$
		EndIf
	Else
		RepeatClipboard = 0
		KeysClipboard = 0
		KeysClipboard2 = 0
	EndIf
	
	If KeyDown(29) And KeyDown(46) Then
		If StringPick = Len(aString) And Len(astring) > 0 Then SetClipboardText(aString)
	EndIf
	
	If Not KeyDown(29) Then
		If KeysSave <> 0 Then
			If KeyDown(GetHitByKey(KeysSave)) Or KeyDown(KeysSaveNumpad) Then
				If MilliSecs() > KeysASC Then
					aString$ = aString$ + Chr(KeysSave)
					KeysASC = MilliSecs()+20
					Return aString$
				EndIf
			Else
				KeysSaveNumpad = 0
				KeysASC = 0
				KeysSave = 0
			EndIf
		EndIf
	EndIf
	
	If KeyDown(29) And KeyDown(30) Then
		StringPick = Len(aString)
		Return aString
	EndIf
	
	If KeysDelete <> 0 Then
		If KeyDown(14) Then
			If KeysDelete < MilliSecs() Then
				If length > 0 Then
					aString$ = Left(aString, length - 1)
					If SelectedWord = length-1 Then SelectedWord = SelectedWord - 1
					KeysDelete = MilliSecs()+20
				EndIf
				Return aString$
			EndIf
		Else
			KeysDelete = 0
		EndIf
	EndIf
	If value = 13 Or value = 0 Then
		Return aString$
	ElseIf value > 0 And value < 7 Or value > 26 And value < 32 Or value = 9
		Return aString$
	Else
		If StringPick = Len(aString) Then
			aString = ""
			StringPick = 0
		EndIf
		
		aString$ = aString$ + Chr(value)
		KeysSave = value
		KeysASC = MilliSecs()+500
		Return aString$
	End If
End Function

Function GetNumpadKey$()
	For i = 71 To 83
		If KeyHit(i) Then
			Select i
				Case 79: Return i+" "+49
				Case 80: Return i+" "+50
				Case 81: Return i+" "+51
				Case 75: Return i+" "+52
				Case 76: Return i+" "+53
				Case 77: Return i+" "+54
				Case 71: Return i+" "+55
				Case 72: Return i+" "+56
				Case 73: Return i+" "+57
				Case 83: Return i+" "+46
				Case 82: Return i+" "+48
				
				;case 181: Return 47
				;case 55: Return 42
				;case 74: Return 45
				;case 78: Return 43
			End Select
			Exit
		EndIf
	Next
End Function

Function GetHitByKey(i)
	Select i
		;eng a-z
		Case 96: Return 41
		Case 49: Return 2
		Case 50: Return 3
		Case 51: Return 4
		Case 52: Return 5
		Case 53: Return 6
		Case 54: Return 7
		Case 55: Return 8
		Case 56: Return 9
		Case 57: Return 10
		Case 48: Return 11
		Case 45: Return 12
		Case 61: Return 13
		Case 92: Return 43
		Case 113: Return 16
		Case 119: Return 17
		Case 101: Return 18
		Case 114: Return 19
		Case 116: Return 20
		Case 121: Return 21
		Case 117: Return 22
		Case 105: Return 23
		Case 111: Return 24
		Case 112: Return 25
		Case 91: Return 26
		Case 93: Return 27
		Case 97: Return 30
		Case 115: Return 31
		Case 100: Return 32
		Case 102: Return 33
		Case 103: Return 34
		Case 104: Return 35
		Case 106: Return 36
		Case 107: Return 37
		Case 108: Return 38
		Case 59: Return 39
		Case 39: Return 40
		Case 122: Return 44
		Case 120: Return 45
		Case 99: Return 46
		Case 118: Return 47
		Case 98: Return 48
		Case 110: Return 49
		Case 109: Return 50
		Case 44: Return 51
		Case 46: Return 52
		Case 47: Return 53
		Case 126: Return 41
		; eng A-Z
		Case 33: Return 2
		Case 64: Return 3
		Case 35: Return 4
		Case 36: Return 5
		Case 37: Return 6
		Case 94: Return 7
		Case 38: Return 8
		Case 42: Return 9
		Case 40: Return 10
		Case 41: Return 11
		Case 95: Return 12
		Case 43: Return 13
		Case 124: Return 42
		Case 81: Return 16
		Case 87: Return 17
		Case 69: Return 18
		Case 82: Return 19
		Case 84: Return 20
		Case 89: Return 21
		Case 85: Return 22
		Case 73: Return 23
		Case 79: Return 24
		Case 80: Return 25
		Case 123: Return 26
		Case 125: Return 27
		Case 65: Return 30
		Case 83: Return 31
		Case 68: Return 32
		Case 70: Return 33
		Case 71: Return 34
		Case 72: Return 35
		Case 74: Return 36
		Case 75: Return 37
		Case 76: Return 38
		Case 58: Return 39
		Case 34: Return 40
		Case 90: Return 43
		Case 88: Return 44
		Case 67: Return 45
		Case 86: Return 46
		Case 66: Return 47
		Case 78: Return 48
		Case 77: Return 49
		Case 60: Return 50
		Case 62: Return 51
		Case 63: Return 52
		Case 32: Return 53
		;other lang a-z
		Case 184: Return 41
		Case 49: Return 2
		Case 50: Return 3
		Case 51: Return 4
		Case 52: Return 5
		Case 53: Return 6
		Case 54: Return 7
		Case 55: Return 8
		Case 56: Return 9
		Case 57: Return 10
		Case 48: Return 11
		Case 45: Return 12
		Case 61: Return 13
		Case 92: Return 43
		Case 233: Return 16
		Case 246: Return 17
		Case 243: Return 18
		Case 234: Return 19
		Case 229: Return 20
		Case 237: Return 21
		Case 227: Return 22
		Case 248: Return 23
		Case 249: Return 24
		Case 231: Return 25
		Case 245: Return 26
		Case 250: Return 27
		Case 244: Return 30
		Case 251: Return 31
		Case 226: Return 32
		Case 224: Return 33
		Case 239: Return 34
		Case 240: Return 35
		Case 238: Return 36
		Case 235: Return 37
		Case 228: Return 38
		Case 230: Return 39
		Case 253: Return 40
		Case 255: Return 44
		Case 247: Return 45
		Case 241: Return 46
		Case 236: Return 47
		Case 232: Return 48
		Case 242: Return 49
		Case 252: Return 50
		Case 225: Return 51
		Case 254: Return 52
		Case 46: Return 53
		; other lang A-Z
		Case 168: Return 41
		Case 33: Return 2
		Case 34: Return 3
		Case 185: Return 4
		Case 59: Return 5
		Case 37: Return 6
		Case 58: Return 7
		Case 63: Return 8
		Case 42: Return 9
		Case 40: Return 10
		Case 41: Return 11
		Case 95: Return 12
		Case 43: Return 13
		Case 47: Return 42
		Case 201: Return 16
		Case 214: Return 17
		Case 211: Return 18
		Case 202: Return 19
		Case 197: Return 20
		Case 205: Return 21
		Case 195: Return 22
		Case 216: Return 23
		Case 217: Return 24
		Case 199: Return 25
		Case 213: Return 26
		Case 218: Return 27
		Case 212: Return 30
		Case 219: Return 31
		Case 194: Return 32
		Case 192: Return 33
		Case 207: Return 34
		Case 208: Return 35
		Case 206: Return 36
		Case 203: Return 37
		Case 196: Return 38
		Case 198: Return 39
		Case 221: Return 40
		Case 223: Return 43
		Case 215: Return 44
		Case 209: Return 45
		Case 204: Return 46
		Case 200: Return 47
		Case 210: Return 48
		Case 220: Return 49
		Case 193: Return 50
		Case 222: Return 51
		Case 44: Return 52
	End Select
End Function

Function InputBox$(x%, y%, width%, height%, Txt$, ID% = 0, WW% = False, Offseted# = -1)
	;if ID <> SelectedInputBox Then SelectedWord = -1
	;TextBox(x,y,width,height,Txt$)
	Color (255, 255, 255)
	DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x, y, width, height)
	;Rect(x, y, width, height)
	Color (0, 0, 0)

	Local MouseOnBox% = False
	If MouseOn(x, y, width, height) Then
		Color(50, 50, 50)
		MouseOnBox = True
		If MouseHit1 Then 
			SelectedInputBox = ID : FlushKeys
		EndIf
	EndIf
	
	Rect(x + 2, y + 2, width - 4, height - 4)
	Color (255, 255, 255)	
	
	If (Not MouseOnBox) And MouseHit1 And SelectedInputBox = ID Then SelectedInputBox = 0
	If MouseHit1 Then StringPick = 0
	
	
	If SelectedInputBox = ID Then
		Txt = rInput(Txt)
		Color 255,255,255
		If ((Not(StringPick = Len(Txt) And SelectedInputBox = ID)) Or StringPick = 0) And (Offseted=-1 Or AAStringWidth(Txt) <= Offseted) Then
			If Not ww Then
				If StringPick = Len(Txt) Then 
					If (MilliSecs2() Mod 800) < 400 Then Rect (x + width / 2 - (AAStringWidth(Txt)/2), y + height / 2 - 5*MenuScale, 2, 12)
				Else
					If (MilliSecs2() Mod 800) < 400 Then Rect (x + width / 2 + AAStringWidth(Txt) / 2 + 2, y + height / 2 - 5*MenuScale, 2, 12)
				EndIf
			Else
				If (MilliSecs2() Mod 800) < 400 Then Rect ((x + 10*MenuScale) + AAStringWidth(Txt) + 2, y + height / 2 - 5*MenuScale, 2, 12)
			EndIf
		ElseIf ((Not(StringPick = Len(Txt) And SelectedInputBox = ID)) Or StringPick = 0) Then
			If (MilliSecs2() Mod 800) < 400 Then Rect ((x + 10*MenuScale) + AAStringWidth(Right(Txt, Max(0, Offseted/AAStringWidth("W")))) + 2, y + height / 2 - 5*MenuScale, 2, 12)
		EndIf
	EndIf
	
	If StringPick = Len(Txt) And SelectedInputBox = ID Then
		Color 51,144,255
		
		If (Offseted=-1 Or AAStringWidth(Txt) <= Offseted) Then
			If Not ww Then
				Rect((x + width / 2)-(AAStringWidth(Txt)/2), (y + height / 2)-5*MenuScale, AAStringWidth(Txt), AAStringHeight(Txt), True)
			Else
				Rect(x + 10*MenuScale, (y + height / 2)-5*MenuScale, AAStringWidth(Txt), AAStringHeight(Txt), True)
			EndIf
		Else
			Rect(x + 10*MenuScale, (y + height / 2)-5*MenuScale, AAStringWidth(Right(Txt, Max(0, Offseted/AAStringWidth("W")))), AAStringHeight(Txt), True)
		EndIf
	EndIf
	
	Color 255,255,255
	
	If Not ww Then
		AAText(x + width / 2, y + height / 2, Txt, True, True)
	Else
		Local CurrentText$ = Txt
		If Offseted <> -1 Then
			If AAStringWidth(CurrentText) > Offseted Then CurrentText = Right(CurrentText, Max(0, Offseted/AAStringWidth("W")))
		EndIf
		AAText(x + 10*MenuScale, (y + height / 2)-5*MenuScale, CurrentText)
	EndIf
	Return Txt
End Function

Function DrawFrame(x%, y%, width%, height%, xoffset%=0, yoffset%=0)
	If x+width > GraphicWidth Then width = GraphicWidth-x-1
	If y+height > GraphicHeight Then height = GraphicHeight-y-1
	
	;x = Max(x, 0)
	;y = Max(y, 0)
	;if x < 0 then
	;	width = width-x
	;	x = 0
	;endif
	
	Color 255, 255, 255
	DrawTiledImageRect(MenuWhite, xoffset, (y Mod 256), 512, 512, x, y, width, height)
	
	DrawTiledImageRect(MenuBlack, yoffset, (y Mod 256), 512, 512, x+3*MenuScale, y+3*MenuScale, width-6*MenuScale, height-6*MenuScale)
End Function

Function DrawFrameBlack(x%, y%, width%, height%, xoffset%=0, yoffset%=0)
	Color 255, 255, 255
	DrawTiledImageRect(MenuBlack, xoffset, (y Mod 256), 512, 512, x, y, width, height)
End Function

Function DrawClickableText(x%, y%, txt$, r,g,b)
	Local prevR = ColorRed(), prevG = ColorGreen(), prevB = ColorBlue(), clicked = False
	
	If MouseOn(x, y, AAStringWidth(txt), AAStringHeight(txt)) Then 
		Color r,g,b
		If MouseHit1 Then clicked = True
	EndIf
	AAText x, y, txt
	Color prevR, prevG, prevB
	Return clicked
End Function

Function DrawButton%(x%, y%, width%, height%, txt$, bigfont% = True, waitForMouseUp%=False, usingAA%=True,IsAServerButton = -1,IsAServersButton = -1, IsAPageButton = -1, r=-1,g=-1,b=-1,scriptx%=False)
	Local clicked% = False
	DrawFrame (x, y, width, height)
	If MouseOn(x, y, width, height) Then
		Color(30, 30, 30)
		If (MouseHit1 And (Not waitForMouseUp)) Or (MouseUp1 And waitForMouseUp) Then 
			clicked = True
			If ((SelectServer <> IsAServerButton And IsAServerButton <> -1) Or (SELECTED_SERVERS <> IsAServersButton And IsAServersButton <> -1)) And (Not (IsAServerButton <> -1 And ServerMenuOpen = True)) Then PlaySound_Strict(ButtonSFX)
			If IsAServerButton = -1 And IsAServersButton = -1 Then PlaySound_Strict(ButtonSFX)
		EndIf
		If IsAServerButton <> -1 And IsAServerButton = SelectServer Then
			;Color(0, 0,100)
			Rect(x + 4, y + 4, width - 8, height - 8)
		Else If IsAServerButton = -1
			Rect(x + 4, y + 4, width - 8, height - 8)
		EndIf 
	Else
		If IsAServerButton <> -1 Then
			If IsAServerButton = SelectServer Then
				Color(30, 30, 30)
				Rect(x + 4, y + 4, width - 8, height - 8)
			Else
				Color(0, 0, 0)
			EndIf
		Else
			Color(0, 0, 0)
		EndIf
	EndIf
	If IsAPageButton = SELECTED_PAGE Or IsAPageButton = SELECTED_P_PAGE Then 
		;Color(0, 0,100)
		Rect(x + 4, y + 4, width - 8, height - 8)
	EndIf
	If r <> -1 Then 
		Color(r,g,b)
	Else
		Color (255, 255, 255)
	EndIf
	
	If IsAServersButton <> -1 And IsAServersButton = SELECTED_SERVERS Then Color(70, 70, 70)
	If usingAA Then
		If bigfont = 1 Then 
			AASetFont Font2 
		ElseIf bigfont <> 0 Then
			AASetFont bigfont
		Else
			AASetFont Font1
		EndIf
		
		AAText(x + width / 2, y + height / 2, txt, True, True)
	Else
		If bigfont = 1 Then 
			SetFont Font2 
		ElseIf bigfont <> 0 Then
			SetFont bigfont
		Else
			SetFont Font1
		EndIf
		Text(x + width / 2, y + height / 2, txt, True, True)
	EndIf
	If IsAServerButton <> -1 And (ServerMenuOpen = True) Then Return False
	
	If clicked Then 
		Delete Each Errors
		If Not scriptx Then
			If GetScripts() Then
				public_inqueue(public_OnPressUIButton)
				public_addparam(txt, SE_STRING)
				public_addparam(x)
				public_addparam(y)
				public_addparam(width)
				public_addparam(height)
				callback()
			EndIf
			If SE_RETURN_VALUE\StaticIndex Then clicked = False
		EndIf
	EndIf
	
	Return clicked
End Function

Function DrawBaldButton%(x%, y%, width%, height%, txt$, bigfont% = True, waitForMouseUp%=False, usingAA%=True)
	Local clicked% = False
	If MouseOn(x, y, width, height) Then
		Color(30, 30, 30)
		If (MouseHit1 And (Not waitForMouseUp)) Or (MouseUp1 And waitForMouseUp) Then 
			clicked = True
			PlaySound_Strict(ButtonSFX)
		EndIf
		Rect(x + 2, y + 2, width - 2, height - 2)
	Else
		Color(0, 0, 0)
	EndIf
	Color (255, 255, 255)
	If usingAA Then
		If bigfont Then AASetFont Font2 Else AASetFont Font1
		AAText(x + width / 2, y + height / 2, txt, True, True)
	Else
		If bigfont Then SetFont Font2 Else SetFont Font1
		Text(x + width / 2, y + height / 2, txt, True, True)
	EndIf
	Return clicked
End Function

Function DrawButton2%(x%, y%, width%, height%, txt$, bigfont% = True)
	Local clicked% = False
	
	DrawFrame (x, y, width, height)
	Local hit% = MouseHit(1)
	If MouseOn(x, y, width, height) Then
		Color(30, 30, 30)
		If hit Then clicked = True : PlaySound_Strict(ButtonSFX)
		Rect(x + 4, y + 4, width - 8, height - 8)	
	Else
		Color(0, 0, 0)
	EndIf
	
	Color (255, 255, 255)
	If bigfont Then SetFont Font2 Else SetFont Font1
	Text(x + width / 2, y + height / 2, txt, True, True)
	
	Return clicked
End Function

Function DrawTick%(x%, y%, selected%, locked% = False)
	Local width% = 20 * MenuScale, height% = 20 * MenuScale
	
	Color (255, 255, 255)
	DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x, y, width, height)
	;Rect(x, y, width, height)
	
	Local Highlight% = MouseOn(x, y, width, height) And (Not locked)
	
	If Highlight Then
		Color(50, 50, 50)
		If MouseHit1 Then selected = (Not selected) : PlaySound_Strict (ButtonSFX)
	Else
		Color(0, 0, 0)		
	End If
	
	Rect(x + 2, y + 2, width - 4, height - 4)
	
	If selected Then
		If Highlight Then
			Color 255,255,255
		Else
			Color 200,200,200
		EndIf
		DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x + 4, y + 4, width - 8, height - 8)
		;Rect(x + 4, y + 4, width - 8, height - 8)
	EndIf
	
	Color 255, 255, 255
	
	Return selected
End Function

Function SlideBar#(x%, y%, width%, value#, showtext% = True)
	
	If MouseDown1 And OnSliderID=0 Then
		If ScaledMouseX() >= x And ScaledMouseX() <= x + width + 14 And ScaledMouseY() >= y And ScaledMouseY() <= y + 20 Then
			value = Min(Max((ScaledMouseX() - x) * 100 / width, 0), 100)
		EndIf
	EndIf
	
	Color 255,255,255
	Rect(x, y, width + 14, 20,False)
	
	DrawImage(BlinkMeterIMG, x + width * value / 100.0 +3, y+3)
	If ShowText Then
		Color 170,170,170 
		AAText (x - 50 * MenuScale, y + 4*MenuScale, "LOW")					
		AAText (x + width + 38 * MenuScale, y+4*MenuScale, "HIGH")
	EndIf
	
	Return value
	
End Function

Function RowText(A$, X#, Y#, W#, H#, align% = 0, Leading#=1, Shadow%=False)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=SMALLEST_POWER_TWO
	
	Local LinesShown = 0
	Local Height = AAStringHeight(A$) + Leading
	Local b$
	
	Local PrevR%, PrevG%, PrevB%
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (AAStringWidth (b$ + temp$) > W) And (AAStringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If AAStringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			If align Then
				If Shadow Then
					PrevR = ColorRed()
					PrevG = ColorGreen()
					PrevB = ColorBlue()
					Color 0,0,0
					AAText((X + W / 2 - (AAStringWidth(b) / 2))+1, (LinesShown * Height + Y)+1, b)
					Color PrevR, PrevG, PrevB
				EndIf
				AAText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b)
			Else
				If Shadow Then
					PrevR = ColorRed()
					PrevG = ColorGreen()
					PrevB = ColorBlue()
					Color 0,0,0
					AAText(X+1, (LinesShown * Height + Y)+1, b)
					Color PrevR, PrevG, PrevB
				EndIf
				AAText(X, LinesShown * Height + Y, b)
			EndIf			
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	If (b$ <> "") And((LinesShown + 1) <= H) Then

		If align Then
			If Shadow Then
				PrevR = ColorRed()
				PrevG = ColorGreen()
				PrevB = ColorBlue()
				Color 0,0,0
				AAText((X + W / 2 - (AAStringWidth(b) / 2))+1 , (LinesShown * Height + Y)+1, b)
				Color PrevR, PrevG, PrevB
			EndIf
			AAText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		Else
			If Shadow Then
				PrevR = ColorRed()
				PrevG = ColorGreen()
				PrevB = ColorBlue()
				Color 0,0,0
				AAText(X+1, (LinesShown * Height + Y)+1, b)
				Color PrevR, PrevG, PrevB
			EndIf
			AAText(X, LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		EndIf
	EndIf
	
End Function

Function RowFormatText(A$, X, Y, W, H, align% = 0, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=SMALLEST_POWER_TWO
	
	Local LinesShown = 0
	Local Height = AAStringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (AAStringWidth (b$ + temp$) > W) And (AAStringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If AAStringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			If align Then
				FormatText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b)
			Else
				FormatText(X, LinesShown * Height + Y, b)
			EndIf			
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	If (b$ <> "") And((LinesShown + 1) <= H) Then
		If align Then
			FormatText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		Else
			FormatText(X, LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		EndIf
	EndIf
	
End Function

Type loadedfonts
	Field namefont$
	Field sizefont%, bold%, italic%, underlined%
	Field mfont%
	Field isadefaultfont%
End Type

Function GetFormattedText$(txt$)
	Local Currtoken$, currtxt$, command$
	Local FoundCommand% = 0
	
	For i = 1 To Len(txt)
		FoundCommand = 0
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command$ = Piece(Right(txt, Max(Len(txt)-i+2, 0)), 2, "%")
			params$ = ""
			
			; ====================== oldest
			
			Select command
				Case "r", "g", "b", "y", "w", "p"
				Default: FoundCommand = FoundCommand+1
			End Select
			
			; ====================== new

			Select Piece(command, 1, "|")
				Case "color", "font", "align", "alignfix", "tab", "clickable"
				Default: FoundCommand = FoundCommand+1
			End Select
			
			If FoundCommand = 2 Then 
				FoundCommand = False
			Else
				i = i+Len(command)+1
			EndIf
		Else
			FoundCommand = False
		EndIf
		
		If Not FoundCommand Then currtxt = currtxt+currtoken
	Next
	
	Return currtxt
	
End Function

Function FormatTextDefault$(x#, y#, txt$, a = False, b = False, alpha# = 1.0, withshadow%=False)
	PrevSelectedFont = SelectedFont_
	Local r = ColorRed(), g = ColorGreen(), bb = ColorBlue()
	Local currtoken$, cw# = 0.0, currtxt$, command$, params$, highestheight#, highestwidth#, clickable% = False, clickabletext$, clickabler, clickableg, clickableb
	For i = 1 To Len(txt)
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command = Right(txt, Max(Len(txt)-i+2, 0))
			command$ = Piece(command, 2, "%")
			i = i+Len(command)+1
		Else
			currtxt = currtxt+currtoken
		EndIf
	Next

	Local clickabletokens$
	Local FoundCommand% = 0
	Local OffsetForClickability# = 0.0
	
	If a Then x = x-(StringWidth(currtxt)/2)
	If b Then y = y-(StringHeight(currtxt)/2)
	For i = 1 To Len(txt)
		FoundCommand = 0
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command$ = Piece(Right(txt, Max(Len(txt)-i+2, 0)), 2, "%")
			params$ = ""
			; oldest
			
			Color 255*alpha,255*alpha,255*alpha
			
			Select command
				Case "r"
					Color 255*alpha,0,0
				Case "g"
					Color 0,255*alpha,0
				Case "b"
					Color 0,0,255*alpha
					
				Case "y"
					Color 255*alpha,255*alpha,0
				Case "w"
					Color 255*alpha,255*alpha,255*alpha
				Case "p"
					Color 255*alpha,0,255*alpha
				Default
					FoundCommand = FoundCommand+1
			End Select
			; new
			Select Piece(command, 1, "|")
				Case "font"
					params = Piece(command, 2, "|")
					namefont$ = Piece(params, 1, ",")
					sizefont = Int(Piece(params, 2, ","))
					bold = Int(Piece(params, 3, ","))
					italic = Int(Piece(params, 4, ","))
					underlined = Int(Piece(params, 5, ","))
					
					lf2.loadedfonts = Null
					For lf.loadedfonts = Each loadedfonts
						If lf\namefont = namefont Then
							If lf\sizefont = sizefont Then
								If lf\bold = bold Then
									If lf\italic = italic Then
										If lf\underlined = underlined Then lf2 = lf : Exit
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					
					If lf2 = Null Then
						lf2 = New loadedfonts
						lf2\namefont = namefont
						lf2\sizefont = sizefont
						lf2\bold = bold
						lf2\italic = italic
						lf2\underlined = underlined
						lf2\mfont = LoadFont(namefont, sizefont*MenuScale, bold, italic, underlined)
						lf2\isadefaultfont = True
					EndIf
					
					SetFont lf2\mfont
				Case "color"
					params = Piece(command, 2, "|")
					Color Int(Piece(params, 1, ","))*alpha, Int(Piece(params, 2, ","))*alpha, Int(Piece(params, 3, ","))*alpha
				Default
					FoundCommand = FoundCommand+1
			End Select
			
			If FoundCommand = 2 Then 
				FoundCommand = False
			Else
				i = i+Len(command)+1
			EndIf
		Else
			;debuglog "AAStringWidth: "+AAStringWidth(currtoken)+" Token: "+currtoken
			FoundCommand = False
		EndIf
		
		If Not FoundCommand Then
			If WithShadow Then
				prevr = ColorRed()
				prevg = ColorGreen()
				prevb = ColorBlue()
				Color 0,0,0
				Text x+cw+1, y+1, currtoken
				Color prevr, prevg, prevb
			EndIf
			Text x+cw, y, currtoken
			
			cw = cw+StringWidth(currtoken)
			highestwidth = highestwidth+StringWidth(currtoken)
			If StringHeight(currtoken) >= highestheight Then highestheight = StringHeight(currtoken)
		EndIf
	Next
	Color r,g,bb
	
	SetFont SelectedFont_
	
	Return highestheight+" "+highestwidth
End Function

Function FormatText$(x#, y#, txt$, a = False, b = False, alpha# = 1.0, withshadow%=False)
	PrevAASelectedFont = AASelectedFont
	If Not DisableRedirectAccess Then
		For rt.RedirectText = Each RedirectText
			If Instr(txt, rt\txt) Then
				txt = Replace(txt, rt\txt, rt\totxt)
				Exit
			EndIf
		Next
	EndIf
	
	Local r = ColorRed(), g = ColorGreen(), bb = ColorBlue()
	Local currtoken$, cw# = 0.0, currtxt$, command$, params$, highestheight#, highestwidth#, clickable% = False, clickabletext$, clickabler, clickableg, clickableb
	For i = 1 To Len(txt)
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command = Right(txt, Max(Len(txt)-i+2, 0))
			command$ = Piece(command, 2, "%")
			i = i+Len(command)+1
		Else
			currtxt = currtxt+currtoken
		EndIf
	Next

	Local clickabletokens$
	Local FoundCommand% = 0
	Local OffsetForClickability# = 0.0

	If a Then x = x-(AAStringWidth(currtxt)/2)
	If b Then y = y-(AAStringHeight(currtxt)/2)
	For i = 1 To Len(txt)
		FoundCommand = 0
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command$ = Piece(Right(txt, Max(Len(txt)-i+2, 0)), 2, "%")
			params$ = ""
			; oldest
			
			Color 255*alpha,255*alpha,255*alpha
			
			Select command
				Case "r"
					Color 255*alpha,0,0
				Case "g"
					Color 0,255*alpha,0
				Case "b"
					Color 0,0,255*alpha
					
				Case "y"
					Color 255*alpha,255*alpha,0
				Case "w"
					Color 255*alpha,255*alpha,255*alpha
				Case "p"
					Color 255*alpha,0,255*alpha
				Default
					FoundCommand = FoundCommand+1
			End Select
			; new
			Select Piece(command, 1, "|")
				Case "color"
					params = Piece(command, 2, "|")
					Color Int(Piece(params, 1, ","))*alpha, Int(Piece(params, 2, ","))*alpha, Int(Piece(params, 3, ","))*alpha
				Case "font"
					params = Piece(command, 2, "|")
					namefont$ = Piece(params, 1, ",")
					sizefont = Int(Piece(params, 2, ","))
					bold = Int(Piece(params, 3, ","))
					italic = Int(Piece(params, 4, ","))
					underlined = Int(Piece(params, 5, ","))
					
					lf2.loadedfonts = Null
					For lf.loadedfonts = Each loadedfonts
						If lf\namefont = namefont Then
							If lf\sizefont = sizefont Then
								If lf\bold = bold Then
									If lf\italic = italic Then
										If lf\underlined = underlined Then lf2 = lf : Exit
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					If lf2 = Null Then
						lf2 = New loadedfonts
						lf2\namefont = namefont
						lf2\sizefont = sizefont
						lf2\bold = bold
						lf2\italic = italic
						lf2\underlined = underlined
						lf2\mfont = AALoadFont(namefont, sizefont*MenuScale, bold, italic, underlined)
					EndIf
					
					AASetFont lf2\mfont
				Case "align"
					a = True
					;x = x-(AAStringWidth(currtxt)/2)
				Case "alignfix"
					a = True
					x = x-(AAStringWidth(currtxt)/2)
				Case "tab"
					params = Piece(command, 2, "|")
					x = x + Float(Piece(params, 1, ","))*MenuScale
				Case "clickable"
					params = Piece(command, 2, "|")
					clickable = Int(Piece(params, 1, ","))
					clickabletext = Piece(params, 2, ",")
					clickabler = Int(Piece(params, 3, ","))
					clickableg = Int(Piece(params, 4, ","))
					clickableb = Int(Piece(params, 5, ","))
					
					Local currtokenclick$ = ""
					clickabletokens = ""
					
					If clickable = 0 Then 
						OffsetForClickability = 0
					Else
						OffsetForClickability = OffsetForClickability + AAStringWidth(GetFormattedText(Left(txt, i-1)))
						If Instr(txt, "%", i+1) > 0 Then
							For ACLICK = Instr(txt, "%", i+1) To Len(txt)
								currtokenclick = Mid(txt, ACLICK, 1)
								If currtokenclick = "%" Then
									Select Piece(Piece(Right(txt, Max(Len(txt)-ACLICK+2, 0)), 2, "%"), 1, "|")
										Case "clickable": Exit
									End Select
								Else
									clickabletokens = clickabletokens + currtokenclick
								EndIf
							Next
						EndIf
					EndIf
					
				Default
					FoundCommand = FoundCommand+1
			End Select
			
			If FoundCommand = 2 Then 
				FoundCommand = False
			Else
				i = i+Len(command)+1
			EndIf
		Else
			;debuglog "AAStringWidth: "+AAStringWidth(currtoken)+" Token: "+currtoken
			FoundCommand = False
		EndIf
		
		If Not FoundCommand Then
			If clickable Then
				prevr = ColorRed()
				prevg = ColorGreen()
				prevb = ColorBlue()
				If MouseOn(x+OffsetForClickability, y, AAStringWidth(clickabletokens), AAStringHeight(clickabletokens)) Then
					If WithShadow Then
						Color 0,0,0
						AAText x+cw+1, y+1, currtoken
					EndIf
					Color clickabler*alpha, clickableg*alpha, clickableb*alpha
					If MouseHit1 Then
						ExecFile(clickabletext)
						MouseHit1 = False
					EndIf
				Else
					If WithShadow Then
						Color 0,0,0
						AAText x+cw+1, y+1, currtoken
					EndIf
					Color prevr, prevg, prevb
				EndIf
				AAText x+cw, y, currtoken
			Else
				If WithShadow Then
					prevr = ColorRed()
					prevg = ColorGreen()
					prevb = ColorBlue()
					Color 0,0,0
					AAText x+cw+1, y+1, currtoken
					Color prevr, prevg, prevb
				EndIf
				AAText x+cw, y, currtoken
			EndIf
			cw = cw+AAStringWidth(currtoken)
			highestwidth = highestwidth+AAStringWidth(currtoken)
			If AAStringHeight(currtoken) >= highestheight Then highestheight = AAStringHeight(currtoken)
		EndIf
	Next
	Color r,g,bb
	
	AASetFont PrevAASelectedFont
	
	Return highestheight+" "+highestwidth
End Function

Function LimitFormatText$(x#, y#, txt$, width#, a = False, b = False, alpha# = 1.0, withshadow%=False)
	PrevAASelectedFont = AASelectedFont
	If Not DisableRedirectAccess Then
		For rt.RedirectText = Each RedirectText
			If Instr(txt, rt\txt) Then
				txt = Replace(txt, rt\txt, rt\totxt)
				Exit
			EndIf
		Next
	EndIf
	
	Local r = ColorRed(), g = ColorGreen(), bb = ColorBlue()
	Local currtoken$, cw# = 0.0, currtxt$, command$, params$, highestheight#, highestwidth#, clickable% = False, clickabletext$, clickabler, clickableg, clickableb
	For i = 1 To Len(txt)
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command = Right(txt, Max(Len(txt)-i+2, 0))
			command$ = Piece(command, 2, "%")
			i = i+Len(command)+1
		Else
			currtxt = currtxt+currtoken
		EndIf
	Next

	Local clickabletokens$
	Local FoundCommand% = 0
	
	If a Then x = x-(AAStringWidth(currtxt)/2)
	If b Then y = y-(AAStringHeight(currtxt)/2)
	
	Local DotsRendered% = 0
	Local OffsetForClickability# = 0.0
	
	For i = 1 To Len(txt)
		FoundCommand = 0
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command$ = Piece(Right(txt, Max(Len(txt)-i+2, 0)), 2, "%")
			params$ = ""
			; oldest
			
			Color 255*alpha,255*alpha,255*alpha
			
			Select command
				Case "r"
					Color 255*alpha,0,0
				Case "g"
					Color 0,255*alpha,0
				Case "b"
					Color 0,0,255*alpha
					
				Case "y"
					Color 255*alpha,255*alpha,0
				Case "w"
					Color 255*alpha,255*alpha,255*alpha
				Case "p"
					Color 255*alpha,0,255*alpha
				Default
					FoundCommand = FoundCommand+1
			End Select
			; new
			Select Piece(command, 1, "|")
				Case "color"
					params = Piece(command, 2, "|")
					Color Int(Piece(params, 1, ","))*alpha, Int(Piece(params, 2, ","))*alpha, Int(Piece(params, 3, ","))*alpha
				Case "font"
					params = Piece(command, 2, "|")
					namefont$ = Piece(params, 1, ",")
					sizefont = Int(Piece(params, 2, ","))
					bold = Int(Piece(params, 3, ","))
					italic = Int(Piece(params, 4, ","))
					underlined = Int(Piece(params, 5, ","))
					
					lf2.loadedfonts = Null
					For lf.loadedfonts = Each loadedfonts
						If lf\namefont = namefont Then
							If lf\sizefont = sizefont Then
								If lf\bold = bold Then
									If lf\italic = italic Then
										If lf\underlined = underlined Then lf2 = lf : Exit
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					If lf2 = Null Then
						lf2 = New loadedfonts
						lf2\namefont = namefont
						lf2\sizefont = sizefont
						lf2\bold = bold
						lf2\italic = italic
						lf2\underlined = underlined
						lf2\mfont = AALoadFont(namefont, sizefont*MenuScale, bold, italic, underlined)
					EndIf
					
					AASetFont lf2\mfont
				Case "align"
					a = True
					;x = x-(AAStringWidth(currtxt)/2)
				Case "alignfix"
					a = True
					x = x-(AAStringWidth(currtxt)/2)
				Case "tab"
					params = Piece(command, 2, "|")
					x = x + Float(Piece(params, 1, ","))*MenuScale
				Case "clickable"
					params = Piece(command, 2, "|")
					clickable = Int(Piece(params, 1, ","))
					clickabletext = Piece(params, 2, ",")
					clickabler = Int(Piece(params, 3, ","))
					clickableg = Int(Piece(params, 4, ","))
					clickableb = Int(Piece(params, 5, ","))
					
					Local currtokenclick$ = ""
					clickabletokens = ""
					
					If clickable = 0 Then 
						OffsetForClickability = 0
					Else
						OffsetForClickability = OffsetForClickability + AAStringWidth(GetFormattedText(Left(txt, i-1)))
						If Instr(txt, "%", i+1) > 0 Then
							For ACLICK = Instr(txt, "%", i+1) To Len(txt)
								currtokenclick = Mid(txt, ACLICK, 1)
								If currtokenclick = "%" Then
									Select Piece(Piece(Right(txt, Max(Len(txt)-ACLICK+2, 0)), 2, "%"), 1, "|")
										Case "clickable": Exit
									End Select
								Else
									clickabletokens = clickabletokens + currtokenclick
								EndIf
							Next
						EndIf
					EndIf
				Default
					FoundCommand = FoundCommand+1
			End Select
			
			If FoundCommand = 2 Then 
				FoundCommand = False
			Else
				i = i+Len(command)+1
			EndIf
		Else
			;debuglog "AAStringWidth: "+AAStringWidth(currtoken)+" Token: "+currtoken
			FoundCommand = False
		EndIf
		
		If Not FoundCommand Then
			If cw >= width-AAStringWidth("...")-4*MenuScale Then 
				currtoken = "..."
				DotsRendered = DotsRendered+3
			EndIf
			
			If clickable Then
				prevr = ColorRed()
				prevg = ColorGreen()
				prevb = ColorBlue()
				If MouseOn(x+OffsetForClickability, y, AAStringWidth(clickabletokens), AAStringHeight(clickabletokens)) Then
					If WithShadow Then
						Color 0,0,0
						AAText x+cw+1, y+1, currtoken
					EndIf
					Color clickabler*alpha, clickableg*alpha, clickableb*alpha
					If MouseHit1 Then
						ExecFile(clickabletext)
						MouseHit1 = False
					EndIf
				Else
					If WithShadow Then
						Color 0,0,0
						AAText x+cw+1, y+1, currtoken
					EndIf
					Color prevr, prevg, prevb
				EndIf

				AAText x+cw, y, currtoken
			Else
				If WithShadow Then
					prevr = ColorRed()
					prevg = ColorGreen()
					prevb = ColorBlue()
					Color 0,0,0
					AAText x+cw+1, y+1, currtoken
					Color prevr, prevg, prevb
				EndIf
				AAText x+cw, y, currtoken
			EndIf
			cw = cw+AAStringWidth(currtoken)
			highestwidth = highestwidth+AAStringWidth(currtoken)
			If AAStringHeight(currtoken) >= highestheight Then highestheight = AAStringHeight(currtoken)
			
			If DotsRendered >= 3 Then Exit

		EndIf
	Next
	Color r,g,bb
	
	AASetFont PrevAASelectedFont
	
	Return highestheight+" "+highestwidth
End Function

Function RowText2(A$, X, Y, W, H, align% = 0, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=SMALLEST_POWER_TWO
	
	Local LinesShown = 0
	Local Height = StringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (StringWidth (b$ + temp$) > W) And (StringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If StringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			If align Then
				Text(X + W / 2 - (StringWidth(b) / 2), LinesShown * Height + Y, b)
			Else
				Text(X, LinesShown * Height + Y, b)
			EndIf
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	If (b$ <> "") And((LinesShown + 1) <= H) Then
		If align Then
			Text(X + W / 2 - (StringWidth(b) / 2), LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		Else
			Text(X, LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		EndIf
	EndIf
	
End Function

Function GetLineAmount(A$, W, H, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	;ErrorAxis
	If H<1 Then H=SMALLEST_POWER_TWO
	
	Local LinesShown = 0
	Local Height = AAStringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (AAStringWidth (b$ + temp$) > W) And (AAStringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If AAStringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H And H <> -1 Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	Return LinesShown+1
	
End Function

Function GetLineAmount2(A$, W, H, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=SMALLEST_POWER_TWO
	
	Local LinesShown = 0
	Local Height = StringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (StringWidth (b$ + temp$) > W) And (StringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If StringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	Return LinesShown+1
	
End Function

Function LimitText%(txt$, x%, y%, width%, usingAA%=True, Shadow%=False)
	Local TextLength%
	Local UnFitting%
	Local LetterWidth%
	If usingAA Then
		If txt = "" Or width = 0 Then Return 0
		TextLength = AAStringWidth(txt)
		UnFitting = TextLength - width
		If UnFitting <= 0 Then ;mahtuu
			If Shadow Then
				PrevR = ColorRed()
				PrevG = ColorGreen()
				PrevB = ColorBlue()
				Color 0,0,0
				AAText(x+1, y+1, txt)
				Color PrevR,PrevG,PrevB
			EndIf
			AAText(x, y, txt)
		Else ;ei mahdu
			LetterWidth = TextLength / Len(txt)
			If Shadow Then
				PrevR = ColorRed()
				PrevG = ColorGreen()
				PrevB = ColorBlue()
				Color 0,0,0
				AAText(x+1, y+1, Left(txt, Max(Len(txt) - UnFitting / LetterWidth - 4, 1)) + "...")
				Color PrevR,PrevG,PrevB
			EndIf
			AAText(x, y, Left(txt, Max(Len(txt) - UnFitting / LetterWidth - 4, 1)) + "...")
		End If
	Else
		If txt = "" Or width = 0 Then Return 0
		TextLength = StringWidth(txt)
		UnFitting = TextLength - width
		If UnFitting <= 0 Then ;mahtuu
			If Shadow Then
				PrevR = ColorRed()
				PrevG = ColorGreen()
				PrevB = ColorBlue()
				Color 0,0,0
				Text(x+1, y+1, txt)
				Color PrevR,PrevG,PrevB
			EndIf
			Text(x, y, txt)
		Else ;ei mahdu
			LetterWidth = TextLength / Len(txt)
			
			If Shadow Then
				PrevR = ColorRed()
				PrevG = ColorGreen()
				PrevB = ColorBlue()
				Color 0,0,0
				Text(x+1, y+1, Left(txt, Max(Len(txt) - UnFitting / LetterWidth - 4, 1)) + "...")
				Color PrevR,PrevG,PrevB
			EndIf
			Text(x, y, Left(txt, Max(Len(txt) - UnFitting / LetterWidth - 4, 1)) + "...")
		End If
	EndIf
End Function

Function DrawTooltip(message$)
	Local scale# = GraphicHeight/768.0
	
	Local width = (AAStringWidth(message$))+20*MenuScale
	
	Color 25,25,25
	Rect(ScaledMouseX()+20,ScaledMouseY(),width,19*scale,True)
	Color 150,150,150
	Rect(ScaledMouseX()+20,ScaledMouseY(),width,19*scale,False)
	AASetFont Font1
	AAText(ScaledMouseX()+(20*MenuScale)+(width/2),ScaledMouseY()+(12*MenuScale), message$, True, True)
End Function

Global QuickLoadPercent% = -1
Global QuickLoadPercent_DisplayTimer# = 0
Global QuickLoad_CurrEvent.Events

Function DrawQuickLoading()
	
	If QuickLoadPercent > -1
		MidHandle QuickLoadIcon
		DrawImage QuickLoadIcon,GraphicWidth-90,GraphicHeight-150
		Color 255,255,255
		AASetFont Font1
		AAText GraphicWidth-100,GraphicHeight-90,"LOADING: "+QuickLoadPercent+"%",1
		If QuickLoadPercent > 99
			If QuickLoadPercent_DisplayTimer < 70
				QuickLoadPercent_DisplayTimer# = Min(QuickLoadPercent_DisplayTimer+FPSfactor,70)
			Else
				QuickLoadPercent = -1
			EndIf
		EndIf
		QuickLoadEvents()
	Else
		QuickLoadPercent = -1
		QuickLoadPercent_DisplayTimer# = 0
		QuickLoad_CurrEvent = Null
	EndIf
	
End Function

Function DrawOptionsTooltip(x%,y%,width%,height%,option$,value#=0,ingame%=False)
	Local fx# = x+6*MenuScale
	Local fy# = y+6*MenuScale
	Local fw# = width-12*MenuScale
	Local fh# = height-12*MenuScale
	Local lines% = 0, lines2% = 0
	Local txt$ = ""
	Local txt2$ = "", R% = 0, G% = 0, B% = 0
	Local usetestimg% = False, extraspace% = 0
	
	AASetFont Font1
	Color 255,255,255
	Select Lower(option$)
		;Graphic options
			;[Block]
		Case "cmqu"
			txt = "Affects the quality of the picture in surveillance cameras. Strongly affects performance. It is recommended to set a low value"
		Case "cam"
			txt = "The time after which the surveillance camera will be updated. Increases productivity if speed is slow."
		Case "gu"
			txt = "Disable gamma update. Gives good performance, but gets darker in the game."
		Case "cant"
			txt = "You will not be able to load the save when players count 1>"
		Case "cant2"
			txt = "You will not be able to load the save in non-redirect server"
		Case "srv"
			txt = "Local servers are searched only on port 50021"
		Case "p2p"
			txt = "If you have Memory Access Violation after 100% or infinite load (99%) then use the server.exe"
		Case "nocheat"
			txt = "No cheat disable opening the console, as a result, no one can use cheats"
			txt2 = "This option cannot be changed in-game."
			R = 255
		Case "hostserver"
			txt = "Better use server.exe in the dedicated server folder"
			;txt2 = "This mode is buggy."
			;R = 255
		Case "voice"
			txt = "This button enables voice chat on the server."
			txt2 = "This option cannot be changed in-game."
			R = 255
		Case "bugs"
			txt = "If you turn the intro, then you will expect some glitchs and crash"
			txt2 = "Please don't turn the intro!"
			R = 255
		Case "bump"
			txt = Chr(34)+"Bump mapping"+Chr(34)+" is used to simulate bumps and dents by distorting the lightmaps."
			txt2 = "This option cannot be changed in-game."
			R = 255
		Case "vsync"
			txt = Chr(34)+"Vertical sync"+Chr(34)+" waits for the display to finish its current refresh cycle before calculating the next frame, preventing issues such as "
			txt = txt + "screen tearing. This ties the game's frame rate to your display's refresh rate and may cause some input lag."
		Case "antialias"
			txt = Chr(34)+"Anti-Aliasing"+Chr(34)+" is used to smooth the rendered image before displaying in order to reduce aliasing around the edges of models."
			txt2 = "This option only takes effect in fullscreen."
			R = 255
		Case "roomlights"
			txt = "Toggles the artificial lens flare effect generated over specific light sources."
		Case "gamma"
			txt = Chr(34)+"Gamma correction"+Chr(34)+" is used to achieve a good brightness factor to balance out your display's gamma if the game appears either too dark or bright. "
			txt = txt + "Setting it too high or low can cause the graphics to look less detailed."
			R = 255
			G = 255
			B = 255
			txt2 = "Current value: "+Int(value*100)+"% (default is 100%)"
		Case "texquality"
			txt = Chr(34)+"Texture LOD Bias"+Chr(34)+" affects the distance at which texture detail will change to prevent aliasing. Change this option if textures flicker or look too blurry."
		Case "particleamount"
			txt = "Determines the amount of particles that can be rendered per tick."
			Select value
				Case 0
					R = 255
					txt2 = "Only smoke emitters will produce particles."
				Case 1
					R = 255
					G = 255
					txt2 = "Only a few particles will be rendered per tick."
				Case 2
					G = 255
					txt2 = "All particles are rendered."
			End Select
		Case "vram"
			txt = "Textures that are stored in the Video-RAM will load faster, but this also has negative effects on the texture quality as well."
			txt2 = "This option cannot be changed in-game."
			R = 255
			;[End Block]
		;Sound options
			;[Block]
		Case "musicvol"
			txt = "Adjusts the volume of background music. Sliding the bar fully to the left will mute all music."
			R = 255
			G = 255
			B = 255
			txt2 = "Current value: "+Int(value*100)+"% (default is 50%)"
		Case "soundvol"
			txt = "Adjusts the volume of sound effects. Sliding the bar fully to the left will mute all sounds."
			R = 255
			G = 255
			B = 255
			txt2 = "Current value: "+Int(value*100)+"% (default is 100%)"
		Case "sfxautorelease"
			txt = Chr(34)+"Sound auto-release"+Chr(34)+" will free a sound from memory if it not used after 5 seconds. Prevents memory allocation issues."
			R = 255
			txt2 = "This option cannot be changed in-game."
		Case "usertrack"
			txt = "Toggles the ability to play custom tracks over channel 1 of the radio. These tracks are loaded from the " + Chr(34) + "SFX\Radio\UserTracks\" + Chr(34)
			txt = txt + " directory. Press " + Chr(34) + "1" + Chr(34) + " when the radio is selected to change track."
			R = 255
			txt2 = "This option cannot be changed in-game."
		Case "usertrackmode"
			txt = "Sets the playing mode for the custom tracks. "+Chr(34)+"Repeat"+Chr(34)+" plays every file in alphabetical order. "+Chr(34)+"Random"+Chr(34)+" chooses the "
			txt = txt + "next track at random."
			R = 255
			G = 255
			txt2 = "Note that the random mode does not prevent previously played tracks from repeating."
		Case "usertrackscan"
			txt = "Re-checks the user tracks directory for any new or removed sound files."
			;[End Block]
		;Control options	
			;[Block]
		Case "mousesensitivity"
			txt = "Adjusts the speed of the mouse pointer."
			R = 255
			G = 255
			B = 255
			txt2 = "Current value: "+Int((0.5+value)*100)+"% (default is 50%)"
		Case "mouseinvert"
			txt = Chr(34)+"Invert mouse Y-axis"+Chr(34)+" is self-explanatory."
		Case "mousesmoothing"
			txt = "Adjusts the amount of smoothing of the mouse pointer."
			R = 255
			G = 255
			B = 255
			txt2 = "Current value: "+Int(value*100)+"% (default is 100%)"
		Case "controls"
			txt = "Configure the in-game control scheme."
			;[End Block]
		;Advanced options	
			;[Block]
		Case "hud"
			txt = "Display the blink and stamina meters."
		Case "consoleenable"
			txt = "Toggles the use of the developer console. Can be used in-game by pressing " + KeyName(KEY_CONSOLE) + "."
		Case "consoleerror"
			txt = Chr(34)+"Open console on error"+Chr(34)+" is self-explanatory."
		Case "achpopup"
			txt = "Displays a pop-up notification when an achievement is unlocked."
		Case "showfps"
			txt = "Displays the frames per second counter at the top left-hand corner."
		Case "showscpviewmodel"
			txt = "Shows the SCP Viewmodels when enabled."
		Case "framelimit"
			txt = "Limits the frame rate that the game can run at to a desired value."
			If value > 0 And value < 60
				R = 255
				G = 255
				txt2 = "Usually, 60 FPS or higher is preferred. If you are noticing excessive stuttering at this setting, try lowering it to make your framerate more consistent."
			EndIf
		Case "antialiastext"
			txt = Chr(34)+"Antialiased text"+Chr(34)+" smooths out the text before displaying. Makes text easier to read at high resolutions."
			;[End Block]
	End Select
	
	lines% = GetLineAmount(txt,fw,fh)
	If usetestimg
		extraspace = 210*MenuScale
	EndIf
	If txt2$ = ""
		DrawFrame(x,y,width,((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+extraspace)
	Else
		lines2% = GetLineAmount(txt2,fw,fh)
		DrawFrame(x,y,width,(((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+(AAStringHeight(txt2)*lines2)+(10+lines2)*MenuScale)+extraspace)
	EndIf
	RowText(txt,fx,fy,fw,fh)
	If txt2$ <> ""
		Color R,G,B
		RowText(txt2,fx,(fy+(AAStringHeight(txt)*lines)+(5+lines)*MenuScale),fw,fh)
	EndIf
	If usetestimg
		MidHandle Menu_TestIMG
		If txt2$ = ""
			DrawImage Menu_TestIMG,x+(width/2),y+100*MenuScale+((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)
		Else
			DrawImage Menu_TestIMG,x+(width/2),y+100*MenuScale+(((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+(AAStringHeight(txt2)*lines2)+(10+lines2)*MenuScale)
		EndIf
	EndIf
End Function

Function DrawOptionsToolText#(x%,y%,width%,height%,txt$)
	Local fx# = x+6*MenuScale
	Local fy# = y+6*MenuScale
	Local fw# = width-12*MenuScale
	Local fh# = height-12*MenuScale
	Local lines% = 0, lines2% = 0
	Local txt2$ = "", R% = 0, G% = 0, B% = 0
	Local usetestimg% = False, extraspace% = 0
	
	AASetFont Font1
	Color 255,255,255
	
	lines% = GetLineAmount(txt,fw,fh)
	If usetestimg
		extraspace = 210*MenuScale
	EndIf
	If txt2$ = ""
		DrawFrame(x,y,width,((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+extraspace)
	Else
		lines2% = GetLineAmount(txt2,fw,fh)
		DrawFrame(x,y,width,(((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+(AAStringHeight(txt2)*lines2)+(10+lines2)*MenuScale)+extraspace)
	EndIf
	RowText(txt,fx,fy,fw,fh)
	If txt2$ <> ""
		Color R,G,B
		RowText(txt2,fx,(fy+(AAStringHeight(txt)*lines)+(5+lines)*MenuScale),fw,fh)
	EndIf
	If usetestimg
		MidHandle Menu_TestIMG
		If txt2$ = ""
			DrawImage Menu_TestIMG,x+(width/2),y+100*MenuScale+((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)
		Else
			DrawImage Menu_TestIMG,x+(width/2),y+100*MenuScale+(((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+(AAStringHeight(txt2)*lines2)+(10+lines2)*MenuScale)
		EndIf
	EndIf
	Return ((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+extraspace
End Function

Function DrawTextRect(x, y, width, height, txt$)
	Color 0,0,0
	Rect(x,y,width,height)
	Color 255,255,255
	FormatText(x+width/2, y+height/2, txt, True, True)
End Function

Function DrawOptionsToolImage(x%,y%, width%, height%, image)
	DrawFrame(x,y,width,height)
	DrawImage(image, x + 20 * MenuScale, y + 20 * MenuScale)
End Function



Function DrawMapCreatorTooltip(x%,y%,width%,height%,mapname$)
	Local fx# = x+6*MenuScale
	Local fy# = y+6*MenuScale
	Local fw# = width-12*MenuScale
	Local fh# = height-12*MenuScale
	Local lines% = 0
	
	AASetFont Font1
	Color 255,255,255
	
	Local txt$[5]
	If Right(mapname,6)="cbmap2" Then
		txt[0] = Left(mapname$,Len(mapname$)-7)
		Local f% = OpenFile("Map Creator\Maps\"+mapname$)
		
		Local author$ = ReadLine(f)
		Local descr$ = ReadLine(f)
		ReadByte(f)
		ReadByte(f)
		Local ramount% = ReadInt(f)
		If ReadInt(f) > 0 Then
			Local hasForest% = True
		Else
			hasForest% = False
		EndIf
		If ReadInt(f) > 0 Then
			Local hasMT% = True
		Else
			hasMT% = False
		EndIf
		
		CloseFile f%
	Else
		txt[0] = Left(mapname$,Len(mapname$)-6)
		author$ = "[Unknown]"
		descr$ = "[No description]"
		ramount% = 0
		hasForest% = False
		hasMT% = False
	EndIf
	txt[1] = "Made by: "+author$
	txt[2] = "Description: "+descr$
	If ramount > 0 Then
		txt[3] = "Room amount: "+ramount
	Else
		txt[3] = "Room amount: [Unknown]"
	EndIf
	If hasForest Then
		txt[4] = "Has custom forest: Yes"
	Else
		txt[4] = "Has custom forest: No"
	EndIf
	If hasMT Then
		txt[5] = "Has custom maintenance tunnel: Yes"
	Else
		txt[5] = "Has custom maintenance tunnel: No"
	EndIf
	
	lines% = GetLineAmount(txt[2],fw,fh)
	DrawFrame(x,y,width,(AAStringHeight(txt[0])*6)+AAStringHeight(txt[2])*lines+5*MenuScale)
	
	Color 255,255,255
	AAText(fx,fy,txt[0])
	AAText(fx,fy+AAStringHeight(txt[0]),txt[1])
	RowText(txt[2],fx,fy+(AAStringHeight(txt[0])*2),fw,fh)
	AAText(fx,fy+((AAStringHeight(txt[0])*2)+AAStringHeight(txt[2])*lines+5*MenuScale),txt[3])
	AAText(fx,fy+((AAStringHeight(txt[0])*3)+AAStringHeight(txt[2])*lines+5*MenuScale),txt[4])
	AAText(fx,fy+((AAStringHeight(txt[0])*4)+AAStringHeight(txt[2])*lines+5*MenuScale),txt[5])
	
End Function

Function ChangeMenu_TestIMG(change$)
	
	If Menu_TestIMG <> 0 Then FreeImage Menu_TestIMG
	AmbientLightRoomTex% = CreateTexture(2,2,257)
	TextureBlend AmbientLightRoomTex,5
	SetBuffer(TextureBuffer(AmbientLightRoomTex))
	ClsColor 0,0,0
	Cls
	SetBuffer BackBuffer()
	Menu_TestIMG = Create3DIcon(200,200,"GFX\map\room3z3_opt.rmesh",0,-0.75,1,0,0,0,menuroomscale#,menuroomscale#,menuroomscale#,True)
	ScaleImage Menu_TestIMG,MenuScale,MenuScale
	MaskImage Menu_TestIMG,255,0,255
	FreeTexture AmbientLightRoomTex : AmbientLightRoomTex = 0
	
	CurrMenu_TestIMG = change$
	
End Function

Global OnSliderID% = 0

Function Slider3(x%,y%,width%,value%,ID%,val1$,val2$,val3$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True)
	Rect(x+(width/2)+5,y-8,4,14,True)
	Rect(x+width+10,y-8,4,14,True)
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+width/2) And (ScaledMouseX() <= x+(width/2)+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+width)
			value = 2
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+(width/2)+3,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+(width/2)+7,y+10+MenuScale,val2,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val3,True)
	EndIf
	
	Return value
	
End Function

Function Slider4(x%,y%,width%,value%,ID%,val1$,val2$,val3$,val4$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True) ;1
	Rect(x+(width*(1.0/3.0))+(10.0/3.0),y-8,4,14,True) ;2
	Rect(x+(width*(2.0/3.0))+(20.0/3.0),y-8,4,14,True) ;3
	Rect(x+width+10,y-8,4,14,True) ;4
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+width*(1.0/3.0)) And (ScaledMouseX() <= x+width*(1.0/3.0)+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+width*(2.0/3.0)) And (ScaledMouseX() <= x+width*(2.0/3.0)+8)
			value = 2
		ElseIf (ScaledMouseX() >= x+width)
			value = 3
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+width*(1.0/3.0)+2,y-8)
	ElseIf value = 2
		DrawImage(BlinkMeterIMG,x+width*(2.0/3.0)+4,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+width*(1.0/3.0)+2+(10.0/3.0),y+10+MenuScale,val2,True)
	ElseIf value = 2
		AAText(x+width*(2.0/3.0)+2+((10.0/3.0)*2),y+10+MenuScale,val3,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val4,True)
	EndIf
	
	Return value
	
End Function

Function Slider5(x%,y%,width%,value%,ID%,val1$,val2$,val3$,val4$,val5$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True) ;1
	Rect(x+(width/4)+2.5,y-8,4,14,True) ;2
	Rect(x+(width/2)+5,y-8,4,14,True) ;3
	Rect(x+(width*0.75)+7.5,y-8,4,14,True) ;4
	Rect(x+width+10,y-8,4,14,True) ;5
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+width/4) And (ScaledMouseX() <= x+(width/4)+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+width/2) And (ScaledMouseX() <= x+(width/2)+8)
			value = 2
		ElseIf (ScaledMouseX() >= x+width*0.75) And (ScaledMouseX() <= x+(width*0.75)+8)
			value = 3
		ElseIf (ScaledMouseX() >= x+width)
			value = 4
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+(width/4)+1.5,y-8)
	ElseIf value = 2
		DrawImage(BlinkMeterIMG,x+(width/2)+3,y-8)
	ElseIf value = 3
		DrawImage(BlinkMeterIMG,x+(width*0.75)+4.5,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+(width/4)+4.5,y+10+MenuScale,val2,True)
	ElseIf value = 2
		AAText(x+(width/2)+7,y+10+MenuScale,val3,True)
	ElseIf value = 3
		AAText(x+(width*0.75)+9.5,y+10+MenuScale,val4,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val5,True)
	EndIf
	
	Return value
	
End Function

Function Slider7(x%,y%,width%,value%,ID%,val1$,val2$,val3$,val4$,val5$,val6$,val7$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True) ;1
	Rect(x+(width*(1.0/6.0))+(10.0/6.0),y-8,4,14,True) ;2
	Rect(x+(width*(2.0/6.0))+(20.0/6.0),y-8,4,14,True) ;3
	Rect(x+(width*(3.0/6.0))+(30.0/6.0),y-8,4,14,True) ;4
	Rect(x+(width*(4.0/6.0))+(40.0/6.0),y-8,4,14,True) ;5
	Rect(x+(width*(5.0/6.0))+(50.0/6.0),y-8,4,14,True) ;6
	Rect(x+width+10,y-8,4,14,True) ;7
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+(width*(1.0/6.0))) And (ScaledMouseX() <= x+(width*(1.0/6.0))+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+(width*(2.0/6.0))) And (ScaledMouseX() <= x+(width*(2.0/6.0))+8)
			value = 2
		ElseIf (ScaledMouseX() >= x+(width*(3.0/6.0))) And (ScaledMouseX() <= x+(width*(3.0/6.0))+8)
			value = 3
		ElseIf (ScaledMouseX() >= x+(width*(4.0/6.0))) And (ScaledMouseX() <= x+(width*(4.0/6.0))+8)
			value = 4
		ElseIf (ScaledMouseX() >= x+(width*(5.0/6.0))) And (ScaledMouseX() <= x+(width*(5.0/6.0))+8)
			value = 5
		ElseIf (ScaledMouseX() >= x+width)
			value = 6
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+(width*(1.0/6.0))+1,y-8)
	ElseIf value = 2
		DrawImage(BlinkMeterIMG,x+(width*(2.0/6.0))+2,y-8)
	ElseIf value = 3
		DrawImage(BlinkMeterIMG,x+(width*(3.0/6.0))+3,y-8)
	ElseIf value = 4
		DrawImage(BlinkMeterIMG,x+(width*(4.0/6.0))+4,y-8)
	ElseIf value = 5
		DrawImage(BlinkMeterIMG,x+(width*(5.0/6.0))+5,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+(width*(1.0/6.0))+2+(10.0/6.0),y+10+MenuScale,val2,True)
	ElseIf value = 2
		AAText(x+(width*(2.0/6.0))+2+((10.0/6.0)*2),y+10+MenuScale,val3,True)
	ElseIf value = 3
		AAText(x+(width*(3.0/6.0))+2+((10.0/6.0)*3),y+10+MenuScale,val4,True)
	ElseIf value = 4
		AAText(x+(width*(4.0/6.0))+2+((10.0/6.0)*4),y+10+MenuScale,val5,True)
	ElseIf value = 5
		AAText(x+(width*(5.0/6.0))+2+((10.0/6.0)*5),y+10+MenuScale,val6,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val7,True)
	EndIf
	
	Return value
	
End Function

Global OnBar%
Global ScrollBarY# = 0.0
Global ScrollMenuHeight# = 0.0

Function DrawScrollBar#(x, y, width, height, barx, bary, barwidth, barheight, bar#, dir = 0)
	;0 = vaakasuuntainen, 1 = pystysuuntainen
	
	Local MouseSpeedX = MouseXSpeed()
	Local MouseSpeedY = MouseYSpeed()
	
	Color(0, 0, 0)
	;Rect(x, y, width, height)
	Button(barx, bary, barwidth, barheight, "")
	
	If dir = 0 Then ;vaakasuunnassa
		If height > 10 Then
			Color 250,250,250
			Rect(barx + barwidth / 2, bary + 5*MenuScale, 2*MenuScale, barheight - 10)
			Rect(barx + barwidth / 2 - 3*MenuScale, bary + 5*MenuScale, 2*MenuScale, barheight - 10)
			Rect(barx + barwidth / 2 + 3*MenuScale, bary + 5*MenuScale, 2*MenuScale, barheight - 10)
		EndIf
	Else ;pystysuunnassa
		If width > 10 Then
			Color 250,250,250
			Rect(barx + 4*MenuScale, bary + barheight / 2, barwidth - 10*MenuScale, 2*MenuScale)
			Rect(barx + 4*MenuScale, bary + barheight / 2 - 3*MenuScale, barwidth - 10*MenuScale, 2*MenuScale)
			Rect(barx + 4*MenuScale, bary + barheight / 2 + 3*MenuScale, barwidth - 10*MenuScale, 2*MenuScale)
		EndIf
	EndIf
	
	If MouseX()>barx And MouseX()<barx+barwidth
		If MouseY()>bary And MouseY()<bary+barheight
			OnBar = True
		Else
			If (Not MouseDown1)
				OnBar = False
			EndIf
		EndIf
	Else
		If (Not MouseDown1)
			OnBar = False
		EndIf
	EndIf
	
	If MouseDown1
		If OnBar
			If dir = 0
				Return Min(Max(bar + MouseSpeedX / Float(width - barwidth), 0), 1)
			Else
				Return Min(Max(bar + MouseSpeedY / Float(height - barheight), 0), 1)
			EndIf
		EndIf
	EndIf
	
	Return bar
	
End Function

Function Button%(x,y,width,height,txt$, disabled%=False)
	Local Pushed = False
	
	Color 50, 50, 50
	If Not disabled Then 
		If MouseX() > x And MouseX() < x+width Then
			If MouseY() > y And MouseY() < y+height Then
				If MouseDown1 Then
					Pushed = True
					Color 50*0.6, 50*0.6, 50*0.6
				Else
					Color Min(50*1.2,255),Min(50*1.2,255),Min(50*1.2,255)
				EndIf
			EndIf
		EndIf
	EndIf
	
	If Pushed Then 
		Rect x,y,width,height
		Color 133,130,125
		Rect x+1*MenuScale,y+1*MenuScale,width-1*MenuScale,height-1*MenuScale,False	
		Color 10,10,10
		Rect x,y,width,height,False
		Color 250,250,250
		Line x,y+height-1*MenuScale,x+width-1*MenuScale,y+height-1*MenuScale
		Line x+width-1*MenuScale,y,x+width-1*MenuScale,y+height-1*MenuScale
	Else
		Rect x,y,width,height
		Color 133,130,125
		Rect x,y,width-1*MenuScale,height-1*MenuScale,False	
		Color 250,250,250
		Rect x,y,width,height,False
		Color 10,10,10
		Line x,y+height-1,x+width-1,y+height-1
		Line x+width-1,y,x+width-1,y+height-1		
	EndIf
	
	Color 255,255,255
	If disabled Then Color 70,70,70
	Text x+width/2, y+height/2-1*MenuScale, txt, True, True
	
	Color 0,0,0
	
	If Pushed And MouseHit1 Then PlaySound_Strict ButtonSFX : Return True
End Function

Function SetRandomSeed$()
	Local RandomSeedv$
	If Rand(15)=1 Then 
		Select Rand(14)
			Case 1 
				RandomSeedv = "NIL"
			Case 2
				RandomSeedv = "NO"
			Case 3
				RandomSeedv = "d9341"
			Case 4
				RandomSeedv = "5CP_I73"
			Case 5
				RandomSeedv = "DONTBLINK"
			Case 6
				RandomSeedv = "CRUNCH"
			Case 7
				RandomSeedv = "die"
			Case 8
				RandomSeedv = "HTAED"
			Case 9
				RandomSeedv = "rustledjim"
			Case 10
				RandomSeedv = "larry"
			Case 11
				RandomSeedv = "JORGE"
			Case 12
				RandomSeedv = "dirtymetal"
			Case 13
				RandomSeedv = "whatpumpkin"
			Case 14
				RandomSeedv = "14542015"
		End Select
	Else
		n = Rand(4,8)
		For i = 1 To n
			If Rand(3)=1 Then
				RandomSeedv = RandomSeedv + Rand(0,9)
			Else
				RandomSeedv = RandomSeedv + Chr(Rand(97,122))
			EndIf
		Next							
	EndIf
	Return RandomSeedv
End Function
Function ButtonOval(x, y, width, height, ovalcolor1,ovalcolor2,ovalcolor3, onmouseColor1, onmouseColor2, onmouseColor3)
	Local clicked% = False
	Color ovalcolor1,ovalcolor2,ovalcolor3
	If MouseOn(x, y, width, height) Then
		Color onmouseColor1,onmouseColor2,onmouseColor3
		If MouseHit1 Then clicked = True
	EndIf
	Oval(x, y, width, height)
	Return clicked
End Function


Const MAX_CLUES = 32
Const CLUES_FILE$ = "Data\clues.ini"

Type clues
	Field txt$
	Field timer
End Type

Global info_Image
Global CurrentClue.clues
Global Clues[MAX_CLUES]

Function InitInfoClues(file$)
	Delete Each clues
	If info_Image <> 0 Then FreeImage info_Image
	For i = 0 To MAX_CLUES-1 : Clues[i] = -1 : Next
	Local f = ReadFile(file)
	If f = 0 Then Return
	While Not Eof(f) : CreateQuickClue(ReadLine(f)) : Wend
	CloseFile f
	
	info_image = LoadImage_Strict("GFX\multiplayer\menu\info.png")
	ResizeImage(info_image, 40*MenuScale, 40*MenuScale)
	;BufferDirty(ImageBuffer(info_image))
	MaskImage info_image, 0,0,0
	
	ResetClue(Object.clues(Clues[Rand(0,GetCluesCount()-1)]))
End Function

Function ResetClue(clue.Clues)
	clue\timer = MilliSecs()+Rand(6000,7000)
	CurrentClue = clue
End Function

Function DrawQuickClues()
	; Draw clue
	AASetFont Font1
	Color 200,200,200
	DrawImage info_image, GraphicWidth/2-(20*MenuScale), 60*MenuScale
	RowText(CurrentClue\txt, GraphicWidth/2-(165*MenuScale), 120*MenuScale, 350*MenuScale, 120*MenuScale, True, 1, True)
	If CurrentClue\timer < MilliSecs() Then ResetClue(Object.clues(Clues[Rand(0,GetCluesCount()-1)]))
	
End Function

Function CreateQuickClue(txt$)
	For i = 0 To MAX_CLUES-1
		If Clues[i] = -1 Then
			Local cl.clues = New clues
			cl\txt = txt
			Clues[i] = Handle(cl)
			Exit
		EndIf
	Next
	Return i
End Function

Function GetCluesCount()
	For i = 0 To MAX_CLUES-1
		If Clues[i] = -1 Then Exit
	Next
	Return i
End Function

Function CorrectValue%(Value%)
	Select MenuScale
		Case 0.2, 0.5, 1.2
			;[Block]
			Return(Value + 1)
			;[End Block]
		Case 0.6, 0.8
			;[Block]
			Return(Value + 2)
			;[End Block]
		Case 0.9, 1.1
			;[Block]
			Return(Value - 1)
			;[End Block]
		Default
			;[Block]
			Return(Value)
			;[End Block]
	End Select
End Function

Function ChangeResolution(width, height)
	If udp_GetStream() Then DisconnectServer("", False)
	PutINIValue(OptionFile, "options", "width", width)
	PutINIValue(OptionFile, "options", "height", height)
	If fullscreensetting Then
		PutINIValue(OptionFile, "options", "fullscreen", "true")
	Else
		PutINIValue(OptionFile, "options", "fullscreen", "false")
	EndIf
	If LauncherEnabledSetting Then
		PutINIValue(OptionFile, "launcher", "launcher enabled", "true")
	Else
		PutINIValue(OptionFile, "launcher", "launcher enabled", "false")
	EndIf
	If BorderlessWindowedSetting Then
		PutINIValue(OptionFile, "options", "borderless windowed", "true")
	Else
		PutINIValue(OptionFile, "options", "borderless windowed", "false")
	EndIf
	If Bit16ModeSetting Then
		PutINIValue(OptionFile, "options", "16bit", "true")
	Else
		PutINIValue(OptionFile, "options", "16bit", "false")
	EndIf
	PutINIValue(OptionFile, "options", "gfx driver new", SelectedGFXDriverSetting)
	PutINIValue(OptionFile, "launcher", "changeres", 1)
	PutINIValue(OptionFile, "launcher", "isstarted", 0)
	
	EndGraphics()
	If BorderlessWindowedSetting Or FullscreenSetting Then
		Graphics3D width, height, 0, 3
		
		; -- Change the window style to 'WS_POPUP' and then set the window position to force the style to update.
		api_SetWindowLong( G_app_handle, C_GWL_STYLE, C_WS_POPUP )
		api_SetWindowPos( G_app_handle, C_HWND_TOP, G_viewport_x, G_viewport_y, G_viewport_width, G_viewport_height, C_SWP_SHOWWINDOW )
	Else
		AspectRatioRatio = 1.0
		RealGraphicWidth = width
		RealGraphicHeight = height
		If fullscreensetting Then
			Graphics3D(width, height, (16*Bit16ModeSetting), 1)
		Else
			Graphics3D(width, height, 0, 2)
		End If
	EndIf
	
	ClsColor 0,0,0
	Cls
	Flip 0
	ClsColor 0,0,0
	Cls
	
	ExecuteApp("game.exe")
	
	While True
		UpdateINIFile(OptionFile)
		If GetINIInt(OptionFile, "launcher", "isstarted") = 1 Then
			PutINIValue(OptionFile, "launcher", "isstarted", 0)
			CloseGame()
		EndIf
		Delay 10
	Wend
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D