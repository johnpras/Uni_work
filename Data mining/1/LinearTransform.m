% Linear normalization
function yV=LinearTransform(xV)

xV=xV(:);
xmin=min(xV);
xmax=max(xV);
d=xmax-xmin;
yV=(xV-xmin)/d;     %return value: Normalized from [xmin, xmax] to [0, 1]

