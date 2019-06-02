import requests
from bs4 import BeautifulSoup

url = 'https://python123.io/ws/demo.html'
r = requests.get(url)
demo = r.text

# 设置为html解析器进行解析
soup = BeautifulSoup(demo, 'html.parser')

# 打印解析后的内容
# print(soup.prettify())

# 打印标签
print(soup.title)
print(soup.a)

# 标签的详细信息
print(soup.a.name)
print(soup.a.parent.name)
print(soup.a.parent.parent.name)
# 标签属性
print(soup.a.attrs)
print(soup.a.attrs['class'])
print(soup.a.attrs['href'])
print(type(soup.a.attrs))
print(type(soup.a))

# 查看内容信息
print(soup.a.string)
print(soup.p.string)
print(type(soup.a.string))