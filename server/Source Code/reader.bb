Const READER_PORT = 7777
Global Stream = CreateUDPStream(READER_PORT)
if FileType("output.txt") <> 1 Then CloseFile(WriteFile("output.txt"))
While True
	While RecvUDPMsg(Stream)
		Local f = OpenFile("output.txt")
		While Not Eof(f)
			ReadLine(f)
		Wend
		WriteLine f,"Receive from "+UDPMsgIP(Stream)+", port: "+UDPMsgPort(Stream)
		WriteLine f,"Received bytes: "
		While Not Eof(Stream)
			WriteByte f, ReadByte(Stream)
		Wend
		CloseFile f
	Wend
Wend