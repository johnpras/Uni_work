#include "ask1.h"

intminmax *
minmax_1_svc(intpair *argp, struct svc_req *rqstp)
{
	static intminmax  result;

printf("minmax function called\n");
//δήλωση πίνακα 2 θέσεων, μια για το μικρότερη και η άλλη για το μεγαλύτερο
result.MM.MM_len= 2;
result.MM.MM_val= (int *) malloc(result.MM.MM_len * sizeof(int));

//εύρεση min
result.max=-99999;
result.min=99999;
for(int i=0;i<argp->n;i++){
	if(argp->Y.Y_val[i] < result.min){
		result.min = argp->Y.Y_val[i];
	}
}
//εύρεση max
for(int i=0;i<argp->n;i++){
	if(argp->Y.Y_val[i] > result.max){
		result.max = argp->Y.Y_val[i];
	}
}
//τοποθέτηση min,max στον πίνακα που θα γυρίσω στον client
result.MM.MM_val[0]=result.min;
result.MM.MM_val[1]=result.max;
printf("minimun = %d, maximum = %d\n",result.MM.MM_val[0],result.MM.MM_val[1]);

	return &result;
}


intmul *
mul_1_svc(intpair *argp, struct svc_req *rqstp)
{
	static intmul  result;

printf("mult function called\n");
//δήλωση πίνακα 
result.aY.aY_len= argp->n;
result.aY.aY_val= (int *) malloc(result.aY.aY_len * sizeof(int));
//εύρεση του a*Y και τοποθέτηση στον πίνακα που θα γυρίσω στον client
for(int i=0;i<argp->n;i++){
	result.aY.aY_val[i]=argp->Y.Y_val[i]*argp->a;
	printf("%d ",result.aY.aY_val[i]);
}
printf("\n");

	return &result;
}


intavg *
avg_1_svc(intpair *argp, struct svc_req *rqstp)
{
	static intavg  result;


printf("avg function called\n");

int ssum=0;
int aavg=0;

for(int i=0;i<argp->n;i++){
	ssum += argp->Y.Y_val[i];
}
aavg=ssum/argp->n;
printf("avg is %d\n",aavg); 
//υπολογισμός average, και την τοποθετούμε στη μεταβλητή avg για να την πάρουμε μετά στον client
result.avg=aavg;

	return &result;
}
