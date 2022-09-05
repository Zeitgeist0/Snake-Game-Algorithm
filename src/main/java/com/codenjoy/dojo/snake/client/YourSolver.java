package com.codenjoy.dojo.snake.client;


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

import java.util.List;


public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        if (board.isGameOver()) {
            return Direction.RIGHT.toString();
        }

        //Preparation
        System.out.println(board.toString());
        Point head = board.getHead();

      //      Good apple
      List<Point> pointList = board.get(Elements.NONE, Elements.GOOD_APPLE);
      List<Point> appleList = board.get(Elements.GOOD_APPLE);
      Point applePoint = appleList.get(0);
        Dijkstra.Vertex appleV = Dijkstra.createGraph(pointList, head , applePoint);
    List<Dijkstra.Vertex> path =   Dijkstra.getShortestPathTo(appleV);

      // Bad apple
      List<Point> badApplePointList = board.get(Elements.NONE, Elements.BAD_APPLE , Elements.GOOD_APPLE);
      List<Point> badAppleList = board.get(Elements.BAD_APPLE);
      Point badApplePoint = badAppleList.get(0);
      Dijkstra.Vertex badAppleV = Dijkstra.createBadAppleGraph(badApplePointList, head , badApplePoint);
      List<Dijkstra.Vertex> pathToBadApple =   Dijkstra.getShortestPathTo(badAppleV);

//      if(path.size() ==1) {
//  return FindWall.wallProtocol(board,head);
//}
//      return Dijkstra.findDirection(head,path.get(1).getPoint()).toString();
      System.out.println(path);
      System.out.println(pathToBadApple);

if(path.size() == 1 && pathToBadApple.size() == 1) {
  System.out.println("Going wall");
  return FindWall.wallProtocol(board,head);
} else if (path.size() == 1 && pathToBadApple.size() != 1) {
  System.out.println("Going bad apple");
  return Dijkstra.findDirection(head,pathToBadApple.get(1).getPoint()).toString();
} else {
  System.out.println("Going good apple");
  return Dijkstra.findDirection(head,path.get(1).getPoint()).toString();
}

    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                "http://46.101.237.57/codenjoy-contest/board/player/8muqx12ddtivw7h1g6c0?code=8675965232734344936",
                new YourSolver(new RandomDice()),
                new Board());
    }

}
