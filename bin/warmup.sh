# GOOD
# OpenJDK Runtime Environment (build 1.8.0_92-cedar14-b14)
# /app/.jdk/bin/java

# BAD
#OpenJDK Runtime Environment (IcedTea 2.6.6) (7u101-2.6.6-0ubuntu0.14.04.1)
#/usr/bin/java
until $(curl -o /dev/null -s -I -f http://localhost:$PORT); do
	echo "### DEFAULT ###"
	echo `java -version`
	echo "### 1.8 ###"
	echo `/app/.jdk/bin/java -version`
	echo "### 1.7 ###"
	echo `/usr/bin/java -version`
  	echo "http://localhost:$PORT"
  	sleep 1
done