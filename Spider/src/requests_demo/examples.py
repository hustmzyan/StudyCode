import requests
import os

def jd_item():
    url = 'https://item.jd.com/100003344497.html'
    try:
        r = requests.get(url)
        r.raise_for_status()  # 如果状态不是200，则引发HTTPError异常
        # r.encoding = r.apparent_encoding
        print(r.text[:1000])
    except:
        print('爬取失败')

def amazon_item():
    # 需要伪装成浏览器访问
    url = 'https://www.amazon.cn/dp/B07FQKB4TM'
    try:
        kv = {'user-agent': 'Mozilla/5.0'}
        r = requests.get(url, headers = kv)
        r.raise_for_status()  # 如果状态不是200，则引发HTTPError异常
        r.encoding = r.apparent_encoding
        print(r.text[1000:2000])
    except:
        print('爬取失败')

"""
百度的关键词接口：http://www.baidu.com/s?wd=keyword
360的关键词接口：htt[://www.so.com/s?q=keyword
"""
def baidu_search():
    url = 'http://www.baidu.com/s'
    try:
        kv = {'wd': 'Python'}
        r = requests.get(url, params = kv)
        print(r.request.url)
        r.raise_for_status()  # 如果状态不是200，则引发HTTPError异常
        print(len(r.text))
    except:
        print('爬取失败')

def _360_search():
    url = 'http://www.so.com/s'
    try:
        kv = {'q': 'Python'}
        r = requests.get(url, params=kv)
        print(r.request.url)
        r.raise_for_status()  # 如果状态不是200，则引发HTTPError异常
        print(len(r.text))
    except:
        print('爬取失败')

"""
抓取照片 
"""
def cap_pictrue():
    url = 'http://image.nationalgeographic.com.cn/2017/0211/20170211061910157.jpg'
    root = '/Users/macbook/Desktop/pic/'
    path = root + url.split('/')[-1]
    try:
        if not os.path.exists(root):
            os.mkdir(root)
        if not os.path.exists(path):
            r = requests.get(url)
            with open(path, 'wb') as f:
                f.write(r.content)
                f.close()
                print('文件保存成功')
        else:
            print('文件已存在')
    except:
        print('爬取失败')


"""
ip归属地查询
"""
def ip_check():
    url = 'http://m.ip138.com/ip.asp?ip='
    try:
        r = requests.get(url + '115.156.143.128')
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        # print(r.status_code)
        print(r.text[-500:])
    except:
        print('爬取失败')



if __name__ == '__main__':
    # jd_item()
    # amazon_item()
    # baidu_search()
    # _360_search()
    # cap_pictrue()
    ip_check()