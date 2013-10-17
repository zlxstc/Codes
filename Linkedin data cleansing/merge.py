#! /usr/bin/env python
#coding=utf-8
import os
import linecache
src="all_companies_aap"
tgt="C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/old_linkedin_merged/"+src+'_merged.txt'
def Merger(dir,oldname,newname):
    linecache.clearcache()
#    num_lines = file_len(dir+oldname)
#    print num_lines    
    lineNo = 1
    text=[]
    while(lineNo < 265000):
#        print lineNo   
        line1 = linecache.getline(dir+oldname, lineNo)        
        line2 = linecache.getline(dir+oldname, lineNo+1)
        
#        if len(line1)<2 and len(line2)<2 and len(line3)<2 and len(line4)<2:
#            break
    #        print line2        
        if len(line1.split(','))>3:            
            if len(line2.split(','))<3:            
                line1=line1.strip()+line2
                text.append(line1)
                lineNo=lineNo+2            
            else:
                text.append(line1)
    #            text.append(line2)
                lineNo=lineNo+1
        else:
#            print "1"+text[-1]
            text[-1]=(text[-1].strip())
#            print "2"+text[-1]          
            text[-1]=text[-1]+line1
#            print "3"+text[-1]
            lineNo=lineNo+1
    for item in text:            
        new_file = open(newname,'a+')
        new_file.write(item)
#    if lineNo ==num_lines:
#        new_file = open(newname,'a+')
#        new_file.write(linecache.getline(dir+oldname, lineNo))       
def file_len(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
    return i + 1


Merger("C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/",src,tgt)

