[![codecov](https://codecov.io/gh/jimnewpower/line-smoothing/branch/master/graph/badge.svg?token=YULIH50YWG)](undefined)

# line-smoothing
Smoothing for polylines and polygons. Implementation of George Chaikin's corner-cutting smoothing algorithm. By iterating on calls to the algorithm, more smoothness will be achieved.

# code example
```
Poly original = new PolyImpl();
original.add(Coordinate.of(0, 0));
original.add(Coordinate.of(10, 0));
original.add(Coordinate.of(10, 10));
original.add(Coordinate.of(0, 10));
original.closePolygon();

Poly smoothed = new ChaikinSmoother().smooth(original);
```

## Example 1 (Square with 1 iteration)
![](./src/main/resources/chaikin-square-1.png)

## Example 2 (Square with 2 iterations)
![](./src/main/resources/chaikin-square-2.png)

## Example 3 (Square with 3 iterations)
![](./src/main/resources/chaikin-square-3.png)

## Example 4 (Square with 4 iterations)
![](./src/main/resources/chaikin-square-4.png)

## Example 5 (Polygon with 6 iterations)
![](./src/main/resources/chaikin-1.png)

## Example 6 (Polygon with 6 iterations)
![](./src/main/resources/chaikin-2.png)

## Example 7 (Polyline with 4 iterations)
![](./src/main/resources/polyline-4.png)
