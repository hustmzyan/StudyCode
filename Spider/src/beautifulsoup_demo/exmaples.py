import requests
from bs4 import BeautifulSoup
import bs4


def getHTMLText(url):
    try:
        r = requests.get(url, timeout = 30)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        return r.text
    except:
        print('爬取失败')
        return ''


def fillUnivList(uList, html):
    soup = BeautifulSoup(html, 'html.parser')
    for tr in soup.find('tbody').children:
        if isinstance(tr, bs4.element.Tag):
            tds = tr('td')
            uList.append([tds[0].string, tds[1].string, tds[3].string])



def printUnivList(uList, num):
    tplt = "{0:^10}\t{1:{3}^6}\t{2:^10}"
    print(tplt.format("排名", "学校名称", "得分", chr(12288)))
    for i in range(num):
        u = uList[i]
        print(tplt.format(u[0], u[1], u[2], chr(12288)))

"""
抓取最好大学排名网站  2019年软科排名
"""
def main():
    uInfo = []
    url = 'http://www.zuihaodaxue.cn/zuihaodaxuepaiming2019.html'
    html = getHTMLText(url)
    fillUnivList(uInfo, html)
    printUnivList(uInfo, 20)  # 20 Univ

if __name__ == '__main__':
    main()
