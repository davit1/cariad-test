# Getting Started

### Questions

* Do we have to remove duplicate entries or not? I decided it makes sense to remove them because the result
array contains unique strings anyway.
* How do you want responses to always be less than 500 mills, it's technically very difficult to achieve.
I have always experienced talking about latencies in terms of distributions. So I have written a simple
python script that sends 1000 requests to the service and then plots a histogram to show the distribution
  (it is saved in the _resources_ folder).
Which actually delivered 100 percent of results faster than 500 ms but I assume if 
the service was deployed in the cloud, than the latencies would cross 
the 500 ms threshold.