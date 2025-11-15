**Project Report:**

The Vehicle Maintenance Tracker is a Java based data management system designed to record and monitor vehicle service information for a garage or a vehicle service center. The system models real world service data, such as the owner’s name, vehicle model, mileage, service cost, warranty status, and the date and time of each maintenance session. 

We decided to include our abstract classes, Vehicle and Recordbase. The purpose of these classes are to define an interface for the classes inheriting from them. RecordBase provides utility methods for validating, such as requireNonBlank(), which makes sure that a String is not blank. The Vehicle class extends RecordBase and is used as a base for different types of vehicles. It has an abstract method getCategory() which is required for each vehicle to define its type. We have four subclasses, Car, Truck, Jeep, Truck, which extend from Vehicle. These classes are needed for polymorphism as these classes are related to each other by inheritence. 

Our ServiceRecord class extends RecordBase. The purpose of this class is to represent a single record's details. The constructor features validation, for example start time must match the service date, and it also features compareTo(), sorting the data in natural order. We made a Comparator class which provides the different methods to sort ServiceRecord objects. Our ServiceRepository class manages the collection of ServiceRecord objects and features the methods, such as adding and removing records.

We have a ServiceRecords test class, which includes JUnit 5 tests focusing on sorting and searching and the consrtuctors. With the validation methods, our system can simply handle invalid inputs from the user by throwing an error. We gained a deeper understanding of how abstraction and polymorphism works, the relevance of having different, flexible ways of sorting data, aswell as defensive coding through the validation checking.


