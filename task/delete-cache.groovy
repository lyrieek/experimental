def delDir(){

    def dir = "C:\\Users\\user\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache"

    new File(dir).deleteDir()

    sleep(2000)

    delDir()
}

delDir()