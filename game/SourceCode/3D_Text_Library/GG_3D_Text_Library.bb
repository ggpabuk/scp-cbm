; TO DO: Change character spacing in GG_Change_Text()


; ************************************************************************************
; ******   Library Code - End users shouldn't have to worry about this stuff.   ******
;GG_Make_Bitmap_Font("Courier New Rus.ttf", 64, 1, 0,0,16,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZàáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß0123456789!¹;%:?*()_+/\|=-*,.[]{}<>")
Const GG_MAX_CHARS_PER_LINE = 250

Type tGG_Font
	Field sFontID$
	Field hBitmap
	Field sChars$
End Type

Type tGG_Character
	; This type only holds master characters - not the ones actually used to display text.
	Field iIndex%
	Field oFont.tGG_Font	; identifies which font this character object uses
	Field sChar$
	Field fWidth#	; uses float to make division accurate when positioning
	Field hSprite%
End Type

Type tGG_TextLine
	Field hPivot	; use this to position, rotate and scale the text line
	Field sText$
	Field hCharSprite[GG_MAX_CHARS_PER_LINE]
	Field hArcCharPivot[GG_MAX_CHARS_PER_LINE]
	Field oFont.tGG_Font	; the font used on this line
	Field iAlign%
	Field fSpacing#
	Field iOrientation%
	Field bArc			; true if an arc
	Field fStartAngle#	; only used with arcs
	Field iVertical%	; only used with arcs
	Field cPv
End Type

Function GG_Set_Color(hLine.tGG_TextLine, r, g, b)
	if hLine = Null Then Return
	For i = 1 To GG_MAX_CHARS_PER_LINE
		if hLine\hCharSprite[i] = 0 Then Return
		EntityColor hLine\hCharSprite[i], r, g, b
	Next
End Function

Function GG_Create_Text_Block.tGG_TextLine(sText$,iCharsPerLine% = 80,iAlign% = -1,fLineSpacing# = 1,fLetterSpacing# = 1,hParent% = 0)
	; Create a collection of text lines that are all parented to a single pivot, which is returned.
	Local hLine,hPivot,sTextPart$,i%,fLineY#,iSpace%
	; If justified, use default letter spacing to prevent strange spacing.
	If iAlign > 1 Then fLetterSpacing = 1
	; Cannot exceed max chars per line.
	If iCharsPerLine > GG_MAX_CHARS_PER_LINE Then iCharsPerLine = GG_MAX_CHARS_PER_LINE
	Local cLine.tGG_TextLine
	
	hPivot = CreatePivot(hParent)
	; Keep making text line objects until all text has been used.
	While sText <> ""
		If Len(sText$) >= iCharsPerLine Then
			sTextPart = Left(sText,iCharsPerLine)
		Else
			sTextPart = sText
		End If
		If Len(sTextPart) = iCharsPerLine Then
			; find the first space from the end for word wrapping
			iSpace = GG_InstrRev(sTextPart," ")
			If iSpace > 1 Then
				sTextPart = Left(sTextPart,iSpace-1)
			Else
				iSpace = iCharsPerLine		; nowhere to break, so use the whole line
			End If
			; Remove the text part from the main text.
			sText = Mid(sText,iSpace+1)
		Else
			sText = ""
		End If
		; If on the last line of text and the block is justified, make the last line flush left.
		If sText = "" And iAlign > 1 Then iAlign = -1
		; Make the text line object with the text part.
		cLine.tGG_TextLine = GG_Create_Text_Line(Trim(sTextPart),iAlign,fLetterSpacing,1,hPivot)
		hLine = cLine\hPivot
		cLine\cPv = hPivot
		; Move the line down for line spacing.
		PositionEntity hLine,0,fLineY,0
		fLineY = fLineY - fLineSpacing * 1.8
	Wend
	
	Return cLine
End Function


Function GG_Create_Text_Line.tGG_TextLine(sText$,iAlign% = -1,fSpacing# = 1,iOrientation% = 1,hParent% = 0)
	; Create a single line of text and return the entity to control it.
	Local i%,fXPos#,fPreviousWidth#,oTextLine.tGG_TextLine,oCharacter.tGG_Character
	Local fLastCharX#,fJustifiedX#
	
	If GG_oCurrent_Font = Null Then RuntimeError("GG_Create_Text_Line() was called without a current GG bitmap font.")
	
;	DebugLog "current font: " + GG_oCurrent_Font\sFontID

	If iAlign < -1 Then iAlign = -1

	oTextLine = New tGG_TextLine
	oTextLine\sText = sText
	oTextLine\hPivot = CreatePivot(hParent)
	oTextLine\iAlign = iAlign
	oTextLine\fSpacing = fSpacing
	oTextLine\iOrientation = iOrientation
	
	Local offset = 0
	For i = 1 To Len(sText)
		If i > GG_MAX_CHARS_PER_LINE Then Exit	; cannot exceed the maximum characters per line.
		oCharacter = GG_Find_Character(GG_oCurrent_Font,Mid(sText,i,1))
		if oCharacter <> Null Then
			oTextLine\hCharSprite[i-offset] = CopyEntity(oCharacter\hSprite,oTextLine\hPivot)
			ShowEntity oTextLine\hCharSprite[i-offset]
		Else
			offset = offset+1
		EndIf
	Next
	
	; Remember which font is used for this line.
	oTextLine\oFont = oCharacter\oFont
	
	GG_Space_Text_Line(oTextLine,sText)
	
	Return oTextLine
End Function


Function GG_Space_Text_Line(oTextLine.tGG_TextLine,sText$)
	Local i%,fXPos#,fPreviousWidth#,fJustifiedX#,fLastCharX#,oCharacter.tGG_Character
	Local iTextLength% = Len(sText)
	For i = 1 To iTextLength
		If i > GG_MAX_CHARS_PER_LINE Then Exit	; cannot exceed the maximum characters per line.
		oCharacter = GG_Find_Character(oTextLine\oFont,Mid(sText,i,1))
		If oTextLine\iOrientation = 1 Then
			fXPos = fXPos + (fPreviousWidth/2 + oCharacter\fWidth/2) * .035 * oTextLine\fSpacing
			PositionEntity oTextLine\hCharSprite[i],fXPos,0,0
		Else
			; for vertical, XPos is actually YPos
			fXPos = fXPos - 1.7 * oTextLine\fSpacing
			PositionEntity oTextLine\hCharSprite[i],0,fXPos,0
		End If
		fPreviousWidth = oCharacter\fWidth
	Next
		
	; If the alignment is not flush left, then align the characters justified, center or flush right.
	; Ignore alignment if the orientation is vertical.
	If oTextLine\iAlign <> -1 And oTextLine\iOrientation = 1 Then
		If oTextLine\iAlign > 1 Then
			; Justified. The width is based on the iAlign value.
			; Find the right-most character's position as a frame of reference
			fJustifiedX = oTextLine\iAlign
			fLastCharX = EntityX(oTextLine\hCharSprite[Len(sText)])
			
			For i = 1 To iTextLength
				MoveEntity oTextLine\hCharSprite[i],(fJustifiedX - fLastCharX) * (Float(i-1) / iTextLength),0,0
			Next
		Else
			; Centered or flush right
			For i = 1 To iTextLength
				MoveEntity oTextLine\hCharSprite[i],fXPos/2 * (-oTextLine\iAlign-1),0,0
			Next
		End If
	End If

End Function



Function GG_Space_Text_Arc(oTextLine.tGG_TextLine,sText$)
	Local i%,fPreviousWidth#,oCharacter.tGG_Character,fStartAngle#
	Local iTextLength% = Len(sText)
	Local fSpacing# = .2 * oTextLine\fSpacing
	
	fStartAngle = oTextLine\fStartAngle

	; The following two conditions are have very similar code,
	; but I separated them so the If...Then didn't have to check for each loop.
	If oTextLine\iVertical Then
		For i = 1 To iTextLength
			oCharacter = GG_Find_Character(oTextLine\oFont,Mid(sText,i,1))
			fStartAngle = fStartAngle - (fPreviousWidth Shr 1 + oCharacter\fWidth Shr 1) * fSpacing
			RotateEntity oTextLine\hArcCharPivot[i],0,0,fStartAngle * oTextLine\iOrientation
			fPreviousWidth = oCharacter\fWidth
		Next
	Else
		For i = 1 To iTextLength
			oCharacter = GG_Find_Character(oTextLine\oFont,Mid(sText,i,1))
			fStartAngle = fStartAngle - (fPreviousWidth Shr 1 + oCharacter\fWidth Shr 1) * fSpacing
			RotateEntity oTextLine\hArcCharPivot[i],0,fStartAngle * oTextLine\iOrientation,0
			fPreviousWidth = oCharacter\fWidth
		Next	
	End If
End Function



Function GG_Create_Text_Arc(sText$,fRadius# = 10,fStartAngle# = 90,fSpacing# = 1,iOrientation% = 1,hParent% = 0)
	; Create a single arc of text and return the entity to control it.
	Local i%,fPreviousWidth#,oTextLine.tGG_TextLine,oCharacter.tGG_Character
	Local iVertical%,hCharPivot%
	
	If GG_oCurrent_Font = Null Then RuntimeError("GG_Create_Text_Arc() was called without a current GG bitmap font.")

	Select iOrientation
		Case 1
			iVertical = True
		Case 2
			iVertical = True
			iOrientation = -1
		Case 3
			iVertical = False
			iOrientation = -1
		Case 4
			iVertical = False
			iOrientation = 1
		Default
			iVertical = True
			iOrientation = 1
	End Select
		
	oTextLine = New tGG_TextLine
	oTextLine\sText = sText
	oTextLine\hPivot = CreatePivot(hParent)
	oTextLine\iAlign = iAlign
	oTextLine\fSpacing = fSpacing
	oTextLine\iOrientation = iOrientation
	oTextLine\fStartAngle = fStartAngle
	oTextLine\iVertical = iVertical
	oTextLine\bArc = True

	For i = 1 To Len(sText)
		oCharacter = GG_Find_Character(GG_oCurrent_Font,Mid(sText,i,1))
		hCharPivot = CreatePivot(oTextLine\hPivot)	; the character is parented to this for easier re-spacing
		oTextLine\hArcCharPivot[i] = hCharPivot
		oTextLine\hCharSprite[i] = CopyEntity(oCharacter\hSprite,hCharPivot)
		; For characters in an arc, it is important to center the sprite's handles.
		If iVertical Then
			MoveEntity oTextLine\hCharSprite[i],0,fRadius * iOrientation,0
		Else
			MoveEntity oTextLine\hCharSprite[i],0,0,fRadius * iOrientation
		End If
		ShowEntity oTextLine\hCharSprite[i]
	Next
		
	; Remember which font is used for this arc.
	oTextLine\oFont = oCharacter\oFont
	
	GG_Space_Text_Arc(oTextLine,sText)
	
	Return oTextLine\hPivot
End Function



Function GG_Text_Alpha(hPivot%,fAlpha#)
	Local oTextLine.tGG_TextLine
	For oTextLine = Each tGG_TextLine
		If GetParent(oTextLine\hPivot) = hPivot Or oTextLine\hPivot = hPivot Then
			For i = 1 To Len(oTextLine\sText)
				EntityAlpha oTextLine\hCharSprite[i],fAlpha
			Next
		End If
	Next
End Function



Function GG_Delete_Text_Block(hPivot%)
	; Search all text lines to find ones with the given pivot as a parent, then delete them.
	Local oTextLine.tGG_TextLine
	For oTextLine = Each tGG_TextLine
		If GetParent(oTextLine\hPivot) = hPivot Then GG_Delete_Text_Line(oTextLine\hPivot)
	Next
	FreeEntity hPivot
End Function


Function GG_Delete_Text_Line(hPivot%)
	; Find the text line object and free all its entities and delete it.
	Local oTextLine.tGG_TextLine
	oTextLine = GG_Find_Text_Line(hPivot)
	If oTextLine = Null Then Return	; didn't find it, so do nothing
	For i = 1 To Len(oTextLine\sText)
		FreeEntity(oTextLine\hCharSprite[i])
	Next
	FreeEntity oTextLine\hPivot
	Delete oTextLine
End Function


Function GG_Free_Bitmap_Font(sFontID$)
	; Delete all master character entities of the given font, and free the bitmap.
	Local oCharacter.tGG_Character,oFont.tGG_Font
	oFont = GG_Find_Font(sFontID)
	For oCharacter = Each tGG_Character
		If oCharacter\oFont = oFont Then
			FreeEntity(oCharacter\hSprite)
			Delete oCharacter
		End If
	Next
	FreeTexture oFont\hBitmap
End Function


Function GG_Find_Text_Line.tGG_TextLine(hPivot%)
	Local oTextLine.tGG_TextLine
	For oTextLine = Each tGG_TextLine
		If oTextLine\hPivot = hPivot Then Return oTextLine
	Next
	Return Null
End Function


;Function GG_Find_Character.tGG_Character(sChar$)
;	Local oCharacter.tGG_Character
;	For oCharacter = Each tGG_Character
;		If sChar = oCharacter\sChar And oCharacter\oFont = GG_oCurrent_Font Then Return oCharacter
;	Next
;	; Didn't find the character in the font set, so use the master blank character, which is the last character in the set.
;	oCharacter = Last tGG_Character
;	Return oCharacter
;End Function


Global GG_oCurrent_Font.tGG_Font = Null
Function GG_Set_Current_Font(font.tGG_Font)
	GG_oCurrent_Font = font
End Function

Function GG_Load_Bitmap_Font.tGG_Font(sFontID$,sFilename$)
	Local hFile%,iBoxSize%,iCharCount%,sPath$,sBMPFile$,sTemp$,oLetter.tGG_Character,i%,hBitmap
	
	; See if the requested file exists.
	If FileType(sFilename$) = 0 Then
		RuntimeError("GG_Load_Bitmap_Font cannot find " + sFilename$)
	End If

	; First check for a matching .png file
	sTemp = Replace(sFilename,".ggfnt",".png")
	If FileType(sTemp) = 1 Then
		sBMPFile = sTemp
	Else
		; No .png found, so look for .bmp
		sBMPFile = Replace(sFilename,".ggfnt",".bmp")
		If FileType(sBMPFile) = 0 Then RuntimeError("GG_Load_Bitmap_Font() could not find a bitmap image for " + sFilename)
	End If

	; Read the header.
	hFile = ReadFile(sFilename)
	sTemp = ReadLine(hFile)
	iBoxSize = Mid(sTemp,Instr(sTemp,"=")+1)
	sTemp = ReadLine(hFile)
	iCharCount = Mid(sTemp,Instr(sTemp,"=")+1)
	
	; Load the bitmap as an anim texture so each frame is a character.
	hBitmap = LoadAnimTexture(sBMPFile,2+4+16+32+256,iBoxSize,iBoxSize,0,iCharCount)
	
	
	Local oFont.tGG_Font = New tGG_Font
	oFont\sFontID = sFontID
	oFont\hBitmap = hBitmap

	; Read the character info and make character objects.
	For i = 0 To iCharCount-1
		sTemp = ReadLine(hFile)

		oLetter = New tGG_Character
		oLetter\iIndex = i
		oLetter\oFont = oFont
		oLetter\sChar = Left(sTemp,1)
		oLetter\fWidth = Mid(sTemp,3)
		oLetter\hSprite = CreateSprite()
		SpriteViewMode oLetter\hSprite,2
		EntityTexture oLetter\hSprite,hBitmap,i
		HideEntity oLetter\hSprite

		oFont\sChars$ = oFont\sChars$ + oLetter\sChar
	Next
	
	; Make a blank sprite for characters not found in the set.
	oLetter = New tGG_Character
	oLetter\iIndex = iCharCount
	oLetter\oFont = oFont
	oLetter\sChar = "DUMMY"
	oLetter\fWidth = 10
	oLetter\hSprite = CreateSprite()
	EntityAlpha oLetter\hSprite,0	; make sure you can't see it - ever!
	HideEntity oLetter\hSprite
	
	CloseFile(hFile)

	GG_oCurrent_Font = oFont
	
	Return oFont
End Function


Function GG_Set_Font(sFontID$)
	; Look for the ID and see if it has been loaded.
	Local oFont.tGG_Font,bFound% = False
	For oFont = Each tGG_Font
		If oFont\sFontID = sFontID Then
			bFound = True
			Exit
		End If
	Next
	; If found, then set the current font.
	If bFound Then
		GG_oCurrent_Font = oFont
	End If
End Function


Function GG_Change_Text(hPivot,sText$)
	Local oTextLine.tGG_TextLine,i%,iLen%,iLenOrig%,oCharacter.tGG_Character

	; Find the line or arc. This doesn't work with blocks.
	oTextLine = GG_Find_Text_Line(hPivot)
	If oTextLine = Null Then Return		; didn't find it, so do nothing
	iLen = Len(sText)
	iLenOrig = Len(oTextLine\sText)
	If iLen > iLenOrig Then
		; The new text's length cannot exceed the original text's length.
		sText = Left(sText,iLenOrig)
	ElseIf iLen < iLenOrig Then
		; If the new text's length is shorter than the original, pad the extra characters with spaces.
		For i = 1 To (iLenOrig - iLen)
			sText = sText + " "
		Next
	End If
	
	For i = 1 To iLenOrig
		oCharacter = GG_Find_Character(oTextLine\oFont,Mid(sText,i,1))
		EntityTexture oTextLine\hCharSprite[i],oTextLine\oFont\hBitmap,oCharacter\iIndex
	Next
	
	If oTextLine\bArc Then
		GG_Space_Text_Arc(oTextLine,sText)
	Else
		GG_Space_Text_Line(oTextLine,sText)
	End If
End Function


Function GG_Find_Character.tGG_Character(oFont.tGG_Font,sChar$)
	Local oCharacter.tGG_Character
	For oCharacter = Each tGG_Character
		; If we find the character or the dummy character in the font, return the character.
		If oCharacter\oFont = oFont And (oCharacter\sChar = sChar Or oCharacter\sChar = "DUMMY") Then Return oCharacter
	Next
	Return Null	; should never hit this
End Function


Function GG_InstrRev%(sString$,sFind$)
	For i% = Len(sString)-Len(sFind)+1 To 1 Step -1
		If i + Len(sFind)-1 <= Len(sString) Then
			If Mid(sString,i,Len(sFind)) = sFind Then Return i
		End If
	Next
	Return 0
End Function


Function GG_Find_Font.tGG_Font(sFontID$)
	Local oFont.tGG_Font
	For oFont = Each tGG_Font
		If oFont\sFontID = sFontID Then Return oFont
	Next
	Return Null
End Function


Function GG_Make_Bitmap_Font(sFont$,iBoxSize%,bBold% = 0,bItalic% = 0,bUnderline% = 0,iCharsPerRow% = 0,sChars$ = 0)
	Local iRequestedSize%,sChar$,hImage%,hWidthFont%,iWidth%,iHeight%,i%,x%,y%,sFilename$,sBMPFile$
	
	iSize = iBoxSize 
	Repeat
		; Make sure none of the characters in the font are wider than the given box size.
		hFont = LoadFont(sFont,iSize,bBold,bItalic,bUnderline)
		SetFont(hFont)
		If iBoxSize < GG_Widest_Char_Width(sChars) Then
			iSize = iSize - 1
		Else
			Exit
		End If
	Forever
	
	; To be consistent across box sizes, use 64 as the character width measurement.
	hWidthFont = LoadFont(sFont,64,bBold,bItalic,bUnderline)
	
	If Len(sChars) < iCharsPerRow Then
		iWidth = Len(sChars) * iBoxSize
	Else
		iWidth = iCharsPerRow * iBoxSize
	End If
	
	iHeight = Ceil(Float(Len(sChars)) / iCharsPerRow) * iBoxSize
	
	hImage = CreateImage(iWidth,iHeight)
	SetBuffer(ImageBuffer(hImage))

	; Make sure the destination folder exists.
	If FileType("GG_Bitmap_Fonts") = 0 Then CreateDir("GG_Bitmap_Fonts")

	sFilename = "GG_Bitmap_Fonts/" + Replace(sFont," ","_") + "_" + iBoxSize
	If bBold Then sFilename = sFilename + "B"
	If bItalic Then sFilename = sFilename + "I"
	If bUnderline Then sFilename = sFilename + "U"
	sFilename = sFilename + ".ggfnt"
	
	sBMPFile = Replace(sFilename,".ggfnt",".bmp")
	
	hFile = WriteFile(sFilename)
	WriteLine(hFile,"BoxSize=" + iBoxSize)
	WriteLine(hFile,"CharCount=" + Len(sChars))
	
	For i = 1 To Len(sChars)
		sChar = Mid(sChars,i,1)
		x = ((i-1) Mod iCharsPerRow) * iBoxSize
		y = (Ceil(Float(i) / iCharsPerRow) - 1) * iBoxSize
		; Center the letters inside their boxes
		x = x + Float(iBoxSize - StringWidth(sChar)) / 2
		; Center vertically and subtract a couple pixels to give more room for descenders.
		y = y + Float(iBoxSize - iSize) / 2 - Ceil(.03125 * iBoxSize)
		SetFont(hFont)
		Text x,y,sChar
		SetFont(hWidthFont)
		WriteLine(hFile,sChar + "=" + StringWidth(sChar))
	Next
	
	CloseFile(hFile)
		
	; Save the bitmap image
	SaveImage hImage,sBMPFile
	Print "Finished creating files:"
	Print
	Print sFilename
	Print sBMPFile
	Print
	Print "Press ESC to exit."
	While Not KeyHit(1)
	Wend
	End
End Function

Function GG_Widest_Char_Width(sChars$)
	; find the widest character in the given set for the current font
	Local iWidest% = 0
	For i% = 1 To Len(sChars)
		If StringWidth(Mid(sChars,i,1)) > iWidest Then iWidest = StringWidth(Mid(sChars,i,1))
	Next
	Return iWidest
End Function