* Initial file has STRING(A2) variable cat1 which has n different values.
* Objective is to create n different files.

* This creates a data file for illustration purposes.
* The syntax works for any number of different values of cat1.
	
* Posted by Raynald Levesque to SPSSX-L on 2001/08/09.
* Improved on 2003/01/24.

 * DATA LIST FREE /cat1(A2).
 * BEGIN DATA
"A2" "B7" "01" "08" "W7" "XY"
"A2" "A2" "08" "W7" "W7" "XY"
"A2" "B7" "01" "08" "W7" "XY"
END DATA.
 * LIST.

 * SAVE OUTFILE='c:\temp\mydata.sav'.

* Now start the job.
set mprint=on.

*//////////////////////////////////////////.
DEFINE !SPLIT (var=!TOKENS(1) /value=!TOKENS(1) )
TEMPORARY.
SELECT IF (!var=!value).
SAVE TRANSLATE OUTFILE=!QUOTE(!CONCAT('C:\Users\zlxstc\Desktop\Lead Edge Capital\Linkedin\xlsmaster\',!UNQUOTE(!value),'.xls'))
  /TYPE=XLS
  /VERSION=8
  /MAP
  /REPLACE
  /FIELDNAMES
  /CELLS=VALUES.
EXECUTE.
!ENDDEFINE.
*//////////////////////////////////////////.

* Find all different existing values of cat1.
AGGREGATE  /OUTFILE=* /BREAK=sector /notused = N.

* Write a syntax file which will call the above macro.
WRITE OUTFILE='c:\temp\temp.sps' /'!SPLIT var=sector value="' sector '".'.
Execute.

* Get the original data file and do the macro calls.
GET FILE='C:\Users\zlxstc\Desktop\Lead Edge Capital\Linkedin\7in1\Sortedmasterfile_website.sav'.
INCLUDE 'C:\temp\temp.sps'.