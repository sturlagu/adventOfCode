Map<String, String> opponentMoves = new HashMap<>()
Map<String, String> myMoves = new HashMap()
Map<String, Integer> moveValues = new HashMap()

opponentMoves.put("A", "Rock")
opponentMoves.put("B", "Paper")
opponentMoves.put("C", "Scissors")

myMoves.put("X", "Rock")
myMoves.put("Y", "Paper")
myMoves.put("Z", "Scissors")

moveValues.put("Rock", 1)
moveValues.put("Paper", 2)
moveValues.put("Scissors", 3)

String input = new File('./input.txt').getText('UTF-8')
def inputArray = input.split("\r\n")

List<Integer> resultPart1 = new LinkedList<>()
List<Integer> resultPart2 = new LinkedList<>()

inputArray.each {
    String[] moves = it.split(' ')

    String opponentMove = moves[0]
    String myMove = moves[1]

    String opponentMoveDecrypted = opponentMoves.get(opponentMove)
    String myMoveDecrypted = myMoves.get(myMove)

    Integer opponentMoveValue = (Integer) moveValues.get(opponentMoveDecrypted)
    Integer myMoveValue = (Integer) moveValues.get(myMoveDecrypted)

    int pointsForOutcomeOfBattle
    if(myMoveValue == opponentMoveValue) {
        pointsForOutcomeOfBattle = 3
    }else if(myMoveDecrypted.equals("Rock") && opponentMoveDecrypted.equals("Scissors")
        || (myMoveDecrypted.equals("Paper") && opponentMoveDecrypted.equals("Rock"))
        || (myMoveDecrypted.equals("Scissors") && opponentMoveDecrypted.equals("Paper"))) {
        pointsForOutcomeOfBattle = 6
    } else {
        pointsForOutcomeOfBattle = 0
    }

    // Another way of doing it, but I don't like the readability...
/*    int outcomeOfBattle = myMoveValue - opponentMoveValue
    switch (outcomeOfBattle) {
        case { outcomeOfBattle == 0 } :
            pointsForOutcomeOfBattle = 3
            break
        case { outcomeOfBattle == 1 || outcomeOfBattle == -2 } :
            pointsForOutcomeOfBattle = 6
            break
        default :
            pointsForOutcomeOfBattle = 0
    }*/

    // Part 2
    int pointsForOutcomeOfBattlePart2 = 0
    String state = null
    switch (myMove) {
        case { myMove.equals("Z") } :
            state = "win"
            pointsForOutcomeOfBattlePart2 = 6
            if(opponentMoveDecrypted.equals("Scissors")) {
                myMoveDecrypted = "Rock"
            }else if(opponentMoveDecrypted.equals("Rock")) {
                myMoveDecrypted = "Paper"
            } else if(opponentMoveDecrypted.equals("Paper")) {
                myMoveDecrypted = "Scissors"
            }
            break
        case { myMove.equals("Y") } :
            state = "draw"
            pointsForOutcomeOfBattlePart2 = 3
            myMoveDecrypted = opponentMoveDecrypted
            break
        case { myMove.equals("X") } :
            state = "loss"
            pointsForOutcomeOfBattlePart2 = 0
            if(opponentMoveDecrypted.equals("Scissors")) {
                myMoveDecrypted = "Paper"
            }else if(opponentMoveDecrypted.equals("Rock")) {
                myMoveDecrypted = "Scissors"
            } else if(opponentMoveDecrypted.equals("Paper")) {
                myMoveDecrypted = "Rock"
            }
            break
    }

    //println("MyMove " + myMove + " " + myMoveDecrypted + " " + myMoveValue + ", OppMove " + opponentMove + " " + opponentMoveDecrypted + " " + opponentMoveValue + " -> battle points " + pointsForOutcomeOfBattle + " -> sum ${pointsForOutcomeOfBattle + myMoveValue}")
    resultPart1.add(pointsForOutcomeOfBattle + myMoveValue)

    Integer myMoveValue2 = (Integer) moveValues.get(myMoveDecrypted)
    //println("OppMove " + opponentMove + " " + opponentMoveDecrypted + " " + opponentMoveValue + " -> battle points " + pointsForOutcomeOfBattlePart2 + " (${state}) -> myMove " + myMove + " " + myMoveDecrypted + " " + myMoveValue2 + " -> sum ${pointsForOutcomeOfBattlePart2 + myMoveValue}")
    resultPart2.add(pointsForOutcomeOfBattlePart2 + myMoveValue2)
}

println("Result part 1: " + resultPart1.sum())
println("Result part 2: " + resultPart2.sum())
