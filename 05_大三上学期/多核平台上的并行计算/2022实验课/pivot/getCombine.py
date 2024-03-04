import itertools
file_path = 'uniformvector-2dim-5h.txt'
file = open(file_path, encoding='utf-8')
theData = file.read()
theDatalist = theData.split('\n')
# hands = list(itertools.combinations(theDatalist, 2))
hands = list(itertools.combinations(range(500), 2))
print(hands)
print(len(hands))
file.close()
file = open('processedData.txt','w')
result = ''
for list in hands:
    if(list.__getitem__(0)<list.__getitem__(1)):
        result+=str(list.__getitem__(0))
        result+=' '
        result += str(list.__getitem__(1))
        result+='\n'
    else:
        result += str(list.__getitem__(1))
        result += ' '
        result += str(list.__getitem__(0))
        result += '\n'
print(result)
file.write(result)