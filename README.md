# Uni data structures and algorithms
As part of my uni degree I was tasked with developing a program that solves a data driven problem that utilises multiple data structures and algorithms.

## How to run
This has been built using Java 11. To be able to run the jar you can use the following command:

NOTE: The paths to the products folder and costs.txt file are relative to where the command is run from,
unless you provide an absolute path.
```
java -jar /path/to/application.jar products costs.txt
```

There has been some example data provided inside the [data folder](data). If you `cd` into this directory you can then run the following command:
```
java -jar ../application.jar products costs.txt
```

## Data structure
### Products
The first argument of the program is a directory to the product files.
The application will use only `.txt` files and use the file name as the product name.

The structure of these products is that every indentation tells the application that this item/material is a child of the one before it, for example if we had a file called `my-product.txt` and it contained the following.
```
item1
    item2
        item3
    item4
item5
    item6
```

This would mean our product would be called "my-product" it would have two children: item1 and item5. item1 would also have two children: item2 and item4. item3 is the child of item2 and item6 is the child of item5.

### Costs
The second argument of the program is a file containing the costs for items, that should match (case-sensitive) the names found in the products file.
Only the items at the end of tree branches need to have costs.
The structure of this file is the name of the item, followed by an equals sign, then a number.
There can be any number of spaces between these, except for in the item name where it should match that which is found in the products file.

An example using the product structure above could be:
```
item3 = 1
item4 = 2
item6 = 3
```

Only these three items need to be defined since they are the only items found as the last element in a branch.
