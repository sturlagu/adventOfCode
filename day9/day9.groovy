String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

String[][] motionMap = new String[252][252];

headPositionX = 0
headPositionY = 0

tailPositionX = 0
tailPositionY = 0

// Part 2
ArrayList<Knot> knots = new ArrayList<>()
(1..9).each {
    knots.add(new Knot(it))
}
Knot head = new Knot(0)

inputArray.each { String line ->
    String[] command = line.split(' ')
    String direction = command[0]
    int numberOfMoves = Integer.parseInt(command[1])
    for (int i = 1; i <= numberOfMoves; i++) {
        moveHead(direction)
        moveTail(motionMap)

        moveHeadPart2(direction, head)
        knots.eachWithIndex { Knot knot, Integer index ->
            if(index == 0) {
                moveTailPart2(knot, head, motionMap)
            }else {
                moveTailPart2(knot, knots.get(index - 1), motionMap)
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

println("Task one - number of unique positions visited: ${positionsVisitedCounter}")

// Task 2 - Guessed 10596, too high

void moveHead(String direction) {
    switch (direction) {
        case 'L':
            headPositionX--
            break
        case 'U':
            headPositionY++
            break
        case 'R':
            headPositionX++
            break
        case 'D':
            headPositionY--
            break
    }
}

void moveTail(String[][] motionMap) {
    // Diagonal
    // Top right
    if((headPositionX - tailPositionX) == 2 && (headPositionY - tailPositionY) == 1
        || (headPositionX - tailPositionX) == 1 && (headPositionY - tailPositionY) == 2
    ) {
        if(headPositionX > tailPositionX) {
            tailPositionX++
        }else {
            tailPositionX--
        }
        if(headPositionY > tailPositionY) {
            tailPositionY++
        }else {
            tailPositionY--
        }
    // Bottom left
    }else if((headPositionX - tailPositionX) == -2 && (headPositionY - tailPositionY) == -1
            || (headPositionX - tailPositionX) == -1 && (headPositionY - tailPositionY) == -2
    ) {
        if(headPositionX > tailPositionX) {
            tailPositionX++
        }else {
            tailPositionX--
        }
        if(headPositionY > tailPositionY) {
            tailPositionY++
        }else {
            tailPositionY--
        }
    // Top left
    }else if((headPositionX - tailPositionX) == -2 && (headPositionY - tailPositionY) == 1
            || (headPositionX - tailPositionX) == -1 && (headPositionY - tailPositionY) == 2
    ){
        if(headPositionX > tailPositionX) {
            tailPositionX++
        }else {
            tailPositionX--
        }
        if(headPositionY > tailPositionY) {
            tailPositionY++
        }else {
            tailPositionY--
        }
    // Bottom right
    }else if((headPositionX - tailPositionX) == 2 && (headPositionY - tailPositionY) == -1
            || (headPositionX - tailPositionX) == 1 && (headPositionY - tailPositionY) == -2
    ){
        if(headPositionX > tailPositionX) {
            tailPositionX++
        }else {
            tailPositionX--
        }
        if(headPositionY > tailPositionY) {
            tailPositionY++
        }else {
            tailPositionY--
        }
    }else {
        // X-direction
        if((headPositionX - tailPositionX == 2)) {
            tailPositionX = headPositionX - 1
        }
        if((headPositionX - tailPositionX == -2)) {
            tailPositionX = headPositionX + 1
        }
        // Y-direction
        if((headPositionY - tailPositionY == 2)) {
            tailPositionY = headPositionY - 1
        }
        if((headPositionY - tailPositionY == -2)) {
            tailPositionY = headPositionY + 1
        }
    }
    //motionMap[convertNegativeIndex(tailPositionY)][convertNegativeIndex(tailPositionX)] = "#"
}

int convertNegativeIndex(int index) {
    if (index < 0) {
        index = -index * 2 - 1
    }else {
        index = index * 2
    }
    return index
}

void moveHeadPart2(String direction, Knot head) {
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
void moveTailPart2(Knot knot, Knot knotInFront, String[][] motionMap) {
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
    if(knot.index == 9){
        motionMap[convertNegativeIndex(knot.y)][convertNegativeIndex(knot.x)] = "#"
    }
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