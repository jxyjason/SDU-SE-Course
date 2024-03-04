int main(){
int array[4] = {2,4,3,1};
int len;
len = 4;
int i;
int min;
i=2;
min=1;

while(i<=len){

if(array[min]>array[i]) then
    min = i;

i = i+1;
}
return array[min];
}