import sys
readline = sys.stdin.readline

n,m,k = map(int,readline().split())
data = list(map(int,readline().split()))

data.sort(reverse=True)
count = (m//(k+1)) * k
count += m % (k+1)

ret = (count) * data[0]
ret += (m-count) * data[1]
print(ret)