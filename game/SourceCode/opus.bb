const OPUS_AUTO = -1000
const OPUS_BITRATE_MAX = -1
const OPUS_APPLICATION_VOIP = 2048
const OPUS_APPLICATION_AUDIO = 2049
const OPUS_APPLICATION_RESTRICTED_LOWDELAY = 2051

const OPUS_SIGNAL_VOICE = 3001
const OPUS_SIGNAL_MUSIC = 3002
const OPUS_BANDWIDTH_NARROWBAND = 1101
const OPUS_BANDWIDTH_MEDIUMBAND = 1102
const OPUS_BANDWIDTH_WIDEBAND = 1103
const OPUS_BANDWIDTH_SUPERWIDEBAND = 1104
const OPUS_BANDWIDTH_FULLBAND = 1105
const OPUS_FRAMESIZE_ARG = 5000
const OPUS_FRAMESIZE_2_5_MS = 5001
const OPUS_FRAMESIZE_5_MS = 5002
const OPUS_FRAMESIZE_10_MS = 5003
const OPUS_FRAMESIZE_20_MS = 5004
const OPUS_FRAMESIZE_40_MS = 5005
const OPUS_FRAMESIZE_60_MS = 5006
const OPUS_FRAMESIZE_80_MS = 5007
const OPUS_FRAMESIZE_100_MS = 5008
const OPUS_FRAMESIZE_120_MS = 5009

; =========================================================================================================== Functions
Global opusEncoder

Global PCM_ENCODER_CHANNELS = 1
Global PCM_ENCODER_SAMPLE_RATE = 48000
Global PCM_ENCODER_OPUS_TYPE = OPUS_APPLICATION_VOIP
Global DEFAULT_FRAME_SIZE = 960
		
function opus_pcm_encode(bank) ; Default frame_size is 960
	If opusEncoder = 0 Then Return(0)
	
	local Frame_Size% = opus_get_default_frame_size()
	
	local EncodeData% = CreateBank(Frame_Size)

	Size% = opus_encode(opusEncoder, bank, Frame_Size, EncodeData, banksize(EncodeData))
	if Size <> -1 Then
		ResizeBank EncodeData, Size
	Else
		FreeBank EncodeData
		Return 0
	EndIf
	
	return EncodeData
end function

function opus_pcm_decode(opusDecoder, bank, loss = 0)
	If opusDecoder = 0 Then Return(0)

	Local OutputData% = CreateBank(opus_get_default_frame_size()*2)
	
	opus_decode(opusDecoder, bank, BankSize(bank), OutputData, opus_get_default_frame_size(), loss)
	
	Return OutputData
end function
; =========================================================== Setting
function opus_set_channels(channels%)
	PCM_ENCODER_CHANNELS = channels
end Function

function opus_set_sample_rate(samplerate%)
	PCM_ENCODER_SAMPLE_RATE = samplerate
end Function

function opus_set_type(opustype%)
	PCM_ENCODER_OPUS_TYPE = opustype
end Function

function opus_set_default_frame_size(framesize%)
	DEFAULT_FRAME_SIZE = framesize
end function
; ========================================================== Request
function opus_get_channels()
	Return(PCM_ENCODER_CHANNELS)
end Function

function opus_get_sample_rate()
	Return(PCM_ENCODER_SAMPLE_RATE)
end Function

function opus_get_type()
	Return(PCM_ENCODER_OPUS_TYPE)
end Function

function opus_get_default_frame_size()
	Return(DEFAULT_FRAME_SIZE)
end function

function opus_get_new_encoder()
	if opusEncoder <> 0 then opus_remove_encoder()
	opusEncoder = opus_encoder_create(opus_get_sample_rate(), opus_get_channels(), opus_get_type(), 0)
	Return opusEncoder
End Function

function opus_remove_encoder()
	opus_encoder_destroy(opusEncoder)
	opusEncoder = 0
end function


function opus_get_new_decoder()
	Return opus_decoder_create(opus_get_sample_rate(), opus_get_channels(), 0)
End Function
function opus_remove_decoder(opusDecoder)
	opus_decoder_destroy(opusDecoder)
end function