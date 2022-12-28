String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

int x = 1
int cycle = 0
signalStrengths = new ArrayList<>()

// Part 2
Map<Integer, String[]> CRT = new HashMap<>()
String dots = '........................................'
(1..6).collect { Integer index ->
    CRT.put(index, dots.split(''))
}

inputArray.each { String command ->
    if(command == "noop") {
        cycle++
        checkSignalStrengthCycle(cycle, x)
        setPixelOnCycle(cycle, x, CRT)
    }else {
        cycle++
        setPixelOnCycle(cycle, x, CRT)
        checkSignalStrengthCycle(cycle, x)
        cycle++
        setPixelOnCycle(cycle, x, CRT)
        checkSignalStrengthCycle(cycle, x)
        x += Integer.parseInt(command.split(' ')[1])
    }
}

println("Task one - Sum of signals: " + signalStrengths.sum())
println("--------------- Task two ---------------")
(1..6).collect { Integer index ->
    CRT.get(index).each {
        print(it)
    }
    println()
}

void checkSignalStrengthCycle(int cycle, int x) {
    switch (cycle) {
        case 20:
        case 60:
        case 100:
        case 140:
        case 180:
        case 220:
            signalStrengths.add(cycle * x)
    }
}

void setPixelOnCycle(int cycle, int x, Map<Integer, String[]> CRT) {
    String lit = '#'
    String dark = '.'
    int index = 0
    // Subtracting because we need to match the array index
    cycle--
    x--
    switch (cycle) {
        case 0..39:
            index = 1
            break
        case 40..79:
            index = 2
            cycle = cycle - 40
            break
        case 80..119:
            index = 3
            cycle = cycle - 80
            break
        case 120..159:
            index = 4
            cycle = cycle - 120
            break
        case 160..199:
            index = 5
            cycle = cycle - 160
            break
        case 200..239:
            index = 6
            cycle = cycle - 200
            break
    }
    if((cycle == x) || (cycle == x + 1) || ( cycle == x + 2)) {
        CRT.get(index)[cycle] = lit
    }else {
        CRT.get(index)[cycle] = dark
    }
}