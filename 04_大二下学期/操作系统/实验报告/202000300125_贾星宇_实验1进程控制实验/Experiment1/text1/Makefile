head = main.h
srcs = main.c
objs = main.o
opts = -g -c
all: main
pctl: $(objs)
	gcc $(objs) -o main
pctl.o: $(srcs) $(head)
	gcc $(opts) $(srcs)
clean:
	rm main *.o
