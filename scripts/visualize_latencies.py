import requests
import matplotlib.pyplot as plt
import time

latencies = []

for i in range(1000):
    start = time.time()
    x = requests.get('http://localhost:8080/strings?u=http://127.0.0.1:8090/fibo&u=http://127.0.0.1:8090/primes&u=http://127.0.0.1:8090/odd&u=http://127.0.0.1:8090/rand')
    roundtrip = int((time.time() - start) * 1000)
    latencies.append(roundtrip)
    print(f"saved {roundtrip} to latencies, number {i}")

percentile_lower_than_500 =  sum(i < 500 for i in latencies) / len(latencies) * 100

plt.style.use('ggplot')
plt.xlabel(f"Percentile of requests under 500 mills {str(int(percentile_lower_than_500))}%")
plt.hist(latencies, bins=200)
plt.show()