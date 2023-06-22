# **Virus Spreading Simulator**

![Screenshot_16](https://github.com/chauless/Virus-simulation/assets/93679962/81e9f6e5-5212-444d-88b8-7dc71384df5e)

The Virus Spreading Simulator is an innovative project that uses Java to simulate the spread of a virus within a group of people. Looking at how the pandemic was going, understanding how viruses spread has never been more critical, and my simulator is an indispensable tool for educating people on the impact of viruses and how to prevent their spread.

The Virus Spreading Simulator uses JavaFX framework to create a simulator that shows how virus can spread within a group of people. The program will allow user to change simulation parameters, such as changing the speed at which people move or the speed of spreading the disease. The purpose of the simulation will be to show people how fast a virus can be spread, depending on the parameters you set.

Investing in the Virus Spreading Simulator is investing in the future of public health. By supporting the project, you will be contributing to a crucial effort to create a healthier and safer future for everyone.
</details>

# Project goals

The main goal of the project is to provide a visual representation of how a virus can spread in a group of people. The simulator will allow users to change different parameters to see how they affect the spread of the virus. The simulation will also provide information about the number of infected people, the number of recovered and the number of deaths caused by the virus.

# Technologies Used

The project is developed using Java and JavaFX for the user interface. The project uses various libraries, such as JavaFX, Maven, and JUnit for unit testing. The Model-View-Controller (MVC) pattern will also be used to separate the application's concerns and enhance its structure.

# Description

The simulation will be a panel on which there will be circles that represent people. Each person have he's own position, state, speed, and flow. Simulation settings can be set both before and during the simulation. Once the simulation begins, the circles will begin to move and perform various actions and checks:

* **movement**

  every tick people try to move somewhere, but with 5% probability they can stay where they are
* **collision**

  check if an infected person stays too close to a healthy one, they can catch the disease
* **escape chance**

  if a healthy person sees a sick person in his radius, he tries to avoid contact with him and changes his direction to another
* **recovery check**

  with the parameters of the simulation you can set the time to recover from the disease, if this time has come, then the person is transferred from diseased state to the stateof an over-infected person
* **death probability**

  each infected person has a 0.3% chance of dying, if the person dies, he stops where he is and changes his status to "dead"

At the end of each tick is updated rendering of people on the panel, as well as statistics of people. At any time the simulation can be stopped and saved. It is also possible to load the configuration of the simulation.

Saving and loading of the configuration is implemented by working with Json format files, in which all information about the simulation is stored. You can save the configuration at any time, load it only during a simulation pause.

It is also possible to enable or disable the output of logs in the console. If necessary, you can save logs to a txt file.

# Simulation process

1. Application is running
2. The field is generated
3. Simulation settings are set Simulation settings loaded from a file
4. The simulation is running


    4\.1 For each person, check their current state:
   
        4.1.1 If the person is already in the State.DEAD state, skip to the next person. 
   
        4.1.2 If the person is in the State.SICK state, check if they have reached the recovery duration. 
   
        4.1.3 If the recovery duration has passed, change their state to State.RECOVERED. 
   
        4.1.4 If the person has not reached the recovery duration, continue to the next person. 
   
        4.1.5 If the person is in the State.RECOVERED state, skip to the next person.

5. After updating the states of all people, increment the timer by one tick.
6. Check if there are any sick people remaining in the simulation.

   6\.1 If there are no sick people, stop the simulation.

# Class Descriptions

[**JavaDoc link**](https://github.com/chauless/Virus-simulation/tree/main/Javadoc)

1. SimApp: this class contains the main method and initializes the application window.
2. SimController: this class models the spread of the virus within a group of people. It contains methods to adjust parameters related to the virus and simulate its spread over time.
3. Person: This class represents a person in the simulation and contains methods for drawing, moving, checking for collision.
4. Position: This class represents a position on the pane and generates a random position for a person.
5. Simulation: This class represents a simulation of a virus and instantiates the population and drawing of panes.
6. State: this enum represents a state of a person and contains methods for returning a color of a person.
7. PersonConfig: represents the configuration for a person in a simulation.
