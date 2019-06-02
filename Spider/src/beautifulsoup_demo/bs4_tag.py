import requests
from bs4 import BeautifulSoup

def dataset():
    url = 'https://python123.io/ws/demo.html'
    r = requests.get(url)
    return r.text

# 下行遍历
def bs_down(demo):
    soup = BeautifulSoup(demo, 'html.parser')
    print(soup.head)
    # 获取儿子节点
    # print(soup.head.contents)
    # print(soup.body.contents)
    # print(len(soup.body.contents))

    # 循环子节点遍历
    for child in soup.body.children:
        print(child)

    # 循环子孙节点遍历
    for child in soup.body.descendants:
        print(child)

def bs_up(demo):
    soup = BeautifulSoup(demo, 'html.parser')
    # print(soup.title.parent)
    # print(soup.html.parent)

    # 循环遍历父辈节点
    for parent in soup.a.parents:
        if parent is None:
            print(parent)
        else:
            print(parent.name)

def bs_parallel(demo):
    soup = BeautifulSoup(demo, 'html.parser')
    print(soup.a.next_sibling)
    print(soup.a.next_sibling.next_sibling)
    print(soup.a.previous_sibling)
    print(soup.a.previous_sibling.previous.sibling)

    # 平行循环遍历，注意s
    for sibling in soup.a.next_siblings:
        print(sibling)

if __name__ == '__main__':
    demo = dataset()
    # bs_down(demo)
    # bs_up(demo)
    bs_parallel(demo)