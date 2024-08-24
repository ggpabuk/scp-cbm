;	Skynet++ (Modification with linked list)

;=====================================
;			Virtual machine
;=====================================

Const SE_VERSION$ = "1.0.1"

;	Instructions
Const SE_MOV=1		;Arithmetic operations
Const SE_ADD=2
Const SE_SUB=3
Const SE_MUL=4
Const SE_DIV=5
Const SE_POW=6
Const SE_MOD=7
Const SE_NEG=8
Const SE_INC=9
Const SE_DEC=10

Const SE_CE=11		;Comparsion operators
Const SE_CNE=12
Const SE_CG=13
Const SE_CL=14
Const SE_CGE=15
Const SE_CLE=16

Const SE_AND=17		;Bitwise operations
Const SE_XOR=18
Const SE_OR=19
Const SE_BN=20		;NOT
Const SE_SHL=21
Const SE_SHR=22

Const SE_NOT=23		;Logic NOT

Const SE_JMP=24		;Code jump operations
Const SE_JIT=25
Const SE_JIF=26

Const SE_MTA=27		;Function invocation operations
Const SE_INVU=28
Const SE_INVG=29
Const SE_RET=30
Const SE_END=31

Const SE_GP=32		;Get pointer

Const SE_VT=33		;Value type

Const SE_ARGS=34	;Function arguments

Const SE_CA=35		;Array operations
Const SE_ACC=36
Const SE_LEN=37


;	Instruction value types
Const SE_NULL=0
Const SE_INT=1
Const SE_FLOAT=2
Const SE_STRING=3
Const SE_TRANSIENT=4
Const SE_POINTER=5
Const SE_ACCESSOR=6
Const SE_ARRAY=7
Const SE_LABEL=8
Const SE_FUNC_PTR=9

Const SE_STATIC=10	;Used only in Virtual File

;	Script object
Const MAX_SCRIPT_PUBLICS = 256

Type SE_Script
	Field FirstInst.SE_Inst, LastInst.SE_Inst									;	Linked list of instructions
	Field FirstFuncPtr.SE_FuncPtr, LastFuncPtr.SE_FuncPtr, Main.SE_FuncPtr		;	Linked list of functions
	Field FirstPublic.SE_Public, LastPublic.SE_Public							;	Linked list of public variables
	Field StaticValues
	Field ScriptPublic.SE_FuncPtr[MAX_SCRIPT_PUBLICS]
	Field Version$
End Type

Type SE_Inst
	Field Code
	Field A.SE_Value
	Field B.SE_Value
	Field C.SE_Value

	Field PrevInst.SE_Inst, NextInst.SE_Inst
End Type

Type SE_Value
	Field ValueType
	Field IntValue%
	Field FloatValue#
	Field StringValue$
	Field Pointer.SE_Value
	Field Array.SE_Array
	Field Label.SE_Inst
	Field FuncPtr.SE_FuncPtr
	Field StaticIndex
End Type

Type SE_FuncPtr
	Field Name$
	Field Script.SE_Script
	Field FirstInst.SE_Inst, LastInst.SE_Inst
	Field TransientValues
	Field Arguments

	Field PrevFuncPtr.SE_FuncPtr, NextFuncPtr.SE_FuncPtr
End Type

Type SE_Public
	Field Name$
	Field Value.SE_Value

	Field PrevPublic.SE_Public, NextPublic.SE_Public
End Type

;Function AddLast(List.TList, Item.TItem)
;	If List\LastItem<>Null
;		Item\PrevItem=List\LastItem
;		List\LastItem\NextItem=Item
;		List\LastItem=Item
;	Else
;		List\FirstItem=Item
;		List\LastItem=Item
;	EndIf
;End Function

Function SE_CreateInst.SE_Inst(Script.SE_Script, Code, A.SE_Value, B.SE_Value, C.SE_Value)
	Local Inst.SE_Inst=New SE_Inst
	Inst\Code=Code
	Inst\A=A
	Inst\B=B
	Inst\C=C

	If Script\LastInst<>Null
		Inst\PrevInst=Script\LastInst
		Script\LastInst\NextInst=Inst
		Script\LastInst=Inst
	Else
		Script\FirstInst=Inst
		Script\LastInst=Inst
	EndIf
	
	Return Inst
End Function

Function SE_CreateFuncPtr.SE_FuncPtr(Script.SE_Script, Name$, FirstInst.SE_Inst, LastInst.SE_Inst, TransientValues, Arguments)
	Local FuncPtr.SE_FuncPtr=New SE_FuncPtr
	
	FuncPtr\Name=Name
	FuncPtr\Script=Script
	FuncPtr\FirstInst=FirstInst
	FuncPtr\LastInst=LastInst
	FuncPtr\TransientValues=TransientValues
	FuncPtr\Arguments=Arguments

	If Script\LastFuncPtr<>Null
		FuncPtr\PrevFuncPtr=Script\LastFuncPtr
		Script\LastFuncPtr\NextFuncPtr=FuncPtr
		Script\LastFuncPtr=FuncPtr
	Else
		Script\FirstFuncPtr=FuncPtr
		Script\LastFuncPtr=FuncPtr
	EndIf

	Return FuncPtr
End Function

Function SE_CreatePublic.SE_Public(Script.SE_Script, Name$, Value.SE_Value)
	Local Public.SE_Public=New SE_Public

	Public\Name=Name
	Public\Value=Value


	If Script\LastPublic<>Null
		Public\PrevPublic=Script\LastPublic
		Script\LastPublic\NextPublic=Public
		Script\LastPublic=Public
	Else
		Script\FirstPublic=Public
		Script\LastPublic=Public
	EndIf

	Return Public
End Function

;	Array
Type SE_Array
	Field References
	Field Elements
	Field Bank
End Type

Function SE_Array_Create.SE_Array()
	Local Array.SE_Array=New SE_Array
	Array\Bank=CreateBank()
	Return Array
End Function

Function SE_Array_AddElement.SE_Value(Array.SE_Array)
	Local V.SE_Value=New SE_Value
	
	Local Offset=Array\Elements*4
	Array\Elements=Array\Elements+1
	ResizeBank Array\Bank, Array\Elements*4
	PokeInt Array\Bank, Offset, Handle(V)
	
	Return V
End Function

Function SE_Array_AddElements.SE_Value(Array.SE_Array, loops, type_value = SE_INT)
	if type_value = 0 Then type_value = SE_INT
	For i = 1 To loops
		Local V.SE_Value=New SE_Value
		SE_SetValue(V, 0, type_value)
		
		Local Offset=Array\Elements*4
		Array\Elements=Array\Elements+1
		ResizeBank Array\Bank, Array\Elements*4
		PokeInt Array\Bank, Offset, Handle(V)
	Next
End Function

Function SE_Array_FreeElement(Array.SE_Array, Index)
	If Array\Elements=0 Then Return
	
	If Index>=0 And Index<=Array\Elements
		Array\Elements=Array\Elements-1
		Local Bank=Array\Bank
		Local Size=Array\Elements*4
		Local Offset=Index*4
		
		Local Value.SE_Value=Object.SE_Value(PeekInt(Bank, Offset))
		SE_GCCheck(Value)
		Delete Value
		
		Local NewBank=CreateBank(Size)
		
		If Offset=0
			CopyBank Bank, 4, NewBank, 0, Size
			
		Else If Offset=Size
			CopyBank Bank, 0, NewBank, 0, Size
			
		Else
			CopyBank Bank, 0, NewBank, 0, Offset
			CopyBank Bank, Offset+4, NewBank, Offset, Size-Offset
		EndIf
		
		FreeBank Array\Bank
		Array\Bank=NewBank
	EndIf
End Function

Function SE_Array_GetElement.SE_Value(Array.SE_Array, Index)
	If Array\Elements=0 Then Return
	
	If Index>=0 And Index<Array\Elements
		Local Value.SE_Value=Object.SE_Value(PeekInt(Array\Bank, Index*4))
		Return Value
	EndIf
End Function

Function SE_Array_Delete(Array.SE_Array)
	Local Bank=Array\Bank
	Local EndOffset=(Array\Elements-1)*4
	Local Offset
			
	For Offset=0 To EndOffset Step 4
		Local Value.SE_Value=Object.SE_Value(PeekInt(Bank, Offset))
		SE_GCCheck(Value)
		Delete Value
	Next
			
	FreeBank Bank
End Function



;	Garbage collector
Function SE_GCCheck(Value.SE_Value)
	If Value\ValueType=SE_ARRAY
		Value\Array\References=Value\Array\References-1
	EndIf
End Function

Function SE_GCCollect()
	Local Array.SE_Array

	For Array=Each SE_Array
		If Array\References=0
			SE_Array_Delete(Array)
		EndIf
	Next
End Function




Global SE_SCRIPT_ACCESS_INVOKE = True

;	Current processing
Global SE_CURRENT_SCRIPT.SE_Script
Global SE_CURRENT_FUNCTION.SE_FuncPtr
Global SE_RETURN_VALUE.SE_Value
Global SE_CURRENT_CALLED_FUNCTION.SE_FuncPtr

;	Transient values stack
Global SE_TRANSIENT_STACK_SIZE=256
Global SE_TRANSIENT_STACK_LEVEL
Global SE_TRANSIENT_STACK_OFFSET

Dim SE_TRANSIENT_STACK.SE_Value(0)
Dim SE_AUX_TRANSIENT_STACK.SE_Value(0)

;	Arguments values stack
Global SE_ARGUMENTS_STACK_SIZE=32
Global SE_ARGUMENTS_STACK_LEVEL
Global SE_ARGUMENTS_STACK_OFFSET		;=arguments_stack_level-arguments_number
Global SE_ARGUMENTS_NUMBER				;for current function invocation

Dim SE_ARGUMENTS_STACK.SE_Value(0)
Dim SE_AUX_ARGUMENTS_STACK.SE_Value(0)

SE_RETURN_VALUE=New SE_Value

Function SE_Init()
	if SE_ARGUMENTS_STACK(0) <> null then return
	
	;Initialize transient stack
	Dim SE_TRANSIENT_STACK(SE_TRANSIENT_STACK_SIZE)
	Dim SE_AUX_TRANSIENT_STACK(SE_TRANSIENT_STACK_SIZE)

	Local I
	
	For I=0 To SE_TRANSIENT_STACK_SIZE
		SE_TRANSIENT_STACK(I)=New SE_Value
	Next

	;Initialize arguments stack
	Dim SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_SIZE)
	Dim SE_AUX_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_SIZE)
	
	For I=0 To SE_ARGUMENTS_STACK_SIZE
		SE_ARGUMENTS_STACK(I)=New SE_Value
	Next
End Function

Function SE_GrowTransient()
	If SE_TRANSIENT_STACK_LEVEL>SE_TRANSIENT_STACK_SIZE		;stack  overflow
	
		adderrorlog("Transient stack overflow! ("+SE_TRANSIENT_STACK_LEVEL+","+SE_TRANSIENT_STACK_SIZE+")")
		
		Local I
		Dim SE_AUX_TRANSIENT_STACK(SE_TRANSIENT_STACK_SIZE)

		;temporary move transient stack values to aux stack
		For I=0 To SE_TRANSIENT_STACK_SIZE
			SE_AUX_TRANSIENT_STACK(I)=SE_TRANSIENT_STACK(I)
		Next

		;resize transient stack array
		Dim SE_TRANSIENT_STACK(SE_TRANSIENT_STACK_LEVEL)

		;move transient stack values back to main stack
		For I=0 To SE_TRANSIENT_STACK_SIZE
			SE_TRANSIENT_STACK(I)=SE_AUX_TRANSIENT_STACK(I)
		Next

		;add new values
		For I=SE_TRANSIENT_STACK_SIZE+1 To SE_TRANSIENT_STACK_LEVEL
			SE_TRANSIENT_STACK(I)=New SE_Value
		Next

		SE_TRANSIENT_STACK_SIZE=SE_TRANSIENT_STACK_LEVEL
	EndIf
End Function

Function SE_GrowArguments()
	If SE_ARGUMENTS_STACK_LEVEL>SE_ARGUMENTS_STACK_SIZE		;stack  overflow
		adderrorlog("Arguments stack overflow! ("+SE_ARGUMENTS_STACK_LEVEL+","+SE_ARGUMENTS_STACK_SIZE+")")
		
		Local I
		Dim SE_AUX_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_SIZE)

		;temporary move arguments stack values to aux stack
		For I=0 To SE_ARGUMENTS_STACK_SIZE
			SE_AUX_ARGUMENTS_STACK(I)=SE_ARGUMENTS_STACK(I)
		Next

		;resize arguments stack array
		Dim SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL)

		;move arguments stack values back to main stack
		For I=0 To SE_ARGUMENTS_STACK_SIZE
			SE_ARGUMENTS_STACK(I)=SE_AUX_ARGUMENTS_STACK(I)
		Next

		;add new values
		For I=SE_ARGUMENTS_STACK_SIZE+1 To SE_ARGUMENTS_STACK_LEVEL
			SE_ARGUMENTS_STACK(I)=New SE_Value
		Next

		SE_ARGUMENTS_STACK_SIZE=SE_ARGUMENTS_STACK_LEVEL
	EndIf
End Function

Function SE_Array_Create_Inst(ElementsNumber, ReturnValue.SE_Value)
	Local Array.SE_Array=SE_Array_Create()
	Local Index

	If SE_ARGUMENTS_NUMBER
		Local Args=SE_ARGUMENTS_NUMBER-1
		if Args <> -1 Then
			if Args = 0 Then
				SE_Array_AddElements(Array, SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET)\IntValue)
			Elseif Args = 1 Then
				SE_Array_AddElements(Array, SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET)\IntValue, SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+1)\IntValue)
			Endif
		EndIf
	EndIf

	ReturnValue\ValueType=SE_ARRAY
	ReturnValue\Array=Array
End Function

Function SE_GetAccessorValue.SE_Value(Value.SE_Value)
	Value\ValueType=SE_NULL

	If Value\Pointer=Null
		Value\ValueType=SE_NULL
	Else
		Value=Value\Pointer
	EndIf
	
	Return Value
End Function

Function SE_GetFinalValue.SE_Value(Value.SE_Value)
	If Value\ValueType=SE_POINTER Or Value\ValueType=SE_ACCESSOR
		Value\ValueType=SE_NULL
		
		If Value\Pointer=Null
			Value\ValueType=SE_NULL
		Else
			Value=Value\Pointer
		EndIf
	EndIf

	Return Value
End Function

Function SE_InvokeUserFunction(FuncPtr.SE_FuncPtr, GCCollect = True)
	SE_CURRENT_FUNCTION=FuncPtr
	SE_CURRENT_SCRIPT=FuncPtr\Script

	Local Level=SE_TRANSIENT_STACK_LEVEL
	Local Offset=SE_TRANSIENT_STACK_OFFSET

	SE_TRANSIENT_STACK_OFFSET=SE_TRANSIENT_STACK_LEVEL
	SE_TRANSIENT_STACK_LEVEL=SE_TRANSIENT_STACK_LEVEL+SE_CURRENT_FUNCTION\TransientValues

	SE_GrowTransient()

	If SE_ARGUMENTS_NUMBER>0
		SE_ARGUMENTS_STACK_OFFSET=SE_ARGUMENTS_STACK_LEVEL-SE_ARGUMENTS_NUMBER
		
		Local Argument.SE_Value
		Local Transient.SE_Value
		
		For ArgumentIndex=0 To SE_CURRENT_FUNCTION\Arguments-1
			If ArgumentIndex=SE_ARGUMENTS_NUMBER Then Exit
			
			Argument=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+ArgumentIndex)
			Transient=SE_TRANSIENT_STACK(SE_TRANSIENT_STACK_OFFSET+ArgumentIndex)
			
			Select Argument\ValueType
				Case SE_INT
					Transient\ValueType=SE_INT
					Transient\IntValue=Argument\IntValue
					
				Case SE_FLOAT
					Transient\ValueType=SE_FLOAT
					Transient\FloatValue=Argument\FloatValue
					
				Case SE_STRING
					Transient\ValueType=SE_STRING
					Transient\StringValue=Argument\StringValue
					
				Case SE_POINTER
					Transient\ValueType=SE_POINTER
					Transient\Pointer=Argument\Pointer
					
				Case SE_ARRAY
					Transient\ValueType=SE_ARRAY
					Transient\Array=Argument\Array
					Argument\Array\References=Argument\Array\References+1
			End Select
		Next
	EndIf

	Local Inst.SE_Inst=SE_CURRENT_FUNCTION\FirstInst

	
	Local ValueA.SE_Value=Null
	Local ValueB.SE_Value=Null
	Local ValueC.SE_Value=Null
	Local Jump%

	Repeat
		

		ValueA = Inst\A
		ValueB = Inst\B
		ValueC = Inst\C

		Jump=0		;Jump performed

		If ValueA<>Null
			If ValueA\ValueType=SE_TRANSIENT
				ValueA=SE_TRANSIENT_STACK(SE_TRANSIENT_STACK_OFFSET+ValueA\IntValue)
			EndIf
		EndIf
		
		If ValueB<>Null
			If ValueB\ValueType=SE_TRANSIENT
				ValueB=SE_TRANSIENT_STACK(SE_TRANSIENT_STACK_OFFSET+ValueB\IntValue)
			EndIf
		EndIf
		
		If ValueC<>Null
			If ValueC\ValueType=SE_TRANSIENT
				ValueC=SE_TRANSIENT_STACK(SE_TRANSIENT_STACK_OFFSET+ValueC\IntValue)
			EndIf
		EndIf

		Select Inst\Code
			Case SE_MOV
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				SE_GCCheck(ValueA)
				
				Select ValueB\ValueType
					Case SE_NULL
						ValueA\ValueType=SE_NULL
					Case SE_INT
						ValueA\ValueType=SE_INT
						ValueA\IntValue=ValueB\IntValue
					Case SE_FLOAT
						ValueA\ValueType=SE_FLOAT
						ValueA\FloatValue=ValueB\FloatValue
					Case SE_STRING
						ValueA\ValueType=SE_STRING
						ValueA\StringValue=ValueB\StringValue
					Case SE_ARRAY
						ValueA\ValueType=SE_ARRAY
						ValueA\Array=ValueB\Array
						ValueA\Array\References=ValueA\Array\References+1
				End Select
				
			Case SE_ADD
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				Select ValueA\ValueType
					Case SE_NULL
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\ValueType=SE_INT
								ValueC\IntValue=0
							Case SE_INT
								ValueC\ValueType=SE_INT
								ValueC\IntValue=ValueB\IntValue
							Case SE_FLOAT
								ValueC\ValueType=SE_FLOAT
								ValueC\FloatValue=ValueB\FloatValue
							Case SE_STRING
								ValueC\ValueType=SE_STRING
								ValueC\StringValue="null"+ValueB\StringValue
							Default
								ValueC\ValueType=SE_STRING
								ValueC\StringValue="nullNaN"
						End Select
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\ValueType=SE_INT
								ValueC\IntValue=ValueA\IntValue
							Case SE_INT
								ValueC\ValueType=SE_INT
								ValueC\IntValue=ValueA\IntValue+ValueB\IntValue
							Case SE_FLOAT
								ValueC\ValueType=SE_FLOAT
								ValueC\FloatValue=ValueA\IntValue+ValueB\FloatValue
							Case SE_STRING
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\IntValue+ValueB\StringValue
							Default
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\IntValue+"NaN"
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\ValueType=SE_FLOAT
								ValueC\FloatValue=ValueA\FloatValue
							Case SE_INT
								ValueC\ValueType=SE_FLOAT
								ValueC\FloatValue=ValueA\FloatValue+ValueB\IntValue
							Case SE_FLOAT
								ValueC\ValueType=SE_FLOAT
								ValueC\FloatValue=ValueA\FloatValue+ValueB\FloatValue
							Case SE_STRING
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\FloatValue+ValueB\StringValue
							Default
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\FloatValue+"NaN"
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\StringValue+"null"
							Case SE_INT
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\StringValue+ValueB\IntValue
							Case SE_FLOAT
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\StringValue+ValueB\FloatValue
							Case SE_STRING
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\StringValue+ValueB\StringValue
							Default
								ValueC\ValueType=SE_STRING
								ValueC\StringValue=ValueA\StringValue+"NaN"
						End Select
						
					Default
						ValueC\ValueType=SE_STRING
						ValueC\StringValue="NaN"
				End Select
				
			Case SE_SUB
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				If ValueA\ValueType<SE_STRING And ValueB\ValueType<SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=0
								Case SE_INT
									ValueC\ValueType=SE_INT
									ValueC\IntValue=-ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=-ValueB\FloatValue
							End Select
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=ValueA\IntValue
								Case SE_INT
									ValueC\ValueType=SE_INT
									ValueC\IntValue=ValueA\IntValue-ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\IntValue-ValueB\FloatValue
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=0
								Case SE_INT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\FloatValue-ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\FloatValue-ValueB\FloatValue
							End Select
					End Select
				Else
					ValueC\ValueType=SE_STRING
					ValueC\StringValue="NaN"
				EndIf
				
			Case SE_MUL
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				If ValueA\ValueType<SE_STRING And ValueB\ValueType<SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							ValueC\ValueType=SE_INT
							ValueC\IntValue=0
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=0
								Case SE_INT
									ValueC\ValueType=SE_INT
									ValueC\IntValue=ValueA\IntValue*ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\IntValue*ValueB\FloatValue
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=0
								Case SE_INT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\FloatValue*ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\FloatValue*ValueB\FloatValue
							End Select
					End Select
				Else
					ValueC\ValueType=SE_STRING
					ValueC\StringValue="NaN"
				EndIf
				
			Case SE_DIV
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				If ValueA\ValueType<SE_STRING And ValueB\ValueType<SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_STRING
									ValueC\StringValue="NaN"
									
								Case SE_INT
									If ValueB\IntValue<>0
										ValueC\ValueType=SE_INT
										ValueC\IntValue=0
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
									
								Case SE_FLOAT
									If ValueB\FloatValue<>0
										ValueC\ValueType=SE_INT
										ValueC\IntValue=0
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
							End Select
							
						Case SE_INT
							If ValueA\IntValue<>0
								Select ValueB\ValueType
									Case SE_NULL
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="Infinity"
										
									Case SE_INT
										If ValueB\IntValue<>0
											ValueC\ValueType=SE_INT
											ValueC\IntValue=ValueA\IntValue/ValueB\IntValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="Infinity"
										EndIf
										
									Case SE_FLOAT
										If ValueB\FloatValue<>0
											ValueC\ValueType=SE_FLOAT
											ValueC\FloatValue=ValueA\IntValue/ValueB\FloatValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="Infinity"
										EndIf
								End Select
							Else
								Select ValueB\ValueType
									Case SE_NULL
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
										
									Case SE_INT
										If ValueB\IntValue<>0
											ValueC\ValueType=SE_INT
											ValueC\IntValue=ValueA\IntValue/ValueB\IntValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="NaN"
										EndIf
										
									Case SE_FLOAT
										If ValueB\FloatValue<>0
											ValueC\ValueType=SE_FLOAT
											ValueC\FloatValue=ValueA\IntValue/ValueB\FloatValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="NaN"
										EndIf
								End Select
							EndIf
							
						Case SE_FLOAT
							If ValueA\IntValue<>0
								Select ValueB\ValueType
									Case SE_NULL
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="Infinity"
										
									Case SE_INT
										If ValueB\IntValue<>0
											ValueC\ValueType=SE_FLOAT
											ValueC\IntValue=ValueA\FloatValue/ValueB\IntValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="Infinity"
										EndIf
										
									Case SE_FLOAT
										If ValueB\FloatValue<>0
											ValueC\ValueType=SE_FLOAT
											ValueC\FloatValue=ValueA\FloatValue/ValueB\FloatValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="Infinity"
										EndIf
								End Select
							Else
								Select ValueB\ValueType
									Case SE_NULL
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
										
									Case SE_INT
										If ValueB\IntValue<>0
											ValueC\ValueType=SE_FLOAT
											ValueC\FloatValue=ValueA\FloatValue/ValueB\IntValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="NaN"
										EndIf
										
									Case SE_FLOAT
										If ValueB\FloatValue<>0
											ValueC\ValueType=SE_FLOAT
											ValueC\FloatValue=ValueA\FloatValue/ValueB\FloatValue
										Else
											ValueC\ValueType=SE_STRING
											ValueC\StringValue="NaN"
										EndIf
								End Select
							EndIf
					End Select
				Else
					ValueC\ValueType=SE_STRING
					ValueC\StringValue="NaN"
				EndIf
			
			Case SE_POW
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				If ValueA\ValueType<SE_STRING And ValueB\ValueType<SE_STRING
					Select ValueA\ValueType
					
						Case SE_NULL
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=1
								Case SE_INT
									ValueC\ValueType=SE_INT
									ValueC\IntValue=0^ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=0^ValueB\FloatValue
							End Select
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=1
								Case SE_INT
									ValueC\ValueType=SE_INT
									ValueC\IntValue=ValueA\IntValue^ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\IntValue^ValueB\FloatValue
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_INT
									ValueC\IntValue=1
								Case SE_INT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\FloatValue^ValueB\IntValue
								Case SE_FLOAT
									ValueC\ValueType=SE_FLOAT
									ValueC\FloatValue=ValueA\FloatValue^ValueB\FloatValue
							End Select
					End Select
				Else
					ValueC\ValueType=SE_STRING
					ValueC\StringValue="NaN"
				EndIf
				
			Case SE_MOD
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				If ValueA\ValueType<SE_STRING And ValueB\ValueType<SE_STRING
					Select ValueA\ValueType
					
						Case SE_NULL
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_STRING
									ValueC\StringValue="NaN"
									
								Case SE_INT
									If ValueB\IntValue<>0
										ValueC\ValueType=SE_INT
										ValueC\IntValue=0
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
									
								Case SE_FLOAT
									If ValueB\FloatValue<>0
										ValueC\ValueType=SE_FLOAT
										ValueC\FloatValue=0
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
							End Select
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_STRING
									ValueC\StringValue="NaN"
									
								Case SE_INT
									If ValueB\IntValue<>0
										ValueC\ValueType=SE_INT
										ValueC\IntValue=ValueA\IntValue Mod ValueB\IntValue
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
									
								Case SE_FLOAT
									If ValueB\FloatValue<>0
										ValueC\ValueType=SE_FLOAT
										ValueC\FloatValue=ValueA\IntValue Mod ValueB\FloatValue
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\ValueType=SE_STRING
									ValueC\StringValue="NaN"
									
								Case SE_INT
									If ValueB\IntValue<>0
										ValueC\ValueType=SE_FLOAT
										ValueC\FloatValue=ValueA\FloatValue Mod ValueB\IntValue
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
									
								Case SE_FLOAT
									If ValueB\FloatValue<>0
										ValueC\ValueType=SE_FLOAT
										ValueC\FloatValue=ValueA\FloatValue Mod ValueB\FloatValue
									Else
										ValueC\ValueType=SE_STRING
										ValueC\StringValue="NaN"
									EndIf
							End Select
					End Select
				Else
					ValueC\ValueType=SE_STRING
					ValueC\StringValue="NaN"
				EndIf
				
			Case SE_NEG
				ValueA=SE_GetFinalValue(ValueA)

				Select ValueA\ValueType
					Case SE_NULL
						ValueB\ValueType=SE_INT
						ValueB\IntValue=0
					
					Case SE_INT
						ValueB\ValueType=SE_INT
						ValueB\IntValue=-ValueA\IntValue
					
					Case SE_FLOAT
						ValueB\ValueType=SE_FLOAT
						ValueB\FloatValue=-ValueA\FloatValue
					
					Default
						ValueB\ValueType=SE_STRING
						ValueB\StringValue="NaN"
				End Select
				
			Case SE_INC
				ValueA=SE_GetFinalValue(ValueA)

				Select ValueA\ValueType
					Case SE_NULL
						ValueA\ValueType=SE_INT
						ValueA\IntValue=1
					Case SE_INT
						ValueA\IntValue=ValueA\IntValue+1
					Case SE_FLOAT
						ValueA\FloatValue=ValueA\FloatValue+1
					Default
						ValueA\StringValue="NaN"
				End Select

			Case SE_DEC
				ValueA=SE_GetFinalValue(ValueA)

				Select ValueA\ValueType
					Case SE_NULL
						ValueA\ValueType=SE_INT
						ValueA\IntValue=-1
					Case SE_INT
						ValueA\IntValue=ValueA\IntValue-1
					Case SE_FLOAT
						ValueA\FloatValue=ValueA\FloatValue-1
					Default
						ValueA\StringValue="NaN"
				End Select

			Case SE_CE
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
			
				ValueC\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						If ValueB\ValueType=SE_NULL
							ValueC\IntValue=1
						Else
							ValueC\IntValue=0
						EndIf
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\IntValue=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\IntValue=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\IntValue=ValueB\StringValue)
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\FloatValue=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\FloatValue=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\FloatValue=ValueB\StringValue)
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\StringValue=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\StringValue=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\StringValue=ValueB\StringValue)
						End Select
						
					Case SE_ARRAY
						If ValueB\ValueType=SE_ARRAY
							ValueC\IntValue=(ValueA\Array=ValueB\Array)
						Else
							ValueC\IntValue=0
						EndIf
				End Select
				
			Case SE_CNE
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						If ValueB\ValueType=SE_NULL
							ValueC\IntValue=0
						Else
							ValueC\IntValue=1
						EndIf
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\IntValue<>ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\IntValue<>ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\IntValue<>ValueB\StringValue)
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\FloatValue<>ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\FloatValue<>ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\FloatValue<>ValueB\StringValue)
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\StringValue<>ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\StringValue<>ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\StringValue<>ValueB\StringValue)
						End Select
						
					Case SE_ARRAY
						If ValueB\ValueType=SE_ARRAY
							ValueC\IntValue=(ValueA\Array<>ValueB\Array)
						Else
							ValueC\IntValue=1
						EndIf
				End Select
				
			Case SE_CG
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						ValueC\IntValue=0
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\IntValue>ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\IntValue>ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\IntValue>ValueB\StringValue)
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\FloatValue>ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\FloatValue>ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\FloatValue>ValueB\StringValue)
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\StringValue>ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\StringValue>ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\StringValue>ValueB\StringValue)
						End Select
						
					Case SE_ARRAY
						If ValueB\ValueType=SE_ARRAY
							ValueC\IntValue=(ValueA\Array\Elements>ValueB\Array\Elements)
						Else
							ValueC\IntValue=0
						EndIf
				End Select
				
			Case SE_CL
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						If ValueB\ValueType=SE_NULL
							ValueC\IntValue=0
						Else
							ValueC\IntValue=1
						EndIf
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\IntValue<ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\IntValue<ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\IntValue<ValueB\StringValue)
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\FloatValue<ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\FloatValue<ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\FloatValue<ValueB\StringValue)
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\StringValue<ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\StringValue<ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\StringValue<ValueB\StringValue)
						End Select
						
					Case SE_ARRAY
						If ValueB\ValueType=SE_ARRAY
							ValueC\IntValue=(ValueA\Array\Elements<ValueB\Array\Elements)
						Else
							ValueC\IntValue=0
						EndIf
				End Select
				
			Case SE_CGE
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						If ValueB\ValueType=SE_NULL
							ValueC\IntValue=1
						Else
							ValueC\IntValue=0
						EndIf
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\IntValue>=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\IntValue>=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\IntValue>=ValueB\StringValue)
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\FloatValue>=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\FloatValue>=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\FloatValue>=ValueB\StringValue)
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=1
							Case SE_INT
								ValueC\IntValue=(ValueA\StringValue>=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\StringValue>=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\StringValue>=ValueB\StringValue)
						End Select
					
					Case SE_ARRAY
						If ValueB\ValueType=SE_ARRAY
							ValueC\IntValue=(ValueA\Array\Elements>=ValueB\Array\Elements)
						Else
							ValueC\IntValue=0
						EndIf
				End Select
				
			Case SE_CLE
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						ValueC\IntValue=1
						
					Case SE_INT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\IntValue<=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\IntValue<=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\IntValue<=ValueB\StringValue)
						End Select
						
					Case SE_FLOAT
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\FloatValue<=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\FloatValue<=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\FloatValue<=ValueB\StringValue)
						End Select
						
					Case SE_STRING
						Select ValueB\ValueType
							Case SE_NULL
								ValueC\IntValue=0
							Case SE_INT
								ValueC\IntValue=(ValueA\StringValue<=ValueB\IntValue)
							Case SE_FLOAT
								ValueC\IntValue=(ValueA\StringValue<=ValueB\FloatValue)
							Case SE_STRING
								ValueC\IntValue=(ValueA\StringValue<=ValueB\StringValue)
						End Select
						
					Case SE_ARRAY
						If ValueB\ValueType=SE_ARRAY
							ValueC\IntValue=(ValueA\Array\Elements<=ValueB\Array\Elements)
						Else
							ValueC\IntValue=0
						EndIf
				End Select
				
			Case SE_AND
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT

				If ValueA\ValueType<=SE_STRING And ValueB\ValueType<=SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							ValueC\IntValue=0
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=0
								Case SE_INT
									ValueC\IntValue=(ValueA\IntValue And ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\IntValue And ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\IntValue And ValueB\StringValue)
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=0
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue And ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue And ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue And ValueB\StringValue)
							End Select
							
						Case SE_STRING
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=0
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue And ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue And ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue And ValueB\StringValue)
							End Select
					End Select
				Else
					ValueC\IntValue=0
				EndIf

			Case SE_XOR
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				If ValueA\ValueType<=SE_STRING And ValueB\ValueType<=SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=0
								Case SE_INT
									ValueC\IntValue=ValueB\IntValue
								Case SE_FLOAT
									ValueC\IntValue=ValueB\FloatValue
								Case SE_STRING
									ValueC\IntValue=ValueB\StringValue
							End Select
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\IntValue
								Case SE_INT
									ValueC\IntValue=(ValueA\IntValue Xor ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\IntValue Xor ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\IntValue Xor ValueB\StringValue)
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\FloatValue
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue Xor ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue Xor ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue Xor ValueB\StringValue)
							End Select
							
						Case SE_STRING
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\StringValue
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue Xor ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue Xor ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue Xor ValueB\StringValue)
							End Select
					End Select
				Else
					ValueC\IntValue=0
				EndIf

			Case SE_BN
				ValueA=SE_GetFinalValue(ValueA)
				
				ValueB\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						ValueB\IntValue=-1
					Case SE_INT
						ValueB\IntValue=(~ValueA\IntValue)
					Case SE_FLOAT
						ValueB\IntValue=(~ValueA\FloatValue)
					Case SE_STRING
						ValueB\IntValue=(~ValueA\StringValue)
					Default
						ValueB\IntValue=-1
				End Select
				
			Case SE_OR
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT
				
				If ValueA\ValueType<=SE_STRING And ValueB\ValueType<=SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=0
								Case SE_INT
									ValueC\IntValue=ValueB\IntValue
								Case SE_FLOAT
									ValueC\IntValue=ValueB\FloatValue
								Case SE_STRING
									ValueC\IntValue=ValueB\StringValue
							End Select
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\IntValue
								Case SE_INT
									ValueC\IntValue=(ValueA\IntValue Or ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\IntValue Or ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\IntValue Or ValueB\StringValue)
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\FloatValue
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue Or ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue Or ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue Or ValueB\StringValue)
							End Select
							
						Case SE_STRING
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\StringValue
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue Or ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue Or ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue Or ValueB\StringValue)
							End Select
					End Select
				Else
					ValueC\IntValue=0
				EndIf
				
			Case SE_SHL
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT

				If ValueA\ValueType<=SE_STRING And ValueB\ValueType<=SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							ValueC\IntValue=0
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\IntValue
								Case SE_INT
									ValueC\IntValue=(ValueA\IntValue Shl ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\IntValue Shl ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\IntValue Shl ValueB\StringValue)
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\FloatValue
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue Shl ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue Shl ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue Shl ValueB\StringValue)
							End Select
							
						Case SE_STRING
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\StringValue
								Case SE_INT
									ValueC\IntValue=(ValueA\StringValue Shl ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\StringValue Shl ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\StringValue Shl ValueB\StringValue)
							End Select
					End Select
				Else
					ValueC\IntValue=0
				EndIf
				
			Case SE_SHR
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)
				
				ValueC\ValueType=SE_INT

				If ValueA\ValueType<=SE_STRING And ValueB\ValueType<=SE_STRING
					Select ValueA\ValueType
						Case SE_NULL
							ValueC\IntValue=0
							
						Case SE_INT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\IntValue
								Case SE_INT
									ValueC\IntValue=(ValueA\IntValue Shr ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\IntValue Shr ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\IntValue Shr ValueB\StringValue)
							End Select
							
						Case SE_FLOAT
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\FloatValue
								Case SE_INT
									ValueC\IntValue=(ValueA\FloatValue Shr ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\FloatValue Shr ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\FloatValue Shr ValueB\StringValue)
							End Select
							
						Case SE_STRING
							Select ValueB\ValueType
								Case SE_NULL
									ValueC\IntValue=ValueA\StringValue
								Case SE_INT
									ValueC\IntValue=(ValueA\StringValue Shr ValueB\IntValue)
								Case SE_FLOAT
									ValueC\IntValue=(ValueA\StringValue Shr ValueB\FloatValue)
								Case SE_STRING
									ValueC\IntValue=(ValueA\StringValue Shr ValueB\StringValue)
							End Select
					End Select
				Else
					ValueC\IntValue=0
				EndIf
				
			Case SE_NOT
				ValueA=SE_GetFinalValue(ValueA)

				ValueB\ValueType=SE_INT
				
				Select ValueA\ValueType
					Case SE_NULL
						ValueB\IntValue=1
					Case SE_INT
						ValueB\IntValue=(Not ValueA\IntValue)
					Case SE_FLOAT
						ValueB\IntValue=(Not ValueA\FloatValue)
					Case SE_STRING
						ValueB\IntValue=(Not ValueA\StringValue)
					Default
						ValueB\IntValue=0
				End Select
				
			Case SE_JMP
				Inst=ValueA\Label
				Jump=True
				
			Case SE_JIF
				ValueA=SE_GetFinalValue(ValueA)

				Select ValueA\ValueType
					Case SE_NULL
						Inst=ValueB\Label
						Jump=True
						
					Case SE_INT
						If ValueA\IntValue=0
							Inst=ValueB\Label
							Jump=True
						EndIf
						
					Case SE_FLOAT
						If ValueA\FloatValue=0.0
							Inst=ValueB\Label
							Jump=True
						EndIf
						
					Case SE_STRING
						If ValueA\StringValue=""
							Inst=ValueB\Label
							Jump=True
						EndIf
				End Select
				
			Case SE_JIT
				ValueA=SE_GetFinalValue(ValueA)

				Select ValueA\ValueType
					Case SE_INT
						If ValueA\IntValue<>0
							Inst=ValueB\Label
							Jump=True
						EndIf
						
					Case SE_FLOAT
						If ValueA\FloatValue<>0
							Inst=ValueB\Label
							Jump=True
						EndIf
						
					Case SE_STRING
						If ValueA\StringValue<>""
							Inst=ValueB\Label
							Jump=True
						EndIf
						
					Case SE_ARRAY
						Inst=ValueB\Label
						Jump=True
				End Select
				
			Case SE_MTA
				If ValueA\ValueType=SE_ACCESSOR
;					If ValueA\Pointer=Null
;						ValueA\ValueType=SE_NULL
;					Else
;						ValueA=ValueA\Pointer
;					EndIf

					ValueA=SE_GetAccessorValue(ValueA)
				EndIf

				
				Local ArgOffset=SE_ARGUMENTS_STACK_LEVEL

				SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
				SE_GrowArguments()

				Select ValueA\ValueType
					Case SE_NULL
						SE_ARGUMENTS_STACK(ArgOffset)\ValueType=SE_NULL
					Case SE_INT
						SE_ARGUMENTS_STACK(ArgOffset)\ValueType=SE_INT
						SE_ARGUMENTS_STACK(ArgOffset)\IntValue=ValueA\IntValue
					Case SE_FLOAT
						SE_ARGUMENTS_STACK(ArgOffset)\ValueType=SE_FLOAT
						SE_ARGUMENTS_STACK(ArgOffset)\FloatValue=ValueA\FloatValue
					Case SE_STRING
						SE_ARGUMENTS_STACK(ArgOffset)\ValueType=SE_STRING
						SE_ARGUMENTS_STACK(ArgOffset)\StringValue=ValueA\StringValue
					Case SE_POINTER
						SE_ARGUMENTS_STACK(ArgOffset)\ValueType=SE_POINTER
						SE_ARGUMENTS_STACK(ArgOffset)\Pointer=ValueA\Pointer
					Case SE_ARRAY
						SE_ARGUMENTS_STACK(ArgOffset)\ValueType=SE_ARRAY
						SE_ARGUMENTS_STACK(ArgOffset)\Array=ValueA\Array
				End Select
				
			Case SE_INVU
				Local CurrentFunction.SE_FuncPtr=SE_CURRENT_FUNCTION
				Local CurrentReturnValue.SE_Value=SE_RETURN_VALUE
				Local CurrentArgsNumber=SE_ARGUMENTS_NUMBER
				Local CurrentArgsOffset=SE_ARGUMENTS_STACK_OFFSET
				
				ValueC\ValueType=SE_NULL
				SE_RETURN_VALUE=ValueC
				SE_ARGUMENTS_NUMBER=ValueB\IntValue
				
				If ValueA\ValueType=SE_FUNC_PTR Then 
					if SE_SCRIPT_ACCESS_INVOKE Then SE_InvokeUserFunction(ValueA\FuncPtr, False)
				EndIf
				
				SE_CURRENT_FUNCTION=CurrentFunction
				SE_RETURN_VALUE=CurrentReturnValue
				SE_ARGUMENTS_NUMBER=CurrentArgsNumber
				SE_ARGUMENTS_STACK_OFFSET=CurrentArgsOffset
				
			Case SE_INVG

				CurrentReturnValue=SE_RETURN_VALUE
				CurrentArgsNumber=SE_ARGUMENTS_NUMBER
				CurrentArgsOffset=SE_ARGUMENTS_STACK_OFFSET
				SE_RETURN_VALUE=ValueC
				SE_ARGUMENTS_NUMBER=ValueB\IntValue
				SE_ARGUMENTS_STACK_OFFSET=SE_ARGUMENTS_STACK_LEVEL-SE_ARGUMENTS_NUMBER

				if SE_SCRIPT_ACCESS_INVOKE Then SE_InvokeGlobalFunction(ValueA\StaticIndex)
				
				SE_RETURN_VALUE=CurrentReturnValue
				SE_ARGUMENTS_NUMBER=CurrentArgsNumber
				SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_OFFSET
				SE_ARGUMENTS_STACK_OFFSET=CurrentArgsOffset
				
			Case SE_RET
				ValueA=SE_GetFinalValue(ValueA)
				
				Select ValueA\ValueType
					Case SE_NULL
						SE_RETURN_VALUE\ValueType=SE_NULL
					Case SE_INT
						SE_RETURN_VALUE\ValueType=SE_INT
						SE_RETURN_VALUE\IntValue=ValueA\IntValue
						if ValueA\IntValue = 0 Then SE_RETURN_VALUE\StaticIndex=True
					Case SE_FLOAT
						SE_RETURN_VALUE\ValueType=SE_FLOAT
						SE_RETURN_VALUE\FloatValue=ValueA\FloatValue
					Case SE_STRING
						SE_RETURN_VALUE\ValueType=SE_STRING
						SE_RETURN_VALUE\StringValue=ValueA\StringValue
					Case SE_ARRAY
						SE_RETURN_VALUE\ValueType=SE_ARRAY
						SE_RETURN_VALUE\Array=ValueA\Array
				End Select
				
				Exit
				
			Case SE_END
				Exit
			
			Case SE_GP
				ValueA=SE_GetFinalValue(ValueA)
								
				If ValueB<>ValueA
					ValueB\ValueType=SE_POINTER
					ValueB\Pointer=ValueA
				Else
					ValueB\ValueType=SE_NULL
				EndIf
				
			Case SE_VT
				ValueA=SE_GetFinalValue(ValueA)
				ValueB\ValueType=SE_INT
				ValueB\IntValue=ValueA\ValueType
				
			Case SE_ARGS
				ValueA\Array=SE_ArgsToArray()
				
				If ValueA\Array<>Null
					ValueA\ValueType=SE_ARRAY
				Else
					ValueA\ValueType=SE_NULL
				EndIf

			Case SE_CA
				CurrentArgsNumber=SE_ARGUMENTS_NUMBER
				CurrentArgsOffset=SE_ARGUMENTS_STACK_OFFSET
				SE_ARGUMENTS_NUMBER=ValueA\IntValue
				SE_ARGUMENTS_STACK_OFFSET=SE_ARGUMENTS_STACK_LEVEL-SE_ARGUMENTS_NUMBER
				SE_Array_Create_Inst(ValueA\IntValue, ValueB)
				SE_ARGUMENTS_NUMBER=CurrentArgsNumber
				SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_OFFSET
				SE_ARGUMENTS_STACK_OFFSET=CurrentArgsOffset

			Case SE_ACC
				ValueA=SE_GetFinalValue(ValueA)
				ValueB=SE_GetFinalValue(ValueB)

				ValueC\Pointer=Null
					
				If ValueA\ValueType=SE_ARRAY
					If ValueB\ValueType=SE_INT
						ValueC\Pointer=SE_Array_GetElement(ValueA\Array, ValueB\IntValue)
						
					Else If ValueB\ValueType=SE_FLOAT
						ValueC\Pointer=SE_Array_GetElement(ValueA\Array, ValueB\FloatValue)
						
					Else If ValueB\ValueType=SE_STRING
						ValueC\Pointer=SE_Array_GetElement(ValueA\Array, ValueB\StringValue)
					EndIf
					
					If ValueC\Pointer<>Null
						ValueC\ValueType=SE_ACCESSOR
					Else
						ValueC\ValueType=SE_STRING
						ValueC\StringValue="undefined"
					EndIf
				Else
					Inst\C\ValueType=SE_NULL
				EndIf
	
			Case SE_LEN
				ValueA=SE_GetFinalValue(ValueA)

				If ValueA\ValueType=SE_STRING
					ValueB\ValueType=SE_INT
					ValueB\IntValue=Len(ValueA\StringValue)
					
				Else If ValueA\ValueType=SE_ARRAY
					ValueB\ValueType=SE_INT
					ValueB\IntValue=ValueA\Array\Elements
					
				Else
					ValueB\ValueType=SE_STRING
					ValueB\StringValue="undefined"
				EndIf
		End Select

		If Inst=SE_CURRENT_FUNCTION\LastInst
			Exit
		Else
			If Jump=False Then Inst=Inst\NextInst
		EndIf
		
	Forever

	;Clean up stack
	Local Index
	
	For Index=SE_TRANSIENT_STACK_OFFSET To SE_TRANSIENT_STACK_LEVEL
		SE_GCCheck(SE_TRANSIENT_STACK(Index))
		SE_TRANSIENT_STACK(Index)\ValueType=SE_NULL
	Next
	
	SE_TRANSIENT_STACK_LEVEL=Level
	SE_TRANSIENT_STACK_OFFSET=Offset
	
	local errorlo$ = errorlog()
	if errorlo <> "" then adderrorlog(errorlo+ " in: "+FuncPtr\Name)
End Function

Function SE_InvokeGlobalFunction(FunctionID)
	SE_ExecuteFunction(0, FunctionID)
End Function	

Function SE_IntArg%(Index, DefValue%=0)
	If Index>=SE_ARGUMENTS_NUMBER Then Return DefValue
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	If Argument\ValueType=SE_INT Then Return Argument\IntValue
End Function

Function SE_FloatArg#(Index, DefValue#=0.0)
	If Index>=SE_ARGUMENTS_NUMBER Then Return DefValue
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	If Argument\ValueType=SE_FLOAT Then Return Argument\FloatValue
End Function

Function SE_StringArg$(Index, DefValue$="")
	If Index>=SE_ARGUMENTS_NUMBER Then Return DefValue
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	If Argument\ValueType=SE_STRING Then Return Argument\StringValue
End Function

Function SE_PointerArg.SE_Value(Index)
	If Index>=SE_ARGUMENTS_NUMBER Then Return Null
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	If Argument\ValueType=SE_POINTER Then Return Argument\Pointer
End Function

Function SE_ArgHandle.SE_Value(Index)		;	for advanced users only
	If Index>=SE_ARGUMENTS_NUMBER Then Return Null
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	Return Argument
End Function

Function SE_ArrayArg.SE_Array(Index)
	If Index>=SE_ARGUMENTS_NUMBER Then Return Null
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	If Argument\ValueType=SE_ARRAY Then Return Argument\Array
End Function

Function SE_SetValue(Pointer.SE_Value, Value$, ValueType)
	SE_GCCheck(Pointer)

	Select ValueType
		Case SE_NULL
			Pointer\ValueType=SE_NULL
			
		Case SE_INT
			Pointer\ValueType=SE_INT
			Pointer\IntValue=Value
			
		Case SE_FLOAT
			Pointer\ValueType=SE_FLOAT
			Pointer\FloatValue=Value
			
		Case SE_STRING
			Pointer\ValueType=SE_STRING
			Pointer\StringValue=Value
	End Select
End Function

Function SE_SetArray(Pointer.SE_Value, Array.SE_Array)
	SE_GCCheck(Pointer)
	
	Pointer\ValueType=SE_ARRAY
	Pointer\Array=Array
End Function

Function SE_SetPointer(Pointer.SE_Value, Variable.SE_Value)
	Pointer\ValueType=SE_POINTER
	Pointer\Pointer=Variable
End Function

Function SE_ToIntArg%(Index, DefValue%=0)
	If Index>=SE_ARGUMENTS_NUMBER Then Return DefValue
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	
	Select Argument\ValueType
		Case SE_NULL
			Return 0
			
		Case SE_INT
			Return Argument\IntValue
			
		Case SE_FLOAT
			Return Argument\FloatValue
			
		Case SE_STRING
			Return Argument\StringValue
	End Select
End Function

Function SE_ToFloatArg#(Index, DefValue#=0)
	If Index>=SE_ARGUMENTS_NUMBER Then Return DefValue
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)
	
	Select Argument\ValueType
		Case SE_NULL
			Return 0.0
			
		Case SE_INT
			Return Argument\IntValue
			
		Case SE_FLOAT
			Return Argument\FloatValue
			
		Case SE_STRING
			Return Argument\StringValue
	End Select
End Function

Function SE_ToStringArg$(Index, DefValue$="")
	If Index>=SE_ARGUMENTS_NUMBER Then Return DefValue
	Local Argument.SE_Value=SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)

	Select Argument\ValueType
		Case SE_NULL
			Return ""
			
		Case SE_INT
			Return Argument\IntValue
			
		Case SE_FLOAT
			Return Argument\FloatValue
			
		Case SE_STRING
			Return Argument\StringValue
	End Select
End Function

Function SE_ArgType(Index)
	Return SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_OFFSET+Index)\ValueType
End Function

Function SE_ArgsToArray.SE_Array()
	If SE_ARGUMENTS_NUMBER
		Local Array.SE_Array=SE_Array_Create()
		Local Args=SE_ARGUMENTS_NUMBER-1
	
		For Index=0 To Args
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
		
		Return Array
	EndIf
End Function

Function SE_GetReturnValue$()
	if SE_RETURN_VALUE = Null Then Return -1
	Select SE_RETURN_VALUE\ValueType
		Case SE_NULL
			Return -1
			
		Case SE_INT
			Return SE_RETURN_VALUE\IntValue
			
		Case SE_FLOAT
			Return SE_RETURN_VALUE\FloatValue
			
		Case SE_STRING
			Return SE_RETURN_VALUE\StringValue
	End Select
End Function

Function SE_ReturnNull()
	SE_RETURN_VALUE\ValueType=SE_NULL
End Function

Function SE_ReturnInt(Value%)
	SE_RETURN_VALUE\ValueType=SE_INT
	SE_RETURN_VALUE\IntValue=Value
End Function

Function SE_ReturnFloat(Value#)
	SE_RETURN_VALUE\ValueType=SE_FLOAT
	SE_RETURN_VALUE\FloatValue=Value
End Function

Function SE_ReturnString(Value$)
	SE_RETURN_VALUE\ValueType=SE_STRING
	SE_RETURN_VALUE\StringValue=Value
End Function

Function SE_ReturnPointer(Value.SE_Value)
	SE_RETURN_VALUE\ValueType=SE_POINTER
	SE_RETURN_VALUE\Pointer=Value
End Function

Function SE_ReturnArray(Array.SE_Array)
	SE_RETURN_VALUE\ValueType=SE_ARRAY
	SE_RETURN_VALUE\Array=Array
End Function

Function SE_AddNullArg()
	SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
	SE_ARGUMENTS_NUMBER=SE_ARGUMENTS_NUMBER+1
	SE_GrowArguments()
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\ValueType=SE_NULL
End Function

Function SE_AddIntArg(Value%)
	SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
	SE_ARGUMENTS_NUMBER=SE_ARGUMENTS_NUMBER+1
	SE_GrowArguments()
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\ValueType=SE_INT
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\IntValue=Value
End Function

Function SE_AddFloatArg(Value#)
	SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
	SE_ARGUMENTS_NUMBER=SE_ARGUMENTS_NUMBER+1
	SE_GrowArguments()
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\ValueType=SE_FLOAT
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\FloatValue=Value
End Function

Function SE_AddStringArg(Value$)
	SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
	SE_ARGUMENTS_NUMBER=SE_ARGUMENTS_NUMBER+1
	SE_GrowArguments()
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\ValueType=SE_STRING
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\StringValue=Value
End Function

Function SE_AddPointerArg(Value.SE_Value)
	SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
	SE_ARGUMENTS_NUMBER=SE_ARGUMENTS_NUMBER+1
	SE_GrowArguments()
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\ValueType=SE_POINTER
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\Pointer=Value
End Function

Function SE_AddArrayArg(Array.SE_Array)
	SE_ARGUMENTS_STACK_LEVEL=SE_ARGUMENTS_STACK_LEVEL+1
	SE_ARGUMENTS_NUMBER=SE_ARGUMENTS_NUMBER+1
	SE_GrowArguments()
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\ValueType=SE_ARRAY
	SE_ARGUMENTS_STACK(SE_ARGUMENTS_STACK_LEVEL-1)\Array=Array
End Function

Function SE_FindFunc.SE_FuncPtr(Script.SE_Script, Name$)
	Local FuncPtr.SE_FuncPtr=Script\FirstFuncPtr

	While FuncPtr<>Null
		If FuncPtr\Name=Name Then Return FuncPtr
		FuncPtr=FuncPtr\NextFuncPtr
	Wend
End Function

Function SE_FindPublic.SE_Public(Script.SE_Script, Name$)
	Local Public.SE_Public=Script\FirstPublic

	While Public<>Null
		If Public\Name=Name Then Return Public
		Public=Public\NextPublic
	Wend
End Function

;	Script instance

Type SE_Instance
	Field Script.SE_Script
	Field FirstPublic.SE_Public, LastPublic.SE_Public
End Type

Function SE_CreateInstance.SE_Instance(Script.SE_Script)
	Local Instance.SE_Instance=New SE_Instance
	Instance\Script=Script

	Local SVar.SE_Public=Script\FirstPublic		;Source variable
	Local IVar.SE_Public						;Instance variable

	While SVar<>Null
		IVar=New SE_Public
		IVar\Name=SVar\Name
		IVar\Value=New SE_Value

		If Instance\LastPublic<>Null
			IVar\PrevPublic=Instance\LastPublic
			Instance\LastPublic\NextPublic=IVar
			Instance\LastPublic=IVar
		Else
			Instance\FirstPublic=IVar
			Instance\LastPublic=IVar
		EndIf
		
		SVar=SVar\NextPublic
	Wend

	Return Instance
End Function

Function SE_SetInstance(Instance.SE_Instance)
	Local SVar.SE_Public=Instance\Script\FirstPublic		;Source variable
	Local IVar.SE_Public=Instance\FirstPublic				;Instance variable

	While SVar<>Null
		SE_GCCheck(SVar\Value)
		
		SVar\Value\ValueType=IVar\Value\ValueType
	
		Select IVar\Value\ValueType
			Case SE_INT
				SVar\Value\IntValue=IVar\Value\IntValue

			Case SE_FLOAT
				SVar\Value\FloatValue=IVar\Value\FloatValue

			Case SE_STRING
				SVar\Value\StringValue=IVar\Value\StringValue

			Case SE_POINTER
				SVar\Value\Pointer=IVar\Value\Pointer
				
			Case SE_ARRAY
				SVar\Value\Array=IVar\Value\Array
		End Select
		
		IVar=IVar\NextPublic
		SVar=SVar\NextPublic
	Wend
End Function

Function SE_UnsetInstance(Instance.SE_Instance)
	Local SVar.SE_Public=Instance\Script\FirstPublic		;Source variable
	Local IVar.SE_Public=Instance\FirstPublic				;Instance variable

	While SVar<>Null
		SE_GCCheck(IVar\Value)
		
		IVar\Value\ValueType=SVar\Value\ValueType
	
		Select SVar\Value\ValueType
			Case SE_INT
				IVar\Value\IntValue=SVar\Value\IntValue

			Case SE_FLOAT
				IVar\Value\FloatValue=SVar\Value\FloatValue

			Case SE_STRING
				IVar\Value\StringValue=SVar\Value\StringValue

			Case SE_POINTER
				IVar\Value\Pointer=SVar\Value\Pointer
				
			Case SE_ARRAY
				IVar\Value\Array=SVar\Value\Array
		End Select
		
		IVar=IVar\NextPublic
		SVar=SVar\NextPublic
	Wend
End Function

Function SE_DeleteInstance(Instance.SE_Instance)
	Local Public.SE_Public=Instance\FirstPublic

	While Public<>Null
		Local NextPublic.SE_Public=Public\NextPublic
		SE_GCCheck(Public\Value)
		Delete Public\Value
		Delete Public
		Public=NextPublic
	Wend

	Delete Instance
End Function

Function SE_GetInstanceVar.SE_Value(Instance.SE_Instance, Name$)
	Local Public.SE_Public=Instance\FirstPublic
	
	While Public<>Null
		If Public\Name=Name Then Return Public\Value
		Public=Public\NextPublic
	Wend
End Function

;	Skynet++ Virtual File

Global SE_VF_INST_N			; Instructions number
Global SE_VF_FUNC_PTR_N		; Function poiners number
Global SE_VF_STATIC_N		; Static values number
Global SE_VF_LABEL_N		; Labels number
Global SE_VF_PUBLIC_N		; Public variables number

Dim SE_VF_A_INST.SE_Inst(0)
Dim SE_VF_A_FUNC_PTR.SE_FuncPtr(0)
Dim SE_VF_A_STATIC.SE_Value(0)
Dim SE_VF_A_LABEL.SE_Value(0)

Type SE_VF_Inst
	Field Code
	Field A.SE_VF_Value
	Field B.SE_VF_Value
	Field C.SE_VF_Value

	Field Index
End Type

Type SE_VF_Value
	Field ValueType
	Field Value$, Index
End Type

Type SE_VF_Label
	Field Index
	Field Inst.SE_VF_Inst, Shift
End Type

Type SE_VF_FuncPtr
	Field Name$, Index
	Field FirstInst.SE_VF_Inst, LastInst.SE_VF_Inst
	Field TransientValues
	Field Arguments
End Type

Type SE_VF_Public
	Field Name$
	Field Index			;	Static value index
End Type

Function SE_VF_CreateFuncPtr.SE_VF_FuncPtr(Name$)
	Local F.SE_VF_FuncPtr=New SE_VF_FuncPtr
	F\Name=Name
	F\Index=SE_VF_FUNC_PTR_N
	SE_VF_FUNC_PTR_N=SE_VF_FUNC_PTR_N+1
	Return F
End Function

Function SE_VF_CreateInst.SE_VF_Inst(Code, A.SE_VF_Value, B.SE_VF_Value, C.SE_VF_Value)
	Local Inst.SE_VF_Inst=New SE_VF_Inst
	Inst\Code=Code
	Inst\A=A
	Inst\B=B
	Inst\C=C
;
;	Local DBGM$=Inst\Code+": "
;;	If A<>Null Then DBGM=DBGM+"A "+A\ValueType+"="+A\Value+", "
;;	If B<>Null Then DBGM=DBGM+"B "+B\ValueType+"="+B\Value+", "
;;	If C<>Null Then DBGM=DBGM+"C "+C\ValueType+"="+C\Value
;
;	If A<>Null
;		If A\ValueType>SE_STRING
;			DBGM=DBGM+"A "+A\ValueType+"="+A\Index+", "
;		Else
;			DBGM=DBGM+"A "+A\ValueType+"="+A\Value+", "
;		EndIf
;	EndIf
;
;	If B<>Null
;		If B\ValueType>SE_STRING
;			DBGM=DBGM+"B "+B\ValueType+"="+B\Index+", "
;		Else
;			DBGM=DBGM+"B "+B\ValueType+"="+B\Value+", "
;		EndIf
;	EndIf
;
;	If C<>Null
;		If C\ValueType>SE_STRING
;			DBGM=DBGM+"C "+C\ValueType+"="+C\Index+", "
;		Else
;			DBGM=DBGM+"C "+C\ValueType+"="+C\Value+", "
;		EndIf
;	EndIf
;	DebugLog DBGM

	Inst\Index=SE_VF_INST_N
	SE_VF_INST_N=SE_VF_INST_N+1
	
	Return Inst
End Function

Function SE_VF_CreateBasic.SE_VF_Value(ValueType, Value$)
	Local V.SE_VF_Value=New SE_VF_Value
	V\ValueType=ValueType
	V\Value=Value
	Return V
End Function

Function SE_VF_CreateTransient.SE_VF_Value(FuncPtr.SE_VF_FuncPtr, Argument=False)
	Local V.SE_VF_Value=New SE_VF_Value
	V\ValueType=SE_TRANSIENT
	V\Index=FuncPtr\TransientValues
	FuncPtr\TransientValues=FuncPtr\TransientValues+1
	If Argument Then FuncPtr\Arguments=FuncPtr\Arguments+1
	Return V
End Function

Function SE_VF_CreateStatic.SE_VF_Value()
	Local V.SE_VF_Value=New SE_VF_Value
	V\ValueType=SE_STATIC
	V\Index=SE_VF_STATIC_N
	SE_VF_STATIC_N=SE_VF_STATIC_N+1
	Return V
End Function

Function SE_VF_CreatePublic.SE_VF_Value(Name$)
	Local V.SE_VF_Value=New SE_VF_Value
	V\ValueType=SE_STATIC
	V\Index=SE_VF_STATIC_N

	Local P.SE_VF_Public=New SE_VF_Public
	P\Name=Name
	P\Index=SE_VF_STATIC_N

	SE_VF_PUBLIC_N=SE_VF_PUBLIC_N+1
	SE_VF_STATIC_N=SE_VF_STATIC_N+1
	Return V
End Function

Function SE_VF_CreateLabel.SE_VF_Label()
	Local L.SE_VF_Label=New SE_VF_Label
	L\Index=SE_VF_LABEL_N
	SE_VF_LABEL_N=SE_VF_LABEL_N+1
	
	Return L
End Function

Function SE_VF_GetLabel.SE_VF_Value(Label.SE_VF_Label)
	Local V.SE_VF_Value=New SE_VF_Value
	V\ValueType=SE_LABEL
	V\Index=Label\Index
	Return V
End Function

Function SE_VF_BuildValue.SE_Value(SrcValue.SE_VF_Value)
	Local Value.SE_Value
	
	Select SrcValue\ValueType
		Case SE_NULL
			Value=New SE_Value
			Value\ValueType=SE_NULL
			
		Case SE_INT
			Value=New SE_Value
			Value\ValueType=SE_INT
			Value\IntValue=Int(SrcValue\Value)

		Case SE_FLOAT
			Value=New SE_Value
			Value\ValueType=SE_FLOAT
			Value\FloatValue=Float(SrcValue\Value)

		Case SE_STRING
			Value=New SE_Value
			Value\ValueType=SE_STRING
			Value\StringValue=SrcValue\Value

		Case SE_TRANSIENT
			Value=New SE_Value
			Value\ValueType=SE_TRANSIENT
			Value\IntValue=SrcValue\Index

		Case SE_STATIC
			Value=SE_VF_A_STATIC(SrcValue\Index)

		Case SE_LABEL
			Value=SE_VF_A_LABEL(SrcValue\Index)

		Case SE_FUNC_PTR
			Value=New SE_Value
			Value\ValueType=SE_FUNC_PTR
			Value\FuncPtr=SE_VF_A_FUNC_PTR(SrcValue\Index)
	End Select

	Return Value
End Function

Function SE_VF_BuildScript.SE_Script()
	Local Script.SE_Script=New SE_Script

	Dim SE_VF_A_INST(SE_VF_INST_N)
	Dim SE_VF_A_FUNC_PTR(SE_VF_FUNC_PTR_N)
	Dim SE_VF_A_STATIC(SE_VF_STATIC_N)
	Dim SE_VF_A_LABEL(SE_VF_LABEL_N)

	Local Inst.SE_VF_Inst
	Local FuncPtr.SE_VF_FuncPtr
	Local Label.SE_VF_Label
	Local Public.SE_VF_Public
	Local Index

	;	Prepare arrays
	For FuncPtr=Each SE_VF_FuncPtr
		SE_VF_A_FUNC_PTR(FuncPtr\Index)=SE_CreateFuncPtr(Script, FuncPtr\Name, Null, Null, FuncPtr\TransientValues, FuncPtr\Arguments)
	Next

	For Index=0 To SE_VF_STATIC_N-1
		SE_VF_A_STATIC(Index)=New SE_Value
		SE_VF_A_STATIC(Index)\ValueType=SE_NULL
	Next

	For Label=Each SE_VF_Label
		If Label\Inst=Null
			Label\Inst=First SE_VF_Inst
		Else
			If Label\Shift Then Label\Inst=After Label\Inst
		EndIf
		
		SE_VF_A_LABEL(Label\Index)=New SE_Value
		SE_VF_A_LABEL(Label\Index)\ValueType=SE_LABEL
	Next

	;	Build code
	For Inst=Each SE_VF_Inst
		Local A.SE_Value=Null, B.SE_Value=Null, C.SE_Value=Null
		
		If Inst\A<>Null Then A=SE_VF_BuildValue(Inst\A)
		If Inst\B<>Null Then B=SE_VF_BuildValue(Inst\B)
		If Inst\C<>Null Then C=SE_VF_BuildValue(Inst\C)
		
		SE_VF_A_INST(Inst\Index)=SE_CreateInst(Script, Inst\Code, A, B, C)
	Next

	;	Set pointers
	For FuncPtr=Each SE_VF_FuncPtr
		SE_VF_A_FUNC_PTR(FuncPtr\Index)\FirstInst=SE_VF_A_INST(FuncPtr\FirstInst\Index)
		SE_VF_A_FUNC_PTR(FuncPtr\Index)\LastInst=SE_VF_A_INST(FuncPtr\LastInst\Index)
	Next

	For Label=Each SE_VF_Label
		SE_VF_A_LABEL(Label\Index)\Label=SE_VF_A_INST(Label\Inst\Index)
	Next

	For Public=Each SE_VF_Public
		SE_CreatePublic(Script, Public\Name, SE_VF_A_STATIC(Public\Index))
	Next

	Script\Main=Script\FirstFuncPtr
	Script\Version = SE_VERSION
	
	Return Script
End Function

Function SE_VF_SaveBin(FileName$, Key$ = "")
	Local File=WriteFile(FileName)
	If File=0 Then Return

	Local Inst.SE_VF_Inst
	Local FuncPtr.SE_VF_FuncPtr
	Local Label.SE_VF_Label
	Local Public.SE_VF_Public
	Local Index

	WriteInt File, SE_VF_INST_N
	WriteInt File, SE_VF_FUNC_PTR_N
	WriteInt File, SE_VF_STATIC_N
	WriteInt File, SE_VF_LABEL_N
	WriteInt File, SE_VF_PUBLIC_N

	;	Function pointers
	For FuncPtr=Each SE_VF_FuncPtr
		WriteString	File, FuncPtr\Name
		WriteInt	File, FuncPtr\FirstInst\Index
		WriteInt	File, FuncPtr\LastInst\Index
		WriteInt	File, FuncPtr\TransientValues
		WriteInt	File, FuncPtr\Arguments
	Next

	;	Labels
	For Label=Each SE_VF_Label
		If Label\Inst=Null
			Label\Inst=First SE_VF_Inst
		Else
			If Label\Shift Then Label\Inst=After Label\Inst
		EndIf

		WriteInt File, Label\Inst\Index
	Next

	;	Public variables
	For Public=Each SE_VF_Public
		WriteString	File, Public\Name
		WriteInt	File, Public\Index
	Next

	For Inst=Each SE_VF_Inst
	
		WriteByte	File, Inst\Code
		DebugLog Inst\Code

		;AAAAAAAAAA
		If Inst\A<>Null
			WriteByte	File, Inst\A\ValueType
			
			Select Inst\A\ValueType
				Case SE_INT
					WriteInt	File, Inst\A\Value

				Case SE_FLOAT
					WriteFloat	File, Inst\A\Value

				Case SE_STRING
					WriteString	File, Inst\A\Value

				Default
					WriteInt	File, Inst\A\Index
			End Select
		EndIf

		;BBBBBBBBBB
		If Inst\B<>Null
			WriteByte	File, Inst\B\ValueType
			
			Select Inst\B\ValueType
				Case SE_INT
					WriteInt	File, Inst\B\Value

				Case SE_FLOAT
					WriteFloat	File, Inst\B\Value

				Case SE_STRING
					WriteString	File, Inst\B\Value

				Default
					WriteInt	File, Inst\B\Index
			End Select
		EndIf

		;CCCCCCCCCC
		If Inst\C<>Null
			WriteByte	File, Inst\C\ValueType
			
			Select Inst\C\ValueType
				Case SE_INT
					WriteInt	File, Inst\C\Value

				Case SE_FLOAT
					WriteFloat	File, Inst\C\Value

				Case SE_STRING
					WriteString	File, Inst\C\Value

				Default
					WriteInt	File, Inst\C\Index
			End Select
		EndIf
	Next

	if not Instr(key, "ignoreversion") then WriteLine File, SE_VERSION
	
	CloseFile(File)
End Function

Function SE_VF_CloseFile()
	SE_VF_INST_N=0
	SE_VF_FUNC_PTR_N=0
	SE_VF_STATIC_N=0
	SE_VF_LABEL_N=0
	SE_VF_PUBLIC_N=0

	Delete Each SE_VF_Inst
	Delete Each SE_VF_Value
	Delete Each SE_VF_Label
	Delete Each SE_VF_FuncPtr
	Delete Each SE_VF_Public

	Dim SE_VF_A_INST(0)
	Dim SE_VF_A_FUNC_PTR(0)
	Dim SE_VF_A_STATIC(0)
	Dim SE_VF_A_LABEL(0)
End Function

;	Loading

Function SE_ReadArg.SE_Value(FileHandle, Code=0)
	Local Value.SE_Value
	Local ValueType=ReadByte(FileHandle)

	Select ValueType
		Case SE_INT
			Value=New SE_Value
			Value\ValueType=ValueType
			Value\IntValue=ReadInt(FileHandle)

		Case SE_FLOAT
			Value=New SE_Value
			Value\ValueType=ValueType
			Value\FloatValue=ReadFloat(FileHandle)

		Case SE_STRING
			Value=New SE_Value
			Value\ValueType=ValueType
			Value\StringValue=ReadString(FileHandle)
			if Code = SE_INVG Then Value\StaticIndex = SE_GetFunctionID(Value\StringValue)
		Case SE_TRANSIENT
			Value=New SE_Value
			Value\ValueType=ValueType
			Value\IntValue=ReadInt(FileHandle)

		Case SE_LABEL
			Value=SE_VF_A_LABEL(ReadInt(FileHandle))

		Case SE_FUNC_PTR
			Value=New SE_Value
			Value\ValueType=ValueType
			Value\FuncPtr=SE_VF_A_FUNC_PTR(ReadInt(FileHandle))

		Case SE_STATIC
			Value=SE_VF_A_STATIC(ReadInt(FileHandle))
	End Select

	Return Value
End Function

Function SE_IsValidScript(ScriptName$, ScriptFormat$ = ".gsc")
	Return (Lower(Right(ScriptName, Len(ScriptFormat)))=ScriptFormat)
End Function

Function SE_LoadScriptExec.SE_Script(FileName$)
	Local File=ReadFile(FileName)
	If File=0 Then Return

	Local Script.SE_Script=New SE_Script

	Local Inst.SE_VF_Inst
	Local FuncPtr.SE_VF_FuncPtr
	Local Label.SE_VF_Label
	Local Public.SE_VF_Public
	Local Index

	SE_VF_INST_N=ReadInt(File)-1
	SE_VF_FUNC_PTR_N=ReadInt(File)-1
	SE_VF_STATIC_N=ReadInt(File)-1
	SE_VF_LABEL_N=ReadInt(File)-1
	SE_VF_PUBLIC_N=ReadInt(File)-1

	Dim SE_VF_A_INST(SE_VF_INST_N)
	Dim SE_VF_A_FUNC_PTR(SE_VF_FUNC_PTR_N)
	Dim SE_VF_A_STATIC(SE_VF_STATIC_N)
	Dim SE_VF_A_LABEL(SE_VF_LABEL_N)

	;Prepare instructions array
	For Index=0 To SE_VF_INST_N
		SE_VF_A_INST(Index)=SE_CreateInst(Script, 0, Null, Null, Null)
	Next

	;Prepare static values
	For Index=0 To SE_VF_STATIC_N
		SE_VF_A_STATIC(Index)=New SE_Value
	Next

	;Function pointers
	For Index=0 To SE_VF_FUNC_PTR_N
		Local FuncName$=ReadString(File)
		Local FirstInstIndex=ReadInt(File)
		Local LastInstIndex=ReadInt(File)
		Local TransientValues=ReadInt(File)
		Local Arguments=ReadInt(File)
		
		SE_VF_A_FUNC_PTR(Index)=SE_CreateFuncPtr(Script, FuncName, SE_VF_A_INST(FirstInstIndex), SE_VF_A_INST(LastInstIndex), TransientValues, Arguments)
	Next
	
	;Labels
	For Index=0 To SE_VF_LABEL_N
		Local InstIndex=ReadInt(File)
		SE_VF_A_LABEL(Index)=New SE_Value
		SE_VF_A_LABEL(Index)\Label=SE_VF_A_INST(InstIndex)
	Next
	
	;Public variables
	For Index=0 To SE_VF_PUBLIC_N
		Local PublicName$=ReadString(File)
		Local PublicIndex=ReadInt(File)
		SE_CreatePublic(Script, PublicName, SE_VF_A_STATIC(PublicIndex))
	Next

	;Instructions
	For Index=0 To SE_VF_INST_N
		Local Code=ReadByte(File)
		SE_VF_A_INST(Index)\Code=Code

		Select Code
			Case SE_INC, SE_DEC, SE_JMP, SE_MTA, SE_RET, SE_ARGS
				SE_VF_A_INST(Index)\A=SE_ReadArg(File)
				
			Case SE_MOV, SE_NEG, SE_BN, SE_NOT, SE_JIT, SE_JIF, SE_GP, SE_VT, SE_LEN, SE_CA
				SE_VF_A_INST(Index)\A=SE_ReadArg(File)
				SE_VF_A_INST(Index)\B=SE_ReadArg(File)

			Case SE_ADD, SE_SUB, SE_MUL, SE_DIV, SE_POW, SE_MOD, SE_CE, SE_CNE, SE_CG, SE_CL, SE_CGE, SE_CLE, SE_AND, SE_XOR, SE_OR, SE_SHL, SE_SHR, SE_INVU, SE_INVG, SE_CA, SE_ACC
				SE_VF_A_INST(Index)\A=SE_ReadArg(File, Code)
				SE_VF_A_INST(Index)\B=SE_ReadArg(File)
				SE_VF_A_INST(Index)\C=SE_ReadArg(File)
		End Select

		Local DBG$=SE_VF_A_INST(Index)\Code+": "
		If SE_VF_A_INST(Index)\A<>Null Then DBG=DBG+SE_VF_A_INST(Index)\A\ValueType+", "
		If SE_VF_A_INST(Index)\B<>Null Then DBG=DBG+SE_VF_A_INST(Index)\B\ValueType+", "
		If SE_VF_A_INST(Index)\C<>Null Then DBG=DBG+SE_VF_A_INST(Index)\C\ValueType
		DebugLog DBG
	Next

	Script\Main=Script\FirstFuncPtr
	Script\Version = ReadLine(File)
	if Script\Version = "" Then Script\Version = "NULL"
	
	CloseFile(File)
	Return Script
End Function

Function SE_LoadScriptText.SE_Script(FileName$)
	SE_Compile(FileName)
	If SE_ERROR Then Return
	Local Script.SE_Script=SE_VF_BuildScript()
	SE_ClearCompiler()
	Return Script
End Function

Function SE_CompileScript(InFileName$, OutFileName$, Key$ = "")
	SE_Compile(InFileName)
	If SE_ERROR Then Return
	SE_VF_SaveBin(OutFileName, Key)
	SE_ClearCompiler()
End Function

Function SE_DeleteScript(Script.SE_Script)
	Local Inst.SE_Inst=Script\FirstInst
	Local FuncPtr.SE_FuncPtr=Script\FirstFuncPtr
	Local Public.SE_Public=Script\FirstPublic

	;	Delete function pointers
	
	While FuncPtr<>Null
		Local NextFuncPtr.SE_FuncPtr=FuncPtr\NextFuncPtr
		Delete FuncPtr
		FuncPtr=NextFuncPtr
	Wend

	;	Delete public variables
	While Public<>Null
		Local NextPublic.SE_Public=Public\NextPublic
		Delete Public
		Public=NextPublic
	Wend

	;	Delete instructions
	While Inst<>Null
		Local NextInst.SE_Inst=Inst\NextInst
	
		If Inst\A<>Null
			SE_GCCheck(Inst\A)
			Delete Inst\A
		EndIf

		If Inst\B<>Null
			SE_GCCheck(Inst\B)
			Delete Inst\B
		EndIf

		If Inst\C<>Null
			SE_GCCheck(Inst\C)
			Delete Inst\C
		EndIf

		Inst=NextInst
	Wend
	
	Delete Script
End Function


;Include compiler
Include "Source Code\Skynet++ Compiler.bb"

;Include basic global functions library (contains mathematic, string and array operations)
Include "Source Code\Skynet++ Base Lib.bb"
; functions system
Include "Source Code\Skynet Functions.bb"


SE_DefineConst("null", SE_NULL, "", True, Null)
SE_DefineConst("true", SE_INT, "1", True, Null)
SE_DefineConst("false", SE_INT, "0", True, Null)
SE_DefineConst("pi", SE_FLOAT, Pi, True, Null)
SE_DefineConst("se_null", SE_INT, SE_NULL, True, Null)
SE_DefineConst("se_int", SE_INT, SE_INT, True, Null)
SE_DefineConst("se_float", SE_INT, SE_FLOAT, True, Null)
SE_DefineConst("se_string", SE_INT, SE_STRING, True, Null)
SE_DefineConst("se_pointer", SE_INT, SE_POINTER, True, Null)
SE_DefineConst("se_array", SE_INT, SE_ARRAY, True, Null)