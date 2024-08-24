

Type Materials
	Field name$
	Field Diff
	Field Bump
	
	Field StepSound%
End Type

Function LoadMaterials(file$)
	;Catch("Uncaught (LoadMaterials)")
	;If Not BumpEnabled Then Return
	
	Local TemporaryString$
	Local mat.Materials = Null
	Local StrTemp$ = ""
	
	Local f = OpenFile(file)
	
	While Not Eof(f)
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString,1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			mat.Materials = New Materials
			
			mat\name = Lower(TemporaryString)
			
			If BumpEnabled Then
				StrTemp = GetINIString(file, TemporaryString, "bump")
				If StrTemp <> "" Then 
					mat\Bump =  LoadTexture_Strict(StrTemp)
		
				EndIf
			EndIf
			
			mat\StepSound = (GetINIInt(file, TemporaryString, "stepsound")+1)
		EndIf
	Wend
	
	CloseFile f
	
	;Catch("LoadMaterials")
End Function

Function LoadWorld(file$, rt.RoomTemplates)
	Local map=LoadAnimMesh_Strict(file)
	If Not map Then Return
	
	Local x#,y#,z#,i%,c%
	Local mat.Materials
	
	Local world=CreatePivot()
	Local meshes=CreatePivot(world)
	Local renderbrushes=CreateMesh(world)
	Local collisionbrushes=CreatePivot(world)
	;Local pointentities=CreatePivot(world)
	;Local solidentities=CreatePivot(world)
	EntityType collisionbrushes,HIT_MAP
	
	For c=1 To CountChildren(map)
		
		Local node=GetChild(map,c)	
		Local classname$=Lower(KeyValue(node,"classname"))
		
		Select classname
				
			;===============================================================================
			;Map Geometry
			;===============================================================================
				
			Case "mesh"
				EntityParent node,meshes
				
				If KeyValue(node,"disablecollisions")<>1 Then
					EntityType node,HIT_MAP
					EntityPickMode node, 2					
				EndIf
				
				c=c-1
				;EntityType node,HIT_MAP
				
			Case "brush"
				RotateMesh node,EntityPitch(node),EntityYaw(node),EntityRoll(node)
				PositionMesh node,EntityX(node),EntityY(node),EntityZ(node)
				
				AddMesh node,renderbrushes	
				
				EntityAlpha node,0
				EntityType node,HIT_MAP
				EntityAlpha node,0
				EntityParent node,collisionbrushes
				EntityPickMode node, 2
				
				c=c-1
				
			;===============================================================================
			;Solid Entities
			;===============================================================================
			Case "item"
				;name$ = KeyValue(node,"name","")
				;tempname$ = KeyValue(node,"tempname","")				
				;CreateItem(name,tempname,EntityX(node)*RoomScale,EntityY(node)*RoomScale,EntityZ(node)*RoomScale)
			Case "screen"
				
				x# = EntityX(node)*RoomScale
				y# = EntityY(node)*RoomScale
				z# = EntityZ(node)*RoomScale
				
				If x<>0 Or y<>0 Or z<>0 Then 
					Local ts.TempScreens = New TempScreens	
					ts\x = x
					ts\y = y
					ts\z = z
					ts\imgpath = KeyValue(node,"imgpath","")
					ts\roomtemplate = rt
				EndIf
				
			Case "waypoint"
				x# = EntityX(node)*RoomScale
				y# = EntityY(node)*RoomScale
				z# = EntityZ(node)*RoomScale				
				Local w.TempWayPoints = New TempWayPoints
				w\roomtemplate = rt
				w\x = x
				w\y = y
				w\z = z
				;EntityParent (w\obj, collisionbrushes)
				
			Case "light"
				x# = EntityX(node)*RoomScale
				y# = EntityY(node)*RoomScale
				z# = EntityZ(node)*RoomScale
				
				If x<>0 Or y<>0 Or z<>0 Then 
					range# = Float(KeyValue(node,"range","1"))/2000.0
					lcolor$=KeyValue(node,"color","255 255 255")
					intensity# = Min(Float(KeyValue(node,"intensity","1.0"))*0.8,1.0)
					r=Int(Piece(lcolor,1," "))*intensity
					g=Int(Piece(lcolor,2," "))*intensity
					b=Int(Piece(lcolor,3," "))*intensity
					
					AddTempLight(rt, x,y,z, 2, range, r,g,b)
				EndIf
			Case "spotlight"	
				x# = EntityX(node)*RoomScale
				y# = EntityY(node)*RoomScale
				z# = EntityZ(node)*RoomScale
				If x<>0 Or y<>0 Or z<>0 Then 
					range# = Float(KeyValue(node,"range","1"))/700.0
					lcolor$=KeyValue(node,"color","255 255 255")
					intensity# = Min(Float(KeyValue(node,"intensity","1.0"))*0.8,1.0)
					r=Int(Piece(lcolor,1," "))*intensity
					g=Int(Piece(lcolor,2," "))*intensity
					b=Int(Piece(lcolor,3," "))*intensity
					
					Local lt.LightTemplates = AddTempLight(rt, x,y,z, 3, range, r,g,b)
					angles$=KeyValue(node,"angles","0 0 0")
					pitch#=Piece(angles,1," ")
					yaw#=Piece(angles,2," ")
					lt\pitch = pitch
					lt\yaw = yaw
					
					lt\innerconeangle = Int(KeyValue(node,"innerconeangle",""))
					lt\outerconeangle = Int(KeyValue(node,"outerconeangle",""))	
				EndIf
			Case "soundemitter"
				For i = 0 To 3
					If rt\TempSoundEmitter[i]=0 Then
						rt\TempSoundEmitterX[i]=EntityX(node)*RoomScale
						rt\TempSoundEmitterY[i]=EntityY(node)*RoomScale
						rt\TempSoundEmitterZ[i]=EntityZ(node)*RoomScale
						rt\TempSoundEmitter[i]=Int(KeyValue(node,"sound","0"))
						
						rt\TempSoundEmitterRange[i]=Float(KeyValue(node,"range","1"))
						Exit
					EndIf
				Next
				
			;Invisible collision brush
			Case "field_hit"
				EntityParent node,collisionbrushes
				EntityType node,HIT_MAP
				EntityAlpha node,0
				c=c-1
				
			;===============================================================================
			;Point Entities
			;===============================================================================
				
			;Camera start position point entity
			Case "playerstart"
				angles$=KeyValue(node,"angles","0 0 0")
				pitch#=Piece(angles,1," ")
				yaw#=Piece(angles,2," ")
				roll#=Piece(angles,3," ")
				If cam Then
					PositionEntity cam,EntityX(node),EntityY(node),EntityZ(node)
					RotateEntity cam,pitch,yaw,roll
				EndIf
				
		End Select
	Next
	
	;If BumpEnabled Then 
	;	
	;	For i = 1 To CountSurfaces(renderbrushes)
	;		sf = GetSurface(renderbrushes,i)
	;		b = GetSurfaceBrush( sf )
	;		t = GetBrushTexture(b, 1)
	;		texname$ =  StripPath(TextureName(t))
	;		
	;		For mat.Materials = Each Materials
	;			If texname = mat\name Then
	;				If mat\Bump <> 0 Then 
	;					t1 = GetBrushTexture(b,0)
	;					
	;					BrushTexture b, t1, 0, 0 ;light map
	;					BrushTexture b, mat\Bump, 0, 1 ;bump
	;					BrushTexture b, t, 0, 2 ;diff
	;					
	;					PaintSurface sf,b
	;					
	;					If StripPath(TextureName(t1)) <> "" Then FreeTexture t1
	;					
	;					;If t1<>0 Then FreeTexture t1
	;					;If t2 <> 0 Then FreeTexture t2						
	;				EndIf
	;				Exit
	;			EndIf 
	;		Next
	;		
	;		FreeTexture t
	;		FreeBrush b
	;		
	;	Next
	;	
	;EndIf
	
	EntityFX renderbrushes, 1
	
	FreeEntity map
	
	Return world	
	
	
End Function

;RMESH STUFF;;;;

Function StripFilename$(file$)
	Local mi$=""
	Local lastSlash%=0
	If Len(file)>0
		For i%=1 To Len(file)
			mi=Mid(file$,i,1)
			If mi="\" Or mi="/" Then
				lastSlash=i
			EndIf
		Next
	EndIf
	
	Return Left(file,lastSlash)
End Function

Function GetTextureFromCache%(name$)
	For tc.Materials=Each Materials
		If tc\name = name Then Return tc\Diff
	Next
	Return 0
End Function

Function GetBumpFromCache%(name$)
	For tc.Materials=Each Materials
		If tc\name = name Then Return tc\Bump
	Next
	Return 0
End Function

Function GetCache.Materials(name$)
	For tc.Materials=Each Materials
		If tc\name = name Then Return tc
	Next
	Return Null
End Function

Function AddTextureToCache(texture%)
	Local tc.Materials=GetCache(StripPath(TextureName(texture)))
	If tc.Materials=Null Then
		tc.Materials=New Materials
		tc\name=StripPath(TextureName(texture))
		If BumpEnabled Then
			Local temp$=GetINIString("Data\materials.ini",tc\name,"bump")
			If temp<>"" Then
				tc\Bump=LoadTexture_Strict(temp)

			Else
				tc\Bump=0
			EndIf
		EndIf
		tc\Diff=0
	EndIf
	If tc\Diff=0 Then tc\Diff=texture
End Function

Function ClearTextureCache()
	For tc.Materials=Each Materials
		If tc\Diff<>0 Then FreeTexture tc\Diff
		If tc\Bump<>0 Then FreeTexture tc\Bump
		Delete tc
	Next
End Function

Function FreeTextureCache()
	For tc.Materials=Each Materials
		If tc\Diff<>0 Then FreeTexture tc\Diff
		If tc\Bump<>0 Then FreeTexture tc\Bump
		tc\Diff = 0 : tc\Bump = 0
	Next
End Function

Function LoadRMesh(file$,rt.RoomTemplates)
	;Catch("Uncaught (LoadRMesh)")
	;generate a texture made of white
	Local blankTexture%
	blankTexture=CreateTexture(4,4,1,1)
	ClsColor 255,255,255
	SetBuffer TextureBuffer(blankTexture)
	Cls
	SetBuffer BackBuffer()
	
	Local pinkTexture%
	pinkTexture=CreateTexture(4,4,1,1)
	ClsColor 255,255,255
	SetBuffer TextureBuffer(pinkTexture)
	Cls
	SetBuffer BackBuffer()
	
	ClsColor 0,0,0
	
	;read the file
	Local f%=ReadFile(file)
	Local i%,j%,k%,x#,y#,z#,yaw#
	Local vertex%
	Local temp1i%,temp2i%,temp3i%
	Local temp1#,temp2#,temp3#
	Local temp1s$, temp2s$
	
	Local collisionMeshes% = CreatePivot()
	
	Local hasTriggerBox% = False
	
	For i=0 To 3 ;reattempt up to 3 times
		If f=0 Then
			f=ReadFile(file)
		Else
			Exit
		EndIf
	Next
	If f=0 Then RuntimeError "Error reading file "+Chr(34)+file+Chr(34)
	Local isRMesh$ = ReadString(f)
	If isRMesh$="RoomMesh"
		;Continue
	ElseIf isRMesh$="RoomMesh.HasTriggerBox"
		hasTriggerBox% = True
	Else
		RuntimeError Chr(34)+file+Chr(34)+" is Not RMESH ("+isRMesh+")"
	EndIf
	
	file=StripFilename(file)
	
	Local count%,count2%
	
	;drawn meshes
	Local Opaque%,Alpha%
	
	Opaque=CreateMesh()
	Alpha=CreateMesh()
	
	count = ReadInt(f)
	Local childMesh%
	Local surf%,tex%[2],brush%
	
	Local isAlpha%
	
	Local u#,v#
	
	For i=1 To count ;drawn mesh
		childMesh=CreateMesh()
		
		surf=CreateSurface(childMesh)
		
		brush=CreateBrush()
		
		tex[0]=0 : tex[1]=0
		
		isAlpha=0
		For j=0 To 1
			temp1i=ReadByte(f)
			If temp1i<>0 Then
				temp1s=ReadString(f)
				tex[j]=GetTextureFromCache(temp1s)
				If tex[j]=0 Then ;texture is not in cache
					Select True
						Case temp1i<3
							tex[j]=LoadTexture_Strict(file+temp1s,1)
						Default
							tex[j]=LoadTexture_Strict(file+temp1s,3)
					End Select
					
					If tex[j]<>0 Then
						AddTextureToCache(tex[j])
				EndIf
					
				EndIf
				If tex[j]<>0 Then
					isAlpha=2
					If temp1i=3 Then isAlpha=1

				EndIf
			EndIf
		Next

		surf=CreateSurface(childMesh)
		
		If isAlpha>0 Then PaintSurface surf,brush
		
		FreeBrush brush : brush = 0
		
		count2=ReadInt(f) ;vertices
		
		For j%=1 To count2
			;world coords
			x=ReadFloat(f) : y=ReadFloat(f) : z=ReadFloat(f)
			vertex=AddVertex(surf,x,y,z)
			
			;texture coords
			For k%=0 To 1
				u=ReadFloat(f) : v=ReadFloat(f)
				VertexTexCoords surf,vertex,u,v,0.0,k
			Next
			
			;colors
			temp1i=ReadByte(f)
			temp2i=ReadByte(f)
			temp3i=ReadByte(f)
			VertexColor surf,vertex,temp1i,temp2i,temp3i,1.0
		Next
		
		count2=ReadInt(f) ;polys
		For j%=1 To count2
			temp1i = ReadInt(f) : temp2i = ReadInt(f) : temp3i = ReadInt(f)
			AddTriangle(surf,temp1i,temp2i,temp3i)
		Next
		
		If isAlpha=1 Then
			AddMesh childMesh,Alpha
			EntityAlpha childMesh,0.0
		Else
			AddMesh childMesh,Opaque
			EntityParent childMesh,collisionMeshes
			EntityAlpha childMesh,0.0
			EntityType childMesh,HIT_MAP
			EntityPickMode childMesh,2
			
			;make collision double-sided
			Local flipChild% = CopyMesh(childMesh)
			FlipMesh(flipChild)
			AddMesh flipChild,childMesh
			FreeEntity flipChild			
		EndIf
		For j=0 To 1
			If tex[j] <> 0 Then
				If Lower(StripPath(TextureName(tex[j]))) = "glass.png" Xor Lower(StripPath(TextureName(tex[j]))) = "matglass.png" Then
					AddMesh childMesh,Opaque
					EntityParent childMesh,collisionMeshes
					EntityType childMesh,HIT_MAP
					EntityPickMode childMesh,2
					
					flipChild% = CopyMesh(childMesh)
					FlipMesh(flipChild)
					AddMesh flipChild,childMesh
					FreeEntity flipChild
				EndIf
			EndIf
		Next
		
	Next
	
	Local hiddenMesh%
	hiddenMesh=CreateMesh()
	
	count=ReadInt(f) ;invisible collision mesh
	For i%=1 To count
		surf=CreateSurface(hiddenMesh)
		count2=ReadInt(f) ;vertices
		For j%=1 To count2
			;world coords
			x=ReadFloat(f) : y=ReadFloat(f) : z=ReadFloat(f)
			vertex=AddVertex(surf,x,y,z)
		Next
		
		count2=ReadInt(f) ;polys
		For j%=1 To count2
			temp1i = ReadInt(f) : temp2i = ReadInt(f) : temp3i = ReadInt(f)
			AddTriangle(surf,temp1i,temp2i,temp3i)
			AddTriangle(surf,temp1i,temp3i,temp2i)
		Next
	Next
	
	;trigger boxes
	If hasTriggerBox
		DebugLog "TriggerBoxEnable"
		rt\TempTriggerboxAmount = ReadInt(f)
		For tb = 0 To rt\TempTriggerboxAmount-1
			rt\TempTriggerbox[tb] = CreateMesh(rt\obj)
			count = ReadInt(f)
			For i%=1 To count
				surf=CreateSurface(rt\TempTriggerbox[tb])
				count2=ReadInt(f)
				For j%=1 To count2
					x=ReadFloat(f) : y=ReadFloat(f) : z=ReadFloat(f)
					vertex=AddVertex(surf,x,y,z)
				Next
				count2=ReadInt(f)
				For j%=1 To count2
					temp1i = ReadInt(f) : temp2i = ReadInt(f) : temp3i = ReadInt(f)
					AddTriangle(surf,temp1i,temp2i,temp3i)
					AddTriangle(surf,temp1i,temp3i,temp2i)
				Next
			Next
			rt\TempTriggerboxName[tb] = ReadString(f)
		Next
	EndIf
	
	count=ReadInt(f) ;point entities
	For i%=1 To count
		temp1s=ReadString(f)
		Select temp1s
			Case "screen"
				
				temp1=ReadFloat(f)*RoomScale
				temp2=ReadFloat(f)*RoomScale
				temp3=ReadFloat(f)*RoomScale
				
				temp2s$ =ReadString(f)
				
				If temp1<>0 Or temp2<>0 Or temp3<>0 Then 
					Local ts.TempScreens = New TempScreens	
					ts\x = temp1
					ts\y = temp2
					ts\z = temp3
					ts\imgpath = temp2s
					ts\roomtemplate = rt
				EndIf
				
			Case "waypoint"
				
				temp1=ReadFloat(f)*RoomScale
				temp2=ReadFloat(f)*RoomScale
				temp3=ReadFloat(f)*RoomScale
				
				Local w.TempWayPoints = New TempWayPoints
				w\roomtemplate = rt
				w\x = temp1
				w\y = temp2
				w\z = temp3
				
			Case "light"
				
				temp1=ReadFloat(f)*RoomScale
				temp2=ReadFloat(f)*RoomScale
				temp3=ReadFloat(f)*RoomScale
				
				If temp1<>0 Or temp2<>0 Or temp3<>0 Then 
					range# = ReadFloat(f)/2000.0
					lcolor$=ReadString(f)
					intensity# = Min(ReadFloat(f)*0.8,1.0)
					r%=Int(Piece(lcolor,1," "))*intensity
					g%=Int(Piece(lcolor,2," "))*intensity
					b%=Int(Piece(lcolor,3," "))*intensity
					
					AddTempLight(rt, temp1,temp2,temp3, 2, range, r,g,b)
				Else
					ReadFloat(f) : ReadString(f) : ReadFloat(f)
				EndIf
				
			Case "spotlight"
				
				temp1=ReadFloat(f)*RoomScale
				temp2=ReadFloat(f)*RoomScale
				temp3=ReadFloat(f)*RoomScale
				
				If temp1<>0 Or temp2<>0 Or temp3<>0 Then 
					range# = ReadFloat(f)/2000.0
					lcolor$=ReadString(f)
					intensity# = Min(ReadFloat(f)*0.8,1.0)
					r%=Int(Piece(lcolor,1," "))*intensity
					g%=Int(Piece(lcolor,2," "))*intensity
					b%=Int(Piece(lcolor,3," "))*intensity
					
					Local lt.LightTemplates = AddTempLight(rt, temp1,temp2,temp3, 2, range, r,g,b)
					angles$=ReadString(f)
					pitch#=Piece(angles,1," ")
					yaw#=Piece(angles,2," ")
					lt\pitch = pitch
					lt\yaw = yaw
					
					lt\innerconeangle = ReadInt(f)
					lt\outerconeangle = ReadInt(f)
				Else
					ReadFloat(f) : ReadString(f) : ReadFloat(f) : ReadString(f) : ReadInt(f) : ReadInt(f)
				EndIf
				
			Case "soundemitter"
				
				temp1i=0
				
				For j = 0 To MaxRoomEmitters-1
					If rt\TempSoundEmitter[j]=0 Then
						rt\TempSoundEmitterX[j]=ReadFloat(f)*RoomScale
						rt\TempSoundEmitterY[j]=ReadFloat(f)*RoomScale
						rt\TempSoundEmitterZ[j]=ReadFloat(f)*RoomScale
						rt\TempSoundEmitter[j]=ReadInt(f)
						
						rt\TempSoundEmitterRange[j]=ReadFloat(f)
						temp1i=1
						Exit
					EndIf
				Next
				
				If temp1i=0 Then
					ReadFloat(f)
					ReadFloat(f)
					ReadFloat(f)
					ReadInt(f)
					ReadFloat(f)
				EndIf
				
			Case "playerstart"
				
				temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
				
				angles$=ReadString(f)
				pitch#=Piece(angles,1," ")
				yaw#=Piece(angles,2," ")
				roll#=Piece(angles,3," ")
				If cam Then
					PositionEntity cam,temp1,temp2,temp3
					RotateEntity cam,pitch,yaw,roll
				EndIf
			Case "model"
				file = ReadString(f)
				If file<>""
					Local model.Props = CreatePropObj("GFX\Map\Props\"+file)
					
					model\X = ReadFloat(f)
					model\Y = ReadFloat(f)
					model\Z = ReadFloat(f)
					
					model\RotX = ReadFloat(f)
					model\RotY = ReadFloat(f)
					model\RotZ = ReadFloat(f)
					
					model\ScaleX = ReadFloat(f)
					model\ScaleY = ReadFloat(f)
					model\ScaleZ = ReadFloat(f)
					
					model\roomtemplate = rt
				Else
					DebugLog "file = 0"
					temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
					DebugLog temp1+", "+temp2+", "+temp3
					
					;Stop
				EndIf
		End Select
	Next
	
	Local obj%
	
	temp1i=CopyMesh(Alpha)
	FlipMesh temp1i
	AddMesh temp1i,Alpha
	FreeEntity temp1i
	
	If brush <> 0 Then FreeBrush brush
	
	AddMesh Alpha,Opaque
	FreeEntity Alpha
	
	EntityFX Opaque,3
	
	EntityAlpha hiddenMesh,0.0
	EntityAlpha Opaque,1.0
	
	;EntityType Opaque,HIT_MAP
	EntityType hiddenMesh,HIT_MAP
	FreeTexture blankTexture
	
	;AddMesh hiddenMesh,BigRoomMesh
	
	obj=CreatePivot()
	CreatePivot(obj) ;skip "meshes" object
	EntityParent Opaque,obj
	EntityParent hiddenMesh,obj
	CreatePivot(obj) ;skip "pointentites" object
	CreatePivot(obj) ;skip "solidentites" object
	EntityParent collisionMeshes,obj
	
	CloseFile f
	
	;Catch("LoadRMesh")
	Return obj%
	
End Function


;-----------;;;;

Function StripPath$(file$) 
	Local name$=""
	If Len(file$)>0 
		For i=Len(file$) To 1 Step -1 
			
			mi$=Mid$(file$,i,1) 
			If mi$="\" Or mi$="/" Then Return name$
			
			name$=mi$+name$ 
		Next 
		
	EndIf 
	
	Return name$ 
End Function

Function KeyValue$(entity,key$,defaultvalue$="")
	properties$=EntityName(entity)
	properties$=Replace(properties$,Chr(13),"")
	key$=Lower(key)
	Repeat
		p=Instr(properties,Chr(10))
		If p Then test$=(Left(properties,p-1)) Else test=properties
		testkey$=Piece(test,1,"=")
		testkey=Trim(testkey)
		testkey=Replace(testkey,Chr(34),"")
		testkey=Lower(testkey)
		If testkey=key Then
			value$=Piece(test,2,"=")
			value$=Trim(value$)
			value$=Replace(value$,Chr(34),"")
			Return value
		EndIf
		If Not p Then Return defaultvalue$
		properties=Right(properties,Len(properties)-p)
	Forever 
End Function


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;Forest gen consts
Const gridsize% = 10
Const deviation_chance% = 40 ;out of 100
Const branch_chance% = 65
Const branch_max_life% = 4
Const branch_die_chance% = 18
Const max_deviation_distance% = 3
Const return_chance% = 27
Const center = 5 ;(gridsize-1) / 2

Type Forest
	Field TileMesh%[6]
	Field DetailMesh%[5]
	Field grid%[(gridsize*gridsize)+11]
	Field TileEntities%[(gridsize*gridsize)+1]
	Field Forest_Pivot%
	
	Field Door%[2]
	Field DetailEntities%[2]
	
	Field ID%
End Type

Function move_forward%(dir%,pathx%,pathy%,retval%=0)
	;move 1 unit along the grid in the designated direction
	If dir = 1 Then
		If retval=0 Then
			Return pathx
		Else
			Return pathy+1
		EndIf
	EndIf
	If retval=0 Then
		Return pathx-1+dir
	Else
		Return pathy
	EndIf
End Function

Function chance%(chanc%)
	;perform a chance given a probability
	Return (Rand(0,100)<=chanc)
End Function

Function turn_if_deviating%(max_deviation_distance_%,pathx%,center_%,dir%,retval%=0)
	;check if deviating and return the answer. if deviating, turn around
	Local current_deviation% = center_ - pathx
	Local deviated% = False
	If (dir = 0 And current_deviation >= max_deviation_distance_) Or (dir = 2 And current_deviation <= -max_deviation_distance_) Then
		dir = (dir + 2) Mod 4
		deviated = True
	EndIf
	If retval=0 Then Return dir Else Return deviated
End Function

Function GenForestGrid(fr.Forest)
	;Catch("Uncaught (GenForestGrid)")
	fr\ID=LastForestID+1
	LastForestID=LastForestID+1
	
	Local door1_pos%,door2_pos%
	Local i%,j%
	door1_pos=Rand(3,7)
	door2_pos=Rand(3,7)
	
	;clear the grid
	For i=0 To gridsize-1
		For j=0 To gridsize-1
			fr\grid[(j*gridsize)+i]=0
		Next
	Next
	
	;set the position of the concrete and doors
	;For i=0 To gridsize-1
	;	fr\grid[i]=2
	;	fr\grid[((gridsize-1)*gridsize)+i]=2
	;Next
	fr\grid[door1_pos]=3
	fr\grid[((gridsize-1)*gridsize)+door2_pos]=3
	
	;generate the path
	Local pathx = door2_pos
	Local pathy = 1
	Local dir = 1 ;0 = left, 1 = up, 2 = right
	fr\grid[((gridsize-1-pathy)*gridsize)+pathx] = 1
	
	Local deviated%
	
	While pathy < gridsize -4
		If dir = 1 Then ;determine whether to go forward or to the side
			If chance(deviation_chance) Then
				;pick a branch direction
				dir = 2 * Rand(0,1)
				;make sure you have not passed max side distance
				dir = turn_if_deviating(max_deviation_distance,pathx,center,dir)
				deviated = turn_if_deviating(max_deviation_distance,pathx,center,dir,1)
				If deviated Then fr\grid[((gridsize-1-pathy)*gridsize)+pathx]=1
				pathx=move_forward(dir,pathx,pathy)
				pathy=move_forward(dir,pathx,pathy,1)
			EndIf
			
		Else
			;we are going to the side, so determine whether to keep going or go forward again
			dir = turn_if_deviating(max_deviation_distance,pathx,center,dir)
			deviated = turn_if_deviating(max_deviation_distance,pathx,center,dir,1)
			If deviated Or chance(return_chance) Then dir = 1
			
			pathx=move_forward(dir,pathx,pathy)
			pathy=move_forward(dir,pathx,pathy,1)
			;if we just started going forward go twice so as to avoid creating a potential 2x2 line
			If dir=1 Then
				fr\grid[((gridsize-1-pathy)*gridsize)+pathx]=1
				pathx=move_forward(dir,pathx,pathy)
				pathy=move_forward(dir,pathx,pathy,1)
			EndIf
		EndIf
		
		;add our position to the grid
		fr\grid[((gridsize-1-pathy)*gridsize)+pathx]=1
		
	Wend
	;finally, bring the path back to the door now that we have reached the end
	dir = 1
	While pathy < gridsize-2
		pathx=move_forward(dir,pathx,pathy)
		pathy=move_forward(dir,pathx,pathy,1)
		fr\grid[((gridsize-1-pathy)*gridsize)+pathx]=1
	Wend
	
	If pathx<>door1_pos Then
		dir=0
		If door1_pos>pathx Then dir=2
		While pathx<>door1_pos
			pathx=move_forward(dir,pathx,pathy)
			pathy=move_forward(dir,pathx,pathy,1)
			fr\grid[((gridsize-1-pathy)*gridsize)+pathx]=1
		Wend
	EndIf
	
	;attempt to create new branches
	Local new_y%,temp_y%,new_x%
	Local branch_type%,branch_pos%
	new_y=-3 ;used for counting off; branches will only be considered once every 4 units so as to avoid potentially too many branches
	While new_y<gridsize-6
		new_y=new_y+4
		temp_y=new_y
		new_x=0
		If chance(branch_chance) Then
			branch_type=-1
			If chance(cobble_chance) Then
				branch_type=-2
			EndIf
			;create a branch at this spot
			;determine if on left or on right
			branch_pos=2*Rand(0,1)
			;get leftmost or rightmost path in this row
			leftmost=gridsize
			rightmost=0
			For i=0 To gridsize
				If fr\grid[((gridsize-1-new_y)*gridsize)+i]=1 Then
					If i<leftmost Then leftmost=i
					If i>rightmost Then rightmost=i
				EndIf
			Next
			If branch_pos=0 Then new_x=leftmost-1 Else new_x=rightmost+1
			;before creating a branch make sure there are no 1's above or below
			If (temp_y<>0 And fr\grid[((gridsize-1-temp_y+1)*gridsize)+new_x]=1) Or fr\grid[((gridsize-1-temp_y-1)*gridsize)+new_x]=1 Then
				Exit ;break simply to stop creating the branch
			EndIf
			fr\grid[((gridsize-1-temp_y)*gridsize)+new_x]=branch_type ;make 4s so you don't confuse your branch for a path; will be changed later
			If branch_pos=0 Then new_x=leftmost-2 Else new_x=rightmost+2
			fr\grid[((gridsize-1-temp_y)*gridsize)+new_x]=branch_type ;branch out twice to avoid creating an unwanted 2x2 path with the real path
			i = 2
			While i<branch_max_life
				i=i+1
				If chance(branch_die_chance) Then
					Exit
				EndIf
				If Rand(0,3)=0 Then ;have a higher chance to go up to confuse the player
					If branch_pos = 0 Then
						new_x=new_x-1
					Else
						new_x=new_x+1
					EndIf
				Else
					temp_y=temp_y+1
				EndIf
				
				;before creating a branch make sure there are no 1's above or below
				n=((gridsize - 1 - temp_y + 1)*gridsize)+new_x
				If n < gridsize-1 Then 
					If temp_y <> 0 And fr\grid[n]=1 Then Exit
				EndIf
				n=((gridsize - 1 - temp_y - 1)*gridsize)+new_x
				If n>0 Then 
					If fr\grid[n]=1 Then Exit
				EndIf
				
				;If (temp_y <> 0 And fr\grid[((gridsize - 1 - temp_y + 1)*gridsize)+new_x]=1) Or fr\grid[((gridsize - 1 - temp_y - 1)*gridsize)+new_x] = 1 Then
				;	Exit
				;EndIf
				fr\grid[((gridsize-1-temp_y)*gridsize)+new_x]=branch_type ;make 4s so you don't confuse your branch for a path; will be changed later
				If temp_y>=gridsize-2 Then Exit
			Wend
		EndIf
	Wend
	
	;change branches from 4s to 1s (they were 4s so that they didn't accidently create a 2x2 path unintentionally)
	For i=0 To gridsize-1
		For j=0 To gridsize-1
			If fr\grid[(i*gridsize)+j]=-1 Then
				fr\grid[(i*gridsize)+j]=1
			ElseIf fr\grid[(i*gridsize)+j]=-2
				fr\grid[(i*gridsize)+j]=1
			;ElseIf fr\grid[(i*gridsize)+j]=0
				
			EndIf
		Next
	Next
	
	;Catch("GenForestGrid")
End Function

Function PlaceForest(fr.Forest,x#,y#,z#,r.Rooms)
	;Catch("Uncaught (PlaceForest)")
	;local variables
	Local tx%,ty%
	Local tile_size#=12.0
	Local tile_type%
	Local tile_entity%,detail_entity%
	
	Local tempf1#,tempf2#,tempf3#
	Local i%
	
	If fr\Forest_Pivot<>0 Then FreeEntity fr\Forest_Pivot : fr\Forest_Pivot=0
	For i%=ROOM1 To ROOM4
		If fr\TileMesh[i]<>0 Then FreeEntity fr\TileMesh[i] : fr\TileMesh[i]=0
	Next
	For i%=0 To 4
		If fr\DetailMesh[i]<>0 Then FreeEntity fr\DetailMesh[i] : fr\DetailMesh[i]=0
	Next
	
	fr\Forest_Pivot=CreatePivot()
	PositionEntity fr\Forest_Pivot,x,y,z,True
	
	;load assets
	
	Local hmap[ROOM4], mask[ROOM4]
	Local GroundTexture = LoadTexture_Strict("GFX\map\forest\forestfloor.jpg")
	;TextureBlend GroundTexture, FE_ALPHACURRENT
	Local PathTexture = LoadTexture_Strict("GFX\map\forest\forestpath.jpg")
	;TextureBlend PathTexture, FE_ALPHACURRENT
	
	hmap[ROOM1]=LoadImage("GFX\map\forest\forest1h.png")
	mask[ROOM1]=LoadTexture("GFX\map\forest\forest1h_mask.png",1+2)
	
	hmap[ROOM2]=LoadImage("GFX\map\forest\forest2h.png")
	mask[ROOM2]=LoadTexture("GFX\map\forest\forest2h_mask.png",1+2)
	
	hmap[ROOM2C]=LoadImage("GFX\map\forest\forest2Ch.png")
	mask[ROOM2C]=LoadTexture("GFX\map\forest\forest2Ch_mask.png",1+2)
	
	hmap[ROOM3]=LoadImage("GFX\map\forest\forest3h.png")
	mask[ROOM3]=LoadTexture("GFX\map\forest\forest3h_mask.png",1+2)
	
	hmap[ROOM4]=LoadImage("GFX\map\forest\forest4h.png")
	mask[ROOM4]=LoadTexture("GFX\map\forest\forest4h_mask.png",1+2)
	
	For i = ROOM1 To ROOM4
		;TextureBlend mask[i], FE_ALPHAMODULATE
		
		fr\TileMesh[i]=load_terrain(hmap[i],0.03,GroundTexture,PathTexture,mask[i])
	Next
	FreeTexture GroundTexture
	FreeTexture PathTexture
	;detail meshes
	;fr\DetailMesh[0]=LoadMesh_strict("GFX\map\forest\detail\860_1_tree1.b3d")
	;fr\DetailMesh[1]=LoadMesh_strict("GFX\map\forest\detail\860_1_tree1_leaves.b3d")
	fr\DetailMesh[0]=LoadMesh_Strict("GFX\map\forest\detail\treetest4.b3d");1.b3d)
	;EntityParent fr\DetailMesh[1],fr\DetailMesh[0]
	fr\DetailMesh[1]=LoadMesh_Strict("GFX\map\forest\detail\rock.b3d")
	fr\DetailMesh[2]=LoadMesh_Strict("GFX\map\forest\detail\rock2.b3d")
	fr\DetailMesh[3]=LoadMesh_Strict("GFX\map\forest\detail\treetest5.b3d")
	fr\DetailMesh[4]=LoadMesh_Strict("GFX\map\forest\wall.b3d")
	
	For i%=ROOM1 To ROOM4
		HideEntity fr\TileMesh[i]
	Next
	For i%=0 To 4
		HideEntity fr\DetailMesh[i]
	Next
	
	tempf3=MeshWidth(fr\TileMesh[ROOM1])
	tempf1=tile_size/tempf3
	
	For tx%=0 To gridsize-1
		For ty%=0 To gridsize-1
			If fr\grid[(ty*gridsize)+tx]=1 Then 
				
				tile_type = 0
				If tx+1<gridsize Then tile_type = (fr\grid[(ty*gridsize)+tx+1]>0)
				If tx-1=>0 Then tile_type = tile_type+(fr\grid[(ty*gridsize)+tx-1]>0)
				
				If ty+1<gridsize Then tile_type = tile_type+(fr\grid[((ty+1)*gridsize)+tx]>0)
				If ty-1=>0 Then tile_type = tile_type+(fr\grid[((ty-1)*gridsize)+tx]>0)
				
				;fr\grid[(ty*gridsize)+tx]=tile_type
				
				Local angle%=0
				Select tile_type
					Case 1
						tile_entity = CopyEntity(fr\TileMesh[ROOM1])
						
						If fr\grid[((ty+1)*gridsize)+tx]>0 Then
							angle = 180
						ElseIf fr\grid[(ty*gridsize)+tx-1]>0
							angle = 270
						ElseIf fr\grid[(ty*gridsize)+tx+1]>0
							angle = 90
						End If
						
						tile_type = ROOM1
					Case 2
						If fr\grid[((ty-1)*gridsize)+tx]>0 And fr\grid[((ty+1)*gridsize)+tx]>0 Then
							tile_entity = CopyEntity(fr\TileMesh[ROOM2])
							tile_type = ROOM2
						ElseIf fr\grid[(ty*gridsize)+tx+1]>0 And fr\grid[(ty*gridsize)+tx-1]>0
							tile_entity = CopyEntity(fr\TileMesh[ROOM2])
							angle = 90
							tile_type = ROOM2
						Else
							tile_entity = CopyEntity(fr\TileMesh[ROOM2C])
							If fr\grid[(ty*gridsize)+tx-1]>0 And fr\grid[((ty+1)*gridsize)+tx]>0 Then
								angle = 180
							ElseIf fr\grid[(ty*gridsize)+tx+1]>0 And fr\grid[((ty-1)*gridsize)+tx]>0
								
							ElseIf fr\grid[(ty*gridsize)+tx-1]>0 And fr\grid[((ty-1)*gridsize)+tx]>0
								angle = 270
							Else
								angle = 90
							EndIf
							tile_type = ROOM2C
						EndIf
					Case 3
						tile_entity = CopyEntity(fr\TileMesh[ROOM3])
						
						If fr\grid[((ty-1)*gridsize)+tx]=0 Then
							angle = 180
						ElseIf fr\grid[(ty*gridsize)+tx-1]=0
							angle = 90
						ElseIf fr\grid[(ty*gridsize)+tx+1]=0
							angle = 270
						End If
						
						tile_type = ROOM3
					Case 4
						tile_entity = CopyEntity(fr\TileMesh[ROOM4])	
						tile_type = ROOM4
					Default 
						DebugLog "tile_type: "+tile_type
				End Select
				
				If tile_type > 0 Then 
					
					Local itemPlaced[4]
					;2, 5, 8
					Local it.Items = Null
					If (ty Mod 3)=2 And itemPlaced[Floor(ty/3)]=False Then
						itemPlaced[Floor(ty/3)]=True
						it.Items = CreateItem("Log #"+Int(Floor(ty/3)+1), "paper", 0,0.5,0)
						
						EntityParent(it\collider, tile_entity)
					EndIf
					
					;place trees and other details
					;only placed on spots where the value of the heightmap is above 100
					SetBuffer ImageBuffer(hmap[tile_type])
					width = ImageWidth(hmap[tile_type])
					tempf4# = (tempf3/Float(width))
					For lx = 3 To width-2
						For ly = 3 To width-2
							GetColor lx,width-ly
							
							If ColorRed()>Rand(100,260) Then
								Select Rand(0,7)
									Case 0,1,2,3,4,5,6 ;create a tree
										detail_entity=CopyEntity(fr\DetailMesh[0])
										;EntityType detail_entity,HIT_MAP
										tempf2=Rnd(0.25,0.4)
										
										For i = 0 To 3
											d=CopyEntity(fr\DetailMesh[3])
											;ScaleEntity d,tempf2*1.1,tempf2,tempf2*1.1,True
											RotateEntity d, 0, 90*i+Rnd(-20,20), 0
											EntityParent(d,detail_entity)
											
											EntityFX d, 1;+8
										Next
										
										ScaleEntity detail_entity,tempf2*1.1,tempf2,tempf2*1.1,True
										PositionEntity detail_entity,lx*tempf4-(tempf3/2.0),ColorRed()*0.03-Rnd(3.0,3.2),ly*tempf4-(tempf3/2.0),True
										
										RotateEntity detail_entity,Rnd(-5,5),Rnd(360.0),0.0,True
										
										;EntityAutoFade(detail_entity,4.0,6.0)
									Case 7 ;add a rock
										detail_entity=CopyEntity(fr\DetailMesh[1])
										;EntityType detail_entity,HIT_MAP
										tempf2=Rnd(0.01,0.012)
										;ScaleEntity detail_entity,tempf2,tempf2*Rnd(1.0,2.0),tempf2,True
										
										PositionEntity detail_entity,lx*tempf4-(tempf3/2.0),ColorRed()*0.03-1.3,ly*tempf4-(tempf3/2.0),True
										
										EntityFX detail_entity, 1
										
										RotateEntity detail_entity,0.0,Rnd(360.0),0.0,True
									Case 6 ;add a stump
										detail_entity=CopyEntity(fr\DetailMesh[3])
										;EntityType detail_entity,HIT_MAP
										tempf2=Rnd(0.1,0.12)
										ScaleEntity detail_entity,tempf2,tempf2,tempf2,True
										
										PositionEntity detail_entity,lx*tempf4-(tempf3/2.0),ColorRed()*0.03-1.3,ly*tempf4-(tempf3/2.0),True
								End Select
								
								EntityFX detail_entity, 1
								;PositionEntity detail_entity,Rnd(0.0,tempf3)-(tempf3/2.0),ColorRed()*0.03-0.05,Rnd(0.0,tempf3)-(tempf3/2.0),True
								EntityParent detail_entity,tile_entity
							EndIf
						Next
					Next
					SetBuffer BackBuffer()
					
					TurnEntity tile_entity, 0, angle, 0
					
					PositionEntity tile_entity,x+(tx*tile_size),y,z+(ty*tile_size),True
					
					ScaleEntity tile_entity,tempf1,tempf1,tempf1
					EntityType tile_entity,HIT_MAP
					EntityFX tile_entity,1
					EntityParent tile_entity,fr\Forest_Pivot
					EntityPickMode tile_entity,2
					
					If it<>Null Then EntityParent it\collider,0
					
					fr\TileEntities[tx+(ty*gridsize)] = tile_entity
				Else
					DebugLog "INVALID TILE @ ("+tx+", "+ty+ "): "+tile_type
				EndIf
			EndIf
			
		Next
	Next
	
	;place the wall		
	For i = 0 To 1
		ty = ((gridsize-1)*i)
		
		For tx = 0 To gridsize-1
			If fr\grid[(ty*gridsize)+tx]=3 Then
				fr\DetailEntities[i]=CopyEntity(fr\DetailMesh[4])
				ScaleEntity fr\DetailEntities[i],RoomScale,RoomScale,RoomScale
				
				fr\Door[i] = CopyEntity(r\Objects[3])
				PositionEntity fr\Door[i],72*RoomScale,32.0*RoomScale,0,True
				RotateEntity fr\Door[i], 0,180,0
				ScaleEntity fr\Door[i],48*RoomScale,45*RoomScale,48*RoomScale,True
				EntityParent fr\Door[i],fr\DetailEntities[i]
				;SetAnimTime fr\Door[i], 0
				
				frame = CopyEntity(r\Objects[2],fr\Door[i])
				PositionEntity frame,0,32.0*RoomScale,0,True
				ScaleEntity frame,48*RoomScale,45*RoomScale,48*RoomScale,True
				EntityParent frame,fr\DetailEntities[i]
				
				EntityType fr\DetailEntities[i],HIT_MAP
				;EntityParent frame,fr\DetailEntities[i]
				EntityPickMode fr\DetailEntities[i],2
				
				PositionEntity fr\DetailEntities[i],x+(tx*tile_size),y,z+(ty*tile_size)+(tile_size/2)-(tile_size*i),True
				RotateEntity fr\DetailEntities[i],0,180*i,0
				
				EntityParent fr\DetailEntities[i],fr\Forest_Pivot
			EndIf		
		Next		
	Next
	
	For i = ROOM1 To ROOM4
		FreeImage(hmap[i])
		FreeTexture(mask[i])
	Next
	
	;Catch("PlaceForest")
End Function

Function PlaceForest_MapCreator(fr.Forest,x#,y#,z#,r.Rooms)
	;Catch("Uncaught (PlaceForest_MapCreator)")
	;local variables
	Local tx%,ty%
	Local tile_size#=12.0
	Local tile_type%
	Local tile_entity%,detail_entity%
	
	Local tempf1#,tempf2#,tempf3#
	Local i%
	
	If fr\Forest_Pivot<>0 Then FreeEntity fr\Forest_Pivot : fr\Forest_Pivot=0
	For i%=ROOM1 To ROOM4
		If fr\TileMesh[i]<>0 Then FreeEntity fr\TileMesh[i] : fr\TileMesh[i]=0
	Next
	For i%=0 To 4
		If fr\DetailMesh[i]<>0 Then FreeEntity fr\DetailMesh[i] : fr\DetailMesh[i]=0
	Next
	
	fr\Forest_Pivot=CreatePivot()
	PositionEntity fr\Forest_Pivot,x,y,z,True
	
	;load assets
	
	Local hmap[ROOM4], mask[ROOM4]
	Local GroundTexture = LoadTexture_Strict("GFX\map\forest\forestfloor.jpg")
	;TextureBlend GroundTexture, FE_ALPHACURRENT
	Local PathTexture = LoadTexture_Strict("GFX\map\forest\forestpath.jpg")
	;TextureBlend PathTexture, FE_ALPHACURRENT
	
	hmap[ROOM1]=LoadImage("GFX\map\forest\forest1h.png")
	mask[ROOM1]=LoadTexture("GFX\map\forest\forest1h_mask.png",1+2)
	
	hmap[ROOM2]=LoadImage("GFX\map\forest\forest2h.png")
	mask[ROOM2]=LoadTexture("GFX\map\forest\forest2h_mask.png",1+2)
	
	hmap[ROOM2C]=LoadImage("GFX\map\forest\forest2Ch.png")
	mask[ROOM2C]=LoadTexture("GFX\map\forest\forest2Ch_mask.png",1+2)
	
	hmap[ROOM3]=LoadImage("GFX\map\forest\forest3h.png")
	mask[ROOM3]=LoadTexture("GFX\map\forest\forest3h_mask.png",1+2)
	
	hmap[ROOM4]=LoadImage("GFX\map\forest\forest4h.png")
	mask[ROOM4]=LoadTexture("GFX\map\forest\forest4h_mask.png",1+2)
	
	For i = ROOM1 To ROOM4
		;TextureBlend mask[i], FE_ALPHAMODULATE
		
		fr\TileMesh[i]=load_terrain(hmap[i],0.03,GroundTexture,PathTexture,mask[i])
	Next
	FreeTexture GroundTexture
	FreeTexture PathTexture
	;detail meshes
	;fr\DetailMesh[0]=LoadMesh_strict("GFX\map\forest\detail\860_1_tree1.b3d")
	;fr\DetailMesh[1]=LoadMesh_strict("GFX\map\forest\detail\860_1_tree1_leaves.b3d")
	fr\DetailMesh[0]=LoadMesh_Strict("GFX\map\forest\detail\treetest4.b3d");1.b3d)
	;EntityParent fr\DetailMesh[1],fr\DetailMesh[0]
	fr\DetailMesh[1]=LoadMesh_Strict("GFX\map\forest\detail\rock.b3d")
	fr\DetailMesh[2]=LoadMesh_Strict("GFX\map\forest\detail\rock2.b3d")
	fr\DetailMesh[3]=LoadMesh_Strict("GFX\map\forest\detail\treetest5.b3d")
	fr\DetailMesh[4]=LoadMesh_Strict("GFX\map\forest\wall.b3d")
	
	For i%=ROOM1 To ROOM4
		HideEntity fr\TileMesh[i]
	Next
	For i%=0 To 4
		HideEntity fr\DetailMesh[i]
	Next
	
	tempf3=MeshWidth(fr\TileMesh[ROOM1])
	tempf1=tile_size/tempf3
	
	DebugLog "ForestINIT"
	
	For tx%=0 To gridsize-1
		For ty%=0 To gridsize-1
			If fr\grid[(ty*gridsize)+tx]>0 Then 
				
				tile_type = 0
				Local angle%=0
				
				tile_type = Ceil(Float(fr\grid[(ty*gridsize)+tx])/4.0)
				If tile_type = 6 Then
					tile_type = 2
				EndIf
				angle = (fr\grid[(ty*gridsize)+tx] Mod 4)*90
				
				tile_entity = CopyEntity(fr\TileMesh[tile_type])
				
				DebugLog "Tile: "+tile_type+"| Angle: "+angle
				
				If tile_type > 0 Then 
					
					Local itemPlaced[4]
					;2, 5, 8
					Local it.Items = Null
					If (ty Mod 3)=2 And itemPlaced[Floor(ty/3)]=False Then
						itemPlaced[Floor(ty/3)]=True
						it.Items = CreateItem("Log #"+Int(Floor(ty/3)+1), "paper", 0,0.5,0)
						
						EntityParent(it\collider, tile_entity)
					EndIf
					
					;place trees and other details
					;only placed on spots where the value of the heightmap is above 100
					SetBuffer ImageBuffer(hmap[tile_type])
					width = ImageWidth(hmap[tile_type])
					tempf4# = (tempf3/Float(width))
					For lx = 3 To width-2
						For ly = 3 To width-2
							GetColor lx,width-ly
							
							If ColorRed()>Rand(100,260) Then
								detail_entity = 0
								Select Rand(0,7)
									Case 0,1,2,3,4,5,6 ;create a tree
										detail_entity=CopyEntity(fr\DetailMesh[0])
										;EntityType detail_entity,HIT_MAP
										tempf2=Rnd(0.25,0.4)
										
										For i = 0 To 3
											d=CopyEntity(fr\DetailMesh[3])
											;ScaleEntity d,tempf2*1.1,tempf2,tempf2*1.1,True
											RotateEntity d, 0, 90*i+Rnd(-20,20), 0
											EntityParent(d,detail_entity)
											
											EntityFX d, 1;+8
										Next
										
										ScaleEntity detail_entity,tempf2*1.1,tempf2,tempf2*1.1,True
										PositionEntity detail_entity,lx*tempf4-(tempf3/2.0),ColorRed()*0.03-Rnd(3.0,3.2),ly*tempf4-(tempf3/2.0),True
										
										RotateEntity detail_entity,Rnd(-5,5),Rnd(360.0),0.0,True
										
										;EntityAutoFade(detail_entity,4.0,6.0)
									Case 7 ;add a rock
										detail_entity=CopyEntity(fr\DetailMesh[1])
										;EntityType detail_entity,HIT_MAP
										tempf2=Rnd(0.01,0.012)
										;ScaleEntity detail_entity,tempf2,tempf2*Rnd(1.0,2.0),tempf2,True
										
										PositionEntity detail_entity,lx*tempf4-(tempf3/2.0),ColorRed()*0.03-1.3,ly*tempf4-(tempf3/2.0),True
										
										EntityFX detail_entity, 1
										
										RotateEntity detail_entity,0.0,Rnd(360.0),0.0,True
									Case 6 ;add a stump
										detail_entity=CopyEntity(fr\DetailMesh[3])
										;EntityType detail_entity,HIT_MAP
										tempf2=Rnd(0.1,0.12)
										ScaleEntity detail_entity,tempf2,tempf2,tempf2,True
										
										PositionEntity detail_entity,lx*tempf4-(tempf3/2.0),ColorRed()*0.03-1.3,ly*tempf4-(tempf3/2.0),True
								End Select
								
								If detail_entity <> 0 Then
									EntityFX detail_entity, 1
									;PositionEntity detail_entity,Rnd(0.0,tempf3)-(tempf3/2.0),ColorRed()*0.03-0.05,Rnd(0.0,tempf3)-(tempf3/2.0),True
									EntityParent detail_entity,tile_entity
								EndIf
							EndIf
						Next
					Next
					SetBuffer BackBuffer()
					
					TurnEntity tile_entity, 0, angle, 0
					
					PositionEntity tile_entity,x+(tx*tile_size),y,z+(ty*tile_size),True
					
					DebugLog "tile_entity: "+(x+(tx*tile_size))+"|"+(y)+"|"+(z+(ty*tile_size))
					
					ScaleEntity tile_entity,tempf1,tempf1,tempf1
					EntityType tile_entity,HIT_MAP
					EntityFX tile_entity,1
					EntityParent tile_entity,fr\Forest_Pivot
					EntityPickMode tile_entity,2
					
					If it<>Null Then EntityParent it\collider,0
					
					fr\TileEntities[tx+(ty*gridsize)] = tile_entity
				Else
					DebugLog "INVALID TILE @ ("+tx+", "+ty+ "): "+tile_type
				EndIf
				
				If Ceil(fr\grid[(ty*gridsize)+tx]/4.0)=6 Then
					For i = 0 To 1
						If fr\Door[i]=0 Then
							fr\DetailEntities[i]=CopyEntity(fr\DetailMesh[4])
							ScaleEntity fr\DetailEntities[i],RoomScale,RoomScale,RoomScale
							
							fr\Door[i] = CopyEntity(r\Objects[3])
							PositionEntity fr\Door[i],72*RoomScale,32.0*RoomScale,0,True
							RotateEntity fr\Door[i], 0,180,0
							ScaleEntity fr\Door[i],48*RoomScale,45*RoomScale,48*RoomScale,True
							EntityParent fr\Door[i],fr\DetailEntities[i]
							
							Local frame = CopyEntity(r\Objects[2],fr\Door[i])
							PositionEntity frame,0,32.0*RoomScale,0,True
							ScaleEntity frame,48*RoomScale,45*RoomScale,48*RoomScale,True
							EntityParent frame,fr\DetailEntities[i]
							
							EntityType fr\DetailEntities[i],HIT_MAP
							EntityPickMode fr\DetailEntities[i],2
							
							PositionEntity fr\DetailEntities[i],x+(tx*tile_size),y,z+(ty*tile_size),True
							RotateEntity fr\DetailEntities[i],0,angle+180,0
							MoveEntity fr\DetailEntities[i],0,0,-6
							
							EntityParent fr\DetailEntities[i],fr\Forest_Pivot
							Exit
						EndIf
					Next
				EndIf
			Else
				DebugLog "NO TILE FOUND @ ("+tx+", "+ty+ ")"
			EndIf
		Next
	Next
	
	For i = ROOM1 To ROOM4
		FreeImage(hmap[i])
		FreeTexture(mask[i])
	Next
	
	DebugLog "ForestINIT END"
	
	;Catch("PlaceForest_MapCreator")
End Function

Function DestroyForest(fr.Forest)
	;Catch("Uncaught (DestroyForest)")
	Local tx%,ty%
	For tx% = 0 To gridsize-1
		For ty% = 0 To gridsize-1
			If fr\TileEntities[tx+(ty*gridsize)]<>0 Then
				FreeEntity fr\TileEntities[tx+(ty*gridsize)]
				fr\TileEntities[tx+(ty*gridsize)] = 0
				fr\grid[tx+(ty*gridsize)] = 0
			EndIf
		Next
	Next
	If fr\Door[0]<>0 Then FreeEntity fr\Door[0] : fr\Door[0] = 0
	If fr\Door[1]<>0 Then FreeEntity fr\Door[1] : fr\Door[0] = 1
	If fr\DetailEntities[0]<>0 Then FreeEntity fr\DetailEntities[0] : fr\DetailEntities[0] = 0
	If fr\DetailEntities[1]<>0 Then FreeEntity fr\DetailEntities[1] : fr\DetailEntities[1] = 0
	
	If fr\Forest_Pivot<>0 Then FreeEntity fr\Forest_Pivot : fr\Forest_Pivot=0
	For i%=ROOM1 To ROOM4
		If fr\TileMesh[i]<>0 Then FreeEntity fr\TileMesh[i] : fr\TileMesh[i]=0
	Next
	For i%=0 To 4
		If fr\DetailMesh[i]<>0 Then FreeEntity fr\DetailMesh[i] : fr\DetailMesh[i]=0
	Next
	
	;Catch("DestroyForest")
	;Delete fr
End Function


Function UpdateForest(fr.Forest,ent%)
	;Catch("Uncaught (UpdateForest)")
	;local variables
	Local tx%,ty%
	If Abs(EntityY(ent,True)-EntityY(fr\Forest_Pivot,True))<12.0 Then
		For tx% = 0 To gridsize-1
			For ty% = 0 To gridsize-1
				If fr\TileEntities[tx+(ty*gridsize)]<>0 Then
					If Abs(EntityX(ent,True)-EntityX(fr\TileEntities[tx+(ty*gridsize)],True))<20.0 Then
						If Abs(EntityZ(ent,True)-EntityZ(fr\TileEntities[tx+(ty*gridsize)],True))<20.0 Then
							ShowEntity fr\TileEntities[tx+(ty*gridsize)]
							ShowEntity fr.Forest\Forest_Pivot
						EndIf
					EndIf
				EndIf
			Next
		Next
	EndIf
	;Catch("UpdateForest")
End Function

Function HideForest(fr.Forest)
	For tx% = 0 To gridsize-1
		For ty% = 0 To gridsize-1
			If fr\TileEntities[tx+(ty*gridsize)]<>0 Then
				HideEntity fr\TileEntities[tx+(ty*gridsize)]
			EndIf
		Next
	Next
End Function

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Const MaxRoomLights% = 32
Const MaxRoomEmitters% = 8
Const MaxRoomObjects% = 30


Const ROOM1% = 1, ROOM2% = 2, ROOM2C% = 3, ROOM3% = 4, ROOM4% = 5

Global RoomTempID%
Type RoomTemplates
	Field obj%, id%
	Field objPath$
	
	Field zone%[5]
	
	;Field ambience%
	
	Field TempSoundEmitter%[MaxRoomEmitters]
	Field TempSoundEmitterX#[MaxRoomEmitters],TempSoundEmitterY#[MaxRoomEmitters],TempSoundEmitterZ#[MaxRoomEmitters]
	Field TempSoundEmitterRange#[MaxRoomEmitters]
	
	Field Shape%, Name$
	Field Commonness%, Large%
	Field DisableDecals%
	
	Field TempTriggerboxAmount
	Field TempTriggerbox[128]
	Field TempTriggerboxName$[128]
	
	Field UseLightCones%
	
	Field DisableOverlapCheck% = True
	
	Field MinX#, MinY#, MinZ#
	Field MaxX#, MaxY#, MaxZ#
	
End Type 	

Function CreateRoomTemplate.RoomTemplates(meshpath$)
	Local rt.RoomTemplates = New RoomTemplates
	
	rt\objPath = meshpath
	
	rt\id = RoomTempID
	RoomTempID=RoomTempID+1
	
	Return rt
End Function

Function LoadRoomTemplates(file$)
	;Catch("Uncaught (LoadRoomTemplates)")
	Local TemporaryString$, i%
	Local rt.RoomTemplates = Null
	Local StrTemp$ = ""
	
	Local f = ReadFile(file)
	If f = 0 Then RuntimeError("File "+file+" doesn't exist")
	
	While Not Eof(f)
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString,1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			StrTemp = GetINIString(file, TemporaryString, "mesh path")
			
			rt = CreateRoomTemplate(StrTemp)
			rt\Name = Lower(TemporaryString)
			
			StrTemp = Lower(GetINIString(file, TemporaryString, "shape"))
			
			Select StrTemp
				Case "room1", "1"
					rt\Shape = ROOM1
				Case "room2", "2"
					rt\Shape = ROOM2
				Case "room2c", "2c"
					rt\Shape = ROOM2C
				Case "room3", "3"
					rt\Shape = ROOM3
				Case "room4", "4"
					rt\Shape = ROOM4
				Default
			End Select
			
			For i = 0 To 4
				rt\zone[i]= GetINIInt(file, TemporaryString, "zone"+(i+1))
			Next
			
			rt\Commonness = Max(Min(GetINIInt(file, TemporaryString, "commonness"), 100), 0)
			rt\Large = GetINIInt(file, TemporaryString, "large")
			rt\DisableDecals = GetINIInt(file, TemporaryString, "disabledecals")
			rt\UseLightCones = GetINIInt(file, TemporaryString, "usevolumelighting")
			rt\DisableOverlapCheck = GetINIInt(file, TemporaryString, "disableoverlapcheck")
		EndIf
	Wend
	
	i = 1
	Repeat
		StrTemp = GetINIString(file, "room ambience", "ambience"+i)
		If StrTemp = "" Then Exit
		
		RoomAmbience[i]=LoadSound_Strict(StrTemp)
		i=i+1
	Forever
	
	CloseFile f
	
	;Catch("LoadRoomTemplates")
End Function


Function LoadRoomMesh(rt.RoomTemplates)
	
	If Instr(rt\objPath,".rmesh")<>0 Then ;file is roommesh
		rt\obj = LoadRMesh(rt\objPath, rt)
	Else ;file is b3d
		If rt\objPath <> "" Then rt\obj = LoadWorld(rt\objPath, rt) Else rt\obj = CreatePivot()
	EndIf
	
	If (Not rt\obj) Then RuntimeError "Failed to load map file "+Chr(34)+mapfile+Chr(34)+"."
	
	CalculateRoomTemplateExtents(rt)
	
	HideEntity(rt\obj)
	
End Function

Function LoadRoomMeshes()
	Local temp% = 0
	For rt.RoomTemplates = Each RoomTemplates
		temp=temp+1
	Next	
	
	Local i = 0
	For rt.RoomTemplates = Each RoomTemplates
		If Instr(rt\objpath,".rmesh")<>0 Then ;file is roommesh
			rt\obj = LoadRMesh(rt\objPath, rt)
		Else ;file is b3d
			If rt\objpath <> "" Then rt\obj = LoadWorld(rt\objPath, rt) Else rt\obj = CreatePivot()
		EndIf
		If (Not rt\obj) Then RuntimeError "Failed to load map file "+Chr(34)+mapfile+Chr(34)+"."
		
		HideEntity(rt\obj)
		;drawloading(Int(30 + (15.0 / temp)*i))
		i=i+1
	Next
End Function


LoadRoomTemplates("Data\rooms.ini")

Global RoomScale# = 8.0 / 2048.0
Const ZONEAMOUNT = 3
Global MapWidth% = 18, MapHeight = MapWidth
Dim MapTemp%(MapWidth+1, MapHeight+1)
Dim MapFound%(MapWidth+1, MapHeight+1)

Global RoomAmbience%[20]

Global Sky

Global HideDistance# = 15.0

Global SecondaryLightOn# = True
Global PrevSecondaryLightOn# = True
Global RemoteDoorOn = True
Global Contained106 = False

Type Rooms
	Field zone%
	
	Field found%
	
	Field obj%
	Field x#, y#, z#
	Field angle%
	Field RoomTemplate.RoomTemplates
	Field RoomTriggerBox
	Field dist#
	
	Field SoundCHN%
	
	Field fr.Forest
	
	Field SoundEmitter%[MaxRoomEmitters]
	Field SoundEmitterObj%[MaxRoomEmitters]
	Field SoundEmitterRange#[MaxRoomEmitters]
	Field SoundEmitterCHN%[MaxRoomEmitters]
	
	Field Lights%[MaxRoomLights]
	Field LightIntensity#[MaxRoomLights]
	Field MaxLights% = 0
	Field LightSpriteHidden%[MaxRoomLights]
	Field LightSpritesPivot%[MaxRoomLights]
	Field LightSprites2%[MaxRoomLights]
	Field LightHidden%[MaxRoomLights]
	Field LightFlicker%[MaxRoomLights]
	
	Field LightSprites%[MaxRoomLights]	
	
	Field Objects%[MaxRoomObjects]
	Field ObjectStatic%[MaxRoomObjects]
	Field RoomTextures%[MaxRoomObjects]
	
	Field Levers%[11]
	Field RoomDoors.Doors[8]
	Field NPC.NPCs[12]
	Field grid.Grids
	
	Field Adjacent.Rooms[4]
	Field AdjDoor.Doors[4]
	
	Field NonFreeAble%[10]
	Field Textures%[10]
	
	Field AlarmRotor%[1]
	Field AlarmRotorLight%[1]
	Field TriggerboxAmount
	Field Triggerbox[16]
	Field TriggerboxName$[16]
	Field TriggerBox_Mesh_MinX#[16]
	Field TriggerBox_Mesh_MinY#[16]
	Field TriggerBox_Mesh_MinZ#[16]
	Field TriggerBox_Mesh_MaxX#[16]
	Field TriggerBox_Mesh_MaxY#[16]
	Field TriggerBox_Mesh_MaxZ#[16]
	
	Field TriggerBox_sx#[16]
	Field TriggerBox_sy#[16]
	Field TriggerBox_sz#[16]
	
	Field MaxWayPointY#
	Field LightR#[MaxRoomLights],LightG#[MaxRoomLights],LightB#[MaxRoomLights]
	Field LightCone%[MaxRoomLights]
	Field LightConeSpark%[MaxRoomLights]
	Field LightConeSparkTimer#[MaxRoomLights]
	
	Field MinX#, MinY#, MinZ#
	Field MaxX#, MaxY#, MaxZ#
	Field MagX#, MagY#, MagZ#
	
	Field DistanceX#, DistanceY#, DistanceZ#
	Field ID, IsAnyInRoom%, NearObject
	Field EventID
	Field IsRoomShowed%
End Type 
Const gridsz%=19 ;Same size as the main map itself (better for the map creator)
Global Room.Rooms[MAX_ROOMS]

Const Default_Door% = 0
Const Big_Door% = 1
Const Heavy_Door% = 2
Const Elevator_Door% = 3
Const Office_Door% = 4
Const One_Sided_Door% = 5
Const SCP_914_Door% = 6

Type Grids
	Field grid%[gridsz*gridsz]
	Field angles%[gridsz*gridsz]
	Field Meshes%[7]
	Field Entities%[gridsz*gridsz]
	Field waypoints.WayPoints[gridsz*gridsz]
End Type

Function UpdateGrid(grid.Grids)
	;local variables
	Local tx%,ty%
	For tx% = 0 To gridsz-1
		For ty% = 0 To gridsz-1
			If grid\Entities[tx+(ty*gridsz)]<>0 Then
				If Abs(EntityY(Collider,True)-EntityY(grid\Entities[tx+(ty*gridsz)],True))>4.0 Then Exit
				If Abs(EntityX(Collider,True)-EntityX(grid\Entities[tx+(ty*gridsz)],True))<HideDistance Then
					If Abs(EntityZ(Collider,True)-EntityZ(grid\Entities[tx+(ty*gridsz)],True))<HideDistance Then
						ShowEntity grid\Entities[tx+(ty*gridsz)]
					Else
						HideEntity grid\Entities[tx+(ty*gridsz)]
					EndIf
				Else
					HideEntity grid\Entities[tx+(ty*gridsz)]
				EndIf
			EndIf
		Next
	Next
End Function

Function PlaceGrid_MapCreator(r.Rooms)
	Local x,y,i
	Local Meshes[7]
	Local dr.Doors,it.Items
	
	For i=0 To 6
		Meshes[i]=CopyEntity(OBJTunnel(i))
		DebugLog i
		HideEntity Meshes[i]
	Next
	
	For y = 0 To (gridsz-1)
		For x = 0 To (gridsz-1)
			If r\grid\grid[x+(y*gridsz)]>0 Then
				Local tile_type = 0
				Local angle%=0
				
				tile_type = r\grid\grid[x+(y*gridsz)]
				angle = r\grid\angles[x+(y*gridsz)]*90.0
				
				Local tile_entity = CopyEntity(Meshes[tile_type-1])
				RotateEntity tile_entity,0,angle,0
				ScaleEntity tile_entity,RoomScale,RoomScale,RoomScale,True
				PositionEntity tile_entity,r\x+x*2.0,8.0,r\z+y*2.0,True
				
				Select r\grid\grid[x+(y*gridsz)]
					Case ROOM1
						AddLight%(Null, r\x+x*2.0, 8.0+(368.0*RoomScale), r\z+y*2.0, 2, 500.0 * RoomScale, 255, 255, 255)
					Case ROOM2,ROOM2C
						AddLight%(Null, r\x+x*2.0, 8.0+(368.0*RoomScale), r\z+y*2.0, 2, 500.0 * RoomScale, 255, 255, 255)
					Case ROOM2C
						AddLight%(Null, r\x+x*2.0, 8.0+(412.0*RoomScale), r\z+y*2.0, 2, 500.0 * RoomScale, 255, 255, 255)
					Case ROOM3,ROOM4
						AddLight%(Null,r\x+x*2.0, 8.0+(412.0*RoomScale), r\z+y*2.0, 2, 500.0 * RoomScale, 255, 255, 255)
					Case ROOM4+1
						dr=CreateDoor(r\zone,r\x+(x*2.0)+(Cos(EntityYaw(tile_entity,True))*240.0*RoomScale),8.0,r\z+(y*2.0)+(Sin(EntityYaw(tile_entity,True))*240.0*RoomScale),EntityYaw(tile_entity,True)+90.0,Null,False,3,False,"")
						PositionEntity dr\buttons[0],EntityX(dr\buttons[0],True)+(Cos(EntityYaw(tile_entity,True))*0.05),EntityY(dr\buttons[0],True)+0.0,EntityZ(dr\buttons[0],True)+(Sin(EntityYaw(tile_entity,True))*0.05),True
						
						AddLight%(Null, r\x+x*2.0+(Cos(EntityYaw(tile_entity,True))*555.0*RoomScale), 8.0+(469.0*RoomScale), r\z+y*2.0+(Sin(EntityYaw(tile_entity,True))*555.0*RoomScale), 2, 600.0 * RoomScale, 255, 255, 255)
						
						Local tempInt2=CreatePivot()
						RotateEntity tempInt2,0,EntityYaw(tile_entity,True)+180.0,0,True
						PositionEntity tempInt2,r\x+(x*2.0)+(Cos(EntityYaw(tile_entity,True))*552.0*RoomScale),8.0+(240.0*RoomScale),r\z+(y*2.0)+(Sin(EntityYaw(tile_entity,True))*552.0*RoomScale)
						If r\RoomDoors[1]=Null Then
							r\RoomDoors[1]=dr
							r\Objects[3]=tempInt2
							PositionEntity r\Objects[0],r\x+x*2.0,8.0,r\z+y*2.0,True
							DebugLog "Created door 1 successfully!"
						ElseIf r\RoomDoors[1]<>Null And r\RoomDoors[3]=Null Then
							r\RoomDoors[3]=dr
							r\Objects[5]=tempInt2
							PositionEntity r\Objects[1],r\x+x*2.0,8.0,r\z+y*2.0,True
							DebugLog "Created door 2 successfully!"
						EndIf
					Case ROOM4+2
						AddLight%(Null, r\x+x*2.0-(Sin(EntityYaw(tile_entity,True))*504.0*RoomScale)+(Cos(EntityYaw(tile_entity,True))*16.0*RoomScale), 8.0+(396.0*RoomScale), r\z+y*2.0+(Cos(EntityYaw(tile_entity,True))*504.0*RoomScale)+(Sin(EntityYaw(tile_entity,True))*16.0*RoomScale), 2, 500.0 * RoomScale, 255, 200, 200)
						it = CreateItem("SCP-500-01","scp500",r\x+x*2.0+(Cos(EntityYaw(tile_entity,True))*(-208.0)*RoomScale)-(Sin(EntityYaw(tile_entity,True))*1226.0*RoomScale),8.0+(80.0*RoomScale),r\z+y*2.0+(Sin(EntityYaw(tile_entity,True))*(-208.0)*RoomScale)+(Cos(EntityYaw(tile_entity,True))*1226.0*RoomScale))
						
						
						it = CreateItem("Night Vision Goggles", "nvgoggles",r\x+x*2.0-(Sin(EntityYaw(tile_entity,True))*504.0*RoomScale)+(Cos(EntityYaw(tile_entity,True))*16.0*RoomScale), 8.0+(80.0*RoomScale), r\z+y*2.0+(Cos(EntityYaw(tile_entity,True))*504.0*RoomScale)+(Sin(EntityYaw(tile_entity,True))*16.0*RoomScale))
						
				End Select
				
				r\grid\Entities[x+(y*gridsz)]=tile_entity
				wayp.WayPoints = CreateWaypoint(r\x+(x*2.0),8.2,r\z+(y*2.0),Null,r)
				r\grid\waypoints[x+(y*gridsz)]=wayp
				
				If y<gridsz-1 Then
					If r\grid\waypoints[x+((y+1)*gridsz)]<>Null Then
						dist=EntityDistance(r\grid\waypoints[x+(y*gridsz)]\obj,r\grid\waypoints[x+((y+1)*gridsz)]\obj)
						For i=0 To 3
							If r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x+((y+1)*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x+((y+1)*gridsz)]
								r\grid\waypoints[x+(y*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
						For i=0 To 3
							If r\grid\waypoints[x+((y+1)*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+((y+1)*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+((y+1)*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)]
								r\grid\waypoints[x+((y+1)*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
				If y>0 Then
					If r\grid\waypoints[x+((y-1)*gridsz)]<>Null Then
						dist=EntityDistance(r\grid\waypoints[x+(y*gridsz)]\obj,r\grid\waypoints[x+((y-1)*gridsz)]\obj)
						For i=0 To 3
							If r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x+((y-1)*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x+((y-1)*gridsz)]
								r\grid\waypoints[x+(y*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
						For i=0 To 3
							If r\grid\waypoints[x+((y-1)*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+((y-1)*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)]
								r\grid\waypoints[x+((y-1)*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
				If x>0 Then
					If r\grid\waypoints[x-1+(y*gridsz)]<>Null Then
						dist=EntityDistance(r\grid\waypoints[x+(y*gridsz)]\obj,r\grid\waypoints[x-1+(y*gridsz)]\obj)
						For i=0 To 3
							If r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x-1+(y*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x-1+(y*gridsz)]
								r\grid\waypoints[x+(y*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
						For i=0 To 3
							If r\grid\waypoints[x-1+(y*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x-1+(y*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)]
								r\grid\waypoints[x-1+(y*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
				If x<gridsz-1 Then
					If r\grid\waypoints[x+1+(y*gridsz)]<>Null Then
						dist=EntityDistance(r\grid\waypoints[x+(y*gridsz)]\obj,r\grid\waypoints[x+1+(y*gridsz)]\obj)
						For i=0 To 3
							If r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x+1+(y*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+(y*gridsz)]\connected[i]=r\grid\waypoints[x+1+(y*gridsz)]
								r\grid\waypoints[x+(y*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
						For i=0 To 3
							If r\grid\waypoints[x+1+(y*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)] Then
								Exit
							ElseIf r\grid\waypoints[x+(y*gridsz)]\connected[i]=Null Then
								r\grid\waypoints[x+1+(y*gridsz)]\connected[i]=r\grid\waypoints[x+(y*gridsz)]
								r\grid\waypoints[x+1+(y*gridsz)]\dist[i]=dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
			EndIf
		Next
	Next
	
	For i=0 To 6
		r\grid\Meshes[i]=Meshes[i]
	Next
	
End Function
Function FindFreeRoomID()
	For i = 1 To MAX_ROOMS-1
		If Room[i] = Null Then Return i
	Next
End Function
Function CreateRoom.Rooms(zone%, roomshape%, x#, y#, z#, name$ = "")
	;Catch("Uncaught (CreateRoom)")
	Local r.Rooms = New Rooms
	Local rt.RoomTemplates
	
	r\MaxLights% = -1
	r\zone = zone
	r\ID = FindFreeRoomID()
	Room[r\ID] = r
	r\x = x : r\y = y : r\z = z
	If name <> "" Then
		name = Lower(name)
		For rt.RoomTemplates = Each RoomTemplates
			If rt\Name = name Then
				r\RoomTemplate = rt
				
				If rt\obj=0 Then LoadRoomMesh(rt)
				
				r\obj = CopyEntity(rt\obj)
				ScaleEntity(r\obj, RoomScale, RoomScale, RoomScale)
				EntityType(r\obj, HIT_MAP)
				EntityPickMode(r\obj, 2)
				LoadRoomProps(r)
				
				PositionEntity(r\obj, x, y, z)
				FillRoom(r)
				
				CalculateRoomExtents(r)
				Return r
			EndIf
		Next
	EndIf
	
	Local temp% = 0
	For rt.RoomTemplates = Each RoomTemplates
		
		For i = 0 To 4
			If rt\zone[i]=zone Then 
				If rt\Shape = roomshape Then temp=temp+rt\Commonness : Exit
			EndIf
		Next
		
	Next
	
	Local RandomRoom% = Rand(temp)
	temp = 0
	For rt.RoomTemplates = Each RoomTemplates
		For i = 0 To 4
			If rt\zone[i]=zone And rt\Shape = roomshape Then
				temp=temp+rt\Commonness
				If RandomRoom > temp - rt\Commonness And RandomRoom <= temp Then
					r\RoomTemplate = rt
					
					If rt\obj=0 Then LoadRoomMesh(rt)
					
					r\obj = CopyEntity(rt\obj)
					ScaleEntity(r\obj, RoomScale, RoomScale, RoomScale)
					EntityType(r\obj, HIT_MAP)
					EntityPickMode(r\obj, 2)
					LoadRoomProps(r)
					
					PositionEntity(r\obj, x, y, z)
					FillRoom(r)
					
					CalculateRoomExtents(r)
					Return r	
				End If
			EndIf
		Next
	Next
	
	;Catch("CreateRoom")
End Function

Global allowroomdoorsinit.Rooms

Function FillRoom(r.Rooms)
	;Catch("Uncaught (FillRoom)")
	Local d.Doors, d2.Doors, sc.SecurityCams, de.Decals, r2.Rooms, sc2.SecurityCams
	Local it.Items, i%
	Local xtemp%, ytemp%, ztemp%
	
	Local t1;, Bump	
	allowroomdoorsinit = r
	
	If GetScripts() Then
		public_inqueue(public_OnFillingRoom)
		public_addparam(0, r\ID, SE_INT)
		public_addparam(0, r\x, SE_FLOAT)
		public_addparam(0, r\y, SE_FLOAT)
		public_addparam(0, r\z, SE_FLOAT)
		public_addparam(0, RoomScale, SE_FLOAT)
		callback
	EndIf
	
	Select r\RoomTemplate\Name
		Case "room860"
			;[Block]
			;the wooden door
			r\Objects[2] = LoadMesh_Strict("GFX\map\forest\door_frame.b3d")
			PositionEntity r\Objects[2],r\x + 184.0 * RoomScale,0,r\z,True
			ScaleEntity r\Objects[2],45.0*RoomScale,45.0*RoomScale,80.0*RoomScale,True
			EntityParent r\Objects[2],r\obj
			
			r\Objects[3] =  LoadMesh_Strict("GFX\map\forest\door.b3d")
			PositionEntity r\Objects[3],r\x + 112.0 * RoomScale,0,r\z+0.05,True
			EntityType r\Objects[3], HIT_MAP
			
			ScaleEntity r\Objects[3],46.0*RoomScale,45.0*RoomScale,46.0*RoomScale,True
			EntityParent r\Objects[3],r\obj
			
			r\Objects[4] = CopyEntity(r\Objects[3])
			PositionEntity r\Objects[4],r\x + 256.0 * RoomScale,0,r\z-0.05,True
			RotateEntity r\Objects[4], 0,180,0
			ScaleEntity r\Objects[4],46.0*RoomScale,45.0*RoomScale,46.0*RoomScale,True
			EntityParent r\Objects[4],r\obj
			
;			;DrawPortal stuff
;			Local dp.DrawPortal = CreateDrawPortal(r\x + 184.0 * RoomScale,164.25*RoomScale,r\z,0.0,0.0,0.0,328.5*RoomScale,328.5*RoomScale);,r\x,r\y+5.2,r\z,0.0,0.0,0.0)
;			r\dp=dp
;			EntityParent dp\portal,r\obj
;			
;			CameraClsColor dp\cam,0,0,0
;			CameraRange dp\cam,RoomScale,8.0
;			CameraFogRange dp\cam,0.5,8.0
;			CameraFogColor dp\cam,0,0,0
;			CameraFogMode dp\cam,1
			
			;doors to observation booth
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 928.0 * RoomScale,0,r\z + 640.0 * RoomScale,0,r,Server\Breach,False,False,"ABCD")
			r\RoomDoors[0]\AutoClose = Not Server\Breach
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 928.0 * RoomScale,0,r\z - 640.0 * RoomScale,0,r,True,False,False,"ABCD")
			r\RoomDoors[1]\AutoClose = False
			
			;doors to the room itself
			d = CreateDoor(r\zone, r\x+416.0*RoomScale,0,r\z - 640.0 * RoomScale,0,r,False,False,1)
			d = CreateDoor(r\zone, r\x+416.0*RoomScale,0,r\z + 640.0 * RoomScale,0,r,False,False,1)
			
			;the forest
			If I_Zone\HasCustomForest = False Then
				Local fr.Forest = New Forest
				r\fr=fr
				GenForestGrid(fr)
				PlaceForest(fr,r\x,r\y+30.0,r\z,r)
			EndIf
			;EntityParent fr\Forest_Pivot,r\obj
			
;			PositionEntity dp\cam,EntityX(fr\Door[0],True),r\y+31.0,EntityZ(fr\Door[0],True),True
;			dp\camyaw=EntityYaw(fr\Door[0],True)
;			RotateEntity dp\cam, 0, dp\camyaw, 0, True
;			MoveEntity dp\cam, 0,0,0.5
;			
;			;place the camera at the door
;			For xtemp=0 To -1;gridsize-1
;				If fr\grid[xtemp+((gridsize-1)*gridsize)]=3 Then
;					PositionEntity dp\cam,r\x+(xtemp*8.0),r\y+30.5,r\z+((gridsize-2)*8.0)+0.2,True
;					;make the camera point the right way
;					ytemp=CreatePivot(r\obj)
;					ztemp=CreatePivot()
;					PositionEntity ytemp,EntityX(dp\cam,True),EntityY(dp\cam,True),EntityZ(dp\cam,True),True
;					PositionEntity ztemp,EntityX(dp\cam,True),EntityY(dp\cam,True),EntityZ(dp\cam,True),True
;					TranslateEntity ztemp,0.0,0.0,-10.0,True
;					PointEntity ytemp,ztemp
;					dp\campitch=EntityPitch(ytemp)
;					dp\camyaw=EntityYaw(ytemp)
;					r\Objects[4]=ytemp : ytemp = 0
;					FreeEntity ztemp : ztemp = 0
;				EndIf
;			Next
;			
;			EntityParent dp\cam,fr\Forest_Pivot
			If Server\Breach = False Then
				it = CreateItem("Document SCP-860-1", "paper", r\x + 672.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 335.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle+10, 0
				EntityParent(it\collider, r\obj)
				
				it = CreateItem("Document SCP-860", "paper", r\x + 1152.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 384.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle+170, 0
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x + 672.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 335.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle+10, 0
				EntityParent(it\collider, r\obj)
				
				it = CreateItem("Rocket Launcher", "rpg", r\x + 1152.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 384.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle+170, 0
				EntityParent(it\collider, r\obj)
			EndIf
			;[End Block]
		Case "lockroom"
			;[Block]
			d = CreateDoor(r\zone, r\x - 736.0 * RoomScale, 0, r\z - 104.0 * RoomScale, 0, r, True)
			d\timer = 70 * (5+(5*Server\Breach)) : d\AutoClose = False : d\open = False
			
			EntityParent(d\buttons[0], 0)
			PositionEntity(d\buttons[0], r\x - 288.0 * RoomScale, 0.7, r\z - 640.0 * RoomScale)
			EntityParent(d\buttons[0], r\obj)
			
			FreeEntity(d\buttons[1]) : d\buttons[1] = 0
			
			d2 = CreateDoor(r\zone, r\x + 104.0 * RoomScale, 0, r\z + 736.0 * RoomScale, 270, r, True)
			d2\timer = 70 * (5+(5*Server\Breach)) : d2\AutoClose = False: d2\open = False
			EntityParent(d2\buttons[0], 0)
			PositionEntity(d2\buttons[0], r\x + 640.0 * RoomScale, 0.7, r\z + 288.0 * RoomScale)
			RotateEntity (d2\buttons[0], 0, 90, 0)
			EntityParent(d2\buttons[0], r\obj)
			
			FreeEntity(d2\buttons[1]) : d2\buttons[1] = 0
			
			d\LinkedDoor = d2
			d2\LinkedDoor = d
			
			sc.SecurityCams = CreateSecurityCam(r\x - 688.0 * RoomScale, r\y + 384 * RoomScale, r\z + 688.0 * RoomScale, r, True)
			sc\angle = 45 + 180
			sc\turn = 45
			sc\ScrTexture = 1
			EntityTexture sc\ScrObj, ScreenTexs[sc\ScrTexture]
			
			TurnEntity(sc\CameraObj, 40, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			PositionEntity(sc\ScrObj, r\x + 668 * RoomScale, 1.1, r\z - 96.0 * RoomScale)
			TurnEntity(sc\ScrObj, 0, 90, 0)
			EntityParent(sc\ScrObj, r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 112.0 * RoomScale, r\y + 384 * RoomScale, r\z + 112.0 * RoomScale, r, True)
			sc\angle = 45
			sc\turn = 45
			sc\ScrTexture = 1
			EntityTexture sc\ScrObj, ScreenTexs[sc\ScrTexture]
			
			TurnEntity(sc\CameraObj, 40, 0, 0)
			EntityParent(sc\obj, r\obj)				
			
			PositionEntity(sc\ScrObj, r\x + 96.0 * RoomScale, 1.1, r\z - 668.0 * RoomScale)
			EntityParent(sc\ScrObj, r\obj)
			
			Local em.Emitters = CreateEmitter(r\x - 175.0 * RoomScale, 370.0 * RoomScale, r\z + 656.0 * RoomScale, 0)
			TurnEntity(em\Obj, 90, 0, 0, True)
			EntityParent(em\Obj, r\obj)
			em\RandAngle = 20
			em\Speed = 0.05
			em\SizeChange = 0.007
			em\Achange = -0.006
			em\Gravity = -0.24
			
			em.Emitters = CreateEmitter(r\x - 655.0 * RoomScale, 370.0 * RoomScale, r\z + 240.0 * RoomScale, 0)
			TurnEntity(em\Obj, 90, 0, 0, True)
			EntityParent(em\Obj, r\obj)
			em\RandAngle = 20
			em\Speed = 0.05
			em\SizeChange = 0.007
			em\Achange = -0.006
			em\Gravity = -0.24
			
			;This needs more work
			;dem.DevilEmitters = CreateDevilEmitter(r\x-175.0*RoomScale,r\y+370.0*RoomScale,r\z+656.0*RoomScale,r,2)
			;dem\isDeconGas = True
			;dem.DevilEmitters = CreateDevilEmitter(r\x-655.0*RoomScale,r\y+370.0*RoomScale,r\z+240.0*RoomScale,r,2)
			;dem\isDeconGas = True
			;[End Block]
		Case "lockroom2"
			;[Block]
			For i = 0 To 5
				de.Decals = CreateDecal(Rand(2,3), r\x+Rnd(-392,520)*RoomScale, 3.0*RoomScale+Rnd(0,0.001), r\z+Rnd(-392,520)*RoomScale,90,Rnd(360),0)
				de\Size = Rnd(0.3,0.6)
				ScaleSprite(de\obj, de\Size,de\Size)
				CreateDecal(Rand(15,16), r\x+Rnd(-392,520)*RoomScale, 3.0*RoomScale+Rnd(0,0.001), r\z+Rnd(-392,520)*RoomScale,90,Rnd(360),0)
				de\Size = Rnd(0.1,0.6)
				ScaleSprite(de\obj, de\Size,de\Size)
				CreateDecal(Rand(15,16), r\x+Rnd(-0.5,0.5), 3.0*RoomScale+Rnd(0,0.001), r\z+Rnd(-0.5,0.5),90,Rnd(360),0)
				de\Size = Rnd(0.1,0.6)
				ScaleSprite(de\obj, de\Size,de\Size)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x + 512.0 * RoomScale, r\y + 384 * RoomScale, r\z + 384.0 * RoomScale, r, True)
			sc\angle = 45 + 90
			sc\turn = 45
			TurnEntity(sc\CameraObj, 40, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			PositionEntity(sc\ScrObj, r\x + 668 * RoomScale, 1.1, r\z - 96.0 * RoomScale)
			TurnEntity(sc\ScrObj, 0, 90, 0)
			EntityParent(sc\ScrObj, r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 384.0 * RoomScale, r\y + 384 * RoomScale, r\z - 512.0 * RoomScale, r, True)
			sc\angle = 45 + 90 + 180
			sc\turn = 45
			
			TurnEntity(sc\CameraObj, 40, 0, 0)
			EntityParent(sc\obj, r\obj)				
			
			PositionEntity(sc\ScrObj, r\x + 96.0 * RoomScale, 1.1, r\z - 668.0 * RoomScale)
			EntityParent(sc\ScrObj, r\obj)
			;[End Block]
		Case "gatea"
			;[Block]
			r\RoomDoors[2] = CreateDoor(r\zone, r\x - 4064.0 * RoomScale, r\y-1280.0*RoomScale, r\z + 3952.0 * RoomScale, 0, r, False)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False
			
			d2 = CreateDoor(r\zone, r\x, r\y, r\z - 1024.0 * RoomScale, 0, r, False)
			d2\AutoClose = False : d2\open = False : d2\locked = True
			
			d2 = CreateDoor(r\zone, r\x-1440*RoomScale, r\y-480.0*RoomScale, r\z + 2328.0 * RoomScale, 0, r, False, False, 2)
			If SelectedEnding = "A2" Then 
				d2\AutoClose = False : d2\open = True : d2\locked = True	
			Else
				d2\AutoClose = False : d2\open = False : d2\locked = False	
			EndIf	
			PositionEntity d2\buttons[0], r\x-1320.0*RoomScale, EntityY(d2\buttons[0],True), r\z + 2288.0*RoomScale, True
			PositionEntity d2\buttons[1], r\x-1584*RoomScale, EntityY(d2\buttons[0],True), r\z + 2488.0*RoomScale, True	
			RotateEntity d2\buttons[1], 0, 90, 0, True
			
			d2 = CreateDoor(r\zone, r\x-1440*RoomScale, r\y-480.0*RoomScale, r\z + 4352.0 * RoomScale, 0, r, False, False, 2)
			If SelectedEnding = "A2" Then 
				d2\AutoClose = False : d2\open = True : d2\locked = True	
			Else
				d2\AutoClose = False : d2\open = False : d2\locked = False
			EndIf
			PositionEntity d2\buttons[0], r\x-1320.0*RoomScale, EntityY(d2\buttons[0],True), r\z + 4384.0*RoomScale, True
			RotateEntity d2\buttons[0], 0, 180, 0, True	
			PositionEntity d2\buttons[1], r\x-1584.0*RoomScale, EntityY(d2\buttons[0],True), r\z + 4232.0*RoomScale, True	
			RotateEntity d2\buttons[1], 0, 90, 0, True	
			
			For r2.Rooms = Each Rooms
				If r2\RoomTemplate\Name = "exit1" Then
					r\Objects[1]=r2\Objects[1]
					r\Objects[2]=r2\Objects[2]	
				ElseIf r2\RoomTemplate\Name = "gateaentrance"
					;ylempi hissi
					r\RoomDoors[1] = CreateDoor(0, r\x+1544.0*RoomScale, r\y, r\z-64.0*RoomScale, 90, r, False, 3)
					r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
					PositionEntity(r\RoomDoors[1]\buttons[0],r\x+1584*RoomScale, EntityY(r\RoomDoors[1]\buttons[0],True), r\z+80*RoomScale, True)
					PositionEntity(r\RoomDoors[1]\buttons[1],r\x+1456*RoomScale, EntityY(r\RoomDoors[1]\buttons[1],True), r\z-208*RoomScale, True)	
					r2\Objects[1] = CreatePivot()
					PositionEntity(r2\Objects[1], r\x+1848.0*RoomScale, r\y+240.0*RoomScale, r\z-64.0*RoomScale, True)
					EntityParent r2\Objects[1], r\obj						
				EndIf
			Next
			
			;106:n spawnpoint
			r\Objects[3]=CreatePivot()
			PositionEntity(r\Objects[3], r\x+1216.0*RoomScale, r\y, r\z+2112.0*RoomScale, True)
			EntityParent r\Objects[3], r\obj
			
			;sillan loppup??????
			r\Objects[4]=CreatePivot()
			PositionEntity(r\Objects[4], r\x, r\y+96.0*RoomScale, r\z+6400.0*RoomScale, True)
			EntityParent r\Objects[4], r\obj		
			
			;vartiotorni 1
			r\Objects[5]=CreatePivot()
			PositionEntity(r\Objects[5], r\x+1784.0*RoomScale, r\y+2124.0*RoomScale, r\z+4512.0*RoomScale, True)
			EntityParent r\Objects[5], r\obj	
			
			;vartiotorni 2
			r\Objects[6]=CreatePivot()
			PositionEntity(r\Objects[6], r\x-5048.0*RoomScale, r\y+1912.0*RoomScale, r\z+4656.0*RoomScale, True)
			EntityParent r\Objects[6], r\obj	
			
			;sillan takareuna
			r\Objects[7]=CreatePivot()
			PositionEntity(r\Objects[7], r\x+1824.0*RoomScale, r\y+224.0*RoomScale, r\z+7056.0*RoomScale, True)
			EntityParent r\Objects[7], r\obj	
			
			;sillan takareuna2
			r\Objects[8]=CreatePivot()
			PositionEntity(r\Objects[8], r\x-1824.0*RoomScale, r\y+224.0*RoomScale, r\z+7056.0*RoomScale, True)
			EntityParent r\Objects[8], r\obj	
			
			;"valopyssy"
			r\Objects[9]=CreatePivot()
			PositionEntity(r\Objects[9], r\x+2624.0*RoomScale, r\y+992.0*RoomScale, r\z+6157.0*RoomScale, True)
			EntityParent r\Objects[9], r\obj	
			;objects[10] = valopyssyn yl???osa
			
			;tunnelin loppu
			r\Objects[11]=CreatePivot()
			PositionEntity(r\Objects[11], r\x-4064.0*RoomScale, r\y-1248.0*RoomScale, r\z-1696.0*RoomScale, True)
			EntityParent r\Objects[11], r\obj
			
			collpvt = CreateCube()
			;EntityColor collpvt, 200, 0, 0
			EntityAlpha collpvt, 0.0
			PositionEntity(collpvt, r\x-4160*RoomScale, r\y-1045.0*RoomScale, r\z-1920*RoomScale)
			MoveEntity collpvt, 0.3, 0, -0.3
			ScaleEntity collpvt, 0.55, 0.55, 0.55
			EntityType collpvt, HIT_MAP
			EntityParent collpvt, r\obj
			
			r\Objects[27] = CreatePivot()
			;EntityColor collpvt, 200, 0, 0
			PositionEntity(r\Objects[27], r\x-4160*RoomScale, r\y-1045.0*RoomScale, r\z-1920*RoomScale)
			MoveEntity r\Objects[27], 0.3, 0.1, 30
			EntityParent r\Objects[27], r\obj
			
			r\Objects[13]=LoadMesh_Strict("GFX\map\gateawall1.b3d",r\obj)
			PositionEntity(r\Objects[13], r\x-4308.0*RoomScale, r\y-1045.0*RoomScale, r\z+544.0*RoomScale, True)
			EntityColor r\Objects[13], 25,25,25
			EntityType r\Objects[13],HIT_MAP
			;EntityFX(r\Objects[13],1)
			
			r\Objects[14]=LoadMesh_Strict("GFX\map\gateawall2.b3d",r\obj)
			PositionEntity(r\Objects[14], r\x-3820.0*RoomScale, r\y-1045.0*RoomScale, r\z+544.0*RoomScale, True)	
			EntityColor r\Objects[14], 25,25,25
			EntityType r\Objects[14],HIT_MAP
			;EntityFX(r\Objects[14],1)
			
			HideEntity r\Objects[13]
			HideEntity r\Objects[14]
			
			r\Objects[15]=CreatePivot(r\obj)
			PositionEntity(r\Objects[15], r\x-3568.0*RoomScale, r\y-1089.0*RoomScale, r\z+4944.0*RoomScale, True)
			
			r\Objects[16] = LoadMesh_Strict("GFX\map\gatea_hitbox1.b3d",r\obj)
			EntityPickMode r\Objects[16],2
			EntityType r\Objects[16],HIT_MAP
			EntityAlpha r\Objects[16],0.0
			
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x - 1366.82 * RoomScale, r\y + 77.3125 * RoomScale, r\z - 272.784 * RoomScale, -45)
			
			RoadCollide = CreateCube()
			ScaleEntity RoadCollide,1.72+0.4, 6, 2.59
			PositionEntity RoadCollide, r\x + 911.56131 * RoomScale, r\y - 767 * RoomScale, r\z + 1062.06053 * RoomScale
			MoveEntity RoadCollide, 0.5, 0, 0.1
			EntityType RoadCollide, HIT_INVISIBLEWALL
			EntityAlpha RoadCollide, 0.0
			EntityParent RoadCollide, r\obj
			
			RoadCollide = CreateCube()
			ScaleEntity RoadCollide,1.72+0.4, 6, 2.59
			PositionEntity RoadCollide, r\x - 905.79389 * RoomScale, r\y - 767 * RoomScale, r\z + 1081.67571 * RoomScale
			MoveEntity RoadCollide, -0.5, 0, 0.0
			EntityType RoadCollide, HIT_INVISIBLEWALL
			EntityAlpha RoadCollide, 0.0
			EntityParent RoadCollide, r\obj
			;[End Block]
		Case "gateaentrance"
			;[Block]
			;alempi hissi
			r\RoomDoors[0] = CreateDoor(0, r\x+744.0*RoomScale, 0, r\z+512.0*RoomScale, 90, r, True, 3)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			PositionEntity(r\RoomDoors[0]\buttons[1],r\x+688*RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z+368*RoomScale, True)
			PositionEntity(r\RoomDoors[0]\buttons[0],r\x+784*RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z+656*RoomScale, True)
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x+1048.0*RoomScale, 0, r\z+512.0*RoomScale, True)
			EntityParent r\Objects[0], r\obj
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x, 0, r\z - 360.0 * RoomScale, 0, r, False, True, 5)
			r\RoomDoors[1]\dir = 1 : r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
			PositionEntity(r\RoomDoors[1]\buttons[1], r\x + 422.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1], True), r\z - 576.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\buttons[1], 0.0, r\angle - 90.0, 0.0, True)
			PositionEntity(r\RoomDoors[1]\buttons[0], r\x - 522.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[0], True), EntityZ(r\RoomDoors[1]\buttons[0], True), True)
			RotateEntity(r\RoomDoors[1]\buttons[0], 0.0, r\angle - 225.0, 0.0, True)
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + 413.094 * RoomScale, r\y + 77.312 * RoomScale, r\z + 804.36 * RoomScale, -179)
			;r\Objects[22] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[22], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[22], EntityX(r\obj), EntityY(r\obj)+0.1, EntityZ(r\obj)
			
			;r\Objects[23] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[23], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[23], EntityX(r\obj), EntityY(r\obj)+0.1, EntityZ(r\obj)+1
			
			;r\Objects[24] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[24], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[24], EntityX(r\obj)+1, EntityY(r\obj)+0.1, EntityZ(r\obj)
			
			;For i = 22 To 24
			;	EntityType r\Objects[i], HIT_MAP
			;	a = AddLight(r, EntityX(r\Objects[i]), EntityY(r\Objects[i]), EntityZ(r\Objects[i]), 2, 1, 255,255,255)
			;	EntityColor r\Objects[i], Rand(255), Rand(255), Rand(255)
			;Next
			;[End Block]
		Case "exit1"
			;[Block]
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x+4356.0*RoomScale, 9767.0*RoomScale, r\z+2588.0*RoomScale, True)
			
			r\RoomDoors[4] = CreateDoor(r\zone, r\x, 0, r\z - 320.0 * RoomScale, 0, r, False, True, 5)
			r\RoomDoors[4]\dir = 1 : r\RoomDoors[4]\AutoClose = False : r\RoomDoors[4]\open = False
			PositionEntity(r\RoomDoors[4]\buttons[1], r\x + 358.0 * RoomScale, EntityY(r\RoomDoors[4]\buttons[1], True), r\z - 528.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[4]\buttons[1], 0.0, r\angle - 90.0, 0.0, True)
			PositionEntity(r\RoomDoors[4]\buttons[0], EntityX(r\RoomDoors[4]\buttons[0], True), EntityY(r\RoomDoors[4]\buttons[0], True), r\z - 198.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[4]\buttons[0], 0.0, r\angle - 180.0, 0.0, True)	
			
			;k???yt???v???n takaosa
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x-7680.0*RoomScale, 10992.0*RoomScale, r\z-27048.0*RoomScale, True)
			EntityParent r\Objects[3], r\obj
			
			;oikean puolen watchpoint 1
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x+5203.36*RoomScale, 12128.0*RoomScale, r\z-1739.19*RoomScale, True)
			EntityParent r\Objects[4], r\obj
			;oikean puolen watchpoint 2
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x+4363.02*RoomScale, 10536.0*RoomScale, r\z+2766.16*RoomScale, True)
			EntityParent r\Objects[5], r\obj	
			;vasemman puolen watchpoint 1
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x+5192.0*RoomScale, 12192.0*RoomScale, r\z-1760.0*RoomScale, True)
			EntityParent r\Objects[6], r\obj
			;vasemman puolen watchpoint 2
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x+5192.0*RoomScale, 12192.0*RoomScale, r\z-4352.0*RoomScale, True)
			EntityParent r\Objects[7], r\obj
			
			;alempi hissi
			r\RoomDoors[0] = CreateDoor(0, r\x+720.0*RoomScale, 0, r\z+1432.0*RoomScale, 0, r, True, 3)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			MoveEntity r\RoomDoors[0]\buttons[0],0,0,22.0*RoomScale
			MoveEntity r\RoomDoors[0]\buttons[1],0,0,22.0*RoomScale	
			r\Objects[8] = CreatePivot()
			PositionEntity(r\Objects[8], r\x+720.0*RoomScale, 0, r\z+1744.0*RoomScale, True)
			EntityParent r\Objects[8], r\obj
			
			;ylempi hissi
			r\RoomDoors[1] = CreateDoor(0, r\x-5424.0*RoomScale, 10784.0*RoomScale, r\z-1380.0*RoomScale, 0, r, False, 3)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
			MoveEntity r\RoomDoors[1]\buttons[0],0,0,22.0*RoomScale
			MoveEntity r\RoomDoors[1]\buttons[1],0,0,22.0*RoomScale			
			r\Objects[9] = CreatePivot()
			PositionEntity(r\Objects[9], r\x-5424.0*RoomScale, 10784.0*RoomScale, r\z-1068.0*RoomScale, True)
			EntityParent r\Objects[9], r\obj		
			
			r\RoomDoors[2] = CreateDoor(0, r\x+4352.0*RoomScale, 10784.0*RoomScale, r\z-492.0*RoomScale, 0, r, False)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False	
			
			r\RoomDoors[3] = CreateDoor(0, r\x+4352.0*RoomScale, 10784.0*RoomScale, r\z+500.0*RoomScale, 0, r, False)
			r\RoomDoors[3]\AutoClose = False : r\RoomDoors[3]\open = False	
			
			;walkway
			r\Objects[10] = CreatePivot()
			PositionEntity(r\Objects[10], r\x+4352.0*RoomScale, 10778.0*RoomScale, r\z+1344.0*RoomScale, True)
			EntityParent r\Objects[10], r\obj	
			
			;"682"
			r\Objects[11] = CreatePivot()
			PositionEntity(r\Objects[11], r\x+2816.0*RoomScale, 11024.0*RoomScale, r\z-2816.0*RoomScale, True)
			EntityParent r\Objects[11], r\obj
			
			;r\Objects[12] = 682:n k???si
			
			;"valvomon" takaovi
			r\RoomDoors[5] = CreateDoor(0, r\x+3248.0*RoomScale, 9856.0*RoomScale, r\z+6400.0*RoomScale, 0, r, False, False, 0, "28084020")
			r\RoomDoors[5]\AutoClose = False : r\RoomDoors[5]\open = False		
			
			;"valvomon" etuovi
			d.Doors = CreateDoor(0, r\x+3072.0*RoomScale, 9856.0*RoomScale, r\z+5800.0*RoomScale, 90, r, False, False, 3)
			d\AutoClose = False : d\open = False
			
			r\Objects[14] = CreatePivot()
			PositionEntity(r\Objects[14], r\x+3536.0*RoomScale, 10256.0*RoomScale, r\z+5512.0*RoomScale, True)
			EntityParent r\Objects[14], r\obj
			r\Objects[15] = CreatePivot()
			PositionEntity(r\Objects[15], r\x+3536.0*RoomScale, 10256.0*RoomScale, r\z+5824.0*RoomScale, True)
			EntityParent r\Objects[15], r\obj			
			r\Objects[16] = CreatePivot()
			PositionEntity(r\Objects[16], r\x+3856.0*RoomScale, 10256.0*RoomScale, r\z+5512.0*RoomScale, True)
			EntityParent r\Objects[16], r\obj
			r\Objects[17] = CreatePivot()
			PositionEntity(r\Objects[17], r\x+3856.0*RoomScale, 10256.0*RoomScale, r\z+5824.0*RoomScale, True)
			EntityParent r\Objects[17], r\obj
			
			;MTF:n spawnpoint
			r\Objects[18] = CreatePivot()
			;PositionEntity(r\Objects[18], r\x+3727.0*RoomScale, 10066.0*RoomScale, r\z+6623.0*RoomScale, True)
			PositionEntity(r\Objects[18], r\x+3250.0*RoomScale, 9896.0*RoomScale, r\z+6623.0*RoomScale, True)
			EntityParent r\Objects[18], r\obj
			
			;piste johon helikopterit pakenee nukea
			r\Objects[19] = CreatePivot()
			PositionEntity(r\Objects[19], r\x+3808.0*RoomScale, 12320.0*RoomScale, r\z-13568.0*RoomScale, True)
			EntityParent r\Objects[19], r\obj

			r\Objects[26] = CreatePivot()
			PositionEntity r\Objects[26], r\x+4352.0*RoomScale, 10784.0*RoomScale, r\z+500.0*RoomScale
			MoveEntity r\Objects[26], 0, 0.3, -8
			EntityParent r\Objects[26], r\obj
			
			r\Objects[27] = CreatePivot()
			PositionEntity r\Objects[27], r\x+3072.0*RoomScale, 9856.0*RoomScale, r\z+5800.0*RoomScale
			EntityParent r\Objects[27], r\obj
			
			If Server\Breach Then
				r\Objects[22] = CreateButton(r\x + 3969.68693 * RoomScale, (r\y + 9974.92682 * RoomScale), r\z + 5827.87935 * RoomScale,80,-90,0)
				EntityParent r\Objects[22], r\obj
			EndIf
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + 872.8707 * RoomScale, 0, r\z - 65.66945 * RoomScale, 115)
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + -5499.1101 * RoomScale, r\y + 10254.04158 * RoomScale, r\z - 3793.33341 * RoomScale, 0)
			
			;r\Objects[22] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[22], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[22], EntityX(r\obj), EntityY(r\obj)+0.1, EntityZ(r\obj)
			
			;r\Objects[23] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[23], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[23], EntityX(r\obj), EntityY(r\obj)+0.1, EntityZ(r\obj)+1
			
			;r\Objects[24] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[24], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[24], EntityX(r\obj)+1, EntityY(r\obj)+0.1, EntityZ(r\obj)
			;For i = 22 To 24
			;	EntityType r\Objects[i], HIT_MAP
			;	a = AddLight(r, EntityX(r\Objects[i]), EntityY(r\Objects[i]), EntityZ(r\Objects[i]), 2, 1, 255,255,255)
			;	EntityColor r\Objects[i], Rand(255), Rand(255), Rand(255)
			;Next
			;[End Block]
		Case "roompj"
			;[Block]
			If Server\Breach = False Then
				it = CreateItem("Document SCP-372", "paper", r\x + 800.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 1108.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle, 0
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("Small First Aid Kit", "finefirstaid", r\x + 800.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 1108.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle, 0
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Radio Transceiver", "radio", r\x + 800.0 * RoomScale, r\y + 112.0 * RoomScale, r\z + 944.0 * RoomScale)
			it\state = 80.0
			EntityParent(it\collider, r\obj)
			
			r\Objects[3] = LoadMesh_Strict("GFX\map\372_hb.b3d",r\obj)
			EntityPickMode r\Objects[3],2
			EntityType r\Objects[3],HIT_MAP
			EntityAlpha r\Objects[3],0.0
			
			r\RoomDoors[0] = CreateDoor(r\zone,r\x,r\y,r\z-368.0*RoomScale,0,r,True,True,2)
			r\RoomDoors[0]\AutoClose = False
			PositionEntity (r\RoomDoors[0]\buttons[0], r\x - 496.0 * RoomScale, 0.7, r\z - 272.0 * RoomScale, True)
			TurnEntity(r\RoomDoors[0]\buttons[0], 0, 90, 0)
			;[End Block]
		Case "room079"
			;[Block]
			r\RoomDoors[2] = CreateDoor(r\zone, r\x, -448.0*RoomScale, r\z + 1136.0 * RoomScale, 0, r, False,True, 4)
			r\RoomDoors[2]\dir = 1 : r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False
			PositionEntity(r\RoomDoors[2]\buttons[1], r\x + 224.0 * RoomScale, -250*RoomScale, r\z + 918.0 * RoomScale, True)
			;TurnEntity(d\buttons[0],0,-90,0,True)
			PositionEntity(r\RoomDoors[2]\buttons[0], r\x - 240.0 * RoomScale, -250*RoomScale, r\z + 1366.0 * RoomScale, True)
			;TurnEntity(d\buttons[1],0, 90,0,True)	
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 1456.0*RoomScale, -448.0*RoomScale, r\z + 976.0 * RoomScale, 0, r, False, True, 3)
			r\RoomDoors[0]\dir = 1 : r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 1760.0 * RoomScale, -250*RoomScale, r\z + 1236.0 * RoomScale, True)
			TurnEntity(r\RoomDoors[0]\buttons[0],0,-90-90,0,True)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 1760.0 * RoomScale, -240*RoomScale, r\z + 740.0 * RoomScale, True)
			TurnEntity(r\RoomDoors[0]\buttons[1],0, 90-90,0,True)
			
			r\RoomDoors[1] = CreateDoor(0, r\x + 1144.0*RoomScale, -448.0*RoomScale, r\z + 704.0 * RoomScale, 90, r, False, False, -1)
			
			r\Objects[0] = LoadAnimMesh_Strict("GFX\map\079.b3d")
			ScaleEntity(r\Objects[0], 1.3, 1.3, 1.3, True)
			PositionEntity (r\Objects[0], r\x + 1856.0*RoomScale, -560.0*RoomScale, r\z-672.0*RoomScale, True)
			EntityParent(r\Objects[0], r\obj)
			TurnEntity r\Objects[0],0,180,0,True
			
			r\Objects[1] = CreateSprite(r\Objects[0])
			SpriteViewMode(r\Objects[1],2)
			PositionEntity(r\Objects[1], 0.082, 0.119, 0.010)
			ScaleSprite(r\Objects[1],0.18*0.5,0.145*0.5)
			TurnEntity(r\Objects[1],0,13.0,0)
			MoveEntity r\Objects[1], 0,0,-0.022
			EntityTexture (r\Objects[1],OldAiPics(0))
			
			HideEntity r\Objects[1]
			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity (r\Objects[2], r\x + 1184.0*RoomScale, -448.0*RoomScale, r\z+1792.0*RoomScale, True)
			
			de.Decals = CreateDecal(3,  r\x + 1184.0*RoomScale, -448.0*RoomScale+0.01, r\z+1792.0*RoomScale,90,Rnd(360),0)
			de\Size = 0.5
			ScaleSprite(de\obj, de\Size,de\Size)
			EntityParent de\obj, r\obj
			;[End Block]
		Case "checkpoint1"
			;[Block]
			r\RoomDoors[0] = CreateDoor(0, r\x + 48.0*RoomScale, 0, r\z - 128.0 * RoomScale, 0, r, False, False, 3-Server\Breach)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x - 152.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z - 352.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x - 152.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z + 96.0 * RoomScale, True)
			
			r\RoomDoors[1] = CreateDoor(0, r\x - 352.0*RoomScale, 0, r\z - 128.0 * RoomScale, 0, r, False, False, 3-Server\Breach)
			;FreeEntity r\RoomDoors[1]\buttons[0]
			;FreeEntity r\RoomDoors[1]\buttons[1]
			
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity (r\Objects[0], r\x + 720.0*RoomScale, 120.0*RoomScale, r\z+333.0*RoomScale, True)
			
			r\RoomDoors[0]\timer = 70 * 5
			r\RoomDoors[1]\timer = 70 * 5
			
			sc.SecurityCams = CreateSecurityCam(r\x+192.0*RoomScale, r\y+704.0*RoomScale, r\z-960.0*RoomScale, r)
			sc\angle = 45
			sc\turn = 0
			TurnEntity(sc\CameraObj, 20, 0, 0)
			
			r\Objects[2] = CopyEntity(Monitor2,r\obj)
			ScaleEntity(r\Objects[2], 2.0, 2.0, 2.0)
			PositionEntity (r\Objects[2], r\x - 152.0*RoomScale, 384.0*RoomScale, r\z+124.0*RoomScale, True)
			RotateEntity (r\Objects[2],0,180,0)
			EntityFX r\Objects[2],1
			
			r\Objects[3] = CopyEntity(Monitor2,r\obj)
			ScaleEntity(r\Objects[3], 2.0, 2.0, 2.0)
			PositionEntity (r\Objects[3], r\x - 152.0*RoomScale, 384.0*RoomScale, r\z-380.0*RoomScale, True)
			RotateEntity (r\Objects[3],0,0,0)
			EntityFX r\Objects[3],1
			
			If MapTemp(Floor(r\x / 8.0),Floor(r\z /8.0)-1)=0 Then
				d2.Doors = CreateDoor(r\zone, r\x, 0, r\z  - 4.0, 0, r, 0, False, 0, "GEAR")
				d2\locked = True
			EndIf
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x - 805.322 * RoomScale, r\y + 77.312 * RoomScale, r\z + 338.207 * RoomScale, -80)
			;[End Block]
		Case "checkpoint2"
			;[Block]
			r\RoomDoors[0]= CreateDoor(0, r\x - 48.0*RoomScale, 0, r\z + 128.0 * RoomScale, 0, r, False, False, 5-(Server\Breach*2))
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 152.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z - 96.0 * RoomScale, True)			
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 152.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z + 352.0 * RoomScale, True)
			
			r\RoomDoors[1] = CreateDoor(0, r\x + 352.0*RoomScale, 0, r\z + 128.0 * RoomScale, 0, r, False, False, 5-(Server\Breach*2))
			;FreeEntity r\RoomDoors[1]\buttons[0]
			;FreeEntity r\RoomDoors[1]\buttons[1]
			
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity (r\Objects[0], r\x - 720.0*RoomScale, 120.0*RoomScale, r\z+464.0*RoomScale, True)
			
			r\Objects[2] = CopyEntity(Monitor3,r\obj)
			ScaleEntity(r\Objects[2], 2.0, 2.0, 2.0)
			PositionEntity (r\Objects[2], r\x + 152.0*RoomScale, 384.0*RoomScale, r\z+380.0*RoomScale, True)
			RotateEntity (r\Objects[2],0,180,0)
			EntityFX r\Objects[2],1
			
			r\Objects[3] = CopyEntity(Monitor3,r\obj)
			ScaleEntity(r\Objects[3], 2.0, 2.0, 2.0)
			PositionEntity (r\Objects[3], r\x + 152.0*RoomScale, 384.0*RoomScale, r\z-124.0*RoomScale, True)
			RotateEntity (r\Objects[3],0,0,0)
			EntityFX r\Objects[3],1
			
			r\RoomDoors[0]\timer = 70 * 5
			r\RoomDoors[1]\timer = 70 * 5
			
			If MapTemp(Floor(r\x / 8.0),Floor(r\z /8.0)-1)=0 Then
				d2.Doors = CreateDoor(r\zone, r\x, 0, r\z  - 4.0, 0, r, 0, False, 0, "GEAR")
				d2\locked = True
			EndIf
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + 755.494 * RoomScale, r\y + 77.312 * RoomScale, r\z - 446.897 * RoomScale, 72)
			;[End Block]
		Case "room2pit"
			;[Block]
			i = 0
			For  xtemp% = -1 To 1 Step 2
				For ztemp% = -1 To 1
					em.Emitters = CreateEmitter(r\x + 202.0 * RoomScale * xtemp, 8.0 * RoomScale, r\z + 256.0 * RoomScale * ztemp, 0)
					em\RandAngle = 30
					em\Speed = 0.0045
					em\SizeChange = 0.007
					em\Achange = -0.016
					r\Objects[i] = em\Obj
					If i < 3 Then 
						TurnEntity(em\Obj, 0, -90, 0, True) 
					Else 
						TurnEntity(em\Obj, 0, 90, 0, True)
					EndIf
					TurnEntity(em\Obj, -45, 0, 0, True)
					EntityParent(em\Obj, r\obj)
					i=i+1
				Next
			Next
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 640.0 * RoomScale, 8.0 * RoomScale, r\z - 896.0 * RoomScale)
			EntityParent(r\Objects[6], r\obj)
			
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x - 864.0 * RoomScale, -400.0 * RoomScale, r\z - 632.0 * RoomScale)
			EntityParent(r\Objects[7],r\obj)
			;[End Block]
		Case "room2testroom2"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 640.0 * RoomScale, 0.5, r\z - 912.0 * RoomScale)
			EntityParent(r\Objects[0], r\obj)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 669.0 * RoomScale, 0.5, r\z - 16.0 * RoomScale) ;r\x - 632
			EntityParent(r\Objects[1], r\obj)
			
			Local Glasstex = LoadTexture_Strict("GFX\map\glass.png",1+2)
			r\Objects[2] = CreateSprite()
			EntityTexture(r\Objects[2],Glasstex)
			SpriteViewMode(r\Objects[2],2)
			ScaleSprite(r\Objects[2],182.0*RoomScale*0.5, 192.0*RoomScale*0.5)
			PositionEntity(r\Objects[2], r\x - 632.0 * RoomScale, 224.0*RoomScale, r\z - 208.0 * RoomScale)
			TurnEntity(r\Objects[2],0,180,0)			
			EntityParent(r\Objects[2], r\obj)
			HideEntity (r\Objects[2])
			
			FreeTexture Glasstex
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 240.0 * RoomScale, 0.0, r\z + 640.0 * RoomScale, 90, r, False, False, 1)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x - 512.0 * RoomScale, 0.0, r\z + 384.0 * RoomScale, 0, r, False, False)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False					
			
			;d = CreateDoor(r\zone, r\x - 816.0 * RoomScale, 0.0, r\z - 208.0 * RoomScale, 0, r, False, False)
			;d\AutoClose = False : d\open = False
			;FreeEntity(d\buttons[0]) : d\buttons[0]=0
			;FreeEntity(d\buttons[1]) : d\buttons[1]=0
			
			it = CreateItem("Level 2 Key Card", "key2", r\x - 914.0 * RoomScale, r\y + 137.0 * RoomScale, r\z + 61.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			If Server\Breach = False Then
				it = CreateItem("S-NAV 300 Navigator", "nav", r\x - 312.0 * RoomScale, r\y + 264.0 * RoomScale, r\z + 176.0 * RoomScale)
				it\state = 20 : EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x - 312.0 * RoomScale, r\y + 264.0 * RoomScale, r\z + 176.0 * RoomScale)
				EntityParent(it\collider, r\obj)
				
				it = CreateItem("Level 2 Key Card", "key2", r\x - 910.0 * RoomScale, r\y + 137.0 * RoomScale, r\z + 61.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x - 874.88169*RoomScale, 0, r\z + 639.04615*RoomScale, 90-180)
			;[End Block]
		Case "room3tunnel"
			;[Block]
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity (r\Objects[0], r\x - 190.0*RoomScale, 4.0*RoomScale, r\z+190.0*RoomScale, True)
			
			;[End Block]
		Case "room2toilets"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1040.0 * RoomScale, 192.0 * RoomScale, r\z)
			EntityParent(r\Objects[0], r\obj)
			
			r\Objects[1] = CreatePivot()
			;PositionEntity(r\Objects[1], r\x + 1270.0*RoomScale, 0.5, r\z+570.0*RoomScale)
			PositionEntity(r\Objects[1], r\x + 1530.0*RoomScale, 0.5, r\z+512.0*RoomScale)
			EntityParent(r\Objects[1], r\obj)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 1535.0*RoomScale, r\y+150.0*RoomScale, r\z+512.0*RoomScale)
			EntityParent(r\Objects[2], r\obj)
			;[End Block]
		Case "room2storage"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 1288.0 * RoomScale, 0, r\z, 270, r)
			r\RoomDoors[1] = CreateDoor(r\zone, r\x - 760.0 * RoomScale, 0, r\z, 270, r)
			r\RoomDoors[2] = CreateDoor(r\zone, r\x - 264.0 * RoomScale, 0, r\z, 270, r)
			r\RoomDoors[3] = CreateDoor(r\zone, r\x + 264.0 * RoomScale, 0, r\z, 270, r)
			r\RoomDoors[4] = CreateDoor(r\zone, r\x + 760.0 * RoomScale, 0, r\z, 270, r)
			r\RoomDoors[5] = CreateDoor(r\zone, r\x + 1288.0 * RoomScale, 0, r\z, 270, r)
			
			For i = 0 To 5
				MoveEntity r\RoomDoors[i]\buttons[0], 0,0,-8.0
				MoveEntity r\RoomDoors[i]\buttons[1], 0,0,-8.0
				r\RoomDoors[i]\AutoClose = False : r\RoomDoors[i]\open = False				
			Next
			
			it = CreateItem("Document SCP-939", "paper", r\x + 352.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 256.0 * RoomScale)
			RotateEntity it\collider, 0, r\angle+4, 0
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("9V Battery", "bat", r\x + 352.0 * RoomScale, r\y + 112.0 * RoomScale, r\z + 448.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			If Server\Breach = False Then
				it = CreateItem("Empty Cup", "emptycup", r\x-672*RoomScale, 240*RoomScale, r\z+288.0*RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x-672*RoomScale, 240*RoomScale, r\z+288.0*RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Level "+(Server\Breach+1)+" Key Card", "key"+(Server\Breach+1), r\x - 672.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 224.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room2sroom"
			;[Block]
			d = CreateDoor(r\zone, r\x + 1440.0 * RoomScale, 224.0 * RoomScale, r\z + 32.0 * RoomScale, 90, r, False, False, 4)
			d\AutoClose = False : d\open = False
			
			it = CreateItem("Some SCP-420-J", "420", r\x + 1776.0 * RoomScale, r\y + 400.0 * RoomScale, r\z + 427.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Some SCP-420-J", "420", r\x + 1808.0 * RoomScale, r\y + 400.0 * RoomScale, r\z + 435.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Level 5 Key Card", "key5", r\x + 2232.0 * RoomScale, r\y + 392.0 * RoomScale, r\z + 387.0 * RoomScale)
			RotateEntity it\collider, 0, r\angle, 0, True
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Nuclear Device Document", "paper", r\x + 2248.0 * RoomScale, r\y + 440.0 * RoomScale, r\z + 372.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Radio Transceiver", "radio", r\x + 2240.0 * RoomScale, r\y + 320.0 * RoomScale, r\z + 128.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room2shaft"
			;[Block]
			d = CreateDoor(r\zone, r\x + 1552.0 * RoomScale, r\y, r\z + 552.0 * RoomScale, 0, r, False, False)
			PositionEntity(d\buttons[0], EntityX(d\buttons[0],True), EntityY(d\buttons[0],True), r\z + 518.0 * RoomScale, True)
			PositionEntity(d\buttons[1], EntityX(d\buttons[1],True), EntityY(d\buttons[1],True), r\z + 575.0 * RoomScale, True)
			d\AutoClose = False : d\open = False
			
			d = CreateDoor(r\zone, r\x + 256.0 * RoomScale, r\y, r\z + 744.0 * RoomScale, 90, r, False, False, 2)
			d\AutoClose = False : d\open = False
			
			it = CreateItem("Level 3 Key Card", "key3", r\x + 1119.0 * RoomScale, r\y + 233.0 * RoomScale, r\z + 494.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("First Aid Kit", "firstaid", r\x + 1035.0 * RoomScale, r\y + 145.0 * RoomScale, r\z + 56.0 * RoomScale)
			EntityParent(it\collider, r\obj) : RotateEntity(it\collider, 0, 90, 0)
			
			it = CreateItem("9V Battery", "bat", r\x + 1930.0 * RoomScale, r\y + 97.0 * RoomScale, r\z + 256.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			it = CreateItem("9V Battery", "bat", r\x + 1061.0 * RoomScale, r\y + 161.0 * RoomScale, r\z + 494.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("ReVision Eyedrops", "eyedrops", r\x + 1930.0*RoomScale, r\y + 225.0 * RoomScale, r\z + 128.0*RoomScale)
			EntityParent(it\collider, r\obj)
			
			;Player's position after leaving the pocket dimension
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity r\Objects[0],r\x+1560.0*RoomScale,r\y,r\z+250.0*RoomScale,True
			
			r\Objects[1] = CreatePivot(r\obj)
            PositionEntity r\Objects[1],r\x + 1344.0 * RoomScale, -752.0 * RoomScale,r\z - 384.0 * RoomScale,True
            
            de.Decals = CreateDecal(3,  r\x + 1334.0*RoomScale, -796.0*RoomScale+0.01, r\z-220.0*RoomScale,90,Rnd(360),0)
            de\Size = 0.25
            ScaleSprite(de\obj, de\Size,de\Size)
            EntityParent de\obj, r\obj
			
			r\Objects[2] = CreateButton(r\x + 1181.0 *RoomScale, r\y + 180.0 * RoomScale, r\z - 512.0 * RoomScale, 0, 270)
            EntityParent (r\Objects[2],r\obj)
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + 828.25 * RoomScale, r\y + 77.312 * RoomScale, r\z - 608.083 * RoomScale, 90)
			;[End Block]
		Case "room2poffices"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 240.0 * RoomScale, 0.0, r\z + 448.0 * RoomScale, 90, r, False, False, 0, Str(AccessCode))
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 248.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), EntityZ(r\RoomDoors[0]\buttons[0],True),True)
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 232.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), EntityZ(r\RoomDoors[0]\buttons[1],True),True)			
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x - 496.0 * RoomScale, 0.0, r\z, 90, r, False, False, 0, "ABCD")
			PositionEntity(r\RoomDoors[1]\buttons[0], r\x - 488.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[0],True), EntityZ(r\RoomDoors[1]\buttons[0],True),True)
			PositionEntity(r\RoomDoors[1]\buttons[1], r\x - 504.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[1],True), EntityZ(r\RoomDoors[1]\buttons[1],True),True)				
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False : r\RoomDoors[1]\locked = True	
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x + 240.0 * RoomScale, 0.0, r\z - 576.0 * RoomScale, 90, r, False, False, 0, "7816")
			PositionEntity(r\RoomDoors[2]\buttons[0], r\x + 248.0 * RoomScale, EntityY(r\RoomDoors[2]\buttons[0],True), EntityZ(r\RoomDoors[2]\buttons[0],True),True)
			PositionEntity(r\RoomDoors[2]\buttons[1], r\x + 232.0 * RoomScale, EntityY(r\RoomDoors[2]\buttons[1],True), EntityZ(r\RoomDoors[2]\buttons[1],True),True)		
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False	
			
			it = CreateItem("Mysterious Note", "paper", r\x + 736.0 * RoomScale, r\y + 224.0 * RoomScale, r\z + 544.0 * RoomScale)
			EntityParent(it\collider, r\obj)	
			it = CreateItem("Ballistic Vest", "vest", r\x + 608.0 * RoomScale, r\y + 112.0 * RoomScale, r\z + 32.0 * RoomScale)
			EntityParent(it\collider, r\obj) : RotateEntity(it\collider, 0, 90, 0)
			If Server\Breach = False Then
				it = CreateItem("Incident Report SCP-106-0204", "paper", r\x + 704.0 * RoomScale, r\y + 183.0 * RoomScale, r\z - 576.0 * RoomScale)
				EntityParent(it\collider, r\obj)
				it = CreateItem("Journal Page", "paper", r\x + 912 * RoomScale, r\y + 176.0 * RoomScale, r\z - 160.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("FN P90", "p90", r\x + 704.0 * RoomScale, r\y + 183.0 * RoomScale, r\z - 576.0 * RoomScale)
				EntityParent(it\collider, r\obj)
				it = CreateItem("Rocket Launcher", "rpg", r\x + 912 * RoomScale, r\y + 176.0 * RoomScale, r\z - 160.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("First Aid Kit", "firstaid", r\x + 912.0 * RoomScale, r\y + 112.0 * RoomScale, r\z - 336.0 * RoomScale)
			EntityParent(it\collider, r\obj) : RotateEntity(it\collider, 0, 90, 0)
			;[End Block]
		Case "room2poffices2"
			;[Block]
			d = CreateDoor(r\zone, r\x + 240.0 * RoomScale, 0.0, r\z + 48.0 * RoomScale, 270, r, False, False, 3)
			PositionEntity(d\buttons[0], r\x + 224.0 * RoomScale, EntityY(d\buttons[0],True), r\z + 176.0 * RoomScale,True)
			PositionEntity(d\buttons[1], r\x + 256.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True),True)			
			d\AutoClose = False : d\open = False
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 432.0 * RoomScale, 0.0, r\z, 90, r, False, False, 0, "1234")
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x - 416.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z + 176.0 * RoomScale,True)
			FreeEntity r\RoomDoors[0]\buttons[1] : r\RoomDoors[0]\buttons[1] = 0
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False : r\RoomDoors[0]\locked = True	
			
			de.Decals = CreateDecal(0, r\x - 808.0 * RoomScale, 0.005, r\z - 72.0 * RoomScale, 90, Rand(360), 0)
			EntityParent(de\obj, r\obj)
			de.Decals = CreateDecal(2, r\x - 808.0 * RoomScale, 0.01, r\z - 72.0 * RoomScale, 90, Rand(360), 0)
			de\Size = 0.3 : ScaleSprite(de\obj, de\Size, de\Size) : EntityParent(de\obj, r\obj)
			
			de.Decals = CreateDecal(0, r\x - 432.0 * RoomScale, 0.01, r\z, 90, Rand(360), 0)
			EntityParent(de\obj, r\obj)
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x - 808.0 * RoomScale, 1.0, r\z - 72.0 * RoomScale, True)
			If Server\Breach = False Then
				it = CreateItem("Dr. L's Burnt Note", "paper", r\x - 688.0 * RoomScale, 1.0, r\z - 16.0 * RoomScale)
				EntityParent(it\collider, r\obj)
				
				it = CreateItem("Dr L's Burnt Note", "paper", r\x - 808.0 * RoomScale, 1.0, r\z - 72.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("FN P90", "p90", r\x - 688.0 * RoomScale, 1.0, r\z - 16.0 * RoomScale)
				EntityParent(it\collider, r\obj)
				
				it = CreateItem("Rocket Launcher", "rpg", r\x - 808.0 * RoomScale, 1.0, r\z - 72.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("The Modular Site Project", "paper", r\x + 622.0*RoomScale, r\y + 125.0*RoomScale, r\z - 73.0*RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room2elevator"
			;[Block]
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x+888.0*RoomScale, 240.0*RoomScale, r\z, True)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x+1024.0*RoomScale-0.01, 120.0*RoomScale, r\z, True)
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 448.0 * RoomScale, 0.0, r\z, 90, r, False, 3)
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 416.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z - 208.0 * RoomScale,True)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 480.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z + 184.0 * RoomScale,True)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True : r\RoomDoors[0]\locked = True
			;[End Block]
		Case "room2cafeteria"
			;[Block]
			;scp-294
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x+1847.0*RoomScale, -240.0*RoomScale, r\z-321*RoomScale, True)
			;"spawnpoint" for the cups
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x+1780.0*RoomScale, -248.0*RoomScale, r\z-276*RoomScale, True)
			
			it = CreateItem("cup", "cup", r\x-508.0*RoomScale, -187*RoomScale, r\z+284.0*RoomScale, 240,175,70)
			EntityParent(it\collider, r\obj) : it\name = "Cup of Orange Juice"
			
			it = CreateItem("cup", "cup", r\x+1412 * RoomScale, -187*RoomScale, r\z-716.0 * RoomScale, 87,62,45)
			EntityParent(it\collider, r\obj) : it\name = "Cup of Coffee"
			If Server\Breach = False Then
				it = CreateItem("Empty Cup", "emptycup", r\x-540*RoomScale, -187*RoomScale, r\z+124.0*RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x-540*RoomScale, -187*RoomScale, r\z+124.0*RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Quarter", "25ct", r\x-447.0*RoomScale, r\y-334.0*RoomScale, r\z+36.0*RoomScale)
			EntityParent(it\collider, r\obj)
			it = CreateItem("Quarter", "25ct", r\x+1409.0*RoomScale, r\y-334.0*RoomScale, r\z-732.0*RoomScale)
			EntityParent(it\collider, r\obj)
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x - 577.36594*RoomScale, r\y-384*RoomScale, r\z-807.13401*RoomScale, 155-180)
			;[End Block]
		Case "room2nuke"
			;[Block]
			;"tuulikaapin" ovi
			r\RoomDoors[2] = CreateDoor(r\zone, r\x + 576.0 * RoomScale, 0.0, r\z + 152.0 * RoomScale, 90, r, False, False, 5)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False
			PositionEntity(r\RoomDoors[2]\buttons[0], r\x + 602.0 * RoomScale, EntityY(r\RoomDoors[2]\buttons[0],True), r\z + 20.0 * RoomScale,True)
			PositionEntity(r\RoomDoors[2]\buttons[1], r\x + 550.0 * RoomScale, EntityY(r\RoomDoors[2]\buttons[1],True), r\z + 20.0 * RoomScale,True)
			FreeEntity r\RoomDoors[2]\obj2 : r\RoomDoors[2]\obj2 = 0
			
			r\RoomDoors[3] = CreateDoor(r\zone, r\x - 544.0 * RoomScale, 1504.0*RoomScale, r\z + 738.0 * RoomScale, 90, r, False, False, 5)
			r\RoomDoors[3]\AutoClose = False : r\RoomDoors[3]\open = False			
			PositionEntity(r\RoomDoors[3]\buttons[0], EntityX(r\RoomDoors[3]\buttons[0],True), EntityY(r\RoomDoors[3]\buttons[0],True), r\z + 608.0 * RoomScale,True)
			PositionEntity(r\RoomDoors[3]\buttons[1], EntityX(r\RoomDoors[3]\buttons[1],True), EntityY(r\RoomDoors[3]\buttons[1],True), r\z + 608.0 * RoomScale,True)
			
			;yl???kerran hissin ovi
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 1192.0 * RoomScale, 0.0, r\z, 90, r, True, 3)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			;yl???kerran hissi
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x + 1496.0 * RoomScale, 240.0 * RoomScale, r\z)
			EntityParent(r\Objects[4], r\obj)
			;alakerran hissin ovi
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 680.0 * RoomScale, 1504.0 * RoomScale, r\z, 90, r, False, 3)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
			;alakerran hissi
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x + 984.0 * RoomScale, 1744.0 * RoomScale, r\z)
			EntityParent(r\Objects[5], r\obj)
			
			For n% = 0 To 1
				r\Objects[n * 2] = CopyEntity(LeverBaseOBJ)
				r\Objects[n * 2 + 1] = CopyEntity(LeverOBJ)
				r\Levers[n] = r\Objects[n * 2 + 1]
				
				For i% = 0 To 1
					ScaleEntity(r\Objects[n * 2 + i], 0.04, 0.04, 0.04)
					PositionEntity (r\Objects[n * 2 + i], r\x - 975.0 * RoomScale, r\y + 1712.0 * RoomScale, r\z - (502.0-132.0*n) * RoomScale, True)
					
					EntityParent(r\Objects[n * 2 + i], r\obj)
				Next
				RotateEntity(r\Objects[n * 2], 0, -90-180, 0)
				RotateEntity(r\Objects[n * 2 + 1], 10, -90 - 180-180, 0)
				
				;EntityPickMode(r\Objects[n * 2 + 1], 2)
				EntityPickMode r\Objects[n * 2 + 1], 1, False
				EntityRadius r\Objects[n * 2 + 1], 0.1
				;makecollbox(r\Objects[n * 2 + 1])
			Next
			
			it = CreateItem("Nuclear Device Document", "paper", r\x - 768.0 * RoomScale, r\y + 1684.0 * RoomScale, r\z - 768.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Ballistic Vest", "vest", r\x - 944.0 * RoomScale, r\y + 1652.0 * RoomScale, r\z - 656.0 * RoomScale)
			EntityParent(it\collider, r\obj) : RotateEntity(it\collider, 0, -90, 0)
			
			sc.SecurityCams = CreateSecurityCam(r\x+624.0*RoomScale, r\y+1888.0*RoomScale, r\z-312.0*RoomScale, r)
			sc\angle = 90
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			
			r\Objects[6] = CreatePivot()
			PositionEntity r\Objects[6],r\x+1110.0*RoomScale,r\y+36.0*RoomScale,r\z-208.0*RoomScale
			EntityParent r\Objects[6],r\obj
			;[End Block]
		Case "room2tunnel"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 2640.0 * RoomScale, -2496.0 * RoomScale, r\z + 400.0 * RoomScale)
			EntityParent(r\Objects[0], r\obj)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 4336.0 * RoomScale, -2496.0 * RoomScale, r\z - 2512.0 * RoomScale)
			EntityParent(r\Objects[1], r\obj)
			
			r\Objects[2] = CreatePivot()
			RotateEntity r\Objects[2],0.0,180.0,0.0,True
			PositionEntity(r\Objects[2], r\x + 552.0 * RoomScale, 240.0 * RoomScale, r\z + 656.0 * RoomScale)
			EntityParent(r\Objects[2], r\obj)
;			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 552.0 * RoomScale, 240.0 * RoomScale, r\z - 656.0 * RoomScale)
			EntityParent(r\Objects[4], r\obj)
;			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 264.0 * RoomScale, 0.0, r\z + 656.0 * RoomScale, 90, r, True, 3)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 224.0 * RoomScale, 0.7, r\z + 480.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 304.0 * RoomScale, 0.7, r\z + 832.0 * RoomScale, True)			
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x - 264.0 * RoomScale, 0.0, r\z - 656.0 * RoomScale, 90, r, True, 3)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = True
			PositionEntity(r\RoomDoors[2]\buttons[0], r\x - 224.0 * RoomScale, 0.7, r\z - 480.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[2]\buttons[1], r\x - 304.0 * RoomScale, 0.7, r\z - 832.0 * RoomScale, True)
;			
			temp = ((Int(AccessCode)*3) Mod 10000)
			If temp < 1000 Then temp = temp+1000
			d.Doors = CreateDoor(0, r\x,r\y,r\z,0, r, False, True, False, temp)
			PositionEntity(d\buttons[0], r\x + 224.0 * RoomScale, r\y + 0.7, r\z - 384.0 * RoomScale, True)
			RotateEntity (d\buttons[0], 0,-90,0,True)
			PositionEntity(d\buttons[1], r\x - 224.0 * RoomScale, r\y + 0.7, r\z + 384.0 * RoomScale, True)		
			RotateEntity (d\buttons[1], 0,90,0,True)
			
			de.Decals = CreateDecal(0, r\x + 64.0 * RoomScale, 0.005, r\z + 144.0 * RoomScale, 90, Rand(360), 0)
			EntityParent(de\obj, r\obj)
			it = CreateItem("Scorched Note", "paper", r\x + 64.0 * RoomScale, r\y +144.0 * RoomScale, r\z - 384.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "008"
			;[Block]
			;the container
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x + 292.0 * RoomScale, 130.0*RoomScale, r\z + 516.0 * RoomScale, True)
			
			;the lid of the container
			r\Objects[1] = LoadMesh_Strict("GFX\map\008_2.b3d")
			ScaleEntity r\Objects[1], RoomScale, RoomScale, RoomScale
			PositionEntity(r\Objects[1], r\x + 292 * RoomScale, 151 * RoomScale, r\z + 576.0 * RoomScale, 0)
			EntityParent(r\Objects[1], r\obj)
			
			RotateEntity(r\Objects[1],89,0,0,True)
			
			r\Levers[0] = r\Objects[1]
			
			Glasstex = LoadTexture_Strict("GFX\map\glass.png",1+2)
			r\Objects[2] = CreateSprite()
			EntityTexture(r\Objects[2],Glasstex)
			SpriteViewMode(r\Objects[2],2)
			ScaleSprite(r\Objects[2],256.0*RoomScale*0.5, 194.0*RoomScale*0.5)
			PositionEntity(r\Objects[2], r\x - 176.0 * RoomScale, 224.0*RoomScale, r\z + 448.0 * RoomScale)
			TurnEntity(r\Objects[2],0,90,0)			
			EntityParent(r\Objects[2], r\obj)
			
			FreeTexture Glasstex
			
			;scp-173 spawnpoint
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x - 445.0 * RoomScale, 120.0*RoomScale, r\z + 544.0 * RoomScale, True)
			
			;scp-173 attack point
			r\Objects[4] = CreatePivot(r\obj)
			PositionEntity(r\Objects[4], r\x + 67.0 * RoomScale, 120.0*RoomScale, r\z + 464.0 * RoomScale, True)
			
			r\Objects[5] = CreateSprite()
			PositionEntity(r\Objects[5], r\x - 158 * RoomScale, 368 * RoomScale, r\z + 298.0 * RoomScale)
			ScaleSprite(r\Objects[5], 0.02, 0.02)
			EntityTexture(r\Objects[5], LightSpriteTex(1))
			EntityBlend (r\Objects[5], 3)
			EntityParent(r\Objects[5], r\obj)
			HideEntity r\Objects[5]
			
			d = CreateDoor(r\zone, r\x + 296.0 * RoomScale, 0, r\z - 672.0 * RoomScale, 180, r, True, 0, 4)
			d\AutoClose = False
			PositionEntity (d\buttons[1], r\x + 164.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			FreeEntity d\buttons[0] : d\buttons[0]=0
			FreeEntity d\obj2 : d\obj2=0
			r\RoomDoors[0] = d
			
			d2 = CreateDoor(r\zone, r\x + 296.0 * RoomScale, 0, r\z - 144.0 * RoomScale, 0, r, False)
			d2\AutoClose = False
			PositionEntity (d2\buttons[0], r\x + 432.0 * RoomScale, EntityY(d2\buttons[0],True), r\z - 480.0 * RoomScale, True)
			RotateEntity(d2\buttons[0], 0, -90, 0, True)			
			PositionEntity (d2\buttons[1], r\x + 164.0 * RoomScale, EntityY(d2\buttons[0],True), r\z - 128.0 * RoomScale, True)
			FreeEntity d2\obj2 : d2\obj2=0
			r\RoomDoors[1] = d2
			
			d\LinkedDoor = d2
			d2\LinkedDoor = d
			
			d = CreateDoor(r\zone, r\x - 384.0 * RoomScale, 0, r\z - 672.0 * RoomScale, 0, r, False, 0, 4)
			d\AutoClose = False : d\locked = True : r\RoomDoors[2]=d
			
			
			it = CreateItem("Hazmat Suit", "hazmatsuit", r\x - 76.0 * RoomScale, 0.5, r\z - 396.0 * RoomScale)
			EntityParent(it\collider, r\obj) : RotateEntity(it\collider, 0, 90, 0)
			If Server\Breach = False Then
				it = CreateItem("Document SCP-008", "paper", r\x - 245.0 * RoomScale, r\y + 192.0 * RoomScale, r\z + 368.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x - 245.0 * RoomScale, r\y + 192.0 * RoomScale, r\z + 368.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			;spawnpoint for the scientist used in the "008 zombie scene"
			r\Objects[6] = CreatePivot(r\obj)
			PositionEntity(r\Objects[6], r\x + 160 * RoomScale, 672 * RoomScale, r\z - 384.0 * RoomScale, True)
			;spawnpoint for the player
			r\Objects[7] = CreatePivot(r\obj)
			PositionEntity(r\Objects[7], r\x, 672 * RoomScale, r\z + 352.0 * RoomScale, True)
			
			sc.SecurityCams = CreateSecurityCam(r\x+578.956*RoomScale, r\y+444.956*RoomScale, r\z+772.0*RoomScale, r)
			sc\angle = 135
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;[End Block]
		Case "room035"
			;[Block]
			d = CreateDoor(r\zone, r\x - 296.0 * RoomScale, 0, r\z - 672.0 * RoomScale, 180, r, True, 0, 5)
			d\AutoClose = False : d\locked = True : r\RoomDoors[0]=d
			PositionEntity (d\buttons[1], r\x - 164.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			FreeEntity d\buttons[0] : d\buttons[0]=0
			FreeEntity d\obj2 : d\obj2=0
			
			d2 = CreateDoor(r\zone, r\x - 296.0 * RoomScale, 0, r\z - 144.0 * RoomScale, 0, r, False)
			d2\AutoClose = False : d2\locked = True : r\RoomDoors[1]=d2
			PositionEntity (d2\buttons[0], r\x - 432.0 * RoomScale, EntityY(d2\buttons[0],True), r\z - 480.0 * RoomScale, True)
			RotateEntity(d2\buttons[0], 0, 90, 0, True)
			FreeEntity d2\buttons[1] : d2\buttons[1]=0
			FreeEntity d2\obj2 : d2\obj2=0
			
			;door to the control room
			r\RoomDoors[2] = CreateDoor(r\zone, r\x + 384.0 * RoomScale, 0, r\z - 672.0 * RoomScale, 180, r, False, 0, 5)
			r\RoomDoors[2]\AutoClose = False
			
			;door to the storage room
			r\RoomDoors[3] = CreateDoor(0, r\x + 768.0 * RoomScale, 0, r\z +512.0 * RoomScale, 90, r, False, 0, 0, "5731")
			r\RoomDoors[3]\AutoClose = False			
			
			d\LinkedDoor = d2 : d2\LinkedDoor = d
			
			For i = 0 To 1
				r\Objects[i*2] = CopyEntity(LeverBaseOBJ)
				r\Objects[i*2+1] = CopyEntity(LeverOBJ)
				
				r\Levers[i] = r\Objects[i*2+1]
				
				For n% = 0 To 1
					ScaleEntity(r\Objects[i*2+n], 0.04, 0.04, 0.04)
					PositionEntity (r\Objects[i*2+n], r\x + 210.0 * RoomScale, r\y + 224.0 * RoomScale, r\z - (208-i*76) * RoomScale, True)
					
					EntityParent(r\Objects[i*2+n], r\obj)
				Next
				
				RotateEntity(r\Objects[i*2], 0, -90-180, 0)
				RotateEntity(r\Objects[i*2+1], -80, -90, 0)
				
				EntityPickMode r\Objects[i*2+1], 1, False
				EntityRadius r\Objects[i*2+1], 0.1				
			Next
			
			;the control room
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x + 456 * RoomScale, 0.5, r\z + 400.0 * RoomScale, True)
			
			r\Objects[4] = CreatePivot(r\obj)
			PositionEntity(r\Objects[4], r\x - 576 * RoomScale, 0.5, r\z + 640.0 * RoomScale, True)
			
			For i = 0 To 1
				em.Emitters = CreateEmitter(r\x - 272.0 * RoomScale, 10, r\z + (624.0-i*512) * RoomScale, 0)
				TurnEntity(em\Obj, 90, 0, 0, True)
				EntityParent(em\Obj, r\obj)
				em\RandAngle = 15
				em\Speed = 0.05
				em\SizeChange = 0.007
				em\Achange = -0.006
				em\Gravity = -0.24
				
				r\Objects[5+i]=em\Obj
			Next
			
			;the corners of the cont chamber (needed to calculate whether the player is inside the chamber)
			r\Objects[7] = CreatePivot(r\obj)
			PositionEntity(r\Objects[7], r\x - 720 * RoomScale, 0.5, r\z + 880.0 * RoomScale, True)
			r\Objects[8] = CreatePivot(r\obj)
			PositionEntity(r\Objects[8], r\x + 176 * RoomScale, 0.5, r\z - 144.0 * RoomScale, True)			
			
			it = CreateItem("SCP-035 Addendum", "paper", r\x + 248.0 * RoomScale, r\y + 220.0 * RoomScale, r\z + 576.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			If Server\Breach = False Then
				it = CreateItem("Radio Transceiver", "radio", r\x - 544.0 * RoomScale, 0.5, r\z + 704.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x - 544.0 * RoomScale, 0.5, r\z + 704.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("SCP-500-01", "scp500", r\x + 1168*RoomScale, 224*RoomScale, r\z+576*RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Metal Panel", "scp148", r\x - 360 * RoomScale, 0.5, r\z + 644 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Document SCP-035", "paper", r\x + 1168.0 * RoomScale, 104.0 * RoomScale, r\z + 608.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room513"
			;[Block]
			d = CreateDoor(r\zone, r\x - 704.0 * RoomScale, 0, r\z + 304.0 * RoomScale, 0, r, False, 0, 2)
			d\AutoClose = False ;: d\buttons[0] = False
			PositionEntity (d\buttons[0], EntityX(d\buttons[0],True), EntityY(d\buttons[0],True), r\z + 288.0 * RoomScale, True)
			PositionEntity (d\buttons[1], EntityX(d\buttons[1],True), EntityY(d\buttons[1],True), r\z + 320.0 * RoomScale, True)
			
			sc.SecurityCams = CreateSecurityCam(r\x-312.0 * RoomScale, r\y + 414*RoomScale, r\z + 656*RoomScale, r)
			sc\FollowPlayer = True
			
			it = CreateItem("SCP-513", "scp513", r\x - 60.0 * RoomScale, r\y + 196.0 * RoomScale, r\z + 688.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Blood-stained Note", "paper", r\x + 736.0 * RoomScale,1.0, r\z + 48.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Document SCP-513", "paper", r\x - 480.0 * RoomScale, 104.0*RoomScale, r\z - 176.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			If Rand(0, 1) And Server\Breach Then
				it = CreateItem("SCP-035", "scp035", r\x - 600.0 * RoomScale, r\y + 196.0 * RoomScale, r\z + 688.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			;[End Block]
		Case "room966"
			;[Block]
			d = CreateDoor(r\zone, r\x - 400.0 * RoomScale, 0, r\z, -90, r, False, False, 3)
			d = CreateDoor(r\zone, r\x, 0, r\z - 480.0 * RoomScale, 180, r, False, False, 3)
			;: d\buttons[0] = False
			;PositionEntity (d\buttons[0], EntityX(d\buttons[0],True), EntityY(d\buttons[0],True), r\z + 288.0 * RoomScale, True)
			;PositionEntity (d\buttons[1], EntityX(d\buttons[1],True), EntityY(d\buttons[1],True), r\z + 320.0 * RoomScale, True)
			
			sc.SecurityCams = CreateSecurityCam(r\x-312.0 * RoomScale, r\y + 414*RoomScale, r\z + 656*RoomScale, r)
			sc\angle = 225
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;sc\FollowPlayer = True
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x, 0.5, r\z + 512.0 * RoomScale, True)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x + 64.0 * RoomScale, 0.5, r\z - 640.0 * RoomScale, True)
			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x, 0.5, r\z, True)
			
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x + 320.0 * RoomScale, 0.5, r\z + 704.0 * RoomScale, True)
			
			it = CreateItem("Night Vision Goggles", "nvgoggles", r\x + 320.0 * RoomScale, 0.5, r\z + 704.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			it\state = 300
			
			;[End Block]
		Case "room3storage"
			;[Block]
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x, 240.0 * RoomScale, r\z + 752.0 * RoomScale, True)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x + 5840.0 * RoomScale, -5392.0 * RoomScale, r\z + 1360.0 * RoomScale, True)
			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x + 608.0 * RoomScale, 240.0 * RoomScale, r\z - 624.0 * RoomScale, True)
			
			r\Objects[3] = CreatePivot(r\obj)
			;PositionEntity(r\Objects[3], r\x + 720.0 * RoomScale, -5392.0 * RoomScale, r\z + 752.0 * RoomScale, True)
			PositionEntity(r\Objects[3], r\x - 456.0 * RoomScale, -5392.0 * RoomScale, r\z - 1136 * RoomScale, True)
			
			;"waypoints" # 1
			r\Objects[4] = CreatePivot(r\obj)
			PositionEntity(r\Objects[4], r\x + 2128.0 * RoomScale, -5550.0 * RoomScale, r\z + 2048.0 * RoomScale, True)
			
			r\Objects[5] = CreatePivot(r\obj)
			PositionEntity(r\Objects[5], r\x + 2128.0 * RoomScale, -5550.0 * RoomScale, r\z - 1136.0 * RoomScale, True)
			
			r\Objects[6] = CreatePivot(r\obj)
			PositionEntity(r\Objects[6], r\x + 3824.0 * RoomScale, -5550.0 * RoomScale, r\z - 1168.0 * RoomScale, True)
			
			r\Objects[7] = CreatePivot(r\obj)
			PositionEntity(r\Objects[7], r\x + 3760.0 * RoomScale, -5550.0 * RoomScale, r\z + 2048.0 * RoomScale, True)
			
			r\Objects[8] = CreatePivot(r\obj)
			PositionEntity(r\Objects[8], r\x + 4848.0 * RoomScale, -5550.0 * RoomScale, r\z + 112.0 * RoomScale, True)
			
			;"waypoints" # 2
			r\Objects[9] = CreatePivot(r\obj)
			PositionEntity(r\Objects[9], r\x + 592.0 * RoomScale, -5550.0 * RoomScale, r\z + 6352.0 * RoomScale, True)
			
			r\Objects[10] = CreatePivot(r\obj)
			PositionEntity(r\Objects[10], r\x + 2928.0 * RoomScale, -5550.0 * RoomScale, r\z + 6352.0 * RoomScale, True)
			
			r\Objects[11] = CreatePivot(r\obj)
			PositionEntity(r\Objects[11], r\x + 2928.0 * RoomScale, -5550.0 * RoomScale, r\z + 5200.0 * RoomScale, True)
			
			r\Objects[12] = CreatePivot(r\obj)
			PositionEntity(r\Objects[12], r\x + 592.0 * RoomScale, -5550.0 * RoomScale, r\z + 5200.0 * RoomScale, True)
			
			;"waypoints" # 3
			r\Objects[13] = CreatePivot(r\obj)
			PositionEntity(r\Objects[13], r\x + 1136.0 * RoomScale, -5550.0 * RoomScale, r\z + 2944.0 * RoomScale, True)
			
			r\Objects[14] = CreatePivot(r\obj)
			PositionEntity(r\Objects[14], r\x + 1104.0 * RoomScale, -5550.0 * RoomScale, r\z + 1184.0 * RoomScale, True)
			
			r\Objects[15] = CreatePivot(r\obj)
			PositionEntity(r\Objects[15], r\x - 464.0 * RoomScale,  -5550.0 * RoomScale, r\z + 1216.0 * RoomScale, True)
			
			r\Objects[16] = CreatePivot(r\obj)
			PositionEntity(r\Objects[16], r\x - 432.0 * RoomScale, -5550.0 * RoomScale, r\z + 2976.0 * RoomScale, True)
			
			r\Objects[20] = LoadMesh_Strict("GFX\map\room3storage_hb.b3d",r\obj)
			EntityPickMode r\Objects[20],2
			EntityType r\Objects[20],HIT_MAP
			EntityAlpha r\Objects[20],0.0
			
			;Doors
			r\RoomDoors[0] = CreateDoor(r\zone, r\x, 0.0, r\z + 448.0 * RoomScale, 0, r, True, 3)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x - 160.0 * RoomScale, 0.7, r\z + 480.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 160.0 * RoomScale, 0.7, r\z + 416.0 * RoomScale, True)	
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 5840.0 * RoomScale,  -5632.0 * RoomScale, r\z + 1048.0 * RoomScale, 0, r, False, 3)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
			PositionEntity(r\RoomDoors[1]\buttons[0], r\x + 6000.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[0],True), r\z + 1008.0 * RoomScale, True)					
			PositionEntity(r\RoomDoors[1]\buttons[1], r\x + 5680.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[1],True), r\z + 1088.0 * RoomScale, True)
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x + 608.0 * RoomScale, 0.0, r\z - 312.0 * RoomScale, 0, r, True, 3)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = True
			PositionEntity(r\RoomDoors[2]\buttons[1], r\x + 448.0 * RoomScale, 0.7, r\z - 272.0 * RoomScale, True)	
			PositionEntity(r\RoomDoors[2]\buttons[0], r\x + 768.0 * RoomScale, 0.7, r\z - 352.0 * RoomScale, True)
			
			;r\RoomDoors[3] = CreateDoor(r\zone, r\x + 720.0 * RoomScale,  -5632.0 * RoomScale, r\z + 1064.0 * RoomScale, 0, r, False)
			;PositionEntity(r\RoomDoors[3]\buttons[0], r\x + 896.0 * RoomScale, EntityY(r\RoomDoors[3]\buttons[0],True), r\z + 1024.0 * RoomScale, True)
			;PositionEntity(r\RoomDoors[3]\buttons[1], r\x + 544.0 * RoomScale, EntityY(r\RoomDoors[3]\buttons[1],True), r\z + 1104.0 * RoomScale, True)
			r\RoomDoors[3] = CreateDoor(r\zone, r\x - 456.0 * RoomScale,  -5632.0 * RoomScale, r\z - 824.0 * RoomScale, 0, r, False, 3)
			r\RoomDoors[3]\AutoClose = False : r\RoomDoors[3]\open = False
			;X=+176 | Z=-40
			PositionEntity r\RoomDoors[3]\buttons[0], r\x - 280.0*RoomScale, EntityY(r\RoomDoors[3]\buttons[0],True), r\z - 864.0 * RoomScale, True
			;X=-176 | Z=+40
			PositionEntity r\RoomDoors[3]\buttons[1], r\x - 632.0*RoomScale, EntityY(r\RoomDoors[3]\buttons[1],True), r\z - 784.0 * RoomScale, True
			
			em.Emitters = CreateEmitter(r\x + 5218.0 * RoomScale, -5584.0*RoomScale, r\z - 600* RoomScale, 0)
			TurnEntity(em\Obj, 20, -100, 0, True)
			EntityParent(em\Obj, r\obj) : em\Room = r
			em\RandAngle = 15 : em\Speed = 0.03
			em\SizeChange = 0.01 : em\Achange = -0.006
			em\Gravity = -0.2 
			
			Select Rand(3)
				Case 1
					x# = 2312
					z#=-952
				Case 2
					x# = 3032
					z#=1288
				Case 3
					x# = 2824
					z#=2808
			End Select
			
			it.Items = CreateItem("Black Severed Hand", "hand2", r\x + x*RoomScale, -5596.0*RoomScale+1.0, r\z+z*RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Night Vision Goggles", "nvgoggles", r\x + 1936.0 * RoomScale, r\y - 5496.0 * RoomScale, r\z - 944.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			it\state = 450
			
			de.Decals = CreateDecal(3,  r\x + x*RoomScale, -5632.0*RoomScale+0.01, r\z+z*RoomScale,90,Rnd(360),0)
			de\Size = 0.5
			ScaleSprite(de\obj, de\Size,de\Size)
			EntityParent de\obj, r\obj
			
			;Objects [20],[21],[22],[23]
			For n% = 10 To 11
				r\Objects[n * 2] = CopyEntity(LeverBaseOBJ)
				r\Objects[n * 2 + 1] = CopyEntity(LeverOBJ)
				
				r\Levers[n-10] = r\Objects[n * 2 + 1]
				
				For  i% = 0 To 1
					ScaleEntity(r\Objects[n * 2 + i], 0.04, 0.04, 0.04)
					If n% = 10
						;r\z+6578
						PositionEntity r\Objects[n * 2 + i],r\x+3101*RoomScale,r\y-5461*RoomScale,r\z+6568*RoomScale,True
					Else
						;r\z+3174
						PositionEntity r\Objects[n * 2 + i],r\x+1209*RoomScale,r\y-5461*RoomScale,r\z+3164*RoomScale,True
					EndIf
					
					EntityParent(r\Objects[n * 2 + i], r\obj)
				Next
				RotateEntity(r\Objects[n * 2], 0, 0, 0)
				RotateEntity(r\Objects[n * 2 + 1], -10, 0 - 180, 0)
				
				EntityPickMode r\Objects[n * 2 + 1], 1, False
				EntityRadius r\Objects[n * 2 + 1], 0.1
			Next
			
			r\RoomDoors[4] = CreateDoor(r\zone,r\x+56*RoomScale,r\y-5632*RoomScale,r\z+6344*RoomScale,90,r,False,2)
			r\RoomDoors[4]\AutoClose = False : r\RoomDoors[4]\open = False
			For i = 0 To 1
				FreeEntity r\RoomDoors[4]\buttons[i] : r\RoomDoors[4]\buttons[i] = 0
			Next
			
			d = CreateDoor(r\zone,r\x+1157.0*RoomScale,r\y-5632.0*RoomScale,r\z+660.0*RoomScale,0,r,False,2)
			d\locked = True : d\open = False : d\AutoClose = False
			For i = 0 To 1
				FreeEntity d\buttons[i] : d\buttons[i]=0
			Next
			
			d = CreateDoor(r\zone,r\x+234.0*RoomScale,r\y-5632.0*RoomScale,r\z+5239.0*RoomScale,90,r,False,2)
			d\locked = True : d\open = False : d\AutoClose = False
			For i = 0 To 1
				FreeEntity d\buttons[i] : d\buttons[i]=0
			Next
			
			d = CreateDoor(r\zone,r\x+3446.0*RoomScale,r\y-5632.0*RoomScale,r\z+6369.0*RoomScale,90,r,False,2)
			d\locked = True : d\open = False : d\AutoClose = False
			For i = 0 To 1
				FreeEntity d\buttons[i] : d\buttons[i]=0
			Next
			;[End Block]
		Case "room049"
			;[Block]
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x + 640.0 * RoomScale, 240.0 * RoomScale, r\z + 656.0 * RoomScale, True)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x + 3211.0 * RoomScale, -3280.0 * RoomScale, r\z + 1824.0 * RoomScale, True)
			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x - 672.0 * RoomScale, 240.0 * RoomScale, r\z - 93.0 * RoomScale, True)
			
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x - 2766.0 * RoomScale, -3280.0 * RoomScale, r\z - 1277.0 * RoomScale, True)
			
			;zombie 1
			r\Objects[4] = CreatePivot(r\obj)
			PositionEntity(r\Objects[4], r\x + 528.0 * RoomScale, -3440.0 * RoomScale, r\z + 96.0 * RoomScale, True)
			;zombie 2
			r\Objects[5] = CreatePivot(r\obj)
			PositionEntity(r\Objects[5], r\x  + 64.0 * RoomScale, -3440.0 * RoomScale, r\z - 1000.0 * RoomScale, True)
			
			For n% = 0 To 1
				r\Objects[n * 2 + 6] = CopyEntity(LeverBaseOBJ)
				r\Objects[n * 2 + 7] = CopyEntity(LeverOBJ)
				
				r\Levers[n] = r\Objects[n * 2 + 7]
				
				For i% = 0 To 1
					ScaleEntity(r\Objects[n * 2 + 6 + i], 0.03, 0.03, 0.03)
					
					Select n
						Case 0 ;power feed
							PositionEntity (r\Objects[n * 2 + 6 + i], r\x + 852.0 * RoomScale, r\y - 3374.0 * RoomScale, r\z - 854.0 * RoomScale, True)
							
						Case 1 ;generator
							PositionEntity (r\Objects[n * 2 + 6 + i], r\x - 834.0 * RoomScale, r\y - 3400.0 * RoomScale, r\z + 1093.0 * RoomScale, True)
							
					End Select
					
					EntityParent(r\Objects[n * 2 + 6 + i], r\obj)
				Next
				
				RotateEntity(r\Objects[n*2+6], 0, 180+90*(Not n), 0)
				RotateEntity(r\Objects[n*2+7], 81-92*n, 90*(Not n), 0)
				
				EntityPickMode r\Objects[n * 2 + 7], 1, False
				EntityRadius r\Objects[n * 2 + 7], 0.1
			Next
			
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 330.0 * RoomScale, 0.0, r\z + 656.0 * RoomScale, 90, r, True, 3)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 288.0 * RoomScale, 0.7, r\z + 512.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 368.0 * RoomScale, 0.7, r\z + 840.0 * RoomScale, True)
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 2898.0 * RoomScale, -3520.0 * RoomScale, r\z + 1824.0 * RoomScale, 90, r, False, 3)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False	
			PositionEntity(r\RoomDoors[1]\buttons[1], r\x + 2881.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[1],True), r\z + 1663.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[1]\buttons[0], r\x + 2936.0 * RoomScale, EntityY(r\RoomDoors[1]\buttons[0],True), r\z + 2009.0 * RoomScale, True)				
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x - 672.0 * RoomScale, 0.0, r\z - 408.0 * RoomScale, 0, r, True, 3)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = True
			PositionEntity(r\RoomDoors[2]\buttons[0], r\x - 487.0 * RoomScale, 0.7, r\z - 447.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[2]\buttons[1], r\x - 857.0 * RoomScale, 0.7, r\z - 369.0 * RoomScale, True)				
			
			r\RoomDoors[3] = CreateDoor(r\zone, r\x - 2766.0 * RoomScale, -3520.0 * RoomScale, r\z - 1592.0 * RoomScale, 0, r, False, 3)
			r\RoomDoors[3]\AutoClose = False : r\RoomDoors[3]\open = False		
			PositionEntity(r\RoomDoors[3]\buttons[0], r\x - 2581.0 * RoomScale, EntityY(r\RoomDoors[3]\buttons[0],True), r\z - 1631.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[3]\buttons[1], r\x - 2951.0 * RoomScale, EntityY(r\RoomDoors[3]\buttons[1],True), r\z - 1553.0 * RoomScale, True)	
			
			;For i = 0 To 3
			;	If (i Mod 2) = 1
			;		AssignElevatorObj(r\Objects[i],r\RoomDoors[i],2)
			;	Else
			;		AssignElevatorObj(r\Objects[i],r\RoomDoors[i],True)
			;	EndIf
			;Next
			
			;storage room doors
			r\RoomDoors[4] = CreateDoor(r\zone, r\x + 272.0 * RoomScale, -3552.0 * RoomScale, r\z + 104.0 * RoomScale, 90, r, False)
			r\RoomDoors[4]\AutoClose = False : r\RoomDoors[4]\open = True : r\RoomDoors[4]\locked = True
			r\RoomDoors[5] = CreateDoor(r\zone, r\x + 264.0 * RoomScale, -3520.0 * RoomScale, r\z - 1824.0 * RoomScale, 90, r, False)
			r\RoomDoors[5]\AutoClose = False : r\RoomDoors[5]\open = True : r\RoomDoors[5]\locked = True
			r\RoomDoors[6] = CreateDoor(r\zone, r\x - 264.0 * RoomScale, -3520.0 * RoomScale, r\z + 1824.0 * RoomScale, 90, r, False)
			r\RoomDoors[6]\AutoClose = False : r\RoomDoors[6]\open = True : r\RoomDoors[6]\locked = True
			
			r\RoomDoors[7] = CreateDoor(0, r\x,0,r\z, 0, r, False, 2, -2)
			
			it = CreateItem("Document SCP-049", "paper", r\x - 608.0 * RoomScale, r\y - 3332.0 * RoomScale, r\z + 876.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Level 4 Key Card", "key4", r\x - 512.0 * RoomScale, r\y - 3412.0 * RoomScale, r\z + 864.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("First Aid Kit", "firstaid", r\x +385.0 * RoomScale, r\y - 3412.0 * RoomScale, r\z + 271.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			r\RoomDoors[8] = CreateDoor(r\zone,r\x-272.0*RoomScale,r\y-3552.0*RoomScale,r\z+98.0*RoomScale,90,r,True,True)
			r\RoomDoors[8]\AutoClose = False : r\RoomDoors[8]\open = True : r\RoomDoors[8]\MTFClose = False : r\RoomDoors[8]\locked = True
			For i = 0 To 1
				FreeEntity(r\RoomDoors[8]\buttons[i]) : r\RoomDoors[8]\buttons[i]=0
			Next
			
			d = CreateDoor(r\zone,r\x-2990.0*RoomScale,r\y-3520.0*RoomScale,r\z-1824.0*RoomScale,90,r,False,2)
			d\locked = True : d\DisableWaypoint = True
			d = CreateDoor(r\zone,r\x-896.0*RoomScale,r\y,r\z-640*RoomScale,90,r,False,2)
			d\locked = True : d\DisableWaypoint = True
			
			r\Objects[10] = CreatePivot(r\obj)
			PositionEntity r\Objects[10],r\x-832.0*RoomScale,r\y-3484.0*RoomScale,r\z+1572.0*RoomScale,True
			
			;Spawnpoint for the map layout document
			r\Objects[11] = CreatePivot(r\obj)
			PositionEntity r\Objects[11],r\x+2642.0*RoomScale,r\y-3516.0*RoomScale,r\z+1822.0*RoomScale,True
			r\Objects[12] = CreatePivot(r\obj)
			PositionEntity r\Objects[12],r\x-2666.0*RoomScale,r\y-3516.0*RoomScale,r\z-1792.0*RoomScale,True
			;[End Block]
		Case "room2_2"
			;[Block]
			For r2.Rooms = Each Rooms
				If r2<>r Then
					If r2\RoomTemplate\Name = "room2_2" Then
						r\Objects[0] = CopyEntity(r2\Objects[0]) ;don't load the mesh again
						Exit
					EndIf
				EndIf
			Next
			If r\Objects[0]=0 Then r\Objects[0] = LoadMesh_Strict("GFX\map\fan.b3d")
			ScaleEntity r\Objects[0], RoomScale, RoomScale, RoomScale
			PositionEntity(r\Objects[0], r\x - 248 * RoomScale, 528 * RoomScale, r\z, 0)
			EntityParent(r\Objects[0], r\obj)
			;[End Block]
		Case "room012"
			;[Block]
			d.Doors = CreateDoor(r\zone, r\x + 264.0 * RoomScale, 0.0, r\z + 672.0 * RoomScale, 270, r, False, False, 3)
			PositionEntity(d\buttons[0], r\x + 224.0 * RoomScale, EntityY(d\buttons[0],True), r\z + 540.0 * RoomScale, True)
			PositionEntity(d\buttons[1], r\x + 304.0 * RoomScale, EntityY(d\buttons[1],True), r\z + 840.0 * RoomScale, True)
			TurnEntity d\buttons[1],0,0,0,True
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x -512.0 * RoomScale, -768.0*RoomScale, r\z -336.0 * RoomScale, 0, r, False, False)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False : r\RoomDoors[0]\locked = True
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 176.0 * RoomScale, -512.0*RoomScale, r\z - 364.0 * RoomScale, True)
			FreeEntity r\RoomDoors[0]\buttons[1] : r\RoomDoors[0]\buttons[1]=0
			
			r\Objects[0] = CopyEntity(LeverBaseOBJ)
			r\Objects[1] = CopyEntity(LeverOBJ)
			
			r\Levers[0] = r\Objects[1]
			
			For i% = 0 To 1
				ScaleEntity(r\Objects[i], 0.04, 0.04, 0.04)
				PositionEntity (r\Objects[i], r\x + 240.0 * RoomScale, r\y - 512.0 * RoomScale, r\z - 364 * RoomScale, True)
				
				EntityParent(r\Objects[i], r\obj)
			Next
			;RotateEntity(r\Objects[0], 0, 0, 0)
			RotateEntity(r\Objects[1], 10, -180, 0)
			
			EntityPickMode r\Objects[1], 1, False
			EntityRadius r\Objects[1], 0.1
			
			r\Objects[2] = LoadMesh_Strict("GFX\map\room012_2.b3d")
			ScaleEntity r\Objects[2], RoomScale, RoomScale, RoomScale
			PositionEntity(r\Objects[2], r\x - 360 * RoomScale, - 130 * RoomScale, r\z + 456.0 * RoomScale, 0)
			EntityParent(r\Objects[2], r\obj)
			
			r\Objects[3] = CreateSprite()
			PositionEntity(r\Objects[3], r\x - 43.5 * RoomScale, - 574 * RoomScale, r\z - 362.0 * RoomScale)
			ScaleSprite(r\Objects[3], 0.015, 0.015)
			EntityTexture(r\Objects[3], LightSpriteTex(1))
			EntityBlend (r\Objects[3], 3)
			EntityParent(r\Objects[3], r\obj)
			HideEntity r\Objects[3]
			
			r\Objects[4] = LoadMesh_Strict("GFX\map\room012_3.b3d")
			tex=LoadTexture_Strict("GFX\map\scp-012_0.jpg")
			EntityTexture r\Objects[4],tex, 0,1
			ScaleEntity r\Objects[4], RoomScale, RoomScale, RoomScale
			PositionEntity(r\Objects[4], r\x - 360 * RoomScale, - 130 * RoomScale, r\z + 456.0 * RoomScale, 0)
			EntityParent(r\Objects[4], r\Objects[2])
			FreeTexture tex
			
			it = CreateItem("Document SCP-012", "paper", r\x - 56.0 * RoomScale, r\y - 576.0 * RoomScale, r\z - 408.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it.Items = CreateItem("Severed Hand", "hand", r\x - 784*RoomScale, -576*RoomScale+0.3, r\z+640*RoomScale)
			EntityParent(it\collider, r\obj)
			
			de.Decals = CreateDecal(3,  r\x - 784*RoomScale, -768*RoomScale+0.01, r\z+640*RoomScale,90,Rnd(360),0)
			de\Size = 0.5
			ScaleSprite(de\obj, de\Size,de\Size)
			EntityParent de\obj, r\obj
			;[End Block]
		Case "tunnel2"
			;[Block]
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x, 544.0 * RoomScale, r\z + 512.0 * RoomScale, True)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x, 544.0 * RoomScale, r\z - 512.0 * RoomScale, True)
			;[End Block]
		Case "room2pipes"
			;[Block]
			r\Objects[0]= CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x + 368.0 * RoomScale, 0.0, r\z, True)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x - 368.0 * RoomScale, 0.0, r\z, True)
			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x + 224.0 * RoomScale - 0.005, 192.0 * RoomScale, r\z, True)
			
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x - 224.0 * RoomScale + 0.005, 192.0 * RoomScale, r\z, True)
			;[End Block]
		Case "room3pit"
			;[Block]
			em.Emitters = CreateEmitter(r\x + 512.0 * RoomScale, -76 * RoomScale, r\z - 688 * RoomScale, 0)
			TurnEntity(em\Obj, -90, 0, 0)
			EntityParent(em\Obj, r\obj)
			em\RandAngle = 55
			em\Speed = 0.0005
			em\Achange = -0.015
			em\SizeChange = 0.007
			
			em.Emitters = CreateEmitter(r\x - 512.0 * RoomScale, -76 * RoomScale, r\z - 688 * RoomScale, 0)
			TurnEntity(em\Obj, -90, 0, 0)
			EntityParent(em\Obj, r\obj)
			em\RandAngle = 55
			em\Speed = 0.0005
			em\Achange = -0.015
			em\SizeChange = 0.007
			
			r\Objects[0]= CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x + 704.0 * RoomScale, 112.0*RoomScale, r\z-416.0*RoomScale, True)
			;[End Block]
		Case "room2servers"
			;[Block]
			d.Doors = CreateDoor(0, r\x,0,r\z, 0, r, False, 2, False)
			d\locked = True
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 208.0 * RoomScale, 0.0, r\z - 736.0 * RoomScale, 90, r, True, False, False, "", True)
			r\RoomDoors[0]\AutoClose=False
			r\RoomDoors[1] = CreateDoor(r\zone, r\x - 208.0 * RoomScale, 0.0, r\z + 736.0 * RoomScale, 90, r, True, False, False, "", True)
			r\RoomDoors[1]\AutoClose=False
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x - 672.0 * RoomScale, 0.0, r\z - 1024.0 * RoomScale, 0, r, False, False, False, "GEAR")
			r\RoomDoors[2]\AutoClose=False : r\RoomDoors[2]\DisableWaypoint = True : r\RoomDoors[2]\locked = True
			FreeEntity(r\RoomDoors[2]\buttons[0]) : r\RoomDoors[2]\buttons[0]=0
			FreeEntity(r\RoomDoors[2]\buttons[1]) : r\RoomDoors[2]\buttons[1]=0
			
			For n% = 0 To 2
				r\Objects[n * 2] = CopyEntity(LeverBaseOBJ)
				r\Objects[n * 2 + 1] = CopyEntity(LeverOBJ)
				
				r\Levers[n] = r\Objects[n * 2 + 1]
				
				For i% = 0 To 1
					ScaleEntity(r\Objects[n * 2 + i], 0.03, 0.03, 0.03)
					
					Select n
						Case 0 ;power switch
							PositionEntity (r\Objects[n * 2 + i], r\x - 1260.0 * RoomScale, r\y + 234.0 * RoomScale, r\z + 750 * RoomScale, True)	
						Case 1 ;generator fuel pump
							PositionEntity (r\Objects[n * 2 + i], r\x - 920.0 * RoomScale, r\y + 164.0 * RoomScale, r\z + 898 * RoomScale, True)
						Case 2 ;generator on/off
							PositionEntity (r\Objects[n * 2 + i], r\x - 837.0 * RoomScale, r\y + 152.0 * RoomScale, r\z + 886 * RoomScale, True)
					End Select
					
					EntityParent(r\Objects[n * 2 + i], r\obj)
				Next
				;RotateEntity(r\Objects[n * 2], 0, -90, 0)
				RotateEntity(r\Objects[n*2+1], 81, -180, 0)
				
				;EntityPickMode(r\Objects[n * 2 + 1], 2)
				EntityPickMode r\Objects[n * 2 + 1], 1, False
				EntityRadius r\Objects[n * 2 + 1], 0.1
				;makecollbox(r\Objects[n * 2 + 1])
			Next
			
			RotateEntity(r\Objects[2+1], -81, -180, 0)
			RotateEntity(r\Objects[4+1], -81, -180, 0)
			
			;096 spawnpoint
;			r\Objects[6]=CreatePivot(r\obj)
;			PositionEntity(r\Objects[6], r\x - 848*RoomScale, 0.5, r\z-576*RoomScale, True)
			r\Objects[6]=CreatePivot(r\obj)
			PositionEntity(r\Objects[6],r\x-320*RoomScale,0.5,r\z,True)
			;guard spawnpoint
			r\Objects[7]=CreatePivot(r\obj)
			PositionEntity(r\Objects[7], r\x - 1328.0 * RoomScale, 0.5, r\z + 528*RoomScale, True)
			;the point where the guard walks to
			r\Objects[8]=CreatePivot(r\obj)
			PositionEntity(r\Objects[8], r\x - 1376.0 * RoomScale, 0.5, r\z + 32*RoomScale, True)
			
			r\Objects[9]=CreatePivot(r\obj)
			PositionEntity(r\Objects[9], r\x - 848*RoomScale, 0.5, r\z+576*RoomScale, True)
			;[End Block]
		Case "room3servers"
			;[Block]
			it = CreateItem("9V Battery", "bat", r\x - 132.0 * RoomScale, r\y - 368.0 * RoomScale, r\z - 648.0 * RoomScale)
			EntityParent(it\collider, r\obj)

			it = CreateItem("9V Battery", "bat", r\x - 76.0 * RoomScale, r\y - 368.0 * RoomScale, r\z - 648.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("S-NAV 300 Navigator", "nav", r\x + 124.0 * RoomScale, r\y - 368.0 * RoomScale, r\z - 648.0 * RoomScale)
			it\state = 20 : EntityParent(it\collider, r\obj)
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x + 736.0 * RoomScale, -512.0 * RoomScale, r\z - 400.0 * RoomScale, True)
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x - 552.0 * RoomScale, -512.0 * RoomScale, r\z - 528.0 * RoomScale, True)			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x + 736.0 * RoomScale, -512.0 * RoomScale, r\z + 272.0 * RoomScale, True)
			
			r\Objects[3] = LoadMesh_Strict("GFX\npcs\duck_low_res.b3d")
			ScaleEntity(r\Objects[3], 0.07, 0.07, 0.07)
			tex = LoadTexture_Strict("GFX\npcs\duck2.png")
			EntityTexture r\Objects[3], tex
			PositionEntity (r\Objects[3], r\x + 928.0 * RoomScale, -640*RoomScale, r\z + 704.0 * RoomScale)
			FreeTexture tex
			EntityParent r\Objects[3], r\obj
			;[End Block]
		Case "room3servers2"
			;[Block]
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity(r\Objects[0], r\x - 504.0 * RoomScale, -512.0 * RoomScale, r\z + 271.0 * RoomScale, True)
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x + 628.0 * RoomScale, -512.0 * RoomScale, r\z + 271.0 * RoomScale, True)			
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x - 532.0 * RoomScale, -512.0 * RoomScale, r\z - 877.0 * RoomScale, True)	
			
			it = CreateItem("Document SCP-970", "paper", r\x + 960.0 * RoomScale, r\y - 448.0 * RoomScale, r\z + 251.0 * RoomScale)
			RotateEntity it\collider, 0, r\angle, 0
			EntityParent(it\collider, r\obj)		
			
			it = CreateItem("Gas Mask", "gasmask", r\x + 954.0 * RoomScale, r\y - 504.0 * RoomScale, r\z + 235.0 * RoomScale)
			EntityParent(it\collider, r\obj)		
			;[End Block]
		Case "testroom"
			;[Block]
			For xtemp = 0 To 1
				For ztemp = -1 To 1
					r\Objects[xtemp * 3 + (ztemp + 1)] = CreatePivot()
					PositionEntity(r\Objects[xtemp * 3 + (ztemp + 1)], r\x + (-236.0 + 280.0 * xtemp) * RoomScale, -700.0 * RoomScale, r\z + 384.0 * ztemp * RoomScale)
					EntityParent(r\Objects[xtemp * 3 + (ztemp + 1)], r\obj)
				Next
			Next
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 754.0 * RoomScale, r\y - 1248.0 * RoomScale, r\z)
			EntityParent(r\Objects[6], r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 744.0 * RoomScale, r\y - 856.0 * RoomScale, r\z + 236.0 * RoomScale, r)
			sc\FollowPlayer = True
			
			r\RoomDoors[0] = CreateDoor(0, r\x + 720.0 * RoomScale, 0, r\z, 0, r, False, 2, -1)
			
			r\RoomDoors[1] = CreateDoor(0, r\x - 624.0 * RoomScale, -1280.0 * RoomScale, r\z, 90, r, True)			
			
			it = CreateItem("Document SCP-682", "paper", r\x + 656.0 * RoomScale, r\y - 1200.0 * RoomScale, r\z - 16.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room2closets"
			;[Block]
			If Server\Breach = False Then
				it = CreateItem("Document SCP-1048", "paper", r\x + 736.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 736.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x + 736.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 736.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Gas Mask", "gasmask", r\x + 736.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 544.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			If Not Server\Breach Then
				it = CreateItem("9V Battery", "bat", r\x + 736.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 448.0 * RoomScale)
				EntityParent(it\collider, r\obj)

				it = CreateItem("9V Battery", "bat", r\x + 730.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 496.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("Small First Aid Kit", "finefirstaid", r\x + 736.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 448.0 * RoomScale)
				EntityParent(it\collider, r\obj)

				it = CreateItem("Small First Aid Kit", "finefirstaid", r\x + 730.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 496.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			
			it = CreateItem("Level 1 Key Card", "key1", r\x + 736.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 752.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it.Items = CreateItem("Clipboard","clipboard",r\x + 736.0 * RoomScale, r\y + 224.0 * RoomScale, r\z -480.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Incident Report SCP-1048-A", "paper",r\x + 736.0 * RoomScale, r\y + 224.0 * RoomScale, r\z -480.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			r\Objects[0]=CreatePivot(r\obj)
			PositionEntity r\Objects[0], r\x-1120*RoomScale, -256*RoomScale, r\z+896*RoomScale, True
			r\Objects[1]=CreatePivot(r\obj)
			PositionEntity r\Objects[1], r\x-1232*RoomScale, -256*RoomScale, r\z-160*RoomScale, True
			
			d.Doors = CreateDoor(0, r\x - 240.0 * RoomScale, 0.0, r\z, 90, r, False)
			PositionEntity(d\buttons[0], r\x - 230.0 * RoomScale, EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			PositionEntity(d\buttons[1], r\x - 250.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			d\open = False : d\AutoClose = False
			
			sc.SecurityCams = CreateSecurityCam(r\x, r\y + 704*RoomScale, r\z + 863*RoomScale, r)
			sc\angle = 180
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;sc\FollowPlayer = True
			;[End Block]
		Case "room2offices"
			;[Block]
			If Server\Breach = False Then
				it = CreateItem("Document SCP-106", "paper", r\x + 404.0 * RoomScale, r\y + 145.0 * RoomScale, r\z + 559.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x + 404.0 * RoomScale, r\y + 145.0 * RoomScale, r\z + 559.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Level 2 Key Card", "key2", r\x - 156.0 * RoomScale, r\y + 151.0 * RoomScale, r\z + 72.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("S-NAV 300 Navigator", "nav", r\x + 305.0 * RoomScale, r\y + 153.0 * RoomScale, r\z + 944.0 * RoomScale)
			it\state = 20 : EntityParent(it\collider, r\obj)
			
			it = CreateItem("Notification", "paper", r\x -137.0 * RoomScale, r\y + 153.0 * RoomScale, r\z + 464.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			w.waypoints = CreateWaypoint(r\x - 32.0 * RoomScale, r\y + 66.0 * RoomScale, r\z + 288.0 * RoomScale, Null, r)
			w2.waypoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z - 448.0 * RoomScale, Null, r)
			w\connected[0] = w2 : w\dist[0] = EntityDistance(w\obj, w2\obj)
			w2\connected[0] = w : w2\dist[0] = w\dist[0]
			;[End Block]
		Case "room2offices2"
			;[Block]
			it = CreateItem("Level 1 Key Card", "key1", r\x - 368.0 * RoomScale, r\y - 48.0 * RoomScale, r\z + 80.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Document SCP-895", "paper", r\x - 800.0 * RoomScale, r\y - 48.0 * RoomScale, r\z + 368.0 * RoomScale)
			EntityParent(it\collider, r\obj)

			it = CreateItem("Document SCP-860", "paper", r\x - 800.0 * RoomScale, r\y - 48.0 * RoomScale, r\z - 464.0 * RoomScale)

			EntityParent(it\collider, r\obj)
			
			it = CreateItem("S-NAV 300 Navigator", "nav", r\x - 336.0 * RoomScale, r\y - 48.0 * RoomScale, r\z - 480.0 * RoomScale)
			it\state = 28 : EntityParent(it\collider, r\obj)		
			
			r\Objects[0] = LoadMesh_Strict("GFX\npcs\duck_low_res.b3d")
			ScaleEntity(r\Objects[0], 0.07, 0.07, 0.07)
			
			EntityParent(r\Objects[0], r\obj)
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x-808.0 * RoomScale, -72.0 * RoomScale, r\z - 40.0 * RoomScale, True)
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], r\x-488.0 * RoomScale, 160.0 * RoomScale, r\z + 700.0 * RoomScale, True)
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x-488.0 * RoomScale, 160.0 * RoomScale, r\z - 668.0 * RoomScale, True)
			r\Objects[4] = CreatePivot(r\obj)
			PositionEntity(r\Objects[4], r\x-572.0 * RoomScale, 350.0 * RoomScale, r\z - 4.0 * RoomScale, True)
			
			temp = Rand(1,4)
			PositionEntity(r\Objects[0], EntityX(r\Objects[temp],True),EntityY(r\Objects[temp],True),EntityZ(r\Objects[temp],True),True)
			;[End Block]
		Case "room2offices3"
			;[Block]

			it = CreateItem("Mobile Task Forces", "paper", r\x + 744.0 * RoomScale, r\y +240.0 * RoomScale, r\z + 944.0 * RoomScale)
			EntityParent(it\collider, r\obj)	

			
			it = CreateItem("Object Classes", "paper", r\x + 160.0 * RoomScale, r\y +240.0 * RoomScale, r\z + 568.0 * RoomScale)
			EntityParent(it\collider, r\obj)	
			
			it = CreateItem("Document", "paper", r\x -1440.0 * RoomScale, r\y +624.0 * RoomScale, r\z + 152.0 * RoomScale)
			EntityParent(it\collider, r\obj)	
			
			it = CreateItem("Radio Transceiver", "radio", r\x - 1184.0 * RoomScale, r\y + 480.0 * RoomScale, r\z - 800.0 * RoomScale)
			EntityParent(it\collider, r\obj)				
			
			it = CreateItem("ReVision Eyedrops", "eyedrops", r\x - 1529.0*RoomScale, r\y + 563.0 * RoomScale, r\z - 572.0*RoomScale + 0*0.05)
			EntityParent(it\collider, r\obj)				
			
			it = CreateItem("9V Battery", "bat", r\x - 1545.0 * RoomScale, r\y + 603.0 * RoomScale, r\z - 372.0 * RoomScale)
			EntityParent(it\collider, r\obj)

			it = CreateItem("9V Battery", "bat", r\x - 1540.0 * RoomScale, r\y + 603.0 * RoomScale, r\z - 340.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 1056.0 * RoomScale, 384.0*RoomScale, r\z + 290.0 * RoomScale, 90, r, True)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = True
			PositionEntity r\RoomDoors[0]\buttons[0], EntityX(r\RoomDoors[0]\buttons[0],True),EntityY(r\RoomDoors[0]\buttons[0],True),r\z + 161.0 * RoomScale,True
			PositionEntity r\RoomDoors[0]\buttons[1], EntityX(r\RoomDoors[0]\buttons[1],True),EntityY(r\RoomDoors[0]\buttons[1],True),r\z + 161.0 * RoomScale,True
			;[End Block]
		Case "room3"
			If Rand(0,1) = 1 Then
				PlaceHalloweenScene(r, 23, Rand(0, 3-NEWYEARINDEX), r\x - 12.3613 * RoomScale, r\y + 77.312 * RoomScale, r\z + 433.168 * RoomScale, -180)
			EndIf
		Case "start"
			;[Block]
			;the containment doors
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 4000.0 * RoomScale, 384.0*RoomScale, r\z + 1696.0 * RoomScale, 90, r, True, True)
			r\RoomDoors[1]\locked = False : r\RoomDoors[1]\AutoClose = False
			r\RoomDoors[1]\dir = 1 : r\RoomDoors[1]\open = True 
			FreeEntity(r\RoomDoors[1]\buttons[0]) : r\RoomDoors[1]\buttons[0] = 0
			FreeEntity(r\RoomDoors[1]\buttons[1]) : r\RoomDoors[1]\buttons[1] = 0
			r\RoomDoors[1]\MTFClose = False
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x + 2704.0 * RoomScale, 384.0*RoomScale, r\z + 624.0 * RoomScale, 90, r, False)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False
			FreeEntity(r\RoomDoors[2]\buttons[0]) : r\RoomDoors[2]\buttons[0] = 0
			FreeEntity(r\RoomDoors[2]\buttons[1]) : r\RoomDoors[2]\buttons[1] = 0
			r\RoomDoors[2]\MTFClose = False
			
			d.Doors = CreateDoor(r\zone, r\x + 1392.0 * RoomScale, 384.0*RoomScale, r\z + 64.0 * RoomScale, 90, r, True)
			d\AutoClose = False
			d\MTFClose = False
			d\locked = True
			
			d.Doors = CreateDoor(r\zone, r\x - 640.0 * RoomScale, 384.0*RoomScale, r\z + 64.0 * RoomScale, 90, r, False)
			d\locked = True : d\AutoClose = False
			
			d.Doors = CreateDoor(r\zone, r\x + 1280.0 * RoomScale, 384.0*RoomScale, r\z + 312.0 * RoomScale, 180, r, True)
			d\locked = True : d\AutoClose = False
			PositionEntity(d\buttons[0], r\x + 1120.0 * RoomScale, EntityY(d\buttons[0],True), r\z + 328.0 * RoomScale, True)
			PositionEntity(d\buttons[1], r\x + 1120.0 * RoomScale, EntityY(d\buttons[1],True), r\z + 296.0 * RoomScale, True)
			FreeEntity(d\obj2) : d\obj2=0
			d\MTFClose = False
			
			d.Doors = CreateDoor(r\zone, r\x, 0, r\z + 1184.0 * RoomScale, 0, r, False)
			d\locked = True
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\IntroDesk.b3d")
			ScaleEntity r\Objects[0], RoomScale, RoomScale ,RoomScale
			PositionEntity r\Objects[0], r\x + 272.0 * RoomScale, 0, r\z + 400.0 * RoomScale
			EntityParent r\Objects[0], r\obj
			
			de.Decals = CreateDecal(0, r\x + 272.0 * RoomScale, 0.005, r\z + 262.0 * RoomScale, 90, Rand(360), 0)
			EntityParent(de\obj, r\obj)
			
			r\Objects[1] = LoadMesh_Strict("GFX\map\IntroDrawer.b3d")
			ScaleEntity r\Objects[1], RoomScale, RoomScale ,RoomScale
			PositionEntity r\Objects[1], r\x + 448.0 * RoomScale, 0, r\z + 192.0 * RoomScale
			EntityParent r\Objects[1], r\obj
			
			de.Decals = CreateDecal(0, r\x + 456.0 * RoomScale, 0.005, r\z + 135.0 * RoomScale, 90, Rand(360), 0)
			EntityParent(de\obj, r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 336.0 * RoomScale, r\y + 352 * RoomScale, r\z + 48.0 * RoomScale, r, True)
			sc\angle = 270
			sc\turn = 45
			sc\room = r
			TurnEntity(sc\CameraObj, 20, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			PositionEntity(sc\ScrObj, r\x + 1456 * RoomScale, 608 * RoomScale, r\z +352.0 * RoomScale)
			TurnEntity(sc\ScrObj, 0, 90, 0)
			EntityParent(sc\ScrObj, r\obj)
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + 1689.43 * RoomScale, r\y + 385.312 * RoomScale, r\z - 273.563 * RoomScale, 0)
			PlaceHalloweenScene(r, 23, Rand(0, 3-NEWYEARINDEX), r\x - 447.691 * RoomScale, r\y + 77.312 * RoomScale, r\z + 973.848 * RoomScale, -115)
			
			r\Objects[2] = CreatePivot()
			PositionEntity (r\Objects[2], EntityX(r\obj) + 40.0 * RoomScale, 460.0 * RoomScale, EntityZ(r\obj) + 1072.0 * RoomScale)
			r\Objects[3] = CreatePivot()
			PositionEntity (r\Objects[3], EntityX(r\obj) - 80.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 526.0 * RoomScale)
			r\Objects[4] = CreatePivot()
			PositionEntity (r\Objects[4], EntityX(r\obj) - 128.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 320.0 * RoomScale)
			
			r\Objects[5] = CreatePivot()
			PositionEntity (r\Objects[5], EntityX(r\obj) + 660.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 526.0 * RoomScale)
			r\Objects[6] = CreatePivot()
			PositionEntity (r\Objects[6], EntityX(r\obj) + 700 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 320.0 * RoomScale)
			
			r\Objects[7] = CreatePivot()
			PositionEntity (r\Objects[7], EntityX(r\obj) + 1472.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 912.0 * RoomScale)
			
			For i = 2 To 7
				EntityParent(r\Objects[i], r\obj)
			Next
			
			;r\Objects[22] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[22], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[22], EntityX(r\obj)+1, EntityY(r\obj)+0.1, EntityZ(r\obj)
			
			;r\Objects[23] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[23], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[23], EntityX(r\obj)+4, EntityY(r\obj)+0.1, EntityZ(r\obj)+4
			;
			;r\Objects[24] = CopyEntity(EASTER_EGG_MODEL)
			;ScaleEntity r\objects[24], 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004), 0.002+Rnd(0.0001, 0.0004)
			;PositionEntity r\Objects[24], EntityX(r\obj)+4, EntityY(r\obj)+0.1, EntityZ(r\obj)+2
			;
			;For i = 22 To 24
			;	EntityType r\Objects[i], HIT_MAP
			;	a = AddLight(r, EntityX(r\Objects[i]), EntityY(r\Objects[i]), EntityZ(r\Objects[i]), 2, 1, 255,255,255)
			;	EntityColor r\Objects[i], Rand(255), Rand(255), Rand(255)
			;Next
			;3384,510,2400
			CreateDevilEmitter(r\x+3384.0*RoomScale,r\y+510.0*RoomScale,r\z+2400.0*RoomScale,r,1,4)
			;[End Block]
		Case "room2scps"
			;[Block]
			d.Doors = CreateDoor(r\zone, r\x + 264.0 * RoomScale, 0, r\z, 90, r, True, False, 3)
			d\AutoClose = False : d\open = False
			PositionEntity(d\buttons[0], r\x + 320.0 * RoomScale, EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			PositionEntity(d\buttons[1], r\x + 224.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			
			d.Doors = CreateDoor(r\zone, r\x - 264.0 * RoomScale, 0, r\z, 270, r, True, False, 3)
			d\AutoClose = False : d\open = False
			PositionEntity(d\buttons[0], r\x - 320.0 * RoomScale, EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			PositionEntity(d\buttons[1], r\x - 224.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x-560.0 * RoomScale, 0, r\z - 272.0 * RoomScale, 0, r, True, False, 3)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x + 560.0 * RoomScale, 0, r\z - 272.0 * RoomScale, 180, r, True, False, 3)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False
			
			r\RoomDoors[3] = CreateDoor(r\zone, r\x + 560.0 * RoomScale, 0, r\z + 272.0 * RoomScale, 180, r, True, False, 3)
			r\RoomDoors[3]\AutoClose = False : r\RoomDoors[3]\open = False
			
			r\RoomDoors[4] = CreateDoor(r\zone, r\x-560.0 * RoomScale, 0, r\z + 272.0 * RoomScale, 0, r, True, False, 3)
            r\RoomDoors[4]\AutoClose = False : r\RoomDoors[4]\open = False
			
			it = CreateItem("SCP-714", "scp714", r\x - 552.0 * RoomScale, r\y + 220.0 * RoomScale, r\z - 760.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("SCP-1025", "scp1025", r\x + 552.0 * RoomScale, r\y + 224.0 * RoomScale, r\z - 758.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			If Not server\breach Then
				it = CreateItem("SCP-860", "scp860", r\x + 568.0 * RoomScale, r\y + 178.0 * RoomScale, r\z + 760.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			
			sc.SecurityCams = CreateSecurityCam(r\x + 560.0 * RoomScale, r\y + 386 * RoomScale, r\z - 416.0 * RoomScale, r)
			sc\angle = 180 : sc\turn = 30
			TurnEntity(sc\CameraObj, 30, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 560.0 * RoomScale, r\y + 386 * RoomScale, r\z - 416.0 * RoomScale, r)
			sc\angle = 180 : sc\turn = 30
			TurnEntity(sc\CameraObj, 30, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 560.0 * RoomScale, r\y + 386 * RoomScale, r\z + 480.0 * RoomScale, r)
            sc\angle = 0 : sc\turn = 30
            TurnEntity(sc\CameraObj, 30, 0, 0)
            EntityParent(sc\obj, r\obj)
			
            sc.SecurityCams = CreateSecurityCam(r\x - 560.0 * RoomScale, r\y + 386 * RoomScale, r\z + 480.0 * RoomScale, r)
            sc\angle = 0 : sc\turn = 30
            TurnEntity(sc\CameraObj, 30, 0, 0)
            EntityParent(sc\obj, r\obj)
			
			it = CreateItem("Document SCP-714", "paper", r\x - 728.0 * RoomScale, r\y + 288.0 * RoomScale, r\z - 360.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Document SCP-427", "paper", r\x - 608.0 * RoomScale, r\y + 66.0 * RoomScale, r\z + 636.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			Local dx#,dy#
			For i = 0 To 14
				Select i
					Case 0
						dx# = -64.0
						dz# = -516.0
					Case 1
						dx# = -96.0
						dz# = -388.0
					Case 2
						dx# = -128.0
						dz# = -292.0
					Case 3
						dx# = -128.0
						dz# = -132.0
					Case 4
						dx# = -160.0
						dz# = -36.0
					Case 5
						dx# = -192.0
						dz# = 28.0
					Case 6
						dx# = -384.0
						dz# = 28.0
					Case 7
						dx# = -448.0
						dz# = 92.0
					Case 8
						dx# = -480.0
						dz# = 124.0
					Case 9
						dx# = -512.0
						dz# = 156.0
					Case 10
						dx# = -544.0
						dz# = 220.0
					Case 11
						dx# = -544.0
						dz# = 380.0
					Case 12
						dx# = -544.0
						dz# = 476.0
					Case 13
						dx# = -544.0
						dz# = 572.0
					Case 14
						dx# = -544.0
						dz# = 636.0
				End Select
				de.Decals = CreateDecal(Rand(15,16),r\x+dx#*RoomScale,0.005,r\z+dz#*RoomScale,90,Rand(360),0)
				If i > 10 Then
					de\Size = Rnd(0.2,0.25)
				Else
					de\Size = Rnd(0.1,0.17)
				EndIf
				EntityAlpha(de\obj, 1.0) : ScaleSprite(de\obj,de\Size,de\Size)
				EntityParent de\obj, r\obj
			Next
			;[End Block]
		Case "room205"
			;[Block]
			;d.Doors = CreateDoor(r\zone, r\x + 128.0 * RoomScale, 0, r\z + 640.0 *RoomScale, 90, r, True, False, 3)
			;d\AutoClose = False : d\open = False
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 128.0 * RoomScale, 0, r\z + 640.0 *RoomScale, 90, r, True, False, 3)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\open = False
			;PositionEntity(d\buttons[0], r\x + 320.0 * RoomScale, EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			;PositionEntity(d\buttons[1], r\x + 224.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 1392.0 * RoomScale, -128.0 * RoomScale, r\z - 384*RoomScale, 0, r, True, False, 3, "", True)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			FreeEntity(r\RoomDoors[0]\buttons[0]) : r\RoomDoors[0]\buttons[0]=0
			FreeEntity(r\RoomDoors[0]\buttons[1]) : r\RoomDoors[0]\buttons[1]=0
			
			sc.SecurityCams = CreateSecurityCam(r\x - 1152.0 * RoomScale, r\y + 900.0 * RoomScale, r\z + 176.0 * RoomScale, r, True)
			sc\angle = 90 : sc\turn = 0
			EntityParent(sc\obj, r\obj)
			
			sc\AllowSaving = False
			sc\RenderInterval = 0
			
			EntityParent(sc\ScrObj, 0)
			PositionEntity(sc\ScrObj, r\x - 1716.0 * RoomScale, r\y + 160.0 * RoomScale, r\z + 176.0 * RoomScale, True)
			TurnEntity sc\ScrObj, 0, 90, 0
			ScaleSprite sc\ScrObj, 896.0*0.5*RoomScale, 896.0*0.5*RoomScale
			
			EntityParent(sc\ScrObj, r\obj)
			;EntityBlend(sc\ScrObj, 2)
			
			CameraZoom (sc\Cam, 1.5)
			
			HideEntity sc\ScrOverlay
			HideEntity sc\MonitorObj
			
			r\Objects[0] = CreatePivot(r\obj)
			PositionEntity r\Objects[0], r\x - 1536.0 * RoomScale, r\y + 730.0 * RoomScale, r\z + 192.0 * RoomScale, True
			RotateEntity r\Objects[0], 0,-90,0,True
			
			r\Objects[1] = sc\ScrObj
			
			;[End Block]
		Case "endroom"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x, 0, r\z + 1136 * RoomScale, 0, r, False, True, 6)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			r\RoomDoors[0]\locked = True
			FreeEntity r\RoomDoors[0]\buttons[0] : r\RoomDoors[0]\buttons[0]=0
			FreeEntity r\RoomDoors[0]\buttons[1] : r\RoomDoors[0]\buttons[1]=0
			;[End Block]
		Case "endroomc"
			;[Block]
			d = CreateDoor(r\zone, r\x+1024*RoomScale, 0, r\z, 0, r, False, 2, False, "")
			d\open = False : d\AutoClose = False : d\locked = True
			;[End Block]
		Case "coffin"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x, 0, r\z - 448.0 * RoomScale, 0, r, False, True, 2)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x - 384.0 * RoomScale, 0.7, r\z - 280.0 * RoomScale, True)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 320.0 * RoomScale, r\y + 704 * RoomScale, r\z + 288.0 * RoomScale, r, True)
			sc\angle = 45 + 180
			sc\turn = 45
			sc\CoffinEffect = True
			TurnEntity(sc\CameraObj, 120, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			CoffinCam = sc
			
			PositionEntity(sc\ScrObj, r\x - 800 * RoomScale, 288.0 * RoomScale, r\z - 340.0 * RoomScale)
			EntityParent(sc\ScrObj, r\obj)
			TurnEntity(sc\ScrObj, 0, 180, 0)
			
			r\Objects[2] = CopyEntity(LeverBaseOBJ)
			r\Objects[3] = CopyEntity(LeverOBJ)
				
			r\Levers[0] = r\Objects[3]
				
			For i% = 0 To 1
				ScaleEntity(r\Objects[2 + i], 0.04, 0.04, 0.04)
				PositionEntity (r\Objects[2 + i], r\x - 800.0 * RoomScale, r\y + 180.0 * RoomScale, r\z - 336 * RoomScale, True)
					
				EntityParent(r\Objects[2 + i], r\obj)
			Next
			RotateEntity(r\Objects[2], 0, 180, 0)
			RotateEntity(r\Objects[3], 10, 0, 0)
			
			EntityPickMode r\Objects[3], 1, False
			EntityRadius r\Objects[3], 0.1
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x, -1320.0 * RoomScale, r\z + 2304.0 * RoomScale)
			EntityParent(r\Objects[0], r\obj)
			
			it = CreateItem("Document SCP-895", "paper", r\x - 688.0 * RoomScale, r\y + 133.0 * RoomScale, r\z - 304.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Level 3 Key Card", "key3", r\x + 240.0 * RoomScale, r\y -1456.0 * RoomScale, r\z + 2064.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Night Vision Goggles", "nvgoggles", r\x + 280.0 * RoomScale, r\y -1456.0 * RoomScale, r\z + 2164.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			it\state = 400
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x + 96.0*RoomScale, -1532.0 * RoomScale, r\z + 2016.0 * RoomScale,True)
			
			;de.Decals = CreateDecal(0, r\x + 96.0*RoomScale, -1535.0 * RoomScale, r\z + 32.0 * RoomScale, 90, Rand(360), 0)
			;EntityParent de\obj, r\obj
			;[End Block]
		Case "room2tesla","room2tesla_lcz","room2tesla_hcz"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 114.0 * RoomScale, 0.0, r\z)
			EntityParent(r\Objects[0], r\obj)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 114.0 * RoomScale, 0.0, r\z)
			EntityParent(r\Objects[1], r\obj)			
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x, 0.0, r\z)
			EntityParent(r\Objects[2], r\obj)	
			
			r\Objects[3] = CreateSprite()
			EntityTexture (r\Objects[3], TeslaTexture)
			SpriteViewMode(r\Objects[3],2) 
			;ScaleSprite (r\Objects[3],((512.0 * RoomScale)/2.0),((512.0 * RoomScale)/2.0))
			EntityBlend (r\Objects[3], 3) 
			EntityFX(r\Objects[3], 1 + 8 + 16)
			
			PositionEntity(r\Objects[3], r\x, 0.8, r\z)
			
			HideEntity r\Objects[3]
			EntityParent(r\Objects[3], r\obj)
			
			w.waypoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z + 292.0 * RoomScale, Null, r)
			w2.waypoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z - 284.0 * RoomScale, Null, r)
			w\connected[0] = w2 : w\dist[0] = EntityDistance(w\obj, w2\obj)
			w2\connected[0] = w : w2\dist[0] = w\dist[0]
			
			r\Objects[4] = CreateSprite()
			PositionEntity(r\Objects[4], r\x - 32 * RoomScale, 568 * RoomScale, r\z)
			ScaleSprite(r\Objects[4], 0.03, 0.03)
			EntityTexture(r\Objects[4], LightSpriteTex(1))
			EntityBlend (r\Objects[4], 3)
			EntityParent(r\Objects[4], r\obj)
			HideEntity r\Objects[4]
			
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5],r\x,0,r\z-800*RoomScale)
			EntityParent(r\Objects[5],r\obj)
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6],r\x,0,r\z+800*RoomScale)
			EntityParent(r\Objects[6],r\obj)
			;[End Block]
		Case "room2doors"
			;[Block]
			d = CreateDoor(r\zone, r\x, 0, r\z + 528.0 * RoomScale, 0, r, True)
			d\AutoClose = False ;: d\buttons[0] = False
			PositionEntity (d\buttons[0], r\x - 832.0 * RoomScale, 0.7, r\z + 160.0 * RoomScale, True)
			PositionEntity (d\buttons[1], r\x + 160.0 * RoomScale, 0.7, r\z + 536.0 * RoomScale, True)
			;RotateEntity(d\buttons[1], 0, 90, 0, True)
			
			d2 = CreateDoor(r\zone, r\x, 0, r\z - 528.0 * RoomScale, 180, r, True)
			d2\AutoClose = False : FreeEntity (d2\buttons[0]) : d2\buttons[0] = 0
			PositionEntity (d2\buttons[1], r\x +160.0 * RoomScale, 0.7, r\z - 536.0 * RoomScale, True)
			;RotateEntity(d2\buttons[1], 0, 90, 0, True)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 832.0 * RoomScale, 0.5, r\z)
			EntityParent(r\Objects[0], r\obj)
			
			d2\LinkedDoor = d : d\LinkedDoor = d2
			
			d\open = False : d2\open = True
			;[End Block]
		Case "room4"
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x + 365.55593 * RoomScale, r\y + 416 * RoomScale, r\z + 19.29553 * RoomScale, 90)
		Case "914"
			;[Block]
			;d = CreateDoor(r\zone, r\x, 0, r\z - 368.0 * RoomScale, 0, r, False, True, 2)
			;d\dir = 1 : d\AutoClose = False : d\open = False
			;PositionEntity (d\buttons[0], r\x - 496.0 * RoomScale, 0.7, r\z - 272.0 * RoomScale, True)
			;TurnEntity(d\buttons[0], 0, 90, 0)
			r\RoomDoors[2] = CreateDoor(r\zone,r\x,0,r\z-368.0*RoomScale,0,r,False,True,2)
			r\RoomDoors[2]\dir=1 : r\RoomDoors[2]\AutoClose=False : r\RoomDoors[2]\open=False
			PositionEntity (r\RoomDoors[2]\buttons[0], r\x - 496.0 * RoomScale, 0.7, r\z - 272.0 * RoomScale, True)
			TurnEntity(r\RoomDoors[2]\buttons[0], 0, 90, 0)
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\914key.x")
			r\Objects[1] = LoadMesh_Strict("GFX\map\914knob.x")
			
			For  i% = 0 To 1
				ScaleEntity(r\Objects[i], RoomScale, RoomScale, RoomScale)
				EntityPickMode(r\Objects[i], 2)
			Next
			
			PositionEntity (r\Objects[0], r\x, r\y + 190.0 * RoomScale, r\z + 374.0 * RoomScale)
			PositionEntity (r\Objects[1], r\x, r\y + 230.0 * RoomScale, r\z + 374.0 * RoomScale)
			EntityParent(r\Objects[0], r\obj)
			EntityParent(r\Objects[1], r\obj)
			
			d = CreateDoor(r\zone, r\x - 624.0 * RoomScale, 0.0, r\z + 528.0 * RoomScale, 180, r, True)
			FreeEntity (d\obj2) : d\obj2 = 0
			FreeEntity (d\buttons[0]) : d\buttons[0] = 0
			FreeEntity (d\buttons[1]) : d\buttons[1] = 0
			d\dir = 4
			r\RoomDoors[0] = d: d\AutoClose = False
			
			d = CreateDoor(r\zone, r\x + 816.0 * RoomScale, 0.0, r\z + 528.0 * RoomScale, 180, r, True)
			FreeEntity (d\obj2) : d\obj2 = 0	
			FreeEntity (d\buttons[0]) : d\buttons[0] = 0
			FreeEntity (d\buttons[1]) : d\buttons[1] = 0
			d\dir = 4
			r\RoomDoors[1] = d : d\AutoClose = False
			
			r\Objects[2] = CreatePivot()
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 712.0 * RoomScale, 0.5, r\z + 640.0 * RoomScale)
			PositionEntity(r\Objects[3], r\x + 728.0 * RoomScale, 0.5, r\z + 640.0 * RoomScale)
			EntityParent(r\Objects[2], r\obj)
			EntityParent(r\Objects[3], r\obj)
			
			it = CreateItem("Addendum: 5/14 Test Log", "paper", r\x +954.0 * RoomScale, r\y +228.0 * RoomScale, r\z + 127.0 * RoomScale)
			EntityParent(it\collider, r\obj)	
			
			it = CreateItem("First Aid Kit", "firstaid", r\x + 960.0 * RoomScale, r\y + 112.0 * RoomScale, r\z - 40.0 * RoomScale)
			EntityParent(it\collider, r\obj) : RotateEntity(it\collider, 0, 90, 0)
			
			it = CreateItem("Dr. L's Note", "paper", r\x - 928.0 * RoomScale, 160.0 * RoomScale, r\z - 160.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			PlaceHalloweenScene(r, 22, Rand(0, 3-NEWYEARINDEX), r\x - 866.62987 * RoomScale, 0, r\z + 148.12198 * RoomScale, 90-180)
			
			collpvt = CreateCube()
			EntityAlpha collpvt, 0.0
			PositionEntity(collpvt, r\x, r\y, r\z + 374.0 * RoomScale)
			MoveEntity collpvt, 0.0, 2.5, 0.0
			ScaleEntity collpvt,1.72+0.4+0.8, 1, 2.59+0.8
			EntityType collpvt, HIT_INVISIBLEWALL
			EntityParent collpvt, r\obj
			;[End Block]
		Case "173"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity (r\Objects[0], EntityX(r\obj) + 40.0 * RoomScale, 460.0 * RoomScale, EntityZ(r\obj) + 1072.0 * RoomScale)
			r\Objects[1] = CreatePivot()
			PositionEntity (r\Objects[1], EntityX(r\obj) - 80.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 526.0 * RoomScale)
			r\Objects[2] = CreatePivot()
			PositionEntity (r\Objects[2], EntityX(r\obj) - 128.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 320.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity (r\Objects[3], EntityX(r\obj) + 660.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 526.0 * RoomScale)
			r\Objects[4] = CreatePivot()
			PositionEntity (r\Objects[4], EntityX(r\obj) + 700 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 320.0 * RoomScale)
			
			r\Objects[5] = CreatePivot()
			PositionEntity (r\Objects[5], EntityX(r\obj) + 1472.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\obj) + 912.0 * RoomScale)
			
			For i = 0 To 5
				EntityParent(r\Objects[i], r\obj)
			Next
			
			r\RoomDoors[1] = CreateDoor(r\zone, EntityX(r\obj) + 288.0 * RoomScale, 0, EntityZ(r\obj) + 384.0 * RoomScale, 90, r, False, True)
			r\RoomDoors[1]\AutoClose = False ;: r\RoomDoors[1]\locked = True
			r\RoomDoors[1]\dir = 1 : r\RoomDoors[1]\open = False
			
			FreeEntity(r\RoomDoors[1]\buttons[0]) : r\RoomDoors[1]\buttons[0] = 0
			FreeEntity(r\RoomDoors[1]\buttons[1]) : r\RoomDoors[1]\buttons[1] = 0
			
			de.Decals = CreateDecal(Rand(4, 5), EntityX(r\Objects[5], True), 0.002, EntityZ(r\Objects[5], True), 90, Rnd(360), 0)
			de\Size = 1.2
			ScaleSprite(de\obj, de\Size, de\Size)
			
			For xtemp% = 0 To 1
				For ztemp% = 0 To 1
					de.Decals = CreateDecal(Rand(4, 6), r\x + 700.0 * RoomScale + xtemp * 700.0 * RoomScale + Rnd(-0.5, 0.5), Rnd(0.001, 0.0018), r\z + 600 * ztemp * RoomScale + Rnd(-0.5, 0.5), 90, Rnd(360), 0)
					de\Size = Rnd(0.5, 0.8)
					de\Alpha = Rnd(0.8, 1.0)
					ScaleSprite(de\obj, de\Size, de\Size)
				Next
			Next
			
			;AddLight(r, r\x-224.0*RoomScale, r\y+640.0*RoomScale, r\z+128.0*RoomScale,2,2,200,200,200)
			;AddLight(r, r\x-1056.0*RoomScale, r\y+608.0*RoomScale, r\z+416.0*RoomScale,2,2,200,200,200)
			
			r\RoomDoors[2] = CreateDoor(r\zone, r\x - 1008.0 * RoomScale, 0, r\z - 688.0 * RoomScale, 90, r, True, False, False, "", True)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\open = False : r\RoomDoors[2]\locked = True
			FreeEntity(r\RoomDoors[2]\buttons[0]) : r\RoomDoors[2]\buttons[0] = 0
			FreeEntity(r\RoomDoors[2]\buttons[1]) : r\RoomDoors[2]\buttons[1] = 0
			
			r\RoomDoors[3] = CreateDoor(r\zone, r\x - 2320.0 * RoomScale, 0, r\z - 1248.0 * RoomScale, 90, r, True)
			r\RoomDoors[3]\AutoClose = False : r\RoomDoors[3]\open = True : r\RoomDoors[3]\locked = True
			
			r\RoomDoors[4] = CreateDoor(r\zone, r\x - 4352.0 * RoomScale, 0, r\z - 1248.0 * RoomScale, 90, r, True)
			r\RoomDoors[4]\AutoClose = False : r\RoomDoors[4]\open = True : r\RoomDoors[4]\locked = True	
			
			;the door in the office below the walkway
			r\RoomDoors[7] = CreateDoor(r\zone, r\x - 3712.0 * RoomScale, -385*RoomScale, r\z - 128.0 * RoomScale, 0, r, True)
			r\RoomDoors[7]\AutoClose = False : r\RoomDoors[7]\open = True
			
			d.Doors = CreateDoor(r\zone, r\x - 3712 * RoomScale, -385*RoomScale, r\z - 2336 * RoomScale, 0, r, False)
			d\locked = True : d\DisableWaypoint = True
			
			;the door from the concrete tunnel to the large hall
			d.Doors = CreateDoor(r\zone, r\x - 6864 * RoomScale, 0, r\z - 1248 * RoomScale, 90, r, True)
			d\AutoClose = False
			d\locked = True
			
			;the locked door to the lower level of the hall
			d.Doors = CreateDoor(r\zone, r\x - 5856 * RoomScale, 0, r\z - 1504 * RoomScale, 0, r, False)
			d\locked = True : d\DisableWaypoint = True
			
			;the door to the staircase in the office room
			d.Doors = CreateDoor(r\zone, r\x - 2432 * RoomScale, 0, r\z - 1000 * RoomScale, 0, r, False)
			PositionEntity(d\buttons[0], r\x - 2592 * RoomScale, EntityY(d\buttons[0],True), r\z - 1016 * RoomScale, True)
			PositionEntity(d\buttons[1], r\x - 2592 * RoomScale, EntityY(d\buttons[0],True), r\z - 984 * RoomScale, True)
			d\locked = True : d\DisableWaypoint = True
			
			tex = LoadTexture_Strict("GFX\map\Door02.jpg")
			For ztemp = 0 To 1
				d.Doors = CreateDoor(r\zone, r\x - 5760 * RoomScale, 0, r\z + (320+896*ztemp) * RoomScale, 0, r, False)
				d\locked = True
				d\DisableWaypoint = True
				
				d.Doors = CreateDoor(r\zone, r\x - 8288 * RoomScale, 0, r\z + (320+896*ztemp) * RoomScale, 0, r, False)
				d\locked = True
				If ztemp = 0 Then d\open = True Else d\DisableWaypoint = True
				
				For xtemp = 0 To 2
					d.Doors = CreateDoor(r\zone, r\x - (7424.0-512.0*xtemp) * RoomScale, 0, r\z + (1008.0-480.0*ztemp) * RoomScale, 180*(Not ztemp), r, False)
					EntityTexture d\obj, tex
					d\locked = True
					FreeEntity d\obj2 : d\obj2=0
					FreeEntity d\buttons[0] : d\buttons[0]=0
					FreeEntity d\buttons[1] : d\buttons[1]=0
					d\DisableWaypoint = True
				Next					
				For xtemp = 0 To 4
					d.Doors = CreateDoor(r\zone, r\x - (5120.0-512.0*xtemp) * RoomScale, 0, r\z + (1008.0-480.0*ztemp) * RoomScale, 180*(Not ztemp), r, False)
					EntityTexture d\obj, tex
					d\locked = True
					FreeEntity d\obj2 : d\obj2=0
					FreeEntity d\buttons[0] : d\buttons[0]=0
					FreeEntity d\buttons[1] : d\buttons[1]=0	
					d\DisableWaypoint = True
					
					If xtemp = 2 And ztemp = 1 Then r\RoomDoors[6] = d
				Next	
			Next
			
			CreateItem("Class D Orientation Leaflet", "paper", r\x-(2914+1024)*RoomScale, 170.0*RoomScale, r\z+40*RoomScale)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 4048.0 * RoomScale, r\y - 32.0 * RoomScale, r\z - 1232.0 * RoomScale, r, True)
			sc\angle = 270
			sc\turn = 45
			sc\room = r
			TurnEntity(sc\CameraObj, 20, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			PositionEntity(sc\ScrObj, r\x - 2256 * RoomScale, 224.0 * RoomScale, r\z - 928.0 * RoomScale)
			TurnEntity(sc\ScrObj, 0, 90, 0)
			EntityParent(sc\ScrObj, r\obj)
			
			r\Objects[9] = LoadMesh_Strict("GFX\map\173_2.b3d",r\obj)
			EntityType r\Objects[9],HIT_MAP
			EntityPickMode r\Objects[9],2
			
			r\Objects[10] = LoadMesh_Strict("GFX\map\intro_labels.b3d",r\obj)
			FreeTexture tex
			;[End Block]
		Case "room2ccont"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 64.0 * RoomScale, 0.0, r\z + 368.0 * RoomScale, 180, r, False, False, 2)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False
			If Server\Breach = False Then
				it = CreateItem("Note from Daniel", "paper", r\x-400.0*RoomScale,1040.0*RoomScale,r\z+115.0*RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("FN P90", "p90", r\x-400.0*RoomScale,1040.0*RoomScale,r\z+115.0*RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			For n% = 0 To 2
				r\Objects[n * 2] = CopyEntity(LeverBaseOBJ)
				r\Objects[n * 2 + 1] = CopyEntity(LeverOBJ)
				
				r\Levers[n] = r\Objects[n * 2 + 1]
				
				For  i% = 0 To 1
					ScaleEntity(r\Objects[n * 2 + i], 0.04, 0.04, 0.04)
					PositionEntity (r\Objects[n * 2 + i], r\x - 240.0 * RoomScale, r\y + 1104.0 * RoomScale, r\z + (632.0 - 64.0 * n) * RoomScale, True)
					
					EntityParent(r\Objects[n * 2 + i], r\obj)
				Next
				RotateEntity(r\Objects[n * 2], 0, -90, 0)
				RotateEntity(r\Objects[n * 2 + 1], 10, -90 - 180, 0)
				
				EntityPickMode r\Objects[n * 2 + 1], 1, False
				EntityRadius r\Objects[n * 2 + 1], 0.1
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x-265.0*RoomScale, r\y+1280.0*RoomScale, r\z+105.0*RoomScale, r)
			sc\angle = 45
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;[End Block]
		Case "room106"
			it = CreateItem("Level 5 Key Card", "key5", r\x - 752.0 * RoomScale, r\y - 592 * RoomScale, r\z + 3026.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Dr. Allok's Note", "paper", r\x - 416.0 * RoomScale, r\y - 576 * RoomScale, r\z + 2492.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			it = CreateItem("Recall Protocol RP-106-N", "paper", r\x + 268.0 * RoomScale, r\y - 576 * RoomScale, r\z + 2593.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			d = CreateDoor(r\zone, r\x - 968.0 * RoomScale, -764.0 * RoomScale, r\z + 1392.0 * RoomScale, 0, r, False, False, 4)
			d\AutoClose = False : d\open = False	
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x, 0, r\z - 464.0 * RoomScale, 0, r, False, False, 4)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\open = False			
			
			d = CreateDoor(r\zone, r\x - 624.0 * RoomScale, -1280.0 * RoomScale, r\z, 90, r, False, False, 4)
			d\AutoClose = False : d\open = False

			If Server\Breach Then
				it.Items = CreateItem("Micro-HID", "microhid", r\x - 237.36994 * RoomScale, r\y - 576.0 * RoomScale, r\z + 2483.66502 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			
			r\Objects[6] = LoadMesh_Strict("GFX\map\room1062.b3d")
			
			ScaleEntity (r\Objects[6],RoomScale,RoomScale,RoomScale)
			EntityType r\Objects[6], HIT_MAP
			EntityPickMode r\Objects[6], 3
			PositionEntity(r\Objects[6],r\x+784.0*RoomScale,-980.0*RoomScale,r\z+720.0*RoomScale,True)
			
			EntityParent(r\Objects[6], r\obj)
			
			For n = 0 To 2 Step 2
				r\Objects[n] = CopyEntity(LeverBaseOBJ)
				r\Objects[n+1] = CopyEntity(LeverOBJ)
				
				r\Levers[n/2] = r\Objects[n+1]
				
				For i% = 0 To 1
					ScaleEntity(r\Objects[n+i], 0.04, 0.04, 0.04)
					PositionEntity (r\Objects[n+i], r\x - (555.0 - 81.0 * (n/2)) * RoomScale, r\y - 576.0 * RoomScale, r\z + 3040.0 * RoomScale, True)
					
					EntityParent(r\Objects[n+i], r\obj)
				Next
				RotateEntity(r\Objects[n], 0, 0, 0)
				RotateEntity(r\Objects[n+1], 10, -180, 0)
				
				;EntityPickMode(r\Objects[n * 2 + 1], 2)
				EntityPickMode r\Objects[n+1], 1, False
				EntityRadius r\Objects[n+1], 0.1
				;makecollbox(r\Objects[n * 2 + 1])
			Next
			
			RotateEntity(r\Objects[1], 81,-180,0)
			RotateEntity(r\Objects[3], -81,-180,0)			
			
			r\Objects[4] = CreateButton(r\x - 146.0*RoomScale, r\y - 576.0 * RoomScale, r\z + 3045.0 * RoomScale, 0,0,0)
			EntityParent (r\Objects[4],r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 768.0 * RoomScale, r\y + 1392.0 * RoomScale, r\z + 1696.0 * RoomScale, r, True)
			sc\angle = 45 + 90 + 180
			sc\turn = 20
			TurnEntity(sc\CameraObj, 45, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			r\Objects[7] = sc\CameraObj
			r\Objects[8] = sc\obj
			
			PositionEntity(sc\ScrObj, r\x - 272.0 * RoomScale, -544.0 * RoomScale, r\z + 3020.0 * RoomScale)
			TurnEntity(sc\ScrObj, 0, -10, 0)
			EntityParent sc\ScrObj, r\obj
			sc\CoffinEffect=0
			
			;r\NPC[0] = CreateNPC(NPCtypeD, r\x + 1088.0 * RoomScale, 1096.0 * RoomScale, r\z + 1728.0 * RoomScale)
			r\Objects[5] = CreatePivot()
			TurnEntity r\Objects[5], 0,180,0
			PositionEntity (r\Objects[5], r\x + 1088.0 * RoomScale, 1104.0 * RoomScale, r\z + 1888.0 * RoomScale) 
			EntityParent r\Objects[5], r\obj
			;HideEntity r\NPC[0]\obj
			
			r\Objects[9] = CreatePivot(r\obj)
			PositionEntity (r\Objects[9], r\x - 272 * RoomScale, r\y - 672.0 * RoomScale, r\z + 2736.0 * RoomScale, True)
			
			r\Objects[10] = CreatePivot(r\obj)
			PositionEntity (r\Objects[10], r\x, r\y, r\z - 720.0 * RoomScale, True)
		Case "room1archive"
			;[Block]
			If Server\Breach = False Then
				For xtemp = 0 To 1
					For ytemp = 0 To 2
						For ztemp = 0 To 2
							
							tempstr$ = "9V Battery" : tempstr2$ = "bat"
							chance% = Rand(-10,100)
							Select True
								Case (chance<0)
									Exit
								Case (chance<40) ;40% chance for a document
									tempstr="Document SCP-"
									Select Rand(1,6)
										Case 1
											tempstr=tempstr+"1123"
										Case 2
											tempstr=tempstr+"1048"
										Case 3
											tempstr=tempstr+"939"
										Case 4
											tempstr=tempstr+"682"
										Case 5
											tempstr=tempstr+"079"
										Case 6
											tempstr=tempstr+"096"
										Case 6
											tempstr=tempstr+"966"
									End Select
									tempstr2="paper"
								Case (chance>=40) And (chance<45) ;5% chance for a key card
									temp3%=Rand(1,2)
									tempstr="Level "+Str(temp3)+" Key Card"
									tempstr2="key"+Str(temp3)
								Case (chance>=45) And (chance<50) ;5% chance for a medkit
									tempstr="First Aid Kit"
									tempstr2="firstaid"
								Case (chance>=50) And (chance<60) ;10% chance for a battery
									tempstr="9V Battery"
									tempstr2="bat"
								Case (chance>=60) And (chance<70) ;10% chance for an SNAV
									tempstr="S-NAV 300 Navigator"
									tempstr2="nav"
								Case (chance>=70) And (chance<85) ;15% chance for a radio
									tempstr="Radio Transceiver"
									tempstr2="radio"
								Case (chance>=85) And (chance<95) ;10% chance for a clipboard
									tempstr="Clipboard"
									tempstr2="clipboard"
								Case (chance>=95) And (chance=<100) ;5% chance for misc
									temp3%=Rand(1,3)
									Select temp3
										Case 1 ;playing card
											tempstr="Playing Card"
										Case 2 ;Mastercard
											tempstr="Mastercard"
										Case 3 ;origami
											tempstr="Origami"
									End Select
									tempstr2="misc"
							End Select
							
							x# = (-672.0 + 864.0 * xtemp)* RoomScale
							y# = (96.0  + 96.0 * ytemp) * RoomScale
							z# = (480.0 - 352.0*ztemp + Rnd(-96.0,96.0)) * RoomScale
							
							it = CreateItem(tempstr,tempstr2,r\x+x,y,r\z+z)
							If it <> Null Then EntityParent it\collider,r\obj							
						Next
					Next
				Next
			Else
				For xtemp = 0 To 1
					For ytemp = 0 To 2
						For ztemp = 0 To 2
							
							tempstr$ = "9V Battery" : tempstr2$ = "bat"
							chance% = Rand(-10,100)
							Select True
								Case (chance<0)
									Exit
								Case (chance<40)
									temp3%=Rand(1,5)
									Select temp3
										Case 1
											tempstr="MP5-SD"
											tempstr2 = "mp5sd"
										Case 2
											tempstr="Small First Aid Kit"
											tempstr2 = "finefirstaid"
										Case 3
											tempstr="Box of ammo"
											tempstr2 = "boxofammo"
										Case 4
											tempstr="FN P90"
											tempstr2 = "p90"
										Case 5
											tempstr="USP Tactical"
											tempstr2 = "usp"
									End Select
								Case (chance>=40) And (chance<45)
									temp3%=Rand(1,3)
									tempstr="Level "+Str(temp3)+" Key Card"
									tempstr2="key"+Str(temp3)
								Case (chance>=45) And (chance<50)
									tempstr="First Aid Kit"
									tempstr2="firstaid"
								Case (chance>=50) And (chance<60)
									temp3%=Rand(1,5)
									Select temp3
										Case 1
											tempstr="MP5-SD"
											tempstr2 = "mp5sd"
										Case 2
											tempstr="Rocket Launcher"
											tempstr2 = "rpg"
										Case 3
											tempstr="Small First Aid Kit"
											tempstr2 = "finefirstaid"
										Case 4
											tempstr="FN P90"
											tempstr2 = "p90"
										Case 5
											tempstr="USP Tactical"
											tempstr2 = "usp"
									End Select
								Case (chance>=60) And (chance<70) ;10% chance for an SNAV
									temp3%=Rand(1,5)
									Select temp3
										Case 1
											tempstr="MP5-SD"
											tempstr2 = "mp5sd"
										Case 2
											tempstr="Small First Aid Kit"
											tempstr2 = "finefirstaid"
										Case 3
											tempstr="Minigun"
											tempstr2 = "minigun"
										Case 4
											tempstr="FN P90"
											tempstr2 = "p90"
										Case 5
											tempstr="USP Tactical"
											tempstr2 = "usp"
									End Select
								Case (chance>=70) And (chance<85) ;15% chance for a radio
									temp3%=Rand(1,5)
									Select temp3
										Case 1
											tempstr="MP5-SD"
											tempstr2 = "mp5sd"
										Case 2
											tempstr="Rocket Launcher"
											tempstr2 = "rpg"
										Case 3
											tempstr="Small First Aid Kit"
											tempstr2 = "finefirstaid"
										Case 4
											tempstr="FN P90"
											tempstr2 = "p90"
										Case 5
											tempstr="USP Tactical"
											tempstr2 = "usp"
									End Select
								Case (chance>=85) And (chance<95) ;10% chance for a clipboard
									temp3%=Rand(1,5)
									Select temp3
										Case 1
											tempstr="Small First Aid Kit"
											tempstr2 = "finefirstaid"
										Case 2
											tempstr="Rocket Launcher"
											tempstr2 = "rpg"
										Case 3
											tempstr="Box of ammo"
											tempstr2 = "boxofammo"
										Case 4
											tempstr="FN P90"
											tempstr2 = "p90"
										Case 5
											tempstr="USP Tactical"
											tempstr2 = "usp"
									End Select
								Case (chance>=95) And (chance=<100) ;5% chance for misc
									temp3%=Rand(1,5)
									Select temp3
										Case 1
											tempstr="MP5-SD"
											tempstr2 = "mp5sd"
										Case 2
											tempstr="Small First Aid Kit"
											tempstr2 = "finefirstaid"
										Case 3
											tempstr="Minigun"
											tempstr2 = "minigun"
										Case 4
											tempstr="FN P90"
											tempstr2 = "p90"
										Case 5
											tempstr="USP Tactical"
											tempstr2 = "usp"
									End Select
							End Select
							
							x# = (-672.0 + 864.0 * xtemp)* RoomScale
							y# = (96.0  + 96.0 * ytemp) * RoomScale
							z# = (480.0 - 352.0*ztemp + Rnd(-96.0,96.0)) * RoomScale
							
							it = CreateItem(tempstr,tempstr2,r\x+x,y,r\z+z)
							EntityParent it\collider,r\obj							
						Next
					Next
				Next
			EndIf
			r\RoomDoors[0] = CreateDoor(r\zone,r\x,r\y,r\z - 528.0 * RoomScale,0,r,False,False,6)
			
			sc.SecurityCams = CreateSecurityCam(r\x-256.0*RoomScale, r\y+384.0*RoomScale, r\z+640.0*RoomScale, r)
			sc\angle = 180
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;[End Block]
		Case "room2test1074"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone,r\x,r\y,r\z,0,r,False,False,False,"")
			r\RoomDoors[0]\locked = True
			r\RoomDoors[1] = CreateDoor(r\zone,r\x + 336.0 * RoomScale,r\y,r\z + 671.0 * RoomScale,90,r,True,False,3)
			r\RoomDoors[1]\AutoClose = False
			r\RoomDoors[2] = CreateDoor(r\zone,r\x + 336.0 * RoomScale,r\y,r\z - 800.0 * RoomScale,90,r,True,False,3)
			r\RoomDoors[2]\AutoClose = False
			r\RoomDoors[3] = CreateDoor(r\zone,r\x + 672.0 * RoomScale,r\y,r\z,0,r,False,False)
			
			r\Textures[0] = LoadTexture("GFX\map\1074tex0.jpg") ;blank texture (ripped from official article), seen when you put on 714
			r\Textures[1] = LoadTexture("GFX\map\1074tex1.jpg") ;texture depicting subject D-9341 (the player)
			TextureBlend r\Textures[0], 5 ;texture
			TextureBlend r\Textures[1], 5 ;blends
			
			it = CreateItem("Document SCP-1074","paper",r\x + 300.0 * RoomScale,r\y+20.0*RoomScale,r\z + 671.0*RoomScale)
			EntityParent(it\collider, r\obj)
			
			r\Objects[0] = CreatePivot() ;painting pivot: the player will be attracted when it sees this.
			PositionEntity r\Objects[0],r\x + 835.0 * RoomScale,r\y + 165.0 * RoomScale,r\z + 540.0 * RoomScale, True
			EntityParent r\Objects[0],r\obj
			r\Objects[1] = CreatePivot() ;floor pivot: the player will walk to this point when it sees the painting pivot.
			PositionEntity r\Objects[1],r\x + 835.0 * RoomScale,r\y + 10.0 * RoomScale,r\z + 300.0 * RoomScale, True
			EntityParent r\Objects[1],r\obj
			;Local sf,b,t,msh
			msh% = GetChild(r\obj,2) ;the second child is the rendered mesh
			r\NonFreeAble[0] = GetSurface(msh,1) ;a failsafe if the correct surface isn't found
			For tempint = 1 To CountSurfaces(msh)
				sf% = GetSurface(msh,tempint)
				b% = GetSurfaceBrush( sf )
				t% = GetBrushTexture(b, 1)
				texname$ = StripPath(TextureName(t))
				DebugLog "texname: "+texname
				If Lower(texname) = "1074tex1.jpg" Then
					r\NonFreeAble[0] = sf ;the surface holding 1074's texture
					FreeTexture t
					FreeBrush b
					Exit
				EndIf
				If texname<>"" Then FreeTexture t
				FreeBrush b
			Next
			;[End Block]
		Case "room1123"
			;[Block]
			If Server\Breach = False Then
				it = CreateItem("Document SCP-1123", "paper", r\x + 511.0 * RoomScale, r\y + 125.0 * RoomScale, r\z - 936.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x + 511.0 * RoomScale, r\y + 125.0 * RoomScale, r\z - 936.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("SCP-1123", "1123", r\x + 832.0 * RoomScale, r\y + 166.0 * RoomScale, r\z + 784.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			If Server\Breach = False Then
				it = CreateItem("Leaflet", "paper", r\x - 816.0 * RoomScale, r\y + 704.0 * RoomScale, r\z+ 888.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("FN P90", "p90", r\x - 816.0 * RoomScale, r\y + 704.0 * RoomScale, r\z+ 888.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Gas Mask", "gasmask", r\x + 457.0 * RoomScale, r\y + 150.0 * RoomScale, r\z + 960.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			d.Doors = CreateDoor(r\zone, r\x + 832.0 * RoomScale, 0.0, r\z + 367.0 * RoomScale, 0, r, False, False, 3)
			PositionEntity(d\buttons[0], r\x + 956.0 * RoomScale, EntityY(d\buttons[0],True), r\z + 352.0 * RoomScale, True)
			PositionEntity(d\buttons[1], r\x + 713.0 * RoomScale, EntityY(d\buttons[1],True), r\z + 384.0 * RoomScale, True)
			FreeEntity d\obj2 : d\obj2 = 0
			d.Doors = CreateDoor(r\zone, r\x + 280.0 * RoomScale, 0.0, r\z - 607.0 * RoomScale, 90, r, False, False)
			PositionEntity(d\buttons[0], EntityX(d\buttons[0],True), EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			PositionEntity(d\buttons[1], EntityX(d\buttons[1],True), EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			
			d.Doors = CreateDoor(r\zone, r\x + 280.0 * RoomScale, 512.0 * RoomScale, r\z - 607.0 * RoomScale, 90, r, False, False)
			PositionEntity(d\buttons[0], EntityX(d\buttons[0],True), EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			FreeEntity d\buttons[1] : d\buttons[1]=0
			r\RoomDoors[0] = d
			;PositionEntity(d\buttons[1], EntityX(d\buttons[1],True), EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)		
			
			r\Objects[3] = CreatePivot(r\obj)
			PositionEntity(r\Objects[3], r\x + 832.0 * RoomScale, r\y + 166.0 * RoomScale, r\z + 784.0 * RoomScale, True)
			r\Objects[4] = CreatePivot(r\obj)
			PositionEntity(r\Objects[4], r\x -648.0 * RoomScale, r\y + 592.0 * RoomScale, r\z + 692.0 * RoomScale, True)
			r\Objects[5] = CreatePivot(r\obj)
			PositionEntity(r\Objects[5], r\x + 828.0 * RoomScale, r\y + 592.0 * RoomScale, r\z + 592.0 * RoomScale, True)
			
			r\Objects[6] = CreatePivot(r\obj)
			PositionEntity(r\Objects[6], r\x - 76.0 * RoomScale, r\y + 620.0 * RoomScale, r\z + 744.0 * RoomScale, True)
			r\Objects[7] = CreatePivot(r\obj)
			PositionEntity(r\Objects[7], r\x - 640.0 * RoomScale, r\y + 620.0 * RoomScale, r\z - 864.0 * RoomScale, True)	
			
			r\Objects[8] = LoadMesh_Strict("GFX\map\forest\door_frame.b3d")
			PositionEntity r\Objects[8], r\x - 272.0 * RoomScale, 512.0 * RoomScale, r\z + 288.0 * RoomScale,True
			RotateEntity r\Objects[8],0,90,0,True
			ScaleEntity r\Objects[8],45.0*RoomScale,45.0*RoomScale,80.0*RoomScale,True
			EntityParent r\Objects[8],r\obj
			
			r\Objects[9] =  LoadMesh_Strict("GFX\map\forest\door.b3d")
			PositionEntity r\Objects[9],r\x - 272.0 * RoomScale, 512.0 * RoomScale, r\z + (288.0-70) * RoomScale,True
			RotateEntity r\Objects[9],0,10,0,True
			EntityType r\Objects[9], HIT_MAP
			ScaleEntity r\Objects[9],46.0*RoomScale,45.0*RoomScale,46.0*RoomScale,True
			EntityParent r\Objects[9],r\obj
			
			r\Objects[10] = CopyEntity(r\Objects[8])
			PositionEntity r\Objects[10], r\x - 272.0 * RoomScale, 512.0 * RoomScale, r\z + 736.0 * RoomScale,True
			RotateEntity r\Objects[10],0,90,0,True
			ScaleEntity r\Objects[10],45.0*RoomScale,45.0*RoomScale,80.0*RoomScale,True
			EntityParent r\Objects[10],r\obj
			
			r\Objects[11] =  CopyEntity(r\Objects[9])
			PositionEntity r\Objects[11],r\x - 272.0 * RoomScale, 512.0 * RoomScale, r\z + (736.0-70) * RoomScale,True
			RotateEntity r\Objects[11],0,90,0,True
			EntityType r\Objects[11], HIT_MAP
			ScaleEntity r\Objects[11],46.0*RoomScale,45.0*RoomScale,46.0*RoomScale,True
			EntityParent r\Objects[11],r\obj
			
			r\Objects[12] = CopyEntity(r\Objects[8])
			PositionEntity r\Objects[12], r\x - 592.0 * RoomScale, 512.0 * RoomScale, r\z - 704.0 * RoomScale,True
			RotateEntity r\Objects[12],0,0,0,True
			ScaleEntity r\Objects[12],45.0*RoomScale,45.0*RoomScale,80.0*RoomScale,True
			EntityParent r\Objects[12],r\obj
			
			r\Objects[13] =  CopyEntity(r\Objects[9])
			PositionEntity r\Objects[13],r\x - (592.0+70.0) * RoomScale, 512.0 * RoomScale, r\z - 704.0 * RoomScale,True
			RotateEntity r\Objects[13],0,0,0,True
			EntityType r\Objects[13], HIT_MAP
			ScaleEntity r\Objects[13],46.0*RoomScale,45.0*RoomScale,46.0*RoomScale,True
			EntityParent r\Objects[13],r\obj	
			
			r\Objects[14] = LoadMesh_Strict("GFX\map\1123_hb.b3d",r\obj)
			EntityPickMode r\Objects[14],2
			EntityType r\Objects[14],HIT_MAP
			EntityAlpha r\Objects[14],0.0
			;[End Block]
		Case "pocketdimension"
			;[Block]
			Local hallway = LoadMesh_Strict("GFX\map\pocketdimension2.b3d") ;the tunnels in the first room
			r\Objects[8]=LoadMesh_Strict("GFX\map\pocketdimension3.b3d")	;the room with the throne, moving pillars etc 
			r\Objects[9]=LoadMesh_Strict("GFX\map\pocketdimension4.b3d") ;the flying pillar
			r\Objects[10]=CopyEntity(r\Objects[9])
			
			r\Objects[11]=LoadMesh_Strict("GFX\map\pocketdimension5.b3d") ;the pillar room
			
			
			terrain = LoadMesh_Strict("GFX\map\pocketdimensionterrain.b3d")
			ScaleEntity terrain,RoomScale,RoomScale,RoomScale,True
			;RotateEntity terrain,0,e\room\angle,0,True
			PositionEntity terrain, 0, 2944, 0, True
			
			
			
			CreateItem("Burnt Note", "paper", EntityX(r\obj),0.5,EntityZ(r\obj)+3.5)
			
			For n = 0 To -1;4
				
				Select n
					Case 0
						entity = hallway 					
					Case 1
						entity = r\Objects[8]						
					Case 2
						entity = r\Objects[9]						
					Case 3
						entity = r\Objects[10]							
					Case 4
						entity = r\Objects[11]							
				End Select 
				
				;If BumpEnabled Then 
				;	
				;	For i = 1 To CountSurfaces(entity)
				;		sf = GetSurface(entity,i)
				;		b = GetSurfaceBrush( sf )
				;		t = GetBrushTexture(b,1)
				;		texname$ =  StripPath(TextureName(t))
				;		mat.Materials=GetCache(texname)
				;		If mat<>Null Then
				;			If mat\Bump<>0 Then
				;				t1 = GetBrushTexture(b,0)
				;				
				;				BrushTexture b, t1, 0, 0	
				;				BrushTexture b, mat\Bump, 0, 1
				;				BrushTexture b, t, 0, 2					
				;				
				;				PaintSurface sf,b
				;				
				;				If t1<>0 Then FreeTexture t1 : t1=0
				;			EndIf
				;		EndIf
				;		
				;		If t<>0 Then FreeTexture t : t=0
				;		If b<>0 Then FreeBrush b : b=0
				;	Next
				;	
				;EndIf
				
			Next
			
			For i = 8 To 11
				ScaleEntity (r\Objects[i],RoomScale,RoomScale,RoomScale)
				EntityType r\Objects[i], HIT_MAP
				;EntityPickMode r\Objects[i], 3
				EntityPickMode r\Objects[i], 2
				PositionEntity(r\Objects[i],r\x,r\y,r\z+32.0,True)
			Next
			
			ScaleEntity (terrain,RoomScale,RoomScale,RoomScale)
			EntityType terrain, HIT_MAP
			EntityPickMode terrain, 3
			PositionEntity(terrain,r\x,r\y+2944.0*RoomScale,r\z+32.0,True)			
			
			r\RoomDoors[0] = CreateDoor(0, r\x,2048*RoomScale,r\z+32.0-1024*RoomScale,0,r,False)
			r\RoomDoors[1] = CreateDoor(0, r\x,2048*RoomScale,r\z+32.0+1024*RoomScale,180,r,False)
			
			de.Decals = CreateDecal(18, r\x-(1536*RoomScale), 0.02,r\z+608*RoomScale+32.0, 90,0,0)
			EntityParent(de\obj, r\obj)
			de\Size = Rnd(0.8, 0.8)
			de\blendmode = 2
			de\fx = 1+8
			ScaleSprite(de\obj, de\Size, de\Size)
			EntityFX(de\obj, 1+8)
			EntityBlend de\obj, 2
			
			ScaleEntity (r\Objects[10],RoomScale*1.5,RoomScale*2.0,RoomScale*1.5,True)			
			PositionEntity(r\Objects[11],r\x,r\y,r\z+64.0,True)			
			
			For i = 1 To 8
				r\Objects[i-1] = CopyEntity(hallway) ;CopyMesh
				ScaleEntity (r\Objects[i-1],RoomScale,RoomScale,RoomScale)
				angle# = (i-1) * (360.0/8.0)
				
				EntityType r\Objects[i-1], HIT_MAP
				;EntityPickMode r\Objects[i-1], 3
				EntityPickMode r\Objects[i-1], 2
				
				RotateEntity(r\Objects[i-1],0,angle-90,0)
				PositionEntity(r\Objects[i-1],r\x+Cos(angle)*(512.0*RoomScale),0.0,r\z+Sin(angle)*(512.0*RoomScale))
				EntityParent (r\Objects[i-1], r\obj)
				
				If i < 6 Then 
					de.Decals = CreateDecal(i+7, r\x+Cos(angle)*(512.0*RoomScale)*3.0, 0.02,r\z+Sin(angle)*(512.0*RoomScale)*3.0, 90,angle-90,0)
					de\Size = Rnd(0.5, 0.5)
					de\blendmode = 2
					de\fx = 1+8
					ScaleSprite(de\obj, de\Size, de\Size)
					EntityFX(de\obj, 1+8)
					EntityBlend de\obj, 2
				EndIf				
			Next
			
			For i = 12 To 16
				r\Objects[i] = CreatePivot(r\Objects[11])
				Select i
					Case 12
						PositionEntity(r\Objects[i],r\x,r\y+200*RoomScale,r\z+64.0,True)	
					Case 13
						PositionEntity(r\Objects[i],r\x+390*RoomScale,r\y+200*RoomScale,r\z+64.0+272*RoomScale,True)	
					Case 14
						PositionEntity(r\Objects[i],r\x+838*RoomScale,r\y+200*RoomScale,r\z+64.0-551*RoomScale,True)	
					Case 15
						PositionEntity(r\Objects[i],r\x-139*RoomScale,r\y+200*RoomScale,r\z+64.0+1201*RoomScale,True)	
					Case 16
						PositionEntity(r\Objects[i],r\x-1238*RoomScale,r\y-1664*RoomScale,r\z+64.0+381*RoomScale,True)
				End Select 
				
			Next
			
			Local OldManEyes% = LoadTexture_Strict("GFX\npcs\oldmaneyes.jpg")
			r\Objects[17] = CreateSprite()
			ScaleSprite(r\Objects[17], 0.03, 0.03)
			EntityTexture(r\Objects[17], OldManEyes)
			EntityBlend (r\Objects[17], 3)
			EntityFX(r\Objects[17], 1 + 8)
			SpriteViewMode(r\Objects[17], 2)
			
			r\RoomTextures[18] = LoadTexture_Strict("GFX\npcs\pdplane.png", 1+2)
			r\RoomTextures[19] = LoadTexture_Strict("GFX\npcs\pdplaneeye.png", 1+2)		
			;r\ObjectsTexture[18] = True
			;r\ObjectsTexture[19] = True
			
			r\Objects[20] = CreateSprite()
			ScaleSprite(r\Objects[20], 8.0, 8.0)
			EntityTexture(r\Objects[20], r\RoomTextures[18])
			EntityOrder r\Objects[20], 100
			EntityBlend (r\Objects[20], 2)
			EntityFX(r\Objects[20], 1 + 8)
			SpriteViewMode(r\Objects[20], 2)
			
			FreeTexture t
			FreeEntity hallway
			FreeTexture OldManEyes
			;[End Block]
		Case "room3z3"
			;[Block]
			sc.SecurityCams = CreateSecurityCam(r\x-320.0*RoomScale, r\y+384.0*RoomScale, r\z+512.25*RoomScale, r)
			sc\angle = 225
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;sc\FollowPlayer = True
			;[End Block]
		Case "room2_3","room3_3"
			;[Block]
			w.waypoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z, Null, r)
			;[End Block]
		;New rooms (in SCP:CB 1.3) - ENDSHN
		Case "room1lifts"
			;[Block]
			r\Objects[0] = CreateButton(r\x + 96.0*RoomScale, r\y + 160.0 * RoomScale, r\z + 64.0 * RoomScale, 0,0,0)
			EntityParent (r\Objects[0],r\obj)
			r\Objects[1] = CreateButton(r\x - 96.0*RoomScale, r\y + 160.0 * RoomScale, r\z + 64.0 * RoomScale, 0,0,0)
			EntityParent (r\Objects[1],r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x+384.0*RoomScale, r\y+(448-64)*RoomScale, r\z-960.0*RoomScale, r)
			sc\angle = 45
			sc\turn = 45
			sc\room = r
			TurnEntity(sc\CameraObj, 20, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			w.waypoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z, Null, r)
			;[End Block]
		Case "room2servers2"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 264.0 * RoomScale, 0.0, r\z + 672.0 * RoomScale, 270, r, False, False, 3)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 224.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z + 510.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 304.0 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z + 840.0 * RoomScale, True)	
			TurnEntity r\RoomDoors[0]\buttons[1],0,0,0,True
			r\RoomDoors[1] = CreateDoor(r\zone, r\x -512.0 * RoomScale, -768.0*RoomScale, r\z -336.0 * RoomScale, 0, r, False, False, 3)
			d.Doors = CreateDoor(r\zone, r\x -509.0 * RoomScale, -768.0*RoomScale, r\z -1037.0 * RoomScale, 0, r, False, False, 3)
			d.Doors\locked = True
			d.Doors\DisableWaypoint = True
			it = CreateItem("Night Vision Goggles", "nvgoggles", r\x + 56.0154 * RoomScale, r\y - 648.0 * RoomScale, r\z + 749.638 * RoomScale)
			it\state = 200
			RotateEntity it\collider, 0, r\angle+Rand(245), 0
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room2gw","room2gw_b"
		    ;[Block]
			If r\RoomTemplate\Name = "room2gw_b"
				r\Objects[2] = CreatePivot(r\obj)
				PositionEntity (r\Objects[2], r\x - 156.825*RoomScale, -37.3458*RoomScale, r\z+121.364*RoomScale, True)
				
				de.Decals = CreateDecal(3,  r\x - 156.825*RoomScale, -37.3458*RoomScale, r\z+121.364*RoomScale,90,Rnd(360),0)
				de\Size = 0.5
				ScaleSprite(de\obj, de\Size,de\Size)
				EntityParent de\obj, r\obj
				
				;260 300 -350
				;WIP
				r\Objects[0] = CreatePivot()
				PositionEntity r\Objects[0],r\x+280.0*RoomScale,r\y+345.0*RoomScale,r\z-340.0*RoomScale,True
				EntityParent r\Objects[0],r\obj
			EndIf
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 336.0 * RoomScale, 0.0, r\z - 382.0 * RoomScale, 0, r, False, False)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z - 606.679 * RoomScale, True)	
            PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z - 606.679 * RoomScale, True)
			r\RoomDoors[0]\dir = 0 : r\RoomDoors[0]\AutoClose = False	: r\RoomDoors[0]\open = True  : r\RoomDoors[0]\locked = True	
			r\RoomDoors[0]\MTFClose = False
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 336.0 * RoomScale, 0.0, r\z + 462.0 * RoomScale, 180, r, False, False)
			PositionEntity(r\RoomDoors[1]\buttons[0], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[1]\buttons[0],True), r\z - 606.679 * RoomScale, True)	
            PositionEntity(r\RoomDoors[1]\buttons[1], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[1]\buttons[1],True), r\z - 606.679 * RoomScale, True)
			r\RoomDoors[1]\dir = 0 : r\RoomDoors[1]\AutoClose = False	: r\RoomDoors[1]\open = True  : r\RoomDoors[1]\locked = True
			r\RoomDoors[1]\MTFClose = False
			
			For r2.Rooms = Each Rooms
				If r2<>r Then
					If r2\RoomTemplate\Name = "room2gw" Or r2\RoomTemplate\Name = "room2gw_b" Then
						r\Objects[3] = CopyEntity(r2\Objects[3],r\obj) ;don't load the mesh again
						Exit
					EndIf
				EndIf
			Next
			If r\Objects[3]=0 Then r\Objects[3] = LoadMesh_Strict("GFX\map\room2gw_pipes.b3d",r\obj)
			EntityPickMode r\Objects[3],2
			
			If r\RoomTemplate\Name = "room2gw"
				r\Objects[0] = CreatePivot()
				;PositionEntity r\Objects[0],r\x-48.0*RoomScale,128.0*RoomScale,r\z+320.0*RoomScale
				PositionEntity r\Objects[0],r\x+344.0*RoomScale,128.0*RoomScale,r\z
				EntityParent r\Objects[0],r\obj
				
				Local bd_temp% = False
				If room2gw_brokendoor
					If room2gw_x = r\x
						If room2gw_z = r\z
							bd_temp% = True
						EndIf
					EndIf
				EndIf
				
				If (room2gw_brokendoor = 0 And Rand(1,2)=1) Or bd_temp%
					r\Objects[1] = CopyEntity(DoorOBJ)
					ScaleEntity(r\Objects[1], (204.0 * RoomScale) / MeshWidth(r\Objects[1]), 312.0 * RoomScale / MeshHeight(r\Objects[1]), 16.0 * RoomScale / MeshDepth(r\Objects[1]))
					EntityType r\Objects[1], HIT_MAP
					PositionEntity r\Objects[1], r\x + 336.0 * RoomScale, 0.0, r\z + 462.0 * RoomScale
					RotateEntity(r\Objects[1], 0, 180 + 180, 0)
					EntityParent(r\Objects[1], r\obj)
					MoveEntity r\Objects[1],120.0,0,5.0
					room2gw_brokendoor = True
					room2gw_x# = r\x
					room2gw_z# = r\z
					FreeEntity r\RoomDoors[1]\obj2 : r\RoomDoors[1]\obj2 = 0
				EndIf
			EndIf
			;[End Block]
		Case "room3gw"
	        ;[Block]
			d = CreateDoor(r\zone, r\x - 728.0 * RoomScale, 0.0, r\z - 458.0 * RoomScale, 0, r, False, False, 3)
			d\AutoClose = False	: d\open = False  : d\locked = False
			
			d = CreateDoor(r\zone, r\x - 223.0 * RoomScale, 0.0, r\z - 736.0 * RoomScale, -90, r, False, False, 3)
			d\AutoClose = False	: d\open = False  : d\locked = False
			
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 459.0 * RoomScale, 0.0, r\z + 339.0 * RoomScale, 90, r, False, False)
			PositionEntity(r\RoomDoors[0]\buttons[0], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[0]\buttons[0],True), r\z - 606.679 * RoomScale, True)	
            PositionEntity(r\RoomDoors[0]\buttons[1], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[0]\buttons[1],True), r\z - 606.679 * RoomScale, True)
			r\RoomDoors[0]\dir = 0 : r\RoomDoors[0]\AutoClose = False	: r\RoomDoors[0]\open = True  : r\RoomDoors[0]\locked = True	
			r\RoomDoors[0]\MTFClose = False
			
			r\RoomDoors[1] = CreateDoor(r\zone, r\x + 385.0 * RoomScale, 0.0, r\z + 339.0 * RoomScale, 270, r, False, False)
			PositionEntity(r\RoomDoors[1]\buttons[0], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[1]\buttons[0],True), r\z - 606.679 * RoomScale, True)	
            PositionEntity(r\RoomDoors[1]\buttons[1], r\x + 580.822 * RoomScale, EntityY(r\RoomDoors[1]\buttons[1],True), r\z - 606.679 * RoomScale, True)
			r\RoomDoors[1]\dir = 0 : r\RoomDoors[1]\AutoClose = False	: r\RoomDoors[1]\open = True  : r\RoomDoors[1]\locked = True
			r\RoomDoors[1]\MTFClose = False
			FreeEntity r\RoomDoors[1]\obj2 : r\RoomDoors[1]\obj2 = 0
			
			r\Objects[0] = CreatePivot()
			PositionEntity r\Objects[0],r\x-48.0*RoomScale,128.0*RoomScale,r\z+320.0*RoomScale
			EntityParent r\Objects[0],r\obj
			
			For r2.Rooms = Each Rooms
				If r2<>r Then
					If r2\RoomTemplate\Name = "room3gw" Then
						r\Objects[3] = CopyEntity(r2\Objects[3],r\obj) ;don't load the mesh again
						Exit
					EndIf
				EndIf
			Next
			If r\Objects[3]=0 Then r\Objects[3] = LoadMesh_Strict("GFX\map\room3gw_pipes.b3d",r\obj)
			EntityPickMode r\Objects[3],2
	        ;[End Block]
		Case "room1162"
			;[Block]
			d = CreateDoor(r\zone, r\x + 248.0*RoomScale, 0.0, r\z - 736.0*RoomScale, 90, r, False, False, 2-Server\Breach)
			r\Objects[0] = CreatePivot()
			PositionEntity r\Objects[0],r\x+1012.0*RoomScale,r\y+128.0*RoomScale,r\z-640.0*RoomScale
			EntityParent r\Objects[0],r\obj
			EntityPickMode r\Objects[0],1
			If Server\Breach = False Then
				it = CreateItem("Document SCP-1162", "paper", r\x + 863.227 * RoomScale, r\y + 152.0 * RoomScale, r\z - 953.231 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("USP Tactical", "usp", r\x + 863.227 * RoomScale, r\y + 152.0 * RoomScale, r\z - 953.231 * RoomScale)
				EntityParent(it\collider, r\obj)
				
				it = CreateItem("Level 2 Key Card", "key2", r\x + 866.227 * RoomScale, r\y + 152.0 * RoomScale, r\z - 953.231 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			sc.SecurityCams = CreateSecurityCam(r\x-192.0*RoomScale, r\y+704.0*RoomScale, r\z+192.0*RoomScale, r)
			sc\angle = 225
			sc\turn = 45
			TurnEntity(sc\CameraObj, 20, 0, 0)
			;[End Block]
		Case "room2scps2"
			;[Block]
			r\RoomDoors[0] = CreateDoor(r\zone, r\x + 288.0*RoomScale, r\y, r\z + 576.0*RoomScale, 90, r, False, False, 3)
			r\RoomDoors[0]\open = False : r\RoomDoors[0]\locked = True
			d = CreateDoor(r\zone, r\x + 777.0*RoomScale, r\y, r\z + 671.0*RoomScale, 90, r, False, False, 4)
			d = CreateDoor(r\zone, r\x + 556.0*RoomScale, r\y, r\z + 296.0*RoomScale, 0, r, False, False, 3)
			r\Objects[0] = CreatePivot()
			PositionEntity r\Objects[0],r\x + 576.0*RoomScale,r\y+160.0*RoomScale,r\z+632.0*RoomScale
			EntityParent r\Objects[0],r\obj
			
			If Not server\breach Then
				it = CreateItem("SCP-1499", "scp1499", r\x + 600.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 228.0 * RoomScale)
				RotateEntity it\collider, 0, r\angle, 0
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Document SCP-1499", "paper", r\x + 840.0 * RoomScale, r\y + 260.0 * RoomScale, r\z + 224.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			If Server\Breach = False Then
				it = CreateItem("Document SCP-500", "paper", r\x + 1152.0 * RoomScale, r\y + 224.0 * RoomScale, r\z + 336.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			Else
				it = CreateItem("FN P90", "p90", r\x + 1152.0 * RoomScale, r\y + 224.0 * RoomScale, r\z + 336.0 * RoomScale)
				EntityParent(it\collider, r\obj)
			EndIf
			it = CreateItem("Emily Ross' Badge", "badge", r\x + 364.0 * RoomScale, r\y + 5.0 * RoomScale, r\z + 716.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 850.0 * RoomScale, r\y + 350.0 * RoomScale, r\z + 876.0 * RoomScale, r)
            sc\angle = 220 : sc\turn = 30
            TurnEntity(sc\CameraObj, 30, 0, 0)
            EntityParent(sc\obj, r\obj)
			
            sc.SecurityCams = CreateSecurityCam(r\x + 600.0 * RoomScale, r\y + 514.0 * RoomScale, r\z + 150.0 * RoomScale, r)
            sc\angle = 180 : sc\turn = 30
            TurnEntity(sc\CameraObj, 30, 0, 0)
            EntityParent(sc\obj, r\obj)
			;[End Block]
		Case "room3offices"
			;[Block]			
			d.Doors = CreateDoor(r\zone, r\x + 736.0 * RoomScale, 0.0, r\z + 240.0 * RoomScale, 0, r, False, False, 3)
			PositionEntity(d\buttons[0], r\x + 892.0 * RoomScale, EntityY(d\buttons[0],True), r\z + 224.0 * RoomScale, True)
			PositionEntity(d\buttons[1], r\x + 892.0 * RoomScale, EntityY(d\buttons[1],True), r\z + 255.0 * RoomScale, True)
			FreeEntity d\obj2 : d\obj2 = 0
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\room3offices_hb.b3d",r\obj)
			EntityPickMode r\Objects[0],2
			EntityType r\Objects[0],HIT_MAP
			EntityAlpha r\Objects[0],0.0
			;[End Block]
		Case "room2offices4"
			;[Block]
			d.Doors = CreateDoor(0, r\x - 240.0 * RoomScale, 0.0, r\z, 90, r, False)
			PositionEntity(d\buttons[0], r\x - 230.0 * RoomScale, EntityY(d\buttons[0],True), EntityZ(d\buttons[0],True), True)
			PositionEntity(d\buttons[1], r\x - 250.0 * RoomScale, EntityY(d\buttons[1],True), EntityZ(d\buttons[1],True), True)
			d\open = False : d\AutoClose = False
			
			it = CreateItem("Sticky Note", "paper", r\x - 991.0*RoomScale, r\y - 242.0*RoomScale, r\z + 904.0*RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "room2sl"
			;[Block]
			Local scale# = RoomScale * 4.5 * 0.4
			Local screen%
			
			r\Textures[0] = LoadAnimTexture("GFX\SL_monitors_checkpoint.jpg",1,512,512,0,4)
			r\Textures[1] = LoadAnimTexture("GFX\Sl_monitors.jpg",1,256,256,0,8)
			
			;Monitor Objects
			For i = 0 To 14
				If i <> 7 Then
					r\Objects[i] = CopyEntity(Monitor)
					ScaleEntity(r\Objects[i], scale, scale, scale)
					If i <> 4 And i <> 13 Then
						screen = CreateSprite()
						EntityFX screen,17
						SpriteViewMode screen,2
						ScaleSprite(screen, MeshWidth(Monitor) * scale * 0.95 * 0.5, MeshHeight(Monitor) * scale * 0.95 * 0.5)
						Select i
							Case 0
								EntityTexture screen,r\Textures[1],0
							Case 2
								EntityTexture screen,r\Textures[1],2
							Case 3
								EntityTexture screen,r\Textures[1],1
							Case 8
								EntityTexture screen,r\Textures[1],4
							Case 9
								EntityTexture screen,r\Textures[1],5
							Case 10
								EntityTexture screen,r\Textures[1],3
							Case 11
								EntityTexture screen,r\Textures[1],7
							Default
								EntityTexture screen,r\Textures[0],3
						End Select
						EntityParent screen,r\Objects[i]
					ElseIf i = 4 Then
						r\Objects[20] = CreateSprite()
						EntityFX r\Objects[20],17
						SpriteViewMode r\Objects[20],2
						ScaleSprite(r\Objects[20], MeshWidth(Monitor) * scale * 0.95 * 0.5, MeshHeight(Monitor) * scale * 0.95 * 0.5)
						EntityTexture r\Objects[20],r\Textures[0],2
						EntityParent r\Objects[20],r\Objects[i]
					Else
						r\Objects[21] = CreateSprite()
						EntityFX r\Objects[21],17
						SpriteViewMode r\Objects[21],2
						ScaleSprite(r\Objects[21], MeshWidth(Monitor) * scale * 0.95 * 0.5, MeshHeight(Monitor) * scale * 0.95 * 0.5)
						EntityTexture r\Objects[21],r\Textures[1],6
						EntityParent r\Objects[21],r\Objects[i]
					EndIf
				EndIf
			Next
			For i = 0 To 2
				PositionEntity r\Objects[i],r\x-207.94*RoomScale,r\y+(648.0+(112*i))*RoomScale,r\z-60.0686*RoomScale
				RotateEntity r\Objects[i],0,105+r\angle,0
				EntityParent r\Objects[i],r\obj
				DebugLog i
			Next
			For i = 3 To 5
				PositionEntity r\Objects[i],r\x-231.489*RoomScale,r\y+(648.0+(112*(i-3)))*RoomScale,r\z+95.7443*RoomScale
				RotateEntity r\Objects[i],0,90+r\angle,0
				EntityParent r\Objects[i],r\obj
				DebugLog i
			Next
			For i = 6 To 8 Step 2
				PositionEntity r\Objects[i],r\x-231.489*RoomScale,r\y+(648.0+(112*(i-6)))*RoomScale,r\z+255.744*RoomScale
				RotateEntity r\Objects[i],0,90+r\angle,0
				EntityParent r\Objects[i],r\obj
				DebugLog i
			Next
			For i = 9 To 11
				PositionEntity r\Objects[i],r\x-231.489*RoomScale,r\y+(648.0+(112*(i-9)))*RoomScale,r\z+415.744*RoomScale
				RotateEntity r\Objects[i],0,90+r\angle,0
				EntityParent r\Objects[i],r\obj
				DebugLog i
			Next
			For i = 12 To 14
				PositionEntity r\Objects[i],r\x-208.138*RoomScale,r\y+(648.0+(112*(i-12)))*RoomScale,r\z+571.583*RoomScale
				RotateEntity r\Objects[i],0,75+r\angle,0
				EntityParent r\Objects[i],r\obj
				DebugLog i
			Next
			
			;Doors for room
			r\RoomDoors[0] = CreateDoor(r\zone,r\x+480.0*RoomScale,r\y,r\z-640.0*RoomScale,90,r,False,False,3)
			r\RoomDoors[0]\AutoClose = False
			PositionEntity r\RoomDoors[0]\buttons[0],r\x+576.0*RoomScale,EntityY(r\RoomDoors[0]\buttons[0],True),r\z-480*RoomScale,True
			RotateEntity r\RoomDoors[0]\buttons[0],0,270,0
			r\RoomDoors[1] = CreateDoor(r\zone,r\x+544.0*RoomScale,r\y+480.0*RoomScale,r\z+256.0*RoomScale,270,r,False,False,3)
			r\RoomDoors[1]\AutoClose = False
			FreeEntity r\RoomDoors[1]\obj2 : r\RoomDoors[1]\obj2 = 0
			d = CreateDoor(r\zone,r\x+1504.0*RoomScale,r\y+480.0*RoomScale,r\z+960.0*RoomScale,0,r)
			d\AutoClose = False : d\locked = True
			
			;PathPoint 1 for SCP-049
			r\Objects[7] = CreatePivot()
			PositionEntity r\Objects[7],r\x,r\y+100.0*RoomScale,r\z-800.0*RoomScale,True
			EntityParent r\Objects[7],r\obj
			
			;PathPoints for SCP-049
			r\Objects[15] = CreatePivot()
			PositionEntity r\Objects[15],r\x+700.0*RoomScale,r\y+700.0*RoomScale,r\z+256.0*RoomScale,True
			EntityParent r\Objects[15],r\obj
			r\Objects[16] = CreatePivot()
			PositionEntity r\Objects[16],r\x-60.0*RoomScale,r\y+700.0*RoomScale,r\z+200.0*RoomScale,True
			EntityParent r\Objects[16],r\obj
			r\Objects[17] = CreatePivot()
			PositionEntity r\Objects[17],r\x-48.0*RoomScale,r\y+540.0*RoomScale,r\z+656.0*RoomScale,True
			EntityParent r\Objects[17],r\obj
			
			;Faked room409
			;r\Objects[17] = LoadMesh_Strict("GFX\map\room2sl_2.b3d",r\obj)
			;sc.SecurityCams = CreateSecurityCam(r\x-160.0*RoomScale,r\y-22689.1*RoomScale,r\z-288.0*RoomScale,Null)
			;sc\angle = 225
			;TurnEntity sc\CameraObj, 20, 0, 0
			;EntityParent sc\obj,r\obj
			;sc\SpecialCam = True
			
			;-49.0 689.0 912.0
			;Objects [18],[19]
			r\Objects[9 * 2] = CopyEntity(LeverBaseOBJ)
			r\Objects[9 * 2 + 1] = CopyEntity(LeverOBJ)
			
			r\Levers[0] = r\Objects[9 * 2 + 1]
			
			For  i% = 0 To 1
				ScaleEntity(r\Objects[9 * 2 + i], 0.04, 0.04, 0.04)
				PositionEntity r\Objects[9 * 2 + i],r\x-49*RoomScale,r\y+689*RoomScale,r\z+912*RoomScale,True
				
				EntityParent(r\Objects[9 * 2 + i], r\obj)
			Next
			RotateEntity(r\Objects[9 * 2], 0, 0, 0)
			RotateEntity(r\Objects[9 * 2 + 1], 10, 0 - 180, 0)
				
			EntityPickMode r\Objects[9 * 2 + 1], 1, False
			EntityRadius r\Objects[9 * 2 + 1], 0.1
			
			;Camera in the room itself
			sc.SecurityCams = CreateSecurityCam(r\x-159.0*RoomScale, r\y+384.0*RoomScale, r\z-929.0*RoomScale, r, True)
			sc\angle = 315
			;sc\turn = 45
			sc\room = r
			TurnEntity(sc\CameraObj, 20, 0, 0)
			EntityParent(sc\obj, r\obj)
			
			PositionEntity(sc\ScrObj, r\x-231.489*RoomScale, r\y+760.0*RoomScale, r\z+255.744*RoomScale)
			TurnEntity(sc\ScrObj, 0, 90, 0)
			EntityParent(sc\ScrObj, r\obj)
			
;			r\Objects[20] = CopyEntity(LeverBaseOBJ)
;			r\Objects[21] = CopyEntity(LeverOBJ)
;			
;			For  i% = 0 To 1
;				ScaleEntity(r\Objects[20 + i], 0.04, 0.04, 0.04)
;				PositionEntity r\Objects[20],r\x+82.0*RoomScale, r\y+689.0*RoomScale, r\z+912.0*RoomScale,True
;				PositionEntity r\Objects[21],r\x+90.9775*RoomScale, r\y+604.347*RoomScale, r\z+890.584*RoomScale,True
;				
;				EntityParent(r\Objects[20 + i], r\obj)
;			Next
;			
;			RotateEntity r\Objects[21],8.6,-150.0,-5.0
			;[End Block]
		Case "room2_4"
			;[Block]
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 640.0 * RoomScale, 8.0 * RoomScale, r\z - 896.0 * RoomScale)
			EntityParent(r\Objects[6], r\obj)
			;[End Block]
		Case "room3z2"
			;[Block]
			For r2.Rooms = Each Rooms
				If r2\RoomTemplate\Name = r\RoomTemplate\Name And r2 <> r
					r\Objects[0] = CopyEntity(r2\Objects[0],r\obj)
					Exit
				EndIf
			Next
			If r\Objects[0]=0 Then r\Objects[0] = LoadMesh_Strict("GFX\map\room3z2_hb.b3d",r\obj)
			EntityPickMode r\Objects[0],2
			EntityType r\Objects[0],HIT_MAP
			EntityAlpha r\Objects[0],0.0
			;[End Block]
		Case "lockroom3"
			;[Block]
			d = CreateDoor(r\zone, r\x - 736.0 * RoomScale, 0, r\z - 104.0 * RoomScale, 0, r, True)
			d\timer = 70 * (5+(5*Server\Breach)) : d\AutoClose = False : d\open = False : d\locked = True
			
			EntityParent(d\buttons[0], 0)
			PositionEntity(d\buttons[0], r\x - 288.0 * RoomScale, 0.7, r\z - 640.0 * RoomScale)
			EntityParent(d\buttons[0], r\obj)
			
			FreeEntity(d\buttons[1]) : d\buttons[1] = 0
			
			d2 = CreateDoor(r\zone, r\x + 104.0 * RoomScale, 0, r\z + 736.0 * RoomScale, 270, r, True)
			d2\timer = 70 * (5+(5*Server\Breach)) : d2\AutoClose = False: d2\open = False : d2\locked = True
			EntityParent(d2\buttons[0], 0)
			PositionEntity(d2\buttons[0], r\x + 640.0 * RoomScale, 0.7, r\z + 288.0 * RoomScale)
			RotateEntity (d2\buttons[0], 0, 90, 0)
			EntityParent(d2\buttons[0], r\obj)
			
			FreeEntity(d2\buttons[1]) : d2\buttons[1] = 0
			
			d\LinkedDoor = d2
			d2\LinkedDoor = d
			
			scale# = RoomScale * 4.5 * 0.4
			
			r\Objects[0] = CopyEntity(Monitor)
			ScaleEntity r\Objects[0],scale#,scale#,scale#
			PositionEntity r\Objects[0],r\x+668*RoomScale,1.1,r\z-96.0*RoomScale,True
			RotateEntity r\Objects[0],0,90,0
			EntityParent r\Objects[0],r\obj
			
			r\Objects[1] = CopyEntity(Monitor)
			ScaleEntity r\Objects[1],scale#,scale#,scale#
			PositionEntity r\Objects[1],r\x+96.0*RoomScale,1.1,r\z-668.0*RoomScale,True
			EntityParent r\Objects[1],r\obj
			;[End Block]
		Case "medibay"
			;[Block]
			r\Objects[0] = LoadMesh_Strict("GFX\map\medibay_props.b3d",r\obj)
			EntityType r\Objects[0],HIT_MAP
			EntityPickMode r\Objects[0],2
			
			r\Objects[1] = CreatePivot(r\obj)
			PositionEntity(r\Objects[1], r\x - 762.0 * RoomScale, r\y + 0.0 * RoomScale, r\z - 346.0 * RoomScale, True)
			r\Objects[2] = CreatePivot(r\obj)
			PositionEntity(r\Objects[2], (EntityX(r\Objects[1],True)+(126.0 * RoomScale)), EntityY(r\Objects[1],True), EntityZ(r\Objects[1],True), True)
			it = CreateItem("First Aid Kit", "firstaid", r\x - 506.0 * RoomScale, r\y + 192.0 * RoomScale, r\z - 322.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			it = CreateItem("Syringe", "syringe", r\x - 333.0 * RoomScale, r\y + 100.0 * RoomScale, r\z + 97.3 * RoomScale)
			EntityParent(it\collider, r\obj)
			it = CreateItem("Syringe", "syringe", r\x - 340.0 * RoomScale, r\y + 100.0 * RoomScale, r\z + 52.3 * RoomScale)
			EntityParent(it\collider, r\obj)
			r\RoomDoors[0] = CreateDoor(r\zone, r\x - 264.0 * RoomScale, r\y - 0.0 * RoomScale, r\z + 640.0 * RoomScale, 90, r, False, False, 3)
			
			r\Objects[3] = CreatePivot(r\obj)
			;PositionEntity r\Objects[3],r\x-926.891*RoomScale,r\y,r\z-318.399*RoomScale,True
			PositionEntity r\Objects[3],r\x-820.0*RoomScale,r\y,r\z-318.399*RoomScale,True
			;[End Block]
		Case "room2cpit"
			;[Block]
			em.Emitters = CreateEmitter(r\x + 512.0 * RoomScale, -76 * RoomScale, r\z - 688 * RoomScale, 0)
            TurnEntity(em\Obj, -90, 0, 0)
            EntityParent(em\Obj, r\obj)
            em\RandAngle = 55
            em\Speed = 0.0005
            em\Achange = -0.015
            em\SizeChange = 0.007
            
            d = CreateDoor(r\zone,r\x-256.0*RoomScale, 0.0, r\z-752.0*RoomScale,90,r,False,2,3)
            d\locked = True : d\open = False : d\AutoClose = False : d\MTFClose = False : d\DisableWaypoint = True
			PositionEntity d\buttons[0],r\x-240.0*RoomScale,EntityY(d\buttons[0],True),EntityZ(d\buttons[0],True),True
			
			it = CreateItem("Dr L's Note", "paper", r\x - 160.0 * RoomScale, 32.0 * RoomScale, r\z - 353.0 * RoomScale)
			EntityParent(it\collider, r\obj)
			;[End Block]
		Case "dimension1499"
			;[Block]
			r\Levers[1] = LoadMesh_Strict("GFX\map\dimension1499\1499object0_cull.b3d",r\obj)
			EntityType r\Levers[1],HIT_MAP
			EntityAlpha r\Levers[1],0
			
			r\Levers[0] = CreatePivot()
			PositionEntity r\Levers[0],r\x+205.0*RoomScale,r\y+200.0*RoomScale,r\z+2287.0*RoomScale
			EntityParent r\Levers[0],r\obj
			;[End Block]
	End Select
	
	For lt.lighttemplates = Each LightTemplates
		If lt\roomtemplate = r\RoomTemplate Then
			newlt = AddLight(r, r\x+lt\x, r\y+lt\y, r\z+lt\z, lt\ltype, lt\range, lt\r, lt\g, lt\b)
			If newlt <> 0 Then 
				If lt\ltype = 3 Then
					LightConeAngles(newlt, lt\innerconeangle, lt\outerconeangle)
					RotateEntity(newlt, lt\pitch, lt\yaw, 0)
				EndIf
			EndIf
		EndIf
	Next
	
	For ts.tempscreens = Each TempScreens
		If ts\roomtemplate = r\RoomTemplate Then
			CreateScreen(r\x+ts\x, r\y+ts\y, r\z+ts\z, ts\imgpath, r)
		EndIf
	Next
	
	For tw.TempWayPoints = Each TempWayPoints
		If tw\roomtemplate = r\RoomTemplate Then
			CreateWaypoint(r\x+tw\x, r\y+tw\y, r\z+tw\z, Null, r)
		EndIf
	Next
	
	If r\RoomTemplate\TempTriggerboxAmount > 0
		r\TriggerboxAmount = r\RoomTemplate\TempTriggerboxAmount
		For i = 0 To r\TriggerboxAmount-1
			r\Triggerbox[i] = CopyEntity(r\RoomTemplate\TempTriggerbox[i],r\obj)
			r\TriggerboxName[i] = r\RoomTemplate\TempTriggerboxName[i]
			
			HideEntity r\Triggerbox[i]
			DebugLog "Triggerbox found: "+i
			DebugLog "Triggerbox "+i+" name: "+r\TriggerboxName[i]
		Next
	EndIf
	
	For i = 0 To MaxRoomEmitters-1
		If r\RoomTemplate\TempSoundEmitter[i]<>0 Then
			r\SoundEmitterObj[i]=CreatePivot(r\obj)
			PositionEntity r\SoundEmitterObj[i], r\x+r\RoomTemplate\TempSoundEmitterX[i],r\y+r\RoomTemplate\TempSoundEmitterY[i],r\z+r\RoomTemplate\TempSoundEmitterZ[i],True
			EntityParent(r\SoundEmitterObj[i],r\obj)
			
			r\SoundEmitter[i] = r\RoomTemplate\TempSoundEmitter[i]
			r\SoundEmitterRange[i] = r\RoomTemplate\TempSoundEmitterRange[i]
		EndIf
	Next
	
	allowroomdoorsinit = Null
	;Catch("FillRoom ("+r\RoomTemplate\Name+")")
End Function


Function UpdateRooms()
	For r.Rooms = Each Rooms
		HideEntity r\obj
		r\isroomshowed = False
	Next
	For i = 1 To Server\PlayersCount
		If PlayerOptimize[i]\Fake Then multiplayer_UpdateplayerRoom(PlayerOptimize[i])
		
		If PlayerOptimize[i]\PlayerRoomID < MAX_ROOMS Then
			r.Rooms = Room[PlayerOptimize[i]\PlayerRoomID]
			If r <> Null Then
				If Not r\isroomshowed Then
					ShowEntity r\obj
					r\isroomshowed = True
				
					For i=0 To 3
						If r\Adjacent[i]<>Null Then
							ShowEntity r\Adjacent[i]\obj
							r\Adjacent[i]\isroomshowed = True
						EndIf
					Next
				EndIf
			EndIf
		EndIf
	Next
End Function

Function IsEntityInRoom(room.Rooms, ent)
	If EntityX(ent) >= room\x And EntityX(ent) <= room\x+room\DistanceX Then
		If EntityY(ent) >= room\y And EntityY(ent) <= room\y+room\DistanceY Then
			If EntityZ(ent) >= room\z And EntityZ(ent) <= room\z+room\DistanceZ Then
				Return True
			EndIf
		EndIf
	EndIf
	Return False
End Function

Function mp_UpdateplayerRoom(p.players)
	For r.Rooms = Each Rooms
		If IsEntityInRoom(r, p\Pivot) Then p\PlayerRoomID = r\ID
	Next
	mp_SetRoomNameToPlayer(p)
End Function

Function sqrcount#(value#)
	Return Sqr(value*value)
End Function

Function IsRoomAdjacent(this.Rooms,that.Rooms)
	If this=Null Then Return False
	If this=that Then Return True
	For i=0 To 3
		If that=this\Adjacent[i] Then Return True
	Next
	Return False
End Function

;-------------------------------------------------------------------------------------------------------
Global LightVolume#, TempLightVolume#
Function AddLight%(room.Rooms, x#, y#, z#, ltype%, range#, r%, g%, b%)
	Local i
	
	If room<>Null Then
		For i = 0 To MaxRoomLights-1
			If room\Lights[i]=0 Then
				room\Lights[i] = CreateLight(ltype)
				;room\LightDist[i] = range
				LightRange(room\Lights[i],range)
				LightColor(room\Lights[i],r,g,b)
				PositionEntity(room\Lights[i],x,y,z,True)
				EntityParent(room\Lights[i],room\obj)
				
				room\LightIntensity[i] = (r+g+b)/255.0/3.0
				
				room\LightSprites[i]= CreateSprite()
				PositionEntity(room\LightSprites[i], x, y, z)
				ScaleSprite(room\LightSprites[i], 0.13 , 0.13)
				EntityTexture(room\LightSprites[i], LightSpriteTex(0))
				EntityBlend (room\LightSprites[i], 3)
				
				EntityParent(room\LightSprites[i], room\obj)
				
				room\LightSpritesPivot[i] = CreatePivot()
				EntityRadius room\LightSpritesPivot[i],0.05
				PositionEntity(room\LightSpritesPivot[i], x, y, z)
				EntityParent(room\LightSpritesPivot[i], room\obj)
				
				room\LightSprites2[i] = CreateSprite()
				PositionEntity(room\LightSprites2[i], x, y, z)
				ScaleSprite(room\LightSprites2[i], 0.6, 0.6)
				EntityTexture(room\LightSprites2[i], LightSpriteTex(2))
				EntityBlend(room\LightSprites2[i], 3)
				EntityOrder(room\LightSprites2[i], -1)
				EntityColor(room\LightSprites2[i], r%, g%, b%)
				EntityParent(room\LightSprites2[i], room\obj)
				EntityFX(room\LightSprites2[i],1)
				RotateEntity(room\LightSprites2[i],0,0,Rand(360))
				SpriteViewMode(room\LightSprites2[i],1)
				room\LightSpriteHidden%[i] = True
				HideEntity room\LightSprites2[i]
				room\LightFlicker%[i] = Rand(1,10)
				
				room\LightR[i] = r
				room\LightG[i] = g
				room\LightB[i] = b
				
				HideEntity room\Lights[i]
				
				room\MaxLights% = i
				
				Return room\Lights[i]
			EndIf
		Next
	Else
		Local light%,sprite%
		light=CreateLight(ltype)
		LightRange(light,range)
		LightColor(light,r,g,b)
		PositionEntity(light,x,y,z,True)
		sprite=CreateSprite()
		PositionEntity(sprite, x, y, z)
		ScaleSprite(sprite, 0.13 , 0.13)
		EntityTexture(sprite, LightSpriteTex(0))
		EntityBlend (sprite, 3)
		Return light
	EndIf
End Function

Type LightTemplates
	Field roomtemplate.RoomTemplates
	Field ltype%
	Field x#, y#, z#
	Field range#
	Field r%, g%, b%
	
	Field pitch#, yaw#
	Field innerconeangle%, outerconeangle#
End Type 

Function AddTempLight.LightTemplates(rt.RoomTemplates, x#, y#, z#, ltype%, range#, r%, g%, b%)
	lt.lighttemplates = New LightTemplates
	lt\roomtemplate = rt
	lt\x = x
	lt\y = y
	lt\z = z
	lt\ltype = ltype
	lt\range = range
	lt\r = r
	lt\g = g
	lt\b = b
	
	Return lt
End Function

;-------------------------------------------------------------------------------------------------------

Type TempWayPoints
	Field x#, y#, z#
	Field roomtemplate.RoomTemplates
End Type 

Type WayPoints
	Field obj
	Field door.Doors
	Field room.Rooms
	Field state%
	;Field tempDist#
	;Field tempSteps%
	Field connected.WayPoints[5]
	Field dist#[5]
	
	Field Fcost#, Gcost#, Hcost#
	
	Field parent.WayPoints
End Type

Function CreateWaypoint.WayPoints(x#,y#,z#,door.Doors, room.Rooms)
	
	w.waypoints = New WayPoints
	
	If 1 Then
		w\obj = CreatePivot()
		PositionEntity w\obj, x,y,z	
	Else
		w\obj = CreateSprite()
		PositionEntity(w\obj, x, y, z)
		ScaleSprite(w\obj, 0.15 , 0.15)
		EntityTexture(w\obj, LightSpriteTex(0))
		EntityBlend (w\obj, 3)	
	EndIf
	
	EntityParent w\obj, room\obj
	
	w\room = room
	w\door=door
	
	Return w
End Function

Function InitWayPoints(loadingstart=45)
	
	Local d.Doors, w.WayPoints, w2.WayPoints, r.Rooms, ClosestRoom.Rooms
	
	Local x#, y#, z#
	
	temper = MilliSecs()
	
	Local dist#, dist2#
	
	For d.Doors = Each Doors
		If d\obj <> 0 Then HideEntity d\obj
		If d\obj2 <> 0 Then HideEntity d\obj2	
		If d\frameobj <> 0 Then HideEntity d\frameobj
		
		If d\room = Null Then 
			ClosestRoom.Rooms = Null
			dist# = 30
			For r.Rooms = Each Rooms
				x# = Abs(EntityX(r\obj,True)-EntityX(d\frameobj,True))
				If x < 20.0 Then
					z# = Abs(EntityZ(r\obj,True)-EntityZ(d\frameobj,True))
					If z < 20.0 Then
						dist2 = x*x+z*z
						If dist2 < dist Then
							ClosestRoom = r
							dist = dist2
						EndIf
					EndIf
				EndIf
			Next
		Else
			ClosestRoom = d\room
		EndIf
		
		If (Not d\DisableWaypoint) Then CreateWaypoint(EntityX(d\frameobj, True), EntityY(d\frameobj, True)+0.18, EntityZ(d\frameobj, True), d, ClosestRoom)
	Next
	
	amount# = 0
	For w.WayPoints = Each WayPoints
		EntityPickMode w\obj, 1, True
		EntityRadius w\obj, 0.2
		amount=amount+1
	Next
	
	
	;pvt = CreatePivot()
	
	number = 0
	iter = 0
	For w.WayPoints = Each WayPoints
		
		number = number + 1
		iter = iter + 1
		If iter = 20 Then 
			;drawloading(loadingstart+Floor((35.0/amount)*number)) 
			iter = 0
		EndIf
		
		w2.WayPoints = After(w)
		
		Local canCreateWayPoint% = False
		
		While (w2<>Null)
			
			If (w\room=w2\room Or w\door<>Null Or w2\door<>Null)
				
				dist# = EntityDistance(w\obj, w2\obj)
				
				If w\room\MaxWayPointY# = 0.0 Or w2\room\MaxWayPointY# = 0.0
					canCreateWayPoint = True
				Else
					If Abs(EntityY(w\obj)-EntityY(w2\obj))<=w\room\MaxWayPointY
						canCreateWayPoint = True
					EndIf
				EndIf
				
				If dist < 7.0 Then
					If canCreateWayPoint
						If EntityVisible(w\obj, w2\obj) Then;e=w2\obj Then 
							For i = 0 To 4
								If w\connected[i] = Null Then
									w\connected[i] = w2.WayPoints 
									w\dist[i] = dist
									Exit
								EndIf
							Next
							
							For n = 0 To 4
								If w2\connected[n] = Null Then 
									w2\connected[n] = w.WayPoints 
									w2\dist[n] = dist
									Exit
								EndIf					
							Next
						EndIf
					EndIf	
				EndIf
			EndIf
			w2 = After(w2)
		Wend
		
	Next
	
	;FreeEntity pvt	
	
	For d.Doors = Each Doors
		If d\obj <> 0 Then ShowEntity d\obj
		If d\obj2 <> 0 Then ShowEntity d\obj2	
		If d\frameobj <> 0 Then ShowEntity d\frameobj		
	Next
	
	For w.WayPoints = Each WayPoints
		EntityPickMode w\obj, 0, 0
		EntityRadius w\obj, 0
		
		For i = 0 To 4
			If w\connected[i]<>Null Then 
				tline = CreateLine(EntityX(w\obj,True),EntityY(w\obj,True),EntityZ(w\obj,True),EntityX(w\connected[i]\obj,True),EntityY(w\connected[i]\obj,True),EntityZ(w\connected[i]\obj,True))
				EntityColor(tline, 255,0,0)
				EntityParent tline, w\obj
			EndIf
		Next
	Next
	
	DebugLog "InitWaypoints() - "+(MilliSecs2()-temper)
	
End Function

Function RemoveWaypoint(w.WayPoints)
	FreeEntity w\obj
	Delete w
End Function


Dim MapF(MapWidth+1, MapHeight+1), MapG(MapWidth+1, MapHeight+1), MapH(MapWidth+1, MapHeight+1)
Dim MapState(MapWidth+1, MapHeight+1)
Dim MapParent(MapWidth+1, MapHeight+1, 2)
Function FindPath(n.NPCs, x#, y#, z#)
	
	DebugLog "findpath: "+n\NPCtype
	
	Local temp%, dist#, dist2#
	Local xtemp#, ytemp#, ztemp#
	
	Local w.WayPoints, StartPoint.WayPoints, EndPoint.WayPoints   
	
	Local StartX% = Floor(EntityX(n\Collider,True) / 8.0 + 0.5), StartZ% = Floor(EntityZ(n\Collider,True) / 8.0 + 0.5)
	
	Local EndX% = Floor(x / 8.0 + 0.5), EndZ% = Floor(z / 8.0 + 0.5)
	
	
	Local CurrX, CurrZ

   ;pathstatus = 0, route hasn't been searched for yet
   ;pathstatus = 1, route found
   ;pathstatus = 2, route not found (target unreachable)
	
	For w.WayPoints = Each WayPoints
		w\state = 0
		w\Fcost = 0
		w\Gcost = 0
		w\Hcost = 0
	Next
	
	n\PathStatus = 0
	n\PathLocation = 0
	For i = 0 To 19
		n\Path[i] = Null
	Next
	
	Local pvt = CreatePivot()
	PositionEntity(pvt, x,y,z, True)   
	
	temp = CreatePivot()
	PositionEntity(temp, EntityX(n\Collider,True), EntityY(n\Collider,True)+0.15, EntityZ(n\Collider,True))
	
	dist = 350.0
	For w.WayPoints = Each WayPoints
		xtemp = EntityX(w\obj,True)-EntityX(temp,True)
          ;If xtemp < 8.0 Then
		ztemp = EntityZ(w\obj,True)-EntityZ(temp,True)
             ;If ztemp < 8.0 Then
		ytemp = EntityY(w\obj,True)-EntityY(temp,True)
                ;If ytemp < 8.0 Then
		dist2# = (xtemp*xtemp)+(ytemp*ytemp)+(ztemp*ztemp)
		If dist2 < dist Then 
			;prefer waypoints that are visible
			If Not EntityVisible(w\obj, temp) Then dist2 = dist2*3
			If dist2 < dist Then 
				dist = dist2
				StartPoint = w
			EndIf
		EndIf
                ;EndIf
             ;EndIf
          ;EndIf
	Next
	DebugLog "DIST: "+dist
	
	FreeEntity temp
	
	If StartPoint = Null Then Return 2
	StartPoint\state = 1      
	
       ;If EndPoint = Null Then
	EndPoint = Null
	dist# = 400.0
	For w.WayPoints = Each WayPoints
		xtemp = EntityX(pvt,True)-EntityX(w\obj,True)
          ;If xtemp =< 8.0 Then
		ztemp = EntityZ(pvt,True)-EntityZ(w\obj,True)
             ;If ztemp =< 8 Then
		ytemp = EntityY(pvt,True)-EntityY(w\obj,True)
		dist2# = (xtemp*xtemp)+(ytemp*ytemp)+(ztemp*ztemp)
		
		If dist2 < dist Then ; And EntityVisible(w\obj, pvt)
			dist = dist2
			EndPoint = w
		EndIf            
             ;EndIf
          ;EndIf
	Next
       ;EndIf
	
	FreeEntity pvt
	
	If EndPoint = StartPoint Then
		If dist < 0.4 Then
			Return 0
		Else
			n\Path[0]=EndPoint
			Return 1               
		EndIf
	EndIf
	If EndPoint = Null Then Return 2
	
       ;aloitus- ja lopetuspisteet l???ydetty, aletaan etsi??? reitti???
	
	Repeat
		
		temp% = False
		smallest.WayPoints = Null
		dist# = 10000.0
		For w.WayPoints = Each WayPoints
			If w\state = 1 Then
                temp = True
                If (w\Fcost) < dist Then
					dist = w\Fcost
					smallest = w
                EndIf
			EndIf
		Next
		
		If smallest <> Null Then
			
			w = smallest
			w\state = 2
			
			For i = 0 To 4
                If w\connected[i]<>Null Then
					If w\connected[i]\state < 2 Then
						
						If w\connected[i]\state=1 Then ;open list
							gtemp# = w\Gcost+w\dist[i]
							If n\NPCtype = NPCtypeMTF Then
								If w\connected[i]\door = Null Then gtemp = gtemp + 0.5
							EndIf
							If gtemp < w\connected[i]\Gcost Then ;parempi reitti -> overwrite
								w\connected[i]\Gcost = gtemp
								w\connected[i]\Fcost = w\connected[i]\Gcost + w\connected[i]\Hcost
								w\connected[i]\parent = w
							EndIf
						Else
							w\connected[i]\Hcost# = Abs(EntityX(w\connected[i]\obj,True)-EntityX(EndPoint\obj,True))+Abs(EntityZ(w\connected[i]\obj,True)-EntityZ(EndPoint\obj,True))
							gtemp# = w\Gcost+w\dist[i]
							If n\NPCtype = NPCtypeMTF Then
								If w\connected[i]\door = Null Then gtemp = gtemp + 0.5
							EndIf
							w\connected[i]\Gcost = gtemp
							w\connected[i]\Fcost = w\Gcost+w\Hcost
							w\connected[i]\parent = w
							w\connected[i]\state=1
						EndIf            
					EndIf
					
                EndIf
			Next
		Else ;open listilt??? ei l???ytynyt mit??????n
			If EndPoint\state > 0 Then
                StartPoint\parent = Null
                EndPoint\state = 2
                Exit
			EndIf
		EndIf
		
		If EndPoint\state > 0 Then
			StartPoint\parent = Null
			EndPoint\state = 2
			Exit
		EndIf
		
	Until temp = False
	
	If EndPoint\state > 0 Then
		
		Local currpoint.WayPoints = EndPoint
		Local twentiethpoint.WayPoints = EndPoint
		
		Local length = 0
		Repeat
			length = length +1
			currpoint = currpoint\parent
			If length>20 Then
                twentiethpoint = twentiethpoint\parent
			EndIf
		Until currpoint = Null
		
		currpoint.WayPoints = EndPoint
		While twentiethpoint<>Null
			length=Min(length-1,19)
			twentiethpoint = twentiethpoint\parent
			n\Path[length] = twentiethpoint
		Wend
		
		Return 1
	Else
		Return 2 
	EndIf
	
End Function
Function CreateLine(x1#,y1#,z1#, x2#,y2#,z2#, mesh=0)
	
	If mesh = 0 Then 
		mesh=CreateMesh()
		EntityFX(mesh,16)
		surf=CreateSurface(mesh)	
		verts = 0	
		
		AddVertex surf,x1#,y1#,z1#,0,0
	Else
		surf = GetSurface(mesh,1)
		verts = CountVertices(surf)-1
	End If
	
	AddVertex surf,(x1#+x2#)/2,(y1#+y2#)/2,(z1#+z2#)/2,0,0 
	; you could skip creating the above vertex and change the line below to
	; AddTriangle surf,verts,verts+1,verts+0
	; so your line mesh would use less vertices, the drawback is that some videocards (like the matrox g400)
	; aren't able to create a triangle with 2 vertices. so, it's your call :)
	AddVertex surf,x2#,y2#,z2#,1,0
	
	AddTriangle surf,verts,verts+2,verts+1
	
	Return mesh
End Function

;-------------------------------------------------------------------------------------------------------

Global SelectedScreen.Screens
Type Screens
	Field obj%
	Field imgpath$
	Field img
	Field room.Rooms
End Type

Type TempScreens
	Field imgpath$
	Field x#,y#,z#
	Field roomtemplate.RoomTemplates
End Type

Function CreateScreen.Screens(x#,y#,z#,imgpath$,r.Rooms)
	s.screens = New Screens
	s\obj = CreatePivot()
	EntityPickMode(s\obj, 1)	
	EntityRadius s\obj, 0.1
	
	PositionEntity s\obj, x,y,z
	s\imgpath = imgpath
	s\room = r
	EntityParent s\obj, r\obj
	
	Return s
End Function

Function UpdateScreens()

End Function

Dim MapName$(MapWidth, MapHeight)
Dim MapRoomID%(ROOM4 + 1)
Dim MapRoom$(ROOM4 + 1, 0)

;-------------------------------------------------------------------------------------------------------


Dim GorePics%(10)
Global SelectedMonitor.SecurityCams
Global CoffinCam.SecurityCams
Type SecurityCams
	Field obj%, MonitorObj%
	
	Field BaseObj%, CameraObj%
	
	Field ScrObj%, ScrWidth#, ScrHeight#
	Field Screen%, Cam%, ScrTexture%, ScrOverlay%
	Field angle#, turn#, CurrAngle#
	Field State#, PlayerState%
	
	Field soundCHN%
	
	Field InSight%
	
	Field RenderInterval#
	
	Field room.Rooms
	
	Field FollowPlayer%
	Field CoffinEffect%
	
	Field AllowSaving%
	
	Field MinAngle#, MaxAngle#, dir%
End Type

Global ScreenTexs%[2]

Global CurrRoom2slRenderCam%
Global Room2slCam%
Function CreateSecurityCam.SecurityCams(x#, y#, z#, r.Rooms, screen% = False)
	Local sc.SecurityCams = New SecurityCams
	
	sc\obj = CopyEntity(CamBaseOBJ)
	ScaleEntity(sc\obj, 0.0015, 0.0015, 0.0015)
	sc\CameraObj = CopyEntity(CamOBJ)
	ScaleEntity(sc\CameraObj, 0.01, 0.01, 0.01)
	
	sc\room = r
	
	sc\Screen = screen
	If screen Then
		sc\AllowSaving = True
		
		sc\RenderInterval = 12
		
		Local scale# = RoomScale * 4.5 * 0.4
		
		sc\ScrObj = CreateSprite()
		EntityFX sc\ScrObj, 17
		SpriteViewMode(sc\ScrObj, 2)
		sc\ScrTexture = 0
		EntityTexture sc\ScrObj, ScreenTexs[sc\ScrTexture]
		ScaleSprite(sc\ScrObj, MeshWidth(Monitor) * scale * 0.95 * 0.5, MeshHeight(Monitor) * scale * 0.95 * 0.5)
		
		sc\ScrOverlay = CreateSprite(sc\ScrObj)
		;	scaleSprite(sc\scrOverlay , 0.5, 0.4)
		ScaleSprite(sc\ScrOverlay, MeshWidth(Monitor) * scale * 0.95 * 0.5, MeshHeight(Monitor) * scale * 0.95 * 0.5)
		MoveEntity(sc\ScrOverlay, 0, 0, -0.0005)
		EntityTexture(sc\ScrOverlay, MonitorTexture)
		SpriteViewMode(sc\ScrOverlay, 2)
		EntityBlend(sc\ScrOverlay , 3)
		
		sc\MonitorObj = CopyEntity(Monitor, sc\ScrObj)
		
		ScaleEntity(sc\MonitorObj, scale, scale, scale)
		
		sc\Cam = CreateCamera()
		CameraViewport(sc\Cam, 0, 0, 512, 512)
		CameraRange sc\Cam, 0.05, 8.0 ;6.0
		CameraZoom(sc\Cam, 0.8)
		HideEntity(sc\Cam)	
	End If
	
	PositionEntity(sc\obj, x, y, z)
	
	If r<>Null Then EntityParent(sc\obj, r\obj)
	
	Return sc
End Function

Function UpdateSecurityCams()
	For sc.SecurityCams = Each SecurityCams
		If sc\Cam<>0 Then HideEntity sc\Cam
		If sc\scrobj <> 0 Then ShowEntity sc\scrobj
	Next
	
	
End Function

Function UpdateMonitorSaving()
	Local sc.SecurityCams
	Local close% = False
	
	If SelectedDifficulty\saveType <> SAVEONSCREENS Then Return
	
	For sc = Each SecurityCams
		If sc\AllowSaving And sc\Screen Then
			close = False
			If sc\room\dist < 6.0 Or PlayerRoom=sc\room Then 
				close = True
			EndIf
			
			If close And GrabbedEntity = 0 And ClosestButton = 0 Then
				If EntityInView(sc\ScrObj,Camera) And EntityDistance(sc\ScrObj,Camera)<1.0 Then
					If EntityVisible(sc\ScrObj,Camera) Then
						DrawHandIcon = True
						If MouseHit1 Then 
							SelectedMonitor = sc
						EndIf
					Else
						If SelectedMonitor = sc Then SelectedMonitor = Null
					EndIf
				Else
					If SelectedMonitor = sc Then SelectedMonitor = Null
				EndIf
				
				If SelectedMonitor = sc Then
					If sc\InSight Then
						Local pvt% = CreatePivot()
						PositionEntity pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
						PointEntity(pvt, sc\ScrObj)
						RotateEntity(Collider, EntityPitch(Collider), CurveAngle(EntityYaw(pvt), EntityYaw(Collider), Min(Max(15000.0 / (-Sanity), 20.0), 200.0)), 0)
						TurnEntity(pvt, 90, 0, 0)
						user_camera_pitch = CurveAngle(EntityPitch(pvt), user_camera_pitch + 90.0, Min(Max(15000.0 / (-Sanity), 20.0), 200.0))
						user_camera_pitch=user_camera_pitch-90
						FreeEntity pvt
					EndIf
				EndIf
			Else
				If SelectedMonitor = sc Then SelectedMonitor = Null
			EndIf
		EndIf
	Next
	
End Function

Function UpdateLever(obj, locked=False)
	EntityPickMode obj, 0
	Local dist# = EntityDistance(Camera, obj)
	
	If EntityPitch(obj,True) > 0 Then ;p??????ll???
		Return True
	Else ;pois p??????lt???
		Return False
	EndIf	
	
End Function

Function UpdateButton(obj)
	
	Local dist# = EntityDistance(Collider, obj);entityDistance(collider, d\buttons[i])
	If dist < 0.8 Then
		EntityPickMode obj, 2
		Local temp% = CreatePivot()
		PositionEntity temp, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
		PointEntity temp,obj
		
		If EntityPick(temp, 0.65) = obj Then
			If ClosestButton = 0 Then 
				ClosestButton = obj
			Else
				If dist < EntityDistance(Collider, ClosestButton) Then ClosestButton = obj
			End If							
		End If
		EntityPickMode obj, 0
		FreeEntity temp
	EndIf			
	
End Function

Function UpdateElevators#(State#, door1.Doors, door2.Doors, room1, room2, ignorerotation% = True)
	Local x#, z#, sound%
	Local dist#, dir#, n.NPCs, it.Items
	If door1 = Null Or door2 = Null Then Return
	
	door1\IsElevatorDoor = 1
	door2\IsElevatorDoor = 1

	;If door1\open = True And door2\open = False And door1\openstate = 180 Then 
	;	State = -1
	;ElseIf door2\open = True And door1\open = False And door2\openstate = 180 Then
	;	State = 1	
	;ElseIf Abs(door1\openstate-door2\openstate)<0.2 Then
	;	door1\IsElevatorDoor = 2
	;	door2\IsElevatorDoor = 2
	;EndIf
	
	
	If door1\liftid <> 1 Then
		door1\liftobj = room1
		door2\liftobj = room2
		door1\liftid = 1
		door2\liftid = 2
		door1\liftparent = door2
		door2\liftparent = door1
		
		door1\liftmaindoor = door1
		door2\liftmaindoor = door1
	EndIf
	
	door1\locked = True
	door2\locked = True
	
	Local inside = False
	
	If door1\open = False And door2\open = False Then
		door1\locked = True
		door2\locked = True
		If door1\openstate = 0 And door2\openstate = 0 Then
			If State < 0 Then
				State = State - FPSfactor
				
				If State < -500 Then
					door1\locked = True
					door2\locked = False
					State = 0

					For p.players = Each players
						If Abs(EntityX(p\AnticheatPivot)-EntityX(room1,True))<280.0*RoomScale+(0.015*0.05) Then
							If Abs(EntityZ(p\AnticheatPivot)-EntityZ(room1,True))<280.0*RoomScale+(0.015*0.05) Then	
								If Abs(EntityY(p\AnticheatPivot)-EntityY(room1,True))<280.0*RoomScale+(0.015*0.05) Then
								
									;PositionEntity(p\Pivot, EntityX(p\Pivot), EntityY(p\Pivot)-0.32, EntityZ(p\Pivot))
									;ResetEntity p\Pivot
												
									If (Not ignorerotation) Then
										dist# = Distance(EntityX(p\AnticheatPivot),EntityZ(p\AnticheatPivot),EntityX(room1,True),EntityZ(room1,True))
										dir# = point_direction(EntityX(p\AnticheatPivot),EntityZ(p\AnticheatPivot),EntityX(room1,True),EntityZ(room1,True))
										dir=dir+EntityYaw(room2,True)-EntityYaw(room1,True)
										dir=WrapAngle(dir)
										x# = Max(Min(Cos(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min(Sin(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										RotateEntity p\Pivot,EntityPitch(p\Pivot),EntityYaw(room2,True)+angleDist(EntityYaw(p\Pivot),EntityYaw(room1,True)),EntityRoll(p\Pivot)
									Else
										x# = Max(Min((EntityX(p\AnticheatPivot)-EntityX(room1,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min((EntityZ(p\AnticheatPivot)-EntityZ(room1,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
									EndIf

									teleported = False
									If p\PlayerRoomName = "gatea" Then
										For ra.Rooms = Each Rooms
											If ra\RoomTemplate\Name = "gateaentrance" Then
												;if Server\Interpolation Then
												;PositionEntity(p\AnticheatPivot, EntityX(p\Pivot), 0.302, EntityZ(p\Pivot))
												;ResetEntity p\AnticheatPivot
												
												;PositionEntity(p\Pivot, EntityX(p\Pivot), 0.302, EntityZ(p\Pivot))
												;ResetEntity p\Pivot
												;EndIf
												mp_SetPlayerRoomID(p, ra)
												
												;SetPlayerPosition(p\ID, "gateaentrance", EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
												teleported = True
												Exit
											EndIf
										Next
									EndIf
									
									SetPlayerPositionEx(p\ID, p\PlayerRoomID, EntityX(room2,True)+x,EntityY(room2,True)+0.32,EntityZ(room2,True)+z)
									p\ShouldDebugPosition = True
									
									;if not teleported then
									;
									;	if p\PlayerRoomName = "gateaentrance" Then
									;		For ra.Rooms = Each Rooms
									;			If ra\RoomTemplate\Name = "gatea" Then
									;				mp_SetPlayerRoomID(p, ra)
									;				
									;				PositionEntity(p\Pivot, EntityX(p\Pivot), EntityY(p\Pivot)+0.32, EntityZ(p\Pivot))
									;				ResetEntity p\Pivot
									;				
									;				SetPlayerPositionEx(p\ID, p\PlayerRoomID, EntityX(p\Pivot),EntityY(p\Pivot), EntityZ(p\Pivot))
									;				Exit
									;			EndIf
									;		Next
									;	EndIf
									;endif
									
									
									;mp_UpdatePlayerPosition(p, True)
									;p\ShouldDebugPosition = True
									;SetPlayerPosition(p\ID, p\PlayerRoomName, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot))
								EndIf
							EndIf
						EndIf
					Next
					
					For n.NPCs = Each NPCs
						If Abs(EntityX(n\Collider)-EntityX(room1,True))<280.0*RoomScale+(0.015*0.05) Then
							If Abs(EntityZ(n\Collider)-EntityZ(room1,True))<280.0*RoomScale+(0.015*0.05) Then
								If Abs(EntityY(n\Collider)-EntityY(room1,True))<280.0*RoomScale+(0.015*0.05) Then
									If (Not ignorerotation) Then
										dist# = Distance(EntityX(n\Collider,True),EntityZ(n\Collider,True),EntityX(room1,True),EntityZ(room1,True))
										dir# = point_direction(EntityX(n\Collider,True),EntityZ(n\Collider,True),EntityX(room1,True),EntityZ(room1,True))
										dir=dir+EntityYaw(room2,True)-EntityYaw(room1,True)
										dir=WrapAngle(dir)
										x# = Max(Min(Cos(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min(Sin(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										RotateEntity n\Collider,EntityPitch(n\Collider,True),EntityYaw(room2,True)+angleDist(EntityYaw(n\Collider,True),EntityYaw(room1,True)),EntityRoll(n\Collider,True),True
									Else
										x# = Max(Min((EntityX(n\Collider)-EntityX(room1,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min((EntityZ(n\Collider)-EntityZ(room1,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
									EndIf
									
									TeleportEntity(n\Collider, EntityX(room2,True)+x,(0.1*0.05)+EntityY(room2,True)+(EntityY(n\Collider)-EntityY(room1,True)),EntityZ(room2,True)+z,n\CollRadius,True)
									If n = Curr173
										Curr173\IdleTimer = 10
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					For it.Items = Each Items
						If it\picker < 1 Then
							If Abs(EntityX(it\collider)-EntityX(room1,True))<280.0*RoomScale+(0.015*0.05) Then
								If Abs(EntityZ(it\collider)-EntityZ(room1,True))<280.0*RoomScale+(0.015*0.05) Then
									If Abs(EntityY(it\collider)-EntityY(room1,True))<280.0*RoomScale+(0.015*0.05) Then
										If (Not ignorerotation) Then
											dist# = Distance(EntityX(it\collider,True),EntityZ(it\collider,True),EntityX(room1,True),EntityZ(room1,True))
											dir# = point_direction(EntityX(it\collider,True),EntityZ(it\collider,True),EntityX(room1,True),EntityZ(room1,True))
											dir=dir+EntityYaw(room2,True)-EntityYaw(room1,True)
											dir=WrapAngle(dir)
											x# = Max(Min(Cos(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
											z# = Max(Min(Sin(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
											RotateEntity it\collider,EntityPitch(it\collider,True),EntityYaw(room2,True)+angleDist(EntityYaw(it\collider,True),EntityYaw(room1,True)),EntityRoll(it\collider,True),True
										Else
											x# = Max(Min((EntityX(it\collider)-EntityX(room1,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
											z# = Max(Min((EntityZ(it\collider)-EntityZ(room1,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
										EndIf
										
										TeleportEntity(it\collider, EntityX(room2,True)+x,(0.1*0.05)+EntityY(room2,True)+(EntityY(it\collider)-EntityY(room1,True)),EntityZ(room2,True)+z,0.01,True)
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					door2\open = True
					door1\open = False
					door1\TriggeredElevatorButton = 0
					door2\TriggeredElevatorButton = 0
					PlaySound2(ElevatorBeepSFX, Camera, room1, 4.0)
				EndIf
			Else
				State = State + FPSfactor
				
				If State > 500 Then 
					door1\locked = False
					door2\locked = True				
					State = 0

					For p.players = Each players
						If Abs(EntityX(p\AnticheatPivot)-EntityX(room2,True))<280.0*RoomScale+(0.015*0.05) Then
							If Abs(EntityZ(p\AnticheatPivot)-EntityZ(room2,True))<280.0*RoomScale+(0.015*0.05) Then	
								If Abs(EntityY(p\AnticheatPivot)-EntityY(room2,True))<280.0*RoomScale+(0.015*0.05) Then
									;PositionEntity(p\Pivot, EntityX(p\Pivot), EntityY(p\Pivot)-0.32, EntityZ(p\Pivot))
									;ResetEntity p\Pivot
									
									If (Not ignorerotation) Then
										dist# = Distance(EntityX(p\AnticheatPivot),EntityZ(p\AnticheatPivot),EntityX(room2,True),EntityZ(room2,True))
										dir# = point_direction(EntityX(p\AnticheatPivot),EntityZ(p\AnticheatPivot),EntityX(room2,True),EntityZ(room2,True))
										dir=dir+EntityYaw(room1,True)-EntityYaw(room2,True)
										x# = Max(Min(Cos(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min(Sin(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										RotateEntity p\Pivot,EntityPitch(p\Pivot),EntityYaw(room2,True)+angleDist(EntityYaw(p\Pivot),EntityYaw(room1,True)),EntityRoll(p\Pivot),True
									Else
										x# = Max(Min((EntityX(p\AnticheatPivot)-EntityX(room2,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min((EntityZ(p\AnticheatPivot)-EntityZ(room2,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
									EndIf

									
									teleported = False
									If p\PlayerRoomName = "gatea" Then
										For ra.Rooms = Each Rooms
											If ra\RoomTemplate\Name = "gateaentrance" Then
											
												;PositionEntity(p\AnticheatPivot, EntityX(p\Pivot), 0.302, EntityZ(p\Pivot))
												;ResetEntity p\AnticheatPivot

												;PositionEntity(p\Pivot, EntityX(p\Pivot), 0.302, EntityZ(p\Pivot))
												;ResetEntity p\Pivot

												mp_SetPlayerRoomID(p, ra)
												teleported = True
												Exit
											EndIf
										Next
									EndIf
									
									SetPlayerPositionEx(p\ID, p\PlayerRoomID, EntityX(room1,True)+x,EntityY(room1,True)+0.32,EntityZ(room1,True)+z)
									p\ShouldDebugPosition = True
								EndIf
							EndIf
						EndIf
					Next

					For n.NPCs = Each NPCs
						If Abs(EntityX(n\Collider)-EntityX(room2,True))<280.0*RoomScale+(0.015*0.05) Then
							If Abs(EntityZ(n\Collider)-EntityZ(room2,True))<280.0*RoomScale+(0.015*0.05) Then
								If Abs(EntityY(n\Collider)-EntityY(room2,True))<280.0*RoomScale+(0.015*0.05) Then
									If (Not ignorerotation) Then
										dist# = Distance(EntityX(n\Collider,True),EntityZ(n\Collider,True),EntityX(room2,True),EntityZ(room2,True))
										dir# = point_direction(EntityX(n\Collider,True),EntityZ(n\Collider,True),EntityX(room2,True),EntityZ(room2,True))
										dir=dir+EntityYaw(room1,True)-EntityYaw(room2,True)
										x# = Max(Min(Cos(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min(Sin(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
										RotateEntity n\Collider,EntityPitch(n\Collider,True),EntityYaw(room2,True)+angleDist(EntityYaw(n\Collider,True),EntityYaw(room1,True)),EntityRoll(n\Collider,True),True
									Else
										x# = Max(Min((EntityX(n\Collider)-EntityX(room2,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
										z# = Max(Min((EntityZ(n\Collider)-EntityZ(room2,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
									EndIf
									
									TeleportEntity(n\Collider, EntityX(room1,True)+x,(0.1*0.05)+EntityY(room1,True)+(EntityY(n\Collider)-EntityY(room2,True)),EntityZ(room1,True)+z,n\CollRadius,True)
									If n = Curr173
										Curr173\IdleTimer = 10
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					For it.Items = Each Items
						If it\picker < 1 Then
							If Abs(EntityX(it\collider)-EntityX(room2,True))<280.0*RoomScale+(0.015*0.05) Then
								If Abs(EntityZ(it\collider)-EntityZ(room2,True))<280.0*RoomScale+(0.015*0.05) Then
									If Abs(EntityY(it\collider)-EntityY(room2,True))<280.0*RoomScale+(0.015*0.05) Then
										If (Not ignorerotation) Then
											dist# = Distance(EntityX(it\collider,True),EntityZ(it\collider,True),EntityX(room2,True),EntityZ(room2,True))
											dir# = point_direction(EntityX(it\collider,True),EntityZ(it\collider,True),EntityX(room2,True),EntityZ(room2,True))
											dir=dir+EntityYaw(room1,True)-EntityYaw(room2,True)
											x# = Max(Min(Cos(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
											z# = Max(Min(Sin(dir)*dist,280*RoomScale-0.22),-280*RoomScale+0.22)
											RotateEntity it\collider,EntityPitch(it\collider,True),EntityYaw(room2,True)+angleDist(EntityYaw(it\collider,True),EntityYaw(room1,True)),EntityRoll(it\collider,True),True
										Else
											x# = Max(Min((EntityX(it\collider)-EntityX(room2,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
											z# = Max(Min((EntityZ(it\collider)-EntityZ(room2,True)),280*RoomScale-0.22),-280*RoomScale+0.22)
										EndIf
										
										TeleportEntity(it\collider, EntityX(room1,True)+x,(0.1*0.05)+EntityY(room1,True)+(EntityY(it\collider)-EntityY(room2,True)),EntityZ(room1,True)+z,0.01,True)
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					
					door1\open = True
					door2\open = False
					door1\TriggeredElevatorButton = 0
					door2\TriggeredElevatorButton = 0
					PlaySound2(ElevatorBeepSFX, Camera, room2, 4.0)
				EndIf	
				
			EndIf
		EndIf
	EndIf
	
	Return State
	
End Function
;-------------------------------------------------------------------------------------------------------

Type Props
	Field file$
	Field obj
	Field X#, Y#, Z#, ScaleX#, ScaleY#, ScaleZ#, RotX#, RotY#, RotZ#
	Field roomtemplate.RoomTemplates
End Type

Function CreatePropObj.Props(file$)
	Local p.Props
	For p.Props = Each Props
		If p\file = file Then
			p2.Props = New props
			p2\file = file
			p2\obj = CopyEntity(p\obj)
			HideEntity p2\obj
			Return p2
		EndIf
	Next
	
	p.Props = New Props
	p\file = file
	p\obj = LoadMesh_Strict(file)
	HideEntity p\obj
	Return p
End Function

Function LoadRoomProps(r.Rooms)
	If r\RoomTemplate <> Null Then
		Local Model% = 0
		For p.Props = Each Props
			If p\RoomTemplate = r\RoomTemplate Then
				Model = CopyEntity(p\obj)
				EntityParent Model, r\obj
				PositionEntity Model, p\X, p\Y, p\Z
				RotateEntity Model, p\rotX, p\rotY, p\rotZ
				ScaleEntity Model, p\scaleX, p\scaleY, p\scaleZ
				EntityType Model, HIT_MAP
			EndIf
		Next
	EndIf
End Function

;-------------------------------------------------------------------------------------------------------


Function SetRoom(room_name$,room_type%,pos%,min_pos%,max_pos%) ;place a room without overwriting others
	If max_pos<min_pos Then DebugLog "Can't place "+room_name : Return False
	DebugLog "--- SETROOM: "+Upper(room_name)+" ---"
	Local looped%,can_place%
	looped = False
	can_place = True
	While MapRoom(room_type,pos)<>""
		DebugLog "found "+MapRoom(room_type,pos)
		pos=pos+1
		If pos>max_pos Then
			If looped=False Then
				pos=min_pos+1 : looped=True
			Else
				can_place=False
				Exit
			EndIf
		EndIf
	Wend
	DebugLog room_name+" "+Str(pos)
	If can_place=True Then
		DebugLog "--------------"
		MapRoom(room_type,pos)=room_name
		Return True
	Else
		DebugLog "couldn't place "+room_name
		Return False
	EndIf
End Function

Function GetZone(y%)
	Return Min(Floor((Float(MapWidth-y)/MapWidth*ZONEAMOUNT)),ZONEAMOUNT-1)
End Function

;-------------------------------------------------------------------------------------------------------


Function load_terrain(hmap,yscale#=0.7,t1%,t2%,mask%)
	
	DebugLog "load_terrain: "+hmap
	
	; load the heightmap
	If hmap = 0 Then RuntimeError "Heightmap image "+hmap+" does not exist."
	
	; store heightmap dimensions
	Local x = ImageWidth(hmap)-1, y = ImageHeight(hmap)-1
	Local lx,ly,index
	
	; load texture and lightmaps
	If t1 = 0 Then RuntimeError "load_terrain error: invalid texture 1"
	If t2 = 0 Then RuntimeError "load_terrain error: invalid texture 2"
	If mask = 0 Then RuntimeError "load_terrain error: invalid texture mask"
	
	; auto scale the textures to the right size
	If t1 Then ScaleTexture t1,x/4,y/4
	If t2 Then ScaleTexture t2,x/4,y/4
	If mask Then ScaleTexture mask,x,y
	
	; start building the terrain
	Local mesh = CreateMesh()
	Local surf = CreateSurface(mesh)
	
	; create some verts for the terrain
	For ly = 0 To y
		For lx = 0 To x
			AddVertex surf,lx,0,ly,1.0/lx,1.0/ly
		Next
	Next
	RenderWorld
			
	; connect the verts with faces
	For ly = 0 To y-1
		For lx = 0 To x-1
			AddTriangle surf,lx+((x+1)*ly),lx+((x+1)*ly)+(x+1),(lx+1)+((x+1)*ly)
			AddTriangle surf,(lx+1)+((x+1)*ly),lx+((x+1)*ly)+(x+1),(lx+1)+((x+1)*ly)+(x+1)
		Next
	Next
			
	; position the terrain to center 0,0,0
	Local mesh2% = CopyMesh(mesh,mesh)
	Local surf2% = GetSurface(mesh2,1)
	PositionMesh mesh, -x/2.0,0,-y/2.0
	PositionMesh mesh2, -x/2.0,0.01,-y/2.0
	
	; alter vertice height to match the heightmap red channel
	LockBuffer ImageBuffer(hmap)
	LockBuffer TextureBuffer(mask)
	;SetBuffer 
	For lx = 0 To x
		For ly = 0 To y
			;using vertex alpha and two meshes instead of FE_ALPHAWHATEVER
			;it doesn't look perfect but it does the job
			;you might get better results by downscaling the mask to the same size as the heightmap
			Local maskX# = Min(lx*Float(TextureWidth(mask))/Float(ImageWidth(hmap)),TextureWidth(mask)-1)
			Local maskY# = TextureHeight(mask)-Min(ly*Float(TextureHeight(mask))/Float(ImageHeight(hmap)),TextureHeight(mask)-1)
			RGB1=ReadPixelFast(Min(lx,x-1),y-Min(ly,y-1),ImageBuffer(hmap))
			r=(RGB1 And $FF0000)Shr 16 ;separate out the red
			Local alpha#=(((ReadPixelFast(Max(maskX-5,5),Max(maskY-5,5),TextureBuffer(mask)) And $FF000000) Shr 24)/$FF)
			alpha#=alpha+(((ReadPixelFast(Min(maskX+5,TextureWidth(mask)-5),Min(maskY+5,TextureHeight(mask)-5),TextureBuffer(mask)) And $FF000000) Shr 24)/$FF)
			alpha#=alpha+(((ReadPixelFast(Max(maskX-5,5),Min(maskY+5,TextureHeight(mask)-5),TextureBuffer(mask)) And $FF000000) Shr 24)/$FF)
			alpha#=alpha+(((ReadPixelFast(Min(maskX+5,TextureWidth(mask)-5),Max(maskY-5,5),TextureBuffer(mask)) And $FF000000) Shr 24)/$FF)
			alpha#=alpha*0.25
			alpha#=Sqr(alpha)
			
			index = lx + ((x+1)*ly)
			VertexCoords surf, index , VertexX(surf,index), r*yscale,VertexZ(surf,index)
			VertexCoords surf2, index , VertexX(surf2,index), r*yscale,VertexZ(surf2,index)
			VertexColor surf2, index, 255.0,255.0,255.0,alpha
			; set the terrain texture coordinates
			VertexTexCoords surf,index,lx,-ly 
			VertexTexCoords surf2,index,lx,-ly 
		Next
	Next
	UnlockBuffer TextureBuffer(mask)
	UnlockBuffer ImageBuffer(hmap)
	
	UpdateNormals mesh
	UpdateNormals mesh2
	
	EntityTexture mesh,t1,0,0
	;EntityTexture mesh,mask,0,1
	EntityTexture mesh2,t2,0,0;2
	
	EntityFX mesh, 1
	EntityFX mesh2, 1+2+32
	
	Return mesh
End Function


Global UpdateRoomLightsTimer# = 0.0

Function UpdateRoomLights(cam%)
	
	Local r.Rooms, i, random#, alpha#, dist#
	
	For r.Rooms = Each Rooms
		If r\dist < HideDistance*0.7 Or r = PlayerRoom Then
			For i = 0 To r\MaxLights%
				If r\Lights%[i]<>0 Then
					If EnableRoomLights% And (SecondaryLightOn>0.5) And cam%=Camera Then
						EntityOrder r\LightSprites2[i],-1
						If UpdateRoomLightsTimer=0.0 Then
							ShowEntity r\LightSprites[i]
							
							If EntityDistance(cam%,r\Lights%[i])<8.5 Then
								If r\LightHidden[i] Then
									ShowEntity r\Lights%[i]
									r\LightHidden[i] = False
								EndIf
							Else
								If (Not r\LightHidden[i]) Then
									HideEntity r\Lights%[i]
									r\LightHidden[i] = True
								EndIf
							EndIf
							
							If (EntityDistance(cam%,r\LightSprites2[i])<8.5 Or r\RoomTemplate\UseLightCones) Then
								If EntityVisible(cam%,r\LightSpritesPivot[i]) Or r\RoomTemplate\UseLightCones Then
									If r\LightSpriteHidden%[i] Then
										ShowEntity r\LightSprites2%[i]
										r\LightSpriteHidden%[i] = False
									EndIf
									If PlayerRoom\RoomTemplate\Name$ = "173" Then
										random# = Rnd(0.38,0.42)
									Else
										If r\LightFlicker%[i]<5 Then
											random# = Rnd(0.38,0.42)
										ElseIf r\LightFlicker%[i]>4 And r\LightFlicker%[i]<10 Then
											random# = Rnd(0.35,0.45)
										Else
											random# = Rnd(0.3,0.5)
										EndIf
									EndIf
									ScaleSprite r\LightSprites2[i],random#,random#
									
									dist# = (EntityDistance(cam%,r\LightSpritesPivot[i])+0.5)/7.5
									dist# = Max(Min(dist#,1.0),0.0)
									alpha# = Float(Inverse(dist#))
									
									If alpha# > 0.0 Then
										EntityAlpha r\LightSprites2[i],Max(3*(Brightness/255)*(r\LightIntensity[i]/2),1)*alpha#
									Else
										;Instead of rendering the sprite invisible, just hiding it if the player is far away from it
										If (Not r\LightSpriteHidden%[i]) Then
											HideEntity r\LightSprites2[i]
											r\LightSpriteHidden%[i]=True
										EndIf
									EndIf
									
									If r\RoomTemplate\UseLightCones Then
										If EntityDistance(cam%,r\LightSprites2[i])>=8.5 Or (Not EntityVisible(cam%,r\LightSpritesPivot[i])) Then
											HideEntity r\LightSprites2%[i]
											r\LightSpriteHidden%[i] = True
										EndIf
									EndIf
								Else
									If (Not r\LightSpriteHidden%[i]) Then
										HideEntity r\LightSprites2%[i]
										r\LightSpriteHidden%[i] = True
									EndIf
								EndIf
							Else
								If (Not r\LightSpriteHidden%[i]) Then
									HideEntity r\LightSprites2%[i]
									r\LightSpriteHidden%[i] = True
									If r\LightCone[i]<>0 Then HideEntity r\LightCone[i]
									If r\LightConeSpark[i]<>0 HideEntity r\LightConeSpark[i]
									EndIf
								EndIf
							
							If r\LightCone[i]<>0 Then ShowEntity r\LightCone[i]
							
							If r\LightConeSpark[i]<>0 Then
								If r\LightConeSparkTimer[i]>0 And r\LightConeSparkTimer[i]<10
									ShowEntity r\LightConeSpark[i]
									r\LightConeSparkTimer[i]=r\LightConeSparkTimer[i]+FPSfactor
								Else
									HideEntity r\LightConeSpark[i]
									r\LightConeSparkTimer[i]=0
								EndIf
							EndIf
							
							If r\LightCone[i]<>0 Then
								ScaleEntity r\LightCone[i],0.005+Max(((-0.4+random#)*0.025),0),0.005+Max(((-0.4+random#)*0.025),0),0.005+Max(((-0.4+random#)*0.025),0)
								If r\LightFlicker%[i]>4 Then
									If Rand(400)=1 Then
										SetEmitter(r\LightSpritesPivot[i],ParticleEffect[0])
										PlaySound2(IntroSFX(Rand(10,12)),cam,r\LightSpritesPivot[i])
										ShowEntity r\LightConeSpark[i]
										r\LightConeSparkTimer[i] = FPSfactor
									EndIf
								EndIf
							EndIf
						Else
							If (EntityDistance(cam%,r\LightSprites2[i])<8.5 Or r\RoomTemplate\UseLightCones) Then
								If PlayerRoom\RoomTemplate\Name$ = "173" Then
									random# = Rnd(0.38,0.42)
								Else
									If r\LightFlicker%[i]<5 Then
										random# = Rnd(0.38,0.42)
									ElseIf r\LightFlicker%[i]>4 And r\LightFlicker%[i]<10 Then
										random# = Rnd(0.35,0.45)
									Else
										random# = Rnd(0.3,0.5)
									EndIf
								EndIf
								
								If (Not r\LightSpriteHidden[i]) Then
									ScaleSprite r\LightSprites2[i],random#,random#
								EndIf
							EndIf
							
							If r\LightCone[i]<>0 Then
								ScaleEntity r\LightCone[i],0.005+Max(((-0.4+random#)*0.025),0),0.005+Max(((-0.4+random#)*0.025),0),0.005+Max(((-0.4+random#)*0.025),0)
							EndIf
							
							If r\LightConeSpark[i]<>0 Then
								If r\LightConeSparkTimer[i]>0 And r\LightConeSparkTimer[i]<10 Then
									ShowEntity r\LightConeSpark[i]
									r\LightConeSparkTimer[i]=r\LightConeSparkTimer[i]+FPSfactor
								Else
									HideEntity r\LightConeSpark[i]
									r\LightConeSparkTimer[i]=0
								EndIf
							EndIf
						EndIf
						UpdateRoomLightsTimer = UpdateRoomLightsTimer + FPSfactor
						If UpdateRoomLightsTimer >= 8 Then
							UpdateRoomLightsTimer = 0.0
						EndIf
					ElseIf cam%=Camera Then
						If SecondaryLightOn<=0.5 Then
							HideEntity r\LightSprites[i]
						Else
							ShowEntity r\LightSprites[i]
						EndIf
						
						If (Not r\LightHidden[i]) Then
							HideEntity r\Lights%[i]
							r\LightHidden[i] = True
						EndIf
						If (Not r\LightSpriteHidden[i]) Then
							HideEntity r\LightSprites2[i]
							r\LightSpriteHidden[i]=True
						EndIf
						If r\LightCone[i]<>0 Then HideEntity r\LightCone[i]
						If r\LightConeSpark[i]<>0 Then HideEntity r\LightConeSpark[i]
					Else
						;This will make the lightsprites not glitch through the wall when they are rendered by the cameras
						EntityOrder r\LightSprites2[i],0
					EndIf
				EndIf
			Next
		EndIf
	Next
	
End Function

Function UpdateCheckpointMonitors(numb%)
	Local i,sf,b,t1
	Local entity%
	
	If numb% = 0
		entity% = Monitor2
		UpdateCheckpoint1 = True
	Else
		entity% = Monitor3
		UpdateCheckpoint2 = True
	EndIf
	
	For i = 2 To CountSurfaces(entity)
		sf = GetSurface(entity,i)
		b = GetSurfaceBrush(sf)
		If b<>0 Then
			t1 = GetBrushTexture(b,0)
			If t1<>0 Then
				name$ = StripPath(TextureName(t1))
				If Lower(name) <> "monitortexture.jpg"
					If numb% = 0
						If MonitorTimer# < 50
							BrushTexture b, MonitorTexture2, 0, 0
						Else
							BrushTexture b, MonitorTexture4, 0, 0
						EndIf
					Else
						If MonitorTimer2# < 50
							BrushTexture b, MonitorTexture2, 0, 0
						Else
							BrushTexture b, MonitorTexture3, 0, 0
						EndIf
					EndIf
					PaintSurface sf,b
				EndIf
				If name<>"" Then FreeTexture t1
			EndIf
			FreeBrush b
		EndIf
	Next
	
End Function

Function TurnCheckpointMonitorsOff(numb%)
	Local i,sf,b,t1
	Local entity%
	
	If numb% = 0
		entity% = Monitor2
		UpdateCheckpoint1 = False
		MonitorTimer# = 0.0
	Else
		entity% = Monitor3
		UpdateCheckpoint2 = False
		MonitorTimer2# = 0.0
	EndIf
	
	For i = 2 To CountSurfaces(entity)
		sf = GetSurface(entity,i)
		b = GetSurfaceBrush(sf)
		If b<>0 Then
			t1 = GetBrushTexture(b,0)
			If t1<>0 Then
				name$ = StripPath(TextureName(t1))
				If Lower(name) <> "monitortexture.jpg"
					BrushTexture b, MonitorTextureOff, 0, 0
					PaintSurface sf,b
				EndIf
				If name<>"" Then FreeTexture t1
			EndIf
			FreeBrush b
		EndIf
	Next
	
End Function

Function TimeCheckpointMonitors()

End Function

Function AmbientLightRooms(value%=0)
	Local mesh%,surf%,brush%,tex0%
	
	If value=AmbientLightRoomVal Then Return
	AmbientLightRoomVal = value
	
	Local oldbuffer% = BackBuffer() ;probably shouldn't make assumptions here but who cares, why wouldn't it use the backbuffer ;GetBuffer()
	
	SetBuffer TextureBuffer(AmbientLightRoomTex)
	
	ClsColor value,value,value
	Cls
	ClsColor 0,0,0
	
	SetBuffer oldbuffer
End Function

;#########################################################################
;CHUNKS FOR 1499
;#########################################################################

Dim CHUNKDATA(64,64)

Function SetChunkDataValues()
	Local StrTemp$,i%,j%
	StrTemp$ = ""
	SeedRnd GenerateSeedNumber(Server\RandomSeed)
	
	For i = 0 To 63
		For j = 0 To 63
			CHUNKDATA(i,j)=Rand(0,GetINIInt("Data\1499chunks.INI","general","count"))
		Next
	Next
	
	SeedRnd MilliSecs2()
	
End Function

Type ChunkPart
	Field Amount%
	Field obj%[128]
	Field RandomYaw#[128]
	Field ID
End Type

Function CreateChunkParts(r.Rooms)
	Local File$ = "Data\1499chunks.INI"
	Local ChunkAmount% = GetINIInt(File$,"general","count")
	Local i%,StrTemp$,j%
	Local chp.ChunkPart,chp2.ChunkPart
	Local obj%
	StrTemp$ = ""
	SeedRnd GenerateSeedNumber(Server\RandomSeed)
	
	For i = 0 To ChunkAmount%
		Local loc% = GetINISectionLocation(File$,"chunk"+i)
		If loc > 0 Then
			StrTemp$ = GetINIString2(File,loc%,"count")
			chp = New ChunkPart
			chp\Amount% = Int(StrTemp$)
			DebugLog "------------------"
			For j = 0 To Int(StrTemp$)
				Local objID% = GetINIString2(File$,loc%,"obj"+j)
				Local x$ = GetINIString2(File$,loc%,"obj"+j+"-x")
				Local z$ = GetINIString2(File$,loc%,"obj"+j+"-z")
				Local yaw$ = GetINIString2(File$,loc%,"obj"+j+"-yaw")
				DebugLog "1499 chunk X/Z/Yaw: "+x$+"|"+z$+"|"+yaw$
				chp\obj%[j] = CopyEntity(r\Objects[objID%])
				If Lower(yaw$) = "random"
					chp\RandomYaw#[j] = Rnd(360)
					RotateEntity chp\obj[j],0,chp\RandomYaw[j],0
				Else
					RotateEntity chp\obj[j],0,Float(yaw),0
				EndIf
				PositionEntity chp\obj[j],Float(x),0,Float(z)
				ScaleEntity chp\obj[j],RoomScale,RoomScale,RoomScale
				EntityType chp\obj[j],HIT_MAP
				EntityPickMode chp\obj[j],2
				HideEntity chp\obj[j]
				;EntityParent chp\obj[j],r\obj
			Next
			chp2 = Before(chp)
			If chp2 <> Null
				chp\ID = chp2\ID+1
			EndIf
			DebugLog "<<<<<<<<<<<<<<<<"
			DebugLog "Generated 1499 chunk "+chp\ID+" sucessfully"
		EndIf
	Next
	
	SeedRnd MilliSecs2()
	
End Function

Type Chunk
	Field obj%[128]
	Field x#,z#,y#
	Field Amount%
	Field IsSpawnChunk%
	Field ChunkPivot%
	;Field ChunkPivotDebug%
	;Field ChunkDebugObj%
	Field PlatForm%
End Type

Function CreateChunk.Chunk(obj%,x#,y#,z#,isSpawnChunk%=False)
	Local ch.Chunk = New Chunk
	Local i%, chp.ChunkPart
	
	ch\ChunkPivot = CreatePivot()
	ch\x = x#
	ch\y = y#
	ch\z = z#
	PositionEntity ch\ChunkPivot,ch\x+20.0,ch\y,ch\z+20.0,True
	
	ch\IsSpawnChunk = isSpawnChunk
	
	;ch\ChunkPivotDebug% = CreateSphere(8,ch\ChunkPivot)
	;EntityColor ch\ChunkPivotDebug,255*(Not isSpawnChunk),255*(isSpawnChunk),0
	;EntityFX ch\ChunkPivotDebug,1
	
	;ch\ChunkDebugObj = CreateCube(ch\ChunkPivotDebug)
	;ScaleEntity ch\ChunkDebugObj,20,0.1,20
	;EntityColor ch\ChunkDebugObj,Rand(255),Rand(255),Rand(255)
	;EntityFX ch\ChunkDebugObj,1
	;EntityAlpha ch\ChunkDebugObj,0.2
	
	If obj% > -1
		ch\Amount% = GetINIInt("Data\1499chunks.INI","chunk"+obj,"count")
		For chp = Each ChunkPart
			If chp\ID = obj%
				For i = 0 To ch\Amount
					ch\obj[i] = CopyEntity(chp\obj[i],ch\ChunkPivot)
				Next
			EndIf
		Next
	EndIf
	
	ch\PlatForm = CopyEntity(Dimension1499\room\Objects[0],ch\ChunkPivot)
	EntityType ch\PlatForm,HIT_MAP
	EntityPickMode ch\PlatForm,2
	
	Return ch
End Function

Function UpdateChunks(r.Rooms,ChunkPartAmount%,spawnNPCs%=True,Ent,p.players)
	Local ch.Chunk,StrTemp$,i%,x#,z#,ch2.Chunk,y#,n.NPCs,j%
	Local ChunkX#,ChunkZ#,ChunkMaxDistance#=3*40
	
	; =================== Exploit fix
	;if dimension1499\room\id = p\playerroomid then
	If Distance(EntityX(Ent), EntityZ(Ent), 8, 0) > 1000 Then
		p\x = 8
		p\y = 805
		p\z = 0
		p\PlayerRoomID = r\ID
		mp_UpdatePlayerPosition(p)
	EndIf
	;endif
	; ===================
	
	ChunkX# = Int(EntityX(Ent)/40)
	ChunkZ# = Int(EntityZ(Ent)/40)
	
	y# = EntityY(Dimension1499\room\obj)
	x# = -ChunkMaxDistance#+(ChunkX*40)
	z# = -ChunkMaxDistance#+(ChunkZ*40)
	
	Local CurrChunkData% = 0, MaxChunks% = GetINIInt("Data\1499chunks.INI","general","count")
	
	Repeat
		Local chunkfound% = False
		For ch = Each Chunk
			If ch\x# = x#
				If ch\z# = z#
					chunkfound% = True
					Exit
				EndIf
			EndIf
		Next
		If (Not chunkfound)
			CurrChunkData = CHUNKDATA(Abs(((x+32)/40) Mod 64),Abs(((z+32)/40) Mod 64))
			;ch2 = CreateChunk(Rand(0,GetINIInt("Data\1499chunks.INI","general","count")),x#,y#,z#)
			ch2 = CreateChunk(CurrChunkData%,x#,y#,z#)
			ch2\IsSpawnChunk = False
		EndIf
		x#=x#+40.0
		If x# > (ChunkMaxDistance#+(ChunkX*40))
			z#=z#+40.0
			x# = -ChunkMaxDistance#+(ChunkX*40)
		EndIf
	Until z# > (ChunkMaxDistance#+(ChunkZ*40))
	
	For ch = Each Chunk
;		If DebugHUD
;			ShowEntity ch\ChunkPivotDebug
;		Else
;			HideEntity ch\ChunkPivotDebug
;		EndIf
		If (Not ch\IsSpawnChunk)
			If Distance(EntityX(Ent),EntityZ(Ent),EntityX(ch\ChunkPivot),EntityZ(ch\ChunkPivot))>ChunkMaxDistance
				FreeEntity ch\ChunkPivot
				Delete ch
			EndIf
		EndIf
	Next
	
End Function

Function HideChunks()
	Local ch.Chunk,i
	
	For ch = Each Chunk
		If (Not ch\IsSpawnChunk)
			For i = 0 To ch\Amount
				FreeEntity ch\obj[i]
			Next
			FreeEntity ch\PlatForm
			FreeEntity ch\ChunkPivot
			Delete ch
		EndIf
	Next
	
End Function

Function DeleteChunks()
	
	Delete Each Chunk
	Delete Each ChunkPart
	
End Function

Type Dummy1499
	Field anim%
	Field obj%
End Type

;#########################################################################
;END CHUNKS
;#########################################################################

Function AddLightCones(room.Rooms)
	Local i
	
	For i = 0 To MaxRoomLights-1
		If room\Lights[i]<>0
			room\LightCone[i] = CopyEntity(LightConeModel)
			ScaleEntity room\LightCone[i],0.01,0.01,0.01
			EntityColor room\LightCone[i],room\LightR[i],room\LightG[i],room\LightB[i]
			EntityAlpha room\LightCone[i],0.15
			EntityBlend room\LightCone[i],3
			PositionEntity room\LightCone[i],EntityX(room\LightSpritesPivot[i],True),EntityY(room\LightSpritesPivot[i],True),EntityZ(room\LightSpritesPivot[i],True),True
			EntityParent room\LightCone[i],room\LightSpritesPivot[i]
			
			If room\LightFlicker%[i] > 4
				room\LightConeSpark[i] = CreateSprite()
				ScaleSprite room\LightConeSpark[i],1.0,1.0
				EntityTexture room\LightConeSpark[i],ParticleTextures(8)
				SpriteViewMode room\LightConeSpark[i],2
				EntityFX room\LightConeSpark[i],1
				RotateEntity room\LightConeSpark[i],-90,0,0
				EntityBlend room\LightConeSpark[i],3
				EntityAlpha room\LightConeSpark[i],1.0
				PositionEntity room\LightConeSpark[i],EntityX(room\LightSpritesPivot[i],True),EntityY(room\LightSpritesPivot[i],True)+0.05,EntityZ(room\LightSpritesPivot[i],True),True
				EntityParent room\LightConeSpark[i],room\LightSpritesPivot[i]
			EndIf
		EndIf
	Next
	
End Function

Function CalculateRoomTemplateExtents(r.RoomTemplates)
	If r\DisableOverlapCheck Then Return
	
	GetMeshExtents(GetChild(r\obj,2))
	r\MinX = Mesh_MinX
	r\MinY = Mesh_MinY
	r\MinZ = Mesh_MinZ
	r\MaxX = Mesh_MaxX
	r\MaxY = Mesh_MaxY
	r\MaxZ = Mesh_MaxZ
	
	DebugLog("roomtemplateextents: "+r\MinX+", "+r\MinY	+", "+r\MinZ	+", "+r\MaxX	+", "+r\MaxY+", "+r\MaxZ)
End Function

Function CountRoomTriggerBox(r.Rooms)
	
	For i = 0 To r\TriggerboxAmount-1
		r\TriggerBox_sx[i] = EntityScaleX(r\Triggerbox[i], 1)
		r\TriggerBox_sy[i] = Max(EntityScaleY(r\Triggerbox[i], 1), 0.001)
		r\TriggerBox_sz[i] = EntityScaleZ(r\Triggerbox[i], 1)
	
		GetMeshExtents(r\Triggerbox[i])
		r\TriggerBox_Mesh_MinX[i] = Mesh_MinX
		r\TriggerBox_Mesh_MinY[i] = Mesh_MinY
		r\TriggerBox_Mesh_MinZ[i] = Mesh_MinZ
		
		r\TriggerBox_Mesh_MaxX[i] = Mesh_MaxX
		r\TriggerBox_Mesh_MaxY[i] = Mesh_MaxY
		r\TriggerBox_Mesh_MaxZ[i] = Mesh_MaxZ
	Next
	
End Function

Function CalculateRoomExtents(r.Rooms)
	If r\RoomTemplate\DisableOverlapCheck Then Return
	
	;shrink the extents slightly - we don't care if the overlap is smaller than the thickness of the walls
	Local shrinkAmount# = 0.05
	
	;convert from the rooms local space to world space
	TFormVector(r\RoomTemplate\MinX, r\RoomTemplate\MinY, r\RoomTemplate\MinZ, r\obj, 0)
	r\MinX = TFormedX() + shrinkAmount + r\x
	r\MinY = TFormedY() + shrinkAmount
	r\MinZ = TFormedZ() + shrinkAmount + r\z
	
	;convert from the rooms local space to world space
	TFormVector(r\RoomTemplate\MaxX, r\RoomTemplate\MaxY, r\RoomTemplate\MaxZ, r\obj, 0)
	r\MaxX = TFormedX() - shrinkAmount + r\x
	r\MaxY = TFormedY() - shrinkAmount
	r\MaxZ = TFormedZ() - shrinkAmount + r\z
	
	If (r\MinX > r\MaxX) Then
		Local tempX# = r\MaxX
		r\MaxX = r\MinX
		r\MinX = tempX
	EndIf
	If (r\MinZ > r\MaxZ) Then
		Local tempZ# = r\MaxZ
		r\MaxZ = r\MinZ
		r\MinZ = tempZ
	EndIf
	
	DebugLog("roomextents: "+r\MinX+", "+r\MinY	+", "+r\MinZ	+", "+r\MaxX	+", "+r\MaxY+", "+r\MaxZ)
End Function

Function CheckRoomOverlap(r1.Rooms, r2.Rooms)
	If (r1\MaxX	<= r2\MinX Or r1\MaxY <= r2\MinY Or r1\MaxZ <= r2\MinZ) Then Return False
	If (r1\MinX	>= r2\MaxX Or r1\MinY >= r2\MaxY Or r1\MinZ >= r2\MaxZ) Then Return False
	
	Return True
End Function

Function PreventRoomOverlap(r.Rooms)
	If r\RoomTemplate\DisableOverlapCheck Then Return
	
	Local r2.Rooms,r3.Rooms
	
	Local isIntersecting% = False
	
	;Just skip it when it would try to check for the checkpoints
	If r\RoomTemplate\Name = "checkpoint1" Or r\RoomTemplate\Name = "checkpoint2" Or r\RoomTemplate\Name = "start" Then Return True
	
	;First, check if the room is actually intersecting at all
	For r2 = Each Rooms
		If r2 <> r And (Not r2\RoomTemplate\DisableOverlapCheck) Then
			If CheckRoomOverlap(r, r2) Then
				isIntersecting = True
				Exit
			EndIf
		EndIf
	Next
	
	;If not, then simply return it as True
	If (Not isIntersecting)
		Return True
	EndIf
	
	;Room is interseting: First, check if the given room is a ROOM2, so we could potentially just turn it by 180 degrees
	isIntersecting = False
	Local x% = r\x/8.0
	Local y% = r\z/8.0
	If r\RoomTemplate\Shape = ROOM2 Then
		;Room is a ROOM2, let's check if turning it 180 degrees fixes the overlapping issue
		r\angle = r\angle + 180
		RotateEntity r\obj,0,r\angle,0
		CalculateRoomExtents(r)
		
		For r2 = Each Rooms
			If r2 <> r And (Not r2\RoomTemplate\DisableOverlapCheck) Then
				If CheckRoomOverlap(r, r2) Then
					;didn't work -> rotate the room back and move to the next step
					isIntersecting = True
					r\angle = r\angle - 180
					RotateEntity r\obj,0,r\angle,0
					CalculateRoomExtents(r)
					Exit
				EndIf
			EndIf
		Next
	Else
		isIntersecting = True
	EndIf
	
	;room is ROOM2 and was able to be turned by 180 degrees
	If (Not isIntersecting)
		DebugLog "ROOM2 turning succesful! "+r\RoomTemplate\Name
		Return True
	EndIf
	
	;Room is either not a ROOM2 or the ROOM2 is still intersecting, now trying to swap the room with another of the same type
	isIntersecting = True
	Local temp2,x2%,y2%,rot%,rot2%
	For r2 = Each Rooms
		If r2 <> r And (Not r2\RoomTemplate\DisableOverlapCheck)  Then
			If r\RoomTemplate\Shape = r2\RoomTemplate\Shape And r\zone = r2\zone And (r2\RoomTemplate\Name <> "checkpoint1" And r2\RoomTemplate\Name <> "checkpoint2" And r2\RoomTemplate\Name <> "start") Then
				x = r\x/8.0
				y = r\z/8.0
				rot = r\angle
				
				x2 = r2\x/8.0
				y2 = r2\z/8.0
				rot2 = r2\angle
				
				isIntersecting = False
				
				r\x = x2*8.0
				r\z = y2*8.0
				r\angle = rot2
				PositionEntity r\obj,r\x,r\y,r\z
				RotateEntity r\obj,0,r\angle,0
				CalculateRoomExtents(r)
				
				r2\x = x*8.0
				r2\z = y*8.0
				r2\angle = rot
				PositionEntity r2\obj,r2\x,r2\y,r2\z
				RotateEntity r2\obj,0,r2\angle,0
				CalculateRoomExtents(r2)
				
				;make sure neither room overlaps with anything after the swap
				For r3 = Each Rooms
					If (Not r3\RoomTemplate\DisableOverlapCheck) Then
						If r3 <> r Then
							If CheckRoomOverlap(r, r3) Then
								isIntersecting = True
								Exit
							EndIf
						EndIf
						If r3 <> r2 Then
							If CheckRoomOverlap(r2, r3) Then
								isIntersecting = True
								Exit
							EndIf
						EndIf	
					EndIf
				Next
				
				;Either the original room or the "reposition" room is intersecting, reset the position of each room to their original one
				If isIntersecting Then
					r\x = x*8.0
					r\z = y*8.0
					r\angle = rot
					PositionEntity r\obj,r\x,r\y,r\z
					RotateEntity r\obj,0,r\angle,0
					CalculateRoomExtents(r)
					
					r2\x = x2*8.0
					r2\z = y2*8.0
					r2\angle = rot2
					PositionEntity r2\obj,r2\x,r2\y,r2\z
					RotateEntity r2\obj,0,r2\angle,0
					CalculateRoomExtents(r2)
					
					isIntersecting = False
				EndIf
			EndIf
		EndIf
	Next
	
	;room was able to the placed in a different spot
	If (Not isIntersecting)
		DebugLog "Room re-placing successful! "+r\RoomTemplate\Name
		Return True
	EndIf
	
	DebugLog "Couldn't fix overlap issue for room "+r\RoomTemplate\Name
	Return False
End Function

Function Update096ElevatorEvent#(e.Events,EventState#,d.Doors,elevatorobj%)
	Local prevEventState# = EventState#
	
	If EventState < 0 Then
		EventState = 0
		prevEventState = 0
	EndIf
	
	If d\openstate = 0 And d\open = False Then
		If Abs(EntityX(e\NearObject)-EntityX(elevatorobj%,True))<=280.0*RoomScale+(0.015*FPSfactor) Then
			If Abs(EntityZ(e\NearObject)-EntityZ(elevatorobj%,True))<=280.0*RoomScale+(0.015*FPSfactor) Then
				If Abs(EntityY(e\NearObject)-EntityY(elevatorobj%,True))<=280.0*RoomScale+(0.015*FPSfactor) Then
					d\locked = True
					If EventState = 0 Then
						TeleportEntity(Curr096\Collider,EntityX(d\frameobj),EntityY(d\frameobj)+1.0,EntityZ(d\frameobj),Curr096\CollRadius)
						PointEntity Curr096\Collider,elevatorobj
						RotateEntity Curr096\Collider,0,EntityYaw(Curr096\Collider),0
						MoveEntity Curr096\Collider,0,0,-0.5
						ResetEntity Curr096\Collider
						Curr096\State = 6
						SetNPCFrame(Curr096,0)
						e\Sound = LoadSound_Strict("SFX\SCP\096\ElevatorSlam.ogg")
						EventState = EventState + FPSfactor * 1.4
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
	
	If EventState > 0 Then
		If prevEventState = 0 Then
			e\SoundCHN = PlaySound_Strict(e\Sound)
		EndIf
		
		If EventState > 70*1.9 And EventState < 70*2+FPSfactor
			CameraShake = 7
		ElseIf EventState > 70*4.2 And EventState < 70*4.25+FPSfactor
			CameraShake = 1
		ElseIf EventState > 70*5.9 And EventState < 70*5.95+FPSfactor
			CameraShake = 1
		ElseIf EventState > 70*7.25 And EventState < 70*7.3+FPSfactor
			CameraShake = 1
			d\fastopen = True
			d\open = True
			Curr096\State = 4
			Curr096\LastSeen = 1
		ElseIf EventState > 70*8.1 And EventState < 70*8.15+FPSfactor
			CameraShake = 1
		EndIf
		
		If EventState <= 70*8.1 Then
			d\openstate = Min(d\openstate,20)
		EndIf
		EventState = EventState + FPSfactor * 1.4
	EndIf
	Return EventState
	
End Function









;~IDEal Editor Parameters:
;~F#111#118#11F#1E12
;~B#1226
;~C#Blitz3D