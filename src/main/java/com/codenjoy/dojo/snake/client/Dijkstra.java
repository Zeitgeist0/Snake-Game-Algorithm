package com.codenjoy.dojo.snake.client;



import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Dijkstra {
  public static class Vertex implements Comparable<Vertex> {
    private Point point;
    private List<Edge> edges = new ArrayList<>();
    private double minDistance = Double.POSITIVE_INFINITY;
    private Vertex previous;
    private boolean visited;
    public Vertex(Point point) {
      this.point = point;
    }

    public void addEdge(Point point) {
      edges.add(new Edge(new Vertex(point), 1));
    }

    public void addEdge(Vertex vertex) {
      edges.add(new Edge(vertex, 1));
    }
    public void setEdges(List<Edge> edges) {
      this.edges = edges;
    }

    public int compareTo(Vertex other) {
      return Double.compare(minDistance, other.minDistance);
    }

    public Point getPoint() {
      return point;
    }
public boolean isVisited () {return visited;}
    public void setPoint(Point point) {
      this.point = point;
    }

    @Override
    public String toString() {
      return "{" + point + '}';
    }
  }

  static record Edge(Vertex target, double weight) {}

  public static void addNeighbours(Vertex head, List<Vertex> vertexList) {
 Queue<Vertex> vertexQueue = new LinkedList<>();
vertexQueue.offer(head);
while (!vertexQueue.isEmpty()){
Vertex current = vertexQueue.poll();
if(!current.isVisited()) {
  current.visited = true;
vertexList.forEach(vertex -> {
  if (isNeighbour(current.getPoint(), vertex.getPoint())) {
    current.addEdge(vertex);
    vertexQueue.offer(vertex);
  }
});
      } }
  }


  public  static  Vertex createGraph (List <Point> points , Point head, Point apple ) {
    List<Vertex> vertexList = new ArrayList<>();
    points.forEach(point -> {
      vertexList.add(new Vertex(point));
    });
   Vertex headV = new Vertex(head);
  addNeighbours(headV,vertexList);
  computePaths(headV);
  Vertex appleV = null;

    for (int i = 0; i < vertexList.size() -1 ; i++) {
      if(vertexList.get(i).getPoint().equals(apple)) {
        appleV = vertexList.get(i);
      }
    }
    return appleV;
  }

  public  static  Vertex createBadAppleGraph (List <Point> points , Point head, Point apple ) {
    List<Vertex> vertexList = new ArrayList<>();
    points.forEach(point -> {
      vertexList.add(new Vertex(point));
    });
    Vertex headV = new Vertex(head);
    addNeighbours(headV,vertexList);
    computePaths(headV);
    Vertex appleV = null;

    for (int i = 0; i < vertexList.size() -1 ; i++) {
      if(vertexList.get(i).getPoint().equals(apple)) {
        appleV = vertexList.get(i);
      }
    }
    return appleV;
  }

  public static boolean isNeighbour(Point current, Point point) {
    Point up = new PointImpl(current.getX(), current.getY() + 1);
    Point down = new PointImpl(current.getX(), current.getY() - 1);
    Point left = new PointImpl(current.getX() - 1, current.getY());
    Point right = new PointImpl(current.getX() + 1, current.getY());

    return point.itsMe(up) || point.itsMe(down) || point.itsMe(left) || point.itsMe(right);
  }

  public static void computePaths(Vertex source) {
    source.minDistance = 0;
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
      Vertex current = vertexQueue.poll();

      for (Edge adjEdge : current.edges) {

        Vertex neighbour = adjEdge.target();

        double edgeWeight = adjEdge.weight();

        double distanceThroughCurrent = current.minDistance + edgeWeight;

        if (distanceThroughCurrent < neighbour.minDistance) {

          vertexQueue.remove(neighbour);

          neighbour.minDistance = distanceThroughCurrent;

          neighbour.previous = current;

          vertexQueue.add(neighbour);
        }
      }
    }
  }

  public static List<Vertex> getShortestPathTo(Vertex target) {
    List<Vertex> path = new ArrayList<>();
    for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
      path.add(vertex);
    }
    Collections.reverse(path);
    return path;
  }



  public static Direction findDirection (Point current, Point next ) {
    Point up = new PointImpl(current.getX(), current.getY() + 1);
    Point down = new PointImpl(current.getX(), current.getY() - 1);
    Point left = new PointImpl(current.getX() - 1, current.getY());
    Point right = new PointImpl(current.getX() + 1, current.getY());

    if (next.equals(up)) {
      return  Direction.UP;
    } else if (next.equals(down)){
      return  Direction.DOWN;
    } else if (next.equals(left)){
      return  Direction.LEFT;
    }else if (next.equals(right)){
      return  Direction.RIGHT;
    }
    return Direction.UP;
  }

  public static void main(String[] args) {

} }
