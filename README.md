# Project
This project is about a musical instrument rental shop. This shop is made for people who want to use musical instrument but don't want to buy it or wanna try the musical instrument before decided to buy the musical instruments.

The system provides functionality for customers who want to rent.   Users of the system include customers of musical instrument rental shops., must be provided the following functionality:

       Display musical instrument rental menu including Rent a Musical Instrument, Return a Musical Instrument, Show Available Instrument, Exit, and Cancel Musical Instrument        Select and item from the menu
       If select 1 : Rent a Musical Instrument
            - Display available instruments:and price for rent per day
            - Enter information including instrument ID, number of rental days, and customer name
            - Display Rent Confirmation
            - Ask : Would customers like to rent another musical instrument?
                 - If select yes : Display available instruments page
                 - If select no : Display summary page and go to homepage

         If select 2 : Return a Musical Instrument
            - Ask for enter customer’s ID
                -if we have rented instruments
                        - Display the instrument’s information that the customer has rented, and Ask: Do the customer want to return this instrument? (yes/no): 
                                         - If select yes : instrument will return
                                          - If select no or don’t have any rented instruments: go to homepage
                -if we don’t have rented instruments
                        -Display “No rentals found for Customer ID … or no rentals were canceled”
             -Return to homepage

        If select 4 : Show Available Musical Instruments 
           - Ask for enter customer’s ID
                - if we have rented instruments
                        -Display “Summary of rent : [Instrument] [Price per days] [Total price]”
                        -Display “Total amount for Customer ID … —> 00.0 Bath”
                -if we don't have any rented instruments
                        -Display “No rental transactions have been made yet”
           -Return to honepage

        If select 5 : Cancel Musical Instruments
           - Ask for enter customer’s ID
                -if we have rented instruments
                        - Display the instrument’s information that the customer has rented, and Ask: Do the customer want to return this instrument? (yes/no): 
                                         - If select yes : instrument will return and show refund price
                                          - If select no or don’t have any rented instruments: go to homepage
                -if we don’t have rented instruments
                        -Display “No rentals found for Customer ID … or no rentals were canceled”
          -Return to homepage

         if select 6 : Exit
            -Exit the system

