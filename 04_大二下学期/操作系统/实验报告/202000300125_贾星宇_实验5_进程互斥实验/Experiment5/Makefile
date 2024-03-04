hdrs = ipc.h
c_src = control.c ipc.c
c_obj = control.o ipc.o
r_src = reader.c ipc.c
r_obj = reader.o ipc.o
w_src = writer.c ipc.c
w_obj = writer.o ipc.o
opts = -g -c

all: control reader writer

control: $(c_obj)
	gcc $(c_obj) -o control

control.o: $(c_src) $(hdrs)
	gcc $(opts) $(c_src)

reader: $(r_obj)
	gcc $(r_obj) -o reader

reader.o: $(r_src) $(hdrs)
	gcc $(opts) $(r_src)

writer: $(w_obj)
	gcc $(w_obj) -o writer

writer.o: $(w_src) $(hdrs)
	gcc $(opts) $(w_src)

clean:
	rm control reader writer *.o
