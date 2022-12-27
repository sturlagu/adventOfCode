String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

Directory filesystem = null

inputArray.each { String line ->
    if(line.startsWith('$')) {
        String command = line.substring(2, line.length())
        if(command == "cd /") {
            filesystem = new Directory('/', null)
        }else if(command.startsWith('cd ')){
            filesystem.changeDirectory(command.split(' ')[1])
        }else if(command == "ls") {}
    }else {
        if(line.startsWith('dir')) {
            String directoryName = line.split(' ')[1]
            filesystem.addDirectory(new Directory(directoryName, filesystem.current))
        }else {
            String[] command = line.split(' ')
            int fileSize = Integer.parseInt(command[0])
            String fileName = command[1]
            filesystem.addFile(new FileX(fileName, fileSize))
        }
    }
}

// Task 1
// Groovy magic - If you don't define the variable it will be a global variable (scoped to the script)
sumOfSizesThatMeetCriteria = 0
criteriaForTaskOne = 100000
getDirectorySizes(filesystem)
println("Sum of category sizes that meet criteria for task one: " + sumOfSizesThatMeetCriteria)

println()
//Task 2
filesystem.directorySize = getTotalUsedSpace(filesystem)
println("Total used space: " + filesystem.directorySize)
criteriaForTaskTwo = getSpaceNeededForUpdate(filesystem)
println("We need size " + criteriaForTaskTwo + " for task two" )
listOfSizesThatMeetCriteriaForTaskTwo = new ArrayList<>()
getDeletionSize(filesystem)
println("We need to delete " + listOfSizesThatMeetCriteriaForTaskTwo.sort().get(0) + " of space for the update")

void getDeletionSize(Directory directory) {
    directory.directories.each{
        if (it.directories.size() > 0) {
            getDeletionSize(it)
        }
        if(it.directorySize >= criteriaForTaskTwo) {
            listOfSizesThatMeetCriteriaForTaskTwo.add(it.directorySize)
        }
    }
}

int getSpaceNeededForUpdate(Directory filesystem) {
    int availableSpaceNeeded = 30000000
    int fileSystemTotalCapacity = 70000000
    int spaceAvailable = fileSystemTotalCapacity - filesystem.directorySize
    int spaceNeeded = availableSpaceNeeded - spaceAvailable
    return spaceNeeded
}

int getTotalUsedSpace(Directory directory) {
    accSize = 0
    directory.directories.each {
        accSize += it.directorySize
    }
    accSize += directory.files.collect { it.size }.sum() ?: 0
    return accSize
}

void getDirectorySizes(Directory directory) {
    directory.directories.each{
        if (it.directories.size() > 0) {
            getDirectorySizes(it)
        }
        it.directorySize += getSubCategoriesSizes(it)
        it.directorySize += it.files.collect { it.size }.sum() ?: 0
        if(it.directorySize <= criteriaForTaskOne) {
            sumOfSizesThatMeetCriteria += it.directorySize
            //println("(${ it.name }) ${ it.directorySize }")
        }
    }
}

int getSubCategoriesSizes(Directory directory) {
    int accSize = 0
    directory.directories.each {
        if (it.directories.size() > 0) {
            getSubCategoriesSizes(it)
        }
        accSize += it.directorySize
    }
    return accSize
}

class Directory {
    String name
    List<FileX> files
    List<Directory> directories
    String type
    Directory previousPointer
    Directory current
    int directorySize

    Directory(String name, Directory previous) {
        this.name = name
        this.type = "dir"
        this.directories = new ArrayList<Directory>()
        this.files = new ArrayList<FileX>()
        this.previousPointer = previous
        this.current = this
        this.directorySize = 0
    }

    void addDirectory(Directory directory) {
        this.current.directories.add(directory)
    }

    void changeDirectory(String directoryName) {
        if(directoryName.equals('..')){
            this.current = this.current.previousPointer
        }else {
            Directory changeTo = this.current.directories.find {it.name == directoryName }
            if(changeTo != null){
                this.current = changeTo
            }
        }
    }

    void addFile(FileX file) {
        this.current.files.add(file)
    }
}

// To avoid conflict with package File I've called it FileX
class FileX {
    String name
    int size
    String type

    FileX(String name, int size) {
        this.name = name
        this.size = size
        this.type = "file"
    }
}