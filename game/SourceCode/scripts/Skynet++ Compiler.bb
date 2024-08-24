;	Skynet++

;===========================================
;			Error output
;===========================================

Function SE_ClearCompiler()
	Delete Each SE_Line
	Delete Each SE_Token
	Delete Each SE_Node
	Delete Each SE_Chunk
	Delete Each SE_Block

	Local C.SE_ConstantDef

	For C=Each SE_ConstantDef
		If C\Super=False Then Delete C
	Next

	Delete Each SE_LocalDef
	Delete Each SE_StaticDef
	Delete Each SE_GlobalDef
	Delete Each SE_PublicDef
	Delete Each SE_FunctionDef
	Delete Each SE_TempValue
	Delete Each SE_BlockSpace
	
	SE_VF_CloseFile()
End Function

;Error output
Global SE_ERROR
Global SE_ERROR_MESSAGE$			; Something wrong
Global SE_ERROR_DUMP_LINE=True

Function SE_SetError(Message$)
	SE_ERROR=True
	SE_ERROR_MESSAGE=Message
	SE_ClearCompiler()
End Function

Function SE_Error(Token.SE_Token, Message$)
	If SE_ERROR_DUMP_LINE Then Message=Message+Chr(13)+Chr(13)+Token\TokenLine\LineText
	SE_SetError("error at line "+Token\TokenLine\Number+", column "+Token\Offset+": "+Message)
End Function

Function SE_Error_Expecting(Token.SE_Token, Expecting$)
	SE_Error(Token, "expecting "+Expecting$+" but encountered "+Token\Error)
End Function

Function SE_Error_Unexpected(Token.SE_Token)
	SE_Error(Token, "unexpected "+Token\Error)
End Function

Function SE_Error_EOL(Token.SE_Token)
	SE_Error(Token, "unexpected end of line")
End Function

Type SE_Warning
	Field Message$
End Type

Function SE_Warn(Token.SE_Token, Message$)
	Local W.SE_Warning=New SE_Warning
	W\Message="WARNING!"
	If Token<>Null Then W\Message="(line "+Token\TokenLine\Number+", column "+Token\Offset+")"
	W\Message=W\Message+": "+Message
End Function

;===========================================
;			Preprocessor
;===========================================

Type SE_Line
	Field LineText$
	Field Number
	Field Length
End Type

;	Preprocessor token
Type SE_PPToken
	Field Token$
End Type

Function SE_ParseDirective(Directive$)
	Local Offset=1
	Local Length=Len(Directive)
	Local Token$
	
	Local PPT.SE_PPToken

	Repeat
		Local Char=Asc(Mid(Directive, Offset, 1))

		Token=""

		If Char=34
			Local EndOffset=Instr(Directive, Chr(34), Offset+1)
			
			If EndOffset=0
				EndOffset=Length
			EndIf
			
			Token=Mid(Directive, Offset+1, EndOffset-Offset-1)
			Offset=EndOffset+1

			PPT=New SE_PPToken
			PPT\Token=Token
			
		Else If (Char>32 And Char<>34 And Char<>127)
			Repeat
				Token=Token+Chr(Char)
				Offset=Offset+1
				If Offset>Length Then Exit
				Char=Asc(Mid(Directive, Offset, 1))
			Until Not (Char>32 And Char<>34 And Char<>127)

			PPT=New SE_PPToken
			PPT\Token=Token
		Else
			Offset=Offset+1
		EndIf

		If Offset>Length Then Exit
	Forever
End Function

Function SE_PP_Include(FileName$, SrcFileName$)
	Local Length=Len(SrcFileName)
	Local Path$
	
	For I=Length To 1 Step -1
		If Mid(SrcFileName, I, 1)="\" Or Mid(SrcFileName, I, 1)="/"
			
			Path=Left(SrcFileName, I)
			SE_ParseLines(Path+FileName)
			Return
		EndIf
	Next
	
	SE_ParseLines(FileName)
End Function

Function SE_ParseLines(FileName$)
	Local File=ReadFile(FileName)

	If File=0
		SE_Warn(Null, "unable to open file "+Chr(34)+FileName+Chr(34))
		Return
	EndIf
	SeekFile File, 0
	While Not Eof(File)
	
		LineNumber=0
		LineText$=Trim(ReadLine(File))
		Length=Len(LineText)
		Offset=1
		RemBlockOpened = False
		RemOffset=0
		SkipLine=False
		LineNumber=LineNumber+1
		
		;	Preprocessor directive execution
		If Left(LineText, 1)="#"
				Delete Each SE_PPToken
				SE_ParseDirective(Right(LineText, Length-1))
				
				Local PP_Token.SE_PPToken=First SE_PPToken
				
				Select PP_Token\Token
					Case "include"
						PP_Token=After PP_Token
						SE_PP_Include(PP_Token\Token, FileName)
						If SE_ERROR Then Return
					Default:
						SE_Warn(Null, "undefined preprocessor directive execution ("+PP_Token\Token+")")
				End Select
				
				Delete Each SE_PPToken
				
				If SE_ERROR Then Return
				Length=0
		EndIf
		
		While Offset<Length
			If Mid(LineText, Offset, 2)="//"
				LineText=Left(LineText, Offset-1)
				Length=Len(LineText)
				Exit
				
			Else If Mid(LineText, Offset, 2)="/*"
				LineText=Left(LineText, Offset-1)+Right(LineText, Length-Offset-1)
				Length=Len(LineText)
				
				RemOffset=Offset
				RemBlockOpened=RemBlockOpened+1
				Offset=Offset+2

			Else If Mid(LineText, Offset, 2)="*/"
				RemBlockOpened=RemBlockOpened-1

				If RemBlockOpened=0
					If RemOffset=0
						LineText=Right(LineText, Length-Offset-1)
						Length=Len(LineText)
						Offset=1
					Else
						LineText=Left(LineText, RemOffset-1)+Right(LineText, Length-Offset-1)
						Length=Len(LineText)
						Offset=RemOffset+1
					EndIf
				Else
					Offset=Offset+2
				EndIf

			Else If Mid(LineText, Offset, 1)=Chr(34)
				Offset=Instr(LineText, Chr(34), Offset+1)
				If Offset=0
					SE_SetError("Terror at line "+LineNumber+": expecting '"+Chr(34)+"'")
					Return
				EndIf
				Offset=Offset+1
			Else
				Offset=Offset+1
			EndIf
		Wend

		If RemBlockOpened=False
			LineText=Trim(LineText)
			If Length Then Length=Len(LineText)
	
			If Length>0
				Local L.SE_Line=New SE_Line
				L\LineText=LineText
				L\Number=LineNumber
				L\Length=Length
			EndIf
		EndIf
		DebugLog LineText
	Wend

	If RemBlockOpened Then SE_Warn(Null, "expecting */")

	CloseFile(File)
End Function

;===========================================
;			Tokens parser
;===========================================

;Tokens
Const SE_TOKEN_INT=1
Const SE_TOKEN_FLOAT=2
Const SE_TOKEN_STRING=3
Const SE_TOKEN_IDENTIFIER=4
Const SE_TOKEN_OPENPAR=5
Const SE_TOKEN_CLOSEPAR=6
Const SE_TOKEN_OPERATOR=7
Const SE_TOKEN_COMMA=8
Const SE_TOKEN_SEMICOLON=9
Const SE_TOKEN_ARRAY_START=10
Const SE_TOKEN_ARRAY_END=11
Const SE_TOKEN_KEYWORD=12
Const SE_TOKEN_END_OF_LINE=13
Const SE_TOKEN_END_OF_FILE=14

Type SE_Token
	Field TokenType
	Field TokenText$
	Field TokenLine.SE_Line
	Field Offset
	Field Error$				;	token error message output
End Type

Function SE_CreateToken.SE_Token(TokenType, TokenText$, TokenLine.SE_Line, Offset, Error$)
	Local Token.SE_Token=New SE_Token
	Token\TokenType=TokenType
	Token\TokenText=TokenText
	Token\TokenLine=TokenLine
	Token\Offset=Offset
	Token\Error=Error
	Return Token
End Function

Const SE_OPERATORS_NUMBER=21
Global SE_OPERATORS$[SE_OPERATORS_NUMBER]

SE_OPERATORS[0]="++"
SE_OPERATORS[1]="--"
SE_OPERATORS[2]="**"
SE_OPERATORS[3]="<<"
SE_OPERATORS[4]=">>"
SE_OPERATORS[5]="<="
SE_OPERATORS[6]=">="
SE_OPERATORS[7]="=="
SE_OPERATORS[8]="!="
SE_OPERATORS[9]="~"
SE_OPERATORS[10]="+"
SE_OPERATORS[11]="-"
SE_OPERATORS[12]="*"
SE_OPERATORS[13]="/"
SE_OPERATORS[14]="%"
SE_OPERATORS[15]="<"
SE_OPERATORS[16]=">"
SE_OPERATORS[17]="&"
SE_OPERATORS[18]="^"
SE_OPERATORS[19]="|"
SE_OPERATORS[20]="="

Function SE_CheckOperator$(LineText$, Offset)
	Local Index

	For Index=0 To SE_OPERATORS_NUMBER-1
		Local Length=Len(SE_OPERATORS[Index])
		Local Token$=Mid(LineText, Offset, Length)
		If Token=SE_OPERATORS[Index] Then Return SE_OPERATORS[Index]
	Next
End Function

Function SE_CheckIdentifier(Token.SE_Token)
	Select Token\TokenText
		Case "end","const","local","static","global","public","def","if","then","else","select","case","for","while","do","repeat","return","continue","break"
			Token\TokenType=SE_TOKEN_KEYWORD
			Token\Error="keyword '"+Token\TokenText+"'"
			Return True
			
		Case "not", "and", "or", "typeof", "len", "args"
			Token\TokenType=SE_TOKEN_OPERATOR
			Token\Error="operator '"+Token\TokenText+"'"
			Return True
	End Select
End Function

Function SE_ParseTokens()
	Local L.SE_Line

	For L=Each SE_Line
	
		Local Offset=0
		Local LineText$=L\LineText
		Local Length=L\Length

		Repeat
			Local Token.SE_Token=Null
		
			Offset=Offset+1

			If Offset>Length
				SE_CreateToken(SE_TOKEN_END_OF_LINE, "", L, Offset, "end of line")
				Exit
			EndIf

			Local Char$=Mid(LineText, Offset, 1)

			While Asc(Char)<=32 Or Asc(Char)=127
				Offset=Offset+1
				Char=Mid(LineText, Offset, 1)
			Wend

			If Char="("
				SE_CreateToken(SE_TOKEN_OPENPAR, "(", L, Offset, "'('")

			Else If Char=")"
				SE_CreateToken(SE_TOKEN_CLOSEPAR, ")", L, Offset, "')'")

			Else If Char=","
				SE_CreateToken(SE_TOKEN_COMMA, ",", L, Offset, "','")
				
			Else If Char=";"
				SE_CreateToken(SE_TOKEN_SEMICOLON, ";", L, Offset, "';'")

			Else If Char="["
				SE_CreateToken(SE_TOKEN_ARRAY_START, "[", L, Offset, "'['")
				
			Else If Char="]"
				SE_CreateToken(SE_TOKEN_ARRAY_END, "]", L, Offset, "']'")

			Else If Asc(Char)=34	;	"
				Token=SE_CreateToken(SE_TOKEN_STRING, TokenText, L, Offset, "string")
				Local NextOffset=Instr(LineText, Chr(34), Offset+1)
				Token\TokenText=Mid(LineText, Offset+1, NextOffset-Offset-1)
				Offset=NextOffset

			Else If (Char>="0" And Char<="9")
				Token=SE_CreateToken(SE_TOKEN_INT, Char, L, Offset, "integer ")

				Repeat
					Offset=Offset+1
					Char=Mid(LineText, Offset, 1)
					
					If Char="."
						Token\TokenType=SE_TOKEN_FLOAT
						Token\Error="float "
						
					Else If Not (Char>="0" And Char<="9")
						Offset=Offset-1
						Exit
					EndIf
					
					Token\TokenText=Token\TokenText+Char
				Until Offset=Length

				Token\Error=Token\Error+Token\TokenText

			Else If Char="."
				Token=SE_CreateToken(SE_TOKEN_FLOAT, Char, L, Offset, "float ")
				
				Repeat
					Offset=Offset+1
					Char=Mid(LineText, Offset, 1)

					If Not (Char>="0" And Char=<"9")
						Offset=Offset-1
						Exit
					EndIf
					
					Token\TokenText=Token\TokenText+Char
				Until Offset=Length

				Token\Error=Token\Error+Token\TokenText

			Else If (Char>="a" And Char<="z") Or (Char>="A" And Char<="Z") Or Char="_"
				Token=SE_CreateToken(SE_TOKEN_IDENTIFIER, Char, L, Offset, "identifier ")

				Repeat
					Offset=Offset+1
					Char=Mid(LineText, Offset, 1)

					If Not ((Char>="0" And Char<="9") Or (Char>="a" And Char<="z") Or (Char>="A" And Char<="Z") Or Char="_")
						Offset=Offset-1
						Exit
					EndIf
					
					Token\TokenText=Token\TokenText+Char
				Until Offset=Length

				Token\TokenText=Lower(Token\TokenText)

				If SE_CheckIdentifier(Token.SE_Token)=False
					Token\Error=Token\Error+"'"+Token\TokenText+"'"
				EndIf
			Else
				Local Operator$=SE_CheckOperator(LineText, Offset)
				
				If Operator<>""
					Token=SE_CreateToken(SE_TOKEN_OPERATOR, Operator, L, Offset, "operator "+Operator)
					Offset=Offset+Len(Operator)-1
				Else
					SE_SetError("error at line "+LineNumber+": unknown character '"+Char+"'")
					Return
				EndIf
			EndIf
		Forever
	Next

	SE_CreateToken(SE_TOKEN_END_OF_FILE, "", Null, 0, "end of file")
End Function

;===========================================
;			Expression parser
;===========================================

Global SE_CURRENT_TOKEN.SE_Token
Global SE_ORDER

;Node types
Const SE_NODE_ROOT=1
Const SE_NODE_SINGLE=3
Const SE_NODE_CHAIN=4
Const SE_NODE_SEPARATOR=5
Const SE_NODE_END=6

Type SE_Node
	Field Token.SE_Token
	Field NodeType
	Field Level
	Field Order

	Field FirstNode.SE_Node[2]
	Field LastNode.SE_Node[2]
	
	Field Parent.SE_Node
	Field PrevNode.SE_Node, NextNode.SE_Node
End Type

Function SE_DefineNode.SE_Node(Token.SE_Token)
	Local NodeType
	Local NodeLevel

	SE_ORDER=SE_ORDER+1

	Select Token\TokenType
		Case SE_TOKEN_INT, SE_TOKEN_FLOAT, SE_TOKEN_STRING, SE_TOKEN_IDENTIFIER
			NodeType=SE_NODE_SINGLE
			NodeLevel=1

		Case SE_TOKEN_OPERATOR
			Select Token\TokenText
				Case "args"
					NodeType=SE_NODE_SINGLE
					NodeLevel=1
					
				Case "len", "typeof"
					NodeType=SE_NODE_SINGLE
					NodeLevel=2

				Case "++", "--"
					NodeType=SE_NODE_SINGLE
					NodeLevel=3

				Case "*"
					Local PrevToken.SE_Token=Before Token
					
					If PrevToken<>Null
						Select PrevToken\TokenType
							Case SE_TOKEN_OPENPAR, SE_TOKEN_ARRAY_START, SE_TOKEN_OPERATOR, SE_TOKEN_COMMA, SE_TOKEN_SEMICOLON, SE_TOKEN_KEYWORD, SE_TOKEN_END_OF_LINE
								NodeType=SE_NODE_SINGLE
								NodeLevel=3
							Default
								NodeType=SE_NODE_CHAIN
								NodeLevel=7
						End Select
					Else
						NodeType=SE_NODE_SINGLE
						NodeLevel=3
					EndIf
				
				Case "**"
					NodeType=SE_NODE_CHAIN
					NodeLevel=4

				Case "~"
					NodeType=SE_NODE_SINGLE
					NodeLevel=5

				Case "-"
					PrevToken=Before Token
					
					If PrevToken<>Null
						Select PrevToken\TokenType
							Case SE_TOKEN_OPENPAR, SE_TOKEN_ARRAY_START, SE_TOKEN_OPERATOR, SE_TOKEN_COMMA, SE_TOKEN_SEMICOLON, SE_TOKEN_KEYWORD, SE_TOKEN_END_OF_LINE
								NodeType=SE_NODE_SINGLE
								NodeLevel=6
							Default
								NodeType=SE_NODE_CHAIN
								NodeLevel=8
						End Select
					Else
						NodeType=SE_NODE_SINGLE
						NodeLevel=6
					EndIf

				Case "/", "%"
					NodeType=SE_NODE_CHAIN
					NodeLevel=7

				Case "+"
					NodeType=SE_NODE_CHAIN
					NodeLevel=8

				Case "<<", ">>"
					NodeType=SE_NODE_SINGLE
					NodeLevel=9

				Case "&"
					NodeType=SE_NODE_CHAIN
					NodeLevel=10

				Case "^"
					NodeType=SE_NODE_CHAIN
					NodeLevel=11

				Case "|"
					NodeType=SE_NODE_CHAIN
					NodeLevel=12

				Case "<", "<=", ">", ">=", "==", "!="
					NodeType=SE_NODE_CHAIN
					NodeLevel=13

				Case "not"
					NodeType=SE_NODE_SINGLE
					NodeLevel=14

				Case "and"
					NodeType=SE_NODE_CHAIN
					NodeLevel=15
					
				Case "or"
					NodeType=SE_NODE_CHAIN
					NodeLevel=16

				Case "="
					NodeType=SE_NODE_SINGLE
					NodeLevel=17
			End Select

		Case SE_TOKEN_COMMA
			NodeType=SE_NODE_SEPARATOR

		Case SE_TOKEN_OPENPAR, SE_TOKEN_ARRAY_START
			NodeType=SE_NODE_ROOT
			NodeLevel=1

		Case SE_TOKEN_CLOSEPAR, SE_TOKEN_ARRAY_END
			NodeType=SE_NODE_END

		Default
			Return
	End Select

	Local Node.SE_Node=New SE_Node
	Node\Token=Token
	Node\NodeType=NodeType
	Node\Level=NodeLevel
	Node\Order=SE_ORDER

	Return Node
End Function

Function SE_LinkNode(Node.SE_Node, Parent.SE_Node, AddLast=True)
	Local PrevParent.SE_Node=Node\Parent
	Local Arrangement

	If PrevParent<>Null
		Arrangement=Node\Order>PrevParent\Order
	
		If Node\PrevNode<>Null
			Node\PrevNode\NextNode=Node\NextNode
		Else
			PrevParent\FirstNode[Arrangement]=Node\NextNode
		EndIf
		
		If Node\NextNode<>Null
			Node\NextNode\PrevNode=Node\PrevNode
		Else
			PrevParent\LastNode[Arrangement]=Node\PrevNode
		EndIf

		Node\PrevNode=Null
		Node\NextNode=Null
	EndIf

	Node\Parent=Parent
	Arrangement=Node\Order>Parent\Order

	If AddLast
		If Parent\LastNode[Arrangement]<>Null
			Node\PrevNode=Parent\LastNode[Arrangement]
			Parent\LastNode[Arrangement]\NextNode=Node
			Parent\LastNode[Arrangement]=Node
		Else
			Parent\FirstNode[Arrangement]=Node
			Parent\LastNode[Arrangement]=Node
		EndIf
	Else
		If Parent\FirstNode[Arrangement]<>Null
			Node\NextNode=Parent\FirstNode[Arrangement]
			Parent\FirstNode[Arrangement]\PrevNode=Node
			Parent\FirstNode[Arrangement]=Node
		Else
			Parent\FirstNode[Arrangement]=Node
			Parent\LastNode[Arrangement]=Node
		EndIf
	EndIf

End Function

Function SE_ParseExpression(Root.SE_Node, CurrentToken.SE_Token)
	If CurrentToken<>Null
		SE_CURRENT_TOKEN=CurrentToken
		SE_ORDER=0
	EndIf

	Local Parent.SE_Node=Root
	Local Node.SE_Node

	Repeat
		If SE_CURRENT_TOKEN\TokenType=SE_TOKEN_END_OF_LINE
			If Root\Token<>Null
				SE_Error_EOL(SE_CURRENT_TOKEN)
				Return
			Else
				Return
			EndIf
		EndIf
		
		Node=SE_DefineNode(SE_CURRENT_TOKEN)

		If Node=Null Then Return

		Select Node\NodeType
			Case SE_NODE_ROOT
				SE_LinkNode(Node, Parent)
				SE_CURRENT_TOKEN=After SE_CURRENT_TOKEN
				SE_ParseExpression(Node, Null)
				If SE_ERROR Then Return
				Parent=Node

			Case SE_NODE_SINGLE, SE_NODE_CHAIN
				Local LinkRight=False
			
				Repeat
					If Node\Level<Parent\Level Or Parent=Root
						LinkRight=True
						Exit
						
					Else If Node\Level=Parent\Level
						If Node\NodeType=SE_NODE_SINGLE
							SE_Error_Unexpected(SE_CURRENT_TOKEN)
							Return
						EndIf
						
						Parent=Parent\Parent
						Exit
					EndIf
					
					Parent=Parent\Parent
				Forever
				
				If LinkRight
					Local Child.SE_Node=Parent\LastNode[1]
					
					While Child<>Null
						If Child\NodeType>SE_NODE_CHAIN Then Exit
						Local PrevChild.SE_Node=Child\PrevNode
						SE_LinkNode(Child, Node, False)
						Child=PrevChild
					Wend
				EndIf

				SE_LinkNode(Node, Parent)
				
				Parent=Node

			Case SE_NODE_SEPARATOR
				Parent=Root
				SE_LinkNode(Node, Root)

			Case SE_NODE_END
				If Root\Token=Null
					SE_Error(SE_CURRENT_TOKEN, "mismatched brackets")
					Return
					
				Else If Root\Token\TokenType<>(SE_CURRENT_TOKEN\TokenType-1) ;If you don't understand - see token constants
					SE_Error(SE_CURRENT_TOKEN, "mismatched brackets")
					Return
				EndIf
				
				SE_LinkNode(Node, Root)
				Return
		End Select
		
		SE_CURRENT_TOKEN=After SE_CURRENT_TOKEN
	Forever
End Function

;===========================================
;			Chunk parser
;===========================================

Const SE_CHUNK_KEYWORD=1
Const SE_CHUNK_EXPRESSION=2
Const SE_CHUNK_TERMINATOR=3		; Hasta la vista, line!
Const SE_CHUNK_EOF=4

;Chunk
Type SE_Chunk
	Field ChunkType
	Field Token.SE_Token
	Field Node.SE_Node
End Type

Function SE_ParseChunks()
	SE_CURRENT_TOKEN=First SE_Token

	While SE_CURRENT_TOKEN<>Null
		Local Chunk.SE_Chunk=New SE_Chunk
		Chunk\Token=SE_CURRENT_TOKEN
		
		If SE_CURRENT_TOKEN\TokenType=SE_TOKEN_KEYWORD
			Chunk\ChunkType=SE_CHUNK_KEYWORD
			SE_CURRENT_TOKEN=After SE_CURRENT_TOKEN
			
		Else If SE_CURRENT_TOKEN\TokenType=SE_TOKEN_SEMICOLON Or SE_CURRENT_TOKEN\TokenType=SE_TOKEN_END_OF_LINE
			Chunk\ChunkType=SE_CHUNK_TERMINATOR
			SE_CURRENT_TOKEN=After SE_CURRENT_TOKEN

		Else If SE_CURRENT_TOKEN\TokenType=SE_TOKEN_END_OF_FILE
			Chunk\ChunkType=SE_CHUNK_EOF
			SE_CURRENT_TOKEN=After SE_CURRENT_TOKEN
			
		Else
			Chunk\ChunkType=SE_CHUNK_EXPRESSION
			Chunk\Node=New SE_Node
			Chunk\Node\NodeType=SE_NODE_ROOT
			SE_ParseExpression(Chunk\Node, SE_CURRENT_TOKEN)
		EndIf
	Wend
End Function

;===========================================
;			Block parser
;===========================================

Const SE_BLOCK_EXPRESSION=1				; common expression type
Const SE_BLOCK_CONST=2					; constant
Const SE_BLOCK_VAR=3					; local, static, global, public
Const SE_BLOCK_DEF=4					; def
Const SE_BLOCK_IF=5						; if
Const SE_BLOCK_ELSE=6					; else
Const SE_BLOCK_SELECT=7					; select
Const SE_BLOCK_CASE=8					; case
Const SE_BLOCK_FOR=9					; for
Const SE_BLOCK_WHILE=10					; while
Const SE_BLOCK_DO=11					; do
Const SE_BLOCK_REPEAT=12				; repeat
Const SE_BLOCK_BREAK=13					; break
Const SE_BLOCK_CONTINUE=14				; continue
Const SE_BLOCK_RETURN=15				; return

Type SE_Block
	Field BlockType, Description$
	Field Node.SE_Node
	Field Token.SE_Token

	Field Parent.SE_Block
	Field PrevBlock.SE_Block, NextBlock.SE_Block
	Field FirstBlock.SE_Block, LastBlock.SE_Block
End Type

Global SE_CURRENT_CHUNK.SE_Chunk
Global SE_ROOT_BLOCK.SE_Block


Function SE_LinkBlock(Block.SE_Block, Parent.SE_Block)
	If Parent\LastBlock<>Null
		Block\PrevBlock=Parent\LastBlock
		Parent\LastBlock\NextBlock=Block
		Parent\LastBlock=Block
	Else
		Parent\FirstBlock=Block
		Parent\LastBlock=Block
	EndIf

	Block\Parent=Parent
End Function

Function SE_CheckChunk(Expecting)
	If SE_CURRENT_CHUNK\ChunkType=Expecting
		Return
	Else
		SE_Error_Unexpected(SE_CURRENT_CHUNK\Token)
		Return True
	EndIf
End Function

Function SE_ParseBlock(Parent.SE_Block)
	Local Block.SE_Block=New SE_Block
	Local Child.SE_Block

	Block\Token=SE_CURRENT_CHUNK\Token
	
	SE_LinkBlock(Block, Parent)

	Select SE_CURRENT_CHUNK\ChunkType
		Case SE_CHUNK_KEYWORD
			Select SE_CURRENT_CHUNK\Token\TokenText
				Case "const"
					Block\BlockType=SE_BLOCK_CONST

					;const [expression] end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					;const expression [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

				Case "local", "static", "global", "public"
					Block\BlockType=SE_BLOCK_VAR
					Block\Description=SE_CURRENT_CHUNK\Token\TokenText

					;local [expression] end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					;local expression [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

				Case "def"
					If Parent<>SE_ROOT_BLOCK
						SE_Error(SE_CURRENT_CHUNK\Token, "function can be defined only in main program")
						Return
					EndIf
					
					Block\BlockType=SE_BLOCK_DEF

					;def [my_function()] end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					;def my_function() [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

					;function body
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					SE_ParseBlocks(Block)

				Case "if"
					Block\BlockType=SE_BLOCK_IF

					;if [expression]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_KEYWORD
						If SE_CURRENT_CHUNK\Token\TokenText="then" Then SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					EndIf

					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR
						SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						SE_ParseBlocks(Block)
					Else
						SE_ParseBlock(Block)
					EndIf

				Case "else"
					Block\BlockType=SE_BLOCK_ELSE
					
					If Parent\BlockType<>SE_BLOCK_IF And Parent\BlockType<>SE_BLOCK_SELECT
						SE_Error(SE_CURRENT_CHUNK\Token, "'else' belongs to nothing")
						Return
					EndIf

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						
					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_KEYWORD
						If SE_CURRENT_CHUNK\Token\TokenText="if" And Parent\BlockType=SE_BLOCK_IF
							SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
							If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
							
							Block\Node=SE_CURRENT_CHUNK\Node
							SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						Else
							SE_Error_Unexpected(SE_CURRENT_CHUNK\Token)
							Return
						EndIf
						
					Else If SE_CURRENT_CHUNK\ChunkType<>SE_CHUNK_TERMINATOR
						SE_Error_Unexpected(SE_CURRENT_CHUNK\Token)
						Return
					EndIf

				Case "select"
					Block\BlockType=SE_BLOCK_SELECT
					
					;select [expression] end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					;select expression [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

					;inner block
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					SE_ParseBlocks(Block)

				Case "case"
					Block\BlockType=SE_BLOCK_CASE
					
					If Parent\BlockType<>SE_BLOCK_SELECT
						SE_Error(SE_CURRENT_CHUNK\Token, "'case' belongs to nothing")
						Return
					EndIf

					;case [expression] end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					;case expression [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

				Case "for"
					Block\BlockType=SE_BLOCK_FOR

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK

					;for initial; condition; increment
					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_EXPRESSION
						;for [initial]; condition; increment
						Child=New SE_Block
						Child\BlockType=SE_BLOCK_EXPRESSION
						Child\Description="init"
						Child\Node=SE_CURRENT_CHUNK\Node
						SE_LinkBlock(Child, Block)

						;for initial[;] condition; increment
						SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						If (SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR And SE_CURRENT_CHUNK\Token\TokenText=";")=False
							SE_Error_Expecting(SE_CURRENT_CHUNK\Token, "';'")
							Return
						EndIf
						
					Else If (SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR And SE_CURRENT_CHUNK\Token\TokenText=";")
						;for [empty initial]; condition; increment
						Child=New SE_Block
						Child\BlockType=SE_BLOCK_EXPRESSION
						Child\Description="init"
						SE_LinkBlock(Child, Block)
						
					Else
						;for [wrong initial]; condition; increment
						SE_Error_Expecting(SE_CURRENT_CHUNK\Token, "expression")
						Return
					EndIf

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK

					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_EXPRESSION
						;for initial; [condition]; increment
						Child=New SE_Block
						Child\BlockType=SE_BLOCK_EXPRESSION
						Child\Node=SE_CURRENT_CHUNK\Node
						SE_LinkBlock(Child, Block)

						;for initial; [condition]; increment
						SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						If (SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR And SE_CURRENT_CHUNK\Token\TokenText=";")=False
							SE_Error_Expecting(SE_CURRENT_CHUNK\Token, "';'")
							Return
						EndIf
						
					Else If (SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR And SE_CURRENT_CHUNK\Token\TokenText=";")
						;for initial; [empty condition]; increment
						Child=New SE_Block
						Child\BlockType=SE_BLOCK_EXPRESSION
						SE_LinkBlock(Child, Block)
					Else
						;for initial; [wrong condition]; increment
						SE_Error_Expecting(SE_CURRENT_CHUNK\Token, "expression")
						Return
					EndIf

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK

					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_EXPRESSION
						;for initial; condition; [increment]
						Child=New SE_Block
						Child\BlockType=SE_BLOCK_EXPRESSION
						Child\Node=SE_CURRENT_CHUNK\Node
						SE_LinkBlock(Child, Block)

						;for initial; condition; increment [end_of_line]
						SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						If Not SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR
							SE_Error_Expecting(SE_CURRENT_CHUNK\Token, "';'")
							Return
						EndIf
						
					Else If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_TERMINATOR
						Child=New SE_Block
						Child\BlockType=SE_BLOCK_EXPRESSION
						SE_LinkBlock(Child, Block)

					Else
						;for initial; contidion; [increment]
						SE_Error_Expecting(SE_CURRENT_CHUNK\Token, "expression")
						Return
					EndIf

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					SE_ParseBlocks(Block)

				Case "while"
					Block\BlockType=SE_BLOCK_WHILE
					
					;while [expression] end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_EXPRESSION) Then Return
					Block\Node=SE_CURRENT_CHUNK\Node

					;while expression [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					SE_ParseBlocks(Block)

				Case "do"
					Block\BlockType=SE_BLOCK_DO

					;do [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					SE_ParseBlocks(Block)

				Case "repeat"
					Block\BlockType=SE_BLOCK_REPEAT
					
					If Parent\BlockType<>SE_BLOCK_DO
						SE_Error(SE_CURRENT_CHUNK\Token, "'repeat' without 'do'")
						Return
					EndIf

					;repeat [if] condition end_of_line
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK

					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_KEYWORD And SE_CURRENT_CHUNK\Token\TokenText="if"
						SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK

						;repeat if [condition] end_of_line
						If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_EXPRESSION
							Block\Node=SE_CURRENT_CHUNK\Node
	
							;repeat if condition [end_of_line]
							SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
							If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return
						EndIf
					Else
						;repeat [end_of_line]
						If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return
					EndIf

					Return True

				Case "break"
					Block\BlockType=SE_BLOCK_BREAK
					
					;break [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

				Case "continue"
					Block\BlockType=SE_BLOCK_CONTINUE
					
					;continue [end_of_line]
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
					If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

				Case "return"
					Block\BlockType=SE_BLOCK_RETURN
					
					SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK

					;return [expression] end_of_line
					If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_EXPRESSION
						Block\Node=SE_CURRENT_CHUNK\Node
						
						;return expression [end_of_line]
						SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
						If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return
						
					Else If SE_CURRENT_CHUNK\ChunkType<>SE_CHUNK_TERMINATOR
						SE_Error_Unexpected(SE_CURRENT_CHUNK\Token)
						Return
					EndIf
			End Select

		Case SE_CHUNK_EXPRESSION
			Block\BlockType=SE_BLOCK_EXPRESSION
			
			;[expression] end_of_line
			Block\Node=SE_CURRENT_CHUNK\Node
			
			;expression [end_of_line]
			SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
			If SE_CheckChunk(SE_CHUNK_TERMINATOR) Then Return

		Default
			SE_Error_Unexpected(SE_CURRENT_CHUNK\Token)
	End Select
End Function

Function SE_ParseBlocks(Parent.SE_Block)
	If Parent=Null
		SE_CURRENT_CHUNK=First SE_Chunk
		SE_ROOT_BLOCK=New SE_Block
		Parent=SE_ROOT_BLOCK
		Local Root=True
	EndIf

	While SE_CURRENT_CHUNK<>Null		
		If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_KEYWORD And SE_CURRENT_CHUNK\Token\TokenText="end"
			If Root
				SE_Error_Unexpected(SE_CURRENT_CHUNK\Token)
				Return
			Else
				SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
				Exit
			EndIf

		Else If SE_CURRENT_CHUNK\ChunkType=SE_CHUNK_EOF
			If Root
				Exit
			Else
				SE_Error(Parent\Token, "unexpected end of construction (encountered end of file)")
				Return
			EndIf
		EndIf
		
		If SE_ParseBlock(Parent) Then Exit
		If SE_ERROR Then Return
		SE_CURRENT_CHUNK=After SE_CURRENT_CHUNK
	Wend
End Function

;===========================================
;			Definitions
;===========================================

Type SE_ConstantDef
	Field Name$
	Field ValueType
	Field Value$
	Field Super			; Constant available from all files

	Field Token.SE_Token	; Token for error output
End Type

Type SE_LocalDef
	Field Value.SE_VF_Value

	Field Name$
	Field NextVar.SE_LocalDef

	Field Token.SE_Token	; Token for error output
End Type

Type SE_StaticDef
	Field Value.SE_VF_Value

	Field Name$
	Field NextVar.SE_StaticDef

	Field Token.SE_Token	; Token for error output
End Type

Type SE_GlobalDef
	Field Value.SE_VF_Value

	Field Name$

	Field Token.SE_Token	; Token for error output
End Type

Type SE_PublicDef
	Field Value.SE_VF_Value

	Field Name$

	Field Token.SE_Token	; Token for error output
End Type

Type SE_FunctionDef
	Field Func.SE_VF_FuncPtr
	
	;Variables
	Field FirstLocal.SE_LocalDef, LastLocal.SE_LocalDef
	Field FirstStatic.SE_StaticDef, LastStatic.SE_StaticDef

	;Temp stack
	Field FirstTemp.SE_TempValue, LastTemp.SE_TempValue, TopTemp.SE_TempValue

	Field Token.SE_Token	; Token for error output
End Type

Global SE_FUNCTION_DEF.SE_FunctionDef

Function SE_DefineConst(Name$, ValueType, Value$, Super, Token.SE_Token)
	If SE_CheckConstant(Name) Then Return
	
	C.SE_ConstantDef=New SE_ConstantDef
	C\Name=Name
	C\ValueType=ValueType
	C\Value=Value
	C\Super=Super
	C\Token=Token
	
	Return True
End Function

Function SE_DefineLocal(Name$, Argument=False, Token.SE_Token)
	If SE_CheckLocalScope(Name) Then Return

	Local L.SE_LocalDef=New SE_LocalDef
	L\Name=Name
	
	L\Value=SE_VF_CreateTransient(SE_FUNCTION_DEF\Func, Argument)

	L\Token=Token
	
	If SE_FUNCTION_DEF\FirstLocal=Null
		SE_FUNCTION_DEF\FirstLocal=L
		SE_FUNCTION_DEF\LastLocal=L
	Else
		SE_FUNCTION_DEF\LastLocal\NextVar=L
		SE_FUNCTION_DEF\LastLocal=L
	EndIf
	
	Return True
End Function

Function SE_DefineStatic(Name$, Token.SE_Token)
	If SE_CheckLocalScope(Name) Then Return

	Local S.SE_StaticDef=New SE_StaticDef
	S\Name=Name
	
	S\Value=SE_VF_CreateStatic()

	S\Token=Token
	
	If SE_FUNCTION_DEF\FirstStatic=Null
		SE_FUNCTION_DEF\FirstStatic=S
		SE_FUNCTION_DEF\LastStatic=S
	Else
		SE_FUNCTION_DEF\LastStatic\NextVar=S
		SE_FUNCTION_DEF\LastStatic=S
	EndIf
	
	Return True
End Function

Function SE_DefineGlobal(Name$, Token.SE_Token)
	If SE_CheckGlobalScope(Name) Then Return

	Local G.SE_GlobalDef=New SE_GlobalDef
	G\Name=Name
	G\Value=SE_VF_CreateStatic()
	G\Token=Token
	
	Return True
End Function

Function SE_DefinePublic(Name$, Token.SE_Token)
	If SE_CheckGlobalScope(Name) Then Return

	Local P.SE_PublicDef=New SE_PublicDef
	P\Name=Name
	P\Value=SE_VF_CreatePublic(Name)
	P\Token=Token
	
	Return True
End Function

Function SE_DefineFunction.SE_FunctionDef(Name$, Token.SE_Token)
	If SE_CheckFunction(Name) Then Return

	Local F.SE_FunctionDef
	
	F=New SE_FunctionDef
	F\Func=SE_VF_CreateFuncPtr(Name)
	F\Token=Token
	Return F
End Function

Function SE_CheckConstant(Name$)
	Local C.SE_ConstantDef
	
	For C=Each SE_ConstantDef
		If C\Name=Name Return True
	Next
End Function

Function SE_CheckLocalScope(Name$)
	Local L.SE_LocalDef
	Local S.SE_StaticDef

	;Local variable
	L=SE_FUNCTION_DEF\FirstLocal
	
	While L<>Null
		If L\Name=Name
			Return True
		EndIf
		
		L=L\NextVar
	Wend
	
	;Static variable
	S=SE_FUNCTION_DEF\FirstStatic
	
	While S<>Null
		If S\Name=Name
			Return True
		EndIf
		
		S=S\NextVar
	Wend
End Function

Function SE_CheckGlobalScope(Name$)
	Local G.SE_GlobalDef
	Local P.SE_PublicDef

	;Global
	For G=Each SE_GlobalDef
		If G\Name=Name
			Return True
		EndIf
	Next
	
	;Public
	For P=Each SE_PublicDef
		If P\Name=Name
			Return True
		EndIf
	Next
End Function

Function SE_CheckVariable(Name$)
	Local L.SE_LocalDef
	Local S.SE_StaticDef
	Local G.SE_GlobalDef
	Local P.SE_PublicDef
	
	;Local variable
	L=SE_FUNCTION_DEF\FirstLocal
	
	While L<>Null
		If L\Name=Name
			Return True
		EndIf
		
		L=L\NextVar
	Wend
	
	;Static variable
	S=SE_FUNCTION_DEF\FirstStatic
	
	While S<>Null
		If S\Name=Name
			Return True
		EndIf
		
		S=S\NextVar
	Wend

	;Global
	For G=Each SE_GlobalDef
		If G\Name
			Return True
		EndIf
	Next
	
	;Public
	For P=Each SE_PublicDef
		If P\Name
			Return True
		EndIf
	Next
End Function

Function SE_CheckFunction(Name$)
	Local Func.SE_FunctionDef

	For Func=Each SE_FunctionDef
		If Func\Func\Name=Name Then Return True
	Next
End Function

Function SE_GetConstant.SE_VF_Value(Name$)
	Local C.SE_ConstantDef
	
	For C=Each SE_ConstantDef
		If C\Name=Name Return SE_VF_CreateBasic(C\ValueType, C\Value)
	Next
End Function

Function SE_GetVariable.SE_VF_Value(Name$)
	;Local variable
	Local L.SE_LocalDef=SE_FUNCTION_DEF\FirstLocal
	
	While L<>Null
		If L\Name=Name
			Return L\Value
		EndIf
		
		L=L\NextVar
	Wend
	
	;Static variable
	Local S.SE_StaticDef=SE_FUNCTION_DEF\FirstStatic
	
	While S<>Null
		If S\Name=Name
			Return S\Value
		EndIf
		
		S=S\NextVar
	Wend
	
	;Global
	Local G.SE_GlobalDef
	
	For G=Each SE_GlobalDef
		If G\Name=Name
			Return G\Value
		EndIf
	Next
	
	;Public
	Local P.SE_PublicDef
	
	For P=Each SE_PublicDef
		If P\Name=Name
			Return P\Value
		EndIf
	Next
	
	;Unless - create local variable
	L=New SE_LocalDef
	L\Name=Name
	L\Value=SE_VF_CreateTransient(SE_FUNCTION_DEF\Func)
	
	If SE_FUNCTION_DEF\FirstLocal=Null
		SE_FUNCTION_DEF\FirstLocal=L
		SE_FUNCTION_DEF\LastLocal=L
	Else
		SE_FUNCTION_DEF\LastLocal\NextVar=L
		SE_FUNCTION_DEF\LastLocal=L
	EndIf
	
	Return L\Value
End Function

Function SE_GetFunction.SE_VF_FuncPtr(Name$)
	Local Func.SE_FunctionDef

	For Func=Each SE_FunctionDef
		If Func\Func\Name=Name Then Return Func\Func
	Next
End Function

Function SE_GetFunctionDef.SE_FunctionDef(Name$)
	Local Func.SE_FunctionDef

	For Func=Each SE_FunctionDef
		If Func\Func\Name=Name Then Return Func
	Next
End Function

;===========================================
;			Definitions parser
;===========================================

Global SE_CURRENT_BLOCK.SE_Block
Global SE_MAIN_FUNCTION.SE_FunctionDef

Function SE_ParseDefinition(Block.SE_Block)
	Local Token.SE_Token=Block\Token
	Local LastToken.SE_Token=Token

	Local Node.SE_Node
	Local LeftNode.SE_Node
	Local RightNode.SE_Node

	Select Block\BlockType
		Case SE_BLOCK_CONST
			Node=Block\Node\FirstNode[1]
			
			Repeat
				If Node=Null
					SE_Error_EOL(LastToken)
					Return
				EndIf

				LastToken=Node\Token

				If (Node\Token\TokenType=SE_TOKEN_OPERATOR And Node\Token\TokenText="=")=False
					SE_Error(Node\Token, "constant must be initialized")
					Return
				EndIf

				LeftNode=Node\FirstNode[0]
				RightNode=Node\FirstNode[1]

				If LeftNode=Null
					SE_Error(Node\Token, "expecting identifier")
					Return
					
				Else If LeftNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
					SE_Error_Expecting(LeftNode\Token, "identifier")
					Return
				EndIf

				If RightNode=Null
					SE_Error(Node\Token, "expecting identifier")
					Return
					
				Else If RightNode\Token\TokenType>SE_TOKEN_IDENTIFIER
					SE_Error_Expecting(RightNode\Token, "constant value")
					Return
				EndIf

				If RightNode\Token\TokenType=SE_TOKEN_IDENTIFIER
					Local ConstantDef.SE_ConstantDef

					For ConstantDef=Each SE_ConstantDef
						If ConstantDef\Name=RightNode\Token\TokenText Then Exit
					Next

					If ConstantDef<>Null
						If SE_DefineConst(LeftNode\Token\TokenText, ConstantDef\ValueType, ConstantDef\Value, False, LeftNode\Token)
							SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
							Return
						EndIf
					Else
						SE_Error(RightNode\Token, "cannot assign value of variable '"+RightNode\Token\TokenText+"' to constant '"+LeftNode\Token\TokenText+"'")
						Return
					EndIf
				Else
					If SE_DefineConst(LeftNode\Token\TokenText, RightNode\Token\TokenType, RightNode\Token\TokenText, False, LeftNode\Token)=False
						SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
						Return
					EndIf
				EndIf

				Node=Node\NextNode
						
				If Node=Null
					Exit
				Else If Node\Token\TokenType=SE_TOKEN_COMMA
					Node=Node\NextNode
				EndIf
			Forever

		Case SE_BLOCK_VAR
			Node=Block\Node\FirstNode[1]
		
			Select Block\Description
				Case "local"
					Repeat
						If Node=Null
							SE_Error_EOL(LastToken)
							Return
						EndIf

						LastToken=Node\Token
						
						If Node\Token\TokenType=SE_TOKEN_IDENTIFIER
							If SE_DefineLocal(Node\Token\TokenText, False, Node\Token)=False
								SE_Error(Node\Token, "duplicate identifier '"+Node\Token\TokenText+"'")
								Return
							EndIf
							
						Else If Node\Token\TokenType=SE_TOKEN_OPERATOR And Node\Token\TokenText="="
							LeftNode=Node\FirstNode[0]
							
							If LeftNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
								SE_Error_Expecting(LeftNode\Token, "identifier")
								Return
							EndIf
							
							If SE_DefineLocal(LeftNode\Token\TokenText, False, LeftNode\Token)=False
								SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
								Return
							EndIf
						Else
							SE_Error_Expecting(Node\Token, "identifier")
							Return
						EndIf
	
						Node=Node\NextNode
						
						If Node=Null
							Exit
						Else If Node\Token\TokenType=SE_TOKEN_COMMA
							Node=Node\NextNode
						EndIf
					Forever

				Case "static"
					Repeat
						If Node=Null
							SE_Error_EOL(LastToken)
							Return
						EndIf

						LastToken=Node\Token
						
						If Node\Token\TokenType=SE_TOKEN_IDENTIFIER
							If SE_DefineStatic(Node\Token\TokenText, Node\Token)=False
								SE_Error(Node\Token, "duplicate identifier '"+Node\Token\TokenText+"'")
								Return
							EndIf
							
						Else If Node\Token\TokenType=SE_TOKEN_OPERATOR And Node\Token\TokenText="="
							LeftNode=Node\FirstNode[0]
							
							If LeftNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
								SE_Error_Expecting(LeftNode\Token, "identifier")
								Return
							EndIf
							
							If SE_DefineStatic(LeftNode\Token\TokenText, LeftNode\Token)=False
								SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
								Return
							EndIf
						Else
							SE_Error_Expecting(Node\Token, "identifier")
							Return
						EndIf
	
						Node=Node\NextNode
						
						If Node=Null
							Exit
						Else If Node\Token\TokenType=SE_TOKEN_COMMA
							Node=Node\NextNode
						EndIf
					Forever

				Case "global"
					Repeat
						If Node=Null
							SE_Error_EOL(LastToken)
							Return
						EndIf

						LastToken=Node\Token
						
						If Node\Token\TokenType=SE_TOKEN_IDENTIFIER
							If SE_DefineGlobal(Node\Token\TokenText, Node\Token)=False
								SE_Error(Node\Token, "duplicate identifier '"+Node\Token\TokenText+"'")
								Return
							EndIf
							
						Else If Node\Token\TokenType=SE_TOKEN_OPERATOR And Node\Token\TokenText="="
							LeftNode=Node\FirstNode[0]
							
							If LeftNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
								SE_Error_Expecting(LeftNode\Token, "identifier")
								Return
							EndIf
							
							If SE_DefineGlobal(LeftNode\Token\TokenText, LeftNode\Token)=False
								SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
								Return
							EndIf
						Else
							SE_Error_Expecting(Node\Token, "identifier")
							Return
						EndIf
	
						Node=Node\NextNode
						
						If Node=Null
							Exit
						Else If Node\Token\TokenType=SE_TOKEN_COMMA
							Node=Node\NextNode
						EndIf
					Forever

				Case "public"
					Repeat
						If Node=Null
							SE_Error_EOL(LastToken)
							Return
						EndIf

						LastToken=Node\Token
						
						If Node\Token\TokenType=SE_TOKEN_IDENTIFIER
							If SE_DefinePublic(Node\Token\TokenText, Node\Token)=False
								SE_Error(Node\Token, "duplicate identifier '"+Node\Token\TokenText+"'")
								Return
							EndIf
							
						Else If Node\Token\TokenType=SE_TOKEN_OPERATOR And Node\Token\TokenText="="
							LeftNode=Node\FirstNode[0]
							
							If LeftNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
								SE_Error_Expecting(LeftNode\Token, "identifier")
								Return
							EndIf
							
							If SE_DefinePublic(LeftNode\Token\TokenText, LeftNode\Token)=False
								SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
								Return
							EndIf
						Else
							SE_Error_Expecting(Node\Token, "identifier")
							Return
						EndIf
	
						Node=Node\NextNode

						If Node=Null
							Exit
						Else If Node\Token\TokenType=SE_TOKEN_COMMA
							Node=Node\NextNode
						EndIf
					Forever
			End Select

		Case SE_BLOCK_DEF
			If Block\Parent<>SE_ROOT_BLOCK
				SE_SetError("error at line "+Token\TokenLine\Number+": function can be defined only in main program")
				Return
			EndIf

			Node=Block\Node\FirstNode[1]

			If Node=Null
				SE_Error(Block\Token, "expecting function identifier")
				Return
			EndIf

			If Node\Token\TokenType<>SE_TOKEN_IDENTIFIER
				SE_Error_Expecting(Node\Token, "identifier")
				Return
			EndIf

			SE_FUNCTION_DEF=SE_DefineFunction(Node\Token\TokenText, Node\Token)

			If SE_FUNCTION_DEF=Null
				SE_Error(Token, "duplicate identifier '"+Node\Token\TokenText+"'")
				Return
			EndIf

			Node=Node\FirstNode[1]

			If Node=Null
				SE_Error(Token, "expecting '('")
				Return
			EndIf

			If Node\Token\TokenType<>SE_TOKEN_OPENPAR
				SE_Error_Expecting(Node\Token, "'('")
				Return
			EndIf

			Node=Node\FirstNode[1]

			Local Continue

			Repeat
				If Node\Token\TokenType=SE_TOKEN_CLOSEPAR
					If Continue
						SE_Error_Unexpected(Node\Token)
						Return
					Else
						Exit
					EndIf
					
				Else If Node\Token\TokenType=SE_TOKEN_IDENTIFIER
					If SE_DefineLocal(Node\Token\TokenText, True, Node\Token)=False
						SE_Error(Node\Token, "duplicate identifier '"+Node\Token\TokenText+"'")
						Return
					EndIf

					Continue=False
						
				Else If Node\Token\TokenType=SE_TOKEN_OPERATOR And Node\Token\TokenText="="
					LeftNode=Node\FirstNode[0]
					
					If LeftNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
						SE_Error_Expecting(LeftNode\Token, "identifier")
						Return
					EndIf
					
					If SE_DefineLocal(LeftNode\Token\TokenText, True, LeftNode\Token)=False
						SE_Error(LeftNode\Token, "duplicate identifier '"+LeftNode\Token\TokenText+"'")
						Return
					EndIf

					Continue=False
				Else
					SE_Error_Expecting(Node\Token, "identifier")
					Return
				EndIf

				Node=Node\NextNode
				
				If Node=Null
					SE_Error_EOL(Token)
					Return
					
				Else If Node\Token\TokenType=SE_TOKEN_COMMA
					Node=Node\NextNode
					Continue=True
				EndIf
			Forever
	End Select

	;DebugLog Block\BlockType
End Function

Function SE_ParseDefinitions()
	Local Block.SE_Block=SE_ROOT_BLOCK\FirstBlock

	SE_MAIN_FUNCTION=SE_DefineFunction("_main", Null)

	;Step 1 - Read definitions
	While Block<>Null
		If Block\Parent=SE_ROOT_BLOCK Then SE_FUNCTION_DEF=SE_MAIN_FUNCTION
		SE_ParseDefinition(Block)
		If SE_ERROR Then Return
		Block=After Block
	Wend

	;Step 2 - Check name conflicts
	Local G.SE_GlobalDef
	Local P.SE_PublicDef
	Local F.SE_FunctionDef
	Local L.SE_LocalDef
	Local S.SE_StaticDef

	For G=Each SE_GlobalDef
		If SE_CheckConstant(G\Name) Or SE_CheckFunction(G\Name)
			SE_Error(G\Token, "duplicate identifier '"+G\Name+"'")
			Return
		EndIf
	Next

	For P=Each SE_PublicDef
		If SE_CheckConstant(P\Name) Or SE_CheckFunction(P\Name)
			SE_Error(P\Token, "duplicate identifier '"+P\Name+"'")
			Return
		EndIf
	Next

	For F=Each SE_FunctionDef
		SE_CheckConstant(F\Func\Name)

		L=F\FirstLocal

		While L<>Null
			If SE_CheckConstant(L\Name) Or SE_CheckFunction(L\Name)
				SE_Error(L\Token, "duplicate identifier '"+L\Name+"'")
				Return
			EndIf

			If SE_CheckGlobalScope(L\Name) Then SE_Warn(L\Token, "name conflict with global/public variable '"+L\Name+"'")
			L=L\NextVar
		Wend

		S=F\FirstStatic

		While S<>Null
			If SE_CheckConstant(S\Name) Or SE_CheckFunction(S\Name)
				SE_Error(S\Token, "duplicate identifier '"+S\Name+"'")
				Return
			EndIf
			
			If SE_CheckGlobalScope(S\Name) Then SE_Warn(S\Token, "name conflict with global/public variable '"+S\Name+"'")
			S=S\NextVar
		Wend
	Next
End Function

;===========================================
;			Block space stack
;===========================================

Global SE_TOP_SPACE.SE_BlockSpace

Type SE_BlockSpace
	Field Block.SE_Block
	Field StartLabel.SE_VF_Label
	Field EndLabel.SE_VF_Label
End Type

Function SE_PushBlock.SE_BlockSpace(Block.SE_Block)
	Local BlockSpace.SE_BlockSpace=New SE_BlockSpace
	BlockSpace\Block=Block
	SE_TOP_SPACE=BlockSpace
	Return BlockSpace
End Function

Function SE_PopBlock()
	Delete Last SE_BlockSpace
	SE_TOP_SPACE=Last SE_BlockSpace
End Function

;===========================================
;			Syntax check
;===========================================

Function SE_NextNode.SE_Node(Node.SE_Node)
	If Node\NodeType=SE_NODE_SINGLE Or Node\NodeType=SE_NODE_ROOT
		Return Node\NextNode
		
	Else If Node\NodeType=SE_NODE_CHAIN
		Node=Node\NextNode
	
		Repeat
			If Node=Null Then Return
			If Node\NodeType<>SE_NODE_CHAIN Then Return Node
			Node=Node\NextNode
		Forever
	EndIf
End Function

Function SE_NodeSyntax(Node.SE_Node)
	Local Token.SE_Token=Node\Token

	If Token\TokenType<=SE_TOKEN_STRING
		If Node\FirstNode[1]<>Null
			SE_Error_Unexpected(Node\FirstNode[1]\Token)
			Return
		EndIf
		
	Else If Token\TokenType=SE_TOKEN_IDENTIFIER
		If Node\FirstNode[1]<>Null
			SE_NodeSyntax(Node\FirstNode[1])
			If SE_ERROR Then Return
		EndIf

	Else If Token\TokenType=SE_TOKEN_OPERATOR
		SE_OperatorSyntax(Node)
		If SE_ERROR Then Return

	Else If Token\TokenType=SE_TOKEN_OPENPAR
		SE_ParenthesisSyntax(Node)
		If SE_ERROR Then Return

	Else If Token\TokenType=SE_TOKEN_ARRAY_START
		SE_ArraySyntax(Node)
		If SE_ERROR Then Return

	Else
		SE_Error_Unexpected(Token)
		Return
	EndIf
End Function

Function SE_OperatorSyntax(Node.SE_Node)
	Local FirstTime=True

	Repeat
		Local Token.SE_Token=Node\Token
		Local LeftNode.SE_Node
		Local RightNode.SE_Node
	
		Select Token\TokenText
			Case "="
				LeftNode=Node\FirstNode[0]
				RightNode=Node\FirstNode[1]
			
				If LeftNode<>Null
					If LeftNode\Token\TokenType=SE_TOKEN_IDENTIFIER
;						If LeftNode\FirstNode[1]<>Null
;							SE_Error_Unexpected(LeftNode\FirstNode[1]\Token)
;							Return
;						EndIf
					
						If SE_CheckConstant(LeftNode\Token\TokenText) 
							SE_Error(Token, "cannot assign value to constant")
							Return
							
						Else If SE_CheckFunction(LeftNode\Token\TokenText)
							SE_Error(Token, "cannot assign value to function")
							Return
						EndIf
					Else
						SE_Error_Unexpected(Token)
						Return
					EndIf
				Else
					SE_Error_Unexpected(Token)
					Return
				EndIf

				If RightNode<>Null
					SE_NodeSyntax(RightNode)
					If SE_ERROR Then Return
				Else
					SE_Error(Token, "expecting expression")
					Return
				EndIf

			Case "**", "/", "%", "+", "<<", ">>", "&", "^", "|", "<", "<=", ">", ">=", "==", "!=", "and", "or"
				LeftNode=Node\FirstNode[0]
				RightNode=Node\FirstNode[1]
				
				If FirstTime
					If LeftNode=Null
						SE_Error_Unexpected(Token)
						Return
					EndIf
					
					FirstTime=False
				EndIf

				If LeftNode<>Null Then SE_NodeSyntax(LeftNode)
				If SE_ERROR Then Return

				If RightNode=Null
					SE_Error(Token, "expecting expression")
					Return
				EndIf

				SE_NodeSyntax(RightNode)
				If SE_ERROR Then Return

			Case "-"
				If Node\NodeType=SE_NODE_CHAIN
					LeftNode=Node\FirstNode[0]
					RightNode=Node\FirstNode[1]
					
					If FirstTime
						If LeftNode=Null
							SE_Error_Unexpected(Token)
							Return
						EndIf
						
						FirstTime=False
					EndIf
	
					If LeftNode<>Null Then SE_NodeSyntax(LeftNode)
					If SE_ERROR Then Return
	
					If RightNode=Null
						SE_Error(Token, "expecting expression")
						Return
					EndIf
	
					SE_NodeSyntax(RightNode)
					If SE_ERROR Then Return
				Else
					RightNode=Node\FirstNode[1]
					
					If RightNode=Null
						SE_Error(Token, "expecting expression")
						Return
					EndIf
	
					SE_NodeSyntax(RightNode)
					If SE_ERROR Then Return
				EndIf
				
			Case "*"
				LeftNode=Node\FirstNode[0]
				RightNode=Node\FirstNode[1]

				If Node\Level=7
					If FirstTime
						If LeftNode=Null
							SE_Error_Unexpected(Token)
							Return
						EndIf
						
						FirstTime=False
					EndIf
	
					If LeftNode<>Null Then SE_NodeSyntax(LeftNode)
					If SE_ERROR Then Return
	
					If RightNode=Null
						SE_Error(Token, "expecting expression")
						Return
					EndIf
	
					SE_NodeSyntax(RightNode)
					If SE_ERROR Then Return
				Else
					If RightNode=Null
						SE_Error(Token, "expecting identifier")
						Return
					EndIf
						
					If RightNode\Token\TokenType<>SE_TOKEN_IDENTIFIER
						SE_Error_Expecting(RightNode\Token, "variable identifier")
						Return
					EndIf
	
					If RightNode\FirstNode[1]<>Null
						SE_Error_Unexpected(RightNode\FirstNode[1]\Token)
						Return
					EndIf
				EndIf
				
			Case "++", "--"
				LeftNode=Node\FirstNode[0]
				RightNode=Node\FirstNode[1]
				
				If LeftNode=Null And RightNode=Null
					SE_Error(Token, "expecting variable identifier")
					Return
				EndIf

				If LeftNode<>Null And RightNode<>Null
					SE_Error(Token, "undefined argument for "+Token\Error)
					Return
				EndIf

				If LeftNode<>Null
					If LeftNode\Token\TokenType=SE_TOKEN_IDENTIFIER
						If LeftNode\FirstNode[1]<>Null
							If LeftNode\FirstNode[1]\Token\TokenType<>SE_TOKEN_ARRAY_START
								SE_Error_Unexpected(LeftNode\FirstNode[1]\Token)
								Return
							EndIf
						EndIf

						If SE_CheckConstant(LeftNode\Token\TokenText)
							SE_Error(LeftNode\Token, "operator "+Token\Error+" cannot be applied to constants")
							Return
							
						Else If SE_CheckFunction(LeftNode\Token\TokenText)
							SE_Error(LeftNode\Token, "operator "+Token\Error+" cannot be applied to functions")
							Return
						EndIf
					Else
						SE_Error_Expecting(LeftNode\Token, "variable identifier")
						Return
					EndIf
				Else
					If RightNode\Token\TokenType=SE_TOKEN_IDENTIFIER
						If RightNode\FirstNode[1]<>Null
							If RightNode\FirstNode[1]\Token\TokenType<>SE_TOKEN_ARRAY_START
								SE_Error_Unexpected(RightNode\FirstNode[1]\Token)
								Return
							EndIf
						EndIf

						If SE_CheckConstant(RightNode\Token\TokenText)
							SE_Error(RightNode\Token, "operator "+Token\Error+" cannot be applied to constants")
							Return
							
						Else If SE_CheckFunction(RightNode\Token\TokenText)
							SE_Error(RightNode\Token, "operator "+Token\Error+" cannot be applied to functions")
							Return
						EndIf
					Else
						SE_Error_Expecting(RightNode\Token, "variable identifier")
						Return
					EndIf
				EndIf

			Case "typeof", "~", "not", "len"
				LeftNode=Node\FirstNode[0]
				RightNode=Node\FirstNode[1]

				If LeftNode<>Null
					SE_Error_Unexpected(LeftNode\Token)
					Return
				EndIf

				If RightNode=Null
					SE_Error(Token, "expecting expression")
					Return
				EndIf

				SE_NodeSyntax(RightNode)
				If SE_ERROR Then Return
		End Select

		
		If Node\NextNode=Null
			Exit
		Else
			If Node\NodeType=SE_NODE_SINGLE
				If Node\NextNode\NodeType=SE_NODE_END Or Node\NextNode\NodeType=SE_NODE_SEPARATOR
					Exit
				Else
					SE_Error_Unexpected(Node\Token)
					Return
				EndIf
			Else
				If Node\NextNode\NodeType=SE_NODE_END Or Node\NextNode\NodeType=SE_NODE_SEPARATOR
					Exit
				EndIf
			EndIf
		EndIf

		If Node\NodeType=SE_NODE_SINGLE
			Exit
			
		Else If Node\NodeType=SE_NODE_CHAIN
			Node=Node\NextNode
		EndIf

		If Node=Null Then Exit
	Forever
End Function

Function SE_ParenthesisSyntax(Node.SE_Node)
	Local Parent.SE_Node=Node\Parent
	Local Invoke

	If Parent\Token<>Null
		If Parent\Token\TokenType=SE_TOKEN_IDENTIFIER
			If SE_CheckConstant(Parent\Token\TokenText)
				SE_Error(Parent\Token, "constant '"+Parent\Token\TokenText+"' cannot be invoked ")
				Return
			EndIf
			
			Invoke=True
			
		Else If Node\PrevNode<>Null
			SE_Error_Unexpected(Node\Token)
			Return
		EndIf
	EndIf

	If Node\FirstNode[1]=Null
		SE_Error_EOL(Node\Token)
		Return
	Else
		Node=Node\FirstNode[1]
	EndIf

	If Invoke=False
		SE_NodeSyntax(Node)

		Node=SE_NextNode(Node)

		If Node\Token\TokenType<>SE_TOKEN_CLOSEPAR
			SE_Error_Unexpected(Node\Token)
			Return
		EndIf
	Else
		If Node\Token\TokenType<>SE_TOKEN_CLOSEPAR
			Repeat
				SE_NodeSyntax(Node)
				If SE_ERROR Then Return
				
				Node=SE_NextNode(Node)
				
				If Node\Token\TokenType=SE_TOKEN_CLOSEPAR
					Exit
					
				Else If Node\Token\TokenType=SE_TOKEN_COMMA
					Node=Node\NextNode
					
				Else
					SE_Error_Unexpected(Node\Token)
					Return
				EndIf
			Forever
		EndIf
	EndIf

	If Node\NextNode<>Null
		SE_NodeSyntax(Node\NextNode)
		If SE_ERROR Then Return
	EndIf
End Function

Function SE_ArraySyntax(Node.SE_Node)
	Local Parent.SE_Node=Node\Parent
	Local Access
	Local Operator.SE_Node

	If Parent\Token<>Null
		If Parent\Token\TokenType=SE_TOKEN_IDENTIFIER
			Access=True
	
		Else If Parent\NodeType=SE_NODE_ROOT
			;If Node\PrevNode\NodeType=SE_NODE_END Then Access=True
			If Node\PrevNode<>Null
				If Node\PrevNode\NodeType=SE_NODE_END Then Access=True
			EndIf
		EndIf
	EndIf

	If Node\FirstNode[1]=Null
		SE_Error_EOL(Node\Token)
		Return
	Else
		Node=Node\FirstNode[1]
	EndIf

	If Access
		If Node\Token\TokenType=SE_TOKEN_ARRAY_END
			SE_Error_Unexpected(Node\Token)
			Return
		Else
			SE_NodeSyntax(Node)
			If SE_ERROR Then Return
	
			Node=SE_NextNode(Node)
			
			If Node\Token\TokenType<>SE_TOKEN_ARRAY_END
				SE_Error_Expecting(Node\Token, "']'")
				Return
			EndIf
		EndIf
	Else
		While Node\Token\TokenType<>SE_TOKEN_ARRAY_END
			SE_NodeSyntax(Node)
			If SE_ERROR Then Return

			Node=SE_NextNode(Node)
			
			If Node\Token\TokenType=SE_TOKEN_COMMA
				Node=Node\NextNode
				
			Else If Node\Token\TokenType<>SE_TOKEN_ARRAY_END
				SE_Error_Unexpected(Node\Token)
				Return
			EndIf
		Wend
	EndIf

	If Node\NextNode<>Null
		If Access
			SE_NodeSyntax(Node\NextNode)
			If SE_ERROR Then Return
		Else
			SE_Error_Unexpected(Node\NextNode\Token)
			Return
		EndIf
	EndIf
End Function

Function SE_Var_Syntax(Block.SE_Block)
	Local Node.SE_Node=Block\Node\FirstNode[1]

	Local LastToken.SE_Token=Block\Token

	Repeat
		If Node=Null
			SE_Error_EOL(LastToken)
			Return
		EndIf
	
		SE_NodeSyntax(Node)
		If SE_ERROR Then Return

		LastToken.SE_Token=Node\Token
		
		Node=SE_NextNode(Node)

		If Node=Null
			Exit
			
		Else If Node\Token\TokenType=SE_TOKEN_COMMA
			Node=Node\NextNode

		Else
			SE_Error_Unexpected(Node\Token)
			Return
		EndIf
	Forever
End Function

Function SE_Def_Syntax(Block.SE_Block)
	Local Node.SE_Node=Block\Node\FirstNode[1]
	SE_FUNCTION_DEF=SE_GetFunctionDef(Node\Token\TokenText)

	Node=Node\FirstNode[1]\FirstNode[1]

	Local LastToken.SE_Token=Block\Token

	Repeat
		If Node\Token\TokenType=SE_TOKEN_CLOSEPAR
			Exit
			
		Else If Node\Token\TokenType=SE_TOKEN_COMMA
			Node=Node\NextNode
		EndIf

		SE_NodeSyntax(Node)
		If SE_ERROR Then Return
		Node=SE_NextNode(Node)
	Forever

	SE_PushBlock(Block)
	SE_BlockBodySyntax(Block\FirstBlock)
	If SE_ERROR Then Return
	SE_PopBlock()
End Function

Function SE_If_Syntax(Block.SE_Block)
	SE_NodeSyntax(Block\Node\FirstNode[1])
	If SE_ERROR Then Return

	SE_PushBlock(Block)
	SE_BlockBodySyntax(Block\FirstBlock)
	If SE_ERROR Then Return
	SE_PopBlock()
End Function

Function SE_Else_Syntax(Block.SE_Block)
	If Block\Node<>Null
		SE_NodeSyntax(Block\Node\FirstNode[1])
		If SE_ERROR Then Return
	EndIf
End Function

Function SE_Select_Syntax(Block.SE_Block)
	SE_NodeSyntax(Block\Node\FirstNode[1])
	If SE_ERROR Then Return

	Local Child.SE_Block=Block\FirstBlock

	If Child<>Null
		If Child\BlockType<>SE_BLOCK_CASE
			SE_Error_Expecting(Child\Token, "'case'")
			Return
		EndIf
	Else
		SE_Error(Block\Token, "expecting at least one 'case'")
		Return
	EndIf

	Local StrictCase

	SE_PushBlock(Child)
	While Child<>Null
		If Child\BlockType=SE_BLOCK_CASE And StrictCase
			SE_Error_Unexpected(Child\Token)
			Return
		EndIf
	
		If Child\BlockType=SE_BLOCK_ELSE
			StrictCase=True
		Else
			SE_BlockSyntax(Child)
			If SE_ERROR Then Return
		EndIf
		
		Child=Child\NextBlock
	Wend
	SE_PopBlock()
End Function

Function SE_Case_Syntax(Block.SE_Block)
	Local Node.SE_Node=Block\Node\FirstNode[1]

	Local LastToken.SE_Token=Block\Token

	Repeat
		If Node=Null
			SE_Error_EOL(LastToken)
			Return
		EndIf
	
		SE_NodeSyntax(Node)
		If SE_ERROR Then Return

		LastToken.SE_Token=Node\Token
		
		Node=SE_NextNode(Node)

		If Node=Null
			Exit
			
		Else If Node\Token\TokenType=SE_TOKEN_COMMA
			Node=Node\NextNode

		Else
			SE_Error_Unexpected(Node\Token)
			Return
		EndIf
	Forever
End Function

Function SE_For_Syntax(Block.SE_Block)
	Local Child.SE_Block=Block\FirstBlock
	Local Node.SE_Node

	;	Initial expression
	If Child\Node<>Null
		SE_NodeSyntax(Child\Node\FirstNode[1])
		If SE_ERROR Then Return
	EndIf

	;	Condition expression
	Child=Child\NextBlock

	If Child\Node<>Null
		SE_NodeSyntax(Child\Node\FirstNode[1])
		If SE_ERROR Then Return
	EndIf

	;	Increment expression
	Child=Child\NextBlock

	If Child\Node<>Null
		SE_NodeSyntax(Child\Node\FirstNode[1])
		If SE_ERROR Then Return
	EndIf

	SE_PushBlock(Block)
	SE_BlockBodySyntax(Child\NextBlock)
	If SE_ERROR Then Return
	SE_PopBlock()
End Function

Function SE_While_Syntax(Block.SE_Block)
	Local Node.SE_Node=Block\Node\FirstNode[1]

	SE_NodeSyntax(Node)
	If SE_ERROR Then Return

	SE_PushBlock(Block)
	SE_BlockBodySyntax(Block\FirstBlock)
	If SE_ERROR Then Return
	SE_PopBlock()
End Function

Function SE_Do_Syntax(Block.SE_Block)
	SE_PushBlock(Block)
	SE_BlockBodySyntax(Block\FirstBlock)
	If SE_ERROR Then Return
	SE_PopBlock()
End Function

Function SE_Repeat_Syntax(Block.SE_Block)
	If Block\Node<>Null
		SE_NodeSyntax(Block\Node\FirstNode[1])
		If SE_ERROR Then Return
	EndIf
End Function

Function SE_Break_Syntax(Block.SE_Block)
	Local BlockSpace.SE_BlockSpace=Last SE_BlockSpace

	While BlockSpace<>Null
		Local BlockType=BlockSpace\Block\BlockType
		If BlockType=SE_BLOCK_FOR Or BlockType=SE_BLOCK_WHILE Or BlockType=SE_BLOCK_DO Then Return
		BlockSpace=Before BlockSpace
	Wend

	SE_Error(Block\Token, "'break' without any loop")
End Function

Function SE_Continue_Syntax(Block.SE_Block)
	Local BlockSpace.SE_BlockSpace=Last SE_BlockSpace

	While BlockSpace<>Null
		Local BlockType=BlockSpace\Block\BlockType
		If BlockType=SE_BLOCK_FOR Or BlockType=SE_BLOCK_WHILE Or BlockType=SE_BLOCK_DO Then Return
		BlockSpace=Before BlockSpace
	Wend

	SE_Error(Block\Token, "'continue' without any loop")
End Function

Function SE_Return_Syntax(Block.SE_Block)
	If Block\Node<>Null
		SE_NodeSyntax(Block\Node\FirstNode[1])
		If SE_ERROR Then Return
	EndIf
End Function

Function SE_BlockSyntax(Block.SE_Block)
	Select Block\BlockType
		Case SE_BLOCK_EXPRESSION
			SE_NodeSyntax(Block\Node\FirstNode[1])
			If SE_ERROR Then Return

		Case SE_BLOCK_VAR
			SE_Var_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_DEF
			SE_Def_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_IF
			SE_If_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_ELSE
			SE_Else_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_SELECT
			SE_Select_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_CASE
			SE_Case_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_FOR
			SE_For_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_WHILE
			SE_While_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_DO
			SE_Do_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_REPEAT
			SE_Repeat_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_BREAK
			SE_Break_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_CONTINUE
			SE_Continue_Syntax(Block)
			If SE_ERROR Then Return

		Case SE_BLOCK_RETURN
			SE_Return_Syntax(Block)
			If SE_ERROR Then Return
	End Select
End Function

Function SE_BlockBodySyntax(Block.SE_Block)
	While Block<>Null
		SE_BlockSyntax(Block)
		If SE_ERROR Then Return
		Block=Block\NextBlock
	Wend
End Function

Function SE_CheckSyntax()
	Local Block.SE_Block=SE_ROOT_BLOCK\FirstBlock

	While Block<>Null
		SE_BlockSyntax(Block)
		If SE_ERROR Then Return
		Block=Block\NextBlock
	Wend
End Function

;===========================================
;			Temp stack
;===========================================

Type SE_TempValue
	Field Value.SE_VF_Value

	Field NextTemp.SE_TempValue, PrevTemp.SE_TempValue
End Type

Function SE_PushTemp.SE_VF_Value()
	Local TopTemp.SE_TempValue=SE_FUNCTION_DEF\TopTemp

	If TopTemp<>Null
		TopTemp=TopTemp\NextTemp
	Else
		TopTemp=SE_FUNCTION_DEF\FirstTemp
	EndIf

	If TopTemp=Null
		TopTemp=New SE_TempValue

		If SE_FUNCTION_DEF\FirstTemp=Null
			SE_FUNCTION_DEF\FirstTemp=TopTemp
			SE_FUNCTION_DEF\LastTemp=TopTemp
		Else
			TopTemp\PrevTemp=SE_FUNCTION_DEF\LastTemp
			SE_FUNCTION_DEF\LastTemp\NextTemp=TopTemp
			SE_FUNCTION_DEF\LastTemp=TopTemp
		EndIf

		TopTemp\Value=New SE_VF_Value
	EndIf

	SE_FUNCTION_DEF\TopTemp=TopTemp

	Return TopTemp\Value
End Function

Function SE_PopTemp.SE_VF_Value()
	If SE_FUNCTION_DEF\TopTemp=Null Then SE_ERROR=True
	Local Value.SE_VF_Value=SE_FUNCTION_DEF\TopTemp\Value
	SE_FUNCTION_DEF\TopTemp=SE_FUNCTION_DEF\TopTemp\PrevTemp
	Return Value
End Function

Function SE_IndexTemps()
	Local FuncDef.SE_FunctionDef

	For FuncDef=Each SE_FunctionDef
		Local Temp.SE_TempValue=FuncDef\FirstTemp
		Local Index=FuncDef\Func\TransientValues

		While Temp<>Null
			Temp\Value\ValueType=SE_TRANSIENT
			Temp\Value\Index=Index
			Index=Index+1
			Temp=Temp\NextTemp
		Wend

		FuncDef\Func\TransientValues=Index
	Next
End Function

;===========================================
;			Compilation
;===========================================

;	Magic
Function SE_CompileIdentifier.SE_VF_Value(Node.SE_Node)
	Local Value.SE_VF_Value

	If Node\FirstNode[1]=Null
		Value=SE_GetConstant(Node\Token\TokenText)
		If Value<>Null Then Return Value

		Return SE_GetVariable(Node\Token\TokenText)
		
	Else
		SE_CompileNode(Node\FirstNode[1])
	EndIf
End Function

Function SE_CompileOperator.SE_VF_Value(Node.SE_Node)
	Local Token.SE_Token=Node\Token

	Local FirstTime=True
	Local ChainEnd

	Local Label.SE_VF_Label
	Local Temp1.SE_VF_Value, Temp2.SE_VF_Value

	Local Inst.SE_VF_Inst

	Repeat
		Local ValueA.SE_VF_Value=Null
		Local ValueB.SE_VF_Value=Null
		Local ValueC.SE_VF_Value=Null

		If Node\NextNode<>Null
			If Node\NextNode\NodeType=SE_NODE_SEPARATOR Or Node\NextNode\NodeType=SE_NODE_END
				ChainEnd=True
			EndIf
		Else
			ChainEnd=True
		EndIf
	
		Select Token\TokenText
			Case "="
				ValueA=SE_CompileNode(Node\FirstNode[0])
				ValueB=SE_CompileNode(Node\FirstNode[1])

				If ValueB=Null Then ValueB=SE_PopTemp()
				If ValueA=Null Then ValueA=SE_PopTemp()

				SE_VF_CreateInst(SE_MOV, ValueA, ValueB, Null)

				Return ValueA

			Case "+"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_ADD, ValueA, ValueB, ValueC)

			Case "-"
				If Node\Level=6
					ValueA=SE_CompileNode(Node\FirstNode[1])
					If ValueA=Null Then ValueA=SE_PopTemp()

					ValueB=SE_PushTemp()

					SE_VF_CreateInst(SE_NEG, ValueA, ValueB, Null)

					Return ValueB
				Else
					If FirstTime
						FirstTime=False
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()
					Else
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						ValueA=SE_PopTemp()
					EndIf
	
					ValueC=SE_PushTemp()
	
					SE_VF_CreateInst(SE_SUB, ValueA, ValueB, ValueC)
				EndIf

			Case "*"
				If Node\Level=3
					ValueA=SE_GetVariable(Node\FirstNode[1]\Token\TokenText)
					ValueB=SE_PushTemp()

					SE_VF_CreateInst(SE_GP, ValueA, ValueB, Null)

					Return ValueB
				Else
					If FirstTime
						FirstTime=False
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()
					Else
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						ValueA=SE_PopTemp()
					EndIf
	
					ValueC=SE_PushTemp()
	
					SE_VF_CreateInst(SE_MUL, ValueA, ValueB, ValueC)
				EndIf

			Case "/"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_DIV, ValueA, ValueB, ValueC)

			Case "**"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_POW, ValueA, ValueB, ValueC)

			Case "%"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_MOD, ValueA, ValueB, ValueC)

			Case "++"
				;identifier++
				If Node\FirstNode[0]<>Null
					ValueA=SE_PushTemp()
					ValueB=SE_CompileNode(Node\FirstNode[0])
					If ValueB=Null Then ValueB=SE_PopTemp()

					SE_VF_CreateInst(SE_MOV, ValueA, ValueB, Null)
					SE_VF_CreateInst(SE_INC, ValueB, Null, Null)
					SE_PopTemp()
					
					Return ValueA
					
				;++identifier
				Else
					ValueA=SE_CompileNode(Node\FirstNode[1])
					If ValueA=Null Then ValueA=SE_PopTemp()

					SE_VF_CreateInst(SE_INC, ValueA, Null, Null)
					
					Return ValueA
				EndIf

			Case "--"
				;identifier--
				If Node\FirstNode[0]<>Null
					ValueA=SE_PushTemp()
					ValueB=SE_CompileNode(Node\FirstNode[0])
					If ValueB=Null Then ValueB=SE_PopTemp()

					SE_VF_CreateInst(SE_MOV, ValueA, ValueB, Null)
					SE_VF_CreateInst(SE_DEC, ValueB, Null, Null)
					Return ValueA
					
				;--identifier
				Else
					ValueA=SE_CompileNode(Node\FirstNode[1])
					If ValueA=Null Then ValueA=SE_PopTemp()
					
					SE_VF_CreateInst(SE_DEC, ValueA, Null, Null)
					
					Return ValueA
				EndIf

			Case "=="
				If FirstTime
					FirstTime=False
				
					If ChainEnd=False
						Label=SE_VF_CreateLabel()
						Temp1=SE_PushTemp()
						Temp2=SE_PushTemp()

						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						SE_VF_CreateInst(SE_CE, ValueA, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
						
					Else
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
						
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						ValueC=SE_PushTemp()

						SE_VF_CreateInst(SE_CE, ValueA, ValueB, ValueC)
					EndIf
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])
					
					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_CE, Temp2, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_CE, Temp2, ValueB, Temp1)
						Label\Shift=True
						SE_PopTemp()
					EndIf
				EndIf

			Case "!="
				If FirstTime
					FirstTime=False
				
					If ChainEnd=False
						Label=SE_VF_CreateLabel()
						Temp1=SE_PushTemp()
						Temp2=SE_PushTemp()

						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						SE_VF_CreateInst(SE_CNE, ValueA, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
						
					Else
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
						
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						ValueC=SE_PushTemp()

						SE_VF_CreateInst(SE_CNE, ValueA, ValueB, ValueC)
					EndIf
				Else

					ValueB=SE_CompileNode(Node\FirstNode[1])
					
					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_CNE, Temp2, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_CNE, Temp2, ValueB, Temp1)
						Label\Shift=True
						SE_PopTemp()
					EndIf
				EndIf

			Case ">"
				If FirstTime
					FirstTime=False
				
					If ChainEnd=False
						Label=SE_VF_CreateLabel()
						Temp1=SE_PushTemp()
						Temp2=SE_PushTemp()

						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						SE_VF_CreateInst(SE_CG, ValueA, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
						
					Else
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
						
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						ValueC=SE_PushTemp()

						SE_VF_CreateInst(SE_CG, ValueA, ValueB, ValueC)
					EndIf
				Else

					ValueB=SE_CompileNode(Node\FirstNode[1])
					
					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_CG, Temp2, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_CG, Temp2, ValueB, Temp1)
						Label\Shift=True
						SE_PopTemp()
					EndIf
				EndIf

			Case "<"
				If FirstTime
					FirstTime=False
				
					If ChainEnd=False
						Label=SE_VF_CreateLabel()
						Temp1=SE_PushTemp()
						Temp2=SE_PushTemp()

						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						SE_VF_CreateInst(SE_CL, ValueA, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
						
					Else
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
						
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						ValueC=SE_PushTemp()

						SE_VF_CreateInst(SE_CL, ValueA, ValueB, ValueC)
					EndIf
				Else

					ValueB=SE_CompileNode(Node\FirstNode[1])
					
					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_CL, Temp2, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_CL, Temp2, ValueB, Temp1)
						Label\Shift=True
						SE_PopTemp()
					EndIf
				EndIf

			Case ">="
				If FirstTime
					FirstTime=False
				
					If ChainEnd=False
						Label=SE_VF_CreateLabel()
						Temp1=SE_PushTemp()
						Temp2=SE_PushTemp()

						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						SE_VF_CreateInst(SE_CGE, ValueA, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
						
					Else
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
						
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						ValueC=SE_PushTemp()

						SE_VF_CreateInst(SE_CGE, ValueA, ValueB, ValueC)
					EndIf
				Else
				
					ValueB=SE_CompileNode(Node\FirstNode[1])
					
					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_CGE, Temp2, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_CGE, Temp2, ValueB, Temp1)
						Label\Shift=True
						SE_PopTemp()
					EndIf
				EndIf

			Case "<="
				If FirstTime
					FirstTime=False
				
					If ChainEnd=False
						Label=SE_VF_CreateLabel()
						Temp1=SE_PushTemp()
						Temp2=SE_PushTemp()

						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
	
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						SE_VF_CreateInst(SE_CLE, ValueA, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
						
					Else
						ValueA=SE_CompileNode(Node\FirstNode[0])
						ValueB=SE_CompileNode(Node\FirstNode[1])
						
						If ValueB=Null Then ValueB=SE_PopTemp()
						If ValueA=Null Then ValueA=SE_PopTemp()

						ValueC=SE_PushTemp()

						SE_VF_CreateInst(SE_CLE, ValueA, ValueB, ValueC)
					EndIf
				Else
				
					ValueB=SE_CompileNode(Node\FirstNode[1])
					
					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_CLE, Temp2, ValueB, Temp1)
						SE_VF_CreateInst(SE_JIF, Temp1, SE_VF_GetLabel(Label), Null)
						SE_VF_CreateInst(SE_MOV, Temp2, ValueB, Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_CLE, Temp2, ValueB, Temp1)
						Label\Shift=True
						SE_PopTemp()
					EndIf
				EndIf

			Case "&"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_AND, ValueA, ValueB, ValueC)

			Case "&"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_AND, ValueA, ValueB, ValueC)

			Case "^"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_XOR, ValueA, ValueB, ValueC)

			Case "|"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_OR, ValueA, ValueB, ValueC)

			Case "~"
				ValueA=SE_CompileNode(Node\FirstNode[1])
				If ValueA=Null Then ValueA=SE_PopTemp()

				ValueB=SE_PushTemp()

				SE_VF_CreateInst(SE_BN, ValueA, ValueB, Null)

				Return ValueB

			Case "<<"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_SHL, ValueA, ValueB, ValueC)

			Case ">>"
				If FirstTime
					FirstTime=False
					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					ValueA=SE_PopTemp()
				EndIf

				ValueC=SE_PushTemp()

				SE_VF_CreateInst(SE_SHR, ValueA, ValueB, ValueC)

			Case "not"
				ValueA=SE_CompileNode(Node\FirstNode[1])
				If ValueA=Null Then ValueA=SE_PopTemp()

				ValueB=SE_PushTemp()

				SE_VF_CreateInst(SE_NOT, ValueA, ValueB, Null)

				Return ValueB

			Case "and"
				If FirstTime
					FirstTime=False

					Label=SE_VF_CreateLabel()
					Temp1=SE_PushTemp()

					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()

					SE_VF_CreateInst(SE_MOV, Temp1, ValueA, Null)
					SE_VF_CreateInst(SE_JIF, ValueA, SE_VF_GetLabel(Label), Null)

					If ChainEnd=False
						SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						SE_VF_CreateInst(SE_JIF, ValueB, SE_VF_GetLabel(Label), Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						Label\Shift=True
					EndIf
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						SE_VF_CreateInst(SE_JIF, ValueB, SE_VF_GetLabel(Label), Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						Label\Shift=True
					EndIf
				EndIf

			Case "or"
				If FirstTime
					FirstTime=False

					Label=SE_VF_CreateLabel()
					Temp1=SE_PushTemp()

					ValueA=SE_CompileNode(Node\FirstNode[0])
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()
					If ValueA=Null Then ValueA=SE_PopTemp()

					SE_VF_CreateInst(SE_MOV, Temp1, ValueA, Null)
					SE_VF_CreateInst(SE_JIT, ValueA, SE_VF_GetLabel(Label), Null)

					If ChainEnd=False
						SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						SE_VF_CreateInst(SE_JIT, ValueB, SE_VF_GetLabel(Label), Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						Label\Shift=True
					EndIf
				Else
					ValueB=SE_CompileNode(Node\FirstNode[1])

					If ValueB=Null Then ValueB=SE_PopTemp()

					If ChainEnd=False
						SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						SE_VF_CreateInst(SE_JIT, ValueB, SE_VF_GetLabel(Label), Null)
					Else
						Label\Inst=SE_VF_CreateInst(SE_MOV, Temp1, ValueB, Null)
						Label\Shift=True
					EndIf
				EndIf

			Case "len"
				ValueA=SE_CompileNode(Node\FirstNode[1])
				If ValueA=Null Then ValueA=SE_PopTemp()

				ValueB=SE_PushTemp()

				SE_VF_CreateInst(SE_LEN, ValueA, ValueB, Null)

				Return ValueB

			Case "typeof"
				ValueA=SE_CompileNode(Node\FirstNode[1])
				If ValueA=Null Then ValueA=SE_PopTemp()

				ValueB=SE_PushTemp()

				SE_VF_CreateInst(SE_VT, ValueA, ValueB, Null)

				Return ValueB

			Case "args"
				ValueA=SE_PushTemp()

				SE_VF_CreateInst(SE_ARGS, ValueA, Null, Null)

				Return ValueB
		End Select

		If Node\NodeType=SE_NODE_SINGLE
			Exit
			
		Else If Node\NodeType=SE_NODE_CHAIN
			Node=Node\NextNode
		EndIf
		
		If ChainEnd Then Exit

		Token=Node\Token
	Forever
End Function

Function SE_CompileParenthesis.SE_VF_Value(Node.SE_Node)
	Local Parent.SE_Node=Node\Parent
	Local Arg.SE_VF_Value, ArgsNum

	Local ValueA.SE_VF_Value
	Local ValueB.SE_VF_Value
	Local ValueC.SE_VF_Value

	Local FuncPtr.SE_VF_FuncPtr
	
	If Parent\Token<>Null
		If Parent\Token\TokenType=SE_TOKEN_IDENTIFIER
			Node=Node\FirstNode[1]
	
			If Node\Token\TokenType<>SE_TOKEN_CLOSEPAR
				Repeat
					Arg=SE_CompileNode(Node)
					If Arg=Null Then Arg=SE_PopTemp()
					ArgsNum=ArgsNum+1
	
					SE_VF_CreateInst(SE_MTA, Arg, Null, Null)
	
					Node=SE_NextNode(Node)
					
					If Node\Token\TokenType=SE_TOKEN_CLOSEPAR
						Exit
					Else
						Node=Node\NextNode
					EndIf
				Forever
			EndIf
	
			ValueA=New SE_VF_Value
			ValueB=SE_VF_CreateBasic(SE_INT, ArgsNum)
			ValueC=SE_PushTemp()
			
			FuncPtr=SE_GetFunction(Parent\Token\TokenText)
			
			If FuncPtr<>Null
				ValueA\ValueType=SE_FUNC_PTR
				ValueA\Index=FuncPtr\Index
				SE_VF_CreateInst(SE_INVU, ValueA, ValueB, ValueC)
			Else
				ValueA\ValueType=SE_STRING
				ValueA\Value=Parent\Token\TokenText
				SE_VF_CreateInst(SE_INVG, ValueA, ValueB, ValueC)
			EndIf
		Else
			SE_CompileNode(Node\FirstNode[1])
			Node=SE_NextNode(Node\FirstNode[1])
		EndIf
	Else
		SE_CompileNode(Node\FirstNode[1])
		Node=SE_NextNode(Node\FirstNode[1])
	EndIf

	Node=Node\NextNode

	If Node<>Null
		SE_CompileNode(Node)
	EndIf
End Function

Function SE_CompileArray.SE_VF_Value(Node.SE_Node)
	Local Parent.SE_Node=Node\Parent
	Local Access
	Local Indirect

	Local ValueA.SE_VF_Value
	Local ValueB.SE_VF_Value
	Local ValueC.SE_VF_Value

	If Parent\Token<>Null
		If Parent\Token\TokenType=SE_TOKEN_IDENTIFIER
			Access=True

		Else If Parent\NodeType=SE_NODE_ROOT
			If Node\PrevNode<>Null
				If Node\PrevNode\NodeType=SE_NODE_END
					Access=True
					Indirect=True
					RuntimeError ""
				EndIf
			EndIf
		EndIf
	EndIf

	Node=Node\FirstNode[1]

	If Access
		ValueB=SE_CompileNode(Node)
		If ValueB=Null Then ValueB=SE_PopTemp()

		If Indirect=False
			ValueA=SE_GetVariable(Parent\Token\TokenText)
		Else
			ValueA=SE_PopTemp()
		EndIf

		ValueC=SE_PushTemp()
		SE_VF_CreateInst(SE_ACC, ValueA, ValueB, ValueC)

		Node=SE_NextNode(Node)
		Node=Node\NextNode

		If Node<>Null Then SE_CompileNode(Node)
	Else
		Local Elements
	
		While Node\Token\TokenType<>SE_TOKEN_ARRAY_END
			ValueA=SE_CompileNode(Node)
			If ValueA=Null Then ValueA=SE_PopTemp()

			SE_VF_CreateInst(SE_MTA, ValueA, Null, Null)
			Elements=Elements+1

			Node=SE_NextNode(Node)
			
			If Node\Token\TokenType=SE_TOKEN_COMMA Then Node=Node\NextNode
		Wend

		ValueA=SE_VF_CreateBasic(SE_INT, Elements)
		ValueB=SE_PushTemp()
		SE_VF_CreateInst(SE_CA, ValueA, ValueB, Null)
	EndIf
End Function

Function SE_CompileNode.SE_VF_Value(Node.SE_Node)
	Local Token.SE_Token=Node\Token

	If Token\TokenType<=SE_TOKEN_STRING
		Return SE_VF_CreateBasic(Node\Token\TokenType, Node\Token\TokenText)
		
	Else If Token\TokenType=SE_TOKEN_IDENTIFIER
		Return SE_CompileIdentifier(Node)

	Else If Token\TokenType=SE_TOKEN_OPERATOR
		Return SE_CompileOperator(Node)

	Else If Token\TokenType=SE_TOKEN_OPENPAR
		SE_CompileParenthesis(Node)

	Else If Token\TokenType=SE_TOKEN_ARRAY_START
		SE_CompileArray(Node)
		
	EndIf
End Function

Function SE_Compile_Var(Block.SE_Block)
	Local Node.SE_Node=Block\Node\FirstNode[1]

	While Node<>Null
		If Node\Token\TokenType=SE_TOKEN_OPERATOR Then SE_CompileNode(Node)
		
		Node=SE_NextNode(Node)

		If Node=Null Then Return
		If Node\Token\TokenType=SE_TOKEN_COMMA Then Node=Node\NextNode
	Wend
End Function

Function SE_Compile_Def(Block.SE_Block)
	Local Node.SE_Node=Block\Node\FirstNode[1]
	
	SE_FUNCTION_DEF=SE_GetFunctionDef(Node\Token\TokenText)
	SE_FUNCTION_DEF\Func\FirstInst=Last SE_VF_Inst
	
	Node=Node\FirstNode[1]\FirstNode[1]

	While Node\Token\TokenType<>SE_TOKEN_CLOSEPAR
		Local Parameter.SE_VF_Value
		Local Label.SE_VF_Label

		If Node\Token\TokenType=SE_TOKEN_OPERATOR
			Label=SE_VF_CreateLabel()

			Parameter=SE_GetVariable(Node\FirstNode[0]\Token\TokenText)
			SE_VF_CreateInst(SE_JIF, Parameter, SE_VF_GetLabel(Label), Null)
			SE_CompileNode(Node)

			Label\Inst=Last SE_VF_Inst
			Label\Shift=True
		EndIf
		
		Node=SE_NextNode(Node)
		If Node\Token\TokenType=SE_TOKEN_COMMA Then Node=Node\NextNode
	Wend

	Block=Block\FirstBlock

	While Block<>Null
		SE_CompileBlock(Block)
		Block=Block\NextBlock
	Wend

	SE_VF_CreateInst(SE_END, Null, Null, Null)
	
	If SE_FUNCTION_DEF\Func\FirstInst=Null
		SE_FUNCTION_DEF\Func\FirstInst=First SE_VF_Inst
	Else
		SE_FUNCTION_DEF\Func\FirstInst=After SE_FUNCTION_DEF\Func\FirstInst
	EndIf
	
	SE_FUNCTION_DEF\Func\LastInst=Last SE_VF_Inst
End Function

Function SE_Compile_If(RootBlock.SE_Block)
	Local Block.SE_Block=RootBlock\FirstBlock
	Local ElseCount

	While Block<>Null
		If Block\BlockType=SE_BLOCK_ELSE Then ElseCount=ElseCount+1
		Block=Block\NextBlock
	Wend

	Local ConditionNode.SE_Node=RootBlock\Node\FirstNode[1]
	Local ConditionValue.SE_VF_Value

	Local EndLabel.SE_VF_Label=SE_VF_CreateLabel()
	Local ElseLabel.SE_VF_Label

	If ElseCount Then ElseLabel=SE_VF_CreateLabel()

	Block=RootBlock\FirstBlock
	
	Repeat
		If ConditionNode<>Null
			ConditionValue=SE_CompileNode(ConditionNode)
			If ConditionValue=Null Then ConditionValue=SE_PopTemp()

			If ElseCount
				SE_VF_CreateInst(SE_JIF, ConditionValue, SE_VF_GetLabel(ElseLabel), Null)
			Else
				SE_VF_CreateInst(SE_JIF, ConditionValue, SE_VF_GetLabel(EndLabel), Null)
			EndIf

			ConditionNode=Null
		EndIf

		While Block<>Null
			If Block\BlockType=SE_BLOCK_ELSE
				If Block\Node<>Null Then ConditionNode=Block\Node\FirstNode[1]
				Block=Block\NextBlock
				Exit
			EndIf

			SE_CompileBlock(Block)
			Block=Block\NextBlock
		Wend

		If Block=Null
			EndLabel\Inst=Last SE_VF_Inst
			EndLabel\Shift=True
			Exit
		EndIf

		SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(EndLabel), Null, Null)

		ElseLabel\Inst=Last SE_VF_Inst
		ElseLabel\Shift=True

		ElseCount=ElseCount-1
		
		If ElseCount Then ElseLabel=SE_VF_CreateLabel()
	Forever
End Function

Function SE_Compile_Select(RootBlock.SE_Block)
	Local Block.SE_Block=RootBlock\FirstBlock

	Local CaseCount

	While Block<>Null
		If Block\BlockType=SE_BLOCK_CASE Or Block\BlockType=SE_BLOCK_ELSE Then CaseCount=CaseCount+1
		Block=Block\NextBlock
	Wend

	Local Value.SE_VF_Value=SE_CompileNode(RootBlock\Node\FirstNode[1])
	If Value=Null Then Value=SE_PopTemp()
	Local MainValue.SE_VF_Value=SE_PushTemp()
	SE_VF_CreateInst(SE_MOV, MainValue, Value, Null)

	Block=RootBlock\FirstBlock

	Local ComparisonNode.SE_Node=Block\Node
	Local ComparisonValue.SE_VF_Value
	Local ComparisonResult.SE_VF_Value=SE_PushTemp()

	Local EndLabel.SE_VF_Label=SE_VF_CreateLabel()
	Local CaseLabel.SE_VF_Label
	Local CodeLabel.SE_VF_Label

	Repeat
		If Block\BlockType=SE_BLOCK_CASE
		
			If CaseLabel<>Null
				CaseLabel\Inst=Last SE_VF_Inst
				CaseLabel\Shift=True
			EndIf
		
			CaseCount=CaseCount-1
			If CaseCount Then CaseLabel=SE_VF_CreateLabel()
		
			CodeLabel=Null

			ComparisonNode=Block\Node\FirstNode[1]

			ComparisonResult=SE_PushTemp()
			
			Repeat
				ComparisonValue=SE_CompileNode(ComparisonNode)
				If ComparisonValue=Null Then ComparisonValue=SE_PopTemp()

				ComparisonNode=SE_NextNode(ComparisonNode)
				
				SE_VF_CreateInst(SE_CE, MainValue, ComparisonValue, ComparisonResult)
				
				If ComparisonNode=Null
					If CaseCount
						SE_VF_CreateInst(SE_JIF, ComparisonResult, SE_VF_GetLabel(CaseLabel), Null)
					Else
						SE_VF_CreateInst(SE_JIF, ComparisonResult, SE_VF_GetLabel(EndLabel), Null)
					EndIf

					If CodeLabel<>Null
						CodeLabel\Inst=Last SE_VF_Inst
						CodeLabel\Shift=True
					EndIf
					Exit
				Else
					ComparisonNode=ComparisonNode\NextNode
					If CodeLabel=Null Then CodeLabel=SE_VF_CreateLabel()
					SE_VF_CreateInst(SE_JIT, ComparisonResult, SE_VF_GetLabel(CodeLabel), Null)
				EndIf

			Forever

			SE_PopTemp()

			Block=Block\NextBlock

		Else If Block\BlockType=SE_BLOCK_ELSE
			CaseLabel\Inst=Last SE_VF_Inst
			CaseLabel\Shift=True
				
			Block=Block\NextBlock
		EndIf

		While Block<>Null
			If Block\BlockType=SE_BLOCK_CASE Or Block\BlockType=SE_BLOCK_ELSE Then Exit
			SE_CompileBlock(Block)
			Block=Block\NextBlock
		Wend

		If Block=Null
			EndLabel\Inst=Last SE_VF_Inst
			EndLabel\Shift=True
			Exit
		EndIf

		SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(EndLabel), Null, Null)
	Forever
End Function

Function SE_Compile_For(RootBlock.SE_Block)
	Local Block.SE_Block=RootBlock\FirstBlock

	Local Initial.SE_Node=Block\Node
	Block=Block\NextBlock
	
	Local Condition.SE_Node=Block\Node
	Block=Block\NextBlock
	
	Local Final.SE_Node=Block\Node
	Block=Block\NextBlock

	;	Create block space
	Local BlockSpace.SE_BlockSpace=SE_PushBlock(Null)
	
	BlockSpace\StartLabel=SE_VF_CreateLabel()

	Local Temp.SE_TempValue

	;Compile initial expression
	If Initial<>Null
		If SE_CompileNode(Initial\FirstNode[1])=Null Then SE_PopTemp()
	EndIf

	BlockSpace\StartLabel\Inst=Last SE_VF_Inst
	BlockSpace\StartLabel\Shift=True

	;Compile condition expression
	Local ConditionResult.SE_VF_Value
	
	If Condition<>Null
		BlockSpace\EndLabel=SE_VF_CreateLabel()
		ConditionResult=SE_CompileNode(Condition\FirstNode[1])
		If ConditionResult=Null Then ConditionResult=SE_PopTemp()
		SE_VF_CreateInst(SE_JIF, ConditionResult, SE_VF_GetLabel(BlockSpace\EndLabel), Null)
	EndIf

	;Compile body
	While Block<>Null
		SE_CompileBlock(Block)
		Block=Block\NextBlock
	Wend

	;Compile final expression
	If Final<>Null
		If SE_CompileNode(Final\FirstNode[1])=Null Then SE_PopTemp()
	EndIf

	If BlockSpace\EndLabel<>Null
		BlockSpace\EndLabel\Inst=SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\StartLabel), Null, Null)
		BlockSpace\EndLabel\Shift=True
	Else
		SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\StartLabel), Null, Null)
	EndIf

	SE_PopBlock()
End Function

Function SE_Compile_While(RootBlock.SE_Block)
	Local Condition.SE_Node=RootBlock\Node

	Local BlockSpace.SE_BlockSpace=SE_PushBlock(Null)
	BlockSpace\StartLabel=SE_VF_CreateLabel()
	BlockSpace\EndLabel=SE_VF_CreateLabel()

	Local Block.SE_Block=RootBlock\FirstBlock

	BlockSpace\StartLabel\Inst=Last SE_VF_Inst
	BlockSpace\StartLabel\Shift=True

	;Compile condition expression
	Local ConditionResult.SE_VF_Value

	If Condition<>Null
		ConditionResult=SE_CompileNode(Condition\FirstNode[1])
		If ConditionResult=Null Then ConditionResult=SE_PopTemp()
		SE_VF_CreateInst(SE_JIF, ConditionResult, SE_VF_GetLabel(BlockSpace\EndLabel), Null)
	EndIf

	;Compile body
	While Block<>Null
		SE_CompileBlock(Block)
		Block=Block\NextBlock
	Wend

	BlockSpace\EndLabel\Inst=SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\StartLabel), Null, Null)
	BlockSpace\EndLabel\Shift=True

	SE_PopBlock()
End Function

Function SE_Compile_Do(RootBlock.SE_Block)
	Local BlockSpace.SE_BlockSpace=SE_PushBlock(Null)
	BlockSpace\StartLabel=SE_VF_CreateLabel()

	Local Block.SE_Block=RootBlock\FirstBlock

	BlockSpace\StartLabel\Inst=Last SE_VF_Inst
	BlockSpace\StartLabel\Shift=True

	;Compile body
	While Block<>Null
		If Block\BlockType=SE_BLOCK_REPEAT Then Exit
		SE_CompileBlock(Block)
		Block=Block\NextBlock
	Wend

	If Block=Null
		SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\StartLabel), Null, Null)
	Else
		Local Condition.SE_Node=Block\Node
		Local ConditionResult.SE_VF_Value
	
		If Condition<>Null
			BlockSpace\EndLabel=SE_VF_CreateLabel()
			ConditionResult=SE_CompileNode(Condition\FirstNode[1])
			If ConditionResult=Null Then ConditionResult=SE_PopTemp()
			SE_VF_CreateInst(SE_JIT, ConditionResult, SE_VF_GetLabel(BlockSpace\StartLabel), Null)
		EndIf
	EndIf

	If BlockSpace\EndLabel<>Null
		BlockSpace\EndLabel\Inst=Last SE_VF_Inst
		BlockSpace\EndLabel\Shift=True
	Else
		SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\StartLabel), Null, Null)
	EndIf

	SE_PopBlock()
End Function

Function SE_Compile_Break(Block.SE_Block)
	Local BlockSpace.SE_BlockSpace=Last SE_BlockSpace
	If BlockSpace\EndLabel=Null Then BlockSpace\EndLabel=SE_VF_CreateLabel()

	SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\EndLabel), Null, Null)
End Function

Function SE_Compile_Continue(Block.SE_Block)
	Local BlockSpace.SE_BlockSpace=Last SE_BlockSpace

	SE_VF_CreateInst(SE_JMP, SE_VF_GetLabel(BlockSpace\StartLabel), Null, Null)
End Function

Function SE_Compile_Return(Block.SE_Block)
	Local Expression.SE_Node=Block\Node
	Local Value.SE_VF_Value

	If Expression<>Null
		Value=SE_CompileNode(Block\Node\FirstNode[1])
		If Value=Null Then Value=SE_PopTemp()
		
		SE_VF_CreateInst(SE_RET, Value, Null, Null)
	Else
		SE_VF_CreateInst(SE_END, Value, Null, Null)
	EndIf
End Function

Function SE_CompileBlock(Block.SE_Block)
	Select Block\BlockType
		Case SE_BLOCK_EXPRESSION
			If SE_CompileNode(Block\Node\FirstNode[1])=Null Then SE_PopTemp()

		Case SE_BLOCK_VAR
			SE_Compile_Var(Block)

		Case SE_BLOCK_DEF
			SE_Compile_Def(Block)

		Case SE_BLOCK_IF
			SE_Compile_If(Block)

		Case SE_BLOCK_SELECT
			SE_Compile_Select(Block)

		Case SE_BLOCK_FOR
			SE_Compile_For(Block)

		Case SE_BLOCK_WHILE
			SE_Compile_While(Block)

		Case SE_BLOCK_DO
			SE_Compile_Do(Block)

		Case SE_BLOCK_BREAK
			SE_Compile_Break(Block)

		Case SE_BLOCK_CONTINUE
			SE_Compile_Continue(Block)

		Case SE_BLOCK_RETURN
			SE_Compile_Return(Block)
	End Select
End Function

Function SE_Compile(FileName$)
	;	1	Parse lines
	SE_ParseLines(FileName)
	If SE_ERROR Then Return

	;	2	Parse tokens
	SE_ParseTokens()
	If SE_ERROR Then Return

	;	3	Parse chunks
	SE_ParseChunks()
	If SE_ERROR Then Return

	;	4	Parse blocks
	SE_ParseBlocks(Null)
	If SE_ERROR Then Return

	;	5	Parse definitions
	SE_ParseDefinitions()
	If SE_ERROR Then Return

	;	6	Syntax check
	SE_CheckSyntax()
	If SE_ERROR Then Return

	;	7	Compile functions (except _main)
	Local Block.SE_Block=SE_ROOT_BLOCK\FirstBlock

	While Block<>Null
		If Block\BlockType=SE_BLOCK_DEF Then SE_CompileBlock(Block)
		Block=Block\NextBlock
	Wend

	;	8	Compile function '_main'
	Block=SE_ROOT_BLOCK\FirstBlock
	SE_FUNCTION_DEF=SE_MAIN_FUNCTION

	SE_FUNCTION_DEF\Func\FirstInst=Last SE_VF_Inst
	
	While Block<>Null
		If Block\BlockType<>SE_BLOCK_DEF Then SE_CompileBlock(Block)
		Block=Block\NextBlock
	Wend

	SE_VF_CreateInst(SE_END, Null, Null, Null)

	If SE_FUNCTION_DEF\Func\FirstInst=Null
		SE_FUNCTION_DEF\Func\FirstInst=First SE_VF_Inst
	Else
		SE_FUNCTION_DEF\Func\FirstInst=After SE_FUNCTION_DEF\Func\FirstInst
	EndIf
	
	SE_FUNCTION_DEF\Func\LastInst=Last SE_VF_Inst

	;	Shift down all pointers
	For SE_FUNCTION_DEF=Each SE_FunctionDef
		If SE_FUNCTION_DEF<>SE_MAIN_FUNCTION Then SE_FUNCTION_DEF\Func\LastInst=After SE_FUNCTION_DEF\Func\LastInst
	Next

	SE_IndexTemps()
End Function