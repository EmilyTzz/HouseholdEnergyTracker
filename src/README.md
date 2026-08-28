# Household Energy Tracker

# Author
- Emily Trinh

# Project Description
- This is an application where users can enter their monthly electricity and natural gas 
usage, along with the prices they pay per kWh and GJ. The program calculates their monthly 
energy costs and emissions and keeps track of their usage throughout the year. Users can 
view a summary of their monthly usage and statistics, such as which month had the highest 
usage, emissions, or costs. They can also see whether their average emissions have increased
or decreased over time. The application also has an interactive feature where the logo 
changes its facial expression when a user's emissions for a month are higher than their
average emissions.
- NOTE: This application can currently only track the usage for a year

# Running as a JAR file

1. Go to https://gluonhq.com/products/javafx/ to download the latest JavaFX sdk.
2. Open the command prompt in the location of the JAR file: target
3. Then, run the following command:
    - java --module-path "C:\Program Files\Java\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics -jar HouseholdEnergyTracker-1.0.jar

Note:
- You must have JavaFX SDK installed
- Replace the path above with the location of your JavaFX lib folder
- The HouseholdEnergyTracker-1.0.jar file must be located in the folder where you run the command

# How To Use The Application

# Enter a Monthly Usage
1. Navigate to the 1st tab
2. Select the month to enter the electricity and natural gas usage
3. Click Enter, once everything is filled

# Edit Price/kwH or Price/GJ
1. Navigate to the 2nd tab
2. Enter the new prices /kwH and /GJ
3. Click Enter once everything is filled

# View Overview
1. Navigate to the 3rd tab
2. To sort the data in a specific order, select from "Sort Options". Here, it will display the summaries in the sorted order and a bar chart that compare the months and the values being sorted
3. To view individual summaries of each of the month, select from "Monthly Overview"
4. To view usage statistics, select "Usage Statistics". Here, it will display pie charts that chose how much each month contribute to the overall average emission, and total costs

# Saving Data

1. Click "File"
2. Choose "Save" 
3. Pick file location 

# Loading Data

1. Click "File"
2. Choose Load
3. Select a usage CSV file to load from

# Status Messages

- Left side shows any successful action
- Right side shows any unsuccessful action
