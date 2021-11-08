%���������� 1,7 .������������� NaNs ��� Iris dataset

%1.)�������� ��� ������� �� NaNs ��� ������� ��� ����������
data=irisV;
data (any(isnan(data),2),:) = [];
figure(2)
plot(data,'.')
title('�������� ��� ������� �� NaNs')

%2.)�������� ��� ������ �� NaNs ��� ������� ��� ����������
data2=irisV;
data2 (:,any(isnan(data2),1)) = [];
figure(3)
plot(data2,'.')
title('�������� ��� ������ �� NaNs')

%3.)������������� ��� NaNs �� 0 ��� ������� ��� ����������
data3= irisV;
notNaN = ~isnan(data3);
data3(~notNaN) = 0;
figure(4)
plot(data3,'.')
title('������������� ��� NaNs �� 0')

%4.)������ ��� ����� NaN ��� ������������� ���� �� �� ���� ���� ��� ������ ���
%������� ��� ������� ��� ����������(��������������� ��� ���������� �����
%������� �� ������ � ��� ������ ������� �� ������ �

data4= irisV;
notNaN = ~isnan(data4);
data4(~notNaN) = 0;
totalNo = sum(notNaN);
columnTot = sum(data4);
colMean = columnTot./ totalNo;
        
        
for i = 1:length(colMean)
       data4(find(notNaN(:,i)==0),i)=colMean(i);
end
figure(5)
plot(data4,'.');
title('������������� ��� NaNs �� �� ���� ���� ��� ������ ��� �������')
xlim([10 70])
ylim([0 25])