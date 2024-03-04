#导包,发起请求使用urllib库的request请求模块
import urllib.request
from bs4 import BeautifulSoup
# urlopen()向URL发请求,返回响应对象,注意url必须完整
response=urllib.request.urlopen('https://www.xyyuedu.com/etdw/caofangzi/')
print(response)
#提取响应内容
html = response.read().decode('gbk')
#打印响应内容
print(html)
soup = BeautifulSoup(html,'html.parser')
print(soup.prettify())
print(soup.title)
print(soup.get_text())
print(soup.findAll('a'))










