%παράδειγμα 1,7 .Αντικατάσταση NaNs στο Iris dataset

%1.)διαγραφή των γραμμών με NaNs και προβολή του γραφήματος
data=irisV;
data (any(isnan(data),2),:) = [];
figure(2)
plot(data,'.')
title('διαγραφή των γραμμών με NaNs')

%2.)διαγραφή των στηλών με NaNs και προβολή του γραφήματος
data2=irisV;
data2 (:,any(isnan(data2),1)) = [];
figure(3)
plot(data2,'.')
title('διαγραφή των στηλών με NaNs')

%3.)Αντικατάσταση των NaNs με 0 και προβολή του γραφήματος
data3= irisV;
notNaN = ~isnan(data3);
data3(~notNaN) = 0;
figure(4)
plot(data3,'.')
title('αντικατάσταση των NaNs με 0')

%4.)Εύρεση των τιμών NaN και αντικατάσταση τους με τη μέση τιμή της στήλης που
%ανήκουν και προβολή του γραφήματος(χρησιμοποιώντας τις διαστάσεις μήκος
%πέταλου ως άξονας Χ και πλάτος πέταλου ως άξονας Υ

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
title('αντικατάσταση των NaNs με τη μέση τιμή της στήλης που ανήκουν')
xlim([10 70])
ylim([0 25])