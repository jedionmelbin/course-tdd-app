FROM ubuntu:latest
LABEL authors="jedionmelbin"

ENTRYPOINT ["top", "-b"]