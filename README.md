**Project Report:**

*Domain Overview*

The Vehicle Maintenance Tracker is a Java based data management system designed to record and monitor vehicle service information for a garage or a vehicle service center. The system models real world service data, such as the owner’s name, vehicle model, mileage, service cost, warranty status, and the date and time of each maintenance session. We decided to include our abstract classes, Vehicle and Recordbase. RecordBase provides utility methods for validating, such as requireNonBlank(), which makes sure that a String is not blank. The Vehicle class extends RecordBase and is used as a base for different types of vehicles. It has an abstract method getCategory() which is required for each vehicle to define its type. We have four subclasses, Car, Truck, Jeep, Truck, which extend from Vehicle. Our ServiceRecord class extends RecordBase, and it is to represent a single record's details. We made a Comparator class which provides the different methods to sort ServiceRecord objects. Our ServiceRepository class manages the collection of ServiceRecord objects and features the methods, such as adding and removing records.We also have a ServiceRecords test class, which includes JUnit 5 tests focusing on sorting and searching and the consrtuctors.

*Design justification*

This data system emphasises object oriented design principles, showcasing polymorphism, inheritence. Our system contains two abstract classes, RecordBase and Vehicle. The purpose of these classes are to define an interface for the classes inheriting from them. These classes are needed for polymorphism as these classes are related to each other by inheritence. The constructor of the ServiceRecord class features validation through the RecordBase abstract class, and for example start time must match the service date. The validation ensures that these fields are not left blank and contains positive values. ServiceRecord also features compareTo(), sorting the data in natural order. Inheritence is used for the Vehicle class and its subclasses. This allows the polymorphic handling of vehicles and allows any new vehicle types to be introduced. The comparators in the Comparator class provides reusable sorting on different fields allowing the data to be organised in various ways.The ServiceRecords class is used for managing the record objects. It contains various methods, such as adding a new record, displaying the records, counting the records, removing the records, finding records, and easy sorting methods. The CSV loader class adds to the data system by reading csv files that store data into records, allowing the system to be populated with data. Our ServiceRecordTest class allows us to test the methods that were created. With the validation methods, our system can simply handle invalid inputs such as null values from the user by throwing an error. 

*Defensive coding examples*
1. requireNonBlank() - This method in the RecordsBase class makes sure that the main string fields like ownerName, workOrderId, and serviceType are not left empty or null. It makes it a requirement for the user to add a value into these fields. This means that invalid records are not included in the system. Creating a record with a blank string will throw back an IllegalArgumentException.

2. requirePositive() - This method in the RecordsBase class ensures that the value for serviceCost is above zero. This means that if the user creates a record with an invalid serviceCost value, it will throw back an IllegalArgumentException. This prevents unrealistic data from being recorded in the system.

3. if (!startTime.toLocalDate().equals(serviceDate))
    { throw new IllegalArgumentException("startTime date must match serviceDate"); } - This if statement in the ServiceRecord constructor ensures that the start time must match the service date. If these two values do not match, then IllegalArgumentException("startTime date must match serviceDate") is thrown. This is to prevent inconsistent data about the service in the record.

4. if (serviceDate.isAfter(LocalDate.now()))
   { throw new IllegalArgumentException("serviceDate cannot be in the future"); } - This if statement in the ServiceRecord constructor ensures that future service dates are not accepted as the date of service cannot be in the future. If the serviceDate is set to a future date, then IllegalArgumentException("serviceDate cannot be in the future") is thrown. This is also to prevent inconsistent data to be stored in the record.

5. this.vehicle = Objects.requireNonNull(vehicle, "vehicle") - This makes sure that every service record in the system has a vehicle associated with it. If the user does not enter a valid vehicle with the record, an exception is thrown in order to avoid null inputs in the system which can affect records searching.

*Contribution*

Aloka has created the various classes and subclasses as well as the CSV file loading. Adhira has created the test classes and documentation.

*Adhira's Reflection:*

From working through creating the vehicle maintainence system I am gaining a deeper understanding of how abstraction and polymorphism works, the relevance of having different, flexible ways of sorting data, aswell as defensive coding through the validation checking. My focus was on working on various JUnit tests for the Service Record, Vehicle and Service Repository classes, and I understood the important role that testing has in object oriented programming. Writing the tests for cases such as null values, null vehicles or mismatched start times and dates made me aware of the errors that are to be expected to occur. I discovered that using parameterised tests make the testing process more efficient as you are able to test a method using multiple values at once. Working on this project is improving my ability to create a reliable data management system.

*Aloka's reflection:*
During Stage 1 of this project, I built the core structure of our Vehicle Maintenance Tracker system. I designed and implemented all of the required classes, including Vehicle and its subclasses (Car, Jeep, Truck, and Van) using inheritance and an abstract class. This helped me understand how shared behaviour can be placed in one parent class and how subclasses can override specific details. I also created ServiceRecord and ServiceRepository, and added several searching, sorting, and filtering methods using ArrayLists and basic loops.

Another important part of this stage was CSV loading. I learned how to use StringTokenizer, how to validate input safely, and how to skip bad rows without crashing the program. Overall, Stage 1 improved my understanding of object-oriented design, defensive coding, and handling real-world data. I feel more confident moving into Stage 2, where we will extend the system with hashing, equality, and more advanced queries.


