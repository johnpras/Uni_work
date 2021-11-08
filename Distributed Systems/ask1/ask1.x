struct intpair {
	int Y<10>;
	int n;
	int a;
};
struct intminmax {
	int min;
	int max;
	int MM<5>;
};
struct intmul {
	int aY<10>;
};
struct intavg {
	int avg;
};


program ASK1_PROG {
		version ASK1_VERS {
			intminmax MINMAX(intpair) = 1;
			intmul MUL(intpair) = 2;
			intavg AVG(intpair) = 3;
	} = 1;
} = 0x23451111;
