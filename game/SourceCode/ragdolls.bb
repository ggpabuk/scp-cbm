; TAIL RAGDOLL (new year hat)

Type rg_vector
    Field x#
    Field y#
	Field z#
End Type
Type rd_bone
	Field obj, vector.rg_Vector
	Field attachedto%, axisDeviateX#, axisDeviateY#, axisDeviateZ#
End Type
Function rd_AddEntity.rd_bone(ent, mainent)
	Local ragdoll.rd_bone = new rd_bone
	ragdoll\obj = ent
	ragdoll\attachedto = mainent
	ragdoll\vector = vector_Create()
	ragdoll\axisDeviateX = EntityX(mainent)
	ragdoll\axisDeviateY = EntityY(mainent)
	ragdoll\axisDeviateZ = EntityZ(mainent)
	vector_Update(ragdoll\vector, EntityX(ent), EntityY(ent), EntityZ(ent))
	Return ragdoll
End Function
Function rd_SetForce(ragdoll.rd_bone, x#, y#, z#)
	MoveEntity ragdoll\obj, min(x*FPSfactor, 1.0), min(y*FPSfactor, 1.0), min(z*FPSfactor, 1.0)
End Function
Function rd_Update(ragdoll.rd_bone)
	Local VelocityX#, VelocityY#, VelocityZ#
	vector_Update(ragdoll\vector, EntityX(ragdoll\obj), EntityY(ragdoll\obj), EntityZ(ragdoll\obj))
	VelocityX# = -(ragdoll\vector\x-ragdoll\axisDeviateX)*0.08*FPSfactor
	VelocityY# = -(ragdoll\vector\y-ragdoll\axisDeviateY)*0.08*FPSfactor
	VelocityZ# = -(ragdoll\vector\z-ragdoll\axisDeviateZ)*0.08*FPSfactor
	
	MoveEntity(ragdoll\obj, VelocityX, VelocityY, VelocityZ)
	if VelocityX > 1.0 Or VelocityY > 1.0 Or VelocityZ > 1.0 Then PositionEntity ragdoll\obj, ragdoll\axisDeviateX,ragdoll\axisDeviateY,ragdoll\axisDeviateZ
End Function
Function rd_RemoveAll()
	For ragdolls.rd_bone = Each rd_Bone
		Delete ragdolls\vector
		Delete ragdolls
	Next
End Function
Function rd_Remove(ragdoll.rd_bone)
	Delete ragdoll\vector
	Delete ragdoll
End Function
Function vector_Create.rg_vector()
    v.rg_vector = New rg_vector
    Return v
End Function

Function vector_Update(v.rg_vector, x#, y#, z#)
    v\x = x
    v\y = y
	v\z = z
End Function
