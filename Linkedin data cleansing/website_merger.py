#! /usr/bin/env python
#coding=utf-8
import os
import fileinput
def website_merger(srcfile, tgtfile):
    #read each line of srcfile
    isWebsite = 0
    result = "C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/result6.csv"
    lines = open(srcfile, 'r')
#    lines = file1.readlines()
    file2 = open(tgtfile, 'r')
    lines2 = file2.readlines()
    file3 = open(result, 'a+')
#    lines3 = 
    for line in lines:        
        linkedin_id = line.split(',')[0]
        website = line.split(',')[8]
        #get the website,if possible
        if (website.__len__()>6):
         #   print website
            for line2 in lines2:
                     #   print "line2:"+line2.split(',')[0]
                if(line2.split(',')[0] == linkedin_id):                
            #                print "ID  :"+ linkedin_id
                    file3.write(line2.replace("http://",website))
                    break
            #                lines2.next()
#                else:
#                    continue
        
#        print "line:"+linkedin_id
    #find the line with same id in tgtfile
#        lines2 = open(tgtfile, 'a+')
        
        
    #replace "http://" with website
    
    return 0
dir='C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/'
website_merger(dir+'masterfile2.csv',dir+'Sortedmasterfile2.csv')