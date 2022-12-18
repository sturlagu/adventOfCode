String input = new File('./input.txt').getText('UTF-8')
String[] movesArray = input.split('\r\n')

Map<Integer, List<String>> creates = new HashMap<>()

creates.put(1, ['R', 'W', 'F', 'H', 'T', 'S'])
creates.put(2, ['W', 'Q', 'D', 'G', 'S'])
creates.put(3, ['W', 'T', 'B'])
creates.put(4, ['J', 'Z', 'Q', 'N', 'T', 'W', 'R', 'D'])
creates.put(5, ['Z', 'T', 'V', 'L', 'G', 'H', 'B', 'F'])
creates.put(6, ['G', 'S', 'B', 'V', 'C', 'T', 'P', 'L'])
creates.put(7, ['P', 'G', 'W', 'T', 'R', 'B', 'Z'])
creates.put(8, ['R', 'J', 'C', 'T', 'M', 'G', 'N'])
creates.put(9, ['W', 'B', 'G', 'L'])

movesArray.eachWithIndex { String move, Integer lineNumber ->
    def moveArray = move.split(' ')
    def quantity = Integer.parseInt(moveArray[1])
    def from = Integer.parseInt(moveArray[3])
    def to = Integer.parseInt(moveArray[5])

    while(quantity != 0){
        String create = creates.get(from).pop()
        creates.get(to).add(0, create)
        //println("(${lineNumber + 1}) Moving ${create} from ${from} to ${to}")
        quantity--
    }
    //println("line: ${lineNumber + 1} " + creates)
}
print("Part 1 - create order: ")
for (int i = 1; i <= 9; i++){
    List<String> currentCreates = creates.get(i)
    if(currentCreates.size() > 0) {
        print(currentCreates.get(0))
    }
}

Map<Integer, List<String>> createsPartTwo = new HashMap<>()

createsPartTwo.put(1, ['R', 'W', 'F', 'H', 'T', 'S'])
createsPartTwo.put(2, ['W', 'Q', 'D', 'G', 'S'])
createsPartTwo.put(3, ['W', 'T', 'B'])
createsPartTwo.put(4, ['J', 'Z', 'Q', 'N', 'T', 'W', 'R', 'D'])
createsPartTwo.put(5, ['Z', 'T', 'V', 'L', 'G', 'H', 'B', 'F'])
createsPartTwo.put(6, ['G', 'S', 'B', 'V', 'C', 'T', 'P', 'L'])
createsPartTwo.put(7, ['P', 'G', 'W', 'T', 'R', 'B', 'Z'])
createsPartTwo.put(8, ['R', 'J', 'C', 'T', 'M', 'G', 'N'])
createsPartTwo.put(9, ['W', 'B', 'G', 'L'])

movesArray.eachWithIndex { String move, Integer lineNumber ->
    def moveArray = move.split(' ')
    def quantity = Integer.parseInt(moveArray[1])
    def from = Integer.parseInt(moveArray[3])
    def to = Integer.parseInt(moveArray[5])

    ArrayList<String> createsToMove = new ArrayList<String>()
    while(quantity != 0){
        createsToMove.add(createsPartTwo.get(from).pop())
        quantity--
    }
    createsPartTwo.get(to).addAll(0, createsToMove)
}

println()
print("Part 2 - create order: ")
for (int i = 1; i <= 9; i++){
    List<String> currentCreates = createsPartTwo.get(i)
    if(currentCreates.size() > 0) {
        print(currentCreates.get(0))
    }
}

