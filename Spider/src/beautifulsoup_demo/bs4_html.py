import requests
from bs4 import BeautifulSoup

url = 'https://python123.io/ws/demo.html'
r = requests.get(url)
demo = r.text

# 设置为html解析器进行解析
soup = BeautifulSoup(demo, 'html.parser')

# 打印解析后的内容
# print(soup.prettify())
# print(soup.a.prettify())

# 提取a标签中所有的链接
for link in soup.find_all('a'):
    print(link.get('href'))

