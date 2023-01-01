String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

String operation
String factorString
Integer divisibleBy
Map<Boolean, Integer> testOutcome = new HashMap<>()
Integer currentMonkey = 0
Map<Integer, Integer> inspectionMap = new HashMap<>()
Map<Integer, ArrayList<BigInteger>> monkeys = new HashMap<>()
ArrayList<Integer> monkeysDivisors = new ArrayList<>()
ArrayList<BigInteger> itemsForTrueOutcome = new ArrayList<>()
ArrayList<BigInteger> originalItems = new ArrayList<>()
ArrayList<BigInteger> itemsForFalseOutcome = new ArrayList<>()
boolean isDone = false

(0..7).each { Integer index ->
    monkeys.put(index, new ArrayList<BigInteger>())
    inspectionMap.put(index, 0)
}

inputArray.eachWithIndex { String line, Integer index ->
    switch (line.trim()) {
        case { line.trim().toLowerCase().startsWith("monkey") }:
            String number = line.split(' ')[1]
            currentMonkey = Integer.parseInt(number.split('')[0])
            break
        case { line.trim().toLowerCase().startsWith("starting items") }:
            String items = line.split(': ')[1]
            if (items.contains(',')) {
                items.split(', ').collect { String item ->
                    monkeys.get(currentMonkey).add(new BigInteger(item))
                }
            } else {
                monkeys.get(currentMonkey).add(new BigInteger(items))
            }
            break
        case { line.trim().toLowerCase().startsWith('test') }:
            divisibleBy = Integer.parseInt(line.split('by ')[1])
            monkeysDivisors.add(divisibleBy)
            break
    }
}

(1..10000).each { Integer loopCount ->
    println("Loop " + loopCount + " of 10 000")
    inputArray.eachWithIndex { String line, Integer index ->
        switch (line.trim()) {
            case { line.trim().toLowerCase().startsWith("monkey") }:
                String number = line.split(' ')[1]
                currentMonkey = Integer.parseInt(number.split('')[0])
                break
            case { line.trim().toLowerCase().startsWith("operation") }:
                String operations = line.split('= old ')[1]
                operation = operations.split(' ')[0]
                factorString = operations.split(' ')[1]
                break
            case { line.trim().toLowerCase().startsWith('test') }:
                divisibleBy = Integer.parseInt(line.split('by ')[1])
                break
            case { line.trim().toLowerCase().startsWith('if') }:
                String toMonkey = line.split('monkey ')[1]
                if(line.trim().toLowerCase().startsWith('if true')) {
                    testOutcome.put(true, Integer.parseInt(toMonkey))
                }else {
                    testOutcome.put(false, Integer.parseInt(toMonkey))
                    isDone = true
                }
                break
        }


        if(isDone) {
            Integer toMonkeyTrue = 0
            Integer toMonkeyFalse = 0
            Integer accMod = monkeysDivisors.inject { Integer sum, Integer value -> sum * value }
            monkeys.get(currentMonkey).each { BigInteger item ->
                Integer inspectedItems = inspectionMap.get(currentMonkey)
                inspectedItems++
                inspectionMap.put(currentMonkey, inspectedItems)
                BigInteger worryLevel = 0
                BigInteger factor = 0
                originalItems.add(item)

                if(factorString == "old") {
                    factor = item
                }else {
                    factor = new BigInteger(factorString)
                }

                if(operation == '+') {
                    worryLevel = item.add(factor)
                }else if(operation == '*') {
                    worryLevel = item * factor
                }

                // Part 1
                //worryLevel = new BigInteger(new BigDecimal(worryLevel).divideToIntegralValue(new BigDecimal(3)).toString())
                // Part 2
                worryLevel = worryLevel % accMod

                if(worryLevel % divisibleBy == 0) {
                    toMonkeyTrue = testOutcome.get(true)
                    itemsForTrueOutcome.add(worryLevel)
                }else {
                    toMonkeyFalse = testOutcome.get(false)
                    itemsForFalseOutcome.add(worryLevel)
                }
            }

            monkeys.get(currentMonkey).removeAll(originalItems)
            monkeys.get(toMonkeyTrue).addAll(monkeys.get(toMonkeyTrue).size(), itemsForTrueOutcome)
            monkeys.get(toMonkeyFalse).addAll(monkeys.get(toMonkeyFalse).size(), itemsForFalseOutcome)

            itemsForTrueOutcome = new ArrayList<>()
            itemsForFalseOutcome = new ArrayList<>()
            originalItems = new ArrayList<>()
            isDone = false
        }
    }
    //println(monkeys.toString())
}

(0..7).each { Integer index ->
    println("Monkey ${index} with ${inspectionMap.get(index)} inspected items")
}