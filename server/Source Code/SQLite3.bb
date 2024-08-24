; SQLite 3 Wrapper for Blitz3D and BlitzPlus ------------------------

;#Region Comments

; Author:		Alan Broad (HappyCat)
; Contact:		alan@broad.ukf.net
; Version:		1.1
; Date:			1 July 2005
; Requirements:	SQLite3.dll		http://www.sqlite.org
;				SQLite3.decls	http://www.blitzbasic.com/codearcs/codearcs.php?code=1414
;				put these in the "userlibs" folder under Blitz.

; These are wrapper functions to (hopefully) simplify the use of the
; SQLite 3 library. Not everything in SQLite 3 is covered (mostly
; because some of the advanced SQLite 3 functions require callbacks
; which can't be done in Blitz3D or BlitzPlus).

; Most functions take the following parameters:

;	DatabaseHandle or StatementHandle
; 		The OpenDatabase and PrepareSQL functions always remember
;		the last database opened and the last SQL statement prepared
;		(unless you tell them otherwise with the MakeCurrent
;		parameter) so in a simple single database setup you might
;		be able to get away with never actually storing the handles
; 		and letting the functions take care of it.

;	ErrorsAreFatal
;		If true (the default) any SQLite errors will cause a
;		RuntimeError and stop your program. If this is false then
;		errors will simply return 0, "" or false and your program
;		will continue.

; Individual functions are documented where necessary.

; Full SQLite 3 documentation can be found at http://www.sqlite.org/capi3ref.html
; and SQlite's SQL syntax is explained at http://www.sqlite.org/lang.html

; Change History

; v1.1, 1 July 2005
; 	  - Renamed DatabaseInAutoCommitMode to AutoCommitIsOn and added
;		BeginTransaction, CommitTransaction and RollbackTransaction.
;		Handling transactions yourself with these three new functions
;		can massively improve the speed of inserting and updating data.

; v1.0, 30 June 2005
; 	  -	Initial release

;#End Region

; Constants, Variables and Types ------------------------------------

;#Region Error Code Constants

Const SQLITE_OK          =  0   ; Successful result
Const SQLITE_ERROR       =  1   ; SQL error Or missing database
Const SQLITE_INTERNAL    =  2   ; An internal logic error in SQLite
Const SQLITE_PERM        =  3   ; Access permission denied
Const SQLITE_ABORT       =  4   ; Callback routine requested an abort
Const SQLITE_BUSY        =  5   ; The database file is locked
Const SQLITE_LOCKED      =  6   ; A table in the database is locked
Const SQLITE_NOMEM       =  7   ; A malloc() failed
Const SQLITE_READONLY    =  8   ; Attempt To Write a readonly database
Const SQLITE_INTERRUPT   =  9   ; Operation terminated by SQLITE_INTERRUPT()
Const SQLITE_IOERR       = 10   ; Some kind of disk I/O error occurred
Const SQLITE_CORRUPT     = 11   ; The database disk image is malformed
Const SQLITE_NOTFOUND    = 12   ; (Internal Only) Table Or record Not found
Const SQLITE_FULL        = 13   ; Insertion failed because database is full
Const SQLITE_CANTOPEN    = 14   ; Unable To open the database file
Const SQLITE_PROTOCOL    = 15   ; Database lock protocol error
Const SQLITE_EMPTY       = 16   ; (Internal Only) Database table is empty
Const SQLITE_SCHEMA      = 17   ; The database schema changed
Const SQLITE_TOOBIG      = 18   ; Too much Data For one row of a table
Const SQLITE_CONSTRAINT  = 19   ; Abort due To constraint violation
Const SQLITE_MISMATCH    = 20   ; Data type mismatch
Const SQLITE_MISUSE      = 21   ; Library used incorrectly
Const SQLITE_NOLFS       = 22   ; Uses OS features Not supported on host
Const SQLITE_AUTH        = 23   ; Authorization denied
Const SQLITE_ROW         = 100  ; sqlite3_step() has another row ready
Const SQLITE_DONE        = 101

;#End Region

;#Region Column Type Constants

Const SQLITE_UNKNOWN  = 0
Const SQLITE_INTEGER  = 1
Const SQLITE_FLOAT    = 2
Const SQLITE_TEXT     = 3
Const SQLITE_STRING   = 3
Const SQLITE_BLOB     = 4
Const SQLITE_NULL     = 5

;#End Region

;#Region Internal Types

Type DatabaseHandleContainer
	Field TheHandle
End Type

;#End Region

;#Region Internal Variables

Global CurrentDatabaseHandle = 0
Global CurrentStatementHandle = 0

;#End Region

; Functions ---------------------------------------------------------

;#Region Opening and Closing a Database

; OpenDatabase opens or creates a database file.

; OpenMode values:
	Const DBOpenModeCreate = 0		; If database exists open it, otherwise create it, default
	Const DBOpenModeOpen = 1		; If database exists open it, otherwise do nothing
	Const DBOpenModeReplace = 2		; If database exists replace it with new database

Function OpenDatabase(Filename$, OpenMode = DBOpenModeCreate, MakeCurrent = True, ErrorsAreFatal = True)

	; Handle the different OpenModes
	Local AlreadyExists = FileType(Filename$) = 1
	
	Select OpenMode
		Case DBOpenModeOpen
			If Not AlreadyExists Then Return SQLite3_ErrorHasOccurred("OpenDatabase", ErrorsAreFatal, "the specified database file (" + Filename$ + ") does not exist")
		Case DBOpenModeReplace
			If AlreadyExists Then DeleteFile(Filename$)
	End Select
	
	; Create the temporary database handle container
	Local DatabaseHandleContainer.DatabaseHandleContainer = New DatabaseHandleContainer

	; Open/create the database
	Local ErrorCode = SQLite3_Open(Filename$, DatabaseHandleContainer)
	
	; Get the handle from the handle container
	Local DatabaseHandle = DatabaseHandleContainer\TheHandle
	Delete DatabaseHandleContainer
	
	; It worked
	If ErrorCode = SQLITE_OK Then
	
		; Make the new database the current one
		If MakeCurrent Then	CurrentDatabaseHandle = DatabaseHandle
		
		; Return the new database handle
		Return DatabaseHandle
		
	Else
		; There was an error
		SQLite3_ErrorHasOccurred("OpenDatabase", ErrorsAreFatal)
	End If
	
End Function

; The database will fail to close if there are SQL statements that
; have been prepared but not finalised.
Function CloseDatabase(DatabaseHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	; Is there a database to close?
	If DatabaseHandle <> 0 Then 
	
		; Close the database
		Local ErrorCode = SQLite3_Close(DatabaseHandle)
		
		; It worked
		If ErrorCode = SQLITE_OK Then
			; Reset the current handle if required
			If DatabaseHandle = CurrentDatabaseHandle Then CurrentDatabaseHandle = 0
			Return True
		Else
			; There was an error
			SQLite3_ErrorHasOccurred("CloseDatabase", ErrorsAreFatal)
		End If

	Else
		Return False
	End If
	
End Function

; Not required for single user use - TimeOutInMilliSecs = 0 removes timeout
Function SetDatabaseTimeout(TimeOutInMilliSecs, DatabaseHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		
		Local ErrorCode = SQLite3_Busy_TimeOut(DatabaseHandle, TimeOutInMilliSecs)
		
		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			; There was an error
			SQLite3_ErrorHasOccurred("SetDatabaseTimeout", ErrorsAreFatal)
		End If

	Else
		Return False
	End If

End Function

;#End Region

;#Region Information Functions

Function DatabaseVersion$()
	Return SQLite3_LibVersion()
End Function

; Gets the ID (either the value of the primary key column if there is
; one, or the internal ID) of the last row inserted into the database.
Function LastRowIDInserted(DatabaseHandle = 0)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		Return SQLite3_Last_Insert_RowID(DatabaseHandle)
	Else
		Return 0
	End If

End Function

; Gets the number of rows changed by the last SQL statement run.
Function RowsChangedByLastStatement(DatabaseHandle = 0)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		Return SQLite3_Changes(DatabaseHandle)
	Else
		Return 0
	End If
	
End Function

; Gets the number of rows changed since the database was opened.
Function RowsChangedThisSession(DatabaseHandle = 0)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		Return SQLite3_Total_Changes(DatabaseHandle)
	Else
		Return 0
	End If
	
End Function

;#End Region

;#Region Transactions

; The database is always in AutoCommitMode unless you're in the middle of a
; SQL statement that defines it's own transactions with BEGIN, COMMIT and ROLLBACK.
Function AutoCommitIsOn(DatabaseHandle = 0)

	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		Return SQLite3_Get_AutoCommit(DatabaseHandle) <> 0
	Else
		Return False
	End If
	
End Function

; Begins a new transaction.
Function BeginTransaction(DatabaseHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		Return ExecuteSQL("BEGIN", DatabaseHandle, ErrorsAreFatal) = SQLITE_OK
	Else
		Return False
	End If
	
End Function

; Commits the current transaction.
; If KeepTransactionOpen = true then it immediately begins a new transaction
; for you. This is handy if you're commiting in the middle of a large batch
; of updates but you're not finished yet.
Function CommitTransaction(KeepTransactionOpen = False, DatabaseHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
	
		If KeepTransactionOpen Then
			ExecuteSQL("COMMIT", DatabaseHandle, ErrorsAreFatal)
			Return ExecuteSQL("BEGIN", DatabaseHandle, ErrorsAreFatal) = SQLITE_OK
		Else
			Return ExecuteSQL("COMMIT", DatabaseHandle, ErrorsAreFatal) = SQLITE_OK
		End If
		
	Else
		Return False
	End If
	
End Function

; Rolls back the current transaction.
Function RollbackTransaction(DatabaseHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		Return ExecuteSQL("ROLLBACK", DatabaseHandle, ErrorsAreFatal) = SQLITE_OK
	Else
		Return False
	End If
	
End Function

;#End Region

;#Region Error Handling Functions

; If you call any of the functions with ErrorsAreFatal = false and
; they fail (return 0, "" or false) you can find out what the error
; code and error message were by calling LastDatabaseErrorCode and
; LastDatabaseErrorMessage.

Function LastDatabaseErrorCode(DatabaseHandle = 0)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle

	Return SQLite3_ErrCode(DatabaseHandle)
	
End Function

Function LastDatabaseErrorMessage$(DatabaseHandle = 0)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle

	Return Return SQLite3_ErrMsg(DatabaseHandle)

End Function

; Cancels the currently executing SQL. I'd imaging it also rolls back
; transactions, but the SQLite documentation doesn't say.
; Note that any prepared SQL will be unusable after this.
Function InterruptDatabase(DatabaseHandle = 0)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle
	
	If DatabaseHandle <> 0 Then
		SQLite3_Interrupt(DatabaseHandle)
		Return True
	Else
		Return False
	End If
	
End Function

;#End Region

;#Region Executing SQL

; Executes the supplied SQL statement but cannot return any results
; Handy for things like CREATE TABLE, INSERT, UPDATE and DELETE
Function ExecuteSQL(SQL$, DatabaseHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle

	If DatabaseHandle <> 0 Then

		; Execute the SQL
		Local ErrorCode = SQLite3_Exec(DatabaseHandle, SQL$, 0, 0, 0)
	
		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			; There was an error
			Return SQLite3_ErrorHasOccurred("ExecuteSQL", ErrorsAreFatal)
		End If

	Else
		Return False
	End If
		
End Function

;#End Region

;#Region Data Retrieval Functions

; Prepares a SQL statement ready for you to retrieve the data.
Function PrepareSQL(SQL$, DatabaseHandle = 0, MakeCurrent = True, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If DatabaseHandle = 0 Then DatabaseHandle = CurrentDatabaseHandle

	If DatabaseHandle <> 0 Then

		; Create the temporary statement handle container
		Local StatementHandleContainer.DatabaseHandleContainer = New DatabaseHandleContainer
	
		; Prepare the SQL
		Local ErrorCode = SQLite3_Prepare(DatabaseHandle, SQL$, Len(SQL$), StatementHandleContainer, 0)
	
		; Get the handle from the handle container
		Local StatementHandle = StatementHandleContainer\TheHandle
		Delete StatementHandleContainer
	
		; It worked
		If ErrorCode = SQLITE_OK Then
		
			; Make the new statement the current one
			If MakeCurrent Then CurrentStatementHandle = StatementHandle
			
			; Return the new statement handle
			Return StatementHandle
	
		Else
			; There was an error
			Return SQLite3_ErrorHasOccurred("PrepareSQL", ErrorsAreFatal)
		End If

	Else
		Return 0
	End If
		
End Function

; Moves to the next row (or first if it's not been called yet for the
; SQL statement) in the result set of the SQL statement. You can then
; use the GetColumn???? functions to retrieve the values.
; If AutomaticallyFinalise is true (the default) then the SQL statement
; will be automatically finalised when the end of the result set is
; reached, otherwise you'll have to call FinaliseSQL yourself.
Function GetNextDataRow(StatementHandle = 0, AutomaticallyFinalise = True, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then

		; Get the next row
		Local ErrorCode = SQLite3_Step(StatementHandle)
		
		; It worked
		Select ErrorCode
			Case SQLITE_ROW
				Return True
			Case SQLITE_DONE
				If AutomaticallyFinalise Then FinaliseSQL(StatementHandle, ErrorsAreFatal)
				Return False
			Default
				Return SQLite3_ErrorHasOccurred("GetNextDataRow", ErrorsAreFatal)
		End Select
		
	Else
		Return False
	End If

End Function

; Closes a prepared SQL statement - the database won't close if there
; are un-finalised SQL statements.
Function FinaliseSQL(StatementHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		
		; Finalise the SQL statement
		Local ErrorCode = SQLite3_Finalize(StatementHandle)
		
		; It worked
		If ErrorCode = SQLITE_OK Then
			; Reset the current handle if required
			If StatementHandle = CurrentStatementHandle Then CurrentStatementHandle = 0
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("FinaliseSQL", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Resets the SQL statement so that data reading (with
; GetNextDataRow) starts back at the beginning again of the
; result set.
; Shouldn't really be combined with the AutomaticallyFinalise
; parameter of GetNextDataRow as resetting a finalised
; statement doesn't work.
Function ResetSQL(StatementHandle = 0, ErrorsAreFatal = True)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		
		; Finalise the SQL statement
		Local ErrorCode = SQLite3_Reset(StatementHandle)
		
		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("ResetSQL", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Checks to see if the SQL statement has expired (needs to be
; re-prepared). According to the SQLite docs expiration only usually
; occurs in circumstances which aren't allowed by this wrapper
; (ie. some of the more advanced functions which Blitz can't use)
; so this shouldn't really be required.
Function SQLHasExpired(StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		; Finalise the SQL statement
		Return SQLite3_Expired(StatementHandle) <> 0
	Else
		; If there's no statement then assume it's expired
		Return True
	End If
	
End Function

; Returns the handle of the database that was used to prepare the
; SQL statement.
Function GetDatabaseHandleFromStatementHandle(StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_DB_Handle(StatementHandle)
	Else
		Return 0
	End If

End Function

; Returns the number of columns in the SQL statement's result set.
Function GetColumnCount(StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Count(StatementHandle)
	Else
		Return 0
	End If
		
End Function

; In the following functions ColumnIndex is zero based.
; Ie. the first column is 0, the second is 1, etc.

; Returns the name of the specified of column in the SQL statement's result set.
Function GetColumnName$(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Name(StatementHandle, ColumnIndex)
	Else
		Return ""
	End If
		
End Function

; Returns the type of the specified column in the SQL statement's result set.
; Returns the type as an integer which you can then compare to the SQLITE_INTEGER,
; SQLITE_FLOAT, SQLITE_STRING constants.
Function GetColumnType(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Type(StatementHandle, ColumnIndex)
	Else
		Return SQLITE_UNKNOWN
	End If

End Function

; Returns the type of the specified column in the SQL statement's result set.
; Returns the type as a string.
Function GetColumnDeclaredType$(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_DeclType(StatementHandle, ColumnIndex)
	Else
		Return ""
	End If

End Function

; Returns the amount of space used to store the column's contents (in bytes).
Function GetColumnSize(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Bytes(StatementHandle, ColumnIndex)
	Else
		Return 0
	End If
	
End Function

; In the following functions if the column is actually of a different
; type to that requested SQLite does its best to convert the contents.

; Returns the column's contents as an integer.
Function GetColumnValueAsInteger(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Int(StatementHandle, ColumnIndex)
	Else
		Return 0
	End If
		
End Function

; Returns the column's contents as a float.
Function GetColumnValueAsFloat#(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Double(StatementHandle, ColumnIndex)
	Else
		Return 0
	End If
		
End Function

; Returns the column's contents as a string.
Function GetColumnValueAsString$(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Text(StatementHandle, ColumnIndex)
	Else
		Return ""
	End If
		
End Function

; Never tried this - should return the handle to the blob, no idea how to use it though
Function GetColumnValueAsBlob(ColumnIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Column_Blob(StatementHandle, ColumnIndex)
	Else
		Return 0
	End If
		
End Function

;#End Region

;#Region SQL Parameter Binding

; SQLite support paramaterised SQL. Parameters can be specified as "?",
; ":Example" or "$Example".
; See: http://www.sqlite.org/capi3ref.html#sqlite3_bind_blob
; for more details on parameter naming and binding.

; Returns the number of parameters in the SQL statement.
Function SQLParameterCount(StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Bind_Parameter_Count(StatementHandle)
	Else
		Return 0
	End If
		
End Function

; Returns the name of the specified parameter in the SQL statement.
; Parameters defined as "?" don't have names.
Function SQLParameterName$(ParameterIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Bind_Parameter_Name(StatementHandle, ParameterIndex)
	Else
		Return ""
	End If
		
End Function

; Returns the index of the parameter with the specified name in the SQL statement.
Function SQLParameterIndex(ParameterName$, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then
		Return SQLite3_Bind_Parameter_Index(StatementHandle, ParameterName$)
	Else
		Return 0
	End If
		
End Function

; Set the specified parameter to null.
Function BindSQLParameterAsNull(ParameterIndex, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then

		Local ErrorCode = SQLite3_Bind_Null(StatementHandle, ParameterIndex)

		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("BindParameterAsNull", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Set the specified parameter to the supplied integer value.
Function BindSQLParameterAsInteger(ParameterIndex, Value, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then

		Local ErrorCode = SQLite3_Bind_Int(StatementHandle, ParameterIndex, Value)

		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("BindParameterAsInteger", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Should set the specified parameter to the supplied float value.
; Unfortunately it doesn't seem to work.
Function BindSQLParameterAsFloat(ParameterIndex, Value#, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then

		Local ErrorCode = SQLite3_Bind_Double(StatementHandle, ParameterIndex, Value#)

		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("BindParameterAsFloat", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Set the specified parameter to the supplied string value.
Function BindSQLParameterAsString(ParameterIndex, Value$, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then

		Local ErrorCode = SQLite3_Bind_Text(StatementHandle, ParameterIndex, Value$, -1, 0)

		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("BindParameterAsString", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Never tried this - no idea if it works or not
Function BindSQLParameterAsBlob(ParameterIndex, BlobHandle, LengthOfBlob, StatementHandle = 0)
	
	; Get the current handle if required
	If StatementHandle = 0 Then StatementHandle = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle <> 0 Then

		Local ErrorCode = SQLite3_Bind_Blob(StatementHandle, ParameterIndex, BlobHandle, LengthOfBlob, 0)

		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("BindParameterAsBlob", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

; Transfers the bindings from one SQL statement to another.
; Useful I guess if you want to run multiple SQL statements with
; the same bindings.
Function TransferSQLBindings(StatementHandle1, StatementHandle2)
	
	; Get the current handle if required
	If StatementHandle1 = 0 Then StatementHandle1 = CurrentStatementHandle
	
	; Is there a statement to process?
	If StatementHandle1 <> 0 And StatementHandle2 <> 0 Then

		Local ErrorCode = SQLite3_Transfer_Bindings(StatementHandle1, StatementHandle2)

		; It worked
		If ErrorCode = SQLITE_OK Then
			Return True
		Else
			Return SQLite3_ErrorHasOccurred("TransferBindings", ErrorsAreFatal)
		End If
		
	Else
		Return False
	End If
	
End Function

;#End Region

;#Region Internal Functions

; Just a function used internally to handle fatal errors.
Function SQLite3_ErrorHasOccurred(FunctionName$, ErrorsAreFatal, Message$ = "")
	If ErrorsAreFatal Then
		If Message$ = "" Then Message$ = SQLite3_ErrMsg(DatabaseHandle)
		RuntimeError("Error in SQLite3 > " + FunctionName$ + ": " + Message$)
	End If
End Function

;#End Region
