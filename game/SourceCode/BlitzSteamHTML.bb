;	BlitzSteam - Steam wrapper for Blitz
;	Copyright (C) 2015 Xaymar (Michael Fabian Dirks)
;
;	This program is free software: you can redistribute it and/or modify
;	it under the terms of the GNU Lesser General Public License as
;	published by the Free Software Foundation, either version 3 of the 
;	License, or (at your option) any later version.
;
;	This program is distributed in the hope that it will be useful,
;	but WITHOUT ANY WARRANTY; without even the implied warranty of
;	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;	GNU General Public License for more details.
;
;	You should have received a copy of the GNU Lesser General Public License
;	along with this program.  If not, see <http://www.gnu.org/licenses/>.

;----------------------------------------------------------------
;! Steam Stuff
;----------------------------------------------------------------

; SteamClient: WarningMessageHook
Global Steam_WarningMessageHook_Callback = 0
Function Steam_WarningMessageHook(bIsWarning%, pchMessageBuffer%)
	If Steam_WarningMessageHook_Callback = 0 Then
		Steam_WarningMessageHook_Callback = BP_GetFunctionPointer()
		Return
	EndIf
	
	; Read Message from buffer
	Local msg$ = ""
	If bIsWarning = 1 Then
		msg = "[Warning]"
	Else
		msg = "[Info]"
	EndIf
	msg = msg + PeekMemoryStringC(pchMessageBuffer)
	
	DebugLog "[Steam]" + msg$
End Function:Steam_WarningMessageHook(0, 0)
Function PeekMemoryStringC(Memory%, Length%=-1)
	Local Ptr = Memory
	Local iChar = 0, tiChar = 0
	Local sOut$ = ""
	
	Repeat
		tiChar = PeekMemoryByte(Ptr)
		
		; Advance memory, decrease Length
		Ptr = Ptr + 1
		Length = Length - 1
		
		If (tiChar = 0) Then
			Length = 0
		Else
			sOut = sOut + Chr(tiChar)
		EndIf
	Until Length = 0
	
	Return sOut
End Function

;----------------------------------------------------------------
;! SteamBrowser (Image & Texture Drawing)
;----------------------------------------------------------------
Global SteamBrowser_Callback_BrowserReady_p = 0, SteamBrowser_Callback_BrowserReady_c = 0
Global SteamBrowser_Callback_NeedsPaint_p = 0, SteamBrowser_Callback_NeedsPaint_c = 0
Global SteamBrowser_Callback_StartRequest_p = 0, SteamBrowser_Callback_StartRequest_c = 0

Type SteamBrowser
	Field Id%, lSteamAPICall%
	Field Size%[1] ;Width, Height
	Field URL$
	
	; CEF Stuff
	Field UpdateRegion[3] ;X,Y,W,H
	Field Serial%, Scale#, ScrollX%, ScrollY%
	
	; Image Based
	Field hImage%
	
	; Texture Based
	Field hTexture%
End Type

Function SteamBrowser_Create.SteamBrowser(Width%, Height%, URL$="http://google.com/", UserAgent$="SteamBrowser", UserCSS$="")
	; Create our Object
	Local SB.SteamBrowser = New SteamBrowser
	SB\Id = 0 ; Initialize to 0 until the Browser is ready.
	SB\Size[0] = Width
	SB\Size[1] = Height
	SB\URL = URL
	SB\lSteamAPICall = BS_ISteamHTMLSurface_CreateBrowser(BS_SteamHTMLSurface(), UserAgent, UserCSS)
	
	; We need to register our callbacks, or nothing will work.
	BS_Callback_RegisterResult SteamBrowser_Callback_BrowserReady_c, SB\lSteamAPICall, BS_SteamHTMLSurface_BrowserReady ; Listen to the result of the last SteamAPICall.
	BS_Callback_Register SteamBrowser_Callback_NeedsPaint_c, BS_SteamHTMLSurface_NeedsPaint
	BS_Callback_Register SteamBrowser_Callback_StartRequest_c, BS_SteamHTMLSurface_StartRequest
	
	; Image Based
	SB\hImage = CreateImage(SB\Size[0], SB\Size[1])
	; Texture Based
	SB\hTexture = CreateTexture(SB\Size[0], SB\Size[1], 1+2)
	
	Return SB
End Function

Function SteamBrowser_Destroy(SB.SteamBrowser)
	; Texture Based
	FreeTexture SB\hTexture
	; Image Based
	FreeImage SB\hImage
	
	BS_ISteamHTMLSurface_RemoveBrowser BS_SteamHTMLSurface(), SB\Id
	Delete SB
End Function

Function SteamBrowser_Find.SteamBrowser(iId%, lSteamAPICall%)
	Local SB.SteamBrowser = Null
	; Find by Id
	If iId <> 0
		For SB.SteamBrowser = Each SteamBrowser
			If (SB\Id = iId) Then
				Return SB
			EndIf
		Next
	EndIf
	; Find by SteamAPICall
	If lSteamAPICall <> 0
		For SB.SteamBrowser = Each SteamBrowser
			If (SB\lSteamAPICall <> 0) And (BS_Long_Compare(lSteamAPICall, SB\lSteamAPICall) = 0) Then
				Return SB
			EndIf
		Next
	EndIf
	Return Null
End Function

Function SteamBrowser_SetSize(SB.SteamBrowser, Width%, Height%)
	SB\Size[0] = Width
	SB\Size[1] = Height
	
	; Update Browser Size
	BS_ISteamHTMLSurface_SetSize BS_SteamHTMLSurface(), SB\Id, Width, Height
	
	; Image Based
	FreeImage(SB\hImage)
	SB\hImage = CreateImage(Width, Height)
	; Texture Based
	FreeTexture(SB\hTexture)
	SB\hTexture = CreateTexture(Width, Height, 1+2)
End Function

Function SteamBrowser_LoadUrl(SB.SteamBrowser, URL$, PostData$="")
	BS_ISteamHTMLSurface_LoadURL BS_SteamHTMLSurface(), SB\Id, URL, PostData
End Function

Function SteamBrowser_IsReady(SB.SteamBrowser)
	Return (SB\Id <> 0)
End Function

Function SteamBrowser_GetImageHandle(SB.SteamBrowser)
	Return SB\hImage
End Function

Function SteamBrowser_GetTextureHandle(SB.SteamBrowser)
	Return SB\hTexture
End Function

Function SteamBrowser_Callback_BrowserReady(pvParam%, bIOFailure, lSteamAPICall)
	If (SteamBrowser_Callback_BrowserReady_p = 0) Then
		SteamBrowser_Callback_BrowserReady_p = BP_GetFunctionPointer()
		SteamBrowser_Callback_BrowserReady_c = BS_Callback_New(SteamBrowser_Callback_BrowserReady_p)
		Return 
	EndIf
	
	; Search for a valid SteamBrowser object.
	Local SB.SteamBrowser = SteamBrowser_Find(0, lSteamAPICall)
	if SB <> null Then
		SB\Id = BS_Memory_PeekInt(pvParam, 0)
		SteamBrowser_SetSize(SB, SB\Size[0], SB\Size[1])
		SteamBrowser_LoadUrl(SB, SB\URL)
		
		; Destroy our SteamAPICall pointer and clear the value.
		BS_Long_Destroy(SB\lSteamAPICall):SB\lSteamAPICall = 0
	EndIf
End Function:SteamBrowser_Callback_BrowserReady(0, 0, 0)

Function SteamBrowser_Callback_NeedsPaint(pvParam%, p2, p3)
	If (SteamBrowser_Callback_NeedsPaint_p = 0) Then
		SteamBrowser_Callback_NeedsPaint_p = BP_GetFunctionPointer()
		SteamBrowser_Callback_NeedsPaint_c = BS_Callback_New(SteamBrowser_Callback_NeedsPaint_p)
		Return 
	EndIf
	
	; Search for a valid SteamBrowser object.
	Local SB.SteamBrowser = SteamBrowser_Find(BS_Memory_PeekInt(pvParam, 0), 0)
	
	if SB <> Null Then
	; Parse Data from pvParam
		Local pBuffer, pW, pH, pUpdateX, pUpdateY, pUpdateW, pUpdateH, pScrollX, pScrollY, pScale#, pSerial
		pBuffer				= BS_Memory_PeekInt(pvParam, 4)
		pW					= BS_Memory_PeekInt(pvParam, 8)
		pH					= BS_Memory_PeekInt(pvParam, 12)
		pUpdateX			= BS_Memory_PeekInt(pvParam, 16)
		pUpdateY			= BS_Memory_PeekInt(pvParam, 20)
		pUpdateW			= BS_Memory_PeekInt(pvParam, 24)
		pUpdateH			= BS_Memory_PeekInt(pvParam, 28)
		pScrollX			= BS_Memory_PeekInt(pvParam, 32)
		pScrollY			= BS_Memory_PeekInt(pvParam, 36)
		pScale				= BS_Memory_PeekFloat(pvParam, 40)
		pSerial				= BS_Memory_PeekInt(pvParam, 44)
		
		; Assign to Object
		SB\UpdateRegion[0] = pUpdateX
		SB\UpdateRegion[1] = pUpdateY
		SB\UpdateRegion[2] = pUpdateW
		SB\UpdateRegion[3] = pUpdateH
		SB\Scale = pScale
		SB\ScrollX = pScrollX
		SB\ScrollY = pScrollY
		SB\Serial = pSerial
		
		; Fix up Buffer Size (Always next bigger 16*n, for whatever reason (SIBLY WHAT DID YOU DO!?))
		Local nW = (16 * Ceil(pW / 16.0))
		;DebugLog ImageWidth(SB\hImage) + ":" + ImageHeight(SB\hImage)
		
		if sb\himage <> 0 then
			; Image Based
			Local hImageBuffer = ImageBuffer(SB\hImage)
			LockBuffer hImageBuffer
			BS_Helper_CopyMemoryIntMangle pBuffer, BS_Memory_PeekInt(hImageBuffer, 72), 0, pW, pH, nW, SB\Size[1], pUpdateX, pUpdateY, pUpdateW, pUpdateH
			UnlockBuffer hImageBuffer
		Endif
		
		if sb\Htexture <> 0 Then
			; Texture Based (Identical)
			Local hTextureBuffer = TextureBuffer(SB\hTexture)
			LockBuffer hTextureBuffer
			BS_Helper_CopyMemoryIntMangle pBuffer, BS_Memory_PeekInt(hTextureBuffer, 72), 0, pW, pH, nW, SB\Size[1], pUpdateX, pUpdateY, pUpdateW, pUpdateH
			UnlockBuffer hTextureBuffer
		EndIf
	EndIf
	
	
	; pvParam Structure
	;CALLBACK_MEMBER(0, HHTMLBrowser, unBrowserHandle) // the browser that needs the paint
	;CALLBACK_MEMBER(1, const char *, pBGRA ) // a pointer to the B8G8R8A8 data for this surface, valid until SteamAPI_RunCallbacks is next called
	;CALLBACK_MEMBER(2, uint32, unWide) // the total width of the pBGRA texture
	;CALLBACK_MEMBER(3, uint32, unTall) // the total height of the pBGRA texture
	;CALLBACK_MEMBER(4, uint32, unUpdateX) // the offset in X for the damage rect for this update
	;CALLBACK_MEMBER(5, uint32, unUpdateY) // the offset in Y for the damage rect for this update
	;CALLBACK_MEMBER(6, uint32, unUpdateWide) // the width of the damage rect for this update
	;CALLBACK_MEMBER(7, uint32, unUpdateTall) // the height of the damage rect for this update
	;CALLBACK_MEMBER(8, uint32, unScrollX) // the page scroll the browser was at when this texture was rendered
	;CALLBACK_MEMBER(9, uint32, unScrollY) // the page scroll the browser was at when this texture was rendered
	;CALLBACK_MEMBER(10, float, flPageScale) // the page scale factor on this page when rendered
	;CALLBACK_MEMBER(11, uint32, unPageSerial) // incremented on each new page load, you can use this to reject draws while navigating to new pages
End Function:SteamBrowser_Callback_NeedsPaint(0, 0, 0)

Function SteamBrowser_Callback_StartRequest(pvParam%, p2, p3)
	If (SteamBrowser_Callback_StartRequest_p = 0) Then
		SteamBrowser_Callback_StartRequest_p = BP_GetFunctionPointer()
		SteamBrowser_Callback_StartRequest_c = BS_Callback_New(SteamBrowser_Callback_StartRequest_p)
		Return 
	EndIf
	
	; Search for a valid SteamBrowser object.
	Local SB.SteamBrowser = SteamBrowser_Find(BS_Memory_PeekInt(pvParam, 0), 0)
	
	; Default to allow all requests. (Why not? For an Example, this is good enough.)
	; Could implement a simple filter using a second type, but why don't you experiment a bit?
	if SB <> Null Then BS_ISteamHTMLSurface_AllowStartRequest BS_SteamHTMLSurface(), SB\Id, True
End Function:SteamBrowser_Callback_StartRequest(0, 0, 0)