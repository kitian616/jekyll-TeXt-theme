exec


docker run image_name /bin/bash -c "mvn" can propagate SIGTERM signal

vs.

docker run image_name /bin/bash -c "echo abc;mvn" it will not be propagated to mvn

docker run image_name /bin/bash -c "echo abc;exec mvn"  can solve this issue.

docker run image_name /bin/bash -c "echo abc&&mvn" can propagate SIGTERM signal

fork exec ?


trap : tldp.org