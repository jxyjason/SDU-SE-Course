#!/bin/bash
cat <<EOF | gcc -xc -c -o tmp2.o -
#include <stdio.h>
int ret3() { return 3; }

int ret5() { return 5; }

int add(int x, int y) { return x+y; }

int sub(int x, int y) { return x-y; }

int add6(int a, int b, int c, int d, int e, int f) {
  return a+b+c+d+e+f;
}

int myprint() {printf("Hello, software school.sdu.cn!!!!!!\n");}
EOF

assert() {
  expected="$1"
  input="$2"

  echo "$input" | python3 cbypython.py - > tmp.s || exit
  #  python3 cbypython.py "$input" > tmp.s
  gcc -o tmp tmp.s tmp2.o
  ./tmp
  actual="$?"

  if [ "$actual" = "$expected" ]; then
    echo "$input => $actual"
  else
    echo "$input => $expected expected, but got $actual"
    exit 1
  fi
}

assert 9 'int main() {int a; a =5; int b; b =9;}'
assert 6 'int main() {int a; a =5; int b ; b= a +1;}'
assert 4 'int main() {int _a; _a=5; int b; b = _a - 1;}'

assert 8 'int main() {return 7+1;}'
assert 13 'int main() {return 7+2*3;}'
assert 6 'int main() {int a,b,c,d; a=3; return a+3; 9;}'

assert 9 'int main() {7; 8; 9;}'
assert 9 'int main() {int _a; _a =5; int b; b =9;}'

assert 9 'int main() {7; ; 9;}'
assert 14 'int main() {int _a; _a=5; {{;};} ; {int b; b = _a + 9;};;;}'

assert 3 'int main() { return ret3(); }'
assert 5 'int main() { {return ret5();} }'
assert 9 'int main() { ret3()+5; 9; }'

assert 21 'int main() { return add6(1,1+1,3,4,5,6); }'
assert 27 'int main() { return add6(2,3,4,5,6,3+4); }'
assert 33 'int main() { return add6(3,4,5,6,7,8); }'
assert 66 'int main() { return add6(1,2,add6(3,4,5,6,7,8),9,10,11); }'
assert 136 'int main() { return add6(1,2,add6(3,add6(4,5,6,7,8,9),10,11,12,13),14,15,16); }'
assert 5  'int main() {int a; a=2; {int b; b=a+3;return b;} }'

assert 33  'int main() {
                return foo(11,22);
             }
             int foo(int a, int b){
                return a+b;
             }'
assert 21  'int foo(int a, int b, int c, int d, int e, int f){
                  return a+b+c+d+e+f;
             }

             int main() {
                  return foo(1,2,3,4,5,6);
             } '

assert 3 'int main() {if(1) then return 3; else return 5;}'
assert 5 'int main() {if(0) then return 3; else return 5;}'
assert 5 'int main() {if(1>2) then return 3; else return 5;}'
assert 3 'int main() {if(1<2) then ; return 3;}'
assert 3 'int main() {if(1>2) then return 5; else ; return 3;}'
assert 6 'int main() {myprint(); return 6; }'

assert 3 'int main() {bool b; b= (1<2) || (1>2) ; if(b) then return 3;}'

echo OK
