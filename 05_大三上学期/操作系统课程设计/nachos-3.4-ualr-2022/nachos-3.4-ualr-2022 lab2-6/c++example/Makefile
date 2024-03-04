all: stack inheritstack templatestack

stack: stack.h stack.cc
	g++ -g -o stack stack.cc

inheritstack: inheritstack.h inheritstack.cc list.h list.cc
	g++ -g -o inheritstack inheritstack.cc list.cc

templatestack: templatestack.h templatestack.cc
	g++ -g -o templatestack templatestack.cc
