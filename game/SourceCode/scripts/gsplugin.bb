Const P_TYPE_BYTE = 1
Const P_TYPE_SHORT = 2

Const P_TYPE_INT = 3
Const P_TYPE_FLOAT = 4
Const P_TYPE_STRING = 5

Const MAX_PLUGINS = 64
Global lastpluginid = 0
Global plugins.plugin[MAX_PLUGINS]
Type plugin
	Field ID
	Field pluginname$
	Field bank
End Type
Function plugin_remove(pluginid)
	if plugins[pluginid]\bank <> 0 Then FreeBank plugins[pluginid]\bank
	Delete plugins[pluginid]
End Function
Function plugin_poke(pluginid, value$, i_type)
	p.plugin = plugins[pluginid]
	if p <> Null Then
		Select i_type
			Case P_TYPE_BYTE
				ResizeBank p\bank, BankSize(p\bank)+1
				PokeByte p\bank, BankSize(p\bank)-1, value
			Case P_TYPE_SHORT
				ResizeBank p\bank, BankSize(p\bank)+2
				PokeShort p\bank, BankSize(p\bank)-2, value
			Case P_TYPE_INT
				ResizeBank p\bank, BankSize(p\bank)+4
				PokeInt p\bank, BankSize(p\bank)-4, value
			Case P_TYPE_FLOAT
				ResizeBank p\bank, BankSize(p\bank)+4
				PokeFloat p\bank, BankSize(p\bank)-4, value
			Case P_TYPE_STRING
				Local bsize = BankSize(p\bank)
				ResizeBank p\bank, BankSize(p\bank)+Len(value)
				For i = 1 To Len(value)
					PokeByte p\bank,bsize+i-1,Asc(Mid$(value,i,1))
				Next
			Default:
				Return 0
		End Select
		Return 1
	EndIf
	Return 0
End Function
Function plugin_init(pluginname$)
	Local p.plugin = New plugin
	lastpluginid = lastpluginid + 1
	p\ID = lastpluginid
	p\pluginname = pluginname$
	p\bank = CreateBank(0)
	plugins[p\ID] = p
	Return p\ID
End Function
Function plugin_call$(pluginid, functionname$, returnType)
	p.plugin = plugins[pluginid]
	Local returnbank% = CreateBank(255), returned$, returned2$
	if p <> Null Then
		if BankSize(p\bank) <> 0 Then
			returned2 = CallDLL(p\pluginname,functionname, p\bank, returnbank)
			DebugLog "Retrun bank"
		Else
			returned2 = CallDLL(p\pluginname,functionname, 0, returnbank)
		EndIf
	EndIf
	plugin_clear(pluginid)
	Select returnType
		Case P_TYPE_BYTE
			returned = PeekByte(returnbank, 0)
		Case P_TYPE_SHORT
			returned = PeekShort(returnbank, 0)
		Case P_TYPE_INT
			returned = PeekInt(returnbank, 0)
		Case P_TYPE_FLOAT
			returned = PeekFloat(returnbank, 0)
		Case P_TYPE_STRING
			returned = PeekString_work(returnbank, 0)
	End Select
	FreeBank returnbank
	if returned = "" or returned = 0 Then Return returned2
	Return returned
End Function
Function plugin_clear(pluginid)
	p.plugin = plugins[pluginid]
	if p <> Null Then
		ResizeBank p\bank, 0
		Return 1
	EndIf
	Return 0
End Function
Function PeekString_work$(bankHandle, offset)
	Local result$, char
	While offset < BankSize(bankHandle)
		char = PeekByte(bankhandle, offset)
		if char = 13 Or char = 0 Then Exit
		if char <> 10 Then result = result + char
		offset = offset+1
	Wend
	Return result
End Function