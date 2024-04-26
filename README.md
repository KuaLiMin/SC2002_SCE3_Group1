# SC2002_SCE3_Group1

## How to Use
### Javadocs
Access javadocs under ```SC2002_SCE3_Group1/docs/index.html```.
### Foms
To start the Foms program, run ```SC2002_SCE3_Group1/foms/main/Foms.java```.

## Data Persistence
By default, data from files under ```SC2002_SCE3_Group1/foms/originalfiles``` is sued to populate. Upon proper termination of a session of FOMS, serialised save files are created under ```SC2002_SCE3_Group1/foms/persistentdata```.

At initialisation, FOMS will look for valid save files, resorting to the original files if a complete set of save files are not found.

If a user would like to input their own file, they should:
1. Ensure there are no files under ```SC2002_SCE3_Group1/foms/persistentdata```
2. Replace the relavant ```.csv``` files in ```SC2002_SCE3_Group1/foms/originalfiles``` 

## Report
For information about our assumptions, OOP concepts, and packages, please refer to the report we submitted.

## Contributors
- Li Min
- Ziyan
- Charlton
- Li-Kai
- Xinyi
