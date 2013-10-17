import os
import fileinput

srcfile = "c:/Users/zlxstc/desktop/Lead edge capital/Linkedin/test/test1.txt"
tgtfile = "c:/Users/zlxstc/desktop/Lead edge capital/Linkedin/test/result1.txt"

def decode(srcfile, tgtfile):
    file1 = open(srcfile, 'r')
    file2 = open(tgtfile, 'w+')
    for line in file1:
        file2.write(replace(line))
        
def replace(line):
    line.replace("&amp;", "&")
    line.replace("&#xc0;","A")
    line.replace("&#xc1;","A")
    line.replace("&#xc2;","A")
    line.replace("&#xc3;","A")
    line.replace("&#xc4;","A")
    line.replace("&#xc5;","A")
    line.replace("&#xc7;","C")
    line.replace("&#xc8;","E")
    line.replace("&#xc9;","E")
    line.replace("&#xca;","E")
    line.replace("&#xcb;","E")
    line.replace("&#xcc;","I")
    line.replace("&#xcd;","I")
    line.replace("&#xce;","I")
    line.replace("&#xcf;","I")
    line.replace("&#xd2;","O")
    line.replace("&#xd3;","O")
    line.replace("&#xd4;","O")
    line.replace("&#xd5;","O")
    line.replace("&#xd6;","O")
    line.replace("&#xd9;","U")
    line.replace("&#xda;","U")
    line.replace("&#xdb;","U")
    line.replace("&#xdc;","U")
    line.replace("&#xe0;","a")
    line.replace("&#xe1;","a")
    line.replace("&#xe2;","a")
    line.replace("&#xe3;","a")
    line.replace("&#xe4;","a")
    line.replace("&#xe5;","a")
    line.replace("&#xe8;","e")
    line.replace("&#xe9;","e")
    line.replace("&#xea;","e")
    line.replace("&#xeb;","e")
    line.replace("&#xec;","i")
    line.replace("&#xed;","i")
    line.replace("&#xee;","i")
    line.replace("&#xef;","i")
    line.replace("&#xf2;","o")
    line.replace("&#xf3;","o")
    line.replace("&#xf4;","o")
    line.replace("&#xf5;","o")
    line.replace("&#xf8;","o")
    line.replace("&#xf9;","u")
    line.replace("&#xfa;","u")
    line.replace("&#xfb;","u")
    line.replace("&#xfc;","u")
    return line

decode(srcfile, tgtfile)
    

    