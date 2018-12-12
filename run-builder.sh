#!/bin/bash

# This shell script starts the build-image and gives you an
# interactive shell to work with.
#
# Once you have the interactive shell, you can type
#   mvn package
# ... to build the jar


docker run --rm -it -v `PWD`:`PWD` -w `PWD` netty-builder /bin/bash
