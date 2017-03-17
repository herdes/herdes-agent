curl -X POST -H "Content-Type: application/json" http://127.0.0.1:2375/containers/create -d '{
    "Entrypoint": "/bin/bash",
    "Image": "ubuntu"
}'