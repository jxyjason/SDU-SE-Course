from bs4 import BeautifulSoup
import requests
import pymysql
import time

# 打开数据库连接
db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     password='1111',
                     database='webshujuguanli')
# 使用cursor()方法获取操作游标
cursor = db.cursor()
bbs_id = 0
#128431条数据（论坛，航拍无人机板块），得到了53894条
for page in range(2,2143):#2143
    # 爬取基本网页
    url = 'https://bbs.dji.com/forum.php?mod=forumdisplay&fid=84&page='+str(page)
    headers = {
        "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.64"
    }
    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")

    # 寻找有用价值
    soup = soup.find(id='threadlisttableid').find_all('tbody')


    drone_name = ''
    link_drone_name = ''
    bbs_content = ''
    link_content = ''
    bbs_user_name = ''
    link_user = ''
    bbs_comment = ''
    link_comment = ''
    bbs_publish_time = ''


    for each_body in soup:
        get_a = each_body.find_all('a')
        if not get_a:
            continue
        index = 0
        for each_a in get_a:
            if each_a.get_text().strip() != '' and each_a.get_text().strip() != 'New':
                if index==0:
                    drone_name = each_a.get_text()
                    link_drone_name = "https://bbs.dji.com/"+str(each_a.attrs['href'])
                elif index==1:
                    bbs_content = each_a.get_text()
                    link_content = "https://bbs.dji.com/" + str(each_a.attrs['href'])
                elif index==2:
                    bbs_user_name = each_a.get_text()
                    link_user = "https://bbs.dji.com/" + str(each_a.attrs['href'])
                elif index==3:
                    bbs_comment = each_a.get_text()
                    link_comment = "https://bbs.dji.com/" + str(each_a.attrs['href'])
                elif index==4:
                    bbs_publish_time = each_a.get_text()
                else:
                    print('+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++')
                index+=1
                print(each_a.get_text() + '  ' + 'https://bbs.dji.com/' + each_a.attrs['href'])

        sql = "INSERT INTO bbs_camera_drone(`drone_name`,`link_drone_name`,`bbs_content`,`link_content`,`bbs_user_name`," \
              "`link_user`,`bbs_comment`,`link_comment`,`bbs_publish_time`) VALUES (" + "\"" + drone_name + "\""+\
              ",\"" + link_drone_name + "\""+ \
              ",\"" + bbs_content + "\"" + \
              ",\"" + link_content + "\"" + \
              ",\"" + bbs_user_name + "\"" + \
              ",\"" + link_user + "\"" + \
              ",\"" + bbs_comment + "\"" + \
              ",\"" + link_comment + "\"" + \
              ",\"" + bbs_publish_time + "\"" + \
              ");"
        print(sql)
        try:
            # 执行sql语句
            cursor.execute(sql)
            # 提交到数据库执行
            db.commit()
        except:
            # 如果发生错误则回滚
            db.rollback()
        time.sleep(0.2)
        bbs_id+=1
        print()

db.close()











