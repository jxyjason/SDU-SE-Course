    .text
    .global main
main:
    push %rbp
    mov %rsp, %rbp
    sub $64, %rsp
    mov $0, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov $62, %rdi
    mov %rdi, (%rax)
    mov $8, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov $41, %rdi
    mov %rdi, (%rax)
    mov $16, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov $13, %rdi
    mov %rdi, (%rax)
    mov $24, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov $11, %rdi
    mov %rdi, (%rax)
    lea -40(%rbp), %rax
    push %rax
    mov $4, %rax
    pop %rdi
    mov %rax, (%rdi)
    lea -48(%rbp), %rax
    push %rax
    mov $2, %rax
    pop %rdi
    mov %rax, (%rdi)
    lea -56(%rbp), %rax
    push %rax
    mov $1, %rax
    pop %rdi
    mov %rax, (%rdi)
.L.condition.1:
    lea -40(%rbp), %rax
    mov (%rax), %rax
    push %rax
    lea -48(%rbp), %rax
    mov (%rax), %rax
    pop %rdi
    cmp %rdi, %rax
    setle %al
    movzb %al, %rax
    cmp $0, %rax
    je  .L.end.1
    lea -48(%rbp), %rax
    mov (%rax), %rax
    sub $1, %rax
    imul $8, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov (%rax), %rax
    push %rax
    lea -56(%rbp), %rax
    mov (%rax), %rax
    sub $1, %rax
    imul $8, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov (%rax), %rax
    pop %rdi
    cmp %rdi, %rax
    setg %al
    movzb %al, %rax
    cmp $0, %rax
    je  .L.else.2
    lea -56(%rbp), %rax
    push %rax
    lea -48(%rbp), %rax
    mov (%rax), %rax
    pop %rdi
    mov %rax, (%rdi)
    jmp .L.endd.2
.L.else.2:
.L.endd.2:
    lea -48(%rbp), %rax
    push %rax
    mov $1, %rax
    push %rax
    lea -48(%rbp), %rax
    mov (%rax), %rax
    pop %rdi
    add %rdi, %rax
    pop %rdi
    mov %rax, (%rdi)
    jmp .L.condition.1
.L.end.1:
    lea -56(%rbp), %rax
    mov (%rax), %rax
    sub $1, %rax
    imul $8, %rax
    push %rax
    lea -32(%rbp), %rax
    pop %rdi
    add %rdi, %rax
    mov (%rax), %rax
    jmp .main.return
.main.return:
    mov %rbp, %rsp
    pop %rbp
    ret
