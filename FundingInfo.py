import os
import fileinput
from urllib2 import Request, urlopen, URLError, HTTPError

srcfile = "C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/internet2.csv"
tgtfile = "C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/inter_net2.csv"
def FundingInfo(srcfile, tgtfile):
    file1 = open(srcfile, 'r')
    file2 = open(tgtfile, 'w+')
    for name in file1:
        company =str(name.strip('\n'))
        Funds = getFundingInfo(company)
        if (Funds!='No Record'):
            file2.write(company + "," + Funds + "\n")
        else:
            company1 = stripthelast(company)
            Funds1 = getFundingInfo(company1)
            if (Funds1!='No Record'):
                file2.write(company + "," + Funds1 + "\n")
            else:
                company2 = company.replace(" ","-")
                Funds2 = getFundingInfo(company2)
                if (Funds2!='No Record'):
                    file2.write(company + "," + Funds2 + "\n")
                else:
                    company3 = company1.replace(" ","-")
                    Funds3 = getFundingInfo(company3)
                    if (Funds3!='No Record'):
                        file2.write(company + "," + Funds2 + "\n")
                    else:
                        file2.write(company + "," + "No Record" + "\n")
                        
        
def getFundingInfo(company):
    urlhead = "http://ec2-107-21-104-179.compute-1.amazonaws.com/v/1/company/"    
    url = urlhead + str(company).strip() + ".js"
    response = makeRequest(url)
    splitter1 = response.split('"total_money_raised": ')
    if (len(splitter1) > 1):
        splited = splitter1[1].split(',')[0]
        return splited.strip('"')
    else:
        return 'No Record'
    
def makeRequest(requestUrl):
    try:
        file = urlopen(requestUrl)
        return str(file.read())
    except URLError, e:
        return '\"total_money_raised\": \"No Record\",'
#    return str(file.read())


def stripthelast(company):
    try:
        if (len(company.split(' ')) > 1):
            last = company.split(' ')[-1]
            return company.rsplit(last)[0].rsplit()[0]
        else:
            return str(company)
    except IndexError:
        return str(company)
FundingInfo(srcfile, tgtfile)
#getFundingInfo("MobileBits GmbH")