from bs4 import BeautifulSoup
import requests
#129013条+-1条数据（论坛，航拍无人机板块）
file = open("bbs.txt", "a",encoding="utf-8")
for page in range(2143):
    # 爬取基本网页
    url = 'https://bbs.dji.com/forum.php?mod=forumdisplay&fid=84&page='+str(page)
    headers = {
        "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.64"
    }
    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")

    # 寻找有用价值
    soup = soup.find(id='threadlisttableid').find_all('tbody')
    for each_body in soup:
        get_a = each_body.find_all('a')
        for each_a in get_a:
            if each_a.get_text().strip() != '':
                print(each_a.get_text() + '  ' + 'https://bbs.dji.com/' + each_a.attrs['href'])
                file.write(each_a.get_text() + '  ' + 'https://bbs.dji.com/' + each_a.attrs['href']+'\n')
        print('\n')
        file.write('\n')

file.close()



















# import requests
# from bs4 import BeautifulSoup
#
# url = 'https://bbs.dji.com/forum-84-1.html'
# headers = {
#     "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.64"
# }
# # 发送请求并获取响应
# response = requests.get(url,headers=headers)
# html = response.content
#
# # 使用BeautifulSoup解析HTML
# soup = BeautifulSoup(html, 'html.parser')
#
# # 获取博客列表
# blog_list = soup.select('.xst')
#
# # 遍历博客列表并获取每篇博客的链接
# for blog in blog_list:
#     blog_url = blog['href']
#     blog_response = requests.get("https://bbs.dji.com/"+blog_url,headers=headers)
#     blog_html = blog_response.content
#     blog_soup = BeautifulSoup(blog_html, 'html.parser')
#
#     # 获取博客内容
#     blog_content = blog_soup.select('.t_f')[0].get_text()
#     print(blog_content)