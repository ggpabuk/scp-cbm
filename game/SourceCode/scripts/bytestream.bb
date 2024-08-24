; // New system
Type bs
	Field bank
	Field offset
	Field inbank
	Field readed
End Type
Function CreateByteStream.bs(maxsize)
	Local bstream.bs = New bs
	bstream\bank = CreateBank(maxsize)
	Return bstream
End Function
Function RemoveByteStream(bstream.bs)
	FreeBank bstream\bank
	Delete bstream
End Function
Function ByteStreamWriteChar(bstream.bs, value%)
	bstream\inbank = bstream\inbank+1
	bstream\offset = bstream\offset+1
	PokeByte bstream\bank, bstream\offset-1, value
End Function
Function ByteStreamWriteShort(bstream.bs, value%)
	bstream\inbank = bstream\inbank+2
	bstream\offset = bstream\offset+2
	PokeShort bstream\bank, bstream\offset-2, value
End Function
Function ByteStreamWriteInt(bstream.bs, value%)
	bstream\inbank = bstream\inbank+4
	bstream\offset = bstream\offset+4
	PokeInt bstream\bank, bstream\offset-4, value
End Function
Function ByteStreamWriteFloat(bstream.bs, value#)
	bstream\inbank = bstream\inbank+4
	bstream\offset = bstream\offset+4
	PokeFloat bstream\bank, bstream\offset-4, value
End Function
Function ByteStreamWriteString(bstream.bs, value$)
	ByteStreamWriteChar(bstream, Len(value))
	For i = 1 To Len(value)
		ByteStreamWriteChar bstream, Asc(Mid(value, i, 1))
	Next
End Function
Function ByteStreamWriteLine(bstream.bs, value$)
	For i = 1 To Len(value)
		ByteStreamWriteChar bstream, Asc(Mid(value, i, 1))
	Next
	ByteStreamWriteChar bstream, 13
	ByteStreamWriteChar bstream, 10
End Function

Function ByteStreamReadChar%(bstream.bs)
	bstream\readed = bstream\readed+1
	Return PeekByte( bstream\bank, bstream\readed-1)
End Function

Function ByteStreamReadShort%(bstream.bs)
	bstream\readed = bstream\readed+2
	Return PeekShort (bstream\bank, bstream\readed-2)
End Function

Function ByteStreamReadInt%(bstream.bs)
	bstream\readed = bstream\readed+4
	Return PeekInt (bstream\bank, bstream\readed-4)
End Function

Function ByteStreamReadFloat#(bstream.bs)
	bstream\readed = bstream\readed+4
	Return PeekFloat (bstream\bank, bstream\readed-4)
End Function

Function ByteStreamReadString$(bstream.bs)
	Local ret$
	Local c = ByteStreamReadChar(bstream)
	For i = 1 To c
		ret = ret + Chr(ByteStreamReadChar(bstream))
	Next
	Return ret
End Function

Function ByteStreamReadLine$(bstream.bs)
	Local ret$, token$
	While True
		token = ByteStreamReadChar(bstream)
		if token = 0 Or token = 10 Then Exit
		if token <> 13 then ret = ret+token
	Wend
	Return ret
End Function

Function ByteStreamSetWriteOffset(bstream.bs, offset)
	bstream\offset = offset
End Function

Function ByteStreamSetReadOffset(bstream.bs, offset)
	bstream\readed = offset
End Function

Function ByteStreamReset(bstream.bs)
	bstream\offset = 0
	bstream\inbank = 0
End Function

Function ByteStreamResetRead(bstream.bs)
	bstream\readed = 0
End Function

;Function ByteStreamSend(bstream.bs, ID)
;	WriteBytes bstream\bank, stream, 0, bstream\inbank
;	SendUDPMsg(stream, Player[ID]\IP, Player[ID]\Port)
;End Function

;Function ByteStreamSendToAll(bstream.bs)
;	For i = 1 To MAX_PLAYERS-1
;		WriteBytes bstream\bank, stream, 0, bstream\inbank
;		SendUDPMsg(stream, Player[i]\IP, Player[i]\Port)
;	Next
;End Function

Function GetByteStreamDataSize(bstream.bs)
	Return bstream\inbank
End Function

Function GetByteStreamData(bstream.bs)
	Return bstream\bank
End Function