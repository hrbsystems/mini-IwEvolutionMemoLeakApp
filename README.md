# mini-IwEvolutionMemoLeakApp project

>**This project aims reproduce the leak of memory we are facing in our app.
> As the project is big, we tried to isolate only the pace of code that 
> causes the problem always it is executed cyclically**.  
> unfortunately, this Mini-Project has a lot of code too, but we will provide over here,  
> all the info that can make the task of reproduce the problem easier
> 

## Problem description:

> The problem was described in a Stackoverflow issue (ID: #######)
> You can access the issue through this link:
> [Issue ]().
> 

## How to reproduce:
Although the project has a lot of files, you can focus only in 3 of them.  
They are:
1. com/iw/iwmobile/extensions/evolution/IwFormEvolutionNavig.java
2. com/iw/iwmobile/extentions/evolution/IwFormAddEvolution11.java  
3. com/iw/iwmobile/extentions/evolution/IwFormAddEvolution21.java  

They are the forms that are executed cyclically in this order.  
Each time the cycle finish, something like 200MB are retained.  

The actions that need to be executed is launch the first form
IwFormEvolutionNavig. Pressing the button "+" the second form (IwFormAddEvolution11) 
is launched. This second form allows us to launch the third one (IwFormAddEvolution21).
The last one we can save the medical record, finishing one cycle. The first form is shown again  
allowing the repetition of this cycle how manu times you want.

>Only what you should do is execute the project. It was coded to provide automatically
the first form. This first form offer a way to access the second
one and the second allows you to reach the third one, and so on.

The slide below illustrates this flow: 

![The Cycle flow](./docs/Cycle-Stepes.png)

As you can see, the execution of the cycle is very simply. It is only press 5 buttons in   
the sequence shown above in the picture. To get the start point all you need to do is run
the project (mini-app).








