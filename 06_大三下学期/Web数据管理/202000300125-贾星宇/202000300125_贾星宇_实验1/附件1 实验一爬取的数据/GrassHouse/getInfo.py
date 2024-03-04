#导包,发起请求使用urllib库的request请求模块
import urllib.request
from bs4 import BeautifulSoup
# urlopen()向URL发请求,返回响应对象,注意url必须完整

for i in range(9):
    tileUrl = str(236859+i)
    url = 'https://www.xyyuedu.com/etdw/caofangzi/'+tileUrl+'.html'
    response = urllib.request.urlopen(url)
    # 提取响应内容
    html = response.read().decode('gbk')
    # 打印响应内容
    soup = BeautifulSoup(html, 'html.parser')
    print('--------------------------'+soup.find(id='onearcxsbd').p.get_text())
    fileWriteName = 'Chapter0'+str(i+1)+'.txt'
    file = open(fileWriteName, 'w', encoding='utf8')
    file.write(soup.find(id='onearcxsbd').p.get_text())
    file.close()
