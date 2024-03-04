  .text
  .globl main
main:
  push %rbp
  mov %rsp, %rbp
  sub $0, %rsp
  mov $0, %rax
  call myprint
  mov $6, %rax
  jmp .main.return
.main.return:
  mov %rbp, %rsp
  pop %rbp
  ret
