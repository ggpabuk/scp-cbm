This bitmap font library is designed for easily creating text in a 3D game.
THIS IS NOT A 2D FONT LIBRARY!


--------------------------------------------------------------------------

Function: GG_Make_Bitmap_Font(sFont$,iBoxSize%,bBold%,bItalic%,bUnderline%,iCharsPerRow%,sChars$)

Purpose: Create a GG font for use with all the other functions below.
Arguments:
sFont$ - System font name.
iBoxSize% - The size in pixels of the square area for each character on the final bitmap. For best texturing results, use sizes that are powers of 2 (16,32,64,etc.)
bBold%,bItalic%,bUnderline% - Boolean values whether to make the font bold, italic and/or underlined.
iCharsPerRow% - The number of characters to put on each row in the bitmap before starting a new row.
sChars$ - A string containing all characters to be included in the generated bitmap font. The order doesn't matter.


--------------------------------------------------------------------------

Function: GG_Load_Bitmap_Font(sFontID$,sFilename$)

Purpose: Load a GG font. This must be done before creating any text.
Arguments:
sFontID$ - A unique identification you give to the font. This ID is used when calling GG_Set_Font().
sFilename$ - The path and filename of the .ggfnt file you want to load.


--------------------------------------------------------------------------

Function: GG_Set_Font(sFontID$)

Purpose: Sets the current GG font. Note that whenever a new GG font is loaded, it becomes the current font automatically.
Argument:
sFontID$ - The font id that you want to set as the current font. This ID must match an ID used when calling GG_Load_Bitmap_Font().


--------------------------------------------------------------------------

Function: GG_Create_Text_Block(sText$,[iCharsPerLine% = 80],[iAlign% = -1],[fLineSpacing# = 1],[fLetterSpacing# = 1],[hParent = 0])

Purpose: Creates a multi-line block of text with autowrapping.
Arguments:
sText$ - The text you want to create.
iCharsPerLine% - The maximum number of characters per line before wrapping to a new line.
iAlign% - The alignment of the text. Valid values are:
	Align left: -1
	Align center: 0
	Align right: 1
	Justify: Any number greater than 1. This number is the width that each line will be justified to. If justified, then fLetterSpacing# is ignored.
fLineSpacing# - The amount of space between lines. This is a relative value, where 1 is "normal", 2 is "double", etc.
fLetterSpacing# - The amount of space between characters on a line. This is a relative value, where 1 is "normal", 2 is "double", etc.
hParent - Handle to an entity that will be the parent of the created text block.


--------------------------------------------------------------------------

Function: GG_Create_Text_Line(sText$,[iAlign% = -1],[fSpacing# = 1],[iOrientation% = 1],[hParent = 0])

Purpose: Creates a single line of text and returns a handle.
Arguments:
sText$ - The text you want to create.
iAlign% - iAlign% - The alignment of the text. Valid values are:
	Align left: -1
	Align center: 0
	Align right: 1
	Justify: Any number greater than 1. This number is the width that the line will be justified to. If justified, then fSpacing# is ignored.
fSpacing# - The amount of space between characters on the line. This is a relative value, where 1 is "normal", 2 is "double", etc.
iOrientation% - If this value is 1, then text runs horizontally left to right. Any other value will run text vertically top to bottom.
hParent - Handle to an entity that will be the parent of the created text line.


--------------------------------------------------------------------------

Function: GG_Create_Text_Arc(sText$,[fRadius# = 10],[fStartAngle# = 90],[fSpacing# = 1],[iOrientation% = 1],[hParent = 0])

Purpose: Creates line of text along an arc and returns a handle.
Arguments:
sText$ - The text you want to create.
fRadius# - The radius of the arc.
fStartAngle# - The angle on the arc where the text will start. Positive values are counter-clockwise, with 0 at the top of the arc.
fSpacing# - The amount of space between characters on the arc. This is a relative value, where 1 is "normal", 2 is "double", etc. Spacing is also affected by the fRadius# value.
iOrientation% - There are 4 possible values for orienting the text on the arc:
	1: Vertical, clockwise
	2: Vertical, counter-clockwise
	3: Horizontal, outside of arc
	4: Horizontal, inside of arc
hParent - Handle to an entity that will be the parent of the created text arc.


--------------------------------------------------------------------------

Function: GG_Text_Alpha(hPivot%,fAlpha#)

Purpose: Set the alpha transparency level of a text block, line or arc.
Arguments:
hPivot% - Handle to the text entity that you want to set. This is one of the handles returned when creating a text block, line or arc.


--------------------------------------------------------------------------

Function: GG_Change_Text(hPivot%,sText$)

Purpose: Change the text of an existing text line or arc. Does NOT work with blocks!
Arguments:
hPivot% - Handle to the text entity that you want to change. This is one of the handles returned when creating a text line or arc.
sText$ - The new text. If the new text exceeds the length of the original text for the line or arc, then the new text is cut to fit. For this reason, when you create your original text line, be sure to include enough characters for all possible values, even if you use extra spaces. This function is intended to change SMALL amounts of text, like a score. If you use this function to change large amounts of text, your program will slow considerably. Separate static text from dynamic text and you should be ok.


--------------------------------------------------------------------------

Tips:
To create a bitmap font to use with this text library, use the GG_Make_Bitmap_Font() function. This will create a bitmap (.bmp) and a font description text file (.ggfnt) in a GG_Bitmap_Fonts folder. After the bitmap (.bmp) file is created. It is recommended that you load the image into an image editing program such as Photoshop, then decorate the font to look nice. Make the black background transparent and save the image as a .png. GG_Load_Bitmap_Font() will first look for a .png file, then look for a .bmp file if a .png isn't found. If you do make a .png file, you can delete the original .bmp file.

Since the Text function in BlitzBasic doesn't smooth the edges of the text, it is a good idea to create a bitmap font at twice the size that you actually need it, then scale it down by half in an image editing program. The scaling down will create smooth looking characters. Remember to change the BoxSize value in your .ggfnt file to the new size if resizing. It is also recommended to rename your files to match the size used.
