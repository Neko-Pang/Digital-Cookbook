# Digital-Cookbook
MacroHard
----------------------------------------------------------
1. Introduction
It is a digital cookbook software, providing functions that users can search for recipes and add their own recipes.

2. Operating environment
-Windows Operation System (32/64 bit)
-JDK1.8.0 installation (or newer version)
-Eclipse installation
-MySQL workbench 8.0 installation (or newer version)

3. Installation method
(1) Activate the eclipse and  import the java project file.
(2) Open the package"CookBook", open the "DataBaseController.java", change the database password to your own, and save. (line 35)
    Example:
     Username: root
     Password: 123456
(line 35) private String dbPass = "xiaoxia12"; ------> private String dbPass = "123456";
(3) Activate the MySQL, login with username "root", create a new database named "cookbookdatabase", and run the file "initialize".
(5) Finally, go back to eclipse and run the "Main.java" in the package "com.UI.view".

 4.Change log
 2018/7/4 ver1.0.0 First Version

------------------------------------------------------------
Thanks for using our software!
