# PL/SQL Version Control
Version control tool for specific PL/SQL source files.

For now, application supports versioning of .pkb and .vw files as this is the core of codebase.

The .pkb versioning is as follows:
- There is a mfw_GetVersion function on top of each package body
- Function returns the current version (hardcoded and edited by developer with each change)
- Format of return statement is: X.x.Y.y.MM-DD-YYYY
- In Declare section of function is a comment section with each line in the following format:
- MM.DD.YYYY A.a  BBBBBBBBBBBBBBBBB CCCCCC
- MM.DD.YYYY is a date of change
- A.a is either X.x or Y.y (version numbers)
- BBBBBBBBBBBBBBBBB is name of the person making changed (RPAD with space)
- CCCCCC is variable length message starting with task identifier

The .vw versioning is as follows:
- There is a version attribute on top of each view, value is hardcoded to X.x.Y.y.MM-DD-YYYY
- Between SELECT statement and first attribute (version), there is a comment section with the same format as in .pkb

#Requirements

#Usage

#Future improvements
