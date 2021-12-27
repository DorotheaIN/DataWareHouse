import json

class asinExtract(object):
    def __init__(self, url, urlOutput):
        x = 0

        asin = []

        with open(url, 'r') as f:
            while 1:
                line = f.readline()
                if not line:
                    break
                data = json.loads(line)
                asin.append(data["asin"])
                x += 1

        asin = list(set(asin))
        print(len(asin))

        with open(urlOutput, 'w') as f:
            f.write(json.dumps(asin))

        print("done")
        print(x)

if __name__=="__main__":
    asinExtract('Movies_and_TV_5.json', "asin.json")