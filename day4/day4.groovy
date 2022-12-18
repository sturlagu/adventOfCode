String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

int overlapCountPartOne = 0
int overlapCountPartTwo = 0
inputArray.each { String line ->
    String[] pair = line.split(',')
    String memberOne = pair[0]
    String memberTwo = pair[1]

    String[] memberOneValues = memberOne.split('-')
    Integer memberOneLowerValue = Integer.valueOf(memberOneValues[0])
    Integer memberOneUpperValue = Integer.valueOf(memberOneValues[1])

    String[] memberTwoValues = memberTwo.split('-')
    Integer memberTwoLowerValue = Integer.valueOf(memberTwoValues[0])
    Integer memberTwoUpperValue = Integer.valueOf(memberTwoValues[1])

    if((memberOneLowerValue <= memberTwoLowerValue && memberOneUpperValue >= memberTwoUpperValue)
    || (memberTwoLowerValue <= memberOneLowerValue && memberTwoUpperValue >= memberOneUpperValue)){
        //println("one: ${memberOne} , two: ${memberTwo}")
        overlapCountPartOne++
    }

    if((memberOneLowerValue <= memberTwoLowerValue && memberTwoLowerValue <= memberOneUpperValue)
        || (memberOneLowerValue <= memberTwoUpperValue && memberTwoUpperValue <= memberOneUpperValue)
        || (memberTwoLowerValue <= memberOneLowerValue && memberOneLowerValue <= memberTwoUpperValue)
        || (memberTwoLowerValue <= memberOneUpperValue && memberOneUpperValue <= memberTwoUpperValue)){
        println("one: ${memberOne} , two: ${memberTwo}")
        overlapCountPartTwo++
    }
}
println("Part 1 - assignments overlapping: " + overlapCountPartOne)
println("Part 2 - assignments overlapping: " + overlapCountPartTwo)

