# **Recipe Browser**

## An application to add meals and browse through your catalogue

This application will be a program that focuses on preparing meals and giving recommendations to users
on what food to eat. Users would be able to add meals to their catalogue to plan out meal preps from what ingredients they have available.
The function to add certain recipes along with their ingredients would be a part of the program as well.
This application is aimed at people who need some sort of way to keep track of their meal prep as it is easy to get distracted
and overly spend money outside the prepared schedules (unplanned take-outs etc.).

This project is of interest to me as a student who lives alone and renting a place near UBC, it is vital that
I keep self-control and prepare meals beforehand to ensure that I do not overspend on my monthly budget. I have
had many moments where I would end up going off course because I forgot what my original plan. I believe this 
was mainly due to the lack of a method to keep track of the schedule. I believe this project is beneficial for me in helping 
me plan my monthly budget and improving my coding skills.

## User Stories

- As a user, I want to add meals in my catalogue
- As a user, I want to remove a meal from my catalogue
- As a user, I want to view the information of a meal including its ingredients and amount eaten.
- As a user, I want to add ingredients for my meals.
- As a user, I want to save my catalogue to a file. 
- As a user, I want to load my catalogue from a file.

# Instructions for Grader

- You can generate the first required event related to adding Meals to Catalogue by pressing the "Add a Meal" button in the main menu.
- You can generate the second required event related adding Meals to Catalogue by typing "Add a meal" , "Add", or "A" in the text field and then hitting submit on
the main menu. (Case insensitive)
- You can locate my visual component by opening up the program. (May have to reload the project. Sometimes doesn't show image on the first run until the program is restarted.)
- You can save the state of my application by pressing the save button or typing "Save catalogue", "save", or "s" in the textfield in the menu and pressing submit.
- You can reload the state of my application by pressing the load button or typing "Load save", "load", or "l" in the textfield in the menu and pressing submit.

** The meals and ingredients are case sensitive. **

# Phase 4: Task 2

Event Log:
Tue Nov 29 19:16:14 PST 2022
Catalogue name has been changed to: My amazing catalogue!

Tue Nov 29 19:16:39 PST 2022
Ramen has been added to: My amazing catalogue!

Tue Nov 29 19:16:42 PST 2022
Chasu has been added as an ingredient to: Ramen

Tue Nov 29 19:16:45 PST 2022
Noodles has been added as an ingredient to: Ramen

Tue Nov 29 19:16:50 PST 2022
Fries has been added to: My amazing catalogue!

Tue Nov 29 19:16:55 PST 2022
Fries has been removed from: Fries


Process finished with exit code 0

# Phase 4: Task 3

Possible refactoring to improve the project if I had more time:
- Change the type of the catalogue collection to a set to remove having two array lists, one for holding the meals and another for holding indexes. 
- UI class could be broken up to handle more individual parts, currently everything related to GUI is attached to one class.
- Clean up and have more methods to split functionality to remove repetitive code in the GUI class. 
- Have it so that the response for the meals and ingredients are case insensitive for better user experience. 