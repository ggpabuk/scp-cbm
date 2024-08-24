Const EASY_BANK_STANDART_SIZE = 0
Type e_bank
	Field bank%
	Field inbank%
	Field readed%
End Type
; --------------------------------------------- creating/removing
	Function e_CreateBank.e_bank()
		Local bnk.e_bank = New e_bank
		bnk\bank = CreateBank(EASY_BANK_STANDART_SIZE)
		Return bnk
	End Function
	Function e_FreeBank(bnk.e_bank)
		FreeBank bnk\bank
		Delete bnk
	End Function
	Function e_Convert.e_bank(bank)
		Local bnk.e_bank = New e_bank
		bnk\bank = bank
		bnk\inbank = BankSize(bank)
		Return bnk
	End Function
;---------------------------------------------- poke
	Function e_PokeByte(bnk.e_bank, value%)
		bnk\inbank = bnk\inbank+1
		ResizeBank bnk\bank, bnk\inbank
		PokeByte bnk\bank, bnk\inbank-1, value
	End Function
	Function e_PokeShort(bnk.e_bank, value%)
		bnk\inbank = bnk\inbank+2
		ResizeBank bnk\bank, bnk\inbank
		PokeShort bnk\bank, bnk\inbank-2, value
	End Function
	Function e_PokeInt(bnk.e_bank, value%)
		bnk\inbank = bnk\inbank+4
		ResizeBank bnk\bank, bnk\inbank
		PokeInt bnk\bank, bnk\inbank-4, value
	End Function
	Function e_PokeFloat(bnk.e_bank, value#)
		bnk\inbank = bnk\inbank+4
		ResizeBank bnk\bank, bnk\inbank
		PokeFloat bnk\bank, bnk\inbank-4, value
	End Function
	Function e_PokeLine#(bnk.e_bank, value$)
		e_PokeByte bnk, Len(value)
		For i = 1 To Len(value)-1
			e_PokeByte bnk, Asc(Mid(value, 1, i))
		Next
	End Function
	Function e_FreePoke(bnk.e_bank)
		bnk\inbank = 0
		ResizeBank bnk\bank, 0
	End Function
; ----------------------------------------------- peek
	Function e_PeekByte%(bnk.e_bank)
		bnk\readed = bnk\readed+1
		Return PeekByte(bnk\bank, bnk\readed-1)
	End Function
	Function e_PeekShort%(bnk.e_bank)
		bnk\readed = bnk\readed+2
		Return PeekShort(bnk\bank, bnk\readed-2)
	End Function
	Function e_PeekInt%(bnk.e_bank)
		bnk\readed = bnk\readed+4
		Return PeekInt(bnk\bank, bnk\readed-4)
	End Function
	Function e_PeekFloat#(bnk.e_bank)
		bnk\readed = bnk\readed+4
		Return PeekFloat(bnk\bank, bnk\readed-4)
	End Function
	Function e_PeekString#(bnk.e_bank)
		Local returnstr$
		Local size = e_PeekByte(bnk)
		For i = 0 To size-1
			returnstr = returnstr + Chr(e_PeekByte(bnk))
		Next
		Return returnstr$
	End Function
	Function e_FreePeek(bnk.e_bank)
		bnk\readed = 0
	End Function
; ---------------------------------------------- compression
	Function e_BankCompress(bnk.e_bank, compressiontype)
		Return 1
		Local compressedbank = ZipApi_Compress(bnk\bank, compressiontype)
		if compressedBank > 0 Then
			FreeBank bnk\bank
			bnk\bank = compressedBank
		EndIf
	End Function
	Function e_BankUnCompress(bnk.e_bank, sourceSize)
		Local uncompressedbank = ZipApi_UnCompress(bnk\bank, sourceSize)
		CurrentUnCompressedBank = uncompressedbank
		if uncompressedbank > 0 Then
			FreeBank bnk\bank
			bnk\bank = uncompressedbank
			bnk\inbank = sourceSize
			Return 1
		EndIf
		Return 0
	End Function
; ---------------------------------------------- other
	Function e_pointer(bnk.e_bank)
		Return bnk\bank
	End Function
	Function e_BankSize(bnk.e_bank)
		Return bnk\inbank
	End Function
	Function e_ResizeBank(bnk.e_bank, value)
		bnk\inbank = value
		ResizeBank bnk\bank, value
	End Function