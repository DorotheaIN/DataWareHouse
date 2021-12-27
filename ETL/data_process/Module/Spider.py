import requests
import json
import time
from lxml import etree
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor,wait,ALL_COMPLETED
import random
import threading
from fake_useragent import UserAgent
import os

proxypool_url='http://127.0.0.1:5555/random'
base_url='https://www.amazon.com/dp/'
input_file_name='..\\Data\\asin.json'
#User IP Proxy Pool
head_user_agent1 = ['Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko',
                         'Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/28.0.1500.95 Safari/537.36',
                         'Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; rv:11.0) like Gecko)',
                         'Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1',
                        'Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070309 Firefox/2.0.0.3',
                        'Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070803 Firefox/1.5.0.12',
                        'Opera/9.27 (Windows NT 5.2; U; zh-cn)',
                        'Mozilla/5.0 (Macintosh; PPC Mac OS X; U; en) Opera 8.0',
                        'Opera/8.0 (Macintosh; PPC Mac OS X; U; en)',
                        'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.12) Gecko/20080219 Firefox/2.0.0.12 Navigator/9.0.0.6',
                        'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0)',
                        'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)',
                        'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C; .NET4.0E)',
                        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Maxthon/4.0.6.2000 Chrome/26.0.1410.43 Safari/537.1 ',
                        'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C; .NET4.0E; QQBrowser/7.3.9825.400)',
                        'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0 ',
                        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Firefox/21.0.1180.92 Safari/537.1 LBBROWSER',
                        'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)',
                        'Mozilla/5.0(compatible;MSIE9.0;WindowsNT6.1;Trident/5.0',
                        'Mozilla/5.0(Macintosh;U;IntelMacOSX10_6_8;en-us)AppleWebKit/534.50(KHTML,likeGecko)Version/5.1Safari/534.50',
                        'Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;TheWorld)',
                        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Firefox/20.0.1132.11 TaoBrowser/3.0 Safari/536.11',
                        'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0']
 
us=UserAgent(path=r"..\\userAgent.json")
us1=UserAgent()
#Web Write Folder
aby_dir='..\\Data\\html\\'
fail=[]

def get_result(asin):
    url=base_url+asin   
    success=False
    #If cannot crawl, change the IP and crawl again and again, 10 times is the maximum
    attempts=10
    m=''
    while(success==False and attempts>0):
        try:
            proxy=requests.get(proxypool_url).text.strip()
            proxies = {'http': 'http://' + proxy}
            cookie = {'session-id':'142-0083704-2597614','session-id-time':'2082787201l','i18n-prefs':'USD','lc-main':'en_US', 'sp-cdn':"L5Z9:CN", 'ubid-main':'130-3487354-1709414', 'session-token':"rocTDldgoHVAkw37tgKa4MUohl5/nmgpe5gwZlqLXreSK658oUd4G0lZnx8yJNS+oj+ZaaLXKINGOCQtekiuyZaheRMgDI21UvLMeumA0TuTXndiSyc459DjbZqCGQEylTdR4Q3yxLvowD+rS8dmp2ss1qKKTHxcep13MOEgxrG4HaYVCEiPXkNhNEm50rn4; av-timezone=Asia/Shanghai"}
            cookie1={'session-id':'132-9852551-3900415', 'session-id-time':'2082787201l','i18n-prefs':'USD', 'sp-cdn':"L5Z9:CN",'ubid-main':'134-5091169-2757968','session-token':'3cJhlSwapyp/3OVFrZIKj8NR973Kx6Ayl83Gys3zsin9bAJbOWCzSYoMbH/2g2PA6CQwcuLy+Hqeb1Mwjt968ztOFwHHSfzDMc9TGewo3LGm2bQ5JqmnW6rjo0DEjSG29nczJVmdN8eaRjU0uRURzDXF0wbIBL9l2O8mOFWHnjh5bjPF7pMt8VTvb/4Q2qqr','lc-main':'en_US'
 }
            cookie2={'lc-main':'en_US','av-timezone':'Asia/Shanghai','session-id':'134-2100190-5995345','i18n-prefs':'USD','sp-cdn':'L5Z9:CN','ubid-main':'130-9729655-0757155','session-id-time':'2082787201l','session-token':'r2P3B/vIQY/O08+Ir+5kIILgXtanWpeZtVCo7AyNQ/Es/nFPy/haxCrlteRkyipxcrNKxYeS/YIECHMJuy0b7q6zAl2eIAzwIjIqMfhvH3SWeKWble6qtViX2AwvOfUUjMuTrvkOvgAR7WCpeTY54AUrVYCJKoyObjX5wOsjVbW9GV+YTq8mrc0bVXy0hq1j'}
            cookie3={'session-id':'132-9852551-3900415','session-id-time':'2082787201l', 'i18n-prefs':'USD', 'sp-cdn':'L5Z9:CN', 'ubid-main':'134-5091169-2757968','session-token':'o74Vf2tbIvuFkoOgUTacamahfzzmVdn2gsfLNN9yqKtI6u2DTmVX4US+G33QL2Ta4l8FxPpRAtH3EHRT0HzmTU30ot1r2AlQ9S+SMAk15BiibyXOAV2KY1HI5FM3DS++FUcRvLOfhBburBw0+RTdRDI8R6UwBbvxsKrqWLVD8brEIZP2+gwdvwxrLxEq3L/c','lc-main':'en_US'
            }
            header = {
                "User-Agent": head_user_agent1[random.randrange(0, len(head_user_agent1))],
                "Connection": "closer",
                "Upgrade-Insecure-Requests": "1",
                "Cache-Control": "max-age=0",
                "Referer":"http://www.google.com"
            }
            result = requests.get(url,headers=header,proxies=proxies)
            result = result.text.encode('gbk', 'ignore').decode('gbk')
            soup = BeautifulSoup(result, 'lxml')
            movie_title = str(soup.select('title')[0].getText())
            m=movie_title
            detail=soup.select("#detailBulletsWrapper_feature_div > h2:nth-child(2)")
            if (movie_title != 'Robot Check') and (movie_title != 'Sorry! Something went wrong!') and (movie_title != 'Amazon.com'):   
                #Preventing pages from crawling down before they are fully rendered        
                if(str(detail) == '[<h2>Product details</h2>]'):
                    fo = open(aby_dir + asin + ".html", "w")
                    fo.write(result)
                    fo.close()
                    #Random dormancy, anti-crawl
                    a=random.uniform(0,0.2)
                    time.sleep(a)
                    success = True
                else:
                    attempts-=1
            else:      
                attempts=attempts-1
        except Exception as e:
            print('[error]', e)
            attempts -= 1
            if attempts == 0:
                break
    if(success==False):
        print('[title error]', m,asin)
        fail.append(asin)
def get_results(asins):
# Accelerating crawling with concurrency
    executor = ThreadPoolExecutor(max_workers=8)
    future_tasks = [executor.submit(get_result, a) for a in asins]
    wait(future_tasks, return_when=ALL_COMPLETED)

if __name__=="__main__":
   start_time = time.time()
   #read file
   with open(input_file_name,'r') as asin_obj:
     asins = json.load(asin_obj)
   while(1):
    a2=os.listdir("..\\Data\\html")
    #Remove the .html from the file name and compare
    for i in range(len(a2)):
        a2[i]=a2[i][:-5]
    #Find the difference between the two and crawl
    a=list(set(asins)-set(a2))  
    print(len(asins))
    get_results(a)
    end_time = time.time()
    print('%s' %(end_time-start_time))
    if(len(a2)==len(asins)):
        break
