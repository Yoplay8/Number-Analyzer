# Abstract View
This program will take in a set of numbers. Multiple numbers can be put on the same line separated with spaces. The user can then select any row of numbers that they entered and compare them to see the frequency of each number. All numbers are saved to a text file.

# Full View
This program will take in a set of numbers that can either have one or multiple numbers on each line separated with spaces. As of right now entering in more than one space between numbers will cause an error. Anything that's not a number is not allowed to be entered. Decimals are not allowed which is fine because this program didn't need to include decimals. When the user enters in a number and the input is valid the new number will be saved to a file and added to the JList.

The user will have the option to compare any numbers from the list that are highlighted. Nothing will happen if nothing is highlighted. Once compared another list will hold the frequency of each number and another list will hold all the rows that were selected. The list that holds the selected rows will additionally have numbers that may be highlighted green if the given number appeared in the line above.

If the user enters in a number and wants to edit a certain row they can click on that row to edit. All edits are validated before updating the JList and file. 

In addition to highlighting rows the user can also delete the rows that are highlighted. Which will update the JList and file.

# Future Ideas
I'm thinking I should have used regular expressions when separating the numbers with spaces because I noticed that if the user accidentally enters in more than one space between numbers an error will occur.

I might add a way so the user can hold multiple sets of data. As of right now only one set of data is allowed. So I'll add in a drop down menu or something so the user can switch between data sets.

I might add a feature so the user can type in a certain number and in the frequency list that number will be highlighted. I could probably also implement a way so a range of numbers could be looked up and highlighted.

I don't really have a solution or idea yet but I noticed the user wants to open up multiple instances of the program. This can cause errors and confusion. I don't want the user to have to go through that so one I will either have the program some how refresh its self. I don't really know how that will work. Or two I'll find a way to prevent the user from having multiple instances. I don't know how this will work nor do I really think this is essential. Then again this is kind of like a random throw up in the air. I need to investigate this situation more to be able to come to a finer conclusion.


# My Notes
I might consider taking out the green highlights as this doesn't seem to be as useful as I thought.
As of right now I'm not actually sure about this decision. Ill keep this in unless I find a stronger reason to take this out.

The right list seems to be confusing to the user as they might not completely understand why the
numbers are being sent from the left to right. Possibly with the middle list also add headers in each list.

Get rid of the border lines in the middle list.

Organize arguments so they aren't all placed in sporadically.

I've noticed that the TF_Hold_Nums clears the input in the box not matter what. Maybe change that
because the user might get annoyed if the input keeps clearing and they accidentally clicked out of the
box. Same with the TF_Range fields maybe do something about that. This is not a critical fix but
something to consider.

I never used XML but I'm starting to realize that XML has a huge advantage in that its easier to change file data.
