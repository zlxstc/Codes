import os, fileinput

path1 = os.path.abspath("C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/result.csv")
path2 = os.path.abspath("C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/result3.csv")
result = "C:/Users/zlxstc/Desktop/Lead Edge Capital/Linkedin/test/results.csv"
#file1
file1 = open(path1, 'r')
file2 = open(path2, 'r')
file3 = open(result, 'a+')

for row in file1:
#    row['source'] = os.path.basename(path1)
    file3.write(row)

#file2
#bg = csv.DictReader(open(path2, 'rb'), delimiter = '\n')

for row in file2:
#    row['source'] = os.path.basename(path1)
    file3.write(row)
