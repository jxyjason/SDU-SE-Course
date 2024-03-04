# 导包,发起请求使用urllib库的request请求模块
import urllib.request
from bs4 import BeautifulSoup

for i in range(26):
    # urlopen()向URL发请求,返回响应对象,注意url必须完整
    index = str(191658+i)
    response = urllib.request.urlopen('https://www.nunubook.com/xiandaixiaoshuo/4176/'+index+'.html')
    html = response.read().decode('utf8')
    soup = BeautifulSoup(html, 'html.parser')
    print('-----------------------------------------------------------------------------')
    fileWriteName = 'Chapter0' + str(i + 1) + '.txt'
    file = open(fileWriteName, 'w', encoding='utf8')
    file.write(soup.find(id='text').get_text())
    file.close()


