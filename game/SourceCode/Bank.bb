; -- NEW BANKS -- ;
; 	By Ne4to
; --------------- ;
Type fBank
	Field Bank
	Field BankIndex
End Type
Global CurrentBank.fBank
Function StartBank(b.fBank)
	b\BankIndex = 0
	CurrentBank = b
End Function
Function fCreateBank.fBank()
	Local fb.fBank = New fBank
	fb\Bank = CreateBank(1)
	Return fb
End Function
Function fPokeInt(i, resize = True)
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	if resize Then ResizeBank(CurrentBank\Bank, CurrentBank\BankIndex*2)
	PokeInt(CurrentBank\Bank, CurrentBank\BankIndex, i)
End Function
Function fPokeFloat(i#, resize = True)
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	if resize Then ResizeBank(CurrentBank\Bank, CurrentBank\BankIndex*2)
	PokeFloat(CurrentBank\Bank, CurrentBank\BankIndex, i)
End Function
Function fPokeShort(i#, resize = True)
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	if resize Then ResizeBank(CurrentBank\Bank, CurrentBank\BankIndex*2)
	PokeFloat(CurrentBank\Bank, CurrentBank\BankIndex, i)
End Function
Function fPokeByte(i, resize = True)
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	if resize Then ResizeBank(CurrentBank\Bank, CurrentBank\BankIndex*2)
	PokeByte(CurrentBank\Bank, CurrentBank\BankIndex, i)
End Function
Function fPokeString(f$,resize = True)
	Local strl = Len(f)-1
	fPokeByte strl,Resize
	For i = 0 To strl
		fPokeByte Asc(Mid(f$, i+1, 1)),Resize
	Next
End function
Function fPeekString$()
	Local txt$, byte, strlen = fPeekByte()
	For i = 0 To strlen
		byte = fPeekByte()
		txt = txt + Chr(byte)
	Next
	Return txt$
End Function
Function fPeekByte()
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	Return PeekByte(CurrentBank\Bank,CurrentBank\BankIndex)
End Function
Function fPeekInt()
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	Return PeekInt(CurrentBank\Bank,CurrentBank\BankIndex)
End Function
Function fPeekFloat#()
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	Return PeekFloat(CurrentBank\Bank,CurrentBank\BankIndex)
End Function
Function fPeekShort()
	CurrentBank\BankIndex = CurrentBank\BankIndex+4
	Return PeekShort(CurrentBank\Bank,CurrentBank\BankIndex)
End Function
Function SkipPeek(count = 1)
	CurrentBank\BankIndex = CurrentBank\BankIndex+(count*4)
End Function
Function ReadBank(srv)
	ResizeBank CurrentBank\Bank,ReadInt(srv)
	ReadBytes(CurrentBank\Bank, srv,0,BankSize(CurrentBank\Bank))
End Function
Function SendBank(srv)
	WriteInt srv,CurrentBank\BankIndex
	WriteBytes(CurrentBank\Bank,srv,0,CurrentBank\BankIndex)
End Function