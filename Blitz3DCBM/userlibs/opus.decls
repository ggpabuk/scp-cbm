.lib "opus.dll"

;============================================================== encoder
opus_encoder_create%(samplerate%, channels%, encodemode%, errorbuff%)
opus_encoder_init%(encoder%, samplerate%, channels%, encodemode%)
opus_encode%(encoder%, data*, frame_size%, output*, max_data_bytes%)
opus_encoder_destroy%(encoder%)
opus_encoder_get_size%(channels%)
opus_encoder_ctl%(encoder%, request%, arg1%, arg2%)
;============================================================== decoder
opus_decoder_create%(samplerate%, channels%, errorbuff%)
opus_decoder_init%(decoder%, samplerate%, channels%)
opus_decode%(decoder%, data*, datalen%, output*, frame_size%, decode_fec%)
opus_decoder_destroy%(decoder%)
opus_decoder_get_size%(channels%)

opus_pcm_soft_clip%(data*, frame_size%, channels%, softclip_mem*)

opus_strerror$(error%)