.lib "bbAddon.dll"

; ------------ Maths ------------
RadToDeg#(Angle#):"_RadToDeg@4"
DegToRad#(Angle#):"_DegToRad@4"
LinearInterp#(a#,b#,rate#):"_LinearInterp@12"
CosInterp#(a#,b#,rate#):"_CosInterp@12"
CubicInterp#(a#,b#,c#,d#,rate#):"_CubicInterp@20"

Min#(a#,b#):"_Min@8"
Max#(a#,b#):"_Max@8"

; ------------ Events ------------

EmitEvent(Id%,X%,Y%,Data$,Source%):"_EmitEvent@20"
PollEvent%():"_PollEvent@0"
EventID%():"_EventID@0"
EventX%():"_EventX@0"
EventY%():"_EventY@0"
EventSource%():"_EventSource@0"
EventData$():"_EventData@0"

EventEnableStack(Enable%):"_EventEnableStack@4"
EventStackEnabled%():"_EventStackEnabled@0"
SetEventStackSize(size%):"_SetEventStackSize@4"
EventStackSize%():"_EventStackSize@0"
CountEvents%():"_CountEvents@0"


; ------------ Counter ------------

HDTSupport%():"_HDTSupport@0"

Millisecondes%():"_Millisecondes@0"
SystemCounter%():"_SystemCounter@0"
ResetClock ():"_ResetClock@0"
GetMicroSecs%():"_MicroSecs@0"
ComputerTime%():"_ComputerTime@0"
ElapsedTime%():"_ElapsedTime@0"
PITClock#():"_PITClock@0"
AppMicroCoef#():"_AppMicroCoef@0"

CreateCounter%():"_CreateCounter@0"
ResetCounter( counter ):"_ResetCounter@4"
CounterTime %( counter ):"_CounterTime@4"
CounterElasped%( counter ):"_CounterElasped@4"
CounterFree( counter ) :"_CounterFree@4"
CounterStartedAt%( counter ):"_CounterStartedAt@4"


; ------------ Linked-List ------------

DebugCountLinks%():"_DebugCountLinks@0"
NewList%():"_NewList@0"
AddLast%(List,value$):"_ListAddLast@8"
AddFirst%(List,value$):"_ListAddFirst@8"
InsertBefore%(Link,before):"_ListInsertBefore@8"
InsertAfter%(Link,after):"_ListInsertAfter@8"
DeleteList(List):"_DeleteList@4"
DeleteLink(Link):"_DeleteLink@4"
ListContains%(List,Value$):"_ListContains@8"
ListIsEmpty%(List):"_ListIsEmpty@4"
ListFirst%(list):"_FirstLink@4"
ListLast%(list):"_LastLink@4"
AfterLink%(link):"_AfterLink@4"
BeforeLink%(link):"_BeforeLink@4"
LinkValue$(link):"_LinkValue@4"
SetLinkValue(Link,value$):"_SetLinkValue@8"

LinkCompare%(l1,l2):"_LinkCompare@8"
StrCompare%(a$,b$):"_StrCompare@8"
SwapLinks%(link1,link2):"_SwapLinks@8"
SortList%(List,Sens):"_SortList@8"


; ------------ Tokenizer ------------
Tokenize%(chaine$,Delimiters$, Symbols$):"_Tokenize@12"
