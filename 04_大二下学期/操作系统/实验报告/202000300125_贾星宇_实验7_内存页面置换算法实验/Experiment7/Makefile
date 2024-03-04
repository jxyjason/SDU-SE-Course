head = vmrp.h
srcs = vmrp.cc
objs = vmrp.o
opts = -w -g -c

all: vmrp

vmrp: $(objs)
	g++ $(objs) -o vmrp

vmrp.o: $(srcs) $(head)
	g++ $(opts) $(srcs)

clean:
	rm vmrp *.o
