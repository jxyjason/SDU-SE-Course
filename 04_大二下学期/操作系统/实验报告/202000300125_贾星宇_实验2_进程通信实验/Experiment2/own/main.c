
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
int main(int argc,char *argv[]){
	int pid1,pid2;
	int pipe1[2];
	int pipe2[2];
	int x=1,y=1;
	if((pipe(pipe1)) < 0) {
		perror("pipe not create");
		exit (EXIT_FAILURE);
	}
	if((pipe(pipe2))<0) {
		perror("pipe not create") ;
		exit(EXIT_FAILURE);
	}
	if((pid1=fork())<0) {
		perror("process not create");
		exit(EXIT_FAILURE);
	}
	else if(pid1==0) {
		close(pipe1[0]);
		close(pipe2[0]);
		close(pipe2[1]);
		int fx=1;
		do {
			if(x==1) {
				fx=1;
			}
			else fx=fx*x;
			printf("child %d f(x): f(%d) = %d\n",getpid(),x++,fx);
			write(pipe1[1],&fx,sizeof(int));
		}
		while(x<=9);
		close(pipe1[1]);
		exit(EXIT_SUCCESS);
	}
	else {
		if((pid2=fork())<0) {
			perror(" process not create");
			exit(EXIT_FAILURE);
		}
		else if(pid2==0) {
			close(pipe1[0]);
			close(pipe2[0]);
			close(pipe1[1]);
			int fy=1,fy1=1,fy2=1;
			do {
				if(y==1||y==2) fy=1;
				else {
					fy1=fy2;
					fy2=fy;
					fy=fy1+fy2;
				}
				printf("child %d f(y): f(%d) = %d\n",getpid(),y++,fy);
				write(pipe2[1],&fy,sizeof(int));
			}
			while(y<=9);
			close(pipe2[1]);
			exit(EXIT_SUCCESS);
		}
		else {
			close(pipe1[1]);
			close(pipe2[1]);
			int m=1,n=1;
			do {
				read(pipe1[0],&x,sizeof(int));
				read(pipe2[0],&y,sizeof(int));
				printf("parent %d f(x:y):(%d,%d) = %d\n",getpid(),m++,n++,x+y);
			}
			while(m<=9&&n<=9);
			close(pipe1[0]);
			close(pipe2[0]);
			return EXIT_SUCCESS;
		}
	}
}

