String input = new File('./input.txt').getText('UTF-8')
def rucksackArray = input.split("\r\n")
def itemList = new ArrayList()
def priority = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

rucksackArray.each { String rucksack ->
    def compartmentOne = rucksack.substring(0, (int)(rucksack.length() / 2))
    def compartmentTwo = rucksack.substring((int)(rucksack.length() / 2), rucksack.length())
    compartmentOne.split('').any { String character ->
        if(compartmentTwo.contains(character)) {
            itemList.add(character)
            return true
        }
    }
}

def priorityValuesOfItems = new ArrayList()
itemList.each { character ->
    priorityValuesOfItems.add(priority.indexOf(character.toString()) + 1 )
}

println("Part 1 sum: " + priorityValuesOfItems.sum())

def groupList = new ArrayList()
def group = []
rucksackArray.eachWithIndex { String rucksack, Integer index ->
    index += 1
    group.add(rucksack)
    if(index % 3 == 0){
        groupList.add(group)
        group = []
    }
}

def badgeList = new ArrayList()
groupList.each { team ->
    String memberOne = team[0]
    String memberTwo = team[1]
    String memberThree = team[2]

    memberOne.split('').any {
        if(memberTwo.contains(it) && memberThree.contains(it)) {
            badgeList.add(it)
            return true
        }
    }
}

def priorityValuesOfBadges = new ArrayList()
badgeList.each { badge ->
    priorityValuesOfBadges.add(priority.indexOf(badge.toString()) + 1 )
}

println("Part 2 sum: " + priorityValuesOfBadges.sum())
