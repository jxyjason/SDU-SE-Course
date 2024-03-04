# 导包,发起请求使用urllib库的request请求模块
import urllib.request
from bs4 import BeautifulSoup

for i in range(26):
    index = str(6992 - i)
    response = urllib.request.urlopen('https://www.ppzuowen.com/book/xiaowangzi/' + index + '.html')
    html = response.read()
    soup = BeautifulSoup(html, 'html.parser')
    print('-----------------------------------------------------------------------------')
    fileWriteName = 'Chapter0' + str(i + 1) + '.txt'
    print(soup.select("[class~=articleContent]")[0].get_text())
    file = open(fileWriteName, 'w', encoding='utf8')
    file.write(str(soup.select("[class~=articleContent]")[0].get_text()))
    file.close()


