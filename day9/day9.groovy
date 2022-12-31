String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

String[][] motionMap = new String[252][252];
String[][] motionMapPart2 = new String[252][252];

Knot head = new Knot(0)
Knot tail = new Knot(-1)

// Part 2
ArrayList<Knot> knots = new ArrayList<>()
(1..9).each {
    knots.add(new Knot(it))
}

inputArray.each { String line ->
    String[] command = line.split(' ')
    String direction = command[0]
    int numberOfMoves = Integer.parseInt(command[1])
    for (int i = 1; i <= numberOfMoves; i++) {
        // Part 1
        moveHead(direction, head)
        moveTail(tail, head, motionMap)

        // Part 2
        knots.eachWithIndex { Knot knot, Integer index ->
            if(index == 0) {
                moveTail(knot, head, motionMapPart2)
            }else {
                moveTail(knot, knots.get(index - 1), motionMapPart2)
            }
        }
    }
}

int positionsVisitedCounter = 0
(0..251).each { int i ->
    (0..251).each { int y ->
        if(motionMap[i][y] == "#") {
            positionsVisitedCounter++
        }
    }
}

int positionsVisitedCounter2 = 0
(0..251).each { int i ->
    (0..251).each { int y ->
        if(motionMapPart2[i][y] == "#") {
            positionsVisitedCounter2++
        }
    }
}

println("Task one - number of unique positions visited: ${positionsVisitedCounter}")
println("Task two - number of unique positions visited: ${positionsVisitedCounter2}")

void moveHead(String direction, Knot head) {
    switch (direction) {
        case 'L':
            head.x--
            break
        case 'U':
            head.y++
            break
        case 'R':
            head.x++
            break
        case 'D':
            head.y--
            break
    }
}
void moveTail(Knot knot, Knot knotInFront, String[][] motionMap) {
    // Diagonal
    // Top right
    if((knotInFront.x - knot.x) == 2 && (knotInFront.y - knot.y) == 1
            || (knotInFront.x - knot.x) == 1 && (knotInFront.y - knot.y) == 2
    ) {
        if(knotInFront.x > knot.x) {
            knot.x++
        }else {
            knot.x--
        }
        if(knotInFront.y > knot.y) {
            knot.y++
        }else {
            knot.y--
        }
        // Bottom left
    }else if((knotInFront.x - knot.x) == -2 && (knotInFront.y - knot.y) == -1
            || (knotInFront.x - knot.x) == -1 && (knotInFront.y - knot.y) == -2
    ) {
        if(knotInFront.x > knot.x) {
            knot.x++
        }else {
            knot.x--
        }
        if(knotInFront.y > knot.y) {
            knot.y++
        }else {
            knot.y--
        }
        // Top left
    }else if((knotInFront.x - knot.x) == -2 && (knotInFront.y - knot.y) == 1
            || (knotInFront.x - knot.x) == -1 && (knotInFront.y - knot.y) == 2
    ){
        if(knotInFront.x > knot.x) {
            knot.x++
        }else {
            knot.x--
        }
        if(knotInFront.y > knot.y) {
            knot.y++
        }else {
            knot.y--
        }
        // Bottom right
    }else if((knotInFront.x - knot.x) == 2 && (knotInFront.y - knot.y) == -1
            || (knotInFront.x - knot.x) == 1 && (knotInFront.y - knot.y) == -2
    ){
        if(knotInFront.x > knot.x) {
            knot.x++
        }else {
            knot.x--
        }
        if(knotInFront.y > knot.y) {
            knot.y++
        }else {
            knot.y--
        }
    }else {
        // X-direction
        if((knotInFront.x - knot.x == 2)) {
            knot.x = knotInFront.x - 1
        }
        if((knotInFront.x - knot.x == -2)) {
            knot.x = knotInFront.x + 1
        }
        // Y-direction
        if((knotInFront.y - knot.y == 2)) {
            knot.y = knotInFront.y - 1
        }
        if((knotInFront.y - knot.y == -2)) {
            knot.y = knotInFront.y + 1
        }
    }
    // Part 1
    if(knot.index == -1){
        motionMap[convertNegativeIndex(knot.y)][convertNegativeIndex(knot.x)] = "#"
    }
    // Part 2
    if(knot.index == 9){
        motionMap[convertNegativeIndex(knot.y)][convertNegativeIndex(knot.x)] = "#"
    }
}

int convertNegativeIndex(int index) {
    if(index < 0) {
        index = -index * 2 -1
    }else {
        index = index * 2
    }
    return index
}

class Knot {
    int index
    int x
    int y

    Knot(int index) {
        this.index = index
        this.x = 0
        this.y = 0
    }
}