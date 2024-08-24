.lib "vlib.dll"

snd_setformat%(samples%, bits%, channels%)
snd_geterror%()
snd_geterrorstr$(code%)

snd_out_open%(format%)
snd_out_close%(snd_out%)
snd_out_stop%(snd_out%)
snd_out_write%(snd_out%, bank*, offset%, count%)
snd_out_update%(snd_out%)
snd_out_playavail%(snd_out%)

snd_in_open%(format%, block_size%, block_count%)
snd_in_close%(snd_in%)
snd_in_start%(snd_in%)
snd_in_stop%(snd_in%)
snd_in_readavail%(snd_in%)
snd_in_read%(snd_in%, bank*, size%)
snd_in_isrecording%(snd_in%)
snd_in_flush%(snd_in%)
snd_in_name$(deviceID%) : "_snd_in_name@4"
snd_in_count%() : "_snd_in_count@0"

snd_codec_add%(filename$)
snd_codec_remove%(codec%)

snd_codec_count%()
snd_codec_tag%(index%)
snd_codec_name$(index%)
snd_codec_formats%(index%, flags%)
snd_codec_getformat%(index%)
snd_codec_formatname$(index%)
snd_codec_copyright$(index%)
snd_codec_license$(index%)
snd_codec_features$(index%)

snd_convert_open%(srcFormat%, destFormat%)
snd_convert_close%(snd_convert%)
snd_convert_getsize%(snd_convert%, size%)
snd_convert_encode%(snd_convert%, src*, src_offset%, src_count%, dest*, dest_offset%, dest_count%)

snd_format_create%(tag%, samples%, bits%, channels%)
snd_format_createfrom%(flags%, format%, tag%, samples%, bits%, channels%)
snd_format_choose%(flags%, format%, title$, window%)
snd_format_free%(format%)
snd_format_name$(format%)
snd_format_tagname$(format_tag%)
snd_format_samples%(format%)
snd_format_bits%(format%)
snd_format_channels%(format%)
snd_format_tag%(format%)
snd_format_bps%(format%)

snd_fft%(bank*, count%)
