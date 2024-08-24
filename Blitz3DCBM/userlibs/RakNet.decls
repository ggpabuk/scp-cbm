.lib "raknet.dll"

RN_GetRakPeerInterface%():"RN_GetRakPeerInterface@0"
RN_DestroyRakPeerInterface%(rakPeerInterface%):"RN_DestroyRakPeerInterface@4"


RN_Startup%(rakPeerInterface%,maxConnections%, threadSleepTimer%, localPort%):"RN_Startup@16"
RN_IsActive%(rakPeerInterface%):"RN_IsActive@4"
RN_GetMaximumNumberOfPeers%(rakPeerInterface%):"RN_GetMaximumNumberOfPeers@4"
RN_SetMaximumIncomingConnections(rakPeerInterface%,numberAllowed%):"RN_SetMaximumIncomingConnections@8"
RN_GetMaximumIncomingConnections%(rakPeerInterface%):"RN_GetMaximumIncomingConnections@4"
RN_Connect%(rakPeerInterface%,host$,serverport%,passwordData$,passwordDataLength%):"RN_Connect@20"
RN_Shutdown(rakPeerInterface%,blockDuration%):"RN_Shutdown@8"
RN_CloseConnection(rakPeerInterface%,target%,sendDisconnectionNotification%):"RN_CloseConnection@16"
RN_GetConnectionList%(rakPeerInterface%,remoteSystems%,numberOfSystems%):"RN_GetConnectionList@12"
RN_Send%(rakPeerInterface%,data$,length%,priority%,reliability%,orderingStream%,systemAddress%,broadcast%):"RN_Send@32"
RN_SendBitStream%(rakPeerInterface%,bitStream%,priority%,reliability%,orderingChannel%,systemAddress%,broadcast%):"RN_SendBitStream@28"
RN_Receive%(rakPeerInterface%):"RN_Receive@4"
RN_DeallocatePacket%(rakPeerInterface%,packet%):"RN_DeallocatePacket@8"




RN_GetInternalID%(rakPeerInterface%):"RN_GetInternalID@4" 
RN_GetExternalID%(rakPeerInterface%,target%):"RN_GetExternalID@12"
RN_PingPlayer(rakPeerInterface,systemAddress):"RN_PingPlayer@8"
RN_PingHost%(rakPeerInterface%,host$,remotePort%,onlyReplyOnAcceptingConnections):"RN_PingHost@16"
RN_GetAveragePing%(rakPeerInterface,systemAddress):"RN_GetAveragePing@8"
RN_GetLastPing%(rakPeerInterface,systemAddress):"RN_GetLastPing@8"
RN_GetLowestPing%(rakPeerInterface,systemAddress):"RN_GetLowestPing@8"
RN_SetOccasionalPing(rakPeerInterface,doPing):"RN_SetOccasionalPing@8"
RN_SetOfflinePingResponse(rakPeerInterface,data$,length):"RN_SetOfflinePingResponse@12"
RN_GetNumberOfAddresses%(rakPeerInterface%):"RN_GetNumberOfAddresses@4"
RN_GetLocalIP$(rakPeerInterface,index):"RN_GetLocalIP@8"
RN_GetIndexFromSystemAddress%(rakPeerInterface,systemAddress):"RN_GetIndexFromSystemAddress@8"
RN_GetSystemAddressFromIndex%(rakPeerInterface,index):"RN_GetSystemAddressFromIndex@8"

RN_GetUNASSIGNED_SYSTEM_ADDRESS%():"RN_GetUNASSIGNED_SYSTEM_ADDRESS@0"
RN_GetUNASSIGNED_NETWORK_ID%():"RN_GetUNASSIGNED_NETWORK_ID@0"
RN_GetTime%():"RN_GetTime@0"


RN_PacketGetData$(packet%):"RN_PacketGetData@4"
RN_PacketGetBitSize%(packet%):"RN_PacketGetBitSize@4"
RN_PacketGetplayerIndex%(packet%):"RN_PacketGetplayerIndex@4"; Server only according to RakNetTypes.h
RN_PacketGetSystemAddress%(packet%):"RN_PacketGetSystemAddress@4"
RN_PacketGetBinaryAddress(packet%):"RN_PacketGetBinaryAddress@4"
RN_PacketGetPort(packet%):"RN_PacketGetPort@4"
RN_SystemAddressGetBinaryAddress%(systemAddress%):"RN_SystemAddressGetBinaryAddress@4"
RN_SystemAddressGetPort%(systemAddress%):"RN_SystemAddressGetPort@4"


RN_InitializeSecurity(rakPeerInterface%, pubKeyE$, pubKeyN$, privKeyP$, privKeyQ$):"RN_InitializeSecurity@20"
RN_DisableSecurity(rakPeerInterface%):"RN_DisableSecurity@4"
RN_SetIncomingPassword(rakPeerInterface%, passwordData$, passwordDataLength%):"RN_SetIncomingPassword@12"
RN_GetIncomingPassword(rakPeerInterface%, passwordData$, passwordDataLength%):"RN_GetIncomingPassword@12"

RN_ApplyNetworkSimulator(rakPeerInterface%,maxSendBPS#,minExtraPing%,extraPingVariance%):"RN_ApplyNetworkSimulator@16"
RN_IsNetworkSimulatorActive%(rakPeerInterface%):"RN_IsNetworkSimulatorActive@4"

RN_AddToBanList(rakPeerInterface%,ip$,milliseconds%):"RN_AddToBanList@12"
RN_RemoveFromBanList(rakPeerInterface%,ip$):"RN_RemoveFromBanList@8"
RN_ClearBanList(rakPeerInterface%):"RN_ClearBanList@4"
RN_IsBanned%(rakPeerInterface%,ip$):"RN_IsBanned@8"


RN_AdvertiseSystem(rakPeerInterface%,host$,remotePort%, data$,dataLength%):"RN_AdvertiseSystem@20"

;Bitstreams
RN_BitStreamCreate1%(initialBytesToAllocate%):"RN_BitStreamCreate1@4"
RN_BitStreamCreate2%(data$,length%,copydata%):"RN_BitStreamCreate2@12"
RN_BitStreamReset (bitstream%):"RN_BitStreamReset@4"
RN_BitStreamDestroy(bitstream%):"RN_BitStreamDestroy@4"
RN_BitStreamCreateFromPacket%(packet%):"RN_BitStreamCreateFromPacket@4"

RN_BitStreamWriteBool (bitstream%,input%):"RN_BitStreamWriteBool@8"
RN_BitStreamWriteUnsignedChar (bitstream%,input%):"RN_BitStreamWriteUnsignedChar@8"
RN_BitStreamWriteChar (bitstream%,input%):"RN_BitStreamWriteChar@8"
RN_BitStreamWriteUnsignedShort (bitstream%,input%):"RN_BitStreamWriteUnsignedShort@8"
RN_BitStreamWriteShort (bitstream%,input%):"RN_BitStreamWriteShort@8"
RN_BitStreamWriteUnsignedInt (bitstream%,input%):"RN_BitStreamWriteUnsignedInt@8"
RN_BitStreamWriteInt (bitstream%,input%):"RN_BitStreamWriteInt@8"
RN_BitStreamWriteUnsignedLong (bitstream%,input%):"RN_BitStreamWriteUnsignedLong@8"
RN_BitStreamWriteLong (bitstream%,input%):"RN_BitStreamWriteLong@8"
RN_BitStreamWriteFloat (bitstream%,input#):"RN_BitStreamWriteFloat@8"
RN_BitStreamWriteDouble (bitstream%,input#):"RN_BitStreamWriteDouble@8"
RN_BitStreamWrite (bitstream%,input$,numberOfBytes%):"RN_BitStreamWrite@12"
RN_BitStreamWriteCompressedUnsignedChar (bitstream%,input%):"RN_BitStreamWriteCompressedUnsignedChar@8"
RN_BitStreamWriteCompressedChar (bitstream%,input%):"RN_BitStreamWriteCompressedChar@8"
RN_BitStreamWriteCompressedUnsignedShort (bitstream%,input%):"RN_BitStreamWriteCompressedUnsignedShort@8"
RN_BitStreamWriteCompressedShort (bitstream%,input%):"RN_BitStreamWriteCompressedShort@8"
RN_BitStreamWriteCompressedUnsignedInt (bitstream%,input%):"RN_BitStreamWriteCompressedUnsignedInt@8"
RN_BitStreamWriteCompressedInt (bitstream%,input%):"RN_BitStreamWriteCompressedInt@8"
RN_BitStreamWriteCompressedUnsignedLong (bitstream%,input%):"RN_BitStreamWriteCompressedUnsignedLong@8"
RN_BitStreamWriteCompressedLong (bitstream%,input%):"RN_BitStreamWriteCompressedLong@8"
RN_BitStreamWriteCompressedFloat (bitstream%,input#):"RN_BitStreamWriteCompressedFloat@8"
RN_BitStreamWriteCompressedDouble (bitstream%,input#):"RN_BitStreamWriteCompressedDouble@8"
RN_BitStreamReadBool%(bitstream%):"RN_BitStreamReadBool@4"
RN_BitStreamReadUnsignedChar%(bitstream%):"RN_BitStreamReadUnsignedChar@4"
RN_BitStreamReadChar%(bitstream%):"RN_BitStreamReadChar@4"
RN_BitStreamReadUnsignedShort%(bitstream%):"RN_BitStreamReadUnsignedShort@4"
RN_BitStreamReadShort%(bitstream%):"RN_BitStreamReadShort@4"
RN_BitStreamReadUnsignedInt%(bitstream%):"RN_BitStreamReadUnsignedInt@4"
RN_BitStreamReadInt%(bitstream%):"RN_BitStreamReadInt@4"
RN_BitStreamReadUnsignedLong%(bitstream%):"RN_BitStreamReadUnsignedLong@4"
RN_BitStreamReadLong%(bitstream%):"RN_BitStreamReadLong@4"
RN_BitStreamReadFloat#(bitstream%):"RN_BitStreamReadFloat@4"
RN_BitStreamReadDouble#(bitstream%):"RN_BitStreamReadDouble@4"
RN_BitStreamRead$(bitstream%,numberOfBytes%):"RN_BitStreamRead@8"
RN_BitStreamReadCompressedUnsignedChar%(bitstream%):"RN_BitStreamReadCompressedUnsignedChar@4"
RN_BitStreamReadCompressedChar%(bitstream%):"RN_BitStreamReadCompressedChar@4"
RN_BitStreamReadCompressedUnsignedShort%(bitstream%):"RN_BitStreamReadCompressedUnsignedShort@4"
RN_BitStreamReadCompressedShort%(bitstream%):"RN_BitStreamReadCompressedShort@4"
RN_BitStreamReadCompressedUnsignedInt%(bitstream%):"RN_BitStreamReadCompressedUnsignedInt@4"
RN_BitStreamReadCompressedInt%(bitstream%):"RN_BitStreamReadCompressedInt@4"
RN_BitStreamReadCompressedUnsignedLong%(bitstream%):"RN_BitStreamReadCompressedUnsignedLong@4"
RN_BitStreamReadCompressedLong%(bitstream%):"RN_BitStreamReadCompressedLong@4"
RN_BitStreamReadCompressedFloat#(bitstream%):"RN_BitStreamReadCompressedFloat@4"
RN_BitStreamReadCompressedDouble#(bitstream%):"RN_BitStreamReadCompressedDouble@4"
RN_BitStreamResetReadPointer (bitstream%):"RN_BitStreamResetReadPointer@4"
RN_BitStreamAssertStreamEmpty (bitstream%):"RN_BitStreamAssertStreamEmpty@4"
RN_BitStreamPrintBits (bitstream%):"RN_BitStreamPrintBits@4"
RN_BitStreamIgnoreBits (bitstream%,numberOfBits%):"RN_BitStreamIgnoreBits@8"
RN_BitStreamSetWriteOffset (bitstream%,offset%):"RN_BitStreamSetWriteOffset@8"
RN_BitStreamGetNumberOfBitsUsed%(bitstream%):"RN_BitStreamGetNumberOfBitsUsed@4"
RN_BitStreamGetNumberOfBytesUsed%(bitstream%):"RN_BitStreamGetNumberOfBytesUsed@4"
RN_BitStreamGetReadOffset%(bitstream%):"RN_BitStreamGetReadOffset@4"
RN_BitStreamGetNumberOfUnreadBits%(bitstream%):"RN_BitStreamGetNumberOfUnreadBits@4"
RN_BitStreamSetData (bitstream%,data$,numberOfBits%):"RN_BitStreamSetData@12"
RN_BitStreamGetData$(bitstream%):"RN_BitStreamGetData@4"
RN_BitStreamGetDataPointer%(bitstream%):"RN_BitStreamGetDataPointer@4"





; Statistics. More functions are wrapped (you can add them here if needed!)
RN_ServerGetStatistics%(rakPeerInterface%,systemAddress%):  "RN_ServerGetStatistics@8"

;RN_StatisticsGetMessagesSent(stat%,queue%):  "RN_StatisticsGetmessagesSent@8"
;RN_StatisticsGetMessageDataBitsSent(stat%,queue%):  "RN_StatisticsGetmessageDataBitsSent@8"
;RN_StatisticsGetMessageTotalBitsSent(stat%,queue%):  "RN_StatisticsGetmessageTotalBitsSent@8"
;RN_StatisticsGetTotalBitsSent%(stat%):  "RN_StatisticsGetTotalBitsSent@4"
;RN_StatisticsGetBitsReceived%(stat%):  "RN_StatisticsGetBitsReceived@4"
;RN_StatisticsGetPacketsSent%(stat%):  "RN_StatisticsGetPacketsSent@4"
;RN_StatisticsGetPacketsReceived%(stat%):  "RN_StatisticsGetPacketsReceived@4"
;RN_StatisticsGetmessageResends%(stat%):"RN_StatisticsGetmessageResends@4"

RN_StatisticsGetBitsSentLastSecond%(stat%):  "RN_StatisticsGetBitsSentLastSecond@4"
RN_StatisticsGetBitsReceivedLastSecond%(stat%):  "RN_StatisticsGetBitsReceivedLastSecond@4"
RN_StatisticsGetTotalBitsReceived%(stat%):  "RN_StatisticsGetTotalBitsReceived@4"
RN_StatisticsGetTotalBitsSent%(stat%):  "RN_StatisticsGetTotalBitsSent@4"
RN_StatisticsGetTotalPacketLoss#(stat%):  "RN_StatisticsGetTotalPacketLoss@4"
RN_StatisticsGetPacketLossLastSecond#(stat%):  "RN_StatisticsGetPacketLossLastSecond@4"