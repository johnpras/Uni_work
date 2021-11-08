load iris.dat;
setosa=iris((iris(:,5)==1),:);      % data for setosa (label = 1)
versicolor=iris((iris(:,5)==2),:);  % data for versicolor (label = 2)
virginica=iris((iris(:,5)==3),:);   % data for virginica (label = 3)
obsv_n=size(iris,1);                % total number of observations

%2-D representation
Characteristics={'sepal length','sepal width', 'petal length','petal width'};
pairs=[1 2; 1 3; 1 4; 2 3; 2 4; 3 4];
for j=1:6,
    x=pairs(j,1);
    y=pairs(j,2);
    subplot(2,3,j);
    
    % The following code aims at plotting 3 pairs: setosa(y) vs
    % setosa(x), versicolor(y) vs versicolor(x) etc. These pairs will be
    % plotted in different color (by default).
    
    % MarkerSize in order to define dot size.
    
    % Matix Characteristics is used to print the right labels next to x/y
    % axes.
    plot([setosa(:,x) versicolor(:,x) virginica(:,x)],...
        [setosa(:,y) versicolor(:,y) virginica(:,y)],'.','MarkerSize',10);
    xlabel(Characteristics(x));
    ylabel(Characteristics(y));
end

[ro,co]=size(iris);
p=60;               % Percentage of NaN values that will replace initial values in the iris data set
irisV=iris;         % irisV-> Modified iris dataset
r1=randperm(ro);    % random permutaion: returns a row vector containing a random permutation of the integers from 1 to n inclusive.
irisV(r1(1:p),1)=NaN;   % replace 60% of first column's values with NaN

r1=randperm(ro);
irisV(r1(1:p),2)=NaN;   % replace 60% of second column's values with NaN

r1=randperm(ro);
irisV(r1(1:p),3)=NaN;   % replace 60% of third column's values with NaN

r1=randperm(ro);
irisV(r1(1:p),4)=NaN;   % replace 60% of fourth column's values with NaN