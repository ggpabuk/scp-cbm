Const MaxItemAmount% = 10
Global BurntNote%
Global InvSelect%, SelectedItem.Items
Global DetectItemMoving
Global ClosestItem.Items
Global ItemsRotateRand = Rand(360)
Const ITEM_WAIT_FACTOR# = 5.0

Type ItemTemplates
	Field templateID
	Field name$
	Field tempname$
	
	Field sound%
	
	Field found%
	
	Field obj%, objpath$, parentobjpath$
	Field invimg%,invimg2%,invimgpath$
	Field imgpath$, img%
	
	Field isAnim%
	
	Field scale#
	;Field bumptex%
End Type 

Function CreateItemTemplate.ItemTemplates(name$, tempname$, objpath$, invimgpath$, imgpath$, scale#, texturepath$ = "",invimgpath2$="",Anim%=0, texflags%=9, Icon3D% = False)
	Local it.ItemTemplates = New ItemTemplates, n
	
	
	;if another item shares the same object, copy it
	For it2.itemtemplates = Each ItemTemplates
		If it2\objpath = objpath And it2\obj <> 0 Then it\obj = CopyEntity(it2\obj) : it\parentobjpath=it2\objpath : Exit
	Next
	
	If it\obj = 0 Then; it\obj = LoadMesh(objpath)
		If Anim<>0 Then
			it\obj = LoadAnimMesh_Strict(objpath)
			it\isAnim=True
		Else
			it\obj = LoadMesh_Strict(objpath)
			it\isAnim=False
		EndIf
		it\objpath = objpath
	EndIf
	it\objpath = objpath

	If texturepath <> "" Then
		texture=LoadTexture_Strict(texturepath,texflags%)
		EntityTexture it\obj, texture
		FreeTexture texture
	EndIf  
	
	it\scale = scale
	ScaleEntity it\obj, scale, scale, scale, True
	
	;if another item shares the same object, copy it
	For it2.itemtemplates = Each ItemTemplates
		If it2\invimgpath = invimgpath And it2\invimg <> 0 Then
			it\invimg = it2\invimg ;CopyImage()
			If it2\invimg2<>0 Then
				it\invimg2=it2\invimg2 ;CopyImage()
			EndIf
			Exit
		EndIf
	Next
	If it\invimg=0 Then
		if Icon3D = True Then
			it\invimg = Create3DIcon(64,64, objpath,texturepath) ; dont work
			MaskImage(it\invimg, 123, 54, 34)
		Else
			it\invimg = LoadImage_Strict(invimgpath)
			it\invimgpath = invimgpath
			MaskImage(it\invimg, 123, 54, 34)
			if ImageWidth(it\invimg) > 64 Or ImageHeight(it\invimg) > 64 Then ResizeImage it\invimg, 64,64
		EndIf
	EndIf
	
	If (invimgpath2 <> "") Then
		If it\invimg2=0 Then
			it\invimg2 = LoadImage_Strict(invimgpath2)
			MaskImage(it\invimg2,123,54,34)
			if ImageWidth(it\invimg2) > 64 Or ImageHeight(it\invimg2) > 64 Then ResizeImage it\invimg2, 64,64
		EndIf
	Else
		it\invimg2 = 0
	EndIf
	
	it\imgpath = imgpath
	
	;If imgpath<>"" Then
	;	it\img=LoadImage(imgpath)
	;	
	;	;DebugLog imgpath
	;	
	;	If it\img<>0 Then ResizeImage(it\img, ImageWidth(it\img) * MenuScale, ImageHeight(it\img) * MenuScale)
	;EndIf
	
	it\tempname = tempname
	it\name = name
	
	it\sound = 1
	it\templateID = FindFreeItemTemplateID()
	HideEntity it\obj
	if tempname = "chicken" Then RotateMesh it\obj, 0, 0, 90
	
	it\templateID = generateseednumber(name)+generateseednumber(tempname)+generateseednumber(objpath)+generateseednumber(invimgpath)+generateseednumber(imgpath)+generateseednumber(str(scale))+generateindex(name)+generateindex(tempname)+generateindex(objpath)+generateindex(invimgpath)+generateindex(imgpath)+generateindex(str(scale))
	Return it
	
End Function

Function FindFreeItemTemplateID%()
	Local id% = 1
	While (True)
		Local taken% = False
		For it.ItemTemplates = Each ItemTemplates
			If it\templateID = id Then
				taken = True
				Exit
			EndIf
		Next
		If (Not taken) Then
			Return id
		EndIf
		id = id + 1
	Wend
End Function

Function InitItemTemplates()
	Local it.ItemTemplates,it2.ItemTemplates
	
	it = CreateItemTemplate("Some SCP-420-J", "420", "GFX\items\420.x", "GFX\items\INV420.jpg", "", 0.0005)
	it\sound = 2
	
	CreateItemTemplate("Level 1 Key Card", "key1",  "GFX\items\keycard.x", "GFX\items\INVkey1.jpg", "", 0.0004,"GFX\items\keycard1.jpg")
	CreateItemTemplate("Level 2 Key Card", "key2",  "GFX\items\keycard.x", "GFX\items\INVkey2.jpg", "", 0.0004,"GFX\items\keycard2.jpg")
	CreateItemTemplate("Level 3 Key Card", "key3",  "GFX\items\keycard.x", "GFX\items\INVkey3.jpg", "", 0.0004,"GFX\items\keycard3.jpg")
	CreateItemTemplate("Level 4 Key Card", "key4",  "GFX\items\keycard.x", "GFX\items\INVkey4.jpg", "", 0.0004,"GFX\items\keycard4.jpg")
	CreateItemTemplate("Level 5 Key Card", "key5", "GFX\items\keycard.x", "GFX\items\INVkey5.jpg", "", 0.0004,"GFX\items\keycard5.jpg")
	CreateItemTemplate("Playing Card", "misc", "GFX\items\keycard.x", "GFX\items\INVcard.jpg", "", 0.0004,"GFX\items\card.jpg")
	CreateItemTemplate("Mastercard", "misc", "GFX\items\keycard.x", "GFX\items\INVmastercard.jpg", "", 0.0004,"GFX\items\mastercard.jpg")
	CreateItemTemplate("Key Card Omni", "key6", "GFX\items\keycard.x", "GFX\items\INVkeyomni.jpg", "", 0.0004,"GFX\items\keycardomni.jpg")
	
	it = CreateItemTemplate("SCP-860", "scp860", "GFX\items\key.b3d", "GFX\items\INVkey.jpg", "", 0.001)
	it\sound = 3
	
	it = CreateItemTemplate("Document SCP-079", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc079.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-895", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc895.jpg", 0.003) : it\sound = 0 
	it = CreateItemTemplate("Document SCP-860", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc860.jpg", 0.003) : it\sound = 0 	
	it = CreateItemTemplate("Document SCP-860-1", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc8601.jpg", 0.003) : it\sound = 0 	
	it = CreateItemTemplate("SCP-093 Recovered Materials", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc093rm.jpg", 0.003) : it\sound = 0 	
	it = CreateItemTemplate("Document SCP-106", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc106.jpg", 0.003) : it\sound = 0	
	it = CreateItemTemplate("Dr. Allok's Note", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc106_2.jpg", 0.0025) : it\sound = 0
	it = CreateItemTemplate("Recall Protocol RP-106-N", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docRP.jpg", 0.0025) : it\sound = 0
	it = CreateItemTemplate("Document SCP-682", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc682.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-173", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc173.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-372", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc372.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-049", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc049.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-096", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc096.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-008", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc008.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-012", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc012.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-500", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc500.png", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-714", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc714.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-513", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc513.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-035", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc035.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("SCP-035 Addendum", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc035ad.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-939", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc939.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-966", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc966.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-970", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc970.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-1048", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1048.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-1123", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1123.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-1162", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1162.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document SCP-1499", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1499.png", 0.003) : it\sound = 0
	it = CreateItemTemplate("Incident Report SCP-1048-A", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1048a.jpg", 0.003) : it\sound = 0
	
	it = CreateItemTemplate("Drawing", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1048.jpg", 0.003) : it\sound = 0
	
	it = CreateItemTemplate("Leaflet", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\leaflet.jpg", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0
	
	it = CreateItemTemplate("Dr. L's Note", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\docL1.jpg", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0
	it = CreateItemTemplate("Dr L's Note", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\docL2.jpg", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0
	it = CreateItemTemplate("Blood-stained Note", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\docL3.jpg", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0
	it = CreateItemTemplate("Dr. L's Burnt Note", "paper", "GFX\items\paper.x", "GFX\items\INVbn.jpg", "GFX\items\docL4.jpg", 0.0025, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0
	it = CreateItemTemplate("Dr L's Burnt Note", "paper", "GFX\items\paper.x", "GFX\items\INVbn.jpg", "GFX\items\docL5.jpg", 0.0025, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0
	it = CreateItemTemplate("Scorched Note", "paper", "GFX\items\paper.x", "GFX\items\INVbn.jpg", "GFX\items\docL6.jpg", 0.0025, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0
	
	it = CreateItemTemplate("Journal Page", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docGonzales.jpg", 0.0025) : it\sound = 0
	
	
	it = CreateItemTemplate("Log #1", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\f4.jpg", 0.0017, "GFX\items\f4.jpg") : it\sound = 0
	it = CreateItemTemplate("Log #2", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\f5.jpg", 0.0017, "GFX\items\f4.jpg") : it\sound = 0
	it = CreateItemTemplate("Log #3", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\f6.jpg", 0.0017, "GFX\items\f4.jpg") : it\sound = 0
	
	it = CreateItemTemplate("Strange Note", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\docStrange.jpg", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0
	
	it = CreateItemTemplate("Nuclear Device Document", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docNDP.jpg", 0.003) : it\sound = 0	
	it = CreateItemTemplate("Class D Orientation Leaflet", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docORI.jpg", 0.003) : it\sound = 0	
	
	it = CreateItemTemplate("Note from Daniel", "paper", "GFX\items\note.x", "GFX\items\INVnote2.jpg", "GFX\items\docdan.jpg", 0.0025) : it\sound = 0
	
	it = CreateItemTemplate("Burnt Note", "paper", "GFX\items\paper.x", "GFX\items\INVbn.jpg", "GFX\items\bn.it", 0.003, "GFX\items\BurntNoteTexture.jpg")
	it\img = BurntNote : it\sound = 0
	
	it = CreateItemTemplate("Mysterious Note", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\sn.it", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0	
	
	it = CreateItemTemplate("Mobile Task Forces", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docMTF.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Security Clearance Levels", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docSC.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Object Classes", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docOBJC.jpg", 0.003) : it\sound = 0
	it = CreateItemTemplate("Document", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docRAND3.jpg", 0.003) : it\sound = 0 
	it = CreateItemTemplate("Addendum: 5/14 Test Log", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\docRAND2.jpg", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0 
	it = CreateItemTemplate("Notification", "paper", "GFX\items\paper.x", "GFX\items\INVnote.jpg", "GFX\items\docRAND1.jpg", 0.003, "GFX\items\notetexture.jpg") :it\sound = 0 	
	it = CreateItemTemplate("Incident Report SCP-106-0204", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docIR106.jpg", 0.003) : it\sound = 0 
	
	it = CreateItemTemplate("Ballistic Vest", "vest", "GFX\items\vest.x", "GFX\items\INVvest.jpg", "", 0.02,"GFX\items\Vest.png") : it\sound = 2
	it = CreateItemTemplate("Heavy Ballistic Vest", "finevest", "GFX\items\vest.x", "GFX\items\INVvest.jpg", "", 0.022,"GFX\items\Vest.png")
	it\sound = 2
	it = CreateItemTemplate("Bulky Ballistic Vest", "veryfinevest", "GFX\items\vest.x", "GFX\items\INVvest.jpg", "", 0.025,"GFX\items\Vest.png")
	it\sound = 2
	
	it = CreateItemTemplate("Hazmat Suit", "hazmatsuit", "GFX\items\hazmat.b3d", "GFX\items\INVhazmat.jpg", "", 0.013)
	it\sound = 2
	it = CreateItemTemplate("Hazmat Suit", "hazmatsuit2", "GFX\items\hazmat.b3d", "GFX\items\INVhazmat.jpg", "", 0.013)
	it\sound = 2
	it = CreateItemTemplate("Heavy Hazmat Suit", "hazmatsuit3", "GFX\items\hazmat.b3d", "GFX\items\INVhazmat.jpg", "", 0.013)
	it\sound = 2
	
	it = CreateItemTemplate("cup", "cup", "GFX\items\cup.x", "GFX\items\INVcup.jpg", "", 0.04) : it\sound = 2
	
	it = CreateItemTemplate("Empty Cup", "emptycup", "GFX\items\cup.x", "GFX\items\INVcup.jpg", "", 0.04) : it\sound = 2	
	
	it = CreateItemTemplate("SCP-500-01", "scp500", "GFX\items\pill.b3d", "GFX\items\INVpill.jpg", "", 0.0001) : it\sound = 2
	EntityColor it\obj,255,0,0
	
	it = CreateItemTemplate("First Aid Kit", "firstaid", "GFX\items\firstaid.x", "GFX\items\INVfirstaid.jpg", "", 0.05)
	it = CreateItemTemplate("Small First Aid Kit", "finefirstaid", "GFX\items\firstaid.x", "GFX\items\INVfirstaid.jpg", "", 0.03)
	it = CreateItemTemplate("Blue First Aid Kit", "firstaid2", "GFX\items\firstaid.x", "GFX\items\INVfirstaid2.jpg", "", 0.03, "GFX\items\firstaidkit2.jpg")
	it = CreateItemTemplate("Strange Bottle", "veryfinefirstaid", "GFX\items\eyedrops.b3d", "GFX\items\INVbottle.jpg", "", 0.002, "GFX\items\bottle.jpg")	
	
	it = CreateItemTemplate("Gas Mask", "gasmask", "GFX\items\gasmask.b3d", "GFX\items\INVgasmask.jpg", "", 0.02) : it\sound = 2
	it = CreateItemTemplate("Gas Mask", "supergasmask", "GFX\items\gasmask.b3d", "GFX\items\INVgasmask.jpg", "", 0.021) : it\sound = 2
	it = CreateItemTemplate("Heavy Gas Mask", "gasmask3", "GFX\items\gasmask.b3d", "GFX\items\INVgasmask.jpg", "", 0.021) : it\sound = 2
	
	it = CreateItemTemplate("Origami", "misc", "GFX\items\origami.b3d", "GFX\items\INVorigami.jpg", "", 0.003) : it\sound = 0
	
	CreateItemTemplate("Electronical components", "misc", "GFX\items\electronics.x", "GFX\items\INVelectronics.jpg", "", 0.0011)
	
	it = CreateItemTemplate("Metal Panel", "scp148", "GFX\items\metalpanel.x", "GFX\items\INVmetalpanel.jpg", "", RoomScale) : it\sound = 2
	it = CreateItemTemplate("SCP-148 Ingot", "scp148ingot", "GFX\items\scp148.x", "GFX\items\INVscp148.jpg", "", RoomScale) : it\sound = 2
	
	CreateItemTemplate("S-NAV 300 Navigator", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.jpg", "GFX\items\navigator.png", 0.0008)
	CreateItemTemplate("S-NAV Navigator", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.jpg", "GFX\items\navigator.png", 0.0008)
	CreateItemTemplate("S-NAV Navigator Ultimate", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.jpg", "GFX\items\navigator.png", 0.0008)
	CreateItemTemplate("S-NAV 310 Navigator", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.jpg", "GFX\items\navigator.png", 0.0008)
	
	CreateItemTemplate("Radio Transceiver", "radio", "GFX\items\radio.x", "GFX\items\INVradio.jpg", "GFX\items\radioHUD.png", 1.0);0.0010)
	CreateItemTemplate("Radio Transceiver", "fineradio", "GFX\items\radio.x", "GFX\items\INVradio.jpg", "GFX\items\radioHUD.png", 1.0)
	CreateItemTemplate("Radio Transceiver", "veryfineradio", "GFX\items\radio.x", "GFX\items\INVradio.jpg", "GFX\items\radioHUD.png", 1.0)
	CreateItemTemplate("Radio Transceiver", "18vradio", "GFX\items\radio.x", "GFX\items\INVradio.jpg", "GFX\items\radioHUD.png", 1.02)
	
	it = CreateItemTemplate("Cigarette", "cigarette", "GFX\items\420.x", "GFX\items\INV420.jpg", "", 0.0004) : it\sound = 2
	
	it = CreateItemTemplate("Joint", "420s", "GFX\items\420.x", "GFX\items\INV420.jpg", "", 0.0004) : it\sound = 2
	
	it = CreateItemTemplate("Smelly Joint", "420s", "GFX\items\420.x", "GFX\items\INV420.jpg", "", 0.0004) : it\sound = 2
	
	it = CreateItemTemplate("Severed Hand", "hand", "GFX\items\severedhand.b3d", "GFX\items\INVhand.jpg", "", 0.04) : it\sound = 2
	it = CreateItemTemplate("Black Severed Hand", "hand2", "GFX\items\severedhand.b3d", "GFX\items\INVhand2.jpg", "", 0.04, "GFX\items\shand2.png") : it\sound = 2
	
	CreateItemTemplate("9V Battery", "bat", "GFX\items\Battery\Battery.x", "GFX\items\Battery\INVbattery9v.jpg", "", 0.008)
	CreateItemTemplate("18V Battery", "18vbat", "GFX\items\Battery\Battery.x", "GFX\items\Battery\INVbattery18v.jpg", "", 0.01, "GFX\items\Battery\Battery 18V.jpg")
	CreateItemTemplate("Strange Battery", "killbat", "GFX\items\Battery\Battery.x", "GFX\items\Battery\INVbattery22900.jpg", "", 0.01,"GFX\items\Battery\Strange Battery.jpg")
	
	CreateItemTemplate("Eyedrops", "fineeyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVeyedrops.jpg", "", 0.0012, "GFX\items\eyedrops.jpg")
	CreateItemTemplate("Eyedrops", "supereyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVeyedrops.jpg", "", 0.0012, "GFX\items\eyedrops.jpg")
	CreateItemTemplate("ReVision Eyedrops", "eyedrops","GFX\items\eyedrops.b3d", "GFX\items\INVeyedrops.jpg", "", 0.0012, "GFX\items\eyedrops.jpg")
	CreateItemTemplate("RedVision Eyedrops", "eyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVeyedropsred.jpg", "", 0.0012,"GFX\items\eyedropsred.jpg")
	
	it = CreateItemTemplate("SCP-714", "scp714", "GFX\items\scp714.b3d", "GFX\items\INV714.jpg", "", 0.3)
	it\sound = 3
	
	it = CreateItemTemplate("SCP-1025", "scp1025", "GFX\items\scp1025.b3d", "GFX\items\INV1025.jpg", "", 0.1)
	it\sound = 0
	
	it = CreateItemTemplate("SCP-513", "scp513", "GFX\items\513.x", "GFX\items\INV513.jpg", "", 0.1)
	it\sound = 2
	
	;BoH items
	
	it = CreateItemTemplate("Clipboard", "clipboard", "GFX\items\clipboard.b3d", "GFX\items\INVclipboard.jpg", "", 0.003, "", "GFX\items\INVclipboard2.jpg", 1)
	
	it = CreateItemTemplate("SCP-1123", "1123", "GFX\items\HGIB_Skull1.b3d", "GFX\items\inv1123.jpg", "", 0.015) : it\sound = 2
	
	;it = CreateItemTemplate("Document SCP-1074", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc1074.jpg", 0.003) : it\sound = 0
	;it = CreateItemTemplate("SCP-1074 Containment Notice", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc_arce.jpg", 0.003) : it\sound = 0
	
	it = CreateItemTemplate("Night Vision Goggles", "supernv", "GFX\items\NVG.b3d", "GFX\items\INVsupernightvision.jpg", "", 0.02) : it\sound = 2
	it = CreateItemTemplate("Night Vision Goggles", "nvgoggles", "GFX\items\NVG.b3d", "GFX\items\INVnightvision.jpg", "", 0.02) : it\sound = 2
	it = CreateItemTemplate("Night Vision Goggles", "finenvgoggles", "GFX\items\NVG.b3d", "GFX\items\INVveryfinenightvision.jpg", "", 0.02) : it\sound = 2
	
	it = CreateItemTemplate("Syringe", "syringe", "GFX\items\Syringe\syringe.b3d", "GFX\items\Syringe\inv.png", "", 0.005) : it\sound = 2
	it = CreateItemTemplate("Syringe", "finesyringe", "GFX\items\Syringe\syringe.b3d", "GFX\items\Syringe\inv.png", "", 0.005) : it\sound = 2
	it = CreateItemTemplate("Syringe", "veryfinesyringe", "GFX\items\Syringe\syringe.b3d", "GFX\items\Syringe\inv.png", "", 0.005) : it\sound = 2
	
	;.........
	
	;new Items in SCP:CB 1.3 - ENDSHN
	it = CreateItemTemplate("SCP-1499","scp1499","GFX\items\SCP-1499.b3d","GFX\items\INVscp1499.jpg", "", 0.023) : it\sound = 2
	it = CreateItemTemplate("SCP-1499","super1499","GFX\items\SCP-1499.b3d","GFX\items\INVscp1499.jpg", "", 0.023) : it\sound = 2
	CreateItemTemplate("Emily Ross' Badge", "badge", "GFX\items\badge.x", "GFX\items\INVbadge.jpg", "GFX\items\badge1.jpg", 0.0001, "GFX\items\badge1_tex.jpg")
	it = CreateItemTemplate("Lost Key", "key", "GFX\items\key.b3d", "GFX\items\INV1162_1.jpg", "", 0.001, "GFX\items\key2.png","",0,1+2+8) : it\sound = 3
	it = CreateItemTemplate("Disciplinary Hearing DH-S-4137-17092", "oldpaper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\dh.s", 0.003) : it\sound = 0
	it = CreateItemTemplate("Coin", "coin", "GFX\items\key.b3d", "GFX\items\INVcoin.jpg", "", 0.0005, "GFX\items\coin.png","",0,1+2+8) : it\sound = 3
	it = CreateItemTemplate("Movie Ticket", "ticket", "GFX\items\key.b3d", "GFX\items\INVticket.jpg", "GFX\items\ticket.png", 0.002, "GFX\items\tickettexture.png","",0,1+2+8) : it\sound = 0
	CreateItemTemplate("Old Badge", "badge", "GFX\items\badge.x", "GFX\items\INVoldbadge.jpg", "GFX\items\badge2.png", 0.0001, "GFX\items\badge2_tex.png","",0,1+2+8)
	
	it = CreateItemTemplate("Quarter","25ct", "GFX\items\key.b3d", "GFX\items\INVcoin.jpg", "", 0.0005, "GFX\items\coin.png","",0,1+2+8) : it\sound = 3
	it = CreateItemTemplate("Wallet","wallet", "GFX\items\wallet.b3d", "GFX\items\INVwallet.jpg", "", 0.0005,"","",1) : it\sound = 2
	
	CreateItemTemplate("SCP-427","scp427","GFX\items\427.b3d","GFX\items\INVscp427.jpg", "", 0.001)
	it = CreateItemTemplate("Upgraded pill", "scp500death", "GFX\items\pill.b3d", "GFX\items\INVpill.jpg", "", 0.0001) : it\sound = 2
	EntityColor it\obj,255,0,0
	it = CreateItemTemplate("Pill", "pill", "GFX\items\pill.b3d", "GFX\items\INVpillwhite.jpg", "", 0.0001) : it\sound = 2
	EntityColor it\obj,255,255,255
	
	it = CreateItemTemplate("Sticky Note", "paper", "GFX\items\note.x", "GFX\items\INVnote2.jpg", "GFX\items\note682.jpg", 0.0025) : it\sound = 0
	it = CreateItemTemplate("The Modular Site Project", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docMSP.jpg", 0.003) : it\sound = 0
	
	it = CreateItemTemplate("Research Sector-02 Scheme", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\docmap.jpg", 0.003) : it\sound = 0
	
	it = CreateItemTemplate("Document SCP-427", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.jpg", "GFX\items\doc427.jpg", 0.003) : it\sound = 0
	; guns
	CreateItemTemplate("USP Tactical", "usp", "GFX\items\USP_Tactical_Worldmodel.b3d", "GFX\items\INVusp.jpg", "", 0.02)
	CreateItemTemplate("FN P90", "p90", "GFX\items\P90_Worldmodel.b3d", "GFX\items\INVp90.jpg", "", 0.02)
	CreateItemTemplate("MP5-SD", "mp5sd", "GFX\items\mp5sd_worldmodel.b3d", "GFX\items\INVmp5.jpg", "", 0.014, "GFX\items\mp5sd_main.png")
	CreateItemTemplate("Rocket Launcher", "rpg", "GFX\items\rpg_worldmodel.b3d", "GFX\items\INVrpg.jpg", "", 0.015)
	CreateItemTemplate("Minigun", "minigun", "GFX\items\M134_Worldmodel.b3d", "GFX\items\INVm134.jpg", "", 0.014)
	if Not IsAHalloween() Then
		CreateItemTemplate("Grenade", "grenade", "GFX\items\grenadeworldmodel.b3d", "GFX\items\grenade.jpg", "", 0.014)
	Else
		CreateItemTemplate("Grenade", "grenade", "GFX\items\grenadeworldmodelHw.b3d", "GFX\items\grenade.jpg", "", 0.014)
	EndIf
	it = CreateItemTemplate("Uranium Candy", "urancandy", "GFX\items\candy.b3d", "GFX\items\candyicon.jpg", "", 0.01) : it\sound = 2
	CreateItemTemplate("Cooked Chicken", "chicken", "GFX\multiplayer\game\models\c.b3d", "GFX\multiplayer\game\images\chicken.jpg", "", 0.0007, "GFX\multiplayer\game\models\chicken.jpg")
	CreateItemTemplate("Box of ammo", "boxofammo", "GFX\items\box_ammo.b3d", "GFX\items\boxofammo.jpg", "", 0.02)
	
	CreateItemTemplate("Micro-HID", "microhid", "GFX\items\MicroHid_Worldmodel.b3d", "GFX\items\microhid.jpg", "", 0.015)
	CreateItemTemplate("Desert Eagle", "deagle", "GFX\items\deagle_worldmodel.b3d", "GFX\items\INVdeagle.jpg", "", 0.02)
	CreateItemTemplate("SPAS-12", "spas12", "GFX\items\SPAS_Worldmodel.b3d", "GFX\items\INVspas.jpg", "", 0.016)
	CreateItemTemplate("M4A4", "m4a4", "GFX\items\M4_Worldmodel.b3d", "GFX\items\INVm4.jpg", "", 0.02)
	CreateItemTemplate("Handcuffs", "handcuffs", "GFX\items\Handcuts_Worldmodel.b3d", "GFX\items\INVhandcuts.jpg", "", 0.01)
	CreateItemTemplate("SCP-035", "scp035", "GFX\items\035.b3d", "GFX\items\INV035.jpg", "", 0.018)
	
	CreateItemTemplate("Flashbang", "grenadeflashbang", "GFX\items\flashgrenadeworldmodel.b3d", "GFX\items\INVflashgrenade.jpg", "", 0.014)
	CreateItemTemplate("Smoke grenade", "grenadesmoke", "GFX\items\smokegrenadeworldmodel.b3d", "GFX\items\INVsmokegrenade.jpg", "", 0.014)
	
	CreateItemTemplate("Combat knife", "knife", "GFX\items\knife_worldmodel.b3d", "GFX\items\INVknife.jpg", "", 0.012)
	
	CreateItemTemplate("HK-G36", "hkg36", "GFX\items\HKG36_Worldmodel.b3d", "GFX\items\INVhkg36.jpg", "", 0.027)
	
	if GetScripts() then public_inqueue(public_OnInitItemTemplates, True)
End Function 

Type Items
	Field name$
	Field collider%,model%
	Field itemtemplate.ItemTemplates
	Field DropSpeed#
	
	Field r%,g%,b%,a#
	
	Field level
	
	Field SoundChn%
	
	Field dist#, disttimer#
	
	Field state#, state2#
	
	Field Picked%
	
	Field invimg%

	Field SecondInv.Items[20]
	Field ID%
	Field invSlots%
	Field waitFactor#
	Field DropSound%
	Field picker, prevpicker%
	Field x#, y#, z#
	Field collx#, colly#, collz#, collided%
	Field IsItemInView
	Field nettimeout#
End Type 
Global M_Item.Items[MAX_ITEMS]
Function SetItemID(it.Items, ID)
	M_Item[it\ID] = Null
	it\ID = ID
	M_Item[it\ID] = it
End Function
Function CreateItemByTemplate.Items(template.ItemTemplates, x#, y#, z#, r%=0,g%=0,b%=0,a#=1.0,invSlots%=0, give = True)
	;CatchErrors("Uncaught (CreateItem)")
	If template = Null Then Return Null
	Local i.Items = New Items
	Local it.ItemTemplates = template
	
	local name$ = ""
	local tempname$ = ""

	i\itemtemplate = it
	i\collider = CreatePivot()			
	EntityRadius i\collider, 0.01
	EntityPickMode i\collider, 0, False
	i\model = CopyEntity(it\obj,i\collider)
	i\name = it\name
	name = it\name
	tempname = it\tempname
			
	ShowEntity i\collider
	ShowEntity i\model
	
	ResetEntity i\collider		
	PositionEntity(i\collider, x, y, z, True)
	RotateEntity (i\collider, 0, ItemsRotateRand, 0)
	i\dist = EntityDistance(Collider, i\collider)
	i\collided = false
	i\colly = -41189
	i\DropSpeed = 0
	
	If tempname = "cup" Then
		i\r=r
		i\g=g
		i\b=b
		i\a=a
		
		Local liquid = CopyEntity(LiquidObj)
		ScaleEntity liquid, i\itemtemplate\scale,i\itemtemplate\scale,i\itemtemplate\scale,True
		PositionEntity liquid, EntityX(i\collider,True),EntityY(i\collider,True),EntityZ(i\collider,True)
		EntityParent liquid, i\model
		EntityColor liquid, r,g,b
		
		If a < 0 Then 
			EntityFX liquid, 1
			EntityAlpha liquid, Abs(a)
		Else
			EntityAlpha liquid, Abs(a)
		EndIf
		
		
		EntityShininess liquid, 1.0
	EndIf
	
	i\invimg = i\itemtemplate\invimg
	If (tempname="clipboard") And (invSlots=0) Then
		invSlots = 10
		SetAnimTime i\model,17.0
		i\invimg = i\itemtemplate\invimg2
	ElseIf (tempname="wallet") And (invSlots=0) Then
		invSlots = 10
		SetAnimTime i\model,0.0
	EndIf
	EntityType i\collider, HIT_ITEM
	i\invSlots=invSlots
	Return i
End Function
Function CreateItem.Items(name$, tempname$, x#, y#, z#, r%=0,g%=0,b%=0,a#=1.0,invSlots%=0, give = True)
	;CatchErrors("Uncaught (CreateItem)")
	Local i.Items = New Items
	Local it.ItemTemplates
	
	name = Lower(name)
	tempname = Lower (tempname)
	
	For it.ItemTemplates = Each ItemTemplates
		If Lower(it\name) = name Then
			If Lower(it\tempname) = tempname Then
				i\itemtemplate = it
				i\collider = CreatePivot()			
				EntityRadius i\collider, 0.01
				EntityPickMode i\collider, 0, False
				
				i\model = CopyEntity(it\obj,i\collider)
				;EntityPickMode i\model, 0, False
				
				i\name = it\name
				ShowEntity i\collider
				ShowEntity i\model
				Exit
			EndIf
		EndIf
	Next 
	
	If i\itemtemplate = Null Then
		Delete i
		Return
	EndIf
	
	ResetEntity i\collider		
	PositionEntity(i\collider, x, y, z, True)
	RotateEntity (i\collider, 0, ItemsRotateRand, 0)
	EntityType(i\collider, HIT_ITEM)
	i\dist = EntityDistance(Collider, i\collider)
	i\collided = false
	i\colly = -41189
	i\DropSpeed = 0
	
	If tempname = "cup" Then
		i\r=r
		i\g=g
		i\b=b
		i\a=a
		
		Local liquid = CopyEntity(LiquidObj)
		ScaleEntity liquid, i\itemtemplate\scale,i\itemtemplate\scale,i\itemtemplate\scale,True
		PositionEntity liquid, EntityX(i\collider,True),EntityY(i\collider,True),EntityZ(i\collider,True)
		EntityParent liquid, i\model
		EntityColor liquid, r,g,b
		
		If a < 0 Then 
			EntityFX liquid, 1
			EntityAlpha liquid, Abs(a)
		Else
			EntityAlpha liquid, Abs(a)
		EndIf
		
		
		EntityShininess liquid, 1.0
	EndIf
	
	i\invimg = i\itemtemplate\invimg
	If (tempname="clipboard") And (invSlots=0) Then
		invSlots = 10
		SetAnimTime i\model,17.0
		i\invimg = i\itemtemplate\invimg2
	ElseIf (tempname="wallet") And (invSlots=0) Then
		invSlots = 10
		SetAnimTime i\model,0.0
	EndIf
	
	i\invSlots=invSlots
	
	SetItemID(i, FindFreeItemID())
	i\picker = 0
	Return i
End Function

Function RemoveItem(i.Items, give = True)
	;CatchErrors("Uncaught (RemoveItem)")
	Local n
	FreeEntity(i\model) : FreeEntity(i\collider) : i\collider = 0
	For n% = 0 To MaxItemAmount - 1
		If Inventory(n) = i
			DebugLog "Removed "+i\itemtemplate\name+" from slot "+n
			Inventory(n) = Null
			ItemAmount = ItemAmount-1
			Exit
		EndIf
	Next
	If SelectedItem = i Then
		Select SelectedItem\itemtemplate\tempname 
			Case "nvgoggles", "supernv"
				WearingNightVision = False
			Case "gasmask", "supergasmask", "gasmask2", "gasmask3"
				WearingGasMask = False
			Case "vest", "finevest", "veryfinevest"
				WearingVest = False
			Case "hazmatsuit","hazmatsuit2","hazmatsuit3"
				WearingHazmat = False	
			Case "scp714"
				Wearing714 = False
			Case "scp1499","super1499"
				Wearing1499 = False
			Case "scp427"
				I_427\Using = False
		End Select
		
		SelectedItem = Null
	EndIf
	
	if EqquipedGun <> Null Then
		if i\picker = MyPlayer\ID Then
			if IsAGun(i\Itemtemplate\tempname) Then
				Local foundgun = False
				For a = 0 To MaxItemAmount-1
					if Inventory(a) <> Null And Inventory(a) <> i Then
						if IsAGun(i\Itemtemplate\tempname) = IsAGun(Inventory(a)\Itemtemplate\tempname) Then
							foundgun = true
							Exit
						endif
					EndIf
				Next
				if not foundgun then
					EqquipedGun = Null
					SelectedItem = Null
				endif
			EndIf
		EndIf
	EndIf
	
	If i\itemtemplate\img <> 0
		FreeImage i\itemtemplate\img
		i\itemtemplate\img = 0
	EndIf
	if udp_network\stream <> 0 And give = True Then
		udp_ByteStreamWriteChar M_REMOVEITEM
		udp_ByteStreamWriteShort i\ID
		udp_SetMicroByte(M_REMOVEITEM)
	EndIf
	M_Item[i\ID] = Null
	Delete i
	;CatchErrors("RemoveItem")
End Function
Function UpdateItems()
	;CatchErrors("Uncaught (UpdateItems)")
	UpdateNetworkItems()
End Function
Function UpdateNetworkItems()
	Local i.Items, i2.Items
	Local pick%
	
	Local HideDist# = HideDistance*0.5
	Local hand
	
	ClosestItem = Null
	Local dist#, foundinv, a
	
	Local ed#, xtemp#, ytemp#, ztemp#
	
	if NetworkServer\MainPlayer Then
		For i.Items = Each Items
			HideEntity i\collider
			i\IsItemInView = False
			If (Not i\Picked) Then
				For p.players = Each players
					if EntityDistance(p\pivot, i\collider) < HideDist Then 
						i\IsItemInView = True
						Exit
					EndIf
				Next
				
				If i\disttimer < MilliSecs() Then
					i\dist = EntityDistance(Camera, i\collider)
					i\disttimer = MilliSecs()+700
				EndIf
				
				If i\dist < HideDist Or i\IsItemInView Then
					ShowEntity i\collider
					If i\dist < 1.2 Then
						If ClosestItem = Null Then
							If EntityInView(i\model, Camera) Then
								If EntityVisible(i\collider,Camera) Then
									ClosestItem = i
								EndIf
							EndIf
						ElseIf ClosestItem = i Or i\dist < EntityDistance(Camera, ClosestItem\collider) Then 
							If EntityInView(i\model, Camera) Then
								If EntityVisible(i\collider,Camera) Then
									ClosestItem = i
								EndIf
							EndIf
						EndIf
					EndIf
					
					if i\itemtemplate\templateid = 146 then
						if (not multiplayer_IsASCP(MyPlayer\BreachType)) and (not player_isdead()) Then
							if EntityDistance(Camera, i\collider) < 4 then
								if EntityVisible(i\collider,MyHitbox) Then
									sanity = sanity-fpsfactor
									restoresanity = false
									
									pvt% = CreatePivot()
									PositionEntity pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
									PointEntity(pvt, i\collider)
									
									RotateEntity(Collider, EntityPitch(Collider), CurveAngle(EntityYaw(pvt), EntityYaw(Collider), Min(Max(15000.0 / (-Sanity), 20.0), 200.0)), 0)
									user_camera_pitch = CurveValue(EntityPitch(pvt), user_camera_pitch, Min(Max(15000.0 / (-Sanity), 20.0), 200.0))
									
									if EntityDistance(Camera, i\collider) > 0.5 then
										angle# = WrapAngle(EntityYaw(pvt)-EntityYaw(Collider))
										If angle<40.0 Then
											ForceMove = (40.0-angle)*0.008
										ElseIf angle > 310.0
											ForceMove = (40.0-Abs(360.0-angle))*0.008
										EndIf
									EndIf
									
									FreeEntity pvt
									
									if Sanity < -1500 Then
										if VomitTimer = 0 Then VomitTimer = 1
									EndIf
								EndIf
							endif
						EndIf
					endif
					
					if not i\collided then
						If i\colly = EntityY(i\collider) and shouldentitiesfall Then
							i\collided = true
							i\DropSpeed = 0
						Else
							i\colly = -21849192
							If ShouldEntitiesFall
								pick = LinePick(EntityX(i\collider),EntityY(i\collider),EntityZ(i\collider),0, -10, 0)
								If pick
									i\colly = EntityY(i\collider)
									i\DropSpeed = i\DropSpeed - 0.0004 * FPSfactor
									TranslateEntity i\collider, 0, i\DropSpeed * FPSfactor, 0
									DetectItemMoving = DetectItemMoving + 1
									
								Else
									i\DropSpeed = 0
								EndIf
								
							Else
								i\DropSpeed = 0
							EndIf
						EndIf
					EndIf

					If EntityY(i\collider) < -35.0 Then
						if not i\collided then
							removeitem(i)
							return
						else
							PositionEntity i\collider, EntityY(i\collider), i\colly, EntityZ(i\collider), True
							ResetEntity i\collider
						endif
					EndIf
					
				EndIf
			Else
				PositionEntity i\collider,EntityX(Player[i\picker]\Pivot),EntityY(Player[i\picker]\Pivot),EntityZ(Player[i\picker]\Pivot), True
				ResetEntity i\collider
				i\collided = false
				i\colly = -41189
				i\DropSpeed = 0
				If i\picker = NetworkServer\MyID Then
					foundinv = False
					For a = 0 To MaxItemAmount-1
						If Inventory(a) = i Then foundinv = True : Exit
					Next
					if Not foundinv Then
						For a = 0 To MaxItemAmount-1
							if Inventory(a) <> Null Then
								For b = 0 To Inventory(a)\invSlots-1
									if Inventory(a)\SecondInv[b] = i Then
										foundinv = True
										Exit
									Endif
								Next
							EndIf
						Next
						if not foundinv then
							For a = 0 To MaxItemAmount-1
								If Inventory(a) = Null Then 
									Inventory(a) = i
									Exit
								EndIf
							Next
						endif
					Endif
				Else
					For a = 0 To MaxItemAmount-1
						if Inventory(a) <> Null Then
							If Inventory(a) = i Then
								Inventory(a) = Null
								Exit
							EndIf
							For b = 0 To Inventory(a)\invSlots-1
								if Inventory(a)\SecondInv[b] = i Then
									Inventory(a)\SecondInv[b] = Null
									Exit
								Endif
							Next
						EndIf
					Next
				EndIf
			EndIf

			RotateEntity (i\collider, 0, ItemsRotateRand, 0)
			
			if i\picker <> 0 Then
				if i\picker <> NetworkServer\MyID then
					if Player[i\picker]\showobjects = True Then
						if Player[i\picker]\SelectedItem = i\ID Then
							hand = GetPlayerHand(i\picker)
							if hand <> 0 Then
								if EntityDistance(Camera, hand) < HideDist Then
									ShowEntity i\collider
									PositionEntity i\collider, EntityX(hand, True), EntityY(hand, True), EntityZ(hand, True), True
									RotateEntity i\collider, EntityPitch(hand, True)-45, EntityYaw(hand, True)-70, EntityRoll(hand, True)+20, True
									ResetEntity i\collider
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			EndIf
		Next
		
		if DetectItemMoving Then
			local FoundMove% = False
			
			For i.Items = Each Items
				if i\IsItemInView Then
					For i2.Items = Each Items
						if i2\IsItemInView Then
							If i<>i2 Then
								if (Not i2\Picked) Then
									xtemp# = (EntityX(i2\collider,True)-EntityX(i\collider,True))
									ytemp# = (EntityY(i2\collider,True)-EntityY(i\collider,True))
									ztemp# = (EntityZ(i2\collider,True)-EntityZ(i\collider,True))
									
									ed# = (xtemp*xtemp+ztemp*ztemp)
									If ed<0.07 Then
										if Abs(ytemp)<0.25 Then
											xtemp = xtemp*(0.07-ed)
											ztemp = ztemp*(0.07-ed)
											
											While Abs(xtemp)+Abs(ztemp)<0.001
												xtemp = xtemp+Rnd(-0.002,0.002)
												ztemp = ztemp+Rnd(-0.002,0.002)
											Wend
											
											i\collided = false
											i\colly = -41189
											
											i2\collided = false
											i2\colly = -41189
											
											TranslateEntity i2\collider,xtemp,0,ztemp
											TranslateEntity i\collider,-xtemp,0,-ztemp
											FoundMove = True
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
					Next
				EndIf
			Next
			if not FoundMove Then DetectItemMoving = 0
		EndIf
	Else

		For i.Items = Each Items
			HideEntity i\collider
			i\IsItemInView = False
			If (Not i\Picked) Then
				
				If i\disttimer < MilliSecs() Then
					i\dist = EntityDistance(Camera, i\collider)
					i\disttimer = MilliSecs()+700
				EndIf
				If i\dist < HideDist Or i\IsItemInView Then
					ShowEntity i\collider
					If i\dist < 1.2 Then
						If ClosestItem = Null Then
							If EntityInView(i\model, Camera) Then
								If EntityVisible(i\collider,Camera) Then
									ClosestItem = i
								EndIf
							EndIf
						ElseIf ClosestItem = i Or i\dist < EntityDistance(Camera, ClosestItem\collider) Then 
							If EntityInView(i\model, Camera) Then
								If EntityVisible(i\collider,Camera) Then
									ClosestItem = i
								EndIf
							EndIf
						EndIf
					EndIf
					
					if i\itemtemplate\templateid = 146 then
						if (not multiplayer_IsASCP(MyPlayer\BreachType)) and (not player_isdead()) Then
							if EntityDistance(Camera, i\collider) < 4 then
								if EntityVisible(i\collider,MyHitbox) Then
									sanity = sanity-fpsfactor
									restoresanity = false
									
									pvt% = CreatePivot()
									PositionEntity pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
									PointEntity(pvt, i\collider)
									
									RotateEntity(Collider, EntityPitch(Collider), CurveAngle(EntityYaw(pvt), EntityYaw(Collider), Min(Max(15000.0 / (-Sanity), 20.0), 200.0)), 0)
									user_camera_pitch = CurveValue(EntityPitch(pvt), user_camera_pitch, Min(Max(15000.0 / (-Sanity), 20.0), 200.0))
									
									if EntityDistance(Camera, i\collider) > 0.5 then
										angle# = WrapAngle(EntityYaw(pvt)-EntityYaw(Collider))
										If angle<40.0 Then
											ForceMove = (40.0-angle)*0.008
										ElseIf angle > 310.0
											ForceMove = (40.0-Abs(360.0-angle))*0.008
										EndIf
									EndIf
									
									FreeEntity pvt
									
									if Sanity < -1500 Then
										if VomitTimer = 0 Then VomitTimer = 1
									EndIf
								EndIf
							endif
						EndIf
					endif
					
					if not i\collided then
						If i\colly = EntityY(i\collider) and shouldentitiesfall Then
							i\collided = true
							i\DropSpeed = 0
						Else
							i\colly = -21849192
							If ShouldEntitiesFall
								pick = LinePick(EntityX(i\collider),EntityY(i\collider),EntityZ(i\collider),0, -10, 0)
								If pick
									i\colly = EntityY(i\collider)
									i\DropSpeed = i\DropSpeed - 0.0004 * FPSfactor
									TranslateEntity i\collider, 0, i\DropSpeed * FPSfactor, 0
									DetectItemMoving = DetectItemMoving + 1
									
								Else
									i\DropSpeed = 0
								EndIf
								
							Else
								i\DropSpeed = 0
							EndIf
						EndIf
					EndIf
					
				EndIf
			Else
			
				PositionEntity i\collider,EntityX(Player[i\picker]\Pivot),EntityY(Player[i\picker]\Pivot),EntityZ(Player[i\picker]\Pivot), True
				ResetEntity i\collider
				
				i\collided = false
				i\colly = -41189
				i\DropSpeed = 0
				If i\picker = NetworkServer\MyID Then
					foundinv = False
					For a = 0 To MaxItemAmount-1
						If Inventory(a) = i Then 
							foundinv = True
							Exit
						EndIf
					Next
					if Not foundinv Then
						For a = 0 To MaxItemAmount-1
							if Inventory(a) <> Null Then
								For b = 0 To Inventory(a)\invSlots-1
									if Inventory(a)\SecondInv[b] = i Then
										foundinv = True
										Exit
									Endif
								Next
							EndIf
						Next
						if not foundinv then
							For a = 0 To MaxItemAmount-1
								If Inventory(a) = Null Then 
									Inventory(a) = i
									Exit
								EndIf
							Next
						endif
					Endif
				Else
					For a = 0 To MaxItemAmount-1
						if Inventory(a) <> Null Then
							If Inventory(a) = i Then
								Inventory(a) = Null
								Exit
							EndIf
							For b = 0 To Inventory(a)\invSlots-1
								if Inventory(a)\SecondInv[b] = i Then
									Inventory(a)\SecondInv[b] = Null
									Exit
								Endif
							Next
						EndIf
					Next
				EndIf
			EndIf
			
			;abobusbobusbobabusljksbngfjobsdiujobjsbg@!#sdgs1234

			dist# = Distance(EntityX(i\Collider, True),EntityZ(i\Collider, True), i\x, i\z)
			
			if Distance(EntityY(i\Collider), 0, i\y, 0) > 0.3 Then
				i\nettimeout = i\nettimeout-FPSfactor
			Else
				i\nettimeout = 70
			EndIf
			
			if dist > 4 or i\nettimeout < 1 then
				PositionEntity i\collider,i\x,i\y,i\z, True
				ResetEntity i\collider
				i\collided = false
				DetectItemMoving = DetectItemMoving + 1
			Else
				if i\collided then
					;if dist >= 0.01 then
					PositionEntity i\collider,CurveValue(i\x, EntityX(i\Collider, True), 5),EntityY(i\Collider),CurveValue(i\z, EntityZ(i\Collider, True), 5), True
					ResetEntity(i\Collider)
						
					;	DetectItemMoving = DetectItemMoving + 1
					;endif
				EndIf
			EndIf
			
			RotateEntity (i\collider, 0, ItemsRotateRand, 0)
			
			if i\picker <> 0 Then
				if i\picker <> NetworkServer\MyID then
					if Player[i\picker]\showobjects = True Then
						if Player[i\picker]\SelectedItem = i\ID Then
							hand = GetPlayerHand(i\picker)
							if hand <> 0 Then
								if EntityDistance(Camera, hand) < HideDist Then
									ShowEntity i\collider
									PositionEntity i\collider, EntityX(hand, True), EntityY(hand, True), EntityZ(hand, True), True
									RotateEntity i\collider, EntityPitch(hand, True)-45, EntityYaw(hand, True)-70, EntityRoll(hand, True)+20, True
									ResetEntity i\collider
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			EndIf
		Next
	EndIf

	If ClosestItem <> Null Then
		if CanInteract() Then
			If (MOUSEINTERACT And MouseHit1) Or ((Not MouseINTERACT) And IsMouseUp()) Then
				PickItem(ClosestItem)
				BlockGuns = True
			EndIf
		EndIf
	EndIf
	
End Function

Function PlayerDropItem(it.Items)
	PositionEntity it\collider,Player[it\picker]\x,Player[it\picker]\y+0.7,Player[it\picker]\z, True
	ResetEntity it\Collider
	RotateEntity(it\collider, 0, EntityYaw(Player[it\picker]\Pivot)+Rnd(-110,110), 0)
	MoveEntity(it\collider, 0, -0.1, 0.1)
	it\picked = False
	it\picker = 0
	If (it\itemtemplate\tempname="clipboard") Then
		it\invimg = it\itemtemplate\invimg2
		SetAnimTime it\model,17.0
	ElseIf (it\itemtemplate\tempname="wallet") Then
		SetAnimTime it\model,0.0
	EndIf
End Function

Function GiveItem(name$, tempname$, forgiveid = 0)
	if forgiveid = 0 Then
		For i = 0 To MaxItemAmount-1
			If Inventory(i) = Null Then
				Inventory(i) = CreateItem(name, tempname, 1, 1, 1)
				EntityType (Inventory(i)\collider, HIT_ITEM)
				EntityParent(Inventory(i)\collider, 0)
				HideEntity Inventory(i)\Collider
				Inventory(i)\Picked = True
				Inventory(i)\Picker = NetworkServer\MyID
				Inventory(i)\itemtemplate\found=True
				Return
			EndIf
		Next
	Else
		it.Items = CreateItem(name, tempname, 1, 1, 1)
		EntityType (it\collider, HIT_ITEM)
		EntityParent(it\collider, 0)
		HideEntity it\Collider
		it\Picked = True
		it\Picker = forgiveid
		it\itemtemplate\found=True
	EndIf
End Function
Function PickItem(item.Items)
	if KillTimer < 0 Or item\Picked = True Or multiplayer_IsASCP(MyPlayer\BreachType) Or MyPlayer\BreachType = 0 Then Return
	;CatchErrors("Uncaught (PickItem)")
	If WearingHazmat > 0 Then
		Msg = "You cannot pick up any items while wearing a hazmat suit."
		MsgTimer = 70*5
		Return
	EndIf
	if MyPlayer\Handcuffed Then
		Msg = "You cannot pick up any items because you handcuffed"
		MsgTimer = 70*5
		Return
	EndIf
	Local n% = 0
	Local canpickitem = True
	Local fullINV% = True
	ClosestItem = Null
	For  n% = 0 To MaxItemAmount - 1
		if Inventory(n) <> Null Then
			if Inventory(n)\Picked = False Then Inventory(n) = Null
		EndIf
		For a = 0 To MaxItemAmount-1
			if Inventory(a) <> Null And a <> n Then
				if Inventory(a) = Inventory(n) Then Inventory(a) = Null
			EndIf
		Next
	Next
	For n% = 0 To MaxItemAmount - 1
		If Inventory(n)=Null
			fullINV = False
			Exit
		EndIf
	Next
	if NetworkServer\Breach Then
		For n% = 0 To MaxItemAmount - 1
			If Inventory(n) <> Null Then
				if Inventory(n)\itemtemplate\name = item\itemtemplate\name And Inventory(n)\itemtemplate\tempname = item\itemtemplate\tempname Then
					Msg = "You cannot carry same items."
					MsgTimer = 70 * 5
					Return
				EndIf
			EndIf
		Next
	EndIf
	If (Not fullINV) Then
		For n% = 0 To MaxItemAmount - 1
			If Inventory(n) = Null Then
				Select item\itemtemplate\tempname
					case "scp035"
						LightFlash = 2
						CameraShake = 10.0
						PlaySound_Strict Death914SFX
						multiplayer_WriteSound Death914SFX
						multiplayer_RequestRole(MODEL_035)
					Case "urancandy"
						Msg = "You feel a little bad."
						MsgTimer = 70*6
						BlurTimer = 500
					Case "1123"
						If Not (Wearing714 = 1) Then
							If PlayerRoom\RoomTemplate\Name <> "room1123" Then
								ShowEntity Light
								LightFlash = 7
								PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))	
								DeathMSG = "Subject D-9341 was shot dead after attempting to attack a member of Nine-Tailed Fox. Surveillance tapes show that the subject had been "
								DeathMSG = DeathMSG + "wandering around the site approximately 9 minutes prior, shouting the phrase " + Chr(34) + "get rid of the four pests" + Chr(34)
								DeathMSG = DeathMSG + " in chinese. SCP-1123 was found in [REDACTED] nearby, suggesting the subject had come into physical contact with it. How "
								DeathMSG = DeathMSG + "exactly SCP-1123 was removed from its containment chamber is still unknown."
								Kill()
							Elseif multiplayer_IsFullSync() Then
								ShowEntity Light
								LightFlash = 3
								Kill()
							Else
								For e.Events = Each Events
									If e\eventname = "room1123" Then 
										If e\eventstate = 0 Then
											ShowEntity Light
											LightFlash = 3
											PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))
										EndIf
										e\eventstate = Max(1, e\eventstate)
										
										Exit
									EndIf
								Next
							EndIf
						EndIf
						
						Return
					Case "killbat"
						ShowEntity Light
						LightFlash = 1.0
						PlaySound_Strict(IntroSFX(11))
						DeathMSG = "Subject D-9341 found dead inside SCP-914's output booth next to what appears to be an ordinary nine-volt battery. The subject is covered in severe "
						DeathMSG = DeathMSG + "electrical burns, and assumed to be killed via an electrical shock caused by the battery. The battery has been stored for further study."
						Kill()
					Case "scp148"
						GiveAchievement(Achv148)	
					Case "scp513"
						GiveAchievement(Achv513)
					Case "scp860"
						GiveAchievement(Achv860)
					Case "key6"
						GiveAchievement(AchvOmni)
					Case "veryfinevest"
						Msg = "The vest is too heavy to pick up."
						MsgTimer = 70*6
						Exit
					Case "firstaid", "finefirstaid", "veryfinefirstaid", "firstaid2"
						item\state = 0
					Case "navigator", "nav"
						If item\itemtemplate\name = "S-NAV Navigator Ultimate" Then GiveAchievement(AchvSNAV)
					Case "hazmatsuit", "hazmatsuit2", "hazmatsuit3"
						canpickitem = True
						For z% = 0 To MaxItemAmount - 1
							If Inventory(z) <> Null Then
								If Inventory(z)\itemtemplate\tempname="hazmatsuit" Or Inventory(z)\itemtemplate\tempname="hazmatsuit2" Or Inventory(z)\itemtemplate\tempname="hazmatsuit3" Then
									canpickitem% = False
									Exit
								ElseIf Inventory(z)\itemtemplate\tempname="vest" Or Inventory(z)\itemtemplate\tempname="finevest" Then
									canpickitem% = 2
									Exit
								EndIf
							EndIf
						Next
						
						If canpickitem=False Then
							Msg = "You are not able to wear two hazmat suits at the same time."
							MsgTimer = 70 * 5
							Return
						ElseIf canpickitem=2 Then
							Msg = "You are not able to wear a vest and a hazmat suit at the same time."
							MsgTimer = 70 * 5
							Return
						Else
							;TakeOffStuff(1+16)
							SelectedItem = item
						EndIf
					Case "vest","finevest"
						canpickitem = True
						For z% = 0 To MaxItemAmount - 1
							If Inventory(z) <> Null Then
								If Inventory(z)\itemtemplate\tempname="vest" Or Inventory(z)\itemtemplate\tempname="finevest" Then
									canpickitem% = False
									Exit
								ElseIf Inventory(z)\itemtemplate\tempname="hazmatsuit" Or Inventory(z)\itemtemplate\tempname="hazmatsuit2" Or Inventory(z)\itemtemplate\tempname="hazmatsuit3" Then
									canpickitem% = 2
									Exit
								EndIf
							EndIf
						Next
						
						If canpickitem=False Then
							Msg = "You are not able to wear two vests at the same time."
							MsgTimer = 70 * 5
							Return
						ElseIf canpickitem=2 Then
							Msg = "You are not able to wear a vest and a hazmat suit at the same time."
							MsgTimer = 70 * 5
							Return
						Else
							;TakeOffStuff(2)
							SelectedItem = item
						EndIf
				End Select
				
				If item\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(item\itemtemplate\sound))
				multiplayer_WriteSound(PickSFX(item\itemtemplate\sound), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 3.0)
				
				item\itemtemplate\found=True
				ItemAmount = ItemAmount + 1
				
				Inventory(n) = item
				HideEntity(item\collider)
				if udp_network\stream <> 0 Then
					udp_ByteStreamWriteChar M_TAKEITEM
					udp_ByteStreamWriteShort item\ID
					udp_SetMicroByte(M_TAKEITEM)
				EndIf
				if NetworkServer\MainPlayer Then 
					item\picker = NetworkServer\MyID
					item\Picked = True
				EndIf
				if item\itemtemplate\tempname = "scp035" then RemoveItem(item)
				
				Exit
			EndIf
		Next
	Else
		Msg = "You cannot carry any more items."
		MsgTimer = 70 * 5
	EndIf
	;CatchErrors("PickItem")
End Function
Function DropItem(item.Items,playdropsound%=True)
	;CatchErrors("DropItem")
	if multiplayer_IsASCP(MyPlayer\BreachType) Then Return
	if item\Picked = False Then Return
	if Instr(item\itemtemplate\tempname, "microhid") And MyPlayer\MicroHIDSHOOT Then Return
	If WearingHazmat > 0 Then
		Msg = "You cannot drop any items while wearing a hazmat suit."
		MsgTimer = 70*5
		Return
	EndIf
	If playdropsound Then
		If item\itemtemplate\sound <> 66 Then 
			PlaySound_Strict(PickSFX(item\itemtemplate\sound))
			multiplayer_WriteSound(PickSFX(item\itemtemplate\sound), EntityX(Collider), EntityY(Collider), EntityZ(Collider), 3.0)
		EndIf
	EndIf
	
	ShowEntity(item\collider)
	PositionEntity(item\collider, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
	RotateEntity(item\collider, EntityPitch(Camera), EntityYaw(Camera)+Rnd(-20,20), 0)
	MoveEntity(item\collider, 0, -0.1, 0.1)
	RotateEntity(item\collider, 0, EntityYaw(Camera)+Rnd(-110,110), 0)
	
	ResetEntity (item\collider)
	
	For z% = 0 To MaxItemAmount - 1
		If Inventory(z) = item Then Inventory(z) = Null
	Next
	Select item\itemtemplate\tempname
		Case "gasmask", "supergasmask", "gasmask3"
			WearingGasMask = False
		Case "hazmatsuit",  "hazmatsuit2", "hazmatsuit3"
			WearingHazmat = False
		Case "vest", "finevest"
			WearingVest = False
		Case "nvgoggles"
			If WearingNightVision = 1 Then CameraFogFar = StoredCameraFogFar : WearingNightVision = False
		Case "supernv"
			If WearingNightVision = 2 Then CameraFogFar = StoredCameraFogFar : WearingNightVision = False
		Case "finenvgoggles"
			If WearingNightVision = 3 Then CameraFogFar = StoredCameraFogFar : WearingNightVision = False
		Case "scp714"
			Wearing714 = False
		Case "scp1499","super1499"
			Wearing1499 = False
		Case "scp427"
			I_427\Using = False
	End Select
	if NetworkServer\MainPlayer Then 
		item\Picker = 0
		item\Picked = False
	EndIf
	if udp_network\stream <> 0 Then
		udp_ByteStreamWriteChar M_DROPITEM
		udp_ByteStreamWriteShort(item\ID)
		udp_SetMicroByte(M_DROPITEM)
	EndIf
	if IsAGun(item\itemtemplate\tempname) Then 
		if EqquipedGun <> Null Then
			if IsAGun(item\itemtemplate\tempname) = EqquipedGun\ID Then EqquipedGun = Null
		EndIf
	Endif
	if item\InvSlots <> 0 Then
		If (item\itemtemplate\tempname="clipboard") Then
			item\invimg = item\itemtemplate\invimg2
			SetAnimTime item\model,17.0
		ElseIf (item\itemtemplate\tempname="wallet") Then
			SetAnimTime item\model,0.0
		EndIf
	Endif
	For a%=0 To item\invSlots-1
		If item\SecondInv[a] <> Null Then
			DropItem(item\SecondInv[a], False)
			item\SecondInv[a] = Null
		EndIf
	Next
End Function
;Update any ailments inflicted by SCP-294 drinks.
Function Update294()
	;CatchErrors("Uncaught (Update294)")
	
	If CameraShakeTimer > 0 Then
		CameraShakeTimer = CameraShakeTimer - (FPSfactor/70)
		CameraShake = 2
	EndIf
	
	If VomitTimer > 0 Then
		DebugLog VomitTimer
		VomitTimer = VomitTimer - (FPSfactor/70)
		
		If (MilliSecs2() Mod 1600) < Rand(200, 400) Then
			If BlurTimer = 0 Then BlurTimer = Rnd(10, 20)*70
			CameraShake = Rnd(0, 2)
		EndIf
		
;		If (MilliSecs2() Mod 1000) < Rand(1200) Then 
		
		If Rand(50) = 50 And (MilliSecs2() Mod 4000) < 200 Then PlaySound_Strict(CoughSFX(Rand(0,2)))
		
		;Regurgitate when timer is below 10 seconds. (ew)
		If VomitTimer < 10 And Rnd(0, 500 * VomitTimer) < 2 Then
			If (Not ChannelPlaying(VomitCHN)) And (Not Regurgitate) Then
				VomitCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\294\Retch" + Rand(1, 2) + ".ogg"))
				multiplayer_WriteTempSound("SFX\SCP\294\Retch" + Rand(1, 2) + ".ogg", EntityX(Collider), EntityY(Collider), EntityZ(Collider))
				Regurgitate = MilliSecs2() + 50
			EndIf
		EndIf
		
		If Regurgitate > MilliSecs2() And Regurgitate <> 0 Then
			mouse_y_speed_1 = mouse_y_speed_1 + 1.0
		Else
			Regurgitate = 0
		EndIf
		
	ElseIf VomitTimer < 0 Then ;vomit
		VomitTimer = VomitTimer - (FPSfactor/70)
		
		If VomitTimer > -5 Then
			If (MilliSecs2() Mod 400) < 50 Then CameraShake = 4 
			mouse_x_speed_1 = 0.0
			Playable = False
		Else
			Playable = True
		EndIf
		
		If (Not Vomit) Then
			BlurTimer = 40 * 70
			VomitSFX = LoadSound_Strict("SFX\SCP\294\Vomit.ogg")
			VomitCHN = PlaySound_Strict(VomitSFX)
			multiplayer_WriteTempSound("SFX\SCP\294\Vomit.ogg", EntityX(Collider),EntityY(Collider),EntityZ(Collider))
			PrevInjuries = Injuries
			PrevBloodloss = Bloodloss
			Injuries = 1.5
			Bloodloss = 70
			EyeIrritation = 9 * 70
			
			pvt = CreatePivot()
			PositionEntity(pvt, EntityX(Camera), EntityY(Collider) - 0.05, EntityZ(Camera))
			TurnEntity(pvt, 90, 0, 0)
			EntityPick(pvt, 0.3)
			de.decals = CreateDecal(5, PickedX(), PickedY() + 0.005, PickedZ(), 90, 180, 0)
			de\Size = 0.001 : de\SizeChange = 0.001 : de\MaxSize = 0.6 : EntityAlpha(de\obj, 1.0) : EntityColor(de\obj, 0.0, Rnd(200, 255), 0.0) : ScaleSprite de\obj, de\size, de\size
			multiplayer_WriteDecal(de)
			FreeEntity pvt
			Vomit = True
		EndIf
		
		UpdateDecals()
		
		mouse_y_speed_1 = mouse_y_speed_1 + Max((1.0 + VomitTimer / 10), 0.0)
		
		If VomitTimer < -15 Then
			FreeSound_Strict(VomitSFX)
			VomitTimer = 0
			If KillTimer >= 0 Then
				PlaySound_Strict(BreathSFX(0,0))
			EndIf
			Injuries = PrevInjuries
			Bloodloss = PrevBloodloss
			Vomit = False
		EndIf
	EndIf
	
	;CatchErrors("Update294")
End Function
;Function CopyItem.Items(i.Items)
;	Local it.Items = CreateItem(i\itemtemplate\name, i\itemtemplate\tempname, EntityX(i\collider), EntityY(i\collider), EntityZ(i\collider), i\r,i\g,i\b,i\a,False)
;	RotateEntity it\collider, EntityPitch(i\collider), EntityYaw(i\collider), EntityRoll(i\collider)
;	Return it
;End Function



;~IDEal Editor Parameters:
;~F#B#1E
;~C#Blitz3D