clc;
clear all;
close all;

load iris.dat;
[row,col]=size(iris);
X=[iris(:,1),iris(:,2),iris(:,3),iris(:,4)];
[row,col]=size(X);

% (1)-(3)
Deucl=pdist(X,'euclidean');
Dcb=pdist(X,'cityblock');
C=cov(X);
Dmah=pdist(X,'mahalanobis',C);
Zeucl=squareform(Deucl);
Zcb=squareform(Dcb);
Zmah=squareform(Dmah);
for j=2:row
    Aeucl(j-1)=Zeucl(1,j);
    Acb(j-1)=Zcb(1,j);
    Amah(j-1)=Zmah(1,j);
end
plot(Aeucl,'b');
hold on;
plot(Acb,'r');
plot(Amah,'k');
title('Blue: Euclidean   Red: Cityblock   Black: Mahalanobis');


% (4)
X=[iris(:,1),iris(:,2)];
[row,col]=size(X);
Deucl2=pdist(X,'euclidean');
Zeucl2=squareform(Deucl2);
Dcb2=pdist(X,'cityblock');
Zcb2=squareform(Dcb2);
C=cov(X);
Dmah2=pdist(X,'mahalanobis',C);
Zmah2=squareform(Dmah2);
for j=2:row
    Aeucl2(j-1)=Zeucl2(1,j);
    Acb2(j-1)=Zcb2(1,j);
    Amah2(j-1)=Zmah2(1,j);
end
figure(2)
plot(Aeucl,'b');
hold on;
plot(Aeucl2,'r');
title('EUCLIDEAN   Blue: 4 features Red: 2 features');

figure(3)
plot(Acb,'b');
hold on;
plot(Acb2,'r');
title('CITYBLOCK   Blue: 4 features   Red: 2 features');

figure(4)
plot(Amah,'b');
hold on;
plot(Amah2,'r');
title('MAHALANOBIS   Blue: 4 features   Red: 2 features');

% (5)
%a)
X=[iris(:,1),iris(:,3)];
[row,col]=size(X);
Deucl2=pdist(X,'euclidean');
Zeucl2=squareform(Deucl2);
Dcb2=pdist(X,'cityblock');
Zcb2=squareform(Dcb2);
C=cov(X);
Dmah2=pdist(X,'mahalanobis',C);
Zmah2=squareform(Dmah2);
for j=2:row
    Aeucl2(j-1)=Zeucl2(1,j);
    Acb2(j-1)=Zcb2(1,j);
    Amah2(j-1)=Zmah2(1,j);
end
figure(5)
plot(Aeucl,'b');
hold on;
plot(Aeucl2,'r');
title('EUCLIDEAN   Blue: 4 features Red: 1o 3o');

figure(6)
plot(Acb,'b');
hold on;
plot(Acb2,'r');
title('CITYBLOCK   Blue: 4 features   Red: 1o 3o');

figure(7)
plot(Amah,'b');
hold on;
plot(Amah2,'r');
title('MAHALANOBIS   Blue: 4 features   Red: 1o 3o');

%b)
X=[iris(:,3),iris(:,4)];
[row,col]=size(X);
Deucl2=pdist(X,'euclidean');
Zeucl2=squareform(Deucl2);
Dcb2=pdist(X,'cityblock');
Zcb2=squareform(Dcb2);
C=cov(X);
Dmah2=pdist(X,'mahalanobis',C);
Zmah2=squareform(Dmah2);
for j=2:row
    Aeucl2(j-1)=Zeucl2(1,j);
    Acb2(j-1)=Zcb2(1,j);
    Amah2(j-1)=Zmah2(1,j);
end
figure(8)
plot(Aeucl,'b');
hold on;
plot(Aeucl2,'r');
title('EUCLIDEAN   Blue: 4 features Red: 3o 4o');

figure(9)
plot(Acb,'b');
hold on;
plot(Acb2,'r');
title('CITYBLOCK   Blue: 4 features   Red: 3o 4o');

figure(10)
plot(Amah,'b');
hold on;
plot(Amah2,'r');
title('MAHALANOBIS   Blue: 4 features   Red: 3o 4o');

%c)
X=[iris(:,1),iris(:,2),iris(:,3)];
[row,col]=size(X);
Deucl2=pdist(X,'euclidean');
Zeucl2=squareform(Deucl2);
Dcb2=pdist(X,'cityblock');
Zcb2=squareform(Dcb2);
C=cov(X);
Dmah2=pdist(X,'mahalanobis',C);
Zmah2=squareform(Dmah2);
for j=2:row
    Aeucl2(j-1)=Zeucl2(1,j);
    Acb2(j-1)=Zcb2(1,j);
    Amah2(j-1)=Zmah2(1,j);
end
figure(11)
plot(Aeucl,'b');
hold on;
plot(Aeucl2,'r');
title('EUCLIDEAN   Blue: 4 features Red: 1o 2o 3o');

figure(12)
plot(Acb,'b');
hold on;
plot(Acb2,'r');
title('CITYBLOCK   Blue: 4 features   Red: 1o 2o 3o');

figure(13)
plot(Amah,'b');
hold on;
plot(Amah2,'r');
title('MAHALANOBIS   Blue: 4 features   Red: 1o 2o 3o');

%d)
X=[iris(:,1),iris(:,2),iris(:,4)];
[row,col]=size(X);
Deucl2=pdist(X,'euclidean');
Zeucl2=squareform(Deucl2);
Dcb2=pdist(X,'cityblock');
Zcb2=squareform(Dcb2);
C=cov(X);
Dmah2=pdist(X,'mahalanobis',C);
Zmah2=squareform(Dmah2);
for j=2:row
    Aeucl2(j-1)=Zeucl2(1,j);
    Acb2(j-1)=Zcb2(1,j);
    Amah2(j-1)=Zmah2(1,j);
end
figure(14)
plot(Aeucl,'b');
hold on;
plot(Aeucl2,'r');
title('EUCLIDEAN   Blue: 4 features Red: 1o 2o 4o');

figure(15)
plot(Acb,'b');
hold on;
plot(Acb2,'r');
title('CITYBLOCK   Blue: 4 features   Red: 1o 2o 4o');

figure(16)
plot(Amah,'b');
hold on;
plot(Amah2,'r');
title('MAHALANOBIS   Blue: 4 features   Red: 1o 2o 4o');
