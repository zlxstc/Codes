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
GET DATA  /TYPE=TXT 
  /FILE="C:\Users\zlxstc\Desktop\Lead Edge Capital\Linkedin\old_linkedin_merged\all_companies_aap_merged.txt" 
  /ENCODING='Locale' 
  /DELCASE=LINE 
  /DELIMITERS="," 
  /ARRANGEMENT=DELIMITED 
  /FIRSTCASE=1 
  /IMPORTCASE=ALL 
  /VARIABLES= 
  V1 F7.0 
  V2 A56 
  V3 A82 
  V4 A36 
  V5 A4 
  V6 A5 
  V7 A4 
  V8 A2 
  V9 A2 
  V10 A2 
  V11 A2 
  V12 A2 
  V13 A2 
  V14 A2 
  V15 A2 
  V16 A2 
  V17 A2 
  V18 A2 
  V19 A6 
  V20 A10 
  V21 A6 
  V22 A10 
  V23 A30 
  V24 A2 
  V25 A2 
  V26 A2 
  V27 A2 
  V28 A4 
  V29 A2 
  V30 A2 
  V31 A2 
  V32 A1737 
  V33 A28. 
CACHE. 

* DATASET NAME DataSet12 WINDOW=FRONT.
SAVE OUTFILE='C:\Users\zlxstc\Desktop\Lead Edge Capital\Linkedin\10variables2\p.sav'
  /DROP=V5 V6 V7 V8 V9 V10 V11 V12 V13 V14 V15 V16 V17 V18 V24 V25 V26 V27 V28 V29 V30 V31 V33
  /COMPRESSED.

Execute.