.lib "BlitzSQL.dll"

OpenSQLStream%(hostName$, port%, userName$, passWord$, dataBase$, flag%)
SQLConnected%(streamId%)
SQLQuery%(streamId%, query$)
SQLRowCount%(queryId%)
SQLFetchRow%(queryId%)
SQLFieldCount%(queryId%)
ReadSQLField$(rowId%, fieldName$)
ReadSQLFieldIndex$(rowId%, fieldIndex%)
FreeSQLQuery%(queryId%)
FreeSQLRow%(rowId%)
CloseSQLStream%(streamId%)