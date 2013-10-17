#! /usr/bin/env python
#coding=utf-8
import glob, os

def rename(dir, pattern):
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        print title
        print ext
        title = title.strip()
        os.rename(pathAndFilename, 
                  os.path.join(dir, title + ext))
rename("C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/xlsmaster", "*.xls")
