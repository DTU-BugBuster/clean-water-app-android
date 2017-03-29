

# M9 History Graph, Persistence and Contracts
## Design
Each team member should pick one method (doesn't have to be one you wrote, but should be different from one chosen by other team members). For this method write a complete contract as we discussed in class: signature, pre and post conditions, invariants (if any) and framing conditions.

## Implementation
We will finally implement persistence. Your application should now be able to load and save the data so that you don't have to re-enter it each run. You may choose whatever method of persistence you wish to use.

We will also display the water quality history graph. The system will prompt a manager for the setup information (like year and location) and then will display the graph results.

## Requirements
Provide a way to select saving and loading of system data
Persist (save) the data (serialization, custom text, etc.)
Restore (load) previously saved data
Manager can see option for showing the historical purity report
System prompts the manager for setup information for the report
System displays the XY graph with the information
# Grading Criteria
    Contract .....................25
    Previous functions still work .... 10
    Application can save data .... 15
    Application can load data .... 15
    Managers can request a history graph (user and worker cannot) … 05
    Manager can enter setup information for graph .... 10
    History Graph Displayed correctly .......... 10
    Design review and javadocs … 10
## Submissions
Electronic submissions should be submitted in .PDF format.
