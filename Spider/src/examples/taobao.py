import requests
import re

def getHTMLText(url):
    coo = 'thw=cn; cna=p5wXFU+kmzkCAXOcjHdXuLaB; t=d3e09d22d79100b8220637eb89ef1103; tracknick=%5Cu8FDB%5Cu51FB%5Cu7684%5Cu4E25%5Cu5148%5Cu68EE; tg=0; enc=2CnE5tEV85MoQApVnFh1EMswOmN7OlYvYVbN47DQ%2B0SoSd9QXhoyKCYpQJOHXC7bQ%2BqyPkK0AdkVPLXFBLXfZg%3D%3D; hng=CN%7Czh-CN%7CCNY%7C156; v=0; cookie2=15e2525878c72e0295ea481c5a402c84; _tb_token_=e755d5470a8d1; alitrackid=www.taobao.com; unb=2457648483; sg=%E6%A3%AE3b; _l_g_=Ug%3D%3D; skt=2c825611ca74fea6; cookie1=BdNh2yFGauGbbXPomUunAzgdmA07APUQjT5QS9p5EGw%3D; csg=f564e9fb; uc3=vt3=F8dBy3vOzm8ZHLi19no%3D&id2=UUwVY95%2BbIL6WA%3D%3D&nk2=3q4e7T%2FUWLhWeWI8&lg2=V32FPkk%2Fw0dUvg%3D%3D; existShop=MTU1ODg2MDk0NQ%3D%3D; lgc=%5Cu8FDB%5Cu51FB%5Cu7684%5Cu4E25%5Cu5148%5Cu68EE; _cc_=W5iHLLyFfA%3D%3D; dnk=%5Cu8FDB%5Cu51FB%5Cu7684%5Cu4E25%5Cu5148%5Cu68EE; _nk_=%5Cu8FDB%5Cu51FB%5Cu7684%5Cu4E25%5Cu5148%5Cu68EE; cookie17=UUwVY95%2BbIL6WA%3D%3D; JSESSIONID=42A78DCD8BFF115ED21EDB0C16BA5736; lastalitrackid=login.taobao.com; mt=ci=6_1; l=bBTmpT2Pvp8z9HzDBOfahurza77TeIRb4AVzaNbMiICPOw5e5wchWZtN8kTwC3hNw17eJ3ui-tt0BeYBcBf..; isg=BDw8SD_VTCVGKXgA6iAh2y-7DdzkO3ywJN2K8xa98icK4dxrP0TK77ZbwEE8mBi3; uc1=cookie14=UoTZ7H0xnLgoGg%3D%3D&lng=zh_CN&cookie16=Vq8l%2BKCLySLZMFWHxqs8fwqnEw%3D%3D&existShop=false&cookie21=W5iHLLyFeYZ1WM9hVnmS&tag=8&cookie15=W5iHLLyFOGW7aA%3D%3D&pas=0'
    cookies = {}
    for line in coo.split(';'):
        name, value = line.strip().split('=', 1)
        cookies[name] = value
    header = {'user-agent': 'Mozilla/5.0'}
    try:
        r = requests.get(url, headers = header, cookies = cookies, timeout = 30)
        print('网页状态码：', r.status_code)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        # print(r.text)
        return r.text
    except:
        print('爬取失败')
        return ''

def parsePage(ilt, html):
    try:
        plt = re.findall(r'\"view_price\"\:\"[\d\.]*\"', html)
        tlt = re.findall(r'\"raw_title\"\:\".*?\"', html)
        # print(plt, tlt)
        for i in range(len(plt)):
            price = eval(plt[i].split(':')[1])
            title = eval(tlt[i].split(':')[1])
            ilt.append([price, title])
    except:
        print("")

def printGoodsList(ilt):
    tplt = "{:4}\t{:8}\t{:16}"
    print(tplt.format("序号", "价格", "商品名称"))
    count = 0
    for g in ilt:
        count = count + 1
        print(tplt.format(count, g[0], g[1]))

def main():
    goods = '书包'
    depth = 3
    start_url = 'https://s.taobao.com/search?q=' + goods
    info_List = []
    for i in range(depth):
        try:
            url = start_url + '&s=' + str(44*i)
            html = getHTMLText(url)
            parsePage(info_List, html)
        except:
            continue
    printGoodsList(info_List)

if __name__ == '__main__':
    main()