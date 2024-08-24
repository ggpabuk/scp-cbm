;========================================
;				MATH
;========================================

Function SE_BL_Math(FunctionName$)
	Select Lower(FunctionName)
		Case "int"
			SE_ReturnInt(SE_ToIntArg(0))

		Case "float"
			SE_ReturnFloat(SE_ToFloatArg(0))
			
		Case "str"
			SE_ReturnString(SE_ToFloatArg(0))

		Case "floor"
			SE_ReturnFloat(Floor(SE_ToFloatArg(0)))

		Case "ceil"
			SE_ReturnFloat(Ceil(SE_ToFloatArg(0)))

		Case "sgn"
			SE_ReturnInt(Sgn(SE_ToIntArg(0)))

		Case "abs"
			If SE_ArgType(0)=SE_INT
				SE_ReturnInt(Ceil(SE_IntArg(0)))	
			Else
				SE_ReturnFloat(Ceil(SE_ToFloatArg(0)))
			EndIf

		Case "sqr"
			SE_ReturnFloat(Sqr(SE_ToFloatArg(0)))

		Case "sin"
			SE_ReturnFloat(Sin(SE_ToFloatArg(0)))

		Case "cos"
			SE_ReturnFloat(Cos(SE_ToFloatArg(0)))

		Case "tan"
			SE_ReturnFloat(Tan(SE_ToFloatArg(0)))

		Case "asin"
			SE_ReturnFloat(ASin(SE_ToFloatArg(0)))

		Case "acos"
			SE_ReturnFloat(ACos(SE_ToFloatArg(0)))

		Case "atan"
			SE_ReturnFloat(ATan(SE_ToFloatArg(0)))

		Case "atan2"
			SE_ReturnFloat(ATan2(SE_ToFloatArg(0), SE_ToFloatArg(1)))

		Case "exp"
			SE_ReturnFloat(Exp(SE_ToFloatArg(0)))

		Case "log"
			SE_ReturnFloat(Log(SE_ToFloatArg(0)))

		Case "log10"
			SE_ReturnFloat(Log10(SE_ToFloatArg(0)))

		Case "rnd"
			SE_ReturnFloat(Rnd(SE_ToFloatArg(0), SE_ToFloatArg(1)))

		Case "rand"
			SE_ReturnInt(Rand(SE_ToIntArg(0), SE_ToIntArg(1)))

		Default
			Return False
	End Select

	Return True
End Function

;========================================
;				STRING
;========================================

Function SE_BL_Str(FunctionName$)
	Select Lower(FunctionName)
		Case "num"
			SE_ReturnString(SE_ToStringArg(0))

		Case "left"
			SE_ReturnString(Left(SE_ToStringArg(0), SE_ToIntArg(1)))

		Case "right"
			SE_ReturnString(Right(SE_ToStringArg(0), SE_ToIntArg(1)))

		Case "mid"
			SE_ReturnString(Mid(SE_ToStringArg(0), SE_ToIntArg(1), SE_ToIntArg(2)))

		Case "replace"
			SE_ReturnString(Replace(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToStringArg(2)))

		Case "instr"
			SE_ReturnInt(Instr(SE_ToStringArg(0), SE_ToStringArg(1), SE_ToIntArg(2)))
		Case "lower"
			SE_ReturnString(Lower(SE_ToStringArg(0)))

		Case "upper"
			SE_ReturnString(Upper(SE_ToStringArg(0)))

		Case "trim"
			SE_ReturnString(Trim(SE_ToStringArg(0)))

		Case "chr"
			SE_ReturnString(Chr(SE_ToIntArg(0)))

		Case "chr"
			SE_ReturnString(Chr(SE_ToIntArg(0)))

		Case "asc"
			SE_ReturnInt(Asc(SE_ToStringArg(0)))

		Case "hex"
			SE_ReturnString(Hex(SE_ToIntArg(0)))

		Case "bin"
			SE_ReturnString(Bin(SE_ToIntArg(0)))

		Case "repeat"
			SE_ReturnString(String(SE_ToStringArg(0), SE_ToIntArg(1)))
		Default
			Return False
	End Select
	Return True
End Function

;========================================
;				ARRAY
;========================================

Function SE_BL_Array_Create()
	Local Array.SE_Array=SE_Array_Create()
	Local Length=SE_ToIntArg(0)

	For I=1 To Length
		SE_Array_AddElement(Array)
	Next

	SE_ReturnArray(Array)
End Function

Function SE_BL_Array_Push()
	If SE_ArgType(0)<>SE_ARRAY Then Return
	
	Local Array.SE_Array=SE_ArrayArg(0)

	Local Args=SE_ARGUMENTS_NUMBER-1

	For Index=1 To Args
		Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
		Local Element.SE_Value=SE_Array_AddElement(Array)
		
		Element\ValueType=Argument\ValueType
		
		Select Argument\ValueType
			Case SE_INT
				Element\IntValue=Argument\IntValue
				
			Case SE_FLOAT
				Element\FloatValue=Argument\FloatValue
				
			Case SE_STRING
				Element\StringValue=Argument\StringValue
				
			Case SE_POINTER
				Element\Pointer=Argument\Pointer
				
			Case SE_ARRAY
				Element\Array=Argument\Array
		End Select
	Next
End Function

Function SE_BL_Array_Pop()
	If SE_ArgType(0)<>SE_ARRAY Then Return
	
	Local Array.SE_Array=SE_ArrayArg(0)

	If Array\Elements=0 Then Return

	Local Index=Array\Elements-1
	
	Local Element.SE_Value=Object.SE_Value(PeekInt(Array\Bank, Index*4))

	Select Element\ValueType
		Case SE_INT
			SE_ReturnInt(Element\IntValue)
			
		Case SE_FLOAT
			SE_ReturnFloat(Element\FloatValue)
			
		Case SE_STRING
			SE_ReturnString(Element\StringValue)
			
		Case SE_POINTER
			SE_ReturnPointer(Element\Pointer)
			
		Case SE_ARRAY
			SE_ReturnArray(Element\Array)
	End Select

	Array\Elements=Array\Elements-1
	Local Bank=Array\Bank
	Local Size=Array\Elements*4
	Local Offset=Index*4
		
	SE_GCCheck(Element)
	Delete Element
	
	Local NewBank=CreateBank(Size)

	CopyBank Bank, 0, NewBank, 0, Size
	
	FreeBank Array\Bank
	Array\Bank=NewBank
End Function

Function SE_BL_Array_Delete()
	If SE_ArgType(0)<>SE_ARRAY Then Return
	
	Local Array.SE_Array=SE_ArrayArg(0)

	Local Args=SE_ARGUMENTS_NUMBER-1

	For Index=1 To Args
		Local ElementIndex=SE_ToIntArg(Index)

		SE_Array_FreeElement(Array, ElementIndex)
	Next
End Function

Function SE_BL_Array_FromString()
	Local Array.SE_Array
	Local S$=SE_ToStringArg(0)
	Local Length=Len(S)

	If Length=0 Then Return

	Array=SE_Array_Create()

	For I=1 To Length
		Local V.SE_Value=SE_Array_AddElement(Array)
		V\ValueType=SE_STRING
		V\StringValue=Mid(S, I, 1)
	Next

	SE_ReturnArray(Array)
End Function

Function SE_BL_Array_Sort()
	If SE_ArgType(0)<>SE_ARRAY Then Return
	
	Local Array.SE_Array=SE_ArrayArg(0)
	Local Ascending=SE_ToIntArg(1, True)

	Local Length=Array\Elements
	Local Index
	Local MIndex

	Local M.SE_Value, MF.SE_Value
	Local K.SE_Value, KF.SE_Value

	Local Result

	If Ascending
		Repeat
			M=Object.SE_Value(PeekInt(Array\Bank, Index*4))
			MF=SE_GetFinalValue(M)
			MIndex=-1
	
			For I=Index+1 To Length-1
				K=Object.SE_Value(PeekInt(Array\Bank, I*4))
				KF=SE_GetFinalValue(K)
	
				Result=0
				
				Select KF\ValueType
					Case SE_NULL
						If MF\ValueType=SE_NULL
							Result=0
						Else
							Result=1
						EndIf
						
					Case SE_INT
						Select MF\ValueType
							Case SE_NULL
								Result=0
							Case SE_INT
								Result=(KF\IntValue<MF\IntValue)
							Case SE_FLOAT
								Result=(KF\IntValue<MF\FloatValue)
							Case SE_STRING
								Result=(KF\IntValue<MF\StringValue)
						End Select
						
					Case SE_FLOAT
						Select MF\ValueType
							Case SE_NULL
								Result=0
							Case SE_INT
								Result=(KF\FloatValue<MF\IntValue)
							Case SE_FLOAT
								Result=(KF\FloatValue<MF\FloatValue)
							Case SE_STRING
								Result=(KF\FloatValue<MF\StringValue)
						End Select
						
					Case SE_STRING
						Select MF\ValueType
							Case SE_NULL
								Result=0
							Case SE_INT
								Result=(KF\StringValue<MF\IntValue)
							Case SE_FLOAT
								Result=(KF\StringValue<MF\FloatValue)
							Case SE_STRING
								Result=(KF\StringValue<MF\StringValue)
						End Select
						
					Case SE_ARRAY
						If MF\ValueType=SE_ARRAY
							Result=(KF\Array\Elements<MF\Array\Elements)
						Else
							Result=0
						EndIf
				End Select
	
				If Result
					MIndex=I
					MF=KF
					M=K
				EndIf
			Next
	
			If MIndex>=0
				PokeInt Array\Bank, MIndex*4, PeekInt(Array\Bank, Index*4)
				PokeInt Array\Bank, Index*4, Handle(M)
			EndIf
	
			Index=Index+1
			If Index=Length Then Exit
		Forever
	Else
		Repeat
			M=Object.SE_Value(PeekInt(Array\Bank, Index*4))
			MF=SE_GetFinalValue(M)
			MIndex=-1
	
			For I=Index+1 To Length-1
				K=Object.SE_Value(PeekInt(Array\Bank, I*4))
				KF=SE_GetFinalValue(K)
	
				Result=0
				
				Select KF\ValueType
					Case SE_NULL
						Result=0	;(null>type or null>null)=false
						
					Case SE_INT
						Select MF\ValueType
							Case SE_NULL
								Result=0
							Case SE_INT
								Result=(KF\IntValue>MF\IntValue)
							Case SE_FLOAT
								Result=(KF\IntValue>MF\FloatValue)
							Case SE_STRING
								Result=(KF\IntValue>MF\StringValue)
						End Select
						
					Case SE_FLOAT
						Select MF\ValueType
							Case SE_NULL
								Result=0
							Case SE_INT
								Result=(KF\FloatValue>MF\IntValue)
							Case SE_FLOAT
								Result=(KF\FloatValue>MF\FloatValue)
							Case SE_STRING
								Result=(KF\FloatValue>MF\StringValue)
						End Select
						
					Case SE_STRING
						Select MF\ValueType
							Case SE_NULL
								Result=0
							Case SE_INT
								Result=(KF\StringValue>MF\IntValue)
							Case SE_FLOAT
								Result=(KF\StringValue>MF\FloatValue)
							Case SE_STRING
								Result=(KF\StringValue>MF\StringValue)
						End Select
						
					Case SE_ARRAY
						If MF\ValueType=SE_ARRAY
							Result=(KF\Array\Elements>MF\Array\Elements)
						Else
							Result=0
						EndIf
				End Select
	
				If Result
					MIndex=I
					MF=KF
					M=K
				EndIf
			Next
	
			If MIndex>=0
				PokeInt Array\Bank, MIndex*4, PeekInt(Array\Bank, Index*4)
				PokeInt Array\Bank, Index*4, Handle(M)
			EndIf
	
			Index=Index+1
			If Index=Length Then Exit
		Forever
	EndIf
End Function

Function SE_BL_Array(FunctionName$)
	If Left(FunctionName, 6)="array_"
		FunctionName=Right(FunctionName, Len(FunctionName)-6)
	Else
		Return False
	EndIf

	Select Lower(FunctionName)
		Case "create"
			SE_BL_Array_Create()

		Case "push"
			SE_BL_Array_Push()

		Case "pop"
			SE_BL_Array_Pop()

		Case "delete"
			SE_BL_Array_Delete()

		Case "sort"
			SE_BL_Array_Sort()

		Case "from_string"
			SE_BL_Array_FromString()
	End Select

	Return True
End Function