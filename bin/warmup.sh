until $(curl -o /dev/null -s -I -f http://localhost:$PORT); do
	echo `java -version`
	echo `which java`
  	echo "http://localhost:$PORT"
  	sleep 1
done